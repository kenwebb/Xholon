/*
  bookmarklets.js
  Ken Webb  28 January 2021
  
  https://en.wikipedia.org/wiki/Bookmarklet
  It seems that bookmarklets must be added to the Bookmarks tool in the web browser.
  They can no longer be run directly from the URL line.
*/

// double
javascript:(function(x)(alert(x + x)})(2)
javascript:((x) => alert(x + x))(2) // this works

// Xh root name
javascript:(function(){console.log(xh.root().name());})();
javascript:(() => console.log(xh.root().name()))()

javascript:((x) => alert(x + x))(2) // this works
javascript:((x) => $wnd.xh.root().println(x + x))(5) // this works

