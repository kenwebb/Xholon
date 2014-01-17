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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.primordion.xholon.base.IControl;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * A client that works with Xholon's Scalable Vector Graphics (SVG) service.
 * <p>Example of use in Xholon csh.xml:</p>
<pre>
&lt;SvgClient>
  &lt;Attribute_String roleName="setup">Azimuth Petri Net,/org/primordion/user/app/azimuth/pn01/svg/chemistryNetBasicA03.svg,,,Compounds,/org/primordion/user/app/azimuth/pn01/SvgViewables.xml,%c&lt;/Attribute_String>
&lt;/SvgClient>
</pre>
The following can be pasted into the model while it's running:
<pre>
&lt;SvgClient>
  &lt;Attribute_String roleName="setup">${MODELNAME_DEFAULT},/org/primordion/user/app/SpringIdol/SpringIdol_0_1217099156531.svg&lt;/Attribute_String>
&lt;/SvgClient>
</pre>
or:
<pre>
&lt;SvgClient>
  &lt;Attribute_String roleName="setup">${MODELNAME_DEFAULT},file:///home/ken/workspace/Xholon/gui/Robot_0_1318858297363.svg&lt;/Attribute_String>
&lt;/SvgClient>
</pre>
or:
<pre>
&lt;SvgClient>
  &lt;Attribute_String roleName="setup">${MODELNAME_DEFAULT},${SVGURI_DEFAULT}&lt;/Attribute_String>
&lt;/SvgClient>
</pre>
or, to use all the defaults:
<pre>
&lt;SvgClient/>
</pre>
or include the SVG content itself: SVG_DATA_URI
<pre>
&lt;SvgClient>
  &lt;Attribute_String roleName="svgUri"><![CDATA[data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg"><circle r="50"/></svg>]]>&lt;/Attribute_String>
  &lt;Attribute_String roleName="setup">${MODELNAME_DEFAULT},${SVGURI_DEFAULT}&lt;/Attribute_String>
&lt;/SvgClient>
</pre>
or:
<pre>
&lt;SvgClient>
  &lt;Attribute_String roleName="setup">${MODELNAME_DEFAULT},${SVGURI_DEFAULT},,,ExtraCellularSpace,${VIEWABLES_CREATE}&lt;/Attribute_String>
&lt;/SvgClient>
</pre>
or (basic cell model):
<pre>
&lt;SvgClient>
  &lt;Attribute_String roleName="setup">${MODELNAME_DEFAULT},file:///home/ken/workspace/Xholon/gui/ExtraCellularSpace_0_1319344004318.svg,,,./,${VIEWABLES_CREATE}&lt;/Attribute_String>
&lt;/SvgClient>
</pre>
or an existing SVG file at wikipedia commons:
<pre>
&lt;SvgClient>
  &lt;Attribute_String roleName="setup">${MODELNAME_DEFAULT},http://upload.wikimedia.org/wikipedia/commons/d/d9/Oort_cloud_Sedna_orbit.svg&lt;/Attribute_String>
&lt;/SvgClient>
</pre>
TODO SvgClient.act() doesn't get invoked if it's a child of View
 * @author ken
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 15, 2011)
 */
public class SvgClient extends XholonWithPorts {
	
	// service states
	public static final int STATE_NOT_READY = 0;
	public static final int STATE_READY = 1;
	
	/**
	 * The SVG service.
	 */
	protected IXholon service = null;
	
	/**
	 * The current state of the SVG service.
	 * The SVG diagram may be loaded asynchronously,
	 * and nothing can be done with it until it has finished loading.
	 */
	protected int serviceState = STATE_NOT_READY;
	
	/**
	 * SVG setup parameters.
	 */
	protected String setup = null;
	
	/**
	 * The SVG URI may be specified separately.
	 * This is used to specify a data URI.
	 */
	protected String svgUri = null;
	
	/**
	 * An optional attributes XML URI. ex:
	 * <p>"/org/primordion/user/app/Risk/SvgAttributes.xml"</p>
	 */
	protected String svgAttributesUri = null;
	
	/**
	 * Initial text to add to the SVG image when it's loaded.
	 */
	protected List<String> makeText = new ArrayList<String>();
	
	/**
	 * A list of zero or more XML + JavaScript behaviors that should be added to the SVG service.
	 */
	protected List<String> viewBehavior = new ArrayList<String>();
	
	/**
	 * Whether or not the act() method is paused or active.
	 */
	protected boolean paused = false;
	
	@Override
	public boolean isRootNode() {return false;}
	
	/*
	 * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
	 */
	public void postConfigure() {
	  if (this.hasChildNodes()) {this.getFirstChild().postConfigure();} // this statement MUST come first
		if (svgUri != null) {
			if (!isValidSvg(svgUri)) {
				if (this.hasNextSibling()) {this.getNextSibling().postConfigure();}
				this.removeChild();
				return;
			}
		}
		service = this.getService("SvgViewService");
		if (svgUri != null) {
			service.sendSyncMessage(ISvgView.SIG_SETUP_SVGURI_REQ, svgUri, this);
		}
		if (svgAttributesUri != null) {
			service.sendSyncMessage(ISvgView.SIG_SETUP_SVGATTRIBUTESURI_REQ, svgAttributesUri, this);
		}
		if (setup == null) {
			setup = ISvgView.SVGALL_DEFAULTS;
		}
		service.sendSyncMessage(ISvgView.SIG_SETUP_REQ, setup, this);
		if (this.hasNextSibling()) {this.getNextSibling().postConfigure();}
		// SvgClient should always be a child of the "View" node, rather than a child of a model node
		if (!"View".equals(this.getParentNode().getRoleName())) {
			// make this SvgClient node a child of View
			this.removeChild();
			this.appendChild(this.getApp().getView());
		}
		// make sure that the act() method of this instance of SvgClient gets invoked each timeStep
		this.getApp().setShouldStepView(true);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
	  //System.out.println("SvgClient.act1");
		if (!paused && (serviceState == STATE_READY)) {
		  //System.out.println("SvgClient.act2");
			service.sendSyncMessage(ISvgView.SIG_ACT_REQ, null, this);
		}
		super.act();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedSyncMessage(org.primordion.xholon.base.IMessage)
	 */
	public IMessage processReceivedSyncMessage(IMessage msg)
	{
		Object response = null;
		
		switch (msg.getSignal()) {
		case ISvgView.SIG_STATUS_OK_REQ:
		  System.out.println("SvgClient processReceivedSyncMessage() makeText: " + makeText.size());
		  if (makeText != null) {
			  for (int i = 0; i < makeText.size(); i++) {
				  service.sendSyncMessage(ISvgView.SIG_MAKETEXT_REQ, makeText.get(i), this);
			  }
		  }
		  if (viewBehavior.size() > 0) {
		    Iterator<String> it = viewBehavior.iterator();
		    while (it.hasNext()) {
		      addViewBehavior(it.next());
		    }
		  }
		  service.sendSyncMessage(ISvgView.SIG_DISPLAY_REQ, null, this);
		  serviceState = STATE_READY;
		  break;
		case ISvgView.SIG_STATUS_NOT_OK_REQ:
		  System.out.println(msg.getData());
		  this.removeChild(); // remove self from the Xholon tree
		  break;
		default:
		  return super.processReceivedSyncMessage(msg);
		}
		return new Message(ISvgView.SIG_PROCESS_RSP, response, this, msg.getSender());
	}
	
	// added for GWT
	public int setAttributeVal(String attrName, String attrValue) {
	  if ("setup".equals(attrName)) {
	    setSetup(attrValue);
	  }
	  else if ("svgUri".equals(attrName)) {
	    setSvgUri(attrValue);
	  }
	  else if ("makeText".equals(attrName)) {
	    setMakeText(attrValue);
	  }
	  else if ("viewBehavior".equals(attrName)) {
	    setViewBehavior(attrValue);
	  }
	  return 0;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#removeChild()
	 * SvgClient thinks it's a rootNode3, so Xholon.java version of removeChild() fails
	 */
	/*public void removeChild()
	{
	  System.out.println("SvgClient.removeChild() : " + this);
		IXholon lNode = getPreviousSibling();
		IXholon rNode = getNextSibling();
	  System.out.println("SvgClient.removeChild() pSib: " + lNode);
	  System.out.println("SvgClient.removeChild() nSib: " + rNode);
		if (lNode == null) { // this is the first (leftmost) sibling
			if (rNode == null) {
				getParentNode().setFirstChild(null);
			}
			else {
				getParentNode().setFirstChild(rNode); // nextSibling is new firstChild of parent
			}
		}
		else {
			if (rNode == null) {
				lNode.setNextSibling(null);
			}
			else {
				lNode.setNextSibling(rNode);
			}
		}
		setParentNode(null);
		setNextSibling(null);
	}*/
	
	/**
	 * Is this valid SVG content?
	 * @param svgUriString
	 * @return true or false
	 */
	protected boolean isValidSvg(String svgUriString) {
		if (svgUriString.indexOf("<svg") == -1) {
			return false;
		}
		return true;
	}
	
	/**
	 * Style an SVG element.
	 * @param anId A Xholon or SVG id (ex: rect123).
	 * @param styleName (ex: fill).
	 * @param styleValue (ex: #ff0000).
	 */
	public void style(String anId, String styleName, String styleValue) {
		style(toCsv(anId, styleName, styleValue));
	}
	
	/**
	 * Style an SVG element.
	 * @param csvStr (ex: "rect123,fill,#ff0000")
	 */
	public void style(String csvStr) {
		service.sendSyncMessage(ISvgView.SIG_STYLE_REQ, csvStr, this);
	}
	
	/**
	 * Set an XML attribute of an SVG element.
	 * @param anId A Xholon or SVG id (ex: "Ball").
	 * @param attrName (ex: "cx").
	 * @param attrValue (ex: "34").
	 */
	public void xmlAttr(String anId, String attrName, String attrValue) {
		xmlAttr(toCsv(anId, attrName, attrValue));
	}
	
	/**
	 * Set an XML attribute of an SVG element.
	 * @param csvStr (ex: "Ball,cx,34")
	 */
	public void xmlAttr(String csvStr) {
		service.sendSyncMessage(ISvgView.SIG_XMLATTR_REQ, csvStr, this);
	}
	
	/**
	 * Concatenates the inputs into a single comma-separated String.
	 * @param anId A Xholon or SVG id (ex: "Ball").
	 * @param aName (ex: "cx").
	 * @param aValue (ex: "34").
	 * @return A comma-separated String (ex: "Ball,cx,34").
	 */
	public String toCsv(String anId, String aName, String aValue) {
		StringBuilder sb = new StringBuilder()
		.append(anId).append(",").append(aName).append(",").append(aValue);
		return sb.toString();
	}
	
	/**
	 * Add a new view behavior.
	 * @param viewBehavior An XML string, containing a JavaScript behavior.
	 * This is typically an "SvgViewBrowserbehavior".
	 */
	public void addViewBehavior(String viewBehavior) {
	  service.sendSyncMessage(ISvgView.SIG_ADD_VIEWBEHAVIOR_REQ, viewBehavior, this);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#handleNodeSelection()
	 */
	public Object handleNodeSelection() {
		if (getApp().getControllerState() == IControl.CS_STOPPED) {
			// force a final update to the SVG image
			service.sendSyncMessage(ISvgView.SIG_ACT_REQ, null, this);
		}
		else {
			paused = !paused;
		}
		return this.toString();
	}
	
	public String getSetup() {
		return setup;
	}

	public void setSetup(String setup) {
		this.setup = setup;
	}

	public String getSvgUri() {
		return svgUri;
	}

	public void setSvgUri(String svgUri) {
		this.svgUri = svgUri;
	}

	public String getSvgAttributesUri() {
		return svgAttributesUri;
	}

	public void setSvgAttributesUri(String svgAttributesUri) {
		this.svgAttributesUri = svgAttributesUri;
	}

	public List<String> getMakeText() {
		return makeText;
	}

	public void setMakeText(String makeText) {
		this.makeText.add(makeText);
	}
	
	public List<String> getViewBehavior() {
		return viewBehavior;
	}

	public void setViewBehavior(String viewBehavior) {
		this.viewBehavior.add(viewBehavior);
	}

}
