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
//import org.primordion.xholon.base.IReflection;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.XholonClass;
import org.primordion.xholon.base.XhRelTypes;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.util.ClassHelper;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2GraphFormat;

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
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on April 17, 2014)
 * @see <a href="http://www.neo4j.org/">Neo4j website</a>
 */
@SuppressWarnings("serial")
public class Xholon2Neo4jCypher extends AbstractXholon2ExternalFormat implements IXholon2GraphFormat {
	
	private static final String CSH_VARIABLE_NAME = "csh"; // Composite Structure Hierarchy domain-specific nodes
	private static final String SRV_VARIABLE_NAME = "srv"; // Service hierarchy nodes
	
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
	
	private boolean ihPortProperties = true;
	private boolean cshPortRelationships = true;
	
	private boolean firstChildRelationships = true;
	private boolean nextSiblingRelationships = true;
	private boolean parentNodeRelationships = false;
	
	// separate booleans for nodes and relationships ?
	private boolean xhcNodes = true;
	private boolean mechNodes = false;
	private boolean controlNodes = false;
	private boolean viewNodes = false;
	private boolean serviceNodes = true;
	
	private boolean appNode = true;
	private boolean appProperties = true;
	
	private boolean relationships = true;
	private boolean properties = true;
	private boolean appSpecificProperties = true;
	
	private boolean stateMachines = false;
	private boolean headerComment = true;
	private boolean decorations = true; // xhc and mech nodes may contain decorations
	
	private String ihVariableName = "ih"; // Neo4j Cypher variable for Xholon IH nodes
	private String cshVariableName = CSH_VARIABLE_NAME; // Neo4j Cypher variable for Xholon CSH nodes
	private String cshNameTemplate = IXholon.GETNAME_NOROLENAME; // "^^c_i^"
	private boolean useCshVariableName = false; // true (cshVariableName), false (cshNameTemplate)
	
	/**
	 * Set of XholonClass nodes that are used by Xholon instances in this model.
	 * The nodes are stored in a Set to avoid duplicates.
	 * They are sorted by the natural IXholonClass order, alphabetical by name.
	 * */
	protected Set xholonClassSet = new TreeSet();
	
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
	  writeXholonClassNodes();
	  writeApplication();
	  sbNode.append(sbRel.toString());
	  writeToTarget(sbNode.toString(), outFileName, outPath, root);
	}
	
	/**
	 * Write a Cypher comment at the top.
	 */
	protected void writeHeaderComment() {
	  if (!headerComment) {return;}
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
	  writeRelationship(cshName, XhRelTypes.XHC, null, makeNeo4jIhVariable(node.getXhc()));
	  // write child nodes
	  sbNode.append(")");
	  IXholon childNode = node.getFirstChild();
	  String childName = makeNeo4jCshVariable(childNode);
	  if (firstChildRelationships && (childNode != null)) {
	    // (csh0)-[:FIRST_CHILD]->(csh2),
	    writeRelationship(cshName, XhRelTypes.FIRST_CHILD, null, childName);
	  }
	  writePorts(node);
	  while (childNode != null) {
	    writeNode(childNode);
	    childNode = childNode.getNextSibling();
	    if (nextSiblingRelationships && (childNode != null)) {
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
	  //consoleLog("makeNeo4jCshVariable( " + node.getName());
	  StringBuilder sbVar = new StringBuilder();
	  if (useCshVariableName) {
	    sbVar
	    .append(cshVariableName)
	    .append(node.getId());
	  }
	  else {
	    String v = node.getName(cshNameTemplate);
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
	  //consoleLog("makeNeo4jCshVariable( end");
	  return sbVar.toString();
	}
	
	/**
	 * Make a Neo4j-compatible variable from a IH node.
	 * @param xhcNode ex: an instance of a XholonClass node with id = 1
	 * @return ex: "ih1"
	 */
	protected String makeNeo4jIhVariable(IXholon xhcNode) {
	  if (xhcNode == null) {return "";}
	  //consoleLog("makeNeo4jIhVariable( " + xhcNode.getName());
	  StringBuilder sbVar = new StringBuilder();
    sbVar
	  .append(ihVariableName)
	  .append(xhcNode.getId());
	  //consoleLog("makeNeo4jIhVariable( end");
	  return sbVar.toString();
	}
  
  @Override
  public void writeEdges(IXholon node) {
  
  }
  
  @Override
  public void writeNodeAttributes(IXholon node) {
    if (!properties) {return;}
    String comma = ""; // "" for first attribute, or "," for additional attributes
    StringBuilder sbAttr = new StringBuilder();
    if (node.getRoleName() != null) {
			//sbAttr.append("\nroleName").append(": \"").append(node.getRoleName()).append("\"");
			writeStringProperty("roleName", node.getRoleName(), comma, sbAttr);
			comma = ",";
		}
		if (node.getUid() != null) {
			//sbAttr.append(comma).append("\nuid").append(": \"").append(node.getUid()).append("\"");
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
	  if (!appSpecificProperties) {return;}
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
			// don't write attributes of type IXholon; writePorts() will do these
			//if (IXholon.class.isAssignableFrom(value.getClass())) {
			if (ClassHelper.isAssignableFrom(IXholon.class, value.getClass())) {
				continue;
			}
			if (value == null) {continue;}
			// convert first character of the name to lowercase
			name = "" + Character.toLowerCase(name.charAt(0)) + name.substring(1);
			//writePropertyValue(level, name, value.toString());
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
	protected void writeXholonServices()
	{
	  if (!serviceNodes) {return;}
		IXholon node = root.getRootNode().getNextSibling();
		if (node != null) {
		  cshVariableName = SRV_VARIABLE_NAME; // "srv"
			writeNode(node);
			cshVariableName = CSH_VARIABLE_NAME; // set it back to "csh"
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
	  if (!cshPortRelationships) {return;}
	  consoleLog("writePorts starting");
		boolean portFieldNameFound = false; // "port" field name
		Iterator it = node.getAllPorts().iterator();
		StringBuilder sbPort = new StringBuilder();
		String nodeName = makeNeo4jCshVariable(node);
		consoleLog("writePorts " + nodeName);
		while (it.hasNext()) {
			PortInformation pi = (PortInformation)it.next();
			String reffedName = makeNeo4jCshVariable(pi.getReffedNode());
			if ((reffedName.length() > 0) && (!reffedName.startsWith("unknownClassName"))) {
				//writePropertyRef(level, pi.getFieldName(), reffedName);
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
	  if (!xhcNodes) {return;}
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
	 */
	protected void writeXholonClassNodeDetails(IXholonClass node, IXholonClass childOrSibling)
	{
	  consoleLog("writeXholonClassNodeDetails " + node);
	  String ihVarName = makeNeo4jIhVariable(node);
	  sbNode
		.append(",\n(")
		.append(ihVarName)
		.append(":")
		.append(node.getLocalPart());
		sbNode.append(" {");
		sbNode.append("\nid").append(": ").append(node.getId());
		sbNode.append(",\nxhType").append(": ").append(node.getXhType());
		consoleLog("writeXholonClassNodeDetails 1");
		if (node.isPrefixed() == true) {
		  sbNode.append(",\nprefixed").append(": ").append(true);
		}
		//sbNode.append(",\nimplName").append(": \"").append(getImplName(node)).append("\"");
		consoleLog("writeXholonClassNodeDetails 2");
		writeStringProperty("implName", getImplName(node), ",", sbNode);
		writeStringProperty("defaultContent", node.getDefaultContent(), ",", sbNode);
		consoleLog("writeXholonClassNodeDetails 3");
		if (node.getPrototype() != null) {
		  writeStringProperty("prototype", node.getPrototype().toString(), ",", sbNode); // a JavaScriptObject
		}
		consoleLog("writeXholonClassNodeDetails 4");
		writeStringProperty("childSuperClass", node.getChildSuperClass(), ",", sbNode);
		writeDecorations(node.getDecoration());
		consoleLog("writeXholonClassNodeDetails 5");
		
		// List<PortInformation> portInformation
		if (ihPortProperties) {
		  StringBuilder sbPi = new StringBuilder();
		  Iterator<PortInformation> piIt = node.getPortInformation().iterator();
		  while (piIt.hasNext()) {
		    consoleLog("writeXholonClassNodeDetails a");
			  PortInformation pi = piIt.next();
			  Object obj = pi.getVal_Object(); // a GWT JavaScriptObject
			  consoleLog("writeXholonClassNodeDetails b");
			  sbPi.append(pi.toString()).append("\n"); //obj.toString());
			  consoleLog("writeXholonClassNodeDetails c");
			  /*xmlWriter.writeStartElement("port");
			  xmlWriter.writeAttribute("name", pi.getFieldName());
			  if (pi.getFieldNameIndex() != PortInformation.PORTINFO_NOTANARRAY) { // -1
				  xmlWriter.writeAttribute("index", pi.getFieldNameIndexStr());
			  }
			  xmlWriter.writeAttribute("connector", pi.getXpathExpression());
			  xmlWriter.writeEndElement("port");*/
		  }
		  consoleLog("writeXholonClassNodeDetails d");
		  if (sbPi.length() > 0) {
		    writeStringProperty("portInformation", sbPi.toString(), ",", sbNode);
		  }
		  consoleLog("writeXholonClassNodeDetails e");
		}
		consoleLog("writeXholonClassNodeDetails 6");
		
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
  		// NO write app as a relationship, but only from the root XholonClass
  		//writeRelationship(ihVarName, XhRelTypes.APP, null, makeNeo4jIhVariable(app));
		}
		sbNode.append("})");
		consoleLog("writeXholonClassNodeDetails end");
	}
	
	/**
	 * Write decorations for an xhc or mech node.
	 * @param decoration An instance of IDecoration, or null.
	 */
	protected void writeDecorations(IDecoration decoration) {
	  if (!decorations || (decoration == null)) {return;}
	  consoleLog("writeDecorations starting");
	  String comma = ",";
	  writeStringProperty("color", decoration.getColor(), comma, sbNode);
	  writeStringProperty("font", decoration.getFont(), comma, sbNode);
	  writeStringProperty("icon", decoration.getIcon(), comma, sbNode);
	  writeStringProperty("toolTip", decoration.getToolTip(), comma, sbNode);
	  writeStringProperty("symbol", decoration.getSymbol(), comma, sbNode);
	  writeStringProperty("format", decoration.getFormat(), comma, sbNode);
	  consoleLog("writeDecorations end");
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
	  if (!appNode) {return;}
		if (serviceNodes && root.getXhc().hasAncestor("XholonService")) {
			// Don't write the Application if the intent of writing this file
			// is to write just the Xholon services.
			return;
		}
		sbNode.append(",\n(app:Application");
		if (appProperties) {
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
		writeRelationship("app", XhRelTypes.PORT, "{fieldName: \"root\"}", makeNeo4jCshVariable(app.getRoot()));
		if (xhcNodes) {
	  	// (app)-[:PORT {fieldName: "xhcRoot"}]->(ih0)
		  writeRelationship("app", XhRelTypes.PORT, "{fieldName: \"xhcRoot\"}", makeNeo4jIhVariable(app.getXhcRoot()));
		}
		if (serviceNodes) {
	  	// (app)-[:PORT {fieldName: "srvRoot"}]->(srv0)
		  writeRelationship("app", XhRelTypes.PORT, "{fieldName: \"srvRoot\"}", makeNeo4jCshVariable(app.getSrvRoot()));
		}
	}
	
}
