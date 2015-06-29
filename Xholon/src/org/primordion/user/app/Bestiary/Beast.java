package org.primordion.user.app.Bestiary;

import java.util.Iterator;
import java.util.List;

import org.primordion.xholon.base.IQueue;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Beast
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
 */
public class Beast extends XholonWithPorts implements CeBestiary {
	
	/** Default color, color of new beasts such as Dog. */
	//private static final int COLOR_DEFAULT = 0xFF00FF; //Color.MAGENTA;
	
	/**
	 * Color designation, for use in GUIs.
	 * ex: "0xFF0000"
	 */
	//private int color = COLOR_DEFAULT;
	
	private String roleName = null;
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	//public int getColor() {
	//	return color;
	//}

	//public void setColor(int color) {
	//	this.color = color;
	//}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getAllPorts()
	 */
	public List getAllPorts() {
		List portList = this.getXhc().getPortInformation();
		// eval the XPath expressions to determine the reffed nodes
		Iterator portIt = portList.iterator();
		while (portIt.hasNext()) {
			PortInformation pi = (PortInformation)portIt.next();
			IXholon reffedNode = this.getXPath().evaluate(pi.getXpathExpression(), this);
			if (reffedNode == null) {
				portList.remove(pi);
			}
			else {
				pi.setReffedNode(reffedNode);
			}
		}
		return portList;
	}

	/*
	 * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
	 */
	public void postConfigure() {
		if ("HabitatCell".equals(getParentNode().getXhcName())) {
			//System.out.println(this + " Beast.postConfigure()");
			IXholon q = getXPath().evaluate("ancestor::Bestiary/Beasts", this);
			((IQueue)q).enqueue(this);
		}
		super.postConfigure();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#doAction(java.lang.String)
	 */
	public void doAction(String action)
	{
		// give all children and other descendants a chance to handle the action
		IXholon node = getFirstChild();
		while (node != null) {
			node.doAction(action);
			node = node.getNextSibling();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		String outStr = getName();
		return outStr;
	}
	
}
