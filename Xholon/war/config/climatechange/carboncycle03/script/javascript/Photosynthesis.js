<Photosynthesis val_double="120.0" implName="org.primordion.user.app.climatechange.carboncycle03.Flow_javascript"><![CDATA[
  var behaviorObject = new Object();
  
  behaviorObject.rateFactor = 0.0;
  behaviorObject.atmosphere = null;
  behaviorObject.terrestrialVegetation = null;
  
  behaviorObject.postConfigure = function() {
    atmosphere = contextNodeKey.getParentNode().getAtmosphere();
    terrestrialVegetation = contextNodeKey.getParentNode().getTerrestrialVegetation();
    rateFactor = contextNodeKey.getVal() / terrestrialVegetation.getVal();
  }
  
  behaviorObject.act = function() {
    var rate = contextNodeKey.getVal();
    atmosphere.decVal(rate);
    terrestrialVegetation.incVal(rate);
  }
  
  behaviorObject.postAct = function() {
    contextNodeKey.setVal(terrestrialVegetation.getVal() * rateFactor);
  }

]]></Photosynthesis>