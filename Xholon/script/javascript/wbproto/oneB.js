// Flashcard app (with SVG)
// this works

// (0) paste temp03.xml as last child of Chameleon (it lacks DeckSelectorbehavior)

// (1) paste the following using Firebug
xh.DeckSelectorbehavior = function DeckSelectorbehavior() {}
xh.DeckSelectorbehavior.prototype.postConfigure = function() {
  console.log("DeckSelectorbehavior.prototype.postConfigure");
  console.log(this);
  console.log(this.cnode);
  console.log(this.cnode.parent());
  this.deckSelector = this.cnode.parent();
  console.log(this.deckSelector);
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

// (2) paste the following as last child of DeckSelector in CSH
<DeckSelectorbehavior implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[
  var beh = new $wnd.xh.DeckSelectorbehavior();
]]></DeckSelectorbehavior>
