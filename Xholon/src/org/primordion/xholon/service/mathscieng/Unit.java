package org.primordion.xholon.service.mathscieng;

/**
 * This is a very simplistic replacement for the JScience javax.measure.unit.Unit class.
 * All it does is encapsulate a single String, the name of the unit.
 * It also provides dummy versions of functionality required by other classes in Xholon.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on September 10, 2013)
 */
public class Unit {
  
  /**
   * Holds the dimensionless unit <code>ONE</code>.
   */
  public static final Unit ONE = new Unit("");
  
  protected String val = null;
  
  public Unit(String val) {
    this.val = val;
  }
  
  public String getStandardUnit() {
    return val;
  }
  
  public String getDimension() {
    return "";
  }
  
  public String toString() {
    return val;
  }
  
}
