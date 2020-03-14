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
  Haskell has fold functions; Ramda has reduce functions.
*/

var vbeh = `<VisitTest implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[
var me, count, arr, testing;
var beh = {
  postConfigure: function() {
    me = this.cnode.parent();
    testing = Math.floor(Math.random() * 10);
    count = 0;
    me.length = 0; // makes me somewhat array-like
    arr = [];
    
    //me.visit(this.cnode);
    //this.cnode.remove();
  },
  act: function() {
    me.println(this.toString());
  },
  visit: function(visitor) {
    me.println("visited by " + visitor);
    this.doReduceCount();
    this.doReduceLength();
    this.doMapArrIXholon(visitor);
    return true;
  },
  doReduceCount: function() {
    count++;
  },
  doReduceLength: function() {
    me.length++;
  },
  doMapArrIXholon: function(visitor) {
    arr.push(visitor.name());
  },
  // testing:6 count:3 arr:helloWorldSystem_0,hello_2,world_3
  toString: function() {
    return "testing:" + testing + " count:" + count + " arr:" + arr;
  }
}
//# sourceURL=VisitTest.js
]]></VisitTest>`;

var node = xh.root();
var visitee = node.append(vbeh).last();
console.log(visitee.name());
node.visit(visitee); // true

/*
result in out tab:
visited by helloWorldSystem_0 state=0
visited by hello_2 [ port:world_3] state=0
visited by world_3 [ port:hello_2] state=0

result of calling step() for Cell model (note that the arr is serialized, naturally ordered by id):
testing:6 count:29 arr:extraCellularSpace_0,extraCellularSolution_1,glucose_2,eukaryoticCell_3,cellMembrane_4,
cellBilayer_5,cytoplasm_6,cytosol_7,glucose_8,glucose_6_Phosphate_9,fructose_6_Phosphate_10,fructose_1x6_Biphosphate_11,
dihydroxyacetonePhosphate_12,glyceraldehyde_3_Phosphate_13,x1x3_BisphosphoGlycerate_14,x3_PhosphoGlycerate_15,
x2_PhosphoGlycerate_16,phosphoEnolPyruvate_17,pyruvate_18,hexokinase_19,phosphoGlucoIsomerase_20,phosphoFructokinase_21,
aldolase_22,triosePhosphateIsomerase_23,glyceraldehyde_3_phosphateDehydrogenase_24,phosphoGlycerokinase_25,
phosphoGlyceromutase_26,enolase_27,pyruvateKinase_28

*/
