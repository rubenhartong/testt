// Kleine IndexedDB-laag: alle wijnen (inclusief foto's als data-URL) blijven op het toestel.
const DB_NAME = "wijnkelder";
const STORE = "wines";

let dbPromise;

function openDb() {
  if (!dbPromise) {
    dbPromise = new Promise((resolve, reject) => {
      const req = indexedDB.open(DB_NAME, 1);
      req.onupgradeneeded = () => {
        req.result.createObjectStore(STORE, { keyPath: "id" });
      };
      req.onsuccess = () => resolve(req.result);
      req.onerror = () => reject(req.error);
    });
  }
  return dbPromise;
}

async function tx(mode, fn) {
  const db = await openDb();
  return new Promise((resolve, reject) => {
    const t = db.transaction(STORE, mode);
    const result = fn(t.objectStore(STORE));
    t.oncomplete = () => resolve(result?.result ?? result);
    t.onerror = () => reject(t.error);
  });
}

export const getAllWines = () => tx("readonly", (s) => s.getAll());
export const getWine = (id) => tx("readonly", (s) => s.get(id));
export const putWine = (wine) => tx("readwrite", (s) => s.put({ ...wine, updatedAt: Date.now() }));
export const deleteWine = (id) => tx("readwrite", (s) => s.delete(id));
export const clearWines = () => tx("readwrite", (s) => s.clear());
