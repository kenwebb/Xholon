/**
 * HistoryManager
 * Ken Webb
 * January 31, 2020
 * Create a history object inside an Avatar, or possibly within other Xholon node types.
 * 
 * http://127.0.0.1:8888/Xholon.html?app=f77e463b1fd97ac23197e5cd48ea5468&src=gist&gui=clsc&jslib=BigraphParser,HistoryManager
 * 
 * example usage:
var ava = xh.avatar();
xh.HistoryManager(ava);
console.log(ava["history"]);

 * then
ava.action("go HISTORY;");
 */

if (typeof xh == "undefined") {
  xh = {};
}

xh.HistoryManager = function(node) {
  var history = {};
  history.stack = [];
  
  // pushHistory
  history.pushHistory = function(node) {
    //console.log("PUSH: " + (node ? node.name() : null));
    if (node != null) {
      this.stack.push(node);
    }
  }
  
  // popHistory
  history.popHistory = function() {
    if (this.stack.length >= 2) {
      this.stack.pop(); // pop the current node
      var pnode = this.stack.pop(); // get the previous node, which the Avatar will moveto, which will cause the Avatar to re-push this pnode
      //console.log("POP: " + pnode.name());
      return pnode; // return the previous node
    }
  }
  
  if (node.hasClass("Avatar")) {
    history.pushHistory(node.obj()); // at start, push Avatar's current contextNode
  }
  else {
    history.pushHistory(node.parent()); // this is not an Avatar
  }
  node["history"] = history;
  //# sourceURL=HistoryManager.js
}

