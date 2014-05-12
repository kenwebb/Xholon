/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2014 Ken Webb
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

package org.primordion.xholon.script;

import org.primordion.xholon.base.IXholon;

/**
 * Generate GWT JSNI code for use in an external format writer.
 * The new code enables the use of dat.GUI or other equivalent JavaScript library,
 * to allow users to change the values of parameters at run-time.
 * GWT obfuscates the original Java parameter names,
 * while the process described here keeps these names and values available.
 * The use of an efParams JavaScript object with Java getters and setters,
 * is similar to the GWT concept of overlays.
 * Warning: This is intended as a best-effort helper, so don't expect it to be perfect.
 * 
 * A possible alternative way to do this is with a GWT Editor (com.google.gwt.editor.client),
 * but that approach requires one or more new Java classes and a ui.xml file for each EF type.
 * 
 * Example usage:
 * <pre>
&lt;EfParamsGenerator>
protected String nameTemplate = "r:C^^^";
private boolean shouldBeVisible = true;
protected int maxLabelLen = -1;
&lt;/EfParamsGenerator>
 * </pre>
 * 
 * To convert an existing external format writer (EFW) .java file:
 * - use this generator to generate the new code
 *   - copy parameter variables from the original EFW
 *   - paste them into the clipboard tab in any Xholon app, as shown above in "Example usage:"
 *   - right-click the Application node and select Edit > Paste Last Child
 * - copy the resulting text from the out tab to the end of the existing EFW
 * - comment out the original variable declarations in the EFW
 * - convert variable names to the corresponding newly-generated getters
 * - do additional editing as required
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideCodingBasicsOverlay.html">GWT overlays</a>
 * @since 0.9.1 (Created on May 3, 2014)
 */
public class EfParamsGenerator extends XholonScript {
  
  private String input = null;
  private StringBuilder sbMakeEfParams = null;
  private StringBuilder sbGettersSetters = null;
  
  @Override
  public void setVal(String str) {
    this.input = str;
  }
  
  @Override
  public void postConfigure() {
    IXholon node = this.getParentNode();
    
    sbMakeEfParams = new StringBuilder()
    .append("  /**\n")
    .append("   * Make a JavaScript object with all the parameters for this external format.\n")
    .append("   */\n")
    .append("  protected native void makeEfParams() /*-{\n")
    .append("    var p = {};\n");
    
    sbGettersSetters = new StringBuilder();
    
    String[] lines = input.split("\n");
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i].trim();
      if (line.length() == 0) {continue;}
      if (line.startsWith("/")) {
        // this is part of a comment line
        sbGettersSetters.append(line).append("\n");
        continue;
      }
      else if (line.startsWith("*")) {
        // this is part of a comment line
        sbGettersSetters.append(" ").append(line).append("\n");
        continue;
      }
      String[] tokens = line.split(" ");
      if (tokens.length < 5) {continue;}
      makeEfParam(tokens);
      makeSetterGetter(tokens);
    }
    
    sbMakeEfParams.append("    this.efParams = p;\n")
    .append("  }-*/;\n\n");
    
    node.println(sbMakeEfParams.toString());
    node.println(sbGettersSetters.toString());
    removeChild();
  }
  
  /**
   * Make a JSNI efParams JavaScript object parameter.
   * @param tokens 
   */
  protected void makeEfParam(String[] tokens) {
    sbMakeEfParams
    .append("    p.")
    .append(tokens[2])
    .append(" = ")
    .append(tokens[4])
    .append("\n");
  }
  
  /**
   * Make a JSNI getter and setter.
   * @param tokens 
   */
  protected void makeSetterGetter(String[] tokens) {
    String paramName = tokens[2]; // ex: "nameTemplate"
    String paramMethodName = "" + Character.toUpperCase(paramName.charAt(0))
      + paramName.substring(1); // ex: "NameTemplate"
    
    if (tokens.length > 5) {
      // this must be an end-of-line comment
      for (int i = 5; i < tokens.length; i++) {
        sbGettersSetters.append(" ").append(tokens[i]);
      }
      sbGettersSetters.append("\n");
    }
    
    sbGettersSetters
    .append("  public native ")
    .append(tokens[1]);
    if ("boolean".equals(tokens[1])) {
      sbGettersSetters.append(" is");
    }
    else {
      sbGettersSetters.append(" get");
    }
    sbGettersSetters.append(paramMethodName)
    .append("() /*-{return this.efParams.")
    .append(paramName)
    .append(";}-*/;\n");
    
    sbGettersSetters
    .append("  //public native void set")
    .append(paramMethodName)
    .append("(")
    .append(tokens[1])
    .append(" ")
    .append(paramName)
    .append(") /*-{this.efParams.")
    .append(paramName)
    .append(" = ")
    .append(paramName)
    .append(";}-*/;\n\n");
  }
  
}
