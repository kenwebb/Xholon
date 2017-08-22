// Generated from Room.g4 by ANTLR 4.7
// jshint ignore: start
var antlr4 = require('xholon/lib/antlr4/index');

// This class defines a complete listener for a parse tree produced by RoomParser.
function RoomListener() {
	antlr4.tree.ParseTreeListener.call(this);
	return this;
}

RoomListener.prototype = Object.create(antlr4.tree.ParseTreeListener.prototype);
RoomListener.prototype.constructor = RoomListener;

// Enter a parse tree produced by RoomParser#roomModel.
RoomListener.prototype.enterRoomModel = function(ctx) {
};

// Exit a parse tree produced by RoomParser#roomModel.
RoomListener.prototype.exitRoomModel = function(ctx) {
};


// Enter a parse tree produced by RoomParser#logicalSystem.
RoomListener.prototype.enterLogicalSystem = function(ctx) {
};

// Exit a parse tree produced by RoomParser#logicalSystem.
RoomListener.prototype.exitLogicalSystem = function(ctx) {
};


// Enter a parse tree produced by RoomParser#subSystemRef.
RoomListener.prototype.enterSubSystemRef = function(ctx) {
};

// Exit a parse tree produced by RoomParser#subSystemRef.
RoomListener.prototype.exitSubSystemRef = function(ctx) {
};


// Enter a parse tree produced by RoomParser#subSystemClass.
RoomListener.prototype.enterSubSystemClass = function(ctx) {
};

// Exit a parse tree produced by RoomParser#subSystemClass.
RoomListener.prototype.exitSubSystemClass = function(ctx) {
};


// Enter a parse tree produced by RoomParser#logicalThread.
RoomListener.prototype.enterLogicalThread = function(ctx) {
};

// Exit a parse tree produced by RoomParser#logicalThread.
RoomListener.prototype.exitLogicalThread = function(ctx) {
};


// Enter a parse tree produced by RoomParser#layerConnection.
RoomListener.prototype.enterLayerConnection = function(ctx) {
};

// Exit a parse tree produced by RoomParser#layerConnection.
RoomListener.prototype.exitLayerConnection = function(ctx) {
};


// Enter a parse tree produced by RoomParser#generalProtocolClass.
RoomListener.prototype.enterGeneralProtocolClass = function(ctx) {
};

// Exit a parse tree produced by RoomParser#generalProtocolClass.
RoomListener.prototype.exitGeneralProtocolClass = function(ctx) {
};


// Enter a parse tree produced by RoomParser#protocolClass.
RoomListener.prototype.enterProtocolClass = function(ctx) {
};

// Exit a parse tree produced by RoomParser#protocolClass.
RoomListener.prototype.exitProtocolClass = function(ctx) {
};


// Enter a parse tree produced by RoomParser#compoundProtocolClass.
RoomListener.prototype.enterCompoundProtocolClass = function(ctx) {
};

// Exit a parse tree produced by RoomParser#compoundProtocolClass.
RoomListener.prototype.exitCompoundProtocolClass = function(ctx) {
};


// Enter a parse tree produced by RoomParser#subProtocol.
RoomListener.prototype.enterSubProtocol = function(ctx) {
};

// Exit a parse tree produced by RoomParser#subProtocol.
RoomListener.prototype.exitSubProtocol = function(ctx) {
};


// Enter a parse tree produced by RoomParser#message.
RoomListener.prototype.enterMessage = function(ctx) {
};

// Exit a parse tree produced by RoomParser#message.
RoomListener.prototype.exitMessage = function(ctx) {
};


// Enter a parse tree produced by RoomParser#varDecl.
RoomListener.prototype.enterVarDecl = function(ctx) {
};

// Exit a parse tree produced by RoomParser#varDecl.
RoomListener.prototype.exitVarDecl = function(ctx) {
};


// Enter a parse tree produced by RoomParser#portClass.
RoomListener.prototype.enterPortClass = function(ctx) {
};

// Exit a parse tree produced by RoomParser#portClass.
RoomListener.prototype.exitPortClass = function(ctx) {
};


// Enter a parse tree produced by RoomParser#actorRef.
RoomListener.prototype.enterActorRef = function(ctx) {
};

// Exit a parse tree produced by RoomParser#actorRef.
RoomListener.prototype.exitActorRef = function(ctx) {
};


// Enter a parse tree produced by RoomParser#actorClass.
RoomListener.prototype.enterActorClass = function(ctx) {
};

// Exit a parse tree produced by RoomParser#actorClass.
RoomListener.prototype.exitActorClass = function(ctx) {
};


// Enter a parse tree produced by RoomParser#iinterface.
RoomListener.prototype.enterIinterface = function(ctx) {
};

// Exit a parse tree produced by RoomParser#iinterface.
RoomListener.prototype.exitIinterface = function(ctx) {
};


// Enter a parse tree produced by RoomParser#structure.
RoomListener.prototype.enterStructure = function(ctx) {
};

// Exit a parse tree produced by RoomParser#structure.
RoomListener.prototype.exitStructure = function(ctx) {
};


// Enter a parse tree produced by RoomParser#port.
RoomListener.prototype.enterPort = function(ctx) {
};

// Exit a parse tree produced by RoomParser#port.
RoomListener.prototype.exitPort = function(ctx) {
};


// Enter a parse tree produced by RoomParser#binding.
RoomListener.prototype.enterBinding = function(ctx) {
};

// Exit a parse tree produced by RoomParser#binding.
RoomListener.prototype.exitBinding = function(ctx) {
};


// Enter a parse tree produced by RoomParser#behavior.
RoomListener.prototype.enterBehavior = function(ctx) {
};

// Exit a parse tree produced by RoomParser#behavior.
RoomListener.prototype.exitBehavior = function(ctx) {
};


// Enter a parse tree produced by RoomParser#stateMachine.
RoomListener.prototype.enterStateMachine = function(ctx) {
};

// Exit a parse tree produced by RoomParser#stateMachine.
RoomListener.prototype.exitStateMachine = function(ctx) {
};


// Enter a parse tree produced by RoomParser#transition.
RoomListener.prototype.enterTransition = function(ctx) {
};

// Exit a parse tree produced by RoomParser#transition.
RoomListener.prototype.exitTransition = function(ctx) {
};


// Enter a parse tree produced by RoomParser#sstate.
RoomListener.prototype.enterSstate = function(ctx) {
};

// Exit a parse tree produced by RoomParser#sstate.
RoomListener.prototype.exitSstate = function(ctx) {
};


// Enter a parse tree produced by RoomParser#transitionPoint.
RoomListener.prototype.enterTransitionPoint = function(ctx) {
};

// Exit a parse tree produced by RoomParser#transitionPoint.
RoomListener.prototype.exitTransitionPoint = function(ctx) {
};


// Enter a parse tree produced by RoomParser#choicePoint.
RoomListener.prototype.enterChoicePoint = function(ctx) {
};

// Exit a parse tree produced by RoomParser#choicePoint.
RoomListener.prototype.exitChoicePoint = function(ctx) {
};


// Enter a parse tree produced by RoomParser#subgraph.
RoomListener.prototype.enterSubgraph = function(ctx) {
};

// Exit a parse tree produced by RoomParser#subgraph.
RoomListener.prototype.exitSubgraph = function(ctx) {
};


// Enter a parse tree produced by RoomParser#entry.
RoomListener.prototype.enterEntry = function(ctx) {
};

// Exit a parse tree produced by RoomParser#entry.
RoomListener.prototype.exitEntry = function(ctx) {
};


// Enter a parse tree produced by RoomParser#exit.
RoomListener.prototype.enterExit = function(ctx) {
};

// Exit a parse tree produced by RoomParser#exit.
RoomListener.prototype.exitExit = function(ctx) {
};


// Enter a parse tree produced by RoomParser#action.
RoomListener.prototype.enterAction = function(ctx) {
};

// Exit a parse tree produced by RoomParser#action.
RoomListener.prototype.exitAction = function(ctx) {
};


// Enter a parse tree produced by RoomParser#triggers.
RoomListener.prototype.enterTriggers = function(ctx) {
};

// Exit a parse tree produced by RoomParser#triggers.
RoomListener.prototype.exitTriggers = function(ctx) {
};


// Enter a parse tree produced by RoomParser#entryPoint.
RoomListener.prototype.enterEntryPoint = function(ctx) {
};

// Exit a parse tree produced by RoomParser#entryPoint.
RoomListener.prototype.exitEntryPoint = function(ctx) {
};


// Enter a parse tree produced by RoomParser#exitPoint.
RoomListener.prototype.enterExitPoint = function(ctx) {
};

// Exit a parse tree produced by RoomParser#exitPoint.
RoomListener.prototype.exitExitPoint = function(ctx) {
};


// Enter a parse tree produced by RoomParser#code.
RoomListener.prototype.enterCode = function(ctx) {
};

// Exit a parse tree produced by RoomParser#code.
RoomListener.prototype.exitCode = function(ctx) {
};


// Enter a parse tree produced by RoomParser#dataClass.
RoomListener.prototype.enterDataClass = function(ctx) {
};

// Exit a parse tree produced by RoomParser#dataClass.
RoomListener.prototype.exitDataClass = function(ctx) {
};


// Enter a parse tree produced by RoomParser#importedFQN.
RoomListener.prototype.enterImportedFQN = function(ctx) {
};

// Exit a parse tree produced by RoomParser#importedFQN.
RoomListener.prototype.exitImportedFQN = function(ctx) {
};



exports.RoomListener = RoomListener;
