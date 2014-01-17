package org.primordion.ctrnn.AdapSysLab;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.TextResource;

public interface Resources extends ClientBundleWithLookup {
  public static final Resources INSTANCE = GWT.create(Resources.class);

  @Source("AdapSysLab_xhn.xml")
  TextResource _xhn();
  
  @Source("InheritanceHierarchy.xml")
  TextResource ih();
  
  @Source("ClassDetails.xml")
  TextResource cd();
  
  @Source("CompositeStructureHierarchy.xml")
  TextResource csh();
  
  @Source("Information.xml")
  TextResource info();
  
  @Source("AdapSysLab_Network.svg")
  TextResource svg();
  
}
