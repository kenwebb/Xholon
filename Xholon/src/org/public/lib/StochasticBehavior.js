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
  // Generally-useful Constants
  const constants = {}
  constants.INDEXEDDB_COLUMN_NAMES = ["hash", "state", "action", "count"];
  
  // fields/columns in each row of the source data
  constants.FIELD_HASH   = 0;
  constants.FIELD_STATE  = 1;
  constants.FIELD_ACTION = 2;
  constants.FIELD_COUNT  = 3;
  
  constants.CONTROL_DETERMINISTICALLY = false;
  
  constants.DEFAULT_MAX_LEVELS = 99;
  constants.DEFAULT_NAME_TEMPLATE = "R^^^^^";
  
  constants.DEFAULT_DB_NAME = "AvatarStateDB";
  constants.DEFAULT_DB_STORE_NAME = "avastate";
  constants.DEFAULT_DB_STORE_KEY = ["hash", "action"];
  constants.DEFAULT_COLLECT_DATA = false; // whether or not to collect new data
  
  constants.BRACKETS = [" ("," ",")"];
  
  constants.IGNORE_LIST = null; //["Avatar"]; // TODO use this to filter out nodes in hashifySXpres()
  
  // TODO should I use these constants?
  //const WebCryptographyService = $wnd.xh.service("WebCryptographyService");
  //const IndexedDBService = $wnd.xh.service("IndexedDBService");
  
  $wnd.xh.StochasticBehavior.setup = function(constantsArg) {
    // for each Object property in constantsArg, set the corresponding property in constants
    Object.keys(constantsArg).forEach(function(key,index) {
      constants[key] = constantsArg[key];
    });
  }
  
  $wnd.xh.StochasticBehavior.hashifySXpres = function(node, level) {
    var nodeName = node.name(this.nameTemplate);
    if (constants.IGNORE_LIST && (constants.IGNORE_LIST.indexOf(nodeName) != -1)) {
      return "";
    }
    this.newState += nodeName;
    if (node.xhc().name() == "Space") {
      // this is specific to the Island Game
      this.newState += (" (Avatar)");
    }
    else if (level != this.maxLevels) {
      var childNode = node.first();
      if (childNode) {
        this.newState += constants.BRACKETS[0]; //" (";
        while (childNode != null) {
          var separator = this.hashifySXpres(childNode, level + 1);
          childNode = childNode.next();
          if (childNode != null) {
            this.newState += separator; //" ";
          }
        }
        this.newState += constants.BRACKETS[2]; //")";
      }
      else if (node["maxClones"] && (node.parent().xhc().name() != "Avatar")) {
        this.newState += "*" + node["maxClones"];
      }
    }
    return constants.BRACKETS[1];
  }
  
  // possible new version that uses constants.IGNORE_LIST; this is quite tricky
  /*$wnd.xh.StochasticBehavior.hashifySXpres = function(node, level, nodeName) {
    var nodeName = nodeName || node.name(this.nameTemplate);
    this.newState += nodeName;
    if (node.xhc().name() == "Space") {
      // this is specific to the Island Game
      this.newState += (" (Avatar)");
    }
    else if (level != this.maxLevels) {
      var childNode = node.first();
      if (childNode) {
        var childNodeName = childNode.name(this.nameTemplate);
        if (constants.IGNORE_LIST && (constants.IGNORE_LIST.indexOf(childNodeName) != -1) && !childNode.next()) {
          // ignore this single child that is in the IGNORE_LIST  ex: a single other Avatar node
        }
        else {
          this.newState += constants.BRACKETS[0]; //" (";
          while (childNode != null) {
            //var childNodeName = childNode.name(this.nameTemplate);
            this.hashifySXpres(childNode, level + 1, childNodeName);
            childNode = childNode.next();
            if (childNode != null) {
              this.newState += constants.BRACKETS[1]; //" ";
              childNodeName = childNode.name(this.nameTemplate);
            }
          }
          this.newState += constants.BRACKETS[2]; //")";
        }
      }
      else if (node["maxClones"] && (node.parent().xhc().name() != "Avatar")) {
        this.newState += "*" + node["maxClones"];
      }
    }
  }*/
  
  // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
  // DisplayDbButton
  $wnd.xh.StochasticBehavior.DisplayDbButton = function DisplayDbButton(dbName, dbStoreName) {
    this.dbName = dbName || constants.DEFAULT_DB_NAME; // ex: "AvatarStateDB"
    this.dbStoreName = dbStoreName || constants.DEFAULT_DB_STORE_NAME; // ex: "avastate"
  }

  $wnd.xh.StochasticBehavior.DisplayDbButton.prototype.handleNodeSelection = function() {
    $wnd.xh.IndexedDBService.display(this.dbName, this.dbStoreName, constants.INDEXEDDB_COLUMN_NAMES);
    return "\u0011";
  }

  
  // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
  // QueryDbButton
  const QUERY_ACTION_TYPE = 1; // 1, 2, 3
  $wnd.xh.StochasticBehavior.QueryDbButton = function QueryDbButton(queryActionType, dbName, dbStoreName) {
    this.queryActionType = queryActionType || QUERY_ACTION_TYPE;
    this.dbName = dbName || constants.DEFAULT_DB_NAME; // ex: "AvatarStateDB"
    this.dbStoreName = dbStoreName || constants.DEFAULT_DB_STORE_NAME; // ex: "avastate"
  }
  
  $wnd.xh.StochasticBehavior.QueryDbButton.prototype.handleNodeSelection = function() {
    switch (this.queryActionType) {
    case 1: $wnd.xh.IndexedDBService.query(this.dbName, this.dbStoreName, constants.INDEXEDDB_COLUMN_NAMES, this.cnode.next(), "text"); break;
    case 2: $wnd.xh.IndexedDBService.query(this.dbName, this.dbStoreName, constants.INDEXEDDB_COLUMN_NAMES, this.cnode, "msg", 401); break;
    case 3: $wnd.xh.IndexedDBService.query(this.dbName, this.dbStoreName, constants.INDEXEDDB_COLUMN_NAMES, this.cnode, "call", 401); break;
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
  
  $wnd.xh.StochasticBehavior.QueryResultsProcessor = function QueryResultsProcessor(ava, maxLevels, nameTemplate) {
    this.ava = ava || $wnd.xh.avatar();
    this.maxLevels = maxLevels || constants.DEFAULT_MAX_LEVELS; //1;
    this.nameTemplate = nameTemplate || constants.DEFAULT_NAME_TEMPLATE; //"R^^^^^";
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
      this.hashifySXpres(this.ava.parent(), 0); // a JSON string
      this.wcsobj = this.wcs.obj();
      if (this.wcsobj) {
        // the async hash function will send an async message that will be received by processReceivedMessage(msg)
        this.wcsobj.hash(this.newState, this.me);
      }
    }
    else {
      // deterministically control the Avatar
      if (constants.CONTROL_DETERMINISTICALLY) {
        this.ava.action("next");
      }
    }
  }
  
  /**
   * Recursively hashify a Xholon subtree, with output in a Lisp S-expression format.
   * @param node A node in the Xholon hierarchy.
   * @param level Current level in the IXholon subtree.
   */
  $wnd.xh.StochasticBehavior.QueryResultsProcessor.prototype.hashifySXpres = function(node, level) {
    return $wnd.xh.StochasticBehavior.hashifySXpres.call(this, node, level);
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
      if (fields[constants.FIELD_HASH] == hash) {
        totalCounts += Number(fields[constants.FIELD_COUNT]);
        var obj = {};
        obj.action = fields[constants.FIELD_ACTION];
        obj.count = fields[constants.FIELD_COUNT];
        arr.push(obj);
      }
      else {
        arr = this.adjust(totalCounts, arr);
        if (hash) {
          outobj[hash] = arr;
        }
        arr = [];
        totalCounts = 0;
        hash = fields[constants.FIELD_HASH];
        totalCounts += Number(fields[constants.FIELD_COUNT]);
        var obj = {};
        obj.action = fields[constants.FIELD_ACTION];
        obj.count = fields[constants.FIELD_COUNT];
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

  $wnd.xh.StochasticBehavior.CollectData = function CollectData(dbName, dbStoreName, dbStoreKey, ava, maxLevels, nameTemplate, collectData) {
    this.dbName = dbName || constants.DEFAULT_DB_NAME;
    this.dbStoreName = dbStoreName || constants.DEFAULT_DB_STORE_NAME;
    this.dbStoreKey = dbStoreKey || constants.DEFAULT_DB_STORE_KEY;
    this.collectData = collectData || constants.DEFAULT_COLLECT_DATA;
    this.ava = ava || $wnd.xh.avatar();
    this.maxLevels = maxLevels || constants.DEFAULT_MAX_LEVELS; //1;
    this.nameTemplate = nameTemplate || constants.DEFAULT_NAME_TEMPLATE; //"R^^^^^";
  }

  $wnd.xh.StochasticBehavior.CollectData.prototype.postConfigure = function() {
    this.wcs = $wnd.xh.service("WebCryptographyService");
    this.idbs = $wnd.xh.service("IndexedDBService");
    this.idbssetup = false;
    this.idlecount = 0;
    
    // initialize prevState
    this.newState = "";
    this.hashifySXpres(this.ava, 0);
    this.prevState = this.newState;
    this.newState = "";
  }

  $wnd.xh.StochasticBehavior.CollectData.prototype.act = function() {
    if (this.collectData) {
      this.newState = "";
      this.hashifySXpres(this.ava.parent(), 0);
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
        this.idbsobj.setup(this.dbName, this.dbStoreName, this.dbStoreKey);
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
   * @param level Current level in the IXholon subtree.
   */
  $wnd.xh.StochasticBehavior.CollectData.prototype.hashifySXpres = function(node, level) {
    return $wnd.xh.StochasticBehavior.hashifySXpres.call(this, node, level);
  }
  
  const AVA_CURRENT_ACTIONS = "currentActions";
  
  $wnd.xh.StochasticBehavior.CollectData.prototype.saveToDB = function(key, state) {
    var ignoreArr = ["out", "pause", "pause;", "step", "step;"];
    var currentActionStr = this.ava[AVA_CURRENT_ACTIONS].pop(); //shift();
    while (currentActionStr) {
      // dispose of specific action strings, such as ones that start with "out"
      var caArr = currentActionStr.split(" ");
      if (!ignoreArr.includes(caArr[0])) {break;}
      currentActionStr = this.ava[AVA_CURRENT_ACTIONS].pop(); //shift();
    }
    this.ava[AVA_CURRENT_ACTIONS].splice(0,this.ava[AVA_CURRENT_ACTIONS].length); // clear contents of the array
    if (!currentActionStr) {return;}
    var record = {};
    record["hash"] = key;
    record["state"] = state;
    record["action"] = currentActionStr.replace(";", ""); // ex: "next;" becomes "next"
    record["count"] = 1;
    this.idbsobj.update(this.dbName, this.dbStoreName, [key, currentActionStr], record);
  }

  $wnd.xh.StochasticBehavior.CollectData.prototype.saveIdleToDB = function(key, state, count) {
    var currentActionStr = "idle";
    var record = {};
    record["hash"] = key;
    record["state"] = state;
    record["action"] = currentActionStr;
    record["count"] = count;
    this.idbsobj.update(this.dbName, this.dbStoreName, [key, currentActionStr], record);
  }
  
})(window, document); // end (function()

