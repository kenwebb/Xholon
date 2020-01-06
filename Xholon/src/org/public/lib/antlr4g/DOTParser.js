// Generated from grammars-v4-master/dot/DOT.g4 by ANTLR 4.7
// jshint ignore: start
var antlr4 = require('xholon/lib/antlr4/index');
var DOTListener = require('./DOTListener').DOTListener;
var grammarFileName = "DOT.g4";

var serializedATN = ["\u0003\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964",
    "\u0003\u001a\u0082\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004",
    "\t\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t\u0007",
    "\u0004\b\t\b\u0004\t\t\t\u0004\n\t\n\u0004\u000b\t\u000b\u0004\f\t\f",
    "\u0004\r\t\r\u0004\u000e\t\u000e\u0004\u000f\t\u000f\u0003\u0002\u0005",
    "\u0002 \n\u0002\u0003\u0002\u0003\u0002\u0005\u0002$\n\u0002\u0003\u0002",
    "\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0005\u0003",
    ",\n\u0003\u0007\u0003.\n\u0003\f\u0003\u000e\u00031\u000b\u0003\u0003",
    "\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003",
    "\u0004\u0003\u0004\u0005\u0004;\n\u0004\u0003\u0005\u0003\u0005\u0003",
    "\u0005\u0003\u0006\u0003\u0006\u0005\u0006B\n\u0006\u0003\u0006\u0006",
    "\u0006E\n\u0006\r\u0006\u000e\u0006F\u0003\u0007\u0003\u0007\u0003\u0007",
    "\u0005\u0007L\n\u0007\u0003\u0007\u0005\u0007O\n\u0007\u0006\u0007Q",
    "\n\u0007\r\u0007\u000e\u0007R\u0003\b\u0003\b\u0005\bW\n\b\u0003\b\u0003",
    "\b\u0005\b[\n\b\u0003\t\u0003\t\u0003\t\u0005\t`\n\t\u0006\tb\n\t\r",
    "\t\u000e\tc\u0003\n\u0003\n\u0003\u000b\u0003\u000b\u0005\u000bj\n\u000b",
    "\u0003\f\u0003\f\u0005\fn\n\f\u0003\r\u0003\r\u0003\r\u0003\r\u0005",
    "\rt\n\r\u0003\u000e\u0003\u000e\u0005\u000ex\n\u000e\u0005\u000ez\n",
    "\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000f\u0003",
    "\u000f\u0003\u000f\u0002\u0002\u0010\u0002\u0004\u0006\b\n\f\u000e\u0010",
    "\u0012\u0014\u0016\u0018\u001a\u001c\u0002\u0006\u0003\u0002\u000e\u000f",
    "\u0004\u0002\u000e\u000e\u0010\u0011\u0003\u0002\n\u000b\u0003\u0002",
    "\u0013\u0016\u0002\u0089\u0002\u001f\u0003\u0002\u0002\u0002\u0004/",
    "\u0003\u0002\u0002\u0002\u0006:\u0003\u0002\u0002\u0002\b<\u0003\u0002",
    "\u0002\u0002\nD\u0003\u0002\u0002\u0002\fP\u0003\u0002\u0002\u0002\u000e",
    "V\u0003\u0002\u0002\u0002\u0010a\u0003\u0002\u0002\u0002\u0012e\u0003",
    "\u0002\u0002\u0002\u0014g\u0003\u0002\u0002\u0002\u0016k\u0003\u0002",
    "\u0002\u0002\u0018o\u0003\u0002\u0002\u0002\u001ay\u0003\u0002\u0002",
    "\u0002\u001c\u007f\u0003\u0002\u0002\u0002\u001e \u0007\r\u0002\u0002",
    "\u001f\u001e\u0003\u0002\u0002\u0002\u001f \u0003\u0002\u0002\u0002",
    " !\u0003\u0002\u0002\u0002!#\t\u0002\u0002\u0002\"$\u0005\u001c\u000f",
    "\u0002#\"\u0003\u0002\u0002\u0002#$\u0003\u0002\u0002\u0002$%\u0003",
    "\u0002\u0002\u0002%&\u0007\u0003\u0002\u0002&\'\u0005\u0004\u0003\u0002",
    "\'(\u0007\u0004\u0002\u0002(\u0003\u0003\u0002\u0002\u0002)+\u0005\u0006",
    "\u0004\u0002*,\u0007\u0005\u0002\u0002+*\u0003\u0002\u0002\u0002+,\u0003",
    "\u0002\u0002\u0002,.\u0003\u0002\u0002\u0002-)\u0003\u0002\u0002\u0002",
    ".1\u0003\u0002\u0002\u0002/-\u0003\u0002\u0002\u0002/0\u0003\u0002\u0002",
    "\u00020\u0005\u0003\u0002\u0002\u00021/\u0003\u0002\u0002\u00022;\u0005",
    "\u0014\u000b\u00023;\u0005\u000e\b\u00024;\u0005\b\u0005\u000256\u0005",
    "\u001c\u000f\u000267\u0007\u0006\u0002\u000278\u0005\u001c\u000f\u0002",
    "8;\u0003\u0002\u0002\u00029;\u0005\u001a\u000e\u0002:2\u0003\u0002\u0002",
    "\u0002:3\u0003\u0002\u0002\u0002:4\u0003\u0002\u0002\u0002:5\u0003\u0002",
    "\u0002\u0002:9\u0003\u0002\u0002\u0002;\u0007\u0003\u0002\u0002\u0002",
    "<=\t\u0003\u0002\u0002=>\u0005\n\u0006\u0002>\t\u0003\u0002\u0002\u0002",
    "?A\u0007\u0007\u0002\u0002@B\u0005\f\u0007\u0002A@\u0003\u0002\u0002",
    "\u0002AB\u0003\u0002\u0002\u0002BC\u0003\u0002\u0002\u0002CE\u0007\b",
    "\u0002\u0002D?\u0003\u0002\u0002\u0002EF\u0003\u0002\u0002\u0002FD\u0003",
    "\u0002\u0002\u0002FG\u0003\u0002\u0002\u0002G\u000b\u0003\u0002\u0002",
    "\u0002HK\u0005\u001c\u000f\u0002IJ\u0007\u0006\u0002\u0002JL\u0005\u001c",
    "\u000f\u0002KI\u0003\u0002\u0002\u0002KL\u0003\u0002\u0002\u0002LN\u0003",
    "\u0002\u0002\u0002MO\u0007\t\u0002\u0002NM\u0003\u0002\u0002\u0002N",
    "O\u0003\u0002\u0002\u0002OQ\u0003\u0002\u0002\u0002PH\u0003\u0002\u0002",
    "\u0002QR\u0003\u0002\u0002\u0002RP\u0003\u0002\u0002\u0002RS\u0003\u0002",
    "\u0002\u0002S\r\u0003\u0002\u0002\u0002TW\u0005\u0016\f\u0002UW\u0005",
    "\u001a\u000e\u0002VT\u0003\u0002\u0002\u0002VU\u0003\u0002\u0002\u0002",
    "WX\u0003\u0002\u0002\u0002XZ\u0005\u0010\t\u0002Y[\u0005\n\u0006\u0002",
    "ZY\u0003\u0002\u0002\u0002Z[\u0003\u0002\u0002\u0002[\u000f\u0003\u0002",
    "\u0002\u0002\\_\u0005\u0012\n\u0002]`\u0005\u0016\f\u0002^`\u0005\u001a",
    "\u000e\u0002_]\u0003\u0002\u0002\u0002_^\u0003\u0002\u0002\u0002`b\u0003",
    "\u0002\u0002\u0002a\\\u0003\u0002\u0002\u0002bc\u0003\u0002\u0002\u0002",
    "ca\u0003\u0002\u0002\u0002cd\u0003\u0002\u0002\u0002d\u0011\u0003\u0002",
    "\u0002\u0002ef\t\u0004\u0002\u0002f\u0013\u0003\u0002\u0002\u0002gi",
    "\u0005\u0016\f\u0002hj\u0005\n\u0006\u0002ih\u0003\u0002\u0002\u0002",
    "ij\u0003\u0002\u0002\u0002j\u0015\u0003\u0002\u0002\u0002km\u0005\u001c",
    "\u000f\u0002ln\u0005\u0018\r\u0002ml\u0003\u0002\u0002\u0002mn\u0003",
    "\u0002\u0002\u0002n\u0017\u0003\u0002\u0002\u0002op\u0007\f\u0002\u0002",
    "ps\u0005\u001c\u000f\u0002qr\u0007\f\u0002\u0002rt\u0005\u001c\u000f",
    "\u0002sq\u0003\u0002\u0002\u0002st\u0003\u0002\u0002\u0002t\u0019\u0003",
    "\u0002\u0002\u0002uw\u0007\u0012\u0002\u0002vx\u0005\u001c\u000f\u0002",
    "wv\u0003\u0002\u0002\u0002wx\u0003\u0002\u0002\u0002xz\u0003\u0002\u0002",
    "\u0002yu\u0003\u0002\u0002\u0002yz\u0003\u0002\u0002\u0002z{\u0003\u0002",
    "\u0002\u0002{|\u0007\u0003\u0002\u0002|}\u0005\u0004\u0003\u0002}~\u0007",
    "\u0004\u0002\u0002~\u001b\u0003\u0002\u0002\u0002\u007f\u0080\t\u0005",
    "\u0002\u0002\u0080\u001d\u0003\u0002\u0002\u0002\u0015\u001f#+/:AFK",
    "NRVZ_cimswy"].join("");


var atn = new antlr4.atn.ATNDeserializer().deserialize(serializedATN);

var decisionsToDFA = atn.decisionToState.map( function(ds, index) { return new antlr4.dfa.DFA(ds, index); });

var sharedContextCache = new antlr4.PredictionContextCache();

var literalNames = [ null, "'{'", "'}'", "';'", "'='", "'['", "']'", "','", 
                     "'->'", "'--'", "':'" ];

var symbolicNames = [ null, null, null, null, null, null, null, null, null, 
                      null, null, "STRICT", "GRAPH", "DIGRAPH", "NODE", 
                      "EDGE", "SUBGRAPH", "NUMBER", "STRING", "ID", "HTML_STRING", 
                      "COMMENT", "LINE_COMMENT", "PREPROC", "WS" ];

var ruleNames =  [ "graph", "stmt_list", "stmt", "attr_stmt", "attr_list", 
                   "a_list", "edge_stmt", "edgeRHS", "edgeop", "node_stmt", 
                   "node_id", "port", "subgraph", "id" ];

function DOTParser (input) {
	antlr4.Parser.call(this, input);
    this._interp = new antlr4.atn.ParserATNSimulator(this, atn, decisionsToDFA, sharedContextCache);
    this.ruleNames = ruleNames;
    this.literalNames = literalNames;
    this.symbolicNames = symbolicNames;
    return this;
}

DOTParser.prototype = Object.create(antlr4.Parser.prototype);
DOTParser.prototype.constructor = DOTParser;

Object.defineProperty(DOTParser.prototype, "atn", {
	get : function() {
		return atn;
	}
});

DOTParser.EOF = antlr4.Token.EOF;
DOTParser.T__0 = 1;
DOTParser.T__1 = 2;
DOTParser.T__2 = 3;
DOTParser.T__3 = 4;
DOTParser.T__4 = 5;
DOTParser.T__5 = 6;
DOTParser.T__6 = 7;
DOTParser.T__7 = 8;
DOTParser.T__8 = 9;
DOTParser.T__9 = 10;
DOTParser.STRICT = 11;
DOTParser.GRAPH = 12;
DOTParser.DIGRAPH = 13;
DOTParser.NODE = 14;
DOTParser.EDGE = 15;
DOTParser.SUBGRAPH = 16;
DOTParser.NUMBER = 17;
DOTParser.STRING = 18;
DOTParser.ID = 19;
DOTParser.HTML_STRING = 20;
DOTParser.COMMENT = 21;
DOTParser.LINE_COMMENT = 22;
DOTParser.PREPROC = 23;
DOTParser.WS = 24;

DOTParser.RULE_graph = 0;
DOTParser.RULE_stmt_list = 1;
DOTParser.RULE_stmt = 2;
DOTParser.RULE_attr_stmt = 3;
DOTParser.RULE_attr_list = 4;
DOTParser.RULE_a_list = 5;
DOTParser.RULE_edge_stmt = 6;
DOTParser.RULE_edgeRHS = 7;
DOTParser.RULE_edgeop = 8;
DOTParser.RULE_node_stmt = 9;
DOTParser.RULE_node_id = 10;
DOTParser.RULE_port = 11;
DOTParser.RULE_subgraph = 12;
DOTParser.RULE_id = 13;

function GraphContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DOTParser.RULE_graph;
    return this;
}

GraphContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
GraphContext.prototype.constructor = GraphContext;

GraphContext.prototype.stmt_list = function() {
    return this.getTypedRuleContext(Stmt_listContext,0);
};

GraphContext.prototype.GRAPH = function() {
    return this.getToken(DOTParser.GRAPH, 0);
};

GraphContext.prototype.DIGRAPH = function() {
    return this.getToken(DOTParser.DIGRAPH, 0);
};

GraphContext.prototype.STRICT = function() {
    return this.getToken(DOTParser.STRICT, 0);
};

GraphContext.prototype.id = function() {
    return this.getTypedRuleContext(IdContext,0);
};

GraphContext.prototype.enterRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.enterGraph(this);
	}
};

GraphContext.prototype.exitRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.exitGraph(this);
	}
};




DOTParser.GraphContext = GraphContext;

DOTParser.prototype.graph = function() {

    var localctx = new GraphContext(this, this._ctx, this.state);
    this.enterRule(localctx, 0, DOTParser.RULE_graph);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 29;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===DOTParser.STRICT) {
            this.state = 28;
            this.match(DOTParser.STRICT);
        }

        this.state = 31;
        _la = this._input.LA(1);
        if(!(_la===DOTParser.GRAPH || _la===DOTParser.DIGRAPH)) {
        this._errHandler.recoverInline(this);
        }
        else {
        	this._errHandler.reportMatch(this);
            this.consume();
        }
        this.state = 33;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << DOTParser.NUMBER) | (1 << DOTParser.STRING) | (1 << DOTParser.ID) | (1 << DOTParser.HTML_STRING))) !== 0)) {
            this.state = 32;
            this.id();
        }

        this.state = 35;
        this.match(DOTParser.T__0);
        this.state = 36;
        this.stmt_list();
        this.state = 37;
        this.match(DOTParser.T__1);
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

function Stmt_listContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DOTParser.RULE_stmt_list;
    return this;
}

Stmt_listContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
Stmt_listContext.prototype.constructor = Stmt_listContext;

Stmt_listContext.prototype.stmt = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(StmtContext);
    } else {
        return this.getTypedRuleContext(StmtContext,i);
    }
};

Stmt_listContext.prototype.enterRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.enterStmt_list(this);
	}
};

Stmt_listContext.prototype.exitRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.exitStmt_list(this);
	}
};




DOTParser.Stmt_listContext = Stmt_listContext;

DOTParser.prototype.stmt_list = function() {

    var localctx = new Stmt_listContext(this, this._ctx, this.state);
    this.enterRule(localctx, 2, DOTParser.RULE_stmt_list);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 45;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << DOTParser.T__0) | (1 << DOTParser.GRAPH) | (1 << DOTParser.NODE) | (1 << DOTParser.EDGE) | (1 << DOTParser.SUBGRAPH) | (1 << DOTParser.NUMBER) | (1 << DOTParser.STRING) | (1 << DOTParser.ID) | (1 << DOTParser.HTML_STRING))) !== 0)) {
            this.state = 39;
            this.stmt();
            this.state = 41;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if(_la===DOTParser.T__2) {
                this.state = 40;
                this.match(DOTParser.T__2);
            }

            this.state = 47;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
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

function StmtContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DOTParser.RULE_stmt;
    return this;
}

StmtContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
StmtContext.prototype.constructor = StmtContext;

StmtContext.prototype.node_stmt = function() {
    return this.getTypedRuleContext(Node_stmtContext,0);
};

StmtContext.prototype.edge_stmt = function() {
    return this.getTypedRuleContext(Edge_stmtContext,0);
};

StmtContext.prototype.attr_stmt = function() {
    return this.getTypedRuleContext(Attr_stmtContext,0);
};

StmtContext.prototype.id = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(IdContext);
    } else {
        return this.getTypedRuleContext(IdContext,i);
    }
};

StmtContext.prototype.subgraph = function() {
    return this.getTypedRuleContext(SubgraphContext,0);
};

StmtContext.prototype.enterRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.enterStmt(this);
	}
};

StmtContext.prototype.exitRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.exitStmt(this);
	}
};




DOTParser.StmtContext = StmtContext;

DOTParser.prototype.stmt = function() {

    var localctx = new StmtContext(this, this._ctx, this.state);
    this.enterRule(localctx, 4, DOTParser.RULE_stmt);
    try {
        this.state = 56;
        this._errHandler.sync(this);
        var la_ = this._interp.adaptivePredict(this._input,4,this._ctx);
        switch(la_) {
        case 1:
            this.enterOuterAlt(localctx, 1);
            this.state = 48;
            this.node_stmt();
            break;

        case 2:
            this.enterOuterAlt(localctx, 2);
            this.state = 49;
            this.edge_stmt();
            break;

        case 3:
            this.enterOuterAlt(localctx, 3);
            this.state = 50;
            this.attr_stmt();
            break;

        case 4:
            this.enterOuterAlt(localctx, 4);
            this.state = 51;
            this.id();
            this.state = 52;
            this.match(DOTParser.T__3);
            this.state = 53;
            this.id();
            break;

        case 5:
            this.enterOuterAlt(localctx, 5);
            this.state = 55;
            this.subgraph();
            break;

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

function Attr_stmtContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DOTParser.RULE_attr_stmt;
    return this;
}

Attr_stmtContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
Attr_stmtContext.prototype.constructor = Attr_stmtContext;

Attr_stmtContext.prototype.attr_list = function() {
    return this.getTypedRuleContext(Attr_listContext,0);
};

Attr_stmtContext.prototype.GRAPH = function() {
    return this.getToken(DOTParser.GRAPH, 0);
};

Attr_stmtContext.prototype.NODE = function() {
    return this.getToken(DOTParser.NODE, 0);
};

Attr_stmtContext.prototype.EDGE = function() {
    return this.getToken(DOTParser.EDGE, 0);
};

Attr_stmtContext.prototype.enterRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.enterAttr_stmt(this);
	}
};

Attr_stmtContext.prototype.exitRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.exitAttr_stmt(this);
	}
};




DOTParser.Attr_stmtContext = Attr_stmtContext;

DOTParser.prototype.attr_stmt = function() {

    var localctx = new Attr_stmtContext(this, this._ctx, this.state);
    this.enterRule(localctx, 6, DOTParser.RULE_attr_stmt);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 58;
        _la = this._input.LA(1);
        if(!((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << DOTParser.GRAPH) | (1 << DOTParser.NODE) | (1 << DOTParser.EDGE))) !== 0))) {
        this._errHandler.recoverInline(this);
        }
        else {
        	this._errHandler.reportMatch(this);
            this.consume();
        }
        this.state = 59;
        this.attr_list();
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

function Attr_listContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DOTParser.RULE_attr_list;
    return this;
}

Attr_listContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
Attr_listContext.prototype.constructor = Attr_listContext;

Attr_listContext.prototype.a_list = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(A_listContext);
    } else {
        return this.getTypedRuleContext(A_listContext,i);
    }
};

Attr_listContext.prototype.enterRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.enterAttr_list(this);
	}
};

Attr_listContext.prototype.exitRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.exitAttr_list(this);
	}
};




DOTParser.Attr_listContext = Attr_listContext;

DOTParser.prototype.attr_list = function() {

    var localctx = new Attr_listContext(this, this._ctx, this.state);
    this.enterRule(localctx, 8, DOTParser.RULE_attr_list);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 66; 
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        do {
            this.state = 61;
            this.match(DOTParser.T__4);
            this.state = 63;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << DOTParser.NUMBER) | (1 << DOTParser.STRING) | (1 << DOTParser.ID) | (1 << DOTParser.HTML_STRING))) !== 0)) {
                this.state = 62;
                this.a_list();
            }

            this.state = 65;
            this.match(DOTParser.T__5);
            this.state = 68; 
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        } while(_la===DOTParser.T__4);
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

function A_listContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DOTParser.RULE_a_list;
    return this;
}

A_listContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
A_listContext.prototype.constructor = A_listContext;

A_listContext.prototype.id = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(IdContext);
    } else {
        return this.getTypedRuleContext(IdContext,i);
    }
};

A_listContext.prototype.enterRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.enterA_list(this);
	}
};

A_listContext.prototype.exitRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.exitA_list(this);
	}
};




DOTParser.A_listContext = A_listContext;

DOTParser.prototype.a_list = function() {

    var localctx = new A_listContext(this, this._ctx, this.state);
    this.enterRule(localctx, 10, DOTParser.RULE_a_list);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 78; 
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        do {
            this.state = 70;
            this.id();
            this.state = 73;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if(_la===DOTParser.T__3) {
                this.state = 71;
                this.match(DOTParser.T__3);
                this.state = 72;
                this.id();
            }

            this.state = 76;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if(_la===DOTParser.T__6) {
                this.state = 75;
                this.match(DOTParser.T__6);
            }

            this.state = 80; 
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        } while((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << DOTParser.NUMBER) | (1 << DOTParser.STRING) | (1 << DOTParser.ID) | (1 << DOTParser.HTML_STRING))) !== 0));
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

function Edge_stmtContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DOTParser.RULE_edge_stmt;
    return this;
}

Edge_stmtContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
Edge_stmtContext.prototype.constructor = Edge_stmtContext;

Edge_stmtContext.prototype.edgeRHS = function() {
    return this.getTypedRuleContext(EdgeRHSContext,0);
};

Edge_stmtContext.prototype.node_id = function() {
    return this.getTypedRuleContext(Node_idContext,0);
};

Edge_stmtContext.prototype.subgraph = function() {
    return this.getTypedRuleContext(SubgraphContext,0);
};

Edge_stmtContext.prototype.attr_list = function() {
    return this.getTypedRuleContext(Attr_listContext,0);
};

Edge_stmtContext.prototype.enterRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.enterEdge_stmt(this);
	}
};

Edge_stmtContext.prototype.exitRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.exitEdge_stmt(this);
	}
};




DOTParser.Edge_stmtContext = Edge_stmtContext;

DOTParser.prototype.edge_stmt = function() {

    var localctx = new Edge_stmtContext(this, this._ctx, this.state);
    this.enterRule(localctx, 12, DOTParser.RULE_edge_stmt);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 84;
        this._errHandler.sync(this);
        switch(this._input.LA(1)) {
        case DOTParser.NUMBER:
        case DOTParser.STRING:
        case DOTParser.ID:
        case DOTParser.HTML_STRING:
            this.state = 82;
            this.node_id();
            break;
        case DOTParser.T__0:
        case DOTParser.SUBGRAPH:
            this.state = 83;
            this.subgraph();
            break;
        default:
            throw new antlr4.error.NoViableAltException(this);
        }
        this.state = 86;
        this.edgeRHS();
        this.state = 88;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===DOTParser.T__4) {
            this.state = 87;
            this.attr_list();
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

function EdgeRHSContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DOTParser.RULE_edgeRHS;
    return this;
}

EdgeRHSContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
EdgeRHSContext.prototype.constructor = EdgeRHSContext;

EdgeRHSContext.prototype.edgeop = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(EdgeopContext);
    } else {
        return this.getTypedRuleContext(EdgeopContext,i);
    }
};

EdgeRHSContext.prototype.node_id = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(Node_idContext);
    } else {
        return this.getTypedRuleContext(Node_idContext,i);
    }
};

EdgeRHSContext.prototype.subgraph = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(SubgraphContext);
    } else {
        return this.getTypedRuleContext(SubgraphContext,i);
    }
};

EdgeRHSContext.prototype.enterRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.enterEdgeRHS(this);
	}
};

EdgeRHSContext.prototype.exitRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.exitEdgeRHS(this);
	}
};




DOTParser.EdgeRHSContext = EdgeRHSContext;

DOTParser.prototype.edgeRHS = function() {

    var localctx = new EdgeRHSContext(this, this._ctx, this.state);
    this.enterRule(localctx, 14, DOTParser.RULE_edgeRHS);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 95; 
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        do {
            this.state = 90;
            this.edgeop();
            this.state = 93;
            this._errHandler.sync(this);
            switch(this._input.LA(1)) {
            case DOTParser.NUMBER:
            case DOTParser.STRING:
            case DOTParser.ID:
            case DOTParser.HTML_STRING:
                this.state = 91;
                this.node_id();
                break;
            case DOTParser.T__0:
            case DOTParser.SUBGRAPH:
                this.state = 92;
                this.subgraph();
                break;
            default:
                throw new antlr4.error.NoViableAltException(this);
            }
            this.state = 97; 
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        } while(_la===DOTParser.T__7 || _la===DOTParser.T__8);
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

function EdgeopContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DOTParser.RULE_edgeop;
    return this;
}

EdgeopContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
EdgeopContext.prototype.constructor = EdgeopContext;


EdgeopContext.prototype.enterRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.enterEdgeop(this);
	}
};

EdgeopContext.prototype.exitRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.exitEdgeop(this);
	}
};




DOTParser.EdgeopContext = EdgeopContext;

DOTParser.prototype.edgeop = function() {

    var localctx = new EdgeopContext(this, this._ctx, this.state);
    this.enterRule(localctx, 16, DOTParser.RULE_edgeop);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 99;
        _la = this._input.LA(1);
        if(!(_la===DOTParser.T__7 || _la===DOTParser.T__8)) {
        this._errHandler.recoverInline(this);
        }
        else {
        	this._errHandler.reportMatch(this);
            this.consume();
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

function Node_stmtContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DOTParser.RULE_node_stmt;
    return this;
}

Node_stmtContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
Node_stmtContext.prototype.constructor = Node_stmtContext;

Node_stmtContext.prototype.node_id = function() {
    return this.getTypedRuleContext(Node_idContext,0);
};

Node_stmtContext.prototype.attr_list = function() {
    return this.getTypedRuleContext(Attr_listContext,0);
};

Node_stmtContext.prototype.enterRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.enterNode_stmt(this);
	}
};

Node_stmtContext.prototype.exitRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.exitNode_stmt(this);
	}
};




DOTParser.Node_stmtContext = Node_stmtContext;

DOTParser.prototype.node_stmt = function() {

    var localctx = new Node_stmtContext(this, this._ctx, this.state);
    this.enterRule(localctx, 18, DOTParser.RULE_node_stmt);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 101;
        this.node_id();
        this.state = 103;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===DOTParser.T__4) {
            this.state = 102;
            this.attr_list();
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

function Node_idContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DOTParser.RULE_node_id;
    return this;
}

Node_idContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
Node_idContext.prototype.constructor = Node_idContext;

Node_idContext.prototype.id = function() {
    return this.getTypedRuleContext(IdContext,0);
};

Node_idContext.prototype.port = function() {
    return this.getTypedRuleContext(PortContext,0);
};

Node_idContext.prototype.enterRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.enterNode_id(this);
	}
};

Node_idContext.prototype.exitRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.exitNode_id(this);
	}
};




DOTParser.Node_idContext = Node_idContext;

DOTParser.prototype.node_id = function() {

    var localctx = new Node_idContext(this, this._ctx, this.state);
    this.enterRule(localctx, 20, DOTParser.RULE_node_id);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 105;
        this.id();
        this.state = 107;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===DOTParser.T__9) {
            this.state = 106;
            this.port();
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

function PortContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DOTParser.RULE_port;
    return this;
}

PortContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
PortContext.prototype.constructor = PortContext;

PortContext.prototype.id = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(IdContext);
    } else {
        return this.getTypedRuleContext(IdContext,i);
    }
};

PortContext.prototype.enterRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.enterPort(this);
	}
};

PortContext.prototype.exitRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.exitPort(this);
	}
};




DOTParser.PortContext = PortContext;

DOTParser.prototype.port = function() {

    var localctx = new PortContext(this, this._ctx, this.state);
    this.enterRule(localctx, 22, DOTParser.RULE_port);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 109;
        this.match(DOTParser.T__9);
        this.state = 110;
        this.id();
        this.state = 113;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===DOTParser.T__9) {
            this.state = 111;
            this.match(DOTParser.T__9);
            this.state = 112;
            this.id();
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

function SubgraphContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DOTParser.RULE_subgraph;
    return this;
}

SubgraphContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
SubgraphContext.prototype.constructor = SubgraphContext;

SubgraphContext.prototype.stmt_list = function() {
    return this.getTypedRuleContext(Stmt_listContext,0);
};

SubgraphContext.prototype.SUBGRAPH = function() {
    return this.getToken(DOTParser.SUBGRAPH, 0);
};

SubgraphContext.prototype.id = function() {
    return this.getTypedRuleContext(IdContext,0);
};

SubgraphContext.prototype.enterRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.enterSubgraph(this);
	}
};

SubgraphContext.prototype.exitRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.exitSubgraph(this);
	}
};




DOTParser.SubgraphContext = SubgraphContext;

DOTParser.prototype.subgraph = function() {

    var localctx = new SubgraphContext(this, this._ctx, this.state);
    this.enterRule(localctx, 24, DOTParser.RULE_subgraph);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 119;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===DOTParser.SUBGRAPH) {
            this.state = 115;
            this.match(DOTParser.SUBGRAPH);
            this.state = 117;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << DOTParser.NUMBER) | (1 << DOTParser.STRING) | (1 << DOTParser.ID) | (1 << DOTParser.HTML_STRING))) !== 0)) {
                this.state = 116;
                this.id();
            }

        }

        this.state = 121;
        this.match(DOTParser.T__0);
        this.state = 122;
        this.stmt_list();
        this.state = 123;
        this.match(DOTParser.T__1);
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

function IdContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DOTParser.RULE_id;
    return this;
}

IdContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
IdContext.prototype.constructor = IdContext;

IdContext.prototype.ID = function() {
    return this.getToken(DOTParser.ID, 0);
};

IdContext.prototype.STRING = function() {
    return this.getToken(DOTParser.STRING, 0);
};

IdContext.prototype.HTML_STRING = function() {
    return this.getToken(DOTParser.HTML_STRING, 0);
};

IdContext.prototype.NUMBER = function() {
    return this.getToken(DOTParser.NUMBER, 0);
};

IdContext.prototype.enterRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.enterId(this);
	}
};

IdContext.prototype.exitRule = function(listener) {
    if(listener instanceof DOTListener ) {
        listener.exitId(this);
	}
};




DOTParser.IdContext = IdContext;

DOTParser.prototype.id = function() {

    var localctx = new IdContext(this, this._ctx, this.state);
    this.enterRule(localctx, 26, DOTParser.RULE_id);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 125;
        _la = this._input.LA(1);
        if(!((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << DOTParser.NUMBER) | (1 << DOTParser.STRING) | (1 << DOTParser.ID) | (1 << DOTParser.HTML_STRING))) !== 0))) {
        this._errHandler.recoverInline(this);
        }
        else {
        	this._errHandler.reportMatch(this);
            this.consume();
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


exports.DOTParser = DOTParser;
