package org.primordion.user.app.solarsystem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.TextResource;

public interface Resources extends ClientBundleWithLookup {
  public static final Resources INSTANCE = GWT.create(Resources.class);

  // a module doesn't have a _xhn
  //@Source("_xhn.xml")
  //TextResource _xhn();
  
  @Source("ih.xml")
  TextResource ih();
  
  @Source("cd.xml")
  TextResource cd();
  
  @Source("cshLargestObjects.xml")
  TextResource cshLargestObjects();
  
  @Source("cshSunEarth.xml")
  TextResource cshSunEarth();
  
  @Source("cshSunPlanets.xml")
  TextResource cshSunPlanets();
  
  @Source("moduleSunEarth.xml")
  TextResource moduleSunEarth();
  
  @Source("moduleSunEarth_inline.xml")
  TextResource moduleSunEarth_inline();
  
  @Source("moduleSunPlanets.xml")
  TextResource moduleSunPlanets();
  
  @Source("moduleSunspotCycle.xml")
  TextResource moduleSunspotCycle();
  
}
