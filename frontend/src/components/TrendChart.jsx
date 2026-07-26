import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Area,
  AreaChart,
} from 'recharts';
import { fmtShort } from '../utils/format';

const COLORS = {
  sage: '#527B45',
  clay: '#C2734A',
  ink: '#3A4132',
  blue: '#5D89A6',
};

export default function TrendChart({ data, dataKey, color = 'sage', unit = '', type = 'area' }) {
  if (!data || data.length === 0) {
    return (
      <div className="flex h-64 items-center justify-center text-sm text-ink-500">
        No data yet — add your first record to see trends.
      </div>
    );
  }

  const chartData = [...data]
    .sort((a, b) => new Date(a.recordDate) - new Date(b.recordDate))
    .map((d) => ({ ...d, date: fmtShort(d.recordDate) }));

  const stroke = COLORS[color] || color;

  return (
    <div className="h-64 w-full">
      <ResponsiveContainer width="100%" height="100%">
        {type === 'area' ? (
          <AreaChart data={chartData} margin={{ top: 5, right: 10, left: -20, bottom: 0 }}>
            <defs>
              <linearGradient id={`fill-${color}`} x1="0" y1="0" x2="0" y2="1">
                <stop offset="0%" stopColor={stroke} stopOpacity={0.35} />
                <stop offset="100%" stopColor={stroke} stopOpacity={0} />
              </linearGradient>
            </defs>
            <CartesianGrid strokeDasharray="3 6" stroke="#EAE3D0" />
            <XAxis dataKey="date" stroke="#5B6250" style={{ fontSize: 11 }} />
            <YAxis stroke="#5B6250" style={{ fontSize: 11 }} />
            <Tooltip
              contentStyle={{
                background: '#FBF8F1',
                border: '1px solid #EAE3D0',
                borderRadius: 12,
                fontSize: 12,
              }}
              formatter={(v) => [`${v} ${unit}`, dataKey]}
            />
            <Area
              type="monotone"
              dataKey={dataKey}
              stroke={stroke}
              strokeWidth={2.5}
              fill={`url(#fill-${color})`}
            />
          </AreaChart>
        ) : (
          <LineChart data={chartData} margin={{ top: 5, right: 10, left: -20, bottom: 0 }}>
            <CartesianGrid strokeDasharray="3 6" stroke="#EAE3D0" />
            <XAxis dataKey="date" stroke="#5B6250" style={{ fontSize: 11 }} />
            <YAxis stroke="#5B6250" style={{ fontSize: 11 }} />
            <Tooltip
              contentStyle={{
                background: '#FBF8F1',
                border: '1px solid #EAE3D0',
                borderRadius: 12,
                fontSize: 12,
              }}
            />
            <Line
              type="monotone"
              dataKey={dataKey}
              stroke={stroke}
              strokeWidth={2.5}
              dot={{ r: 3, fill: stroke }}
            />
          </LineChart>
        )}
      </ResponsiveContainer>
    </div>
  );
}
