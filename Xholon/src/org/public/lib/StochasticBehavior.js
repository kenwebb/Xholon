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

if (typeof window.xh == "undefined") {
  window.xh = {};
}

if (typeof window.xh.StochasticBehavior == "undefined") {
  window.xh.StochasticBehavior = {};
}

(function($wnd, $doc) {
  //$wnd = window;
  
  $wnd.xh.StochasticBehavior.test = function(arg) {
    console.log("StochasticBehavior testing: " + arg);
  }
  
  // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
  // DisplayDbButton
  $wnd.xh.StochasticBehavior.DisplayDbButton = function DisplayDbButton(dbName, dbStoreName) {
    this.dbName = dbName; // ex: "AvatarStateDB"
    this.dbStoreName = dbStoreName; // ex: "avastate"
  }

  $wnd.xh.StochasticBehavior.DisplayDbButton.prototype.handleNodeSelection = function() {
    $wnd.xh.IndexedDBService.display(this.dbName, this.dbStoreName, ["hash", "state", "action", "count"]);
    return "\u0011";
  }

  
  // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
  // QueryDbButton
  var QUERY_ACTION_TYPE = 1; // 1, 2, 3
  $wnd.xh.StochasticBehavior.QueryDbButton = function QueryDbButton(queryActionType, dbName, dbStoreName) {
    QUERY_ACTION_TYPE = queryActionType;
    this.dbName = dbName; // ex: "AvatarStateDB"
    this.dbStoreName = dbStoreName; // ex: "avastate"
  }
  
  $wnd.xh.StochasticBehavior.QueryDbButton.prototype.handleNodeSelection = function() {
    switch (QUERY_ACTION_TYPE) {
    case 1: $wnd.xh.IndexedDBService.query(this.dbName, this.dbStoreName, ["hash", "state", "action", "count"], this.cnode.next(), "text"); break;
    case 2: $wnd.xh.IndexedDBService.query(this.dbName, this.dbStoreName, ["hash", "state", "action", "count"], this.cnode, "msg", 401); break;
    case 3: $wnd.xh.IndexedDBService.query(this.dbName, this.dbStoreName, ["hash", "state", "action", "count"], this.cnode, "call", 401); break;
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
  
  // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
  // QueryResultsProcessor
  // usage: var beh = new $wnd.xh.StochasticBehavior.QueryResultsProcessor();
  
  // fields/columns in each row of the source data
  const FIELD_HASH   = 0;
  const FIELD_STATE  = 1;
  const FIELD_ACTION = 2;
  const FIELD_COUNT  = 3;
  
  const CONTROL_DETERMINISTICALLY = false;
  
  $wnd.xh.StochasticBehavior.QueryResultsProcessor = function QueryResultsProcessor(ava) {
    this.ava = ava;
  }
  
  $wnd.xh.StochasticBehavior.QueryResultsProcessor.prototype.postConfigure = function() {
    this.me = this.cnode; // QueryResultsProcessor node
    this.qr = this.me.parent(); // QueryResults node, where the source data lives
    this.processed = false; // whether or not the source data has been processed
    this.behaviorProbs = null;
    this.wcs = $wnd.xh.service("WebCryptographyService");
    this.newState = "";
  }
  
  $wnd.xh.StochasticBehavior.QueryResultsProcessor.prototype.act = function() {
    if (this.qr.text() && !this.processed) {
      // process the QueryResults
      var outobj = this.process(this.qr.text().trim());
      this.behaviorProbs = outobj;
      this.processed = true;
    }
    if (this.processed) {
      this.newState = "";
      this.hashifySXpres(this.ava.parent()); // a JSON string
      this.wcsobj = this.wcs.obj();
      if (this.wcsobj) {
        // the async hash function will send an async message that will be received by processReceivedMessage(msg)
        this.wcsobj.hash(this.newState, this.me);
      }
    }
    else {
      // deterministically control the Avatar
      if (CONTROL_DETERMINISTICALLY) {
        this.ava.action("next");
      }
    }
  }
  
  /**
   * Recursively hashify a Xholon subtree, with output in a Lisp S-expression format.
   * @param node A node in the Xholon hierarchy.
   */
  $wnd.xh.StochasticBehavior.QueryResultsProcessor.prototype.hashifySXpres = function(node) {
    this.newState += node.name("R^^^^^");
    if (node.xhc().name() == "Space") {
      this.newState += (" (Avatar)");
    }
    else if (node.first()) {
      this.newState += " (";
      var childNode = node.first();
      while (childNode != null) {
        this.hashifySXpres(childNode);
        childNode = childNode.next();
        if (childNode != null) {
          this.newState += " ";
        }
      }
      this.newState += ")";
    }
    else if (node["maxClones"] && (node.parent().xhc().name() != "Avatar")) {
      this.newState += "*" + node["maxClones"];
    }
  }

  $wnd.xh.StochasticBehavior.QueryResultsProcessor.prototype.processReceivedMessage = function(msg) {
    if (msg.signal == 101) {
      // process a message from the hash function
      var hash = msg.data[1];
      var arr = this.behaviorProbs[hash];
      var rnum = Math.random();
      var ele = arr.find(function(item) {
        var rc = rnum < item.probability;
        return rc;
      });
      this.ava.action(ele.action);
    }
  }

  $wnd.xh.StochasticBehavior.QueryResultsProcessor.prototype.process = function(str) {
    var rows = str.split("\n");
    var hash = "";
    var action = "";
    var outobj = {};
    var totalCounts = 0;
    var arr = [];
    for (var i = 0; i < rows.length; i++) {
      var row = rows[i];
      var fields = row.split(",");
      if (fields[FIELD_HASH] == hash) {
        totalCounts += Number(fields[FIELD_COUNT]);
        var obj = {};
        obj.action = fields[FIELD_ACTION];
        obj.count = fields[FIELD_COUNT];
        arr.push(obj);
      }
      else {
        arr = this.adjust(totalCounts, arr);
        if (hash) {
          outobj[hash] = arr;
        }
        arr = [];
        totalCounts = 0;
        hash = fields[FIELD_HASH];
        totalCounts += Number(fields[FIELD_COUNT]);
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
  }

  $wnd.xh.StochasticBehavior.QueryResultsProcessor.prototype.adjust = function(totalCounts, adjarr) {
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
  
  // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
  // CollectData
  var DB_NAME = "AvatarStateDB";
  var DB_STORE_NAME = "avastate";
  var DB_STORE_KEY = ["hash", "action"];
  var COLLECT_DATA = false; // whether or not to collect new data

  $wnd.xh.StochasticBehavior.CollectData = function CollectData(dbName, dbStoreName, dbStoreKey, ava, collectData) {
    DB_NAME = dbName;
    DB_STORE_NAME = dbStoreName;
    DB_STORE_KEY = dbStoreKey;
    COLLECT_DATA = collectData;
    this.ava = ava;
  }

  $wnd.xh.StochasticBehavior.CollectData.prototype.postConfigure = function() {
    this.wcs = $wnd.xh.service("WebCryptographyService");
    this.idbs = $wnd.xh.service("IndexedDBService");
    this.idbssetup = false;
    this.idlecount = 0;
    
    // initialize prevState
    this.newState = "";
    this.hashifySXpres(this.ava);
    this.prevState = this.newState;
    this.newState = "";
  }

  $wnd.xh.StochasticBehavior.CollectData.prototype.act = function() {
    if (COLLECT_DATA) {
      this.newState = "";
      this.hashifySXpres(this.ava.parent());
      if (this.newState == this.prevState) {
        this.idlecount++;
      }
      else {
        this.ava.action("out transcript " + this.newState);
        var jsonStr = this.prevState;
        this.prevState = this.newState;
        this.wcsobj = this.wcs.obj();
        if (this.wcsobj) {
          // the async hash function will send an async message that will be received by processReceivedMessage(msg)
          this.wcsobj.hash(jsonStr, this.cnode);
        }
      }
      this.idbsobj = this.idbs.obj();
      if (this.idbsobj && !this.idbssetup) {
        this.idbsobj.setup(DB_NAME, DB_STORE_NAME, DB_STORE_KEY);
        this.idbssetup = true;
      }
    }
  }

  $wnd.xh.StochasticBehavior.CollectData.prototype.processReceivedMessage = function(msg) {
    if (msg.signal == 101) {
      // process a message from the hash function
      if (this.idlecount > 0) {
        this.saveIdleToDB(msg.data[1], msg.data[0], this.idlecount);
      }
      this.idlecount = 0;
      this.saveToDB(msg.data[1], msg.data[0]);
    }
  }

  /**
   * Recursively hashify a Xholon subtree, with output in a Lisp S-expression format.
   * @param node A node in the Xholon hierarchy.
   */
  $wnd.xh.StochasticBehavior.CollectData.prototype.hashifySXpres = function(node) {
    this.newState += node.name("R^^^^^");
    if (node.xhc().name() == "Space") {
      this.newState += (" (Avatar)");
    }
    else if (node.first()) {
      this.newState += " (";
      var childNode = node.first();
      while (childNode != null) {
        this.hashifySXpres(childNode);
        childNode = childNode.next();
        if (childNode != null) {
          this.newState += " ";
        }
      }
      this.newState += ")";
    }
    else if (node["maxClones"] && (node.parent().xhc().name() != "Avatar")) {
      this.newState += "*" + node["maxClones"];
    }
  }

  $wnd.xh.StochasticBehavior.CollectData.prototype.saveToDB = function(key, state) {
    var ignoreArr = ["out", "pause", "pause;", "step", "step;"];
    var currentActionStr = this.ava["currentActions"].pop(); //shift();
    while (currentActionStr) {
      // dispose of specific action strings, such as ones that start with "out"
      var caArr = currentActionStr.split(" ");
      if (!ignoreArr.includes(caArr[0])) {break;}
      currentActionStr = this.ava["currentActions"].pop(); //shift();
    }
    this.ava["currentActions"].splice(0,this.ava["currentActions"].length); // clear contents of the array
    if (!currentActionStr) {return;}
    var record = {};
    record["hash"] = key;
    record["state"] = state;
    record["action"] = currentActionStr.replace(";", ""); // ex: "next;" becomes "next"
    record["count"] = 1;
    this.idbsobj.update(DB_NAME, DB_STORE_NAME, [key, currentActionStr], record);
  }

  $wnd.xh.StochasticBehavior.CollectData.prototype.saveIdleToDB = function(key, state, count) {
    var currentActionStr = "idle";
    var record = {};
    record["hash"] = key;
    record["state"] = state;
    record["action"] = currentActionStr;
    record["count"] = count;
    this.idbsobj.update(DB_NAME, DB_STORE_NAME, [key, currentActionStr], record);
  }
  
})(window, document); // end (function()

