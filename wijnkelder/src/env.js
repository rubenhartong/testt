// Draait de app als artifact binnen Claude? Dan gebruiken we Claude's eigen opslag en model
// in plaats van IndexedDB en een API-sleutel.
export const IN_CLAUDE = typeof window !== "undefined" && typeof window.claude?.use === "function";

const use = (name) => (IN_CLAUDE ? window.claude.use(name).catch(() => null) : Promise.resolve(null));

export const claudeDb = use("db");
export const claudeSample = use("sample");
export const claudeDownloads = use("downloads");
export const claudeUser = use("user");
