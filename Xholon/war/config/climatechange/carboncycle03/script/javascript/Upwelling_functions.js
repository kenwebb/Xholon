<Upwelling val_double="101.0" implName="org.primordion.user.app.climatechange.carboncycle03.Flow_javascript"><![CDATA[
  
  var rateFactor = 0.0;
  var surfaceOcean = null;
  var deepOcean = null;
  
  function postConfigure() {
    surfaceOcean = contextNodeKey.getParentNode().getSurfaceOcean();
    deepOcean = contextNodeKey.getParentNode().getDeepOcean();
    rateFactor = contextNodeKey.getVal() / deepOcean.getVal();
  }
  
  function act() {
    var rate = contextNodeKey.getVal();
    surfaceOcean.incVal(rate);
    deepOcean.decVal(rate);
  }
  
  function postAct() {
    contextNodeKey.setVal(deepOcean.getVal() * rateFactor);
  }

]]></Upwelling>