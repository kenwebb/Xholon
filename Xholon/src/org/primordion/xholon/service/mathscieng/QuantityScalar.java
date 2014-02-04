/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2011 Ken Webb
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

//import javax.measure.unit.Unit;
import org.primordion.xholon.service.mathscieng.Unit;

//import org.jscience.physics.amount.Amount;
import org.primordion.xholon.service.mathscieng.Amount;

//import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.IQuantity;

/**
 * QuantityScalar directly encapsulates an instance of org.jscience.physics.amount.Amount.
 * Typically, an instance of QuantityScalar is created with an XML String.
 * For example:
<pre>
&lt;Albedo>0.3&lt;/Albedo> &lt;!-- Dimensionless with no unit -->
&lt;Energy>0.0 J&lt;/Energy> &lt;!-- Energy in Joules -->
&lt;Length>1.0 km&lt;/Length> &lt;!-- Length in kilometers -->
&lt;Length>200.0 m&lt;/Length> &lt;!-- Length in meters -->
&lt;Length>120.0 mi&lt;/Length> &lt;!-- Length in miles -->
&lt;Mass>123.4 kg&lt;/Mass> &lt;!-- Mass in kilograms -->
&lt;Temperature>273.15 K&lt;/Temperature> &lt;!-- temperature in degrees Kelvin -->
&lt;Celsius>0.0 ℃&lt;/Celsius> &lt;!-- temperature in degrees Celsius -->
&lt;Density>1000.0 kg/m^3&lt;/Density>
&lt;SpecificHeat>4218.0 J/kg/K&lt;/SpecificHeat>
&lt;Depth>50.0 m&lt;/Depth>
&lt;HeatCapacity>0.0 J/m^3/K&lt;/HeatCapacity>
</pre>
 * Script to add a value to an existing Amount:
<pre>
&lt;script implName="lang:javascript:inline:">&lt;![CDATA[
var cnode = contextNodeKey;
cnode.setVal(cnode.getVal() + 1.0);
println(cnode.getVal());
]]>&lt;/script>
</pre>
 * Script to show all properties of a Quantity:
<pre>
&lt;script implName="lang:javascript:inline:">&lt;![CDATA[
var cnode = contextNodeKey;
println('amount:' + cnode.getAmount());
println('val_Object:' + cnode.getVal_Object());
println('val_String:' + cnode.getVal_String());
println('val:' + cnode.getVal());
println('val_long:' + cnode.getVal_long());
println('val_int:' + cnode.getVal_int());
println('unit:' + cnode.getUnit());
println('standardUnit:' + cnode.getStandardUnit());
println('dimension:' + cnode.getDimension());
]]>&lt;/script>
</pre>
 * The XML String must include both a numeric value and a unit, separated by a space.
 * If it's a Dimensionless quantity like Albedo, then only the numeric value should be provided.
 * A Quantity cannot have more than one dimension.
 * <pre>TODO
 * Possibly assign a value of 0 units if no value was specified (ex: &lt;Mass> becomes 10 kg);
 * </pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 12, 2011)
 */
public class QuantityScalar extends Xholon implements IQuantity {

	/**
	 * The amount encapsulated by this instance of Quantity.
	 */
	protected Amount amount = null;

	/**
	 * TODO Unable to set the roleName
	 * because of the use of QuantityFactory which calls xmlReader.next()
	 */
	protected String roleName = null;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure() {
		if (amount == null) {
			String defaultContent = this.getXhc().getDefaultContent();
			if (defaultContent != null) {
				this.setVal(defaultContent);
			}
		}
		super.postConfigure();
	}
	
	/**
	 * @see org.primordion.xholon.base.Xholon#getVal_Object()
	 * @return An instance of org.jscience.physics.amount.Amount.
	 */
	public Object getVal_Object() {
		return amount;
	}
	
	/**
	 * @see org.primordion.xholon.base.Xholon#setVal(java.lang.Object)
	 * @param val It must be an instance of org.jscience.physics.amount.Amount,
	 * and not for example an instance of java.lang.Double.
	 */
	public void setVal(Object val) {
		setAmount((Amount)val);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_String()
	 */
	public String getVal_String() {
		return "" + amount;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(java.lang.String)
	 */
	public void setVal(String val) {
		setAmount(Amount.valueOf(val));
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	public double getVal() {
		//return amount.doubleValue(amount.getUnit());
		return amount.doubleValue();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(double)
	 */
	public void setVal(double val) {
		if (amount == null) {
			// assume this is a Dimensionless quantity
			setAmount(Amount.valueOf(val, Unit.ONE));
		}
		else {
			//setAmount(Amount.valueOf(val, amount.getUnit()));
			amount.setVal(val);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#incVal(double)
	 */
	public void incVal(double val) {
		setVal(getVal() + val);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#decVal(double)
	 */
	public void decVal(double val) {
		setVal(getVal() - val);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_long()
	 */
	public long getVal_long() {
		try {
			return amount.longValue(amount.getUnit());
		} catch (ArithmeticException e) {
			return Long.MIN_VALUE;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(long)
	 */
	public void setVal(long val) {
		if (amount == null) {
			// assume this is a Dimensionless quantity
			setAmount(Amount.valueOf(val, Unit.ONE));
		}
		else {
			setAmount(Amount.valueOf(val, amount.getUnit()));
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_int()
	 */
	public int getVal_int() {
		try {
			return (int)amount.longValue(amount.getUnit());
		} catch (ArithmeticException e) {
			return (int)Integer.MIN_VALUE;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(int)
	 */
	public void setVal(int val) {
		if (amount == null) {
			// assume this is a Dimensionless quantity
			setAmount(Amount.valueOf(val, Unit.ONE));
		}
		else {
			setAmount(Amount.valueOf(val, amount.getUnit()));
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IQuantity#getUnit()
	 */
	public String getUnit() {
		return amount.getUnit().toString();
	}
	
	/*
	 * @see org.primordion.xholon.base.IQuantity#getStandardUnit()
	 */
	public String getStandardUnit() {
		return amount.getUnit().getStandardUnit().toString();
	}
	
	/*
	 * @see org.primordion.xholon.base.IQuantity#getDimension()
	 */
	public String getDimension() {
		Unit unit = amount.getUnit();
		if (unit.equals(Unit.ONE)) {
			return "";
		}
		else {
			return unit.getDimension().toString();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IQuantity#getValues()
	 */
	public double[] getValues() {
		double[] a = {this.getVal()};
		return a;
	}
	
	/*
	 * @see org.primordion.xholon.base.IQuantity#getValueN(int)
	 */
	public double getValueN(int index) {
		return this.getVal();
	}
	
	/**
	 * Examples of a format element (a child of a Quantity element in XML):
	<pre>
&lt;Format>%4$s (%1$.0f %3$s) %2$s %5$s%n&lt;Format>
&lt;Format>%4$s %1$.0f %2$s %5$s%n&lt;Format>
&lt;Format>The runway must be at least %.0f meters long.&lt;Format>
&lt;Format>The runway must be at least %.0f %s long.&lt;Format></pre>where:<ul>
	<li>1$ scalar magnitude</li>
	<li>2$ units</li>
	<li>3$ relative error</li>
	<li>4$ Xholon name</li>
	<li>5$ dimensions</li>
	</ul>
	 * @see org.primordion.xholon.base.Attribute#toString()
	 */
	public String toString() {
		//String format = ((IDecoration)this.getXhc()).getFormat(); // GWT
		if (amount == null) {
			return this.getName();
		}
		/* format is only used in original Xholon in a few user/wb/khan models (GWT)
		else if ((format != null) && (format.length() > 0)) {
			return String.format(format,
					amount.doubleValue(amount.getUnit()), // 1$
					amount.getUnit().toString(),          // 2$
					amount.getRelativeError(),            // 3$
					this.getName(),                       // 4$
					this.getDimension()                   // 5$
					);
		}*/
		else {
			return this.getName() + " " + this.getAmount() + " " + this.getDimension();
		}
	}

	/**
	 * Get Amount.
	 * @return The amount encapsulated by this instance of Quantity.
	 */
	public Amount getAmount() {
		return amount;
	}

	/**
	 * Set Amount.
	 * @param amount The amount encapsulated by this instance of Quantity.
	 */
	public void setAmount(Amount amount) {
		this.amount = amount;
	}

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
	
	//public IQuantity plus(IQuantity that) {
	//	amount = amount.plus((Amount<?>)that.getVal_Object());
	//	return this;
	//}
	
}
