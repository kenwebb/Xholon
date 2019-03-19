'use strict';

/**
 * AvatarHistory
 * Ken Webb
 * March 18, 2019
 * A test implementation of a Avatar HistoryManager.
 * 
 * http://127.0.0.1:8888/Xholon.html?app=dcc6eca037a3c0073ebe55eb8ffeba68&src=gist&gui=none&jslib=AvatarHistory
 * 
// sample usage:
var ava = xh.avatar();
ava.history = xh.AvatarHistory;
//OR just:
xh.avatar().history = xh.AvatarHistory;

// test push()
ava.action("go 0");

// test pop()
ava.action("go history");

// add to avatarKeyMap
var akm = JSON.parse(xh.avatarKeyMap());
akm.H = "go history";
xh.avatarKeyMap(JSON.stringify(akm));
// I have added "H" to Application default avatarKeyMap, so it will be available for all apps

 * 
 */

(function($wnd) {

const DEFAULT_MAX_STACK_SIZE = 10;

if (typeof $wnd.xh == "undefined") {
  $wnd.xh = {};
}

if (typeof $wnd.xh.AvatarHistory == "undefined") {
  $wnd.xh.AvatarHistory = {};
}
else {
  return; // it has already been defined
}

//var ava = $wnd.xh.avatar(); // the system Avatar
var hist = $wnd.xh.AvatarHistory;

// code
//ava.history = {};
hist.stack = [];
hist.doingHistory = false;
hist.maxStackSize = DEFAULT_MAX_STACK_SIZE;
//ava.println(hist.toString());

hist.pushHistory = function(node) {
  if (this.doingHistory) {
    this.doingHistory = false;
    // ignore the node
  }
  else {
    this.stack.push(node);
    if (this.stack.length > this.maxStackSize) {
      this.stack.shift(); // remove node at bottom of stack; the oldest node
    }
  }
  //ava.println(hist.toString());
}

hist.popHistory = function() {
  if (this.stack.length == 0) {
    return null;
  }
  this.doingHistory = true;
  //ava.println(hist.toString());
  return this.stack.pop();
}

hist.toString = function() {
  var len = this.stack.length;
  var str = "stack: [";
  for (var i = 0; i < len; i++) {
    str += this.stack[i].id();
    if (i < len-1) {
      str += ",";
    }
  }
  str += "]";
  str += " doingHistory:" + this.doingHistory;
  return str;
}

})(window); // end (function()

