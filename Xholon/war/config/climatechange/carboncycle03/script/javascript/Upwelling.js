<Upwelling val_double="101.0" implName="org.primordion.user.app.climatechange.carboncycle03.Flow_javascript"><![CDATA[
  var behaviorObject = new Object();
  
  behaviorObject.rateFactor = 0.0;
  behaviorObject.surfaceOcean = null;
  behaviorObject.deepOcean = null;
  
  behaviorObject.postConfigure = function() {
    surfaceOcean = contextNodeKey.getParentNode().getSurfaceOcean();
    deepOcean = contextNodeKey.getParentNode().getDeepOcean();
    rateFactor = contextNodeKey.getVal() / deepOcean.getVal();
  }
  
  behaviorObject.act = function() {
    var rate = contextNodeKey.getVal();
    surfaceOcean.incVal(rate);
    deepOcean.decVal(rate);
  }
  
  behaviorObject.postAct = function() {
    contextNodeKey.setVal(deepOcean.getVal() * rateFactor);
  }

]]></Upwelling>