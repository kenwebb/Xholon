package org.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.TextResource;

/**
 * @see the separate files in config/_common
 */
public interface RCConfig extends ClientBundleWithLookup {
  public static final RCConfig INSTANCE = GWT.create(RCConfig.class);

  @Source("org/client/config/_common/_default_xhn.xml")
  TextResource _default_xhn();
  
  @Source("org/client/config/_common/XhMechanisms.xml")
  TextResource XhMechanisms();
  
  @Source("org/client/config/_common/_control/Control_CompositeHierarchy.xml")
  TextResource Control_CompositeHierarchy();
  
  @Source("org/client/config/_common/_mechanism/XholonMechanism.xml")
  TextResource XholonMechanism();
  
  @Source("org/client/config/_common/_mechanism/XholonMechanismCd.xml")
  TextResource XholonMechanismCd();
  
  @Source("org/client/config/_common/_module/PetriNetIh.xml")
  TextResource PetriNetIh();
  
  @Source("org/client/config/_common/_service/Service_CompositeStructureHierarchy.xml")
  TextResource Service_CompositeStructureHierarchy();
  
  @Source("org/client/config/_common/_viewer/XholonViewer.xml")
  TextResource XholonViewer();
  
  @Source("org/client/config/_common/_viewer/XholonViewerCd.xml")
  TextResource XholonViewerCd();
  
}
