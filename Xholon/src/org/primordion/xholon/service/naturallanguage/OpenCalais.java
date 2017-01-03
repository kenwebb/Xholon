/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2017 Ken Webb
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

package org.primordion.xholon.service.naturallanguage;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.util.ClassHelper;

/**
 * A NaturalLanguage concrete class.
 * 
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://cloud.google.com/natural-language/">Open Calais site</a>
 * @since 0.9.1 (Created on January 2, 2017)
 */
public class OpenCalais extends XholonWithPorts implements INaturalLanguage {
  
  public OpenCalais() {}
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    switch (msg.getSignal()) {
    case SIG_TEST_REQ:
      return new Message(SIG_TEST_RESP, this.test(msg), this, msg.getSender());
    default:
      return super.processReceivedSyncMessage(msg);
    } // end switch
  } // end processReceivedSyncMessage()
  
  protected Object test(IMessage msg) {
    //String[] data = ((String)msg.getData()).split("|");
    String[] data = splitNative((String)msg.getData(), "|");
    int testNum = Integer.parseInt(data[0]);
    IXholon sender = msg.getSender();
    consoleLog("OpenCalais.test()");
    consoleLog(data);
    switch (testNum) {
    case 1:
    {
      String accessToken = "TODO";
      String url = "https://api.thomsonreuters.com/permid/calais?access-token=" + accessToken;
      String str = makeSyncAjaxCall(url, data[1], "POST", accessToken);
      return str;
    }
    case 2:
    {
      // return all values of <rdf:Description rdf:about
      // assume sender is rdf:RDF node
      /*IXholon node = sender.getFirstChild();
      while (node != null) {
        String attrVal = getAttrVal(node, "rdf:about");
        this.println(attrVal);
        node = node.getNextSibling();
      }
      return null;*/
      Object descrObj = this.makeDescriptionsObject(sender);
      Object typesObj = this.makeTypesObject(sender);
      consoleLog(typesObj);
      this.makePortsAndAttrs(sender, sender.getNextSibling(), descrObj);
      return descrObj;
    }
    } // end switch
    return null;
  }
  
  protected native String[] splitNative(String str, String delim) /*-{
    return str.split(delim);
  }-*/;
  
  //private native String getAttrVal(IXholon node, String attr) /*-{
  //  return node[attr];
  //}-*/;
  
  protected native Object makeDescriptionsObject(IXholon parentNode) /*-{
    var obj = {};
    var node = parentNode.first(); // this should be a rdf:Description node
    while (node) {
      var about = node["rdf:about"];
      if (about) {
        if (obj[about]) {
          // this about-URI already exists
          $wnd.console.log(about + " already exists");
        }
        else {
          obj[about] = node;
        }
      }
      node = node.next();
    }
    return obj;
  }-*/;
  
  // makeIhStr()
  protected native Object makeTypesObject(IXholon rootRdfNode) /*-{
    var xhcRoot = rootRdfNode.xhc().parent();
    var obj = {};
    var classXml = "<_-.XholonClass>\n";
    var node = rootRdfNode.first(); // this should be a rdf:Description node
    while (node) {
      var rdfTypeNode = node.first();
      if (rdfTypeNode && (rdfTypeNode.xhc().name() == "type")) {
        var rdfTypeFull = rdfTypeNode["rdf:resource"];
        if (rdfTypeFull) {
          rdfTypeFull = rdfTypeFull.trim();
          var pos = rdfTypeFull.lastIndexOf("/");
          var rdfType = rdfTypeFull.substring(pos+1);
          if (!obj[rdfType]) {
            obj[rdfType] = rdfTypeFull;
            classXml += "  <" + rdfType + "OC/>\n"; // append "OC" to mark this as a Open Calais type
            classXml += "  <" + rdfType + "OCs/>\n"; // pluralized container name
            // TODO append an instance of the pluralized container to newsStoryRoot
          }
        }
      }
      node = node.next();
    }
    classXml += "</_-.XholonClass>\n";
    $wnd.console.log(classXml);
    xhcRoot.append(classXml);
    return obj;
  }-*/;
  
  protected native void makePortsAndAttrs(IXholon parentNode, IXholon newsStoryRoot, Object descrptsObj) /*-{
    var node = parentNode.first(); // this should be a rdf:Description node
    while (node) {
      var newsStoryNode = null;
      var cnode = node.first(); // this should be an rdf:type node
      if (cnode && (cnode.xhc().name() == "type")) {
        var rdfTypeFull = cnode["rdf:resource"];
        if (rdfTypeFull) {
          rdfTypeFull = rdfTypeFull.trim();
          var pos = rdfTypeFull.lastIndexOf("/");
          var rdfType = rdfTypeFull.substring(pos+1);
          var xmlStr = "<" + rdfType + "OC/>\n"; // append "OC" to mark this as a Open Calais type
          // TODO should append to the appropriate newsStoryRoot container
          newsStoryRoot.append(xmlStr);
          newsStoryNode = newsStoryRoot.last();
        }
      }
      while (cnode) {
        var cnodeXhcName = cnode.xhc().name();
        var resource = cnode["rdf:resource"];
        if (resource && (cnodeXhcName != "docId")) {
          var remoteNode = descrptsObj[resource];
          if (remoteNode) {
            // add a new port to the Description node
            $wnd.console.log(cnodeXhcName + " link from " + node.name() + " to " + remoteNode.name());
            node[cnodeXhcName] = remoteNode;
            newsStoryNode[cnodeXhcName] = remoteNode; // TODO it should point to the child of NewsStory
          }
          else {
            $wnd.console.log("  " + cnode.name() + " rdf:resource " + resource + " links to nothing");
          }
        }
        else {
          // assume that this cnode contains a String value
          var str = cnode.text();
          if (str) {
            var attrName = cnodeXhcName;
            if (attrName == "name") {
              // I have to change the name because of a potential conflict with later calling node.name()
              attrName = "c:name";
            }
            node[attrName] = str;
            newsStoryNode[attrName] = str;
          }
        }
        var nextCnode = cnode.next();
        //cnode.remove(); // remove cnode from the Xholon tree
        cnode = nextCnode;
      }
      $wnd.console.log(node);
      node = node.next();
    }
  }-*/;
  
  protected native String makeSyncAjaxCall(String url, String msgText, String conType, String accessToken)/*-{
    //$.support.cors = true;  OpenCalais01.js does this
    var xhReq = new XMLHttpRequest();
    xhReq.open(conType, url, false);
    if(conType == "POST") {
      xhReq.setRequestHeader('Content-Type','text/raw');
      xhReq.setRequestHeader('OutputFormat', 'application/json');
      xhReq.setRequestHeader('X-AG-Access-Token', accessToken);
    }
    
    xhReq.send(msgText);
    var serverResponse = xhReq.status + xhReq.responseText;
    return serverResponse;
  }-*/;
	
}
