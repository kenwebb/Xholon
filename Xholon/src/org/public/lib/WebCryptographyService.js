'use strict';

/**
 * WebCryptographyService
 * Ken Webb
 * August 19, 2018
 * @see XholonWorkbook "Web Cryptography API" 036d7c299b7c96e4838059e06b6d8334
 * @see https://developer.mozilla.org/en-US/docs/Web/API/SubtleCrypto/digest
 * 
 * usage: (this works from Dev Tools)
var wcs = xh.service("WebCryptographyService");
var obj = wcs.obj();
if (obj) {
  obj.hash("hello world"); // b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9
}
 */

if (typeof xh == "undefined") {
  xh = {};
}

xh.WebCryptographyService = {};

xh.WebCryptographyService.hash = function(str, caller) {
  if (typeof crypto == "undefined") {
    console.log("WebCryptographyService is NOT available. crypto is undefined");
    return;
  }
  if (typeof crypto.subtle == "undefined") {
    console.log("WebCryptographyService is NOT available. crypto.subtle is undefined");
    return;
  }
  //console.log("WebCryptographyService is available");
  
  var sha256 = function(str) {
    // We transform the string into an arraybuffer.
    var buffer = new TextEncoder("utf-8").encode(str);
    return crypto.subtle.digest("SHA-256", buffer).then(function (hash) {
      return hex(hash);
    });
  }
  
  var hex = function(buffer) {
    var hexCodes = [];
    var view = new DataView(buffer);
    for (var i = 0; i < view.byteLength; i += 4) {
      // Using getUint32 reduces the number of iterations needed (we process 4 bytes each time)
      var value = view.getUint32(i);
      // toString(16) will give the hex representation of the number without padding
      var stringValue = value.toString(16);
      // We use concatenation and slice for padding
      var padding = '00000000';
      var paddedValue = (padding + stringValue).slice(-padding.length);
      hexCodes.push(paddedValue);
    }
    // Join all the hex strings into one
    return hexCodes.join("");
  }
  
  var root = xh.root();
  sha256(str).then(function(digest) {
    if (typeof caller === "undefined") {
      root.println(digest);
    }
    else {
      // send a Xholon async message, containing both the original string and the computed hash/digest
      caller.msg(101, [str, digest], root);
    }
  });
  
}

