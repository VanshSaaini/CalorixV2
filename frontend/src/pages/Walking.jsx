import { useEffect, useRef, useState } from 'react';
import { Play, Square, Footprints, Trash2, MapPin } from 'lucide-react';
import toast from 'react-hot-toast';
import { walkingApi } from '../api/endpoints';
import { useAuth } from '../context/AuthContext';
import PageHeader from '../components/PageHeader';
import Loader from '../components/Loader';
import EmptyState from '../components/EmptyState';
import { fmtDate } from '../utils/format';
import { haversineMeters, MIN_ACCURATE_JUMP_METERS, fmtDuration, fmtDistance, fmtPace } from '../utils/geo';

const TICK_MS = 1000;

export default function Walking() {
  const { user } = useAuth();

  const [tracking, setTracking] = useState(false);
  const [elapsedSeconds, setElapsedSeconds] = useState(0);
  const [distanceMeters, setDistanceMeters] = useState(0);
  const [gpsStatus, setGpsStatus] = useState('idle'); // idle | acquiring | active | error
  const [saving, setSaving] = useState(false);

  const [sessions, setSessions] = useState([]);
  const [loadingHistory, setLoadingHistory] = useState(true);

  const watchIdRef = useRef(null);
  const timerRef = useRef(null);
  const startTimeRef = useRef(null);
  const lastPointRef = useRef(null);
  const routePointsRef = useRef([]);

  const loadHistory = async () => {
    setLoadingHistory(true);
    try {
      const data = await walkingApi.list(user.id);
      setSessions(data || []);
    } catch {
      setSessions([]);
    } finally {
      setLoadingHistory(false);
    }
  };

  useEffect(() => {
    if (user?.id) loadHistory();
  }, [user?.id]);

  useEffect(() => {
    // Clean up geolocation watch and timer if the user navigates away mid-walk.
    return () => {
      if (watchIdRef.current !== null) navigator.geolocation.clearWatch(watchIdRef.current);
      if (timerRef.current) clearInterval(timerRef.current);
    };
  }, []);

  const handlePosition = (position) => {
    const point = {
      latitude: position.coords.latitude,
      longitude: position.coords.longitude,
      recordedAt: new Date().toISOString(),
    };

    setGpsStatus('active');

    if (lastPointRef.current) {
      const jump = haversineMeters(lastPointRef.current, point);
      if (jump >= MIN_ACCURATE_JUMP_METERS) {
        setDistanceMeters((prev) => prev + jump);
        lastPointRef.current = point;
        routePointsRef.current.push({ ...point, sequence: routePointsRef.current.length });
      }
    } else {
      lastPointRef.current = point;
      routePointsRef.current.push({ ...point, sequence: 0 });
    }
  };

  const startWalk = () => {
    if (!navigator.geolocation) {
      toast.error('Geolocation is not supported on this device.');
      return;
    }

    setElapsedSeconds(0);
    setDistanceMeters(0);
    lastPointRef.current = null;
    routePointsRef.current = [];
    startTimeRef.current = new Date();
    setGpsStatus('acquiring');
    setTracking(true);

    watchIdRef.current = navigator.geolocation.watchPosition(handlePosition, () => {
      setGpsStatus('error');
      toast.error('Could not read your location. Check location permissions.');
    }, {
      enableHighAccuracy: true,
      maximumAge: 2000,
      timeout: 15000,
    });

    timerRef.current = setInterval(() => {
      setElapsedSeconds((prev) => prev + 1);
    }, TICK_MS);
  };

  const stopWalk = async () => {
    if (watchIdRef.current !== null) {
      navigator.geolocation.clearWatch(watchIdRef.current);
      watchIdRef.current = null;
    }
    if (timerRef.current) {
      clearInterval(timerRef.current);
      timerRef.current = null;
    }

    setTracking(false);
    setGpsStatus('idle');

    const endTime = new Date();

    if (elapsedSeconds < 5) {
      toast.error('Walk too short to save.');
      return;
    }

    setSaving(true);
    try {
      await walkingApi.save(user.id, {
        startTime: startTimeRef.current.toISOString(),
        endTime: endTime.toISOString(),
        durationSeconds: elapsedSeconds,
        distanceMeters,
        routePoints: routePointsRef.current,
      });
      toast.success('Walk saved.');
      loadHistory();
    } catch (err) {
      toast.error(err?.response?.data?.message || 'Failed to save walk');
    } finally {
      setSaving(false);
    }
  };

  const remove = async (id) => {
    if (!confirm('Delete this walking session?')) return;
    try {
      await walkingApi.delete(id);
      toast.success('Deleted.');
      loadHistory();
    } catch {
      toast.error('Failed to delete');
    }
  };

  const gpsLabel = {
    idle: 'GPS idle',
    acquiring: 'Acquiring GPS signal…',
    active: 'GPS locked',
    error: 'GPS unavailable',
  }[gpsStatus];

  return (
    <div>
      <PageHeader title="Walking" subtitle="Start a walk and CalorixV2 tracks your time and distance live." />

      <div className="grid gap-6 lg:grid-cols-[1.4fr,1fr]">
        <div className="card flex flex-col items-center gap-8 py-10">
          <div className="flex items-center gap-2 text-xs uppercase tracking-[0.24em] text-ink-500">
            <MapPin className={`h-3.5 w-3.5 ${gpsStatus === 'active' ? 'text-sage-500' : 'text-ink-500'}`} />
            {gpsLabel}
          </div>

          <div className="flex flex-col items-center">
            <p className="h-serif text-6xl font-semibold text-ink-900 tabular-nums" data-testid="walk-timer">
              {fmtDuration(elapsedSeconds)}
            </p>
            <p className="mt-1 text-xs uppercase tracking-[0.24em] text-ink-500">Elapsed</p>
          </div>

          <div className="flex flex-col items-center">
            <p className="h-serif text-3xl font-semibold text-sage-600 tabular-nums" data-testid="walk-distance">
              {fmtDistance(distanceMeters)}
            </p>
            <p className="mt-1 text-xs uppercase tracking-[0.24em] text-ink-500">Distance</p>
          </div>

          {!tracking ? (
            <button
              onClick={startWalk}
              disabled={saving}
              data-testid="start-walk-btn"
              className="flex items-center gap-2 rounded-full bg-sage-500 px-8 py-3.5 text-sm font-semibold text-cream-50 shadow-soft transition hover:bg-sage-600 disabled:opacity-60"
            >
              <Play className="h-4 w-4" /> Start walk
            </button>
          ) : (
            <button
              onClick={stopWalk}
              data-testid="stop-walk-btn"
              className="flex items-center gap-2 rounded-full bg-clay-500 px-8 py-3.5 text-sm font-semibold text-cream-50 shadow-soft transition hover:bg-clay-600"
            >
              <Square className="h-4 w-4" /> Stop & save
            </button>
          )}
        </div>

        <div className="card">
          <p className="h-serif text-xl font-semibold text-ink-900">History</p>
          <p className="mb-4 text-xs uppercase tracking-[0.24em] text-ink-500">Recent walks</p>

          {loadingHistory ? (
            <Loader />
          ) : sessions.length === 0 ? (
            <EmptyState title="No walks yet" hint="Start your first walk to see it here." />
          ) : (
            <ul className="space-y-3">
              {sessions.map((s) => (
                <li
                  key={s.id}
                  className="flex items-center justify-between rounded-2xl border border-cream-200 bg-cream-50 px-4 py-3"
                  data-testid="walk-session-row"
                >
                  <div className="flex items-center gap-3">
                    <span className="grid h-9 w-9 place-items-center rounded-xl bg-sage-50 text-sage-500">
                      <Footprints className="h-4 w-4" />
                    </span>
                    <div>
                      <p className="text-sm font-semibold text-ink-900">{fmtDistance(s.distanceMeters)}</p>
                      <p className="text-xs text-ink-500">
                        {fmtDate(s.startTime)} · {fmtDuration(s.durationSeconds)} · {fmtPace(s.distanceMeters, s.durationSeconds)}
                      </p>
                    </div>
                  </div>
                  <button
                    onClick={() => remove(s.id)}
                    className="text-ink-500 transition hover:text-clay-600"
                    aria-label="Delete walk"
                  >
                    <Trash2 className="h-4 w-4" />
                  </button>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
    </div>
  );
}
