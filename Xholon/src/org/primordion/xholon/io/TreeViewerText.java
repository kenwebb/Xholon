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

package org.primordion.xholon.io;

//import java.io.IOException;
//import java.io.Writer;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.service.IXholonService;

/**
 * View a text representation of the an IXholon tree or subtree.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 10, 2009)
 */
public class TreeViewerText extends Xholon {
	
	/**
	 * The actual number of levels (rows) visited.
	 */
	protected int numRowsVisited = 0;
	
	/**
	 * The actual number of nodes (columns) visited.
	 */
	protected int numColsVisited = 0;
	
	//protected Writer out;
	protected StringBuilder sb;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedSyncMessage(org.primordion.xholon.base.IMessage)
	 */
	public IMessage processReceivedSyncMessage(IMessage msg)
	{
		switch (msg.getSignal()) {
		case IXholonService.SIG_PROCESS_REQUEST:
			draw((IXholon)msg.getData());
			return new Message(IXholonService.SIG_RESPONSE, null, msg.getReceiver(), msg.getSender());
		default:
			return super.processReceivedSyncMessage(msg);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedMessage(org.primordion.xholon.base.IMessage)
	 */
	public void processReceivedMessage(IMessage msg)
	{
		switch (msg.getSignal()) {
		case IXholonService.SIG_PROCESS_REQUEST:
			draw((IXholon)msg.getData());
			break;
		default:
			break;
		}
	}
	
	/**
	 * Draw a 2D representation of the tree, using inorder traversal.
	 * source: Goodrich & Tamassia (2001) p. 255
	 * "We can draw a binary tree T with an algorithm that assigns x- and y-coordinates
	 *  to a node v of T using the follosing two rules:
	 *   1) x(v) is equal to the number of nodes visited before v in the inorder traversal of T.
	 *   2) y(v) is equal to the depth of v in T."
	 * @param drawRoot The root of the tree that will be drawn.
	 */
	public void draw(IXholon drawRoot)
	{
		int numCols = drawRoot.treeSize();
		int numRows = drawRoot.height() + 1;
		if ((numCols > 1000) || (numRows > 100)) { // don't draw if it's too big
			return;
		}
		//out = drawRoot.getApp().getOut(); // GWT
		sb = new StringBuilder(256);
		IXholon[][] drawGrid = null;
		try {
			drawGrid = new IXholon[numCols][numRows];
		} catch (Exception e) { //OutOfMemoryError e) { // GWT
			//logger.warn("Xholon draw()", e); // GWT
			return;
		}
		numColsVisited = draw(drawRoot, 0, 0, drawGrid) + 1;
		numRowsVisited += 1;
		IXholon node = null;
		write("\n"); //drawRoot.print("\n");
		//for (int i = 0; i < numRows; i++) {
		//	for (int j = 0; j < numCols; j++) {
		for (int i = 0; i < numRowsVisited; i++) {
			for (int j = 0; j < numColsVisited; j++) {
				node = drawGrid[j][i];
				if ( node == null) {
					write(" "); //drawRoot.print(" ");
				}
				else {
					if (node.isExternal()) {
						// all Xholon names start with a lower-case letter 
						write(node.getName().charAt(0)); //drawRoot.print(node.getName().charAt(0));
					}
					else { // isInternal()
						char ch = Character.toUpperCase(node.getName().charAt(0));
						write(ch); //drawRoot.print(ch);
					}
				}
			}
			write("\n\n"); //drawRoot.println("\n");
		}
		flush();
	}
	
	/**
	 * Draw a node in the tree.
	 * @param node A node that will be drawn.
 	 * @param numNodesVisited x(v)
	 * @param level y(v)
	 * @param grid An n by m array sufficient to hold all the elements in the tree (OK for small trees).
	 */
	protected int draw(IXholon node, int numNodesVisited, int level, IXholon[][] grid)
	{
		if (level > numRowsVisited) {
			numRowsVisited = level;
		}
		if (node.getFirstChild() != null) {
			numNodesVisited = draw(node.getFirstChild(), numNodesVisited, level + 1, grid);
		}
		grid[numNodesVisited][level] = node;
		numNodesVisited++;
		if (node.getNextSibling() != null && (!node.isRootNode())) {
			numNodesVisited = draw(node.getNextSibling(), numNodesVisited, level + 1, grid);
		}
		return numNodesVisited;
	}
	
	protected void write(String str) {
	  sb.append(str);
	}
	
	protected void write(char c) {
	  sb.append(c);
	}
	
	protected void flush() {
	  showTextTree(sb.toString());
	}
	
	//protected native void showTextTree(String text) /*-{
	//  var element = $doc.getElementById("texttree");
  //  if (element) {
  //    element.innerHTML = "<pre>" + text + "</pre>";
  //  }
	//}-*/;
	
	protected void showTextTree(String text) {
	  XholonGwtTabPanelHelper.addTab(text, "tt", "texttree.txt", false);
	}
	
}
