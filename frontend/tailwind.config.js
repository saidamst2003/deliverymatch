/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        'khaki': '#BCB88A',
        'khaki-dark': '#A9A47A', // A darker shade for hover
        'deep-green': '#006400',
        'moss-green': '#8A9A5B',
        'forest-green': '#228B22',
        'medium-green': '#3CB371',
      }
    },
  },
  plugins: [],
}

