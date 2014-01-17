/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

package org.primordion.xholon.util;

/**
 * Provide sequential access to a remote text file.
 * It's intended to function similarly to java.io.BufferedReader.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on August 20, 2013)
 */
public class MiscIoGwt {
  
  /**
   * Lines of text.
   */
  protected String[] store = null;
  
  /**
   * Length of store.
   */
  protected int len = 0;
  
  /**
   * Index of the next line to read from the store.
   */
  protected int index = -1;
  
  public MiscIoGwt() {}
  
  /**
	 * Retrieve a remote text file, and make it ready for sequential access.
	 * The remote file is retrieved synchronously.
	 * TODO use a timeout to make sure the retrieval doesn't take too long.
	 * @param uri Name of remote file.
	 * @return Whether or not it was able to retrieve the remote file.
	 */
	public boolean openInputSync(String uri) {
	  // use GWT JSNI
    String respString = makeSyncAjaxCall(uri, null, "GET");
    // the first 3 characters are the HTTP status code; 200 = OK
    int status = Integer.parseInt(respString.substring(0, 3));
    System.out.println(status);
    if (status == 200) {
      String textString = respString.substring(3);
	    System.out.println(textString);
      store = textString.split("\\r?\\n");
      len = store.length;
      index = 0;
      return true;
    }
    else {
      System.out.println("HTTP error");
      return false;
    }
	}
	
	/**
	 * Make a synchronous Ajax call.
	 */
	private native String makeSyncAjaxCall(String url, String msgText, String conType)/*-{
    var xhReq = null;
    if ($wnd.XMLHttpRequest) {
      // code for IE7+, Firefox, Chrome, Opera, Safari
      xhReq = new XMLHttpRequest();
    }
    else {
      // code for IE6
      xhReq = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if (xhReq) {
      xhReq.open(conType, url, false);
      if(conType == "POST") xhReq.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
      xhReq.send(msgText);
      var serverResponse = xhReq.status + xhReq.responseText;
      return serverResponse;
    }
    return "404"; // TODO
  }-*/;
	
	/**
	 * Read a line of text.
	 * @return A line of text, or null if EOF.
	 */
	public String readLine() {
	  if (store == null) {return null;}
	  if (index == -1) {return null;}
	  if (index < len) {
	    return store[index++];
	  }
	  return null;
	}
	
  
}
