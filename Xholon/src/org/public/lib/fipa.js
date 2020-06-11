/**
 * fipa.js
 * (C) Ken Webb, MIT license
 * June 8, 2020
 * 
 * Support for FIPA ACL.
 * Foundation for Intelligent Physical Agents (FIPA)
 * Agent Communication Language (ACL)
 * Communicative Act (CA)
 * 
 * @see <a href="http://www.fipa.org/specs/">FIPA website</a>
 * http://www.fipa.org/specs/fipa00037/SC00037J.html FIPA Communicative Act Library Specification
 * http://www.fipa.org/specs/fipa00070/SC00070I.html FIPA ACL Message Representation in String Specification
 * https://gist.github.com/kenwebb/6ee032b8913680e72191ffea48dca46f Agent Technologies workbook
 * see Xholon nearley parser library  ~/gwtspace/Xholon/Xholon/nearley/fipaAcl.ne
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/create
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/defineProperties
 * 
 * @example
 * Usage:
 * http://127.0.0.1:8080/war/Xholon.html?app=Agent+Technologies&src=lstr&gui=clsc&jslib=nearley/nearley,nearley/fipaAcl,fipa
 * xh.fipa.TODO
 */

if (typeof xh == "undefined") {
  xh = {};
}

xh.fipa = {}

xh.fipa.SHOULD_TEST = true;

// In this library, I am roughly trying the OLOO pattern/approach.
// OLOO Kyle Simpson "Objects Linked to Other Objects"
// https://github.com/getify/You-Dont-Know-JS

// FIPA Communicative Acts, Message Types
xh.fipa.CA = {
  ACCEPT_PROPOSAL: "accept-proposal",
  AGREE: "agree",
  CANCEL: "cancel",
  CFP: "cfp", // Call for Proposal
  CONFIRM: "confirm",
  DISCONFIRM: "disconfirm",
  FAILURE: "failure",
  INFORM: "inform",
  NOT_UNDERSTOOD: "not-understood",
  PROPOGATE: "propogate",
  PROPOSE: "propose",
  PROXY: "proxy",
  QUERY_IF: "query-if",
  QUERY_REF: "query-ref",
  REFUSE: "refuse",
  REJECT_PROPOSAL: "reject-proposal",
  REQUEST: "request",
  REQUEST_WHEN: "request-when",
  REQUEST_WHENEVER: "request-whenever",
  SUBSCRIBE: "subscribe"
}

// other constants
xh.fipa.CONST = {
  // misc
  AGENT_IDENTIFIER: "agent-identifier",
  // Message Parameters, with the parameter operand as specified in the grammar (fipaAcl.ne)
  CONTENT: ":content", // String
  CONVERSATION_ID: ":conversation-id", // Expression
  ENCODING: ":encoding", // Expression
  IN_REPLY_TO: ":in-reply-to", // Expression
  LANGUAGE: ":language", // Expression
  NAME: ":name", // Word
  ONTOLOGY: ":ontology", // Expression
  PROTOCOL: ":protocol", // Word
  RECEIVER: ":receiver", // AgentIdentifierSet
  REPLY_BY: "reply-by", // DateTime
  REPLY_TO: "reply-to", // AgentIdentifierSet
  REPLY_WITH: "reply-with", // Expression
  SENDER: ":sender" // AgentIdentifier
}

xh.fipa.AgentIdentifier = {
  init: function(name) {
    this.name = name;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CONST.AGENT_IDENTIFIER + " " + xh.fipa.CONST.NAME + " " + this.name + ")";
  }
}

// TESTING (optional)
if (xh.fipa.SHOULD_TEST) {
  var aiI = Object.create(xh.fipa.AgentIdentifier).init("i");
  console.log(aiI.asFipaSL());
  
  var aiJ = Object.create(xh.fipa.AgentIdentifier).init("j");
  console.log(aiJ.asFipaSL());
}

// CONTENT String
xh.fipa.Content = {
  init: function(str) {
    this.str = str;
    return this;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.CONTENT + " " + "\n" + '"' + this.str + '"';
  }
}

// CONVERSATION_ID Expression
xh.fipa.ConversationId = {
  init: function(str) {
    this.str = str;
    return this;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.CONVERSATION_ID + " " + this.str;
  }
}

// ENCODING Expression
xh.fipa.Encoding = {
  init: function(str) {
    this.str = str;
    return this;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.ENCODING + " " + this.str;
  }
}

// IN_REPLY_TO Expression
xh.fipa.InReplyTo = {
  init: function(str) {
    this.str = str;
    return this;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.IN_REPLY_TO + " " + this.str;
  }
}

// LANGUAGE Expression
xh.fipa.Language = {
  init: function(str) {
    this.str = str;
    return this;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.LANGUAGE + " " + this.str;
  }
}

// ONTOLOGY Expression
xh.fipa.Ontology = {
  init: function(str) {
    this.str = str;
    return this;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.ONTOLOGY + " " + this.str;
  }
}

// PROTOCOL Word
xh.fipa.Protocol = {
  init: function(str) {
    this.str = str;
    return this;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.PROTOCOL + " " + this.str;
  }
}

// RECEIVER AgentIdentifierSet
xh.fipa.Receiver = {
  init: function(name) {
    // TODO Receiver contains a set of "agent-identifier"
    this[xh.fipa.CONST.AGENT_IDENTIFIER] = Object.create(xh.fipa.AgentIdentifier).init(name);
    return this;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.RECEIVER + " " + this[xh.fipa.CONST.AGENT_IDENTIFIER].asFipaSL();
  }
}

// REPLY_BY DateTime
xh.fipa.ReplyBy = {
  init: function(str) {
    this.str = str;
    return this;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.REPLY_BY + " " + this.str;
  }
}

// REPLY_TO AgentIdentifierSet
xh.fipa.ReplyTo = {
  init: function(str) {
    this.str = str;
    return this;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.REPLY_TO + " " + this.str;
  }
}

// REPLY_WITH Expression
xh.fipa.ReplyWith = {
  init: function(str) {
    this.str = str;
    return this;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.REPLY_WITH + " " + this.str;
  }
}

// SENDER AgentIdentifier
xh.fipa.Sender = {
  init: function(name) {
    this[xh.fipa.CONST.AGENT_IDENTIFIER] = Object.create(xh.fipa.AgentIdentifier).init(name);
    return this;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.SENDER + " " + this[xh.fipa.CONST.AGENT_IDENTIFIER].asFipaSL();
  }
}

// TESTING (optional)
if (xh.fipa.SHOULD_TEST) {
  var sender = Object.create(xh.fipa.Sender);
  sender.init("k");
  console.log(sender.asFipaSL());
}

// an abstract super-type that AcceptProposal etc. can link from; it has common: sender receiver content language
xh.fipa.AbstractCommunicativeAct = {
  initbase: function(concreteCA, sendername, receivername, content, language) {
    concreteCA.sender = Object.create(xh.fipa.Sender).init(sendername);
    concreteCA.receiver = Object.create(xh.fipa.Receiver).init(receivername);
    concreteCA.content = Object.create(xh.fipa.Content).init(content);
    concreteCA.language = Object.create(xh.fipa.Language).init(language);
    return this;
  },
  asFipaSL: function(concreteCA) {
    return ""
    + concreteCA.sender.asFipaSL() + "\n"
    + concreteCA.receiver.asFipaSL() + "\n"
    + concreteCA.content.asFipaSL() + "\n"
    + concreteCA.language.asFipaSL();
  },
  asJso: function(concreteCA) {
    var aca = concreteCA.aca;
    delete concreteCA.aca;
    var jso = JSON.parse(JSON.stringify(concreteCA));
    concreteCA.aca = aca;
    return jso;
  },
  asJson: function(concreteCA) {
    var aca = concreteCA.aca;
    delete concreteCA.aca;
    var json = JSON.stringify(concreteCA);
    concreteCA.aca = aca;
    return json;
  }
}

// Dummy for testing new structure
xh.fipa.Dummy = {
  init: function(sendername, receivername, content, language) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    this["dummy"] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + "dummy" + "\n" + this.aca.asFipaSL(this["dummy"]) + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.AcceptProposal = {
  init: function(sendername, receivername, content, language, inreplyto) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    params[xh.fipa.CONST.IN_REPLY_TO] = Object.create(xh.fipa.InReplyTo).init(inreplyto);
    this[xh.fipa.CA.ACCEPT_PROPOSAL] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.ACCEPT_PROPOSAL + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.ACCEPT_PROPOSAL]) + "\n"
    + this[xh.fipa.CA.ACCEPT_PROPOSAL][xh.fipa.CONST.IN_REPLY_TO].asFipaSL() + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.Agree = {
  init: function(sendername, receivername, content, language, inreplyto, protocol) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    params[xh.fipa.CONST.IN_REPLY_TO] = Object.create(xh.fipa.InReplyTo).init(inreplyto);
    params[xh.fipa.CONST.PROTOCOL] = Object.create(xh.fipa.Protocol).init(protocol);
    this[xh.fipa.CA.AGREE] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.AGREE + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.AGREE]) + "\n"
    + this[xh.fipa.CA.AGREE][xh.fipa.CONST.IN_REPLY_TO].asFipaSL() + "\n"
    + this[xh.fipa.CA.AGREE][xh.fipa.CONST.PROTOCOL].asFipaSL() + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.Cancel = {
  init: function(sendername, receivername, content, language) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    this[xh.fipa.CA.CANCEL] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.CANCEL + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.CANCEL]) + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.Cfp = { // Call for Proposal
  init: function(sendername, receivername, content, language, ontology) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    params[xh.fipa.CONST.ONTOLOGY] = Object.create(xh.fipa.Ontology).init(ontology);
    this[xh.fipa.CA.CFP] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.CFP + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.CFP]) + "\n"
    + this[xh.fipa.CONST.ONTOLOGY].asFipaSL() + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.Confirm = {
  init: function(sendername, receivername, content, language) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    this[xh.fipa.CA.CONFIRM] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.CONFIRM + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.CONFIRM]) + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.Disconfirm = {
  init: function(sendername, receivername, content, language) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    this[xh.fipa.CA.DISCONFIRM] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.DISCONFIRM + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.DISCONFIRM]) + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.Failure = {
  init: function(sendername, receivername, content, language) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    this[xh.fipa.CA.FAILURE] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.FAILURE + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.FAILURE]) + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.Inform = {
  init: function(sendername, receivername, content, language) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    this[xh.fipa.CA.INFORM] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.INFORM + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.INFORM]) + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.NotUnderstood = {
  init: function(sendername, receivername, content, language) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    this[xh.fipa.CA.NOT_UNDERSTOOD] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.NOT_UNDERSTOOD + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.NOT_UNDERSTOOD]) + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.Propagate = {
  init: function(sendername, receivername, content, language, ontology) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    params[xh.fipa.CONST.ONTOLOGY] = Object.create(xh.fipa.Ontology).init(ontology);
    this[xh.fipa.CA.PROPAGATE] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.PROPAGATE + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.PROPAGATE]) + "\n"
    + this[xh.fipa.CA.PROPAGATE][xh.fipa.CONST.ONTOLOGY].asFipaSL() + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.Propose = {
  init: function(sendername, receivername, content, language, ontology, inreplyto) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    params[xh.fipa.CONST.ONTOLOGY] = Object.create(xh.fipa.Ontology).init(ontology);
    params[xh.fipa.CONST.IN_REPLY_TO] = Object.create(xh.fipa.InReplyTo).init(inreplyto);
    this[xh.fipa.CA.PROPOSE] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.PROPOSE + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.PROPOSE]) + "\n"
    + this[xh.fipa.CA.PROPOSE][xh.fipa.CONST.ONTOLOGY].asFipaSL() + "\n"
    + this[xh.fipa.CA.PROPOSE][xh.fipa.CONST.IN_REPLY_TO].asFipaSL() + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.Proxy = {
  init: function(sendername, receivername, content, language, ontology, protocol, conversationid) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    params[xh.fipa.CONST.ONTOLOGY] = Object.create(xh.fipa.Ontology).init(ontology);
    params[xh.fipa.CONST.PROTOCOL] = Object.create(xh.fipa.Protocol).init(protocol);
    params[xh.fipa.CONST.CONVERSATION_ID] = Object.create(xh.fipa.ConversationId).init(conversationid);
    this[xh.fipa.CA.PROXY] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.PROXY + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.PROXY]) + "\n"
    + this[xh.fipa.CA.PROXY][xh.fipa.CONST.ONTOLOGY].asFipaSL() + "\n"
    + this[xh.fipa.CA.PROXY][xh.fipa.CONST.PROTOCOL].asFipaSL() + "\n"
    + this[xh.fipa.CA.PROXY][xh.fipa.CONST.CONVERSATION_ID].asFipaSL() + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.QueryIf = {
  init: function(sendername, receivername, content, language, inreplyto) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    params[xh.fipa.CONST.IN_REPLY_TO] = Object.create(xh.fipa.InReplyTo).init(inreplyto);
    this[xh.fipa.CA.QUERY_IF] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.QUERY_IF + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.QUERY_IF]) + "\n"
    + this[xh.fipa.CA.QUERY_IF][xh.fipa.CONST.IN_REPLY_TO].asFipaSL() + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.QueryRef = {
  init: function(sendername, receivername, content, language) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    this[xh.fipa.CA.QUERY_REF] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.QUERY_REF + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.QUERY_REF]) + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.Refuse = {
  init: function(sendername, receivername, content, language) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    this[xh.fipa.CA.REFUSE] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.REFUSE + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.REFUSE]) + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.RejectProposal = {
  init: function(sendername, receivername, content, language, inreplyto) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    params[xh.fipa.CONST.IN_REPLY_TO] = Object.create(xh.fipa.InReplyTo).init(inreplyto);
    this[xh.fipa.CA.REJECT_PROPOSAL] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.REJECT_PROPOSAL + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.REJECT_PROPOSAL]) + "\n"
    + this[xh.fipa.CA.REJECT_PROPOSAL][xh.fipa.CONST.IN_REPLY_TO].asFipaSL() + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.Request = {
  init: function(sendername, receivername, content, language) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    this[xh.fipa.CA.REQUEST] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.REQUEST + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.REQUEST]) + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.RequestWhen = {
  init: function(sendername, receivername, content, language) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    this[xh.fipa.CA.REQUEST_WHEN] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.REQUEST_WHEN + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.REQUEST_WHEN]) + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.RequestWhenever = {
  init: function(sendername, receivername, content, language) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    this[xh.fipa.CA.REQUEST_WHENEVER] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.REQUEST_WHENEVER + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.REQUEST_WHENEVER]) + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

xh.fipa.Subscribe = {
  init: function(sendername, receivername, content, language) {
    var params = {};
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct).initbase(params, sendername, receivername, content, language);
    this[xh.fipa.CA.SUBSCRIBE] = params;
    return this;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.SUBSCRIBE + "\n" + this.aca.asFipaSL(this[xh.fipa.CA.SUBSCRIBE]) + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
}

// TESTING (optional)
if (xh.fipa.SHOULD_TEST) {
  
  // test AcceptProposal
  var accpro = Object.create(xh.fipa.AcceptProposal).init("sndr", "rcvr", "this is some content.", "fipa-sl", "irt");
  console.log(accpro.asJso());
  console.log(accpro.asJson());
  console.log(accpro.asFipaSL());

  // test Agree
  var agree = Object.create(xh.fipa.Agree).init("sndr", "rcvr", "this is some content.", "fipa-sl", "irt", "some-fipa-protocol");
  console.log(agree.asJso());
  console.log(agree.asJson());
  console.log(agree.asFipaSL());
}

