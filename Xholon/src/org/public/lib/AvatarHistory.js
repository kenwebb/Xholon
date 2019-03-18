'use strict';

/**
 * AvatarHistory
 * A test implementation of a Avatar HistoryManager.
 * 
 * sample usage:
 * 
var ava = xh.avatar();
ava.history = xh.AvatarHistory;

// test push()
ava.action("go 0");

// test pop()
ava.action("go history");

// add to avatarKeyMap
var akm = JSON.parse(xh.avatarKeyMap());
akm.H = "go history";
xh.avatarKeyMap(JSON.stringify(akm));

 * 
 * 
 * TODO:
 * - ava.action("go 0"); pushes 2 nodes onto the stack - both the source contextNode and the destination contextNode
 *   setContextNode() iscalled from line 875 and line 2602
 * - prevent the current contextNode from going into history
     HistoryManager behavior might be able to control this by not allowing pushHistory(currentNode) just after popping a node
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

