'use strict';

/**
 * StochasticBehavior
 * Ken Webb
 * February 20, 2019
 * 
 * http://127.0.0.1:8888/wb/editwb.html?app=Stochastic+Tree+Wanderers&src=lstr
 * https://gist.github.com/kenwebb/9afb3d0795256930f524485b2b09f2fd
 * 
 * testing:
$wnd.xh.StochasticBehavior.test("123");
 *
 */

if (typeof xh == "undefined") {
  xh = {};
}

if (typeof xh.StochasticBehavior == "undefined") {
  xh.StochasticBehavior = {};
}

(function($wnd, $doc) {
  //$wnd = window;
  
  $wnd.xh.StochasticBehavior.test = function(arg) {
    console.log("StochasticBehavior testing: " + arg);
  }
  
  // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
  // DisplayDbButton
  $wnd.xh.StochasticBehavior.DisplayDbButton = function DisplayDbButton() {}

  $wnd.xh.StochasticBehavior.DisplayDbButton.prototype.handleNodeSelection = function() {
    $wnd.xh.IndexedDBService.display("AvatarStateDB", "avastate", ["hash", "state", "action", "count"]);
    return "\u0011";
  }

  
  // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
  // QueryDbButton
  const QUERY_ACTION_TYPE = 1; // 1, 2, 3
  
  $wnd.xh.StochasticBehavior.QueryDbButton = function QueryDbButton() {}
  
  $wnd.xh.StochasticBehavior.QueryDbButton.prototype.handleNodeSelection = function() {
    switch (QUERY_ACTION_TYPE) {
    case 1: $wnd.xh.IndexedDBService.query("AvatarStateDB", "avastate", ["hash", "state", "action", "count"], this.cnode.next(), "text"); break;
    case 2: $wnd.xh.IndexedDBService.query("AvatarStateDB", "avastate", ["hash", "state", "action", "count"], this.cnode, "msg", 401); break;
    case 3: $wnd.xh.IndexedDBService.query("AvatarStateDB", "avastate", ["hash", "state", "action", "count"], this.cnode, "call", 401); break;
    default: break;
    }
    return "\u0011";
  }
  
  $wnd.xh.StochasticBehavior.QueryDbButton.prototype.processReceivedMessage = function(msg) { // msg()
    this.cnode.println("received a msg: signal=" + msg.signal + " datalength=" + msg.data.length + " sender=" + msg.sender.name());
  }
  
  $wnd.xh.StochasticBehavior.QueryDbButton.prototype.processReceivedSyncMessage = function(msg) { // call()
    this.cnode.println("received a sync msg: signal=" + msg.signal + " datalength=" + msg.data.length + " sender=" + msg.sender.name());
    return "OK";
  }
  
  $wnd.xh.StochasticBehavior.QueryResultsProcessor = `
// fields/columns in each row of the source data
const FIELD_HASH   = 0;
const FIELD_STATE  = 1;
const FIELD_ACTION = 2;
const FIELD_COUNT  = 3;

const CONTROL_DETERMINISTICALLY = false;

// example of source data
// d4400c39b4df8e76a0380024c8cd2b247e9547fe42e22efdc540cf1a1fd816ce, Ottawa (House Bicycle Dog Avatario), next, 3

// some ideas on what the result data should look like
// 09710d1dfb67f188cceb5ff6d0ae1b9b166c1e6b9c4a76c0c07bb6379dde28aa  idle  4  next  2
// JSON.parse('{"05400c39b4df8e76a0380024c8cd2b247e9547fe42e22efdc540cf1a1fd816ce": {"next": 0.3, "prev": 0.2, "idle":0.5}, "10a2f385b80aa54df3548655cba0fc493a17fa355f4da1e2a45b78ccd54aa1cb": {"next": 0.5, "idle":0.5}}');
// JSON.parse('{"05400c39b4df8e76a0380024c8cd2b247e9547fe42e22efdc540cf1a1fd816ce": {"0.3": "next", "0.5": "prev", "1.0": "idle"}, "10a2f385b80aa54df3548655cba0fc493a17fa355f4da1e2a45b78ccd54aa1cb": {"0.5": "prev", "1.0": "idle"}}');
/*
{
05400c39b4df8e76a0380024c8cd2b247e9547fe42e22efdc540cf1a1fd816ce: {next: 0.3, prev: 0.2, idle:0.5},
10a2f385b80aa54df3548655cba0fc493a17fa355f4da1e2a45b78ccd54aa1cb: {next: 0.5, idle:0.5}
}
*/

var me, qr, processed, ava, behaviorProbs, wcs, wcsobj, newState, beh = {

  postConfigure: function() {
    me = this.cnode; // QueryResultsProcessor node
    qr = me.parent(); // QueryResults node, where the source data lives
    processed = false; // whether or not the source data has been processed
    ava = $wnd.xh.avatar();
    behaviorProbs = null;
    wcs = $wnd.xh.service("WebCryptographyService");
  },
  
  act: function() {
    if (qr.text() && !processed) {
      // process the QueryResults
      //me.println("ready to process query results ... TODO");
      var outobj = this.process(qr.text().trim());
      $wnd.console.log(outobj);
      //me.println(JSON.stringify(outobj));
      behaviorProbs = outobj;
      processed = true;
    }
    if (processed) {
      // use behaviorProbs to drive the Avatar
      newState = "";
      this.hashifySXpres(ava.parent()); // a JSON string
      wcsobj = wcs.obj();
      if (wcsobj) {
        // the async hash function will send an async message that will be received by processReceivedMessage(msg)
        wcsobj.hash(newState, me);
      }
    }
    else {
      // deterministically control the Avatar
      if (CONTROL_DETERMINISTICALLY) {
        ava.action("next");
      }
    }
  },
  
  /**
   * Recursively hashify a Xholon subtree, with output in a Lisp S-expression format.
   * @param node A node in the Xholon hierarchy.
   */
  hashifySXpres: function(node) {
    newState += node.name("R^^^^^");
    if (node.xhc().name() == "Space") {
      newState += (" (Avatar)");
    }
    else if (node.first()) {
      newState += " (";
      var childNode = node.first();
      while (childNode != null) {
        this.hashifySXpres(childNode);
        childNode = childNode.next();
        if (childNode != null) {
          newState += " ";
        }
      }
      newState += ")";
    }
    else if (node["maxClones"] && (node.parent().xhc().name() != "Avatar")) {
      newState += "*" + node["maxClones"];
    }
  },

  processReceivedMessage: function(msg) {
    if (msg.signal == 101) {
      // process a message from the hash function
      var hash = msg.data[1];
      //me.println(hash);
      var arr = behaviorProbs[hash];
      //me.println(arr);
      var rnum = Math.random();
      var ele = arr.find(function(item) {
        var rc = rnum < item.probability;
        return rc;
      });
      //me.println(ele.action);
      ava.action(ele.action);
    }
  },

  process: function(str) {
    //me.println(str.length);
    var rows = str.split("\n");
    var hash = "";
    var action = "";
    var outobj = {};
    var totalCounts = 0;
    var arr = [];
    for (var i = 0; i < rows.length; i++) {
      var row = rows[i];
      //me.println(row);
      var fields = row.split(",");
      if (fields[FIELD_HASH] == hash) {
        totalCounts += Number(fields[FIELD_COUNT]);
        //outobj[hash][fields[FIELD_ACTION]] = fields[FIELD_COUNT];
        var obj = {};
        obj.action = fields[FIELD_ACTION];
        obj.count = fields[FIELD_COUNT];
        arr.push(obj);
      }
      else {
        //$wnd.console.log(outobj[hash]);
        //$wnd.console.log(totalCounts);
        arr = this.adjust(totalCounts, arr);
        //console.log(arr);
        if (hash) {
          outobj[hash] = arr;
        }
        arr = [];
        totalCounts = 0;
        hash = fields[FIELD_HASH];
        //outobj[hash] = {};
        totalCounts += Number(fields[FIELD_COUNT]);
        //outobj[hash][fields[FIELD_ACTION]] = fields[FIELD_COUNT];
        var obj = {};
        obj.action = fields[FIELD_ACTION];
        obj.count = fields[FIELD_COUNT];
        arr.push(obj);
      }
    }
    // handle final set of rows
    arr = this.adjust(totalCounts, arr);
    if (hash) {
      outobj[hash] = arr;
    }
    return outobj;
  },

  adjust: function(totalCounts, adjarr) {
    // sort ascending
    adjarr.sort(function (a, b) {
      return a.count - b.count;
    });
    var sumProbs = 0;
    for (var i = 0; i < adjarr.length; i++) {
      sumProbs += adjarr[i].count / totalCounts;
      adjarr[i].probability = sumProbs;
    }
    return adjarr;
  }
  
}
//# sourceURL=QueryResultsProcessor.js
  `;

  $wnd.xh.StochasticBehavior.TestWorldbehavior = `
const DB_NAME = "AvatarStateDB";
const DB_VERSION = 1;
const DB_STORE_NAME = "avastate";
const DB_STORE_KEY = ["hash", "action"];
const AVA_TEST = false; // whether or not to repeatedly run the Avatar test suite
const COLLECT_DATA = false; // whether or not to collect new data

var me, wcs, wcsobj, ava, newState, prevState, idbs, idbsobj, idbssetup, idlecount, beh = {
postConfigure: function() {
  wcs = $wnd.xh.service("WebCryptographyService");
  idbs = $wnd.xh.service("IndexedDBService");
  idbssetup = false;
  idlecount = 0;
  me = this.cnode.parent();
  if ($wnd.xh.html["selectTab"]) {
    $wnd.xh.html.selectTab(0); // display contents of the "out" tab
  }
  
  me.parent().append(this.cnode.remove());
  var city = me.first();
  var things = me.next();
  var thing = things.first();
  while (thing) {
    var nextThing = thing.next();
    city.append(thing.remove());
    thing = nextThing;
    city = city.next();
    if (city == null) {
      city = me.first();
    }
  }
  
  ava = $wnd.xh.avatar();
  
  // initialize prevState
  newState = "";
  this.hashifySXpres(ava);
  prevState = newState;
  newState = "";
  
  ava.action("param currentActions true;");
  var initActions = "param transcript false;param setCtxtOnselect false;param repeat true;appear;become this role " + "Avatario" + ";" + "enter;enter testWorld;enter;pause;";
  ava.action(initActions);
  if (AVA_TEST) { // test suite
    var testActions = "script;\nnext;\nnext;\nprev;\nprev;\nenter;\nwait 5;\nexit;\nbreakpoint;";
    ava.action(testActions);
  }
  ava.action("pause;");

  $wnd.xh.require("StochasticBehavior");
}, // end postConfigure()

act: function() {
  if (COLLECT_DATA) {
    newState = "";
    this.hashifySXpres(ava.parent());
    if (newState == prevState) {
      idlecount++;
    }
    else {
      ava.action("out transcript " + newState);
      var jsonStr = prevState;
      prevState = newState;
      wcsobj = wcs.obj();
      if (wcsobj) {
        // the async hash function will send an async message that will be received by processReceivedMessage(msg)
        wcsobj.hash(jsonStr, this.cnode);
      }
    }
    idbsobj = idbs.obj();
    if (idbsobj && !idbssetup) {
      idbsobj.setup(DB_NAME, DB_STORE_NAME, DB_STORE_KEY);
      idbssetup = true;
    }
  }
  if ($wnd.xh.StochasticBehavior) {
    $wnd.xh.StochasticBehavior.test("123");
  }
}, // end act()

processReceivedMessage: function(msg) {
  if (msg.signal == 101) {
    // process a message from the hash function
    if (idlecount > 0) {
      this.saveIdleToDB(msg.data[1], msg.data[0], idlecount);
    }
    idlecount = 0;
    this.saveToDB(msg.data[1], msg.data[0]);
  }
},

/**
 * Recursively hashify a Xholon subtree, with output in a Lisp S-expression format.
 * @param node A node in the Xholon hierarchy.
 */
hashifySXpres: function(node) {
  newState += node.name("R^^^^^");
  if (node.xhc().name() == "Space") {
    newState += (" (Avatar)");
  }
  else if (node.first()) {
    newState += " (";
    var childNode = node.first();
    while (childNode != null) {
      this.hashifySXpres(childNode);
      childNode = childNode.next();
      if (childNode != null) {
        newState += " ";
      }
    }
    newState += ")";
  }
  else if (node["maxClones"] && (node.parent().xhc().name() != "Avatar")) {
    newState += "*" + node["maxClones"];
  }
},

saveToDB: function(key, state) {
  var ignoreArr = ["out", "pause", "pause;", "step", "step;"];
  var currentActionStr = ava["currentActions"].pop(); //shift();
  while (currentActionStr) {
    // dispose of specific action strings, such as ones that start with "out"
    var caArr = currentActionStr.split(" ");
    if (!ignoreArr.includes(caArr[0])) {break;}
    currentActionStr = ava["currentActions"].pop(); //shift();
  }
  ava["currentActions"].splice(0,ava["currentActions"].length); // clear contents of the array
  if (!currentActionStr) {return;}
  var record = {};
  record["hash"] = key;
  record["state"] = state;
  record["action"] = currentActionStr.replace(";", ""); // ex: "next;" becomes "next"
  record["count"] = 1;
  idbsobj.update(DB_NAME, DB_STORE_NAME, [key, currentActionStr], record);
},

saveIdleToDB: function(key, state, count) {
  var currentActionStr = "idle";
  var record = {};
  record["hash"] = key;
  record["state"] = state;
  record["action"] = currentActionStr;
  record["count"] = count;
  //me.println(JSON.stringify(record));
  idbsobj.update(DB_NAME, DB_STORE_NAME, [key, currentActionStr], record);
}

}
//# sourceURL=TestWorldbehavior.js
  `;

})(window, document); // end (function()

