/*
  metta01.js
  Ken Webb
  23 January 2021
  
    7 8 9
    4 5 6
    1 2 3
  
  JS FP version, based on meTTTa.c in my book:
  "Oracle Distributed Systems - A C Programmer's Development Guide", 1990
  
  see also: existing Xholon Java meTTTa
*/

((title) => {
  console.log(title)
  
  // Grid Position Statuses
  const G_NULL     = 0
  const G_HUMAN    = 1
  const G_COMPUTER = 2
  
  // Players
  const PL_HUMAN    = "H"
  const PL_COMPUTER = "C"
  
  const GRID_SIZE = 8
  const GRID_TEMPLATE = Array(GRID_SIZE).fill(G_NULL) // this is a template
  
  const compose = (...fns) => x => fns.reduceRight((y, f) => f(y), x)
  //const pipe = (...fns) => x => fns.reduce((y, f) => f(y), x)
  
  // create and initialize a grid, using the current grid as a template
  const init_grid = (value, grid) => grid.map(item => value)
  
  // Return a random integer between 1 and n
  const rnd_n = n => Math.floor(Math.random() * n) + 1
  
  const get_null_indexes = grid => grid.reduce((acc, curr, index) => curr === G_NULL ? acc.concat(index) : acc, [])
  const get_rand_item = arr => arr[rnd_n(arr.length) - 1]
  const get_rand_move = grid => compose(get_rand_item, get_null_indexes)(grid)
  //const get_rand_move = grid => get_rand_item(get_null_indexes(grid))
  
  // TESTING ---------------------------------------------------------------
  console.log(init_grid(G_NULL, GRID_TEMPLATE))
  
  console.log(rnd_n(10))
  const rnd_n_multi = (n, arr) => arr.map(item => rnd_n(n))
  console.log(rnd_n_multi(10, new Array(20).fill(0)))
  
  //console.log(get_rand_move(2, init_grid(G_NULL, GRID_TEMPLATE)))
  console.log(get_rand_move(init_grid(G_NULL, GRID_TEMPLATE)))

})("meTTTa")

