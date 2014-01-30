/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2010 Ken Webb
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
 * This is the abstract base class for any class that will function as both:
 * <ul>
 * <li>a Java anonymous inner class, and</li>
 * <li>a Xholon.</li>
 * </ul>
 * The class will be created by the compiler, and instantiated at runtime in
 * the normal way for anonymous inner classes. This base class will then
 * transform it into a node that can be part of a Xholon tree.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on Jan 23, 2010)
 */
public abstract class XholonAnonymous extends Xholon {
	private static final long serialVersionUID = 1170829560686826751L;
	
	private String roleName = null;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getRoleName()
	 */
	public String getRoleName() {
		return roleName;
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * default constructor
	 */
	public XholonAnonymous()
	{
		super();
		appendChild();
		initializeXholonAttributes();
	}
	
	/**
	 * Append this instance of an anonymous inner class as a Xholon child of some other Xholon node.
	 * This other node will be a synthetic field with a name such as this$1 , for example:
	 * <pre>final synthetic org.primordion.user.app.InnerClass.TestInnerClass$1 this$1;</pre>
	 */
	protected void appendChild() {
		IReflection ir = ReflectionFactory.instance();
		IXholon myParent = ir.getAttributeSynthetic(this);
		if (myParent != null) {
			this.appendChild(myParent);
		}
	}
	
	/**
	 * Initialize this instance of IXholon, with some of the attributes required of an IXholon.
	 */
	protected void initializeXholonAttributes()
	{
		Class clazz = getClass().getSuperclass();
		String className = clazz.getName();
		IXholonClass xholonClass = getClassNode(className.substring(className.lastIndexOf('.') + 1));
		while (xholonClass == null) {
			clazz = clazz.getSuperclass();
			if (clazz == null) {break;}
			className = clazz.getName();
			xholonClass = getClassNode(className.substring(className.lastIndexOf('.') + 1));
		}
		setXhc(xholonClass);
		setId(getNextId());
	}
	
}
