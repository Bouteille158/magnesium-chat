import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";
import { VitePWA } from "vite-plugin-pwa";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    react(),
    VitePWA({
      strategies: "injectManifest",
      srcDir: "src",
      filename: "sw.ts",
      registerType: "autoUpdate",
      devOptions: {
        enabled: true,
      },
      manifest: {
        name: "Magnesium chat",
        short_name: "Magnesium chat",
        theme_color: "#ffffff",
        background_color: "#ffffff",
        icons: [
          {
            src: "/android-chrome-192x192.png",
            sizes: "192x192",
            type: "image/png",
          },
          {
            src: "/android-chrome-512x512.png",
            sizes: "512x512",
            type: "image/png",
          },
        ],
        start_url: "/",
        display: "standalone",
      },
      workbox: {
        runtimeCaching: [
          {
            urlPattern: /\.(?:js|css)$/,
            handler: "StaleWhileRevalidate",
          },
          {
            urlPattern: /^\/$/, // la page d'accueil
            handler: "NetworkFirst",
            options: {
              cacheName: "homepage",
            },
          },
        ],
      },
    }),
  ],
});
