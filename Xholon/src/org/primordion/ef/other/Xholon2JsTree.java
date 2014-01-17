/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2009, 2010, 2011 Ken Webb
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
//import java.security.AccessControlException;
import java.util.Date;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Write content that can be handled by jsTree (v 1.0), a JQuery plugin.
 * The output format is compatible with the jsTree HTML_DATA plugin. 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://www.jstree.com/">jsTree</a>
 * @since 0.8.1 (Created on December 9, 2009)
 */
@SuppressWarnings("serial")
public class Xholon2JsTree extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
	
	private String outFileName;
	private String outPath = "./ef/jstree/";
	private String modelName;
	private IXholon root;
	//private Writer out;
	private StringBuilder sb;
	
	/**
	 * If StringWriter is used as the Writer implementation,
	 * then outBuffer will contain the full JsTree-formatted result.
	 */
	//private StringBuffer outBuffer = null;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/** Whether or not to show state machine nodes. */
	private boolean shouldShowStateMachineEntities = false;
	
	/** Template to use when writing out node names. */
	//protected String nameTemplate = "r:C^^^";
	protected String nameTemplate = "^^C^^^"; // don't include role name
	
	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 * ex: xholon2JsTree.initialize(IXholon2ExternalFormat.STRING_WRITER, app.getModelName(), context);
	 */
	public boolean initialize(String outFileName, String modelName, IXholon root) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (outFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".html";
		}
		else if (outFileName.equals(STRING_WRITER)) {
			this.outFileName = outFileName;
		}
		else {
			this.outFileName = outFileName;
		}
		this.modelName = modelName;
		this.root = root;
		return true;
	}

	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#writeAll()
	 */
	public void writeAll() {
		//if (outFileName.equals(STRING_WRITER)) {
		//	out = new StringWriter();
		//	sb = new StringBuilder(1000);
		//	writeAllTree();
		//	setOutBuffer(((StringWriter)out).getBuffer());
		//}
		//else {
			/*boolean shouldClose = true;
			if (root.getApp().isUseAppOut()) {
				out = root.getApp().getOut();
				sb = new StringBuilder(1000);
				shouldClose = false;
			}
			else {
				try {
					// create any missing output directories
					File dirOut = new File(outPath);
					dirOut.mkdirs(); // will create a new directory only if there is no existing one
					out = MiscIo.openOutputFile(outFileName);
					sb = new StringBuilder(1000);
				} catch(AccessControlException e) {
					//out = new PrintWriter(System.out);
					out = root.getApp().getOut();
					sb = new StringBuilder(1000);
					shouldClose = false;
				}
			}*/
			sb = new StringBuilder(1000);
			writeAllTree();
			writeToTarget(sb.toString(), outFileName, outPath, root);
			//try {
			//	out.write(sb.toString());
			//	out.flush();
			//} catch (IOException e) {
			//	Xholon.getLogger().error("", e);
			//}
			//if (shouldClose) {
			//	MiscIo.closeOutputFile(out);
			//}
		//}
	}
	
	/**
	 * Write an entire subtree.
	 */
	protected void writeAllTree()
	{
		//try {
			sb.append("<script implName=\"lang:BrowserJS:inline:\"><![CDATA[\n");
			sb.append("/* " + modelName + " " + timeNow + " */\n");
			sb.append("$(function () {\n");
			sb.append("  $(\"#divOne\").jstree({\n");
			sb.append("    \"core\" : { \"initially_open\" : [] },\n");
			sb.append("    \"contextmenu\" : { items: {\n");
			sb.append("      \"edit\" : {\"label\" : \"Edit\", \"action\" : function (obj) { this.dummyEdit(obj); }}\n");
			sb.append("    }},\n");
			sb.append("    \"html_data\" : {\n");
			sb.append("      \"data\" : \"\" +\n");
			sb.append("\"<div class='jsTreeRoot' id='jsTreeRoot" + timeStamp + "'>\" +\n");
			sb.append("\"<ul>\" +\n");
			writeTreeNode(root, 0); // root is level 0
			sb.append("\"</ul>\" +\n");
			sb.append("\"</div>\"\n");
			sb.append("    },\n");
			sb.append("    \"themes\" : { \"theme\" : \"classic\" },\n");
			sb.append("    \"plugins\" : [ \"themes\", \"html_data\", \"ui\", \"hotkeys\", \"contextmenu\" ]\n");
			sb.append("  });\n");
			sb.append("  $(\"." + root.getXhcName() + " a\").live(\"click\", function(e) {\n");
			sb.append("    $('#divThree').html(\"<p>\" + $(this).text() + \"</p>\");\n");
			sb.append("  });\n");
			sb.append("});\n");
			sb.append("$(function() {$(\"#divOne\").draggable();});\n");
			sb.append("$(function() {$(\"#divOne\").resizable();});\n");
			writeCss();
			sb.append("]]></script>");
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	
	/**
	 * Write one node, and its child nodes.
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeTreeNode(IXholon node, int level)
	{
		// only show state machine nodes if should show them, or if root is a StateMachineCE
		if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
				&& (shouldShowStateMachineEntities == false)
				&& (level > 0)) {
			return;
		}
		//try {
			sb.append("\"<li id='" + node.getId() + "' class='" + node.getXhcName() + "'>");
			sb.append("<a href='#'>" + node.getName() + "</a>");
			// children
			IXholon childNode = node.getFirstChild();
			if (childNode != null) {
				sb.append("\" +\n\"<ul>\" +\n");
				while (childNode != null) {
					writeTreeNode(childNode, level+1);
					childNode = childNode.getNextSibling();
				}
				sb.append("\"</ul>\" +\n\"");
			}
			sb.append("</li>\" +\n");
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}

	/**
	 * Write CSS to describe how the tree should be presented on the page.
	 */
	protected void writeCss()
	{
		//try {
			sb.append("$('#divOne').css('background', 'aliceblue');\n");
			sb.append("$('#divOne').css('width', '500px');\n");
			sb.append("$('#divOne').css('height', '300px');\n");
			sb.append("$('#divOne').css('border-style', 'solid');\n");
			sb.append("$('#divOne').css('border-width', '5px');\n");
			sb.append("$('#divOne').css('border-color', 'cornflowerblue');\n");
			sb.append("$('#divOne').css('overflow', 'auto');\n");
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	
	/*public StringBuffer getOutBuffer() {
		return outBuffer;
	}

	public void setOutBuffer(StringBuffer outBuffer) {
		this.outBuffer = outBuffer;
	}*/

}
