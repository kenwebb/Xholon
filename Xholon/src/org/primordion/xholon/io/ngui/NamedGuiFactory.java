/* AQL Web Interface
 * Copyright (C) 2017 Ken Webb
 * BSD License
 */

package org.primordion.xholon.io.ngui;

//import org.primordion.xholon.base.Xholon;

/**
 * Named Gui factory.
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on June 27, 2017)
 */
public class NamedGuiFactory { // extends Xholon {
	
	public static INamedGui getNamedGui(String namedGuiName) {
		INamedGui ngui = null;
		if (INamedGui.NGUI_AQL_WEB_INTERFACE_NAME.equals(namedGuiName)) { // "AqlWebInterface"
	    ngui = new org.primordion.xholon.io.ngui.AqlWebInterface();
	  }
		return ngui;
	}
	
}
