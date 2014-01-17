package org.primordion.user.app.petrinet.feinberg1;

import org.primordion.xholon.base.XholonWithPorts;

/**
 * Default Java class of Xholon classes that do not specify a Java class.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 27, 2012)
*/
public class Xhfeinberg1 extends XholonWithPorts implements Cefeinberg1 {
	
	public String roleName = null;
	
	// Constructor
	public Xhfeinberg1() {super();}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName) {this.roleName = roleName;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 */
	public String getRoleName() {return roleName;}
	
	public void act()
	{
		switch(xhc.getId()) {
		case ReactionNetworkSystemCE:
			processMessageQ();
			break;
		default:
			break;
		}
		super.act();
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		String outStr = getName();
		return outStr;
	}
}
