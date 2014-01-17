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

//import java.io.Writer;
import org.primordion.xholon.base.IXholon;

/**
 * Take a shapshot of an entire Xholon tree.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on May 17, 2006)
 */
public abstract class Snapshot implements ISnapshot {
	
	// Whether to include toString() information for each node.
	protected static boolean snapshotTostring = true;
	
	// File parameters
	protected static String pathName = "./snapshot/";
	
	//Writer out;
	
	private boolean writeToTab = true;
	
	/**
	 * Constructor.
	 */
	public Snapshot() {}
	
	// parameters
	
	public static void setSnapshotTostring(boolean snapTostring) {snapshotTostring = snapTostring;}
	public static boolean getSnapshotTostring() {return snapshotTostring;}
	
	public static void setPathName(String path) {pathName = path;}
	public static String getPathName() {return pathName;}
	
	/**
	 * Get the name of a node, without any prefixed role name.
	 * @param node A tree node.
	 * @return The name of the tree node.
	 */
	protected String getNodeName(IXholon node)
	{
		String nodeName = node.getName();
		int startIx = nodeName.lastIndexOf(':');
		if (startIx != -1) {
			nodeName = nodeName.substring(startIx+1);
		}
		return nodeName;
	}
	
	/**
   * Write the entire external-format text to an appropriate target.
   * @param efText The external-format text.
   * @param uri A file name, or a GWT-usable tooltip.
   * @param outPath A file system path name, or GWT-usable content type (ex: "_xhn"), or equivalent.
   * @param root The root node of the subtree being written out.
   */
  protected void writeToTarget(String efText, String uri, String outPath, IXholon root) {
    if (root.getApp().isUseGwt()) {
      if (writeToTab) {
        XholonGwtTabPanelHelper.addTab(efText, outPath, uri, false);
      }
      else {
        root.println(efText);
      }
    }
    else {
      //writeToTargetFile(efText, uri, outPath, root);
    }
  }
  
}
