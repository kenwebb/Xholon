/**
 * Replicate a Xholon app in JavaScript.
 * replicate01.js
 * Ken Webb
 * January 19, 2020
 * console.log(repljs01(5));
 * see also: http://rosettacode.org/wiki/Singly-linked_list/Element_definition#JavaScript
 */

/**
 * Return an array
 * @param n number of elements in the resulting array
 * @param base 0 or 1 or something else
 */
var arrayN = function(n, base) {
  let num = base;
  const arr = new Array(n);
  for (let index = 0; index < n; index++) {
    arr[index] = num++;
  }
  return arr;
}

/*
 * Return an array in which each element contains a JavaScript Object that references the Object in the next array element.
 * @param n number of elements in the resulting array
 * @param succStr label to use for the property that references the next array element (ex: "succ")
 * @return an Array of length n, where each element has the form {succ: NEXT}
 *  ex: [{succ: NEXT}, {succ: NEXT}, ..., {succ: NEXT}, null]
 */
var linkedListN = function(n, succStr) {
  // n_
  //const n = 5;
  //console.log(n);
  // build an array of a specified size
  const arr = new Array(n);
  //console.log(arr);
  //console.log(arr.length);
  // build a JavaScript object inside each slot in the array
  //for (let index = 0; index < arr.length; index++) {
  //  arr[index] = {};
  //}
  // build a linked list by connecting all the objects
  //for (let index = 0; index < arr.length; index++) {
  //  arr[index].succ = arr[index+1];
  //}
  // OR
  for (let index = arr.length-1; index >= 0; index--) {
    arr[index] = {};
    arr[index][succStr] = arr[index+1] || null;
  }
  //console.log(arr[0]);
  // lift the newly created linked list out of the array
  return arr;
}

/*
 * This returns the same link strucure as calling linkedListN(n, "_next")
 * source: http://rosettacode.org/wiki/Singly-linked_list/Element_definition#JavaScript
 */
var sll = function(valueArr) {
  function LinkedList(value, next) {
    this._value = value;
    this._next = next;
  }
  LinkedList.prototype.value = function() {
    if (arguments.length == 1) 
      this._value = arguments[0];
    else
      return this._value;
  }
  LinkedList.prototype.next = function() {
    if (arguments.length == 1) 
      this._next = arguments[0];
    else
      return this._next;
  }
   
  // convenience function to assist the creation of linked lists.
  function createLinkedListFromArray(ary) {
    var head = new LinkedList(ary[0], null);
    var prev = head;
    for (var i = 1; i < ary.length; i++) {
      var node = new LinkedList(ary[i], null);
      prev.next(node);
      prev = node;
    }
    return head;
  }
   
  var head = createLinkedListFromArray(valueArr); //([10,20,30,40]);
  return head;
}

var $wnd = $wnd || window;
var app = $wnd.xh.app();
var numc = app.numChildren(true);

// arrayN
var arr1 = arrayN(numc, 0);
console.log(arr1);
console.log(JSON.stringify(arr1));

// linkedListN
var arr2 = linkedListN(numc, "succ");
console.log(arr2);
console.log(JSON.stringify(arr2));
var head = arr2[0]; // first item in the linked list
console.log(head);
console.log(JSON.stringify(head)); // '{"succ":{"succ":{"succ":{ ... }}}}'

console.log("The two arrays should both be the same length: " + arr1.length + " " + arr2.length);

// sll
var head2 = sll(arr1);
console.log(head2);
console.log(JSON.stringify(head2));

