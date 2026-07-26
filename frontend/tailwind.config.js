/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx}'],
  theme: {
    extend: {
      colors: {
        cream: {
          50: '#FBF8F1',
          100: '#F5F1E6',
          200: '#EAE3D0',
        },
        sage: {
          50: '#EEF3EC',
          100: '#D8E4D3',
          200: '#B3C9AA',
          300: '#8EAE81',
          400: '#6E9560',
          500: '#527B45',
          600: '#3F5F35',
          700: '#2F4527',
        },
        clay: {
          400: '#D98E5F',
          500: '#C2734A',
          600: '#A25938',
        },
        ink: {
          900: '#1C2117',
          700: '#3A4132',
          500: '#5B6250',
        },
      },
      fontFamily: {
        serif: ['Fraunces', 'ui-serif', 'Georgia', 'serif'],
        sans: ['Manrope', 'ui-sans-serif', 'system-ui'],
      },
      boxShadow: {
        soft: '0 1px 2px rgba(28,33,23,0.04), 0 8px 24px rgba(28,33,23,0.06)',
        ring: '0 0 0 4px rgba(142,174,129,0.25)',
      },
      backgroundImage: {
        'grain':
          "url(\"data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='120' height='120'><filter id='n'><feTurbulence type='fractalNoise' baseFrequency='0.9' numOctaves='2'/><feColorMatrix values='0 0 0 0 0.11 0 0 0 0 0.13 0 0 0 0 0.09 0 0 0 0.05 0'/></filter><rect width='100%25' height='100%25' filter='url(%23n)'/></svg>\")",
      },
    },
  },
  plugins: [],
};
