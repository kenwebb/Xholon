/*
  avasStick01.js
  Ken Webb, 17 Feb 2021
  Two Avatars and a Stick
  
  see workbook: Two avatars and a stick in a 1d gridcell cycle
  https://www.primordion.com/Xholon/gwt/Xholon.html?app=915111ff5ecefae96a5699d3e09002c2&src=gist&gui=none
  https://www.primordion.com/Xholon/gwt/wb/editwb.html?app=915111ff5ecefae96a5699d3e09002c2&src=gist
  https://gist.github.com/kenwebb/915111ff5ecefae96a5699d3e09002c2
  
  In this version, I use:
   1. a JavaScript Array (the data structure)
   2. JavaScript arrow functions
  The approach is external, in that neither Avatar has any way of knowing where it's located.
  
  TODO
  - maybe GRIDCELL should be an empty {}
  - avas and stick would be properties of each GRIDCELL
  
  TODO
  It makes sense to a use Register Machines, Turing Machines, and Lamdas in this first initial version
  Start with two arrays: a data array, and a code array
*/

// String version
(() => {
  const SIZE = 20
  const GRIDCELL = ""
  const AVATARA = "A"
  const AVATARB = "B"
  const STICK = "S"
  const SPACE = new Array(SIZE).fill(GRIDCELL)
  SPACE[4] = AVATARA
  SPACE[0] = AVATARB
  SPACE[16] = STICK
  console.log(SPACE)
  
  // OR
  const STR = `${AVATARB}___${AVATARA}_____${STICK}___`
})()

// Object version
()()

// simplest structure + code
// λx.λy.x+y
(() => {
  const DATA = [4, 13]
  const code = data => data.concat(data[0] + data[1])
  console.log(code(DATA))
})()

// simplest structure + code - using a true JavaScript lambda expression
// λx.λy.x+y
(() => {
  const DATA = [4, 13]
  const code1 = x => y => x + y
  //const code2 = data => data.concat(data[0] + data[1])
  const code2 = data => data.concat(code1(data[0])(data[1]))
  console.log(code2(DATA)) // [ 4, 13, 17 ]
})()

