/*
  Ken Webb, 25 January 2021
  
  based on the book: How to Bake PI, by Eugenia Cheng
  
*/

// MODULE 1
// symmetry of an equilateral triangle
// two types of symmetry: reflectional, rotational
// "the symmetry of an equilateral triangle is abstractly the same as the permutations of the numbers 1, 2, and 3
//  and the two situations can be studied at the same time"
// http://webgraphviz.com/  converts Graphviz DOT content into an SVG image
((title) => {
  console.log(title)
	
	const EQTRI = [1, 2, 3]
	const reflectM = triangle => triangle.reverse() // [3, 2, 1] uses mutation
	const reflect = triangle => [triangle[2], triangle[1], triangle[0]] // [3, 2, 1]
	const rotate0 = triangle => triangle // [1, 2, 3]
	const rotate120 = triangle => triangle.slice(2).concat(triangle.slice(0, 2)) // [3, 1, 2]
	const rotate240 = triangle => triangle.slice(1).concat(triangle.slice(0, 1)) // [2, 3, 1]
	const dot = triangle => `graph G {rankdir=LR; ${triangle[0]} -- ${triangle[1]} -- ${triangle[2]} -- ${triangle[0]}}`
	
	console.log(EQTRI)
	console.log(reflect(EQTRI))
	console.log(rotate0(EQTRI))
	console.log(rotate120(EQTRI))
	console.log(rotate240(EQTRI))
	
	console.log(dot(EQTRI)) // graph G {rankdir=LR; 1 -- 2 -- 3 -- 1}
	console.log(dot(reflect(EQTRI))) // graph G {rankdir=LR; 3 -- 2 -- 1 -- 3}
	console.log(dot(rotate0(EQTRI)))
	console.log(dot(rotate120(EQTRI))) // graph G {rankdir=LR; 3 -- 1 -- 2 -- 3}
	console.log(dot(rotate240(EQTRI))) // graph G {rankdir=LR; 2 -- 3 -- 1 -- 2}
	
})("Equilateral Triangle p.17")

// MODULE 2
// this works, but it needs improvement
((title) => {
  console.log(title)
	
	// my sort-of real-life non-mathy approach; this is a simple "machine" that answers the question
	const GUESS = 10 // guess different age values, to spiral in on a solution
	const ME = {age: GUESS}
	const FATHER = {age: 3 * ME.age}
	const trace = (me, father) => {console.log(me.age, father.age, father.age / me.age)}
  
	const incAndTest = (me, father, maxYearsTime) => {
	  me.age++
	  father.age++
	  trace(me, father);
	  return ((father.age / me.age > 2) && (maxYearsTime >= 0)) ? incAndTest(me, father, --maxYearsTime) : maxYearsTime
	}
	
	const solve = (me, father) => {
	  let maxYearsTime = GUESS;
	  trace(ME, FATHER);
	  const myAgeNow = me.age
	  maxYearsTime = incAndTest(me, father, GUESS-1)
	  console.log(`I am ${maxYearsTime === 0 ? myAgeNow : "unknown"} years old.`, maxYearsTime)
	}
	
	solve(ME, FATHER)
	
	// abstraction
	// y = 3x
	// y + 10 = 2(x + 10)
	
})("How old am I? p.39")

// example of recursive function:
//const coyote = (index, arr) => arr[index] ? arr[index] + coyote(index + 1, arr) : 0
//console.log(coyote(0, [1, 2, 3, 4, 5])) // 15


