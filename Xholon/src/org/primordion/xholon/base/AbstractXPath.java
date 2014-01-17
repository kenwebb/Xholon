/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for XPath implementations used in Xholon.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on February 2, 2013)
 */
public abstract class AbstractXPath extends Xholon implements IXPath {
	private static final long serialVersionUID = 2963976821200741490L;

	/*
	 * @see org.primordion.xholon.base.IXPath#evaluate(java.lang.String, java.lang.Object)
	 */
	public String evaluate(String expression, Object item)
	{
		return (String)evaluate(expression, item, XPathConstants.STRING);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXPath#evaluate(java.lang.String, java.lang.Object, javax.xml.namespace.QName)
	 */
	public Object evaluate(String expression, Object item, int returnType)
	{
		if (item.getClass() == IXholon.class) {
			switch (returnType) {
			case XPathConstants.IXHOLON:
				return evaluate(expression, (IXholon)item);
			case XPathConstants.NODE:
				return evaluate(expression, (IXholon)item);
			case XPathConstants.NODESET:
				return null;
			case XPathConstants.IXHOLONLIST:
			{
				List<IXholon> allList = new ArrayList<IXholon>();
				List<IXholon> pathList = new ArrayList<IXholon>();
				IXholon node = evaluate(expression, (IXholon)item, pathList);
				allList.add(node);
				// TODO search for additional matching nodes, and add these to allList
				return allList;
			}
			case XPathConstants.NUMBER:
				return evaluate(expression, (IXholon)item).getVal();
			case XPathConstants.STRING:
				return evaluate(expression, (IXholon)item).getName();
			case XPathConstants.BOOLEAN:
			{
				if (evaluate(expression, (IXholon)item) == null) {
					return Boolean.FALSE;
				}
				else {
					return Boolean.TRUE;
				}
			}
			case XPathConstants.IXHOLONPATH:
			{
				List<IXholon> pathList = new ArrayList<IXholon>();
				evaluate(expression, (IXholon)item, pathList);
				return pathList;
			}
			default:
				return null;
			}
		}
		else {
			return null;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXPath#evaluate(java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public IXholon evaluate(final String expression, final IXholon item)
	{
		return evaluate(expression, item, null);
	}
	
}
