/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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
 * It saves the snapshot in XML format.
 * The resulting file is intended to be used for debugging,
 * and as a quick global view of the structure.
 * It is NOT intended as a formal serialization.
 * For each node it includes the node name as the XML tag,
 * and the result of calling toString() as the XML content.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on May 17, 2006)
 * @see <a href="http://www.w3.org/TR/REC-xml/>w3.org</a>
 */
public class SnapshotXML extends Snapshot implements ISnapshot {
	
	//private Writer out;
	private StringBuilder sb;
	
	/**
	 * Constructor.
	 */
	public SnapshotXML() {}
	
	/* 
	 * @see org.primordion.xholon.io.ISnapshot#saveSnapshot(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public void saveSnapshot(IXholon snRoot, String modelName)
	{
		saveSnapshot(snRoot, snRoot.getName(), modelName);
	}
	
	/**
	 * Save a timestamped snapshot to an XML file.
	 * @param snRoot Root of the tree to be saved in the snapshot file.
	 * @param snRootName Name of the root.
	 * @param modelName Name of the model.
	 */
	protected void saveSnapshot(Object snRoot, String snRootName, String modelName)
	{
		Date now = new Date();
		String fileName = pathName + "xhsnap" + now.getTime() + ".xml";
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
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			sb.append("<!-- \n");
			sb.append("Model: " + modelName + "\n");
			sb.append("File: " + fileName + "\n");
			sb.append("Date: " + now + "\n");
			sb.append("Timestep: " + ((IXholon)snRoot).getApp().getTimeStep() + "\n");
			sb.append("-->\n");
			sb.append("<" + snRootName + ">\n");
			writeNode(((IXholon)snRoot).getFirstChild(), 1);
			sb.append("</" + snRootName + ">\n");
			writeToTarget(sb.toString(), fileName, pathName, (IXholon)snRoot);
			//out.write(sb.toString());
			//out.flush();
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		//if (shouldClose) {
		//	MiscIo.closeOutputFile(out);
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
				if (node.getFirstChild() != null) {
					sb.append(indent + "<" + nodeName + ">");
					sb.append(node.toString() + "\n");
					writeNode(node.getFirstChild(), level+1);
					sb.append(indent + "</" + nodeName + ">\n");
				}
				else {
					sb.append(indent + "<" + nodeName + ">");
					sb.append(node.toString());
					sb.append("</" + nodeName + ">\n");
				}
				if (node.getNextSibling() != null) {
					writeNode(node.getNextSibling(), level);
				}
			}
			else {
				if (node.getFirstChild() != null) {
					sb.append(indent + "<" + nodeName + ">\n");
					writeNode(node.getFirstChild(), level+1);
					sb.append(indent + "</" + nodeName + ">\n");
				}
				else {
					sb.append(indent + "<" + nodeName + "/>\n");
				}
				if (node.getNextSibling() != null) {
					writeNode(node.getNextSibling(), level);
				}
			}
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
}
