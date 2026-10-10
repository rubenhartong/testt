// Verkleint een foto naar een JPEG data-URL, zodat opslag en API-aanroepen klein blijven.
// maxChars begrenst de lengte van de data-URL (de Claude-opslag staat max. 256 KiB per wijn toe).
export async function resizeImage(file, { maxSize = 1400, quality = 0.85, maxChars = Infinity } = {}) {
  const bitmap = await createImageBitmap(file, { imageOrientation: "from-image" });
  let size = maxSize;
  let url;
  for (let i = 0; i < 6; i++) {
    const scale = Math.min(1, size / Math.max(bitmap.width, bitmap.height));
    const canvas = document.createElement("canvas");
    canvas.width = Math.round(bitmap.width * scale);
    canvas.height = Math.round(bitmap.height * scale);
    canvas.getContext("2d").drawImage(bitmap, 0, 0, canvas.width, canvas.height);
    url = canvas.toDataURL("image/jpeg", quality);
    if (url.length <= maxChars) break;
    size = Math.round(size * 0.75);
  }
  bitmap.close?.();
  return url;
}

export function dataUrlToBase64(dataUrl) {
  return dataUrl.slice(dataUrl.indexOf(",") + 1);
}

export function dataUrlToBlob(dataUrl) {
  const bin = atob(dataUrlToBase64(dataUrl));
  const bytes = new Uint8Array(bin.length);
  for (let i = 0; i < bin.length; i++) bytes[i] = bin.charCodeAt(i);
  return new Blob([bytes], { type: dataUrl.slice(5, dataUrl.indexOf(";")) || "image/jpeg" });
}
