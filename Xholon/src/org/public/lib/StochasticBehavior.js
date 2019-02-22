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
  $wnd.xh.StochasticBehavior.DisplayDbButton = function DisplayDbButton() {}

  $wnd.xh.StochasticBehavior.DisplayDbButton.prototype.handleNodeSelection = function() {
    $wnd.xh.IndexedDBService.display("AvatarStateDB", "avastate", ["hash", "state", "action", "count"]);
    return "\u0011";
  }

  
  // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
  // QueryDbButton
  var QUERY_ACTION_TYPE = 1; // 1, 2, 3
  $wnd.xh.StochasticBehavior.QueryDbButton = function QueryDbButton(queryActionType) {
    QUERY_ACTION_TYPE = queryActionType;
  }
  
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
  
  // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
  // QueryResultsProcessor
  // usage: var beh = new $wnd.xh.StochasticBehavior.QueryResultsProcessor();
  
  // fields/columns in each row of the source data
  const FIELD_HASH   = 0;
  const FIELD_STATE  = 1;
  const FIELD_ACTION = 2;
  const FIELD_COUNT  = 3;
  
  const CONTROL_DETERMINISTICALLY = false;
  
  $wnd.xh.StochasticBehavior.QueryResultsProcessor = function QueryResultsProcessor() {
    //var me, qr, processed, ava, behaviorProbs, wcs, wcsobj, newState;
  }
  
  $wnd.xh.StochasticBehavior.QueryResultsProcessor.prototype.postConfigure = function() {
    this.me = this.cnode; // QueryResultsProcessor node
    this.qr = this.me.parent(); // QueryResults node, where the source data lives
    this.processed = false; // whether or not the source data has been processed
    this.ava = $wnd.xh.avatar();
    this.behaviorProbs = null;
    this.wcs = $wnd.xh.service("WebCryptographyService");
    this.newState = "";
  }
  
  $wnd.xh.StochasticBehavior.QueryResultsProcessor.prototype.act = function() {
    if (this.qr.text() && !this.processed) {
      // process the QueryResults
      //me.println("ready to process query results ... TODO");
      var outobj = this.process(this.qr.text().trim());
      $wnd.console.log(outobj);
      //me.println(JSON.stringify(outobj));
      this.behaviorProbs = outobj;
      this.processed = true;
    }
    if (this.processed) {
      // use behaviorProbs to drive the Avatar
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
      //me.println(hash);
      var arr = this.behaviorProbs[hash];
      //me.println(arr);
      var rnum = Math.random();
      var ele = arr.find(function(item) {
        var rc = rnum < item.probability;
        return rc;
      });
      //me.println(ele.action);
      this.ava.action(ele.action);
    }
  }

  $wnd.xh.StochasticBehavior.QueryResultsProcessor.prototype.process = function(str) {
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
  // TestWorldbehavior
  const DB_NAME = "AvatarStateDB";
  const DB_VERSION = 1;
  const DB_STORE_NAME = "avastate";
  const DB_STORE_KEY = ["hash", "action"];
  const AVA_TEST = false; // whether or not to repeatedly run the Avatar test suite
  const COLLECT_DATA = false; // whether or not to collect new data

  $wnd.xh.StochasticBehavior.TestWorldbehavior = function TestWorldbehavior() {
    //var me, wcs, wcsobj, ava, newState, prevState, idbs, idbsobj, idbssetup, idlecount;
  }

  $wnd.xh.StochasticBehavior.TestWorldbehavior.prototype.postConfigure = function() {
    this.wcs = $wnd.xh.service("WebCryptographyService");
    this.idbs = $wnd.xh.service("IndexedDBService");
    this.idbssetup = false;
    this.idlecount = 0;
    this.me = this.cnode.parent();
    if ($wnd.xh.html["selectTab"]) {
      $wnd.xh.html.selectTab(0); // display contents of the "out" tab
    }
    
    this.me.parent().append(this.cnode.remove());
    var city = this.me.first();
    var things = this.me.next();
    var thing = things.first();
    while (thing) {
      var nextThing = thing.next();
      city.append(thing.remove());
      thing = nextThing;
      city = city.next();
      if (city == null) {
        city = this.me.first();
      }
    }
    
    this.ava = $wnd.xh.avatar();
    
    // initialize prevState
    this.newState = "";
    this.hashifySXpres(this.ava);
    this.prevState = this.newState;
    this.newState = "";
    
    this.ava.action("param currentActions true;");
    var initActions = "param transcript false;param setCtxtOnselect false;param repeat true;appear;become this role " + "Avatario" + ";" + "enter;enter testWorld;enter;pause;";
    this.ava.action(initActions);
    if (AVA_TEST) { // test suite
      var testActions = "script;\nnext;\nnext;\nprev;\nprev;\nenter;\nwait 5;\nexit;\nbreakpoint;";
      this.ava.action(testActions);
    }
    this.ava.action("pause;");

    //$wnd.xh.require("StochasticBehavior");
    
    $wnd.xh.css.style("body {background-color: #110934; color: white}");
    $wnd.xh.css.style("textarea {background-color: rgba(240, 247, 212, 1.0);}");
  } // end postConfigure()

  $wnd.xh.StochasticBehavior.TestWorldbehavior.prototype.act = function() {
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
    //if ($wnd.xh.StochasticBehavior) {
    //  $wnd.xh.StochasticBehavior.test("123");
    //}
  } // end act()

  $wnd.xh.StochasticBehavior.TestWorldbehavior.prototype.processReceivedMessage = function(msg) {
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
  $wnd.xh.StochasticBehavior.TestWorldbehavior.prototype.hashifySXpres = function(node) {
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

  $wnd.xh.StochasticBehavior.TestWorldbehavior.prototype.saveToDB = function(key, state) {
    var ignoreArr = ["out", "pause", "pause;", "step", "step;"];
    var currentActionStr = ava["currentActions"].pop(); //shift();
    while (currentActionStr) {
      // dispose of specific action strings, such as ones that start with "out"
      var caArr = currentActionStr.split(" ");
      if (!ignoreArr.includes(caArr[0])) {break;}
      currentActionStr = ava["currentActions"].pop(); //shift();
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

  $wnd.xh.StochasticBehavior.TestWorldbehavior.prototype.saveIdleToDB = function(key, state, count) {
    var currentActionStr = "idle";
    var record = {};
    record["hash"] = key;
    record["state"] = state;
    record["action"] = currentActionStr;
    record["count"] = count;
    //me.println(JSON.stringify(record));
    this.idbsobj.update(DB_NAME, DB_STORE_NAME, [key, currentActionStr], record);
  }
  
})(window, document); // end (function()

