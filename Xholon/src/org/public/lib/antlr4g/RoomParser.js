// Generated from Room.g4 by ANTLR 4.7
// jshint ignore: start
var antlr4 = require('xholon/lib/antlr4/index');
var RoomListener = require('./RoomListener').RoomListener;
var grammarFileName = "Room.g4";

var serializedATN = ["\u0003\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964",
    "\u0003+\u0106\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t",
    "\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t\u0007\u0004",
    "\b\t\b\u0004\t\t\t\u0004\n\t\n\u0004\u000b\t\u000b\u0004\f\t\f\u0004",
    "\r\t\r\u0004\u000e\t\u000e\u0004\u000f\t\u000f\u0004\u0010\t\u0010\u0004",
    "\u0011\t\u0011\u0004\u0012\t\u0012\u0004\u0013\t\u0013\u0004\u0014\t",
    "\u0014\u0004\u0015\t\u0015\u0003\u0002\u0003\u0002\u0003\u0002\u0005",
    "\u0002.\n\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003",
    "\u0002\u0003\u0002\u0003\u0002\u0007\u00027\n\u0002\f\u0002\u000e\u0002",
    ":\u000b\u0002\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003",
    "\u0003\u0003\u0003\u0007\u0003B\n\u0003\f\u0003\u000e\u0003E\u000b\u0003",
    "\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004",
    "\u0005\u0004M\n\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0005\u0005",
    "R\n\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0007\u0005",
    "X\n\u0005\f\u0005\u000e\u0005[\u000b\u0005\u0003\u0005\u0003\u0005\u0003",
    "\u0006\u0003\u0006\u0003\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0003",
    "\u0007\u0003\u0007\u0003\u0007\u0003\b\u0003\b\u0005\bj\n\b\u0003\t",
    "\u0003\t\u0003\t\u0005\to\n\t\u0003\t\u0003\t\u0005\ts\n\t\u0003\t\u0003",
    "\t\u0003\t\u0003\t\u0007\ty\n\t\f\t\u000e\t|\u000b\t\u0003\t\u0005\t",
    "\u007f\n\t\u0003\t\u0003\t\u0003\t\u0007\t\u0084\n\t\f\t\u000e\t\u0087",
    "\u000b\t\u0003\t\u0005\t\u008a\n\t\u0003\t\u0003\t\u0003\t\u0005\t\u008f",
    "\n\t\u0003\t\u0003\t\u0003\t\u0005\t\u0094\n\t\u0003\t\u0003\t\u0003",
    "\n\u0003\n\u0003\n\u0005\n\u009b\n\n\u0003\n\u0003\n\u0007\n\u009f\n",
    "\n\f\n\u000e\n\u00a2\u000b\n\u0003\n\u0003\n\u0003\u000b\u0003\u000b",
    "\u0003\u000b\u0003\u000b\u0003\u000b\u0003\f\u0005\f\u00ac\n\f\u0003",
    "\f\u0003\f\u0003\f\u0003\f\u0005\f\u00b2\n\f\u0003\f\u0003\f\u0005\f",
    "\u00b6\n\f\u0003\r\u0003\r\u0003\u000e\u0003\u000e\u0003\u000e\u0003",
    "\u000f\u0005\u000f\u00be\n\u000f\u0003\u000f\u0003\u000f\u0003\u000f",
    "\u0005\u000f\u00c3\n\u000f\u0003\u000f\u0003\u000f\u0005\u000f\u00c7",
    "\n\u000f\u0003\u000f\u0003\u000f\u0005\u000f\u00cb\n\u000f\u0003\u0010",
    "\u0003\u0010\u0003\u0010\u0005\u0010\u00d0\n\u0010\u0003\u0010\u0003",
    "\u0010\u0005\u0010\u00d4\n\u0010\u0003\u0010\u0003\u0010\u0003\u0010",
    "\u0003\u0010\u0005\u0010\u00da\n\u0010\u0003\u0010\u0005\u0010\u00dd",
    "\n\u0010\u0003\u0010\u0005\u0010\u00e0\n\u0010\u0003\u0010\u0003\u0010",
    "\u0003\u0011\u0003\u0011\u0003\u0011\u0007\u0011\u00e7\n\u0011\f\u0011",
    "\u000e\u0011\u00ea\u000b\u0011\u0003\u0011\u0003\u0011\u0003\u0012\u0003",
    "\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0013\u0003\u0013\u0003",
    "\u0013\u0003\u0013\u0003\u0014\u0003\u0014\u0003\u0014\u0005\u0014\u00fa",
    "\n\u0014\u0003\u0014\u0003\u0014\u0005\u0014\u00fe\n\u0014\u0003\u0014",
    "\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0015\u0003\u0015\u0003\u0015",
    "\u0002\u0002\u0016\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016",
    "\u0018\u001a\u001c\u001e \"$&(\u0002\u0002\u0002\u0118\u0002*\u0003",
    "\u0002\u0002\u0002\u0004=\u0003\u0002\u0002\u0002\u0006H\u0003\u0002",
    "\u0002\u0002\bN\u0003\u0002\u0002\u0002\n^\u0003\u0002\u0002\u0002\f",
    "a\u0003\u0002\u0002\u0002\u000ei\u0003\u0002\u0002\u0002\u0010k\u0003",
    "\u0002\u0002\u0002\u0012\u0097\u0003\u0002\u0002\u0002\u0014\u00a5\u0003",
    "\u0002\u0002\u0002\u0016\u00ab\u0003\u0002\u0002\u0002\u0018\u00b7\u0003",
    "\u0002\u0002\u0002\u001a\u00b9\u0003\u0002\u0002\u0002\u001c\u00bd\u0003",
    "\u0002\u0002\u0002\u001e\u00cc\u0003\u0002\u0002\u0002 \u00e3\u0003",
    "\u0002\u0002\u0002\"\u00ed\u0003\u0002\u0002\u0002$\u00f2\u0003\u0002",
    "\u0002\u0002&\u00f6\u0003\u0002\u0002\u0002(\u0103\u0003\u0002\u0002",
    "\u0002*+\u0007\u0003\u0002\u0002+-\u0007\'\u0002\u0002,.\u0007&\u0002",
    "\u0002-,\u0003\u0002\u0002\u0002-.\u0003\u0002\u0002\u0002./\u0003\u0002",
    "\u0002\u0002/8\u0007\u0004\u0002\u000207\u0005&\u0014\u000217\u0005",
    "\u000e\b\u000227\u0005\u001e\u0010\u000237\u0005\b\u0005\u000247\u0005",
    "\u0004\u0003\u000257\u0007#\u0002\u000260\u0003\u0002\u0002\u000261",
    "\u0003\u0002\u0002\u000262\u0003\u0002\u0002\u000263\u0003\u0002\u0002",
    "\u000264\u0003\u0002\u0002\u000265\u0003\u0002\u0002\u00027:\u0003\u0002",
    "\u0002\u000286\u0003\u0002\u0002\u000289\u0003\u0002\u0002\u00029;\u0003",
    "\u0002\u0002\u0002:8\u0003\u0002\u0002\u0002;<\u0007\u0005\u0002\u0002",
    "<\u0003\u0003\u0002\u0002\u0002=>\u0007\u0006\u0002\u0002>?\u0007\'",
    "\u0002\u0002?C\u0007\u0004\u0002\u0002@B\u0005\u0006\u0004\u0002A@\u0003",
    "\u0002\u0002\u0002BE\u0003\u0002\u0002\u0002CA\u0003\u0002\u0002\u0002",
    "CD\u0003\u0002\u0002\u0002DF\u0003\u0002\u0002\u0002EC\u0003\u0002\u0002",
    "\u0002FG\u0007\u0005\u0002\u0002G\u0005\u0003\u0002\u0002\u0002HI\u0007",
    "\u0007\u0002\u0002IJ\u0007(\u0002\u0002JL\u0007\'\u0002\u0002KM\u0007",
    "&\u0002\u0002LK\u0003\u0002\u0002\u0002LM\u0003\u0002\u0002\u0002M\u0007",
    "\u0003\u0002\u0002\u0002NO\u0007\b\u0002\u0002OQ\u0007\'\u0002\u0002",
    "PR\u0007&\u0002\u0002QP\u0003\u0002\u0002\u0002QR\u0003\u0002\u0002",
    "\u0002RS\u0003\u0002\u0002\u0002SY\u0007\u0004\u0002\u0002TX\u0005\n",
    "\u0006\u0002UX\u0005\f\u0007\u0002VX\u0005\u001c\u000f\u0002WT\u0003",
    "\u0002\u0002\u0002WU\u0003\u0002\u0002\u0002WV\u0003\u0002\u0002\u0002",
    "X[\u0003\u0002\u0002\u0002YW\u0003\u0002\u0002\u0002YZ\u0003\u0002\u0002",
    "\u0002Z\\\u0003\u0002\u0002\u0002[Y\u0003\u0002\u0002\u0002\\]\u0007",
    "\u0005\u0002\u0002]\t\u0003\u0002\u0002\u0002^_\u0007\t\u0002\u0002",
    "_`\u0007\'\u0002\u0002`\u000b\u0003\u0002\u0002\u0002ab\u0007\n\u0002",
    "\u0002bc\u0007\u000b\u0002\u0002cd\u0007\'\u0002\u0002de\u0007\f\u0002",
    "\u0002ef\u0007\'\u0002\u0002f\r\u0003\u0002\u0002\u0002gj\u0005\u0010",
    "\t\u0002hj\u0005\u0012\n\u0002ig\u0003\u0002\u0002\u0002ih\u0003\u0002",
    "\u0002\u0002j\u000f\u0003\u0002\u0002\u0002kl\u0007\r\u0002\u0002ln",
    "\u0007\'\u0002\u0002mo\u0007&\u0002\u0002nm\u0003\u0002\u0002\u0002",
    "no\u0003\u0002\u0002\u0002or\u0003\u0002\u0002\u0002pq\u0007\u000e\u0002",
    "\u0002qs\u0007\'\u0002\u0002rp\u0003\u0002\u0002\u0002rs\u0003\u0002",
    "\u0002\u0002st\u0003\u0002\u0002\u0002t~\u0007\u0004\u0002\u0002uv\u0007",
    "\u000f\u0002\u0002vz\u0007\u0004\u0002\u0002wy\u0005\u0016\f\u0002x",
    "w\u0003\u0002\u0002\u0002y|\u0003\u0002\u0002\u0002zx\u0003\u0002\u0002",
    "\u0002z{\u0003\u0002\u0002\u0002{}\u0003\u0002\u0002\u0002|z\u0003\u0002",
    "\u0002\u0002}\u007f\u0007\u0005\u0002\u0002~u\u0003\u0002\u0002\u0002",
    "~\u007f\u0003\u0002\u0002\u0002\u007f\u0089\u0003\u0002\u0002\u0002",
    "\u0080\u0081\u0007\u0010\u0002\u0002\u0081\u0085\u0007\u0004\u0002\u0002",
    "\u0082\u0084\u0005\u0016\f\u0002\u0083\u0082\u0003\u0002\u0002\u0002",
    "\u0084\u0087\u0003\u0002\u0002\u0002\u0085\u0083\u0003\u0002\u0002\u0002",
    "\u0085\u0086\u0003\u0002\u0002\u0002\u0086\u0088\u0003\u0002\u0002\u0002",
    "\u0087\u0085\u0003\u0002\u0002\u0002\u0088\u008a\u0007\u0005\u0002\u0002",
    "\u0089\u0080\u0003\u0002\u0002\u0002\u0089\u008a\u0003\u0002\u0002\u0002",
    "\u008a\u008e\u0003\u0002\u0002\u0002\u008b\u008c\u0007\u0011\u0002\u0002",
    "\u008c\u008d\u0007\u0012\u0002\u0002\u008d\u008f\u0005\u001a\u000e\u0002",
    "\u008e\u008b\u0003\u0002\u0002\u0002\u008e\u008f\u0003\u0002\u0002\u0002",
    "\u008f\u0093\u0003\u0002\u0002\u0002\u0090\u0091\u0007\u0013\u0002\u0002",
    "\u0091\u0092\u0007\u0012\u0002\u0002\u0092\u0094\u0005\u001a\u000e\u0002",
    "\u0093\u0090\u0003\u0002\u0002\u0002\u0093\u0094\u0003\u0002\u0002\u0002",
    "\u0094\u0095\u0003\u0002\u0002\u0002\u0095\u0096\u0007\u0005\u0002\u0002",
    "\u0096\u0011\u0003\u0002\u0002\u0002\u0097\u0098\u0007\u0014\u0002\u0002",
    "\u0098\u009a\u0007\'\u0002\u0002\u0099\u009b\u0007&\u0002\u0002\u009a",
    "\u0099\u0003\u0002\u0002\u0002\u009a\u009b\u0003\u0002\u0002\u0002\u009b",
    "\u009c\u0003\u0002\u0002\u0002\u009c\u00a0\u0007\u0004\u0002\u0002\u009d",
    "\u009f\u0005\u0014\u000b\u0002\u009e\u009d\u0003\u0002\u0002\u0002\u009f",
    "\u00a2\u0003\u0002\u0002\u0002\u00a0\u009e\u0003\u0002\u0002\u0002\u00a0",
    "\u00a1\u0003\u0002\u0002\u0002\u00a1\u00a3\u0003\u0002\u0002\u0002\u00a2",
    "\u00a0\u0003\u0002\u0002\u0002\u00a3\u00a4\u0007\u0005\u0002\u0002\u00a4",
    "\u0013\u0003\u0002\u0002\u0002\u00a5\u00a6\u0007\u0015\u0002\u0002\u00a6",
    "\u00a7\u0007\'\u0002\u0002\u00a7\u00a8\u0007\u0016\u0002\u0002\u00a8",
    "\u00a9\u0007\'\u0002\u0002\u00a9\u0015\u0003\u0002\u0002\u0002\u00aa",
    "\u00ac\u0007\u0017\u0002\u0002\u00ab\u00aa\u0003\u0002\u0002\u0002\u00ab",
    "\u00ac\u0003\u0002\u0002\u0002\u00ac\u00ad\u0003\u0002\u0002\u0002\u00ad",
    "\u00ae\u0007\u0018\u0002\u0002\u00ae\u00af\u0007\'\u0002\u0002\u00af",
    "\u00b1\u0007\u0019\u0002\u0002\u00b0\u00b2\u0005\u0018\r\u0002\u00b1",
    "\u00b0\u0003\u0002\u0002\u0002\u00b1\u00b2\u0003\u0002\u0002\u0002\u00b2",
    "\u00b3\u0003\u0002\u0002\u0002\u00b3\u00b5\u0007\u001a\u0002\u0002\u00b4",
    "\u00b6\u0007&\u0002\u0002\u00b5\u00b4\u0003\u0002\u0002\u0002\u00b5",
    "\u00b6\u0003\u0002\u0002\u0002\u00b6\u0017\u0003\u0002\u0002\u0002\u00b7",
    "\u00b8\u0007\'\u0002\u0002\u00b8\u0019\u0003\u0002\u0002\u0002\u00b9",
    "\u00ba\u0007\u0004\u0002\u0002\u00ba\u00bb\u0007\u0005\u0002\u0002\u00bb",
    "\u001b\u0003\u0002\u0002\u0002\u00bc\u00be\u0007$\u0002\u0002\u00bd",
    "\u00bc\u0003\u0002\u0002\u0002\u00bd\u00be\u0003\u0002\u0002\u0002\u00be",
    "\u00bf\u0003\u0002\u0002\u0002\u00bf\u00c6\u0007\u001b\u0002\u0002\u00c0",
    "\u00c2\u0007\'\u0002\u0002\u00c1\u00c3\u0007%\u0002\u0002\u00c2\u00c1",
    "\u0003\u0002\u0002\u0002\u00c2\u00c3\u0003\u0002\u0002\u0002\u00c3\u00c4",
    "\u0003\u0002\u0002\u0002\u00c4\u00c7\u0007\u0016\u0002\u0002\u00c5\u00c7",
    "\u0007(\u0002\u0002\u00c6\u00c0\u0003\u0002\u0002\u0002\u00c6\u00c5",
    "\u0003\u0002\u0002\u0002\u00c7\u00c8\u0003\u0002\u0002\u0002\u00c8\u00ca",
    "\u0007\'\u0002\u0002\u00c9\u00cb\u0007&\u0002\u0002\u00ca\u00c9\u0003",
    "\u0002\u0002\u0002\u00ca\u00cb\u0003\u0002\u0002\u0002\u00cb\u001d\u0003",
    "\u0002\u0002\u0002\u00cc\u00cd\u0007\u001c\u0002\u0002\u00cd\u00cf\u0007",
    "\'\u0002\u0002\u00ce\u00d0\u0007&\u0002\u0002\u00cf\u00ce\u0003\u0002",
    "\u0002\u0002\u00cf\u00d0\u0003\u0002\u0002\u0002\u00d0\u00d3\u0003\u0002",
    "\u0002\u0002\u00d1\u00d2\u0007\u000e\u0002\u0002\u00d2\u00d4\u0007\'",
    "\u0002\u0002\u00d3\u00d1\u0003\u0002\u0002\u0002\u00d3\u00d4\u0003\u0002",
    "\u0002\u0002\u00d4\u00d5\u0003\u0002\u0002\u0002\u00d5\u00d9\u0007\u0004",
    "\u0002\u0002\u00d6\u00d7\u0007\u001d\u0002\u0002\u00d7\u00d8\u0007\u0004",
    "\u0002\u0002\u00d8\u00da\u0007\u0005\u0002\u0002\u00d9\u00d6\u0003\u0002",
    "\u0002\u0002\u00d9\u00da\u0003\u0002\u0002\u0002\u00da\u00dc\u0003\u0002",
    "\u0002\u0002\u00db\u00dd\u0005 \u0011\u0002\u00dc\u00db\u0003\u0002",
    "\u0002\u0002\u00dc\u00dd\u0003\u0002\u0002\u0002\u00dd\u00df\u0003\u0002",
    "\u0002\u0002\u00de\u00e0\u0005\"\u0012\u0002\u00df\u00de\u0003\u0002",
    "\u0002\u0002\u00df\u00e0\u0003\u0002\u0002\u0002\u00e0\u00e1\u0003\u0002",
    "\u0002\u0002\u00e1\u00e2\u0007\u0005\u0002\u0002\u00e2\u001f\u0003\u0002",
    "\u0002\u0002\u00e3\u00e4\u0007\u001e\u0002\u0002\u00e4\u00e8\u0007\u0004",
    "\u0002\u0002\u00e5\u00e7\u0005\u001c\u000f\u0002\u00e6\u00e5\u0003\u0002",
    "\u0002\u0002\u00e7\u00ea\u0003\u0002\u0002\u0002\u00e8\u00e6\u0003\u0002",
    "\u0002\u0002\u00e8\u00e9\u0003\u0002\u0002\u0002\u00e9\u00eb\u0003\u0002",
    "\u0002\u0002\u00ea\u00e8\u0003\u0002\u0002\u0002\u00eb\u00ec\u0007\u0005",
    "\u0002\u0002\u00ec!\u0003\u0002\u0002\u0002\u00ed\u00ee\u0007\u001f",
    "\u0002\u0002\u00ee\u00ef\u0007\u0004\u0002\u0002\u00ef\u00f0\u0005$",
    "\u0013\u0002\u00f0\u00f1\u0007\u0005\u0002\u0002\u00f1#\u0003\u0002",
    "\u0002\u0002\u00f2\u00f3\u0007 \u0002\u0002\u00f3\u00f4\u0007\u0004",
    "\u0002\u0002\u00f4\u00f5\u0007\u0005\u0002\u0002\u00f5%\u0003\u0002",
    "\u0002\u0002\u00f6\u00f7\u0007!\u0002\u0002\u00f7\u00f9\u0007\'\u0002",
    "\u0002\u00f8\u00fa\u0007&\u0002\u0002\u00f9\u00f8\u0003\u0002\u0002",
    "\u0002\u00f9\u00fa\u0003\u0002\u0002\u0002\u00fa\u00fd\u0003\u0002\u0002",
    "\u0002\u00fb\u00fc\u0007\u000e\u0002\u0002\u00fc\u00fe\u0007\'\u0002",
    "\u0002\u00fd\u00fb\u0003\u0002\u0002\u0002\u00fd\u00fe\u0003\u0002\u0002",
    "\u0002\u00fe\u00ff\u0003\u0002\u0002\u0002\u00ff\u0100\u0007\u0004\u0002",
    "\u0002\u0100\u0101\u0007\'\u0002\u0002\u0101\u0102\u0007\u0005\u0002",
    "\u0002\u0102\'\u0003\u0002\u0002\u0002\u0103\u0104\u0007\"\u0002\u0002",
    "\u0104)\u0003\u0002\u0002\u0002$-68CLQWYinrz~\u0085\u0089\u008e\u0093",
    "\u009a\u00a0\u00ab\u00b1\u00b5\u00bd\u00c2\u00c6\u00ca\u00cf\u00d3\u00d9",
    "\u00dc\u00df\u00e8\u00f9\u00fd"].join("");


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
                     "'Structure'", "'Behavior'", "'StateMachine'", "'DataClass'", 
                     "'impotodo'", null, "'rtypetodo'" ];

var symbolicNames = [ null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, "ImportRoom", 
                      "ReferenceType", "MULTIPLICITY", "Documentation", 
                      "ID", "RoomName", "ML_COMMENT", "SL_COMMENT", "WS" ];

var ruleNames =  [ "roomModel", "logicalSystem", "subSystemRef", "subSystemClass", 
                   "logicalThread", "layerConnection", "generalProtocolClass", 
                   "protocolClass", "compoundProtocolClass", "subProtocol", 
                   "message", "varDecl", "portClass", "actorRef", "actorClass", 
                   "structure", "behavior", "stateMachine", "dataClass", 
                   "importedFQN" ];

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
RoomParser.ImportRoom = 33;
RoomParser.ReferenceType = 34;
RoomParser.MULTIPLICITY = 35;
RoomParser.Documentation = 36;
RoomParser.ID = 37;
RoomParser.RoomName = 38;
RoomParser.ML_COMMENT = 39;
RoomParser.SL_COMMENT = 40;
RoomParser.WS = 41;

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
RoomParser.RULE_dataClass = 18;
RoomParser.RULE_importedFQN = 19;

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
        this.state = 40;
        this.match(RoomParser.T__0);
        this.state = 41;
        this.match(RoomParser.ID);
        this.state = 43;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 42;
            this.match(RoomParser.Documentation);
        }

        this.state = 45;
        this.match(RoomParser.T__1);
        this.state = 54;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(((((_la - 4)) & ~0x1f) == 0 && ((1 << (_la - 4)) & ((1 << (RoomParser.T__3 - 4)) | (1 << (RoomParser.T__5 - 4)) | (1 << (RoomParser.T__10 - 4)) | (1 << (RoomParser.T__17 - 4)) | (1 << (RoomParser.T__25 - 4)) | (1 << (RoomParser.T__30 - 4)) | (1 << (RoomParser.ImportRoom - 4)))) !== 0)) {
            this.state = 52;
            this._errHandler.sync(this);
            switch(this._input.LA(1)) {
            case RoomParser.T__30:
                this.state = 46;
                this.dataClass();
                break;
            case RoomParser.T__10:
            case RoomParser.T__17:
                this.state = 47;
                this.generalProtocolClass();
                break;
            case RoomParser.T__25:
                this.state = 48;
                this.actorClass();
                break;
            case RoomParser.T__5:
                this.state = 49;
                this.subSystemClass();
                break;
            case RoomParser.T__3:
                this.state = 50;
                this.logicalSystem();
                break;
            case RoomParser.ImportRoom:
                this.state = 51;
                this.match(RoomParser.ImportRoom);
                break;
            default:
                throw new antlr4.error.NoViableAltException(this);
            }
            this.state = 56;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 57;
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
        this.state = 59;
        this.match(RoomParser.T__3);
        this.state = 60;
        this.match(RoomParser.ID);
        this.state = 61;
        this.match(RoomParser.T__1);
        this.state = 65;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===RoomParser.T__4) {
            this.state = 62;
            this.subSystemRef();
            this.state = 67;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 68;
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
        this.state = 70;
        this.match(RoomParser.T__4);
        this.state = 71;
        this.match(RoomParser.RoomName);
        this.state = 72;
        this.match(RoomParser.ID);
        this.state = 74;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 73;
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
        this.state = 76;
        this.match(RoomParser.T__5);
        this.state = 77;
        this.match(RoomParser.ID);
        this.state = 79;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 78;
            this.match(RoomParser.Documentation);
        }

        this.state = 81;
        this.match(RoomParser.T__1);
        this.state = 87;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(((((_la - 7)) & ~0x1f) == 0 && ((1 << (_la - 7)) & ((1 << (RoomParser.T__6 - 7)) | (1 << (RoomParser.T__7 - 7)) | (1 << (RoomParser.T__24 - 7)) | (1 << (RoomParser.ReferenceType - 7)))) !== 0)) {
            this.state = 85;
            this._errHandler.sync(this);
            switch(this._input.LA(1)) {
            case RoomParser.T__6:
                this.state = 82;
                this.logicalThread();
                break;
            case RoomParser.T__7:
                this.state = 83;
                this.layerConnection();
                break;
            case RoomParser.T__24:
            case RoomParser.ReferenceType:
                this.state = 84;
                this.actorRef();
                break;
            default:
                throw new antlr4.error.NoViableAltException(this);
            }
            this.state = 89;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 90;
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
        this.state = 92;
        this.match(RoomParser.T__6);
        this.state = 93;
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
        this.state = 95;
        this.match(RoomParser.T__7);
        this.state = 96;
        this.match(RoomParser.T__8);
        this.state = 97;
        this.match(RoomParser.ID);
        this.state = 98;
        this.match(RoomParser.T__9);
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
        this.state = 103;
        this._errHandler.sync(this);
        switch(this._input.LA(1)) {
        case RoomParser.T__10:
            this.enterOuterAlt(localctx, 1);
            this.state = 101;
            this.protocolClass();
            break;
        case RoomParser.T__17:
            this.enterOuterAlt(localctx, 2);
            this.state = 102;
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
        this.state = 105;
        this.match(RoomParser.T__10);
        this.state = 106;
        this.match(RoomParser.ID);
        this.state = 108;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 107;
            this.match(RoomParser.Documentation);
        }

        this.state = 112;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__11) {
            this.state = 110;
            this.match(RoomParser.T__11);
            this.state = 111;
            this.match(RoomParser.ID);
        }

        this.state = 114;
        this.match(RoomParser.T__1);
        this.state = 124;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__12) {
            this.state = 115;
            this.match(RoomParser.T__12);
            this.state = 116;
            this.match(RoomParser.T__1);
            this.state = 120;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            while(_la===RoomParser.T__20 || _la===RoomParser.T__21) {
                this.state = 117;
                this.message();
                this.state = 122;
                this._errHandler.sync(this);
                _la = this._input.LA(1);
            }
            this.state = 123;
            this.match(RoomParser.T__2);
        }

        this.state = 135;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__13) {
            this.state = 126;
            this.match(RoomParser.T__13);
            this.state = 127;
            this.match(RoomParser.T__1);
            this.state = 131;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            while(_la===RoomParser.T__20 || _la===RoomParser.T__21) {
                this.state = 128;
                this.message();
                this.state = 133;
                this._errHandler.sync(this);
                _la = this._input.LA(1);
            }
            this.state = 134;
            this.match(RoomParser.T__2);
        }

        this.state = 140;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__14) {
            this.state = 137;
            this.match(RoomParser.T__14);
            this.state = 138;
            this.match(RoomParser.T__15);
            this.state = 139;
            this.portClass();
        }

        this.state = 145;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__16) {
            this.state = 142;
            this.match(RoomParser.T__16);
            this.state = 143;
            this.match(RoomParser.T__15);
            this.state = 144;
            this.portClass();
        }

        this.state = 147;
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
        this.state = 149;
        this.match(RoomParser.T__17);
        this.state = 150;
        this.match(RoomParser.ID);
        this.state = 152;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 151;
            this.match(RoomParser.Documentation);
        }

        this.state = 154;
        this.match(RoomParser.T__1);
        this.state = 158;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===RoomParser.T__18) {
            this.state = 155;
            this.subProtocol();
            this.state = 160;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 161;
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
        this.state = 163;
        this.match(RoomParser.T__18);
        this.state = 164;
        this.match(RoomParser.ID);
        this.state = 165;
        this.match(RoomParser.T__19);
        this.state = 166;
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
        this.state = 169;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__20) {
            this.state = 168;
            this.match(RoomParser.T__20);
        }

        this.state = 171;
        this.match(RoomParser.T__21);
        this.state = 172;
        this.match(RoomParser.ID);
        this.state = 173;
        this.match(RoomParser.T__22);
        this.state = 175;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.ID) {
            this.state = 174;
            this.varDecl();
        }

        this.state = 177;
        this.match(RoomParser.T__23);
        this.state = 179;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 178;
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
        this.state = 181;
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
        this.state = 183;
        this.match(RoomParser.T__1);
        this.state = 184;
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
        this.state = 187;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.ReferenceType) {
            this.state = 186;
            this.match(RoomParser.ReferenceType);
        }

        this.state = 189;
        this.match(RoomParser.T__24);
        this.state = 196;
        this._errHandler.sync(this);
        switch(this._input.LA(1)) {
        case RoomParser.ID:
            this.state = 190;
            this.match(RoomParser.ID);
            this.state = 192;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if(_la===RoomParser.MULTIPLICITY) {
                this.state = 191;
                this.match(RoomParser.MULTIPLICITY);
            }

            this.state = 194;
            this.match(RoomParser.T__19);
            break;
        case RoomParser.RoomName:
            this.state = 195;
            this.match(RoomParser.RoomName);
            break;
        default:
            throw new antlr4.error.NoViableAltException(this);
        }
        this.state = 198;
        this.match(RoomParser.ID);
        this.state = 200;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 199;
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
        this.state = 202;
        this.match(RoomParser.T__25);
        this.state = 203;
        this.match(RoomParser.ID);
        this.state = 205;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 204;
            this.match(RoomParser.Documentation);
        }

        this.state = 209;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__11) {
            this.state = 207;
            this.match(RoomParser.T__11);
            this.state = 208;
            this.match(RoomParser.ID);
        }

        this.state = 211;
        this.match(RoomParser.T__1);
        this.state = 215;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__26) {
            this.state = 212;
            this.match(RoomParser.T__26);
            this.state = 213;
            this.match(RoomParser.T__1);
            this.state = 214;
            this.match(RoomParser.T__2);
        }

        this.state = 218;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__27) {
            this.state = 217;
            this.structure();
        }

        this.state = 221;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__28) {
            this.state = 220;
            this.behavior();
        }

        this.state = 223;
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
        this.state = 225;
        this.match(RoomParser.T__27);
        this.state = 226;
        this.match(RoomParser.T__1);
        this.state = 230;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===RoomParser.T__24 || _la===RoomParser.ReferenceType) {
            this.state = 227;
            this.actorRef();
            this.state = 232;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 233;
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
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 235;
        this.match(RoomParser.T__28);
        this.state = 236;
        this.match(RoomParser.T__1);
        this.state = 237;
        this.stateMachine();
        this.state = 238;
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
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 240;
        this.match(RoomParser.T__29);
        this.state = 241;
        this.match(RoomParser.T__1);
        this.state = 242;
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
    this.enterRule(localctx, 36, RoomParser.RULE_dataClass);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 244;
        this.match(RoomParser.T__30);
        this.state = 245;
        this.match(RoomParser.ID);
        this.state = 247;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.Documentation) {
            this.state = 246;
            this.match(RoomParser.Documentation);
        }

        this.state = 251;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===RoomParser.T__11) {
            this.state = 249;
            this.match(RoomParser.T__11);
            this.state = 250;
            this.match(RoomParser.ID);
        }

        this.state = 253;
        this.match(RoomParser.T__1);
        this.state = 254;
        this.match(RoomParser.ID);
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
    this.enterRule(localctx, 38, RoomParser.RULE_importedFQN);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 257;
        this.match(RoomParser.T__31);
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
