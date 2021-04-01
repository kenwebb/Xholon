/*
Ken Webb  26 January 2021
Dos Reis Abstract Algebra book
*/

// use Memoize to store latest value
// this does not work
((title) => {
  console.log(title)
  const INITIAL = 0
  const succ = () => {
    let value = INITIAL
    return value++
  }
  console.log(INITIAL)
  console.log(succ())
  console.log(succ())
})("Number Line p. 3")

// this works
((title) => {
  console.log(title)
  const INITIAL = 0
  const succ = num => num + 1
  console.log(INITIAL)
  console.log(succ(INITIAL))
  console.log(succ(succ(INITIAL)))
})("Number Line p. 3")

// memoize - this works
// the memoize function:
// - maintains/hides an internal value (cache) defined using "let"
// - returns a function that knows about the internal value (it knows because it's inside the same closure/scope as the internal value)
((title) => {
  console.log(title)
  const INITIAL = 0
  const memoize = () => {
    let cache = INITIAL;
    return () => ++cache;
  }
  const succ = memoize()
  console.log(INITIAL)
  console.log(succ())
  console.log(succ())
  console.log(succ())
  const NUMBERLINE = []
  for (var i = 0; i < 10; i++) {
    NUMBERLINE.push(succ())
  }
  console.log(NUMBERLINE)
  console.log(NUMBERLINE.join("_"))
})("Number Line p. 3")

// booleans
((title) => {
  console.log(title)
  const TRUE = true
  const FALSE = false
  const not = bool => !bool
  console.log(TRUE, FALSE, not(TRUE), not(FALSE))
})("simple booleans")

// the required property is a filter on the set of all positive integers
// const CSH = Array.from({length: cshsize}, (v, i) => makenode(i))
// https://stackoverflow.com/questions/3746725/how-to-create-an-array-containing-1-n
// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/yield
// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/function*
// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Generator
((title) => {
  console.log(title)
  const succ = a => a + 1
  const ONE = 1 // the first element that's a member of ℤ
  const _rpfilter = min => max => x => min <= x && x <= max
  const rpfilter = _rpfilter(1)(100)
  
  const z = one => Array.from({length: 110}, (v, i) => i + one)
  const Z = z(ONE)
  console.log(Z)

})("set-builder notation p.3  {x: x ∈ ℤ and 1 ≤ x ≤ 100}")

// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/function*
((title) => {
  console.log(title)
  const succ = a => a + 1
  const ONE = 1
  
  function* zMaker(initval) {
    var index = initval;
    while (true) {
      yield index
      index = succ(index)
    }
  }
  
  var gen = zMaker(ONE);
  
  console.log(gen.next().value); // 1
  console.log(gen.next().value); // 2
  console.log(gen.next().value); // 3
  console.log(gen.next().value); // 4
  // ...
})("test of: function* yield")

((title) => {
  console.log(title)
  const succ = a => a + 1
  const ONE = 1
  const _rpfilter = min => max => x => min <= x && x <= max
  const rpfilter = _rpfilter(1)(100)
  
  function* zMaker(initval) {
    let val = initval;
    while (true) {
      yield val
      val = succ(val)
    }
  }
  
  const gen = zMaker(ONE);
  
  const arr = []
  let val = gen.next().value
  while (rpfilter(val)) {
    arr.push(val)
    val = gen.next().value
  }
  console.log(arr)
})("test of: function* yield 2")

// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Generator#an_infinite_iterator
((title) => {
  function* infinite() {
    let index = 0;
    while (true) {
      yield index++;
    }
  }
  
  const generator = infinite(); // "Generator { }"
  
  console.log(generator.next().value); // 0
  console.log(generator.next()); // { value: 1, done: false }
  console.log(generator.next().value); // 2
  // ...
  generator.return(); // { value: undefined, done: true }
})("An infinite iterator")

// {x: x ∈ ℤ and 1 ≤ x ≤ 100}
((title, min, max) => {
  console.log(title)
  const succ = a => a + 1
  const _rpfilter = min => max => x => min <= x && x <= max
  const rpfilter = _rpfilter(min)(max)
  function* zMaker(initval) {
    let val = initval
    while (true) {
      yield val
      val = succ(val)
    }
  }
  const gen = zMaker(min) // use gen to generate ℤ+ (the set of all positive integers)
  const makearr = (gen, filter, arr, curr) => filter(curr) ? makearr(gen, filter, arr.concat(curr), gen.next().value) : arr
  const ARR = makearr(gen, rpfilter, [], gen.next().value)
  console.log(ARR) // [1,2,...,100]
})("test of: function* yield 3  {x: x ∈ ℤ and 1 ≤ x ≤ 100}", 1, 100)


