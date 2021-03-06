/*
  Ken Webb, 25 January 2021
  updated 26 Jan 2021
  
  based on:
    https://www.youtube.com/watch?v=BSa2l_Q3qiU
    Pi: An Edible Exploration of Math, Aug 30, 2019
    The Museum of Flight
    The Museum celebrates Pi day (March 14) with Eugenia Cheng,
    author of How to Bake Pi: An Edible Exploration of the Mathematics of Mathematics.
    In this engaging talk, math professor Cheng describes how “math, like recipes,
    have both ingredients and method.”
  
  In this JavaScript (.js) file, I try to capture the part of her lecture about
  operations on a piece of paper that has the letters A B C and D written on it.
  
  see video: https://youtu.be/BSa2l_Q3qiU?t=2079  time 34:39
  
  see also my books:
    Abstract Algebra - A student-friendly approach, by Laura L. Dos Reis and Anthony J. Dos Reis
      - a book for Math students in university
    Concepts of Modern Mathematics, by Ian Stewart
      - a populaerization
  
  My approach here is neither a full-on traditional Math approach, nor is it a popularization.
  Instead it's a computational approach, building on my programming knowledge.
  In today's world, I believe that a computational approach is the best way for the average person to get into Math stuff.
  
  Table of what happens when you do operation 1 followed by operation 2.
  "at the top left is where you do nothing and then you do nothing again"
  All of this involve starting in the A position, where A is at the top of the piece of paper.
  
     0    rot  flip flop
     ---- ---- ---- ----
0     A    B    C    D
rot   B    A    D    C
flip  C    D    A    B
flop  D    C    B    A

  video https://youtu.be/BSa2l_Q3qiU?t=2414  the above table is an iterated Battenberg cake
  video https://youtu.be/BSa2l_Q3qiU?t=2505  a tree representation of the iterated structure
  video https://youtu.be/BSa2l_Q3qiU?t=2912  Category Theory
  
  Notes 26 Jan
  ------------
  - DONE mark the modules using "MODULE"
  - create a Xholon app based on the example from the video
    - ABCD are four Xholon nodes
    - each node has four ports: zero rot flip flop
    - I could do this as a Xholon module, or as a complete new Xholon workbook
  - think about how to use Memoize to cache ports
  - if I do a Xholon app/workbook, then include SVG generated by Graphviz DOT
  - doing the four operations in the example, is the same as moving the Xholon System Avatar from node to node
    - initially the Avatar is pointing to or inside node A
  - this example is similar to my Mud Lake example with signposts (nodes) and paths
   - const NODES = "ABCDEFG"
   - const PATHS = {A:B, B:A, B:C, etc.}
   - start at node A
   - in this example, the network that connects the nodes is irregular in shape
  - the operations can be thought of as functions or ports or both
  - the ABCD example is equivalent to manipulating a coin
    - initial position is: the heads side visible, the picture is right side up (this is A)
    - can then do the four operations: zero rot flip flop
  - another equivalent example is the letter R, which is non-symmetrically both vertically and horizontally
  - in summary, the three equivalent rigid (rotatable, flippable, floppable) structures are:
    1. a piece of paper or card with the letters ABCD written on it
    2. a coin
    3. the letter R
  
  Instead of a full piece of paper, I've used a 3x5 index card
  Side 1   Side 2
  *****    *****
  * A *    * C *
  *   *    *   *
  *   *    *   *
  *   *    *   *
  * B *    * D *
  *****    *****
  - note that the B and D are written upside down on the card
  
  - here's an attempt to make the b and d look upside down, using lower-case letters
  *****    *****
  * a *    * c *
  *   *    *   *
  *   *    *   *
  *   *    *   *
  * q *    * p *
  *****    *****
*/

// MODULE 1
((title) => {
  console.log(title)

  // the four letters
  const A = "A"
  const B = "B"
  const C = "C"
  const D = "D"

  // 0 zero
  const ZEROS = {
    A: A,
    B: B,
    C: C,
    D: D
  }

  // rot
  const ROTATIONS = {
    A: B,
    B: A,
    C: D,
    D: C
  }

  // flip
  const FLIPS = {
    A: C,
    B: D,
    C: A,
    D: B
  }

  // flop
  const FLOPS = {
    A: D,
    B: C,
    C: B,
    D: A
  }

  //console.log(ZEROS.A)
  //console.log(ROTATIONS.A)

  // useful utilities that will not be explained just yet
  // for now, think of these as magic incantations; they could be called abra and cadabra
  const compose = (...fns) => x => fns.reduceRight((y, f) => f(y), x)
  const pipe = (...fns) => x => fns.reduce((y, f) => f(y), x)

  const operate = table => letter => table[letter] //table.letter
  //console.log(operate(ZEROS)(C))

  // the four operations (functions) on letters
  const zero = operate(ZEROS)
  const rot = operate(ROTATIONS)
  const flip = operate(FLIPS)
  const flop = operate(FLOPS)

  console.log(rot(A))
  console.log(pipe(rot, rot)(A))
  
  console.log("ABCD".split("").map(letter => rot(letter)).join("")) // "BADC"
  
  // recreate the 4 x 4 table
  const ABCD = "ABCD"
  console.log("0    " + ABCD.split("").map(letter => zero(letter)).join(" "))
  console.log("rot  " + ABCD.split("").map(letter => rot(letter)).join(" "))
  console.log("flip " + ABCD.split("").map(letter => flip(letter)).join(" "))
  console.log("flop " + ABCD.split("").map(letter => flop(letter)).join(" "))
  /* result:
0    A B C D
rot  B A D C
flip C D A B
flop D C B A
  */
  // note the iterated Battenberg pattern that Eugenia Cheng talks about in the video
  
})("Pi: An Edible Exploration of Math, with Eugenia Cheng")

// ----------------------------------------------------------------------------------
// MODULE 2
// alternative implementations of the four operations
((title) => {
  console.log(title)

  // zero
  const zero = letter => letter

  // rot

  // rot as a traditional JavaScript (C-like) function, using if ... else
  function rot1(letter) {
    if (letter === "A") {
      return "B";
    }
    else if (letter === "B") {
      return "A";
    }
    else if (letter === "C") {
      return "D";
    }
    else {
      return "C";
    }
  }

  // rot as a traditional JavaScript (C-like) function, using switch
  function rot2(letter) {
    switch (letter) {
    case "A": return "B";
    case "B": return "A";
    case "C": return "D";
    default: return "C";
    }
  }

  // rot as a functional programming one-liner
  // the syntax of these conditional expressions also goes back to C (see K&R 2nd ed, p. 51)
  const rot3 = letter => letter === "A" ? "B" : letter === "B" ? "A" : letter === "C" ? "D" : "C"

  // note that Lisp and Scheme use "cond" which is another alternative tp if ... else
  
  console.log(rot1("A"))
  console.log(rot2("A"))
  console.log(rot3("A"))
  
  // instead of using a for loop, use map to run rot1 for all four letters; here are some one-liners
  console.log("ABCD".split("").map(letter => rot3(letter)).join("")) // "BADC"
  console.log("ABCD".split("").map(letter => rot3(letter))) // [ "B", "A", "D", "C" ]
  console.log(["A", "B", "C", "D"].map(letter => rot1(letter))) // [ "B", "A", "D", "C" ]
  console.log(["A", "B", "C", "D"].map(letter => rot2(letter))) // [ "B", "A", "D", "C" ]
  
  // if we use a for loop
  function maprot(array) {
    const newarray = [];
    for (var i = 0; i < array.length; i++) {
      newarray.push(rot1(array[i]));
    }
    return newarray;
  }
  console.log(maprot(["A", "B", "C", "D"]));
  
})("alternative implementations")

// ----------------------------------------------------------------------------------
// MODULE 3
// Graphviz
// http://webgraphviz.com/  converts DOT content into an SVG image
((title) => {
  console.log(title)
  
  // a graph in Graphviz DOT format; a directed graph
  const GRAPHVIZ = `digraph G {
  // A at the top of the page
  A -> A [label=0]
  A -> B [label=rot]
  A -> C [label=flip]
  A -> D [label=flop]
  
  B -> A [label=rot]
  B -> B [label=0]
  B -> C [label=flop]
  B -> D [label=flip]
  
  C -> A [label=flip]
  C -> B [label=flop]
  C -> C [label=0]
  C -> D [label=rot]
  
  D -> A [label=flop]
  D -> B [label=flip]
  D -> C [label=rot]
  D -> D [label=0]
}`
  console.log(GRAPHVIZ)
  
  // an undirected graph
  const GRAPVIZ2 = `graph G {
  A -- A [label=0]
  A -- B [label=rot]
  A -- C [label=flip]
  A -- D [label=flop]
  
  B -- B [label=0]
  B -- C [label=flop]
  B -- D [label=flip]
  
  C -- C [label=0]
  C -- D [label=rot]
  
  D -- D [label=0]
}`
  console.log(GRAPHVIZ2)

})("Graphviz")


