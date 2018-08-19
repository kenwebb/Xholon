'use strict';

/**
 * WebCryptographyService
 * Ken Webb
 * August 19, 2018
 * 
 * testing:
var wcs = xh.service("WebCryptographyService");
wcs.name();
wcs.obj();
var fobj = wcs.obj();
fobj(); // WebCryptographyService is available
wcs.obj()(); // WebCryptographyService is available
 */

if (typeof window.xh == "undefined") {
  window.xh = {};
}

xh.WebCryptographyService = function() {
  console.log("WebCryptographyService is available");
}

