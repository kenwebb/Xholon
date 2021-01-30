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


