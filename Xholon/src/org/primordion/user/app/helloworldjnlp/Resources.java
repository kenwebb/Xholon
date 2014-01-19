package org.primordion.user.app.helloworldjnlp;

import com.google.gwt.core.client.GWT;
//import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ClientBundleWithLookup;
//import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ExternalTextResource;
//import com.google.gwt.resources.client.ImageResource;
//import com.google.gwt.resources.client.TextResource;
//import com.google.gwt.resources.client.ClientBundle.Source;

public interface Resources extends ClientBundleWithLookup {
  public static final Resources INSTANCE = GWT.create(Resources.class);

  @Source("CompositeStructureHierarchy.xml")
  ExternalTextResource csh();
  
  @Source("InheritanceHierarchy.xml")
  ExternalTextResource ih();
  
  @Source("org/client/config/_common/_default_xhn.xml")
  ExternalTextResource default_xhn();
  
}
