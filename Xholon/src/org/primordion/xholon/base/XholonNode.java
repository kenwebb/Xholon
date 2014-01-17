/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 201 Ken Webb
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

import org.primordion.xholon.io.xml.IXml2Xholon;
import org.primordion.xholon.service.IXholonService;

/**
 * A XholonNode is one IXholon instance.
 * If more than one instance matches the nodeId,
 * only the first match will be used.
 * A XholonNode has several possible uses, including:
 * <ul>
 * <li>with the RecordPlaybackService to store history
 * that can be played back.</li>
 * <li>with XholonInstanceDetails to store attributes
 * that should be pasted into specifc node instances.</li>
 * <li>for sending nodes or other content from one app to another,
 * or from one node to another.</li>
 * </ul>
 * <p>Notes</p>
 * <ul>
 * <li>Many of the Java attributes in this class are optional,
 * or only required in specified circumstances.</li>
 * <li>Perhaps the contents should have &lt; instead of < .</li>
 * <li>The contents may aleady contain XML CDATA.</li>
 * <li>Possibly a collection of XholonNode could be a forest, with an outer tag:
 * &lt;_-.XholonInstanceDetails> or &lt;_-.History></li>
 * <li>Xml2Xholon probably needs to treat XholonNode separately, to handle it's XML contents.</li>
 * <li>See base.transferobject package for other ideas.</li>
 * </ul>
 * <p>This works for &lt;XholonInstanceDetails>:</p>
<pre>
&lt;XholonNode nodeId="Earth" />
</pre>
 * This works for &lt;History>:
<pre>
&lt;XholonNode
 app="solarsystemtest"
 timeStep="0"
 nodeId="solarSystemTest_0"
 idType="4"
 action="pasteLastChild"
 time="Sun Nov 13 14:20:31 EST 2011"/>
</pre>
 * This works:
<pre>
&lt;XholonNode nodeId="Planet" idType="1" />
</pre>
 * This works:
<pre>
&lt;XholonNode nodeId="Venus">
  &lt;Attribute_double roleName="radius">1234567.0&lt;/Attribute_double>
  &lt;Attribute_double roleName="mass">98.765e21&lt;/Attribute_double>
&lt;/XholonNode>
</pre>
 * This works:
<pre>
&lt;_-.XholonInstanceDetails>
  &lt;XholonNode nodeId="Uranus">
    &lt;Attribute_double roleName="radius">25362000.0&lt;/Attribute_double>
    &lt;Attribute_double roleName="mass">86832.0e21&lt;/Attribute_double>
  &lt;/XholonNode>
  &lt;XholonNode nodeId="Neptune">
    &lt;Attribute_double roleName="radius">24622000.0&lt;/Attribute_double>
    &lt;Attribute_double roleName="mass">102430.0e21&lt;/Attribute_double>
  &lt;/XholonNode>
&lt;/_-.XholonInstanceDetails>
</pre>

</pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on November 23, 2011)
 */
public class XholonNode extends Xholon {
	private static final long serialVersionUID = -3621367798626117959L;

	/** xhcName ex: "SolarSystem" */
	public static final int XHNODE_IDTYPE_XHCNAME = 1;
	
	/** roleName ex: "Mercury" */
	public static final int XHNODE_IDTYPE_ROLENAME = 2;
	
	/** xhcName or roleName */
	public static final int XHNODE_IDTYPE_XHCNAME_OR_ROLENAME = 3;
	
	/** result of calling node.getName() */
	public static final int XHNODE_IDTYPE_GETNAME = 4;
	
	/** XPath expression */
	public static final int XHNODE_IDTYPE_XPATH = 5;
	
	/** node id ex: "3" */
	public static final int XHNODE_IDTYPE_ID = 6;
	
	/** default */
	public static final int XHNODE_IDTYPE_DEFAULT = XHNODE_IDTYPE_XHCNAME_OR_ROLENAME;
	
	/** The Xholon edit action that should be taken. */
	private String action = "pasteLastChild";
	
	/** The Xholon application that this node is a part of. */
	private String nodezApp = null;
	
	/** The type of id used by the nodeId. */
	private int idType = XHNODE_IDTYPE_DEFAULT;
	
	/**
	 * A nodeId identifies a node, using one of the idType schemes.
	 * Ideally, the nodeId should uniquely identify exactly one node in the current Xholon tree.
	 * If the nodeId identifies more than one node, then only the first matching node will be used.
	 */
	private String nodeId = null;
	
	/** The absolute date and time when the action was recorded. */
	private String time = null;
	
	/** The timeStep when the action should be taken. */
	private int timeStep = Integer.MIN_VALUE;
	
	private IXholon contextNode = null;
	
	/**
	 * The content (XML text) of this XholonNode.
	 */
	private String content = null;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure() {
		handleContents();
		super.postConfigure();
		this.removeChild();
	}
	
	/**
	 * Handle the contents of this XholonNode.
	 * <ul>
	 * <li>Locate the node identified by nodeId and idType.</li>
	 * <li>Apply the contents of this XholonNode, to the located node, using the specified action.</li>
	 * </ul>
	 */
	protected void handleContents() {
		if (nodeId == null) {
			contextNode = this.getParentNode();
		}
		else {
			switch (idType) {
			case XHNODE_IDTYPE_XHCNAME:
				parentNode.visit(this);
				break;
			case XHNODE_IDTYPE_ROLENAME:
				parentNode.visit(this);
				break;
			case XHNODE_IDTYPE_XHCNAME_OR_ROLENAME:
				parentNode.visit(this);
				break;
			case XHNODE_IDTYPE_GETNAME:
				parentNode.visit(this);
				break;
			case XHNODE_IDTYPE_XPATH:
				contextNode = getXPath().evaluate(nodeId, this.getParentNode());
				break;
			case XHNODE_IDTYPE_ID:
				parentNode.visit(this);
				break;
			default:
				// assume default
				break;
			}
		}
		//String contents = "<Testing123/>";
		if (contextNode != null && content != null) {
			// optionally surround the content with a forest tag, to ensure it's valid XML
			StringBuilder sb = new StringBuilder();
			if (!content.startsWith(IXml2Xholon.XML_FOREST) && !content.startsWith("<script ")) {
				sb.append("<_-.forest>")
				.append(content)
				.append("</_-.forest>");
			}
			else {
				sb.append(content);
			}
			IXholon helper = getService(IXholonService.XHSRV_XHOLON_HELPER);
			helper.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, sb.toString(), contextNode);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#visit(org.primordion.xholon.base.IXholon)
	 */
	public boolean visit(IXholon visitee) {
		switch (idType) {
		case XHNODE_IDTYPE_XHCNAME:
			if (nodeId.equals(visitee.getXhcName())) {
				contextNode = visitee;
				return false;
			}
			break;
		case XHNODE_IDTYPE_ROLENAME:
			if (nodeId.equals(visitee.getRoleName())) {
				contextNode = visitee;
				return false;
			}
			break;
		case XHNODE_IDTYPE_XHCNAME_OR_ROLENAME:
			if (nodeId.equals(visitee.getXhcName()) || nodeId.equals(visitee.getRoleName())) {
				contextNode = visitee;
				return false;
			}
			break;
		case XHNODE_IDTYPE_GETNAME:
			if (nodeId.equals(visitee.getName())) {
				contextNode = visitee;
				return false;
			}
			break;
		case XHNODE_IDTYPE_ID:
			if (nodeId.equals(visitee.getId())) {
				contextNode = visitee;
				return false;
			}
			break;
		default:
			break;
		}
		return true;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrVal) {
		if ("nodeId".equals(attrName)) {nodeId = attrVal;}
		else if ("idType".equals(attrName)) {idType = Integer.parseInt(attrVal);}
		else if ("action".equals(attrName)) {action = attrVal;}
		else if ("app".equals(attrName)) {nodezApp = attrVal;}
		else if ("time".equals(attrName)) {time = attrVal;}
		else if ("timeStep".equals(attrName)) {timeStep = Integer.parseInt(attrVal);}
		else {
			return super.setAttributeVal(attrName, attrVal);
		}
		return 0;
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(java.lang.String)
	 */
	public void setVal(String val) {
		content = val;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_String()
	 */
	public String getVal_String() {
		return content;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodezApp() {
		return nodezApp;
	}

	public void setNodezApp(String nodezApp) {
		this.nodezApp = nodezApp;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getTimeStep() {
		return timeStep;
	}

	public void setTimeStep(int timeStep) {
		this.timeStep = timeStep;
	}
	
}
