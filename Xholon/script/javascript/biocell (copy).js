/*
biocell.js
Ken Webb, 18 Jan 2021

https://en.wikipedia.org/wiki/Nucleic_acid
https://en.wikipedia.org/wiki/Nucleobase
*/

(() => {
  //const NUCLE0BASE_ABBREVS = "ACGTU".split("")
  //const NUCLE0BASE_NAMES = "adenine,cytosine,guanine,thymine,uracil".split(",")
  const NUCLE0BASE_ABBREVS_RNA = "ACGU".split("")
  const NUCLE0BASE_NAMES_RNA = "adenine,cytosine,guanine,uracil".split(",")
  const NUCLE0BASE_ABBREVS_DNA = "ACGT".split("")
  const NUCLE0BASE_NAMES_DNA = "adenine,cytosine,guanine,thymine".split(",")
  
  const AMINOACID_ABBREVS = "".split("")
  const AMINOACID_NAMES = "".split(",")
  
  const makePair = (acc, curr) => Object.assign(acc, {[curr.charAt(0)]: curr.charAt(1)}
  //const PAIRS_RNA = "CG GC AU UA".split(" ").reduce((acc, curr) => {acc[curr.charAt(0)] = curr.charAt(1); return acc;}, {})
  const PAIRS_RNA = "CG GC AU UA".split(" ").reduce((acc, curr) => makePair(acc, curr), {}) //Object.assign(acc, {[curr.charAt(0)]: curr.charAt(1)}), {})
  const PAIRS_DNA = "CG GC AT TA".split(" ").reduce((acc, curr) => makePair(acc, curr), {}) //Object.assign(acc, {[curr.charAt(0)]: curr.charAt(1)}), {})
  //const PAIRS_DNA = "CG GC AT TA".split(" ").reduce((acc, curr) => {acc[curr.charAt(0)] = curr.charAt(1); return acc;}, {})
  console.log(PAIRS_RNA)
  console.log(PAIRS_DNA)
  
  const makeRandom = things => count => new Array(count).fill(0).map((item) => things[Math.floor(Math.random() * things.length)])
  const makeRandomRNA = makeRandom(NUCLE0BASE_ABBREVS_RNA)
  const makeRandomDNA = makeRandom(NUCLE0BASE_ABBREVS_DNA)
  
  const RANDOM_RNA_01 = makeRandomRNA(10)
  console.log(RANDOM_RNA_01)
  console.log(RANDOM_RNA_01.join(""))
})()

