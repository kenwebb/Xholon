package org.primordion.xholon.app.Furcifer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.TextResource;

public interface Resources extends ClientBundleWithLookup {
  public static final Resources INSTANCE = GWT.create(Resources.class);

  @Source("Furcifer_xhn.xml")
  TextResource _xhn();
  
  @Source("Furcifer_InheritanceHierarchy.xml")
  TextResource ih();
  
  @Source("Furcifer_ClassDetails.xml")
  TextResource cd();
  
  @Source("Furcifer_CompositeStructureHierarchy.xml")
  TextResource csh();
  
  @Source("Furcifer_Information.html")
  TextResource info();
  
  @Source("default.svg")
  TextResource svg();
  
}
