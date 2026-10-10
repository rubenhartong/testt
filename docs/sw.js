// Pagina: eerst netwerk (max 3 s, voor slecht bereik), anders opgeslagen versie.
// Overige bestanden: eerst cache. Na één keer openen werkt alles zonder internet.
const CACHE = 'hardbass-v8';
const ASSETS = ['./', 'index.html', 'manifest.webmanifest', 'icon.svg', 'icon-192.png', 'icon-512.png'];

self.addEventListener('install', e => {
  e.waitUntil(caches.open(CACHE).then(c => c.addAll(ASSETS.map(u => new Request(u, { cache: 'reload' })))).then(() => self.skipWaiting()));
});
self.addEventListener('activate', e => {
  e.waitUntil(caches.keys()
    .then(keys => Promise.all(keys.filter(k => k !== CACHE).map(k => caches.delete(k))))
    .then(() => self.clients.claim()));
});

function fromNetwork(req, ms) {
  return new Promise((resolve, reject) => {
    const timer = setTimeout(() => reject(new Error('timeout')), ms);
    fetch(req, { cache: 'no-cache' }).then(res => {
      clearTimeout(timer);
      if (res.ok) { const copy = res.clone(); caches.open(CACHE).then(c => c.put(req, copy)); }
      resolve(res);
    }, err => { clearTimeout(timer); reject(err); });
  });
}

self.addEventListener('fetch', e => {
  const req = e.request;
  if (req.method !== 'GET' || new URL(req.url).origin !== location.origin) return;
  if (req.mode === 'navigate') {
    e.respondWith(fromNetwork(req, 3000).catch(() =>
      caches.match(req, { ignoreSearch: true }).then(hit => hit || caches.match('index.html'))));
    return;
  }
  // alleen app-bestanden uit de cache; al het andere (bv. groepssync) gaat gewoon naar het netwerk
  const path = new URL(req.url).pathname.split('/').pop();
  if (!ASSETS.includes(path)) return;
  e.respondWith(caches.match(req, { ignoreSearch: true }).then(hit => hit || fromNetwork(req, 8000)));
});
