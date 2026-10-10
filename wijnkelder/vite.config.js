import { defineConfig } from "vite";

// base "./" zodat de build vanaf elke submap (bv. GitHub Pages) werkt
export default defineConfig({
  base: "./",
});
