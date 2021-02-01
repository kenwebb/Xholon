/*
  petrinet01.js
  Ken Webb - 31 Jan 2021
  Petri Nets using JavaScript ES6 - arrow functions, etc.
  
  see also:
  https://joemathjoe.wordpress.com/publications/
  https://www.azimuthproject.org/azimuth/show/Petri+nets+-+Syllabus
  http://www.informatik.uni-hamburg.de/TGI/PetriNets/index.php
*/

// https://www.azimuthproject.org/azimuth/show/Petri+net
((title) => {
  console.log(title)
  const RABBIT = 1 // OR "*" OR ["R"]
  const FOX = 1
  const birth = r => r + r // one rabbit becomes two
  const predation = (r, f) => f + f // a fox eats a rabbit and reproduces, resulting in two foxes
  const death = f => 0 // one fox dies
  const trace = (r, f) => console.log(`rabbits: ${r} foxes: ${f}`)
  
  // unit tests
  trace(RABBIT, FOX)
  trace(birth(RABBIT), FOX)
  trace(RABBIT, predation(RABBIT, FOX))
  trace(RABBIT, death(FOX))
})("Azimuth - Rabbits and Foxes")

// a version using a JS Objects to represent places where there may be RABBIT or FOX
// this version better captures the Petri Net approach - Places + Transitions
((title) => {
  console.log(title)
  const PLACE_R = {r: 1}
  const PLACE_F = {f: 1}
  const birth = place => {{r: place.r + 1}}
  const predation = (placer, placef) => placer.r -= 1 // what to return ???
  
})("Azimuth - Rabbits and Foxes - 2")

// bipartite structure with Places and Transitions
((title) => {
  console.log(title)
  
  const PLACES = {
    r:1,
    f:1
  }
  
  const TRANSITIONS = {
    birth: places => Object.assign(places, {r: places.r+1}),
    predation: places => Object.assign(places, {r: places.r-1, f: places.f+1}),
    death: places => Object.assign(places, {f: places.f-1})
  }
  
  const trace = places => console.log(`rabbits: ${places.r} foxes: ${places.f}`)
  
  // unit testing; the contents of PLACES is continually mutated
  trace(PLACES)
  trace(TRANSITIONS.birth(PLACES))
  trace(TRANSITIONS.predation(PLACES))
  trace(TRANSITIONS.death(PLACES))
})("Azimuth - Rabbits and Foxes - 3")

// in this version, all the contents of PLACES are immutable
// the function create and return new objects
((title) => {
  console.log(title)
  
  const PLACES = {
    r:1,
    f:1
  }
  
  const TRANSITIONS = {
    birth: places => Object.assign({}, places, {r: places.r+1}),
    predation: places => Object.assign({}, places, {r: places.r-1, f: places.f+1}),
    death: places => Object.assign({}, places, {f: places.f-1})
  }
  
  const trace = places => console.log(`rabbits: ${places.r} foxes: ${places.f}`)
  
  // unit testing; the contents of PLACES is continually mutated
  trace(PLACES)
  trace(TRANSITIONS.birth(PLACES))
  trace(TRANSITIONS.predation(PLACES))
  trace(TRANSITIONS.death(PLACES))
})("Azimuth - Rabbits and Foxes - 4")

// use compose() or pipe()
((title) => {
  console.log(title)
  
  //const compose = (...fns) => x => fns.reduceRight((y, f) => f(y), x)
  const pipe = (...fns) => x => fns.reduce((y, f) => f(y), x)
  
  const PLACES = {
    r:1,
    f:1
  }
  
  const TRANSITIONS = {
    birth: places => Object.assign({}, places, {r: places.r+1}),
    predation: places => Object.assign({}, places, {r: places.r-1, f: places.f+1}),
    death: places => Object.assign({}, places, {f: places.f-1})
  }
  
  const trace = places => `rabbits: ${places.r} foxes: ${places.f}`
  const tracelog = places => {console.log(trace(places)); return places;}
  
  // unit testing
  pipe(tracelog, TRANSITIONS.birth, tracelog, TRANSITIONS.predation, tracelog, TRANSITIONS.death, tracelog)(PLACES)
})("Azimuth - Rabbits and Foxes - 5")

// minmal version
(() => {
  const pipe = (...fns) => x => fns.reduce((y, f) => f(y), x)
  const PLACES = { r:1, f:1}
  const birth = places => Object.assign({}, places, {r: places.r+1})
  const predation = places => Object.assign({}, places, {r: places.r-1, f: places.f+1})
  const death = places => Object.assign({}, places, {f: places.f-1})
  const trace = places => `rabbits: ${places.r} foxes: ${places.f}`
  const tracelog = places => {console.log(trace(places)); return places;}
  pipe(tracelog, birth, tracelog, predation, tracelog, death, tracelog)(PLACES)
})()


