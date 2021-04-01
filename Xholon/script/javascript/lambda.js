/*
Ken Webb, 20 Jan 2021

https://en.wikipedia.org/wiki/Lambda_calculus
Lambda calculus (also written as λ-calculus) is a formal system in mathematical logic for expressing computation based on function abstraction and application using variable binding and substitution. It is a universal model of computation that can be used to simulate any Turing machine. It was introduced by the mathematician Alonzo Church in the 1930s as part of his research into the foundations of mathematics.
*/

(() => {
  // The lambda calculus incorporates two simplifications
  // The first simplification is that the lambda calculus treats functions "anonymously", without giving them explicit names
  
  // square_sum
  // λx.λy.x*x + y*y
  (x, y) => x*x + y*y
  
  // id
  // λx.x
  x => x
  
  // The second simplification is that the lambda calculus only uses functions of a single input. An ordinary function that requires two inputs, for instance the square_sum function, can be reworked into an equivalent function that accepts a single input, and as output returns another function, that in turn accepts a single input.
  x => (y => x*x + y*y)
  
  // This method, known as currying, transforms a function that takes multiple arguments into a chain of functions each with a single argument. 
  console.log( (x => (y => x*x + y*y))(5)(2) ) // 29
  console.log( ((x => (y => x*x + y*y))(5))(2) ) // 29
  
  console.log( (x => x)(999) ) // 999
  
  //λx.x*x+2 // lambda notation
  x => x*x + 2 // JS
  
  // A = (λx.x*x)(5)
  console.log((x => x*x)(5))
  // or
  const a = x => x*x
  const A = a(5)
  console.log(A)
  
  // other references:
  // https://www.mscs.dal.ca/~selinger/papers/papers/lambdanotes.pdf
  // http://www.allisons.org/ll/FP/Lambda/Examples/
  // 
  
  // TODO write a function/lambda that transforms lambda notation into JavaScript
  
  // http://rosettacode.org/wiki/Y_combinator
  const Y = f => (x => x(x))(y => f(x => y(y)(x)))
  const fac = Y(f => n => n > 1 ? n * f(n-1) : 1)
  console.log(fac(5))
})()

// book: Greg Michaelson, An introduction to functional programming through lambda calculus. 1988
// BNF:
// <expression> ::= <name> | <function> | <application>
// <function> ::= λ<name>.<body>
// <body> ::= <expression>
// <application> ::= (<function expression> <argument expression>)
// <function expression> ::= <expression>
// <argument expression> ::= <expression>
(() => {
  // λx.x p, 17
  const id = x => x
  console.log(id(17)) // 17
  
  // 
  console.log((y => y)(19)) // 19
  
  // (λx.x λa.λb.b)
  // all of the following work
  console.log( (x => x)  (a => b => b)  (77)(78)  ) // 78
  console.log( ((x => x) (a => b => b)  (77)(78)) ) // 78
  console.log( ((x => x) (a => b => b)) (77)(78)  ) // 78
  console.log( ((x => x) (a => b => b)  (77))(78) ) // 78
  
  // λfirst.λsecond.first
  const funk = first => second => first
  console.log(funk("uno")("dos")) // uno
  
  // λf.λa.(f a)
  const funk2 = f => a => (f(a))
  const funk3 = funk2(x => x + 1)
  console.log(funk2(funk3)(13)) // 14
  
  // (λx.x λx.x)
  const id2 = x => x
  console.log(id2) // function id2(x)
  console.log(id2(id2)) // function id2(x)
  console.log(id2 === (id2(id2))) // true
  
  // identity operations
  const identity0a = n => n + 0
  const identity0s = n => n - 0
  const identity1m = n => n * 1
  const identity1d = n => n / 1
})()


