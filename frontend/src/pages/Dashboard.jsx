import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  Scale,
  Activity,
  Flame,
  Droplets,
  Ruler,
  Image as ImgIcon,
  Utensils,
  ArrowUpRight,
} from "lucide-react";
import { useAuth } from "../context/AuthContext";
import { dashboardApi } from "../api/endpoints";
import PageHeader from "../components/PageHeader";
import StatCard from "../components/StatCard";
import Loader from "../components/Loader";
import { fmtDate, num, bmiCategory } from "../utils/format";

export default function Dashboard() {
  const { user } = useAuth();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    dashboardApi
      .get()
      .then(setData)
      .catch((err) => {
        console.error(err);
        setData({});
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <Loader />;
  const d = data || {};
  const bmiCat = bmiCategory(d.latestBmi?.bmi);

  return (
    <div>
      <PageHeader
        title={`Hello, ${user?.firstName || "friend"}.`}
        subtitle="A calm look at how your body has been today, this week, and this season."
        testid="dashboard-title"
      />

      <div className="grid gap-5 md:grid-cols-2 lg:grid-cols-4">
        <StatCard
          testid="stat-weight"
          label="Latest Weight"
          value={num(d.latestWeight?.weight)}
          unit="kg"
          hint={
            d.latestWeight ? fmtDate(d.latestWeight.recordDate) : "No data yet"
          }
          icon={Scale}
          tone="sage"
        />
        <StatCard
          testid="stat-bmi"
          label="BMI"
          value={num(d.latestBmi?.bmi)}
          hint={d.latestBmi?.category || bmiCat.label}
          icon={Activity}
          tone="cream"
        />
        <StatCard
          testid="stat-bmr"
          label="BMR"
          value={num(d.latestBmr?.bmr, 0)}
          unit="kcal"
          hint={d.latestBmr?.activityLevel?.replaceAll("_", " ") || "—"}
          icon={Flame}
          tone="clay"
        />
        <StatCard
          testid="stat-water"
          label="Water"
          value={num(d.latestWater?.litres)}
          unit="L"
          hint={
            d.latestWater
              ? fmtDate(d.latestWater.recordDate)
              : "Log your first sip"
          }
          icon={Droplets}
          tone="ink"
        />
      </div>

      <div className="mt-6 grid gap-5 lg:grid-cols-3">
        <div className="card lg:col-span-2">
          <div className="mb-4 flex items-center justify-between">
            <div>
              <p className="text-xs uppercase tracking-[0.24em] text-ink-500">
                Active Goal
              </p>
              <p className="h-serif text-2xl font-semibold text-ink-900">
                {d.activeGoal?.goalType?.replaceAll("_", " ") ||
                  "No active goal"}
              </p>
            </div>
            <Link
              to="/goals"
              className="btn-secondary"
              data-testid="dashboard-goals-link"
            >
              Manage <ArrowUpRight className="h-3.5 w-3.5" />
            </Link>
          </div>
          {d.activeGoal ? (
            <div className="grid grid-cols-2 gap-4 md:grid-cols-4">
              <MetaBlock
                label="Target weight"
                value={`${num(d.activeGoal.targetWeight)} kg`}
              />
              <MetaBlock
                label="Target calories"
                value={`${num(d.activeGoal.targetCalories, 0)} kcal`}
              />
              <MetaBlock
                label="Weekly Δ"
                value={`${num(d.activeGoal.weeklyTarget)} kg`}
              />
              <MetaBlock
                label="Deadline"
                value={fmtDate(d.activeGoal.targetDate)}
              />
            </div>
          ) : (
            <p className="text-sm text-ink-500">
              Set a goal to see progress deltas and get gentle nudges here.
            </p>
          )}
        </div>

        <div className="card">
          <p className="text-xs uppercase tracking-[0.24em] text-ink-500">
            Today's Macros
          </p>
          <p className="h-serif mt-1 text-2xl font-semibold text-ink-900">
            {num(d.latestMacros?.calories, 0)}{" "}
            <span className="text-base text-ink-500">kcal</span>
          </p>
          <div className="mt-4 space-y-2 text-sm">
            <Bar
              label="Protein"
              value={d.latestMacros?.protein}
              color="bg-sage-500"
            />
            <Bar
              label="Carbs"
              value={d.latestMacros?.carbohydrates}
              color="bg-clay-500"
            />
            <Bar label="Fats" value={d.latestMacros?.fats} color="bg-ink-700" />
          </div>
          <Link
            to="/macros"
            className="btn-ghost mt-4 w-full justify-center"
            data-testid="dashboard-macros-link"
          >
            Log macros <Utensils className="h-3.5 w-3.5" />
          </Link>
        </div>
      </div>

      <div className="mt-6 grid gap-5 md:grid-cols-2 lg:grid-cols-3">
        <MiniCard title="Calories" icon={Flame} to="/calories">
          <p className="h-serif text-3xl font-semibold text-ink-900">
            {num(d.latestCalories?.consumedCalories, 0)}{" "}
            <span className="text-sm text-ink-500">consumed</span>
          </p>
          <p className="mt-1 text-sm text-ink-500">
            Burned {num(d.latestCalories?.burnedCalories, 0)} · Remaining{" "}
            {num(d.latestCalories?.remainingCalories, 0)}
          </p>
        </MiniCard>

        <MiniCard title="Body Measurement" icon={Ruler} to="/body-measurements">
          <p className="h-serif text-3xl font-semibold text-ink-900">
            {num(d.latestMeasurement?.waist)}
            <span className="text-sm text-ink-500"> cm waist</span>
          </p>
          <p className="mt-1 text-sm text-ink-500">
            Chest {num(d.latestMeasurement?.chest)} · Hips{" "}
            {num(d.latestMeasurement?.hips)}
          </p>
        </MiniCard>

        <MiniCard title="Latest Progress Photo" icon={ImgIcon} to="/photos">
          {d.latestPhoto?.imageUrl ? (
            <img
              src={d.latestPhoto.imageUrl}
              alt="progress"
              className="mt-1 h-28 w-full rounded-2xl object-cover"
            />
          ) : (
            <p className="text-sm text-ink-500">
              No photos yet. Add your first snapshot.
            </p>
          )}
        </MiniCard>
      </div>
    </div>
  );
}

function MetaBlock({ label, value }) {
  return (
    <div className="rounded-2xl border border-cream-200 bg-white/60 p-3">
      <p className="text-[10px] uppercase tracking-[0.2em] text-ink-500">
        {label}
      </p>
      <p className="mt-1 text-base font-semibold text-ink-900">{value}</p>
    </div>
  );
}

function Bar({ label, value, color }) {
  const v = Number(value) || 0;
  const capped = Math.min(v, 300);
  return (
    <div>
      <div className="flex items-center justify-between text-xs">
        <span className="text-ink-500">{label}</span>
        <span className="font-semibold text-ink-900">{v ? `${v} g` : "—"}</span>
      </div>
      <div className="mt-1 h-1.5 w-full overflow-hidden rounded-full bg-cream-200">
        <div
          className={`h-full rounded-full ${color}`}
          style={{ width: `${(capped / 300) * 100}%` }}
        />
      </div>
    </div>
  );
}

function MiniCard({ title, icon: Icon, to, children }) {
  return (
    <Link
      to={to}
      className="card group transition hover:shadow-lg hover:-translate-y-0.5"
    >
      <div className="mb-3 flex items-center justify-between">
        <p className="text-xs uppercase tracking-[0.24em] text-ink-500">
          {title}
        </p>
        <span className="grid h-8 w-8 place-items-center rounded-full border border-cream-200 text-sage-500 group-hover:bg-sage-500 group-hover:text-cream-50">
          <Icon className="h-4 w-4" />
        </span>
      </div>
      {children}
    </Link>
  );
}
