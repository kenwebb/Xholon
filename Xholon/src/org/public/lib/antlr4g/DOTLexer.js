// Generated from grammars-v4-master/dot/DOT.g4 by ANTLR 4.7
// jshint ignore: start
var antlr4 = require('xholon/lib/antlr4/index');


var serializedATN = ["\u0003\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964",
    "\u0002\u001a\u00ea\b\u0001\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004",
    "\u0004\t\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t",
    "\u0007\u0004\b\t\b\u0004\t\t\t\u0004\n\t\n\u0004\u000b\t\u000b\u0004",
    "\f\t\f\u0004\r\t\r\u0004\u000e\t\u000e\u0004\u000f\t\u000f\u0004\u0010",
    "\t\u0010\u0004\u0011\t\u0011\u0004\u0012\t\u0012\u0004\u0013\t\u0013",
    "\u0004\u0014\t\u0014\u0004\u0015\t\u0015\u0004\u0016\t\u0016\u0004\u0017",
    "\t\u0017\u0004\u0018\t\u0018\u0004\u0019\t\u0019\u0004\u001a\t\u001a",
    "\u0004\u001b\t\u001b\u0004\u001c\t\u001c\u0003\u0002\u0003\u0002\u0003",
    "\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0003",
    "\u0006\u0003\u0006\u0003\u0007\u0003\u0007\u0003\b\u0003\b\u0003\t\u0003",
    "\t\u0003\t\u0003\n\u0003\n\u0003\n\u0003\u000b\u0003\u000b\u0003\f\u0003",
    "\f\u0003\f\u0003\f\u0003\f\u0003\f\u0003\f\u0003\r\u0003\r\u0003\r\u0003",
    "\r\u0003\r\u0003\r\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0003",
    "\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000f\u0003\u000f\u0003",
    "\u000f\u0003\u000f\u0003\u000f\u0003\u0010\u0003\u0010\u0003\u0010\u0003",
    "\u0010\u0003\u0010\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0003",
    "\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0012\u0005",
    "\u0012y\n\u0012\u0003\u0012\u0003\u0012\u0006\u0012}\n\u0012\r\u0012",
    "\u000e\u0012~\u0003\u0012\u0006\u0012\u0082\n\u0012\r\u0012\u000e\u0012",
    "\u0083\u0003\u0012\u0003\u0012\u0007\u0012\u0088\n\u0012\f\u0012\u000e",
    "\u0012\u008b\u000b\u0012\u0005\u0012\u008d\n\u0012\u0005\u0012\u008f",
    "\n\u0012\u0003\u0013\u0003\u0013\u0003\u0014\u0003\u0014\u0003\u0014",
    "\u0003\u0014\u0007\u0014\u0097\n\u0014\f\u0014\u000e\u0014\u009a\u000b",
    "\u0014\u0003\u0014\u0003\u0014\u0003\u0015\u0003\u0015\u0003\u0015\u0007",
    "\u0015\u00a1\n\u0015\f\u0015\u000e\u0015\u00a4\u000b\u0015\u0003\u0016",
    "\u0003\u0016\u0003\u0017\u0003\u0017\u0003\u0017\u0007\u0017\u00ab\n",
    "\u0017\f\u0017\u000e\u0017\u00ae\u000b\u0017\u0003\u0017\u0003\u0017",
    "\u0003\u0018\u0003\u0018\u0007\u0018\u00b4\n\u0018\f\u0018\u000e\u0018",
    "\u00b7\u000b\u0018\u0003\u0018\u0003\u0018\u0003\u0019\u0003\u0019\u0003",
    "\u0019\u0003\u0019\u0007\u0019\u00bf\n\u0019\f\u0019\u000e\u0019\u00c2",
    "\u000b\u0019\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u0019",
    "\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0007\u001a\u00cd\n",
    "\u001a\f\u001a\u000e\u001a\u00d0\u000b\u001a\u0003\u001a\u0005\u001a",
    "\u00d3\n\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0003",
    "\u001b\u0003\u001b\u0007\u001b\u00db\n\u001b\f\u001b\u000e\u001b\u00de",
    "\u000b\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001c",
    "\u0006\u001c\u00e5\n\u001c\r\u001c\u000e\u001c\u00e6\u0003\u001c\u0003",
    "\u001c\u0007\u0098\u00b5\u00c0\u00ce\u00dc\u0002\u001d\u0003\u0003\u0005",
    "\u0004\u0007\u0005\t\u0006\u000b\u0007\r\b\u000f\t\u0011\n\u0013\u000b",
    "\u0015\f\u0017\r\u0019\u000e\u001b\u000f\u001d\u0010\u001f\u0011!\u0012",
    "#\u0013%\u0002\'\u0014)\u0015+\u0002-\u0016/\u00021\u00173\u00185\u0019",
    "7\u001a\u0003\u0002\u0015\u0004\u0002UUuu\u0004\u0002VVvv\u0004\u0002",
    "TTtt\u0004\u0002KKkk\u0004\u0002EEee\u0004\u0002IIii\u0004\u0002CCc",
    "c\u0004\u0002RRrr\u0004\u0002JJjj\u0004\u0002FFff\u0004\u0002PPpp\u0004",
    "\u0002QQqq\u0004\u0002GGgg\u0004\u0002WWww\u0004\u0002DDdd\u0003\u0002",
    "2;\u0006\u0002C\\aac|\u0082\u0101\u0004\u0002>>@@\u0005\u0002\u000b",
    "\f\u000f\u000f\"\"\u0002\u00f8\u0002\u0003\u0003\u0002\u0002\u0002\u0002",
    "\u0005\u0003\u0002\u0002\u0002\u0002\u0007\u0003\u0002\u0002\u0002\u0002",
    "\t\u0003\u0002\u0002\u0002\u0002\u000b\u0003\u0002\u0002\u0002\u0002",
    "\r\u0003\u0002\u0002\u0002\u0002\u000f\u0003\u0002\u0002\u0002\u0002",
    "\u0011\u0003\u0002\u0002\u0002\u0002\u0013\u0003\u0002\u0002\u0002\u0002",
    "\u0015\u0003\u0002\u0002\u0002\u0002\u0017\u0003\u0002\u0002\u0002\u0002",
    "\u0019\u0003\u0002\u0002\u0002\u0002\u001b\u0003\u0002\u0002\u0002\u0002",
    "\u001d\u0003\u0002\u0002\u0002\u0002\u001f\u0003\u0002\u0002\u0002\u0002",
    "!\u0003\u0002\u0002\u0002\u0002#\u0003\u0002\u0002\u0002\u0002\'\u0003",
    "\u0002\u0002\u0002\u0002)\u0003\u0002\u0002\u0002\u0002-\u0003\u0002",
    "\u0002\u0002\u00021\u0003\u0002\u0002\u0002\u00023\u0003\u0002\u0002",
    "\u0002\u00025\u0003\u0002\u0002\u0002\u00027\u0003\u0002\u0002\u0002",
    "\u00039\u0003\u0002\u0002\u0002\u0005;\u0003\u0002\u0002\u0002\u0007",
    "=\u0003\u0002\u0002\u0002\t?\u0003\u0002\u0002\u0002\u000bA\u0003\u0002",
    "\u0002\u0002\rC\u0003\u0002\u0002\u0002\u000fE\u0003\u0002\u0002\u0002",
    "\u0011G\u0003\u0002\u0002\u0002\u0013J\u0003\u0002\u0002\u0002\u0015",
    "M\u0003\u0002\u0002\u0002\u0017O\u0003\u0002\u0002\u0002\u0019V\u0003",
    "\u0002\u0002\u0002\u001b\\\u0003\u0002\u0002\u0002\u001dd\u0003\u0002",
    "\u0002\u0002\u001fi\u0003\u0002\u0002\u0002!n\u0003\u0002\u0002\u0002",
    "#x\u0003\u0002\u0002\u0002%\u0090\u0003\u0002\u0002\u0002\'\u0092\u0003",
    "\u0002\u0002\u0002)\u009d\u0003\u0002\u0002\u0002+\u00a5\u0003\u0002",
    "\u0002\u0002-\u00a7\u0003\u0002\u0002\u0002/\u00b1\u0003\u0002\u0002",
    "\u00021\u00ba\u0003\u0002\u0002\u00023\u00c8\u0003\u0002\u0002\u0002",
    "5\u00d8\u0003\u0002\u0002\u00027\u00e4\u0003\u0002\u0002\u00029:\u0007",
    "}\u0002\u0002:\u0004\u0003\u0002\u0002\u0002;<\u0007\u007f\u0002\u0002",
    "<\u0006\u0003\u0002\u0002\u0002=>\u0007=\u0002\u0002>\b\u0003\u0002",
    "\u0002\u0002?@\u0007?\u0002\u0002@\n\u0003\u0002\u0002\u0002AB\u0007",
    "]\u0002\u0002B\f\u0003\u0002\u0002\u0002CD\u0007_\u0002\u0002D\u000e",
    "\u0003\u0002\u0002\u0002EF\u0007.\u0002\u0002F\u0010\u0003\u0002\u0002",
    "\u0002GH\u0007/\u0002\u0002HI\u0007@\u0002\u0002I\u0012\u0003\u0002",
    "\u0002\u0002JK\u0007/\u0002\u0002KL\u0007/\u0002\u0002L\u0014\u0003",
    "\u0002\u0002\u0002MN\u0007<\u0002\u0002N\u0016\u0003\u0002\u0002\u0002",
    "OP\t\u0002\u0002\u0002PQ\t\u0003\u0002\u0002QR\t\u0004\u0002\u0002R",
    "S\t\u0005\u0002\u0002ST\t\u0006\u0002\u0002TU\t\u0003\u0002\u0002U\u0018",
    "\u0003\u0002\u0002\u0002VW\t\u0007\u0002\u0002WX\t\u0004\u0002\u0002",
    "XY\t\b\u0002\u0002YZ\t\t\u0002\u0002Z[\t\n\u0002\u0002[\u001a\u0003",
    "\u0002\u0002\u0002\\]\t\u000b\u0002\u0002]^\t\u0005\u0002\u0002^_\t",
    "\u0007\u0002\u0002_`\t\u0004\u0002\u0002`a\t\b\u0002\u0002ab\t\t\u0002",
    "\u0002bc\t\n\u0002\u0002c\u001c\u0003\u0002\u0002\u0002de\t\f\u0002",
    "\u0002ef\t\r\u0002\u0002fg\t\u000b\u0002\u0002gh\t\u000e\u0002\u0002",
    "h\u001e\u0003\u0002\u0002\u0002ij\t\u000e\u0002\u0002jk\t\u000b\u0002",
    "\u0002kl\t\u0007\u0002\u0002lm\t\u000e\u0002\u0002m \u0003\u0002\u0002",
    "\u0002no\t\u0002\u0002\u0002op\t\u000f\u0002\u0002pq\t\u0010\u0002\u0002",
    "qr\t\u0007\u0002\u0002rs\t\u0004\u0002\u0002st\t\b\u0002\u0002tu\t\t",
    "\u0002\u0002uv\t\n\u0002\u0002v\"\u0003\u0002\u0002\u0002wy\u0007/\u0002",
    "\u0002xw\u0003\u0002\u0002\u0002xy\u0003\u0002\u0002\u0002y\u008e\u0003",
    "\u0002\u0002\u0002z|\u00070\u0002\u0002{}\u0005%\u0013\u0002|{\u0003",
    "\u0002\u0002\u0002}~\u0003\u0002\u0002\u0002~|\u0003\u0002\u0002\u0002",
    "~\u007f\u0003\u0002\u0002\u0002\u007f\u008f\u0003\u0002\u0002\u0002",
    "\u0080\u0082\u0005%\u0013\u0002\u0081\u0080\u0003\u0002\u0002\u0002",
    "\u0082\u0083\u0003\u0002\u0002\u0002\u0083\u0081\u0003\u0002\u0002\u0002",
    "\u0083\u0084\u0003\u0002\u0002\u0002\u0084\u008c\u0003\u0002\u0002\u0002",
    "\u0085\u0089\u00070\u0002\u0002\u0086\u0088\u0005%\u0013\u0002\u0087",
    "\u0086\u0003\u0002\u0002\u0002\u0088\u008b\u0003\u0002\u0002\u0002\u0089",
    "\u0087\u0003\u0002\u0002\u0002\u0089\u008a\u0003\u0002\u0002\u0002\u008a",
    "\u008d\u0003\u0002\u0002\u0002\u008b\u0089\u0003\u0002\u0002\u0002\u008c",
    "\u0085\u0003\u0002\u0002\u0002\u008c\u008d\u0003\u0002\u0002\u0002\u008d",
    "\u008f\u0003\u0002\u0002\u0002\u008ez\u0003\u0002\u0002\u0002\u008e",
    "\u0081\u0003\u0002\u0002\u0002\u008f$\u0003\u0002\u0002\u0002\u0090",
    "\u0091\t\u0011\u0002\u0002\u0091&\u0003\u0002\u0002\u0002\u0092\u0098",
    "\u0007$\u0002\u0002\u0093\u0094\u0007^\u0002\u0002\u0094\u0097\u0007",
    "$\u0002\u0002\u0095\u0097\u000b\u0002\u0002\u0002\u0096\u0093\u0003",
    "\u0002\u0002\u0002\u0096\u0095\u0003\u0002\u0002\u0002\u0097\u009a\u0003",
    "\u0002\u0002\u0002\u0098\u0099\u0003\u0002\u0002\u0002\u0098\u0096\u0003",
    "\u0002\u0002\u0002\u0099\u009b\u0003\u0002\u0002\u0002\u009a\u0098\u0003",
    "\u0002\u0002\u0002\u009b\u009c\u0007$\u0002\u0002\u009c(\u0003\u0002",
    "\u0002\u0002\u009d\u00a2\u0005+\u0016\u0002\u009e\u00a1\u0005+\u0016",
    "\u0002\u009f\u00a1\u0005%\u0013\u0002\u00a0\u009e\u0003\u0002\u0002",
    "\u0002\u00a0\u009f\u0003\u0002\u0002\u0002\u00a1\u00a4\u0003\u0002\u0002",
    "\u0002\u00a2\u00a0\u0003\u0002\u0002\u0002\u00a2\u00a3\u0003\u0002\u0002",
    "\u0002\u00a3*\u0003\u0002\u0002\u0002\u00a4\u00a2\u0003\u0002\u0002",
    "\u0002\u00a5\u00a6\t\u0012\u0002\u0002\u00a6,\u0003\u0002\u0002\u0002",
    "\u00a7\u00ac\u0007>\u0002\u0002\u00a8\u00ab\u0005/\u0018\u0002\u00a9",
    "\u00ab\n\u0013\u0002\u0002\u00aa\u00a8\u0003\u0002\u0002\u0002\u00aa",
    "\u00a9\u0003\u0002\u0002\u0002\u00ab\u00ae\u0003\u0002\u0002\u0002\u00ac",
    "\u00aa\u0003\u0002\u0002\u0002\u00ac\u00ad\u0003\u0002\u0002\u0002\u00ad",
    "\u00af\u0003\u0002\u0002\u0002\u00ae\u00ac\u0003\u0002\u0002\u0002\u00af",
    "\u00b0\u0007@\u0002\u0002\u00b0.\u0003\u0002\u0002\u0002\u00b1\u00b5",
    "\u0007>\u0002\u0002\u00b2\u00b4\u000b\u0002\u0002\u0002\u00b3\u00b2",
    "\u0003\u0002\u0002\u0002\u00b4\u00b7\u0003\u0002\u0002\u0002\u00b5\u00b6",
    "\u0003\u0002\u0002\u0002\u00b5\u00b3\u0003\u0002\u0002\u0002\u00b6\u00b8",
    "\u0003\u0002\u0002\u0002\u00b7\u00b5\u0003\u0002\u0002\u0002\u00b8\u00b9",
    "\u0007@\u0002\u0002\u00b90\u0003\u0002\u0002\u0002\u00ba\u00bb\u0007",
    "1\u0002\u0002\u00bb\u00bc\u0007,\u0002\u0002\u00bc\u00c0\u0003\u0002",
    "\u0002\u0002\u00bd\u00bf\u000b\u0002\u0002\u0002\u00be\u00bd\u0003\u0002",
    "\u0002\u0002\u00bf\u00c2\u0003\u0002\u0002\u0002\u00c0\u00c1\u0003\u0002",
    "\u0002\u0002\u00c0\u00be\u0003\u0002\u0002\u0002\u00c1\u00c3\u0003\u0002",
    "\u0002\u0002\u00c2\u00c0\u0003\u0002\u0002\u0002\u00c3\u00c4\u0007,",
    "\u0002\u0002\u00c4\u00c5\u00071\u0002\u0002\u00c5\u00c6\u0003\u0002",
    "\u0002\u0002\u00c6\u00c7\b\u0019\u0002\u0002\u00c72\u0003\u0002\u0002",
    "\u0002\u00c8\u00c9\u00071\u0002\u0002\u00c9\u00ca\u00071\u0002\u0002",
    "\u00ca\u00ce\u0003\u0002\u0002\u0002\u00cb\u00cd\u000b\u0002\u0002\u0002",
    "\u00cc\u00cb\u0003\u0002\u0002\u0002\u00cd\u00d0\u0003\u0002\u0002\u0002",
    "\u00ce\u00cf\u0003\u0002\u0002\u0002\u00ce\u00cc\u0003\u0002\u0002\u0002",
    "\u00cf\u00d2\u0003\u0002\u0002\u0002\u00d0\u00ce\u0003\u0002\u0002\u0002",
    "\u00d1\u00d3\u0007\u000f\u0002\u0002\u00d2\u00d1\u0003\u0002\u0002\u0002",
    "\u00d2\u00d3\u0003\u0002\u0002\u0002\u00d3\u00d4\u0003\u0002\u0002\u0002",
    "\u00d4\u00d5\u0007\f\u0002\u0002\u00d5\u00d6\u0003\u0002\u0002\u0002",
    "\u00d6\u00d7\b\u001a\u0002\u0002\u00d74\u0003\u0002\u0002\u0002\u00d8",
    "\u00dc\u0007%\u0002\u0002\u00d9\u00db\u000b\u0002\u0002\u0002\u00da",
    "\u00d9\u0003\u0002\u0002\u0002\u00db\u00de\u0003\u0002\u0002\u0002\u00dc",
    "\u00dd\u0003\u0002\u0002\u0002\u00dc\u00da\u0003\u0002\u0002\u0002\u00dd",
    "\u00df\u0003\u0002\u0002\u0002\u00de\u00dc\u0003\u0002\u0002\u0002\u00df",
    "\u00e0\u0007\f\u0002\u0002\u00e0\u00e1\u0003\u0002\u0002\u0002\u00e1",
    "\u00e2\b\u001b\u0002\u0002\u00e26\u0003\u0002\u0002\u0002\u00e3\u00e5",
    "\t\u0014\u0002\u0002\u00e4\u00e3\u0003\u0002\u0002\u0002\u00e5\u00e6",
    "\u0003\u0002\u0002\u0002\u00e6\u00e4\u0003\u0002\u0002\u0002\u00e6\u00e7",
    "\u0003\u0002\u0002\u0002\u00e7\u00e8\u0003\u0002\u0002\u0002\u00e8\u00e9",
    "\b\u001c\u0002\u0002\u00e98\u0003\u0002\u0002\u0002\u0015\u0002x~\u0083",
    "\u0089\u008c\u008e\u0096\u0098\u00a0\u00a2\u00aa\u00ac\u00b5\u00c0\u00ce",
    "\u00d2\u00dc\u00e6\u0003\b\u0002\u0002"].join("");


var atn = new antlr4.atn.ATNDeserializer().deserialize(serializedATN);

var decisionsToDFA = atn.decisionToState.map( function(ds, index) { return new antlr4.dfa.DFA(ds, index); });

function DOTLexer(input) {
	antlr4.Lexer.call(this, input);
    this._interp = new antlr4.atn.LexerATNSimulator(this, atn, decisionsToDFA, new antlr4.PredictionContextCache());
    return this;
}

DOTLexer.prototype = Object.create(antlr4.Lexer.prototype);
DOTLexer.prototype.constructor = DOTLexer;

DOTLexer.EOF = antlr4.Token.EOF;
DOTLexer.T__0 = 1;
DOTLexer.T__1 = 2;
DOTLexer.T__2 = 3;
DOTLexer.T__3 = 4;
DOTLexer.T__4 = 5;
DOTLexer.T__5 = 6;
DOTLexer.T__6 = 7;
DOTLexer.T__7 = 8;
DOTLexer.T__8 = 9;
DOTLexer.T__9 = 10;
DOTLexer.STRICT = 11;
DOTLexer.GRAPH = 12;
DOTLexer.DIGRAPH = 13;
DOTLexer.NODE = 14;
DOTLexer.EDGE = 15;
DOTLexer.SUBGRAPH = 16;
DOTLexer.NUMBER = 17;
DOTLexer.STRING = 18;
DOTLexer.ID = 19;
DOTLexer.HTML_STRING = 20;
DOTLexer.COMMENT = 21;
DOTLexer.LINE_COMMENT = 22;
DOTLexer.PREPROC = 23;
DOTLexer.WS = 24;

DOTLexer.prototype.channelNames = [ "DEFAULT_TOKEN_CHANNEL", "HIDDEN" ];

DOTLexer.prototype.modeNames = [ "DEFAULT_MODE" ];

DOTLexer.prototype.literalNames = [ null, "'{'", "'}'", "';'", "'='", "'['", 
                                    "']'", "','", "'->'", "'--'", "':'" ];

DOTLexer.prototype.symbolicNames = [ null, null, null, null, null, null, 
                                     null, null, null, null, null, "STRICT", 
                                     "GRAPH", "DIGRAPH", "NODE", "EDGE", 
                                     "SUBGRAPH", "NUMBER", "STRING", "ID", 
                                     "HTML_STRING", "COMMENT", "LINE_COMMENT", 
                                     "PREPROC", "WS" ];

DOTLexer.prototype.ruleNames = [ "T__0", "T__1", "T__2", "T__3", "T__4", 
                                 "T__5", "T__6", "T__7", "T__8", "T__9", 
                                 "STRICT", "GRAPH", "DIGRAPH", "NODE", "EDGE", 
                                 "SUBGRAPH", "NUMBER", "DIGIT", "STRING", 
                                 "ID", "LETTER", "HTML_STRING", "TAG", "COMMENT", 
                                 "LINE_COMMENT", "PREPROC", "WS" ];

DOTLexer.prototype.grammarFileName = "DOT.g4";



exports.DOTLexer = DOTLexer;

