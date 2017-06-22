// AQL grammar
// see PEG.js github page
// usage:
// THESE WORK
//  PEG.parse("typeside Ty = literal { ... }");
//  PEG.parse("typeside Ty = literal { ... } schema S = literal : Ty {} instance I = literal : S {}");
//  PEG.parse("\ntypeside Ty = literal { ... }\nschema S = literal : Ty {}\ninstance I = literal : S {}\ntypeside Ty = literal { ... }\nschema S = literal : Ty {}\ninstance I = literal : S {}");
//  PEG.parse("\ntypeside Ty = literal { ... }\nschema S = literal : Ty { entities foreign_keys attributes }\ninstance I = literal : S {}\ntypeside Ty = literal {...}\nschema S = literal : Ty {}\ninstance I = literal : S {}");
// PEG.parse("\ntypeside Ty = literal { ... }\nschema S = literal : Ty { entities One foreign_keys attributes }\ninstance I = literal : S {}\ntypeside Ty = literal {...}\nschema S = literal : Ty { entities One }\ninstance I = literal : S {}");
// PEG.parse("\ntypeside Ty = literal { ... }\nschema S = literal : Ty { entities foreign_keys attributes }");
// PEG.parse("\ntypeside Ty = literal { ... }\nschema S = literal : Ty { entities One Two Three foreign_keys attributes }");
// PEG.parse("\ntypeside Ty = literal { ... }\nschema S = literal : Ty { entities One Two Three foreign_keys attributes }\ninstance I = literal : S {}");
// PEG.parse("\ntypeside Ty = literal { ... }\nschema S = literal : Ty { entities One foreign_keys fk : Two -> Three fkx:One->Two attributes }\ninstance I = literal : S {}\ntypeside Ty = literal {...}\nschema S = literal : Ty { entities One }\ninstance I = literal : S {}");
// PEG.parse("\ntypeside _x345g_u77 = literal { ... }\nschema _S4 = literal : Ty { entities _1One foreign_keys _fk1 : _5Two222 -> Three _fkx:_One->_Two attributes }\ninstance I = literal : S {}\ntypeside Ty = literal {...}\nschema _S = literal : Ty { entities _One111 }\ninstance I = literal : S {}");

/*
try {
  PEG.parse("\ntypeside Ty = literal { BAD }\nschema S = literal : Ty { entities foreign_keys attributes }");
} catch(e) {
  console.log(e.message + " Line:" + e.location.start.line + " Column:" + e.location.start.column);
}
*/

start = (_ typeside / _ schema / _ instance)+

// TYPESIDE
typeside = "typeside" _ tsName _ "=" _ "literal" _ "{" _ typesideInside _ "}"
tsName = first:[a-z_]i bf:[a-z0-9_]i* { return first + bf.join("") }
typesideInside = "..."

// SCHEMA
schema = "schema" _ schName _ "=" _ "literal" _ ":" _ tsName _ "{" _ schemaInside _ "}"
schName = first:[a-z_]i bf:[a-z0-9_]i* { return first + bf.join("") }
schemaInside = (_ entities _ entityLines / _ foreign_keys _ foreignkeyLines / _ attributes)*
entities = "entities"
foreign_keys = "foreign_keys"
attributes = "attributes"
// SCHEMA entities
entityLines = (entityLine _)*
entityLine = !foreign_keys !attributes entName
entName = first:[a-z_]i bf:[a-z0-9_]i* { return first + bf.join("") }
// SCHEMA foreign_keys
// fk2 : Two -> Three  OR  fk1:One->Two
foreignkeyLines = (foreignkeyLine _)*
foreignkeyLine = !attributes foreignkeyLineDetails
foreignkeyLineDetails = fkStr _ ":" _ fkStr _ "->" _ fkStr
fkStr = first:[a-z_]i bf:[a-z0-9_]i* { return first + bf.join("") }

// INSTANCE
instance = "instance" _ instName _ "=" _ "literal" _ ":" _ schName _ "{" _ "}"
instName = first:[a-z_]i bf:[a-z0-9_]i* { return first + bf.join("") }

// WHITESPACE
_ "whitespace"
  = [ \t\n\r]* { return null; }

