/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

package org.primordion.xholon.base;

//import java.io.Reader;
//import java.io.StringReader;

//import com.google.gwt.http.client.RequestBuilder;
//import com.google.gwt.http.client.RequestCallback;
//import com.google.gwt.http.client.RequestException;
//import com.google.gwt.http.client.Request;
//import com.google.gwt.http.client.Response;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.xml.IXmlReader;
import org.primordion.xholon.io.xml.XmlReaderFactory_gwt;
//import org.primordion.xholon.util.MiscIo;

/**
 * Set parameter values for applications and individual xholons, typically at configuration time.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.6 (Created on April 23, 2007)
 */
public class Parameters {
  
  /**
   * Configure an instance of IApplication, by reading an XML parameters file.
   * @param fileName The name of a file that contains XML.
   * @param app An instance of IApplication.
   */
  public static void xmlFile2Params(String fileName, IApplication app)
  throws XholonConfigurationException
  {
    /* GWT
    if (Msg.debugM) {System.out.println("PRF  " + fileName);}
    Reader in = MiscIo.openInputFile(fileName);
    if (in == null) {return;}
    IXmlReader xmlReader = XmlReaderFactory_gwt.getXmlReader(in);
    int eventType = xmlReader.getEventType();
    readConfigFromXmlSource(app, xmlReader, eventType);
    MiscIo.closeInputFile(in);
    */
  }
  
  /**
   * Configure an instance of IApplication, by reading an XML parameters String.
   * @param xmlString A String that contains XML.
   * @param app An instance of IApplication.
   */
  public static void xmlString2Params(String xmlString, IApplication app)
  throws XholonConfigurationException
  {
    /* GWT
    if (Msg.debugM) {System.out.println("PRS  " + xmlString);}
    Reader in = new StringReader(xmlString);
    IXmlReader xmlReader = XmlReaderFactory_gwt.getXmlReader(in);
    int eventType = xmlReader.getEventType();
    readConfigFromXmlSource(app, xmlReader, eventType);
    */
    System.out.println(xmlString);
    if (xmlString.length() > 0) {
      final IXmlReader xmlReader = XmlReaderFactory_gwt.getXmlReader(xmlString);
      final int eventType = xmlReader.getEventType();
      try {
        readConfigFromXmlSource(app, xmlReader, eventType);
      } catch(XholonConfigurationException e) {
        System.out.println("Parameters: unable to readConfigFromXmlSource");
      }
    }
  }
  
  /**
   * GWT
   * Configure an instance of IApplication, by reading an XML parameters String.
   * @param xmlString A String that contains XML.
   * @param app An instance of IApplication.
   */
  public static void xmlUri2Params(String uri, final IApplication app)
  throws XholonConfigurationException
  {
    System.out.println("Parameters.xmlUri2Params(" + uri + " starting ...");
    if ((uri.startsWith("http://")) || (uri.startsWith("https://"))) {
    
      // use GWT JSNI
      String respString = makeSyncAjaxCall(uri, null, "GET");
      // the first 3 characters are the HTTP status code; 200 = OK
      int status = Integer.parseInt(respString.substring(0, 3));
      System.out.println(status);
      if (status == 200) {
        String xmlString = respString.substring(3);
        System.out.println(xmlString);
        if (xmlString.length() > 0) {
          final IXmlReader xmlReader = XmlReaderFactory_gwt.getXmlReader(xmlString);
          final int eventType = xmlReader.getEventType();
          try {
            readConfigFromXmlSource(app, xmlReader, eventType);
          } catch(XholonConfigurationException e) {
            System.out.println("Parameters: unable to readConfigFromXmlSource");
          }
        }
      }
      else {
        System.out.println("HTTP error");
      }

      /*
      // use the GWT classes
      RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, uri); // URL.encode(uri));
      System.out.println("Parameters.xmlUri2Params( 1");
      try {
        Request request = builder.sendRequest(null, new RequestCallback() {

          @Override
          public void onResponseReceived(Request req, Response resp) {
            final String xmlString = resp.getText();
            System.out.println("Parameters: onResponseReceived " + xmlString);
            if (xmlString.length() > 0) {
              final IXmlReader xmlReader = XmlReaderFactory_gwt.getXmlReader(xmlString);
              final int eventType = xmlReader.getEventType();
              try {
                readConfigFromXmlSource(app, xmlReader, eventType);
              } catch(XholonConfigurationException e) {
                System.out.println("Parameters: onResponseReceived unable to readConfigFromXmlSource");
              }
            }
          }

          @Override
          public void onError(Request res, Throwable throwable) {
            // handle errors
            System.out.println("Parameters: Error occurred");
          }
        });
      } catch (RequestException e) {
          e.printStackTrace();
      */
    }
    else {
      System.out.println("Parameters.xmlUri2Params( else");
    }
  }
  
  private static native String makeSyncAjaxCall(String url, String msgText, String conType)/*-{
    var xhReq = new XMLHttpRequest();
    xhReq.open(conType, url, false);
    if(conType == "POST") xhReq.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
    xhReq.send(msgText);
    var serverResponse = xhReq.status + xhReq.responseText;
    return serverResponse;
  }-*/;

  /**
   * Read configuration values from an XML file.
   * @param fileName Name of the configuration file. ex: Config_HelloWorld.xml
   * @param app An application that needs parameters read in.
   * @throws XholonConfigurationException
   */
  protected static void readConfigFromXmlSource(IApplication app, IXmlReader xmlReader, int eventType)
  throws XholonConfigurationException
  {
    boolean showParams = false;
    String pName = null;
    String pValue = null;
    int attrCount = 0;
    int i = 0;
    while (eventType != IXmlReader.END_DOCUMENT) {
      if (eventType == IXmlReader.START_DOCUMENT) {}
      else if (eventType == IXmlReader.END_DOCUMENT) {}
      else if (eventType == IXmlReader.START_TAG) {
        if (xmlReader.getName().equals("param")) {
          attrCount = xmlReader.getAttributeCount();
          i = 0;
          while (i < attrCount) {
            if ("name".equals(xmlReader.getAttributeName(i))) {
              pName = xmlReader.getAttributeValue(i);
            }
            else if ("value".equals(xmlReader.getAttributeName(i))) {
              pValue = xmlReader.getAttributeValue(i);
            }
            i++;
          }
          if ("ShowParams".equals(pName)) {
            if ("true".equals(pValue)) {showParams = true;}
            else {showParams = false;}
          }
          else {
            boolean response = app.setParam(pName, pValue);
            if (response) {
              if (showParams) {
                System.out.println(pName + " : " + pValue);
              }
            }
            else {
              // this may be an app-specific param defined in a XholonWorkbook using <params><param ...
              setParamNative(app, pName, pValue);
            }
          }
        }
      }
      else if (eventType == IXmlReader.END_TAG) {}
      else if (eventType == IXmlReader.TEXT) {}
      eventType = xmlReader.next();
    } // end while
  }
  
  protected static native void setParamNative(IApplication app, String pName, String pValue) /*-{
    app[pName] = pValue;
  }-*/;
  
}
