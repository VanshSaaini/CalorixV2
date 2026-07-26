import { format, parseISO } from 'date-fns';

export const today = () => format(new Date(), 'yyyy-MM-dd');

export const fmtDate = (iso) => {
  if (!iso) return '—';
  try {
    return format(typeof iso === 'string' ? parseISO(iso) : iso, 'dd MMM yyyy');
  } catch {
    return iso;
  }
}

export const fmtShort = (iso) => {
  if (!iso) return '';
  try {
    return format(typeof iso === 'string' ? parseISO(iso) : iso, 'dd MMM');
  } catch {
    return iso;
  }
};

export const num = (v, d = 1) => {
  if (v === null || v === undefined || v === '') return '—';
  const n = Number(v);
  return Number.isFinite(n) ? n.toFixed(d) : '—';
};

export const bmiCategory = (b) => {
  if (!b) return { label: '—', tone: 'sage' };
  if (b < 18.5) return { label: 'Underweight', tone: 'clay' };
  if (b < 25) return { label: 'Healthy', tone: 'sage' };
  if (b < 30) return { label: 'Overweight', tone: 'clay' };
  return { label: 'Obese', tone: 'clay' };
};

export const computeBmi = (weightKg, heightCm) => {
  if (!weightKg || !heightCm) return null;
  const m = heightCm / 100;
  return Number((weightKg / (m * m)).toFixed(1));
};

// Mifflin-St Jeor
export const computeBmr = (weight, height, age, gender) => {
  if (!weight || !height || !age) return null;
  const base = 10 * Number(weight) + 6.25 * Number(height) - 5 * Number(age);
  const bmr = gender === 'MALE' ? base + 5 : gender === 'FEMALE' ? base - 161 : base - 78;
  return Number(bmr.toFixed(0));
};

export const activityMultiplier = (level) =>
  ({
    SEDENTARY: 1.2,
    LIGHTLY_ACTIVE: 1.375,
    MODERATELY_ACTIVE: 1.55,
    VERY_ACTIVE: 1.725,
    EXTRA_ACTIVE: 1.9,
  }[level] || 1.2);
