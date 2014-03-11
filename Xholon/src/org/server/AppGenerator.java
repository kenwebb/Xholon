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

//import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Generate a new IApplication subclass with app-specific methods.
 * This is called during the development and production mode builds.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on August 6, 2013)
 */
public class AppGenerator extends Generator {
  
  /**
   * The name of every generated class will end with this suffix.
   * The suffix needs to be unusual enough that no application class would use it.
   */
  protected static final String CLASSNAME_SUFFIX = "GWTGEN"; // "Gen"
  
  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName)
      throws UnableToCompleteException {
    
    //System.out.println("Starting AppGenerator.generate( typeName=" + typeName);
    
    TypeOracle oracle = context.getTypeOracle();
    JClassType classType = oracle.findType(typeName);
    JClassType iXholonType = oracle.findType(org.primordion.xholon.base.IXholon.class.getName());
    
    String packageName = classType.getPackage().getName();
    String simpleName = classType.getSimpleSourceName() + CLASSNAME_SUFFIX;
    ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);
    composer.setSuperclass(classType.getName());
    
    // add imports needed by composer
    
    composer.addImport("org.primordion.xholon.app.IApplication");
    composer.addImport("org.primordion.xholon.base.IControl");
    composer.addImport("org.primordion.xholon.base.IMechanism");
    composer.addImport("org.primordion.xholon.base.IXholon");
    composer.addImport("org.primordion.xholon.base.IXholonClass");
    composer.addImport("java.util.ArrayList");
    composer.addImport("java.util.List");
    
    PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
    SourceWriter src = null;
    if (printWriter == null) {
      // the code has already been generated
      return typeName + CLASSNAME_SUFFIX;
    } else {
      src = composer.createSourceWriter(context, printWriter);
    }
    
    System.out.println("Starting AppGenerator.generate( typeName=" + typeName);
    
    //                                 Add appSpecific methods.
    JClassType[] types = getPackageTypes(classType);
    
    // classes and objects
    src.println(writeMakeAppSpecificNode(packageName, classType.getSimpleSourceName(), types, iXholonType));
    src.println(writeFindAppSpecificClass(packageName, classType.getSimpleSourceName(), types, iXholonType));
    src.println(writeIsAppSpecificClassFindable(packageName, classType.getSimpleSourceName(), types, iXholonType));
    
    // constants
    src.println(writeFindAppSpecificConstantValue(packageName, classType.getSimpleSourceName(), types, iXholonType));
    
    // ports
    src.println(writeGetAppSpecificObjectVal(packageName, classType.getSimpleSourceName(), types, iXholonType, oracle));
    src.println(writeSetAppSpecificObjectVal(packageName, classType.getSimpleSourceName(), types, iXholonType));
    // port arrays
    src.println(writeSetAppSpecificObjectArrayVal(packageName, classType.getSimpleSourceName(), types, iXholonType));
    
    // attributes
    src.println(writeGetAppSpecificAttribute(packageName, types, iXholonType, oracle));
    src.println(writeSetAppSpecificAttribute(packageName, types, iXholonType, oracle));
    src.println(writeGetAppSpecificAttributes(packageName, types, iXholonType, oracle));
    src.println(writeIsAppSpecificAttribute(packageName, types, iXholonType, oracle));
    
    src.commit(logger);
    return typeName + CLASSNAME_SUFFIX;
  }
  
  /**
   * Get an array of class types that belong to the application's package.
   */
  protected JClassType[] getPackageTypes(JClassType classType) {
    JClassType[] typesIn = classType.getPackage().getTypes();
    // filter out any generated types that are already in the package (ex: "App...GWTGEN")
    //List typeListIn = Arrays.asList(typesIn);
    List<JClassType> typeListOut = new ArrayList<JClassType>();
    for (int i = 0; i < typesIn.length; i++) {
      JClassType type = typesIn[i];
      String typeName = type.getName();
      //System.out.println("in:" + typeName);
      if (!typeName.endsWith(CLASSNAME_SUFFIX)) {
        typeListOut.add(type);
        
        //JClassType supertype = type.getSuperclass();
        //if (supertype != null) {
        //  System.out.println(typeName + " " + supertype.getName());
        //}
        
      }
    }
    JClassType[] typesOut = typeListOut.toArray(new JClassType[0]);
    // return the filtered array
    //for (int j = 0; j < typesOut.length; j++) {
    //  System.out.println("out:" + typesOut[j].getName());
    //}
    return typesOut;
  }
  
  /* Write a makeAppSpecificNode() method.
   * Example:
<code>
public IXholon makeAppSpecificNode(String implName) {
  if ("org.primordion.user.app.PingPong.XhPingPong".equals(implName)) {
    return new XhPingPong();
  }
  return super.makeAppSpecificNode(implName);
}
</code>
  */
  protected String writeMakeAppSpecificNode(String appPackageName, String appSimpleName,
      JClassType[] types, JClassType iXholonType) {
    String xhSimpleName = null;
    
    StringBuilder sbt = new StringBuilder();
    for(int i = 0; i < types.length; i++) {
      JClassType type = types[i];
      if (type.isDefaultInstantiable() && (type.isAssignableTo(iXholonType))) {
        //we have determined that the IXholon class can be constructed using a simple new operation
        xhSimpleName = type.getName();
        if (!xhSimpleName.startsWith("App")) {
          sbt.append("  if (\"")
          .append(appPackageName + "." + xhSimpleName)
          .append("\".equals(implName)) {\n");
          sbt.append("    return new ").append(xhSimpleName).append("();\n");
          sbt.append("  }\n");
        }
      }
    }
    
    StringBuilder sb = new StringBuilder()
    .append("public IXholon makeAppSpecificNode(String implName) {\n")
    .append(sbt.toString())
    .append("  return super.makeAppSpecificNode(implName);\n")
    .append("}\n");
    //System.out.println(sb.toString());
    return sb.toString();
  }
  
  /* Write a findAppSpecificClass() method.
   * Example:
<code>
public Class<IXholon> findAppSpecificClass(String className) {
  if ("org.primordion.user.app.PingPong.XhPingPong".equals(className)) {
    Class clazz = org.primordion.user.app.PingPong.XhPingPong.class;
    return (Class<IXholon>)clazz;
  }
  return super.findAppSpecificClass(className);
}
</code>
  */
  protected String writeFindAppSpecificClass(String appPackageName, String appSimpleName,
      JClassType[] types, JClassType iXholonType) {
    // assume that the Xh class name is related to the App class name
    // ex: AppPingPong XhPingPong
    //String xhSimpleName = "Xh" + appSimpleName.substring(3);
    
    String xhSimpleName = null;
    
    StringBuilder sbt = new StringBuilder();
    for(int i = 0; i < types.length; i++) {
      JClassType type = types[i];
      if (type.isDefaultInstantiable() && (type.isAssignableTo(iXholonType))) {
        //we have determined that the IXholon class can be constructed using a simple new operation
        xhSimpleName = type.getName();
        if (!xhSimpleName.startsWith("App")) {
          sbt.append("  if (\"")
          .append(appPackageName + "." + xhSimpleName)
          .append("\".equals(className)) {\n")
          //.append("    println(\"findAppSpecificClass(\" + className + \")\");\n")
          .append("    Class clazz = ")
          .append(appPackageName + "." + xhSimpleName)
          .append(".class;\n")
          .append("    return (Class<IXholon>)clazz;\n")
          .append("  }\n")
          ;
        }
      }
    }
    
    StringBuilder sb = new StringBuilder()
    .append("public Class<IXholon> findAppSpecificClass(String className) {\n")
    .append(sbt.toString())
    .append("  return super.findAppSpecificClass(className);\n")
    .append("}\n");
    //System.out.println(sb.toString());
    return sb.toString();
  }
  
  /* Write a findAppSpecificConstantValue() method.
   * Example:
<code>
public int findAppSpecificConstantValue(Class clazz, String constName) {
  if (clazz == null) {return 0;}
  else if ("org.primordion.xholon.xmiapps.XhTestFsm".equals(clazz.getName())) {
    if ("P_PARTNER".equals(constName)) {return XhTestFsm.P_PARTNER;}
  }
  return super.findAppSpecificConstantValue(clazz, constName);
}
</code>
  */
  protected String writeFindAppSpecificConstantValue(String appPackageName, String appSimpleName,
      JClassType[] types, JClassType iXholonType) {
    String xhSimpleName = null;
    StringBuilder sb = new StringBuilder(128);
    StringBuilder sbt = new StringBuilder(128);
    for(int i = 0; i < types.length; i++) {
      JClassType type = types[i];
      if (
          //type.isDefaultInstantiable() &&
          type.isAssignableTo(iXholonType)) {
        //we have determined that the IXholon class can be constructed using a simple new operation
        xhSimpleName = type.getName();
        if (!xhSimpleName.startsWith("App")) {
          JField[] fields = type.getFields();
          StringBuilder sbf = new StringBuilder(128);
          for (int j = 0; j < fields.length; j++) {
            JField field = fields[j];
            JType fieldType = field.getType();
            if (field.isStatic() && field.isFinal() && field.isPublic()
                && "int".equals(fieldType.getSimpleSourceName())) {
              String fieldName = field.getName();
              sbf.append("    if (\"")
              .append(fieldName)
              .append("\".equals(constName)) {return ")
              .append(xhSimpleName)
              .append(".")
              .append(fieldName)
              .append(";}\n");
            }
          }
          if (sbf.length() > 0) {
            sbt.append("  else if (\"")
            .append(appPackageName + "." + xhSimpleName)
            .append("\".equals(clazz.getName())) {\n")
            .append(sbf.toString())
            .append("  }\n");
          }
        }
      }
    }
    
    if (sbt.length() > 0) {
      sb.append("public int findAppSpecificConstantValue(Class clazz, String constName) {\n")
      .append("  if (clazz == null) {return 0;}\n")
      .append(sbt.toString())
      
      .append("  Class superclass = clazz.getSuperclass();\n")
      .append("  if ((superclass != null) && superclass.getName().startsWith(\"")
      .append(appPackageName)
      .append("\")) {\n")
      .append("    return findAppSpecificConstantValue(superclass, constName);\n")
      .append("  }\n")
      
      .append("  return super.findAppSpecificConstantValue(clazz, constName);\n")
      .append("}\n");
    }
    //System.out.println(sb.toString());
    return sb.toString();
  }
  
  /* Write getAppSpecificObjectVal() and getAppSpecificObjectValNames() methods.
   * Example:
<code>
public IXholon getAppSpecificObjectVal(IXholon node, Class<IXholon> clazz, String attrName) {
  if (node == null) {return null;}
  else if ("org.primordion.user.app.testNodePorts.XhtestNodePorts".equals(clazz.getName())) {
    if ("three".equalsIgnoreCase(attrName)) {
      return ((org.primordion.user.app.testNodePorts.XhtestNodePorts)node).getThree();
    }
  }
  Class superclass = clazz.getSuperclass();
  if (superclass.getName().startsWith("org.primordion.user.app.testNodePorts")) {
    return getAppSpecificObjectVal(node, superclass, attrName);
  }
  return super.getAppSpecificObjectVal(node, clazz, attrName);
}

public String getAppSpecificObjectValNames(IXholon node, Class<IXholon> clazz) {
  String names = "";
  if (node == null) {return null;}
  else if ("org.primordion.user.app.testNodePorts.XhtestNodePorts".equals(clazz.getName())) {
    names = "three,";
  }
  Class superclass = clazz.getSuperclass();
  if (superclass.getName().startsWith("org.primordion.user.app.testNodePorts")) {
    names = names + getAppSpecificObjectValNames(node, superclass);
  }
  else {
    names = names + super.getAppSpecificObjectValNames(node, clazz);
  }
  return names;
}
</code>
  */
  protected String writeGetAppSpecificObjectVal(String appPackageName, String appSimpleName,
      JClassType[] types, JClassType iXholonType, TypeOracle oracle) {
    String xhSimpleName = null;
    
    StringBuilder sbt = new StringBuilder(128);
    StringBuilder sbfnT = new StringBuilder(); // for use with getAppSpecificObjectValNames()
    for(int i = 0; i < types.length; i++) {
      JClassType type = types[i];
      xhSimpleName = type.getName();
      
      if (!xhSimpleName.startsWith("App")) {
        // get all public getters
        JMethod[] methods = type.getMethods();
        StringBuilder sbm = new StringBuilder(128);
        StringBuilder sbfnM = new StringBuilder();
        for (int j = 0; j < methods.length; j++) {
          JMethod method = methods[j];
          JClassType rType = oracle.findType(method.getReturnType().getQualifiedSourceName());
          // rType = null if oracle is given a primitive type (int etc.)
          JType[] paramTypes = method.getParameterTypes();
          if (method.isPublic()
              && (method.getName().startsWith("get"))
              && (method.getName().length() > 3)
              && ((rType != null) && (rType.isAssignableTo(iXholonType)))
              && (paramTypes.length == 0)) {
            
            // the paramType can be any class or interface assignable to IXholon
            //if (paramTypes.length == 0) {
              //JClassType paramType = paramTypes[0].isClassOrInterface();
              //if ((paramType != null) && (paramType.isAssignableTo(iXholonType))) {
                
                String fieldName = method.getName().substring(3);
                if (fieldName.length() == 1) {
                  // ex: "getK" becomes "k"
                  fieldName = "" + Character.toLowerCase(fieldName.charAt(0));
                }
                else {
                  // ex: "getSpace" becomes "space"
                  fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
                }
                
                sbm.append("    if (\"")
                .append(fieldName)
                .append("\".equalsIgnoreCase(attrName)) {\n")
                .append("      return ((")
                .append(appPackageName + "." + xhSimpleName)
                .append(")node).")
                .append(method.getName())
                .append("();\n")
                //.append(paramType.getName())
                //.append(")val);\n")
                //.append("      return true;\n")
                .append("    }\n");
                
                sbfnM.append(fieldName).append(",");
              //}
            //}
          }
        } // end for
        if (sbm.length() > 0) {
          sbt.append("  else if (\"")
          .append(appPackageName + "." + xhSimpleName)
          .append("\".equals(clazz.getName())) {\n")
          .append(sbm.toString())
          //.append("    return null;\n")
          .append("  }\n");
        }
        if (sbfnM.length() > 0) {
          sbfnT.append("  else if (\"")
          .append(appPackageName + "." + xhSimpleName)
          .append("\".equals(clazz.getName())) {\n")
          .append("    names = \"")
          .append(sbfnM.toString())
          .append("\";\n")
          .append("  }\n");
        }
      }
    }
    
    StringBuilder sb = new StringBuilder(128)
    .append("public IXholon getAppSpecificObjectVal(IXholon node, Class<IXholon> clazz, String attrName) {\n")
    .append("  if (node == null) {return null;}\n")
    .append(sbt.toString())
    .append("  Class superclass = clazz.getSuperclass();\n")
    .append("  if (superclass.getName().startsWith(\"")
    .append(appPackageName)
    .append("\")) {\n")
    .append("    return getAppSpecificObjectVal(node, superclass, attrName);\n")
    .append("  }\n")
    .append("  return super.getAppSpecificObjectVal(node, clazz, attrName);\n")
    .append("}\n");
    
    sb
    .append("\npublic String getAppSpecificObjectValNames(IXholon node, Class<IXholon> clazz) {\n")
    .append("  String names = \"\";\n")
    .append("  if (node == null) {return null;}\n")
    .append(sbfnT.toString())
    .append("  Class superclass = clazz.getSuperclass();\n")
    .append("  if (superclass.getName().startsWith(\"")
    .append(appPackageName)
    .append("\")) {\n")
    .append("    names = names + getAppSpecificObjectValNames(node, superclass);\n")
    .append("  }\n")
    .append("  else {\n")
    .append("    names = names + super.getAppSpecificObjectValNames(node, clazz);\n")
    .append("  }\n")
    .append("  return names;\n")
    .append("}\n");
    
    return sb.toString();
  } // end writeGetAppSpecificObjectVal()

  /* Write a setAppSpecificObjectVal() method.
   * Example:
<code>
public boolean setAppSpecificObjectVal(IXholon node, Class<IXholon> clazz, String attrName, IXholon val) {
  if (node == null) {return false;}
  else if ("org.primordion.user.app.testNodePorts.XhtestNodePorts".equals(clazz.getName())) {
    if ("three".equalsIgnoreCase(attrName)) {
      ((org.primordion.user.app.testNodePorts.XhtestNodePorts)node).setThree((IXholon)val);
      return true;
    }
  }
  Class superclass = clazz.getSuperclass();
  if (superclass.getName().startsWith("org.primordion.user.app.testNodePorts")) {
    return setAppSpecificObjectVal(node, superclass, attrName, val);
  }
  return super.setAppSpecificObjectVal(node, clazz, attrName, val);
}
</code>
  */
  protected String writeSetAppSpecificObjectVal(String appPackageName, String appSimpleName,
      JClassType[] types, JClassType iXholonType) {
    String xhSimpleName = null;
    
    StringBuilder sbt = new StringBuilder(128);
    for(int i = 0; i < types.length; i++) {
      JClassType type = types[i];
      xhSimpleName = type.getName();
      
      if (!xhSimpleName.startsWith("App")) {
        // get all public setters with a single IXholon arg
        JMethod[] methods = type.getMethods();
        StringBuilder sbm = new StringBuilder(128);
        for (int j = 0; j < methods.length; j++) {
          JMethod method = methods[j];
          if (method.isPublic() && (method.getName().startsWith("set")) && (method.getName().length() > 3)) {
            JType[] paramTypes = method.getParameterTypes();
            // the paramType can be any class or interface assignable to IXholon
            if (paramTypes.length == 1) {
              JClassType paramType = paramTypes[0].isClassOrInterface();
              if ((paramType != null) && (paramType.isAssignableTo(iXholonType))) {
                
                String fieldName = method.getName().substring(3);
                if (fieldName.length() == 1) {
                  // ex: "setK" becomes "k"
                  fieldName = "" + Character.toLowerCase(fieldName.charAt(0));
                }
                else {
                  // ex: "setSpace" becomes "space"
                  fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
                }
                
                sbm.append("    if (\"")
                .append(fieldName)
                .append("\".equalsIgnoreCase(attrName)) {\n")
                .append("      ((")
                .append(appPackageName + "." + xhSimpleName)
                .append(")node).")
                .append(method.getName())
                .append("((")
                .append(paramType.getName())
                .append(")val);\n")
                .append("      return true;\n")
                .append("    }\n");
              }
            }
          }
        }
        if (sbm.length() != 0) {
          sbt.append("  else if (\"")
          .append(appPackageName + "." + xhSimpleName)
          .append("\".equals(clazz.getName())) {\n")
          .append(sbm.toString())
          //.append("    return false;\n")
          .append("  }\n");
        }
      }
    }
    
    StringBuilder sb = new StringBuilder(128)
    .append("public boolean setAppSpecificObjectVal(IXholon node, Class<IXholon> clazz, String attrName, IXholon val) {\n")
    .append("  if (node == null) {return false;}\n")
    .append(sbt.toString())
    .append("  Class superclass = clazz.getSuperclass();\n")
    .append("  if (superclass.getName().startsWith(\"")
    .append(appPackageName)
    .append("\")) {\n")
    .append("    return setAppSpecificObjectVal(node, superclass, attrName, val);\n")
    .append("  }\n")
    .append("  return super.setAppSpecificObjectVal(node, clazz, attrName, val);\n")
    .append("}\n");
    //System.out.println(sb.toString());
    return sb.toString();
  } // end writeSetAppSpecificObjectVal()
  
  /* Write a isAppSpecificClassFindable() method.
   * Example:
<code>
public boolean isAppSpecificClassFindable(String implName) {
  if ("org.primordion.user.app.PingPong.XhPingPong".equals(implName)) {
    return true;
  }
  return super.isAppSpecificClassFindable(implName);
}
</code>
  */
  protected String writeIsAppSpecificClassFindable(String appPackageName, String appSimpleName,
      JClassType[] types, JClassType iXholonType) {
    String xhSimpleName = null;
    
    StringBuilder sbt = new StringBuilder();
    for(int i = 0; i < types.length; i++) {
      JClassType type = types[i];
      if (type.isDefaultInstantiable() && (type.isAssignableTo(iXholonType))) {
        //we have determined that the IXholon class can be constructed using a simple new operation
        xhSimpleName = type.getName();
        if (!xhSimpleName.startsWith("App")) {
          sbt.append("  if (\"")
          .append(appPackageName + "." + xhSimpleName)
          .append("\".equals(implName)) {\n")
          .append("    return true;\n")
          .append("  }\n");
        }
      }
    }
    
    StringBuilder sb = new StringBuilder()
    .append("public boolean isAppSpecificClassFindable(String implName) {\n")
    .append(sbt.toString())
    .append("  return super.isAppSpecificClassFindable(implName);\n")
    .append("}\n");
    //System.out.println(sb.toString());
    return sb.toString();
  }
  
  /* Write a getAppSpecificAttribute() method.
   * Example:
<code>
public Object getAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName) {
  if (node == null) {return null;}
  else if ("org.primordion.xholon.xmiapps.XhTestFsm".equals(clazz.getName())) {
    if ("ADouble".equals(attrName)) {return ((XhTestFsm)node).getADouble();}
    if ("AString".equals(attrName)) {return ((XhTestFsm)node).getAString();}
    if ("ABooleanTrue".equals(attrName)) {return ((XhTestFsm)node).getABooleanTrue();}
    if ("AChar".equals(attrName)) {return ((XhTestFsm)node).getAChar();}
    if ("AByte".equals(attrName)) {return ((XhTestFsm)node).getAByte();}
    if ("AShort".equals(attrName)) {return ((XhTestFsm)node).getAShort();}
    if ("AFloat".equals(attrName)) {return ((XhTestFsm)node).getAFloat();}
    if ("AnInteger".equals(attrName)) {return ((XhTestFsm)node).getAnInteger();}
    if ("MyInteger".equals(attrName)) {return ((XhTestFsm)node).getMyInteger();}
    if ("ABooleanFalse".equals(attrName)) {return ((XhTestFsm)node).getABooleanFalse();}
    if ("State".equals(attrName)) {return ((XhTestFsm)node).getState();}
  }
  Class superclass = clazz.getSuperclass();
  if ((superclass != null) && superclass.getName().startsWith("org.primordion.xholon.xmiapps")) {
    return getAppSpecificAttribute(node, superclass, attrName);
  }
  return null;
}
</code>
  */
  protected String writeGetAppSpecificAttribute(String appPackageName,
      JClassType[] types, JClassType iXholonType, TypeOracle oracle) {
    String xhSimpleName = null;
    StringBuilder sb = new StringBuilder();
    StringBuilder sbt = new StringBuilder();
    for(int i = 0; i < types.length; i++) {
      JClassType type = types[i];
      if ((type.isClass() != null) && (type.isAssignableTo(iXholonType))) {
        //we have determined that the type is an IXholon class (concrete or abstract, not an interface)
        xhSimpleName = type.getName();
        //if (!xhSimpleName.startsWith("App")) {
          // retrieve getter methods that don't return an IXholon
          JMethod[] methods = type.getMethods();
          StringBuilder sbm = new StringBuilder();
          for (int j = 0; j < methods.length; j++) {
            JMethod m = methods[j];
            String mName = m.getName();
            JClassType rType = oracle.findType(m.getReturnType().getQualifiedSourceName());
            // rType = null if oracle is given a primitive type (int etc.)
            if (m.isPublic() && !m.isAbstract() && !m.isStatic()
                && (m.getParameterTypes().length == 0)
                && ((rType == null) || !(rType.isAssignableTo(iXholonType)))
                && (((mName.startsWith("get")) && (mName.length() > 3))
                  || ((mName.startsWith("is"))  && (mName.length() > 2)))
            ) {
              // ex: if ("ADouble".equals(attrName)) {return ((XhTestFsm)node).getADouble();}
              sbm.append("    if (\"")
              .append(mName.startsWith("get") ? mName.substring(3) : mName.substring(2))
              .append("\".equalsIgnoreCase(attrName)) {return ((")
              .append(xhSimpleName)
              .append(")node).")
              .append(mName)
              .append("();}\n");
            }
          } // end for(j
          if (sbm.length() > 0) {
            sbt.append("  else if (\"")
            .append(appPackageName + "." + xhSimpleName)
            .append("\".equals(clazz.getName())) {\n")
            //.append("    System.out.println(\"AppGenerator getAppSpecificAttribute2: \" + clazz.getName());\n")
            .append(sbm.toString())
            .append("  }\n");
          }
        //} // end if App
      } // end if
    } // end for(i
    
    if (sbt.length() > 0) {
      sb
      .append("public Object getAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName) {\n")
      //.append("  System.out.println(\"AppGenerator getAppSpecificAttribute1: \" + node + \" \" + clazz.getName() + \" \" + attrName);\n")
      .append("  if (node == null) {return null;}\n")
      .append(sbt.toString())
      .append("  Class superclass = clazz.getSuperclass();\n")
      .append("  if ((superclass != null) && superclass.getName().startsWith(\"")
      .append(appPackageName)
      .append("\")) {\n")
      .append("    return getAppSpecificAttribute(node, superclass, attrName);\n")
      .append("  }\n")
      .append("  return super.getAppSpecificAttribute(node, clazz, attrName);\n")
      .append("}\n");
      //System.out.println(sb.toString());
    }
    return sb.toString();
  }
  
  /* Write a setAppSpecificAttribute() method.
   * Example:
<code>
public void setAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName, Object attrVal) {
  try {
    if (node == null) {return;}
    else if ("org.primordion.xholon.xmiapps.XhTestFsm".equals(clazz.getName())) {
      if ("ADouble".equals(attrName)) {((XhTestFsm)node).setADouble(Double.parseDouble((String)attrVal));return;}
      if ("AString".equals(attrName)) {((XhTestFsm)node).setAString((String)attrVal);return;}
      if ("ABooleanTrue".equals(attrName)) {((XhTestFsm)node).setABooleanTrue(Boolean.parseBoolean((String)attrVal));return;}
      if ("AChar".equals(attrName)) {((XhTestFsm)node).setAChar(((String)attrVal).length() > 0 ? ((String)attrVal).charAt(0) : ' ');return;}
      if ("AByte".equals(attrName)) {((XhTestFsm)node).setAByte(Byte.parseByte((String)attrVal));return;}
      if ("AShort".equals(attrName)) {((XhTestFsm)node).setAShort(Short.parseShort((String)attrVal));return;}
      if ("AFloat".equals(attrName)) {((XhTestFsm)node).setAFloat(Float.parseFloat((String)attrVal));return;}
      if ("AnInteger".equals(attrName)) {((XhTestFsm)node).setAnInteger(Integer.parseInt((String)attrVal));return;}
      if ("MyInteger".equals(attrName)) {((XhTestFsm)node).setMyInteger(Integer.parseInt((String)attrVal));return;}
      if ("ABooleanFalse".equals(attrName)) {((XhTestFsm)node).setABooleanFalse(Boolean.parseBoolean((String)attrVal));return;}
      if ("State".equals(attrName)) {((XhTestFsm)node).setState(Integer.parseInt((String)attrVal));return;}
    }
  } catch(java.lang.NumberFormatException e) {return;}
  Class superclass = clazz.getSuperclass();
  if (superclass.getName().startsWith("org.primordion.xholon.xmiapps")) {
    setAppSpecificAttribute(node, superclass, attrName, attrVal);
  }
}
</code>
  */
  protected String writeSetAppSpecificAttribute(String appPackageName,
      JClassType[] types, JClassType iXholonType, TypeOracle oracle) {
    String xhSimpleName = null;
    StringBuilder sb = new StringBuilder();
    StringBuilder sbt = new StringBuilder();
    for(int i = 0; i < types.length; i++) {
      JClassType type = types[i];
      if ((type.isClass() != null) && (type.isAssignableTo(iXholonType))) {
        //we have determined that the type is an IXholon class (concrete or abstract, not an interface)
        xhSimpleName = type.getName();
        //if (!xhSimpleName.startsWith("App")) {
          // retrieve getter methods that don't return an IXholon
          JMethod[] methods = type.getMethods();
          StringBuilder sbm = new StringBuilder();
          for (int j = 0; j < methods.length; j++) {
            JMethod m = methods[j];
            String mName = m.getName();
            JType[] paramTypes = m.getParameterTypes();
            if (m.isPublic() && !m.isAbstract() && !m.isStatic()
                && (paramTypes.length == 1)
                && (paramTypes[0].isArray() == null)
                && (mName.startsWith("set"))) {
              JClassType paramType = paramTypes[0].isClassOrInterface();
              if ((paramType != null) && (paramType.isAssignableTo(iXholonType))) {
                // the param is an IXholon, so ignore it
                continue;
              }
              // ex: if ("ADouble".equals(attrName)) {((XhTestFsm)node).setADouble(Double.parseDouble((String)attrVal));return;}
              sbm.append("      if (\"")
              .append(mName.substring(3))
              .append("\".equalsIgnoreCase(attrName)) {((")
              .append(xhSimpleName)
              .append(")node).")
              .append(mName)
              .append("(");
              String paramTypeStr = paramTypes[0].getQualifiedSourceName();
              if (paramTypeStr == null) {
              }
              else if (paramTypeStr.equals(JPrimitiveType.BOOLEAN.toString())
                  || (paramTypeStr.equals("java.lang.Boolean"))) {
                sbm.append("Boolean.parseBoolean((String)attrVal));");
              }
              else if (paramTypeStr.equals(JPrimitiveType.BYTE.toString())
                  || (paramTypeStr.equals("java.lang.Byte"))) {
                sbm.append("Byte.parseByte((String)attrVal));");
              }
              else if (paramTypeStr.equals(JPrimitiveType.CHAR.toString())
                  || (paramTypeStr.equals("java.lang.Character"))) {
                sbm.append("((String)attrVal).length() > 0 ? ((String)attrVal).charAt(0) : ' ');");
              }
              else if (paramTypeStr.equals(JPrimitiveType.DOUBLE.toString())
                  || (paramTypeStr.equals("java.lang.Double"))) {
                sbm.append("Double.parseDouble((String)attrVal));");
              }
              else if (paramTypeStr.equals(JPrimitiveType.FLOAT.toString())
                  || (paramTypeStr.equals("java.lang.Float"))) {
                sbm.append("Float.parseFloat((String)attrVal));");
              }
              else if (paramTypeStr.equals(JPrimitiveType.INT.toString())
                  || (paramTypeStr.equals("java.lang.Integer"))) {
                sbm.append("Integer.parseInt((String)attrVal));");
              }
              else if (paramTypeStr.equals(JPrimitiveType.LONG.toString())
                  || (paramTypeStr.equals("java.lang.Long"))) {
                sbm.append("Long.parseLong((String)attrVal));");
              }
              else if (paramTypeStr.equals(JPrimitiveType.SHORT.toString())
                  || (paramTypeStr.equals("java.lang.Boolean"))) {
                sbm.append("Short.parseShort((String)attrVal));");
              }
              else if (paramTypeStr.equals("java.lang.String")) {
                sbm.append("(String)attrVal);");
              }
              else if (paramTypeStr.equals("java.lang.Object")) {
                // it's not clear what to do here
                sbm.append("attrVal);");
              }
              else {
                // this shouldn't happen
                sbm.append("null);");
              }
              sbm.append("return;}\n");
            }
          } // end for(j
          if (sbm.length() > 0) {
            sbt.append("    else if (\"")
            .append(appPackageName + "." + xhSimpleName)
            .append("\".equals(clazz.getName())) {\n")
            .append(sbm.toString())
            .append("    }\n");
          }
        //} // end if APP
      } // end if
    } // end for(i
    
    if (sbt.length() > 0) {
      sb
      .append("public void setAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName, Object attrVal) {\n")
      .append("  try {\n")
      .append("    if (node == null) {return;}\n")
      .append(sbt.toString())
      .append("  } catch(java.lang.NumberFormatException e) {return;}\n")
      .append("  Class superclass = clazz.getSuperclass();\n")
      .append("  if (superclass.getName().startsWith(\"")
      .append(appPackageName)
      .append("\")) {\n")
      .append("    setAppSpecificAttribute(node, superclass, attrName, attrVal); return;\n")
      .append("  }\n")
      .append("  super.setAppSpecificAttribute(node, clazz, attrName, attrVal);")
      .append("}\n");
      //System.out.println(sb.toString());
    }
    return sb.toString();
  }
  
  /* Write a getAppSpecificAttributes() method.
   * Example:
<code>
public Object[][] getAppSpecificAttributes(IXholon node, Class<IXholon> clazz, boolean returnAll) {
  List names = new ArrayList();
  List values = new ArrayList();
  List types = new ArrayList();
  if (node == null) {return new Object[0][0];}
  else if ("org.primordion.xholon.xmiapps.XhTestFsm".equals(clazz.getName())) {
    names.add("ADouble");values.add(((XhTestFsm)node).getADouble());types.add(Double.class);
    names.add("AString");values.add(((XhTestFsm)node).getAString());types.add(String.class);
    names.add("ABooleanTrue");values.add(((XhTestFsm)node).getABooleanTrue());types.add(Boolean.class);
    names.add("AChar");values.add(((XhTestFsm)node).getAChar());types.add(Character.class);
    names.add("AByte");values.add(((XhTestFsm)node).getAByte());types.add(Byte.class);
    names.add("AShort");values.add(((XhTestFsm)node).getAShort());types.add(Short.class);
    names.add("AFloat");values.add(((XhTestFsm)node).getADouble());types.add(Float.class);
    names.add("AnInteger");values.add(((XhTestFsm)node).getAnInteger());types.add(Integer.class);
    names.add("MyInteger");values.add(((XhTestFsm)node).getMyInteger());types.add(Integer.class);
    names.add("ABooleanFalse");values.add(((XhTestFsm)node).getABooleanFalse());types.add(Boolean.class);
    names.add("State");values.add(((XhTestFsm)node).getState());types.add(Integer.class);
  }
  if (returnAll) {
    Class superclass = clazz.getSuperclass();
    if (superclass.getName().startsWith("org.primordion.xholon.xmiapps")) {
      Object[][] scAttrs = getAppSpecificAttributes(node, superclass, returnAll);
      if (scAttrs != null) {
        for (int i = 0; i < scAttrs.length; i++) {
          names.add(scAttrs[i][0]);values.add(scAttrs[i][1]);types.add(scAttrs[i][2]);
        }
      }
    }
  }
  Object attributes[][] = new Object[names.size()][3];
  for (int attrIx = 0; attrIx < names.size(); attrIx++) {
    attributes[attrIx][0] = names.get(attrIx);
    attributes[attrIx][1] = values.get(attrIx);
    attributes[attrIx][2] = types.get(attrIx);
  }
  return attributes;
}
</code>
  */
  protected String writeGetAppSpecificAttributes(String appPackageName,
      JClassType[] types, JClassType iXholonType, TypeOracle oracle) {
    String xhSimpleName = null;
    StringBuilder sb = new StringBuilder();
    StringBuilder sbt = new StringBuilder();
    for(int i = 0; i < types.length; i++) {
      JClassType type = types[i];
      if ((type.isClass() != null) && (type.isAssignableTo(iXholonType))) {
        //we have determined that the type is an IXholon class (concrete or abstract, not an interface)
        xhSimpleName = type.getName();
        //if (!xhSimpleName.startsWith("App")) {
          // retrieve getter methods that don't return an IXholon
          JMethod[] methods = type.getMethods();
          StringBuilder sbm = new StringBuilder();
          for (int j = 0; j < methods.length; j++) {
            JMethod m = methods[j];
            String mName = m.getName();
            JClassType rType = oracle.findType(m.getReturnType().getQualifiedSourceName());
            // rType = null if oracle is given a primitive type (int etc.)
            if (m.isPublic() && !m.isAbstract() && !m.isStatic()
                && (m.getParameterTypes().length == 0)
                && ((rType == null) || !(rType.isAssignableTo(iXholonType)))
                && ((mName.startsWith("get")) || (mName.startsWith("is")))) {
              // ex: names.add("ADouble");values.add(((XhTestFsm)node).getADouble());types.add(Double.class);
              sbm.append("    names.add(\"")
              .append(mName.startsWith("get") ? mName.substring(3) : mName.substring(2))
              .append("\");values.add(((")
              .append(xhSimpleName)
              .append(")node).")
              .append(mName)
              .append("());types.add(");
              sbm.append(m.getReturnType().getQualifiedSourceName());
              sbm.append(".class);\n");
            }
          } // end for(j
          if (sbm.length() > 0) {
            sbt.append("  else if (\"")
            .append(appPackageName + "." + xhSimpleName)
            .append("\".equals(clazz.getName())) {\n")
            .append(sbm.toString())
            .append("  }\n");
          }
        //} // end if "App"
      } // end if
    } // end for(i
    
    if (sbt.length() > 0) {
      sb
      .append("public Object[][] getAppSpecificAttributes(IXholon node, Class<IXholon> clazz, boolean returnAll) {\n")
      .append("  List names = new ArrayList();\n")
      .append("  List values = new ArrayList();\n")
      .append("  List types = new ArrayList();\n")
      .append("  if (node == null) {return new Object[0][0];}\n")
      .append(sbt.toString())
      .append("  if (returnAll) {\n")
      .append("    Class superclass = clazz.getSuperclass();\n")
      .append("    if (superclass.getName().startsWith(\"")
      .append(appPackageName)
      .append("\")) {\n")
      .append("      Object[][] scAttrs = getAppSpecificAttributes(node, superclass, returnAll);\n")
      .append("      if (scAttrs != null) {\n")
      .append("        for (int i = 0; i < scAttrs.length; i++) {\n")
      .append("          names.add(scAttrs[i][0]);values.add(scAttrs[i][1]);types.add(scAttrs[i][2]);\n")
      .append("        }\n")
      .append("      }\n")
      .append("    }\n")
      
      .append("    else {\n")
      .append("      Object[][] scAttrs = super.getAppSpecificAttributes(node, clazz, returnAll);\n")
      .append("      if (scAttrs != null) {\n")
      .append("        for (int i = 0; i < scAttrs.length; i++) {\n")
      .append("          names.add(scAttrs[i][0]);values.add(scAttrs[i][1]);types.add(scAttrs[i][2]);\n")
      .append("        }\n")
      .append("      }\n")
      .append("    }\n")
      
      .append("  }\n")
      .append("  Object attributes[][] = new Object[names.size()][3];\n")
      .append("  for (int attrIx = 0; attrIx < names.size(); attrIx++) {\n")
      .append("    attributes[attrIx][0] = names.get(attrIx);\n")
      .append("    attributes[attrIx][1] = values.get(attrIx);\n")
      .append("    attributes[attrIx][2] = types.get(attrIx);\n")
      .append("  }\n")
      .append("  return attributes;\n")
      .append("}\n");
      //System.out.println(sb.toString());
    }
    return sb.toString();
  }
  
  /**
   * Write an isAppSpecificAttribute() method.
   */
  protected String writeIsAppSpecificAttribute(String appPackageName,
      JClassType[] types, JClassType iXholonType, TypeOracle oracle) {
    String xhSimpleName = null;
    StringBuilder sb = new StringBuilder();
    StringBuilder sbt = new StringBuilder();
    for(int i = 0; i < types.length; i++) {
      JClassType type = types[i];
      if ((type.isClass() != null) && (type.isAssignableTo(iXholonType))) {
        //we have determined that the type is an IXholon class (concrete or abstract, not an interface)
        xhSimpleName = type.getName();
        //if (!xhSimpleName.startsWith("App")) {
          // retrieve getter methods that don't return an IXholon
          JMethod[] methods = type.getMethods();
          StringBuilder sbm = new StringBuilder();
          for (int j = 0; j < methods.length; j++) {
            JMethod m = methods[j];
            String mName = m.getName();
            JType[] paramTypes = m.getParameterTypes();
            if (m.isPublic() && !m.isAbstract() && !m.isStatic()
                && (paramTypes.length == 1)
                && (paramTypes[0].isArray() == null)
                && (mName.startsWith("set"))) {
              JClassType paramType = paramTypes[0].isClassOrInterface();
              if ((paramType != null) && (paramType.isAssignableTo(iXholonType))) {
                // the param is an IXholon, so ignore it
                continue;
              }
              // ex: if ("ADouble".equals(attrName)) {return true;}
              sbm.append("    if (\"")
              .append(mName.substring(3))
              .append("\".equalsIgnoreCase(attrName)) {return true;}\n");
            }
          } // end for(j
          if (sbm.length() > 0) {
            sbt.append("  else if (\"")
            .append(appPackageName + "." + xhSimpleName)
            .append("\".equals(clazz.getName())) {\n")
            .append(sbm.toString())
            .append("  }\n");
          }
        //} // end if App
      } // end if
    } // end for(i
    
    if (sbt.length() > 0) {
      sb
      .append("public boolean isAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName) {\n")
      .append("  if (node == null) {return false;}\n")
      .append(sbt.toString())
      .append("  Class superclass = clazz.getSuperclass();\n")
      .append("  if ((superclass != null) && superclass.getName().startsWith(\"")
      .append(appPackageName)
      .append("\")) {\n")
      .append("    return isAppSpecificAttribute(node, superclass, attrName);\n")
      .append("  }\n")
      .append("  return false;\n")
      .append("}\n");
      //System.out.println(sb.toString());
    }
    return sb.toString();
  }
  
  /* Write a setAppSpecificObjectArrayVal() method.
   * Don't write this method if there are no port arrays.
   * Example:
<code>
public boolean setAppSpecificObjectArrayVal(IXholon node, Class<IXholon> clazz, String attrName, int index, IXholon val) {
  if (node == null) {return false;}
  else if ("org.primordion.user.app.testNodePorts.XhtestNodePorts".equals(clazz.getName())) {
    if ("ff".equalsIgnoreCase(attrName)) {
      //if (index >= 2) {return false;}
      ((org.primordion.user.app.testNodePorts.XhtestNodePorts)node).setFf(index, (IXholon)val);
      return true;
    }
  }
  return false;
}
</code>
  */
  protected String writeSetAppSpecificObjectArrayVal(String appPackageName, String appSimpleName,
      JClassType[] types, JClassType iXholonType) {
    String xhSimpleName = null;
    
    StringBuilder sbt = new StringBuilder(128);
    for(int i = 0; i < types.length; i++) {
      JClassType type = types[i];
      xhSimpleName = type.getName();
      
      if (!xhSimpleName.startsWith("App")) {
        // get all public setters with a single IXholon arg
        JMethod[] methods = type.getMethods();
        StringBuilder sbm = new StringBuilder(128);
        for (int j = 0; j < methods.length; j++) {
          JMethod method = methods[j];
          if (method.isPublic() && (method.getName().startsWith("set")) && (method.getName().length() > 3)) {
            JType[] paramTypes = method.getParameterTypes();
            // the paramType can be any class or interface assignable to IXholon
            // ex: public void setFf(int index, IXholon reffedNode) {ff[index] = reffedNode;}
            // TODO also check if first param is an int ?
            if (paramTypes.length == 2) {
              JClassType paramType = paramTypes[1].isClassOrInterface();
              if ((paramType != null) && (paramType.isAssignableTo(iXholonType))) {
                
                String fieldName = method.getName().substring(3);
                if (fieldName.length() == 1) {
                  // ex: "setK" becomes "k"
                  fieldName = "" + Character.toLowerCase(fieldName.charAt(0));
                }
                else {
                  // ex: "setSpace" becomes "space"
                  fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
                }
                
                sbm.append("    if (\"")
                .append(fieldName)
                .append("\".equalsIgnoreCase(attrName)) {\n")
                .append("      ((")
                .append(appPackageName + "." + xhSimpleName)
                .append(")node).")
                .append(method.getName())
                .append("(index, (")
                .append(paramType.getName())
                .append(")val);\n")
                .append("      return true;\n")
                .append("    }\n");
              }
            }
          }
        }
        if (sbm.length() != 0) {
          sbt.append("  else if (\"")
          .append(appPackageName + "." + xhSimpleName)
          .append("\".equals(clazz.getName())) {\n")
          .append(sbm.toString())
          .append("  }\n");
        }
      }
    }
    if (sbt.length() == 0) {return "";}
    
    StringBuilder sb = new StringBuilder(128)
    .append("public boolean setAppSpecificObjectArrayVal(IXholon node, Class<IXholon> clazz, String attrName, int index, IXholon val) {\n")
    .append("  if (node == null) {return false;}\n")
    .append(sbt.toString())
    .append("  return false;\n")
    .append("}\n");
    return sb.toString();
  } // end writeSetAppSpecificObjectArrayVal()
  
}
