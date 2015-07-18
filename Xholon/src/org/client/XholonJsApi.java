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

package org.client;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.Xholon;

/**
 * Xholon JavaScript API.
 * 
 * To generate the developer's documentation for the API, using YUIDocs:
 *   manually copy XholonJsApi.js from ~/gwtspace/Xholon/src/org/client to ~/gwtspace/yuidoc
 *   cd ~/gwtspace/yuidoc
 *   yuidoc .
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on August 1, 2013)
 */
public class XholonJsApi {
  
  /**
   * This is for use with the JavaScript API.
   * Examples (using Chrome Developer Tools, with Cell model):
   */
   //   var node = xh.xpath("descendant::CellBilayer", xh.root());
   //   var root = xh.root(); xh.xpath("*", root);
   //   xh.xpath("attribute::port[1]", node);  // XPath is 1-based
   //   xh.xpath("..", node);
   //   xh.xpath("../following-sibling::*/*[2]", node);
   //   xh.xpath("../following-sibling::*/*[2]", node).toString();
   //   var node = xh.xpath("descendant::Flux[@roleName='TpfAtm_sw']", xh.root());
   //   var zombie = xh.xpath("descendant::Zombie[@roleName='Luke']", xh.root());
   //
  public static IXholon xpath(String expression, IXholon node) {
    if (node == null) {return null;}
    return ((Xholon)node).getXPath().evaluate(expression, node);
  }
  
  /**
   * Get all ports as an array.
   * @param node
   * @return An array of PortInformation objects, or an empty array.
   */
  public static Object[] ports(IXholon node) {
    return node.getAllPorts().toArray();
  }
  
  /**
   * Get all ports as an array.
   * @param node
   * @return An array of PortInformation objects, or an empty array.
   */
  public static Object[] portSpec(IXholon node) {
    return ReflectionFactory.instance().getAllPorts(node, false).toArray();
  }
  
  /**
   * Popup a panel containing user-specified HTML.
   * @param title - Popup title.
   * @param htmlText - HTML content of the panel.
   * @param autoHide - true if the dialog should be automatically hidden when the user clicks outside of it
   * @param modal - true if keyboard and mouse events for widgets not contained by the dialog should be ignored
   * @param left - the popup's left position relative to the browser's client area (in pixels)
   * @param top - the popup's top position relative to the browser's client area (in pixels)
   * @return - the DialogBox's underlying DOM element
   */
  public static Element popup(String title, String htmlText, boolean autoHide, boolean modal, int left, int top) {
    final DialogBox db = new DialogBox(autoHide, modal);
    db.setText(title);
    //String safeHtmlText = SafeHtmlUtils.fromString(htmlText).asString();
    //db.setWidget(new HTML(safeHtmlText)); // this doesn't work  "<" becomes &lt;  etc.
    db.setWidget(new HTML(htmlText));
    db.setPopupPosition(left, top);
    db.show();
    return db.getElement();
  }
  
  public static native void consoleLog(String s) /*-{
    $wnd.console.log(s);
  }-*/;
  
  /**
   * JUST USE JAVASCRIPT xh.test()
   * Get an instance of XholonJsApiUnitTest, for unit testing.
   * @return 
   */
  //public static void test() {
  //  XholonJsApiUnitTest apiTest = new XholonJsApiUnitTest();
  //  apiTest.runUnitTests();
  //}
  
  /**
   * Export a top-level static API that can be used by external JavaScript.
   * The full comments are in XholonJsApi.js, in YUIDoc format.
   * @param app
   */
  public static native void exportTopLevelApi(IApplication app) /*-{
    
    if (typeof $wnd.xh == "undefined") {
  	  $wnd.xh = {};
  	}
  	//xh = $wnd.xh; // make xh available from within Xholon; GWT already has a xh function
  	$wnd.xh.html = {};
  	$wnd.xh.css = {};
  	
  	// app
    $wnd.xh.app = $entry(function() {
      return app.@org.primordion.xholon.app.Application::getApp()();
    });
    
    // root
    $wnd.xh.root = $entry(function() {
      return app.@org.primordion.xholon.app.Application::getRoot()();
    });
    
    // attrs
    $wnd.xh.attrs = $entry(function(node) {
      if ($wnd.console) {
        $wnd.console.log("Properties of " + node);
        for (var prop in node) {
          var pname = prop;
          var pval = node[pname];
          if (typeof pval != "function") {
            $wnd.console.log(pname + ": " + pval);
          }
        }
      }
    });
    
    // xpath
    $wnd.xh.xpath = $entry(function(expression, node) {
      return @org.client.XholonJsApi::xpath(Ljava/lang/String;Lorg/primordion/xholon/base/IXholon;)(expression, node);
    });
    
    // service
    $wnd.xh.service = $entry(function(serviceName) {
      return app.@org.primordion.xholon.app.Application::getService(Ljava/lang/String;)(serviceName);
    });
    
    // services
    $wnd.xh.services = $entry(function() {
      var serviceLocator = app.@org.primordion.xholon.app.Application::getService(Ljava/lang/String;)("ServiceLocatorService");
      serviceLocator.@org.primordion.xholon.base.IXholon::doAction(Ljava/lang/String;)("Show activation statuses");
    });
    
    // param
    $wnd.xh.param = $entry(function(pName, pValue) {
      if (pValue === undefined) {
        return app.@org.primordion.xholon.app.Application::getParam(Ljava/lang/String;)(pName)
      }
      else {
        app.@org.primordion.xholon.app.Application::setParam(Ljava/lang/String;Ljava/lang/String;)(pName, pValue);
      }
    });
    
    // state
    $wnd.xh.state = $entry(function(controllerState) {
      app.@org.primordion.xholon.app.Application::setControllerState(I)(controllerState);
    });
    
    // require
    $wnd.xh.require = $entry(function(scriptName, scriptPath) {
      @org.primordion.xholon.io.gwt.HtmlScriptHelper::requireScript(Ljava/lang/String;Ljava/lang/String;)(scriptName, scriptPath);
    });
    
    // test
    $wnd.xh.test = $entry(function() {
      //@org.client.XholonJsApi::test()();
      if ($wnd.QUnit) {
        $wnd.QUnit.config.autostart = false;
        $wnd.xh.require("XholonJsApiUnitTests.js");
      }
    });
    
    // xport
    // "export" is a JS keyword, so use "xport" instead
    // $wnd.xh.xport("Graphviz", xh.root(), '{"layout":"neato"}');
    $wnd.xh.xport = $entry(function(formatName, node, efParams, writeToTab) {
      var efs = $wnd.xh.service('ExternalFormatService');
      if (efs) {
        // IXholonService.SIG_PROCESS_REQUEST = -3998
        var data = [formatName];
        if (efParams !== undefined) {
          data.push(efParams);
        } else {data.push(null);}
        if (writeToTab !== undefined) {
          data.push(writeToTab ? "true" : "false");
        } else {data.push("true");};
        efs.call(-3998, data, node);
      }
    });
    
    // xports
    $wnd.xh.xports = $entry(function() {
      var efs = $wnd.xh.service('ExternalFormatService');
      if (efs) {
        return efs.actions();
      }
      return "";
    });
    
    // html.toggle
    $wnd.xh.html.toggle = $entry(function(elementId) {
      @org.client.HtmlElementCache::toggleElementDisplay(Ljava/lang/String;)(elementId);
    });
    
    // html.xhElements
    $wnd.xh.html.xhElements = $entry(function() {
      return @org.client.HtmlElementCache::getTopLevelElementNames()();
    });
    
    // html.popup
    // "Title", "<div><p>Hello Popup</p></div>"
    $wnd.xh.html.popup = $entry(function(title, htmlText, autoHide, modal, left, top) {
      if (autoHide === undefined) {autoHide = true;}
      if (modal === undefined) {modal = false;}
      if (left === undefined) {left = 0;}
      if (top === undefined) {top = 0;}
      return @org.client.XholonJsApi::popup(Ljava/lang/String;Ljava/lang/String;ZZII)(title, htmlText, autoHide, modal, left, top);
    });
    
    // css.style
    // $wnd.xh.css.style(".d3cpnode circle {stroke-width: 0px;}");
    $wnd.xh.css.style = $entry(function(content) {
      var style = $doc.createElement("style");
      style.appendChild($doc.createTextNode(content));
      $doc.head.appendChild(style);
    });
    
  }-*/;
  
  /**
   * For now, I'm storing the various API function objects in the single Application object.
   * This method is called by client.org.Xholon as XholonJsApi.exportIXholonApi((IXholon)app);
   *   where app is a IApplication
   // test
   root.first();
   var node1 = root.first();
   var node2 = node1.first();
   root.first().first();
   * The full comments are in XholonJsApi.js, in YUIDoc format.
   * @param node
   */
  public static native void exportIXholonApi(IXholon node) /*-{
    
    $wnd.xh.ixholonapi = new Object();
    var api = $wnd.xh.ixholonapi;
    
    // parent
    api.parent = $entry(function() {
      return this.@org.primordion.xholon.base.IXholon::getParentNode()();
    });
    
    // first
    api.first = $entry(function() {
      return this.@org.primordion.xholon.base.IXholon::getFirstChild()();
    });
    
    // last
    api.last = $entry(function() {
      return this.@org.primordion.xholon.base.IXholon::getLastChild()();
    });
    
    // next
    api.next = $entry(function() {
      return this.@org.primordion.xholon.base.IXholon::getNextSibling()();
    });
    
    // prev
    api.prev = $entry(function() {
      return this.@org.primordion.xholon.base.IXholon::getPreviousSibling()();
    });
    
    // remove
    api.remove = $entry(function() {
      this.@org.primordion.xholon.base.IXholon::removeChild()();
      return this;
    });
    
    // append
    api.append = $entry(function(content) {
      if (content) {
        if (typeof content == "string") {
          $wnd.xh.service('XholonHelperService').call(-2013, content, this);
        }
        else {
          content.@org.primordion.xholon.base.IXholon::appendChild(Lorg/primordion/xholon/base/IXholon;)(this);
        }
      }
      return this;
    });
    
    // appendTo
    api.appendTo = $entry(function(target) {
      this.@org.primordion.xholon.base.IXholon::appendChild(Lorg/primordion/xholon/base/IXholon;)(target);
      return this;
    });
    
    // prepend
    api.prepend = $entry(function(content) {
      if (content) {
        if (typeof content == "string") {
          $wnd.xh.service('XholonHelperService').call(-2014, content, this);
        }
        else {
          content.@org.primordion.xholon.base.IXholon::insertFirstChild(Lorg/primordion/xholon/base/IXholon;)(this);
        }
      }
      return this;
    });
    
    // prependTo
    api.prependTo = $entry(function(target) {
      this.@org.primordion.xholon.base.IXholon::insertFirstChild(Lorg/primordion/xholon/base/IXholon;)(target);
      return this;
    });
    
    // before
    api.before = $entry(function(content) {
      if (content) {
        if (typeof content == "string") {
          $wnd.xh.service('XholonHelperService').call(-2016, content, this);
        }
        else {
          content.@org.primordion.xholon.base.IXholon::insertBefore(Lorg/primordion/xholon/base/IXholon;)(this);
        }
      }
      return this;
    });
    
    // insertBefore
    api.insertBefore = $entry(function(target) {
      this.@org.primordion.xholon.base.IXholon::insertBefore(Lorg/primordion/xholon/base/IXholon;)(target);
      return this;
    });
    
    // after
    api.after = $entry(function(content) {
      if (content) {
        if (typeof content == "string") {
          $wnd.xh.service('XholonHelperService').call(-2015, content, this);
        }
        else {
          content.@org.primordion.xholon.base.IXholon::insertAfter(Lorg/primordion/xholon/base/IXholon;)(this);
        }
      }
      return this;
    });
    
    // insertAfter
    api.insertAfter = $entry(function(target) {
      this.@org.primordion.xholon.base.IXholon::insertAfter(Lorg/primordion/xholon/base/IXholon;)(target);
      return this;
    });
    
    // id
    api.id = $entry(function() {
      return this.@org.primordion.xholon.base.IXholon::getId()();
    });
    
    // name
    api.name = $entry(function(template) {
      if (!template) {
        return this.@org.primordion.xholon.base.IXholon::getName()();
      }
      else {
        return this.@org.primordion.xholon.base.IXholon::getName(Ljava/lang/String;)(template);
      }
    });
    
    // role
    api.role = $entry(function(roleName) {
      if (roleName === undefined) {
        return this.@org.primordion.xholon.base.IXholon::getRoleName()();
      }
      else {
        this.@org.primordion.xholon.base.IXholon::setRoleName(Ljava/lang/String;)(roleName);
        return this;
      }
    });
    
    // print
    api.print = $entry(function(obj) {
      this.@org.primordion.xholon.base.IXholon::print(Ljava/lang/Object;)(obj);
      return this;
    });
    
    // println
    api.println = $entry(function(obj) {
      this.@org.primordion.xholon.base.IXholon::println(Ljava/lang/Object;)(obj);
      return this;
    });
    
    // xhc
    api.xhc = $entry(function(xhClassName) {
      if (xhClassName === undefined) {
        return this.@org.primordion.xholon.base.IXholon::getXhc()();
      }
      else {
        var xhClass = this.@org.primordion.xholon.base.IXholon::getClassNode(Ljava/lang/String;)(xhClassName);
        if (xhClass) {
          this.@org.primordion.xholon.base.IXholon::setXhc(Lorg/primordion/xholon/base/IXholonClass;)(xhClass);
        }
        return this;
      }
    });
    
    // anno
    api.anno = $entry(function(annotation) {
      if (annotation === undefined) {
        return this.@org.primordion.xholon.base.IXholon::getAnnotation()();
      }
      else {
        this.@org.primordion.xholon.base.IXholon::setAnnotation(Ljava/lang/String;)(annotation);
        return this;
      }
    });
    
    // val
    api.val = $entry(function(val) {
      if (val === undefined) {
        return this.@org.primordion.xholon.base.IXholon::getVal()();
      }
      else {
        this.@org.primordion.xholon.base.IXholon::setVal(D)(val);
        return this;
      }
    });
    
    // inc
    api.inc = $entry(function(incAmount) {
      this.@org.primordion.xholon.base.IXholon::incVal(D)(incAmount);
      return this;
    });
    
    // dec
    api.dec = $entry(function(decAmount) {
      this.@org.primordion.xholon.base.IXholon::decVal(D)(decAmount);
      return this;
    });
    
    // text
    api.text = $entry(function(val) {
      if (val === undefined) {
        return this.@org.primordion.xholon.base.IXholon::getVal_String()();
      }
      else {
        this.@org.primordion.xholon.base.IXholon::setVal_String(Ljava/lang/String;)(val);
        return this;
      }
    });
    
    // bool
    api.bool = $entry(function(val) {
      if (val === undefined) {
        return this.@org.primordion.xholon.base.IXholon::getVal_boolean()();
      }
      else {
        this.@org.primordion.xholon.base.IXholon::setVal_boolean(Z)(val);
        return this;
      }
    });
    
    // obj
    api.obj = $entry(function(val) {
      if (val === undefined) {
        return this.@org.primordion.xholon.base.IXholon::getVal_Object()();
      }
      else {
        this.@org.primordion.xholon.base.IXholon::setVal_Object(Ljava/lang/Object;)(val);
        return this;
      }
    });
    
    // msg
    api.msg = $entry(function(signal, data, sender, index) {
      if (index === undefined) {
        this.@org.primordion.xholon.base.IXholon::sendMessage(ILjava/lang/Object;Lorg/primordion/xholon/base/IXholon;)(signal, data, sender);
        return this;
      }
      else {
        this.@org.primordion.xholon.base.IXholon::sendMessage(ILjava/lang/Object;Lorg/primordion/xholon/base/IXholon;I)(signal, data, sender, index);
        return this;
      }
    });
    
    // call
    api.call = $entry(function(signal, data, sender, index) {
      var responseMsg = null;
      if (index === undefined) {
        responseMsg = this.@org.primordion.xholon.base.IXholon::sendSyncMessage(ILjava/lang/Object;Lorg/primordion/xholon/base/IXholon;)(signal, data, sender);
      }
      else {
        responseMsg = this.@org.primordion.xholon.base.IXholon::sendSyncMessage(ILjava/lang/Object;Lorg/primordion/xholon/base/IXholon;I)(signal, data, sender, index);
      }
      if (responseMsg) {
        return responseMsg.obj();
      }
      else {
       return null;
      }
    });
    
    // actions
    // example of an actionList:
    //var actionList = {};
    //actionList["test1"] = function() {alert("TESTING 123 root");};
    //actionList["test2"] = function() {alert("TESTING 456 root");};
    api.actions = $entry(function(actionList) {
      if (actionList !== undefined) {
        this.actionList = actionList;
        return this;
      }
      return this.@org.primordion.xholon.base.IXholon::getActionList()();
    });
    
    // action
    api.action = $entry(function(action) {
      this.@org.primordion.xholon.base.IXholon::doAction(Ljava/lang/String;)(action);
      return this;
    });
    
    // port
    api.port = $entry(function(portName, portRef) {
      if (portRef === undefined) {
        if (isNaN(portName)) {
          // portName is a String
          var node = this;
          var clazz = node.@org.primordion.xholon.base.IXholon::getClass()();
          var app = $wnd.xh.app();
          return app.@org.primordion.xholon.app.Application::getAppSpecificObjectVal(Lorg/primordion/xholon/base/IXholon;Ljava/lang/Class;Ljava/lang/String;)(node, clazz, portName);
        }
        else {
          // portName is a number
          return this.@org.primordion.xholon.base.IXholon::getPort(I)(portName);
        }
      }
      else {
        if (isNaN(portName)) {
          // portName is a String
          // public boolean setAppSpecificObjectVal(IXholon tNode, String attrName, IXholon val)
          var node = this;
          var clazz = node.@org.primordion.xholon.base.IXholon::getClass()();
          var app = $wnd.xh.app();
          app.@org.primordion.xholon.app.Application::setAppSpecificObjectVal(Lorg/primordion/xholon/base/IXholon;Ljava/lang/Class;Ljava/lang/String;Lorg/primordion/xholon/base/IXholon;)(node, clazz, portName, portRef);
          return this;
        }
        else {
          // portName is a number
          this.@org.primordion.xholon.base.IXholon::setPort(ILorg/primordion/xholon/base/IXholon;)(portName, portRef);
          return this;
        }
      }
    });
    
    // ports (from AppGenerator and app)
    api.ports = $entry(function() {
      return @org.client.XholonJsApi::ports(Lorg/primordion/xholon/base/IXholon;)(this);
    });
    
    // portSpec (from Reflection and xhc)
    api.portSpec = $entry(function() {
      return @org.client.XholonJsApi::portSpec(Lorg/primordion/xholon/base/IXholon;)(this);
    });
    
    // portNames
    api.portNames = $entry(function() {
      var node = this;
      var clazz = node.@org.primordion.xholon.base.IXholon::getClass()();
      var app = $wnd.xh.app();
      return app.@org.primordion.xholon.app.Application::getAppSpecificObjectValNames(Lorg/primordion/xholon/base/IXholon;Ljava/lang/Class;)(node, clazz);
    });
    
    // attr
    api.attr = $entry(function(attrName, attrVal) {
      var node = this;
      var clazz = node.@org.primordion.xholon.base.IXholon::getClass()();
      var app = $wnd.xh.app();
      if (attrVal === undefined) {
        return app.@org.primordion.xholon.app.Application::getAppSpecificAttribute(Lorg/primordion/xholon/base/IXholon;Ljava/lang/Class;Ljava/lang/String;)(node, clazz, attrName);
      }
      else {
        app.@org.primordion.xholon.app.Application::setAppSpecificAttribute(Lorg/primordion/xholon/base/IXholon;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/Object;)(node, clazz, attrName, attrVal.toString());
        return this;
      }
    });
    
    // isAttr
    api.isAttr = $entry(function(attrName) {
      var node = this;
      var clazz = node.@org.primordion.xholon.base.IXholon::getClass()();
      var app = $wnd.xh.app();
      return app.@org.primordion.xholon.app.Application::isAppSpecificAttribute(Lorg/primordion/xholon/base/IXholon;Ljava/lang/Class;Ljava/lang/String;)(node, clazz, attrName);
    });
    
    // attrs
    api.attrs = $entry(function(returnAll) {
      var node = this;
      var clazz = node.@org.primordion.xholon.base.IXholon::getClass()();
      var app = $wnd.xh.app();
      return app.@org.primordion.xholon.app.Application::getAppSpecificAttributes(Lorg/primordion/xholon/base/IXholon;Ljava/lang/Class;Z)(node, clazz, returnAll);
    });
    
    // xpath
    api.xpath = $entry(function(expression) {
      return @org.client.XholonJsApi::xpath(Ljava/lang/String;Lorg/primordion/xholon/base/IXholon;)(expression, this);
    });
    
    // hasClass
    api.hasClass = $entry(function(className) {
      var xhc = this.@org.primordion.xholon.base.IXholon::getXhc()();
      return xhc.@org.primordion.xholon.base.IXholonClass::hasAncestor(Ljava/lang/String;)(className);
    });
    
    // hashify
    api.hashify = $entry(function(type) {
      if (type === undefined) {
        return this.@org.primordion.xholon.base.IXholon::hashify(Ljava/lang/String;)(null);
      }
      else {
        return this.@org.primordion.xholon.base.IXholon::hashify(Ljava/lang/String;)(type);
      }
    });
    
    // xhconsole
    api.xhconsole = $entry(function() {
      var msg = $wnd.xh.service('XholonHelperService').call(-2011, this, null);
      if (msg) {
        return msg.data[1];
      }
      else {
        return null;
      }
    });
    
    // IDecoration methods
    
    // color
    // var color = node.color();
    // var color = node.xhc().color();
    // node.color("0x00ff00"); node.color("#ff0000");
    // node.xhc().color("rgba(0,255,0,0.5)");
    api.color = $entry(function(val) {
      if (val === undefined) {
        return this.@org.primordion.xholon.base.IDecoration::getColor()();
      }
      else {
        this.@org.primordion.xholon.base.IDecoration::setColor(Ljava/lang/String;)(val);
        return this;
      }
    });
    
    // opacity
    api.opacity = $entry(function(val) {
      if (val === undefined) {
        return this.@org.primordion.xholon.base.IDecoration::getOpacity()();
      }
      else {
        this.@org.primordion.xholon.base.IDecoration::setOpacity(Ljava/lang/String;)(val);
        return this;
      }
    });
    
    // font
    api.font = $entry(function(val) {
      if (val === undefined) {
        return this.@org.primordion.xholon.base.IDecoration::getFont()();
      }
      else {
        this.@org.primordion.xholon.base.IDecoration::setFont(Ljava/lang/String;)(val);
        return this;
      }
    });
    
    // icon
    // me.icon("https://github.com/favicon.ico");
    api.icon = $entry(function(val) {
      if (val === undefined) {
        return this.@org.primordion.xholon.base.IDecoration::getIcon()();
      }
      else {
        this.@org.primordion.xholon.base.IDecoration::setIcon(Ljava/lang/String;)(val);
        return this;
      }
    });
    
    // toolTip
    api.toolTip = $entry(function(val) {
      if (val === undefined) {
        return this.@org.primordion.xholon.base.IDecoration::getToolTip()();
      }
      else {
        this.@org.primordion.xholon.base.IDecoration::setToolTip(Ljava/lang/String;)(val);
        return this;
      }
    });
    
    // symbol
    api.symbol = $entry(function(val) {
      if (val === undefined) {
        return this.@org.primordion.xholon.base.IDecoration::getSymbol()();
      }
      else {
        this.@org.primordion.xholon.base.IDecoration::setSymbol(Ljava/lang/String;)(val);
        return this;
      }
    });
    
    // format
    api.format = $entry(function(val) {
      if (val === undefined) {
        return this.@org.primordion.xholon.base.IDecoration::getFormat()();
      }
      else {
        this.@org.primordion.xholon.base.IDecoration::setFormat(Ljava/lang/String;)(val);
        return this;
      }
    });

    // geo
    api.geo = $entry(function(val) {
      if (val === undefined) {
        return this.@org.primordion.xholon.base.IDecoration::getGeo()();
      }
      else {
        this.@org.primordion.xholon.base.IDecoration::setGeo(Ljava/lang/String;)(val);
        return this;
      }
    });

    
    // TODO pcs(expression) and select(expression)
    
  }-*/;
  
  /**
   * Initialize the IXholon API.
   * Copy all the API functions from Application to the org.primordion.xholon.base.Xholon prototype.
   */
  public static native void initIXholonApi() /*-{
    var root = $wnd.xh.root();
    var api = $wnd.xh.ixholonapi;
    
    // find the prototype object for org.primordion.xholon.base.Xholon
    var arr = [];
    try {
      var p = Object.getPrototypeOf(root);
      while(true) {
        arr.push(p);
        p = Object.getPrototypeOf(p);
      }
    } catch(e) {
      // TypeError, end of the prototype chain has been reached
    }
    var len = arr.length;
    // typical values in the final array
    // [org.primordion.cellontro.app.XhCell,
    //  org.primordion.xholon.base.XholonWithPorts,
    //  org.primordion.xholon.base.Xholon,
    //  Object, Object, null]
    if (len < 4) {return;}
    var xhp = arr[len - 4];
    
    // copy all the API functions from api to xhp
    for (fname in api) {
      xhp[fname] = api[fname];
    }
    
  }-*/;
  
}
