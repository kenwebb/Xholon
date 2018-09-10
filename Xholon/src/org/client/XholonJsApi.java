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
import org.primordion.xholon.io.XholonGwtTabPanelHelper;
import org.primordion.xholon.util.ClassHelper;

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
   * Get an XPath 1.0 compliant expression that uniquely identifies a path
   * from an ancestor node to a descendant node.
   *
   * Example:
console.log(xh.xpathExpr(xh.root().first(), xh.root()));
  "HelloWorldSystem/Hello"
   *
   * Example:
var ancestor = $wnd.xh.root().first().first(); // City
var descendant = ancestor.xpath("School[@roleName='Hilson']");
$wnd.console.log($wnd.xh.xpathExpr(descendant, ancestor));
  "City/School[@roleName='Hilson']"
   *
   * @param descendant A Xholon node that is a descendant of ancestor.
   * @param ancestor A Xholon node that is an ancestor of descendant.
   */
  public static String xpathExpr(IXholon descendant, IXholon ancestor) {
    if (descendant == null) {return null;}
    if (ancestor == null) {return null;}
    return ((Xholon)ancestor).getXPath().getExpression(descendant, ancestor, false);
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
  
  public static boolean isXholonNode(Object obj) {
    Class clazz = obj.getClass();
    return ClassHelper.isAssignableFrom(Xholon.class, clazz);
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
    
    // xpathExpr
    $wnd.xh.xpathExpr = $entry(function(descendant, ancestor) {
      return @org.client.XholonJsApi::xpathExpr(Lorg/primordion/xholon/base/IXholon;Lorg/primordion/xholon/base/IXholon;)(descendant, ancestor);
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
        var response = app.@org.primordion.xholon.app.Application::getParam(Ljava/lang/String;)(pName)
        if (response == null) {
          return app[pName] ? app[pName] : null;
        }
        else {
          return response;
        }
      }
      else {
        $wnd.console.log(pName + " " + pValue);
        var response = app.@org.primordion.xholon.app.Application::setParam(Ljava/lang/String;Ljava/lang/String;)(pName, pValue);
        $wnd.console.log(response);
        if (response == false) {
          app[pName] = pValue;
        }
        return response;
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
    $wnd.xh.xport = $entry(function(formatName, node, efParams, writeToTab, returnString) {
      var efs = $wnd.xh.service('ExternalFormatService');
      if (efs) {
        // IXholonService.SIG_PROCESS_REQUEST = -3998
        var data = [formatName];
        if (efParams !== undefined) {
          data.push(efParams);
        } else {
          data.push(null);
        }
        if (writeToTab !== undefined) {
          data.push(writeToTab ? "true" : "false");
        } else {
          data.push("true");
        };
        if (returnString !== undefined) {
          data.push(returnString ? "true" : "false");
        } else {
          data.push("false");
        };
        var msg = efs.call(-3998, data, node);
        if (returnString !== undefined) {
          return msg.data;
        }
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
    
    // avatar
    $wnd.xh.avatar = $entry(function(newAvatar) {
      if (newAvatar === undefined) {
        return app.@org.primordion.xholon.app.Application::getAvatar()();
      }
      else {
        app.@org.primordion.xholon.app.Application::setAvatar(Lorg/primordion/xholon/base/IXholon;)(newAvatar);
      }
    });
    
    // avatarKeyMap
    // should use param to get/set this
    // Usage example:
    //   var json = xh.avatarKeyMap();
    //   var obj = JSON.parse(json);
    //   obj.X = "build Cat role Licorice";
    //   obj.Y = "build Dog role Fido";
    //   obj["1"] = "take *cat";
    //   var newJson = JSON.stringify(obj);
    //   xh.avatarKeyMap(newJson);
    $wnd.xh.avatarKeyMap = $entry(function(keyMap) {
      if (keyMap === undefined) {
        return app.@org.primordion.xholon.app.Application::getAvatarKeyMap()();
      }
      else {
        app.@org.primordion.xholon.app.Application::setAvatarKeyMap(Ljava/lang/String;)(keyMap);
      }
    });
    
    // speech recognition
    // @param lang (ex: "en-GB")
    $wnd.xh.speechRecognition = $entry(function(lang) {
      if ('webkitSpeechRecognition' in $wnd) {
        var r = new $wnd.webkitSpeechRecognition();
        r.continuous = true;
        if (lang !== undefined) {
          r.lang = lang;
        }
        r.onresult = function(event) {
          var result = event.results[event.results.length-1][0].transcript;
          var nss = $wnd.xh.service("NodeSelectionService");
          var data = nss.call(-3894, null, $wnd.xh.root()).data;
          if (data.length > 0) {
            var node = data[0];
            if (node.xhc()
                && node.xhc().parent()
                && ((node.xhc().name() == "Avatar") || (node.xhc().parent().name() == "Avatar"))) {
              node.action(result);
            }
            else {
              app.@org.primordion.xholon.app.Application::getAvatar()().action(result);
            }
          }
          else {
            app.@org.primordion.xholon.app.Application::getAvatar()().action(result);
          }
        }
        r.onend = function(event) {
          r.start();
        }
        r.start();
      }
      else {
        // $wnd.xh.root().println("speech recognition not supported");
      }
    });
    
    // Enable external access through a new Avatar, using WebRTC PeerJS.
    // This would typically be used from PeerjsChat.html
    // xh.webRTC();
    // xh.webRTC("Alligator", undefined, 3);
    $wnd.xh.webRTC = $entry(function(id, key, debug) {
      if (typeof $wnd.Peer === "undefined") {return;}
      var root = $wnd.xh.root();
      if (key === undefined) {
        var key = "lwjd5qra8257b9"; // peerjs demo API key
      }
      if (debug === undefined) {
        var debug = 0; // debug level (0 - 3)
      }
      if (id === undefined) {
        var appName = "" + $wnd.xh.html.urlparam("app").replace(/\+/g," ");
        var id = appName + "_" + new Date().getTime() + "_" + root.id();
      }
      root.println(id);
      var peer = new $wnd.Peer(id, {key: key, debug: debug});
      peer.on('connection', function(connection) {
        if (connection.label == "chat") { // Peerjs Chat also tries to set up a "file" connection
          var avatar = root.append("<Avatar/>").last();
          avatar.action("vanish");
          connection.on('open', function() {
            connection.send('Welcome to ' + $wnd.xh.param("ModelName"));
          });
          connection.on('data', function(data) {
            //root.println(data);
            var msg = avatar.call(-9, data, root);
            connection.send(msg.data.substring(1));
          });
        }
      });
    });
    
    // show local storage
    // max size is 5MB
    // see: http://blog.blakesimpson.co.uk/read/46-check-size-of-localstorage-usage-in-chrome
    $wnd.xh.showLocalStorage = $entry(function() {
      var total = 0;
      for(var x in localStorage) {
        var amount = (localStorage[x].length * 2) / 1024 / 1024;
        total += amount;
        $wnd.console.log( x + " = " + amount.toFixed(4) + " MB");
      }
      $wnd.console.log( "Total: " + total.toFixed(4) + " MB");
    });
    
    // isXholonNode
    // Determine if obj is an instance of a Java class that descends from Xholon.java.
    // xh.isXholonNode(xh.root());  //returns true
    // xh.isXholonNode(13);         //returns false
    $wnd.xh.isXholonNode = $entry(function(obj) {
      return @org.client.XholonJsApi::isXholonNode(Ljava/lang/Object;)(obj);
    });
    
    // random - replacement for Javascript Math.random(), with a seed
    $wnd.xh.random = $entry(function() {
      return @org.primordion.xholon.util.MiscRandom::random()();
    });
    
    // seed - seed the random number generator
    $wnd.xh.seed = $entry(function(seed) {
      @org.primordion.xholon.util.MiscRandom::seedRandomNumberGeneratorInt(I)(seed);
    });
    
    // matchGraph
    $wnd.xh.matchGraph = $entry(function(graphStr, subgraphStr, separator) {
      if (separator === undefined) {separator = "\n";}
      return @org.wip.VF2.runner.App::matchGraph(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(graphStr, subgraphStr, separator);
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
    
    // get a URL search param
    $wnd.xh.html.urlparam = $entry(function(searchParam) {
      var params = $wnd.location.search.substr(1).split('&');
      for (var i = 0; i < params.length; i++) {
        var p = params[i].split('=');
        if (p[0] == searchParam) {
          return decodeURIComponent(p[1]);
        }
      }
      return null;
    });
    
    // Select a tab in #xhtabs .
    // xh.html.selectTab(0); // "out"
    // xh.html.selectTab(1); // "clipboard"
    // xh.html.selectTab(2); // "notes"
    $wnd.xh.html.selectTab = $entry(function(index) {
      try {
        @org.primordion.xholon.io.XholonGwtTabPanelHelper::selectTab(I)(index);
      } catch(e) {
        $wnd.console.log("Unable to select tab " + index);
      }
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
    
    // cloneLastChild
    api.cloneLastChild = $entry(function() {
      $wnd.xh.service('XholonHelperService').call(-2024, this, this);
      return this;
    });
    
    // cloneFirstChild
    api.cloneFirstChild = $entry(function() {
      $wnd.xh.service('XholonHelperService').call(-2025, this, this);
      return this;
    });
    
    // cloneAfter
    api.cloneAfter = $entry(function() {
      $wnd.xh.service('XholonHelperService').call(-2026, this, this);
      return this;
    });
    
    // cloneBefore
    api.cloneBefore = $entry(function() {
      $wnd.xh.service('XholonHelperService').call(-2027, this, this);
      return this;
    });
    
    // id
    api.id = $entry(function() {
      return this.@org.primordion.xholon.base.IXholon::getId()();
    });
    
    // _id  (in general, this is for system use only)
    api._id = $entry(function(iid) {
      if (iid === undefined) {
        return this.@org.primordion.xholon.base.IXholon::getId()();
      }
      else {
        this.@org.primordion.xholon.base.IXholon::setId(I)(iid);
        return this;
      }
    });
    
    // identity
    api.identity = $entry(function() {
      return this.@org.primordion.xholon.base.IXholon::getIdentity()();
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
    
    // xhType
    api.xhType = $entry(function() {
      return this.@org.primordion.xholon.base.IXholon::getXhType()();
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
    
    // links
    // Use JavaScript to return an array of all links/references from this node to other Xholon nodes.
    // @param placeGraph Whether or not to include place-graph links (parent, first, next, xhc, app)
    // @param linkGraph Whether or not to include link-graph links (ports, etc.)
    // @return An array of objects. This is the same format as calling: node.ports()[0].obj();
    api.links = $entry(function(placeGraph, linkGraph) {
      var node = this;
      if (placeGraph === undefined) {var placeGraph = true;}
      if (linkGraph === undefined) {var linkGraph = true;}
      var outArr = [];
      var fillLinkObj = function(fn, fni, rn, xe) {
        var obj = {};
        obj.fieldName = fn;
        obj.fieldNameIndex = fni;
        obj.fieldNameIndexStr = "" + fni;
        obj.reffedNode = rn;
        if (!xe) {
          var xe = null;
          if (rn == $wnd.xh.root()) {
            xe = "./";
          }
          else {
            xe = $wnd.xh.xpathExpr(rn, $wnd.xh.root());
            if (!xe) {
              // TODO the generated xpathExpr is wrong when $wnd.xh.app() is used
              xe = $wnd.xh.xpathExpr(rn, $wnd.xh.app());
            }
            xe = "ancestor::" + xe;
          }
        }
        obj.xpathExpression = xe;
        return obj;
      };
      //outArr.push(fillLinkObj("this", -1, node, "./"));
      if (placeGraph) {
        if (node.parent()) {outArr.push(fillLinkObj("parent", -1, node.parent(), null));}
        if (node.first()) {outArr.push(fillLinkObj("first", -1, node.first(), null));}
        if (node.next()) {outArr.push(fillLinkObj("next", -1, node.next(), null));}
        if (node.xhc()) {outArr.push(fillLinkObj("xhc", -1, node.xhc(), null));}
      }
      if (linkGraph) {
        var parentFound = false; // be able to differentiate standard "parent" from a separate link to the parent
        var firstChildFound = false;
        var nextSiblingFound = false; // be able to differentiate standard "nextSibling" from a separate link to the next sibling
        for (var prop in node) {
          var pname = prop;
          if (pname == null) {continue;}
          if (pname == "constructor") {continue;}
          var pval = node[pname];
          if (pval == null) {continue;}
          if (typeof pval == "object") {
            if (Array.isArray(pval)) {
              // this may be an array of ports
              if (!linkGraph) {continue;}
              //var ix = 0; // Dec 4, 2017
              for (var i = 0; i < pval.length; i++) {
                if (pval[i] && $wnd.xh.isXholonNode(pval[i])) {
                  if (node.@org.primordion.xholon.base.IXholon::getPort()() == pval) {
                    // this is the Java built-in "port" array
                    pname = "port";
                    // Dec 4, 2017
                    //if (pval[i].xhc() && (pval[i].xhc().name() == "Port")) {
                    //  // this is a replicated IPort
                    //  $wnd.console.log("IPort: " + pval[i]);
                    //  //$wnd.console.log(pval[i].port(0).next().parent().parent());
                    //  var replIx = 0;
                    //  while (pval[i].port(replIx)) {
                    //    var rnode = pval[i].port(replIx).next();
                    //    if (rnode) {
                    //      rnode = rnode.parent();
                    //      if (rnode) {
                    //        rnode = rnode.parent();
                    //      }
                    //    }
                    //    $wnd.console.log(rnode);
                    //    if (rnode) {
                    //      outArr.push(fillLinkObj(pname, ix++, rnode, null));
                    //    }
                    //    replIx++;
                    //  }
                    //}
                    //else {
                    //  outArr.push(fillLinkObj(pname, ix++, pval[i], null));
                    //}
                  }
                  //else {
                  //  outArr.push(fillLinkObj(pname, ix++, pval[i], null));
                  //}
                  outArr.push(fillLinkObj(pname, i, pval[i], null));
                }
              }
            }
            else {
              if (!$wnd.xh.isXholonNode(pval)) {continue;}
              else if (!parentFound && pval == node.parent()) {
                // I've found the standard link to the parent; I assume that the standard link is the first such link
                parentFound = true;
                continue;
              }
              else if (!firstChildFound && pval == node.first()) {
                // I've found the standard link to the first child; I assume that the standard link is the first such link
                firstChildFound = true;
                continue;
              }
              else if (!nextSiblingFound && pval == node.next()) {
                // I've found the standard link to the next sibling; I assume that the standard link is the first such link
                nextSiblingFound = true;
                continue;
              }
              else if (pval == node.xhc()) {continue;}
              else if (pval == $wnd.xh.app()) {if (placeGraph) {pname = "app";} else {continue;}}
              outArr.push(fillLinkObj(pname, -1, pval, null));
            }
          }
        }
      }
      return outArr;
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
    
    // attrz
    // usage: xh.root().attrz(false, true, false, true);
    // example of returned Object: {first_: "Bob", last_: "Bo"}
    api.attrz = $entry(function(javaAttrs, jsAttrs, returnAll, noDollar) {
      var obj = {};
      var node = this;
      if (javaAttrs === undefined) {var javaAttrs = true;}
      if (jsAttrs === undefined) {var jsAttrs = true;}
      if (returnAll === undefined) {var returnAll = false;}
      if (noDollar === undefined) {var noDollar = false;}
      if (javaAttrs) {
        var javaArr = node.attrs(returnAll); // can pass in true or false
        for (var jvi = 0; jvi < javaArr.length; jvi++) {
          var jvName = javaArr[jvi][0];
          var jvVal = javaArr[jvi][1];
          if ((jvVal != null) && (typeof jvVal != "object")) {
            obj[jvName] = jvVal;
          }
        }
      }
      if (jsAttrs) {
        var service = $wnd.xh.service("XholonCreationService");
        var msg = null;
        if (noDollar) {
          msg = service.call(-3895, node, node); // exclude attributes whose names contain "$"
        }
        else {
          msg = service.call(-3896, node, node);
        }
        var jsObj = msg.data;
        for (var jsi in jsObj) {
          obj[jsi] = jsObj[jsi];
        }
      }
      return obj;
    });
    
    // xpath
    api.xpath = $entry(function(expression) {
      return @org.client.XholonJsApi::xpath(Ljava/lang/String;Lorg/primordion/xholon/base/IXholon;)(expression, this);
    });
    
    // hasClass
    api.hasClass = $entry(function(className) {
      var xhc = this.@org.primordion.xholon.base.IXholon::getXhc()();
      if (!xhc) {return false;}
      return xhc.@org.primordion.xholon.base.IXholonClass::hasAncestor(Ljava/lang/String;)(className);
    });
    
    // classAttr
    // search XholonClass hierarchy (this.xhc() and this.xhc() ancestors) for a named attribute
    // getter and setter
    api.classAttr = $entry(function(attrName, attrVal) {
      var xhc = this.xhc();
      while (xhc) {
        if (xhc[attrName]) {
          if (attrVal === undefined) {
            return xhc[attrName];
          }
          else {
            xhc[attrName] = attrVal;
          }
        }
        xhc = xhc.parent();
      }
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
    
    // select
    api.select = $entry(function() {
      var app = $wnd.xh.app();
      return app.@org.primordion.xholon.app.Application::handleNodeSelection(Lorg/primordion/xholon/base/IXholon;Ljava/lang/String;)(this, this.name());
    });
    
    // subtreez  function name uses "z" instead of "s", so it doesn't conflict with the "subtrees" attribute
    api.subtreez = $entry(function(stNames) {
      if (stNames === undefined) {
        return this.@org.primordion.xholon.base.IXholon::subtrees(Ljava/lang/String;)(null);
      }
      else {
        return this.@org.primordion.xholon.base.IXholon::subtrees(Ljava/lang/String;)(stNames);
      }
    });
    
    // subtree
    api.subtree = $entry(function(stName) {
      if (stName === undefined) {
        return this.@org.primordion.xholon.base.IXholon::subtree(Ljava/lang/String;)(null);
      }
      else {
        return this.@org.primordion.xholon.base.IXholon::subtree(Ljava/lang/String;)(stName);
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

    // sound
    api.sound = $entry(function(val) {
      if (val === undefined) {
        return this.@org.primordion.xholon.base.IDecoration::getSound()();
      }
      else {
        this.@org.primordion.xholon.base.IDecoration::setSound(Ljava/lang/String;)(val);
        return this;
      }
    });

    // childrenAsCsv
    // var root = xh.root();
    // root.first().childrenAsCsv();
    // root.first().childrenAsCsv("R^^^^^","|");
    // root.first().childrenAsCsv("R^^^^^").split(",");
    // root.first().childrenAsCsv("R^^^^^").split(",").sort();
    api.childrenAsCsv = $entry(function(nameTemplate, separator) {
      if (nameTemplate === undefined) {nameTemplate = null;}
      if (separator === undefined) {separator = null;}
      return this.@org.primordion.xholon.base.IXholon::getChildrenAsCsv(Ljava/lang/String;Ljava/lang/String;)(nameTemplate, separator);
    });
    
    // siblingsAsCsv
    // xh.root().first().first().next().siblingsAsCsv("R^^^^^","|").split("|").sort();
    api.siblingsAsCsv = $entry(function(nameTemplate, separator) {
      if (nameTemplate === undefined) {nameTemplate = null;}
      if (separator === undefined) {separator = null;}
      return this.@org.primordion.xholon.base.IXholon::getSiblingsAsCsv(Ljava/lang/String;Ljava/lang/String;)(nameTemplate, separator);
    });
    
    // includes
    // var bool = node.includes("one,two", "R^^^^^", ",");
    // var bool = root.first().includes("one,three,two", "R^^^^^", ","); bool // true
    // var bool = root.first().includes("example,example,example", "^^c^^^", ","); bool // true
    api.includes = $entry(function(targetStr, nameTemplate, separator) {
      if (targetStr === undefined) {return false;}
      if (nameTemplate === undefined) {nameTemplate = null;}
      if (separator === undefined) {separator = null;}
      var sstr = this.childrenAsCsv(nameTemplate, separator);
      if (!sstr) {return false;}
      var sarr = sstr.split(separator).sort();
      var tarr = targetStr.split(separator).sort();
      // work thru each array in parallel
      var sindex = 0;
      var tindex = 0;
      var count = tarr.length; // count down each time thru the while loop
      while ((sindex < sarr.length) && (tindex < tarr.length)) {
        var sitem = sarr[sindex];
        var titem = tarr[tindex];
        if (sitem == titem) {
          // a match was found
          count--;
          tindex++;
        }
        sindex++;
      }
      if (count == 0) {
        return true;
      }
      else {
        return false;
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
