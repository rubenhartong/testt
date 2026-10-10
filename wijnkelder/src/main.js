import "./style.css";
import { getAllWines, getWine, putWine, deleteWine, clearWines } from "./db.js";
import { lookupWine, MODELS, DEFAULT_MODEL } from "./ai.js";
import { resizeImage } from "./image.js";
import { IN_CLAUDE, claudeSample, claudeDownloads } from "./env.js";

const app = document.getElementById("app");
// Binnen Claude kleinere foto's, zodat elke wijn in één database-document past
const PHOTO_OPTS = IN_CLAUDE ? { maxSize: 1000, quality: 0.75, maxChars: 180_000 } : {};
const YEAR = () => new Date().getFullYear();

// ---------- Instellingen (alleen in deze browser) ----------
const settings = {
  get apiKey() { return safeGet("wk.apiKey") || ""; },
  set apiKey(v) { safeSet("wk.apiKey", v); },
  get model() { return safeGet("wk.model") || DEFAULT_MODEL; },
  set model(v) { safeSet("wk.model", v); },
};
function safeGet(k) { try { return localStorage.getItem(k); } catch { return null; } }
function safeSet(k, v) { try { localStorage.setItem(k, v); } catch { /* opslag geblokkeerd */ } }

// Filterstand van de lijst onthouden tijdens de sessie
const listState = { filter: "alles", query: "", sort: "drinken" };

// ---------- Helpers ----------
const esc = (v) =>
  String(v ?? "").replace(/[&<>"']/g, (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;" })[c]);

const uid = () => (crypto.randomUUID ? crypto.randomUUID() : String(Date.now()) + Math.random());

function toast(msg) {
  const el = document.createElement("div");
  el.className = "toast";
  el.textContent = msg;
  document.body.append(el);
  setTimeout(() => el.remove(), 3500);
}

/** Bevestigingsvenster in de pagina zelf (confirm() werkt niet overal). */
function ask(message, okLabel = "OK", cancelLabel = "Annuleren") {
  return new Promise((resolve) => {
    const wrap = document.createElement("div");
    wrap.className = "modal";
    wrap.innerHTML = `
      <div class="modal-box" role="alertdialog" aria-modal="true" aria-labelledby="modal-msg">
        <p id="modal-msg">${esc(message)}</p>
        <div class="modal-actions">
          <button class="btn" data-answer="0">${esc(cancelLabel)}</button>
          <button class="btn primary" data-answer="1">${esc(okLabel)}</button>
        </div>
      </div>`;
    wrap.addEventListener("click", (e) => {
      const b = e.target.closest("[data-answer]");
      if (!b && e.target !== wrap) return;
      wrap.remove();
      resolve(b?.dataset.answer === "1");
    });
    document.body.append(wrap);
    wrap.querySelector('[data-answer="1"]').focus();
  });
}

function wineTitle(w) {
  return [w.producer, w.name].filter(Boolean).join(" – ") || "Onbekende wijn";
}

/** Bepaalt waar de wijn staat in zijn drinkvenster. */
function drinkStatus(w) {
  const y = YEAR();
  const from = w.drinkFrom, until = w.drinkUntil;
  if (!from && !until) return { key: "onbekend", label: "Drinkvenster onbekend" };
  if (from && y < from) return { key: "bewaren", label: `Bewaren tot ${from}` };
  if (until && y > until) return { key: "over", label: "Over hoogtepunt" };
  if (w.peakFrom && w.peakUntil && y >= w.peakFrom && y <= w.peakUntil)
    return { key: "piek", label: "Op hoogtepunt" };
  if (until && until - y <= 1) return { key: "snel", label: `Drinken vóór eind ${until}` };
  return { key: "nu", label: until ? `Drinkbaar t/m ${until}` : "Nu drinkbaar" };
}

const isDrinkNow = (w) => ["piek", "nu", "snel"].includes(drinkStatus(w).key);

function stars(rating, interactive = false) {
  let html = `<span class="stars${interactive ? " interactive" : ""}" ${interactive ? 'role="radiogroup" aria-label="Mijn beoordeling"' : ""}>`;
  for (let i = 1; i <= 5; i++) {
    const cls = rating >= i ? "on" : rating >= i - 0.5 ? "half" : "";
    html += interactive
      ? `<button type="button" class="star ${cls}" data-rate="${i}" aria-label="${i} sterren">★</button>`
      : `<span class="star ${cls}">★</span>`;
  }
  return html + "</span>";
}

// ---------- Router ----------
let route = location.hash.replace(/^#\/?/, "");
const currentRoute = () => route;
/** Navigeer naar een scherm; buiten Claude houden we ook de URL bij. */
function go(to) {
  route = to.replace(/^#\/?/, "");
  if (!IN_CLAUDE && location.hash !== "#/" + route) history.pushState(null, "", "#/" + route);
  render();
}
// Schermen laden asynchroon; teken niet als de gebruiker intussen ergens anders heen ging
const onRoute = (route) => currentRoute() === route;

window.addEventListener("popstate", () => { route = location.hash.replace(/^#\/?/, ""); render(); });
document.addEventListener("click", (e) => {
  const a = e.target.closest('a[href^="#/"]');
  if (!a) return;
  e.preventDefault();
  go(a.getAttribute("href"));
});
render();

let lastRoute = null;

async function render() {
  const hash = currentRoute();
  const [view, id, sub] = hash.split("/");
  if (hash !== lastRoute) window.scrollTo(0, 0);
  lastRoute = hash;
  if (view === "wijn" && id && sub === "bewerk") return renderEdit(id);
  if (view === "wijn" && id) return renderDetail(id);
  if (view === "nieuw") return renderNew();
  if (view === "instellingen") return renderSettings();
  if (hash) return go(""); // onbekende route → lijst
  return renderList();
}

// ---------- Lijst ----------
async function renderList() {
  const wines = await getAllWines();
  if (!onRoute("")) return;
  const inStock = wines.filter((w) => (w.quantity ?? 0) > 0);
  const bottles = inStock.reduce((n, w) => n + (w.quantity ?? 0), 0);
  const nowCount = inStock.filter(isDrinkNow).length;
  const overCount = inStock.filter((w) => drinkStatus(w).key === "over").length;

  const filters = {
    alles: () => true,
    nu: (w) => isDrinkNow(w) && w.quantity > 0,
    bewaren: (w) => drinkStatus(w).key === "bewaren" && w.quantity > 0,
    over: (w) => drinkStatus(w).key === "over" && w.quantity > 0,
    op: (w) => !w.quantity,
  };
  const sorters = {
    drinken: (a, b) => (a.drinkUntil ?? 9999) - (b.drinkUntil ?? 9999),
    recent: (a, b) => b.createdAt - a.createdAt,
    naam: (a, b) => wineTitle(a).localeCompare(wineTitle(b), "nl"),
    jaargang: (a, b) => (a.vintage ?? 9999) - (b.vintage ?? 9999),
    score: (a, b) => (b.myRating ?? 0) - (a.myRating ?? 0),
  };

  const q = listState.query.toLowerCase();
  const shown = wines
    .filter(filters[listState.filter])
    .filter((w) =>
      !q ||
      [w.name, w.producer, w.region, w.country, w.appellation, w.vintage, w.location, ...(w.grapes || [])]
        .join(" ").toLowerCase().includes(q))
    .sort(sorters[listState.sort]);

  const chip = (key, label) =>
    `<button class="chip${listState.filter === key ? " active" : ""}" data-filter="${key}">${label}</button>`;

  app.innerHTML = `
    <header class="topbar">
      <h1>🍷 Wijnkelder</h1>
      <a class="icon-btn" href="#/instellingen" aria-label="Instellingen">⚙️</a>
    </header>
    <main class="page">
      ${!IN_CLAUDE && !settings.apiKey ? `<div class="notice">Stel eerst je <a href="#/instellingen">Anthropic API-sleutel</a> in om wijnen automatisch te laten herkennen.</div>` : ""}
      <section class="stats">
        <div><strong>${bottles}</strong><span>flessen</span></div>
        <div><strong>${nowCount}</strong><span>nu drinken</span></div>
        <div class="${overCount ? "warn" : ""}"><strong>${overCount}</strong><span>over hoogtepunt</span></div>
      </section>
      <div class="search-row">
        <input type="search" id="search" placeholder="Zoek op naam, streek, druif…" value="${esc(listState.query)}">
        <select id="sort" aria-label="Sorteren">
          <option value="drinken">Eerst drinken</option>
          <option value="recent">Recent toegevoegd</option>
          <option value="naam">Naam</option>
          <option value="jaargang">Jaargang</option>
          <option value="score">Mijn score</option>
        </select>
      </div>
      <nav class="chips">
        ${chip("alles", "Alles")}${chip("nu", "Nu drinken")}${chip("bewaren", "Bewaren")}${chip("over", "Over hoogtepunt")}${chip("op", "Op")}
      </nav>
      <ul class="wine-list">
        ${shown.map(cardHtml).join("") ||
          `<li class="empty">${wines.length ? "Geen wijnen gevonden." : "Nog geen wijnen. Tik op <b>+</b> om je eerste fles toe te voegen."}</li>`}
      </ul>
    </main>
    <a class="fab" href="#/nieuw" aria-label="Wijn toevoegen">+</a>`;

  app.querySelector("#sort").value = listState.sort;
  app.querySelector("#sort").onchange = (e) => { listState.sort = e.target.value; renderList(); };
  const search = app.querySelector("#search");
  search.oninput = (e) => {
    listState.query = e.target.value;
    renderList().then(() => {
      const s = app.querySelector("#search");
      s.focus();
      s.setSelectionRange(s.value.length, s.value.length);
    });
  };
  app.querySelectorAll("[data-filter]").forEach((b) =>
    b.onclick = () => { listState.filter = b.dataset.filter; renderList(); });
}

function cardHtml(w) {
  const st = drinkStatus(w);
  const sub = [w.vintage, w.region || w.country, w.type].filter(Boolean).join(" · ");
  return `
    <li>
      <a class="card" href="#/wijn/${w.id}">
        <div class="thumb">${w.photo ? `<img src="${w.photo}" alt="" loading="lazy">` : "🍾"}</div>
        <div class="card-body">
          <div class="card-title">${esc(wineTitle(w))}</div>
          <div class="card-sub">${esc(sub)}</div>
          <div class="card-meta">
            ${w.aiStatus === "bezig" ? `<span class="badge busy">Bezig met opzoeken…</span>`
              : w.aiStatus === "fout" ? `<span class="badge over">Opzoeken mislukt</span>`
              : `<span class="badge ${st.key}">${esc(st.label)}</span>`}
            ${w.myRating ? stars(w.myRating) : ""}
          </div>
        </div>
        <div class="qty${w.quantity ? "" : " zero"}">${w.quantity ?? 0}×</div>
      </a>
    </li>`;
}

// ---------- Nieuwe wijn ----------
function renderNew() {
  app.innerHTML = `
    <header class="topbar">
      <a class="icon-btn" href="#/" aria-label="Terug">←</a>
      <h1>Wijn toevoegen</h1><span></span>
    </header>
    <main class="page">
      <div class="add-options">
        <label class="big-btn primary">
          📷 Maak foto van etiket
          <input type="file" accept="image/*" capture="environment" id="camera" hidden>
        </label>
        <label class="big-btn">
          🖼️ Kies foto uit galerij
          <input type="file" accept="image/*" id="gallery" hidden>
        </label>
      </div>
      <label class="field">
        <span>Extra info (optioneel) — bv. jaargang als die niet op de foto staat, of typ de wijn als je geen foto hebt</span>
        <textarea id="hint" rows="2" placeholder="bv. Château Musar 2016"></textarea>
      </label>
      <div class="row">
        <label class="field"><span>Aantal flessen</span><input type="number" id="qty" min="0" value="1" inputmode="numeric"></label>
        <label class="field"><span>Locatie in kelder</span><input id="loc" placeholder="bv. rek B, plank 2"></label>
      </div>
      <button class="big-btn" id="textOnly">🔎 Zoek op zonder foto</button>
      <button class="link-btn" id="manual">Of voer handmatig in (zonder opzoeken)</button>
      <p class="hint">De foto wordt naar Claude gestuurd, die het etiket leest en online het drinkvenster, druiven, spijscombinaties en meer opzoekt. Dit duurt meestal 20–60 seconden; je kunt intussen verder.</p>
    </main>`;

  const extras = () => ({
    hint: app.querySelector("#hint").value.trim(),
    quantity: Math.max(0, parseInt(app.querySelector("#qty").value, 10) || 0),
    location: app.querySelector("#loc").value.trim(),
  });

  const onFile = async (e) => {
    const file = e.target.files?.[0];
    if (!file) return;
    try {
      const photo = await resizeImage(file, PHOTO_OPTS);
      await createAndLookup({ photo, ...extras() });
    } catch (err) {
      toast("Kon de foto niet verwerken: " + err.message);
    }
  };
  app.querySelector("#camera").onchange = onFile;
  app.querySelector("#gallery").onchange = onFile;
  app.querySelector("#textOnly").onclick = () => {
    const ex = extras();
    if (!ex.hint) return toast("Typ eerst welke wijn het is.");
    createAndLookup(ex);
  };
  app.querySelector("#manual").onclick = async () => {
    const ex = extras();
    const wine = newWine({ name: ex.hint || null, quantity: ex.quantity, location: ex.location });
    await putWine(wine);
    go(`#/wijn/${wine.id}/bewerk`);
  };
}

function newWine(fields = {}) {
  return {
    id: uid(), createdAt: Date.now(), quantity: 1, grapes: [], foodPairing: [], sources: [],
    myRating: 0, myNotes: "", aiStatus: "geen", ...fields,
  };
}

async function createAndLookup({ photo, hint, quantity, location: loc }) {
  if (!IN_CLAUDE && !settings.apiKey) {
    toast("Stel eerst je API-sleutel in.");
    go("#/instellingen");
    return;
  }
  const wine = newWine({ photo, hint, quantity, location: loc, name: hint || null });
  await putWine(wine);
  go(`#/wijn/${wine.id}`);
  runLookup(wine.id);
}

/** Zoekt informatie op en voegt die samen met wat de gebruiker zelf al invulde. */
async function runLookup(id) {
  let wine = await getWine(id);
  await putWine({ ...wine, aiStatus: "bezig", aiError: null });
  refreshIfShowing(id);
  try {
    const info = await lookupWine({
      apiKey: settings.apiKey, model: settings.model, photo: wine.photo, hint: wine.hint,
    });
    wine = await getWine(id);
    if (!wine) return; // intussen verwijderd
    const { recognized, ...data } = info;
    const merged = { ...wine };
    for (const [k, v] of Object.entries(data)) {
      if (v !== null && v !== undefined && !(Array.isArray(v) && !v.length)) merged[k] = v;
    }
    merged.aiStatus = recognized === false ? "fout" : "klaar";
    merged.aiError = recognized === false ? "Het etiket kon niet goed worden herkend. Vul aan of probeer een scherpere foto." : null;
    merged.lookedUpAt = Date.now();
    await putWine(merged);
    toast(recognized === false ? "Wijn niet goed herkend" : `${wineTitle(merged)} is opgezocht ✓`);
  } catch (err) {
    wine = await getWine(id);
    if (wine) await putWine({ ...wine, aiStatus: "fout", aiError: err.message });
    toast("Opzoeken mislukt: " + err.message);
  }
  refreshIfShowing(id);
}

function refreshIfShowing(id) {
  if (onRoute(`wijn/${id}`) || onRoute("")) render();
}

// ---------- Detail ----------
async function renderDetail(id) {
  const w = await getWine(id);
  if (!onRoute(`wijn/${id}`)) return;
  if (!w) return go("");
  const st = drinkStatus(w);
  const row = (label, value) => value ? `<div class="info-row"><dt>${label}</dt><dd>${esc(value)}</dd></div>` : "";

  app.innerHTML = `
    <header class="topbar">
      <a class="icon-btn" href="#/" aria-label="Terug">←</a>
      <h1 class="ellipsis">${esc(wineTitle(w))}</h1>
      <a class="icon-btn" href="#/wijn/${w.id}/bewerk" aria-label="Bewerken">✏️</a>
    </header>
    <main class="page detail">
      ${w.photo ? `<img class="hero" src="${w.photo}" alt="Foto van ${esc(wineTitle(w))}">` : ""}
      ${w.aiStatus === "bezig" ? `<div class="notice busy"><span class="spinner"></span> Claude herkent het etiket en zoekt informatie op…</div>` : ""}
      ${w.aiStatus === "fout" ? `<div class="notice error">${esc(w.aiError || "Opzoeken mislukt.")}</div>` : ""}

      <h2>${esc(wineTitle(w))}</h2>
      <p class="sub">${esc([w.vintage ?? (w.aiStatus === "klaar" ? "NV" : ""), w.type, w.appellation || w.region, w.country].filter(Boolean).join(" · "))}</p>

      <section class="panel">
        <div class="panel-head">
          <span class="badge ${st.key}">${esc(st.label)}</span>
        </div>
        ${windowBar(w)}
        ${w.drinkAdvice ? `<p>${esc(w.drinkAdvice)}</p>` : ""}
      </section>

      <section class="panel stock">
        <div>
          <div class="label">In kelder</div>
          <div class="stepper">
            <button data-qty="-1" aria-label="Fles minder">−</button>
            <strong>${w.quantity ?? 0}</strong>
            <button data-qty="1" aria-label="Fles erbij">+</button>
          </div>
        </div>
        <button class="btn" id="drink" ${w.quantity ? "" : "disabled"}>🥂 Fles gedronken</button>
      </section>

      <section class="panel">
        <div class="label">Mijn beoordeling</div>
        ${stars(w.myRating || 0, true)}
        <textarea id="notes" rows="3" placeholder="Mijn proefnotities…">${esc(w.myNotes)}</textarea>
        ${w.consumed?.length ? `<p class="hint">Gedronken: ${w.consumed.map((d) => new Date(d).toLocaleDateString("nl-NL")).join(", ")}</p>` : ""}
      </section>

      ${w.description ? `<section class="panel"><p>${esc(w.description)}</p></section>` : ""}

      <section class="panel">
        <dl class="info">
          ${row("Druiven", w.grapes?.join(", "))}
          ${row("Alcohol", w.alcohol ? `${w.alcohol}%` : "")}
          ${row("Smaak", w.tastingNotes)}
          ${row("Past bij", w.foodPairing?.join(", "))}
          ${row("Serveren", w.servingTemp)}
          ${row("Decanteren", w.decant)}
          ${row("Critici", w.criticScore)}
          ${row("Marktprijs", w.avgPrice)}
          ${row("Betaald", w.purchasePrice ? `€ ${w.purchasePrice}` : "")}
          ${row("Gekocht", w.purchaseDate ? new Date(w.purchaseDate).toLocaleDateString("nl-NL") : "")}
          ${row("Waar gekocht", w.purchasePlace)}
          ${row("Locatie", w.location)}
        </dl>
      </section>

      ${w.sources?.length ? `
        <section class="panel">
          <div class="label">Bronnen</div>
          <ul class="sources">${w.sources.map((s) => `<li><a href="${esc(safeUrl(s.url))}" target="_blank" rel="noopener noreferrer">${esc(s.title || s.url)}</a></li>`).join("")}</ul>
        </section>` : ""}

      <div class="actions">
        <button class="btn" id="relookup" ${w.aiStatus === "bezig" ? "disabled" : ""}>🔄 Opnieuw opzoeken</button>
        <label class="btn">📷 Andere foto<input type="file" accept="image/*" id="newPhoto" hidden></label>
        <button class="btn danger" id="delete">🗑️ Verwijderen</button>
      </div>
    </main>`;

  const save = async (patch) => {
    const fresh = await getWine(id);
    await putWine({ ...fresh, ...patch });
  };

  app.querySelectorAll("[data-qty]").forEach((b) => b.onclick = async () => {
    const fresh = await getWine(id);
    await save({ quantity: Math.max(0, (fresh.quantity ?? 0) + Number(b.dataset.qty)) });
    renderDetail(id);
  });
  app.querySelector("#drink").onclick = async () => {
    const fresh = await getWine(id);
    await save({
      quantity: Math.max(0, (fresh.quantity ?? 0) - 1),
      consumed: [...(fresh.consumed || []), Date.now()],
    });
    toast("Proost! 🥂 Vergeet je beoordeling niet.");
    renderDetail(id);
  };
  app.querySelectorAll("[data-rate]").forEach((b) => b.onclick = async () => {
    const n = Number(b.dataset.rate);
    const fresh = await getWine(id);
    // Nogmaals op dezelfde ster tikken geeft een halve ster
    const rating = fresh.myRating === n ? n - 0.5 : n;
    await save({ myRating: rating });
    renderDetail(id);
  });
  app.querySelector("#notes").onchange = (e) => save({ myNotes: e.target.value });
  app.querySelector("#relookup").onclick = () => runLookup(id);
  app.querySelector("#newPhoto").onchange = async (e) => {
    const file = e.target.files?.[0];
    if (!file) return;
    await save({ photo: await resizeImage(file, PHOTO_OPTS) });
    if (await ask("Foto opgeslagen. Wil je de wijn opnieuw laten opzoeken met deze foto?", "Opnieuw opzoeken", "Nee")) runLookup(id);
    else renderDetail(id);
  };
  app.querySelector("#delete").onclick = async () => {
    if (!(await ask(`"${wineTitle(w)}" verwijderen?`, "Verwijderen"))) return;
    await deleteWine(id);
    go("#/");
  };
}

function safeUrl(url) {
  return /^https?:\/\//i.test(url || "") ? url : "#";
}

/** Tijdlijn van het drinkvenster met hoogtepunt en het huidige jaar. */
function windowBar(w) {
  const from = w.drinkFrom, until = w.drinkUntil;
  if (!from || !until || until < from) return "";
  const y = YEAR();
  const start = Math.min(from, w.vintage || from, y) - 1;
  const end = Math.max(until, y) + 1;
  const pct = (v) => ((v - start) / (end - start)) * 100;
  const peak = w.peakFrom && w.peakUntil
    ? `<div class="wb-peak" style="left:${pct(w.peakFrom)}%;width:${pct(w.peakUntil + 1) - pct(w.peakFrom)}%"></div>` : "";
  return `
    <div class="window-bar" aria-label="Drinkvenster ${from} tot ${until}">
      <div class="wb-track">
        <div class="wb-window" style="left:${pct(from)}%;width:${pct(until + 1) - pct(from)}%"></div>
        ${peak}
        <div class="wb-now" style="left:${pct(y + 0.5)}%"><span>${y}</span></div>
      </div>
      <div class="wb-labels"><span>${from}</span>${w.peakFrom ? `<span>hoogtepunt ${w.peakFrom}–${w.peakUntil}</span>` : ""}<span>${until}</span></div>
    </div>`;
}

// ---------- Bewerken ----------
async function renderEdit(id) {
  const w = await getWine(id);
  if (!onRoute(`wijn/${id}/bewerk`)) return;
  if (!w) return go("");
  const f = (key, label, type = "text", extra = "") => `
    <label class="field"><span>${label}</span>
      <input name="${key}" type="${type}" value="${esc(Array.isArray(w[key]) ? w[key].join(", ") : w[key])}" ${extra}>
    </label>`;
  const types = ["", "rood", "wit", "rosé", "mousserend", "dessert", "versterkt", "oranje"];

  app.innerHTML = `
    <header class="topbar">
      <a class="icon-btn" href="#/wijn/${id}" aria-label="Annuleren">✕</a>
      <h1>Bewerken</h1><span></span>
    </header>
    <main class="page">
      <form id="form" class="form">
        ${f("producer", "Producent")}
        ${f("name", "Naam / cuvée")}
        <div class="row">
          ${f("vintage", "Jaargang", "number", 'inputmode="numeric"')}
          <label class="field"><span>Type</span><select name="type">${types.map((t) => `<option ${w.type === t ? "selected" : ""} value="${t}">${t || "—"}</option>`).join("")}</select></label>
        </div>
        <div class="row">${f("country", "Land")}${f("region", "Streek")}</div>
        ${f("appellation", "Appellatie")}
        ${f("grapes", "Druiven (komma-gescheiden)")}
        <fieldset><legend>Drinkvenster</legend>
          <div class="row">${f("drinkFrom", "Vanaf", "number")}${f("drinkUntil", "Tot en met", "number")}</div>
          <div class="row">${f("peakFrom", "Hoogtepunt vanaf", "number")}${f("peakUntil", "Hoogtepunt tot", "number")}</div>
        </fieldset>
        <fieldset><legend>Mijn kelder</legend>
          <div class="row">${f("quantity", "Aantal flessen", "number", 'min="0"')}${f("location", "Locatie")}</div>
          <div class="row">${f("purchasePrice", "Prijs per fles (€)", "number", 'step="0.01"')}${f("purchaseDate", "Aankoopdatum", "date")}</div>
          ${f("purchasePlace", "Gekocht bij")}
        </fieldset>
        <details><summary>Meer details</summary>
          ${f("alcohol", "Alcohol %", "number", 'step="0.1"')}
          ${f("tastingNotes", "Smaaknotities")}
          ${f("foodPairing", "Past bij (komma-gescheiden)")}
          ${f("servingTemp", "Serveertemperatuur")}
          ${f("decant", "Decanteren")}
          ${f("criticScore", "Critici-scores")}
          ${f("avgPrice", "Marktprijs")}
        </details>
        <button class="big-btn primary" type="submit">Opslaan</button>
      </form>
    </main>`;

  const numbers = ["vintage", "drinkFrom", "drinkUntil", "peakFrom", "peakUntil", "quantity", "purchasePrice", "alcohol"];
  const lists = ["grapes", "foodPairing"];
  app.querySelector("#form").onsubmit = async (e) => {
    e.preventDefault();
    const data = Object.fromEntries(new FormData(e.target));
    for (const k of numbers) data[k] = data[k] === "" ? null : Number(data[k]);
    for (const k of lists) data[k] = data[k].split(",").map((s) => s.trim()).filter(Boolean);
    data.quantity ??= 0;
    data.type ||= null;
    const fresh = await getWine(id);
    await putWine({ ...fresh, ...data });
    go(`#/wijn/${id}`);
  };
}

// ---------- Instellingen ----------
function renderSettings() {
  app.innerHTML = `
    <header class="topbar">
      <a class="icon-btn" href="#/" aria-label="Terug">←</a>
      <h1>Instellingen</h1><span></span>
    </header>
    <main class="page">
      ${IN_CLAUDE ? `
      <p>Deze versie draait binnen Claude. Wijnen worden herkend met je eigen Claude-account; je hebt geen API-sleutel nodig.
        Claude zoekt hier niet live op internet maar gebruikt zijn eigen kennis. De losse app (zie README) doet wel live webzoekopdrachten.</p>` : `
      <form id="settings" class="form">
        <label class="field"><span>Anthropic API-sleutel</span>
          <input name="apiKey" type="password" autocomplete="off" placeholder="sk-ant-…" value="${esc(settings.apiKey)}">
        </label>
        <p class="hint">Maak een sleutel aan op <a href="https://console.anthropic.com/settings/keys" target="_blank" rel="noopener">console.anthropic.com</a>.
          De sleutel wordt alleen in deze browser bewaard en rechtstreeks naar Anthropic gestuurd. Gebruik deze app daarom alleen op je eigen toestel.</p>
        <label class="field"><span>Model</span>
          <select name="model">${MODELS.map((m) => `<option value="${m.id}" ${settings.model === m.id ? "selected" : ""}>${m.label}</option>`).join("")}</select>
        </label>
        <button class="big-btn primary" type="submit">Opslaan</button>
      </form>`}

      <h2>Back-up</h2>
      <p class="hint">${IN_CLAUDE ? "Je wijnen worden bewaard bij dit artifact in Claude." : "Je wijnen staan alleen op dit toestel. Maak regelmatig een back-up."}</p>
      <div class="actions">
        <button class="btn" id="export">⬇️ Exporteren</button>
        <label class="btn">⬆️ Importeren<input type="file" accept="application/json" id="import" hidden></label>
        <button class="btn danger" id="wipe">Alles wissen</button>
      </div>
    </main>`;

  const form = app.querySelector("#settings");
  if (form) form.onsubmit = (e) => {
    e.preventDefault();
    const data = Object.fromEntries(new FormData(e.target));
    settings.apiKey = data.apiKey.trim();
    settings.model = data.model;
    toast("Instellingen opgeslagen");
    go("#/");
  };
  app.querySelector("#export").onclick = async () => {
    const wines = await getAllWines();
    const json = JSON.stringify({ app: "wijnkelder", version: 1, wines }, null, 1);
    const filename = `wijnkelder-${new Date().toISOString().slice(0, 10)}.json`;
    if (IN_CLAUDE) {
      const downloads = await claudeDownloads;
      if (!downloads) return toast("Exporteren is hier niet beschikbaar.");
      try { await downloads.save({ filename, data: json }); } catch (err) {
        if (err?.code !== "declined") toast("Exporteren is niet gelukt.");
      }
      return;
    }
    const blob = new Blob([json], { type: "application/json" });
    const a = document.createElement("a");
    a.href = URL.createObjectURL(blob);
    a.download = filename;
    a.click();
    setTimeout(() => URL.revokeObjectURL(a.href), 1000);
  };
  app.querySelector("#import").onchange = async (e) => {
    const file = e.target.files?.[0];
    if (!file) return;
    try {
      const { wines } = JSON.parse(await file.text());
      if (!Array.isArray(wines)) throw new Error("geen wijnen gevonden");
      for (const w of wines) if (w?.id) await putWine({ ...w, aiStatus: w.aiStatus === "bezig" ? "fout" : w.aiStatus });
      toast(`${wines.length} wijnen geïmporteerd`);
    } catch (err) {
      toast("Import mislukt: " + err.message);
    }
  };
  app.querySelector("#wipe").onclick = async () => {
    if (!(await ask("Weet je zeker dat je ALLE wijnen wilt wissen? Dit kan niet ongedaan worden.", "Alles wissen"))) return;
    await clearWines();
    toast("Alle wijnen gewist");
  };
}

// Opzoekingen die werden onderbroken (app gesloten) markeren als mislukt, zodat je ze opnieuw kunt starten
getAllWines().then((wines) =>
  wines.filter((w) => w.aiStatus === "bezig").forEach((w) =>
    putWine({ ...w, aiStatus: "fout", aiError: "Opzoeken werd onderbroken. Tik op ‘Opnieuw opzoeken’." })));

if ("serviceWorker" in navigator && import.meta.env.PROD && !IN_CLAUDE) {
  navigator.serviceWorker.register("./sw.js").catch(() => {});
}
