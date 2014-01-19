<Upwelling val_double="101.0" implName="org.primordion.user.app.climatechange.carboncycle03.Flow_javascript"><![CDATA[
  // THIS DOES NOT WORK
  // the values of rateFactor etc. get reinitialized each time the script is evaluated
                                                                                                                      
  //var rateFactor = 0.0;
  //var surfaceOcean = null;
  //var deepOcean = null;
  
  println(typeof surfaceOcean);
  if (typeof surfaceOcean == "undefined") {
    println("init");
    surfaceOcean = contextNodeKey.getParentNode().getSurfaceOcean();
    deepOcean = contextNodeKey.getParentNode().getDeepOcean();
    rateFactor = contextNodeKey.getVal() / deepOcean.getVal();
  }
  
  println("acting");
  var rate = contextNodeKey.getVal();
  surfaceOcean.incVal(rate);
  deepOcean.decVal(rate);
  
  function postAct() {
    println("post acting");
    contextNodeKey.setVal(deepOcean.getVal() * rateFactor);
  }

]]></Upwelling>