import { defineConfig } from "vite";

// base "./" zodat de build vanaf elke submap (bv. GitHub Pages) werkt.
// `vite build --mode artifact` maakt één bundel zonder losse chunks voor de Claude-artifactversie.
export default defineConfig(({ mode }) => ({
  base: "./",
  build:
    mode === "artifact"
      ? { outDir: "dist-artifact", rollupOptions: { output: { inlineDynamicImports: true } } }
      : {},
}));
