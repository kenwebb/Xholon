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
 * For testing, see:
 *  http://127.0.0.1:8888/wb/editwb.html?app=Haskell+prelude+-+xhprelude.js&src=lstr
 *  http://127.0.0.1:8888/Xholon.html?app=Haskell prelude - xhprelude.js&src=lstr&gui=clsc&jslib=ramda.min,xhprelude
 * @example
 * Usage:
 * 
 */

if (typeof window.xh == "undefined") {
  window.xh = {};
}

window.xh.RP = {};
var RP = window.xh.RP;

var TODO = null; // temporary

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
  return arr.reduceRight((acc, cur) => acc && cur, true);
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
RP.digitToInt = function(a) {
  return Number(a);
}

// div :: Integral a => a -> a -> a
// computes the integer division of its integral arguments.
RP.div = function(a, b) {
  return Math.floor(a/b);
}

// drop :: Int -> [a] -> [a]
RP.drop = function(n, arr) {
  return R.drop(n, arr);
}

// dropWhile :: (a -> Bool) -> [a] -> [a]
RP.dropWhile = function(funk, arr) {
  return R.dropWhile(funk, arr);
}

// elem :: Eq a => a -> [a] -> Bool
RP.elem = function(a, arr) {
  return arr.includes(a);
}

// error :: String -> a
RP.error = function(str) {
  return str; // TODO ???
}

// even :: Integral a => a -> Bool
RP.even = function(a) {
  return a % 2 == 0;
}

// exp :: Floating a => a -> a
RP.exp = function(a) {
  return Math.exp(a);
}

// filter :: (a -> Bool) -> [a] -> [a]
RP.filter = function(funk, arr) {
  return arr.filter(funk);
}

// flip :: (a -> b -> c) -> b -> a -> c
RP.flip = function(funk, a, b, ...c) {
  return R.flip(funk, b, a, ...c);
}

// floor :: (RealFrac a, Integral b) => a -> b
RP.floor = function(a) {
  return Math.floor(a);
}

// foldl :: (a -> b -> a) -> a -> [b] -> a
// Ramda: reduce ((a, b) → a) → a → [b] → a
RP.foldl = function(funk, init, arr) {
  return R.reduce(funk, init, arr);
  // or use Array.prototype.reduce()
}

// foldl1 :: (a -> a -> a) -> [a] -> a
// folds left over non--empty lists.
RP.foldl1 = function(funk, arr) {
  return R.reduce(funk, R.head(arr), R.tail(arr));
}

// foldr :: (a -> b -> b) -> b -> [a] -> b
// Ramda: reduceRight ((a, b) → b) → b → [a] → b
// bin2int :: [Bit] -> Int
// bin2int = bits => R.reduceRight((x, y) => x + 2*y, 0, bits)
// or use Array.prototype.reduceRight()
RP.foldr = function(funk, init, arr) {
  return R.reduceRight(funk, init, arr);
}

// foldr1 :: (a -> a -> a) -> [a] -> a
RP.foldr1 = function(funk, arr) {
  return R.reduceRight(funk, R.head(arr), R.tail(arr));
}

// fromIntegral :: (Integral a, Num b) => a -> b
RP.fromIntegral = function(a) {
  return a;
}

// fst :: (a, b) -> a
RP.fst = function(a, b) {
  return a;
}

// gcd :: Integral a => a -> a -> a
// returns the greatest common divisor between its two integral arguments.
// wikipedia Greatest common divisor - Euclid's algorithm:
//  gcd(a,0) = a
//  gcd(a,b) = gcd(b, a mod b).
RP.gcd = function(a, b) {
  return (b == 0) ? Math.abs(a) : RP.gcd(b, a % b);
}

// head :: [a] -> a
RP.head = function(arr) {
  return R.head(arr);
}

// id :: a -> a
RP.id = function(a) {
  return a;
}

// init :: [a] -> [a]
RP.init = function(arr) {
  return R.init(arr);
}

// isAlpha :: Char -> Bool
// applied to a character argument, returns True if the character is alphabetic, and False otherwise.
RP.isAlpha = function(c) {
  return RP.isUpper(c) || RP.isLower(c);
}

// isDigit :: Char -> Bool
// applied to a character argument, returns True if the character is a numeral, and False otherwise.
RP.isDigit = function(c) {
  return c >= '0' && c <= '9';
}

// isLower :: Char -> Bool
// applied to a character argument, returns True if the character is a lower case alphabetic, and False otherwise.
RP.isLower = function(c) {
  return c >= 'a' && c <= 'z';
}

// isSpace :: Char -> Bool
// returns True if its character argument is a whitespace character and False otherwise.
RP.isSpace = function(c) {
  return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\f' || c == '\v';
}

// isUpper :: Char -> Bool
// applied to a character argument, returns True if the character is an upper case alphabetic, and False otherwise.
RP.isUpper = function(c) {
  return c >= 'A' && c <= 'Z';
}

// iterate :: (a -> a) -> a -> [a]
// iterate~f~x returns the infinite list [x,~f(x),~f(f(x)),~...].
// Ramda has nothing obvious that can help with this ?
RP.iterate = function(funk, a) {
  // here's a simple placeholder
  var val = a;
  var arr = [];
  for (var i = 0; i < 10; i++) {
    arr.push(val);
    val = funk(val, a);
  }
  return arr;
}

// last :: [a] -> a
// applied to a non--empty list, returns the last element of the list.
RP.last = function(arr) {
  return R.last(arr);
}

// lcm :: Integral a => a -> a -> a
// returns the least common multiple of its two integral arguments.
// see wikipedia
RP.lcm = function(a, b) {
  return Math.abs(a * b) / RP.gcd(a, b);
}

// length :: [a] -> Int
// returns the number of elements in a finite list.
RP.length = function(arr) {
  return arr.length;
}

// lines :: String -> [String]
// applied to a list of characters containing newlines, returns a list of lists by breaking the original list
// into lines using the newline character as a delimiter. The newline characters are removed from the result.
RP.lines = function(str) {
  return str.trim().split("\n");
}

// log :: Floating a => a -> a
// returns the natural logarithm of its argument.
RP.log = function(a) {
  return Math.log(a);
}

// map :: (a -> b) -> [a] -> [b]
// given a function, and a list of any type, returns a list where each element is the result of
// applying the function to the corresponding element in the input list.
RP.map = function(funk, arr) {
  return R.map(funk, arr);
}

// max :: Ord a => a -> a -> a
// applied to two values of the same type which have an ordering defined upon them, returns the maximum of the two elements according to the operator >=.
RP.max = function(a, b) {
  return R.max(a, b);
}

// maximum :: Ord a => [a] -> a
// applied to a non--empty list whose elements have an ordering defined upon them, returns the maximum element of the list.
RP.maximum = function(arr) {
  return RP.foldl1(RP.max, arr);
}

// min :: Ord a => a -> a -> a
// applied to two values of the same type which have an ordering defined upon them, returns the minimum of the two elements according to the operator <=.
RP.min = function(a, b) {
  return R.min(a, b);
}

// minimum :: Ord a => [a] -> a
// applied to a non--empty list whose elements have an ordering defined upon them, returns the minimum element of the list.
RP.minimum = function(arr) {
  return RP.foldl1(RP.min, arr);
}

// mod :: Integral a => a -> a -> a
// returns the modulus of its two arguments.
RP.mod = function(a, b) {
  return a % b;
}

// not :: Bool -> Bool
// returns the logical negation of its boolean argument.
RP.not = function(b) {
  return !b;
}

// notElem :: Eq a => a -> [a] -> Bool
// returns True if its first argument is not an element of the list as its second argument.
RP.notElem = function(a, arr) {
  return !arr.includes(a);
}

// null :: [a] -> Bool
// returns True if its argument is the empty list ([]) and False otherwise.
RP.null = function(arr) {
  return R.isEmpty(arr);
}

// odd :: Integral a => a -> Bool
// applied to an integral argument, returns True if the argument is odd, and False otherwise.
RP.odd = function(a) {
  return !RP.even(a);
}

// or :: [Bool] -> Bool
// applied to a list of boolean values, returns their logical disjunction (see also and).
RP.or = function(arr) {
  return arr.reduceRight((acc, cur) => acc || cur, false);
}

// ord :: Char -> Int
// applied to a character, returns its ascii code as an integer.
RP.ord = function(c) {
  return c.charCodeAt();
}

// pi :: Floating a => a
RP.pi = function() {
  return Math.PI;
}

// pred :: Enum a => a -> a
// applied to a value of an enumerated type returns the predecessor (previous value in the enumeration) of its argument.
// If its argument is the first value in an enumeration an error will occur.
RP.pred = function(a) {
  // for now, asssume that a is an integer
  return --a;
}

// print :: Show a => a -> IO ()
// takes a value of any type in the Show class as an argument and returns an I/O action as a result.
// A side-effect of applying print is that it causes its argument value to be printed to the screen.
RP.print = function(a) {
  console.log(RP.show(a));
}

// putStr :: String -> IO ()
// takes a string as an argument and returns an I/O action as a result.
// A side-effect of applying putStr is that it causes its argument string to be printed to the screen.
RP.putStr = function(str) {
  console.log(str);
}

// product :: Num a => [a] -> a
// applied to a list of numbers, returns their product.
RP.product = function(arr) {
  return R.product(arr);
}

// quot :: Integral a => a -> a -> a
// returns the quotient after dividing the its first integral argument by its second integral argument.
RP.quot = function(a, b) {
  return Math.floor(a/b);
}

// rem :: Integral a => a -> a -> a
// returns the remainder after dividing its first integral argument by its second integral argument.
RP.rem = function(a, b) {
  return a % b;
}

// repeat :: a -> [a]
// given a value, returns an infinite list of elements the same as the value.
// Note: I'm requiring a second argument with a finite value for infinity
RP.repeat = function(a, inf) {
  return R.repeat(a, inf);
}

// replicate :: Int -> a -> [a]
// given an integer (positive or zero) and a value, returns a list containing the specified number of instances of that value.
RP.replicate = function(n, a) {
  return R.repeat(a, n);
}

// reverse :: [a] -> [a]
// applied to a finite list of any type, returns a list of the same elements in reverse order.
RP.reverse = function(arr) {
  return R.reverse(arr);
}

// round :: (RealFrac a, Integral b) => a -> b
RP.round = function(a) {
  return Math.round(a);
}

// show :: Show a => a -> String
// converts a value (which must be a member of the Show class), to its string representation.
RP.show = function(a) {
  return String(a);
}

// sin :: Floating a => a -> a
RP.sin = function(a) {
  return Math.sin(a);
}

// snd :: (a, b) -> b
RP.snd = function(a, b) {
  return b;
}

// sort :: Ord a => [a] -> [a]
// sorts its argument list in ascending order. The items in the list must be in the class Ord.
RP.sort = function(arr) {
  return arr.sort();
}

// span :: (a -> Bool) -> [a] -> ([a],[a])
// given a predicate and a list, splits the list into two lists (returned as a tuple) such that
// elements in the first list are taken from the head of the list while the predicate is satisfied,
// and elements in the second list are the remaining elements from the list once the predicate is not satisfied.
RP.span = function(funk, arr) {
  return R.splitWhen(funk, arr);
}

// splitAt :: Int -> [a] -> ([a],[a])
// given an integer (positive or zero) and a list, splits the list into two lists (returned as a tuple) at the position corresponding to the given integer. 
// If the integer is greater than the length of the list,
// it returns a tuple containing the entire list as its first element and the empty list as its second element.
RP.splitAt = function(n, arr) {
  return R.splitAt(n, arr);
}

// sqrt :: Floating a => a -> a
RP.sqrt = function(a) {
  return Math.sqrt(a);
}

// subtract :: Num a => a -> a -> a
// subtracts its first argument from its second argument.
RP.subtract = function(a, b) {
  return b - a;
}

// succ :: Enum a => a -> a
RP.succ = function(a) {
  // for now, asssume that a is an integer
  return ++a;
}

// sum :: Num a => [a] -> a
// computes the sum of a finite list of numbers.
RP.sum = function(arr) {
  return R.sum(arr);
}

// tail :: [a] -> [a]
// applied to a non--empty list, returns the list without its first element.
RP.tail = function(arr) {
  return R.tail(arr);
}

// take :: Int -> [a] -> [a]
// applied to an integer (positive or zero) and a list, returns the specified number of elements from the front of the list.
// If the list has less than the required number of elements, take returns the entire list.
RP.take = function(n, arr) {
  return R.take(n, arr);
}

// takewhile :: (a -> Bool) -> [a] -> [a]
// applied to a predicate and a list, returns a list containing elements from the front of the list while the predicate is satisfied.
RP.takeWhile = function(funk, arr) {
  return R.takeWhile(funk, arr);
}

// tan :: Floating a => a -> a
RP.tan = function(a) {
  return Math.tan(a);
}

// toLower :: Char -> Char
// converts an uppercase alphabetic character to a lowercase alphabetic character.
// If this function is applied to an argument which is not uppercase the result will be the same as the argument unchanged.
RP.toLower = function(c) {
  return c.toLowerCase();
}

// toUpper :: Char -> Char
RP.toUpper = function(c) {
  return c.toUpperCase();
}

// truncate :: (RealFrac a, Integral b) => a -> b
// drops the fractional part of a floating point number, returning only the integral part.
RP.truncate = function(a) {
  return Math.trunc(a);
}

// undefined :: a
// an undefined value. It is a member of every type.
RP.undefined = function() {
  return undefined;
}

// unlines :: [String] -> String
// converts a list of strings into a single string, placing a newline character between each of them.
// It is the converse of the function lines.
RP.unlines = function(arr) {
  return arr.join("\n") + "\n";
}

// until :: (a -> Bool) -> (a -> a) -> a -> a
// given a predicate, a unary function and a value, it recursively re--applies the function to the value until the predicate is satisfied.
// If the predicate is never satisfied until will not terminate.
RP.until = function(funk, ufunk, a) {
  return R.until(funk, ufunk, a);
}

// unwords :: [String] -> String
// concatenates a list of strings into a single string, placing a single space between each of them.
RP.unwords = function(arr) {
  return arr.join(" ");
}

// words :: String -> [String]
// breaks its argument string into a list of words such that each word is delimited by one or more whitespace characters.
RP.words = function(str) {
  return str.trim().split(/\s/);
}

// zip :: [a] -> [b] -> [(a,b)]
// applied to two lists, returns a list of pairs which are formed by tupling together corresponding elements of the given lists.
// If the two lists are of different length, the length of the resulting list is that of the shortest.
RP.zip = function(arr, brr) {
  return R.zip(arr, brr);
}

// zipWith :: (a -> b -> c) -> [a] -> [b] -> [c]
// applied to a binary function and two lists,
// returns a list containing elements formed be applying the function to corresponding elements in the lists.
RP.zipWith = function(funk, arr, brr) {
  return R.zipWith(funk, arr, brr);
}

/*RP.myTests = function() {
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
  console.log(RP.break((x => x == 2), [1, 2, 3, 1, 2, 3])); // [[1], [2, 3, 1, 2, 3]]
  console.log(RP.ceiling(33.4)); // 34
  console.log(RP.chr(65)); // "A"
  console.log(RP.compare('x', 'x')); // "EQ"
  console.log(RP.compare("zebra", "ardvark")); // "GT"
  console.log(RP.compare(13, 27)); // "LT"
  console.log(RP.concat([[1,2,3], [4], [], [5,6,7,8]])); // [1, 2, 3, 4, 5, 6, 7, 8]
  console.log(RP.concatMap((n => Number(n).toString()), [1,2,3,4])); // [ "1", "2", "3", "4" ]  TODO should be "1234"; but consider the diffs betwteen JS and Haskell
}*/
//RP.myTests();

// tests from http://www.cse.chalmers.se/edu/course/TDA555/tourofprelude.html
RP.haskPreludeTests = function() {
  console.log(RP.abs(-3)); // 3
  console.log(RP.all((x => x < 11), [1,2,3,4,5,6,7,8,9,10])); // true
  console.log(RP.all(RP.isDigit, "123abc".split(""))); // false
  console.log(RP.and([true, true, false, true])); // false
  console.log(RP.and([true, true, true, true])); // true
  console.log(RP.and([])); // true
  console.log(RP.any((x => x < 11), [1,2,3,4,5,6,7,8,9,10])); // true
  console.log(RP.any(RP.isDigit, "123abc".split(""))); // true
  console.log(RP.any(RP.isDigit, "alphabetics".split(""))); // false
  console.log(RP.atan(RP.pi())); // 1.26263
  console.log(RP.break(RP.isSpace, "hello there fred")); // ("hello", " there fred")
  console.log(RP.break(RP.isDigit, "no digits here")); // ("no digits here","")
  console.log(RP.ceiling(3.8)); // 4
  console.log(RP.ceiling(-3.8)); // -3
  console.log(RP.chr(65)); // 'A'
  console.log(RP.ord(RP.chr(65)) == 65); // true
  console.log(RP.compare("aardvark", "zebra")); // LT
  console.log(RP.concat([[1,2,3], [4], [], [5,6,7,8]])); // [1, 2, 3, 4, 5, 6, 7, 8]
  console.log(RP.concatMap(RP.show, [1,2,3,4]).join("")); // "1234"  [ "1", "2", "3", "4" ]
  console.log(RP.const(12, "lucky")); // 12
  console.log(RP.cos(RP.pi())); // -1.0
  console.log(RP.cos(RP.pi() / 2)); // -4.37114e-08 6.123233995736766e-17
  console.log(RP.digitToInt('3')); // 3
  console.log(RP.div(16, 9)); // 1
  console.log(RP.div(-12, 5)); // -3
  console.log(RP.drop(3, [1,2,3,4,5,6,7,8,9,10])); // [4, 5, 6, 7, 8, 9, 10]
  console.log(RP.drop(4, "abc")); // "" <empty string>
  console.log(RP.dropWhile((x => x < 5), [1,2,3,4,5,6,7,8,9,10])); // [5,6,7,8,9,10]
  console.log(RP.elem(5, [1,2,3,4,5,6,7,8,9,10])); // true
  console.log(RP.elem("rat", ["fat", "cat", "sat", "flat"])); // false
  console.log(RP.error("this is an error message")); // this is an error message
  console.log(RP.even(2)); // true
  console.log(RP.even(11 * 3)); // false
  console.log(RP.exp(1)); // 2.71828
  console.log(RP.filter(RP.isDigit, "fat123cat456".split("")).join("")); // "123456"
  console.log(RP.flip(RP.elem)([1,2,3,4,5,6,7,8,9,10], 5)); // true
  console.log(RP.floor(3.8)); // 3
  console.log(RP.floor(-3.8)); // -4
  console.log(RP.foldl(R.add, 0, [1,2,3,4,5,6,7,8,9,10])); // 55
  
  // TODO foldl (flip (:)) [] [1..10] // [10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
  console.log(RP.foldl(R.prepend, [1,2,3,4,5,6,7,8,9,10], [])); // OK [1,2,3,4,5,6,7,8,9,10]
  console.log(RP.foldl(R.append, [1,2,3,4,5,6,7,8,9,10], [])); // OK [1,2,3,4,5,6,7,8,9,10]
  //console.log(RP.foldl(RP.flip(R.prepend)([], [1,2,3,4,5,6,7,8,9,10]))); // ??? NO
  
  console.log(RP.foldl1(R.max, [1,10,5,2,-1])); // 10
  console.log(RP.foldr(R.concat, "", ["con", "cat", "en", "ate"])); // "concatenate"
  console.log(RP.foldr1(R.multiply, [1,2,3,4,5,6,7,8,9,10])); // 3628800
  console.log(RP.fromIntegral(10000000000)); // 1.0e+10 10000000000
  console.log(RP.fst(1, 2)); // 1
  console.log(RP.fst(1.11, 2.22)); // 1.11
  console.log(RP.fst("hello", "world")); // "hello"
  console.log(RP.gcd(2, 10)); // 2
  console.log(RP.gcd(-7, 13)); // 1
  console.log(RP.head([1,2,3,4,5,6,7,8,9,10])); // 1
  console.log(RP.head(["this", "and", "that"])); // "this"
  console.log(RP.id(12)); // 12
  console.log(RP.id(RP.id("fred"))); // "fred"
  console.log(RP.map(RP.id, [1,2,3,4,5,6,7,8,9,10]).toString() == [1,2,3,4,5,6,7,8,9,10].toString()); // true
  console.log(RP.init([1,2,3,4,5,6,7,8,9,10])); // [1,2,3,4,5,6,7,8,9]
  console.log(RP.isAlpha('a')); // true
  console.log(RP.isAlpha('1')); // false
  console.log(RP.isDigit('1')); // true
  console.log(RP.isDigit('a')); // false
  console.log(RP.isLower('a')); // true
  console.log(RP.isLower('A')); // false
  console.log(RP.isLower('1')); // false
  console.log(RP.isSpace(' ')); // true
  console.log(RP.dropWhile(RP.isSpace, "   \nhello  \n")); // "hello  \n"
  console.log(RP.isUpper('A')); // true
  console.log(RP.isUpper('a')); // false
  console.log(RP.isUpper('1')); // false
  console.log(RP.iterate(R.add, 1)); // [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ]
  console.log(RP.iterate(R.concat, "*")); // [ "*", "**", "***", "****", "*****", "******", "*******", "********", "*********", "**********" ]
  console.log(RP.last([1,2,3,4,5,6,7,8,9,10])); // 10
  console.log(RP.lcm(2, 10)); // 10
  console.log(RP.lcm(2, 11)); // 22
  console.log(RP.length([1,2,3,4,5,6,7,8,9,10])); // 10
  console.log(RP.lines("hello world\nit's me,\neric\n")); // ["hello world", "it's me,", "eric"]
  console.log(RP.log(1)); // 0.0
  console.log(RP.log(3.2)); // 1.6315
  console.log(RP.map(RP.sqrt, [1,2,3,4,5])); // [1.0, 1.41421, 1.73205, 2.0, 2.23607]
  console.log(RP.max(1, 2)); // 2
  console.log(RP.maximum([-10, 0 , 5, 22, 13])); // 22
  console.log(RP.min(1, 2)); // 1
  console.log(RP.minimum([-10, 0 , 5, 22, 13])); // -10
  console.log(RP.mod(16, 9)); // 7
  console.log(RP.not(3 == 4)); // true
  console.log(RP.not(10 > 2)); // false
  console.log(RP.notElem(3, [1,2,3])); // false
  console.log(RP.notElem(4, [1,2,3])); // true
  console.log(RP.null([])); // true
  console.log(RP.null(RP.take(3, [1,2,3,4,5,6,7,8,9,10]))); // false
  console.log(RP.odd(1)); // true
  console.log(RP.odd(2 * 12)); // false
  console.log(RP.or([false, false, true, false])); // true
  console.log(RP.or([])); // false
  console.log(RP.ord('A')); // 65
  console.log(RP.chr(RP.ord('A')) == 'A'); // true
  console.log(RP.pi()); // 3.14159
  console.log(RP.cos(RP.pi())); // -1.0
  console.log(RP.pred(1)); // 0
  //console.log(RP.pred(true)); // false
  console.log(RP.print(RP.pi())); // 3.14159.....
  console.log(RP.putStr("Hello World\nI'm here!")); // Hello World\nI'm here!
  console.log(RP.product([1,2,3,4,5,6,7,8,9,10])); // 3628800
  console.log(RP.quot(16, 8)); // 2
  console.log(RP.quot(16, 9)); // 1
  console.log(RP.rem(16, 8)); // 0
  console.log(RP.rem(16, 9)); // 7
  console.log(RP.repeat(12, 3)); // [12, 12, 12]
  console.log(RP.replicate(3, "apples")); // ["apples", "apples", "apples"]);
  console.log(RP.reverse([1,2,3,4,5,6,7,8,9,10])); // [10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
  console.log(RP.round(3.2)); // 3
  console.log(RP.round(3.5)); // 4
  console.log(RP.round(-3.2)); // -3
  console.log(RP.print("six plus two equals " + RP.show(6 + 2))); // "six plus two equals 8"
  console.log(RP.sin(RP.pi() / 2)); // 1.0
  console.log((RP.sin(RP.pi()) ^ 2) + (RP.cos(RP.pi()) ^ 2)); // -1  1.0  1.3817732906760363
  console.log(RP.snd(1, 2)); // 2
  console.log(RP.snd(1.11, 2.22)); // 2.22
  console.log(RP.snd("hello", "world")); // "world"
  console.log(RP.snd("harry", 3)); // 3
  console.log(RP.sort([1, 4, -2, 8, 11, 0])); // [-2,0,1,4,8,11]
  console.log(RP.span(RP.isDigit, "123abc456")); // ("123", "abc456") TODO
  console.log(RP.splitAt(3, [1,2,3,4,5,6,7,8,9,10])); // ([1, 2, 3], [4, 5, 6, 7, 8, 9, 10])
  console.log(RP.splitAt(5, "abc")); // ("abc, "")
  console.log(RP.sqrt(16)); // 4
  console.log(RP.subtract(7, 10)); // 3
  //console.log(RP.succ('a')); // 'b'
  //console.log(RP.succ(false)); // true
  console.log(RP.succ(1)); // 2
  console.log(RP.sum([1,2,3,4,5,6,7,8,9,10])); // 55
  console.log(RP.tail([1,2,3])); // [2,3]
  console.log(RP.tail("hugs")); // "ugs"
  console.log(RP.take(4, "goodbye")); // "good"
  console.log(RP.takeWhile((x => x < 5), [1, 2, 3, 10, 4, 2])); // [1,2,3]
  console.log(RP.tan(RP.pi() / 4)); // 1.0
  console.log(RP.toLower('A')); // 'a'
  console.log(RP.toLower('3')); // '3'
  console.log(RP.toUpper('a')); // 'A'
  console.log(RP.toUpper('3')); // '3'
  console.log(RP.truncate(3.2)); // 3
  console.log(RP.truncate(-3.2)); // -3
  console.log(RP.undefined()); // 
  console.log(RP.unlines(["hello world", "it's me,", "eric"])); // "hello world\nit's me,\neric\n"
  console.log(RP.until((x => x > 1000), (y => y * 2), 1)); // 1024
  console.log(RP.unwords(["the", "quick", "brown", "fox"])); // "the quick brown fox"
  console.log(RP.words("the quick brown\n\nfox")); // ["the", "quick", "brown", "fox"]
  console.log(RP.zip([1,2,3,4,5,6], "abcd")); // [(1, 'a'), (2, 'b'), (3, 'c'), (4, 'd')]
  console.log(RP.zipWith(R.add, [1,2,3,4,5], [6,7,8,9,10])); // [7, 9, 11, 13, 15] Note: can't use RP.add
}
RP.haskPreludeTests();

