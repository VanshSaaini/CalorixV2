// Haversine distance between two lat/lng points, in meters.
export function haversineMeters(a, b) {
  if (!a || !b) return 0;
  const R = 6371000; // Earth radius in meters
  const toRad = (d) => (d * Math.PI) / 180;

  const dLat = toRad(b.latitude - a.latitude);
  const dLng = toRad(b.longitude - a.longitude);

  const lat1 = toRad(a.latitude);
  const lat2 = toRad(b.latitude);

  const h =
    Math.sin(dLat / 2) ** 2 +
    Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLng / 2) ** 2;

  const c = 2 * Math.atan2(Math.sqrt(h), Math.sqrt(1 - h));

  return R * c;
}

// GPS points can jitter a meter or two even while standing still.
// Ignore hops smaller than this so live distance doesn't creep upward.
export const MIN_ACCURATE_JUMP_METERS = 2;

export function fmtDuration(totalSeconds) {
  const s = Math.max(0, Math.floor(totalSeconds));
  const hh = Math.floor(s / 3600);
  const mm = Math.floor((s % 3600) / 60);
  const ss = s % 60;
  const pad = (n) => String(n).padStart(2, '0');
  return hh > 0 ? `${pad(hh)}:${pad(mm)}:${pad(ss)}` : `${pad(mm)}:${pad(ss)}`;
}

export function fmtDistance(meters) {
  if (!meters || meters < 0) return '0.00 km';
  return `${(meters / 1000).toFixed(2)} km`;
}

// average pace in minutes per km, formatted as "m:ss /km"
export function fmtPace(meters, seconds) {
  if (!meters || meters < 10 || !seconds) return '—';
  const secPerKm = seconds / (meters / 1000);
  const m = Math.floor(secPerKm / 60);
  const s = Math.round(secPerKm % 60);
  return `${m}:${String(s).padStart(2, '0')} /km`;
}
