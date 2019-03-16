/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2008 Ken Webb
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

import java.util.Iterator;
import java.util.List;

import org.primordion.xholon.util.ClassHelper;
import org.primordion.xholon.util.Misc;

/**
 * <p>Provides information about a specific port</p>
 * <ul>
 * <li>Field name</li>
 * <li>Field name array index (optional)</li>
 * <li>The IXholon node that is referenced by this field</li>
 * </ul>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on June 1, 2008)
 */
public class PortInformation extends Xholon {
	
	public static final int PORTINFO_NOTANARRAY = -1;
	public static final String PORTINFO_NOTANARRAY_STR = String.valueOf(PORTINFO_NOTANARRAY);
	
	private String fieldName = null;
	private int fieldNameIndex = PORTINFO_NOTANARRAY;
	private String fieldNameIndexStr = null;
	private IXholon reffedNode = null;
	private String xpathExpression = null;
	
	// optional IPort values
	private boolean iport = false; // whether or not this is an IPort port, and whether or not the other 4 iport variables have any meaning
	private int iportMultiplicity = 0; // 1
	private boolean iportIsConjugated = false;
	private String iportProvidedInterfaceNames = null; // "100" "" "101,102"
	private String iportRequiredInterfaceNames = null; // "100" "" "101,102"
	
	/**
	 * Should toString() return the remote IXholon referenced by IPort replications.
	 * true - follow the chain and return the remote IXholon
	 * false - just return the IPort
	 */
	private boolean shouldReturnIPortRemote = true;
	
	public PortInformation(String fieldName, IXholon reffedNode) {
		this.fieldName = fieldName;
		this.fieldNameIndex = PORTINFO_NOTANARRAY;
		this.fieldNameIndexStr = null;
		this.reffedNode = reffedNode;
	}
	
	public PortInformation(String fieldName, int fieldNameIndex, IXholon reffedNode) {
		this.fieldName = fieldName;
		this.fieldNameIndex = fieldNameIndex;
		this.fieldNameIndexStr = String.valueOf(fieldNameIndex);
		this.reffedNode = reffedNode;
	}
	
	/**
	 * This constructor is specifically designed for use by IXholonClass,
	 * but can be used for other purposes as well.
	 * @param fieldName
	 * @param fieldNameIndex
	 * @param reffedNode If used by IXholonClass, this should typically be null.
	 * @param xpathExpression
	 */
	public PortInformation(String fieldName, int fieldNameIndex, IXholon reffedNode, String xpathExpression) {
		this.fieldName = fieldName;
		this.fieldNameIndex = fieldNameIndex;
		this.fieldNameIndexStr = String.valueOf(fieldNameIndex);
		this.reffedNode = reffedNode;
		this.xpathExpression = xpathExpression;
	}
	
	/**
	 * This constructor is specifically designed for use by IXholonClass,
	 * but can be used for other purposes as well.
	 * @param fieldName
	 * @param fieldNameIndexStr
	 * @param reffedNode If used by IXholonClass, this should typically be null.
	 * @param xpathExpression
	 */
	public PortInformation(String fieldName, String fieldNameIndexStr, IXholon reffedNode, String xpathExpression) {
		this.fieldName = fieldName;
		// fieldNameIndexStr may contain a number or a String value (ex: 1 P_PARTNER)
		this.fieldNameIndex = Misc.atoi(fieldNameIndexStr, 0); // 0 if it's a String
		this.fieldNameIndexStr = fieldNameIndexStr;
		this.reffedNode = reffedNode;
		this.xpathExpression = xpathExpression;
	}
	
	/**
	 * A List<PortInformation>, as returned by XholonClass,
	 * may contain multiple scalar ports along with multiple array ports.
	 * This method finds the number of items in one named array port,
	 * as contained within a List<PortInformation>.
	 * @param piList A list of PortInformation instances, some of which may be array ports.
	 * @param fn The field name of an array port, an IXholon[]
	 * @return A count >= 0.
	 */
	public static int findArraySize(List<PortInformation> piList, String fn) {
		if (fn == null) {return 0;}
		Iterator<PortInformation> piIt = piList.iterator();
		int count = 0;
		while (piIt.hasNext()) {
			if (fn.equals(piIt.next().getFieldName())) {
				count++;
			}
		}
		return count;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getFieldNameIndex() {
		return fieldNameIndex;
	}
	
	public void setFieldNameIndex(int fieldNameIndex) {
		this.fieldNameIndex = fieldNameIndex;
	}
	
	public String getFieldNameIndexStr() {
		return fieldNameIndexStr;
	}

	public void setFieldNameIndexStr(String fieldNameIndexStr) {
		this.fieldNameIndexStr = fieldNameIndexStr;
	}

	public IXholon getReffedNode() {
		return reffedNode;
	}
	
	public void setReffedNode(IXholon reffedNode) {
		this.reffedNode = reffedNode;
	}

	public String getXpathExpression() {
		return xpathExpression;
	}

	public void setXpathExpression(String xpathExpression) {
		this.xpathExpression = xpathExpression;
	}

	public boolean isShouldReturnIPortRemote() {
		return shouldReturnIPortRemote;
	}

	public void setShouldReturnIPortRemote(boolean shouldReturnIPortRemote) {
		this.shouldReturnIPortRemote = shouldReturnIPortRemote;
	}

	/**
	 * Get the local name of the port.
	 * @return Field name with optional index.
	 * ex: "port[0]" or "hello" or "whatever[4]"
	 */
	public String getLocalName() {
		String str = fieldName;
		if (fieldNameIndex != PORTINFO_NOTANARRAY) {
			str += "[" + fieldNameIndex + "]";
		}
		return str;
	}
	
	/**
	 * Get the local name of the port.
	 * @return Field name with optional index.
	 * ex: "port0" or "hello" or "whatever4"
	 */
	public String getLocalNameNoBrackets() {
		String str = fieldName;
		if (fieldNameIndex != PORTINFO_NOTANARRAY) {
			str += fieldNameIndex;
		}
		return str;
	}
	
	/**
	 * Call this after the constructor, if the port is an IPort.
	 */
	public void setIportValues(int iportMultiplicity, boolean iportIsConjugated, String iportProvidedInterfaceNames, String iportRequiredInterfaceNames) {
	  this.iport = true;
	  this.iportMultiplicity = iportMultiplicity;
	  this.iportIsConjugated = iportIsConjugated;
	  this.iportProvidedInterfaceNames = iportProvidedInterfaceNames;
	  this.iportRequiredInterfaceNames = iportRequiredInterfaceNames;
	}
	
	public boolean isIport() {return this.iport;}
	public int getIportMultiplicity() {return this.iportMultiplicity;}
	public boolean isIportIsConjugated() {return this.iportIsConjugated;}
	public String getIportProvidedInterfaceNames() {return iportProvidedInterfaceNames;}
	public String getIportRequiredInterfaceNames() {return iportRequiredInterfaceNames;}
	
	/**
	 * This is for use by the XholonJsApi.
	 * A JavaScript receiver of a PortInformation object can call this method
	 * to get a JavaScript object with 5 properties.
	 * ex: kenny.ports()[0].getVal_Object().reffedNode.toString();
	 */
	public Object getVal_Object() {
	  return piAsJso(fieldName, fieldNameIndex, fieldNameIndexStr, reffedNode, xpathExpression,
	      iport, iportMultiplicity, iportIsConjugated, iportProvidedInterfaceNames, iportRequiredInterfaceNames);
	}
	
	protected native Object piAsJso(String fieldName, int fieldNameIndex,
	    String fieldNameIndexStr, IXholon reffedNode, String xpathExpression,
	        boolean iport, int iportMultiplicity, boolean iportIsConjugated, String iportProvidedInterfaceNames, String iportRequiredInterfaceNames) /*-{
	  var pi = new Object();
	  pi.fieldName         = fieldName;
	  pi.fieldNameIndex    = fieldNameIndex;
	  pi.fieldNameIndexStr = fieldNameIndexStr;
	  pi.reffedNode        = reffedNode;
	  pi.xpathExpression   = xpathExpression;
	  if (iport) {
	    pi.iport = true;
	    pi.iportMultiplicity = iportMultiplicity;
	    pi.iportIsConjugated = iportIsConjugated;
	    pi.iportProvidedInterfaceNames = iportProvidedInterfaceNames;
	    pi.iportRequiredInterfaceNames = iportRequiredInterfaceNames;
	  }
	  else {
	    pi.iport = false;
	  }
	  return pi;
	}-*/;
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String str = getLocalName();
		if (this.iport == true) {
		  str += " iport";
		}
		
		if (reffedNode != null) {
			str += ":";
			if (shouldReturnIPortRemote && (ClassHelper.isAssignableFrom(Port.class, reffedNode.getClass()))) {
				IPort iiport = (IPort)reffedNode;
				IXholon remote = iiport.getLink();
				if (remote != null) {
					if (ClassHelper.isAssignableFrom(Port.class, remote.getClass())) {
						remote = remote.getParentNode().getParentNode();
					}
					str += remote.getName();
				}
			}
			else {
				str += reffedNode.getName();
			}
		}
		if (xpathExpression != null) {
			str += " " + xpathExpression;
		}
		return str;
	}

}
