/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

package org.primordion.xholon.base;

import com.google.gwt.user.client.Window;

//import java.net.MalformedURLException;
//import java.net.URL;

//import org.primordion.xholon.io.BrowserLauncher;

/**
 * Any Xholon node can be given a textual annotation.
 * <p>Annotations can be defined using the &lt;Annotation&gt; tag in CompositeStructure.xml files,
 * ex: &lt;Annotation&gt;http://en.wikipedia.org/wiki/Water&lt;/Annotation&gt;</p>
 * <p>??? Annotations can also be defined using the &lt;attribute&gt; tag in CompositeStructure.xml files,
 * ex: &lt;attribute name="annotation" value="http://en.wikipedia.org/wiki/Water"/&gt;.
 * <strong>Warning:</strong> If there is more than one &lt;attribute&gt; tag for a xholon in the .xml file,
 * then the "annotation" element must be the last one in the set.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on October 13, 2007)
 */
public class Annotation extends Xholon implements IAnnotation {
	private static final long serialVersionUID = 4444110948091750196L;
	
	/**
	 * The text of the annotation.
	 */
	private String val = null;
	
	/**
	 * default constructor
	 */
	public Annotation() {}
	
	/**
	 * constructor
	 * @param val
	 */
	public Annotation(String val) {
		this.val = val;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure()
	{
		if (SHOULD_REMOVE_THIS_NODE) {
			// save this Annotation node's first child and next sibling, before removing it from the tree
			IXholon savedFirstChild = firstChild;
			IXholon savedNextSibling = nextSibling;
			IXholon savedParentNode = parentNode;
			// remove this Annotation node from the tree
			removeChild();
			// have the owner of the Annotation store it somewhere
			savedParentNode.setAnnotation(val);
			// execute recursively
			if (savedFirstChild != null) {
				savedFirstChild.postConfigure();
			}
			if (savedNextSibling != null) {
				savedNextSibling.postConfigure();
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(java.lang.String)
	 */
	public void setVal(String val)
	{
		this.val = val;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_String()
	 */
	public String getVal_String()
	{
		return val;
	}
	
	/**
	 * This IS an Annotation, so this node just shows its own contents.
	 * @see org.primordion.xholon.base.Xholon#showAnnotation()
	 */
	public void showAnnotation()
	{
		if (isUrl()) {
			showUrl();
			//println("[URL] " + val);
		}
		else {
			println(val);
		}
	}
	
	/**
	 * Is the current value of "val" a valid URL.
	 * @return true or false (an unknown protocol is specified)
	 */
	protected boolean isUrl()
	{
		if (val.startsWith("http")) {
		  return true;
		}
		return false;
	}
	
	/**
	 * Show the annotation by treating it as a URL and passing it to a browser for display.
	 */
	protected void showUrl()
	{
		//BrowserLauncher.launch(val);
		Window.open(val, "_blank", ""); // checked if it starts with "http"
	}
	
	/**
	 * Make and return a unique key for a IXholon node.
	 * @param node
	 * @return
	 */
	public static String makeUniqueKey(IXholon node)
	{
		return Integer.toString(node.hashCode()) + "_" + Integer.toString(IAnnotation.class.hashCode());
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString()
	{
		if ((val != null) && (val.length() > ANNOTATION_MAX_DISPLAY_LEN)) {
			return val.substring(0, ANNOTATION_MAX_DISPLAY_LEN) + " ...";
		}
		return val;
	}
	
	/**
	 * main for testing
	 * @param args
	 */
	/*public static void main(String[] args) {
		IAnnotation ann = new Annotation();
		ann.setVal("This is not a url"); ann.showAnnotation();
		ann.setVal("http://www.primordion.com"); ann.showAnnotation();
	}*/
}
