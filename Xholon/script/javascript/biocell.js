/*
biocell.js
Ken Webb, 18 Jan 2021

https://en.wikipedia.org/wiki/Nucleic_acid
https://en.wikipedia.org/wiki/Nucleobase
https://en.wikipedia.org/wiki/Biomolecule
https://en.wikipedia.org/wiki/Amino_acid
https://en.wikipedia.org/wiki/Proteinogenic_amino_acid
https://en.wikipedia.org/wiki/Genetic_code
https://en.wikipedia.org/wiki/RNA_polymerase
https://en.wikipedia.org/wiki/Ribosome
https://en.wikipedia.org/wiki/Molecular_machine#Biological
*/

(() => {
  //const NUCLE0BASE_ABBREVS = "ACGTU".split("")
  //const NUCLE0BASE_NAMES = "adenine,cytosine,guanine,thymine,uracil".split(",")
  const NUCLE0BASE_ABBREVS_RNA = "ACGU".split("")
  const NUCLE0BASE_NAMES_RNA = "adenine,cytosine,guanine,uracil".split(",")
  const NUCLE0BASE_ABBREVS_DNA = "ACGT".split("")
  const NUCLE0BASE_NAMES_DNA = "adenine,cytosine,guanine,thymine".split(",")
  
  // TODO TRIPLETS_RNA = ["AAA", ..., "UUU"] // 64
  const TWOS_RNA = "AA AC AG AU CA CC CG CU GA GC GG GU UA UC UG UU".split(" ")
  console.log(TWOS_RNA)
  const makeTriplets = ix => new Array(16).fill(NUCLE0BASE_ABBREVS_RNA[ix]).map((item, index) => item + TWOS_RNA[index])
  const TRIPLETS_RNA = [].concat(makeTriplets(0)).concat(makeTriplets(1)).concat(makeTriplets(2)).concat(makeTriplets(3))
  //const AXX = new Array(16).fill(NUCLE0BASE_ABBREVS_RNA[0])
  console.log(TRIPLETS_RNA)
  
  const AMINOACID_SHORTS = "ACDEFGHIKLMNOPQRSTUVWY".split("")
  const AMINOACID_ABBREVS = "Ala,Cys,Asp,Glu,Phe,Gly,His,Ile,Lys,Leu,Met,Asn,Pyl,Pro,Gln,Arg,Ser,Thr,Sec,Val,Trp,Tyr".split(",")
  const AMINOACID_NAMES = "Alanine,Cysteine,Aspartic acid,Glutamic acid,Phenylalanine,Glycine,Histidine,Isoleucine,Lysine,Leucine,Methionine,Asparagine,Pyrrolysine,Proline,Glutamine,Arginine,Serine,Threonine,Selenocysteine,Valine,Tryptophan,Tyrosine".split(",")
  console.log(AMINOACID_SHORTS)
  console.log(AMINOACID_ABBREVS)
  console.log(AMINOACID_NAMES)
  
  // https://upload.wikimedia.org/wikipedia/commons/d/d6/GeneticCode21-version-2.svg
  //const GENETIC_CODE_RNA = [] // map each TRIPLETS_RNA index/value to a AMINOACID_SHORTS index/value
  // ex: {GCA: "A"}  {AAA: "K"}
  
  // https://raw.githubusercontent.com/urmi-21/orfipy/master/scripts/example_user_table.json
  // https://github.com/urmi-21/orfipy
  const GENETIC_CODE_DNA = {
		"name": "Standard (transl_table=1)",
		"start": [
		  "TTG",
		  "CTG",
		  "ATG"
		],
		"stop": [
		  "TAA",
		  "TAG",
		  "TGA"
		],
		"table": {
      "AAA": "K",
      "AAC": "N",
      "AAG": "K",
      "AAT": "N",
      "ACA": "T",
      "ACC": "T",
      "ACG": "T",
      "ACT": "T",
      "AGA": "R",
      "AGC": "S",
      "AGG": "R",
      "AGT": "S",
      "ATA": "I",
      "ATC": "I",
      "ATG": "M",
      "ATT": "I",
      "CAA": "Q",
      "CAC": "H",
      "CAG": "Q",
      "CAT": "H",
      "CCA": "P",
      "CCC": "P",
      "CCG": "P",
      "CCT": "P",
      "CGA": "R",
      "CGC": "R",
      "CGG": "R",
      "CGT": "R",
      "CTA": "L",
      "CTC": "L",
      "CTG": "L",
      "CTT": "L",
      "GAA": "E",
      "GAC": "D",
      "GAG": "E",
      "GAT": "D",
      "GCA": "A",
      "GCC": "A",
      "GCG": "A",
      "GCT": "A",
      "GGA": "G",
      "GGC": "G",
      "GGG": "G",
      "GGT": "G",
      "GTA": "V",
      "GTC": "V",
      "GTG": "V",
      "GTT": "V",
      "TAA": "*",
      "TAC": "Y",
      "TAG": "*",
      "TAT": "Y",
      "TCA": "S",
      "TCC": "S",
      "TCG": "S",
      "TCT": "S",
      "TGA": "*",
      "TGC": "C",
      "TGG": "W",
      "TGT": "C",
      "TTA": "L",
      "TTC": "F",
      "TTG": "L",
      "TTT": "F"
    }
  }
  console.log(GENETIC_CODE_DNA.table.CTT) // L
  
  const GENETIC_CODE_RNA = {
		"name": "Standard (transl_table=1)",
		"start": [
		  "UUG",
		  "CUG",
		  "AUG"
		],
		"stop": [
		  "UAA",
		  "UAG",
		  "UGA"
		],
		"table": {
      "AAA": "K",
      "AAC": "N",
      "AAG": "K",
      "AAU": "N",
      "ACA": "T",
      "ACC": "T",
      "ACG": "T",
      "ACU": "T",
      "AGA": "R",
      "AGC": "S",
      "AGG": "R",
      "AGU": "S",
      "AUA": "I",
      "AUC": "I",
      "AUG": "M",
      "AUU": "I",
      "CAA": "Q",
      "CAC": "H",
      "CAG": "Q",
      "CAU": "H",
      "CCA": "P",
      "CCC": "P",
      "CCG": "P",
      "CCU": "P",
      "CGA": "R",
      "CGC": "R",
      "CGG": "R",
      "CGU": "R",
      "CUA": "L",
      "CUC": "L",
      "CUG": "L",
      "CUU": "L",
      "GAA": "E",
      "GAC": "D",
      "GAG": "E",
      "GAU": "D",
      "GCA": "A",
      "GCC": "A",
      "GCG": "A",
      "GCU": "A",
      "GGA": "G",
      "GGC": "G",
      "GGG": "G",
      "GGU": "G",
      "GUA": "V",
      "GUC": "V",
      "GUG": "V",
      "GUU": "V",
      "UAA": "*",
      "UAC": "Y",
      "UAG": "*",
      "UAU": "Y",
      "UCA": "S",
      "UCC": "S",
      "UCG": "S",
      "UCU": "S",
      "UGA": "*",
      "UGC": "C",
      "UGG": "W",
      "UGU": "C",
      "UUA": "L",
      "UUC": "F",
      "UUG": "L",
      "UUU": "F"
    }
  }  
  
  const makePair = (acc, curr) => Object.assign(acc, {[curr.charAt(0)]: curr.charAt(1)})
  const PAIRS_RNA = "CG GC AU UA".split(" ").reduce((acc, curr) => makePair(acc, curr), {})
  const PAIRS_DNA = "CG GC AT TA".split(" ").reduce((acc, curr) => makePair(acc, curr), {})
  console.log(PAIRS_RNA)
  console.log(PAIRS_DNA)
  
  const makeRandom = things => count => new Array(count).fill(0).map((item) => things[Math.floor(Math.random() * things.length)])
  const makeRandomRNA = makeRandom(NUCLE0BASE_ABBREVS_RNA)
  const makeRandomStrandDNA = makeRandom(NUCLE0BASE_ABBREVS_DNA)
  const _makeComplementaryStrandDNA = mapping => strand => strand.map(item => mapping[item])
  const makeComplementaryStrandDNA = _makeComplementaryStrandDNA(PAIRS_DNA)
  
  const RANDOM_RNA_01 = makeRandomRNA(10)
  console.log(RANDOM_RNA_01)
  console.log(RANDOM_RNA_01.join(""))
  
  // rnaPolymerase  DNA -> RNA
  const rnaPolymerase = dna => dna.map((item) => item === "T" ? "U" : item)
  const ABC = makeRandomStrandDNA(120)
  console.log(ABC)
  const DEF = rnaPolymerase(ABC)
  console.log(DEF)
  const GHI = makeComplementaryStrandDNA(ABC)
  console.log(GHI)
  
  const DNA_2STRAND = [ABC, GHI]
  console.log(DNA_2STRAND)
  
  // tRNA - return the amino acid corresponding to the triplet starting at rnaArr[ix]
  const _tRNA = (gcodeTable) => (rnaArr, ix) => gcodeTable[rnaArr.slice(ix, ix+3).join("")]
  //const tRNA = (gcodeTable, rnaArr, ix) => gcodeTable[rnaArr.slice(ix, ix+3).join("")]
  const tRNA = _tRNA(GENETIC_CODE_RNA.table)
  const TEST_RNA = makeRandomRNA(10)
  console.log(TEST_RNA)
  //console.log(tRNA(GENETIC_CODE_RNA.table, TEST_RNA, 3))
  //console.log(tRNA(GENETIC_CODE_RNA.table, DEF, 3))
  console.log(tRNA(TEST_RNA, 3))
  console.log(tRNA(DEF, 3))
  
  // ribosome
  // translates a mRNA string into a polypeptide string using tRNA
  const ribosome = (rnaArr, funk) => rnaArr.reduce((acc, curr, index, arr) => index % 3 === 0 ? acc.concat(funk(arr, index)) : acc, "")
  console.log(ribosome(DEF, tRNA))
  
  // TODO do an entire DNA string -> RNA string -> polypeptide string -> effective function  (form -> function)
  // transcription :: DNA -> RNA
  // translation :: RNA -> Protein
  // compose(fold3D, translate, transcribe)
  
  // TODO fold the protein into its functional #D shape
  // do this by mapping from amino acid string to a set of JavaScript functions
  // the functions/lambdas would be as I've done with Cell Model, and with Petri Nets
  // ex: "PVCWHFGPEACWHHQTLFPSKKRDFLISPDPL*GSCTPA*" -> {SET_OF_FUNCTIONS}
  // ex: (sm1, sm2) = ???  OR use Petri Net incoming and outgoing arcs to effect changes
  
})()

// https://stackoverflow.blog/2021/02/03/sequencing-your-dna-with-a-usb-dongle-and-open-source-code/?cb=1
// https://nanoporetech.com/
// https://en.wikipedia.org/wiki/Nanopore_sequencing
(() => {
  // TODO ???
})()

