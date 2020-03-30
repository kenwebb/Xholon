// Browser Storage
// run this in the browser console (Firefox or Chrome: Dev Tools)

clear();

var cbsLog = function() {
console.log(CacheStorage.length); // 2
console.log(document.cookie); // Cookies  document.cookie.length;
console.log(indexedDB); // IDBFactory {  }
console.log(localStorage.length); // 68
console.log(sessionStorage.length); // 0
}

var cbsClearCacheStorage = function() {
  // TODO ???
}

var cbsClearLocalStorage = function() {
  localStorage.clear();
}

var cbsClearAll = function() {
  cbsClearCacheStorage();
  cbsClearLocalStorage();
}

