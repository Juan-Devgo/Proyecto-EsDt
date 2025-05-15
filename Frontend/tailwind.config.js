/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          light: '#4da6ff',
          DEFAULT: '#0078ff',
          dark: '#0057b8',
        },
        amber: {
          300: '#e09f3e',
          400: '#d69429',
          500: '#c78500',
        },
        'blue-gray': {
          500: '#335c67',
        },
        red: {
          700: '#9e2a2b',
        }
      },
    },
  },
  plugins: [],
}