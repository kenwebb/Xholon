package org.primordion.user.app.climatechange.igm;

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
  
  @Source("default_csh.xml")
  TextResource default_csh();
  
  @Source("SvgViewables.xml")
  TextResource SvgViewables();
  
  @Source("IdealizedGreenhouseEmissivity78.svg")
  TextResource IdealizedGreenhouseEmissivity78();
  
  @Source("gui/IdealizedGreenhouseModel_0_1316471572049_2.svg")
  TextResource IdealizedGreenhouseModel_0_1316471572049_2();
  
}
