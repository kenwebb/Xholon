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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * Export a Xholon model in SQL format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on May 9, 2017)
 */
@SuppressWarnings("serial")
public class Xholon2Sql extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat, IXmlWriter {
  
  // sbCurrentNodeAttrState attribute states
  static final private int ATTRSTATE_INITIAL      = 0;
  static final private int ATTRSTATE_CREATE_TABLE = 1;
  static final private int ATTRSTATE_INSERT_INTO  = 2;
  static final private int ATTRSTATE_VALUES       = 3;
  
  // indexes into the sqlDataType String array
  static final private int SQLDATATYPE_STRING = 0; //"varchar(80)";
  static final private int SQLDATATYPE_INT = 1; //"int";
  static final private int SQLDATATYPE_FLOAT = 2;
  static final private int SQLDATATYPE_DOUBLE = 3;
  static final private int SQLDATATYPE_LONG = 4;
  static final private int SQLDATATYPE_BOOLEAN = 5;
  
  static final private String[] SQLDATATYPE_MYSQL = {"varchar(80)", "int", "float", "double", "bigint", "bool"};
  static final private String[] SQLDATATYPE_POSTGRESQL = {"TODO", "TODO", "TODO", "TODO", "TODO", "TODO"};
  
  private String outFileName;
  private String outPath = "./ef/sql/";
  private String modelName;
  private IXholon root;
  private StringBuilder sbOut;
  private StringBuilder sbCurrentNode;
  private int sbCurrentNodeAttrState = ATTRSTATE_INITIAL;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  // default is MySql
  private String[] sqlDataType = SQLDATATYPE_MYSQL;
  
  /**
   * Constructor.
   */
  public Xholon2Sql() {}
  
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
    
    // SQL Target
    if ("MySQL".equals(getSqlTarget())) {
      sqlDataType = SQLDATATYPE_MYSQL;
    }
    else if ("PostgreSQL".equals(getSqlTarget())) {
      sqlDataType = SQLDATATYPE_POSTGRESQL;
    }
    else {
      // this should be a comma-delimited string, that needs to be turned into a Java String[]
      // TODO
    }
    
    // regenerate IDs
    if (isRegenerateIDs()) {
      this.regenerateIDs(root, 0);
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
    this.writeStartDocument();
    this.writeNode(root);
    this.writeFromXhcStringBuilder(root.getApp().getXhcRoot());
    this.writeEndDocument();
    this.writeToTarget(sbOut.toString(), outFileName, outPath, root);
  }
    
  public void writeNode(IXholon xhNode) {
    // only show state machine nodes if should show them, or if root is a StateMachineCE
    if ((xhNode.getXhcId() == CeStateMachineEntity.StateMachineCE)
        && (isShouldShowStateMachineEntities() == false)
        && xhNode != root) {
      return;
    }
    IXholonClass xhClass = xhNode.getXhc();
    String xhClassName = xhClass.getName();
    sbCurrentNode = this.findStringBuilder(xhClass);
    if (sbCurrentNode == null) {
      // for MySQL
      sbCurrentNode = this.newStringBuilder(xhClass, new StringBuilder());
      
      if (isDropTables()) {
        sbCurrentNode
        .append("DROP TABLE ")
        .append("IF EXISTS ")
        .append(xhClassName)
        .append(";\n");
      }
      if (isCreateTables()) {
        sbCurrentNode
        .append("CREATE TABLE ")
        .append("IF NOT EXISTS ")
        .append(xhClassName)
        .append(" (\n")
        .append(" ID ").append(sqlDataType[SQLDATATYPE_INT]).append(" NOT NULL auto_increment")
        .append(",\n roleName ").append(sqlDataType[SQLDATATYPE_STRING]).append(" NOT NULL default ''")
        .append(",\n xhcName ").append(sqlDataType[SQLDATATYPE_STRING]).append(" NOT NULL default ''");
        sbCurrentNodeAttrState = ATTRSTATE_CREATE_TABLE;
        writeNodeAttributes(xhNode);
        sbCurrentNode
        .append(",\n PRIMARY KEY  (ID)\n")
        .append(");\n");
      }
    }
    //INSERT INTO cw_Votes (ID, vote_item, vote_councillor, vote_result) VALUES
    // (NULL, 1, 3, 1);
    if (isInsertRows()) {
      sbCurrentNode.append("INSERT INTO ")
      .append(xhClassName)
      .append(" (ID, roleName, xhcName");
      sbCurrentNodeAttrState = ATTRSTATE_INSERT_INTO;
      writeNodeAttributes(xhNode);
      sbCurrentNode
      .append(") VALUES\n")
      .append(" (")
      .append(xhNode.getId())
      .append(", '")
      .append((xhNode.getRoleName() != null) ? xhNode.getRoleName() : "")
      .append("', '")
      .append(xhNode.getXhc().getName())
      .append("'");
      sbCurrentNodeAttrState = ATTRSTATE_VALUES;
      writeNodeAttributes(xhNode);
      sbCurrentNode.append(");\n");
    }
    // recurse through all children
    IXholon childNode = xhNode.getFirstChild();
    while (childNode != null) {
      writeNode(childNode);
      childNode = childNode.getNextSibling();
    }
  }
  
  /**
   * Find StringBuilder for the specified IXholonClass.
   * @return a pre-existing instance of StringBuilder or null.
   */
  protected native StringBuilder findStringBuilder(IXholonClass xhClass) /*-{
    return xhClass.stringBuilder;
  }-*/;
  
  /**
   * Save a new StringBuilder for the specified IXholonClass.
   * @return the new StringBuilder so the caller can use it to chain functions.
   */
  protected native StringBuilder newStringBuilder(IXholonClass xhClass, StringBuilder newSb) /*-{
    xhClass.stringBuilder = newSb;
    return newSb;
  }-*/;
  
  /**
   * Delete StringBuilder for the specified IXholonClass.
   */
  protected native void deleteStringBuilder(IXholonClass xhClass) /*-{
    delete xhClass.stringBuilder;
  }-*/;
  
  protected void writeFromXhcStringBuilder(IXholonClass xhClass) {
    StringBuilder sb = this.findStringBuilder(xhClass);
    if ((sb != null) && (sb.length() > 0)) {
      sbOut.append(sb.toString()).append("\n");
      if (isDeletePrevious()) {
        this.deleteStringBuilder(xhClass);
      }
    }
    IXholonClass childNode = (IXholonClass)xhClass.getFirstChild();
    while (childNode != null) {
      writeFromXhcStringBuilder(childNode);
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
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    
    // new
    p.sqlTarget = "MySQL"; // "MySQL" or "PostgreSQL" or a comma-delimited string of data type names
    p.dropTables = true; // whether or not to write out DROP TABLE statements
    p.createTables = true; // whether or not to write out CREATE TABLE statements
    p.insertRows = true; // whether or not to write out INSERT statements
    p.deletePrevious = true; // whether or not to call deleteStringBuilder() at end of the export
    p.parent = true; // whether or not to write parent node as an INSERT field
    p.keyName = "ID,roleName,xhcName"; // "ID" or "id" or "key" or whatever
    p.keyFormat = "^^^^i^,r^^^^^,^^C^^^"; // 
    p.regenerateIDs = false; // whether or not to regenerate all Xholon CSH IDs before doing the export; to ensure uniqueness of ID field
    
    // old
    p.shouldShowLinks = true;
    p.shouldShowStateMachineEntities = false;
    //p.nameTemplate = "r:C^^^";
    p.showXhc = false;
    p.fileNameExtension = ".sql";
    p.shouldWriteVal = false;
    p.shouldWriteAllPorts = false;
    
    this.efParams = p;
  }-*/;

  public native String getSqlTarget() /*-{return this.efParams.sqlTarget;}-*/;
  //public native void setSqlTarget(String sqlTarget) /*-{this.efParams.sqlTarget = sqlTarget;}-*/;

  public native boolean isDropTables() /*-{return this.efParams.dropTables;}-*/;
  //public native void setDropTables(boolean dropTables) /*-{this.efParams.dropTables = dropTables;}-*/;

  public native boolean isCreateTables() /*-{return this.efParams.createTables;}-*/;
  //public native void setCreateTables(boolean createTables) /*-{this.efParams.createTables = createTables;}-*/;

  public native boolean isInsertRows() /*-{return this.efParams.insertRows;}-*/;
  //public native void setInsertRows(boolean insertRows) /*-{this.efParams.insertRows = insertRows;}-*/;

  public native boolean isDeletePrevious() /*-{return this.efParams.deletePrevious;}-*/;
  //public native void setDeletePrevious(boolean deletePrevious) /*-{this.efParams.deletePrevious = deletePrevious;}-*/;

  public native boolean isParent() /*-{return this.efParams.parent;}-*/;
  //public native void setParent(boolean parent) /*-{this.efParams.parent = parent;}-*/;

  public native String getKeyName() /*-{return this.efParams.keyName;}-*/;
  //public native void setKeyName(String keyName) /*-{this.efParams.keyName = keyName;}-*/;

  public native String getKeyFormat() /*-{return this.efParams.keyFormat;}-*/;
  //public native void setKeyFormat(String keyFormat) /*-{this.efParams.keyFormat = keyFormat;}-*/;

  public native boolean isRegenerateIDs() /*-{return this.efParams.regenerateIDs;}-*/;
  //public native void setRegenerateIDs(boolean regenerateIDs) /*-{this.efParams.regenerateIDs = regenerateIDs;}-*/;

  /** Whether or not to show links between nodes. */
  public native boolean isShouldShowLinks() /*-{return this.efParams.shouldShowLinks;}-*/;
  //public native void setShouldShowLinks(boolean shouldShowLinks) /*-{this.efParams.shouldShowLinks = shouldShowLinks;}-*/;

  /** Whether or not to show state machine nodes. */
  public native boolean isShouldShowStateMachineEntities() /*-{return this.efParams.shouldShowStateMachineEntities;}-*/;
  //public native void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities) /*-{this.efParams.shouldShowStateMachineEntities = shouldShowStateMachineEntities;}-*/;

  /** Template to use when writing out node names. */
  //public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  //public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;

  /** Whether or not to include an edge from IXholon to IXholonClass. */
  public native boolean isShowXhc() /*-{return this.efParams.showXhc;}-*/;
  //public native void setShowXhc(boolean showXhc) /*-{this.efParams.showXhc = showXhc;}-*/;

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
    this.writeComment("TODO writeStartDocument()\n");
    
    StringBuilder commentSb = new StringBuilder()
    .append("\nTo view this file, download an open-source relational database product such as mysql or postgresql.\n")
    .append("\nAutomatically generated by Xholon version 0.9.1, using Xholon2Sql.java\n")
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
    switch (sbCurrentNodeAttrState) {
    case ATTRSTATE_CREATE_TABLE:
      sbCurrentNode
      .append(",\n ")
      .append(name);
      switch (Misc.getJavaDataType(value)) {
      case IJavaTypes.JAVACLASS_String:
        sbCurrentNode.append(" ").append(sqlDataType[SQLDATATYPE_STRING]).append(" NOT NULL default ''");
        break;
      case IJavaTypes.JAVACLASS_int:
        sbCurrentNode.append(" ").append(sqlDataType[SQLDATATYPE_INT]);
        break;
      case IJavaTypes.JAVACLASS_float:
        sbCurrentNode.append(" ").append(sqlDataType[SQLDATATYPE_FLOAT]);
        break;
      case IJavaTypes.JAVACLASS_double:
        sbCurrentNode.append(" ").append(sqlDataType[SQLDATATYPE_DOUBLE]);
        break;
      case IJavaTypes.JAVACLASS_long:
        sbCurrentNode.append(" ").append(sqlDataType[SQLDATATYPE_LONG]);
        break;
      case IJavaTypes.JAVACLASS_boolean:
        sbCurrentNode.append(" ").append(sqlDataType[SQLDATATYPE_BOOLEAN]);
        break;
      default:
        break;
      }
      break;
    case ATTRSTATE_INSERT_INTO:
      sbCurrentNode.append(", ").append(name);
      break;
    case ATTRSTATE_VALUES:
      switch (Misc.getJavaDataType(value)) {
      case IJavaTypes.JAVACLASS_String:
        sbCurrentNode.append(", '").append(value).append("'");
        break;
      default:
        sbCurrentNode.append(", ").append(value);
        break;
      }
      break;
    default:
      break;
    }
  }
  
  @Override
  // DO NOT IMPLEMENT THIS
  public void writeText(String text) {}

  @Override
  public void writeComment(String data) {
    sbOut.append("/* " + data + " */\n");
  }

  @Override
  public String getWriterName() {
    return "Xholon2Sql";
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
