// Generated from Room.g4 by ANTLR 4.7
// jshint ignore: start
var antlr4 = require('xholon/lib/antlr4/index');
var RoomListener = require('./RoomListener').RoomListener;
var grammarFileName = "Room.g4";

var serializedATN = ["\u0003\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964",
    "\u0003/\u012a\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t",
    "\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t\u0007\u0004",
    "\b\t\b\u0004\t\t\t\u0004\n\t\n\u0004\u000b\t\u000b\u0004\f\t\f\u0004",
    "\r\t\r\u0004\u000e\t\u000e\u0004\u000f\t\u000f\u0004\u0010\t\u0010\u0004",
    "\u0011\t\u0011\u0004\u0012\t\u0012\u0004\u0013\t\u0013\u0004\u0014\t",
    "\u0014\u0004\u0015\t\u0015\u0004\u0016\t\u0016\u0004\u0017\t\u0017\u0004",
    "\u0018\t\u0018\u0003\u0002\u0003\u0002\u0003\u0002\u0005\u00024\n\u0002",
    "\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002",
    "\u0003\u0002\u0007\u0002=\n\u0002\f\u0002\u000e\u0002@\u000b\u0002\u0003",
    "\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0007",
    "\u0003H\n\u0003\f\u0003\u000e\u0003K\u000b\u0003\u0003\u0003\u0003\u0003",
    "\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005\u0004S\n\u0004",
    "\u0003\u0005\u0003\u0005\u0003\u0005\u0005\u0005X\n\u0005\u0003\u0005",
    "\u0003\u0005\u0003\u0005\u0003\u0005\u0007\u0005^\n\u0005\f\u0005\u000e",
    "\u0005a\u000b\u0005\u0003\u0005\u0003\u0005\u0003\u0006\u0003\u0006",
    "\u0003\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0003\u0007\u0003\u0007",
    "\u0003\u0007\u0003\b\u0003\b\u0005\bp\n\b\u0003\t\u0003\t\u0003\t\u0005",
    "\tu\n\t\u0003\t\u0003\t\u0005\ty\n\t\u0003\t\u0003\t\u0003\t\u0003\t",
    "\u0007\t\u007f\n\t\f\t\u000e\t\u0082\u000b\t\u0003\t\u0005\t\u0085\n",
    "\t\u0003\t\u0003\t\u0003\t\u0007\t\u008a\n\t\f\t\u000e\t\u008d\u000b",
    "\t\u0003\t\u0005\t\u0090\n\t\u0003\t\u0003\t\u0003\t\u0005\t\u0095\n",
    "\t\u0003\t\u0003\t\u0003\t\u0005\t\u009a\n\t\u0003\t\u0003\t\u0003\n",
    "\u0003\n\u0003\n\u0005\n\u00a1\n\n\u0003\n\u0003\n\u0007\n\u00a5\n\n",
    "\f\n\u000e\n\u00a8\u000b\n\u0003\n\u0003\n\u0003\u000b\u0003\u000b\u0003",
    "\u000b\u0003\u000b\u0003\u000b\u0003\f\u0005\f\u00b2\n\f\u0003\f\u0003",
    "\f\u0003\f\u0003\f\u0005\f\u00b8\n\f\u0003\f\u0003\f\u0005\f\u00bc\n",
    "\f\u0003\r\u0003\r\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000f\u0005",
    "\u000f\u00c4\n\u000f\u0003\u000f\u0003\u000f\u0003\u000f\u0005\u000f",
    "\u00c9\n\u000f\u0003\u000f\u0003\u000f\u0005\u000f\u00cd\n\u000f\u0003",
    "\u000f\u0003\u000f\u0005\u000f\u00d1\n\u000f\u0003\u0010\u0003\u0010",
    "\u0003\u0010\u0005\u0010\u00d6\n\u0010\u0003\u0010\u0003\u0010\u0005",
    "\u0010\u00da\n\u0010\u0003\u0010\u0003\u0010\u0003\u0010\u0003\u0010",
    "\u0005\u0010\u00e0\n\u0010\u0003\u0010\u0005\u0010\u00e3\n\u0010\u0003",
    "\u0010\u0005\u0010\u00e6\n\u0010\u0003\u0010\u0003\u0010\u0003\u0011",
    "\u0003\u0011\u0003\u0011\u0007\u0011\u00ed\n\u0011\f\u0011\u000e\u0011",
    "\u00f0\u000b\u0011\u0003\u0011\u0003\u0011\u0003\u0012\u0003\u0012\u0003",
    "\u0012\u0005\u0012\u00f7\n\u0012\u0003\u0012\u0003\u0012\u0003\u0013",
    "\u0003\u0013\u0003\u0013\u0003\u0013\u0007\u0013\u00ff\n\u0013\f\u0013",
    "\u000e\u0013\u0102\u000b\u0013\u0003\u0013\u0003\u0013\u0003\u0014\u0003",
    "\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0005",
    "\u0014\u010d\n\u0014\u0003\u0014\u0003\u0014\u0003\u0015\u0003\u0015",
    "\u0003\u0015\u0003\u0016\u0003\u0016\u0003\u0016\u0005\u0016\u0117\n",
    "\u0016\u0003\u0016\u0003\u0016\u0003\u0017\u0003\u0017\u0003\u0017\u0005",
    "\u0017\u011e\n\u0017\u0003\u0017\u0003\u0017\u0005\u0017\u0122\n\u0017",
    "\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0018\u0003\u0018",
    "\u0003\u0018\u0002\u0002\u0019\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012",
    "\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.\u0002\u0002\u0002\u013e",
    "\u00020\u0003\u0002\u0002\u0002\u0004C\u0003\u0002\u0002\u0002\u0006",
    "N\u0003\u0002\u0002\u0002\bT\u0003\u0002\u0002\u0002\nd\u0003\u0002",
    "\u0002\u0002\fg\u0003\u0002\u0002\u0002\u000eo\u0003\u0002\u0002\u0002",
    "\u0010q\u0003\u0002\u0002\u0002\u0012\u009d\u0003\u0002\u0002\u0002",
    "\u0014\u00ab\u0003\u0002\u0002\u0002\u0016\u00b1\u0003\u0002\u0002\u0002",
    "\u0018\u00bd\u0003\u0002\u0002\u0002\u001a\u00bf\u0003\u0002\u0002\u0002",
    "\u001c\u00c3\u0003\u0002\u0002\u0002\u001e\u00d2\u0003\u0002\u0002\u0002",
    " \u00e9\u0003\u0002\u0002\u0002\"\u00f3\u0003\u0002\u0002\u0002$\u00fa",
    "\u0003\u0002\u0002\u0002&\u0105\u0003\u0002\u0002\u0002(\u0110\u0003",
    "\u0002\u0002\u0002*\u0113\u0003\u0002\u0002\u0002,\u011a\u0003\u0002",
    "\u0002\u0002.\u0127\u0003\u0002\u0002\u000201\u0007\u0003\u0002\u0002",
    "13\u0007+\u0002\u000224\u0007*\u0002\u000232\u0003\u0002\u0002\u0002",
    "34\u0003\u0002\u0002\u000245\u0003\u0002\u0002\u00025>\u0007\u0004\u0002",
    "\u00026=\u0005,\u0017\u00027=\u0005\u000e\b\u00028=\u0005\u001e\u0010",
    "\u00029=\u0005\b\u0005\u0002:=\u0005\u0004\u0003\u0002;=\u0007\'\u0002",
    "\u0002<6\u0003\u0002\u0002\u0002<7\u0003\u0002\u0002\u0002<8\u0003\u0002",
    "\u0002\u0002<9\u0003\u0002\u0002\u0002<:\u0003\u0002\u0002\u0002<;\u0003",
    "\u0002\u0002\u0002=@\u0003\u0002\u0002\u0002><\u0003\u0002\u0002\u0002",
    ">?\u0003\u0002\u0002\u0002?A\u0003\u0002\u0002\u0002@>\u0003\u0002\u0002",
    "\u0002AB\u0007\u0005\u0002\u0002B\u0003\u0003\u0002\u0002\u0002CD\u0007",
    "\u0006\u0002\u0002DE\u0007+\u0002\u0002EI\u0007\u0004\u0002\u0002FH",
    "\u0005\u0006\u0004\u0002GF\u0003\u0002\u0002\u0002HK\u0003\u0002\u0002",
    "\u0002IG\u0003\u0002\u0002\u0002IJ\u0003\u0002\u0002\u0002JL\u0003\u0002",
    "\u0002\u0002KI\u0003\u0002\u0002\u0002LM\u0007\u0005\u0002\u0002M\u0005",
    "\u0003\u0002\u0002\u0002NO\u0007\u0007\u0002\u0002OP\u0007,\u0002\u0002",
    "PR\u0007+\u0002\u0002QS\u0007*\u0002\u0002RQ\u0003\u0002\u0002\u0002",
    "RS\u0003\u0002\u0002\u0002S\u0007\u0003\u0002\u0002\u0002TU\u0007\b",
    "\u0002\u0002UW\u0007+\u0002\u0002VX\u0007*\u0002\u0002WV\u0003\u0002",
    "\u0002\u0002WX\u0003\u0002\u0002\u0002XY\u0003\u0002\u0002\u0002Y_\u0007",
    "\u0004\u0002\u0002Z^\u0005\n\u0006\u0002[^\u0005\f\u0007\u0002\\^\u0005",
    "\u001c\u000f\u0002]Z\u0003\u0002\u0002\u0002][\u0003\u0002\u0002\u0002",
    "]\\\u0003\u0002\u0002\u0002^a\u0003\u0002\u0002\u0002_]\u0003\u0002",
    "\u0002\u0002_`\u0003\u0002\u0002\u0002`b\u0003\u0002\u0002\u0002a_\u0003",
    "\u0002\u0002\u0002bc\u0007\u0005\u0002\u0002c\t\u0003\u0002\u0002\u0002",
    "de\u0007\t\u0002\u0002ef\u0007+\u0002\u0002f\u000b\u0003\u0002\u0002",
    "\u0002gh\u0007\n\u0002\u0002hi\u0007\u000b\u0002\u0002ij\u0007+\u0002",
    "\u0002jk\u0007\f\u0002\u0002kl\u0007+\u0002\u0002l\r\u0003\u0002\u0002",
    "\u0002mp\u0005\u0010\t\u0002np\u0005\u0012\n\u0002om\u0003\u0002\u0002",
    "\u0002on\u0003\u0002\u0002\u0002p\u000f\u0003\u0002\u0002\u0002qr\u0007",
    "\r\u0002\u0002rt\u0007+\u0002\u0002su\u0007*\u0002\u0002ts\u0003\u0002",
    "\u0002\u0002tu\u0003\u0002\u0002\u0002ux\u0003\u0002\u0002\u0002vw\u0007",
    "\u000e\u0002\u0002wy\u0007+\u0002\u0002xv\u0003\u0002\u0002\u0002xy",
    "\u0003\u0002\u0002\u0002yz\u0003\u0002\u0002\u0002z\u0084\u0007\u0004",
    "\u0002\u0002{|\u0007\u000f\u0002\u0002|\u0080\u0007\u0004\u0002\u0002",
    "}\u007f\u0005\u0016\f\u0002~}\u0003\u0002\u0002\u0002\u007f\u0082\u0003",
    "\u0002\u0002\u0002\u0080~\u0003\u0002\u0002\u0002\u0080\u0081\u0003",
    "\u0002\u0002\u0002\u0081\u0083\u0003\u0002\u0002\u0002\u0082\u0080\u0003",
    "\u0002\u0002\u0002\u0083\u0085\u0007\u0005\u0002\u0002\u0084{\u0003",
    "\u0002\u0002\u0002\u0084\u0085\u0003\u0002\u0002\u0002\u0085\u008f\u0003",
    "\u0002\u0002\u0002\u0086\u0087\u0007\u0010\u0002\u0002\u0087\u008b\u0007",
    "\u0004\u0002\u0002\u0088\u008a\u0005\u0016\f\u0002\u0089\u0088\u0003",
    "\u0002\u0002\u0002\u008a\u008d\u0003\u0002\u0002\u0002\u008b\u0089\u0003",
    "\u0002\u0002\u0002\u008b\u008c\u0003\u0002\u0002\u0002\u008c\u008e\u0003",
    "\u0002\u0002\u0002\u008d\u008b\u0003\u0002\u0002\u0002\u008e\u0090\u0007",
    "\u0005\u0002\u0002\u008f\u0086\u0003\u0002\u0002\u0002\u008f\u0090\u0003",
    "\u0002\u0002\u0002\u0090\u0094\u0003\u0002\u0002\u0002\u0091\u0092\u0007",
    "\u0011\u0002\u0002\u0092\u0093\u0007\u0012\u0002\u0002\u0093\u0095\u0005",
    "\u001a\u000e\u0002\u0094\u0091\u0003\u0002\u0002\u0002\u0094\u0095\u0003",
    "\u0002\u0002\u0002\u0095\u0099\u0003\u0002\u0002\u0002\u0096\u0097\u0007",
    "\u0013\u0002\u0002\u0097\u0098\u0007\u0012\u0002\u0002\u0098\u009a\u0005",
    "\u001a\u000e\u0002\u0099\u0096\u0003\u0002\u0002\u0002\u0099\u009a\u0003",
    "\u0002\u0002\u0002\u009a\u009b\u0003\u0002\u0002\u0002\u009b\u009c\u0007",
    "\u0005\u0002\u0002\u009c\u0011\u0003\u0002\u0002\u0002\u009d\u009e\u0007",
    "\u0014\u0002\u0002\u009e\u00a0\u0007+\u0002\u0002\u009f\u00a1\u0007",
    "*\u0002\u0002\u00a0\u009f\u0003\u0002\u0002\u0002\u00a0\u00a1\u0003",
    "\u0002\u0002\u0002\u00a1\u00a2\u0003\u0002\u0002\u0002\u00a2\u00a6\u0007",
    "\u0004\u0002\u0002\u00a3\u00a5\u0005\u0014\u000b\u0002\u00a4\u00a3\u0003",
    "\u0002\u0002\u0002\u00a5\u00a8\u0003\u0002\u0002\u0002\u00a6\u00a4\u0003",
    "\u0002\u0002\u0002\u00a6\u00a7\u0003\u0002\u0002\u0002\u00a7\u00a9\u0003",
    "\u0002\u0002\u0002\u00a8\u00a6\u0003\u0002\u0002\u0002\u00a9\u00aa\u0007",
    "\u0005\u0002\u0002\u00aa\u0013\u0003\u0002\u0002\u0002\u00ab\u00ac\u0007",
    "\u0015\u0002\u0002\u00ac\u00ad\u0007+\u0002\u0002\u00ad\u00ae\u0007",
    "\u0016\u0002\u0002\u00ae\u00af\u0007+\u0002\u0002\u00af\u0015\u0003",
    "\u0002\u0002\u0002\u00b0\u00b2\u0007\u0017\u0002\u0002\u00b1\u00b0\u0003",
    "\u0002\u0002\u0002\u00b1\u00b2\u0003\u0002\u0002\u0002\u00b2\u00b3\u0003",
    "\u0002\u0002\u0002\u00b3\u00b4\u0007\u0018\u0002\u0002\u00b4\u00b5\u0007",
    "+\u0002\u0002\u00b5\u00b7\u0007\u0019\u0002\u0002\u00b6\u00b8\u0005",
    "\u0018\r\u0002\u00b7\u00b6\u0003\u0002\u0002\u0002\u00b7\u00b8\u0003",
    "\u0002\u0002\u0002\u00b8\u00b9\u0003\u0002\u0002\u0002\u00b9\u00bb\u0007",
    "\u001a\u0002\u0002\u00ba\u00bc\u0007*\u0002\u0002\u00bb\u00ba\u0003",
    "\u0002\u0002\u0002\u00bb\u00bc\u0003\u0002\u0002\u0002\u00bc\u0017\u0003",
    "\u0002\u0002\u0002\u00bd\u00be\u0007+\u0002\u0002\u00be\u0019\u0003",
    "\u0002\u0002\u0002\u00bf\u00c0\u0007\u0004\u0002\u0002\u00c0\u00c1\u0007",
    "\u0005\u0002\u0002\u00c1\u001b\u0003\u0002\u0002\u0002\u00c2\u00c4\u0007",
    "(\u0002\u0002\u00c3\u00c2\u0003\u0002\u0002\u0002\u00c3\u00c4\u0003",
    "\u0002\u0002\u0002\u00c4\u00c5\u0003\u0002\u0002\u0002\u00c5\u00cc\u0007",
    "\u001b\u0002\u0002\u00c6\u00c8\u0007+\u0002\u0002\u00c7\u00c9\u0007",
    ")\u0002\u0002\u00c8\u00c7\u0003\u0002\u0002\u0002\u00c8\u00c9\u0003",
    "\u0002\u0002\u0002\u00c9\u00ca\u0003\u0002\u0002\u0002\u00ca\u00cd\u0007",
    "\u0016\u0002\u0002\u00cb\u00cd\u0007,\u0002\u0002\u00cc\u00c6\u0003",
    "\u0002\u0002\u0002\u00cc\u00cb\u0003\u0002\u0002\u0002\u00cd\u00ce\u0003",
    "\u0002\u0002\u0002\u00ce\u00d0\u0007+\u0002\u0002\u00cf\u00d1\u0007",
    "*\u0002\u0002\u00d0\u00cf\u0003\u0002\u0002\u0002\u00d0\u00d1\u0003",
    "\u0002\u0002\u0002\u00d1\u001d\u0003\u0002\u0002\u0002\u00d2\u00d3\u0007",
    "\u001c\u0002\u0002\u00d3\u00d5\u0007+\u0002\u0002\u00d4\u00d6\u0007",
    "*\u0002\u0002\u00d5\u00d4\u0003\u0002\u0002\u0002\u00d5\u00d6\u0003",
    "\u0002\u0002\u0002\u00d6\u00d9\u0003\u0002\u0002\u0002\u00d7\u00d8\u0007",
    "\u000e\u0002\u0002\u00d8\u00da\u0007+\u0002\u0002\u00d9\u00d7\u0003",
    "\u0002\u0002\u0002\u00d9\u00da\u0003\u0002\u0002\u0002\u00da\u00db\u0003",
    "\u0002\u0002\u0002\u00db\u00df\u0007\u0004\u0002\u0002\u00dc\u00dd\u0007",
    "\u001d\u0002\u0002\u00dd\u00de\u0007\u0004\u0002\u0002\u00de\u00e0\u0007",
    "\u0005\u0002\u0002\u00df\u00dc\u0003\u0002\u0002\u0002\u00df\u00e0\u0003",
    "\u0002\u0002\u0002\u00e0\u00e2\u0003\u0002\u0002\u0002\u00e1\u00e3\u0005",
    " \u0011\u0002\u00e2\u00e1\u0003\u0002\u0002\u0002\u00e2\u00e3\u0003",
    "\u0002\u0002\u0002\u00e3\u00e5\u0003\u0002\u0002\u0002\u00e4\u00e6\u0005",
    "\"\u0012\u0002\u00e5\u00e4\u0003\u0002\u0002\u0002\u00e5\u00e6\u0003",
    "\u0002\u0002\u0002\u00e6\u00e7\u0003\u0002\u0002\u0002\u00e7\u00e8\u0007",
    "\u0005\u0002\u0002\u00e8\u001f\u0003\u0002\u0002\u0002\u00e9\u00ea\u0007",
    "\u001e\u0002\u0002\u00ea\u00ee\u0007\u0004\u0002\u0002\u00eb\u00ed\u0005",
    "\u001c\u000f\u0002\u00ec\u00eb\u0003\u0002\u0002\u0002\u00ed\u00f0\u0003",
    "\u0002\u0002\u0002\u00ee\u00ec\u0003\u0002\u0002\u0002\u00ee\u00ef\u0003",
    "\u0002\u0002\u0002\u00ef\u00f1\u0003\u0002\u0002\u0002\u00f0\u00ee\u0003",
    "\u0002\u0002\u0002\u00f1\u00f2\u0007\u0005\u0002\u0002\u00f2!\u0003",
    "\u0002\u0002\u0002\u00f3\u00f4\u0007\u001f\u0002\u0002\u00f4\u00f6\u0007",
    "\u0004\u0002\u0002\u00f5\u00f7\u0005$\u0013\u0002\u00f6\u00f5\u0003",
    "\u0002\u0002\u0002\u00f6\u00f7\u0003\u0002\u0002\u0002\u00f7\u00f8\u0003",
    "\u0002\u0002\u0002\u00f8\u00f9\u0007\u0005\u0002\u0002\u00f9#\u0003",
    "\u0002\u0002\u0002\u00fa\u00fb\u0007 \u0002\u0002\u00fb\u0100\u0007",
    "\u0004\u0002\u0002\u00fc\u00ff\u0005&\u0014\u0002\u00fd\u00ff\u0005",
    "(\u0015\u0002\u00fe\u00fc\u0003\u0002\u0002\u0002\u00fe\u00fd\u0003",
    "\u0002\u0002\u0002\u00ff\u0102\u0003\u0002\u0002\u0002\u0100\u00fe\u0003",
    "\u0002\u0002\u0002\u0100\u0101\u0003\u0002\u0002\u0002\u0101\u0103\u0003",
    "\u0002\u0002\u0002\u0102\u0100\u0003\u0002\u0002\u0002\u0103\u0104\u0007",
    "\u0005\u0002\u0002\u0104%\u0003\u0002\u0002\u0002\u0105\u0106\u0007",
    "!\u0002\u0002\u0106\u0107\u0007,\u0002\u0002\u0107\u0108\u0007+\u0002",
    "\u0002\u0108\u0109\u0007\"\u0002\u0002\u0109\u010a\u0007+\u0002\u0002",
    "\u010a\u010c\u0007\u0004\u0002\u0002\u010b\u010d\u0005*\u0016\u0002",
    "\u010c\u010b\u0003\u0002\u0002\u0002\u010c\u010d\u0003\u0002\u0002\u0002",
    "\u010d\u010e\u0003\u0002\u0002\u0002\u010e\u010f\u0007\u0005\u0002\u0002",
    "\u010f\'\u0003\u0002\u0002\u0002\u0110\u0111\u0007#\u0002\u0002\u0111",
    "\u0112\u0007+\u0002\u0002\u0112)\u0003\u0002\u0002\u0002\u0113\u0114",
    "\u0007$\u0002\u0002\u0114\u0116\u0007\u0004\u0002\u0002\u0115\u0117",
    "\u0007+\u0002\u0002\u0116\u0115\u0003\u0002\u0002\u0002\u0116\u0117",
    "\u0003\u0002\u0002\u0002\u0117\u0118\u0003\u0002\u0002\u0002\u0118\u0119",
    "\u0007\u0005\u0002\u0002\u0119+\u0003\u0002\u0002\u0002\u011a\u011b",
    "\u0007%\u0002\u0002\u011b\u011d\u0007+\u0002\u0002\u011c\u011e\u0007",
    "*\u0002\u0002\u011d\u011c\u0003\u0002\u0002\u0002\u011d\u011e\u0003",
    "\u0002\u0002\u0002\u011e\u0121\u0003\u0002\u0002\u0002\u011f\u0120\u0007",
    "\u000e\u0002\u0002\u0120\u0122\u0007+\u0002\u0002\u0121\u011f\u0003",
    "\u0002\u0002\u0002\u0121\u0122\u0003\u0002\u0002\u0002\u0122\u0123\u0003",
    "\u0002\u0002\u0002\u0123\u0124\u0007\u0004\u0002\u0002\u0124\u0125\u0007",
    "+\u0002\u0002\u0125\u0126\u0007\u0005\u0002\u0002\u0126-\u0003\u0002",
    "\u0002\u0002\u0127\u0128\u0007&\u0002\u0002\u0128/\u0003\u0002\u0002",
    "\u0002)3<>IRW]_otx\u0080\u0084\u008b\u008f\u0094\u0099\u00a0\u00a6\u00b1",
    "\u00b7\u00bb\u00c3\u00c8\u00cc\u00d0\u00d5\u00d9\u00df\u00e2\u00e5\u00ee",
    "\u00f6\u00fe\u0100\u010c\u0116\u011d\u0121"].join("");


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
                     "'Structure'", "'Behavior'", "'StateMachine'", "'Transition'", 
                     "'->'", "'State'", "'action'", "'DataClass'", "'impotodo'", 
                     null, "'rtypetodo'" ];

var symbolicNames = [ null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, "ImportRoom", "ReferenceType", "MULTIPLICITY", 
                      "Documentation", "ID", "RoomName", "ML_COMMENT", "SL_COMMENT", 
                      "WS" ];

var ruleNames =  [ "roomModel", "logicalSystem", "subSystemRef", "subSystemClass", 
                   "logicalThread", "layerConnection", "generalProtocolClass", 
                   "protocolClass", "compoundProtocolClass", "subProtocol", 
                   "message", "varDecl", "portClass", "actorRef", "actorClass", 
                   "structure", "behavior", "stateMachine", "transition", 
                   "sstate", "action", "dataClass", "importedFQN" ];

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
RoomParser.ImportRoom = 37;
RoomParser.ReferenceType = 38;
RoomParser.MULTIPLICITY = 39;
RoomParser.Documentation = 40;
RoomParser.ID = 41;
RoomParser.RoomName = 42;
RoomParser.ML_COMMENT = 43;
RoomParser.SL_COMMENT = 44;
RoomParser.WS = 45;

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
RoomParser.RULE_structure = 15;
RoomParser.RULE_behavior = 16;
RoomParser.RULE_stateMachine = 17;
RoomParser.RULE_transition = 18;
RoomParser.RULE_sstate = 19;
RoomParser.RULE_action = 20;
RoomParser.RULE_dataClass = 21;
RoomParser.RULE_importedFQN = 22;

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
        this.state = 46;
        this.match(RoomParser.T__0);
        this.state = 47;
        this.match(RoomParser.ID);
        this.state = 49;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 48;
            this.match(RoomParser.Documentation);
        }

        this.state = 51;
        this.match(RoomParser.T__1);
        this.state = 60;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << RoomParser.T__3) | (1 << RoomParser.T__5) | (1 << RoomParser.T__10) | (1 << RoomParser.T__17) | (1 << RoomParser.T__25))) !== 0) || _la===RoomParser.T__34 || _la===RoomParser.ImportRoom) {
            this.state = 58;
            this._errHandler.sync(this);
            switch(this._input.LA(1)) {
            case RoomParser.T__34:
                this.state = 52;
                this.dataClass();
                break;
            case RoomParser.T__10:
            case RoomParser.T__17:
                this.state = 53;
                this.generalProtocolClass();
                break;
            case RoomParser.T__25:
                this.state = 54;
                this.actorClass();
                break;
            case RoomParser.T__5:
                this.state = 55;
                this.subSystemClass();
                break;
            case RoomParser.T__3:
                this.state = 56;
                this.logicalSystem();
                break;
            case RoomParser.ImportRoom:
                this.state = 57;
                this.match(RoomParser.ImportRoom);
                break;
            default:
                throw new antlr4.error.NoViableAltException(this);
            }
            this.state = 62;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 63;
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
        this.state = 65;
        this.match(RoomParser.T__3);
        this.state = 66;
        this.match(RoomParser.ID);
        this.state = 67;
        this.match(RoomParser.T__1);
        this.state = 71;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===RoomParser.T__4) {
            this.state = 68;
            this.subSystemRef();
            this.state = 73;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 74;
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
        this.state = 76;
        this.match(RoomParser.T__4);
        this.state = 77;
        this.match(RoomParser.RoomName);
        this.state = 78;
        this.match(RoomParser.ID);
        this.state = 80;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 79;
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
        this.state = 82;
        this.match(RoomParser.T__5);
        this.state = 83;
        this.match(RoomParser.ID);
        this.state = 85;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 84;
            this.match(RoomParser.Documentation);
        }

        this.state = 87;
        this.match(RoomParser.T__1);
        this.state = 93;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(((((_la - 7)) & ~0x1f) == 0 && ((1 << (_la - 7)) & ((1 << (RoomParser.T__6 - 7)) | (1 << (RoomParser.T__7 - 7)) | (1 << (RoomParser.T__24 - 7)) | (1 << (RoomParser.ReferenceType - 7)))) !== 0)) {
            this.state = 91;
            this._errHandler.sync(this);
            switch(this._input.LA(1)) {
            case RoomParser.T__6:
                this.state = 88;
                this.logicalThread();
                break;
            case RoomParser.T__7:
                this.state = 89;
                this.layerConnection();
                break;
            case RoomParser.T__24:
            case RoomParser.ReferenceType:
                this.state = 90;
                this.actorRef();
                break;
            default:
                throw new antlr4.error.NoViableAltException(this);
            }
            this.state = 95;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 96;
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
        this.state = 98;
        this.match(RoomParser.T__6);
        this.state = 99;
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
        this.state = 101;
        this.match(RoomParser.T__7);
        this.state = 102;
        this.match(RoomParser.T__8);
        this.state = 103;
        this.match(RoomParser.ID);
        this.state = 104;
        this.match(RoomParser.T__9);
        this.state = 105;
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
        this.state = 109;
        this._errHandler.sync(this);
        switch(this._input.LA(1)) {
        case RoomParser.T__10:
            this.enterOuterAlt(localctx, 1);
            this.state = 107;
            this.protocolClass();
            break;
        case RoomParser.T__17:
            this.enterOuterAlt(localctx, 2);
            this.state = 108;
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
        this.state = 111;
        this.match(RoomParser.T__10);
        this.state = 112;
        this.match(RoomParser.ID);
        this.state = 114;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 113;
            this.match(RoomParser.Documentation);
        }

        this.state = 118;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__11) {
            this.state = 116;
            this.match(RoomParser.T__11);
            this.state = 117;
            this.match(RoomParser.ID);
        }

        this.state = 120;
        this.match(RoomParser.T__1);
        this.state = 130;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__12) {
            this.state = 121;
            this.match(RoomParser.T__12);
            this.state = 122;
            this.match(RoomParser.T__1);
            this.state = 126;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            while(_la===RoomParser.T__20 || _la===RoomParser.T__21) {
                this.state = 123;
                this.message();
                this.state = 128;
                this._errHandler.sync(this);
                _la = this._input.LA(1);
            }
            this.state = 129;
            this.match(RoomParser.T__2);
        }

        this.state = 141;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__13) {
            this.state = 132;
            this.match(RoomParser.T__13);
            this.state = 133;
            this.match(RoomParser.T__1);
            this.state = 137;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            while(_la===RoomParser.T__20 || _la===RoomParser.T__21) {
                this.state = 134;
                this.message();
                this.state = 139;
                this._errHandler.sync(this);
                _la = this._input.LA(1);
            }
            this.state = 140;
            this.match(RoomParser.T__2);
        }

        this.state = 146;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__14) {
            this.state = 143;
            this.match(RoomParser.T__14);
            this.state = 144;
            this.match(RoomParser.T__15);
            this.state = 145;
            this.portClass();
        }

        this.state = 151;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__16) {
            this.state = 148;
            this.match(RoomParser.T__16);
            this.state = 149;
            this.match(RoomParser.T__15);
            this.state = 150;
            this.portClass();
        }

        this.state = 153;
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
        this.state = 155;
        this.match(RoomParser.T__17);
        this.state = 156;
        this.match(RoomParser.ID);
        this.state = 158;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 157;
            this.match(RoomParser.Documentation);
        }

        this.state = 160;
        this.match(RoomParser.T__1);
        this.state = 164;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===RoomParser.T__18) {
            this.state = 161;
            this.subProtocol();
            this.state = 166;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 167;
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
        this.state = 169;
        this.match(RoomParser.T__18);
        this.state = 170;
        this.match(RoomParser.ID);
        this.state = 171;
        this.match(RoomParser.T__19);
        this.state = 172;
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
        this.state = 175;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__20) {
            this.state = 174;
            this.match(RoomParser.T__20);
        }

        this.state = 177;
        this.match(RoomParser.T__21);
        this.state = 178;
        this.match(RoomParser.ID);
        this.state = 179;
        this.match(RoomParser.T__22);
        this.state = 181;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.ID) {
            this.state = 180;
            this.varDecl();
        }

        this.state = 183;
        this.match(RoomParser.T__23);
        this.state = 185;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 184;
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
        this.state = 187;
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
        this.state = 189;
        this.match(RoomParser.T__1);
        this.state = 190;
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
        this.state = 193;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.ReferenceType) {
            this.state = 192;
            this.match(RoomParser.ReferenceType);
        }

        this.state = 195;
        this.match(RoomParser.T__24);
        this.state = 202;
        this._errHandler.sync(this);
        switch(this._input.LA(1)) {
        case RoomParser.ID:
            this.state = 196;
            this.match(RoomParser.ID);
            this.state = 198;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if(_la===RoomParser.MULTIPLICITY) {
                this.state = 197;
                this.match(RoomParser.MULTIPLICITY);
            }

            this.state = 200;
            this.match(RoomParser.T__19);
            break;
        case RoomParser.RoomName:
            this.state = 201;
            this.match(RoomParser.RoomName);
            break;
        default:
            throw new antlr4.error.NoViableAltException(this);
        }
        this.state = 204;
        this.match(RoomParser.ID);
        this.state = 206;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 205;
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
        this.state = 208;
        this.match(RoomParser.T__25);
        this.state = 209;
        this.match(RoomParser.ID);
        this.state = 211;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 210;
            this.match(RoomParser.Documentation);
        }

        this.state = 215;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__11) {
            this.state = 213;
            this.match(RoomParser.T__11);
            this.state = 214;
            this.match(RoomParser.ID);
        }

        this.state = 217;
        this.match(RoomParser.T__1);
        this.state = 221;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__26) {
            this.state = 218;
            this.match(RoomParser.T__26);
            this.state = 219;
            this.match(RoomParser.T__1);
            this.state = 220;
            this.match(RoomParser.T__2);
        }

        this.state = 224;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__27) {
            this.state = 223;
            this.structure();
        }

        this.state = 227;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__28) {
            this.state = 226;
            this.behavior();
        }

        this.state = 229;
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
    this.enterRule(localctx, 30, RoomParser.RULE_structure);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 231;
        this.match(RoomParser.T__27);
        this.state = 232;
        this.match(RoomParser.T__1);
        this.state = 236;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===RoomParser.T__24 || _la===RoomParser.ReferenceType) {
            this.state = 233;
            this.actorRef();
            this.state = 238;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 239;
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
    this.enterRule(localctx, 32, RoomParser.RULE_behavior);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 241;
        this.match(RoomParser.T__28);
        this.state = 242;
        this.match(RoomParser.T__1);
        this.state = 244;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__29) {
            this.state = 243;
            this.stateMachine();
        }

        this.state = 246;
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
    this.enterRule(localctx, 34, RoomParser.RULE_stateMachine);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 248;
        this.match(RoomParser.T__29);
        this.state = 249;
        this.match(RoomParser.T__1);
        this.state = 254;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===RoomParser.T__30 || _la===RoomParser.T__32) {
            this.state = 252;
            this._errHandler.sync(this);
            switch(this._input.LA(1)) {
            case RoomParser.T__30:
                this.state = 250;
                this.transition();
                break;
            case RoomParser.T__32:
                this.state = 251;
                this.sstate();
                break;
            default:
                throw new antlr4.error.NoViableAltException(this);
            }
            this.state = 256;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 257;
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


TransitionContext.prototype.action = function() {
    return this.getTypedRuleContext(ActionContext,0);
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
    this.enterRule(localctx, 36, RoomParser.RULE_transition);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 259;
        this.match(RoomParser.T__30);
        this.state = 260;
        this.match(RoomParser.RoomName);
        this.state = 261;
        this.match(RoomParser.ID);
        this.state = 262;
        this.match(RoomParser.T__31);
        this.state = 263;
        this.match(RoomParser.ID);
        this.state = 264;
        this.match(RoomParser.T__1);
        this.state = 266;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__33) {
            this.state = 265;
            this.action();
        }

        this.state = 268;
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
    this.enterRule(localctx, 38, RoomParser.RULE_sstate);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 270;
        this.match(RoomParser.T__32);
        this.state = 271;
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

ActionContext.prototype.ID = function() {
    return this.getToken(RoomParser.ID, 0);
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
    this.enterRule(localctx, 40, RoomParser.RULE_action);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 273;
        this.match(RoomParser.T__33);
        this.state = 274;
        this.match(RoomParser.T__1);
        this.state = 276;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.ID) {
            this.state = 275;
            this.match(RoomParser.ID);
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
    this.enterRule(localctx, 42, RoomParser.RULE_dataClass);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 280;
        this.match(RoomParser.T__34);
        this.state = 281;
        this.match(RoomParser.ID);
        this.state = 283;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 282;
            this.match(RoomParser.Documentation);
        }

        this.state = 287;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__11) {
            this.state = 285;
            this.match(RoomParser.T__11);
            this.state = 286;
            this.match(RoomParser.ID);
        }

        this.state = 289;
        this.match(RoomParser.T__1);
        this.state = 290;
        this.match(RoomParser.ID);
        this.state = 291;
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
    this.enterRule(localctx, 44, RoomParser.RULE_importedFQN);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 293;
        this.match(RoomParser.T__35);
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
