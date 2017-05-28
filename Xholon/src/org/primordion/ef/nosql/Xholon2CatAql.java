/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2017 Ken Webb
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

package org.primordion.ef.nosql;

import java.util.Date;
import java.util.List;

import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.XhRelTypes;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
import org.primordion.xholon.util.IJavaTypes;
import org.primordion.xholon.util.Misc;

/**
 * Export a Xholon model in AQL format, which is based on Category Theory.
 * AQL is intended partly as an alternative to SQL.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://categoricaldata.net/aql.html">functorial query languages (FQLs) website</a>
 * @since 0.9.1 (Created on May 18, 2017)
 */
@SuppressWarnings("serial")
public class Xholon2CatAql extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat, IXmlWriter {
  
  //static final private String IH_ROOT_NAME = "XholonClass";
  
  private String outFileName;
  private String outPath = "./ef/aql/";
  private String modelName;
  private IXholon root;
  private IXholon currentNode; // used in conjunction with writing an attribute
  private IXholonClass xhcRoot;
  private StringBuilder sbOut;
  //private StringBuilder sbCurrentNode;
  private StringBuilder sbIhOut;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  // schema S content
  private StringBuilder sbSEntities; // entities
  private StringBuilder sbSForeignKeys; // foreign_keys
  private StringBuilder sbSPathEquations; // path_equations
  private StringBuilder sbSAttributes; // attributes
  private StringBuilder sbSObservationEquations; // observation_equations
  private StringBuilder sbSOptions; // options
  
  // instance I content
  private StringBuilder sbIGenerators; // generators
  private StringBuilder sbIEquations; // equations
  private StringBuilder sbIOptions; // options
  private StringBuilder sbICompPrec; // completion_precedence
  
  private String indent = "  ";
  
  /**
   * Constructor.
   */
  public Xholon2CatAql() {}
  
  @Override
  public String getVal_String() {
    return sbOut.toString();
  }
  
  @Override
  public boolean initialize(String outFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (outFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_"
          + timeStamp + getFileNameExtension();
    }
    else {
      this.outFileName = outFileName;
    }
    this.modelName = modelName;
    this.root = root;
    this.xhcRoot = root.getApp().getXhcRoot();
    
    // regenerate IDs
    if (isRegenerateIDs()) {
      this.regenerateIDs(root, 0);
    }
    
    sbSEntities = new StringBuilder().append(indent).append("entities");
    sbSForeignKeys = new StringBuilder().append(indent).append("foreign_keys");
    sbSPathEquations = new StringBuilder().append(indent).append("path_equations")
    .append("\n").append(indent).append(indent)
    .append("// If you run this with the AQL tool, and if the content includes infinite paths, then you may need to add one or more path_equations to prevent an error.")
    .append("\n").append(indent).append(indent)
    .append("// For example: hello_port0.world_port0 = Hello");
    sbSAttributes = new StringBuilder().append(indent).append("attributes");
    sbSObservationEquations = new StringBuilder().append(indent).append("observation_equations");
    sbSOptions = new StringBuilder().append(indent).append("options");
    
    sbIGenerators = new StringBuilder().append(indent).append("generators");
    sbIEquations = new StringBuilder().append(indent).append("equations");
    sbIOptions = new StringBuilder().append(indent).append("options");
    if (this.getCompletionPrecedence()) {
      sbICompPrec = new StringBuilder().append(indent).append(indent).append("completion_precedence = \"zero succ plus");
    }
    
    return true;
  }
  
  protected void regenerateIDs(IXholon node, int newID) {
    node.setId(newID);
    node = node.getFirstChild();
    while (node != null) {
      this.regenerateIDs(node, ++newID);
      node = node.getNextSibling();
    }
  }
  
  @Override
  public void writeAll() {
    sbOut = new StringBuilder();
    sbIhOut = new StringBuilder();
    this.writeStartDocument();
    switch (this.getTypeside()) {
    case "default": this.writeTypesideDefault(); break;
    case "java": this.writeTypesideJava(); break;
    default: break;
    }
    if (this.isIhNodes()) {
      this.writeIhNodes(root.getApp().getXhcRoot());
    }
    this.writeNode(root);
      if (this.isDeletePrevious()) {
      this.deleteXholonClassVisited(root.getApp().getXhcRoot());
    }
    //sbOut.append(sbIhOut.toString());
    sbOut
    .append("\nschema S = literal : Ty {\n")
    .append(sbSEntities.toString()).append("\n")
    .append(sbSForeignKeys.toString()).append("\n")
    .append(sbSPathEquations.toString()).append("\n")
    .append(sbSAttributes.toString()).append("\n")
    .append(sbSObservationEquations.toString()).append("\n")
    .append(sbSOptions.toString()).append("\n");
    if (this.getProver().length() > 0) {
      sbOut.append(indent).append(indent).append("prover = ").append(this.getProver()).append("\n");
    }
    sbOut.append("}\n");
    sbOut
    .append("\ninstance I = literal : S {\n")
    .append(sbIGenerators.toString()).append("\n")
    .append(sbIEquations.toString()).append("\n")
    .append(sbIOptions.toString()).append("\n");
    if (this.getProver().length() > 0) {
      sbOut.append(indent).append(indent).append("prover = ").append(this.getProver()).append("\n");
    }
    if (this.getCompletionPrecedence()) {
      sbOut.append(sbICompPrec.toString()).append("\"\n");
    }
    sbOut.append("}\n");
    this.writeEndDocument();
    this.writeToTarget(sbOut.toString(), outFileName, outPath, root);
  }
  
  protected void writeTypesideDefault() {
    sbOut
    .append("typeside Ty = literal {\n")
    .append("  types\n")
    .append("    string\n")
    .append("    int\n")
    .append("  constants\n")
    .append("    zero : int\n")
    .append("  functions\n")
    .append("    succ : int -> int\n")
    .append("    plus : int, int -> int\n")
    .append("  equations\n")
    .append("    forall x. plus(zero, x) = x\n")
    .append("    forall x, y. plus(succ(x),y) = succ(plus(x,y))\n")
    .append("  options\n")
    .append("    prover = completion\n")
    .append("}\n");
  }
  
  protected void writeTypesideJava() {
    sbOut
    .append("typeside Ty = literal {\n")
    .append("  java_types\n")
    .append("    string = \"java.lang.String\"\n")
    .append("    int = \"java.lang.Integer\"\n")
    .append("    float = \"java.lang.Float\"\n")
    .append("    double = \"java.lang.Double\"\n")
    .append("    boolean = \"java.lang.Boolean\"\n")
    .append("  java_constants\n")
    .append("    string = \"return input[0]\"\n")
    .append("    int = \"return java.lang.Integer.parseInt(input[0])\"\n")
    .append("    float = \"return java.lang.Float.parseFloat(input[0])\"\n")
    .append("    double = \"return java.lang.Double.parseDouble(input[0])\"\n")
    .append("    boolean = \"return java.lang.Boolean.parseBoolean(input[0])\"\n")
    .append("  java_functions\n")
    .append("    plus : int,int -> int = \"return (input[0] + input[1]).intValue()\"\n")
    .append("    plusf : float,float -> float = \"return input[0] + input[1]\"\n")
    .append("    plusd : double,double -> double = \"return input[0] + input[1]\"\n")
    .append("}\n");
  }
    
  public void writeNode(IXholon xhNode) {
    // only show state machine nodes if should show them, or if root is a StateMachineCE
    if ((xhNode.getXhcId() == CeStateMachineEntity.StateMachineCE)
        && (isShouldShowStateMachineEntities() == false)
        && xhNode != root) {
      return;
    }
    // only write to schema if this XholonClass hasn't been written before
    if (!this.isXholonClassVisited(xhNode.getXhc())) {
      sbSEntities
      .append("\n")
      .append(indent)
      .append(indent)
      //.append(xhNode.getXhcName())
      .append(xhNode.getXhc().getName(IXholonClass.GETNAME_REPLACE));
    }
    sbIGenerators
    .append("\n")
    .append(indent)
    .append(indent)
    .append(xhNode.getName(this.getInstanceNameTemplate()))
    .append(" : ")
    //.append(xhNode.getXhcName())
    .append(xhNode.getXhc().getName(IXholonClass.GETNAME_REPLACE));
    if (this.getCompletionPrecedence()) {
      sbICompPrec.append(" ").append(xhNode.getName(this.getInstanceNameTemplate()));
    }
    this.currentNode = xhNode;
    this.writeNodeAttributes(xhNode);
    if (this.isShouldShowLinks()) {
      this.writeLinks(xhNode);
    }
    if (isParent() && (xhNode != root)) {
      // optionally write parent node
      this.writeParent(xhNode);
    }
    this.setXholonClassVisited(xhNode.getXhc(), true);
    // recurse through all children
    IXholon childNode = xhNode.getFirstChild();
    while (childNode != null) {
      writeNode(childNode);
      childNode = childNode.getNextSibling();
    }
  }
  
  /**
   * Write parent node as a schema foreign_key:
   * hello_parent : Hello -> HelloWorldSystem
   * 
   * and as an instance equation:
   * hello_parent(hello_2) = helloWorldSystem_0
   */
  protected void writeParent(IXholon xhNode) {
    if (!this.isXholonClassVisited(xhNode.getXhc())) {
      sbSForeignKeys
      .append("\n")
      .append(indent)
      .append(indent)
      .append(xhNode.getName(this.getSchemaNameTemplate()))
      .append("_parent : ")
      //.append(xhNode.getXhcName())
      .append(xhNode.getXhc().getName(IXholonClass.GETNAME_REPLACE))
      .append(" -> ")
      //.append(xhNode.getParentNode().getXhcName())
      .append(xhNode.getParentNode().getXhc().getName(IXholonClass.GETNAME_REPLACE));
    }
    sbIEquations
    .append("\n")
    .append(indent)
    .append(indent)
    .append(xhNode.getName(this.getSchemaNameTemplate()))
    .append("_parent(")
    .append(xhNode.getName(this.getInstanceNameTemplate()))
    .append(") = ")
    .append(xhNode.getParentNode().getName(this.getInstanceNameTemplate()));    
  }
  
  protected void writeIhNodes(IXholon xhcNode) {
  }
  
  /**
   * Write an inheritance hierarchy (IH) node, and its child nodes.
   * Optionally only include app-specific IH nodes.
   * @param xhcNode The current node in the hierarchy.
   */
  protected void writeIhNode(IXholon xhcNode, String prefix) {
    if (isMechanismIhNodes() || (xhcNode.getId() < IMechanism.MECHANISM_ID_START)) {

      if (xhcNode.hasChildNodes()) {
        IXholon childXhcNode = xhcNode.getFirstChild();
        while (childXhcNode != null) {
          writeIhNode(childXhcNode, ",\n");
          childXhcNode = childXhcNode.getNextSibling();
        }
      }
    }
    else {
      // this is a mechanism node that should not be shown, but should still be navigated
      if (xhcNode.hasChildNodes()) {
        IXholon childXhcNode = xhcNode.getFirstChild();
        while (childXhcNode != null) {
          writeIhNode(childXhcNode, ",\n");
          childXhcNode = childXhcNode.getNextSibling();
        }
      }
    }
  }
  
  protected native boolean isXholonClassVisited(IXholonClass xhClass) /*-{
    var visited = xhClass.xholon2CatAql;
    if (visited == null) {
      visited = false;
    }
    return visited;
  }-*/;
  
  protected native boolean setXholonClassVisited(IXholonClass xhClass, boolean b) /*-{
    xhClass.xholon2CatAql = b;
  }-*/;
  
  /**
   * Delete boolean for the specified IXholonClass.
   */
  protected native void deleteXholonClassVisitedNative(IXholonClass xhClass) /*-{
    if (xhClass.xholon2CatAql) {
      delete xhClass.xholon2CatAql;
    }
  }-*/;
  
  protected void deleteXholonClassVisited(IXholonClass xhClass) {
    this.deleteXholonClassVisitedNative(xhClass);
    IXholonClass childNode = (IXholonClass)xhClass.getFirstChild();
    while (childNode != null) {
      deleteXholonClassVisited(childNode);
      childNode = (IXholonClass)childNode.getNextSibling();
    }
  }
  
  public void writeEdges(IXholon xhNode) {}
  
  public void writeNodeAttributes(IXholon xhNode) {
    IXholon2Xml xholon2xml = xhNode.getXholon2Xml();
    xholon2xml.setXhAttrStyle(IXholon2Xml.XHATTR_TO_XMLATTR);
    xhNode.toXmlAttributes(xholon2xml, this);
  }
  
  /**
   * Write edges from this node to any others, where Xholon has connected ports.
   * @param node The current node.
   */
  @SuppressWarnings("unchecked")
  protected void writeLinks(IXholon xhNode) {
    List<PortInformation> portList = xhNode.getLinks(false, true);
    for (int i = 0; i < portList.size(); i++) {
      PortInformation pi = (PortInformation)portList.get(i);
      String linkLabel = pi.getFieldName();
      writeLink(xhNode, pi);
    }
  }
  
  protected void writeLink(IXholon xhNode, final PortInformation pi) {
    IXholon remoteNode = pi.getReffedNode();
    if (remoteNode == null) {return;}
    if (!remoteNode.hasAncestor(root.getName())) {
      // remoteNode is outside the scope (not a descendant) of root
      return;
    }
    String linkName = xhNode.getName(this.getSchemaNameTemplate()) + "_" + pi.getLocalNameNoBrackets();
    if (!this.isXholonClassVisited(xhNode.getXhc())) {
      // pitchClassName_n : PitchClassName -> PitchClass
      sbSForeignKeys
      .append("\n").append(indent).append(indent)
      .append(linkName)
      .append(" : ")
      //.append(xhNode.getXhcName())
      .append(xhNode.getXhc().getName(IXholonClass.GETNAME_REPLACE))
      .append(" -> ")
      //.append(remoteNode.getXhcName())
      .append(remoteNode.getXhc().getName(IXholonClass.GETNAME_REPLACE));
    }
    // pitchClassName_n(pitchClassName_60) = pitchClass_47
    sbIEquations
    .append("\n")
    .append(indent)
    .append(indent)
    .append(linkName)
    .append("(")
    .append(xhNode.getName(this.getInstanceNameTemplate()))
    .append(") = ")
    .append(remoteNode.getName(this.getInstanceNameTemplate()));
    if (this.getCompletionPrecedence()) {
      sbICompPrec.append(" ").append(linkName);
    }
  }
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    
    // new for Xholon2Sql
    p.deletePrevious = true; // whether or not to call deleteXholonClassVisited() at end of the export
    p.parent = true; // whether or not to write parent node as a foreign_key and equation
    p.regenerateIDs = false; // whether or not to regenerate all Xholon CSH IDs before doing the export; to ensure uniqueness of ID field
    //p.timeStep = false; // whether or not to include the current Xholon TimeStep
    //p.foreignKeys = true; // whether or not to write out foreign key constraints
    //p.parentForeignKeys = true; // whether or not to write out parent as a foreign key constraint
    p.ihNodes = false; // whether or not to write out IH nodes
    p.mechanismIhNodes = false; // whether or not to write out IH nodes that belong to a Mechanism
    
    p.typeside = "java"; // "default" or "java"
    p.prover = ""; // "" or "completion"
    //p.completion_precedence = false; // only true if prover == "completion"
    p.schemaNameTemplate = "^^c^^^"; // "^^c^^^" is schema-based
    p.instanceNameTemplate = "^^c_i^"; // "^^c_i^" is instance-based
    
    // old
    p.shouldShowLinks = true;
    p.shouldShowStateMachineEntities = false;
    p.fileNameExtension = ".aql";
    p.shouldWriteVal = false;
    p.shouldWriteAllPorts = false;
    
    this.efParams = p;
  }-*/;

  public native boolean isDeletePrevious() /*-{return this.efParams.deletePrevious;}-*/;
  //public native void setDeletePrevious(boolean deletePrevious) /*-{this.efParams.deletePrevious = deletePrevious;}-*/;

  public native boolean isParent() /*-{return this.efParams.parent;}-*/;
  //public native void setParent(boolean parent) /*-{this.efParams.parent = parent;}-*/;

  public native boolean isRegenerateIDs() /*-{return this.efParams.regenerateIDs;}-*/;
  //public native void setRegenerateIDs(boolean regenerateIDs) /*-{this.efParams.regenerateIDs = regenerateIDs;}-*/;

  //public native boolean isTimeStep() /*-{return this.efParams.timeStep;}-*/;
  //public native void setTimeStep(boolean timeStep) /*-{this.efParams.timeStep = timeStep;}-*/;

  //public native boolean isForeignKeys() /*-{return this.efParams.foreignKeys;}-*/;
  //public native void setForeignKeys(boolean foreignKeys) /*-{this.efParams.foreignKeys = foreignKeys;}-*/;

  //public native boolean isParentForeignKeys() /*-{return this.efParams.parentForeignKeys;}-*/;
  //public native void setParentForeignKeys(boolean parentForeignKeys) /*-{this.efParams.parentForeignKeys = parentForeignKeys;}-*/;

  /** Whether or not to show links between nodes. */
  public native boolean isShouldShowLinks() /*-{return this.efParams.shouldShowLinks;}-*/;
  //public native void setShouldShowLinks(boolean shouldShowLinks) /*-{this.efParams.shouldShowLinks = shouldShowLinks;}-*/;

  /** Whether or not to show state machine nodes. */
  public native boolean isShouldShowStateMachineEntities() /*-{return this.efParams.shouldShowStateMachineEntities;}-*/;
  //public native void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities) /*-{this.efParams.shouldShowStateMachineEntities = shouldShowStateMachineEntities;}-*/;

  /** Template to use when writing out node names for a schema. */
  public native String getSchemaNameTemplate() /*-{return this.efParams.schemaNameTemplate;}-*/;
  //public native void setSchemaNameTemplate(String schemaNameTemplate) /*-{this.efParams.schemaNameTemplate = schemaNameTemplate;}-*/;

  /** Template to use when writing out node names for an instance. */
  public native String getInstanceNameTemplate() /*-{return this.efParams.instanceNameTemplate;}-*/;
  //public native void setInstanceNameTemplate(String instanceNameTemplate) /*-{this.efParams.instanceNameTemplate = instanceNameTemplate;}-*/;

  /** Whether or not to include IH nodes. */
  public native boolean isIhNodes() /*-{return this.efParams.ihNodes;}-*/;
  //public native void setIhNodes(boolean ihNodes) /*-{this.efParams.ihNodes = ihNodes;}-*/;

  /** Whether or not to include Mechanism IH nodes. */
  public native boolean isMechanismIhNodes() /*-{return this.efParams.mechanismIhNodes;}-*/;
  //public native void setMechanismIhNodes(boolean mechanismIhNodes) /*-{this.efParams.mechanismIhNodes = mechanismIhNodes;}-*/;

  public native String getTypeside() /*-{return this.efParams.typeside;}-*/;
  //public native void setTypeside(String typeside) /*-{this.efParams.typeside = typeside;}-*/;

  public native String getProver() /*-{return this.efParams.prover;}-*/;
  //public native void setProver(String prover) /*-{this.efParams.prover = prover;}-*/;
  
  public boolean getCompletionPrecedence() {
    if ("completion".equals(this.getProver())) {
      return true;
    }
    return false;
  }

  /** SQL files can be .sql or maybe something else */
  public native String getFileNameExtension() /*-{return this.efParams.fileNameExtension;}-*/;
  //public native void setFileNameExtension(String fileNameExtension) /*-{this.efParams.fileNameExtension = fileNameExtension;}-*/;

  public String getOutFileName() {
    return outFileName;
  }
  
  public void setOutFileName(String outFileName) {
    this.outFileName = outFileName;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public IXholon getRoot() {
    return root;
  }

  public void setRoot(IXholon root) {
    this.root = root;
  }

  public String getOutPath() {
    return outPath;
  }

  public void setOutPath(String outPath) {
    this.outPath = outPath;
  }

  // #############################################################################
  // methods required to implement IXmlWriter
  
  @Override
  // DO NOT IMPLEMENT THIS
  public void createNew(Object out) {}

  @Override
  public void writeStartDocument() {
    StringBuilder commentSb = new StringBuilder()
    .append("\nTo view this file, download the FQL tool (AQL) from http://categoricaldata.net/aql.html.\n")
    .append("Automatically generated by Xholon version 0.9.1, using Xholon2CatAql.java\n")
    .append(new Date())
    .append(" ")
    .append(this.timeStamp)
    .append("\n")
    .append("model: ")
    .append(this.modelName)
    .append("\n")
    .append("www.primordion.com/Xholon\n");
    this.writeComment(commentSb.toString());
  }

  @Override
  public void writeEndDocument() {
    
  }

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeStartElement(String prefix, String localName, String namespaceURI) {}

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeStartElement(String name) {}

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeEndElement(String name, String namespaceURI) {}

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeEndElement(String name) {}

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeNamespace(String prefix, String namespaceURI) {}

  @Override
  // This is for use by Xholon.toXmlAttributes() only
  public void writeAttribute(String name, String value) {
    if ("Val".equalsIgnoreCase(name) && !isShouldWriteVal()) {return;}
    if ("AllPorts".equalsIgnoreCase(name) && !isShouldWriteAllPorts()) {return;}
    String attrName = currentNode.getName(this.getSchemaNameTemplate()) + "_" + name;
    if (!this.isXholonClassVisited(currentNode.getXhc())) {
      sbSAttributes
      .append("\n").append(indent).append(indent)
      .append(attrName)
      .append(" : ")
      //.append(currentNode.getXhcName())
      .append(currentNode.getXhc().getName(IXholonClass.GETNAME_REPLACE))
      .append(" -> ");
    }
    sbIEquations
    // glucose_pheneVal(glucose_2) = 100123
    .append("\n").append(indent).append(indent)
    .append(attrName)
    .append("(")
    .append(currentNode.getName(this.getInstanceNameTemplate()))
    .append(") = ");
    String schemaType = "";
    switch (Misc.getJavaDataType(value)) {
    case IJavaTypes.JAVACLASS_String:
      schemaType = "string";
      sbIEquations.append("\"").append(value).append("\"");
      break;
    case IJavaTypes.JAVACLASS_int:
      schemaType = "int";
      sbIEquations.append(value);
      break;
    case IJavaTypes.JAVACLASS_float:
      schemaType = "float";
      sbIEquations.append("\"").append(value).append("\"");
      break;
    case IJavaTypes.JAVACLASS_double:
      schemaType = "double";
      sbIEquations.append("\"").append(value).append("\"");
      break;
    case IJavaTypes.JAVACLASS_long:
      schemaType = "long";
      sbIEquations.append(value);
      break;
    case IJavaTypes.JAVACLASS_boolean:
      schemaType = "boolean";
      sbIEquations.append(value);
      break;
    default:
      break;
    }
    if (!this.isXholonClassVisited(currentNode.getXhc())) {
      sbSAttributes.append(schemaType);
    }
    if (this.getCompletionPrecedence()) {
      sbICompPrec
      .append(" ")
      .append(attrName);
    }
  }
  
  @Override
  // DO NOT IMPLEMENT THIS
  public void writeText(String text) {}

  @Override
  public void writeComment(String data) {
    sbOut.append("/*" + data + "*/\n");
  }

  @Override
  public String getWriterName() {
    return "Xholon2CatAql";
  }

  @Override
  // DO NOT IMPLEMENT THIS
  public void flush() {}

  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#isShouldWriteVal()
   */
  public native boolean isShouldWriteVal() /*-{
    return this.efParams.shouldWriteVal;
  }-*/;
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#setShouldWriteVal()
   */
  public native void setShouldWriteVal(boolean shouldWriteVal) /*-{
    this.efParams.shouldWriteVal = shouldWriteVal;
  }-*/;
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#isShouldWriteAllPorts()
   */
  public native boolean isShouldWriteAllPorts() /*-{
    return this.efParams.shouldWriteAllPorts;
  }-*/;
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#shouldWriteAllPorts()
   */
  public native void setShouldWriteAllPorts(boolean shouldWriteAllPorts) /*-{
    this.efParams.shouldWriteAllPorts = shouldWriteAllPorts;
  }-*/;
  
}
