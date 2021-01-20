/*
Functions I can use to generate a new Xholon model/app.
Ken Webb
April 16, 2020
*/

// init :: Number -> [Object]
const init = n => [].length = n;

const inita = n => {
  var arr = [];
  arr.length = n;
  return arr; // [ <4 empty slots> ]
}

// init :: a → n → [a]
// Returns a fixed list of size n containing a specified identical value.
// See also times.
const init = n => R.repeat({}, n).map((item, index) => item.id = index);

R.repeat('hi', 5); //=> ['hi', 'hi', 'hi', 'hi', 'hi']

const obj = {};
const repeatedObjs = R.repeat(obj, 5); //=> [{}, {}, {}, {}, {}]
repeatedObjs[0] === repeatedObjs[1]; //=> true


// fill :: [Object] -> [Object]
const fill = xs => xs.map();
