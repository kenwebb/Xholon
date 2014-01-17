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

package org.primordion.ef.other;

//import java.io.File;
//import java.io.IOException;
//import java.io.Writer;
//import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.primordion.xholon.util.XholonSortedNode;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Xholon;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon model in Many Eyes format.
 * This class only includes enough information to produce a basic Many Eyes tag cloud.
 * <pre>
 * Word  	Occurrences
 * Tag 	45
 * Cloud 	55
 * Mass 	10
 * </pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on March 5, 2010)
 * @see <a href="http://manyeyes.alphaworks.ibm.com/manyeyes/">Many Eyes web site</a>
 */
@SuppressWarnings("serial")
public class Xholon2ManyEyes extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
	
	private String outFileName;
	private String outPath = "./ef/manyeyes/";
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

	/**
	 * Constructor.
	 */
	public Xholon2ManyEyes() {}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String mmFileName, String modelName, IXholon root) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (mmFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".eye";
		}
		else {
			this.outFileName = mmFileName;
		}
		this.modelName = modelName;
		this.root = root;
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
			sb.append("XholonClass\tOccurrences\n");
			writeNode(sortRoot);
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
	 * Write one node, and its child nodes.
	 * @param snode The current node in the Xholon hierarchy.
	 */
	@SuppressWarnings("unchecked")
	protected void writeNode(XholonSortedNode snode) {
		List<XholonSortedNode> ls = snode.getSortedListOfNodes(new ArrayList<XholonSortedNode>());
		//try {
			for (int i = 0; i < ls.size(); i++) {
				snode = ls.get(i);
				IXholonClass node = (IXholonClass)snode.getVal_Object();
				sb.append(node.getName() + "\t" + snode.getCount(node) + "\n");
			}
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
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

	/*public Writer getOut() {
		return out;
	}

	public void setOut(Writer out) {
		this.out = out;
	}*/

}
