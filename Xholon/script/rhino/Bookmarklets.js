/*
 * This is a collection of bookmarklet-like scripts.
 * Bookmarklets are typically short, all on one line,
 * and use the (function(){})() format.
 * For now, they can only be used in the XholonConsole.
 * Only the XholonConsole knows how to convert "javascript:"
 * or "js:" into the Xholon XML format.
 * Dec 6, 2011
 */
javascript:(function(a,b){println(a+' '+b);})('one','two');
javascript:(function(a,b){println(a+' '+b);})('cnode',contextNodeKey);
js:(function(a,b){var c=a+b;println(a+'+'+b+'='+c);})(13,17);
js:(function(){println(new java.util.Date());})();
js:(function(){println(new Date());})();
js:(function(){println(Math.random());})();