# 🍷 Wijnkelder

Een persoonlijk alternatief voor Vivino: sla al je wijnen op met een foto, en laat Claude het etiket
herkennen en online opzoeken wanneer je de wijn het beste kunt drinken.

## Wat kan het?

- **Foto van het etiket** (camera of galerij) → Claude leest het etiket en zoekt via webzoekopdrachten:
  drinkvenster en hoogtepunt, druiven, alcohol, smaaknotities, spijscombinaties, serveertemperatuur,
  decanteeradvies, critici-scores, marktprijs en de gebruikte bronnen.
- **Zonder foto opzoeken**: typ gewoon de naam van de wijn.
- **Drinkstatus per fles**: *Bewaren*, *Nu drinkbaar*, *Op hoogtepunt*, *Snel drinken*, *Over hoogtepunt*,
  met een tijdlijn van het drinkvenster.
- **Kelderbeheer**: aantal flessen, locatie, aankoopprijs/-datum/-plek, “fles gedronken” (met datum).
- **Eigen beoordeling** (halve sterren: tik twee keer op dezelfde ster) en proefnotities.
- Zoeken, filteren (nu drinken / bewaren / over hoogtepunt / op) en sorteren.
- **Export/import** als JSON-back-up.
- Installeerbaar op je telefoon (PWA: “Zet op beginscherm”).

Alle gegevens staan lokaal in je browser (IndexedDB). Er is geen server nodig.

## Starten

```bash
cd wijnkelder
npm install
npm run dev      # ontwikkelserver
npm run build    # statische build in dist/
```

De map `dist/` kun je op elke statische host zetten (GitHub Pages, Netlify, Cloudflare Pages, …).
Voor de camera en de installatie als app is **HTTPS** nodig (localhost werkt ook).

## API-sleutel

Open in de app **Instellingen** en vul een Anthropic API-sleutel in (aan te maken op
<https://console.anthropic.com/settings/keys>). De sleutel blijft in je eigen browser en wordt rechtstreeks
naar de Anthropic API gestuurd. Deel de app daarom niet met anderen met jouw sleutel erin.

Standaard wordt **Claude Opus 5.5** gebruikt; in Instellingen kun je kiezen voor Sonnet 5.5 of Haiku 5.5
als je het sneller of goedkoper wilt. Elke opzoeking gebruikt een paar webzoekopdrachten.

## Opbouw

| Bestand | Inhoud |
|---|---|
| `src/main.js` | Schermen (lijst, toevoegen, detail, bewerken, instellingen) en routing |
| `src/ai.js` | Aanroep van Claude: foto + webzoeken → gestructureerde JSON |
| `src/db.js` | Opslag in IndexedDB |
| `src/image.js` | Foto's verkleinen voor opslag en upload |
| `public/` | Manifest, icoon en service worker (offline gebruik) |
