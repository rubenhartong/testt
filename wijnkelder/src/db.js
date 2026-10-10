// Opslag van wijnen (inclusief foto's als data-URL).
// Losse app: IndexedDB op het toestel. Binnen Claude: de database van het artifact.
import { claudeDb } from "./env.js";

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

const local = {
  getAll: () => tx("readonly", (s) => s.getAll()),
  get: (id) => tx("readonly", (s) => s.get(id)),
  put: (wine) => tx("readwrite", (s) => s.put(wine)),
  delete: (id) => tx("readwrite", (s) => s.delete(id)),
  clear: () => tx("readwrite", (s) => s.clear()),
};

function remote(db) {
  const wines = db.collection("wines");
  return {
    getAll: async () => (await wines.get()).docs.map((d) => ({ ...d.data(), id: d.id })),
    get: async (id) => {
      const snap = await wines.doc(id).get();
      return snap.exists ? { ...snap.data(), id } : undefined;
    },
    put: (wine) => wines.doc(wine.id).set(JSON.parse(JSON.stringify(wine))),
    delete: (id) => wines.doc(id).delete(),
    clear: async () => {
      for (const d of (await wines.get()).docs) await wines.doc(d.id).delete();
    },
  };
}

const store = claudeDb.then((db) => (db ? remote(db) : local));

export const getAllWines = async () => (await store).getAll();
export const getWine = async (id) => (await store).get(id);
export const putWine = async (wine) => (await store).put({ ...wine, updatedAt: Date.now() });
export const deleteWine = async (id) => (await store).delete(id);
export const clearWines = async () => (await store).clear();
