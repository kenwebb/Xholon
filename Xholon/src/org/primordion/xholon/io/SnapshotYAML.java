/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.xholon.io;

//import java.io.File;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.Writer;
//import java.security.AccessControlException;
import java.util.Date;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.Xholon;
//import org.primordion.xholon.util.MiscIo;

/**
 * Take a shapshot of an entire Xholon tree.
 * It saves the snapshot in YAML "key: value" format.
 * The resulting file is intended to be used for debugging,
 * and as a quick global view of the structure.
 * It is NOT intended as a formal serialization.
 * For each node it includes the node name as the YAML key,
 * and the result of calling toString() as the YAML value.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on February 19, 2007)
 * @see <a href="http://www.yaml.org">yaml.org</a>
 */
public class SnapshotYAML extends Snapshot implements ISnapshot {
	
	//private Writer out;
	private StringBuilder sb;
	
	/**
	 * Constructor.
	 */
	public SnapshotYAML() {}
	
	@Override
	public String getVal_String() {
	  return sb.toString();
	}
	
	/**
	 * Save a timestamped snapshot to an XML file.
	 * @param snRoot Root of the tree to be saved in the snapshot file.
	 * @param modelName Name of the model.
	 */
	public void saveSnapshot(IXholon snRoot, String modelName)
	{
		saveSnapshot(snRoot, snRoot.getName(), modelName);
	}
	
	/**
	 * Save a timestamped snapshot to a YAML file.
	 * @param snRoot Root of the tree to be saved in the snapshot file.
	 * @param snRootName Name of the root.
	 * @param modelName Name of the model.
	 */
	protected void saveSnapshot(Object snRoot, String snRootName, String modelName)
	{
		Date now = new Date();
		String fileName = outputPathGwtTabName + "xhsnap" + now.getTime() + ".yaml";
		/*boolean shouldClose = true;
		if (((IXholon)snRoot).getApp().isUseAppOut()) {
			out = ((IXholon)snRoot).getApp().getOut();
			shouldClose = false;
		}
		else {
			try {
				// create any missing output directories
				File dirOut = new File(pathName);
				dirOut.mkdirs(); // will create a new directory only if there is no existing one
				// open output file
				out = MiscIo.openOutputFile(fileName);
			} catch(AccessControlException e) {
				out = new PrintWriter(System.out);
				shouldClose = false;
			}
		}*/
		sb = new StringBuilder();
		//try {
			sb.append("%YAML 1.1\n");
			sb.append("---\n");
			sb.append("# Model: " + modelName + "\n");
			sb.append("# File: " + fileName + "\n");
			sb.append("# Date: " + now + "\n");
			sb.append("# Timestep: " + ((IXholon)snRoot).getApp().getTimeStep() + "\n");
			sb.append(snRootName + ": \n");
			writeNode(((IXholon)snRoot).getFirstChild(), 1);
			sb.append("...\n");
			writeToTarget(sb.toString(), fileName, outputPathGwtTabName, (IXholon)snRoot);
			//out.write(sb.toString());
			//out.flush();
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		//if (shouldClose) {
		//	MiscIo.closeOutputFile(out);
		//}
		
		// test it
		//try {
		//	java.util.Map configuration = (java.util.Map)org.jvyaml.YAML.load(new java.io.FileReader(fileName));
		//	System.out.println(configuration);
		//} catch (FileNotFoundException e) {
		//	e.printStackTrace();
		//}
	}
	
	/**
	 * Write a node as it currently exists in the Xholon model.
	 * @param node The node to write to a file.
	 * @param level The distance from the root node (used for indenting).
	 */
	protected void writeNode(IXholon node, int level) {
		String nodeName = getNodeName(node);
		StringBuilder indent = new StringBuilder(level);
		for (int i = 0; i < level; i++) {
			indent.append(' ');
		}
		//try {
			if (snapshotTostring) {
				sb.append(indent + nodeName + ": ");
				if (node.getFirstChild() != null) {
					// if this is not a leaf node, make the value a comment so it will be valid YAML
					// TODO find another way to represent this in YAML
					sb.append("# ");
				}
				sb.append(node.toString() + "\n");
			}
			else {
				sb.append(indent + nodeName + ": \n");
			}
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		if (node.getFirstChild() != null) {
			writeNode(node.getFirstChild(), level+1);
		}
		if (node.getNextSibling() != null) {
			writeNode(node.getNextSibling(), level);
		}
	}
}
