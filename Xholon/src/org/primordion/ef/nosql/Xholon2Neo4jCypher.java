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

package org.primordion.ef.nosql;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.XholonClass;
import org.primordion.xholon.base.XhRelTypes;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.ef.IXholon2GraphFormat;
import org.primordion.xholon.service.nosql.INoSql;
import org.primordion.xholon.util.ClassHelper;
import org.primordion.ef.AbstractXholon2ExternalFormat;

/**
 * Export a Xholon model in Neo4j Cypher format.
 *
 * possibly similar exporters:
 *   Xholon2Spring
 *   Xholon2GraphML
 *   serializers from Xholon JVM
 *   service/blueprints from Xholon JVM
 *   service/nosql from Xholon JVM
 * also look at the IH CD CSH XHN content
 * 
 * Notes:
 *   - a variable such as js or hello_2 is just a temporary variable that doesn't go in the database
 *   - I shouldn't need to include the Xholon id
 *   - may not need the csh label; use a XHN relationship instead
 *   - what is the purpose of the label in Neo4j?
 * 
 * Examples of Cypher:
CREATE (js:Person { name: "Johan", from: "Sweden", learn: "surfing" }),
(ir:Person { name: "Ian", from: "England", title: "author" }),
(rvb:Person { name: "Rik", from: "Belgium", pet: "Orval" }),
(ally:Person { name: "Allison", from: "California", hobby: "surfing" }),
(ee)-[:KNOWS {since: 2001}]->(js),(ee)-[:KNOWS {rating: 5}]->(ir),
(js)-[:KNOWS]->(ir),(js)-[:KNOWS]->(rvb),
(ir)-[:KNOWS]->(js),(ir)-[:KNOWS]->(ally),
(rvb)-[:KNOWS]->(ally)

CREATE (matrix1:Movie { id : '603', title : 'The Matrix', year : '1999-03-31' }),
(matrix2:Movie { id : '604', title : 'The Matrix Reloaded', year : '2003-05-07' }),
(matrix3:Movie { id : '605', title : 'The Matrix Revolutions', year : '2003-10-27' }),
(neo:Actor { name:'Keanu Reeves' }),
(morpheus:Actor { name:'Laurence Fishburne' }),
(trinity:Actor { name:'Carrie-Anne Moss' }),
(matrix1)<-[:ACTS_IN { role : 'Neo' }]-(neo),
(matrix2)<-[:ACTS_IN { role : 'Neo' }]-(neo),
(matrix3)<-[:ACTS_IN { role : 'Neo' }]-(neo),
(matrix1)<-[:ACTS_IN { role : 'Morpheus' }]-(morpheus),
(matrix2)<-[:ACTS_IN { role : 'Morpheus' }]-(morpheus),
(matrix3)<-[:ACTS_IN { role : 'Morpheus' }]-(morpheus),
(matrix1)<-[:ACTS_IN { role : 'Trinity' }]-(trinity),
(matrix2)<-[:ACTS_IN { role : 'Trinity' }]-(trinity),
(matrix3)<-[:ACTS_IN { role : 'Trinity' }]-(trinity)

 * this is tentative
CREATE (helloWorld_0:HelloWorld {}),
(hello_2:Hello { state: 0 }),   or (csh2:Hello {})
(world_3:World { roleName: "one", state: 0 }),
(world_4:World { roleName: "two", state: 0 }),
(app:Application {
  modelName: "Hello World",
  inheritanceHierarchyFile: "config/helloworldjnlp/InheritanceHierarchy.xml",
  compositeStructureHierarchyFile: "config/helloworldjnlp/CompositeStructureHierarchy.xml",
  classDetailsFile: "config/helloworldjnlp/ClassDetails.xml",
  maxProcessLoops: 10
}),
(Hello:XholonClass {name: "Hello", implName: "org.primordion.user.app.helloworldjnlp.XhHelloWorld"}),
  or (ih1:Hello {name: "Hello"
(helloWorld_0)-[:FIRST_CHILD]->(hello_2),
(hello_2)-[:NEXT_SIBLING]->(world_3),
(hello_2)-[:PORT]->(world_3),
(world_3)-[:PORT]->(hello_2),
(hello_2)-[:XHC]->(Hello)  or (csh2)-[:XHC]->(ih1:Hello)

 * simple pure structure with no properties
 * Cypher won't accept _-.XholonClass , so I'm using XholonClass
 * all nodes must be part of the same graph
 * I've tested this out at http://www.neo4j.org/console
 * instead of using :PORT relationship, could use port property on ih2 and ih3
 *   but there may be multiple ports, and each port has multiple properties,
 *   so I may want to have port nodes
 * node csh0 has an Annotation child node csh1
CREATE
(app:Application {
  modelName: "Hello World",
  inheritanceHierarchyFile: "config/helloworldjnlp/InheritanceHierarchy.xml",
  compositeStructureHierarchyFile: "config/helloworldjnlp/CompositeStructureHierarchy.xml",
  classDetailsFile: "config/helloworldjnlp/ClassDetails.xml",
  maxProcessLoops: 10
}),
(ih0:XholonClass),
(ih1:HelloWorldSystem),
(ih2:Hello),
(ih3:World),
(csh0),
(csh2),
(csh3),
(ih0)-[:FIRST_CHILD]->(ih1),
(ih0)-[:APP]->(app),
(ih1)-[:NEXT_SIBLING]->(ih2),
(ih2)-[:NEXT_SIBLING]->(ih3),
(csh0)-[:FIRST_CHILD]->(csh2),
(csh2)-[:NEXT_SIBLING]->(csh3),
(csh2)-[:PORT]->(csh3),
(csh3)-[:PORT]->(csh2),
(csh0)-[:XHC]->(ih1),
(csh2)-[:XHC]->(ih2),
(csh3)-[:XHC]->(ih3)
 *
 * this works
CREATE (csh0),(csh2),(csh3),(csh0)-[:FIRST_CHILD]->(csh2)-[:NEXT_SIBLING]->(csh3)
 *
 * TODO
 * - Neo4j can't handle "E+" or "e" with exponential notation; it can handle "E" or "E-"
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on April 17, 2014)
 * @see <a href="http://www.neo4j.org/">Neo4j website</a>
 */
@SuppressWarnings("serial")
public class Xholon2Neo4jCypher extends AbstractXholon2ExternalFormat implements IXholon2GraphFormat {
  
  private static final String CSH_VARIABLE_NAME = "csh"; // Composite Structure Hierarchy domain-specific nodes
  private static final String SRV_VARIABLE_NAME = "srv"; // Service hierarchy nodes
  private static final String MECH_VARIABLE_NAME = "mech"; // Mechanism hierarchy nodes
  private static final String CNTRL_VARIABLE_NAME = "cntrl"; // Control hierarchy nodes
  
  private String outFileName;
  private String outPath = "./ef/cypher/";
  private String fileNameExtension = ".cypher";
  private String modelName;
  private IXholon root;
  private IApplication app;
  private StringBuilder sbNode; // nodes
  private StringBuilder sbRel; // relationships
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  // each boolean specifies whether or not to write out a certain type of data
  
  //private boolean ihPortProperties = true;
  //private boolean cshPortRelationships = true;
  
  //private boolean firstChildRelationships = true;
  //private boolean nextSiblingRelationships = true;
  //private boolean parentNodeRelationships = false;
  
  // separate booleans for nodes and relationships ?
  //private boolean xhcNodes = true;
  //private boolean mechNodes = true;
  //private boolean controlNodes = true;
  //private boolean viewNodes = false;
  //private boolean serviceNodes = true;
  
  //private boolean appNode = true;
  //private boolean appProperties = true;
  
  //private boolean relationships = true;
  //private boolean properties = true;
  //private boolean appSpecificProperties = true;
  
  //private boolean stateMachines = false;
  //private boolean headerComment = false;
  //private boolean decorations = true; // xhc and mech nodes may contain decorations
  
  //private String ihVariableName = "ih"; // Neo4j Cypher variable for Xholon IH nodes
  //private String mechVariableName = MECH_VARIABLE_NAME;
  //private String srvVariableName = SRV_VARIABLE_NAME;
  //private String cntrlVariableName = CNTRL_VARIABLE_NAME;
  //private String cshVariableName = CSH_VARIABLE_NAME; // Neo4j Cypher variable for Xholon CSH nodes
  //private String cshNameTemplate = IXholon.GETNAME_NOROLENAME; // "^^c_i^"
  //private boolean useCshVariableName = false; // true (cshVariableName), false (cshNameTemplate)
  
  //private boolean restApi = true; // use Neo4j REST API
  
  /**
   * Set of XholonClass nodes that are used by Xholon instances in this model.
   * The nodes are stored in a Set to avoid duplicates.
   * They are sorted by the natural IXholonClass order, alphabetical by name. ???
   * */
  protected Set xholonClassSet = new TreeSet();
  
  /**
   * Set of Mechanism nodes that are used by XholonClass instances in this model.
   * The nodes are stored in a Set to avoid duplicates.
   * They are sorted by the natural IMechanism order, alphabetical by name. ???
   * */
  protected Set mechanismSet = new TreeSet();
  
  /** constructor */
  public Xholon2Neo4jCypher() {}
  
  @Override
  public boolean initialize(String outFileName, String modelName, IXholon aRoot) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (outFileName == null) {
      this.outFileName = outPath + aRoot.getXhcName() + "_" + aRoot.getId() + "_"
          + timeStamp + fileNameExtension;
    }
    else {
      this.outFileName = outFileName;
    }
    this.modelName = modelName;
    this.root = aRoot;
    this.app = aRoot.getApp();
  
    return true;
  }
  
  @Override
  public void writeAll() {
    sbNode = new StringBuilder();
    sbRel = new StringBuilder();
    writeHeaderComment();
    sbNode.append("CREATE\n");
    writeNode(root);
    writeXholonServices();
    writeControlNodes();
    writeXholonClassNodes();
    writeMechanismNodes();
    writeApplication();
    sbNode.append(sbRel.toString());
    String sbNodeStr = sbNode.toString();
    writeToTarget(sbNodeStr, outFileName, outPath, root);
    createUsingRestApi(sbNodeStr);
  }
  
  /**
   * Write a Cypher comment at the top.
   */
  protected void writeHeaderComment() {
    if (!isHeaderComment()) {return;}
    sbNode.append("// Automatically generated by Xholon version 0.9.1, using ")
    .append(this.getClass().getName())
    .append(".java\n// ")
    .append(new Date()).append(" ").append(timeStamp).append("\n// ")
    .append("model: ").append(modelName).append("\n// ")
    .append("www.primordion.com/Xholon\n\n")
    .append("// For info on Neo4j and Cypher, see http://www.neo4j.org/ and http://docs.neo4j.org/\n")
    .append("// To test this generated Cypher content, visit http://www.neo4j.org/learn/cypher\n\n");
  }
  
  @Override
  public void writeNode(IXholon node) {
    // write a CSH node
    // (csh2), or (hello_2),
    if (node != this.root) {
      sbNode.append(",\n");
    }
    sbNode.append("(");
    String cshName = makeNeo4jCshVariable(node);
    sbNode.append(cshName);
    writeNodeAttributes(node);
    // store node's XholonClass
    xholonClassSet.add(node.getXhc());
    // write XHC relationship from CSH node to IH node
    if (isXhcNodes()) {
      writeRelationship(cshName, XhRelTypes.XHC, null, makeNeo4jIhVariable(node.getXhc()));
    }
    // write child nodes
    sbNode.append(")");
    IXholon childNode = node.getFirstChild();
    String childName = makeNeo4jCshVariable(childNode);
    if (isFirstChildRelationships() && (childNode != null)) {
      // (csh0)-[:FIRST_CHILD]->(csh2),
      writeRelationship(cshName, XhRelTypes.FIRST_CHILD, null, childName);
    }
    writePorts(node);
    while (childNode != null) {
      writeNode(childNode);
      childNode = childNode.getNextSibling();
      if (isNextSiblingRelationships() && (childNode != null)) {
        // (csh2)-[:NEXT_SIBLING]->(csh3),
        String childNameNext = makeNeo4jCshVariable(childNode);
        writeRelationship(childName, XhRelTypes.NEXT_SIBLING, null, childNameNext);
        childName = childNameNext;
      }
    }
  }
  
  /**
   * Write out a relationship in Cypher format.
   * ex: (csh2)-[:NEXT_SIBLING]->(csh3),
   * @param fromNodeName 
   * @param relEnum XhRelTypes
   * @param relProps 
   * @param toNodeName 
   */
  protected void writeRelationship(String fromNodeName, XhRelTypes relEnum, String relProps, String toNodeName) {
    sbRel
    .append(",\n(")
    .append(fromNodeName)
    .append(")-[:")
    .append(relEnum);
    if (relProps != null) {
      sbRel
      .append(" ")
      .append(relProps);
    }
    sbRel
    .append("]->(")
    .append(toNodeName)
    .append(")");
  }
  
  /**
   * Make a Neo4j-compatible variable from a CSH node.
   * @param node ex: an instance of a Hello node with id = 2
   * @return "csh2" or "hello_2" depending on the value of useCshVariableName
   */
  protected String makeNeo4jCshVariable(IXholon node) {
    if (node == null) {return "";}
    StringBuilder sbVar = new StringBuilder();
    if (isUseCshVariableName()) {
      sbVar
      .append(getCshVariableName())
      .append(node.getId());
    }
    else {
      String v = node.getName(getCshNameTemplate());
      // handle prefixed Attribute_ nodes  ex: "attr:attr:Attribute_String_30"
      if (v.startsWith("attr:")) {
        v = v.substring(5);
        if (v.startsWith("attr:")) {
          v = v.substring(5);
        }
        v = v.substring(0,1).toLowerCase() + v.substring(1);
      }
      sbVar.append(v);
    }
    return sbVar.toString();
  }
  
  /**
   * Make a Neo4j-compatible variable from a Service node.
   * @param node ex: an instance of a Service node with id = 1234
   * @return "srv1234" or "xpathService_1234" depending on the value of useCshVariableName
   */
  protected String makeNeo4jSrvVariable(IXholon node) {
    if (node == null) {return "";}
    StringBuilder sbVar = new StringBuilder();
    if (isUseCshVariableName()) {
      sbVar
      .append(getSrvVariableName())
      .append(node.getId());
    }
    else {
      String v = node.getName(getCshNameTemplate());
      sbVar.append(v);
    }
    return sbVar.toString();
  }
  
  /**
   * Make a Neo4j-compatible variable from a IH node.
   * @param xhcNode ex: an instance of a XholonClass node with id = 1
   * @return ex: "ih1"
   */
  protected String makeNeo4jIhVariable(IXholon xhcNode) {
    if (xhcNode == null) {return "";}
    StringBuilder sbVar = new StringBuilder();
    sbVar
    .append(getIhVariableName())
    .append(xhcNode.getId());
    return sbVar.toString();
  }
  
  /**
   * Make a Neo4j-compatible variable from a MECH node.
   * @param mechNode ex: an instance of a Mechanism node with defaultPrefix = "xh"
   * @return ex: "mechxh"
   */
  protected String makeNeo4jMechVariable(IMechanism mechNode) {
    if (mechNode == null) {return "";}
    StringBuilder sbVar = new StringBuilder();
    sbVar
    .append(getMechVariableName())
    .append(mechNode.getDefaultPrefix());
    return sbVar.toString();
  }
  
  /**
   * Make a Neo4j-compatible variable from a Control node.
   * @param cntrlNode ex: an instance of a Control node with id = 9
   * @return ex: "cntrl9"
   */
  protected String makeNeo4jCntrlVariable(IXholon cntrlNode) {
    if (cntrlNode == null) {return "";}
    StringBuilder sbVar = new StringBuilder();
    sbVar
    .append(getCntrlVariableName())
    .append(cntrlNode.getId());
    return sbVar.toString();
  }
  
  @Override
  public void writeEdges(IXholon node) {}
  
  @Override
  public void writeNodeAttributes(IXholon node) {
    if (!isProperties()) {return;}
    String comma = ""; // "" for first attribute, or "," for additional attributes
    StringBuilder sbAttr = new StringBuilder();
    if (node.getRoleName() != null) {
      writeStringProperty("roleName", node.getRoleName(), comma, sbAttr);
      comma = ",";
    }
    if (node.getUid() != null) {
      writeStringProperty("uid", node.getUid(), comma, sbAttr);
      comma = ",";
    }
    writeAppSpecificAttributes(node, sbAttr, comma);
    if (sbAttr.length() > 0) {
      sbNode
      .append(" {")
      .append(sbAttr.toString())
      .append("}");
    }
  }
  
  /**
   * Write application specific attributes.
   * @param node
   * @param sbAttr
   * @param comma "" or ","
   */
  protected void writeAppSpecificAttributes(IXholon node, StringBuilder sbAttr, String comma)
  {
    if (!isAppSpecificProperties()) {return;}
    // get attributes that belong directly to the concrete Java class, excluding statics
    Object attribute[][] = ReflectionFactory.instance().getAppSpecificAttributes(node, true, false, false);
    for (int i = 0; i < attribute.length; i++) {
      Class clazz = (Class)attribute[i][2];
      if (clazz.isArray()) {
        // for now, ignore arrays
        continue;
      }
      // TODO for now, should ignore collections and anything else that is not a primitive
      String name = (String)attribute[i][0];
      if (name == null) {continue;}
      if ("roleName".equalsIgnoreCase(name)) {continue;} // already written out
      else if ("uid".equalsIgnoreCase(name)) {continue;} // already written out
      else if ("uri".equalsIgnoreCase(name)) {continue;}
      Object value = attribute[i][1];
      if (ClassHelper.isAssignableFrom(IXholon.class, value.getClass())) {
        // don't write attributes of type IXholon; writePorts() will do these
        continue;
      }
      if (value == null) {continue;}
      // convert first character of the name to lowercase
      name = "" + Character.toLowerCase(name.charAt(0)) + name.substring(1);
      if ((value instanceof Double) && ((Double)value == Double.MAX_VALUE)) {
        // don't write attributes of type Double with MAX_VALUE, which indicates no value has been set
        continue;
      }
      sbAttr
      .append(comma)
      .append("\n")
      .append(name)
      .append(": ");
      if (value instanceof String) {
        sbAttr.append("\"").append(value).append("\"");
      }
      else {
        sbAttr.append(value);
      }
      comma = ",";
    }
  }
  
  /**
   * Write Xholon services.
   */
  protected void writeXholonServices() {
    if (!isServiceNodes()) {return;}
    IXholon node = root.getRootNode().getNextSibling();
    if (node != null) {
      setCshVariableName(SRV_VARIABLE_NAME); // "srv"
      writeNode(node);
      setCshVariableName(CSH_VARIABLE_NAME); // set it back to "csh"
    }
  }
  
  /**
   * Write Control hierarchy.
   * See Control_CompositeHierarchy.xml for details of the structure.
   */
  protected void writeControlNodes() {
    if (!isControlNodes()) {return;}
    IXholon node = app.getControlRoot(); // Controller node which has Control children and siblings
    if ((node != null) && ("Control".equals(node.getXhcName()))) {
      writeControlNode(node);
    }
  }
  
  /**
   * Write a Control node and its firstChild and nextSibling.
   * @param node A non-null Control node.
   * Examples:
(cntrl1 {roleName: "Controller"}),
(cntrl2 {roleName: "Start"}),
...
(cntrl11 {roleName: "MechanismHierarchy"}),
   */
  protected void writeControlNode(IXholon node) {
    String cntrlVarName = makeNeo4jCntrlVariable(node);
    sbNode
    .append(",\n(")
    .append(cntrlVarName)
    .append(" {");
    writeStringProperty("roleName", node.getRoleName(), "", sbNode);
    sbNode.append("})");
    // store node's XholonClass
    xholonClassSet.add(node.getXhc());
    // write XHC relationship from CSH node to IH node
    if (isXhcNodes()) {
      writeRelationship(cntrlVarName, XhRelTypes.XHC, null, makeNeo4jIhVariable(node.getXhc()));
    }
    // firstChild
    IXholon child = node.getFirstChild();
    if ((child != null) && ("Control".equals(child.getXhcName()))) {
      writeRelationship(cntrlVarName, XhRelTypes.FIRST_CHILD, null, makeNeo4jCntrlVariable(child));
      writeControlNode(child);
    }
    // nextSibling
    IXholon sib = node.getNextSibling();
    if ((sib != null) && ("Control".equals(sib.getXhcName()))) {
      writeRelationship(cntrlVarName, XhRelTypes.NEXT_SIBLING, null, makeNeo4jCntrlVariable(sib));
      writeControlNode(sib);
    }
  }
  
  /**
   * Write a node's ports.
   * @param node The current node in the Xholon hierarchy.
   * Examples:
(csh2)-[:PORT {fieldName: "abc", index: 1}]->(csh3),
(csh3)-[:PORT]->(csh2),
   */
  protected void writePorts(IXholon node) {
    if (!isCshPortRelationships()) {return;}
    boolean portFieldNameFound = false; // "port" field name
    Iterator it = node.getAllPorts().iterator();
    StringBuilder sbPort = new StringBuilder();
    String nodeName = makeNeo4jCshVariable(node);
    while (it.hasNext()) {
      PortInformation pi = (PortInformation)it.next();
      String reffedName = makeNeo4jCshVariable(pi.getReffedNode());
      if ((reffedName.length() > 0) && (!reffedName.startsWith("unknownClassName"))) {
        sbPort
        .append(",\n(")
        .append(nodeName)
        .append(")-[:")
        .append(XhRelTypes.PORT);
        if ("port".equals(pi.getFieldName())) {
          if (pi.getFieldNameIndex() > 0) {
            sbPort
            .append(" {index: ")
            .append(pi.getFieldNameIndex())
            .append("}");
          }
        }
        else {
          sbPort
          .append(" {fieldName: \"")
          .append(pi.getFieldName())
          .append("\"}");
        }
        sbPort.append("]->(")
        .append(reffedName)
        .append(")");
      }
    }
    sbRel.append(sbPort.toString());
  }
  
  /**
   * Write XholonClass nodes.
   */
  protected void writeXholonClassNodes() {
    if (!isXhcNodes()) {return;}
    // write all the IXholonClass that have IXholon instances
    Iterator it = xholonClassSet.iterator();
    IXholonClass thisNode = null;
    IXholonClass nextNode = null;
    if (it.hasNext()) {
      thisNode = (IXholonClass)it.next();
    }
    if (it.hasNext()) {
      nextNode = (IXholonClass)it.next();
    }
    // write the xhcRoot node first
    writeXholonClassNodeDetails(app.getXhcRoot(), thisNode);
    // write all the children
    while (thisNode != null) {
      writeXholonClassNodeDetails(thisNode, nextNode);
      thisNode = nextNode;
      if (it.hasNext()) {
        nextNode = (IXholonClass)it.next();
      }
      else {
        nextNode = null;
      }
    }
  }
  
  /**
   * Write the details of one XholonClass node.
   * @param node
   * @param childOrSibling
   * Examples:
(ih0:XholonClass),
(ih1:HelloWorldSystem),
...
(ih0)-[:FIRST_CHILD]->(ih1),
(ih0)-[:APP]->(app), // top-level XholonClass only
(ih1)-[:NEXT_SIBLING]->(ih2),
(ih2)-[:NEXT_SIBLING]->(ih3),
(ih2)-[:MECH]->(mech1234),
   */
  protected void writeXholonClassNodeDetails(IXholonClass node, IXholonClass childOrSibling) {
    String ihVarName = makeNeo4jIhVariable(node);
    sbNode
    .append(",\n(")
    .append(ihVarName)
    .append(":")
    .append(node.getLocalPart());
    sbNode.append(" {");
    sbNode.append("\nid").append(": ").append(node.getId());
    sbNode.append(",\nxhType").append(": ").append(node.getXhType());
    if (node.isPrefixed() == true) {
      sbNode.append(",\nprefixed").append(": ").append(true);
    }
    writeStringProperty("implName", getImplName(node), ",", sbNode);
    writeStringProperty("defaultContent", node.getDefaultContent(), ",", sbNode);
    if (node.getPrototype() != null) {
      writeStringProperty("prototype", node.getPrototype().toString(), ",", sbNode); // a JavaScriptObject
    }
    writeStringProperty("childSuperClass", node.getChildSuperClass(), ",", sbNode);
    writeDecorations(node.getDecoration());
    
    // List<PortInformation> portInformation
    if (isIhPortProperties()) {
      StringBuilder sbPi = new StringBuilder();
      Iterator<PortInformation> piIt = node.getPortInformation().iterator();
      while (piIt.hasNext()) {
        PortInformation pi = piIt.next();
        Object obj = pi.getVal_Object(); // a GWT JavaScriptObject
        sbPi.append(pi.toString()).append("\n"); //obj.toString());
      }
      if (sbPi.length() > 0) {
        // TODO format this in a more Neo4j-consistent way
        writeStringProperty("portInformation", sbPi.toString(), ",", sbNode);
      }
    }
    
    if (isMechNodes()) {
      // store XholonClass node's Mechanism
      mechanismSet.add(node.getMechanism());
      // write MECH relationship from IH node to MECH node
      writeRelationship(ihVarName, XhRelTypes.MECH, null, makeNeo4jMechVariable(node.getMechanism()));
    }    
    if (node != app.getXhcRoot()) { // don't write out parent and nextSibling of the xhcRoot node itself
      // write this node's nextSibling
      if (childOrSibling != null) {
        writeRelationship(ihVarName, XhRelTypes.NEXT_SIBLING, null, makeNeo4jIhVariable(childOrSibling));
      }
    }
    else {
      // write first child of the xhcRoot
      if (childOrSibling != null) {
        writeRelationship(ihVarName, XhRelTypes.FIRST_CHILD, null, makeNeo4jIhVariable(childOrSibling));
      }
    }
    sbNode.append("})");
  }
  
  /**
   * Write mechanism nodes.
   */
  protected void writeMechanismNodes() {
    if (!isMechNodes()) {return;}
    // write all the IMechanism that have IXholonClass instances
    Iterator it = mechanismSet.iterator();
    IMechanism thisNode = null;
    IMechanism nextNode = null;
    if (it.hasNext()) {
      thisNode = (IMechanism)it.next();
    }
    if (it.hasNext()) {
      nextNode = (IMechanism)it.next();
    }
    // write the mechRoot node first
    writeMechanismNodeDetails(app.getMechRoot(), thisNode);
    // write all the children
    while (thisNode != null) {
      writeMechanismNodeDetails(thisNode, nextNode);
      thisNode = nextNode;
      if (it.hasNext()) {
        nextNode = (IMechanism)it.next();
      }
      else {
        nextNode = null;
      }
    }
  }
  
  /**
   * Write the details of one Mechanism node.
   * @param node 
   * @param childOrSibling 
   */
  protected void writeMechanismNodeDetails(IMechanism node, IMechanism childOrSibling) {
    String mechVarName = makeNeo4jMechVariable(node);
    sbNode
    .append(",\n(")
    .append(mechVarName)
    .append(":")
    .append(node.getName())
    .append(")");
  }
  
  /**
   * Write decorations for an xhc or mech node.
   * @param decoration An instance of IDecoration, or null.
   */
  protected void writeDecorations(IDecoration decoration) {
    if (!isDecorations() || (decoration == null)) {return;}
    String comma = ",";
    writeStringProperty("color", decoration.getColor(), comma, sbNode);
    writeStringProperty("font", decoration.getFont(), comma, sbNode);
    writeStringProperty("icon", decoration.getIcon(), comma, sbNode);
    writeStringProperty("toolTip", decoration.getToolTip(), comma, sbNode);
    writeStringProperty("symbol", decoration.getSymbol(), comma, sbNode);
    writeStringProperty("format", decoration.getFormat(), comma, sbNode);
  }
  
  /**
   * Write a Neo4j String property.
   * @param attrName ex: "color"
   * @param attrValue ex: "0xFF0000"
   * @param comma "" or ","
   */
  protected void writeStringProperty(String attrName, String attrValue, String comma,
      StringBuilder sbStringProperty) {
    if (attrValue == null) {return;}
    sbStringProperty
    .append(comma)
    .append("\n")
    .append(attrName)
    .append(": \"")
    .append(attrValue)
    .append("\"");
  }
  
  /**
   * Get the implementation name of the Java class that an IXholonClass node knows about.
   * @param node
   * @return
   */
  protected String getImplName(IXholonClass node)
  {
    IXholonClass implNameNode = node;
    String implName = implNameNode.getImplName();
    IXholon implNameNodeParent = implNameNode.getParentNode();
    while (implName == null) {
      if (implNameNodeParent instanceof XholonClass) {
        implNameNode = (IXholonClass)implNameNodeParent;
        implName = implNameNode.getImplName();
        implNameNodeParent = implNameNode.getParentNode();
      }
      else {
        // the root node of the inheritance hierarchy has been reached, and implName has not been found
        // get the default implName from root's ultimate root
        implName = root.getRootNode().getClass().getName();
        break;
      }
    }
    return implName;
  }
  
  /**
   * Write Application node, properties and relationships.
   */
  protected void writeApplication() {
    if (!isAppNode()) {return;}
    if (isServiceNodes() && root.getXhc().hasAncestor("XholonService")) {
      // Don't write the Application if the intent of writing this file
      // is to write just the Xholon services.
      return;
    }
    sbNode.append(",\n(app:Application");
    if (isAppProperties()) {
      Object appData[][] = ReflectionFactory.instance().getAttributes(app);
      if ((appData != null) && (appData.length > 0)) {
        sbNode.append(" {\n");
        sbNode.append(appData[0][0]).append(": \"").append(appData[0][1]).append("\"");
        for (int i = 1; i < appData.length; i++) {
          String pname = (String)appData[i][0];
          Object pvalue = appData[i][1];
          sbNode
          .append(",\n")
          .append(pname)
          .append(": ");
          if (pvalue instanceof String) {
            sbNode.append("\"").append(pvalue).append("\"");
          }
          else {
            sbNode.append(pvalue);
          }
          // only get the properties that are specific for Application; assume that "ModelName" is the last one
          if ("ModelName".equals(pname)) {break;}
        }
        sbNode.append("\n}");
      }
    }
    sbNode.append(")");
    // (app)-[:PORT {fieldName: "root"}]->(csh0)
    writeRelationship("app", XhRelTypes.PORT, "{fieldName: \"root\"}",
        makeNeo4jCshVariable(app.getRoot()));
    if (isXhcNodes()) {
      // (app)-[:PORT {fieldName: "xhcRoot"}]->(ih0)
      writeRelationship("app", XhRelTypes.PORT, "{fieldName: \"xhcRoot\"}",
          makeNeo4jIhVariable(app.getXhcRoot()));
    }
    if (isServiceNodes()) {
      // (app)-[:PORT {fieldName: "srvRoot"}]->(srv0)
      writeRelationship("app", XhRelTypes.PORT, "{fieldName: \"srvRoot\"}",
          makeNeo4jSrvVariable(app.getSrvRoot()));
    }
    if (isMechNodes()) {
      // (app)-[:PORT {fieldName: "mechRoot"}]->(mech0)
      writeRelationship("app", XhRelTypes.PORT, "{fieldName: \"mechRoot\"}",
          makeNeo4jMechVariable(app.getMechRoot()));
    }
    if (isControlNodes()) {
      // (app)-[:PORT {fieldName: "cntrlRoot"}]->(cntrl0)
      writeRelationship("app", XhRelTypes.PORT, "{fieldName: \"cntrlRoot\"}",
          makeNeo4jCntrlVariable(app.getControlRoot()));
    }
  }
  
  /**
   * Create new Neo4j database content from a Cypher statement using the Neo4j REST API.
   * @param cypherStatement A Cypher CREATE statement.
   * ex: "CREATE (n)"
   */
  protected void createUsingRestApi(String cypherStatement) {
    if (!isRestApi()) {return;}
    
    IXholon service = root.getService(IXholonService.XHSRV_NOSQL + "-Neo4jRestApi");
    if (service != null) {
      service.sendSyncMessage(INoSql.SIG_ADDALL_TOGRAPH_REQ, cypherStatement, root);
    }
    
    //testRestApi();
  }
  
  protected void testRestApi() {
    IXholon service = root.getService(IXholonService.XHSRV_NOSQL + "-Neo4jRestApi");
    if (service == null) {
      service = root.getService(IXholonService.XHSRV_NOSQL);
    }
    if (service != null) {
      //service.sendMessage(INoSql.SIG_TEST_REQ, null, root); // doesn't work in Cell Model; OK in Hello World
      service.sendSyncMessage(INoSql.SIG_TEST_REQ, null, root); // works
    }
  }
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.ihPortProperties = true;
    p.cshPortRelationships = true;
    p.firstChildRelationships = true;
    p.nextSiblingRelationships = true;
    p.parentNodeRelationships = false;
    p.xhcNodes = true;
    p.mechNodes = true;
    p.controlNodes = true;
    p.viewNodes = false;
    p.serviceNodes = true;
    p.appNode = true;
    p.appProperties = true;
    p.relationships = true;
    p.properties = true;
    p.appSpecificProperties = true;
    p.stateMachines = false;
    p.headerComment = false;
    p.decorations = true;
    p.ihVariableName = "ih";
    p.mechVariableName = "mech";
    p.srvVariableName = "srv";
    p.cntrlVariableName = "cntrl";
    p.cshVariableName = "csh";
    p.cshNameTemplate = "^^c_i^";
    p.useCshVariableName = false;
    p.restApi = true;
    this.efParams = p;
  }-*/;

  public native boolean isIhPortProperties() /*-{return this.efParams.ihPortProperties;}-*/;
  //public native void setIhPortProperties(boolean ihPortProperties) /*-{this.efParams.ihPortProperties = ihPortProperties;}-*/;

  public native boolean isCshPortRelationships() /*-{return this.efParams.cshPortRelationships;}-*/;
  //public native void setCshPortRelationships(boolean cshPortRelationships) /*-{this.efParams.cshPortRelationships = cshPortRelationships;}-*/;

  public native boolean isFirstChildRelationships() /*-{return this.efParams.firstChildRelationships;}-*/;
  //public native void setFirstChildRelationships(boolean firstChildRelationships) /*-{this.efParams.firstChildRelationships = firstChildRelationships;}-*/;

  public native boolean isNextSiblingRelationships() /*-{return this.efParams.nextSiblingRelationships;}-*/;
  //public native void setNextSiblingRelationships(boolean nextSiblingRelationships) /*-{this.efParams.nextSiblingRelationships = nextSiblingRelationships;}-*/;

  public native boolean isParentNodeRelationships() /*-{return this.efParams.parentNodeRelationships;}-*/;
  //public native void setParentNodeRelationships(boolean parentNodeRelationships) /*-{this.efParams.parentNodeRelationships = parentNodeRelationships;}-*/;

  // separate booleans for nodes and relationships ?
  public native boolean isXhcNodes() /*-{return this.efParams.xhcNodes;}-*/;
  //public native void setXhcNodes(boolean xhcNodes) /*-{this.efParams.xhcNodes = xhcNodes;}-*/;

  public native boolean isMechNodes() /*-{return this.efParams.mechNodes;}-*/;
  //public native void setMechNodes(boolean mechNodes) /*-{this.efParams.mechNodes = mechNodes;}-*/;

  public native boolean isControlNodes() /*-{return this.efParams.controlNodes;}-*/;
  //public native void setControlNodes(boolean controlNodes) /*-{this.efParams.controlNodes = controlNodes;}-*/;

  public native boolean isViewNodes() /*-{return this.efParams.viewNodes;}-*/;
  //public native void setViewNodes(boolean viewNodes) /*-{this.efParams.viewNodes = viewNodes;}-*/;

  public native boolean isServiceNodes() /*-{return this.efParams.serviceNodes;}-*/;
  //public native void setServiceNodes(boolean serviceNodes) /*-{this.efParams.serviceNodes = serviceNodes;}-*/;

  public native boolean isAppNode() /*-{return this.efParams.appNode;}-*/;
  //public native void setAppNode(boolean appNode) /*-{this.efParams.appNode = appNode;}-*/;

  public native boolean isAppProperties() /*-{return this.efParams.appProperties;}-*/;
  //public native void setAppProperties(boolean appProperties) /*-{this.efParams.appProperties = appProperties;}-*/;

  public native boolean isRelationships() /*-{return this.efParams.relationships;}-*/;
  //public native void setRelationships(boolean relationships) /*-{this.efParams.relationships = relationships;}-*/;

  public native boolean isProperties() /*-{return this.efParams.properties;}-*/;
  //public native void setProperties(boolean properties) /*-{this.efParams.properties = properties;}-*/;

  public native boolean isAppSpecificProperties() /*-{return this.efParams.appSpecificProperties;}-*/;
  //public native void setAppSpecificProperties(boolean appSpecificProperties) /*-{this.efParams.appSpecificProperties = appSpecificProperties;}-*/;

  public native boolean isStateMachines() /*-{return this.efParams.stateMachines;}-*/;
  //public native void setStateMachines(boolean stateMachines) /*-{this.efParams.stateMachines = stateMachines;}-*/;

  public native boolean isHeaderComment() /*-{return this.efParams.headerComment;}-*/;
  //public native void setHeaderComment(boolean headerComment) /*-{this.efParams.headerComment = headerComment;}-*/;

  // xhc and mech nodes may contain decorations
  public native boolean isDecorations() /*-{return this.efParams.decorations;}-*/;
  //public native void setDecorations(boolean decorations) /*-{this.efParams.decorations = decorations;}-*/;

  // Neo4j Cypher variable for Xholon IH nodes
  public native String getIhVariableName() /*-{return this.efParams.ihVariableName;}-*/;
  //public native void setIhVariableName(String ihVariableName) /*-{this.efParams.ihVariableName = ihVariableName;}-*/;

  public native String getMechVariableName() /*-{return this.efParams.mechVariableName;}-*/;
  //public native void setMechVariableName(String mechVariableName) /*-{this.efParams.mechVariableName = mechVariableName;}-*/;

  public native String getSrvVariableName() /*-{return this.efParams.srvVariableName;}-*/;
  //public native void setSrvVariableName(String srvVariableName) /*-{this.efParams.srvVariableName = srvVariableName;}-*/;

  public native String getCntrlVariableName() /*-{return this.efParams.cntrlVariableName;}-*/;
  //public native void setCntrlVariableName(String cntrlVariableName) /*-{this.efParams.cntrlVariableName = cntrlVariableName;}-*/;

  // Neo4j Cypher variable for Xholon CSH nodes
  public native String getCshVariableName() /*-{return this.efParams.cshVariableName;}-*/;
  public native void setCshVariableName(String cshVariableName) /*-{this.efParams.cshVariableName = cshVariableName;}-*/;

  // "^^c_i^"
  public native String getCshNameTemplate() /*-{return this.efParams.cshNameTemplate;}-*/;
  //public native void setCshNameTemplate(String cshNameTemplate) /*-{this.efParams.cshNameTemplate = cshNameTemplate;}-*/;

  // true (cshVariableName), false (cshNameTemplate)
  public native boolean isUseCshVariableName() /*-{return this.efParams.useCshVariableName;}-*/;
  //public native void setUseCshVariableName(boolean useCshVariableName) /*-{this.efParams.useCshVariableName = useCshVariableName;}-*/;

  // use Neo4j REST API
  public native boolean isRestApi() /*-{return this.efParams.restApi;}-*/;
  //public native void setRestApi(boolean restApi) /*-{this.efParams.restApi = restApi;}-*/;
  
}
