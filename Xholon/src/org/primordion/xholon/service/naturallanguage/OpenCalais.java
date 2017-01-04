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
 * Open Calais - A NaturalLanguage concrete class.
 * 
 * TODO
 * - use a better way to locate and name the top of the "NewsStory" subtree
 * - make the code work whether the node and attribute names include prefixes, no prefixes, concatenated names
 * - use another signal name instead of _TEST_
 * - fix "links to nothing" warnings
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://www.opencalais.com">Open Calais site</a>
 * @since 0.9.1 (Created on January 2, 2017)
 */
public class OpenCalais extends XholonWithPorts implements INaturalLanguage {
  
  public OpenCalais() {}
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    switch (msg.getSignal()) {
    case SIG_TEST_REQ:
      return new Message(SIG_TEST_RESP, this.test(msg), this, msg.getSender());
    case SIG_PROCESS_RDF_REQ:
      return new Message(SIG_PROCESS_RDF_RESP, this.processRdf(msg), this, msg.getSender());
    default:
      return super.processReceivedSyncMessage(msg);
    } // end switch
  } // end processReceivedSyncMessage()
  
  protected Object test(IMessage msg) {
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
      Object descrObj = this.makeDescriptionsObject(sender);
      Object typesObj = this.makeTypesObject(sender);
      consoleLog(typesObj);
      this.makePortsAndAttrs(sender, sender.getNextSibling(), descrObj);
      return descrObj;
    }
    /*case 3:
    {
      Object descrObj = this.step1(sender, sender.getNextSibling());
      this.step2(sender, sender.getNextSibling(), descrObj);
      return descrObj;
    }*/
    } // end switch
    return null;
  }
  
  protected Object processRdf(IMessage msg) {
    Object data = msg.getData();
    IXholon sender = msg.getSender();
    Object descrObj = this.processRdfStep1(sender, sender.getNextSibling());
    this.processRdfStep2(sender, sender.getNextSibling(), descrObj);
    return descrObj;
  }
  
  /**
   * test 3, step 1
   */
  protected native Object processRdfStep1(IXholon rdfRoot, IXholon newsStoryRoot) /*-{
    var xhcRoot = rdfRoot.xhc().parent();
    var descrObj = {};
    var typesObj = {};
    var descrNode = rdfRoot.first(); // this should be a rdf:Description node
    while (descrNode) {
      // Find the rdf:type node, if it exists
      var typeNode = descrNode.first(); // is it always the first child of descrNode ?
      var rdfType = "Description"; // the default value
      if (typeNode && (typeNode.xhc().name() == "type")) {
        var rdfTypeFull = typeNode["rdf:resource"];
        if (rdfTypeFull) {
          rdfTypeFull = rdfTypeFull.trim();
          var pos = rdfTypeFull.lastIndexOf("/");
          rdfType = rdfTypeFull.substring(pos+1);
          // Create new IH class nodes, if they don't already exist
          if (!typesObj[rdfType]) {
            typesObj[rdfType] = rdfTypeFull;
            var ihXml = "<_-.XholonClass>\n";
            ihXml += "  <" + rdfType + "OC/>\n"; // append "OC" to mark this as a Open Calais type
            ihXml += "  <" + rdfType + "OCs/>\n"; // pluralized container name
            ihXml += "</_-.XholonClass>\n";
            xhcRoot.append(ihXml);
            // TODO append an instance of the pluralized container to newsStoryRoot
          }
        }
        else {$wnd.console.log("WARNING rdf:resource not found for " + typeNode.toString());}
      }
      else {$wnd.console.log("WARNING type node not found for " + descrNode.toString());}
            
      // Create new CSH node as child of NewsStory
      var cshXml = "<" + rdfType + "OC/>\n"; // append "OC" to mark this as a Open Calais type
      // TODO should append to the appropriate newsStoryRoot container
      newsStoryRoot.append(cshXml);
      var newsStoryNode = newsStoryRoot.last();
      
      // Add new NewsStory node to Description[about] object
      var about = descrNode["rdf:about"];
      if (about) {
        if (!descrObj[about]) {
          descrObj[about] = newsStoryNode;
        }
        else {$wnd.console.log("WARNING duplicate about attribute " + about);}
      }
      else {$wnd.console.log("WARNING rdf:about not found for " + descrNode.toString());}
      
      descrNode = descrNode.next();
    }
    return descrObj;
  }-*/;
  
  /**
   * test 3, step 2
   */
  protected native Object processRdfStep2(IXholon rdfRoot, IXholon newsStoryRoot, Object descrObj) /*-{
    var descrNode = rdfRoot.first(); // this should be a rdf:Description node
    var newsStoryNode = newsStoryRoot.first(); // there should be a corresponding newsStoryNode for each descrNode
    while (descrNode && newsStoryNode) {
      var cnode = descrNode.first(); // this should be an rdf:type node
      while (cnode) {
        var cnodeXhcName = cnode.xhc().name();
        var resource = cnode["rdf:resource"];
        if (resource && (cnodeXhcName != "docId")) {
          var remoteNode = descrObj[resource];
          if (remoteNode) {
            // add a new port to the Description node
            //$wnd.console.log(cnodeXhcName + " link from " + newsStoryNode.name() + " to " + remoteNode.name());
            //descrNode[cnodeXhcName] = remoteNode;
            newsStoryNode[cnodeXhcName] = remoteNode;
          }
          else {
            $wnd.console.log("WARNING " + cnode.name() + " rdf:resource " + resource + " links to nothing");
          }
        }
        else {
          // assume that this cnode contains a String value
          var str = cnode.text();
          if (str) {
            var attrName = cnodeXhcName;
            
            switch (attrName) {
            case "name":
              // I have to change the name because of a potential conflict with later calling descrNode.name()
              attrName = "c:name";
              // Optionally make a roleName from c:name
              if (str) {newsStoryNode.role(str);}
              break;
            case "relevance":
              // RelevanceInfo type
              if (str) {newsStoryNode.role("relevance " + str);}
              break;
            case "exact":
              // InstanceInfo type  (remove single and double quotes)
              if (str) {newsStoryNode.role(str.replace(/["']/g, "").substring(0,15));}
              break;
            case "detection":
              // InstanceInfo type
              if (str) {newsStoryNode.anno(str);}
              break;
            case "quotation":
              // Quotation type  (remove single and double quotes)
              if (str) {newsStoryNode.role(str.replace(/["']/g, "").substring(0,15));}
              break;
            case "aggregate":
              // Confidence type
              if (str) {newsStoryNode.role("aggregate " + str);}
              break;
            default:
              break;
            }
            
            //descrNode[attrName] = str;
            newsStoryNode[attrName] = str;
          }
        }
        var nextCnode = cnode.next();
        //cnode.remove(); // remove cnode from the Xholon tree
        cnode = nextCnode;
      }
      //$wnd.console.log(newsStoryNode);
      descrNode = descrNode.next();
      newsStoryNode = newsStoryNode.next();
      if ((descrNode && !newsStoryNode) || (!descrNode && newsStoryNode)) {
        $wnd.console.log("WARNING unequal numbers of descrNode and newsStoryNode");
      }
    }
    return null;
  }-*/;
  
  protected native String[] splitNative(String str, String delim) /*-{
    return str.split(delim);
  }-*/;
  
  /**
   * test 2
   */
  protected native Object makeDescriptionsObject(IXholon rootRdfNode) /*-{
    var obj = {};
    var node = rootRdfNode.first(); // this should be a rdf:Description node
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
  
  /**
   * test 2
   */
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
  
  /**
   * test 2
   */
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
  
  /**
   * test 1
   */
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
