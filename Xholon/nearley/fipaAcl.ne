# FIPA ACL
# SC00070I  FIPA ACL Message Representation in String
# http://www.fipa.org/specs/fipa00070/index.html
# http://www.fipa.org/specs/fipa00070/SC00070I.html
# 
# Syntactic representation of ACL in string form.
# 2 String ACL Representation
# This section defines the message transport syntax for string representation which is expressed in standard EBNF format (see Table 1).

# nearley
#nearleyc fipaAcl.ne -o fipaAcl.js
#nearley-test -q -i '(inform :sender (agent-identifier :name i) :receiver (set (agent-identifier :name j)) :content "weather (today, raining)" :language Prolog)' fipaAcl.js

# https://github.com/kach/nearley/blob/master/builtin/whitespace.ne
# Whitespace: `_` is optional, `__` is mandatory.
#_  -> wschar:* {% function(d) {return null;} %}
#__ -> wschar:+ {% function(d) {return null;} %}
#wschar -> [ \t\n\v\f] {% id %}

@builtin "whitespace.ne" # `_` means arbitrary amount of whitespace

# based on: https://medium.com/@gajus/parsing-absolutely-anything-in-javascript-using-earley-algorithm-886edcc31e5e
@{%
const flatten = d => {
  return d.reduce(
    (a, b) => {
      return a.concat(b);
    },
    []
  );
}

// this doesn't seem to do anything
const flatten2 = d => {
  return flatten(flatten(d));
}

const filter = d => {
  return d.filter((token) => {
    return token !== null;
  });
}

const selectorBody = (d, i, reject) => {
  const tokens = filter(d);
  if (!tokens.length) {
    return reject;
  }
  return flatten(tokens);
}
%}

# 2.2 Syntax
ACLCommunicativeAct     -> Message {% selectorBody %}

Message                 -> ParenthOpen _ MessageType
                              (MessageParameterSpaced):* _ ParenthClose {% selectorBody %}

#MessageType             -> See [FIPA00037]
# http://www.fipa.org/specs/fipa00018/OC00018.pdf + FIPA00037
MessageType        -> "accept-proposal" {% id %}
                   | "agree" {% id %}
                   | "cancel" {% id %}
                   | "cfp" {% id %}
                   | "confirm" {% id %}
                   | "disconfirm" {% id %}
                   | "failure" {% id %}
                   | "inform" {% id %}
                   | "inform-if" {% id %}
                   | "inform-ref" {% id %}
                   | "not-understood" {% id %}
                   | "propagate" {% id %}
                   | "propose" {% id %}
                   | "proxy" {% id %}
                   | "query-if" {% id %}
                   | "query-ref" {% id %}
                   | "refuse" {% id %}
                   | "reject-proposal" {% id %}
                   | "request" {% id %}
                   | "request-when" {% id %}
                   | "request-whenever" {% id %}
                   | "subscribe" {% id %}

MessageParameterSpaced  -> __ MessageParameter {% selectorBody %}

# OK nearley only finds UserDefinedParameter and never the String such as ":sender"
MessageParameter        -> ":sender" __ AgentIdentifier {% filter %}
                        | ":receiver" __ AgentIdentifierSet {% filter %}
                        | ":content" __ String {% filter %}
                        | ":reply-with" __ Expression {% filter %}
                        | ":reply-by" __ DateTime {% filter %}
                        | ":in-reply-to" __ Expression {% filter %}
                        | ":reply-to" __ AgentIdentifierSet {% filter %}
                        | ":language" __ Expression {% filter %}
                        | ":encoding" __ Expression {% filter %}
                        | ":ontology" __ Expression {% filter %}
                        | ":protocol" __ Word {% filter %}
                        | ":conversation-id" __ Expression {% filter %}
#                        | UserDefinedParameter __ Expression {% filter %}

UserDefinedParameter    -> Word

Expression              -> Word
                        | String
                        | Number
                        | DateTime
                        | ParenthOpen Expression:* ParenthClose {% selectorBody %}
 
AgentIdentifier         -> ParenthOpen _ "agent-identifier" __
                           ":name" __ Word
#                          [ ":addresses" URLSequence ]
#                          [ ":resolvers" AgentIdentifierSequence ]
#                          ( UserDefinedParameter Expression ):*
                           _ ParenthClose {% selectorBody %}

AgentIdentifierSequence -> ParenthOpen "sequence" AgentIdentifier:* ParenthClose

AgentIdentifierSet      -> ParenthOpen _ "set" (AgentIdentifierSpaced):* _ ParenthClose {% selectorBody %}

AgentIdentifierSpaced   -> __ AgentIdentifier {% selectorBody %}

URLSequence             -> ParenthOpen "sequence" URL:* ParenthClose

DateTime                -> DateTimeToken

#URL                     -> See [RFC2396]
URL                     -> String

# 2.3 Lexical Rules
#Word                    -> [~ "\0x00" – "\0x20", "(", ")", "#", "0" – "9", "-", "@"]
#                          [~ "\0x00" – "\0x20", "(", ")"]:*
Word                    -> [a-zA-Z0-9-]:+ {% d => d[0].join('') %}

#String                  -> StringLiteral | ByteLengthEncodedString
String                  -> StringLiteral

#StringLiteral           -> "\"" ([ ~ "\"" ] | "\\\""):* "\""
StringLiteral           -> "\"" [a-zA-Z0-9,() \t\n\v\f-:?=<>\"\\]:+ "\"" {% d => d[1].join('') %}

#ByteLengthEncodedString -> "#" Digit:+ "\"" <byte sequence>

Number                  -> Integer | Float

#URL                     -> See [RFC2396]
URL                     -> String

DateTimeToken           ->  Sign:?
                           Year Month Day "T"
                           Hour Minute Second MilliSecond
                           ( TypeDesignator:? )

Year                    -> Digit Digit Digit Digit

Month                   -> Digit Digit

Day                     -> Digit Digit

Hour                    -> Digit Digit

Minute                  -> Digit Digit

Second                  -> Digit Digit

MilliSecond             -> Digit Digit Digit

TypeDesignator          -> AlphaCharacter

AlphaCharacter          -> [ "a" – "z" ] | [ "A" – "Z" ]

Digit                   -> [ "0" – "9" ]

Sign                    -> [ "+" , "-" ] 

Integer                 -> Sign:? Digit:+ 

Dot                     -> [ "." ]

Float                   -> Sign:? FloatMantissa FloatExponent:?
                        | Sign:? Digit:+ FloatExponent

FloatMantissa           -> Digit:+ Dot Digit:*
                        | Digit:* Dot Digit:+

FloatExponent           -> Exponent Sign:? Digit:+

Exponent                -> [ "e", "E" ]

# KSW added
ParenthOpen             -> [ "(" ] {% function(d) {return null;} %}
ParenthClose            -> [ ")" ] {% function(d) {return null;} %}

