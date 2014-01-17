/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2009 Ken Webb
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.xholon.service.xholonhelper;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.user.client.ui.TextArea;

//import java.awt.Toolkit;
//import java.awt.datatransfer.Clipboard;
//import java.awt.datatransfer.DataFlavor;
//import java.awt.datatransfer.StringSelection;
//import java.awt.datatransfer.Transferable;
//import java.awt.datatransfer.UnsupportedFlavorException;
//import java.io.IOException;
//import java.security.AccessControlException;

/**
 * Interaction with the Java clipboard.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on July 15, 2009)
 */
public class XholonClipboard implements IClipboard {
	
	/**
	 * constructor
	 */
	public XholonClipboard() {}
	
	/*
	 * @see org.primordion.xholon.util.IXholonClipboard#readStringFromClipboard()
	 */
	public String readStringFromClipboard()
	{
		String str = null;
		Element element = Document.get().getElementById("xhclipboard").getFirstChildElement();
    if (element != null) {
        TextAreaElement textfield = element.cast();
        str = textfield.getValue();
        
        /* the following fails at runtime
        TextArea ta = TextArea.wrap(element);
        if (ta.getSelectionLength() == 0) {
          str = ta.getText();
        }
        else {
          str = ta.getSelectedText();
        }
        */
    }
		
		/* GWT
		Clipboard clipboard = null;
		try {
			clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		} catch (AccessControlException e) {
			return "<Null/>";
		}
		Transferable contents = clipboard.getContents(null);
		if (contents == null) {return null;}
		try {
			if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				str = (String)contents.getTransferData(DataFlavor.stringFlavor);
			}
			else {str = "<Null/>";}
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return str;
	}
	
	/*
	 * @see org.primordion.xholon.util.IXholonClipboard#writeStringToClipboard(java.lang.String)
	 */
	public void writeStringToClipboard(String str)
	{
	  Element element = Document.get().getElementById("xhclipboard").getFirstChildElement();
    if (element != null) {
        TextAreaElement textarea = element.cast();
        textarea.setValue(str);
    }
	  
	  /* GWT
		Clipboard clipboard = null;
		try {
			clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		} catch (AccessControlException e) {
			return;
		}
		Transferable contents = new StringSelection(str);
		clipboard.setContents(contents, null);*/
	}
	
	/*
	 * @see org.primordion.xholon.util.IXholonClipboard#copyStringToClipboard(java.lang.String)
	 */
	public void copyStringToClipboard(String str)
	{
	  writeStringToClipboard(str);
	  /* GWT
		Clipboard clipboard = null;
		try {
			clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		} catch (AccessControlException e) {
			return;
		}
		StringSelection stringSelection = new StringSelection(str);
		clipboard.setContents(stringSelection, stringSelection);*/
	}
	
}
