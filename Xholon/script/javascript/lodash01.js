/*
Ken Webb, 21 Jan 2021
http://127.0.0.1:8080/war/Xholon.html?app=HelloWorld&gui=clsc&jslib=lodash/lodash_fp.min

https://github.com/lodash/lodash/wiki/FP-Guide

// The `lodash/map` iteratee receives three arguments:
// (value, index|key, collection)
_.map(['6', '8', '10'], parseInt);
// ➜ [6, NaN, 2]

// The `lodash/fp/map` iteratee is capped at one argument:
// (value)
fp.map(parseInt)(['6', '8', '10']);
// ➜ [6, 8, 10]

// `lodash/padStart` accepts an optional `chars` param.
_.padStart('a', 3, '-')
// ➜ '--a'

// `lodash/fp/padStart` does not.
fp.padStart(3)('a');
// ➜ '  a'
fp.padCharsStart('-')(3)('a');
// ➜ '--a'

see: lodash01.html
I may need to do some initial setup to be able to use the fp version of the lodash code. ???
*/

// the _ may not be working because the .js file includes both lodash and lodash/fp
((title) => {
  console.log(title)
  console.log(_.map(['6', '8', '10'], parseInt)); // ???
  var array = [1, 2, 3];
  console.log(array);
  _.fill(array, 'a');
  console.log(array); // => ['a', 'a', 'a']
  var array2 = _.fill(Array(3), 2)
  console.log(array2); // => [2, 2, 2]
})("Lodash _")

((title) => {
  console.log(title)
  const fp = _ //.noConflict();
  console.log(fp.map(parseInt)(['6', '8', '10'])); // [6, 8, 10]
  const array = [1, 2, 3];
  console.log(fp.fill(0, 3, 'b', array)) // ["b", "b", "b"]
  console.log(fp.fill(0, 3, 2, Array(3))) // [2, 2, 2]
  console.log(fp.fill(1, 3, '*', [4, 6, 8, 10])) // [4, "*", "*", 10]
})("Lodash fp")

// https://simonsmith.io/dipping-a-toe-into-functional-js-with-lodash-fp
((title) => {
  console.log(title)
  //const fp = $wnd._.noConflict()
  const fp = _ //.noConflict()
  // sanitise
  const sanitise = fp.flow(fp.escape, fp.trim)
  console.log(sanitise('    <html>    ')) // &lt;html&gt;
  // has all items
  const DATA = {items: 45}
  const hasAllItems = fp.flow(fp.get('items'), fp.isEqual(45))
  console.log(hasAllItems(DATA)) // true
  // get bars
  const ITEMS = [
    {name: 'baz'},
    {name: 'foo'},
    {name: 'bar'},
    {name: 'bar'},
    {name: 'foo'},
  ];
  const getBars = fp.filter(fp.flow(fp.get('name'), fp.isEqual('bar')))
  console.log(getBars(ITEMS)) // [{name: 'bar'}, {name: 'bar'}]
})("dipping-a-toe-into-functional-js-with-lodash-fp")


