// AQL grammar
// Ken Webb
// June 2017
// see PEG.js github page
// can test online at: https://pegjs.org/online
// to build parser from computer terminal:
//  pegjs --format globals --export-var PEGAQL aql.pegjs
// 
/* TODO
 - use Array.reduce() to make the resulting structure simpler
 - deal with the large number of null and undefined that get created in the result
 - handle AQL comments
 - handle other AQL syntax
 - get it workinig with Xholon
*/

/*
usage: (THESE WORK)
PEGAQL.parse("typeside Ty = literal {  }");
PEGAQL.parse("typeside Ty = literal {  } schema S = literal : Ty {} instance I = literal : S {}");
PEGAQL.parse("\ntypeside Ty = literal {  }\nschema S = literal : Ty {}\ninstance I = literal : S {}\ntypeside Ty = literal { ... }\nschema S = literal : Ty {}\ninstance I = literal : S {}");
PEGAQL.parse("\ntypeside Ty = literal {  }\nschema S = literal : Ty { entities foreign_keys attributes }\ninstance I = literal : S {}\ntypeside Ty = literal {...}\nschema S = literal : Ty {}\ninstance I = literal : S {}");
PEGAQL.parse("\ntypeside Ty = literal {  }\nschema S = literal : Ty { entities One foreign_keys attributes }\ninstance I = literal : S {}\ntypeside Ty = literal {...}\nschema S = literal : Ty { entities One }\ninstance I = literal : S {}");
PEGAQL.parse("\ntypeside Ty = literal {  }\nschema S = literal : Ty { entities foreign_keys attributes }");
PEGAQL.parse("\ntypeside Ty = literal {  }\nschema S = literal : Ty { entities One Two Three foreign_keys attributes }");
PEGAQL.parse("\ntypeside Ty = literal {  }\nschema S = literal : Ty { entities One Two Three foreign_keys attributes }\ninstance I = literal : S {}");
PEGAQL.parse("\ntypeside Ty = literal {  }\nschema S = literal : Ty { entities One foreign_keys fk : Two -> Three fkx:One->Two attributes }\ninstance I = literal : S {}\ntypeside Ty = literal {...}\nschema S = literal : Ty { entities One }\ninstance I = literal : S {}");
PEGAQL.parse("\ntypeside _x345g_u77 = literal {  }\nschema _S4 = literal : Ty { entities _1One foreign_keys _fk1 : _5Two222 -> Three _fkx:_One->_Two attributes }\ninstance I = literal : S {}\ntypeside Ty = literal {...}\nschema _S = literal : Ty { entities _One111 }\ninstance I = literal : S {}");
*/

/*
try {
  PEGAQL.parse("\ntypeside Ty = literal { BAD }\nschema S = literal : Ty { entities foreign_keys attributes }");
} catch(e) {
  console.log(e.message + " Line:" + e.location.start.line + " Column:" + e.location.start.column);
}
*/

/*
// an example that works in https://pegjs.org/online
typeside Ty = literal {
  types
    int
    string
  constants
    Ken Licorice : string
    4 : int
}

schema S = literal : Ty {
  entities
    One
    Two
    Three
  foreign_keys
    fk1 : One -> Two
    fk2:Two->Three
  attributes
    attr1 : One -> int
    attr2 : Two -> string
    attr3 : Three -> string
}

instance I = literal : S {
  generators 
    one1 : One
    one2 : One
    two1 : Two
    three1 : Three
  equations
    one1.attr1 = 4
    one1.fk1 = two1
    attr1(one2) = 8
    two1.attr2 = Ken
    two1.fk2 = three1
    three1.attr3 = Licorice
}
*/

start = (_ typeside / _ schema / _ instance)+

// TYPESIDE
typeside = "typeside" _ tsName _ "=" _ "literal" _ "{" _ typesideInside _ "}"
tsName = first:[a-z_]i bf:[a-z0-9_]i* {return first + bf.join("");}
typesideInside = (_ types _ typeLines / _ constants _ constantLines)*
types = "types"
constants = "constants"
// TYPESIDE types
// string int
typeLines = tls:(typeLine _)*
{ // this is a test of JavaScript Array.reduce()
  return tls.reduce(function(result, element) {
    if (element) {
      return result.concat(element);
    }
    else {
      return result;
    }
  }, []);
}

typeLine = !constants typeName
typeName = first:[a-z_]i bf:[a-z0-9_]i* {return first + bf.join("");}
// TYPESIDE constants
// Ken Licorice : string
// 4 : int
constantLines = (constantLine _)*
constantLine = constantLineDetails
constantLineDetails = ((constantStr _)+ / [0-9]+) _ ":" _ constantStr
constantStr = first:[a-z_]i bf:[a-z0-9_]i* {return first + bf.join("");}

// SCHEMA
schema = "schema" _ schName _ "=" _ "literal" _ ":" _ tsName _ "{" _ schemaInside _ "}"
schName = first:[a-z_]i bf:[a-z0-9_]i* {return first + bf.join("");}
schemaInside = (_ entities _ entityLines / _ foreign_keys _ foreignkeyLines / _ attributes _ attributeLines)*
entities = "entities"
foreign_keys = "foreign_keys"
attributes = "attributes"
// SCHEMA entities
// One Two Three
entityLines = (entityLine _)*
entityLine = !foreign_keys !attributes entName
entName = first:[a-z_]i bf:[a-z0-9_]i* {return first + bf.join("");}
// SCHEMA foreign_keys
// fk2 : Two -> Three
// fk1:One->Two
foreignkeyLines = (foreignkeyLine _)*
foreignkeyLine = !attributes foreignkeyLineDetails
foreignkeyLineDetails = fkStr _ ":" _ fkStr _ "->" _ fkStr
fkStr = first:[a-z_]i bf:[a-z0-9_]i* {return first + bf.join("");}
// SCHEMA attributes
// attr1 : One -> int
attributeLines = (attributeLine _)*
attributeLine = attributeLineDetails
attributeLineDetails = attrStr _ ":" _ attrStr _ "->" _ attrStr
attrStr = first:[a-z_]i bf:[a-z0-9_]i* {return first + bf.join("");}

// INSTANCE
instance = "instance" _ instName _ "=" _ "literal" _ ":" _ schName _ "{" instanceInside "}"
instName = first:[a-z_]i bf:[a-z0-9_]i* {return first + bf.join("");}
instanceInside = (_ generators _ generatorLines / _ equations _ equationLines)*
generators = "generators"
equations = "equations"
// INSTANCE generators
// one1 : One
generatorLines = (generatorLine _)*
generatorLine = !equations generatorLineDetails
generatorLineDetails = genStr _ ":" _ genStr
genStr = first:[a-z_]i bf:[a-z0-9_]i* {return first + bf.join("");}
// INSTANCE equations
// one1.attr1 = 4  OR  attr1(one1) = 4
// one1.fk1 = two1  OR fk1(one1) = two1
equationLines = (equationLine _)*
equationLine = equationLineDetails
equationLineDetails = ((equStr "." equStr) / (equStr "(" equStr ")")) _ "=" _ (equStr / [0-9]+)
equStr = first:[a-z_]i bf:[a-z0-9_]i* {return first + bf.join("");}

// WHITESPACE
//_ "whitespace"
//  = [ \t\n\r]* { return null; }
//_ = (whitespace / comment1)
_ = whitespace

whitespace = [ \t\n\r]* { return null; }
//comment1 = "//" [^\n\r]*
//comment2 = "/*" [] "*/"

