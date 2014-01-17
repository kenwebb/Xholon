package org.primordion.user.app.climatechange.mdcs.m2_2mp;

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
  
  @Source("earthRB_csh.xml")
  TextResource earthRB_csh();
  
  @Source("i18n/EnergyBudgetSvgLabels_en.xml")
  TextResource EnergyBudgetSvgLabels_en();
  
  @Source("SvgViewables.xml")
  TextResource SvgViewables();
  
}
