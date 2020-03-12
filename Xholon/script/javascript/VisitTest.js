/**
  VisitTest.js
  Ken Webb
  March 12, 2020
  
  I have added api.visit to XholonJsApi.java.
  I have added public boolean visit(IXholon visitor) { to Behavior_gwtjs.java.
  
  This is a test that I've done with the HelloWorld app.
  
  The Visitor pattern can operate as a form of map-reduce.
  In this example, "count" is a reduce result.
  I could also map to an array of node names, or IDs, or (in this example) states.
*/

var vbeh = `<XingHelloWorldSystembehavior implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[
var me, count, arr, testing;
var beh = {
  postConfigure: function() {
    me = this.cnode.parent();
    testing = Math.floor(Math.random() * 10);
    count = 0;
    arr = [];
  },
  act: function() {
    me.println(this.toString());
  },
  visit: function(visitor) {
    me.println("visited by " + visitor);
    this.doReduceCount();
    this.doMapArrIXholon(visitor);
    return true;
  },
  doReduceCount: function() {
    count++;
  },
  doMapArrIXholon: function(visitor) {
    arr.push(visitor.name());
  },
  // testing:6 count:3 arr:helloWorldSystem_0,hello_2,world_3
  toString: function() {
    return "testing:" + testing + " count:" + count + " arr:" + arr;
  }
}
//# sourceURL=behaviorScript.js
]]></XingHelloWorldSystembehavior>`;

var node = xh.root();
var visitee = node.append(vbeh).last();
console.log(visitee.name());
node.visit(visitee); // true

/*
result in out tab:
visited by helloWorldSystem_0 state=0
visited by hello_2 [ port:world_3] state=0
visited by world_3 [ port:hello_2] state=0
*/
