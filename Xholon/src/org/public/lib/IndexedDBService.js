'use strict';

/**
 * IndexedDBService
 * Ken Webb
 * August 19, 2018
 * 
 * testing:
var idbs = xh.service("IndexedDBService");
idbs.name();
idbs.obj();
var fobj = idbs.obj();
fobj(); // IndexedDBService is available
idbs.obj()(); // IndexedDBService is available
 */

if (typeof window.xh == "undefined") {
  window.xh = {};
}

xh.IndexedDBService = function() {
  console.log("IndexedDBService is available");
}

