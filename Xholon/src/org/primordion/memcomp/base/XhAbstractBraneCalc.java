/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2006, 2007, 2008 Ken Webb
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

package org.primordion.memcomp.base;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.exception.XholonConfigurationException;

/**
 * Brane Calculus.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Jan 14, 2006)
 */
public class XhAbstractBraneCalc extends XholonWithPorts {

	public static final int P_MEMBRANEP = 0;
	public static final int P_MEMBRANEQ = 1;
	public static final int P_PATCH = 2;
	
	public String roleName = null;
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		roleName = null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 */
	public String getRoleName()
	{
		return roleName;
	}
	
	/**
	 * Phago - Phagocytosis
	 * <p>Membrane P with its internal contents,
	 * and part of the fluid environment that surrounds membrane P,
	 * are engulfed by membrane Q.</p>
	 * @param membraneP An independent membrane which will be taken inside membraneQ.
	 * @param membraneQ An independent membrane that will take membraneP and patchS inside it.
	 * @param patchS A patch of membraneP.
	 */
	public void phago(IXholon membraneP, IXholon membraneQ, IXholon patchS)
	{
		IXholon newMembrane = ((XhAbstractBraneCalc)membraneQ).cophago(patchS);
		((XhAbstractBraneCalc)membraneP).phago(newMembrane);
		// remove the action from the tree, and return to factory
		removeChild();
		remove();
	}
	
	/**
	 * CoPhago - The phago complementary co-action.
	 * @param patch A patch of membrane.
	 * @return A new membrane.
	 */
	protected IXholon cophago(IXholon patch)
	{
		IXholon newMembrane = createObject("Membrane");
		newMembrane.appendChild(this);
		// new subsystem
		IXholon newSubsystem = createObject("Subsystem");
		String rName = getXPath().evaluate("Subsystem", this).getRoleName();
		if (rName.endsWith("_black")) {
			newSubsystem.setRoleName("_white");
		}
		else {
			newSubsystem.setRoleName("_black");
		}
		newSubsystem.appendChild(newMembrane);
		// move patch
		patch.removeChild();
		patch.appendChild(newMembrane);
		return newMembrane;
	}
	
	/**
	 * Phago - Make this membrane a child of parentMembrane.
	 * @param parentMembrane The parent membrane.
	 */
	protected void phago(IXholon parentMembrane)
	{
		removeChild();
		appendChild(parentMembrane);
	}
	
	/**
	 * Exo - Exocytosis
	 * <p>The contents of membrane P are expelled to the fluid environment surrounding membrane Q,
	 * while membrane P itself becomes part of membrane Q.</p>
	 * @param membraneP The initially internal membrane.
	 * @param membraneQ The initially external membrane.
	 */
	public void exo(IXholon membraneP, IXholon membraneQ)
	{
		((XhAbstractBraneCalc)membraneQ).coexo(membraneP);
		((XhAbstractBraneCalc)membraneP).exo();
		// remove the action from the tree, and return to factory
		removeChild();
		remove();
		// return the no-longer existent membrane to the factory; the 2 membranes have merged
		membraneP.remove();
	}
	
	/**
	 * Remove this membrane, and all of its contents, from the tree.
	 */
	protected void exo()
	{
		removeChild();
	}
	
	/**
	 * CoExo - The exo complementary co-action.
	 * @param childMembrane A child membrane.
	 */
	protected void coexo(IXholon childMembrane)
	{
		IXholon node = childMembrane.getFirstChild();
		IXholon nextNode = null;
		while (node != null) {
			nextNode = node.getNextSibling(); // get next node before sibling link is broken
			if (node.getXhcName().equals("Patch")) {
				node.removeChild();
				node.appendChild(this);
			}
			else if (node.getXhcName().equals("Subsystem")) {
				node.removeChild();
				node.appendChild(parentNode);
			}
			else if (node.getXhcName().equals("Membrane")) {
				node.removeChild();
				node.appendChild(parentNode);
			}
			node = nextNode;
		}
	}
	
	/**
	 * Pino - Pinocytosis.
	 * <p>Membrane P engulfs part of the surrounding environment.</p>
	 * @param membrane Membrane P.
	 * @param patch A patch of membrane.
	 */
	public void pino(IXholon membraneP, IXholon patchS)
	{
		((XhAbstractBraneCalc)membraneP).pino(patchS);
		// remove the action from the tree, and return to factory
		removeChild();
		remove();
	}
	
	/**
	 * Create a new membrane to encircle some fluid.
	 * @param patch A patch of membrane.
	 * @return A new membrane.
	 */
	protected IXholon pino(IXholon patch)
	{
		IXholon newMembrane = createObject("Membrane");
		newMembrane.appendChild(this);
		// new subsystem
		IXholon newSubsystem = createObject("Subsystem");
		String rName = getXPath().evaluate("Subsystem", this).getRoleName();
		if (rName.endsWith("_black")) {
			newSubsystem.setRoleName("_white");
		}
		else {
			newSubsystem.setRoleName("_black");
		}
		newSubsystem.appendChild(newMembrane);
		// move patch
		patch.removeChild();
		patch.appendChild(newMembrane);
		return newMembrane;
	}
	
	/**
	 * Mate - Merge two membranes.
	 * @param membraneP A membrane.
	 * @param membraneQ Another membrane.
	 * @param patchS A patch of membrane.
	 */
	public void mate(IXholon membraneP, IXholon membraneQ, IXholon patchS)
	{
		// do phago
		IXholon newMembrane = ((XhAbstractBraneCalc)membraneQ).cophago(patchS);
		((XhAbstractBraneCalc)membraneP).phago(newMembrane);
		// do exo first time
		((XhAbstractBraneCalc)membraneP).exo();
		((XhAbstractBraneCalc)newMembrane).coexo(membraneP);
		// return the no-longer existent membrane to the factory; the 2 membranes have merged
		membraneP.remove();
		// do exo second time
		((XhAbstractBraneCalc)newMembrane).exo();
		((XhAbstractBraneCalc)membraneQ).coexo(newMembrane);
		// return the no-longer existent membrane to the factory; the 2 membranes have merged
		newMembrane.remove();
		// remove the action from the tree, and return to factory
		removeChild();
		remove();
	}
	
	/**
	 * Bud - Split off an inner membrane.
	 * @param membraneP An initially inner membrane.
	 * @param membraneQ An initially outer membrane.
	 * @param patchS A patch of membrane.
	 */
	public void bud(IXholon membraneP, IXholon membraneQ, IXholon patchS)
	{
		// do pino
		IXholon newMembrane1 = ((XhAbstractBraneCalc)membraneQ).pino(patchS);
		// do phago
		IXholon newMembrane2 = ((XhAbstractBraneCalc)newMembrane1).cophago(patchS); // patchS ???
		((XhAbstractBraneCalc)membraneP).phago(newMembrane2);
		// do exo
		((XhAbstractBraneCalc)membraneQ).coexo(newMembrane1);
		((XhAbstractBraneCalc)newMembrane1).exo();
		// return the no-longer existent membrane to the factory; the 2 membranes have merged
		newMembrane1.remove();
		// remove the action from the tree, and return to factory
		removeChild();
		remove();
	}
	
	/**
	 * Drip - Split off zero inner membranes.
	 * @param membraneP A membrane.
	 * @param patchS A patch of membrane.
	 */
	public void drip(IXholon membraneP, IXholon patchS)
	{
		// do first pino
		IXholon newMembrane1 = ((XhAbstractBraneCalc)membraneP).pino(patchS);
		// do second pino
		((XhAbstractBraneCalc)newMembrane1).pino(patchS);
		// do exo
		((XhAbstractBraneCalc)membraneP).coexo(newMembrane1);
		((XhAbstractBraneCalc)newMembrane1).exo();
		// return the no-longer existent membrane to the factory; the 2 membranes have merged
		newMembrane1.remove();
		// remove the action from the tree, and return to factory
		removeChild();
		remove();
	}
	
	/**
	 * Create a new xholon object.
	 * @param className The name of the class which the new xholon will be an instance of.
	 * @return The new initialized xholon.
	 */
	protected IXholon createObject(String className)
	{
		IXholon newNode = null;
		try {
			newNode = getFactory().getXholonNode();
		} catch (XholonConfigurationException e) {
			logger.error(e.getMessage(), e.getCause());
			return null;
		}
		newNode.setId(getNextId());
		newNode.setXhc(getClassNode(className));
		return newNode;
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		if (xhc.hasAncestor("Action")) {
			if ((port != null) && (port.length > 0)) {
				outStr += " [";
				for (int i = 0; i < port.length; i++) {
					if (port[i] != null) {
						outStr += " port:" + port[i].getName();
					}
				}
				outStr += "]";
			}
		}
		return outStr;
	}
}
