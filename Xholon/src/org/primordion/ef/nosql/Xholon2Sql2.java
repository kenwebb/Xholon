/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2019 Ken Webb
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
 * @since 0.9.1 (Created on December 23, 2019)
 * 
 * sample queries:
select type.type_name || '_' || node.id as name from type, node
where type.id = node.type_id;
 * 
 */
@SuppressWarnings("serial")
public class Xholon2Sql2 extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat, IXmlWriter {
  
  // sbCurrentNodeAttrState attribute states
  static final private int ATTRSTATE_INITIAL      = 0;
  static final private int ATTRSTATE_CREATE_TABLE = 1;
  static final private int ATTRSTATE_INSERT_INTO  = 2;
  static final private int ATTRSTATE_VALUES       = 3;
  
  // indexes into the sqlDataType String array
  static final private int SQLDATATYPE_STRING = 0;
  static final private int SQLDATATYPE_INT = 1;
  static final private int SQLDATATYPE_FLOAT = 2;
  static final private int SQLDATATYPE_DOUBLE = 3;
  static final private int SQLDATATYPE_LONG = 4;
  static final private int SQLDATATYPE_BOOLEAN = 5;
  
  static final private String[] SQLDATATYPE_MYSQL = {"varchar(80)", "int", "float", "double", "bigint", "bool"};
  static final private String[] SQLDATATYPE_POSTGRESQL = {"varchar(80)", "integer", "real", "double precision", "bigint", "boolean"};
  
  // indexes into idRoleXhcNames and idRoleXhcFormats
  static final private int IDROLEXHC_ID   = 0;
  static final private int IDROLEXHC_ROLE = 1;
  static final private int IDROLEXHC_XHC  = 2;
  
  //static final private String IH_ROOT_NAME = "type"; // this.getTableType(); //"XholonClass";  retrieve this from efParams
  
  private String outFileName;
  private String outPath = "./ef/sql2/";
  private String modelName;
  private IXholon root;
  private IXholonClass xhcRoot;
  private StringBuilder sbOut;
  private StringBuilder sbCurrentNode;
  private StringBuilder sbIhOut;
  private int sbCurrentNodeAttrState = ATTRSTATE_INITIAL;
  
  private String[] idRoleXhcNames = {null, null, null};
  private String[] idRoleXhcFormats = {null, null, null};
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  // default is MySql
  private String[] sqlDataType = SQLDATATYPE_MYSQL;
  
  /**
   * Constructor.
   */
  public Xholon2Sql2() {}
  
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
    
    // SQL Target
    if ("MySQL".equals(getSqlTarget())) {
      sqlDataType = SQLDATATYPE_MYSQL;
    }
    else if ("PostgreSQL".equals(getSqlTarget())) {
      sqlDataType = SQLDATATYPE_POSTGRESQL;
    }
    else if ("SQLite".equals(getSqlTarget())) {
      // in most cases, SQLite uses PostgreSQL syntax
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
    
    // make sure that each String[] item has a value, which may be null
    idRoleXhcNames = fixIdRoleXhcArr(this.getIdRoleXhcNames().split(","));
    idRoleXhcFormats = fixIdRoleXhcArr(this.getIdRoleXhcFormats().split(","));
    
    return true;
  }
  
  protected String[] fixIdRoleXhcArr(String[] inArr) {
    String[] outArr = {null, null, null};
    for (int i = 0; i < inArr.length; i++) {
      if ((inArr[i] != null) && (inArr[i].trim().length() > 0)) {
        outArr[i] = inArr[i].trim();
      }
    }
    return outArr;
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
    sbCurrentNode = new StringBuilder();
    sbIhOut = new StringBuilder();
    this.writeStartDocument();
    if (this.isIhNodes()) {
      this.writeIhNodes(root.getApp().getXhcRoot());
    }
    this.writeNodes(root);
    //this.writeFromXhcStringBuilder(root.getApp().getXhcRoot());
    sbOut.append(sbIhOut.toString()).append("\n");
    sbOut.append(sbCurrentNode.toString());
    this.writeEndDocument();
    this.writeToTarget(sbOut.toString(), outFileName, outPath, root);
  }
  
  protected void writeNodes(IXholon xhRootNode) {
    if (isDropTables()) {
      /*sbCurrentNode
      .append("DROP TABLE ")
      .append("IF EXISTS ")
      .append(this.getTableNode())
      .append(";\n");*/
      sbCurrentNode
      .append(makeTableDrop(this.getTableNode()));
    }
    /*sbCurrentNode = this.findStringBuilder(xhClass);
    if (sbCurrentNode == null) {
      sbCurrentNode = this.newStringBuilder(xhClass, new StringBuilder());
    }*/
    if (isCreateTables()) {
      /*
      sbCurrentNode
      .append("CREATE TABLE ")
      .append("IF NOT EXISTS ")
      .append(this.getTableNode())
      .append(" (\n");
      String prefix = " ";
      // ID
      if (idRoleXhcNames[IDROLEXHC_ID] != null) {
        sbCurrentNode.append(prefix).append(idRoleXhcNames[IDROLEXHC_ID]).append(" ");
        if ("^^^^i^".equals(idRoleXhcFormats[IDROLEXHC_ID])) {
          sbCurrentNode.append(sqlDataType[SQLDATATYPE_INT]);
        }
        else {
          // the ID could be a STRING "r:c_i^"
          sbCurrentNode.append(sqlDataType[SQLDATATYPE_STRING]);
        }
        sbCurrentNode.append(" NOT NULL")
        //.append(" auto_increment")
        ;
        prefix = ",\n ";
      }
      // ROLE
      if (idRoleXhcNames[IDROLEXHC_ROLE] != null) {
        sbCurrentNode.append(prefix).append(idRoleXhcNames[IDROLEXHC_ROLE]).append(" ").append(sqlDataType[SQLDATATYPE_STRING]).append(" NOT NULL")
        //.append(" default ''")
        ;
        prefix = ",\n ";
      }
      StringBuilder sbForeignKeys = null;
      if (this.isForeignKeys()) {
        sbForeignKeys = new StringBuilder();
      }
      // XHC
      if (idRoleXhcNames[IDROLEXHC_XHC] != null) {
        sbCurrentNode
        .append(prefix)
        .append(idRoleXhcNames[IDROLEXHC_XHC])
        .append(" ");
        if ("^^^^i^".equals(idRoleXhcFormats[IDROLEXHC_XHC])) {
          sbCurrentNode.append(sqlDataType[SQLDATATYPE_INT]);
          if (this.isForeignKeys()) {
            sbForeignKeys
            .append(",\n")
            .append(" FOREIGN KEY (")
            .append(idRoleXhcNames[IDROLEXHC_XHC])
            .append(") REFERENCES ")
            .append(this.getTableType())
            .append("(")
            .append(idRoleXhcNames[IDROLEXHC_ID])
            .append(")")
            ;
          }
        }
        else {
          sbCurrentNode.append(sqlDataType[SQLDATATYPE_STRING]);
        }
        sbCurrentNode.append(" NOT NULL")
        //.append(" default ''")
        ;
        prefix = ",\n ";
      }
      if (isTimeStep()) {
        sbCurrentNode.append(prefix).append("timeStep ").append(sqlDataType[SQLDATATYPE_INT]).append(" NOT NULL");
      }
      sbCurrentNodeAttrState = ATTRSTATE_CREATE_TABLE;
      //writeNodeAttributes(xhRootNode);
      // PARENT
      //if (isParent() && (xhRootNode != root)) {
        // include the ID of xhRootNode's parent
        // TODO MySQL foreign key constraint:
        //   FOREIGN KEY (PersonID) REFERENCES Persons(PersonID)  or
        //   CONSTRAINT FK_PersonOrder FOREIGN KEY (PersonID) REFERENCES Persons(PersonID)
        sbCurrentNode.append(prefix).append("parent_").append(idRoleXhcNames[IDROLEXHC_ID]).append(" ");
        if ("^^^^i^".equals(idRoleXhcFormats[IDROLEXHC_ID])) {
          sbCurrentNode.append(sqlDataType[SQLDATATYPE_INT]);
        }
        else {
          // the ID could be a STRING "r:c_i^"
          sbCurrentNode.append(sqlDataType[SQLDATATYPE_STRING]);
        }
        if (this.isParentForeignKeys()) {
          sbForeignKeys
          .append(",\n")
          .append(this.makeForeignKey(xhRootNode.getParentNode(), "parent_" + idRoleXhcNames[IDROLEXHC_ID]));
        }
        else {
          sbCurrentNode
          .append(" // ")
          .append(this.makeForeignKey(xhRootNode.getParentNode(), "parent_" + idRoleXhcNames[IDROLEXHC_ID]));
        }
      //}
      if (isShouldWriteLinks()) {
        this.writeLinks(xhRootNode, sbForeignKeys);
      }
      sbCurrentNode
      .append(",\n PRIMARY KEY (")
      .append(this.getPrimaryKey());
      if (isTimeStep()) {
        sbCurrentNode.append(", timeStep");
      }
      sbCurrentNode
      .append(")");
      if (this.isForeignKeys()) {
        sbCurrentNode
        .append(sbForeignKeys.toString());
      }
      else {
        sbCurrentNode
        .append("\n");
      }
      sbCurrentNode
      .append(");\n");
    }
    */
      // TODO there may be stuff in the preceeding commented-out line that I should keep
      sbCurrentNode
      .append(makeTableNodeCreate(this.getTableNode(), this.getTableType()));
    }
    writeNode(xhRootNode);
  }
  
  public void writeNode(IXholon xhNode) {
    // only show state machine nodes if should show them, or if root is a StateMachineCE
    if ((xhNode.getXhcId() == CeStateMachineEntity.StateMachineCE)
        && (isShouldShowStateMachineEntities() == false)
        && xhNode != root) {
      return;
    }
    //IXholonClass xhClass = xhNode.getXhc();
    //String xhClassName = xhClass.getName();
    String xhClassName = this.getTableNode();
    if (isInsertRows()) {
      sbCurrentNode.append("INSERT INTO ")
      .append(xhClassName)
      .append(" (");
      String prefix = "";
      if (idRoleXhcNames[IDROLEXHC_ID] != null) {
        sbCurrentNode.append(idRoleXhcNames[IDROLEXHC_ID]);
        prefix = ", ";
      }
      if (idRoleXhcNames[IDROLEXHC_ROLE] != null) {
        sbCurrentNode.append(prefix).append(idRoleXhcNames[IDROLEXHC_ROLE]);
        prefix = ", ";
      }
      if (idRoleXhcNames[IDROLEXHC_XHC] != null) {
        sbCurrentNode.append(prefix).append(idRoleXhcNames[IDROLEXHC_XHC]);
        prefix = ", ";
      }
      if (isTimeStep()) {
        sbCurrentNode.append(prefix).append("timeStep");
      }
      sbCurrentNodeAttrState = ATTRSTATE_INSERT_INTO;
      //writeNodeAttributes(xhNode);
      if (isParent() && (xhNode != root)) {
        // include the ID of xhNode's parent
        sbCurrentNode.append(prefix).append("parent_").append(idRoleXhcNames[IDROLEXHC_ID]);
      }
      else {
        //sbCurrentNode.append(prefix).append("xparent_").append(idRoleXhcNames[IDROLEXHC_ID]);
      }
      if (isShouldWriteLinks()) {
          this.writeLinks(xhNode, null);
        }
      sbCurrentNode
      .append(") VALUES\n")
      .append(" (");
      
      prefix = "";
      // ID
      if (idRoleXhcNames[IDROLEXHC_ID] != null) {
        if ("^^^^i^".equals(idRoleXhcFormats[IDROLEXHC_ID])) {
          // the ID is an integer
          sbCurrentNode.append(xhNode.getName(idRoleXhcFormats[IDROLEXHC_ID]));
        }
        else {
          // the ID is a String
          sbCurrentNode.append("'").append(xhNode.getName(idRoleXhcFormats[IDROLEXHC_ID])).append("'");
        }
        prefix = ", ";
      }
      // ROLE
      if (idRoleXhcNames[IDROLEXHC_ROLE] != null) {
        sbCurrentNode.append(prefix).append("'").append(xhNode.getName(idRoleXhcFormats[IDROLEXHC_ROLE])).append("'");
        prefix = ", ";
      }
      // XHC
      if (idRoleXhcNames[IDROLEXHC_XHC] != null) {
        if ("^^^^i^".equals(idRoleXhcFormats[IDROLEXHC_XHC])) {
          // the XHC Name/ID is an integer
          sbCurrentNode.append(prefix).append(xhNode.getXhcId());
        }
        else {
          // the XHC Name/ID is a String
          sbCurrentNode.append(prefix).append("'").append(xhNode.getName(idRoleXhcFormats[IDROLEXHC_XHC])).append("'");
        }
        prefix = ", ";
      }
      
      if (isTimeStep()) {
        sbCurrentNode.append(prefix).append(root.getApp().getTimeStep());
      }
      sbCurrentNodeAttrState = ATTRSTATE_VALUES;
      //writeNodeAttributes(xhNode);
      if (isParent() && (xhNode != root)) {
        if ("^^^^i^".equals(idRoleXhcFormats[IDROLEXHC_ID])) {
          // the ID is an integer
          sbCurrentNode.append(prefix).append(xhNode.getParentNode().getName(idRoleXhcFormats[IDROLEXHC_ID]));
        }
        else {
          // the ID is a String
          sbCurrentNode.append(prefix).append("'").append(xhNode.getParentNode().getName(idRoleXhcFormats[IDROLEXHC_ID])).append("'");
        }
      }
      if (isShouldWriteLinks()) {
        this.writeLinks(xhNode, null);
      }
      sbCurrentNode.append(");\n");
    }
    // recurse through all children
    IXholon childNode = xhNode.getFirstChild();
    while (childNode != null) {
      writeNode(childNode);
      childNode = childNode.getNextSibling();
    }
  }
  
  protected void writeIhNodes(IXholon xhcNode) {
    if (isDropTables()) {
      //sbIhOut
      //.append("DROP TABLE ")
      //.append("IF EXISTS ")
      //.append(IH_ROOT_NAME)
      //.append(";\n");
      sbIhOut
      .append(makeTableDrop(this.getTableType()));
    }
    if (isCreateTables()) {
      /*String prefix = " ";
      sbIhOut
      .append("CREATE TABLE ")
      .append("IF NOT EXISTS ")
      .append(IH_ROOT_NAME)
      .append(" (\n")
      .append(prefix)
      .append(idRoleXhcNames[IDROLEXHC_ID])
      .append(" ")
      .append(sqlDataType[SQLDATATYPE_INT])
      .append(" NOT NULL");
      prefix = ",\n ";
      sbIhOut
      .append(prefix)
      .append("type_name") //("xhcName")
      .append(" ")
      .append(sqlDataType[SQLDATATYPE_STRING]);
      sbIhOut
      .append(prefix)
      .append("parent_")
      .append(idRoleXhcNames[IDROLEXHC_ID])
      .append(" ")
      .append(sqlDataType[SQLDATATYPE_INT]);
      sbIhOut
      .append(prefix)
      .append("PRIMARY KEY (")
      .append(idRoleXhcNames[IDROLEXHC_ID])
      .append(")");
      // FOREIGN KEY points to parent
      if (this.isForeignKeys()) {
        sbIhOut
        .append(prefix)
        .append("FOREIGN KEY (")
        .append("parent_")
        .append(idRoleXhcNames[IDROLEXHC_ID])
        .append(") REFERENCES ")
        .append(IH_ROOT_NAME)
        .append("(")
        .append(idRoleXhcNames[IDROLEXHC_ID])
        .append(")")
        ;
      }
      sbIhOut
      .append(");\n");*/
      sbIhOut
      .append(makeTableTypeCreate(this.getTableType()));
    }
    if (isInsertRows()) {
      // INSERT INTO tbl_name (a,b,c) VALUES(1,2,3),(4,5,6),(7,8,9);
      sbIhOut.append("INSERT INTO ")
      .append(this.getTableType())
      .append(" (");
      String prefix = "";
      if (idRoleXhcNames[IDROLEXHC_ID] != null) {
        sbIhOut.append(idRoleXhcNames[IDROLEXHC_ID]);
        prefix = ", ";
      }
      if (idRoleXhcNames[IDROLEXHC_XHC] != null) {
        sbIhOut.append(prefix).append("type_name");
        prefix = ", ";
      }
      
      // include the ID of xhNode's parent
      sbIhOut
      .append(prefix)
      .append("parent_")
      .append(idRoleXhcNames[IDROLEXHC_ID])
      ;
      
      sbIhOut
      .append(") VALUES\n");

      writeIhNode(xhcNode, "");
      sbIhOut
      .append(";\n");
    }
  }
  
  /**
   * Write an inheritance hierarchy (IH) node, and its child nodes.
   * Optionally only include app-specific IH nodes.
   * @param xhcNode The current node in the hierarchy.
   */
  protected void writeIhNode(IXholon xhcNode, String prefix) {
    if (isMechanismIhNodes() || (xhcNode.getId() < IMechanism.MECHANISM_ID_START)) {
      sbIhOut
      .append(prefix)
      .append(" (")
      .append(xhcNode.getId())
      .append(", '")
      .append(xhcNode.getName())
      .append("', ");
      if (xhcNode == xhcRoot) {
        sbIhOut.append(0);
      }
      else {
        sbIhOut.append(xhcNode.getParentNode().getId());
      }
      sbIhOut
      .append(")");
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
  
  /**
   * Find StringBuilder for the specified IXholonClass.
   * @return a pre-existing instance of StringBuilder or null.
   */
  //protected native StringBuilder findStringBuilder(IXholonClass xhClass) /*-{
  //  return xhClass.stringBuilder;
  //}-*/;
  
  /**
   * Save a new StringBuilder for the specified IXholonClass.
   * @return the new StringBuilder so the caller can use it to chain functions.
   */
  //protected native StringBuilder newStringBuilder(IXholonClass xhClass, StringBuilder newSb) /*-{
  //  xhClass.stringBuilder = newSb;
  //  return newSb;
  //}-*/;
  
  /**
   * Delete StringBuilder for the specified IXholonClass.
   */
  //protected native void deleteStringBuilder(IXholonClass xhClass) /*-{
  //  delete xhClass.stringBuilder;
  //}-*/;
  
  /*protected void writeFromXhcStringBuilder(IXholonClass xhClass) {
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
  }*/
  
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
  protected void writeLinks(IXholon xhNode, StringBuilder sbForeignKeys) {
    List<PortInformation> portList = xhNode.getLinks(false, true);
    for (int i = 0; i < portList.size(); i++) {
      PortInformation pi = (PortInformation)portList.get(i);
      String linkLabel = pi.getFieldName();
      writeLink(xhNode, pi, sbForeignKeys);
    }
  }
  
  protected void writeLink(IXholon xhNode, final PortInformation pi, StringBuilder sbForeignKeys) {
    IXholon remoteNode = pi.getReffedNode();
    if (remoteNode == null) {return;}
    if (!remoteNode.hasAncestor(root.getName())) {
      // remoteNode is outside the scope (not a descendant) of root
      return;
    }
    switch (sbCurrentNodeAttrState) {
    case ATTRSTATE_CREATE_TABLE:
      // port0 int|varchar(80)
      sbCurrentNode
      .append(",\n ")
      .append(pi.getLocalNameNoBrackets())
      .append(" ");
      if ("^^^^i^".equals(idRoleXhcFormats[IDROLEXHC_ID])) {
        // the ID is an integer
        sbCurrentNode.append(sqlDataType[SQLDATATYPE_INT]);
      }
      else {
        // the ID is a String
        sbCurrentNode.append(sqlDataType[SQLDATATYPE_STRING]);
      }
      String fk = this.makeForeignKey(remoteNode, pi.getLocalNameNoBrackets());
      if (isForeignKeys()) {
        // MySQL vs PostgreSQL - they can both use the same syntax
        sbForeignKeys.append(",\n")
        .append(fk);
      }
      else {
        // write the foreign key as a comment
        sbCurrentNode
        .append(" /* ")
        .append(fk)
        .append(" */ ");
      }
      break;
    case ATTRSTATE_INSERT_INTO:
      // port0
      sbCurrentNode.append(", ").append(pi.getLocalNameNoBrackets());
      break;
    case ATTRSTATE_VALUES:
      // 17|'abc_17'
      sbCurrentNode.append(", ");
      if ("^^^^i^".equals(idRoleXhcFormats[IDROLEXHC_ID])) {
        // the ID is an integer
        sbCurrentNode.append(remoteNode.getName(idRoleXhcFormats[IDROLEXHC_ID]));
      }
      else {
        // the ID is a String
        sbCurrentNode.append("'").append(remoteNode.getName(idRoleXhcFormats[IDROLEXHC_ID])).append("'");
      }
      break;
    default:
      break;
    }
  }
  
  protected String makeForeignKey(IXholon remoteNode, String localName) {
    String refTable = remoteNode.getXhc().getName();
    if ("parent_id".equals(localName)) {
      refTable = this.getTableNode();
    }
    return new StringBuilder()
    .append(" FOREIGN KEY (")
    .append(localName)
    .append(") REFERENCES ")
    .append(refTable)
    .append("(")
    .append(idRoleXhcNames[IDROLEXHC_ID])
    .append(")")
    .toString();
  }
  
  protected String makeTableDrop(String tableName) {
    String str = "DROP TABLE IF EXISTS "
    + tableName
    + ";\n";
    return str;
  }
  
  protected String makeTableTypeCreate(String tableName) {
    String str = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
    + " id integer NOT NULL,\n"
    + " type_name varchar(80),\n"
    + " parent_id integer,\n"
    + " PRIMARY KEY (id),\n"
    + " FOREIGN KEY (parent_id) REFERENCES " + tableName + "(id)"
    + ");\n";
    return str;
  }
  
  /*
CREATE TABLE IF NOT EXISTS node (
 id integer NOT NULL,
 role_name varchar(80) NOT NULL,
 type_id integer NOT NULL,
 parent_id integer,
 PRIMARY KEY (id),
 FOREIGN KEY (type_id) REFERENCES type(id),
 FOREIGN KEY (parent_id) REFERENCES node(id));

  */
  protected String makeTableNodeCreate(String tableName, String tableNameType) {
    String str = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
    + " id integer NOT NULL,\n"
    + " role_name varchar(80) NOT NULL,\n"
    + " type_id integer NOT NULL,\n"
    + " parent_id integer,\n"
    + " PRIMARY KEY (id),\n"
    + " FOREIGN KEY (type_id) REFERENCES " + tableNameType + "(id),\n"
    + " FOREIGN KEY (parent_id) REFERENCES " + tableName + "(id)"
    + ");\n";
    return str;
  }
  
  // backtick is not supported in GWT
//  protected native String makeTableTypeCreate(String tableName) /*-{
//    return
//`CREATE TABLE IF NOT EXISTS ${tableName} (
// id integer NOT NULL,
// type_name varchar(80),
// parent_id integer,
// PRIMARY KEY (id),
// FOREIGN KEY (parent_id) REFERENCES ${tableName}(id));`;
//  }-*/;
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    
    // new
    p.sqlTarget = "PostgreSQL"; // "MySQL" or "PostgreSQL" or "SQLite" or a comma-delimited string of data type names
    p.dropTables = true; // whether or not to write out DROP TABLE statements
    p.createTables = true; // whether or not to write out CREATE TABLE statements
    p.insertRows = true; // whether or not to write out INSERT statements
    p.deletePrevious = true; // whether or not to call deleteStringBuilder() at end of the export
    p.parent = true; // whether or not to write parent node as an INSERT field
    p.idRoleXhcNames = "id,role_name,type_id"; // "ID,roleName,xhcName" or "ID" or "id" or "key" or whatever
    p.idRoleXhcFormats = "^^^^i^,r^^^^^,^^^^i^"; // "^^^^i^,r^^^^^,^^C^^^"
    p.primaryKey = "id"; // OR "ID,roleName,xhcName"; but exclude other possible constituents of the primary key such as "timeStep"
    p.regenerateIDs = false; // whether or not to regenerate all Xholon CSH IDs before doing the export; to ensure uniqueness of ID field
    p.timeStep = false; // whether or not to include the current Xholon TimeStep
    p.foreignKeys = true; // whether or not to write out foreign key constraints
    p.parentForeignKeys = true; // whether or not to write out parent as a foreign key constraint
    p.ihNodes = true; // whether or not to write out IH nodes
    p.mechanismIhNodes = true; // whether or not to write out IH nodes that belong to a Mechanism
    
    // new Dec 2019
    p.tableNode = "node"; // name of the node table  "node" "object"
    p.tableProp = "prop"; // "prop"
    p.tableLink = "link"; // "arrow"
    p.tableType = "type";
    p.tableBhvr = "bhvr";
    
    // old
    p.shouldWriteLinks = false;
    p.shouldShowStateMachineEntities = false;
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

  public native String getIdRoleXhcNames() /*-{return this.efParams.idRoleXhcNames;}-*/;
  //public native void setIdRoleXhcNames(String idRoleXhcNames) /*-{this.efParams.idRoleXhcNames = idRoleXhcNames;}-*/;

  public native String getIdRoleXhcFormats() /*-{return this.efParams.idRoleXhcFormats;}-*/;
  //public native void setIdRoleXhcFormats(String idRoleXhcFormats) /*-{this.efParams.idRoleXhcFormats = idRoleXhcFormats;}-*/;

  public native String getPrimaryKey() /*-{return this.efParams.primaryKey;}-*/;
  //public native void setPrimaryKey(String primaryKey) /*-{this.efParams.primaryKey = primaryKey;}-*/;

  public native boolean isRegenerateIDs() /*-{return this.efParams.regenerateIDs;}-*/;
  //public native void setRegenerateIDs(boolean regenerateIDs) /*-{this.efParams.regenerateIDs = regenerateIDs;}-*/;

  public native boolean isTimeStep() /*-{return this.efParams.timeStep;}-*/;
  //public native void setTimeStep(boolean timeStep) /*-{this.efParams.timeStep = timeStep;}-*/;

  public native boolean isForeignKeys() /*-{return this.efParams.foreignKeys;}-*/;
  //public native void setForeignKeys(boolean foreignKeys) /*-{this.efParams.foreignKeys = foreignKeys;}-*/;

  public native boolean isParentForeignKeys() /*-{return this.efParams.parentForeignKeys;}-*/;
  //public native void setParentForeignKeys(boolean parentForeignKeys) /*-{this.efParams.parentForeignKeys = parentForeignKeys;}-*/;

  /** Whether or not to show state machine nodes. */
  public native boolean isShouldShowStateMachineEntities() /*-{return this.efParams.shouldShowStateMachineEntities;}-*/;
  //public native void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities) /*-{this.efParams.shouldShowStateMachineEntities = shouldShowStateMachineEntities;}-*/;

  /** Template to use when writing out node names. */
  //public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  //public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;

  /** Whether or not to include IH nodes. */
  public native boolean isIhNodes() /*-{return this.efParams.ihNodes;}-*/;
  //public native void setIhNodes(boolean ihNodes) /*-{this.efParams.ihNodes = ihNodes;}-*/;

  /** Whether or not to include Mechanism IH nodes. */
  public native boolean isMechanismIhNodes() /*-{return this.efParams.mechanismIhNodes;}-*/;
  //public native void setMechanismIhNodes(boolean mechanismIhNodes) /*-{this.efParams.mechanismIhNodes = mechanismIhNodes;}-*/;

  /** Name of the node table. */
  public native String getTableNode() /*-{return this.efParams.tableNode;}-*/;
  //public native void setTableNode(String tableNode) /*-{this.efParams.tableNode = tableNode;}-*/;

  /** Name of the prop table. */
  public native String getTableProp() /*-{return this.efParams.tableProp;}-*/;
  //public native void setTableProp(String tableProp) /*-{this.efParams.tableProp = tableProp;}-*/;

  /** Name of the link table. */
  public native String getTableLink() /*-{return this.efParams.tableLink;}-*/;
  //public native void setTableLink(String tableLink) /*-{this.efParams.tableLink = tableLink;}-*/;

  /** Name of the type table. */
  public native String getTableType() /*-{return this.efParams.tableType;}-*/;
  //public native void setTableType(String tableType) /*-{this.efParams.tableType = tableType;}-*/;

  /** Name of the bhvr table. */
  public native String getTableBhvr() /*-{return this.efParams.tableBhvr;}-*/;
  //public native void setTableBhvr(String tableBhvr) /*-{this.efParams.tableBhvr = tableBhvr;}-*/;

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
    .append("\nTo view this file, download an open-source relational database product such as mysql or postgresql or sqlite.\n")
    .append("\nAutomatically generated by Xholon version 0.9.1, using Xholon2Sql2.java\n")
    .append(new Date())
    .append(" ")
    .append(this.timeStamp)
    .append("\n")
    .append("model: ")
    .append(this.modelName)
    .append("\n")
    .append("www.primordion.com/Xholon\n")
    .append("In MySQL, you will need to temporarily disable foreign keys while loading in this file:\n")
    .append("SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS;\n")
    .append("SET FOREIGN_KEY_CHECKS=0;\n")
    .append("source thenameofthisfile.sql;\n")
    .append("SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;\n")
    ;
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
    if ("Links".equalsIgnoreCase(name) && !isShouldWriteLinks()) {return;}
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
  
  @Override
  public native boolean isShouldWriteLinks() /*-{
    return this.efParams.shouldWriteLinks;
  }-*/;

  @Override
  public native void setShouldWriteLinks(boolean shouldWriteLinks) /*-{
    this.efParams.shouldWriteLinks = shouldWriteLinks;
  }-*/;
	
}
