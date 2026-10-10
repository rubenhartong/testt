import { dataUrlToBase64, dataUrlToBlob } from "./image.js";
import { IN_CLAUDE, claudeSample } from "./env.js";

export const MODELS = [
  { id: "claude-opus-5-5", label: "Claude Opus 5.5 (beste kwaliteit)" },
  { id: "claude-sonnet-5-5", label: "Claude Sonnet 5.5 (sneller, goedkoper)" },
  { id: "claude-haiku-5-5", label: "Claude Haiku 5.5 (snelst, goedkoopst)" },
];
export const DEFAULT_MODEL = MODELS[0].id;

const str = { type: ["string", "null"] };
const int = { type: ["integer", "null"] };
const num = { type: ["number", "null"] };

const WINE_SCHEMA = {
  type: "object",
  additionalProperties: false,
  required: [
    "recognized", "name", "producer", "vintage", "type", "country", "region", "appellation",
    "grapes", "alcohol", "drinkFrom", "drinkUntil", "peakFrom", "peakUntil", "drinkAdvice",
    "tastingNotes", "foodPairing", "servingTemp", "decant", "criticScore", "avgPrice",
    "description", "sources",
  ],
  properties: {
    recognized: { type: "boolean", description: "false als het etiket niet te lezen is" },
    name: str,
    producer: str,
    vintage: int,
    type: {
      type: ["string", "null"],
      enum: ["rood", "wit", "rosé", "mousserend", "dessert", "versterkt", "oranje", null],
    },
    country: str,
    region: str,
    appellation: str,
    grapes: { type: "array", items: { type: "string" } },
    alcohol: num,
    drinkFrom: int,
    drinkUntil: int,
    peakFrom: int,
    peakUntil: int,
    drinkAdvice: str,
    tastingNotes: str,
    foodPairing: { type: "array", items: { type: "string" } },
    servingTemp: str,
    decant: str,
    criticScore: str,
    avgPrice: str,
    description: str,
    sources: {
      type: "array",
      items: {
        type: "object",
        additionalProperties: false,
        required: ["title", "url"],
        properties: { title: { type: "string" }, url: { type: "string" } },
      },
    },
  },
};

const SYSTEM = `Je bent een sommelier die een persoonlijke wijnkelder-app ondersteunt.
Je krijgt een foto van een wijnfles/etiket en/of een omschrijving. Herken de wijn (producent, naam/cuvée, jaargang)
en zoek online betrouwbare informatie op: drinkvenster en hoogtepunt (bij voorkeur van critici, de producent of
gespecialiseerde sites), druivenrassen, alcohol, smaaknotities, spijscombinaties, serveertemperatuur, decanteeradvies,
critici-scores en een indicatie van de huidige prijs in euro.

Regels:
- Schrijf alle tekst in het Nederlands.
- Jaartallen (drinkFrom/drinkUntil/peakFrom/peakUntil) zijn kalenderjaren. Vind je geen bron voor het drinkvenster,
  maak dan een onderbouwde schatting op basis van stijl, streek en jaargang en zeg dat in drinkAdvice.
- Gebruik null voor wat je niet weet; verzin geen feiten of scores.
- Geef in sources de pagina's die je echt hebt gebruikt.
- Is er geen jaargang zichtbaar (bv. non-vintage champagne), dan vintage = null.
- Het huidige jaar is ${new Date().getFullYear()}.`;

async function client(apiKey) {
  // De artifact-build bevat de SDK niet; daar loopt alles via Claude zelf.
  if (import.meta.env.MODE === "artifact") throw new Error("Niet beschikbaar in deze versie.");
  const { default: Anthropic } = await import("@anthropic-ai/sdk");
  // De sleutel staat alleen in de browser van de gebruiker zelf (persoonlijke app).
  return new Anthropic({ apiKey, dangerouslyAllowBrowser: true });
}

const SAMPLE_ERRORS = {
  not_granted: "Je hebt deze app geen toegang tot Claude gegeven.",
  sampling_disabled: "Claude is niet beschikbaar voor dit account.",
  rate_limited: "Even te veel aanvragen. Probeer het over een paar minuten opnieuw.",
  image_rejected: "Deze foto kan niet worden gebruikt. Probeer een andere.",
  images_unavailable: "Foto's versturen kan hier niet. Typ de naam van de wijn.",
  refused: "Claude kon deze aanvraag niet verwerken. Probeer een andere foto of omschrijving.",
  invalid_json: "Kon het antwoord van Claude niet lezen. Probeer het opnieuw.",
  session_expired: "Log opnieuw in bij Claude.",
};

/** Binnen Claude: herkenning met het eigen Claude-account van de gebruiker (geen live webzoekopdrachten). */
async function lookupViaClaude({ photo, hint }) {
  const sample = await claudeSample;
  if (!sample) throw new Error("Claude is hier niet beschikbaar.");
  const prompt = `${SYSTEM.replace("en zoek online betrouwbare informatie op", "en geef op basis van je kennis")}
- Je hebt geen internettoegang; laat sources een lege lijst.

${photo ? "De afbeelding is een foto van de fles of het etiket." : "Er is geen foto."}
${hint ? `Extra informatie van de gebruiker: ${hint}` : ""}

Antwoord met alleen één JSON-object met precies deze velden (null als onbekend):
${JSON.stringify(Object.fromEntries(Object.entries(WINE_SCHEMA.properties).map(([k, v]) => [k, v.enum ? v.enum.filter(Boolean).join(" | ") : [].concat(v.type).join(" | ")])))}
Toelichting: drinkFrom/drinkUntil/peakFrom/peakUntil zijn jaartallen; grapes en foodPairing zijn lijsten met tekst.`;
  try {
    return await sample.json(prompt, photo ? { images: [dataUrlToBlob(photo)] } : {});
  } catch (e) {
    throw new Error(SAMPLE_ERRORS[e?.code] || "Er ging iets mis bij Claude. Probeer het opnieuw.");
  }
}

/**
 * Herkent een wijn op basis van foto en/of tekst en zoekt aanvullende info online op.
 * @returns {Promise<object>} object volgens WINE_SCHEMA
 */
export async function lookupWine({ apiKey, model = DEFAULT_MODEL, photo, hint }) {
  if (IN_CLAUDE) return lookupViaClaude({ photo, hint });
  if (!apiKey) throw new Error("Stel eerst je Anthropic API-sleutel in bij Instellingen.");

  const content = [];
  if (photo) {
    content.push({
      type: "image",
      source: { type: "base64", media_type: "image/jpeg", data: dataUrlToBase64(photo) },
    });
  }
  content.push({
    type: "text",
    text: hint
      ? `Extra informatie van de gebruiker: ${hint}\nZoek deze wijn op.`
      : "Herken deze wijn en zoek de informatie op.",
  });

  const messages = [{ role: "user", content }];
  const anthropic = await client(apiKey);

  // Webzoekopdrachten kunnen de beurt pauzeren (pause_turn); dan hervatten we.
  for (let i = 0; i < 5; i++) {
    const params = {
      model,
      max_tokens: 16000,
      system: SYSTEM,
      messages,
      tools: [{ type: "web_search_20260209", name: "web_search", max_uses: 6 }],
      output_config: {
        effort: "medium",
        format: { type: "json_schema", schema: WINE_SCHEMA },
      },
    };
    // Bij een weigering door veiligheidsfilters laat de API automatisch een ander model het proberen.
    const response =
      model === "claude-haiku-5-5"
        ? await anthropic.messages.create(params)
        : await anthropic.beta.messages.create({
            ...params,
            betas: ["server-side-fallback-2026-07-01"],
            fallbacks: "default",
          });

    if (response.stop_reason === "pause_turn") {
      messages.push({ role: "assistant", content: response.content });
      continue;
    }
    if (response.stop_reason === "refusal") {
      throw new Error("Het model weigerde deze aanvraag. Probeer een andere foto of omschrijving.");
    }
    if (response.stop_reason === "max_tokens") {
      throw new Error("Het antwoord werd afgekapt. Probeer het opnieuw.");
    }
    const text = response.content
      .filter((b) => b.type === "text")
      .map((b) => b.text)
      .join("");
    return parseJson(text);
  }
  throw new Error("Het opzoeken duurde te lang. Probeer het opnieuw.");
}

function parseJson(text) {
  try {
    return JSON.parse(text);
  } catch {
    const match = text.match(/\{[\s\S]*\}/);
    if (match) return JSON.parse(match[0]);
    throw new Error("Kon het antwoord van Claude niet lezen.");
  }
}
