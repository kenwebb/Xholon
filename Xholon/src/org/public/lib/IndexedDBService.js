'use strict';

/**
 * IndexedDBService
 * Ken Webb
 * August 19, 2018
 * 
 * testing:
var idbs = xh.service("IndexedDBService");
 *
 * TODO maybe use a third-party library instead of or in addition to this .js file, especially for querying
 * ex: http://dexie.org/  "Dexie.js A Minimalistic Wrapper for IndexedDB"
 */

if (typeof xh == "undefined") {
  xh = {};
}

xh.IndexedDBService = {}

/**
 * Set up a database and object store.
 * @param dbName ex: "MyTestDatabase" "AvatarStateDB"
 * @param objstoreName ex: "customers" "avastate"
 * @param keypathStr ex: "ssn" "hash" ["hash", "action"]
 * @param dbVersion ex: 1  the DB version (default: 1)
 */
xh.IndexedDBService.setup = function(dbName, objstoreName, keypathStr, dbVersion) {
  if (!dbName) {console.error("xh.IndexedDBService.setup() dbName is null"); return;}
  if (!objstoreName) {console.error("xh.IndexedDBService.setup() objstoreName is null"); return;}
  if (!keypathStr) {console.error("xh.IndexedDBService.setup() keypathStr is null"); return;}
  if (!dbVersion) {dbVersion = 1;}
  var root = xh.root();
  if (!window.indexedDB) {
    root.println("Your browser doesn't support a stable version of IndexedDB. Such and such feature will not be available.");
    return;
  }
  
  var db;
  var request = indexedDB.open(dbName, dbVersion);
  request.onerror = function(event) {
    console.error("Web app not allowed to use IndexedDB?!");
  };
  request.onsuccess = function(event) {
    db = event.target.result;
    //if (!db.objectStoreNames.contains(objstoreName)) {
    //  db.createObjectStore(objstoreName, { keyPath: keypathStr });
    //}
    console.log("IndexedDBService is available");
  };
  
  request.onupgradeneeded = function(event) {
    db = event.target.result;
    if (Array.isArray(objstoreName)) {
      for (var i = 0; i < objstoreName.length; i++) {
        // if objstoreName is an Array, then keypathStr must be a corresponding Array
        var objectStore = db.createObjectStore(objstoreName[i], { keyPath: keypathStr[i] });
        objectStore.transaction.oncomplete = function(event) {};
      }
    }
    else {
      var objectStore = db.createObjectStore(objstoreName, { keyPath: keypathStr });
      // Use transaction oncomplete to make sure the objectStore creation is finished before adding data into it.
      objectStore.transaction.oncomplete = function(event) {
        // Store values in the newly created objectStore.
      };
    }
  };

}

/**
 * Update a database and object store.
 */
xh.IndexedDBService.update = function(dbName, objstoreName, key, newData, addOnly) {
  if (!dbName) {console.error("xh.IndexedDBService.update() dbName is null"); return;}
  if (!objstoreName) {console.error("xh.IndexedDBService.update() objstoreName is null"); return;}
  if (!key) {console.error("xh.IndexedDBService.update() key is null"); return;}
  // if key is an array, it should not contain any null values
  if (Array.isArray(key)) {
    for (var i = 0; i < key.length; i++) {
      if (!key[i]) {console.error("xh.IndexedDBService.update() key[" + i + "] is null"); return;}
    }
  }
  var db;
  var request = indexedDB.open(dbName);
  request.onerror = function(event) {
    console.log("IndexedDBService can't find db " + dbName);
  };
  request.onsuccess = function(event) {
    db = event.target.result;
    var objectStore = db.transaction(objstoreName, "readwrite").objectStore(objstoreName);
    var request = objectStore.get(key);
    request.onerror = function(event) {
      console.log(event);
    };
    request.onsuccess = function(event) {
      var data = event.target.result;
      if (data) {
        if (!addOnly) {
          var count = data.count;
          if (count) {
            data.count = count + newData.count; //+ 1;
            // Put this updated object back into the database.
            var requestUpdate = objectStore.put(data);
            requestUpdate.onerror = function(event) {
              //console.log("Do something with the error");
            };
            requestUpdate.onsuccess = function(event) {
              //console.log("Success - the data is updated!");
            };
          }
        }
      }
      else {
        objectStore.add(newData);
      }
    };
  };
}

/**
 * Display a database and object store.
 * usage (from Dev Tools):
 *  xh.IndexedDBService.display("AvatarStateDB", "avastate", ["state", "action", "count"], "csv", "xhout");
 *  xh.IndexedDBService.display("IslandMobDB", "mobrole", ["state", "action", "count"], "json", "console");
 * @param columnNameArr an array of column names to display  ex: ["state", "action", "count"]
 * @param format outputs format  "json" or "csv"
 * @param target where the data will be displayed  "xhout" or "console" or TODO"xhclipboard" or TODO"sysclipboard" or TODO"localstorage"
 */
xh.IndexedDBService.display = function(dbName, objstoreName, columnNameArr, format, target) {
  if (!dbName) {console.error("xh.IndexedDBService.display() dbName is null"); return;}
  if (!objstoreName) {console.error("xh.IndexedDBService.display() objstoreName is null"); return;}
  var root = xh.root();
  var db;
  var request = indexedDB.open(dbName);
  request.onerror = function(event) {
    console.log("IndexedDBService can't find db " + dbName);
  };
  request.onsuccess = function(event) {
    db = event.target.result;
    var str = "";
    switch (format) {
    case "json": str += "[\n"; break;
    case "csv":
    default:
      str += columnNameArr.join() + "\n";
      break;
    }
    var objectStore = db.transaction(objstoreName, "readonly").objectStore(objstoreName);
    objectStore.openCursor().onsuccess = function(event) {
      var cursor = event.target.result;
      if(cursor) {
        switch (format) {
        case "json":
          var str1 = '{';
          var comma = '';
          for (var i = 0; i < columnNameArr.length; i++) {
            str1 += comma + '"' + columnNameArr[i] + '": "' + cursor.value[columnNameArr[i]] + '"';
            comma = ', ';
          }
          str += str1 + "},\n";
          break;
        case "csv":
        default:
          var comma = '';
          for (var i = 0; i < columnNameArr.length; i++) {
            str += comma + cursor.value[columnNameArr[i]];
            comma = ', ';
          }
          str += "\n";
          break;
        }
        cursor.continue();
      } else {
        switch (format) {
        case "json":
          // remove final comma
          str = str.slice(0, -2) + "\n";
          str += "]";
          break;
        case "csv":
        default:
          break;
        }
        switch (target) {
        case "console":
          switch (format) {
          case "json":
            console.log(JSON.parse(str));
            break;
          case "csv":
          default:
            console.log(str);
            break;
          }
          break;
        case "xhout":
        default:
          root.println(str);
          break;
        }
        console.log('Entries all displayed.');
      }
    }; // end objectStore.openCursor().onsuccess
  
  } // end request.onsuccess
}

/**
 * Query data from a database and object store.
 * @param xhNode a IXholon node.
 * @param action what method to call on the xhNode "text" "msg" "call"
 * if xhNode is a Attribute_String, then action might be "text" and signal would be 0 or undefined
 * if xhNode is a Java or JS behavior, then action could be "msg" or "call", and signal would be an integer such as 401
 */
xh.IndexedDBService.query = function(dbName, objstoreName, columnNameArr, xhNode, action, signal) {
  if (!dbName) {console.error("xh.IndexedDBService.query() dbName is null"); return;}
  if (!objstoreName) {console.error("xh.IndexedDBService.query() objstoreName is null"); return;}
  if (!xhNode) {console.error("xh.IndexedDBService.query() xhNode is null"); return;}
  if (typeof signal == "undefined") {signal = 101;}
  if (typeof action == "undefined") {action = "text";}
  var root = xh.root();
  var resultstr = "";
  var db;
  var request = indexedDB.open(dbName);
  request.onerror = function(event) {
    console.log("IndexedDBService can't find db " + dbName);
  };
  request.onsuccess = function(event) {
    db = event.target.result;
    var objectStore = db.transaction(objstoreName, "readonly").objectStore(objstoreName);
    objectStore.openCursor().onsuccess = function(event) {
      var cursor = event.target.result;
      var str = "";
      if(cursor) {
        var comma = "";
        for (var i = 0; i < columnNameArr.length; i++) {
          str += comma + cursor.value[columnNameArr[i]];
          comma = ",";
        }
        cursor.continue();
      } else {
        console.log('Query entries all obtained.');
        switch (action) {
        case "text":
          xhNode.text(resultstr);
          break;
        case "msg":
          xhNode.msg(signal, resultstr, root);
          break;
        case "call":
          var respmsg = xhNode.call(signal, resultstr, root);
          if (respmsg.data) {
            console.log(respmsg.data);
          }
          break;
        default: break;
        }
      }
      resultstr += str + "\n";
    };
  }
}

