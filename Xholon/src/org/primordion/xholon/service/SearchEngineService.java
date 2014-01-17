/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2011 Ken Webb
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

package org.primordion.xholon.service;

import com.google.gwt.user.client.Window;
import com.google.gwt.safehtml.shared.UriUtils;

//import java.util.Locale;
import java.util.Map;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.io.BrowserLauncher;
import org.primordion.xholon.util.StringTokenizer;

/**
 * Provide a list of available search engines, and invoke the one selected.
 * <p>TODO wikipedia has lots of List_of_abc entries</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on November 2, 2011)
 */
public class SearchEngineService extends AbstractXholonService {
	private static final long serialVersionUID = 6496373837634296248L;

	/** Whether or not the roleName should be taken as the search term. */
	private boolean preferRoleName = true;
	
	/**
	 * Wikipedia, and possibly other sites, have numerous "List_of" pages.
	 * If this value is null, then no attempt will be made to search for a "List_of" page.
	 * Otherwise, if the search term is plural,
	 * then the value of this variable will be inserted into the search term.
	 * ex: "Stars" becomes "List_of_stars" .
	 * The value can be localized to any other language.
	 */
	private String listOf = "List_of";
	
	/*
	 * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		if (serviceName.startsWith(getXhcName())) {
			return this;
		}
		else {
			return null;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getActionList()
	 */
	public String[] getActionList() {
		IXholon map = getFirstChild();
		if (map == null) {return null;}
		int numActions = map.getNumChildren(false);
		if (numActions == 0) {return null;}
		String[] actionNames = new String[numActions];
		IXholon node = map.getFirstChild();
		int ix = 0;
		while (node != null) {
			actionNames[ix++] = node.getRoleName();
			node = node.getNextSibling();
		}
		return actionNames;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#doAction(java.lang.String)
	 * action="searchEngineName,nodeClassName" or
	 * action="searchEngineName,nodeClassName,roleName"
	 * TODO the searchUrl should optionally include the replaceStr "_" etc.
	 */
	@SuppressWarnings("unchecked")
	public void doAction(String action)
	{
	  System.out.println("SES action:" + action);
		if (action == null) {return;}
		Map<String, String> map = (Map<String, String>)getFirstChild();
		if (map == null) {return;}
		boolean shouldBeLowerCase = false;
		boolean shouldBeCapitalized = false; // HelloWorld becomes Hello world
		boolean shouldBeListOf = false;
		StringTokenizer st = new StringTokenizer(action, ",");
		String searchEngineName = st.nextToken();
		String searchTermOriginal = st.nextToken();
		if (listOf != null && isPlural(searchTermOriginal)) {
			// we only check the nodeClassName, and not the roleName
			shouldBeListOf = true;
		}
		if (st.hasMoreTokens() && preferRoleName) {
			searchTermOriginal = st.nextToken();
		}
		String searchTerm = searchTermOriginal;
		String replaceStr = "+"; // replace camel case with this String
		if (searchEngineName.startsWith("Wik")) {
			//TODO usually only the first letter should be uppercase
			replaceStr = "_";
		}
		else if (searchEngineName.startsWith("DBpedia")) {
			replaceStr = "_";
		}
		else if (searchEngineName.startsWith("Freebase")) {
			replaceStr = "_";
			shouldBeLowerCase = true;
		}
		else if (searchEngineName.startsWith("Azimuth")) {
			shouldBeCapitalized = true;
		}
		searchTerm = splitCamelCase(searchTerm, replaceStr);
		if (shouldBeLowerCase) {
			searchTerm = searchTerm.toLowerCase();
		}
		if (shouldBeCapitalized) {
			searchTerm = searchTerm.charAt(0) + searchTerm.substring(1).toLowerCase();
		}
		if (shouldBeListOf) {
			searchTerm = listOf + replaceStr + searchTerm.toLowerCase();
		}
		searchTerm += handleNodeSelections(searchTermOriginal, replaceStr, searchEngineName);
		String searchUrl = map.get(searchEngineName);
		if (searchUrl == null) {return;}
		searchUrl = localize(searchUrl);
		searchUrl += searchTerm;
		//System.out.println(searchUrl);
		//BrowserLauncher.launch(searchUrl); // GWT
		System.out.println("SES searchUrl:" + searchUrl);
		if (UriUtils.isSafeUri(searchUrl)) {
		  Window.open(searchUrl, "_blank", ""); // safe uri
		}
	}
	
	/**
	 * Handle selection of multiple nodes.
	 * This is mostly for use with Wolfram|Alpha .
	 * 
	 * TODO additional nodes may be (1)siblings of searchTerm, or (2)children/descendants/other relationship
	 * (1) Mars Jupiter Saturn
	 * (2) Earth Mass Radius
	 * (BUT) Sun Venus Pluto are probably not siblings
	 * The comma is not required, so this issue probably goes away ?
	 * 
	 * @param searchTerm
	 * @param replaceStr
	 * @param searchEngineName
	 * @return
	 */
	protected String handleNodeSelections(String searchTerm, String replaceStr, String searchEngineName) {
		String resultStr = "";
		IXholon[] selectedNodes = null;
		IXholon service = getServiceLocatorService().getService(IXholonService.XHSRV_NODE_SELECTION);
		if (service != null) {
			IMessage nodesMsg = service.sendSyncMessage(
					NodeSelectionService.SIG_GET_SELECTED_NODES_REQ, null, this);
			selectedNodes = (IXholon[])nodesMsg.getData();
			int count = 0;
			for (int i = 0; i < selectedNodes.length; i++) {
				IXholon node = selectedNodes[i];
				String nodeName = node.getXhcName();
				if (preferRoleName && node.getRoleName() != null) {
					nodeName = node.getRoleName();
				}
				if (searchTerm.equals(nodeName)) {continue;}
				if (count == 0) {
					resultStr += "%20";
				}
				else {
					resultStr += "%20"; //",";
				}
				resultStr += splitCamelCase(nodeName, replaceStr);
				count++;
			}
		}
		return resultStr;
	}
	
	/**
	 * Convert camel case to human readable.
	 * @see http://stackoverflow.com/questions/2559759/how-do-i-convert-camelcase-into-human-readable-names-in-java
	 * TODO if there's already an "_", don't do anything
	 * @param s A string that may contain camel case (ex: HelloWorld).
	 * @param r The replacement character (ex: "_").
	 * @return A string with human-readable spaces (ex: Hello World).
	 */
	public native String splitCamelCase(String s, String r)
		/* GWT
		return s.replaceAll(
		  String.format("%s|%s|%s",
		  "(?<=[A-Z])(?=[A-Z][a-z])",
		  "(?<=[^A-Z])(?=[A-Z])",
		  "(?<=[A-Za-z])(?=[^A-Za-z])"
		  ),
		  r
		);*/
		
		/*-{
		var rr = r + '$&';
		return s.charAt(0).toUpperCase() + s.substr(1).replace(/[A-Z]/g, rr);
		}-*/
	;
	
	/**
	 * Localize the search URL.
	 * For example, search the Spanish wikipedia rather than the default English wikipedia.
	 * http://es.wikipedia.org/wiki/ instead of http://en.wikipedia.org/wiki/
	 * @param searchUrl
	 * @return
	 */
	protected String localize(String searchUrl) {
		//String lCode = getLocaleLanguageCode();
		//System.out.println("[" + lCode + "]");
		// TODO
		return searchUrl;
	}
	
	/* GWT
	protected String getLocaleLanguageCode() {
		Locale locale = Locale.getDefault();
		return locale.getLanguage();
	}*/

	/**
	 * Is the search term plural?
	 * Note: This only works for English, and does not cover most of the exceptions in English.
	 * TODO make it more robust; or use a 3rd-party tool
	 * @param searchTerm ex: Stars
	 * @return
	 */
	protected boolean isPlural(String searchTerm) {
		boolean rc = false;
		if (searchTerm.endsWith("s")) {
			rc = true; // tentatively
			if (searchTerm.endsWith("ss")) {
				// ex: glass
				rc = false;
			}
			else if (searchTerm.endsWith("us")) {
				// ex: Uranus (But maybe "List_of_uranus" would find a special type of kangaroos?)
				rc = false;
			}
		}
		return rc;
	}
	
	/**
	 * @return Whether or not the roleName will be taken as the search term.
	 */
	public boolean isPreferRoleName() {
		return preferRoleName;
	}

	/**
	 * @param preferRoleName Whether or not the roleName should be taken as the search term.
	 * If false or if there is no roleName, then the IXholonClassName is used.
	 */
	public void setPreferRoleName(boolean preferRoleName) {
		this.preferRoleName = preferRoleName;
	}

	public String getListOf() {
		return listOf;
	}

	public void setListOf(String listOf) {
		this.listOf = listOf;
	}
	
}
