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

package org.primordion.xholon.service.mathscieng;

import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IQuantity;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;

/**
 * Quantity encapsulates 2 private inner classes,
 * one that handles scalars and the other that handles vectors.
 * Quantity determines at runtime which inner class to instantiate,
 * based on how many values are in the text in the XML.
 * It requires the JScience library. Examples:
<pre>
  &lt;Length>10.0 m&lt;/Length> is a scalar
  &lt;Length>10.0 20.0 m&lt;/Length> is a vector
</pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://jscience.org/">JScience</a>
 * @since 0.8.1 (Created on January 15, 2012)
 */
public class Quantity extends Xholon implements IQuantity {

	/**
	 * An instance of QuantityScalar or QuantityVector.
	 */
	protected Object instance = null;
	
	protected String roleName = null;
	
	public void postConfigure() {
		if (instance == null) {
			// probably no value was specified in the XML
			instance = new QuantityScalarInner();
		}
		((IXholon)instance).postConfigure();
		super.postConfigure();
	}
	public Object getVal_Object() {return ((IXholon)instance).getVal_Object();}
	public void setVal(Object val) {((IXholon)instance).setVal(val);}
	public String getVal_String() {return ((IXholon)instance).getVal_String();}
	
	public void setVal(String val) {
		val = val.trim();
		if (instance == null) {
			if ((val != null) && (val.length() > 0)) {
				if (val.split(" ").length > 2) {
					// ex: "1.0 2.0 m"
					//instance = new QuantityVectorInner(); // GWT
				}
				else {
					// ex: "1.0 m"
					instance = new QuantityScalarInner();
				}
			}
		}
		((IXholon)instance).setVal(val);
	}
	
	public double getVal() {return ((IXholon)instance).getVal();}
	public void setVal(double val) {((IXholon)getInstance()).setVal(val);}
	public void incVal(double val) {((IXholon)instance).incVal(val);}
	public void decVal(double val) {((IXholon)instance).decVal(val);}
	public long getVal_long() {return ((IXholon)instance).getVal_long();}
	public void setVal(long val) {((IXholon)instance).setVal(val);}
	public int getVal_int() {return ((IXholon)instance).getVal_int();}
	public void setVal(int val) {((IXholon)instance).setVal(val);}
	public String getUnit() {return ((IQuantity)instance).getUnit();}
	public String getStandardUnit() {return ((IQuantity)instance).getStandardUnit();}
	public String getDimension() {return ((IQuantity)instance).getDimension();}
	public double[] getValues() {return ((IQuantity)instance).getValues();}
	public double getValueN(int index) {return ((IQuantity)instance).getValueN(index);}
	public String toString() {return instance.toString();}
	public String getRoleName() {return roleName;}
	public void setRoleName(String roleName) {this.roleName = roleName;}
	
	public Object getInstance() {
		if (instance == null) {
			instance = new QuantityScalarInner();
		}
		return instance;
	}

	/**
	 * QuantityScalarInner handles scalar quantities.
	 */
	private class QuantityScalarInner extends QuantityScalar implements IQuantity {
		
		/*
		 * @see org.primordion.xholon.base.Xholon#postConfigure()
		 */
		public void postConfigure() {
			if (amount == null) {
				String defaultContent = ((IXholon)Quantity.this).getXhc().getDefaultContent();
				if (defaultContent != null) {
					this.setVal(defaultContent);
				}
			}
		}

		/*
		 * @see org.primordion.xholon.service.mathscieng.QuantityScalar#toString()
		 */
		@SuppressWarnings("unchecked")
		public String toString() {
			IXholon tqo = (IXholon)Quantity.this;
			//String format = ((IDecoration)tqo.getXhc()).getFormat(); // GWT
			if (amount == null) {
				return tqo.getName();
			}
			/* format is only used in original Xholon in a few user/wb/khan models (GWT)
			else if ((format != null) && (format.length() > 0)) {
				return String.format(format,
						amount.doubleValue(amount.getUnit()), // 1$
						amount.getUnit().toString(),          // 2$
						amount.getRelativeError(),            // 3$
						tqo.getName(),                        // 4$
						this.getDimension()                   // 5$
						);
			}*/
			else {
				return tqo.getName() + " " + this.getAmount() + " " + this.getDimension();
			}
		}
	
	}

	/**
	 * QuantityVectorInner handles vector quantities.
	 */
	// GWT
	/*private class QuantityVectorInner extends QuantityVector implements IQuantity {
		
		public void postConfigure() {
			if (vm == null) {
				String defaultContent = ((IXholon)Quantity.this).getXhc().getDefaultContent();
				if (defaultContent != null) {
					this.setVal(defaultContent);
				}
			}
		}
		
		@SuppressWarnings("unchecked")
		public String toString() {
			IXholon tqo = (IXholon)Quantity.this;
			String format = ((IDecoration)tqo.getXhc()).getFormat();
			if (vm == null) {
				return tqo.getName();
			}
			else if ((format != null) && (format.length() > 0)) {
				return String.format(format,
						vm.doubleValue(vm.getUnit()), // 1$
						vm.getUnit().toString(),      // 2$
						//vm.getRelativeError(),      // 3$
						tqo.getName(),                // 4$
						this.getDimension()           // 5$
						);
			}
			else {
				return tqo.getName()
					+ " " + this.getVectorMeasure()
					+ " ||" + this.getVal() + "|| "
					+ this.getDimension();
			}
		}

	}*/
	
}
