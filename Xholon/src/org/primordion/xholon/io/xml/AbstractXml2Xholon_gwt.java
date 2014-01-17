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

package org.primordion.xholon.io.xml;

import java.util.HashSet;
import java.util.Set;

//import com.google.gwt.http.client.RequestBuilder;
//import com.google.gwt.http.client.RequestCallback;
//import com.google.gwt.http.client.RequestException;
//import com.google.gwt.http.client.Request;
//import com.google.gwt.http.client.Response;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.storage.client.Storage;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.Attribute;
import org.primordion.xholon.base.IInheritanceHierarchy;
import org.primordion.xholon.base.IReflection;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Msg;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.service.creation.ITreeNodeFactory;

/**
 * Abstract class for transforming XML to Xholon, for GWT.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on November 7, 2013)
 */
@SuppressWarnings("serial")
public abstract class AbstractXml2Xholon_gwt extends Xholon implements IXml2Xholon {

  /**
	 * <p>The name of the concrete class that implements IXmlDb .
	 * This will be one of the following:</p>
	 * <p>org.primordion.xholon.io.xmldb.XmlDbExist</p>
	 * <p>org.primordion.xholon.io.xmldb.XmlDbMXQuery</p>
	 */
	//private static final String XML_DATABASE_CLASSNAME = "org.primordion.xholon.io.xmldb.XmlDbExist";
	private static final String XML_DATABASE_CLASSNAME = "org.primordion.xholon.io.xmldb.XmlDbMXQuery";
	
	/**
	 * The URI prefix used to access an XML database.
	 */
	private static final String XML_DATABASE_URIPREFIX = "xmldb:";
	
	/**
	 * The filename suffix used by XQuery expression files.
	 * The suffix might be one of: .xq,.xql,.xqm,.xquery,.xqy,.xqws
	 */
	private static final String XML_DATABASE_FILENAMESUFFIX = ".xq";
	
	/** Factory that allocates memory for nodes. */
	protected ITreeNodeFactory factory = null;
	
	/** The inheritance hierarchy that corresponds to this containment hierarchy. */
	protected IInheritanceHierarchy inherHier = null;
	
	/** The application node that will own the new xholons. */
	protected IApplication app = null;
	
	/** The location of xincluded files (required in subclasses in this implementation).*/
	protected String xincludePath = "./config/_common/";
	
	/** The prefix for XInclude.
	 *  TODO get this from the XML stream.
	 *  ex: xi:include
	 */
	protected String xincludePrefix = "xi";
	
	/**
	 * Set of constructor-arg nodes for later processing.
	 * It's a set to ensure that duplicates are discarded.
	 */
	Set constructorArgs = new HashSet();
	
	@Override
	public void setTreeNodeFactory(ITreeNodeFactory factory) {
		this.factory = factory;
	}

	@Override
	public void setXincludePath(String xincludePath) {this.xincludePath = xincludePath;}

	/* 
	 * @see org.primordion.xholon.io.xml.IXml2Xholon#setApp(org.primordion.xholon.app.IApplication)
	 */
	public void setApp(IApplication app) {this.app = app;}

	@Override
	public String getXincludePath() {return xincludePath;}

	@Override
	public String getXincludePrefix() {
		return xincludePrefix;
	}

	@Override
	public void setXincludePrefix(String xincludePrefix) {
		this.xincludePrefix = xincludePrefix;
	}

	@Override
	public void setInheritanceHierarchy(IInheritanceHierarchy ih) {
		inherHier = ih;
	}
	
	/**
	 *
	 * TODO check both "/" and "\\" as delimiters
	 * @param resourceName This could be a fileName, or a uri,
	 * or the name of a resource in a ClientBundle (ex: Resources.java).
	 * Examples:
	 * "org/primordion/user/app/climatechange/model04/i18n/EnergyBudgetSvgLabels_en.xml"
	 * "http://127.0.0.1:8888/org/primordion/user/app/climatechange/model04/i18n/EnergyBudgetSvgLabels_en.xml"
	 * "EnergyBudgetSvgLabels_en"
	 * The resource name in all of these cases would be: "EnergyBudgetSvgLabels_en"
	 */
	public IXholon xmlGwtresource2Xholon(String resourceName, IXholon parentXholon) {
	  String xmlString = xmlGwtresource2String(resourceName);
	  System.out.println("AbstractXml2Xholon_gwt xmlString: " + xmlString);
	  if (xmlString != null) {
	    IXholon subtree = xmlString2Xholon_internal(xmlString, parentXholon);
	    System.out.println("AbstractXml2Xholon_gwt subtree: " + subtree);
	    return subtree;
	  }
	  return null;
	}
	
	public String xmlGwtresource2String(String resourceName) {
	  int begin = resourceName.lastIndexOf("/");
	  if (begin == -1) {
	    begin = 0;
	  }
	  else {
	    begin++; // move one char past the "/"
	  }
	  int end = resourceName.indexOf(".", begin);
	  if (end == -1) {end = resourceName.length();}
	  String cbKey = resourceName.substring(begin, end);
	  System.out.println("AbstractXml2Xholon_gwt trying cbKey: " + cbKey);
	  return app.rcConfig(cbKey, app.findGwtClientBundle());
	}

	@Override
	public IXholon xmlString2Xholon(String xmlString, IXholon parentXholon) {
		return xmlString2Xholon_internal(xmlString, parentXholon);
	}
	
	/**
	 * Transform an XML String into a Xholon subtree.
	 * This is an internal version of xmlString2Xholon(...).
	 * It should be called by any method in this class or in any subclasses,
	 * rather than calling the public method.
	 * @param xmlString A String that contains XML
	 * @param parentXholon The Xholon parent of the nodes that will be created from the XML String.
	 * @return The top level newly-created Xholon.
	 */
	protected IXholon xmlString2Xholon_internal(String xmlString, IXholon parentXholon)
	{
		IXmlReader xmlReader = XmlReaderFactory_gwt.getXmlReader(xmlString);
		int eventType = xmlReader.getEventType();
		return xml2Xh(parentXholon, xmlReader, eventType);
	}

	@Override
	// not used in this implementation
	public IXholon nonXmlString2Xholon(String nonXmlString,
			IXholon parentXholon, String contentType) {
		return null;
	}

	@Override
	public IXholon xmlUri2Xholon(String uri, IXholon parentXholon) {
		if (Msg.debugM) {System.out.println("URI " + uri);}
		return handleConstructorArgs(xmlUri2Xholon_internal(uri, parentXholon));
	}

	@Override
	// not used in this implementation
	public IXholon xmlFile2Xholon(String fileName, IXholon parentXholon) {
		return null;
	}

	@Override
	// not used in this implementation
	public IXholon xmlVfs2Xholon(String uri, IXholon parentXholon) {
		return null;
	}
	
		/**
	 * The XML parsing may have discovered that some nodes should have been created using
	 * constructors that take constructor arguments. Because we're using a stream parser,
	 * it's difficult to look ahead while parsing. Once the entire subtree has been constructed,
	 * and if there are nodes that should have been constructed with constructor arguments,
	 * then this method can be called to recreate those nodes using those arguments.
	 * @param subtree A Xholon subtree.
	 * @return A revised Xholon subtree.
	 */
	protected IXholon handleConstructorArgs(IXholon subtree)
	{
		if (constructorArgs.isEmpty()) {return subtree;}
		Object[] nodeArray = constructorArgs.toArray();
		for (int i = 0; i < nodeArray.length; i++) {
			// get a node that has constructor-args
			IXholon node = (IXholon)nodeArray[i];
			if (node == subtree) {
				// the root node of the subtree is being replaced
				subtree = handleConstructorArg(node);
			}
			else {
				handleConstructorArg(node);
			}
		}
		return subtree;
	}
	
	/**
	 * Transform an XML URI into a Xholon subtree.
	 * This is an internal version of xmlString2Xholon(...).
	 * It should be called by any method in this class or in any subclasses,
	 * rather than calling the public method.
	 * @param uri A URI, or a filename.
	 * @param parentXholon The Xholon parent of the nodes that will be created from the XML String.
	 * @return The top level newly-created Xholon.
	 */
	protected IXholon xmlUri2Xholon_internal(String uri, final IXholon parentXholon)
	{
	  //consoleLog("Starting xmlUri2Xholon_internal(" + uri);
	  if ((uri.startsWith("http://")) || (uri.startsWith("https://"))) {
	    
	    if (uri.startsWith("https://gist.github.com/")) {
        // ex uri: "https://gist.github.com/kenwebb/3378073/raw/network_csh.xml"
	      //consoleLog("this is a cross-domain gist request; trying sessionStorage cache");
	      Storage storage = Storage.getSessionStorageIfSupported();
	      if (storage != null) {
	        String workbookJsonStr = storage.getItem("workbookJsonStr");
	        if (workbookJsonStr != null) {
	          int begin = uri.lastIndexOf("/");
	          if (begin != -1) {
	            String fileName = uri.substring(begin+1);
	            try {
                String content = JSONParser
                .parseStrict(workbookJsonStr).isObject()
                .get("data").isObject()
                .get("files").isObject()
                .get(fileName).isObject()
                .get("content").isString().stringValue();
                IXholon subtree = xmlString2Xholon_internal(content, parentXholon);
	              return subtree;
              } catch(NullPointerException e) {
                return null;
              }
	          }
	        }
	      }
	      return null;
	    }
	    
	    // use GWT JSNI; will only work for same-domain requests (http://www.primordion.com)
	    String respString = makeSyncAjaxCall(uri, null, "GET");
	    // the first 3 characters are the HTTP status code; 200 = OK
	    int status = Integer.parseInt(respString.substring(0, 3));
	    //consoleLog(status);
	    if (status == 200) {
	      String xmlString = respString.substring(3);
  	    //consoleLog(xmlString);
	      IXholon subtree = xmlString2Xholon_internal(xmlString, parentXholon);
	      return subtree;
	    }
	    else {
	      //consoleLog("HTTP error");
	      return xmlGwtresource2Xholon(uri, parentXholon);
	      //return null;
	    }
	    
	    /*
	    // use the GWT classes
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, uri); // URL.encode(uri));
      try {
        Request request = builder.sendRequest(null, new RequestCallback() {

          @Override
          public void onResponseReceived(Request req, Response resp) {
            final String xmlString = resp.getText();
            System.out.println("onResponseReceived " + xmlString);
            if (xmlString.length() > 0) {
              // TODO the following returns an IXholon subtree
					    IXholon subtree = xmlString2Xholon_internal(xmlString, parentXholon);
					    System.out.println(subtree);
				    }
          }

          @Override
          public void onError(Request res, Throwable throwable) {
            // handle errors
            System.out.println("Error occurred");
          }
        });
      } catch (RequestException e) {
          e.printStackTrace();
      }
      */
    }
	  else {}
	  return null;
	}
	
	private native String makeSyncAjaxCall(String url, String msgText, String conType)/*-{
    var xhReq = new XMLHttpRequest();
    xhReq.open(conType, url, false);
    if(conType == "POST") xhReq.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
    xhReq.send(msgText);
    var serverResponse = xhReq.status + xhReq.responseText;
    return serverResponse;
  }-*/;
	
	/**
	 * Handle one node and its children.
	 * @param node A Xholon node.
	 * @return The replacement node.
	 */
	protected IXholon handleConstructorArg(IXholon node)
	{
		// create a new version of the node using the constructor arguments
		IReflection ir = ReflectionFactory.instance();
		IXholon replacementNode = (IXholon)ir.createObject_UsingConstructor(node.getClass(), node);
		// get id, xhClass, roleName from current node
		replacementNode.setId(node.getId());
		replacementNode.setXhc(node.getXhc());
		replacementNode.setRoleName(node.getRoleName());
		replacementNode.setPorts();
		// remove the constructor arguments
		removeConstructorAttributes(node);
		// do the replacement
		node.replaceNode(replacementNode);
		return replacementNode;
	}
	
	/**
	 * Remove all constructor attributes.
	 * Only attributes that have the roleName "constructor-arg" are removed.
	 */
	protected void removeConstructorAttributes(IXholon node)
	{
		IXholon attr = node.getFirstChild();
		IXholon nextAttr = null;
		while (attr != null) {
			nextAttr = attr.getNextSibling();
			if ((IReflection.CONSTRUCTOR_ARG.equals(attr.getRoleName()))
					&& (Attribute.isAttribute(attr))) {
				attr.removeChild();
			}
			attr = nextAttr;
		}
	}
	
	/**
	 * Make an IXholonClass instance.
	 * @param tagName
	 * @return
	 */
	protected IXholonClass makeXholonClass(String tagName)
	{
	  System.out.println("makeXholonClass " + tagName);
		IXholonClass newXholonClass = null;
		// if there is an implementing Java class, create an instance of XholonClass and configure it
		try {
			newXholonClass = factory.getXholonClassNode();
		} catch (XholonConfigurationException e) {
		  System.out.println("makeXholonClass returning null");
			return null;
		}
		newXholonClass.setId(app.getNextXholonClassId());
		newXholonClass.setApp(app);
		newXholonClass.setMechanism(tagName, IXml2XholonClass.MECHANISM_DEFAULT_NAMESPACEURI, "", newXholonClass.getId());
		newXholonClass.setName(tagName);
		return newXholonClass;
	}

	@Override
	public abstract IXholon xml2Xh(IXholon parentXholon, IXmlReader xmlReader, int eventType);

}
