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
 * see Xholon nearley parser library
 * 
 * @example
 * Usage:
 * xh.fipa.TODO
 */

if (typeof xh == "undefined") {
  xh = {};
}

xh.fipa = {}

/**
 * Construct a FIPA Accept Proposal CA message.
 */
/*window.xh.fipa.AcceptProposal = (sendername, receivername, inreplyto, content, language) => {
  const ca = {}
  ca["accept-proposal"] = {};
  ca["accept-proposal"].sender = {};
  return ca;
}
*/

/*
var acceptproposal = (sendername, receivername, inreplyto, content, language) => {
  return {
    acceptproposal: {
      sender: {agentidentifier: {name: sendername}}
    }
  };
  
}

console.log(acceptproposal("sndr", "rcvr", "irt", "this is some content.", "fipa-sl"));
*/

/*
function Book(name, year) { 
  if (!(this instanceof Book)) { 
    return new Book(name, year);
  }
  this.name = name;
  this.year = year;
}

var person1 = new Book("js book", 2014);
var person2 = Book("js book", 2014);

console.log(person1 instanceof Book);    // true
console.log(person2 instanceof Book);    // true
*/

// OLOO Kyle Simpson "Objects Linked to Other Objects"
// https://github.com/getify/You-Dont-Know-JS

// FIPA Communicative Acts
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
};

// other constants
xh.fipa.CONST = {
  AGENT_IDENTIFIER: "agent-identifier",
  SENDER: ":sender",
  RECEIVER: ":receiver",
  CONTENT: ":content",
  LANGUAGE: ":language",
  NAME: ":name",
  IN_REPLY_TO: ":in-reply-to",
  PROTOCOL: ":protocol"
};

xh.fipa.AgentIdentifier = {
  init: function(name) {
    this.name = name;
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CONST.AGENT_IDENTIFIER + " " + xh.fipa.CONST.NAME + " " + this.name + ")";
  }
};

// examples
//var aiI = Object.create(AgentIdentifier);
//aiI.init("i");
//console.log(aiI.asFipaSL());
//var aiJ = Object.create(AgentIdentifier);
//aiJ.init("j");
//console.log(aiJ.asFipaSL());

xh.fipa.Sender = {
  init: function(name) {
    this[xh.fipa.CONST.AGENT_IDENTIFIER] = Object.create(xh.fipa.AgentIdentifier);
    this[xh.fipa.CONST.AGENT_IDENTIFIER].init(name);
  },
  asFipaSL: function() {
    return xh.fipa.CONST.SENDER + " " + this[xh.fipa.CONST.AGENT_IDENTIFIER].asFipaSL();
  }
};

//var sender = Object.create(Sender);
//sender.init("k");
//console.log(sender.asFipaSL());

xh.fipa.Receiver = {
  init: function(name) {
    // TODO Receiver contains a set of "agent-identifier"
    this[xh.fipa.CONST.AGENT_IDENTIFIER] = Object.create(xh.fipa.AgentIdentifier);
    this[xh.fipa.CONST.AGENT_IDENTIFIER].init(name);
  },
  asFipaSL: function() {
    return xh.fipa.CONST.RECEIVER + " " + this[xh.fipa.CONST.AGENT_IDENTIFIER].asFipaSL();
  }
};

xh.fipa.Content = {
  init: function(str) {
    this.str = str;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.CONTENT + " " + "\n" + '"' + this.str + '"';
  }
};

xh.fipa.Language = {
  init: function(str) {
    this.str = str;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.LANGUAGE + " " + this.str;
  }
};

xh.fipa.InReplyTo = {
  init: function(str) {
    this.str = str;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.IN_REPLY_TO + " " + this.str;
  }
};

xh.fipa.Protocol = {
  init: function(str) {
    this.str = str;
  },
  asFipaSL: function() {
    return xh.fipa.CONST.PROTOCOL + " " + this.str;
  }
};

// an abstract super-type that AcceptProposal etc. can link from; it has common: sender receiver content language
xh.fipa.AbstractCommunicativeAct = {
  initbase: function(concreteCA, sendername, receivername, content, language) {
    // :sender
    concreteCA.sender = Object.create(xh.fipa.Sender);
    concreteCA.sender.init(sendername);
    // :receiver
    concreteCA.receiver = Object.create(xh.fipa.Receiver);
    concreteCA.receiver.init(receivername);
    // :content
    concreteCA.content = Object.create(xh.fipa.Content);
    concreteCA.content.init(content);
    // :language
    concreteCA.language = Object.create(xh.fipa.Language);
    concreteCA.language.init(language);
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

xh.fipa.AcceptProposal = {
  init: function(sendername, receivername, content, language, inreplyto) {
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct);
    this.aca.initbase(this, sendername, receivername, content, language);
    // :in-reply-to
    this[xh.fipa.CONST.IN_REPLY_TO] = Object.create(xh.fipa.InReplyTo);
    this[xh.fipa.CONST.IN_REPLY_TO].init(inreplyto);
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.ACCEPT_PROPOSAL + "\n" + this.aca.asFipaSL(this) + "\n" + this[xh.fipa.CONST.IN_REPLY_TO].asFipaSL() + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
};

xh.fipa.Agree = {
  init: function(sendername, receivername, content, language, inreplyto, protocol) {
    this.aca = Object.create(xh.fipa.AbstractCommunicativeAct);
    this.aca.initbase(this, sendername, receivername, content, language);
    // :in-reply-to
    this[xh.fipa.CONST.IN_REPLY_TO] = Object.create(xh.fipa.InReplyTo);
    this[xh.fipa.CONST.IN_REPLY_TO].init(inreplyto);
    // :protocol
    this[xh.fipa.CONST.PROTOCOL] = Object.create(xh.fipa.Protocol);
    this[xh.fipa.CONST.PROTOCOL].init(protocol);
  },
  asFipaSL: function() {
    return "(" + xh.fipa.CA.AGREE + "\n" + this.aca.asFipaSL(this) + "\n" + this[xh.fipa.CONST.IN_REPLY_TO].asFipaSL() + "\n" + this[xh.fipa.CONST.PROTOCOL].asFipaSL() + ")" + "\n";
  },
  asJso: function() {
    return this.aca.asJso(this);
  },
  asJson: function() {
    return this.aca.asJson(this);
  }
};

// create and test an instance
var accpro = Object.create(xh.fipa.AcceptProposal);
accpro.init("sndr", "rcvr", "this is some content.", "fipa-sl", "irt");
console.log(accpro.asJso());
console.log(accpro.asJson());
console.log(accpro.asFipaSL());

// create and test an instance
var agree = Object.create(xh.fipa.Agree);
agree.init("sndr", "rcvr", "this is some content.", "fipa-sl", "irt", "some-fipa-protocol");
console.log(agree.asJso());
console.log(agree.asJson());
console.log(agree.asFipaSL());

