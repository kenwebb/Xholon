/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2009 Ken Webb
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

package org.primordion.ef.other;

//import java.io.File;
//import java.io.IOException;
//import java.io.StringWriter;
//import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.app.Application;
//import org.primordion.xholon.app.ExternallyInitializedApplication;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IReflection;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonClass;
//import org.primordion.xholon.mech.springframework.CeSpringFramework;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.NodeSelectionService;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
import org.primordion.xholon.util.ClassHelper;
//import org.primordion.xholon.util.MiscIo;
/**
 * Transform a loaded Xholon app into a Spring Framework app.
 * It generates a Spring .xml file or string.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on September 4, 2009)
 */
public class Xholon2Spring extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
	
	/** Whether or not abstract beans should be written out. */
	protected static final boolean SHOULD_WRITE_ABSTRACT_BEANS = false;
	
	/** Whether or not application-specific attributes should be written out. */
	protected static final boolean SHOULD_WRITE_APPSPECIFIC_ATTRIBUTES = true;
	
	/** Whether or not Xholon services should be written out. */
	protected static final boolean SHOULD_WRITE_XHOLON_SERVICES = false;
	
	/** Whether or not a Xholon services import should be written out. */
	protected static final boolean SHOULD_WRITE_XHOLON_SERVICES_IMPORT = true;
	
	/** Whether or not beans for SwingEntity nodes should be written out. */
	protected static final boolean SHOULD_WRITE_SWING_BEANS = false;
	
	/** A suffix to append to the id of abstract beans. */
	protected static final String ABSTRACT_BEAN_SUFFIX = "_abstract";
	
	/** Default pathname for Spring .xml files. */
	public static final String DEFAULT_SPRING_PATHNAME = "./spring/";
	
	// General Properties
	
	/** The name of the file to output. */
	protected String springFileName;
	
	/** The name of the Xholon model. */
	protected String modelName;
	
	/** A root node that should be written out. */
	protected IXholon myRoot = null;
	
	/** One or more subtrees that should be written out. */
	protected IXholon[] mySubtrees = null;
	
	/** The instance of Writer that writes to springFileName or writes an XML string. */
	//protected Writer springOut;
	protected StringBuilder sb;
	
	/** The path where the guiFileName is located. */
	protected String springPathName = DEFAULT_SPRING_PATHNAME;
	
	/** Current date and time. */
	protected Date timeNow;
	
	/** The number of milliseconds since Jan 1, 1970. Same value as timeNow. */
	protected long timeStamp;
	
	/** Whether or not to show state machines. */
	protected boolean showStates = false;
	
	/** Get local part of XholonClass name, plus id. */
	protected String nameTemplate = "^^l_i^";
	
	/** The service locator service. */
	//protected IXholon serviceLocatorService = null;
	
	/**
	 * Set of XholonClass nodes that are used by Xholon instances in this model.
	 * The nodes are stored in a Set to avoid duplicates.
	 * They are sorted by the natural IXholonClass order, alphabetical by name.
	 * */
	protected Set xholonClassSet = new TreeSet();
	
	/**
	 * List of nodes that should be written out separately as Spring framework parts.
	 * These are non-Xholons.
	 * They extend or implement some class or interface that's in the org.springframework Java package,
	 * either directly or indirectly in a hierarchy.
	 * They do not extend in any way from the Xholon class.
	 */
	protected List springObjectList = new ArrayList();

  /**
   * Constructor.
   */
  public Xholon2Spring() {}
  
	/**
	 * Initialize.
	 * @param springFileName The name of a file to write to, or null to use default file name scheme.
	 * @param modelName The name of the application/model.
	 * @param aRoot A root node (tree or subtree) that will be written out.
	 * @return true or false
	 */
	public boolean initialize(String springFileName, String modelName, IXholon aRoot) { //,
		//	IXholon serviceLocatorService) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		//this.serviceLocatorService = serviceLocatorService;
		if (aRoot == null) {
			mySubtrees = getCurrentlySelectedNodes();
			if (mySubtrees != null) {
				this.myRoot = mySubtrees[0];
			}
			else {
				return false;
			}
		}
		else {
			this.myRoot = aRoot;
		}
		if (springFileName == null) {
			this.springFileName = springPathName + this.myRoot.getXhcName()
				+ "_" + this.myRoot.getId() + "_" + timeStamp + ".xml";
		}
		else {
			this.springFileName = springFileName;
		}
		this.modelName = modelName;
		return true;
	}
	
	/**
	 * Get the IXholon nodes that are currently selected.
	 * @return An array of nodes, or null.
	 */
	protected IXholon[] getCurrentlySelectedNodes()
	{
		//if (serviceLocatorService != null) {
			IXholon nss = myRoot.getService(IXholonService.XHSRV_NODE_SELECTION);
			if (nss != null) {
				IMessage nodesMsg = nss.sendSyncMessage(
					NodeSelectionService.SIG_GET_SELECTED_NODES_REQ, null, this);
				return (IXholon[])nodesMsg.getData();
			}
		//}
		return null;
	}
	
	/**
	 * Write all nodes as a file.
	 */
	/*public void writeAllFile()
	{
		// create any missing output directories
		File dirOut = new File(springPathName);
		dirOut.mkdirs(); // will create a new directory only if there is no existing one
		springOut = MiscIo.openOutputFile(springFileName);
		sb = new StringBuilder();
		writeAll();
		MiscIo.closeOutputFile(springOut);
	}*/
	
	/**
	 * Write all nodes as a string.
	 */
	/*public String writeAllString()
	{
		springOut = new StringWriter();
		writeAll();
		return springOut.toString();
	}*/
	
	/**
	 * Write all nodes.
	 */
	public void writeAll()
	{
	  sb = new StringBuilder();
		//try {
			// write file header
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			sb.append(
				"<!--\nAutomatically generated by Xholon version 0.8, using "
				+ this.getClass().getName() + ".java\n"
				+ new Date() + " " + timeStamp + "\n"
				+ "model: " + modelName + "\n"
				+ "www.primordion.com/Xholon\n-->\n");
			sb.append("<beans xmlns=\"http://www.springframework.org/schema/beans\"\n");
			sb.append("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
			sb.append("    xsi:schemaLocation=\"http://www.springframework.org/schema/beans \n");
			sb.append("    http://www.springframework.org/schema/beans/spring-beans-2.0.xsd\">\n");
			
			if (SHOULD_WRITE_XHOLON_SERVICES_IMPORT && !SHOULD_WRITE_XHOLON_SERVICES) {
				if (!myRoot.getXhc().hasAncestor("XholonService")) {
					// Don't need the Xholon services import if the intent of writing this file
					// is to write the Xholon services.
					sb.append("\n  <import resource=\"XholonInternalServices.xml\"/>\n");
				}
			}
			
			sb.append("\n  <!-- Xholon instance beans -->\n");
			if (mySubtrees == null) {
				writeNode(myRoot, 1); // root is level 0
			}
			else {
				for (int i = 0; i < mySubtrees.length; i++) {
					myRoot = mySubtrees[i];
					writeNode(myRoot, 1);
				}
			}
			if (SHOULD_WRITE_XHOLON_SERVICES) {
				writeXholonServices(1);
			}
			if (SHOULD_WRITE_ABSTRACT_BEANS) {
				sb.append("\n  <!-- Spring abstract beans -->\n");
				writeAbstractBeans(1);
			}
			sb.append("  <!-- XholonClass instance beans -->\n");
			writeXholonClassNodes(1);
			sb.append("  <!-- Application instance bean -->\n");
			writeApplication(1);
			sb.append("  <!-- Spring framework beans -->\n");
			writeSpringFrameworkBeans(1);
			sb.append("\n</beans>\n");
			writeToTarget(sb.toString(), springFileName, springPathName, myRoot);
		//} catch (IOException e) {
		//	Xholon.getLogger().error("writeAll()", e);
		//}
		//Misc.closeOutputFile(springOut);
	}
	
	/**
	 * Write one Xholon node (bean), and its child nodes.
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeNode(IXholon node, int level)
	{
		if (!SHOULD_WRITE_SWING_BEANS && (node.getXhc().hasAncestor("SwingEntity"))) {
			// ignore Swing nodes
			return;
		}
		if (isSpringFrameworkPart(node)) {
			// this is a Spring framework part and needs to be written separately as a non-Xholon
			springObjectList.add(node);
			// TODO don't write children now; assume they are all Attribute nodes ?
		}
		else {
			writeNodeDetails(node, level);
			if (!isChildNodesSelected(node)) {
				// children
				IXholon childNode = node.getFirstChild();
				while (childNode != null) {
					writeNode(childNode, level);
					childNode = childNode.getNextSibling();
				}
			}
		}
	}
	
	/**
	 * Is at least one child node explicitly selected to be written out?
	 * If this is true, then each child node will have its own separate chance
	 * to be written out or not to be written out.
	 * @param node
	 * @return true or false
	 */
	protected boolean isChildNodesSelected(IXholon node)
	{
		// if the number of subtrees is 1, then it must be that subtree currently being traversed
		if ((mySubtrees == null) || (mySubtrees.length < 2)) {return false;}
		IXholon aChild = node.getFirstChild();
		while (aChild != null) {
			for (int i = 0; i < mySubtrees.length; i++) {
				if (aChild == mySubtrees[i]) {
					return true; // found a match
				}
			}
			aChild = aChild.getNextSibling();
		}
		return false;
	}
	
	/**
	 * Is this node a part of the Spring framework, and not a Xholon?
	 * @param node The current node in the Xholon hierarchy.
	 * @return true or false
	 */
	protected boolean isSpringFrameworkPart(IXholon node)
	{
		/* commented out for GWT version
		if (node.getXhc().hasAncestor(CeSpringFramework.SpringFrameworkEntityCE)) {
			Object obj = node.getVal_Object();
			if (obj == null) {
				// There's no way to tell what Spring framework class might implement this,
				// so keep it as a Xholon.
				// But possibly log a warning. Every SpringFrameworkEntity should specify
				// what Java class implements it.
				return false;
			}
			return true;
		}*/
		return false;
	}
	
	/**
	 * Write the details of one Xholon node (bean).
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeNodeDetails(IXholon node, int level)
	{
		//try {
			indent(level);
			sb.append("<bean id=\""
					+ node.getName(nameTemplate) + "\""); // Spring id
			if (SHOULD_WRITE_ABSTRACT_BEANS) {
				sb.append(" parent=\""
					+ node.getXhc().getLocalPart() + ABSTRACT_BEAN_SUFFIX + "\""); // Spring parent
			}
			else {
				sb.append(" class=\""
					+ node.getClass().getName() + "\""); // Spring class
			}
			sb.append(">\n");
			writePropertyValue(level+1, "id", Integer.toString(node.getId()));
			writePropertyRef(level+1, "xhc", node.getXhc().getLocalPart());
			if (node.getRoleName() != null) {
				writePropertyValue(level+1, "roleName", node.getRoleName());
			}
			if (node.getUid() != null) {
				writePropertyValue(level+1, "uid", node.getUid());
			}
			if (SHOULD_WRITE_APPSPECIFIC_ATTRIBUTES) {
				writeAppSpecificAttributes(node, level+1);
			}
			writeParentChildSibling(node, level+1);
			writePorts(node, level+1);
			indent(level);
			sb.append("</bean>\n\n");
		//} catch (IOException e) {
		//	Xholon.getLogger().error("writeNodeDetails()", e);
		//}
		xholonClassSet.add(node.getXhc());
	}
	
	/**
	 * Write a node's parentNode, firstChild, and nextSibling as Spring property elements.
	 * TODO executing this method may be optional, depending on the application
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeParentChildSibling(IXholon node, int level)
	{
		// parent node
		if (!node.isRootNode()) {
			// don't include Control nodes
			if (!"Control".equals(node.getParentNode().getXhcName())) {
				if (isSpringFrameworkPart(node.getParentNode())) {
					// TODO try to connect to some other node instead
				}
				else {
					writePropertyRef(level, "parentNode", node.getParentNode().getName(nameTemplate));
				}
			}
		}
		// first child
		if (node.hasChildNodes()) {
			if (isSpringFrameworkPart(node.getFirstChild())) {
				// TODO try to connect to some other node instead
			}
			else {
				writePropertyRef(level, "firstChild", node.getFirstChild().getName(nameTemplate));
			}
		}
		// next sibling
		if (node.getNextSibling() != null) {
			if (isSpringFrameworkPart(node.getNextSibling())) {
				// TODO try to connect to a subsequent sibling
			}
			else {
				writePropertyRef(level, "nextSibling", node.getNextSibling().getName(nameTemplate));
			}
		}
	}
	
	/**
	 * Write a node's ports.
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	protected void writePorts(IXholon node, int level)
	{
		boolean portFieldNameFound = false; // "port" field name
		Iterator it = node.getAllPorts().iterator();
		while (it.hasNext()) {
			PortInformation pi = (PortInformation)it.next();
			//try {
				if ("port".equals(pi.getFieldName())) {
					if (portFieldNameFound == false) {
						// add the first indexed "port"; port[0]
						indent(level);
						sb.append("<property name=\"" + pi.getFieldName() + "\">\n");
						indent(level+1);
						sb.append("<list>\n");
						indent(level+2);
						sb.append("<ref bean=\""
								+ pi.getReffedNode().getName(nameTemplate)
								+ "\"/>\n");
						portFieldNameFound = true;
					}
					else {
						// add subsequent indexed ports; port[n]
						indent(level+2);
						sb.append("<ref bean=\""
								+ pi.getReffedNode().getName(nameTemplate)
								+ "\"/>\n");
					}
				}
				else {
					if (portFieldNameFound == true) {
						// all indexed "port" have been found, so close out the list
						indent(level+1);
						sb.append("</list>\n");
						indent(level);
						sb.append("</property>\n");
						portFieldNameFound = false;
					}
					String reffedName = pi.getReffedNode().getName(nameTemplate);
					if ((reffedName != null) && (!reffedName.startsWith("unknownClassName"))) {
						writePropertyRef(level, pi.getFieldName(), reffedName);
					}
				}
			//} catch (IOException e) {
			//	Xholon.getLogger().error("writePorts()", e);
			//}
		}
		if (portFieldNameFound == true) {
			//try {
				// all indexed "port" have been found, so close out the list
				indent(level+1);
				sb.append("</list>\n");
				indent(level);
				sb.append("</property>\n");
				portFieldNameFound = false;
			//} catch (IOException e) {
			//	Xholon.getLogger().error("writePorts()", e);
			//}
		}
	}
	
	/**
	 * Write XholonClass nodes.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeXholonClassNodes(int level)
	{
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
		writeXholonClassNodeDetails(myRoot.getApp().getXhcRoot(), level, thisNode);
		// write all the children
		while (thisNode != null) {
			writeXholonClassNodeDetails(thisNode, level, nextNode);
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
	 * @param level
	 */
	protected void writeXholonClassNodeDetails(IXholonClass node, int level, IXholonClass childOrSibling)
	{
		//try {
			indent(level);
			sb.append("<bean id=\""
					+ node.getLocalPart() + "\"");
			sb.append(" class=\""
					+ node.getClass().getName() + "\"");
			sb.append(">\n");
			writePropertyValue(level+1, "id", Integer.toString(node.getId()));
			writePropertyValue(level+1, "name", node.getLocalPart());
			writePropertyValue(level+1, "implName", getImplName(node));
			if (node != myRoot.getApp().getXhcRoot()) { // don't write out parent of the xhcRoot node itself
				writePropertyRef(level+1, "parentNode", myRoot.getApp().getXhcRoot().getLocalPart());
				// write this node's nextSibling
				if (childOrSibling != null) {
					writePropertyRef(level+1, "nextSibling", childOrSibling.getLocalPart());
				}
			}
			else {
				// write first child of the xhcRoot
				if (childOrSibling != null) {
					writePropertyRef(level+1, "firstChild", childOrSibling.getLocalPart());
				}
			}
			writePropertyRef(level+1, "app", "Application");
			indent(level);
			sb.append("</bean>\n\n");
		//} catch (IOException e) {
		//	Xholon.getLogger().error("writeNodeDetails()", e);
		//}
	}
	
	/**
	 * Write abstract Spring beans.
	 * An abstract bean is just a convenience bean, that encapsulates a Java class name,
	 * for use by one or more concrete Xholon nodes. An abstract bean is an optional Spring
	 * feature with no direct parallel in Xholon.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeAbstractBeans(int level)
	{
		Iterator it = xholonClassSet.iterator();
		while (it.hasNext()) {
			writeAbstractBeanDetails((IXholonClass)it.next(), level);
		}
	}
	
	/**
	 * Write the details of one abstract Spring bean.
	 * An abstract bean is just a convenience bean, that encapsulates a Java class name,
	 * for use by one or more concrete Xholon nodes. An abstract bean is an optional Spring
	 * feature with no direct parallel in Xholon.
	 * @param node The current XholonClass node.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeAbstractBeanDetails(IXholonClass node, int level)
	{
		String implName = getImplName(node);
		//try {
			indent(level);
			sb.append("<bean id=\""
					+ node.getLocalPart() + ABSTRACT_BEAN_SUFFIX // Spring id
					+ "\" abstract=\"true"
					+ "\" class=\""
					+ implName // Spring class
					+ "\">\n");
			indent(level);
			sb.append("</bean>\n");
		//} catch (IOException e) {
		//	Xholon.getLogger().error("writeAbstractBeanDetails()", e);
		//}
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
				// get the default implName from myRoot's ultimate root
				implName = myRoot.getRootNode().getClass().getName();
				break;
			}
		}
		return implName;
	}
	
	/**
	 * Write Application node.
	 * @param level
	 */
	protected void writeApplication(int level)
	{
		if (myRoot.getXhc().hasAncestor("XholonService")) {
			// Don't write the Application bean if the intent of writing this file
			// is to write just the Xholon services.
			return;
		}
		//try {
			indent(level);
			sb.append("<bean id=\""
					+ "Application"
					+ "\" class=\""
					//+ ExternallyInitializedApplication.class.getName()
					+ myRoot.getApp().getClass().getName()
					+ "\">\n");
			writePropertyValue(level+1, "modelName",
					((Application)myRoot.getApp()).getModelName());
			writePropertyValue(level+1, "maxProcessLoops",
					Integer.toString(((Application)myRoot.getApp()).getMaxProcessLoops()));
			writePropertyValue(level+1, "timeStepInterval",
					Integer.toString(((Application)myRoot.getApp()).getTimeStepInterval()));
			writePropertyValue(level+1, "IQueueImplName", // the 'I' has to be uppercase
					((Application)myRoot.getApp()).getIQueueImplName());
			writePropertyValue(level+1, "sizeMessageQueue",
					Integer.toString(((Application)myRoot.getApp()).getSizeMessageQueue()));
			writePropertyValue(level+1, "maxPorts",
					Integer.toString(((Application)myRoot.getApp()).getMaxPorts()));
			writePropertyValue(level+1, "useInteractions",
					Boolean.toString(((Application)myRoot.getApp()).getUseInteractions()));
			writePropertyValue(level+1, "interactionParams",
					((Application)myRoot.getApp()).getInteractionParams());
			writePropertyRef(level+1, "root",
					((Application)myRoot.getApp()).getRoot().getName(nameTemplate));
			writePropertyRef(level+1, "xhcRoot",
					((Application)myRoot.getApp()).getXhcRoot().getName());
			writePropertyRef(level+1, "srvRoot",
					((Application)myRoot.getApp()).getSrvRoot().getName(nameTemplate));
			indent(level);
			sb.append("</bean>\n\n");
		//} catch (IOException e) {
		//	Xholon.getLogger().error("writeApplication()", e);
		//}
	}
	
	/**
	 * Write application specific attributes.
	 * @param node
	 * @param level
	 */
	protected void writeAppSpecificAttributes(IXholon node, int level)
	{
		IReflection ir = ReflectionFactory.instance();
		// get attributes that belong directly to the concrete Java class, excluding statics
		Object attribute[][] = ir.getAppSpecificAttributes(node, true, false, false);
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
			writePropertyValue(level, name, value.toString());
		}
	}
	
	/**
	 * Write Xholon services.
	 * @param level
	 */
	protected void writeXholonServices(int level)
	{
		IXholon node = myRoot.getRootNode().getNextSibling();
		if (node != null) {
			writeNode(node, level);
		}
	}
	
	/**
	 * Write all Spring framework beans.
	 * These are non-Xholons.
	 * @param level
	 */
	protected void writeSpringFrameworkBeans(int level)
	{
		Iterator it = springObjectList.iterator();
		while (it.hasNext()) {
			writeSpringFrameworkBean((IXholon)it.next(), level);
		}
	}
	
	/**
	 * Write one Spring framework bean.
	 * @param node
	 * @param level
	 */
	protected void writeSpringFrameworkBean(IXholon node, int level)
	{
		//System.out.println("SFbean:" + node);
		Object obj = node.getVal_Object();
		String className = null;
		if (obj instanceof String) {
			// this is almost certainly the implName
			className = (String)obj;
		}
		else {
			// this is almost certainly an instance of the wrapped/proxied class
			className = obj.getClass().getName();
		}
		
		//try {
			indent(level);
			sb.append("<bean id=\""
					+ node.getName(nameTemplate) + "\"");
			sb.append(" class=\""
					+ className + "\"");
			sb.append(">\n");
			// TODO write children which may be Attribute nodes
			// TODO write refs
			if (SHOULD_WRITE_APPSPECIFIC_ATTRIBUTES) {
				writeAppSpecificAttributes(node, level+1);
			}
			//writePorts(node, level+1);
			indent(level);
			sb.append("</bean>\n\n");
		//} catch (IOException e) {
		//	Xholon.getLogger().error("writeSpringFrameworkBean()", e);
		//}
	}
	
	/**
	 * Write a Spring property that contains a value.
	 * @param level
	 * @param propertyName The name of a Spring property.
	 * @param propertyValue
	 */
	protected void writePropertyValue(int level, String propertyName, String propertyValue)
	{
		indent(level);
		StringBuffer str = new StringBuffer()
			.append("<property name=\"")
			.append(propertyName)
			.append("\" value=\"")
			.append(propertyValue)
			.append("\"/>\n");
		//try {
			sb.append(str.toString());
		//} catch (IOException e) {
		//	Xholon.getLogger().error("writePropertyValue()", e);
		//}
	}
	
	/**
	 * Write a Spring property that contains a ref.
	 * @param level
	 * @param propertyName The name of a Spring property.
	 * @param propertyRef
	 */
	protected void writePropertyRef(int level, String propertyName, String propertyRef)
	{
		indent(level);
		StringBuffer str = new StringBuffer()
			.append("<property name=\"")
			.append(propertyName)
			.append("\" ref=\"")
			.append(propertyRef)
			.append("\"/>\n");
		//try {
			sb.append(str.toString());
		//} catch (IOException e) {
		//	Xholon.getLogger().error("writePropertyRef()", e);
		//}
	}
	
	/**
	 * Indent each line in the output .xml file.
	 * @param level Current level in the hierarchy.
	 */
	protected void indent(int level)
	{
		//try {
			for (int i = 0; i < level; i++) {
				sb.append("  ");
			}
		//} catch (IOException e) {
		//	Xholon.getLogger().error("indent()", e);
		//}
	}

	public String getSpringFileName() {
		return springFileName;
	}

	public void setSpringFileName(String springFileName) {
		this.springFileName = springFileName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public IXholon getMyRoot() {
		return myRoot;
	}

	public void setMyRoot(IXholon aRoot) {
		this.myRoot = aRoot;
	}

	public String getSpringPathName() {
		return springPathName;
	}

	public void setSpringPathName(String springPathName) {
		this.springPathName = springPathName;
	}

	public boolean isShowStates() {
		return showStates;
	}

	public void setShowStates(boolean showStates) {
		this.showStates = showStates;
	}

	public String getNameTemplate() {
		return nameTemplate;
	}

	public void setNameTemplate(String nameTemplate) {
		this.nameTemplate = nameTemplate;
	}
	
}
