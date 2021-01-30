/*
  Ken Webb, 27 January 2021
  tutorial01.js
  Introduction to one-liners, and functional programming in JavaScript.
  
  Goal: to teach modules, IIEF, const, let, DATA, arrow functions, basic JavaScript, etc.
  
  A "one-liner" is a single line of JavaScript code, that can be run as is.
  Each one-liner is a piece of code you can copy into Dev Tools and then Run.
  
  Naming conventions:
  Use uppercase for DATA.  ex: const HELLO = "Bonjour"
  Use lowercase or CamelCase for functions and variables.  ex: const funk = (num) => num + num
  
  To use Node.js
  cd ~
  node
  then drag-in any of these one-liners, complete with the CRLF at the end of the line
  
  see also:
  https://developer.mozilla.org/en-US/docs/Web/JavaScript
  https://developer.mozilla.org/en-US/docs/Learn/Front-end_web_developer
*/

"Hello World"

7

4 + 3

console.log("Hello World")

console.log(7)

console.log(4 + 3)

// modules using Immediately Invoked Function Expression (IIEF) format  (() => {})()
// https://en.wikipedia.org/wiki/Immediately_invoked_function_expression
// http://benalman.com/news/2010/11/immediately-invoked-function-expression/
// https://flaviocopes.com/javascript-iife/

(() => {})()

(() => {console.log("Hello World")})()

(() => {console.log(7)})()

(() => {console.log(4 + 3)})()

((x, y) => {console.log(x + y)})("Hello", " World")

((x) => {console.log(x)})(7)

((x, y) => {console.log(x + y)})(4, 3)
((x, y) => {alert(x + y)})(4, 3)
javascript:((x) => alert(x + x))(2) // this works
javascript:((x) => $wnd.xh.root().println(x + x))(5) // this works

// arrays
[1, 2, 3, 4]

console.log([1, 2, 3, 5, 8, 13])

(() => {console.log([2, 4, 6, 8, "Who do we appreciate!"])})()

(() => {console.log([[1,2,3], [11,12,13]])})()

// modules that are made up of sequences of one-liners

(() => {
  const FOUR = 4
  const THREE = 3
  const add = (x, y) => x + y
  console.log(add(FOUR, THREE))
})()

