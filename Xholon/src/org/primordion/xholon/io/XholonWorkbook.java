/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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

//import com.google.gwt.user.client.Window.Navigator;

//import java.io.IOException;
//import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.client.GwtEnvironment;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.app.IApplication;
//import org.primordion.xholon.base.IControl;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
//import org.primordion.xholon.base.IXPath;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Parameters;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.exception.XholonConfigurationException;
//import org.primordion.xholon.service.AbstractXholonService;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.NodeSelectionService;
import org.primordion.xholon.service.svg.SvgClient;

/**
 * Read and process a xholonWorkbook.xml file, url, or string.
 * Details about the format of the .xml document:
 * <ul>
 * <li>The root node is &lt;XholonWorkbook>.</li>
 * <li>All Xholon chunks are direct children of the root node.</li>
 * <li>The document contains exactly one composite structure hierarchy (CSH) chunk.</li>
 * <li>The CSH chunk begins with a tag that ends with "System" (ex: &lt;PhysicalSystem>).</li>
 * <li>There are 0 or more chunks that end with "behavior" (ex: &lt;Blockbehavior>)</li>
 * <li>The IH chunk begins with the tag &lt;_-.XholonClass>.</li>
 * <li>The CH chunk begins with the tag &lt;xholonClassDetails>.</li>
 * <li>There are 0 or 1 &lt;Notes> chunks.</li>
 * </ul>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on January 27, 2012)
 */
public class XholonWorkbook extends Xholon {
	
	/**
	 * Approximate minimum length of an SVG subTree.
	 * Don't bother trying to process anything less than this length.
	 */
	private static final int SVG_MIN_LEN = 200;
	
	//private int bufferCapacity = 1000;
	//private char[] buffer = null;
	private IApplication app = null;
	private IXholon xholonHelperService = null;
	/** To prevent the insertion of unreasonably large workbooks. 100 * bufferCapacity chars */
	//private int maxReads = 100;
	private IXholon contextNode = null;
	
	// params
	private String paramsStr = "params::";
	private String paramStr =  "param_";
	private boolean shouldInsertSvgClient = true;
	private boolean shouldInsertBehaviors = true; //false;
	
	/** List of view behaviors that need to be passed to SvgClient. */
	List<String> viewBehaviorList = new ArrayList<String>();
	
	/**
	 * Read content from a source in xholonWorkbook.xml format.
	 * Parse it into XML chunks,
	 * and pass the chunks off to other classes for insertion into the Xholon app.
	 * @param app This app's instance of IApplication.
	 * @param reader A Reader.
	 * @return null
	 */
	public IXholon xml2Xh(IApplication app, Object reader)
	{
		this.app = app;
		//String workbookContents = readWorkbookContents((Reader)reader);
		String workbookContents = (String)reader; // XmlGwtDomReader.getUnderlyingReader() returns a String
		int pos = workbookContents.indexOf("<XholonWorkbook");
		if (pos == -1) {return null;}
		
		// determine the contextNode
		contextNode = getContextNode();
		if (contextNode == null) {
			contextNode = app.getRoot();
		}
		else if (contextNode == app) {
			// the contextNode is the Application node, so put the Workbook into a new Application
			String modelName = "App" + new Date().getTime();
			//println(modelName);
			String[] args = {}; //{"-modelName", modelName};
			String className = app.getClass().getName();
			//println(className);
			String configFileName = app.getConfigFileName();
			//println(configFileName);
			Application.appMain(args, className, configFileName);
			IApplication newApp = Application.getLastInsertedApplication();
			((Application)newApp).setModelName(modelName);
			//((Application)newApp).setInitialControllerState(IControl.CS_INITIALIZED);
			//println(newApp);
			// re-configure so port references in other App will be found ?
			//newApp.getRoot().configure();
			this.app = newApp;
			contextNode = newApp.getRoot();
		}
		else if (!(contextNode.getRootNode() == app.getRoot())) {
			// the contextNode must be in the composite structure hierarchy
			contextNode = app.getRoot();
		}
		
		pos = workbookContents.indexOf("<", pos+1);
		while (pos != -1) {
		  if (workbookContents.indexOf("<!--", pos) == pos) {
  		  // ignore XML comments  <!--  text -->
		    int endCommentIndex = workbookContents.indexOf("-->", pos+4);
	      if (endCommentIndex == -1) {break;}
	      pos = workbookContents.indexOf("<", endCommentIndex+3);
	      continue;
		  }
			int startTagBeginIndex = pos;
			int startTagEndIndex = workbookContents.indexOf(">", startTagBeginIndex);
			String tagName = workbookContents.substring(startTagBeginIndex+1, startTagEndIndex);
			if ("/XholonWorkbook".equals(tagName)) {break;} // end of document
			else if (tagName.endsWith("/")) {
				// this is an empty tag that can safely be ignored
				pos = workbookContents.indexOf("<", startTagEndIndex);
				continue;
			}
			int tagNameEndIndex = tagName.indexOf(" ");
			if (tagNameEndIndex != -1) {
				tagName = tagName.substring(0, tagNameEndIndex);
			}
			//System.out.println(tagName);
			int endTagBeginIndex = workbookContents.indexOf("</" + tagName, startTagEndIndex+1);
			int endTagEndIndex = workbookContents.indexOf(">", endTagBeginIndex);
			String subTree = workbookContents.substring(startTagBeginIndex, endTagEndIndex+1);
			//System.out.println(subTree);
			processSubTree(tagName, subTree);
			pos = workbookContents.indexOf("<", endTagEndIndex+1);
		}
		
		// the workbook may include one or more UML state machines; if so, then initialize them
		StateMachineEntity.initializeStateMachines(); // initialize any state machines in the app
		
		return null;
	}
	
	/**
	 * Process a subtree found in the workbook.
	 * @param tagName The XML tagName for the subTree node.
	 * @param subTree The string contents of the XML subTree node.
	 */
	protected void processSubTree(String tagName, String subTree) {
	  
	  // Notes
		if ("Notes".equals(tagName)) {
			// try to get Workbook title
			int titleIndex = subTree.indexOf("Title: ");
			if (titleIndex != -1) {
				int beginIndex = titleIndex + 7;
				String ls = getPropertyLineSeparator();
				int endIndex = subTree.indexOf(ls, beginIndex);
				if (endIndex != -1) {
					String title = subTree.substring(beginIndex, endIndex);
					IApplication newApp = Application.getLastInsertedApplication();
					((Application)newApp).setModelName(title);
					((Application)newApp).setHtmlTitles();
				}
			}
			// look for workbook params
			int paramsIndex = subTree.indexOf(paramsStr);
			if (paramsIndex != -1) {
				// shouldInsertSvgClient
				String paramName = paramStr + "shouldInsertSvgClient=";
				int paramIndex = subTree.indexOf(paramName, paramsIndex);
				if (paramIndex != -1) {
					paramIndex += paramName.length();
					String bStr = subTree.substring(paramIndex, paramIndex+4);
					shouldInsertSvgClient = Boolean.parseBoolean(bStr);
				}
				// shouldInsertBehaviors
				paramName = paramStr + "shouldInsertBehaviors=";
				paramIndex = subTree.indexOf(paramName, paramsIndex);
				if (paramIndex != -1) {
					paramIndex += paramName.length();
					String bStr = subTree.substring(paramIndex, paramIndex+4);
					shouldInsertBehaviors = Boolean.parseBoolean(bStr);
				}
			}
			// get Notes content for display in tab  <Notes><![CDATA[...]]></Notes>
			int notesStart = "<Notes><![CDATA[".length();
			int notesEnd = subTree.indexOf("]]></Notes>", notesStart);
			if (notesEnd != -1) {
			  subTree = subTree.substring(notesStart, notesEnd);
			}
			XholonGwtTabPanelHelper.addTab(subTree, "notes", "Workbook Notes", false);
		}
		
		// params (_xhn)
		else if ("params".equals(tagName)) {
		  try {
		    Parameters.xmlString2Params(subTree, app);
		  } catch (XholonConfigurationException e) {
			  //logger.error(e.getMessage(), e.getCause());
		  }
		}
		
		// inheritance hierarchy (ih)
		else if ("_-.XholonClass".equals(tagName)) {
			IXholonClass xhcRoot = app.getXhcRoot();
			sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, subTree, xhcRoot);
		}
		
		// class details (cd)
		else if ("xholonClassDetails".equals(tagName)) {
			IXholonClass xhcRoot = app.getXhcRoot();
			sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, subTree, xhcRoot);
		}
		
		// composite structure hierarchy (csh)
		else if (tagName.endsWith("System")) {
			sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, subTree, contextNode);
		}
		
		// SVG <SvgClient></SvgClient>
		else if ("SvgClient".equals(tagName)) {
			// the user may not want to load the SVG
			if (shouldInsertSvgClient && (subTree.length() > SVG_MIN_LEN)) {
				IXholon viewRoot = app.getView();
				sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, subTree, viewRoot);
				// pass any cached view-behaviors to the new SvgClient node
				IXholon viewNode = viewRoot.getLastChild();
				//consoleLog(viewNode);
				if ((viewNode != null) && "SvgClient".equals(viewNode.getXhcName())) {
				  SvgClient svgClient = (SvgClient)viewNode;
				  Iterator<String> it = viewBehaviorList.iterator();
				  while (it.hasNext()) {
				    String viewBehavior = it.next();
				    //consoleLog(viewBehavior);
				    svgClient.addViewBehavior(viewBehavior);
				  }
				}
			}
		}
		
		// script
		else if ("script".equals(tagName)) {
			// ignore simple scripts
		}
		
		// behavior
		else if (tagName.endsWith("behavior")) {
			// in many cases it is impossible to know which behaviors the user wants to use,
			// and to know which xholon is the intended parent of the behavior
			if (shouldInsertBehaviors) {
				String parentTagName = tagName.substring(0, tagName.indexOf("behavior"));
			  //consoleLog("found a behavior: " + parentTagName);
				if ("SvgViewBrowser".equals(parentTagName)) {
				  // cache this view-behavior until the SVG content exists
				  //consoleLog("found a SvgViewBrowser");
				  viewBehaviorList.add(subTree);
				}
				else {
				  String instanceStr = appendBehaviorXhc(subTree, tagName);
				  //consoleLog("instanceStr\n" + instanceStr);
				  appendBehavior(instanceStr, parentTagName, contextNode);
				}
			}
		}
		
		else {
			// ?
		}
	}
	
	/**
	 * Append a behavior XholonClass to the inheritance hierarchy.
	 * Instances of the behavior will then be able to share this Xholon class.
	 * @param behContent  The XML + JavaScript content of the behavior.
	 * Examples of opening tag on behContent:
	 * ex: <TotalRadiativeForcingbehavior implName="lang:webEditionjs:inline:">
	 * ex: <TotalRadiativeForcingbehavior implName="org.primordion.xholon.base.Behavior_gwtjs">
	 * ex: <TotalRadiativeForcingbehavior implName="org.primordion.xholon.base.Behavior_gwtjsproto"> ???
	 * @return A JavaScript string that can be used to create instances.
	 */
	protected String appendBehaviorXhc(String behContent, String behTagName) {
	  //consoleLog("XholonWorkbook.appendBehaviorXhc starting ... " + behTagName);
	  int startTagBeginIndex = behContent.indexOf("<");
		int startTagEndIndex = behContent.indexOf(">", startTagBeginIndex);
		String xhcTag = behContent.substring(startTagBeginIndex, startTagEndIndex) + "/>";
	  //consoleLog(xhcTag);
	  IXholonClass behXhc = app.getClassNode("Behavior");
	  //consoleLog(behXhc);
	  if (behXhc != null) {
	    sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, xhcTag, behXhc);
	  }
	  // get the new XholonClass
	  behXhc = app.getClassNode(behTagName);
	  //consoleLog(behXhc);
	  if (behXhc != null) {
	    String jsCode = behContent.substring(startTagEndIndex+1, behContent.indexOf("</" + behTagName)).trim();
	    //consoleLog(jsCode);
	    // remove pre/post CDATA tags  <![CDATA[  ]]>
	    if (jsCode.startsWith("<![CDATA[")) {
	      jsCode = jsCode.substring(9, jsCode.length() - 3);
	    }
	    //consoleLog(jsCode);
	    String jsCreateCode = behXhc.prototype(jsCode);
	    if (jsCreateCode != null) {
	      return jsCreateCode;
	    }
	  }
	  //consoleLog("XholonWorkbook.appendBehaviorXhc ending ...");
	  return behContent;
	}
	
	/**
	 * Append a behavior to zero or more existing nodes in the Xholon tree.
	 * This method recursively walks the tree looking for matches.
	 * @param behContent The XML + JavaScript content of the behavior.
	 * @param behParentXhcName The XholonClass name identifying nodes that should append the behavior.
	 * @param node A candidate IXholon node, that may or may not have a matching XholonClass name.
	 */
	protected void appendBehavior(String behContent, String behParentXhcName, IXholon node) {
	  //consoleLog("XholonWorkbook.appendBehavior starting ...");
	  if (behParentXhcName.equals(node.getXhcName())) {
	    sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, behContent, node);
	  }
	  IXholon childNode = node.getFirstChild();
	  while (childNode != null) {
	    appendBehavior(behContent, behParentXhcName, childNode);
	    childNode = childNode.getNextSibling();
	  }
	  //consoleLog("XholonWorkbook.appendBehavior ending ...");
	}
	
	/**
	 * Get the line separator used on the current platform (operating system).
	 * @return One of "\n" "\r\n"
	 */
	protected String getPropertyLineSeparator() {
	  String lineSeparator = "\n"; // GWT Linux, etc.
	  if (app.isUseGwt()) {
	    String platform = GwtEnvironment.navPlatform;
	    if (platform.toLowerCase().startsWith("win")) {
	      lineSeparator = "\r\n"; // GWT Windows
	    }
	  }
	  else {
	    //lineSeparator = System.getProperty("line.separator"); // Java SE
	  }
	  return lineSeparator;
	}
	
	/**
	 * Send a synchronous message to the XholonHelperService.
	 * @param signal
	 * @param data
	 * @param sender
	 * @return A response message.
	 */
	protected IMessage sendXholonHelperService(int signal, Object data, IXholon sender)
	{
		// send the request to the XholonHelperService by sending it a sync message
		if (xholonHelperService == null) {
			xholonHelperService = app.getService(IXholonService.XHSRV_XHOLON_HELPER);
		}
		if (xholonHelperService == null) {
			return null;
		}
		else {
			if (sender == null) {sender = app;}
			return xholonHelperService.sendSyncMessage(signal, data, sender);
		}
	}
	
	/**
	 * Read the entire contents of the workbook.
	 * TODO limit the size of the file that can be read
	 * @param reader The reader.
	 * @return The entire string content of the workbook.
	 */
	/* not needed with GWT
	protected String readWorkbookContents(Reader reader) {
		StringBuilder sb = new StringBuilder();
		int counter = maxReads;
		try {
			reader.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (reader == null) {return null;}
		buffer = new char[bufferCapacity];
		int numCharsRead = read(reader, buffer);
		while (numCharsRead != -1) {
			counter--;
			if (numCharsRead == bufferCapacity) {
				//System.out.print(buffer);
				sb.append(buffer);
			}
			else {
				//System.out.println(new String(buffer).substring(0, numCharsRead));
				sb.append(new String(buffer).substring(0, numCharsRead));
			}
			if (counter <= 0) {break;}
			numCharsRead = read(reader, buffer);
		}
		return sb.toString();
	}*/
	
	/**
	 * Read characters into an array.
	 * This method will block until some input is available,
	 * an I/O error occurs, or the end of the stream is reached.
	 * @param reader The reader.
	 * @param buffer Destination buffer.
	 * @return The number of characters read, or -1 if the end of the stream has been reached.
	 */
	/* not needed with GWT
	protected int read(Reader reader, char[] buffer) {
		int numCharsRead = 0;
		try {
			numCharsRead = reader.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return numCharsRead;
	}*/
	
	/**
	 * Get the IXholon context node.
	 * This is the node into which the workbook was pasted or dropped as a last child.
	 * It's the node in the tree on which a MouseEvent.BUTTON3 selection (Right-Click) event occured.
	 * @return A node, or null.
	 */
	protected IXholon getContextNode()
	{
		IXholon nss = app.getService(IXholonService.XHSRV_NODE_SELECTION);
		if (nss != null) {
			IMessage nodesMsg = nss.sendSyncMessage(
				NodeSelectionService.SIG_GET_BUTTON3_NODE_REQ, null, this);
			return (IXholon)nodesMsg.getData();
		}
		return null;
	}
	
}
