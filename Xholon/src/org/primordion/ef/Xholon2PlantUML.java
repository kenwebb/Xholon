/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2010 Ken Webb
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

package org.primordion.ef;

//import java.io.File;
//import java.io.IOException;
//import java.io.Writer;
//import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.primordion.xholon.util.XholonSortedNode;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.IXPath;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.PortInformation;
//import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon model in PlantUML format.
 * Create a class diagram, or a state diagram.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on March 9, 2010)
 * @see <a href="http://plantuml.sourceforge.net">PlantUML web site</a>
 */
@SuppressWarnings("serial")
public class Xholon2PlantUML extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
	
	private String outFileName;
	private String outPath = "./ef/plantuml/";
	private String modelName;
	private IXholon root;
	//private Writer out;
	private StringBuilder sb;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/** Whether or not to show state machine nodes. */
	private boolean shouldShowStateMachineEntities = false;
	
	/** Template to use when writing out node names. */
	//protected String nameTemplate = "r:C^^^";
	protected String nameTemplate = "^^C^^^"; // don't include role name
	
	/** Root of the binary tree that sorts and counts Xholon nodes. */
	private XholonSortedNode sortRoot = null;
	
	/** The root XholonClass. */
	private IXholonClass rootXholonClass = null;
	
	/** An instance of XPath. */
	private IXPath xpath = null;
	
	/** Whether or not to show associations between classes. */
	private boolean shouldShowClassAssociations = false;
	
	/**
	 * Whether to get associations between classes,
	 * from the XholonClass (true),
	 * or from an instance of Xholon (false).
	 */
	private boolean shouldUseXholonClassAssociations = false;

	/**
	 * Constructor.
	 */
	public Xholon2PlantUML() {}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String mmFileName, String modelName, IXholon root) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (mmFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".txt";
		}
		else {
			this.outFileName = mmFileName;
		}
		this.modelName = modelName;
		this.root = root;
		rootXholonClass = root.getApp().getClassNode("XholonClass");
		xpath = (IXPath)root.getService(IXholonService.XHSRV_XPATH);
		createSortedTree();
		return true;
	}
	
	@SuppressWarnings("unchecked")
	protected void createSortedTree() {
		sortRoot = new XholonSortedNode();
		sortRoot.setVal((Object)root.getXhc());
		Iterator<IXholon> it = root.getChildNodes(true).iterator();
		while (it.hasNext()) {
			IXholon node = it.next();
			if (!shouldShowStateMachineEntities) {
				if (node.getXhcId() == CeStateMachineEntity.StateMachineCE) {continue;}
			}
			sortRoot.add(node.getXhc());
			// superclasses
			IXholonClass xholonSuperclass = (IXholonClass)node.getXhc().getParentNode();
			while (xholonSuperclass != rootXholonClass) {
				sortRoot.add(xholonSuperclass);
				xholonSuperclass = (IXholonClass)xholonSuperclass.getParentNode();
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
	 */
	public void writeAll() {
		/*boolean shouldClose = true;
		if (root.getApp().isUseAppOut()) {
			out = root.getApp().getOut();
			shouldClose = false;
		}
		else {
			try {
				// create any missing output directories
				File dirOut = new File(outPath);
				dirOut.mkdirs(); // will create a new directory only if there is no existing one
				out = MiscIo.openOutputFile(outFileName);
			} catch(AccessControlException e) {
				//out = new PrintWriter(System.out);
				out = root.getApp().getOut();
				shouldClose = false;
			}
		}*/
		sb = new StringBuilder();
		//try {
			sb.append(
				  "' Automatically generated by Xholon version 0.8.1, using Xholon2PlantUML.java\n"
				+ "' " + new Date() + " " + timeStamp + "\n"
				+ "' model: " + modelName + "\n"
				+ "' www.primordion.com/Xholon\n"
				+ "\n");
			sb.append("@startuml ./" + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".png" + "\n");
			if (root.getXhcId() == CeStateMachineEntity.StateMachineCE) {
				writeStateDiagramNode(root);
			}
			else {
				sb.append("abstract class XholonClass\n");
				writeNode(sortRoot);
			}
			sb.append("@enduml\n");
			//out.write(sb.toString());
			//out.flush();
			writeToTarget(sb.toString(), outFileName, outPath, root);
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		//if (shouldClose) {
		//	MiscIo.closeOutputFile(out);
		//}
	}
	
	/**
	 * Class Diagram.
	 * Write one node, and its child nodes.
	 * example: Class01 <|-- Class02
	 * @param snode The current node in the Xholon hierarchy.
	 */
	@SuppressWarnings("unchecked")
	protected void writeNode(XholonSortedNode snode) {
		List<XholonSortedNode> ls = snode.getSortedListOfNodes(new ArrayList<XholonSortedNode>());
		//try {
			for (int i = 0; i < ls.size(); i++) {
				snode = ls.get(i);
				IXholonClass xholonClass = (IXholonClass)snode.getVal_Object();
				// isA, superclass
				IXholonClass xholonSuperclass = (IXholonClass)xholonClass.getParentNode();
				if (xholonSuperclass != rootXholonClass) {
					sb.append(xholonSuperclass.getName() + " <|-- ");
				}
				else {
					sb.append("XholonClass <|-- ");
				}
				sb.append(xholonClass.getName() + "\n");
				// associations, Xholon ports
				if (shouldShowClassAssociations) {
					if (shouldUseXholonClassAssociations) {
						String navInfo = xholonClass.getNavInfo();
						System.out.println(navInfo);
						// TODO use PortClassInformation.parse(xholonClass);
					}
					else {
						// get the first instance node with this XholonClass
						IXholon node = xpath.evaluate("descendant::" + xholonClass.getName(), root);
						if (node != null) {
							List<PortInformation> portList = node.getAllPorts();
							for (int j = 0; j < portList.size(); j++) {
								PortInformation portInfo = (PortInformation)portList.get(j);
								// TODO
								sb.append("  1 -> 1 " + portInfo.getReffedNode().getXhcName() + ";\n");
							}
						}
					}
				}
			}
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	
	/**
	 * State Diagram.
	 * example: State1 --> State2
	 * @param node
	 */
	@SuppressWarnings("unchecked")
	protected void writeStateDiagramNode(IXholon node) {
		node = node.getFirstChild();
		//try {
			while (node != null) {
				switch (node.getXhcId()) {
				case CeStateMachineEntity.RegionCE:
					writeStateDiagramNode(node);
					break;
				case CeStateMachineEntity.PseudostateInitialCE:
					sb.append("[*] --> " + getStateLabel(node.getPort(0).getPort(0)) + "\n");
					break;
				case CeStateMachineEntity.StateCE:
				case CeStateMachineEntity.PseudostateChoiceCE:
					List<PortInformation> allPorts = node.getAllPorts();
					for (int j = 0; j < allPorts.size(); j++) {
						PortInformation portInfo = (PortInformation)allPorts.get(j);
						if (!portInfo.getReffedNode().getXhc().hasAncestor(CeStateMachineEntity.TransitionCE)) {
							// ignore the port if it is not some type of Transition
							continue;
						}
						IXholon transition = portInfo.getReffedNode();
						IXholon targetState = transition.getPort(0);
						if (targetState == null) {continue;}
						String targetStateStr = null;
						switch (targetState.getXhcId()) {
						case CeStateMachineEntity.FinalStateCE:
						case CeStateMachineEntity.PseudostateTerminateCE:
							targetStateStr = "[*]";
							break;
						case CeStateMachineEntity.PseudostateEntryPointCE:
						case CeStateMachineEntity.PseudostateExitPointCE:
							// get the state that the entry/exit point references through a transition
							// TODO this could involve multiple hops
							targetState = targetState.getPort(0).getPort(0);
							targetStateStr = getStateLabel(targetState);
							break;
						default:
							targetStateStr = getStateLabel(targetState);
							break;
						}
						sb.append(getStateLabel(node) + " --> " + targetStateStr + " : " + getTransitionLabel(transition) + "\n");
					}
					if (((StateMachineEntity)node).isOrthogonal()) {
						sb.append("state " + getStateLabel(node) + " {\n");
						// all these children should be Region nodes
						IXholon orthogNode = node.getFirstChild();
						while (orthogNode != null) {
							if (orthogNode != node.getFirstChild()) {
								// separator
								sb.append("--\n");
							}
							writeStateDiagramNode(orthogNode);
							orthogNode = orthogNode.getNextSibling();
						}
						sb.append("}\n");
					}
					else if (((StateMachineEntity)node).isComposite()) {
						sb.append("state " + getStateLabel(node) + " {\n");
						writeStateDiagramNode(node);
						sb.append("}\n");
					}
					
					break;
				default:
					break;
				}
				node = node.getNextSibling();
			}
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	
	/**
	 * Get a label for a state node.
	 * @param node
	 * @return
	 */
	protected String getStateLabel(IXholon node) {
		if (node == null) {
			return "null";
		}
		String label = node.getRoleName();
		if ((label == null) || (label.length() == 0)) {
			label = node.getXhcName();
		}
		return label;
	}
	
	/**
	 * Get a label for a transition node.
	 * @param node
	 * @return
	 */
	protected String getTransitionLabel(IXholon node) {
		if (node == null) {
			return "null";
		}
		String label = null;
		StateMachineEntity trigger = (StateMachineEntity)node.getFirstChild();
		if (trigger != null) {
			switch (trigger.getXhcId()) {
			case CeStateMachineEntity.TriggerCE:
				label = trigger.getRoleName();
				break;
			default:
				break;
			}
		}
		if ((label == null) || (label.length() == 0)) {
			label = node.getRoleName();
		}
		return label;
	}
	
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

	public boolean isShouldShowStateMachineEntities() {
		return shouldShowStateMachineEntities;
	}

	public void setShouldShowStateMachineEntities(
			boolean shouldShowStateMachineEntities) {
		this.shouldShowStateMachineEntities = shouldShowStateMachineEntities;
	}
	
	public String getNameTemplate() {
		return nameTemplate;
	}

	public void setNameTemplate(String nameTemplate) {
		this.nameTemplate = nameTemplate;
	}

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

	//public Writer getOut() {
	//	return out;
	//}

	//public void setOut(Writer out) {
	//	this.out = out;
	//}

	public boolean isShouldUseXholonClassAssociations() {
		return shouldUseXholonClassAssociations;
	}

	public void setShouldUseXholonClassAssociations(
			boolean shouldUseXholonClassAssociations) {
		this.shouldUseXholonClassAssociations = shouldUseXholonClassAssociations;
	}

	public XholonSortedNode getSortRoot() {
		return sortRoot;
	}

	public void setSortRoot(XholonSortedNode sortRoot) {
		this.sortRoot = sortRoot;
	}

	public IXholonClass getRootXholonClass() {
		return rootXholonClass;
	}

	public void setRootXholonClass(IXholonClass rootXholonClass) {
		this.rootXholonClass = rootXholonClass;
	}

	public boolean isShouldShowClassAssociations() {
		return shouldShowClassAssociations;
	}

	public void setShouldShowClassAssociations(boolean shouldShowClassAssociations) {
		this.shouldShowClassAssociations = shouldShowClassAssociations;
	}

}
