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

package org.server;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
 
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
//import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JPackage;
//import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * Generate a new ExternalFormatService subclass with app-specific methods.
 * This is called during the production mode build.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on August 6, 2013)
 */
public class ExternalFormatServiceGenerator extends Generator {
  
  protected static final String CLASSNAME_SUFFIX = "GWTGEN"; // "Gen";
  
  protected static final String EF_PACKAGE_NAME = "org.primordion.ef";
  
  /**
   * Accumulating action names array, for use in writeActionNames().
   */
  StringBuilder sbAn = new StringBuilder();
  
  /**
   * Accumulating init array, for use in writeActionNames().
   */
  StringBuilder sbInit = new StringBuilder();
  
  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName)
      throws UnableToCompleteException {
    
    //System.out.println("Starting ExternalFormatServiceGenerator.generate( typeName=" + typeName);
    
    JClassType classType = context.getTypeOracle().findType(typeName);
    //System.out.println("classType=" + classType.getName());
    
    String packageName = classType.getPackage().getName(); // org.primordion.xholon.service
    //System.out.println("packageName: " + packageName);
    String simpleName = classType.getSimpleSourceName() + CLASSNAME_SUFFIX;
    ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);
    composer.setSuperclass(classType.getName());
    
    // add imports needed by composer
    composer.addImport("org.primordion.xholon.app.IApplication");
    composer.addImport("org.primordion.xholon.base.IXholon");
    composer.addImport("org.primordion.xholon.base.Xholon");
    composer.addImport("org.primordion.xholon.service.ef.IXholon2ExternalFormat");
    
    PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
    SourceWriter src = null;
    if (printWriter == null) {
      // the code has already been generated
      return typeName + CLASSNAME_SUFFIX;
    } else {
      src = composer.createSourceWriter(context, printWriter);
    }
    // add appSpecific methods
    sbAn.append("protected static final String[] DEFAULT_ACTION_NAMES = {\n");
    packageName = EF_PACKAGE_NAME;
    JClassType efClassType = context.getTypeOracle().findType(EF_PACKAGE_NAME + ".AbstractXholon2ExternalFormat");
    
    JClassType[] types = getPackageTypes(efClassType);
    
    JPackage[] subPackages = null;
    if (types != null) {
      // this can happen if EF_PACKAGE_NAME = "org.primordion.ef" does not exist
      subPackages = getSubPackages(packageName, context.getTypeOracle());
      
      writeActionNames(types);
      for (int i = 0; i < subPackages.length; i++) {
        writeActionNames(subPackages[i].getTypes(), subPackages[i].getName());
      }
    }
    sbAn.append("};\n");
    src.println(sbAn.toString());
    
    src.println(writeGetActionList(types));
    src.println(writeInitExternalFormatWriter(types));
    
    src.commit(logger);
    
    return typeName + CLASSNAME_SUFFIX;
  }
  
  /**
   * Get an array of class types that belong to the application's package.
   */
  protected JClassType[] getPackageTypes(JClassType classType) {
    if (classType == null) {return null;}
    JClassType[] types = classType.getPackage().getTypes();
    //for (int i = 0; i < types.length; i++) {
    //  System.out.println("testing: " + types[i].getName());
    //}
    return types;
  }
  
  protected JPackage[] getSubPackages(String pName, TypeOracle typeOracle) {
    pName = pName + ".";
    JPackage[] p = typeOracle.getPackages();
    List<JPackage> pList = new ArrayList<JPackage>();
    for (int i = 0; i < p.length; i++) {
      if (p[i].getName().startsWith(pName)) {
        //System.out.println("package: " + p[i].getName());
        pList.add(p[i]);
      }
    }
    JPackage[] pOut = new JPackage[pList.size()];
    Iterator<JPackage> pIt = pList.iterator();
    int j = 0;
    while (pIt.hasNext()) {
      pOut[j++] = pIt.next();
    }
    return pOut; //(JPackage[])pList.toArray();
  }
  
  protected void writeActionNames(JClassType[] types) {
    String packageName = "";
    if (types.length > 0) {
      packageName = types[0].getPackage().getName();
    }
    List<String> actionNames = new ArrayList<String>();
    for(int i = 0; i < types.length; i++) {
      String typeStr = types[i].getName();
      //System.out.println("typeStr:" + typeStr);
      // exclude inner classes that contain the '$' character
      if ((typeStr.startsWith("Xholon2")) && (typeStr.indexOf('$') == -1)) {
        actionNames.add(typeStr.substring(7));
      }
    }
    
    Collections.sort(actionNames);
    Iterator it = actionNames.iterator();
		while (it.hasNext()) {
		  String actionName = (String)it.next();
			sbAn.append("    \"" + actionName + "\",\n");
			sbInit.append("  else if (\"")
        .append(actionName)
        .append("\".equalsIgnoreCase(formatName)) {\n")
        .append("    xholon2ef = new ")
        .append(packageName)
        .append(".Xholon2")
        .append(actionName)
        .append("();\n")
        .append("  }\n");
		}
  }
  
  /**
   * Write action names for a subpackage.
   * Example: "_xholon,Cd,Csh,Ih,Xhn",
   */
  protected void writeActionNames(JClassType[] types, String subPackageName) {
    List<String> actionNames = new ArrayList<String>();
    for(int i = 0; i < types.length; i++) {
      String typeStr = types[i].getName();
      //System.out.println("typeStr:" + typeStr);
      // exclude inner classes that contain the '$' character
      if ((typeStr.startsWith("Xholon2")) && (typeStr.indexOf('$') == -1)) {
        actionNames.add(typeStr.substring(7));
      }
    }
    int start = subPackageName.lastIndexOf(".") + 1; // org.primordion.xholon.io.ef.other
    sbAn.append("    \"_").append(subPackageName.substring(start));
    Collections.sort(actionNames);
    Iterator it = actionNames.iterator();
		while (it.hasNext()) {
		  String actionName = (String)it.next();
			sbAn.append("," + actionName);
			sbInit.append("  else if (\"")
			  .append("_")
			  .append(subPackageName.substring(start))
			  .append(",")
        .append(actionName)
        .append("\".equalsIgnoreCase(formatName)) {\n")
        .append("    xholon2ef = new ")
        .append(subPackageName)
        .append(".Xholon2")
        .append(actionName)
        .append("();\n")
        .append("  }\n");
		}
		sbAn.append("\",\n");
  }
  
  /**
   * Write a getActionList() method.
   * Example:
<code>
public String[] getActionList() {
  String[] actionNames = {
    "HTModL",
  };
  return actionNames;
}
</code>
   */
  protected String writeGetActionList(JClassType[] types) {
    /*StringBuilder sbt = new StringBuilder();
    for(int i = 0; i < types.length; i++) {
      String typeStr = types[i].getName();
      System.out.println("typeStr:" + typeStr);
      // exclude inner classes that contain the '$' character
      if ((typeStr.startsWith("Xholon2")) && (typeStr.indexOf('$') == -1)) {
        sbt.append("    \"" + typeStr.substring(7) + "\",\n");
      }
    }*/
    StringBuilder sb = new StringBuilder()
    .append("public String[] getActionList() {\n")
    //.append("  String[] actionNames = {\n")
    //.append(sbt)
    //.append("  };\n")
    .append("  return DEFAULT_ACTION_NAMES;\n")
    .append("}\n");
    //System.out.println(sb.toString());
    return sb.toString();
  }
  
  /**
   * Write a initExternalFormatWriter() method.
   * Example:
<code>
public IXholon2ExternalFormat initExternalFormatWriter(IXholon node, String formatName) {
  String modelName = "";
  IApplication app = node.getApp();
  if (app != null) {
    modelName = app.getModelName();
  }
  IXholon2ExternalFormat xholon2ef = null;
  if (formatName == null) {return null;}
  else if ("HTModL".equalsIgnoreCase(formatName)) {
    xholon2ef = new org.primordion.xholon.io.ef.Xholon2HTModL();
  }
  else if ("_other,ChapNetwork".equalsIgnoreCase(formatName)) {
    xholon2ef = new org.primordion.xholon.io.ef.other.Xholon2ChapNetwork();
  }
  else {return null;}
  if (xholon2ef.initialize(null, modelName, node)) {
    return xholon2ef;
  }
  else {
    writeUserErrorMessage(node, formatName, "Unable to initialize.");
  }
  return null;
}
</code>
   */
  protected String writeInitExternalFormatWriter(JClassType[] types) {
    /*StringBuilder sbt = new StringBuilder();
    for(int i = 0; i < types.length; i++) {
      String typeStr = types[i].getName();
      if (typeStr.startsWith("Xholon2")) {
        sbt.append("  else if (\"")
        .append(typeStr.substring(7))
        .append("\".equalsIgnoreCase(formatName)) {\n")
        .append("    xholon2ef = new ")
        .append(types[i].getPackage().getName())
        .append(".")
        .append(typeStr)
        .append("();\n")
        .append("  }\n");
      }
    }*/
    StringBuilder sb = new StringBuilder()
    .append("public IXholon2ExternalFormat initExternalFormatWriter(IXholon node, String formatName, String efParams) {\n")
    .append("  String modelName = \"\";\n")
    .append("  IApplication app = node.getApp();\n")
    .append("  if (app != null) {\n")
    .append("    modelName = app.getModelName();\n")
    .append("  }\n")
    .append("  IXholon2ExternalFormat xholon2ef = null;\n")
    .append("  if (formatName == null) {return null;}\n")
    .append(sbInit.toString())
    .append("  else {return null;}\n")
    .append("  xholon2ef.setEfParamsFromJsonString(efParams);")
    .append("  if ((app.isAllowConfigSrv()) && (xholon2ef.canAdjustOptions()) && (efParams == null)) {\n")
    .append("    xholon2ef.adjustOptions(null, modelName, node, formatName);\n")
    .append("    return null;")
    .append("  }\n")
    .append("  else if (xholon2ef.initialize(null, modelName, node)) {\n")
    .append("    return xholon2ef;\n")
    .append("  }\n")
    .append("  else {\n")
    .append("    writeUserErrorMessage(node, formatName, \"Unable to initialize.\");\n")
    .append("  }\n")
    .append("  return null;\n")
    .append("}\n");
    return sb.toString();
  }
  
}
