// AQL grammar
// see PEG.js github page
// usage:
// THESE PROBABLY STILL WORK
//  PEG.parse("typeside Ty = literal { ... }");
//  PEG.parse("typeside Ty = literal { ... } schema S = literal : Ty {} instance I = literal : S {}");
//  PEG.parse("\ntypeside Ty = literal { ... }\nschema S = literal : Ty {}\ninstance I = literal : S {}\ntypeside Ty = literal { ... }\nschema S = literal : Ty {}\ninstance I = literal : S {}");
//  PEG.parse("\ntypeside Ty = literal { ... }\nschema S = literal : Ty { entities foreignkeys attributes }\ninstance I = literal : S {}\ntypeside Ty = literal {...}\nschema S = literal : Ty {}\ninstance I = literal : S {}");
// PEG.parse("\ntypeside Ty = literal { ... }\nschema S = literal : Ty { entities One foreignkeys attributes }\ninstance I = literal : S {}\ntypeside Ty = literal {...}\nschema S = literal : Ty { entities One }\ninstance I = literal : S {}");

// it sometimes has problems with the "_" in "foreign_keys", so for now change it to "foreignkeys"
// explicitly allow other characters in the various names  chars:[a-zA-Z0-9]  [a-z0–9_$]i*
//   first:[A-Z_$] rest:[a-z0–9_$]i* { return first + rest.join(‘’) }

// THESE WORK
// PEG.parse("\ntypeside Ty = literal { ... }\nschema S = literal : Ty { entities foreignkeys attributes }");
// PEG.parse("\ntypeside Ty = literal { ... }\nschema S = literal : Ty { entities One Two Three foreignkeys attributes }");
// PEG.parse("\ntypeside Ty = literal { ... }\nschema S = literal : Ty { entities One Two Three foreignkeys attributes }\ninstance I = literal : S {}");

start = (_ typeside / _ schema / _ instance)+

// typeside
typeside = "typeside" _ tsName _ "=" _ "literal" _ "{" _ typesideInside _ "}"
tsName = chars:[a-z]i+ { return chars.join(""); }
typesideInside = "..."

// schema
schema = "schema" _ schName _ "=" _ "literal" _ ":" _ tsName _ "{" _ schemaInside _ "}"
schName = chars:[a-z]i+ { return chars.join(""); }
schemaInside = (_ entities _ entityLines / _ foreignkeys / _ attributes)*
entities = "entities"
foreignkeys = "foreignkeys"
attributes = "attributes"
entityLines = (entityLine _)*
entityLine = !foreignkeys !attributes entName
entName = chars:[a-z]i+ { return chars.join(""); }

// instance
instance = "instance" _ instName _ "=" _ "literal" _ ":" _ schName _ "{" _ "}"
instName = chars:[a-z]i+ { return chars.join(""); }

// whitespace
_ "whitespace"
  = [ \t\n\r]* { return null; }
