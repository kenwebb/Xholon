/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2011 Ken Webb
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

package org.primordion.xholon.service.svg;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.util.StringHelper;

/**
 * Store correspondences between Viewables and SVG elements.
 * A Viewable is a node in the Xholon model tree that has an SVG representation.
 * TODO it should be possible for the xhcName to be an XPath expression
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 10, 2011)
 */
public class SvgViewable extends Xholon {

	private String xhcName = ""; // an XPath expression
	private IXholon xhNode = null;
	private String svgId = "";
	private Element svgNode = null; // GWT
	private String format = null; // String.format

	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure() {
		SvgViewBrowser svgView = ((SvgViewBrowser)this.getParentNode());
		if (svgNode == null) {
			svgNode = DOM.getElementById(svgId);
		}
		if (format == null) {
			format = svgView.getFormat();
		}
		if (xhNode == null) {
			xhNode = getXPath().evaluate(xhcName, svgView.getViewablesRoot());
		}
		if (xhNode != null) {
			setText();
		}
		if (xhcName.equals("TimeStep")) {
			setText(Integer.toString(this.getApp().getTimeStep()));
		}
		super.postConfigure();
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#reconfigure()
	 */
	public void reconfigure() {
		svgNode = DOM.getElementById(svgId);
		super.reconfigure();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
		if (xhNode != null) {
			if (!xhNode.hasParentNode()) {
				// the xhNode has been replaced; find the new xhNode
				xhNode = null;
				SvgViewBrowser svgView = ((SvgViewBrowser)this.getParentNode());
				svgView.getViewablesRoot().visit(this);
			}
			setText();
		}
		else if (xhcName.equals("TimeStep")) {
			setText(Integer.toString(this.getApp().getTimeStep()));
		}
		else if (xhNode == null) {
			System.out.println("xhNode is null:" + xhcName);
		}
		super.act();
	}
	
	/**
	 * Set the text in the svgNode, based on the current contents of the xhNode.
	 */
	protected void setText() {
		if ("%c".equals(format)) { // char
			char c = xhNode.getVal_char();
			setText(StringHelper.format(format, c));
		}
		else if ("%d".equals(format)) { // decimal integer
			int d = xhNode.getVal_int();
			setText(StringHelper.format(format, d));
		}
		else if ("%s".equals(format)) { // String
			String s = xhNode.getVal_String();
			setText(s);
		}
		else { // assume it's a double
			double val = xhNode.getVal();
			if (val == Double.NEGATIVE_INFINITY) {
				setText("");
			}
			else {
				setText(StringHelper.format(format, val));
			}
		}
	}
	
	/**
	 * Set the text for an existing SVG tspan node, whose parent must be an SVG
	 * text node.
	 * @param text The text that the tspan node should be set to.
	 */
	protected void setText(String text) {
	  if (svgNode == null) {return;}
		if (text == null) {return;}
		if (!text.equals(svgNode.getFirstChild().getNodeValue())) {
		  // the tspan has a TextNode as its first child
		  svgNode.getFirstChild().setNodeValue(text);
    }
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrVal) {
		if ("xhcName".equals(attrName)) {
			xhcName = attrVal;
		} else if ("svgId".equals(attrName)) {
			svgId = attrVal;
		} else if ("format".equals(attrName)) {
			format = attrVal;
		}
		return 0;
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		String outStr = getName();
		outStr += " xhcName:" + xhcName;
		outStr += " svgId:" + svgId;
		outStr += " xhNode:" + xhNode;
		outStr += " format:" + format;
		return outStr;
	}

	public String getXhcName() {
		return xhcName;
	}

	public void setXhcName(String xhcName) {
		this.xhcName = xhcName;
	}

	public IXholon getXhNode() {
		return xhNode;
	}

	public void setXhNode(IXholon xhNode) {
		this.xhNode = xhNode;
	}

	public String getSvgId() {
		return svgId;
	}

	public void setSvgId(String svgId) {
		this.svgId = svgId;
	}
  
	public Element getSvgNode() {
		return svgNode;
	}

	public void setSvgNode(Element svgNode) {
		this.svgNode = svgNode;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
