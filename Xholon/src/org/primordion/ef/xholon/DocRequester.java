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

package org.primordion.ef.xholon;

//import java.io.IOException;
//import java.io.Reader;
//import java.io.Writer;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.resources.client.ClientBundleWithLookup;

import org.primordion.xholon.base.IXholon;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Request an app-specific document, typically a config .xml file.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on August 11, 2013)
 */
@SuppressWarnings("serial")
public class DocRequester extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {

	protected IXholon root;
	//protected Writer out;

	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String outFileName, String modelName, IXholon root) {
		this.root = root;
		return true;
	}

	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#writeAll()
	 */
	public void writeAll() {}
	
	/**
	 *
	 * @param fileName A local file name, or remote uri (http).
	 * @param contentType One of "_xhn" "ih" "cd" "csh" etc.
	 */
	protected void writeAll(String fileName, String contentType) {
	  /* this is the non-GWT version
		out = root.getApp().getOut();
		String fileName = root.getApp().getCompositeStructureHierarchyFile();
		Reader in = MiscIo.openInputFile(fileName);
		try {
			char[] buf = new char[8192];
			while (true) {
				int len = in.read(buf);
				if (len < 0) {break;}
				out.write(buf, 0, len);
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MiscIo.closeInputFile(in);*/
		
		// GWT version
		//String fileName = root.getApp().getCompositeStructureHierarchyFile();
		if (fileName == null) {
		  // app is probably using a ClientBundle
		  String efText = root.getApp().rcConfig(contentType,
		    (ClientBundleWithLookup)root.getApp().findGwtClientBundle());
		  writeToTarget(efText, contentType, contentType, root);
		  return;
		}
		try {
		  final String _contentType = contentType;
		  final IXholon _root = root;
		  new RequestBuilder(RequestBuilder.GET, fileName).sendRequest("", new RequestCallback() {
        @Override
        public void onResponseReceived(Request req, Response resp) {
          if (resp.getStatusCode() == resp.SC_OK) {
            //root.println(resp.getText());
            writeToTarget(resp.getText(), _contentType, _contentType, _root);
          }
          else {
            root.println("status code:" + resp.getStatusCode());
            root.println("status text:" + resp.getStatusText());
            root.println("text:\n" + resp.getText());
          }
        }

        @Override
        public void onError(Request req, Throwable e) {
          root.println("onError:" + e.getMessage());
        }
      });
    } catch(RequestException e) {
      root.println("RequestException:" + e.getMessage());
    }
	}

}
