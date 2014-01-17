package org.primordion.user.app.azimuth.pn01;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.TextResource;

public interface Resources extends ClientBundleWithLookup {
  public static final Resources INSTANCE = GWT.create(Resources.class);

  @Source("_xhn.xml")
  TextResource _xhn();
  
  @Source("ih.xml")
  TextResource ih();
  
  @Source("cd.xml")
  TextResource cd();
  
  @Source("csh.xml")
  TextResource csh();
  
  @Source("svg/chemistryNetBasicA05opt.svg")
  TextResource chemistryNetBasicA05opt();
  
  @Source("SvgViewables.xml")
  TextResource SvgViewables();
  
  @Source("svg/TheSystem_0_1319378326050.svg")
  TextResource TheSystem_0_1319378326050();
  
}
