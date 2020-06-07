// Generated automatically by nearley, version 2.19.3
// http://github.com/Hardmath123/nearley
(function () {
function id(x) { return x[0]; }

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
var grammar = {
    Lexer: undefined,
    ParserRules: [
    {"name": "_$ebnf$1", "symbols": []},
    {"name": "_$ebnf$1", "symbols": ["_$ebnf$1", "wschar"], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "_", "symbols": ["_$ebnf$1"], "postprocess": function(d) {return null;}},
    {"name": "__$ebnf$1", "symbols": ["wschar"]},
    {"name": "__$ebnf$1", "symbols": ["__$ebnf$1", "wschar"], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "__", "symbols": ["__$ebnf$1"], "postprocess": function(d) {return null;}},
    {"name": "wschar", "symbols": [/[ \t\n\v\f]/], "postprocess": id},
    {"name": "ACLCommunicativeAct", "symbols": ["Message"], "postprocess": selectorBody},
    {"name": "Message$ebnf$1", "symbols": []},
    {"name": "Message$ebnf$1$subexpression$1", "symbols": ["MessageParameterSpaced"]},
    {"name": "Message$ebnf$1", "symbols": ["Message$ebnf$1", "Message$ebnf$1$subexpression$1"], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "Message", "symbols": ["ParenthOpen", "_", "MessageType", "Message$ebnf$1", "_", "ParenthClose"], "postprocess": selectorBody},
    {"name": "MessageType$string$1", "symbols": [{"literal":"a"}, {"literal":"c"}, {"literal":"c"}, {"literal":"e"}, {"literal":"p"}, {"literal":"t"}, {"literal":"-"}, {"literal":"p"}, {"literal":"r"}, {"literal":"o"}, {"literal":"p"}, {"literal":"o"}, {"literal":"s"}, {"literal":"a"}, {"literal":"l"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$1"], "postprocess": id},
    {"name": "MessageType$string$2", "symbols": [{"literal":"a"}, {"literal":"g"}, {"literal":"r"}, {"literal":"e"}, {"literal":"e"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$2"], "postprocess": id},
    {"name": "MessageType$string$3", "symbols": [{"literal":"c"}, {"literal":"a"}, {"literal":"n"}, {"literal":"c"}, {"literal":"e"}, {"literal":"l"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$3"], "postprocess": id},
    {"name": "MessageType$string$4", "symbols": [{"literal":"c"}, {"literal":"f"}, {"literal":"p"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$4"], "postprocess": id},
    {"name": "MessageType$string$5", "symbols": [{"literal":"c"}, {"literal":"o"}, {"literal":"n"}, {"literal":"f"}, {"literal":"i"}, {"literal":"r"}, {"literal":"m"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$5"], "postprocess": id},
    {"name": "MessageType$string$6", "symbols": [{"literal":"d"}, {"literal":"i"}, {"literal":"s"}, {"literal":"c"}, {"literal":"o"}, {"literal":"n"}, {"literal":"f"}, {"literal":"i"}, {"literal":"r"}, {"literal":"m"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$6"], "postprocess": id},
    {"name": "MessageType$string$7", "symbols": [{"literal":"f"}, {"literal":"a"}, {"literal":"i"}, {"literal":"l"}, {"literal":"u"}, {"literal":"r"}, {"literal":"e"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$7"], "postprocess": id},
    {"name": "MessageType$string$8", "symbols": [{"literal":"i"}, {"literal":"n"}, {"literal":"f"}, {"literal":"o"}, {"literal":"r"}, {"literal":"m"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$8"], "postprocess": id},
    {"name": "MessageType$string$9", "symbols": [{"literal":"i"}, {"literal":"n"}, {"literal":"f"}, {"literal":"o"}, {"literal":"r"}, {"literal":"m"}, {"literal":"-"}, {"literal":"i"}, {"literal":"f"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$9"], "postprocess": id},
    {"name": "MessageType$string$10", "symbols": [{"literal":"i"}, {"literal":"n"}, {"literal":"f"}, {"literal":"o"}, {"literal":"r"}, {"literal":"m"}, {"literal":"-"}, {"literal":"r"}, {"literal":"e"}, {"literal":"f"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$10"], "postprocess": id},
    {"name": "MessageType$string$11", "symbols": [{"literal":"n"}, {"literal":"o"}, {"literal":"t"}, {"literal":"-"}, {"literal":"u"}, {"literal":"n"}, {"literal":"d"}, {"literal":"e"}, {"literal":"r"}, {"literal":"s"}, {"literal":"t"}, {"literal":"o"}, {"literal":"o"}, {"literal":"d"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$11"], "postprocess": id},
    {"name": "MessageType$string$12", "symbols": [{"literal":"p"}, {"literal":"r"}, {"literal":"o"}, {"literal":"p"}, {"literal":"a"}, {"literal":"g"}, {"literal":"a"}, {"literal":"t"}, {"literal":"e"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$12"], "postprocess": id},
    {"name": "MessageType$string$13", "symbols": [{"literal":"p"}, {"literal":"r"}, {"literal":"o"}, {"literal":"p"}, {"literal":"o"}, {"literal":"s"}, {"literal":"e"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$13"], "postprocess": id},
    {"name": "MessageType$string$14", "symbols": [{"literal":"p"}, {"literal":"r"}, {"literal":"o"}, {"literal":"x"}, {"literal":"y"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$14"], "postprocess": id},
    {"name": "MessageType$string$15", "symbols": [{"literal":"q"}, {"literal":"u"}, {"literal":"e"}, {"literal":"r"}, {"literal":"y"}, {"literal":"-"}, {"literal":"i"}, {"literal":"f"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$15"], "postprocess": id},
    {"name": "MessageType$string$16", "symbols": [{"literal":"q"}, {"literal":"u"}, {"literal":"e"}, {"literal":"r"}, {"literal":"y"}, {"literal":"-"}, {"literal":"r"}, {"literal":"e"}, {"literal":"f"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$16"], "postprocess": id},
    {"name": "MessageType$string$17", "symbols": [{"literal":"r"}, {"literal":"e"}, {"literal":"f"}, {"literal":"u"}, {"literal":"s"}, {"literal":"e"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$17"], "postprocess": id},
    {"name": "MessageType$string$18", "symbols": [{"literal":"r"}, {"literal":"e"}, {"literal":"j"}, {"literal":"e"}, {"literal":"c"}, {"literal":"t"}, {"literal":"-"}, {"literal":"p"}, {"literal":"r"}, {"literal":"o"}, {"literal":"p"}, {"literal":"o"}, {"literal":"s"}, {"literal":"a"}, {"literal":"l"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$18"], "postprocess": id},
    {"name": "MessageType$string$19", "symbols": [{"literal":"r"}, {"literal":"e"}, {"literal":"q"}, {"literal":"u"}, {"literal":"e"}, {"literal":"s"}, {"literal":"t"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$19"], "postprocess": id},
    {"name": "MessageType$string$20", "symbols": [{"literal":"r"}, {"literal":"e"}, {"literal":"q"}, {"literal":"u"}, {"literal":"e"}, {"literal":"s"}, {"literal":"t"}, {"literal":"-"}, {"literal":"w"}, {"literal":"h"}, {"literal":"e"}, {"literal":"n"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$20"], "postprocess": id},
    {"name": "MessageType$string$21", "symbols": [{"literal":"r"}, {"literal":"e"}, {"literal":"q"}, {"literal":"u"}, {"literal":"e"}, {"literal":"s"}, {"literal":"t"}, {"literal":"-"}, {"literal":"w"}, {"literal":"h"}, {"literal":"e"}, {"literal":"n"}, {"literal":"e"}, {"literal":"v"}, {"literal":"e"}, {"literal":"r"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$21"], "postprocess": id},
    {"name": "MessageType$string$22", "symbols": [{"literal":"s"}, {"literal":"u"}, {"literal":"b"}, {"literal":"s"}, {"literal":"c"}, {"literal":"r"}, {"literal":"i"}, {"literal":"b"}, {"literal":"e"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageType", "symbols": ["MessageType$string$22"], "postprocess": id},
    {"name": "MessageParameterSpaced", "symbols": ["__", "MessageParameter"], "postprocess": selectorBody},
    {"name": "MessageParameter$string$1", "symbols": [{"literal":":"}, {"literal":"s"}, {"literal":"e"}, {"literal":"n"}, {"literal":"d"}, {"literal":"e"}, {"literal":"r"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageParameter", "symbols": ["MessageParameter$string$1", "__", "AgentIdentifier"], "postprocess": filter},
    {"name": "MessageParameter$string$2", "symbols": [{"literal":":"}, {"literal":"r"}, {"literal":"e"}, {"literal":"c"}, {"literal":"e"}, {"literal":"i"}, {"literal":"v"}, {"literal":"e"}, {"literal":"r"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageParameter", "symbols": ["MessageParameter$string$2", "__", "AgentIdentifierSet"], "postprocess": filter},
    {"name": "MessageParameter$string$3", "symbols": [{"literal":":"}, {"literal":"c"}, {"literal":"o"}, {"literal":"n"}, {"literal":"t"}, {"literal":"e"}, {"literal":"n"}, {"literal":"t"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageParameter", "symbols": ["MessageParameter$string$3", "__", "String"], "postprocess": filter},
    {"name": "MessageParameter$string$4", "symbols": [{"literal":":"}, {"literal":"r"}, {"literal":"e"}, {"literal":"p"}, {"literal":"l"}, {"literal":"y"}, {"literal":"-"}, {"literal":"w"}, {"literal":"i"}, {"literal":"t"}, {"literal":"h"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageParameter", "symbols": ["MessageParameter$string$4", "__", "Expression"], "postprocess": filter},
    {"name": "MessageParameter$string$5", "symbols": [{"literal":":"}, {"literal":"r"}, {"literal":"e"}, {"literal":"p"}, {"literal":"l"}, {"literal":"y"}, {"literal":"-"}, {"literal":"b"}, {"literal":"y"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageParameter", "symbols": ["MessageParameter$string$5", "__", "DateTime"], "postprocess": filter},
    {"name": "MessageParameter$string$6", "symbols": [{"literal":":"}, {"literal":"i"}, {"literal":"n"}, {"literal":"-"}, {"literal":"r"}, {"literal":"e"}, {"literal":"p"}, {"literal":"l"}, {"literal":"y"}, {"literal":"-"}, {"literal":"t"}, {"literal":"o"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageParameter", "symbols": ["MessageParameter$string$6", "__", "Expression"], "postprocess": filter},
    {"name": "MessageParameter$string$7", "symbols": [{"literal":":"}, {"literal":"r"}, {"literal":"e"}, {"literal":"p"}, {"literal":"l"}, {"literal":"y"}, {"literal":"-"}, {"literal":"t"}, {"literal":"o"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageParameter", "symbols": ["MessageParameter$string$7", "__", "AgentIdentifierSet"], "postprocess": filter},
    {"name": "MessageParameter$string$8", "symbols": [{"literal":":"}, {"literal":"l"}, {"literal":"a"}, {"literal":"n"}, {"literal":"g"}, {"literal":"u"}, {"literal":"a"}, {"literal":"g"}, {"literal":"e"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageParameter", "symbols": ["MessageParameter$string$8", "__", "Expression"], "postprocess": filter},
    {"name": "MessageParameter$string$9", "symbols": [{"literal":":"}, {"literal":"e"}, {"literal":"n"}, {"literal":"c"}, {"literal":"o"}, {"literal":"d"}, {"literal":"i"}, {"literal":"n"}, {"literal":"g"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageParameter", "symbols": ["MessageParameter$string$9", "__", "Expression"], "postprocess": filter},
    {"name": "MessageParameter$string$10", "symbols": [{"literal":":"}, {"literal":"o"}, {"literal":"n"}, {"literal":"t"}, {"literal":"o"}, {"literal":"l"}, {"literal":"o"}, {"literal":"g"}, {"literal":"y"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageParameter", "symbols": ["MessageParameter$string$10", "__", "Expression"], "postprocess": filter},
    {"name": "MessageParameter$string$11", "symbols": [{"literal":":"}, {"literal":"p"}, {"literal":"r"}, {"literal":"o"}, {"literal":"t"}, {"literal":"o"}, {"literal":"c"}, {"literal":"o"}, {"literal":"l"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageParameter", "symbols": ["MessageParameter$string$11", "__", "Word"], "postprocess": filter},
    {"name": "MessageParameter$string$12", "symbols": [{"literal":":"}, {"literal":"c"}, {"literal":"o"}, {"literal":"n"}, {"literal":"v"}, {"literal":"e"}, {"literal":"r"}, {"literal":"s"}, {"literal":"a"}, {"literal":"t"}, {"literal":"i"}, {"literal":"o"}, {"literal":"n"}, {"literal":"-"}, {"literal":"i"}, {"literal":"d"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "MessageParameter", "symbols": ["MessageParameter$string$12", "__", "Expression"], "postprocess": filter},
    {"name": "UserDefinedParameter", "symbols": ["Word"]},
    {"name": "Expression", "symbols": ["Word"]},
    {"name": "Expression", "symbols": ["String"]},
    {"name": "Expression", "symbols": ["Number"]},
    {"name": "Expression", "symbols": ["DateTime"]},
    {"name": "Expression$ebnf$1", "symbols": []},
    {"name": "Expression$ebnf$1", "symbols": ["Expression$ebnf$1", "Expression"], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "Expression", "symbols": ["ParenthOpen", "Expression$ebnf$1", "ParenthClose"], "postprocess": selectorBody},
    {"name": "AgentIdentifier$string$1", "symbols": [{"literal":"a"}, {"literal":"g"}, {"literal":"e"}, {"literal":"n"}, {"literal":"t"}, {"literal":"-"}, {"literal":"i"}, {"literal":"d"}, {"literal":"e"}, {"literal":"n"}, {"literal":"t"}, {"literal":"i"}, {"literal":"f"}, {"literal":"i"}, {"literal":"e"}, {"literal":"r"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "AgentIdentifier$string$2", "symbols": [{"literal":":"}, {"literal":"n"}, {"literal":"a"}, {"literal":"m"}, {"literal":"e"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "AgentIdentifier", "symbols": ["ParenthOpen", "_", "AgentIdentifier$string$1", "__", "AgentIdentifier$string$2", "__", "Word", "_", "ParenthClose"], "postprocess": selectorBody},
    {"name": "AgentIdentifierSequence$string$1", "symbols": [{"literal":"s"}, {"literal":"e"}, {"literal":"q"}, {"literal":"u"}, {"literal":"e"}, {"literal":"n"}, {"literal":"c"}, {"literal":"e"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "AgentIdentifierSequence$ebnf$1", "symbols": []},
    {"name": "AgentIdentifierSequence$ebnf$1", "symbols": ["AgentIdentifierSequence$ebnf$1", "AgentIdentifier"], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "AgentIdentifierSequence", "symbols": ["ParenthOpen", "AgentIdentifierSequence$string$1", "AgentIdentifierSequence$ebnf$1", "ParenthClose"]},
    {"name": "AgentIdentifierSet$string$1", "symbols": [{"literal":"s"}, {"literal":"e"}, {"literal":"t"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "AgentIdentifierSet$ebnf$1", "symbols": []},
    {"name": "AgentIdentifierSet$ebnf$1$subexpression$1", "symbols": ["AgentIdentifierSpaced"]},
    {"name": "AgentIdentifierSet$ebnf$1", "symbols": ["AgentIdentifierSet$ebnf$1", "AgentIdentifierSet$ebnf$1$subexpression$1"], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "AgentIdentifierSet", "symbols": ["ParenthOpen", "_", "AgentIdentifierSet$string$1", "AgentIdentifierSet$ebnf$1", "_", "ParenthClose"], "postprocess": selectorBody},
    {"name": "AgentIdentifierSpaced", "symbols": ["__", "AgentIdentifier"], "postprocess": selectorBody},
    {"name": "URLSequence$string$1", "symbols": [{"literal":"s"}, {"literal":"e"}, {"literal":"q"}, {"literal":"u"}, {"literal":"e"}, {"literal":"n"}, {"literal":"c"}, {"literal":"e"}], "postprocess": function joiner(d) {return d.join('');}},
    {"name": "URLSequence$ebnf$1", "symbols": []},
    {"name": "URLSequence$ebnf$1", "symbols": ["URLSequence$ebnf$1", "URL"], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "URLSequence", "symbols": ["ParenthOpen", "URLSequence$string$1", "URLSequence$ebnf$1", "ParenthClose"]},
    {"name": "DateTime", "symbols": ["DateTimeToken"]},
    {"name": "URL", "symbols": ["String"]},
    {"name": "Word$ebnf$1", "symbols": [/[a-zA-Z0-9-]/]},
    {"name": "Word$ebnf$1", "symbols": ["Word$ebnf$1", /[a-zA-Z0-9-]/], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "Word", "symbols": ["Word$ebnf$1"], "postprocess": d => d[0].join('')},
    {"name": "String", "symbols": ["StringLiteral"]},
    {"name": "StringLiteral$ebnf$1", "symbols": [/[a-zA-Z0-9,() \t\n\v\f-:?=<>\"\\]/]},
    {"name": "StringLiteral$ebnf$1", "symbols": ["StringLiteral$ebnf$1", /[a-zA-Z0-9,() \t\n\v\f-:?=<>\"\\]/], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "StringLiteral", "symbols": [{"literal":"\""}, "StringLiteral$ebnf$1", {"literal":"\""}], "postprocess": d => d[1].join('')},
    {"name": "Number", "symbols": ["Integer"]},
    {"name": "Number", "symbols": ["Float"]},
    {"name": "URL", "symbols": ["String"]},
    {"name": "DateTimeToken$ebnf$1", "symbols": ["Sign"], "postprocess": id},
    {"name": "DateTimeToken$ebnf$1", "symbols": [], "postprocess": function(d) {return null;}},
    {"name": "DateTimeToken$subexpression$1$ebnf$1", "symbols": ["TypeDesignator"], "postprocess": id},
    {"name": "DateTimeToken$subexpression$1$ebnf$1", "symbols": [], "postprocess": function(d) {return null;}},
    {"name": "DateTimeToken$subexpression$1", "symbols": ["DateTimeToken$subexpression$1$ebnf$1"]},
    {"name": "DateTimeToken", "symbols": ["DateTimeToken$ebnf$1", "Year", "Month", "Day", {"literal":"T"}, "Hour", "Minute", "Second", "MilliSecond", "DateTimeToken$subexpression$1"]},
    {"name": "Year", "symbols": ["Digit", "Digit", "Digit", "Digit"]},
    {"name": "Month", "symbols": ["Digit", "Digit"]},
    {"name": "Day", "symbols": ["Digit", "Digit"]},
    {"name": "Hour", "symbols": ["Digit", "Digit"]},
    {"name": "Minute", "symbols": ["Digit", "Digit"]},
    {"name": "Second", "symbols": ["Digit", "Digit"]},
    {"name": "MilliSecond", "symbols": ["Digit", "Digit", "Digit"]},
    {"name": "TypeDesignator", "symbols": ["AlphaCharacter"]},
    {"name": "AlphaCharacter", "symbols": [/[ "a" – "z" ]/]},
    {"name": "AlphaCharacter", "symbols": [/[ "A" – "Z" ]/]},
    {"name": "Digit", "symbols": [/[ "0" – "9" ]/]},
    {"name": "Sign", "symbols": [/[ "+" , "-" ]/]},
    {"name": "Integer$ebnf$1", "symbols": ["Sign"], "postprocess": id},
    {"name": "Integer$ebnf$1", "symbols": [], "postprocess": function(d) {return null;}},
    {"name": "Integer$ebnf$2", "symbols": ["Digit"]},
    {"name": "Integer$ebnf$2", "symbols": ["Integer$ebnf$2", "Digit"], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "Integer", "symbols": ["Integer$ebnf$1", "Integer$ebnf$2"]},
    {"name": "Dot", "symbols": [/[ "." ]/]},
    {"name": "Float$ebnf$1", "symbols": ["Sign"], "postprocess": id},
    {"name": "Float$ebnf$1", "symbols": [], "postprocess": function(d) {return null;}},
    {"name": "Float$ebnf$2", "symbols": ["FloatExponent"], "postprocess": id},
    {"name": "Float$ebnf$2", "symbols": [], "postprocess": function(d) {return null;}},
    {"name": "Float", "symbols": ["Float$ebnf$1", "FloatMantissa", "Float$ebnf$2"]},
    {"name": "Float$ebnf$3", "symbols": ["Sign"], "postprocess": id},
    {"name": "Float$ebnf$3", "symbols": [], "postprocess": function(d) {return null;}},
    {"name": "Float$ebnf$4", "symbols": ["Digit"]},
    {"name": "Float$ebnf$4", "symbols": ["Float$ebnf$4", "Digit"], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "Float", "symbols": ["Float$ebnf$3", "Float$ebnf$4", "FloatExponent"]},
    {"name": "FloatMantissa$ebnf$1", "symbols": ["Digit"]},
    {"name": "FloatMantissa$ebnf$1", "symbols": ["FloatMantissa$ebnf$1", "Digit"], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "FloatMantissa$ebnf$2", "symbols": []},
    {"name": "FloatMantissa$ebnf$2", "symbols": ["FloatMantissa$ebnf$2", "Digit"], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "FloatMantissa", "symbols": ["FloatMantissa$ebnf$1", "Dot", "FloatMantissa$ebnf$2"]},
    {"name": "FloatMantissa$ebnf$3", "symbols": []},
    {"name": "FloatMantissa$ebnf$3", "symbols": ["FloatMantissa$ebnf$3", "Digit"], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "FloatMantissa$ebnf$4", "symbols": ["Digit"]},
    {"name": "FloatMantissa$ebnf$4", "symbols": ["FloatMantissa$ebnf$4", "Digit"], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "FloatMantissa", "symbols": ["FloatMantissa$ebnf$3", "Dot", "FloatMantissa$ebnf$4"]},
    {"name": "FloatExponent$ebnf$1", "symbols": ["Sign"], "postprocess": id},
    {"name": "FloatExponent$ebnf$1", "symbols": [], "postprocess": function(d) {return null;}},
    {"name": "FloatExponent$ebnf$2", "symbols": ["Digit"]},
    {"name": "FloatExponent$ebnf$2", "symbols": ["FloatExponent$ebnf$2", "Digit"], "postprocess": function arrpush(d) {return d[0].concat([d[1]]);}},
    {"name": "FloatExponent", "symbols": ["Exponent", "FloatExponent$ebnf$1", "FloatExponent$ebnf$2"]},
    {"name": "Exponent", "symbols": [/[ "e", "E" ]/]},
    {"name": "ParenthOpen", "symbols": [/[ "(" ]/], "postprocess": function(d) {return null;}},
    {"name": "ParenthClose", "symbols": [/[ ")" ]/], "postprocess": function(d) {return null;}}
]
  , ParserStart: "ACLCommunicativeAct"
}
if (typeof module !== 'undefined'&& typeof module.exports !== 'undefined') {
   module.exports = grammar;
} else {
   window.grammar = grammar;
}
})();
