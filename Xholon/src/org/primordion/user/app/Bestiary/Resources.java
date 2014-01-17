package org.primordion.user.app.Bestiary;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.TextResource;

public interface Resources extends ClientBundleWithLookup {
  public static final Resources INSTANCE = GWT.create(Resources.class);

  @Source("Bestiary_xhn.xml")
  TextResource _xhn();
  
  @Source("Bestiary_InheritanceHierarchy.xml")
  TextResource ih();
  
  @Source("Bestiary_ClassDetails.xml")
  TextResource cd();
  
  @Source("Bestiary_CompositeStructureHierarchy.xml")
  TextResource csh();
  
  @Source("Bestiary_Information.xml")
  TextResource info();
  
  @Source("special.xml")
  TextResource special();
  
}
