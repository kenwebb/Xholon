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

import com.google.gwt.core.client.GWT;

//import java.io.Writer;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.service.ExternalFormatService;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Take a shapshot of an entire Xholon tree, or of a specified subtree.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on May 17, 2006)
 */
public class Snapshot extends Xholon implements ISnapshot {
	
	/** Snapshot parameters.
	old format:
	  SnapshotParams: SnapshotSource(0 1),SnapshotTostring,Path
	new format April 2014:
	  "externalFormatName,fullXPathFromRoot,outputPathGwtTabName,reuseTabDiv[,snapshotTostring]"
	  "SnapshotXml,/Node[13]/Node,./snapshot/,false,true"
	*/
	
	/**
	 * Full external format name. For a complete list, use the following XholonJsApi method:
	 * $wnd.xh.xports()
	 * Examples:
	 *  HTModL
	 *  _d3,CirclePack
	 *  _other,Newick
	*/
	protected static String externalFormatName = "SnapshotXml";
	
	/**
	 * Full XPath expression that specifies the snapshot root, assuming the context node is the app root.
	 * Examples:
	 *  .
	 *  /Node[13]/Node
	 *  ../..
	 */
	protected static String fullXPathFromRoot = ".";
	
	/**
	 * Old format: pathName  file parameters
	 * New format: outputPathGwtTabName
	 */
	protected static String outputPathGwtTabName = "./snapshot/";
	
	/**
	 * Whether to reuse the same GWT tab or HTML div each timestep (true),
	 * or to create a new one each timestep (false).
	 */
	protected static boolean reuseTabDiv = false;
	
	/**
	 * Whether to include toString() information for each node.
	 * This is only useful when the external format is SnapshotXml or SnapshotYaml.
	 */
	protected static boolean snapshotTostring = true;
	
	//Writer out;
	
	private boolean writeToTab = true;
	
	/**
	 * Constructor.
	 */
	public Snapshot() {}
	
	// parameters
	
	public static void setExternalFormatName(String externalFormatNameArg) {
	  externalFormatName = externalFormatNameArg;
	}
	public static String getExternalFormatName() {return externalFormatName;}
	
	public static void setFullXPathFromRoot(String fullXPathFromRootArg) {
	  fullXPathFromRoot = fullXPathFromRootArg;
	}
	public static String getFullXPathFromRoot() {return fullXPathFromRoot;}
	
	public static void setOutputPathGwtTabName(String outputPathGwtTabNameArg) {
	  outputPathGwtTabName = outputPathGwtTabNameArg;
	}
	public static String getOutputPathGwtTabName() {return outputPathGwtTabName;}
	
	public static void setReuseTabDiv(boolean reuseTabDivArg) {
	  reuseTabDiv = reuseTabDivArg;
	}
	public static boolean getReuseTabDiv() {return reuseTabDiv;}
	
	public static void setSnapshotTostring(boolean snapshotTostringArg) {
	  snapshotTostring = snapshotTostringArg;
	}
	public static boolean getSnapshotTostring() {return snapshotTostring;}
	
	/* 
	 * @see org.primordion.xholon.io.ISnapshot#saveSnapshot(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public void saveSnapshot(IXholon snRoot, String modelName)
	{
	  consoleLog("Snapshot saveSnapshot() 1");
		ExternalFormatService efs = GWT.create(org.primordion.xholon.service.ExternalFormatService.class);
		IXholon node = ((Xholon)snRoot).getXPath().evaluate(fullXPathFromRoot, snRoot);
		consoleLog("Snapshot saveSnapshot() 1");
		if (node != null) {
		  consoleLog("Snapshot saveSnapshot() " + node.getName());
		  IXholon2ExternalFormat xholon2ef = efs.initExternalFormatWriter(node, externalFormatName, null);
		  efs.writeAll(xholon2ef);
    }
	}
	
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
