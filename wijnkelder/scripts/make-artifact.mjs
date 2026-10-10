// Zet de artifact-build om naar één HTML-bestand dat als Claude-artifact gepubliceerd kan worden.
import { readFileSync, writeFileSync, readdirSync } from "node:fs";
import { join } from "node:path";

const dir = "dist-artifact/assets";
const files = readdirSync(dir);
const read = (ext) => files.filter((f) => f.endsWith(ext)).map((f) => readFileSync(join(dir, f), "utf8")).join("\n");

const css = read(".css");
const js = read(".js").replace(/<\/script/gi, "<\\/script");

const html = `<title>Wijnkelder</title>
<style>${css}</style>
<div id="app"></div>
<script type="module">${js}</script>
`;
writeFileSync("dist-artifact/wijnkelder.html", html);
console.log(`dist-artifact/wijnkelder.html (${(html.length / 1024).toFixed(0)} kB)`);
