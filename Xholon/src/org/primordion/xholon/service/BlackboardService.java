/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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

package org.primordion.xholon.service;

import org.primordion.xholon.base.IXholon;

/**
 * Blackboard Service.
 * This service maintains a simple Map that can be used by any node to store,
 * retrieve, and exchange data.
 * Each item of data in the blackboard is identified by a String agreed upon
 * between the users of this service.
 * <p>Example:</p>
 * 
 * <pre>
// store
Map<String,String> blackboardService = (Map<String,String>)this.getService(IXholonService.XHSRV_BLACKBOARD);
blackboardService.put("SbgnPlaceCloneList", "Atp,Adp");
// retrieve
Map<String,String> blackboardService = (Map<String,String>)root.getService(IXholonService.XHSRV_BLACKBOARD);
String cl = blackboardService.get("SbgnPlaceCloneList");
 * </pre>
 * 
 * <p>XholonJsApi example (using Chrome Developer Tools):</p>
 * <pre>
var bs = xh.service("BlackboardService");
console.log(bs.name()); //xholonMap_21
console.log(bs.toString()); //"xholonMap_21 size:0"
console.log(bs.obj()); //[]
bs.append('<Attribute_String roleName="One">data1</Attribute_String>');
bs.append('<Attribute_String roleName="Two">data2</Attribute_String>');
console.log(bs.toString()); //"xholonMap_21 size:2"
console.log(bs.obj()); //[One: "data1", Two: "data2"]
console.log(bs.first().name());
console.log(bs.first().role()); //"One"
console.log(bs.first().text()); //"data1"
console.log(bs.get_213_g$("One")); //"data1"
console.log(bs.first().next().role()); //"Two"
console.log(bs.first().next().text()); //"data2"
 * </pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on August 6, 2012)
 */
public class BlackboardService extends AbstractXholonService {
	private static final long serialVersionUID = 2507897516624143580L;

	/*
	 * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		if (serviceName.startsWith(getXhcName())) {
			return this.getFirstChild();
		}
		else {
			return null;
		}
	}
	
}
