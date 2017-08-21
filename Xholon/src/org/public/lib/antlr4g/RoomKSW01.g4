/*
 * This ANTLR4 grammar was manually created by Ken Webb from Room.xtext
 * August 1, 2017
 * parser rules start with a lowercase letter
 * lexer rules start with a capital letter
 * TODO FQN and ID rules are ambiguous; how do I deal with this ?
 */

grammar RoomKSW01;

roomModel
  : 'RoomModel' ID (Documentation)? '{'
  ( dataClass
  | generalProtocolClass
  | actorClass
  | subSystemClass
  | logicalSystem
  | ImportRoom
  )* '}'
  ;

logicalSystem : 'LogicalSystem' ID '{' ( subSystemRef )* '}' ;

//subSystemRef : 'SubSystemRef' ID ':' ID (Documentation)? ;
subSystemRef : 'SubSystemRef' RoomName ID (Documentation)? ;

subSystemClass : 'SubSystemClass' ID (Documentation)? '{' ( logicalThread | layerConnection | actorRef )* '}' ;

logicalThread : 'LogicalThread' ID ;

layerConnection : 'LayerConnection' 'ref' ID 'satisfied_by' ID ;

generalProtocolClass
  : protocolClass
  | compoundProtocolClass
  ;

protocolClass
  : 'ProtocolClass' ID (Documentation)? ( 'extends' ID )? '{'
    ('incoming' '{' message* '}')?
    ('outgoing' '{' message* '}')?
    ('regular' 'PortClass' portClass)?
    ('conjugated' 'PortClass' portClass)?
  '}'
  ;

compoundProtocolClass
  : 'CompoundProtocolClass' ID (Documentation)? '{' subProtocol* '}'
  ;

subProtocol
  : 'SubProtocol' ID ':' ID
  ;

message
  : ('private')? 'Message' ID '(' (varDecl)? ')'
  (Documentation)?
  ;

varDecl
  : ID
  ;

portClass
  : '{'  '}'
  ;

actorRef
  : (ReferenceType)? 'ActorRef' ( ( ID (MULTIPLICITY)? ':' ) | RoomName ) ID (Documentation)?
	;

actorClass
  : 'ActorClass' ID (Documentation)? ( 'extends' ID )? '{'
  ( 'Interface' '{'  '}' )?
  ( structure )?
  ( behavior )?
  '}'
  ;

structure
  : 'Structure' '{' ( actorRef )* '}'
  ;

behavior
  : 'Behavior' '{' stateMachine '}'
  ;

stateMachine
  : 'StateMachine' '{'  '}'
  ;

dataClass
  : 'DataClass' ID (Documentation)? ( 'extends' ID )? '{' ID '}'
	;

//importRoom : 'import' ( importedFQN 'from' | 'model' ) ID ;
// for now, ignore import
ImportRoom : 'import' ~[\r\n]* -> skip ;

importedFQN : 'impotodo' ; //FQN ('.*')? ;

ReferenceType : 'rtypetodo' ;

MULTIPLICITY : '[' ( '*'|INT ) ']' ;

// FQN and ID rules are ambiguous
//FQN : ID ('.' ID)* ;

Documentation : '[' 'doctodo' ']' ;

//fragment
//StringLiteral
//	:	'"' StringCharacters? '"'
//	;

//fragment
//StringCharacters
//	:	StringCharacter+
//	;

//fragment
//StringCharacter
//	:	~["\\\r\n]
//	;

// [0-9a-fA-F]
//HEX : ('0x'|'0X') ('0'..'9'|'a'..'f'|'A'..'F')+ ;

//ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;
//ID : [a-zA-Z_] [a-zA-Z0-9_]*;

ID : [a-zA-Z] [a-zA-Z0-9]* ;

// roleName, portName, and any other name that ends in ":"
RoomName : [a-zA-Z] [a-zA-Z0-9]* [:] ;

// '0' | [1-9] [0-9]*
//INT : ('0'..'9')+;

fragment
INT : [0-9]+ ;

// KSW this causes error(50)
//RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\''); // Java

//STRING : ('"' ('\\' .|~('\\'|'"'))* '"'|'\'' ('\\' .|~('\\'|'\''))* '\'') ; // JavaScript

//RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/'; // Java
//ML_COMMENT : '/*' ( . )*?'*/'; // JavaScript
ML_COMMENT : '/*' .*? '*/' -> skip ;

// KSW this causes error(50)
//RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?; // Java
//SL_COMMENT : '//' ~('\n'|'\r')* ('\r'? '\n')?; // JavaScript
SL_COMMENT : '//' ~[\r\n]* -> skip ;

//WS : (' '|'\t'|'\r'|'\n')+;
WS : [ \n\t\r]+ -> skip ;

//ANY_OTHER : . ;

