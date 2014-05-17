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

package org.primordion.xholon.util;

/**
 * Static methods that can be used with either standard Java or GWT,
 * in place of using String.format().
 * It only supports certain limited cases of String.format().
 * GWT does not support String.format().
 * In order to use this class with GWT, you must uncomment the "GWT implementation" line
 * and comment the "Standard Java implementation" line.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on August 12, 2013)
 */
public class StringHelper {
  
  /**
   * Round a number to a specified number of decimal places.
   * This method includes two implementations, one of which must be uncommented.
   * @param template "%.0f" "%.1f" "%.2f" "%.3f" ... "%.9f" only
   * @param d A double or float
   */
  public static String format(String template, double d) {
    if (template == null){return "";}
    
    if ((template.length() == 4) && (template.charAt(0) == '%')) {
      // Standard Java implementation
      //return String.format(template, d);
      
      // GWT implementation
      // convert the char into an int by subtracting 48 (ASCII code for '0')
      return formatGWT(template.charAt(2) - 48, d);
    }
    return "";
  }
  
  /**
   * GWT version.
   * @param digits The number of digits to appear after the decimal point
   * @param d A double or float
   */
  private static native String formatGWT(int digits, double d) /*-{
    //$wnd.console.log("formatGWT: " + digits + " " + d + " " + d.toFixed(digits));
    return d.toFixed(digits);
  }-*/;
  
  /**
   * Return a String representation of a boolean.
   * @param template "%b"
   * @param b
   * @return
   */
  public static String format(String template, boolean b) {
    return Boolean.toString(b);
  }
  
  /**
   * Return a String representation of a char.
   * @param template "%c"
   * @param c
   * @return
   */
  public static String format(String template, char c) {
    return Character.toString(c);
  }
  
  /**
   * Return a String representation of a int.
   * @param template "%d"
   * @param i
   * @return
   */
  public static String format(String template, int i) {
    return Integer.toString(i);
  }
  
  /**
   * Return a String representation of a String or other Object.
   * @param template "%s"
   * @param obj
   * @return
   */
  public static String format(String template, Object obj) {
    if ((template == null) || (template.length() != 2)) {return "";}
    char type = template.charAt(1);
    if (type == 's') {return obj.toString();}
    else {return obj.toString();}
  }
  
  /**
   * Return a concatenation of three strings.
   * @param template It must be "%s|%s|%s"
   * @param one
   * @param two
   * @param three
   * @return
   */
  public static String format(String template, String one, String two, String three) {
    if ("%s|%s|%s".equals(template)) {
      return one + "|" + two + "|" + three;
    }
    return "";
  }
  
  /**
   * tests
   * @param args
   */
   /* GWT
  public static void main(String[] args) {
    double d = 1234.56789;
    System.out.println(StringHelper.format("%.0f", d));
    System.out.println(StringHelper.format("%.1f", d));
    System.out.println(StringHelper.format("%.2f", d));
    System.out.println(StringHelper.format("%.3f", d));
    System.out.println(StringHelper.format("%.9f", d));
    System.out.println(StringHelper.format("junk", d));
    
    float f = 1234.56789f;
    System.out.println(StringHelper.format("%.0f", f));
    System.out.println(StringHelper.format("%.1f", f));
    System.out.println(StringHelper.format("%.2f", f));
    System.out.println(StringHelper.format("%.3f", f));
    System.out.println(StringHelper.format("%.9f", f));
    System.out.println(StringHelper.format("junk", f));
    
    System.out.println(StringHelper.format("%s", "This is a String."));
    System.out.println(StringHelper.format("%d", 123));
    System.out.println(StringHelper.format("%c", 'x'));
    System.out.println(StringHelper.format("%b", false));
    System.out.println(StringHelper.format("%s", new StringTokenizer("junk")));
    
    System.out.println(StringHelper.format("%s|%s|%s", "One", "Two", "Three"));
    System.out.println(StringHelper.format("%s %s %s", "One", "Two", "Three"));
    
    //System.out.println(String.format("%f", d));
    //System.out.println(StringHelper.format("%f", d));
  }*/
  
}

