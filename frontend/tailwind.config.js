module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  plugins: [require("@tailwindcss/line-clamp")],
  theme: {
    extend: {
      keyframes: {
        "fade-in": {
          "0%": {
            opacity: 0,
          },
          "100%": {
            opacity: 1,
          },
        },
        "fade-in-top": {
          "0%": {
            opacity: "0",
            transform: "translateY(100px)",
          },
          "100%": {
            opacity: "1",
            transform: "translateY(0)",
          },
        },
      },
      animation: {
        "fade-in": "fade-in 1s ease-in-out",
        "fade-in-top": "fade-in-top 0.5s ease-in-out",
      },
    },
    colors: {
      transparent: "transparent",
      brand: "#2F70D5",
      "brand-light": "#83C6FF",
      "brand-lighter": "#B3D5F2",
      correct: "#50B66B",
      warning: "#FEA446",
      error: "#F55353",
      white: "#fff",
      black: "#000",
    },
  },
};
