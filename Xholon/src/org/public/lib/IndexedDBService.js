'use strict';

/**
 * IndexedDBService
 * Ken Webb
 * August 19, 2018
 * 
 * testing:
var idbs = xh.service("IndexedDBService");
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
    console.log("IndexedDBService is available");
  };
  
  request.onupgradeneeded = function(event) {
    db = event.target.result;
    var objectStore = db.createObjectStore(objstoreName, { keyPath: keypathStr });

    // Use transaction oncomplete to make sure the objectStore creation is finished before adding data into it.
    objectStore.transaction.oncomplete = function(event) {
      // Store values in the newly created objectStore.
      //var avaObjectStore = db.transaction(objstoreName, "readwrite").objectStore(objstoreName);
    };
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
    var avaObjectStore = db.transaction(objstoreName, "readwrite").objectStore(objstoreName);
    var request = avaObjectStore.get(key);
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
          var requestUpdate = avaObjectStore.put(data);
          requestUpdate.onerror = function(event) {
            //console.log("Do something with the error");
          };
          requestUpdate.onsuccess = function(event) {
            //console.log("Success - the data is updated!");
          };
        }
      }
      else {
        avaObjectStore.add(newData);
      }
    };
  };
}

