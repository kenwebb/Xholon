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

/**
 * Modify an existing attribute in an SVG image.
 * It can be a regular attribute, or a style attribute.
 * This is typically used to pre-process an existing SVG image,
 * such as an image that already exists at wikipedia.
 * Examples:
<pre>
&lt;SvgAttribute svgId="rect456" attr="fill" newValue="#ff8080"/>
&lt;SvgAttribute svgId="path789" attr="style=fill" newValue="#ff8080"/>
&lt;SvgAttribute svgId="TheSystem/Compounds" attr="style=fill" newValue="#ff8080"/>
&lt;SvgAttribute svgId="TheSystem/Compounds" attr="width" newValue="80"/>
</pre>
the following fails:
<pre>
&lt;SvgAttribute svgId="scandinavia" attr="id" newValue="World/Europe/Scandinavia"/>
</pre>
or:
<pre>
&lt;_-.attrs>
  &lt;SvgAttribute svgId="TheSystem/Compounds" attr="style=fill" newValue="#ff8080"/>
  &lt;SvgAttribute svgId="TheSystem/Compounds" attr="width" newValue="150"/>
  &lt;SvgAttribute svgId="TheSystem/Compounds" attr="height" newValue="150"/>
&lt;/_-.attrs>
</pre>

 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 24, 2011)
 */
public class SvgAttribute extends Xholon {
	
	private String svgId = "";
	private String attr = "";
	private String newValue = "";
	
	private IXholon svgView = null;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure() {
	  System.out.println("postConfigure");
		svgView = this.getParentNode();
		setAttribute();
		super.postConfigure();
		this.removeChild();
	}
	
	/**
	 * Set an attribute of an existing SVG node.
	 */
	protected void setAttribute() {
		// GWT code
		//System.out.println("svgId");
		//System.out.println(this.toString());
		Element svgNode = DOM.getElementById(svgId);
		//System.out.println("svgNode");
		if (svgNode == null) {return;}
		if (attr.startsWith("style=")) {
			String styleAttr = attr.substring(6);
			// .getStyle().setProperty("backgroundColor", "red") // note camel case
			// <SvgAttribute svgId="HelloWorldSystem/Hello" attr="style=stroke" newValue="red" /> WORKS
			// <SvgAttribute svgId="HelloWorldSystem/Hello" attr="style=fill" newValue="pink" /> WORKS
			// <SvgAttribute svgId="HelloWorldSystem/Hello" attr="style=strokeWidth" newValue="3" /> WORKS
			svgNode.getStyle().setProperty(styleAttr, newValue);
		}
		else if ("id".equals(attr)) {
			svgNode.setId(newValue);
	  }
	  else {
	    //System.out.println("regular attr:" + attr);
  		setNonLengthAttribute(svgNode, attr, newValue);
			/*if (svgNode.hasAttribute(attr)) {
			  System.out.println("regular setPropertyString:" + attr + " " + newValue);
			  // does nothing in GWT
				//svgNode.setPropertyString(attr, newValue);
				// <SvgAttribute svgId="HelloWorldSystem/Hello" attr="width" newValue="50" /> // WORKS
				// <SvgAttribute svgId="HelloWorldSystem/Hello" attr="fill" newValue="yellow" /> // WORKS if no style
				// TODO only convert to an int if newValue is in fact numeric
				if (isLength(attr)) {
				  setLengthAttribute(svgNode, attr, Integer.parseInt(newValue));
				}
				else {
				  setNonLengthAttribute(svgNode, attr, newValue);
				}
			}
			else {
			  System.out.println("regular setAttribute:" + attr + " " + newValue);
				//svgNode.setAttribute(attr, newValue);
				if (isLength(attr)) {
				  setLengthAttribute(svgNode, attr, Integer.parseInt(newValue));
				}
				else {
				  setNonLengthAttribute(svgNode, attr, newValue);
				}
			}*/
		}
	}
	
	/**
	 * Is this a length (SVGAnimatedLength) attribute ?
	 * width height x y rx ry  cx cy r  x1 x2 y1 y2
	 */
	private boolean isLength(String attr) {
	  if (attr == null || attr.length() == 0) {return false;}
	  char c = attr.charAt(0);
	  switch (c) {
	    case 'w':
	      if ("width".equals(attr)) {return true;}
	      break;
	    case 'h':
	      if ("height".equals(attr)) {return true;}
	      break;
	    case 'x':
	      if ("x".equals(attr)) {return true;}
	      break;
	    case 'y':
	      if ("y".equals(attr)) {return true;}
	      break;
	    case 'r':
	      if ("rx".equals(attr)) {return true;}
	      if ("ry".equals(attr)) {return true;}
	      break;
	    default:
	      return false;
	  }
	  return false;
	}
	
	// width height x y rx ry are instances of SVGAnimatedLength
	private native void setLengthAttribute(Element ele, String attr, int val) /*-{
	  if (ele) {
	    ele.setAttributeNS(null, attr, val); // YES
	  }
	}-*/;
	
	/**
	 * not sure if there's any real difference between this and setLengthAttribute()
	 */
	private native void setNonLengthAttribute(Element ele, String attr, String val) /*-{
	  if (ele) {
	    ele.setAttributeNS(null, attr, val); // YES
	  }
	}-*/;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrVal) {
		if ("svgId".equals(attrName)) {
			svgId = attrVal;
		}
		else if ("attr".equals(attrName)) {
			attr = attrVal;
		}
		else if ("newValue".equals(attrName)) {
			newValue = attrVal;
		}
		return 0;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		String outStr = getName();
		outStr += " svgId:" + svgId;
		outStr += " attr:" + attr;
		outStr += " newValue:" + newValue;
		return outStr;
	}

	public String getSvgId() {
		return svgId;
	}

	public void setSvgId(String svgId) {
		this.svgId = svgId;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public IXholon getSvgView() {
		return svgView;
	}

	public void setSvgView(IXholon svgView) {
		this.svgView = svgView;
	}
	
}
