// original
<DeckSelectorbehavior implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[
  var deckSelector;
  var beh = {
    postConfigure: function() {
      deckSelector = this.cnode.parent();
      var selected = deckSelector.text();
      var node = deckSelector.parent().first();
      while (node != null) {
        var nextNode = node.next();
        if (node.xhc().name() == "Deck") {
          if (node.role() != selected) {
            node.remove();
          }
        }
        node = nextNode;
      }
    }
  }
]]></DeckSelectorbehavior>

//========================================================
// I need to include both the prototype code, and the instance code, in the same XML node
//   or in two separate subnodes <prototype>  and  <instance>
// perhaps deckSelector should be an instance variable accessible using "this"

// prototype
// constructor; it initializes "this"
<DeckSelectorbehavior implName="org.primordion.xholon.base.Behavior_gwtjsproto"><![CDATA[
function DeckSelectorbehavior() {
  //this.deckSelector = null;
}
// can I write "postConfigure: function()" NO?
DeckSelectorbehavior.prototype.postConfigure = function() {
  /*this.*/deckSelector = this.cnode.parent();
  var selected = deckSelector.text();
  var node = deckSelector.parent().first();
  while (node != null) {
    var nextNode = node.next();
    if (node.xhc().name() == "Deck") {
      if (node.role() != selected) {
        node.remove();
      }
    }
    node = nextNode;
  }
};
]]></DeckSelectorbehavior>

// instance
// xhc already knows the implName, so I don't need to include it here
// can I do: var beh = new xhc.DeckSelectorbehavior();
// if variables such as deckSelector are all instance variables, then "var deckSelector;" is unnecessary here
// maybe a Behavior_gwtjs JSNI function can call the single line "var beh = new this.xhc().DeckSelectorbehavior();"
//   it could construct this line automatically, if all constructors have no arguments
<DeckSelectorbehavior implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[
  var deckSelector;
  var beh = new DeckSelectorbehavior();
  // later
  beh.postConfigure();
]]></DeckSelectorbehavior>


// not yet correct
<DeckSelectorbehavior implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[
function DeckSelectorbehavior() {}
DeckSelectorbehavior.prototype.postConfigure = function() {
  this.deckSelector = this.cnode.parent();
  var selected = this.deckSelector.text();
  var node = this.deckSelector.parent().first();
  while (node != null) {
    var nextNode = node.next();
    if (node.xhc().name() == "Deck") {
      if (node.role() != selected) {
        node.remove();
      }
    }
    node = nextNode;
  }
};
]]></DeckSelectorbehavior>

