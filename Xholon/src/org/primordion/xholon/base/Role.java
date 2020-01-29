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

package org.primordion.xholon.base;

/**
 * A role is a node that can store a roleName.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on April 4, 2009)
 */
public class Role extends Xholon {
	private static final long serialVersionUID = -7829492100818122122L;
	
	// Note: it's possible that the parent node does not have a roleName attribute, and therefore requires this Role node to function properly
	private static final String ACTION_RETAIN = "retain"; // do nothing; retain this Role node as is
	private static final String ACTION_REPLACE = "replace"; // replace the parent roleName unconditionally, and remove this
	private static final String ACTION_REPLACE_IF = "replaceif"; // if the parent roleName is currently not set, then replace the parent roleName and remove this
	private static final String ACTION_REMOVE = "remove"; // quietly remove this Role node
	private static final String ACTION_COMBINE = "combine"; // combine the parent roleName and this Role rolename ex: "D" + "Dog" becomes "D Dog"
	private static final String ACTION_COMBINE_SEP = " "; // separator between two parts of a combined roleName
	private static final String ACTION_DEFAULT = ACTION_RETAIN;
	
	private String action = ACTION_DEFAULT;
	
	private String actionCombineSep = ACTION_COMBINE_SEP;
	
	/**
	 * The name of a role that a Xholon plays, within the context of its parent.
	 */
	private String roleName = null;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getRoleName()
	 */
	public String getRoleName()
	{
		return roleName;
	}
	
	/**
	 * Get the full name of this Java class.
	 * @return The full name including the path: org.primordion.xholon.base.Role
	 */
	public static String getImplName()
	{
		return Role.class.getName();
	}
	
	@Override
	public int setAttributeVal(String attrName, String attrVal) {
		if ("action".equals(attrName)) {
			if (attrVal.startsWith(ACTION_COMBINE)) {
				this.action = ACTION_COMBINE; // "combine"
				String[] arr = attrVal.split(",");
				if (arr.length >= 2) {
					this.actionCombineSep = arr[1]; // ex: "." or " "
				}
				else if (attrVal.endsWith(",")) {
					this.actionCombineSep = ""; // a zero-length String
				}
			}
			else {
				this.action = attrVal;
			}
		}
		return 0;
	}
	
	@Override
	public void postConfigure() {
		boolean shouldRemove = false;
		switch (action) {
		case ACTION_REPLACE:
		{
			IXholon pnode = this.getParentNode();
			if (pnode != null) {
				pnode.setRoleName(this.roleName);
			}
			shouldRemove = true;
			break;
		}
		case ACTION_REPLACE_IF:
		{
			IXholon pnode = this.getParentNode();
			if (pnode != null) {
			  String prname = pnode.getRoleName();
			  // if (prname.equals(this.roleName)), it may be because the parent has called this Role node to get the roleName, so set it just to be sure
				if ((prname == null) || (prname.equals(this.roleName))) {
					pnode.setRoleName(this.roleName);
				}
			}
			shouldRemove = true;
			break;
		}
		case ACTION_REMOVE:
			shouldRemove = true;
			break;
		case ACTION_COMBINE:
		{
			IXholon pnode = this.getParentNode();
			if (pnode != null) {
			  String prname = pnode.getRoleName();
			  // if (prname.equals(this.roleName)), it may be because the parent has called this Role node to get the roleName, so set it just to be sure
				if ((prname == null) || (prname.equals(this.roleName))) {
					pnode.setRoleName(this.roleName);
				}
				else {
					pnode.setRoleName(prname + actionCombineSep + this.roleName);
				}
			}
			shouldRemove = true;
			break;
		}
		case ACTION_RETAIN:
		default:
			break;
		}
		super.postConfigure();
		if (shouldRemove) {
		  // this has to be done at the end so as not to interfere with next siblings
			this.removeChild();
		}
	}
	
}
