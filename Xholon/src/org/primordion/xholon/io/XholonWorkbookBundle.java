/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2014 Ken Webb
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

import com.google.gwt.storage.client.Storage;

import java.util.HashMap;
import java.util.Map;

import org.primordion.xholon.base.Xholon;

/**
 * Read a XholonWorkbook, and make its text-based named resources available.
 * It looks for the XholonWorkbook as a named item in the HTML5 browser's localStorage.
 * Named resources include: _xhn ih cd csh info svg
 * This class is analogous to the GWT ClientBundleWithLookup interface,
 * and is intended primarily for use by Application.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on March 21, 2014)
 */
public class XholonWorkbookBundle extends Xholon {
  
  // standard resource names
  public static final String RESOURCE__XHN = "_xhn";
  public static final String RESOURCE_IH = "ih";
  public static final String RESOURCE_CD = "cd";
  public static final String RESOURCE_CSH = "csh";
  public static final String RESOURCE_INFO = "info"; // info.xml
  public static final String RESOURCE_NOTES = "notes"; // XholonWorkbook Notes
  public static final String RESOURCE_SVG = "svg";
  
  /**
   * The contents of a XholonWorkbook, as an XML string.
   */
  private String workbookContents = null;
  
  private Map<String, String> resourceMap = null;
  
  private XholonWorkbookBundle() {}
  
  /**
   * constructor
   * @param appName The name of the Xholon Java-based app (ex: "HelloWorld");
   */
  public XholonWorkbookBundle(String appName) {
    Storage storage = Storage.getLocalStorageIfSupported();
    if (storage != null) {
      workbookContents = storage.getItem(appName);
      if (exists()) {
        resourceMap = new HashMap<String, String>();
        processWb();
      }
    }
  }
  
  /**
   * Does this instance of XholonWorkbookBundle contain a XholonWorkbook.
   */
  public boolean exists() {
    return workbookContents != null;
  }
  
  /**
   * Get a named resource from the bundle.
   * All resources are XML strings.
   * @param name - the name of the desired resource (ex: "csh")
   * @return the resource, or null if no such resource is defined.
   */
  public String getResource(String name) {
    if (workbookContents == null) {return null;}
    return resourceMap.get(name);
  }
  
  /**
   * Get all resources as a Java Map.
   * @return A Map, or null.
   */
  public Map getResources() {
    return resourceMap;
  }
  
  /**
   * 
   */
  protected void processWb() {
    int pos = workbookContents.indexOf("<XholonWorkbook");
		if (pos == -1) {
		  workbookContents = null;
		  return;
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
			int endTagBeginIndex = workbookContents.indexOf("</" + tagName, startTagEndIndex+1);
			int endTagEndIndex = workbookContents.indexOf(">", endTagBeginIndex);
			String subTree = workbookContents.substring(startTagBeginIndex, endTagEndIndex+1);
			processSubTree(tagName, subTree);
			pos = workbookContents.indexOf("<", endTagEndIndex+1);
		}
  }
  
  /**
	 * Process a subtree found in the workbook.
	 * @param tagName The XML tagName for the subTree node.
	 * @param subTree The string contents of the XML subTree node.
	 */
	protected void processSubTree(String tagName, String subTree) {
	  
	  // Notes
		if ("Notes".equals(tagName)) {
			resourceMap.put(RESOURCE_NOTES, subTree);
		}
		
		// params (_xhn)
		else if ("params".equals(tagName)) {
		  resourceMap.put(RESOURCE__XHN, subTree);
		}
		
		// inheritance hierarchy (ih)
		else if ("_-.XholonClass".equals(tagName)) {
			resourceMap.put(RESOURCE_IH, subTree);
		}
		
		// class details (cd)
		else if ("xholonClassDetails".equals(tagName)) {
			resourceMap.put(RESOURCE_CD, subTree);
		}
		
		// composite structure hierarchy (csh)
		else if (tagName.endsWith("System")) {
			resourceMap.put(RESOURCE_CSH, subTree);
		}
		
		// SVG <SvgClient></SvgClient>
		else if ("SvgClient".equals(tagName)) {
			resourceMap.put(RESOURCE_SVG, subTree);
		}
		
		// script
		else if ("script".equals(tagName)) {
			// ignore simple scripts
		}
		
		// behavior
		else if (tagName.endsWith("behavior")) {
			// in many cases it is impossible to know which behaviors the user wants to use,
			// and to know which xholon is the intended parent of the behavior
			resourceMap.put(tagName, subTree);
		}
		
		else {
			resourceMap.put(tagName, subTree);
		}
	}
  
}
