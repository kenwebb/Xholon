Checklist for upgrading a model to GWT
September 10, 2013
Ken Webb

assumptions:
- all .java and .xml are in the same folder

-------------------------------------------------------
_xhn.xml
- blank the ih cd csh entries
- optionally remove "./" from front of info entry, or blank it
- change UseDataPlotter to "google2" "gnuplot" or "none"
- change InteractionParams value to 32 (if UseInteractions == "true")

-------------------------------------------------------
ih.xml
- comment out unneeded includes such as <xi:include href="_viewer/XholonViewer.xml"/>

-------------------------------------------------------
add Resources.java (if ih cd csh entries in _xhn.xml are blanked)   ex:
package org.primordion.user.app._____._____._____;

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
  
  @Source("org/client/images/PrimordionLogo_tinyicon.png")
  ImageResource PrimordionLogo_tinyicon();
  
}

-----------------------------------------------------
- add to App (if using Resources.java):
  public Object findGwtClientBundle() {
	  return Resources.INSTANCE;
	}

- in App, fix grid panel code (if any)
- optionally add xi:include files to Resources.java (if .xml files are in app folder along with .java files)

-------------------------------------------------------
add to Xh (optional):
  public int setAttributeVal(String attrName, String attrVal) {
	  IApplication app = this.getApp();
	  Class clazz = Xh________.class;
	  if (app.isAppSpecificAttribute(this, clazz, attrName)) {
	    app.setAppSpecificAttribute(this, clazz, attrName, attrVal);
	  }
	  return 0;
	}

- needed only if using <attribute> or XML attributes (or <Attribute____>) ?

-------------------------------------------------------
- remove Xhn.java if it exists
- add model to org.client.Xholon.java
- add model to index.html
- check cd.xml and csh.xml
- remove "./" from front of any app-specific xincludes ?

