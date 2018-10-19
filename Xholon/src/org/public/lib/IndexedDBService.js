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
 * TODO write to Xholon clipboard instead of to Xholon "out" tab
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
 */
xh.IndexedDBService.setup = function(dbName, objstoreName, keypathStr) {
  if (!dbName) {console.error("xh.IndexedDBService.setup() dbName is null"); return;}
  if (!objstoreName) {console.error("xh.IndexedDBService.setup() objstoreName is null"); return;}
  if (!keypathStr) {console.error("xh.IndexedDBService.setup() keypathStr is null"); return;}
  var root = xh.root();
  if (!window.indexedDB) {
    root.println("Your browser doesn't support a stable version of IndexedDB. Such and such feature will not be available.");
    return;
  }
  
  var db;
  var request = indexedDB.open(dbName);
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
xh.IndexedDBService.update = function(dbName, objstoreName, key, newData) {
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
        var count = data.count;
        if (count) {
          data.count = count + 1;
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
      else {
        objectStore.add(newData);
      }
    };
  };
}

/**
 * Display a database and object store.
 * usage (from Dev Tools): xh.IndexedDBService.display("AvatarStateDB", "avastate", ["state", "action", "count"]);
 * @param columnNameArr an array of column names to display  ex: ["state", "action", "count"]
 */
xh.IndexedDBService.display = function(dbName, objstoreName, columnNameArr) {
  if (!dbName) {console.error("xh.IndexedDBService.update() dbName is null"); return;}
  if (!objstoreName) {console.error("xh.IndexedDBService.update() objstoreName is null"); return;}
  var root = xh.root();
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
        str += cursor.value[columnNameArr[0]] + ', ' + cursor.value[columnNameArr[1]] + ', ' + cursor.value[columnNameArr[2]];
        cursor.continue();
      } else {
        console.log('Entries all displayed.');
      }
      root.println(str);
    };
  
  }
}

