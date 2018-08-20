'use strict';

/**
 * IndexedDBService
 * Ken Webb
 * August 19, 2018
 * 
 * testing:
var idbs = xh.service("IndexedDBService");
idbs.name();
idbs.obj();
var fobj = idbs.obj();
fobj(); // IndexedDBService is available
idbs.obj()(); // IndexedDBService is available
 */

if (typeof xh == "undefined") {
  xh = {};
}

xh.IndexedDBService = {}

/**
 * Set up a database and object store.
 * @param dbName ex: "MyTestDatabase" "AvatarStateDB"
 * @param objstoreName ex: "customers" "avastate"
 * @param keypathStr ex: "ssn" "hash"
 */
xh.IndexedDBService.setup = function(dbName, objstoreName, keypathStr) {
  var root = xh.root();
  //window.indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB;
  //window.IDBTransaction = window.IDBTransaction || window.webkitIDBTransaction || window.msIDBTransaction || {READ_WRITE: "readwrite"};
  // This line should only be needed if it is needed to support the object's constants for older browsers
  //window.IDBKeyRange = window.IDBKeyRange || window.webkitIDBKeyRange || window.msIDBKeyRange;
  if (!window.indexedDB) {
    root.println("Your browser doesn't support a stable version of IndexedDB. Such and such feature will not be available.");
    return;
  }
  
  var db;
  var request = indexedDB.open(dbName);
  request.onerror = function(event) {
    alert("Why didn't you allow my web app to use IndexedDB?!");
  };
  request.onsuccess = function(event) {
    db = event.target.result;
    console.log("IndexedDBService is available");
  };
  //db.onerror = function(event) {
  //  // Generic error handler for all errors targeted at this database's requests!
  //  alert("Database error: " + event.target.errorCode);
  //};
  
  // This is what our customer data looks like.
  /*const customerData = [
    { ssn: "444-44-4444", name: "Bill", age: 35, email: "bill@company.com" },
    { ssn: "555-55-5555", name: "Donna", age: 32, email: "donna@home.org" },
  ];*/
  
  // test data; these are just made-up values
  const avaData = [
    //{ hash: "3e5b208f3cc6bc51d833b433769ff0ef4baba1e183dab1c4c317b790db213892", state: "Ottawa (House Bicycle Dog Avatario)", action: "next", count: 1 },
    //{ hash: "54015536978b077ebfb5ba64a329737ccd979362eca6b9727ef763c5f5409a73", state: "Calgary (Car Toy Book Avatario)", action: "next", count: 2 },
    //{ hash: "55515536978b077ebfb5ba64a329737ccd979362eca6b9727ef763c5f5409999", state: "Montreal (Car Toy Book Avatario)" }
  ];
  
  request.onupgradeneeded = function(event) {
    db = event.target.result;

    // Create an objectStore
    var objectStore = db.createObjectStore(objstoreName, { keyPath: keypathStr });

    // Create an index to search customers by name. We may have duplicates
    // so we can't use a unique index.
    //objectStore.createIndex("name", "name", { unique: false });

    // Create an index to search customers by email. We want to ensure that
    // no two customers have the same email, so use a unique index.
    //objectStore.createIndex("email", "email", { unique: true });

    // Use transaction oncomplete to make sure the objectStore creation is 
    // finished before adding data into it.
    objectStore.transaction.oncomplete = function(event) {
      // Store values in the newly created objectStore.
      var avaObjectStore = db.transaction(objstoreName, "readwrite").objectStore(objstoreName);
      avaData.forEach(function(record) {
        avaObjectStore.add(record);
      });
    };
  }; // end request.onupgradeneeded

} // end xh.IndexedDBService.setup

xh.IndexedDBService.update = function(dbName, objstoreName, key, newData) {
  var db;
  var request = indexedDB.open(dbName);
  request.onerror = function(event) {
    //alert("Why didn't you allow my web app to use IndexedDB?!");
    console.log("IndexedDBService can't find db " + dbName);
  };
  request.onsuccess = function(event) {
    db = event.target.result;
    console.log("IndexedDBService is updating ...");
    
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
            console.log("Do something with the error");
          };
          requestUpdate.onsuccess = function(event) {
            console.log("Success - the data is updated!");
          };
        }
      }
      else {
        avaObjectStore.add(newData);
      }
    };
  };
}

