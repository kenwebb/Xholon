<Downwelling val_double="90.2" implName="org.primordion.user.app.climatechange.carboncycle03.Flow_javascript"><![CDATA[
  var behaviorObject = new Object();
  
  behaviorObject.rateFactor = 0.0;
  behaviorObject.surfaceOcean = null;
  behaviorObject.deepOcean = null;
  
  behaviorObject.postConfigure = function() {
    surfaceOcean = contextNodeKey.getParentNode().getSurfaceOcean();
    deepOcean = contextNodeKey.getParentNode().getDeepOcean();
    rateFactor = contextNodeKey.getVal() / surfaceOcean.getVal();
  }
  
  behaviorObject.act = function() {
    var rate = contextNodeKey.getVal();
    surfaceOcean.decVal(rate);
    deepOcean.incVal(rate);
  }
  
  behaviorObject.postAct = function() {
    contextNodeKey.setVal(surfaceOcean.getVal() * rateFactor);
  }

]]></Downwelling>