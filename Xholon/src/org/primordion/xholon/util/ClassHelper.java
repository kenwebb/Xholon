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
 * Class Helper
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on July 8, 2013)
 */
public class ClassHelper {
  
  /**
   * Is class1 assignable from (a superclass of) class2.
   * Each argument must be a Class, and may not be an Interface.
   * This is intended as a partial GWT substitute for the standard Java Class method.
   * Example:
   * <pre>ClassHelper.isAssignableFrom(Xholon.class, MyNode.class);</pre>
   * @return Returns true if class1 is a superclass of class2, or if class1 == class2.
   */
  public static boolean isAssignableFrom(final Class<?> class1, final Class<?> class2) {
    if ((class1 == null) || class1.isInterface()) {return false;}
    if ((class2 == null) || class2.isInterface()) {return false;}
    Class<?> clazz = class2;
    while (clazz != null) {
      if (clazz == class1) {return true;}
      clazz = clazz.getSuperclass();
    }
    return false;
  }
  
  /*public static void main(String[] args) {
    System.out.println(ClassHelper.isAssignableFrom(
    		org.primordion.xholon.base.Xholon.class,
    		org.primordion.user.app.helloworldjnlp.XhHelloWorld.class));
    System.out.println(ClassHelper.isAssignableFrom(
    		org.primordion.xholon.base.IXholon.class,
    		org.primordion.user.app.helloworldjnlp.XhHelloWorld.class));
    System.out.println(ClassHelper.isAssignableFrom(null, null));
    System.out.println(ClassHelper.isAssignableFrom(
    		org.primordion.xholon.base.Control.class,
    		org.primordion.xholon.base.Control.class));
    System.out.println(ClassHelper.isAssignableFrom(
    		org.primordion.xholon.base.Control.class,
    		org.primordion.xholon.base.Port.class));
  }*/
  
}
