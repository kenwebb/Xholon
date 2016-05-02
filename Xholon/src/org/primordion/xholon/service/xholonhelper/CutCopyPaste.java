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

package org.primordion.xholon.service.xholonhelper;

//import java.io.File;
import java.util.Date;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IControl;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonNode;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXml2Xholon;
import org.primordion.xholon.service.IXholonService;
//import org.primordion.xholon.service.MediaService;
import org.primordion.xholon.service.RecordPlaybackService;

/**
 * Cut, copy, paste actions.
 * <p>TODO Replace and Merge methods don't work properly</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on September 19, 2009)
 */
@SuppressWarnings("serial")
public class CutCopyPaste extends Xholon implements ICutCopyPaste {
	
	// used in Copy/Cut to clipboard
	String formatName = "Xml";
  String efParams = "{\"xhAttrStyle\":1,\"nameTemplate\":\"^^C^^^\",\"xhAttrReturnAll\":true,\"writeStartDocument\":false,\"writeXholonId\":false,\"writeXholonRoleName\":true,\"writePorts\":true,\"writeAnnotations\":true,\"shouldPrettyPrint\":true,\"writeAttributes\":true,\"writeStandardAttributes\":true,\"shouldWriteVal\":false,\"shouldWriteAllPorts\":false}";

	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedMessage(org.primordion.xholon.base.IMessage)
	 */
	public void processReceivedMessage(IMessage msg)
	{
		Object msgData = msg.getData();
		switch (msg.getSignal()) {
		case ISignal.ACTION_COPY_TOCLIPBOARD:
			writeStringToClipboard(copySelf((IXholon)msgData));
			break;
		case ISignal.ACTION_CUT_TOCLIPBOARD:
			writeStringToClipboard(cutSelf((IXholon)msgData));
			break;
		case ISignal.ACTION_PASTE_LASTCHILD_FROMCLIPBOARD:
			pasteLastChild((IXholon)msgData, readStringFromClipboard());
			break;
		case ISignal.ACTION_PASTE_FIRSTCHILD_FROMCLIPBOARD:
			pasteFirstChild((IXholon)msgData, readStringFromClipboard());
			break;
		case ISignal.ACTION_PASTE_AFTER_FROMCLIPBOARD:
			pasteAfter((IXholon)msgData, readStringFromClipboard());
			break;
		case ISignal.ACTION_PASTE_BEFORE_FROMCLIPBOARD:
			pasteBefore((IXholon)msgData, readStringFromClipboard());
			break;
		case ISignal.ACTION_PASTE_REPLACEMENT_FROMCLIPBOARD:
			pasteReplacement((IXholon)msgData, readStringFromClipboard());
			break;
		case ISignal.ACTION_PASTE_MERGE_FROMCLIPBOARD:
			pasteMerge((IXholon)msgData, readStringFromClipboard());
			break;
		case ISignal.ACTION_PASTE_LASTCHILD_FROMDROP:
			pasteLastChild(msg.getSender(), (String)msgData);
			break;
		case ISignal.ACTION_PASTE_FIRSTCHILD_FROMDROP:
			pasteFirstChild(msg.getSender(), (String)msgData);
			break;
		case ISignal.ACTION_PASTE_AFTER_FROMDROP:
			pasteAfter(msg.getSender(), (String)msgData);
			break;
		default:
			
		}
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#copyToClipboard(org.primordion.xholon.base.IXholon)
	 */
	public void copyToClipboard(IXholon node)
	{
		switch (getApp().getControllerState()) {
		case IControl.CS_INITIALIZED:
		case IControl.CS_PAUSED:
		case IControl.CS_STOPPED:
			// If the app controller is not currently running, then call the method synchronously.
			writeStringToClipboard(copySelf(node));
			break;
		default:
			// Else, send the request as an asynchronous message to be actioned in the next time step.
			sendSystemMessage(ISignal.ACTION_COPY_TOCLIPBOARD, node, this);
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#copySelf(org.primordion.xholon.base.IXholon)
	 */
	public String copySelf(IXholon node)
	{
	  // old approach
		//IXholon2Xml xholon2Xml = node.getXholon2Xml();
		//xholon2Xml.setXhAttrStyle(IXholon2Xml.XHATTR_TO_XMLATTR); //XHATTR_TO_XMLELEMENT);
		//String xmlString = xholon2Xml.xholon2XmlString(node);
		
		// new approach
    String xmlString = serialize(node, formatName, efParams);
		
		return xmlString;
	}
	
	/**
   * Serialize a IXholon node as XML, or as another format.
   * @param node The node and subtree that should be serialized.
   * @param formatName External format name (ex: "Xml").
   * @param efParams External format parameters.
   * @return A serialization of the node.
   */
  protected native String serialize(IXholon node, String formatName, String efParams) /*-{
    return $wnd.xh.xport(formatName, node, efParams, false, true);
  }-*/;
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#cutToClipboard(org.primordion.xholon.base.IXholon)
	 */
	public void cutToClipboard(IXholon node)
	{
		switch (getApp().getControllerState()) {
		case IControl.CS_INITIALIZED:
		case IControl.CS_PAUSED:
		case IControl.CS_STOPPED:
			// If the app controller is not currently running, then call the method synchronously.
			writeStringToClipboard(cutSelf(node));
			break;
		default:
			// Else, send the request as an asynchronous message to be actioned in the next time step.
			sendSystemMessage(ISignal.ACTION_CUT_TOCLIPBOARD, node, this);
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#cutSelf(org.primordion.xholon.base.IXholon)
	 */
	public String cutSelf(IXholon node)
	{
		String xmlString = copySelf(node);
		record(node, xmlString, "cut");
		node.removeChild();
		return xmlString;
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteLastChildFromClipboard(org.primordion.xholon.base.IXholon)
	 */
	public void pasteLastChildFromClipboard(IXholon node)
	{
		switch (getApp().getControllerState()) {
		case IControl.CS_INITIALIZED:
		case IControl.CS_PAUSED:
		case IControl.CS_STOPPED:
			// If the app controller is not currently running, then call the method synchronously.
			pasteLastChild(node, readStringFromClipboard());
			break;
		default:
			// Else, send the request as an asynchronous message to be actioned in the next time step.
			sendSystemMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMCLIPBOARD, node, this);
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteLastChildFromDrop(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public void pasteLastChildFromDrop(IXholon node, String xmlString)
	{
		switch (getApp().getControllerState()) {
		case IControl.CS_INITIALIZED:
		case IControl.CS_PAUSED:
		case IControl.CS_STOPPED:
			// If the app controller is not currently running, then call the method synchronously.
			pasteLastChild(node, xmlString);
			break;
		default:
			// Else, send the request as an asynchronous message to be actioned in the next time step.
			sendSystemMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMDROP, xmlString, node);
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteLastChild(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public void pasteLastChild(IXholon node, String xmlString)
	{
	  //System.out.println("pasteLastChild:" + xmlString);
		xmlString = adjustPastedContent(xmlString);
		if (xmlString == null) {return;}
		record(node, xmlString, "pasteLastChild");
		IXml2Xholon xml2Xholon = node.getXml2Xholon();
		IXholon myRootXhNode = xml2Xholon.xmlString2Xholon(xmlString, null);
		if (myRootXhNode == null) {return;}
		if (myRootXhNode.getXhcName().startsWith(IXml2Xholon.XML_FOREST)) {
			myRootXhNode = myRootXhNode.getFirstChild();
		}
		if (myRootXhNode == null) {return;} // forest with no content
		if ((myRootXhNode instanceof IXholonClass)
				&& (node.findFirstChildWithXhClass(myRootXhNode.getName()) != null)) {
			// this node is a duplicate (we'll assume the whole subtree is a duplicate)
			return;
		}
		myRootXhNode.appendChild(node);
		fixSiblingParents(myRootXhNode);
		myRootXhNode.configure();
		myRootXhNode.postConfigure();
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteFirstChildFromClipboard(org.primordion.xholon.base.IXholon)
	 */
	public void pasteFirstChildFromClipboard(IXholon node)
	{
		switch (getApp().getControllerState()) {
		case IControl.CS_INITIALIZED:
		case IControl.CS_PAUSED:
		case IControl.CS_STOPPED:
			// If the app controller is not currently running, then call the method synchronously.
			pasteFirstChild(node, readStringFromClipboard());
			break;
		default:
			// Else, send the request as an asynchronous message to be actioned in the next time step.
			sendSystemMessage(ISignal.ACTION_PASTE_FIRSTCHILD_FROMCLIPBOARD, node, this);
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteFirstChildFromDrop(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public void pasteFirstChildFromDrop(IXholon node, String xmlString)
	{
		switch (getApp().getControllerState()) {
		case IControl.CS_INITIALIZED:
		case IControl.CS_PAUSED:
		case IControl.CS_STOPPED:
			// If the app controller is not currently running, then call the method synchronously.
			pasteFirstChild(node, xmlString);
			break;
		default:
			// Else, send the request as an asynchronous message to be actioned in the next time step.
			sendSystemMessage(ISignal.ACTION_PASTE_FIRSTCHILD_FROMDROP, xmlString, node);
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteFirstChild(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public void pasteFirstChild(IXholon node, String xmlString)
	{
		xmlString = adjustPastedContent(xmlString);
		if (xmlString == null) {return;}
		record(node, xmlString, "pasteFirstChild");
		IXml2Xholon xml2Xholon = node.getXml2Xholon();
		IXholon myRootXhNode = xml2Xholon.xmlString2Xholon(xmlString, null);
		if (myRootXhNode == null) {return;}
		//System.out.println(myRootXhNode);
		if (myRootXhNode.getXhcName().startsWith(IXml2Xholon.XML_FOREST)) {
			myRootXhNode = myRootXhNode.getFirstChild();
			if (myRootXhNode == null) {return;} // forest with no content
			IXholon oldFirstChild = node.getFirstChild();
			node.setFirstChild(myRootXhNode);
			myRootXhNode.setParentNode(node);
			IXholon lastSibling = myRootXhNode.getLastSibling();
			if (lastSibling != null) {
				lastSibling.setNextSibling(oldFirstChild);
			}
			else {
				myRootXhNode.setNextSibling(oldFirstChild);
			}
			fixSiblingParents(myRootXhNode);
		}
		else {
			myRootXhNode.insertFirstChild(node);
		}
		myRootXhNode.configure();
		myRootXhNode.postConfigure();
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteAfterFromClipboard(org.primordion.xholon.base.IXholon)
	 */
	public void pasteAfterFromClipboard(IXholon node)
	{
		switch (getApp().getControllerState()) {
		case IControl.CS_INITIALIZED:
		case IControl.CS_PAUSED:
		case IControl.CS_STOPPED:
			// If the app controller is not currently running, then call the method synchronously.
			pasteAfter(node, readStringFromClipboard());
			break;
		default:
			// Else, send the request as a message to be actioned in the next time step.
			sendSystemMessage(ISignal.ACTION_PASTE_AFTER_FROMCLIPBOARD, node, this);
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteAfterFromDrop(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public void pasteAfterFromDrop(IXholon node, String xmlString)
	{
		switch (getApp().getControllerState()) {
		case IControl.CS_INITIALIZED:
		case IControl.CS_PAUSED:
		case IControl.CS_STOPPED:
			// If the app controller is not currently running, then call the method synchronously.
			pasteAfter(node, xmlString);
			break;
		default:
			// Else, send the request as a message to be actioned in the next time step.
			sendSystemMessage(ISignal.ACTION_PASTE_AFTER_FROMDROP, xmlString, node);
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteAfter(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public void pasteAfter(final IXholon node, String xmlString)
	{
		xmlString = adjustPastedContent(xmlString);
		if (xmlString == null) {return;}
		record(node, xmlString, "pasteAfter");
		IXml2Xholon xml2Xholon = node.getXml2Xholon();
		IXholon myRootXhNode = xml2Xholon.xmlString2Xholon(xmlString, null);
		if (myRootXhNode == null) {return;}
		//System.out.println(myRootXhNode);
		if (myRootXhNode.getXhcName().startsWith(IXml2Xholon.XML_FOREST)) {
			myRootXhNode = myRootXhNode.getFirstChild();
			if (myRootXhNode == null) {return;} // forest with no content
			IXholon oldNextSibling = node.getNextSibling();
			node.setNextSibling(myRootXhNode);
			IXholon lastSibling = myRootXhNode.getLastSibling();
			if (lastSibling != null) {
				lastSibling.setNextSibling(oldNextSibling);
			}
			else {
				myRootXhNode.setNextSibling(oldNextSibling);
			}
			fixSiblingParents(node);
		}
		else {
			myRootXhNode.insertAfter(node);
		}
		myRootXhNode.configure();
		myRootXhNode.postConfigure();
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteBeforeFromClipboard(org.primordion.xholon.base.IXholon)
	 */
	public void pasteBeforeFromClipboard(IXholon node)
	{
		switch (getApp().getControllerState()) {
		case IControl.CS_INITIALIZED:
		case IControl.CS_PAUSED:
		case IControl.CS_STOPPED:
			// If the app controller is not currently running, then call the method synchronously.
			pasteBefore(node, readStringFromClipboard());
			break;
		default:
			// Else, send the request as an asynchronous message to be actioned in the next time step.
			sendSystemMessage(ISignal.ACTION_PASTE_BEFORE_FROMCLIPBOARD, node, this);
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteBefore(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public void pasteBefore(IXholon node, String xmlString)
	{
		if (xmlString == null) {return;}
		//record(node, xmlString, "pasteBefore"); //duplicates pasteFirstChild and pasteAfter
		if ((node.getFirstSibling() == null) && (!node.isRootNode())) {
			// this node is the left-most sibling, the first child of its parent
			pasteFirstChild(node.getParentNode(), xmlString);
		}
		else {
			// this node has a previous sibling
			pasteAfter(node.getPreviousSibling(), xmlString);
		}
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteReplacementFromClipboard(org.primordion.xholon.base.IXholon)
	 */
	public void pasteReplacementFromClipboard(IXholon node)
	{
		switch (getApp().getControllerState()) {
		case IControl.CS_INITIALIZED:
		case IControl.CS_PAUSED:
		case IControl.CS_STOPPED:
			// If the app controller is not currently running, then call the method synchronously.
			pasteReplacement(node, readStringFromClipboard());
			break;
		default:
			// Else, send the request as an asynchronous message to be actioned in the next time step.
			sendSystemMessage(ISignal.ACTION_PASTE_REPLACEMENT_FROMCLIPBOARD, node, this);
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteReplacement(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public void pasteReplacement(IXholon node,String xmlString)
	{
		xmlString = adjustPastedContent(xmlString);
		if (xmlString == null) {return;}
		record(node, xmlString, "pasteReplacement");
		IXml2Xholon xml2Xholon = node.getXml2Xholon();
		IXholon myRootXhNode = xml2Xholon.xmlString2Xholon(xmlString, null);
		if (myRootXhNode == null) {return;}
		//System.out.println(myRootXhNode);
		myRootXhNode.insertAfter(node);
		node.removeChild();
		myRootXhNode.configure();
		myRootXhNode.postConfigure();
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteMergeFromClipboard(org.primordion.xholon.base.IXholon)
	 */
	public void pasteMergeFromClipboard(IXholon node)
	{
		switch (getApp().getControllerState()) {
		case IControl.CS_INITIALIZED:
		case IControl.CS_PAUSED:
		case IControl.CS_STOPPED:
			// If the app controller is not currently running, then call the method synchronously.
			pasteMerge(node, readStringFromClipboard());
			break;
		default:
			// Else, send the request as an asynchronous message to be actioned in the next time step.
			sendSystemMessage(ISignal.ACTION_PASTE_MERGE_FROMCLIPBOARD, node, this);
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.service.xholonhelper.ICutCopyPaste#pasteMerge(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public void pasteMerge(IXholon node, String xmlString)
	{
		xmlString = adjustPastedContent(xmlString);
		if (xmlString == null) {return;}
		record(node, xmlString, "pasteMerge");
		IXml2Xholon xml2Xholon = node.getXml2Xholon();
		IXholon myRootXhNode = xml2Xholon.xmlString2Xholon(xmlString, node);
		if (myRootXhNode == null) {return;}
		myRootXhNode.configure();
		myRootXhNode.postConfigure();
	}
	
	public void cloneLastChild(IXholon node) {
	  String cloneStr = copySelf(node);
	  pasteLastChild(node, cloneStr);
	}
	
	public void cloneFirstChild(IXholon node) {
	  String cloneStr = copySelf(node);
	  pasteFirstChild(node, cloneStr);
	}
	
	public void cloneAfter(IXholon node) {
	  String cloneStr = copySelf(node);
	  pasteAfter(node, cloneStr);
	}
	
	public void cloneBefore(IXholon node) {
	  String cloneStr = copySelf(node);
	  pasteBefore(node, cloneStr);
	}
	
	/**
	 * Fix the siblings of a node so they all have the same parent.
	 * @param node A node that may have next siblings that have the wrong parent.
	 * This node has the correct parent.
	 */
	protected void fixSiblingParents(final IXholon node)
	{
		IXholon nextNode = node.getNextSibling();
		while (nextNode != null) {
			nextNode.setParentNode(node.getParentNode());
			nextNode = nextNode.getNextSibling();
		}
	}
	

	/**
	 * Read a String from the clipboard, such as an XML or XQuery String.
	 * @return A String, or null.
	 */
	protected String readStringFromClipboard()
	{
		String str = null;
		IClipboard xholonClipboard = ClipboardFactory.instance();
		if (xholonClipboard != null) {
			str = xholonClipboard.readStringFromClipboard();
		}
		return str;
	}
	
	/**
	 * Adjust content that was pasted or dropped.
	 * It's main purpose is to handle non-XML text.
	 * @param inStr A string that was pasted or dropped.
	 * @return An adjusted string, or null.
	 */
	protected String adjustPastedContent(String inStr) {
		if ((inStr == null) || (inStr.length() == 0)) {return null;}
		else if (isXml(inStr)) {return inStr;}
		else if (isUri(inStr)) {
			String mediaStr = getTextFromMediaObject(inStr);
			if (mediaStr == null) {
				return wrapUri(inStr);
			}
			else {
				//if (writeToClipboard) {
				//	// save the embedded text back to the clipboard, so users have access to it
				//	writeStringToClipboard(mediaStr);
				//}
				return mediaStr;
			}
		}
		return null;
	}
	
	/**
	 * Write a String to the clipboard.
	 * @param str A String.
	 */
	protected void writeStringToClipboard(String str)
	{
		IClipboard xholonClipboard = ClipboardFactory.instance();
		if (xholonClipboard != null) {
			xholonClipboard.writeStringToClipboard(str);
			// write or copy to clipboard?
			//xholonClipboard.copyStringToClipboard(str);
		}
	}
	
	/**
	 * Is this an XML String?
	 * @param str
	 * @return true or false
	 */
	protected boolean isXml(String str) {
		if (str.charAt(0) == '<') {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Is this a URI String?
	 * @param str
	 * @return true or false
	 */
	protected boolean isUri(String str) {
	  /* GWT
		if (str.charAt(0) == File.separatorChar) {
			// this is a file URI starting with / or \\
			return true;
		}
		else if (str.charAt(0) == '.') {
			// this is probably a file URI starting with ./ or .\\
			return true;
		}
		else if (str.indexOf("://") != -1) {
			// this is a URI starting with http:// file:// etc.
			return true;
		}
		else {
			return false;
		}
		*/
		return false; // GWT
	}
	
	/**
	 * Get text embedded within a media object, such as an image (.png) file.
	 * @param str A string that identifies the media object (a file name or uri name).
	 * @return The string embedded within the object, or null.
	 */
	protected String getTextFromMediaObject(String str)
	{
	  /* GWT
		IXholon mediaService = getService(IXholonService.XHSRV_MEDIA);
		if (mediaService != null) {
			IMessage mediaMsg = mediaService.sendSyncMessage(
				MediaService.SIG_TEXTFROMMEDIA_REQUEST, str, this);
			return (String)mediaMsg.getData();
		}
		*/
		return null;
	}
	
	/**
	 * Wrap an unknown URI in forest + xi:include tags.
	 * @param str A string that needs to be wrapped.
	 * @return A wrapped string.
	 */
	protected String wrapUri(String str) {
		StringBuilder sb = new StringBuilder()
		.append("<_-.forest>")
		.append("<xi:include xmlns:xi=\"http://www.w3.org/2001/XInclude\" href=\"")
		.append(str)
		.append("\"/>")
		.append("</_-.forest>");
		return sb.toString();
	}
	
	/**
	 * Record a cut or paste event.
	 * @param node The effected node.
	 * @param xmlString If it's a paste event, this is the XML String being pasted in.
	 * If it's a cut event, then this is null.
	 * @param editAction Which edit action was used (pasteLastChild, ..., cut).
	 */
	protected void record(IXholon node, String xmlString, String editAction) {
		IXholon record = this.getService(IXholonService.XHSRV_RECORD_PLAYBACK);
		if (record != null) {
			StringBuilder sb = new StringBuilder().append("<XholonNode")
			.append(" app=\"").append(node.getApp().getModelName()).append("\"")
			.append(" timeStep=\"").append(node.getApp().getTimeStep()).append("\"")
			.append(" nodeId=\"").append(node).append("\"")
			.append(" idType=\"").append(XholonNode.XHNODE_IDTYPE_GETNAME).append("\"")
			.append(" action=\"").append(editAction).append("\"")
			.append(" time=\"").append(new Date()).append("\"")
			.append(" >\n<![CDATA[")
			.append(xmlString).append("]]>\n")
			.append("</XholonNode>\n");
			record.sendSyncMessage(RecordPlaybackService.SIG_RECORD_REQ, sb.toString(), this);
		}
	}
	
	/**
	 * This xholon is a child of a service,
	 * so it can determine its Application by asking for its parent's Application.
	 * This xholon may not have been configured with a IXholonClass,
	 * so it can't find its Application by asking its IXholonClass.
	 * @see org.primordion.xholon.base.Xholon#getApp()
	 */
	public IApplication getApp()
	{
		return (IApplication)getParentNode().getApp();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getName()
	 */
	public String getName()
	{
		if (xhc == null) {
			return "CutCopyPaste_" + getId();
		}
		else {
			return super.getName();
		}
	}
	
}
