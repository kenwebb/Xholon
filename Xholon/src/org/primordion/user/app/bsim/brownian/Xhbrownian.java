package org.primordion.user.app.bsim.brownian;

import org.primordion.xholon.base.XholonWithPorts;

/**
 * Default Java class of Xholon classes that do not specify a Java class.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://cellsimulationlabs.github.io/tools/bsim/">BSim website</a>
 * @since 0.9.1 (Created on May 25, 2020)
*/
public class Xhbrownian extends XholonWithPorts implements Cebrownian {
	
	public String roleName = null;
	
	// Constructor
	public Xhbrownian() {super();}
	
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
		case BrownianSystemCE:
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
