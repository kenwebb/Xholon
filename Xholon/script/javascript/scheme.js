// scheme.js
// Ken Webb
// January 14, 2020

// source: R. Ken Dybvig, The Sceme Programming Language, Second Ed.

// p. 12
/*
(define square
  (lambda (n)
    (* n n)))
(square 5)
*/
((title) => {
  const square = n => n * n
  console.log(square(5))
})("2.1 p.12")

// ((lamda (x) (+ x x)) (* 3 4))    24
((title) => {
  (x => x + x)(3 * 4) // 24
})("2.5 p.22")

((title) => {
  const double = x => x + x
  console.log(double(3 * 4)) // 24
  // 
  const ARRAY = [double(3 * 4), double(99 / 11), double(2 - 7)]
  console.log(ARRAY) // [ 24, 18, -10 ]
  // 
  const list = (...args) => args
  const LIST = list(double(3 * 4), double(99 / 11), double(2 - 7))
  console.log(LIST) // [ 24, 18, -10 ]
})("2.5 p.23")


