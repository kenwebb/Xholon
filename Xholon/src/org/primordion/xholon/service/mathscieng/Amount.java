package org.primordion.xholon.service.mathscieng;

/**
 * This is a very simplistic replacement for the JScience org.jscience.physics.amount.Amount class.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on September 10, 2013)
 */
public class Amount {
  
  protected double val = 0.0;
  
  protected Unit unit = Unit.ONE; //null;
  
  public static Amount valueOf(double val, Unit unit) {
    Amount amount = new Amount();
    amount.val = val;
    amount.unit = unit;
    return amount;
  }
  
  public static Amount valueOf(int val, Unit unit) {
    return Amount.valueOf((double)val, unit);
  }
  
  // this is a combo of val and unit (ex: "123 m").
  public static Amount valueOf(String strVal) {
    Amount amount = new Amount();
    String[] strArr = strVal.split(" ");
    amount.val = Double.parseDouble(strArr[0]);
    if (strArr.length > 1) {
      amount.unit = new Unit(strArr[1]);
    }
    return amount;
  }
  
  public Unit getUnit() {
    return unit;
  }
  
  public double doubleValue(Unit unit) {
    return val;
  }
  
  // better performance with Xholon, where units aren't required for computation
  public double doubleValue() {
    return val;
  }
  
  public long longValue(Unit unit) {
    return (long)val;
  }
  
  // better performance with Xholon, where units aren't required for computation
  public long longValue() {
    return (long)val;
  }
  
  // better performance with Xholon, where units aren't required for computation
  public void setVal(double val) {
    this.val = val;
  }
  
  // better performance with Xholon, where units aren't required for computation
  public void setVal(long val) {
    this.val = (double)val;
  }
  
  public String toString() {
    String outStr = "" + val;
    if (unit != null) {
      outStr += " " + unit.toString();
    }
    return outStr;
  }
  
}
