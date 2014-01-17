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

import com.google.gwt.dom.client.Element;

//import java.io.File;
//import java.io.IOException;
//import java.io.Writer;
//import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.client.HtmlElementCache;
import org.primordion.xholon.util.XholonSortedNode;
import org.primordion.xholon.base.IXPath;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon model in yUML format.
 * <p>TODO option to show parent/child as an association</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on April 26, 2010)
 * @see <a href="http://yuml.me/">yUML website</a>
 */
@SuppressWarnings("serial")
public class Xholon2Yuml extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
	
	private static final int MAX_URL_LEN = 2000;
	
	private String outFileName;
	private String outPath = "./ef/yuml/";
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
	protected String nameTemplate = "r:C^^^";

	/** I'm not sure if the pajek format can handle comments. */
	protected boolean showComments = false;
	
	/** Root of the binary tree that sorts and counts Xholon nodes. */
	private XholonSortedNode sortRoot = null;
	
	/** The root XholonClass. */
	private IXholonClass rootXholonClass = null;
	
	/** An instance of XPath. */
	private IXPath xpath = null;

	/** Whether or not to show associations between classes. */
	private boolean shouldShowClassAssociations = true;
	
	/**
	 * Whether to get associations between classes,
	 * from the XholonClass (true),
	 * or from an instance of Xholon (false).
	 */
	private boolean shouldUseXholonClassAssociations = false;
	
	/**
	 * Base URL for retrieving an embedded image.
	 */
	private String yumlBaseUrl = "http://yuml.me/diagram/scruffy/class/";
	
	private boolean shouldProcessAndShow = true;

	/**
	 * Constructor.
	 */
	public Xholon2Yuml() {}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String outFileName, String modelName, IXholon root) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (outFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".txt";
		}
		else {
			this.outFileName = outFileName;
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
		//boolean shouldClose = true;
		/*if (root.getApp().isUseAppOut()) {
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
		sb = new StringBuilder(1000);
		//try {
			if (showComments) {
			  // can only have one comment line
			  sb.append("// paste into http://yuml.me/\n");
			  /*sb.append(
				"#\n# Automatically generated by Xholon version 0.8.1, using Xholon2Yuml.java\n"
				+ "# " + new Date() + " " + timeStamp + "\n"
				+ "# model: " + modelName + "\n"
				+ "# www.primordion.com/Xholon\n\n");*/
			}
			sb.append("[note: ").append(modelName).append("{bg:honeydew}]\n");
			writeNode(sortRoot);
			//out.write(sb.toString());
			//out.flush();
			//root.println(sb.toString());
			processAndShow(sb.toString());
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		//if (shouldClose) {
		//	MiscIo.closeOutputFile(out);
		//}
	}
	
	/**
	 * Write one node, and its child nodes.
	 * @param snode The current node in the Xholon hierarchy.
	 */
	@SuppressWarnings("unchecked")
	protected void writeNode(XholonSortedNode snode) {
		List<XholonSortedNode> ls = snode.getSortedListOfNodes(new ArrayList<XholonSortedNode>());
		//System.out.println(rootXholonClass);
		//try {
			for (int i = 0; i < ls.size(); i++) {
				snode = ls.get(i);
				IXholonClass xholonClass = (IXholonClass)snode.getVal_Object();
				// isA, superclass
				IXholonClass xholonSuperclass = (IXholonClass)xholonClass.getParentNode();
				if (xholonSuperclass != rootXholonClass) {
					sb.append("[" + xholonSuperclass.getName() + "]^-");
				}
				sb.append("[" + xholonClass.getName() + "],\n");
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
								PortInformation portInfo = portList.get(j);
								// [Association]1-src >1[Class],
								sb.append("[" + xholonClass.getName() + "]"
										+ "1-" + portInfo.getFieldName() + " >1["
										+ portInfo.getReffedNode().getXhcName() + "],\n");
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
	 * Process the yuml diagram by sending it to the yUML site.
	 * Show it as an HTML img in the browser yuml element.
	 * @param The text of the diagram.
	 */
	protected void processAndShow(String diagram) {
	  if (shouldProcessAndShow) {
	    StringBuilder pasSb = new StringBuilder()
	    .append(yumlBaseUrl)
	    .append(diagram);
	    String imgStr = pasSb.toString();
	    if (imgStr.length() < MAX_URL_LEN) {
	      HtmlElementCache.appendChild(HtmlElementCache.xhimg, "<img src=\"" + imgStr + "\"/>");
	    }
	  }
	  writeToTarget("// paste into http://yuml.me/\n" + diagram, outFileName, outPath, root);
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

	public boolean isShowComments() {
		return showComments;
	}

	public void setShowComments(boolean showComments) {
		this.showComments = showComments;
	}
}
