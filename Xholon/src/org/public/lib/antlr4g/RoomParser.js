// Generated from Room.g4 by ANTLR 4.7
// jshint ignore: start
var antlr4 = require('xholon/lib/antlr4/index');
var RoomListener = require('./RoomListener').RoomListener;
var grammarFileName = "Room.g4";

var serializedATN = ["\u0003\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964",
    "\u0003?\u01ac\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t",
    "\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t\u0007\u0004",
    "\b\t\b\u0004\t\t\t\u0004\n\t\n\u0004\u000b\t\u000b\u0004\f\t\f\u0004",
    "\r\t\r\u0004\u000e\t\u000e\u0004\u000f\t\u000f\u0004\u0010\t\u0010\u0004",
    "\u0011\t\u0011\u0004\u0012\t\u0012\u0004\u0013\t\u0013\u0004\u0014\t",
    "\u0014\u0004\u0015\t\u0015\u0004\u0016\t\u0016\u0004\u0017\t\u0017\u0004",
    "\u0018\t\u0018\u0004\u0019\t\u0019\u0004\u001a\t\u001a\u0004\u001b\t",
    "\u001b\u0004\u001c\t\u001c\u0004\u001d\t\u001d\u0004\u001e\t\u001e\u0004",
    "\u001f\t\u001f\u0004 \t \u0004!\t!\u0004\"\t\"\u0004#\t#\u0004$\t$\u0004",
    "%\t%\u0004&\t&\u0003\u0002\u0003\u0002\u0003\u0002\u0005\u0002P\n\u0002",
    "\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002",
    "\u0003\u0002\u0007\u0002Y\n\u0002\f\u0002\u000e\u0002\\\u000b\u0002",
    "\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003",
    "\u0007\u0003d\n\u0003\f\u0003\u000e\u0003g\u000b\u0003\u0003\u0003\u0003",
    "\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004o",
    "\n\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0005\u0005t\n\u0005\u0003",
    "\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0007\u0005z\n\u0005\f\u0005",
    "\u000e\u0005}\u000b\u0005\u0003\u0005\u0003\u0005\u0003\u0006\u0003",
    "\u0006\u0003\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0003\u0007\u0003",
    "\u0007\u0003\u0007\u0003\b\u0003\b\u0005\b\u008c\n\b\u0003\t\u0003\t",
    "\u0003\t\u0005\t\u0091\n\t\u0003\t\u0003\t\u0005\t\u0095\n\t\u0003\t",
    "\u0003\t\u0003\t\u0003\t\u0007\t\u009b\n\t\f\t\u000e\t\u009e\u000b\t",
    "\u0003\t\u0005\t\u00a1\n\t\u0003\t\u0003\t\u0003\t\u0007\t\u00a6\n\t",
    "\f\t\u000e\t\u00a9\u000b\t\u0003\t\u0005\t\u00ac\n\t\u0003\t\u0003\t",
    "\u0003\t\u0005\t\u00b1\n\t\u0003\t\u0003\t\u0003\t\u0005\t\u00b6\n\t",
    "\u0003\t\u0003\t\u0003\n\u0003\n\u0003\n\u0005\n\u00bd\n\n\u0003\n\u0003",
    "\n\u0007\n\u00c1\n\n\f\n\u000e\n\u00c4\u000b\n\u0003\n\u0003\n\u0003",
    "\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\f\u0005",
    "\f\u00ce\n\f\u0003\f\u0003\f\u0003\f\u0003\f\u0005\f\u00d4\n\f\u0003",
    "\f\u0003\f\u0005\f\u00d8\n\f\u0003\r\u0003\r\u0003\u000e\u0003\u000e",
    "\u0003\u000e\u0003\u000f\u0005\u000f\u00e0\n\u000f\u0003\u000f\u0003",
    "\u000f\u0003\u000f\u0005\u000f\u00e5\n\u000f\u0003\u000f\u0003\u000f",
    "\u0005\u000f\u00e9\n\u000f\u0003\u000f\u0003\u000f\u0005\u000f\u00ed",
    "\n\u000f\u0003\u0010\u0003\u0010\u0003\u0010\u0005\u0010\u00f2\n\u0010",
    "\u0003\u0010\u0003\u0010\u0005\u0010\u00f6\n\u0010\u0003\u0010\u0003",
    "\u0010\u0005\u0010\u00fa\n\u0010\u0003\u0010\u0005\u0010\u00fd\n\u0010",
    "\u0003\u0010\u0005\u0010\u0100\n\u0010\u0003\u0010\u0003\u0010\u0003",
    "\u0011\u0003\u0011\u0003\u0011\u0007\u0011\u0107\n\u0011\f\u0011\u000e",
    "\u0011\u010a\u000b\u0011\u0003\u0011\u0003\u0011\u0003\u0012\u0003\u0012",
    "\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0007\u0012\u0114\n",
    "\u0012\f\u0012\u000e\u0012\u0117\u000b\u0012\u0003\u0012\u0003\u0012",
    "\u0003\u0013\u0007\u0013\u011c\n\u0013\f\u0013\u000e\u0013\u011f\u000b",
    "\u0013\u0003\u0013\u0003\u0013\u0005\u0013\u0123\n\u0013\u0003\u0013",
    "\u0003\u0013\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014",
    "\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0016\u0003\u0016",
    "\u0003\u0016\u0005\u0016\u0133\n\u0016\u0003\u0016\u0003\u0016\u0003",
    "\u0017\u0003\u0017\u0003\u0017\u0003\u0017\u0007\u0017\u013b\n\u0017",
    "\f\u0017\u000e\u0017\u013e\u000b\u0017\u0003\u0017\u0003\u0017\u0003",
    "\u0018\u0003\u0018\u0003\u0018\u0003\u0018\u0003\u0018\u0003\u0018\u0003",
    "\u0018\u0003\u0018\u0007\u0018\u014a\n\u0018\f\u0018\u000e\u0018\u014d",
    "\u000b\u0018\u0003\u0018\u0005\u0018\u0150\n\u0018\u0003\u0019\u0003",
    "\u0019\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u0019\u0007\u0019\u0158",
    "\n\u0019\f\u0019\u000e\u0019\u015b\u000b\u0019\u0003\u0019\u0005\u0019",
    "\u015e\n\u0019\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001b\u0003",
    "\u001b\u0003\u001b\u0003\u001c\u0003\u001c\u0003\u001c\u0003\u001c\u0007",
    "\u001c\u016a\n\u001c\f\u001c\u000e\u001c\u016d\u000b\u001c\u0003\u001c",
    "\u0003\u001c\u0003\u001d\u0003\u001d\u0003\u001d\u0005\u001d\u0174\n",
    "\u001d\u0003\u001d\u0003\u001d\u0003\u001e\u0003\u001e\u0003\u001e\u0005",
    "\u001e\u017b\n\u001e\u0003\u001e\u0003\u001e\u0003\u001f\u0003\u001f",
    "\u0003\u001f\u0005\u001f\u0182\n\u001f\u0003\u001f\u0003\u001f\u0003",
    " \u0003 \u0003 \u0007 \u0189\n \f \u000e \u018c\u000b \u0003 \u0003",
    " \u0003!\u0003!\u0003!\u0003!\u0003!\u0003\"\u0003\"\u0003\"\u0003#",
    "\u0003#\u0003#\u0003$\u0003$\u0003%\u0003%\u0003%\u0005%\u01a0\n%\u0003",
    "%\u0003%\u0005%\u01a4\n%\u0003%\u0003%\u0003%\u0003%\u0003&\u0003&\u0003",
    "&\u0002\u0002\'\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016",
    "\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDFHJ\u0002\u0003\u0004\u0002",
    "\u0013\u0013\u001f\u001f\u0002\u01c3\u0002L\u0003\u0002\u0002\u0002",
    "\u0004_\u0003\u0002\u0002\u0002\u0006j\u0003\u0002\u0002\u0002\bp\u0003",
    "\u0002\u0002\u0002\n\u0080\u0003\u0002\u0002\u0002\f\u0083\u0003\u0002",
    "\u0002\u0002\u000e\u008b\u0003\u0002\u0002\u0002\u0010\u008d\u0003\u0002",
    "\u0002\u0002\u0012\u00b9\u0003\u0002\u0002\u0002\u0014\u00c7\u0003\u0002",
    "\u0002\u0002\u0016\u00cd\u0003\u0002\u0002\u0002\u0018\u00d9\u0003\u0002",
    "\u0002\u0002\u001a\u00db\u0003\u0002\u0002\u0002\u001c\u00df\u0003\u0002",
    "\u0002\u0002\u001e\u00ee\u0003\u0002\u0002\u0002 \u0103\u0003\u0002",
    "\u0002\u0002\"\u010d\u0003\u0002\u0002\u0002$\u011d\u0003\u0002\u0002",
    "\u0002&\u0126\u0003\u0002\u0002\u0002(\u012b\u0003\u0002\u0002\u0002",
    "*\u012f\u0003\u0002\u0002\u0002,\u0136\u0003\u0002\u0002\u0002.\u0141",
    "\u0003\u0002\u0002\u00020\u0151\u0003\u0002\u0002\u00022\u015f\u0003",
    "\u0002\u0002\u00024\u0162\u0003\u0002\u0002\u00026\u0165\u0003\u0002",
    "\u0002\u00028\u0170\u0003\u0002\u0002\u0002:\u0177\u0003\u0002\u0002",
    "\u0002<\u017e\u0003\u0002\u0002\u0002>\u0185\u0003\u0002\u0002\u0002",
    "@\u018f\u0003\u0002\u0002\u0002B\u0194\u0003\u0002\u0002\u0002D\u0197",
    "\u0003\u0002\u0002\u0002F\u019a\u0003\u0002\u0002\u0002H\u019c\u0003",
    "\u0002\u0002\u0002J\u01a9\u0003\u0002\u0002\u0002LM\u0007\u0003\u0002",
    "\u0002MO\u0007:\u0002\u0002NP\u00079\u0002\u0002ON\u0003\u0002\u0002",
    "\u0002OP\u0003\u0002\u0002\u0002PQ\u0003\u0002\u0002\u0002QZ\u0007\u0004",
    "\u0002\u0002RY\u0005H%\u0002SY\u0005\u000e\b\u0002TY\u0005\u001e\u0010",
    "\u0002UY\u0005\b\u0005\u0002VY\u0005\u0004\u0003\u0002WY\u00076\u0002",
    "\u0002XR\u0003\u0002\u0002\u0002XS\u0003\u0002\u0002\u0002XT\u0003\u0002",
    "\u0002\u0002XU\u0003\u0002\u0002\u0002XV\u0003\u0002\u0002\u0002XW\u0003",
    "\u0002\u0002\u0002Y\\\u0003\u0002\u0002\u0002ZX\u0003\u0002\u0002\u0002",
    "Z[\u0003\u0002\u0002\u0002[]\u0003\u0002\u0002\u0002\\Z\u0003\u0002",
    "\u0002\u0002]^\u0007\u0005\u0002\u0002^\u0003\u0003\u0002\u0002\u0002",
    "_`\u0007\u0006\u0002\u0002`a\u0007:\u0002\u0002ae\u0007\u0004\u0002",
    "\u0002bd\u0005\u0006\u0004\u0002cb\u0003\u0002\u0002\u0002dg\u0003\u0002",
    "\u0002\u0002ec\u0003\u0002\u0002\u0002ef\u0003\u0002\u0002\u0002fh\u0003",
    "\u0002\u0002\u0002ge\u0003\u0002\u0002\u0002hi\u0007\u0005\u0002\u0002",
    "i\u0005\u0003\u0002\u0002\u0002jk\u0007\u0007\u0002\u0002kl\u0007;\u0002",
    "\u0002ln\u0007:\u0002\u0002mo\u00079\u0002\u0002nm\u0003\u0002\u0002",
    "\u0002no\u0003\u0002\u0002\u0002o\u0007\u0003\u0002\u0002\u0002pq\u0007",
    "\b\u0002\u0002qs\u0007:\u0002\u0002rt\u00079\u0002\u0002sr\u0003\u0002",
    "\u0002\u0002st\u0003\u0002\u0002\u0002tu\u0003\u0002\u0002\u0002u{\u0007",
    "\u0004\u0002\u0002vz\u0005\n\u0006\u0002wz\u0005\f\u0007\u0002xz\u0005",
    "\u001c\u000f\u0002yv\u0003\u0002\u0002\u0002yw\u0003\u0002\u0002\u0002",
    "yx\u0003\u0002\u0002\u0002z}\u0003\u0002\u0002\u0002{y\u0003\u0002\u0002",
    "\u0002{|\u0003\u0002\u0002\u0002|~\u0003\u0002\u0002\u0002}{\u0003\u0002",
    "\u0002\u0002~\u007f\u0007\u0005\u0002\u0002\u007f\t\u0003\u0002\u0002",
    "\u0002\u0080\u0081\u0007\t\u0002\u0002\u0081\u0082\u0007:\u0002\u0002",
    "\u0082\u000b\u0003\u0002\u0002\u0002\u0083\u0084\u0007\n\u0002\u0002",
    "\u0084\u0085\u0007\u000b\u0002\u0002\u0085\u0086\u0007:\u0002\u0002",
    "\u0086\u0087\u0007\f\u0002\u0002\u0087\u0088\u0007:\u0002\u0002\u0088",
    "\r\u0003\u0002\u0002\u0002\u0089\u008c\u0005\u0010\t\u0002\u008a\u008c",
    "\u0005\u0012\n\u0002\u008b\u0089\u0003\u0002\u0002\u0002\u008b\u008a",
    "\u0003\u0002\u0002\u0002\u008c\u000f\u0003\u0002\u0002\u0002\u008d\u008e",
    "\u0007\r\u0002\u0002\u008e\u0090\u0007:\u0002\u0002\u008f\u0091\u0007",
    "9\u0002\u0002\u0090\u008f\u0003\u0002\u0002\u0002\u0090\u0091\u0003",
    "\u0002\u0002\u0002\u0091\u0094\u0003\u0002\u0002\u0002\u0092\u0093\u0007",
    "\u000e\u0002\u0002\u0093\u0095\u0007:\u0002\u0002\u0094\u0092\u0003",
    "\u0002\u0002\u0002\u0094\u0095\u0003\u0002\u0002\u0002\u0095\u0096\u0003",
    "\u0002\u0002\u0002\u0096\u00a0\u0007\u0004\u0002\u0002\u0097\u0098\u0007",
    "\u000f\u0002\u0002\u0098\u009c\u0007\u0004\u0002\u0002\u0099\u009b\u0005",
    "\u0016\f\u0002\u009a\u0099\u0003\u0002\u0002\u0002\u009b\u009e\u0003",
    "\u0002\u0002\u0002\u009c\u009a\u0003\u0002\u0002\u0002\u009c\u009d\u0003",
    "\u0002\u0002\u0002\u009d\u009f\u0003\u0002\u0002\u0002\u009e\u009c\u0003",
    "\u0002\u0002\u0002\u009f\u00a1\u0007\u0005\u0002\u0002\u00a0\u0097\u0003",
    "\u0002\u0002\u0002\u00a0\u00a1\u0003\u0002\u0002\u0002\u00a1\u00ab\u0003",
    "\u0002\u0002\u0002\u00a2\u00a3\u0007\u0010\u0002\u0002\u00a3\u00a7\u0007",
    "\u0004\u0002\u0002\u00a4\u00a6\u0005\u0016\f\u0002\u00a5\u00a4\u0003",
    "\u0002\u0002\u0002\u00a6\u00a9\u0003\u0002\u0002\u0002\u00a7\u00a5\u0003",
    "\u0002\u0002\u0002\u00a7\u00a8\u0003\u0002\u0002\u0002\u00a8\u00aa\u0003",
    "\u0002\u0002\u0002\u00a9\u00a7\u0003\u0002\u0002\u0002\u00aa\u00ac\u0007",
    "\u0005\u0002\u0002\u00ab\u00a2\u0003\u0002\u0002\u0002\u00ab\u00ac\u0003",
    "\u0002\u0002\u0002\u00ac\u00b0\u0003\u0002\u0002\u0002\u00ad\u00ae\u0007",
    "\u0011\u0002\u0002\u00ae\u00af\u0007\u0012\u0002\u0002\u00af\u00b1\u0005",
    "\u001a\u000e\u0002\u00b0\u00ad\u0003\u0002\u0002\u0002\u00b0\u00b1\u0003",
    "\u0002\u0002\u0002\u00b1\u00b5\u0003\u0002\u0002\u0002\u00b2\u00b3\u0007",
    "\u0013\u0002\u0002\u00b3\u00b4\u0007\u0012\u0002\u0002\u00b4\u00b6\u0005",
    "\u001a\u000e\u0002\u00b5\u00b2\u0003\u0002\u0002\u0002\u00b5\u00b6\u0003",
    "\u0002\u0002\u0002\u00b6\u00b7\u0003\u0002\u0002\u0002\u00b7\u00b8\u0007",
    "\u0005\u0002\u0002\u00b8\u0011\u0003\u0002\u0002\u0002\u00b9\u00ba\u0007",
    "\u0014\u0002\u0002\u00ba\u00bc\u0007:\u0002\u0002\u00bb\u00bd\u0007",
    "9\u0002\u0002\u00bc\u00bb\u0003\u0002\u0002\u0002\u00bc\u00bd\u0003",
    "\u0002\u0002\u0002\u00bd\u00be\u0003\u0002\u0002\u0002\u00be\u00c2\u0007",
    "\u0004\u0002\u0002\u00bf\u00c1\u0005\u0014\u000b\u0002\u00c0\u00bf\u0003",
    "\u0002\u0002\u0002\u00c1\u00c4\u0003\u0002\u0002\u0002\u00c2\u00c0\u0003",
    "\u0002\u0002\u0002\u00c2\u00c3\u0003\u0002\u0002\u0002\u00c3\u00c5\u0003",
    "\u0002\u0002\u0002\u00c4\u00c2\u0003\u0002\u0002\u0002\u00c5\u00c6\u0007",
    "\u0005\u0002\u0002\u00c6\u0013\u0003\u0002\u0002\u0002\u00c7\u00c8\u0007",
    "\u0015\u0002\u0002\u00c8\u00c9\u0007:\u0002\u0002\u00c9\u00ca\u0007",
    "\u0016\u0002\u0002\u00ca\u00cb\u0007:\u0002\u0002\u00cb\u0015\u0003",
    "\u0002\u0002\u0002\u00cc\u00ce\u0007\u0017\u0002\u0002\u00cd\u00cc\u0003",
    "\u0002\u0002\u0002\u00cd\u00ce\u0003\u0002\u0002\u0002\u00ce\u00cf\u0003",
    "\u0002\u0002\u0002\u00cf\u00d0\u0007\u0018\u0002\u0002\u00d0\u00d1\u0007",
    ":\u0002\u0002\u00d1\u00d3\u0007\u0019\u0002\u0002\u00d2\u00d4\u0005",
    "\u0018\r\u0002\u00d3\u00d2\u0003\u0002\u0002\u0002\u00d3\u00d4\u0003",
    "\u0002\u0002\u0002\u00d4\u00d5\u0003\u0002\u0002\u0002\u00d5\u00d7\u0007",
    "\u001a\u0002\u0002\u00d6\u00d8\u00079\u0002\u0002\u00d7\u00d6\u0003",
    "\u0002\u0002\u0002\u00d7\u00d8\u0003\u0002\u0002\u0002\u00d8\u0017\u0003",
    "\u0002\u0002\u0002\u00d9\u00da\u0007:\u0002\u0002\u00da\u0019\u0003",
    "\u0002\u0002\u0002\u00db\u00dc\u0007\u0004\u0002\u0002\u00dc\u00dd\u0007",
    "\u0005\u0002\u0002\u00dd\u001b\u0003\u0002\u0002\u0002\u00de\u00e0\u0007",
    "7\u0002\u0002\u00df\u00de\u0003\u0002\u0002\u0002\u00df\u00e0\u0003",
    "\u0002\u0002\u0002\u00e0\u00e1\u0003\u0002\u0002\u0002\u00e1\u00e8\u0007",
    "\u001b\u0002\u0002\u00e2\u00e4\u0007:\u0002\u0002\u00e3\u00e5\u0007",
    "8\u0002\u0002\u00e4\u00e3\u0003\u0002\u0002\u0002\u00e4\u00e5\u0003",
    "\u0002\u0002\u0002\u00e5\u00e6\u0003\u0002\u0002\u0002\u00e6\u00e9\u0007",
    "\u0016\u0002\u0002\u00e7\u00e9\u0007;\u0002\u0002\u00e8\u00e2\u0003",
    "\u0002\u0002\u0002\u00e8\u00e7\u0003\u0002\u0002\u0002\u00e9\u00ea\u0003",
    "\u0002\u0002\u0002\u00ea\u00ec\u0007:\u0002\u0002\u00eb\u00ed\u0007",
    "9\u0002\u0002\u00ec\u00eb\u0003\u0002\u0002\u0002\u00ec\u00ed\u0003",
    "\u0002\u0002\u0002\u00ed\u001d\u0003\u0002\u0002\u0002\u00ee\u00ef\u0007",
    "\u001c\u0002\u0002\u00ef\u00f1\u0007:\u0002\u0002\u00f0\u00f2\u0007",
    "9\u0002\u0002\u00f1\u00f0\u0003\u0002\u0002\u0002\u00f1\u00f2\u0003",
    "\u0002\u0002\u0002\u00f2\u00f5\u0003\u0002\u0002\u0002\u00f3\u00f4\u0007",
    "\u000e\u0002\u0002\u00f4\u00f6\u0007:\u0002\u0002\u00f5\u00f3\u0003",
    "\u0002\u0002\u0002\u00f5\u00f6\u0003\u0002\u0002\u0002\u00f6\u00f7\u0003",
    "\u0002\u0002\u0002\u00f7\u00f9\u0007\u0004\u0002\u0002\u00f8\u00fa\u0005",
    " \u0011\u0002\u00f9\u00f8\u0003\u0002\u0002\u0002\u00f9\u00fa\u0003",
    "\u0002\u0002\u0002\u00fa\u00fc\u0003\u0002\u0002\u0002\u00fb\u00fd\u0005",
    "\"\u0012\u0002\u00fc\u00fb\u0003\u0002\u0002\u0002\u00fc\u00fd\u0003",
    "\u0002\u0002\u0002\u00fd\u00ff\u0003\u0002\u0002\u0002\u00fe\u0100\u0005",
    "*\u0016\u0002\u00ff\u00fe\u0003\u0002\u0002\u0002\u00ff\u0100\u0003",
    "\u0002\u0002\u0002\u0100\u0101\u0003\u0002\u0002\u0002\u0101\u0102\u0007",
    "\u0005\u0002\u0002\u0102\u001f\u0003\u0002\u0002\u0002\u0103\u0104\u0007",
    "\u001d\u0002\u0002\u0104\u0108\u0007\u0004\u0002\u0002\u0105\u0107\u0005",
    "$\u0013\u0002\u0106\u0105\u0003\u0002\u0002\u0002\u0107\u010a\u0003",
    "\u0002\u0002\u0002\u0108\u0106\u0003\u0002\u0002\u0002\u0108\u0109\u0003",
    "\u0002\u0002\u0002\u0109\u010b\u0003\u0002\u0002\u0002\u010a\u0108\u0003",
    "\u0002\u0002\u0002\u010b\u010c\u0007\u0005\u0002\u0002\u010c!\u0003",
    "\u0002\u0002\u0002\u010d\u010e\u0007\u001e\u0002\u0002\u010e\u0115\u0007",
    "\u0004\u0002\u0002\u010f\u0114\u0005\u001c\u000f\u0002\u0110\u0114\u0005",
    "$\u0013\u0002\u0111\u0114\u0005&\u0014\u0002\u0112\u0114\u0005(\u0015",
    "\u0002\u0113\u010f\u0003\u0002\u0002\u0002\u0113\u0110\u0003\u0002\u0002",
    "\u0002\u0113\u0111\u0003\u0002\u0002\u0002\u0113\u0112\u0003\u0002\u0002",
    "\u0002\u0114\u0117\u0003\u0002\u0002\u0002\u0115\u0113\u0003\u0002\u0002",
    "\u0002\u0115\u0116\u0003\u0002\u0002\u0002\u0116\u0118\u0003\u0002\u0002",
    "\u0002\u0117\u0115\u0003\u0002\u0002\u0002\u0118\u0119\u0007\u0005\u0002",
    "\u0002\u0119#\u0003\u0002\u0002\u0002\u011a\u011c\t\u0002\u0002\u0002",
    "\u011b\u011a\u0003\u0002\u0002\u0002\u011c\u011f\u0003\u0002\u0002\u0002",
    "\u011d\u011b\u0003\u0002\u0002\u0002\u011d\u011e\u0003\u0002\u0002\u0002",
    "\u011e\u0120\u0003\u0002\u0002\u0002\u011f\u011d\u0003\u0002\u0002\u0002",
    "\u0120\u0122\u0007 \u0002\u0002\u0121\u0123\u0007;\u0002\u0002\u0122",
    "\u0121\u0003\u0002\u0002\u0002\u0122\u0123\u0003\u0002\u0002\u0002\u0123",
    "\u0124\u0003\u0002\u0002\u0002\u0124\u0125\u0007:\u0002\u0002\u0125",
    "%\u0003\u0002\u0002\u0002\u0126\u0127\u0007!\u0002\u0002\u0127\u0128",
    "\u0007:\u0002\u0002\u0128\u0129\u0007\"\u0002\u0002\u0129\u012a\u0007",
    ":\u0002\u0002\u012a\'\u0003\u0002\u0002\u0002\u012b\u012c\u0007#\u0002",
    "\u0002\u012c\u012d\u0007;\u0002\u0002\u012d\u012e\u0007:\u0002\u0002",
    "\u012e)\u0003\u0002\u0002\u0002\u012f\u0130\u0007$\u0002\u0002\u0130",
    "\u0132\u0007\u0004\u0002\u0002\u0131\u0133\u0005,\u0017\u0002\u0132",
    "\u0131\u0003\u0002\u0002\u0002\u0132\u0133\u0003\u0002\u0002\u0002\u0133",
    "\u0134\u0003\u0002\u0002\u0002\u0134\u0135\u0007\u0005\u0002\u0002\u0135",
    "+\u0003\u0002\u0002\u0002\u0136\u0137\u0007%\u0002\u0002\u0137\u013c",
    "\u0007\u0004\u0002\u0002\u0138\u013b\u0005.\u0018\u0002\u0139\u013b",
    "\u00050\u0019\u0002\u013a\u0138\u0003\u0002\u0002\u0002\u013a\u0139",
    "\u0003\u0002\u0002\u0002\u013b\u013e\u0003\u0002\u0002\u0002\u013c\u013a",
    "\u0003\u0002\u0002\u0002\u013c\u013d\u0003\u0002\u0002\u0002\u013d\u013f",
    "\u0003\u0002\u0002\u0002\u013e\u013c\u0003\u0002\u0002\u0002\u013f\u0140",
    "\u0007\u0005\u0002\u0002\u0140-\u0003\u0002\u0002\u0002\u0141\u0142",
    "\u0007&\u0002\u0002\u0142\u0143\u0007;\u0002\u0002\u0143\u0144\u0007",
    ":\u0002\u0002\u0144\u0145\u0007\'\u0002\u0002\u0145\u014f\u0007:\u0002",
    "\u0002\u0146\u014b\u0007\u0004\u0002\u0002\u0147\u014a\u0005> \u0002",
    "\u0148\u014a\u0005<\u001f\u0002\u0149\u0147\u0003\u0002\u0002\u0002",
    "\u0149\u0148\u0003\u0002\u0002\u0002\u014a\u014d\u0003\u0002\u0002\u0002",
    "\u014b\u0149\u0003\u0002\u0002\u0002\u014b\u014c\u0003\u0002\u0002\u0002",
    "\u014c\u014e\u0003\u0002\u0002\u0002\u014d\u014b\u0003\u0002\u0002\u0002",
    "\u014e\u0150\u0007\u0005\u0002\u0002\u014f\u0146\u0003\u0002\u0002\u0002",
    "\u014f\u0150\u0003\u0002\u0002\u0002\u0150/\u0003\u0002\u0002\u0002",
    "\u0151\u0152\u0007(\u0002\u0002\u0152\u015d\u0007:\u0002\u0002\u0153",
    "\u0159\u0007\u0004\u0002\u0002\u0154\u0158\u00056\u001c\u0002\u0155",
    "\u0158\u00058\u001d\u0002\u0156\u0158\u0005:\u001e\u0002\u0157\u0154",
    "\u0003\u0002\u0002\u0002\u0157\u0155\u0003\u0002\u0002\u0002\u0157\u0156",
    "\u0003\u0002\u0002\u0002\u0158\u015b\u0003\u0002\u0002\u0002\u0159\u0157",
    "\u0003\u0002\u0002\u0002\u0159\u015a\u0003\u0002\u0002\u0002\u015a\u015c",
    "\u0003\u0002\u0002\u0002\u015b\u0159\u0003\u0002\u0002\u0002\u015c\u015e",
    "\u0007\u0005\u0002\u0002\u015d\u0153\u0003\u0002\u0002\u0002\u015d\u015e",
    "\u0003\u0002\u0002\u0002\u015e1\u0003\u0002\u0002\u0002\u015f\u0160",
    "\u0007)\u0002\u0002\u0160\u0161\u0007:\u0002\u0002\u01613\u0003\u0002",
    "\u0002\u0002\u0162\u0163\u0007*\u0002\u0002\u0163\u0164\u0007:\u0002",
    "\u0002\u01645\u0003\u0002\u0002\u0002\u0165\u0166\u0007+\u0002\u0002",
    "\u0166\u016b\u0007\u0004\u0002\u0002\u0167\u016a\u0005.\u0018\u0002",
    "\u0168\u016a\u00050\u0019\u0002\u0169\u0167\u0003\u0002\u0002\u0002",
    "\u0169\u0168\u0003\u0002\u0002\u0002\u016a\u016d\u0003\u0002\u0002\u0002",
    "\u016b\u0169\u0003\u0002\u0002\u0002\u016b\u016c\u0003\u0002\u0002\u0002",
    "\u016c\u016e\u0003\u0002\u0002\u0002\u016d\u016b\u0003\u0002\u0002\u0002",
    "\u016e\u016f\u0007\u0005\u0002\u0002\u016f7\u0003\u0002\u0002\u0002",
    "\u0170\u0171\u0007,\u0002\u0002\u0171\u0173\u0007\u0004\u0002\u0002",
    "\u0172\u0174\u0005F$\u0002\u0173\u0172\u0003\u0002\u0002\u0002\u0173",
    "\u0174\u0003\u0002\u0002\u0002\u0174\u0175\u0003\u0002\u0002\u0002\u0175",
    "\u0176\u0007\u0005\u0002\u0002\u01769\u0003\u0002\u0002\u0002\u0177",
    "\u0178\u0007-\u0002\u0002\u0178\u017a\u0007\u0004\u0002\u0002\u0179",
    "\u017b\u0005F$\u0002\u017a\u0179\u0003\u0002\u0002\u0002\u017a\u017b",
    "\u0003\u0002\u0002\u0002\u017b\u017c\u0003\u0002\u0002\u0002\u017c\u017d",
    "\u0007\u0005\u0002\u0002\u017d;\u0003\u0002\u0002\u0002\u017e\u017f",
    "\u0007.\u0002\u0002\u017f\u0181\u0007\u0004\u0002\u0002\u0180\u0182",
    "\u0005F$\u0002\u0181\u0180\u0003\u0002\u0002\u0002\u0181\u0182\u0003",
    "\u0002\u0002\u0002\u0182\u0183\u0003\u0002\u0002\u0002\u0183\u0184\u0007",
    "\u0005\u0002\u0002\u0184=\u0003\u0002\u0002\u0002\u0185\u0186\u0007",
    "/\u0002\u0002\u0186\u018a\u0007\u0004\u0002\u0002\u0187\u0189\u0005",
    "@!\u0002\u0188\u0187\u0003\u0002\u0002\u0002\u0189\u018c\u0003\u0002",
    "\u0002\u0002\u018a\u0188\u0003\u0002\u0002\u0002\u018a\u018b\u0003\u0002",
    "\u0002\u0002\u018b\u018d\u0003\u0002\u0002\u0002\u018c\u018a\u0003\u0002",
    "\u0002\u0002\u018d\u018e\u0007\u0005\u0002\u0002\u018e?\u0003\u0002",
    "\u0002\u0002\u018f\u0190\u00070\u0002\u0002\u0190\u0191\u0007;\u0002",
    "\u0002\u0191\u0192\u0007:\u0002\u0002\u0192\u0193\u00071\u0002\u0002",
    "\u0193A\u0003\u0002\u0002\u0002\u0194\u0195\u00072\u0002\u0002\u0195",
    "\u0196\u0007:\u0002\u0002\u0196C\u0003\u0002\u0002\u0002\u0197\u0198",
    "\u00073\u0002\u0002\u0198\u0199\u0007:\u0002\u0002\u0199E\u0003\u0002",
    "\u0002\u0002\u019a\u019b\u0007<\u0002\u0002\u019bG\u0003\u0002\u0002",
    "\u0002\u019c\u019d\u00074\u0002\u0002\u019d\u019f\u0007:\u0002\u0002",
    "\u019e\u01a0\u00079\u0002\u0002\u019f\u019e\u0003\u0002\u0002\u0002",
    "\u019f\u01a0\u0003\u0002\u0002\u0002\u01a0\u01a3\u0003\u0002\u0002\u0002",
    "\u01a1\u01a2\u0007\u000e\u0002\u0002\u01a2\u01a4\u0007:\u0002\u0002",
    "\u01a3\u01a1\u0003\u0002\u0002\u0002\u01a3\u01a4\u0003\u0002\u0002\u0002",
    "\u01a4\u01a5\u0003\u0002\u0002\u0002\u01a5\u01a6\u0007\u0004\u0002\u0002",
    "\u01a6\u01a7\u0007:\u0002\u0002\u01a7\u01a8\u0007\u0005\u0002\u0002",
    "\u01a8I\u0003\u0002\u0002\u0002\u01a9\u01aa\u00075\u0002\u0002\u01aa",
    "K\u0003\u0002\u0002\u00027OXZensy{\u008b\u0090\u0094\u009c\u00a0\u00a7",
    "\u00ab\u00b0\u00b5\u00bc\u00c2\u00cd\u00d3\u00d7\u00df\u00e4\u00e8\u00ec",
    "\u00f1\u00f5\u00f9\u00fc\u00ff\u0108\u0113\u0115\u011d\u0122\u0132\u013a",
    "\u013c\u0149\u014b\u014f\u0157\u0159\u015d\u0169\u016b\u0173\u017a\u0181",
    "\u018a\u019f\u01a3"].join("");


var atn = new antlr4.atn.ATNDeserializer().deserialize(serializedATN);

var decisionsToDFA = atn.decisionToState.map( function(ds, index) { return new antlr4.dfa.DFA(ds, index); });

var sharedContextCache = new antlr4.PredictionContextCache();

var literalNames = [ null, "'RoomModel'", "'{'", "'}'", "'LogicalSystem'", 
                     "'SubSystemRef'", "'SubSystemClass'", "'LogicalThread'", 
                     "'LayerConnection'", "'ref'", "'satisfied_by'", "'ProtocolClass'", 
                     "'extends'", "'incoming'", "'outgoing'", "'regular'", 
                     "'PortClass'", "'conjugated'", "'CompoundProtocolClass'", 
                     "'SubProtocol'", "':'", "'private'", "'Message'", "'('", 
                     "')'", "'ActorRef'", "'ActorClass'", "'Interface'", 
                     "'Structure'", "'external'", "'Port'", "'Binding'", 
                     "'and'", "'SAP'", "'Behavior'", "'StateMachine'", "'Transition'", 
                     "'->'", "'State'", "'TransitionPoint'", "'ChoicePoint'", 
                     "'subgraph'", "'entry'", "'exit'", "'action'", "'triggers'", 
                     "'<'", "'>'", "'EntryPoint'", "'ExitPoint'", "'DataClass'", 
                     "'impotodo'", null, "'rtypetodo'" ];

var symbolicNames = [ null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, "ImportRoom", 
                      "ReferenceType", "MULTIPLICITY", "Documentation", 
                      "ID", "RoomName", "CodeString", "ML_COMMENT", "SL_COMMENT", 
                      "WS" ];

var ruleNames =  [ "roomModel", "logicalSystem", "subSystemRef", "subSystemClass", 
                   "logicalThread", "layerConnection", "generalProtocolClass", 
                   "protocolClass", "compoundProtocolClass", "subProtocol", 
                   "message", "varDecl", "portClass", "actorRef", "actorClass", 
                   "iinterface", "structure", "port", "binding", "sap", 
                   "behavior", "stateMachine", "transition", "sstate", "transitionPoint", 
                   "choicePoint", "subgraph", "entry", "exit", "action", 
                   "triggers", "trigger", "entryPoint", "exitPoint", "code", 
                   "dataClass", "importedFQN" ];

function RoomParser (input) {
	antlr4.Parser.call(this, input);
    this._interp = new antlr4.atn.ParserATNSimulator(this, atn, decisionsToDFA, sharedContextCache);
    this.ruleNames = ruleNames;
    this.literalNames = literalNames;
    this.symbolicNames = symbolicNames;
    return this;
}

RoomParser.prototype = Object.create(antlr4.Parser.prototype);
RoomParser.prototype.constructor = RoomParser;

Object.defineProperty(RoomParser.prototype, "atn", {
	get : function() {
		return atn;
	}
});

RoomParser.EOF = antlr4.Token.EOF;
RoomParser.T__0 = 1;
RoomParser.T__1 = 2;
RoomParser.T__2 = 3;
RoomParser.T__3 = 4;
RoomParser.T__4 = 5;
RoomParser.T__5 = 6;
RoomParser.T__6 = 7;
RoomParser.T__7 = 8;
RoomParser.T__8 = 9;
RoomParser.T__9 = 10;
RoomParser.T__10 = 11;
RoomParser.T__11 = 12;
RoomParser.T__12 = 13;
RoomParser.T__13 = 14;
RoomParser.T__14 = 15;
RoomParser.T__15 = 16;
RoomParser.T__16 = 17;
RoomParser.T__17 = 18;
RoomParser.T__18 = 19;
RoomParser.T__19 = 20;
RoomParser.T__20 = 21;
RoomParser.T__21 = 22;
RoomParser.T__22 = 23;
RoomParser.T__23 = 24;
RoomParser.T__24 = 25;
RoomParser.T__25 = 26;
RoomParser.T__26 = 27;
RoomParser.T__27 = 28;
RoomParser.T__28 = 29;
RoomParser.T__29 = 30;
RoomParser.T__30 = 31;
RoomParser.T__31 = 32;
RoomParser.T__32 = 33;
RoomParser.T__33 = 34;
RoomParser.T__34 = 35;
RoomParser.T__35 = 36;
RoomParser.T__36 = 37;
RoomParser.T__37 = 38;
RoomParser.T__38 = 39;
RoomParser.T__39 = 40;
RoomParser.T__40 = 41;
RoomParser.T__41 = 42;
RoomParser.T__42 = 43;
RoomParser.T__43 = 44;
RoomParser.T__44 = 45;
RoomParser.T__45 = 46;
RoomParser.T__46 = 47;
RoomParser.T__47 = 48;
RoomParser.T__48 = 49;
RoomParser.T__49 = 50;
RoomParser.T__50 = 51;
RoomParser.ImportRoom = 52;
RoomParser.ReferenceType = 53;
RoomParser.MULTIPLICITY = 54;
RoomParser.Documentation = 55;
RoomParser.ID = 56;
RoomParser.RoomName = 57;
RoomParser.CodeString = 58;
RoomParser.ML_COMMENT = 59;
RoomParser.SL_COMMENT = 60;
RoomParser.WS = 61;

RoomParser.RULE_roomModel = 0;
RoomParser.RULE_logicalSystem = 1;
RoomParser.RULE_subSystemRef = 2;
RoomParser.RULE_subSystemClass = 3;
RoomParser.RULE_logicalThread = 4;
RoomParser.RULE_layerConnection = 5;
RoomParser.RULE_generalProtocolClass = 6;
RoomParser.RULE_protocolClass = 7;
RoomParser.RULE_compoundProtocolClass = 8;
RoomParser.RULE_subProtocol = 9;
RoomParser.RULE_message = 10;
RoomParser.RULE_varDecl = 11;
RoomParser.RULE_portClass = 12;
RoomParser.RULE_actorRef = 13;
RoomParser.RULE_actorClass = 14;
RoomParser.RULE_iinterface = 15;
RoomParser.RULE_structure = 16;
RoomParser.RULE_port = 17;
RoomParser.RULE_binding = 18;
RoomParser.RULE_sap = 19;
RoomParser.RULE_behavior = 20;
RoomParser.RULE_stateMachine = 21;
RoomParser.RULE_transition = 22;
RoomParser.RULE_sstate = 23;
RoomParser.RULE_transitionPoint = 24;
RoomParser.RULE_choicePoint = 25;
RoomParser.RULE_subgraph = 26;
RoomParser.RULE_entry = 27;
RoomParser.RULE_exit = 28;
RoomParser.RULE_action = 29;
RoomParser.RULE_triggers = 30;
RoomParser.RULE_trigger = 31;
RoomParser.RULE_entryPoint = 32;
RoomParser.RULE_exitPoint = 33;
RoomParser.RULE_code = 34;
RoomParser.RULE_dataClass = 35;
RoomParser.RULE_importedFQN = 36;

function RoomModelContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_roomModel;
    return this;
}

RoomModelContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
RoomModelContext.prototype.constructor = RoomModelContext;

RoomModelContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

RoomModelContext.prototype.Documentation = function() {
    return this.getToken(RoomParser.Documentation, 0);
};

RoomModelContext.prototype.dataClass = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(DataClassContext);
    } else {
        return this.getTypedRuleContext(DataClassContext,i);
    }
};

RoomModelContext.prototype.generalProtocolClass = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(GeneralProtocolClassContext);
    } else {
        return this.getTypedRuleContext(GeneralProtocolClassContext,i);
    }
};

RoomModelContext.prototype.actorClass = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(ActorClassContext);
    } else {
        return this.getTypedRuleContext(ActorClassContext,i);
    }
};

RoomModelContext.prototype.subSystemClass = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(SubSystemClassContext);
    } else {
        return this.getTypedRuleContext(SubSystemClassContext,i);
    }
};

RoomModelContext.prototype.logicalSystem = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(LogicalSystemContext);
    } else {
        return this.getTypedRuleContext(LogicalSystemContext,i);
    }
};

RoomModelContext.prototype.ImportRoom = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(RoomParser.ImportRoom);
    } else {
        return this.getToken(RoomParser.ImportRoom, i);
    }
};


RoomModelContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterRoomModel(this);
	}
};

RoomModelContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitRoomModel(this);
	}
};




RoomParser.RoomModelContext = RoomModelContext;

RoomParser.prototype.roomModel = function() {

    var localctx = new RoomModelContext(this, this._ctx, this.state);
    this.enterRule(localctx, 0, RoomParser.RULE_roomModel);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 74;
        this.match(RoomParser.T__0);
        this.state = 75;
        this.match(RoomParser.ID);
        this.state = 77;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 76;
            this.match(RoomParser.Documentation);
        }

        this.state = 79;
        this.match(RoomParser.T__1);
        this.state = 88;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << RoomParser.T__3) | (1 << RoomParser.T__5) | (1 << RoomParser.T__10) | (1 << RoomParser.T__17) | (1 << RoomParser.T__25))) !== 0) || _la===RoomParser.T__49 || _la===RoomParser.ImportRoom) {
            this.state = 86;
            this._errHandler.sync(this);
            switch(this._input.LA(1)) {
            case RoomParser.T__49:
                this.state = 80;
                this.dataClass();
                break;
            case RoomParser.T__10:
            case RoomParser.T__17:
                this.state = 81;
                this.generalProtocolClass();
                break;
            case RoomParser.T__25:
                this.state = 82;
                this.actorClass();
                break;
            case RoomParser.T__5:
                this.state = 83;
                this.subSystemClass();
                break;
            case RoomParser.T__3:
                this.state = 84;
                this.logicalSystem();
                break;
            case RoomParser.ImportRoom:
                this.state = 85;
                this.match(RoomParser.ImportRoom);
                break;
            default:
                throw new antlr4.error.NoViableAltException(this);
            }
            this.state = 90;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 91;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function LogicalSystemContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_logicalSystem;
    return this;
}

LogicalSystemContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
LogicalSystemContext.prototype.constructor = LogicalSystemContext;

LogicalSystemContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

LogicalSystemContext.prototype.subSystemRef = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(SubSystemRefContext);
    } else {
        return this.getTypedRuleContext(SubSystemRefContext,i);
    }
};

LogicalSystemContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterLogicalSystem(this);
	}
};

LogicalSystemContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitLogicalSystem(this);
	}
};




RoomParser.LogicalSystemContext = LogicalSystemContext;

RoomParser.prototype.logicalSystem = function() {

    var localctx = new LogicalSystemContext(this, this._ctx, this.state);
    this.enterRule(localctx, 2, RoomParser.RULE_logicalSystem);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 93;
        this.match(RoomParser.T__3);
        this.state = 94;
        this.match(RoomParser.ID);
        this.state = 95;
        this.match(RoomParser.T__1);
        this.state = 99;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===RoomParser.T__4) {
            this.state = 96;
            this.subSystemRef();
            this.state = 101;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 102;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function SubSystemRefContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_subSystemRef;
    return this;
}

SubSystemRefContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
SubSystemRefContext.prototype.constructor = SubSystemRefContext;

SubSystemRefContext.prototype.RoomName = function() {
    return this.getToken(RoomParser.RoomName, 0);
};

SubSystemRefContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

SubSystemRefContext.prototype.Documentation = function() {
    return this.getToken(RoomParser.Documentation, 0);
};

SubSystemRefContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterSubSystemRef(this);
	}
};

SubSystemRefContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitSubSystemRef(this);
	}
};




RoomParser.SubSystemRefContext = SubSystemRefContext;

RoomParser.prototype.subSystemRef = function() {

    var localctx = new SubSystemRefContext(this, this._ctx, this.state);
    this.enterRule(localctx, 4, RoomParser.RULE_subSystemRef);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 104;
        this.match(RoomParser.T__4);
        this.state = 105;
        this.match(RoomParser.RoomName);
        this.state = 106;
        this.match(RoomParser.ID);
        this.state = 108;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 107;
            this.match(RoomParser.Documentation);
        }

    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function SubSystemClassContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_subSystemClass;
    return this;
}

SubSystemClassContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
SubSystemClassContext.prototype.constructor = SubSystemClassContext;

SubSystemClassContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

SubSystemClassContext.prototype.Documentation = function() {
    return this.getToken(RoomParser.Documentation, 0);
};

SubSystemClassContext.prototype.logicalThread = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(LogicalThreadContext);
    } else {
        return this.getTypedRuleContext(LogicalThreadContext,i);
    }
};

SubSystemClassContext.prototype.layerConnection = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(LayerConnectionContext);
    } else {
        return this.getTypedRuleContext(LayerConnectionContext,i);
    }
};

SubSystemClassContext.prototype.actorRef = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(ActorRefContext);
    } else {
        return this.getTypedRuleContext(ActorRefContext,i);
    }
};

SubSystemClassContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterSubSystemClass(this);
	}
};

SubSystemClassContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitSubSystemClass(this);
	}
};




RoomParser.SubSystemClassContext = SubSystemClassContext;

RoomParser.prototype.subSystemClass = function() {

    var localctx = new SubSystemClassContext(this, this._ctx, this.state);
    this.enterRule(localctx, 6, RoomParser.RULE_subSystemClass);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 110;
        this.match(RoomParser.T__5);
        this.state = 111;
        this.match(RoomParser.ID);
        this.state = 113;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 112;
            this.match(RoomParser.Documentation);
        }

        this.state = 115;
        this.match(RoomParser.T__1);
        this.state = 121;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << RoomParser.T__6) | (1 << RoomParser.T__7) | (1 << RoomParser.T__24))) !== 0) || _la===RoomParser.ReferenceType) {
            this.state = 119;
            this._errHandler.sync(this);
            switch(this._input.LA(1)) {
            case RoomParser.T__6:
                this.state = 116;
                this.logicalThread();
                break;
            case RoomParser.T__7:
                this.state = 117;
                this.layerConnection();
                break;
            case RoomParser.T__24:
            case RoomParser.ReferenceType:
                this.state = 118;
                this.actorRef();
                break;
            default:
                throw new antlr4.error.NoViableAltException(this);
            }
            this.state = 123;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 124;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function LogicalThreadContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_logicalThread;
    return this;
}

LogicalThreadContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
LogicalThreadContext.prototype.constructor = LogicalThreadContext;

LogicalThreadContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

LogicalThreadContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterLogicalThread(this);
	}
};

LogicalThreadContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitLogicalThread(this);
	}
};




RoomParser.LogicalThreadContext = LogicalThreadContext;

RoomParser.prototype.logicalThread = function() {

    var localctx = new LogicalThreadContext(this, this._ctx, this.state);
    this.enterRule(localctx, 8, RoomParser.RULE_logicalThread);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 126;
        this.match(RoomParser.T__6);
        this.state = 127;
        this.match(RoomParser.ID);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function LayerConnectionContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_layerConnection;
    return this;
}

LayerConnectionContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
LayerConnectionContext.prototype.constructor = LayerConnectionContext;

LayerConnectionContext.prototype.ID = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(RoomParser.ID);
    } else {
        return this.getToken(RoomParser.ID, i);
    }
};


LayerConnectionContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterLayerConnection(this);
	}
};

LayerConnectionContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitLayerConnection(this);
	}
};




RoomParser.LayerConnectionContext = LayerConnectionContext;

RoomParser.prototype.layerConnection = function() {

    var localctx = new LayerConnectionContext(this, this._ctx, this.state);
    this.enterRule(localctx, 10, RoomParser.RULE_layerConnection);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 129;
        this.match(RoomParser.T__7);
        this.state = 130;
        this.match(RoomParser.T__8);
        this.state = 131;
        this.match(RoomParser.ID);
        this.state = 132;
        this.match(RoomParser.T__9);
        this.state = 133;
        this.match(RoomParser.ID);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function GeneralProtocolClassContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_generalProtocolClass;
    return this;
}

GeneralProtocolClassContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
GeneralProtocolClassContext.prototype.constructor = GeneralProtocolClassContext;

GeneralProtocolClassContext.prototype.protocolClass = function() {
    return this.getTypedRuleContext(ProtocolClassContext,0);
};

GeneralProtocolClassContext.prototype.compoundProtocolClass = function() {
    return this.getTypedRuleContext(CompoundProtocolClassContext,0);
};

GeneralProtocolClassContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterGeneralProtocolClass(this);
	}
};

GeneralProtocolClassContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitGeneralProtocolClass(this);
	}
};




RoomParser.GeneralProtocolClassContext = GeneralProtocolClassContext;

RoomParser.prototype.generalProtocolClass = function() {

    var localctx = new GeneralProtocolClassContext(this, this._ctx, this.state);
    this.enterRule(localctx, 12, RoomParser.RULE_generalProtocolClass);
    try {
        this.state = 137;
        this._errHandler.sync(this);
        switch(this._input.LA(1)) {
        case RoomParser.T__10:
            this.enterOuterAlt(localctx, 1);
            this.state = 135;
            this.protocolClass();
            break;
        case RoomParser.T__17:
            this.enterOuterAlt(localctx, 2);
            this.state = 136;
            this.compoundProtocolClass();
            break;
        default:
            throw new antlr4.error.NoViableAltException(this);
        }
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function ProtocolClassContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_protocolClass;
    return this;
}

ProtocolClassContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ProtocolClassContext.prototype.constructor = ProtocolClassContext;

ProtocolClassContext.prototype.ID = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(RoomParser.ID);
    } else {
        return this.getToken(RoomParser.ID, i);
    }
};


ProtocolClassContext.prototype.Documentation = function() {
    return this.getToken(RoomParser.Documentation, 0);
};

ProtocolClassContext.prototype.portClass = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(PortClassContext);
    } else {
        return this.getTypedRuleContext(PortClassContext,i);
    }
};

ProtocolClassContext.prototype.message = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(MessageContext);
    } else {
        return this.getTypedRuleContext(MessageContext,i);
    }
};

ProtocolClassContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterProtocolClass(this);
	}
};

ProtocolClassContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitProtocolClass(this);
	}
};




RoomParser.ProtocolClassContext = ProtocolClassContext;

RoomParser.prototype.protocolClass = function() {

    var localctx = new ProtocolClassContext(this, this._ctx, this.state);
    this.enterRule(localctx, 14, RoomParser.RULE_protocolClass);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 139;
        this.match(RoomParser.T__10);
        this.state = 140;
        this.match(RoomParser.ID);
        this.state = 142;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 141;
            this.match(RoomParser.Documentation);
        }

        this.state = 146;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__11) {
            this.state = 144;
            this.match(RoomParser.T__11);
            this.state = 145;
            this.match(RoomParser.ID);
        }

        this.state = 148;
        this.match(RoomParser.T__1);
        this.state = 158;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__12) {
            this.state = 149;
            this.match(RoomParser.T__12);
            this.state = 150;
            this.match(RoomParser.T__1);
            this.state = 154;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            while(_la===RoomParser.T__20 || _la===RoomParser.T__21) {
                this.state = 151;
                this.message();
                this.state = 156;
                this._errHandler.sync(this);
                _la = this._input.LA(1);
            }
            this.state = 157;
            this.match(RoomParser.T__2);
        }

        this.state = 169;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__13) {
            this.state = 160;
            this.match(RoomParser.T__13);
            this.state = 161;
            this.match(RoomParser.T__1);
            this.state = 165;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            while(_la===RoomParser.T__20 || _la===RoomParser.T__21) {
                this.state = 162;
                this.message();
                this.state = 167;
                this._errHandler.sync(this);
                _la = this._input.LA(1);
            }
            this.state = 168;
            this.match(RoomParser.T__2);
        }

        this.state = 174;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__14) {
            this.state = 171;
            this.match(RoomParser.T__14);
            this.state = 172;
            this.match(RoomParser.T__15);
            this.state = 173;
            this.portClass();
        }

        this.state = 179;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__16) {
            this.state = 176;
            this.match(RoomParser.T__16);
            this.state = 177;
            this.match(RoomParser.T__15);
            this.state = 178;
            this.portClass();
        }

        this.state = 181;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function CompoundProtocolClassContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_compoundProtocolClass;
    return this;
}

CompoundProtocolClassContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
CompoundProtocolClassContext.prototype.constructor = CompoundProtocolClassContext;

CompoundProtocolClassContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

CompoundProtocolClassContext.prototype.Documentation = function() {
    return this.getToken(RoomParser.Documentation, 0);
};

CompoundProtocolClassContext.prototype.subProtocol = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(SubProtocolContext);
    } else {
        return this.getTypedRuleContext(SubProtocolContext,i);
    }
};

CompoundProtocolClassContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterCompoundProtocolClass(this);
	}
};

CompoundProtocolClassContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitCompoundProtocolClass(this);
	}
};




RoomParser.CompoundProtocolClassContext = CompoundProtocolClassContext;

RoomParser.prototype.compoundProtocolClass = function() {

    var localctx = new CompoundProtocolClassContext(this, this._ctx, this.state);
    this.enterRule(localctx, 16, RoomParser.RULE_compoundProtocolClass);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 183;
        this.match(RoomParser.T__17);
        this.state = 184;
        this.match(RoomParser.ID);
        this.state = 186;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 185;
            this.match(RoomParser.Documentation);
        }

        this.state = 188;
        this.match(RoomParser.T__1);
        this.state = 192;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===RoomParser.T__18) {
            this.state = 189;
            this.subProtocol();
            this.state = 194;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 195;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function SubProtocolContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_subProtocol;
    return this;
}

SubProtocolContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
SubProtocolContext.prototype.constructor = SubProtocolContext;

SubProtocolContext.prototype.ID = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(RoomParser.ID);
    } else {
        return this.getToken(RoomParser.ID, i);
    }
};


SubProtocolContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterSubProtocol(this);
	}
};

SubProtocolContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitSubProtocol(this);
	}
};




RoomParser.SubProtocolContext = SubProtocolContext;

RoomParser.prototype.subProtocol = function() {

    var localctx = new SubProtocolContext(this, this._ctx, this.state);
    this.enterRule(localctx, 18, RoomParser.RULE_subProtocol);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 197;
        this.match(RoomParser.T__18);
        this.state = 198;
        this.match(RoomParser.ID);
        this.state = 199;
        this.match(RoomParser.T__19);
        this.state = 200;
        this.match(RoomParser.ID);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function MessageContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_message;
    return this;
}

MessageContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
MessageContext.prototype.constructor = MessageContext;

MessageContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

MessageContext.prototype.varDecl = function() {
    return this.getTypedRuleContext(VarDeclContext,0);
};

MessageContext.prototype.Documentation = function() {
    return this.getToken(RoomParser.Documentation, 0);
};

MessageContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterMessage(this);
	}
};

MessageContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitMessage(this);
	}
};




RoomParser.MessageContext = MessageContext;

RoomParser.prototype.message = function() {

    var localctx = new MessageContext(this, this._ctx, this.state);
    this.enterRule(localctx, 20, RoomParser.RULE_message);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 203;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__20) {
            this.state = 202;
            this.match(RoomParser.T__20);
        }

        this.state = 205;
        this.match(RoomParser.T__21);
        this.state = 206;
        this.match(RoomParser.ID);
        this.state = 207;
        this.match(RoomParser.T__22);
        this.state = 209;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.ID) {
            this.state = 208;
            this.varDecl();
        }

        this.state = 211;
        this.match(RoomParser.T__23);
        this.state = 213;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 212;
            this.match(RoomParser.Documentation);
        }

    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function VarDeclContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_varDecl;
    return this;
}

VarDeclContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
VarDeclContext.prototype.constructor = VarDeclContext;

VarDeclContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

VarDeclContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterVarDecl(this);
	}
};

VarDeclContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitVarDecl(this);
	}
};




RoomParser.VarDeclContext = VarDeclContext;

RoomParser.prototype.varDecl = function() {

    var localctx = new VarDeclContext(this, this._ctx, this.state);
    this.enterRule(localctx, 22, RoomParser.RULE_varDecl);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 215;
        this.match(RoomParser.ID);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function PortClassContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_portClass;
    return this;
}

PortClassContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
PortClassContext.prototype.constructor = PortClassContext;


PortClassContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterPortClass(this);
	}
};

PortClassContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitPortClass(this);
	}
};




RoomParser.PortClassContext = PortClassContext;

RoomParser.prototype.portClass = function() {

    var localctx = new PortClassContext(this, this._ctx, this.state);
    this.enterRule(localctx, 24, RoomParser.RULE_portClass);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 217;
        this.match(RoomParser.T__1);
        this.state = 218;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function ActorRefContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_actorRef;
    return this;
}

ActorRefContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ActorRefContext.prototype.constructor = ActorRefContext;

ActorRefContext.prototype.ID = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(RoomParser.ID);
    } else {
        return this.getToken(RoomParser.ID, i);
    }
};


ActorRefContext.prototype.RoomName = function() {
    return this.getToken(RoomParser.RoomName, 0);
};

ActorRefContext.prototype.ReferenceType = function() {
    return this.getToken(RoomParser.ReferenceType, 0);
};

ActorRefContext.prototype.Documentation = function() {
    return this.getToken(RoomParser.Documentation, 0);
};

ActorRefContext.prototype.MULTIPLICITY = function() {
    return this.getToken(RoomParser.MULTIPLICITY, 0);
};

ActorRefContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterActorRef(this);
	}
};

ActorRefContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitActorRef(this);
	}
};




RoomParser.ActorRefContext = ActorRefContext;

RoomParser.prototype.actorRef = function() {

    var localctx = new ActorRefContext(this, this._ctx, this.state);
    this.enterRule(localctx, 26, RoomParser.RULE_actorRef);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 221;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.ReferenceType) {
            this.state = 220;
            this.match(RoomParser.ReferenceType);
        }

        this.state = 223;
        this.match(RoomParser.T__24);
        this.state = 230;
        this._errHandler.sync(this);
        switch(this._input.LA(1)) {
        case RoomParser.ID:
            this.state = 224;
            this.match(RoomParser.ID);
            this.state = 226;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if(_la===RoomParser.MULTIPLICITY) {
                this.state = 225;
                this.match(RoomParser.MULTIPLICITY);
            }

            this.state = 228;
            this.match(RoomParser.T__19);
            break;
        case RoomParser.RoomName:
            this.state = 229;
            this.match(RoomParser.RoomName);
            break;
        default:
            throw new antlr4.error.NoViableAltException(this);
        }
        this.state = 232;
        this.match(RoomParser.ID);
        this.state = 234;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 233;
            this.match(RoomParser.Documentation);
        }

    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function ActorClassContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_actorClass;
    return this;
}

ActorClassContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ActorClassContext.prototype.constructor = ActorClassContext;

ActorClassContext.prototype.ID = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(RoomParser.ID);
    } else {
        return this.getToken(RoomParser.ID, i);
    }
};


ActorClassContext.prototype.Documentation = function() {
    return this.getToken(RoomParser.Documentation, 0);
};

ActorClassContext.prototype.iinterface = function() {
    return this.getTypedRuleContext(IinterfaceContext,0);
};

ActorClassContext.prototype.structure = function() {
    return this.getTypedRuleContext(StructureContext,0);
};

ActorClassContext.prototype.behavior = function() {
    return this.getTypedRuleContext(BehaviorContext,0);
};

ActorClassContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterActorClass(this);
	}
};

ActorClassContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitActorClass(this);
	}
};




RoomParser.ActorClassContext = ActorClassContext;

RoomParser.prototype.actorClass = function() {

    var localctx = new ActorClassContext(this, this._ctx, this.state);
    this.enterRule(localctx, 28, RoomParser.RULE_actorClass);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 236;
        this.match(RoomParser.T__25);
        this.state = 237;
        this.match(RoomParser.ID);
        this.state = 239;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 238;
            this.match(RoomParser.Documentation);
        }

        this.state = 243;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__11) {
            this.state = 241;
            this.match(RoomParser.T__11);
            this.state = 242;
            this.match(RoomParser.ID);
        }

        this.state = 245;
        this.match(RoomParser.T__1);
        this.state = 247;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__26) {
            this.state = 246;
            this.iinterface();
        }

        this.state = 250;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__27) {
            this.state = 249;
            this.structure();
        }

        this.state = 253;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__33) {
            this.state = 252;
            this.behavior();
        }

        this.state = 255;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function IinterfaceContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_iinterface;
    return this;
}

IinterfaceContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
IinterfaceContext.prototype.constructor = IinterfaceContext;

IinterfaceContext.prototype.port = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(PortContext);
    } else {
        return this.getTypedRuleContext(PortContext,i);
    }
};

IinterfaceContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterIinterface(this);
	}
};

IinterfaceContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitIinterface(this);
	}
};




RoomParser.IinterfaceContext = IinterfaceContext;

RoomParser.prototype.iinterface = function() {

    var localctx = new IinterfaceContext(this, this._ctx, this.state);
    this.enterRule(localctx, 30, RoomParser.RULE_iinterface);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 257;
        this.match(RoomParser.T__26);
        this.state = 258;
        this.match(RoomParser.T__1);
        this.state = 262;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << RoomParser.T__16) | (1 << RoomParser.T__28) | (1 << RoomParser.T__29))) !== 0)) {
            this.state = 259;
            this.port();
            this.state = 264;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 265;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function StructureContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_structure;
    return this;
}

StructureContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
StructureContext.prototype.constructor = StructureContext;

StructureContext.prototype.actorRef = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(ActorRefContext);
    } else {
        return this.getTypedRuleContext(ActorRefContext,i);
    }
};

StructureContext.prototype.port = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(PortContext);
    } else {
        return this.getTypedRuleContext(PortContext,i);
    }
};

StructureContext.prototype.binding = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(BindingContext);
    } else {
        return this.getTypedRuleContext(BindingContext,i);
    }
};

StructureContext.prototype.sap = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(SapContext);
    } else {
        return this.getTypedRuleContext(SapContext,i);
    }
};

StructureContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterStructure(this);
	}
};

StructureContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitStructure(this);
	}
};




RoomParser.StructureContext = StructureContext;

RoomParser.prototype.structure = function() {

    var localctx = new StructureContext(this, this._ctx, this.state);
    this.enterRule(localctx, 32, RoomParser.RULE_structure);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 267;
        this.match(RoomParser.T__27);
        this.state = 268;
        this.match(RoomParser.T__1);
        this.state = 275;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << RoomParser.T__16) | (1 << RoomParser.T__24) | (1 << RoomParser.T__28) | (1 << RoomParser.T__29) | (1 << RoomParser.T__30))) !== 0) || _la===RoomParser.T__32 || _la===RoomParser.ReferenceType) {
            this.state = 273;
            this._errHandler.sync(this);
            switch(this._input.LA(1)) {
            case RoomParser.T__24:
            case RoomParser.ReferenceType:
                this.state = 269;
                this.actorRef();
                break;
            case RoomParser.T__16:
            case RoomParser.T__28:
            case RoomParser.T__29:
                this.state = 270;
                this.port();
                break;
            case RoomParser.T__30:
                this.state = 271;
                this.binding();
                break;
            case RoomParser.T__32:
                this.state = 272;
                this.sap();
                break;
            default:
                throw new antlr4.error.NoViableAltException(this);
            }
            this.state = 277;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 278;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function PortContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_port;
    return this;
}

PortContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
PortContext.prototype.constructor = PortContext;

PortContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

PortContext.prototype.RoomName = function() {
    return this.getToken(RoomParser.RoomName, 0);
};

PortContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterPort(this);
	}
};

PortContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitPort(this);
	}
};




RoomParser.PortContext = PortContext;

RoomParser.prototype.port = function() {

    var localctx = new PortContext(this, this._ctx, this.state);
    this.enterRule(localctx, 34, RoomParser.RULE_port);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 283;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===RoomParser.T__16 || _la===RoomParser.T__28) {
            this.state = 280;
            _la = this._input.LA(1);
            if(!(_la===RoomParser.T__16 || _la===RoomParser.T__28)) {
            this._errHandler.recoverInline(this);
            }
            else {
            	this._errHandler.reportMatch(this);
                this.consume();
            }
            this.state = 285;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 286;
        this.match(RoomParser.T__29);
        this.state = 288;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.RoomName) {
            this.state = 287;
            this.match(RoomParser.RoomName);
        }

        this.state = 290;
        this.match(RoomParser.ID);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function BindingContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_binding;
    return this;
}

BindingContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
BindingContext.prototype.constructor = BindingContext;

BindingContext.prototype.ID = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(RoomParser.ID);
    } else {
        return this.getToken(RoomParser.ID, i);
    }
};


BindingContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterBinding(this);
	}
};

BindingContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitBinding(this);
	}
};




RoomParser.BindingContext = BindingContext;

RoomParser.prototype.binding = function() {

    var localctx = new BindingContext(this, this._ctx, this.state);
    this.enterRule(localctx, 36, RoomParser.RULE_binding);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 292;
        this.match(RoomParser.T__30);
        this.state = 293;
        this.match(RoomParser.ID);
        this.state = 294;
        this.match(RoomParser.T__31);
        this.state = 295;
        this.match(RoomParser.ID);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function SapContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_sap;
    return this;
}

SapContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
SapContext.prototype.constructor = SapContext;

SapContext.prototype.RoomName = function() {
    return this.getToken(RoomParser.RoomName, 0);
};

SapContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

SapContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterSap(this);
	}
};

SapContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitSap(this);
	}
};




RoomParser.SapContext = SapContext;

RoomParser.prototype.sap = function() {

    var localctx = new SapContext(this, this._ctx, this.state);
    this.enterRule(localctx, 38, RoomParser.RULE_sap);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 297;
        this.match(RoomParser.T__32);
        this.state = 298;
        this.match(RoomParser.RoomName);
        this.state = 299;
        this.match(RoomParser.ID);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function BehaviorContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_behavior;
    return this;
}

BehaviorContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
BehaviorContext.prototype.constructor = BehaviorContext;

BehaviorContext.prototype.stateMachine = function() {
    return this.getTypedRuleContext(StateMachineContext,0);
};

BehaviorContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterBehavior(this);
	}
};

BehaviorContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitBehavior(this);
	}
};




RoomParser.BehaviorContext = BehaviorContext;

RoomParser.prototype.behavior = function() {

    var localctx = new BehaviorContext(this, this._ctx, this.state);
    this.enterRule(localctx, 40, RoomParser.RULE_behavior);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 301;
        this.match(RoomParser.T__33);
        this.state = 302;
        this.match(RoomParser.T__1);
        this.state = 304;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__34) {
            this.state = 303;
            this.stateMachine();
        }

        this.state = 306;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function StateMachineContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_stateMachine;
    return this;
}

StateMachineContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
StateMachineContext.prototype.constructor = StateMachineContext;

StateMachineContext.prototype.transition = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(TransitionContext);
    } else {
        return this.getTypedRuleContext(TransitionContext,i);
    }
};

StateMachineContext.prototype.sstate = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(SstateContext);
    } else {
        return this.getTypedRuleContext(SstateContext,i);
    }
};

StateMachineContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterStateMachine(this);
	}
};

StateMachineContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitStateMachine(this);
	}
};




RoomParser.StateMachineContext = StateMachineContext;

RoomParser.prototype.stateMachine = function() {

    var localctx = new StateMachineContext(this, this._ctx, this.state);
    this.enterRule(localctx, 42, RoomParser.RULE_stateMachine);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 308;
        this.match(RoomParser.T__34);
        this.state = 309;
        this.match(RoomParser.T__1);
        this.state = 314;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===RoomParser.T__35 || _la===RoomParser.T__37) {
            this.state = 312;
            this._errHandler.sync(this);
            switch(this._input.LA(1)) {
            case RoomParser.T__35:
                this.state = 310;
                this.transition();
                break;
            case RoomParser.T__37:
                this.state = 311;
                this.sstate();
                break;
            default:
                throw new antlr4.error.NoViableAltException(this);
            }
            this.state = 316;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 317;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function TransitionContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_transition;
    return this;
}

TransitionContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
TransitionContext.prototype.constructor = TransitionContext;

TransitionContext.prototype.RoomName = function() {
    return this.getToken(RoomParser.RoomName, 0);
};

TransitionContext.prototype.ID = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(RoomParser.ID);
    } else {
        return this.getToken(RoomParser.ID, i);
    }
};


TransitionContext.prototype.triggers = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(TriggersContext);
    } else {
        return this.getTypedRuleContext(TriggersContext,i);
    }
};

TransitionContext.prototype.action = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(ActionContext);
    } else {
        return this.getTypedRuleContext(ActionContext,i);
    }
};

TransitionContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterTransition(this);
	}
};

TransitionContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitTransition(this);
	}
};




RoomParser.TransitionContext = TransitionContext;

RoomParser.prototype.transition = function() {

    var localctx = new TransitionContext(this, this._ctx, this.state);
    this.enterRule(localctx, 44, RoomParser.RULE_transition);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 319;
        this.match(RoomParser.T__35);
        this.state = 320;
        this.match(RoomParser.RoomName);
        this.state = 321;
        this.match(RoomParser.ID);
        this.state = 322;
        this.match(RoomParser.T__36);
        this.state = 323;
        this.match(RoomParser.ID);
        this.state = 333;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__1) {
            this.state = 324;
            this.match(RoomParser.T__1);
            this.state = 329;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            while(_la===RoomParser.T__43 || _la===RoomParser.T__44) {
                this.state = 327;
                this._errHandler.sync(this);
                switch(this._input.LA(1)) {
                case RoomParser.T__44:
                    this.state = 325;
                    this.triggers();
                    break;
                case RoomParser.T__43:
                    this.state = 326;
                    this.action();
                    break;
                default:
                    throw new antlr4.error.NoViableAltException(this);
                }
                this.state = 331;
                this._errHandler.sync(this);
                _la = this._input.LA(1);
            }
            this.state = 332;
            this.match(RoomParser.T__2);
        }

    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function SstateContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_sstate;
    return this;
}

SstateContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
SstateContext.prototype.constructor = SstateContext;

SstateContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

SstateContext.prototype.subgraph = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(SubgraphContext);
    } else {
        return this.getTypedRuleContext(SubgraphContext,i);
    }
};

SstateContext.prototype.entry = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(EntryContext);
    } else {
        return this.getTypedRuleContext(EntryContext,i);
    }
};

SstateContext.prototype.exit = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(ExitContext);
    } else {
        return this.getTypedRuleContext(ExitContext,i);
    }
};

SstateContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterSstate(this);
	}
};

SstateContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitSstate(this);
	}
};




RoomParser.SstateContext = SstateContext;

RoomParser.prototype.sstate = function() {

    var localctx = new SstateContext(this, this._ctx, this.state);
    this.enterRule(localctx, 46, RoomParser.RULE_sstate);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 335;
        this.match(RoomParser.T__37);
        this.state = 336;
        this.match(RoomParser.ID);
        this.state = 347;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__1) {
            this.state = 337;
            this.match(RoomParser.T__1);
            this.state = 343;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            while(((((_la - 41)) & ~0x1f) == 0 && ((1 << (_la - 41)) & ((1 << (RoomParser.T__40 - 41)) | (1 << (RoomParser.T__41 - 41)) | (1 << (RoomParser.T__42 - 41)))) !== 0)) {
                this.state = 341;
                this._errHandler.sync(this);
                switch(this._input.LA(1)) {
                case RoomParser.T__40:
                    this.state = 338;
                    this.subgraph();
                    break;
                case RoomParser.T__41:
                    this.state = 339;
                    this.entry();
                    break;
                case RoomParser.T__42:
                    this.state = 340;
                    this.exit();
                    break;
                default:
                    throw new antlr4.error.NoViableAltException(this);
                }
                this.state = 345;
                this._errHandler.sync(this);
                _la = this._input.LA(1);
            }
            this.state = 346;
            this.match(RoomParser.T__2);
        }

    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function TransitionPointContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_transitionPoint;
    return this;
}

TransitionPointContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
TransitionPointContext.prototype.constructor = TransitionPointContext;

TransitionPointContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

TransitionPointContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterTransitionPoint(this);
	}
};

TransitionPointContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitTransitionPoint(this);
	}
};




RoomParser.TransitionPointContext = TransitionPointContext;

RoomParser.prototype.transitionPoint = function() {

    var localctx = new TransitionPointContext(this, this._ctx, this.state);
    this.enterRule(localctx, 48, RoomParser.RULE_transitionPoint);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 349;
        this.match(RoomParser.T__38);
        this.state = 350;
        this.match(RoomParser.ID);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function ChoicePointContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_choicePoint;
    return this;
}

ChoicePointContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ChoicePointContext.prototype.constructor = ChoicePointContext;

ChoicePointContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

ChoicePointContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterChoicePoint(this);
	}
};

ChoicePointContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitChoicePoint(this);
	}
};




RoomParser.ChoicePointContext = ChoicePointContext;

RoomParser.prototype.choicePoint = function() {

    var localctx = new ChoicePointContext(this, this._ctx, this.state);
    this.enterRule(localctx, 50, RoomParser.RULE_choicePoint);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 352;
        this.match(RoomParser.T__39);
        this.state = 353;
        this.match(RoomParser.ID);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function SubgraphContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_subgraph;
    return this;
}

SubgraphContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
SubgraphContext.prototype.constructor = SubgraphContext;

SubgraphContext.prototype.transition = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(TransitionContext);
    } else {
        return this.getTypedRuleContext(TransitionContext,i);
    }
};

SubgraphContext.prototype.sstate = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(SstateContext);
    } else {
        return this.getTypedRuleContext(SstateContext,i);
    }
};

SubgraphContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterSubgraph(this);
	}
};

SubgraphContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitSubgraph(this);
	}
};




RoomParser.SubgraphContext = SubgraphContext;

RoomParser.prototype.subgraph = function() {

    var localctx = new SubgraphContext(this, this._ctx, this.state);
    this.enterRule(localctx, 52, RoomParser.RULE_subgraph);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 355;
        this.match(RoomParser.T__40);
        this.state = 356;
        this.match(RoomParser.T__1);
        this.state = 361;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===RoomParser.T__35 || _la===RoomParser.T__37) {
            this.state = 359;
            this._errHandler.sync(this);
            switch(this._input.LA(1)) {
            case RoomParser.T__35:
                this.state = 357;
                this.transition();
                break;
            case RoomParser.T__37:
                this.state = 358;
                this.sstate();
                break;
            default:
                throw new antlr4.error.NoViableAltException(this);
            }
            this.state = 363;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 364;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function EntryContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_entry;
    return this;
}

EntryContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
EntryContext.prototype.constructor = EntryContext;

EntryContext.prototype.code = function() {
    return this.getTypedRuleContext(CodeContext,0);
};

EntryContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterEntry(this);
	}
};

EntryContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitEntry(this);
	}
};




RoomParser.EntryContext = EntryContext;

RoomParser.prototype.entry = function() {

    var localctx = new EntryContext(this, this._ctx, this.state);
    this.enterRule(localctx, 54, RoomParser.RULE_entry);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 366;
        this.match(RoomParser.T__41);
        this.state = 367;
        this.match(RoomParser.T__1);
        this.state = 369;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.CodeString) {
            this.state = 368;
            this.code();
        }

        this.state = 371;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function ExitContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_exit;
    return this;
}

ExitContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ExitContext.prototype.constructor = ExitContext;

ExitContext.prototype.code = function() {
    return this.getTypedRuleContext(CodeContext,0);
};

ExitContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterExit(this);
	}
};

ExitContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitExit(this);
	}
};




RoomParser.ExitContext = ExitContext;

RoomParser.prototype.exit = function() {

    var localctx = new ExitContext(this, this._ctx, this.state);
    this.enterRule(localctx, 56, RoomParser.RULE_exit);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 373;
        this.match(RoomParser.T__42);
        this.state = 374;
        this.match(RoomParser.T__1);
        this.state = 376;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.CodeString) {
            this.state = 375;
            this.code();
        }

        this.state = 378;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function ActionContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_action;
    return this;
}

ActionContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ActionContext.prototype.constructor = ActionContext;

ActionContext.prototype.code = function() {
    return this.getTypedRuleContext(CodeContext,0);
};

ActionContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterAction(this);
	}
};

ActionContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitAction(this);
	}
};




RoomParser.ActionContext = ActionContext;

RoomParser.prototype.action = function() {

    var localctx = new ActionContext(this, this._ctx, this.state);
    this.enterRule(localctx, 58, RoomParser.RULE_action);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 380;
        this.match(RoomParser.T__43);
        this.state = 381;
        this.match(RoomParser.T__1);
        this.state = 383;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.CodeString) {
            this.state = 382;
            this.code();
        }

        this.state = 385;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function TriggersContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_triggers;
    return this;
}

TriggersContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
TriggersContext.prototype.constructor = TriggersContext;

TriggersContext.prototype.trigger = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(TriggerContext);
    } else {
        return this.getTypedRuleContext(TriggerContext,i);
    }
};

TriggersContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterTriggers(this);
	}
};

TriggersContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitTriggers(this);
	}
};




RoomParser.TriggersContext = TriggersContext;

RoomParser.prototype.triggers = function() {

    var localctx = new TriggersContext(this, this._ctx, this.state);
    this.enterRule(localctx, 60, RoomParser.RULE_triggers);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 387;
        this.match(RoomParser.T__44);
        this.state = 388;
        this.match(RoomParser.T__1);
        this.state = 392;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===RoomParser.T__45) {
            this.state = 389;
            this.trigger();
            this.state = 394;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 395;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function TriggerContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_trigger;
    return this;
}

TriggerContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
TriggerContext.prototype.constructor = TriggerContext;

TriggerContext.prototype.RoomName = function() {
    return this.getToken(RoomParser.RoomName, 0);
};

TriggerContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

TriggerContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterTrigger(this);
	}
};

TriggerContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitTrigger(this);
	}
};




RoomParser.TriggerContext = TriggerContext;

RoomParser.prototype.trigger = function() {

    var localctx = new TriggerContext(this, this._ctx, this.state);
    this.enterRule(localctx, 62, RoomParser.RULE_trigger);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 397;
        this.match(RoomParser.T__45);
        this.state = 398;
        this.match(RoomParser.RoomName);
        this.state = 399;
        this.match(RoomParser.ID);
        this.state = 400;
        this.match(RoomParser.T__46);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function EntryPointContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_entryPoint;
    return this;
}

EntryPointContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
EntryPointContext.prototype.constructor = EntryPointContext;

EntryPointContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

EntryPointContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterEntryPoint(this);
	}
};

EntryPointContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitEntryPoint(this);
	}
};




RoomParser.EntryPointContext = EntryPointContext;

RoomParser.prototype.entryPoint = function() {

    var localctx = new EntryPointContext(this, this._ctx, this.state);
    this.enterRule(localctx, 64, RoomParser.RULE_entryPoint);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 402;
        this.match(RoomParser.T__47);
        this.state = 403;
        this.match(RoomParser.ID);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function ExitPointContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_exitPoint;
    return this;
}

ExitPointContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ExitPointContext.prototype.constructor = ExitPointContext;

ExitPointContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
};

ExitPointContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterExitPoint(this);
	}
};

ExitPointContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitExitPoint(this);
	}
};




RoomParser.ExitPointContext = ExitPointContext;

RoomParser.prototype.exitPoint = function() {

    var localctx = new ExitPointContext(this, this._ctx, this.state);
    this.enterRule(localctx, 66, RoomParser.RULE_exitPoint);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 405;
        this.match(RoomParser.T__48);
        this.state = 406;
        this.match(RoomParser.ID);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function CodeContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_code;
    return this;
}

CodeContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
CodeContext.prototype.constructor = CodeContext;

CodeContext.prototype.CodeString = function() {
    return this.getToken(RoomParser.CodeString, 0);
};

CodeContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterCode(this);
	}
};

CodeContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitCode(this);
	}
};




RoomParser.CodeContext = CodeContext;

RoomParser.prototype.code = function() {

    var localctx = new CodeContext(this, this._ctx, this.state);
    this.enterRule(localctx, 68, RoomParser.RULE_code);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 408;
        this.match(RoomParser.CodeString);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function DataClassContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_dataClass;
    return this;
}

DataClassContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
DataClassContext.prototype.constructor = DataClassContext;

DataClassContext.prototype.ID = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(RoomParser.ID);
    } else {
        return this.getToken(RoomParser.ID, i);
    }
};


DataClassContext.prototype.Documentation = function() {
    return this.getToken(RoomParser.Documentation, 0);
};

DataClassContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterDataClass(this);
	}
};

DataClassContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitDataClass(this);
	}
};




RoomParser.DataClassContext = DataClassContext;

RoomParser.prototype.dataClass = function() {

    var localctx = new DataClassContext(this, this._ctx, this.state);
    this.enterRule(localctx, 70, RoomParser.RULE_dataClass);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 410;
        this.match(RoomParser.T__49);
        this.state = 411;
        this.match(RoomParser.ID);
        this.state = 413;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 412;
            this.match(RoomParser.Documentation);
        }

        this.state = 417;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__11) {
            this.state = 415;
            this.match(RoomParser.T__11);
            this.state = 416;
            this.match(RoomParser.ID);
        }

        this.state = 419;
        this.match(RoomParser.T__1);
        this.state = 420;
        this.match(RoomParser.ID);
        this.state = 421;
        this.match(RoomParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function ImportedFQNContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = RoomParser.RULE_importedFQN;
    return this;
}

ImportedFQNContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ImportedFQNContext.prototype.constructor = ImportedFQNContext;


ImportedFQNContext.prototype.enterRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.enterImportedFQN(this);
	}
};

ImportedFQNContext.prototype.exitRule = function(listener) {
    if(listener instanceof RoomListener ) {
        listener.exitImportedFQN(this);
	}
};




RoomParser.ImportedFQNContext = ImportedFQNContext;

RoomParser.prototype.importedFQN = function() {

    var localctx = new ImportedFQNContext(this, this._ctx, this.state);
    this.enterRule(localctx, 72, RoomParser.RULE_importedFQN);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 423;
        this.match(RoomParser.T__50);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};


exports.RoomParser = RoomParser;
