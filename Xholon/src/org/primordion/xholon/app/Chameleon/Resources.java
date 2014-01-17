package org.primordion.xholon.app.Chameleon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.TextResource;

public interface Resources extends ClientBundleWithLookup {
  public static final Resources INSTANCE = GWT.create(Resources.class);

  @Source("Chameleon_xhn.xml")
  TextResource _xhn();
  
  @Source("Chameleon_InheritanceHierarchy.xml")
  TextResource ih();
  
  @Source("Chameleon_ClassDetails.xml")
  TextResource cd();
  
  @Source("Chameleon_CompositeStructureHierarchy.xml")
  TextResource csh();
  
  @Source("Chameleon_Information.xml")
  TextResource info();
  
}
