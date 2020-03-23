/**
 * xhprelude.js
 * (C) Ken Webb, MIT license
 * March 22, 2020
 * 
 * An implementation of functions from Haskell prelude module, for use within Xholon.
 * Requires ramda.
 * see: http://www.cse.chalmers.se/edu/course/TDA555/tourofprelude.html
 * see: http://berniepope.id.au/assets/files/haskell.tour.pdf
 * see: http://127.0.0.1:8888/wb/editwb.html?app=Haskell+-+Hutton+-+Binary+string+transmitter&src=lstr
 * see: http://127.0.0.1:8888/Xholon.html?app=Haskell+-+Hutton+-+Binary+string+transmitter&src=lstr&gui=clsc&jslib=ramda.min
 * 
 * @example
 * Usage:
 * 
 */

if (typeof window.xh == "undefined") {
  window.xh = {};
}

window.xh.RP = {};
var RP = window.xh.RP;

var TODO = null; // temprary

// abs :: Num a => a -> a
RP.abs = function(a) {
  return Math.abs(a);
}

// all :: (a -> Bool) -> [a] -> Bool
// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/every
RP.all = function(funk, arr) {
  return arr.every(funk);
}

// and :: [Bool] -> Bool
// takes the logical conjunction of a list of boolean values (see also or).
// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/Reduce
RP.and = function(arr) {
  return arr.reduce((acc, cur) => acc && cur, true);
}

// any :: (a -> Bool) -> [a] -> Bool
// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/some
RP.any = function(funk, arr) {
  return arr.some(funk);
}

// atan :: Floating a => a -> a
RP.atan = function(a) {
  return Math.atan(a);
}

// break :: (a -> Bool) -> [a] -> ([a],[a])
// given a predicate and a list, breaks the list into two lists (returned as a tuple) at the point where the predicate is first satisfied.
// If the predicate is never satisfied then the first element of the resulting tuple is the entire list and the second element is the empty list ([]).
// Ramda:  splitWhen  (a → Boolean) → [a] → [[a], [a]]
// R.splitWhen(R.equals(2), [1, 2, 3, 1, 2, 3]);   //=> [[1], [2, 3, 1, 2, 3]]
RP.break = function(funk, arr) {
  return R.splitWhen(funk, arr);
}

// ceiling :: (RealFrac a, Integral b) => a -> b
RP.ceiling = function(a) {
  return Math.ceil(a);
}

// chr :: Int -> Char
RP.chr = function(a) {
  return String.fromCharCode(a);
}

// compare :: Ord a => a -> a -> Ordering
// applied to to values of the same type which have an ordering defined on them, returns a value of type Ordering which will be:
//  EQ if the two values are equal;
//  GT if the first value is strictly greater than the second; and
//  LT if the first value is less than or equal to the second value.
RP.compare = function(a, b) {
  if (a == b) {return "EQ";}
  else if (a > b) {return "GT";}
  else if (a < b) {return "LT";}
}

// concat :: [[a]] -> [a]
RP.concat = function(arrs) {
  return arrs.flat(1);
}

// concatMap :: (a -> [b]) -> [a] -> [b]
// given a function which maps a value to a list, and a list of elements of the same type as the value,
// applies the function to the list and then concatenates the result (thus flattening the resulting list).
// concatMap show [1,2,3,4]  "1234"
// Note: [ "1", "2", "3", "4" ].join("") == "1234"
RP.concatMap = function(funk, arr) {
  return arr.flatMap(funk);
}

// const :: const :: a -> b -> a
// creates a constant valued function which always has the value of its first argument, regardless of the value of its second argument.
RP.const = function(a, b) {
  return a;
}

// cos :: Floating a => a -> a
RP.cos = function(a) {
  return Math.cos(a);
}

// digitToInt :: Char -> Int
RP.digitToInt = function() {
  return TODO;
}

// div :: Integral a => a -> a -> a
RP.div = function() {
  return TODO;
}

// drop :: Int -> [a] -> [a]
RP.drop = function() {
  return TODO;
}

// dropWhile :: (a -> Bool) -> [a] -> [a]
RP.dropWhile = function() {
  return TODO;
}

// elem :: Eq a => a -> [a] -> Bool
RP.elem = function() {
  return TODO;
}

// error :: String -> a
RP.error = function() {
  return TODO;
}

// even :: Integral a => a -> Bool
RP.even = function() {
  return TODO;
}

// exp :: Floating a => a -> a
RP.exp = function() {
  return TODO;
}

// filter :: (a -> Bool) -> [a] -> [a]
RP.filter = function() {
  return TODO;
}

// flip :: (a -> b -> c) -> b -> a -> c
RP.flip = function() {
  return TODO;
}

// floor :: (RealFrac a, Integral b) => a -> b
RP.floor = function() {
  return TODO;
}

// foldl :: (a -> b -> a) -> a -> [b] -> a
RP.foldl = function() {
  return TODO;
}

// foldl1 :: (a -> a -> a) -> [a] -> a
RP.foldl1 = function() {
  return TODO;
}

// foldr :: (a -> b -> b) -> b -> [a] -> b
RP.foldr = function() {
  return TODO;
}

// foldr1 :: (a -> a -> a) -> [a] -> a
RP.foldr1 = function() {
  return TODO;
}

// fromIntegral :: (Integral a, Num b) => a -> b
RP.fromIntegral = function() {
  return TODO;
}

// fst :: (a, b) -> a
RP.fst = function() {
  return TODO;
}

// gcd :: Integral a => a -> a -> a
RP.gcd = function() {
  return TODO;
}

// head :: [a] -> a
RP.head = function() {
  return TODO;
}

// id :: a -> a
RP.id = function() {
  return TODO;
}

// init :: [a] -> [a]
RP.init = function() {
  return TODO;
}

// isAlpha :: Char -> Bool
RP.isAlpha = function() {
  return TODO;
}

// isDigit :: Char -> Bool
RP.isDigit = function() {
  return TODO;
}

// isLower :: Char -> Bool
RP.isLower = function() {
  return TODO;
}

// isSpace :: Char -> Bool
RP.isSpace = function() {
  return TODO;
}

// isUpper :: Char -> Bool
RP.isUpper = function() {
  return TODO;
}

// iterate :: (a -> a) -> a -> [a]
RP.iterate = function() {
  return TODO;
}

// last :: [a] -> a
RP.last = function() {
  return TODO;
}

// lcm :: Integral a => a -> a -> a
RP.lcm = function() {
  return TODO;
}

// length :: [a] -> Int
RP.length = function() {
  return TODO;
}

// lines :: String -> [String]
RP.lines = function() {
  return TODO;
}

// log :: Floating a => a -> a
RP.log = function() {
  return TODO;
}

// map :: (a -> b) -> [a] -> [b]
RP.map = function() {
  return TODO;
}

// max :: Ord a => a -> a -> a
RP.max = function() {
  return TODO;
}

// maximum :: Ord a => [a] -> a
RP.maximum = function() {
  return TODO;
}

// min :: Ord a => a -> a -> a
RP.min = function() {
  return TODO;
}

// minimum :: Ord a => [a] -> a
RP.minimum = function() {
  return TODO;
}

// mod :: Integral a => a -> a -> a
RP.mod = function() {
  return TODO;
}

// not :: Bool -> Bool
RP.not = function() {
  return TODO;
}

// notElem :: Eq a => a -> [a] -> Bool
RP.notElem = function() {
  return TODO;
}

// null :: [a] -> Bool
RP.null = function() {
  return TODO;
}

// odd :: Integral a => a -> Bool
RP.odd = function() {
  return TODO;
}

// or :: [Bool] -> Bool
RP.or = function() {
  return TODO;
}

// ord :: Char -> Int
RP.ord = function() {
  return TODO;
}

// pi :: Floating a => a
RP.pi = function() {
  return TODO;
}

// pred :: Enum a => a -> a
RP.pred = function() {
  return TODO;
}

// print :: Show a => a -> IO ()
RP.print = function() {
  return TODO;
}

// putStr :: String -> IO ()
RP.putStr = function() {
  return TODO;
}

// product :: Num a => [a] -> a
RP.product = function() {
  return TODO;
}

// quot :: Integral a => a -> a -> a
RP.quot = function() {
  return TODO;
}

// rem :: Integral a => a -> a -> a
RP.rem = function() {
  return TODO;
}

// repeat :: a -> [a]
RP.repeat = function() {
  return TODO;
}

// replicate :: Int -> a -> [a]
RP.replicate = function() {
  return TODO;
}

// reverse :: [a] -> [a]
RP.reverse = function() {
  return TODO;
}

// round :: (RealFrac a, Integral b) => a -> b
RP.round = function() {
  return TODO;
}

// show :: Show a => a -> String
RP.show = function() {
  return TODO;
}

// sin :: Floating a => a -> a
RP.sin = function() {
  return TODO;
}

// snd :: (a, b) -> b
RP.snd = function() {
  return TODO;
}

// sort :: Ord a => [a] -> [a]
RP.sort = function() {
  return TODO;
}

// span :: (a -> Bool) -> [a] -> ([a],[a])
RP.span = function() {
  return TODO;
}

// splitAt :: Int -> [a] -> ([a],[a])
RP.splitAt = function() {
  return TODO;
}

// sqrt :: Floating a => a -> a
RP.sqrt = function() {
  return TODO;
}

// subtract :: Num a => a -> a -> a
RP.subtract = function() {
  return TODO;
}

// succ :: Enum a => a -> a
RP.succ = function() {
  return TODO;
}

// sum :: Num a => [a] -> a
RP.sum = function() {
  return TODO;
}

// tail :: [a] -> [a]
RP.tail = function() {
  return TODO;
}

// take :: Int -> [a] -> [a]
RP.take = function() {
  return TODO;
}

// takewhile :: (a -> Bool) -> [a] -> [a]
RP.takeWhile = function() {
  return TODO;
}

// tan :: Floating a => a -> a
RP.tan = function() {
  return TODO;
}

// toLower :: Char -> Char
RP.toLower = function() {
  return TODO;
}

// toUpper :: Char -> Char
RP.toUpper = function() {
  return TODO;
}

// truncate :: (RealFrac a, Integral b) => a -> b
RP.truncate = function() {
  return TODO;
}

// undefined :: a
RP.undefined = function() {
  return TODO;
}

// unlines :: [String] -> String
RP.unlines = function() {
  return TODO;
}

// until :: (a -> Bool) -> (a -> a) -> a -> a
RP.until = function() {
  return TODO;
}

// unwords :: [String] -> String
RP.unwords = function() {
  return TODO;
}

// words :: String -> [String]
RP.words = function() {
  return TODO;
}

// zip :: [a] -> [b] -> [(a,b)]
RP.zip = function() {
  return TODO;
}

// zipWith :: (a -> b -> c) -> [a] -> [b] -> [c]
RP.zipWith = function() {
  return TODO;
}

RP.tests = function() {
  console.log(RP.abs(33)); // 33
  console.log(RP.abs(-33)); // 33
  console.log(RP.all((x => x < 11), [1,2,3])); // true
  console.log(RP.all((x => x < 11), [1,2,3,11])); // false
  console.log(RP.and([true,true])); // true
  console.log(RP.and([true,true,false])); // false
  console.log(RP.and([true,true,false,true])); // false
  console.log(RP.and([])); // true
  console.log(RP.any((x => x < 11), [1,2,3,11])); // true
  console.log(RP.atan(1)); // 0.7853981633974483
  //console.log(RP.break((x => x == 2), [1, 2, 3, 1, 2, 3])); // [[1], [2, 3, 1, 2, 3]] ???
  console.log(RP.ceiling(33.4)); // 34
  console.log(RP.chr(65)); // "A"
  console.log(RP.compare('x', 'x')); // "EQ"
  console.log(RP.compare("zebra", "ardvark")); // "GT"
  console.log(RP.compare(13, 27)); // "LT"
  console.log(RP.concat([[1,2,3], [4], [], [5,6,7,8]])); // [1, 2, 3, 4, 5, 6, 7, 8]
  console.log(RP.concatMap((n => Number(n).toString()), [1,2,3,4])); // [ "1", "2", "3", "4" ]  TODO should be "1234"; but consider the diffs betwteen JS and Haskell
}

RP.tests();

