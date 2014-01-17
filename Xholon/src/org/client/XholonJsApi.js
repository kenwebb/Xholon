/**
 * <h3>Xholon JavaScript API</h3>
 * <p>This is the JavaScript application programming interface (API)
 * for Xholon (http://www.primordion.com/Xholon/gwt/) version 0.9.0.</p>
 * <p>It's a wrapper around the Xholon Google Web Toolkit (GWT) (http://www.gwtproject.org/) Java code.
 * Note that GWT compiles all of the Java code to optimized JavaScript.</p>
 * <p>The online JavaScript documentation is produced with the help of YuiDoc,
 * and the comment blocks in this file must be in YuiDoc-compatible format (http://yui.github.io/yuidoc/).</p>
 * <p>The API is Copyright (C) 2014 Ken Webb, and is released under the MIT License.</p>
 * 
 * @module XholonJsApi
 */

/**
 * These methods apply to the top level of the Xholon application.
 * All of the methods in this class MUST be preceeded by "$wnd.xh." (ex: "$wnd.xh.app();")
 * if they are run from within the Xholon application. The "$wnd." is a GWT requirement.
 * They MUST be preceeded by "xh." (ex: "xh.app();") if they are run using a developer's tool such as Firebug
 * or Developer Tools.
 *
 * @class xh
 * @constructor
 */
$wnd.xh = {};

$wnd.xh.html = new Object();

/**
 * Get the single app-specific IApplication instance.
 * @method app
 * @return {IApplication} An instance of a subclass of org.primordion.xholon.app.Application.
 * @example
 *     var app = $wnd.xh.app();
 */
$wnd.xh.app = $entry(function() {
  return app.@org.primordion.xholon.app.Application::getApp()();
});

/**
 * Get the app-specific root of the composite structure hierarchy.
 * @method root
 * @return {IXholon} An instance of a subclass of org.primordion.xholon.base.Xholon.
 * @example
 *     var root = $wnd.xh.root();
 */
$wnd.xh.root = $entry(function() {
  return app.@org.primordion.xholon.app.Application::getRoot()();
});

/**
 * Log all attribute name/value pairs (JavaScript properties) of a node to the browser console.
 * This is mostly intended for debugging.
 * @method attrs
 * @param {IXholon} node An IXholon node.
 * @example
 *     var node = $wnd.xh.root();
 *     $wnd.xh.attrs(node);
 */
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

/**
 * Evaluate an XPath 1.0 expression.
 * @method xpath
 * @param {String} expression An XPath 1.0 String expression (ex: "ancestor::HelloWorldSystem/World");
 * @param {IXholon} node An IXholon node.
 * @return {IXholon} An IXholon node, or null.
 * @example
 *     var node = $wnd.xh.root().first(); // get the Hello node
 *     $wnd.xh.xpath("ancestor::HelloWorldSystem/World", node);
 */
$wnd.xh.xpath = $entry(function(expression, node) {
  return @org.client.XholonJsApi::xpath(Ljava/lang/String;Lorg/primordion/xholon/base/IXholon;)(expression, node);
});

/**
 * Get a Xholon Service.
 * @method service
 * @param {String} serviceName The name of a service (ex: "TimelineService").
 * @return {IXholon} An IXholon node, or null.
 * @example
 *     var service = $wnd.xh.service("TimelineService");
 *     console.log(service);
 */
$wnd.xh.service = $entry(function(serviceName) {
  return app.@org.primordion.xholon.app.Application::getService(Ljava/lang/String;)(serviceName);
});

/**
 * Display a list of names of services and their activation status, in the out tab.
 * @method services
 * @example
 *     $wnd.xh.services();
 */
$wnd.xh.services = $entry(function() {
  var serviceLocator = app.@org.primordion.xholon.app.Application::getService(Ljava/lang/String;)("ServiceLocatorService");
  serviceLocator.@org.primordion.xholon.base.IXholon::doAction(Ljava/lang/String;)("Show activation statuses");
});

/**
 * Get or set the value of an application parameter.
 * @method param
 * @param {String} pName The name of the parameter.
 * @param {String} [pValue] The value of the parameter.
 * @return {String} The value of the parameter, or null, or undefined.
 * @example
 *     // set the interval between time steps as 200 milliseconds
 *     // show the before and after values in the browser console
 *     console.log($wnd.xh.param("TimeStepInterval"));
 *     $wnd.xh.param("TimeStepInterval", "200");
 *     console.log($wnd.xh.param("TimeStepInterval"));
 */
$wnd.xh.param = $entry(function(pName, pValue) {
  if (pValue === undefined) {
    return app.@org.primordion.xholon.app.Application::getParam(Ljava/lang/String;)(pName)
  }
  else {
    app.@org.primordion.xholon.app.Application::setParam(Ljava/lang/String;Ljava/lang/String;)(pName, pValue);
  }
});

/**
 * Set controller state.
 * The example below sets the controller state to CS_STEPPING,
 * which causes the app to move forward one timestep, and then go into CS_PAUSE state.
 * @method state
 * @param {Number} controllerState 5 is currently the only useful value.
 * @example
 *     $wnd.xh.state(5);
 */
$wnd.xh.state = $entry(function(controllerState) {
  app.@org.primordion.xholon.app.Application::setControllerState(I)(controllerState);
});

/**
 * Require a single named JavaScript library.
 * If the script is located in the Xholon application library,
 * then only the scriptName needs to be provided.
 * ex: $wnd.xh.require("d3.v2.min.js");
 * ex: $wnd.xh.require("three.min.js", "http://threejs.org/build/");
 * @method require
 * @param {String} scriptName The name of a script.
 * @param {String} [scriptPath] The URL for the script.
 * @example
 *     $wnd.xh.require("three.min.js", "http://threejs.org/build/");
 */
$wnd.xh.require = $entry(function(scriptName, scriptPath) {
  @org.primordion.xholon.io.gwt.HtmlScriptHelper::requireScript(Ljava/lang/String;Ljava/lang/String;)(scriptName, scriptPath)
});

/**
 * Toggle the visibility of an existing HTML element, typically a div that starts with "xh".
 * @method html.toggle
 * @param {String} elementId An HTML element id.
 * @example
 *     $wnd.xh.html.toggle("xhgui");
 */
$wnd.xh.html.toggle = $entry(function(elementId) {
  @org.client.HtmlElementCache::toggleElementDisplay(Ljava/lang/String;)(elementId);
});

/**
 * Get an array of top-level "xh" element id's.
 * @method html.elements
 * @return {String} a comma-delimited list
 *   (ex: "xhtop,xhgui,xhappspecific,xhconsole,xhtabs,xhchart,xhcanvas,xhgraph,xhsvg,xhimg").
 * @example
 *     $wnd.xh.html.xhelements();
 */
$wnd.xh.html.xhelements = $entry(function() {
  return @org.client.HtmlElementCache::getTopLevelElementNames()();
});


// TODO export

// TODO exports


/**
 * These methods apply to individual IXholon nodes in the Xholon application.
 * A Xholon is a node within a hierarchical tree structure, and builds on the capabilities of tree nodes.
 * A tree node is a node in a tree.
 * It has one parent (none if it's the root), and may have one or more children, and one or more siblings.
 * A Xholon is both a whole, and a part of some other Xholon, at the same time.
 * A Xholon is an instance of a IXholonClass.
 * A Xholon can be an active object, a passive object, a container, or any combination of these three,
 * as defined by its IXholonClass.
 *
 * @class ixholon
 * @constructor
 */
$wnd.xh.ixholonapi = new Object();

var api = $wnd.xh.ixholonapi;

/**
 * Get parent of this node.
 * @method parent
 * @return {IXholon} This node's current parent, or null if this is a root node.
 * @example
 *     var p = node.parent();
 */
api.parent = $entry(function() {
  return this.@org.primordion.xholon.base.IXholon::getParentNode()();
});

/**
 * Get first (leftmost) child of this node.
 * @method first
 * @return {IXholon} First child, or null.
 * @example
 *     var f = node.first();
 */
api.first = $entry(function() {
  return this.@org.primordion.xholon.base.IXholon::getFirstChild()();
});

/**
 * Get next (right) sibling of this node.
 * @method next
 * @return {IXholon} Next sibling, or null.
 * @example
 *     var n = node.next();
 */
api.next = $entry(function() {
  return this.@org.primordion.xholon.base.IXholon::getNextSibling()();
});

/**
 * Get previous (left) sibling of this node. (may be expensive in processing time)
 * @method prev
 * @return {IXholon} Previous sibling, or null.
 * @example
 *     var p = node.prev();
 */
api.prev = $entry(function() {
  return this.@org.primordion.xholon.base.IXholon::getPreviousSibling()();
});

/**
 * Detach this node from its parent and from any siblings.
 * Any siblings are reattached to each other.
 * If there was a previous sibling and a next sibling,
 * these will be attached to each other.
 * If there was only a previous sibling, it's next sibling will now be null.
 * If this node was the parent's firstChild,
 * the parent's firstChild link will be appropriately adjusted.
 * Any children are left intact. The entire subtree is detached.
 * @method remove
 * @chainable
 * @return {IXholon} this
 * @example
 *     node.remove().appendto(newParentNode);
 */
api.remove = $entry(function() {
  this.@org.primordion.xholon.base.IXholon::removeChild()();
  return this;
});

/**
 * Append this node as the last child of its new parent node.
 * TODO appendTo ???
 * @method appendto
 * @chainable
 * @param {IXholon} newParentNode New parent of this node.
 * @return {IXholon} this
 * @example
 *     node.remove().appendto(newParentNode);
 */
api.appendto = $entry(function(newParentNode) {
  this.@org.primordion.xholon.base.IXholon::appendChild(Lorg/primordion/xholon/base/IXholon;)(newParentNode);
  return this;
});

/**
 * Insert this node as the first child of its new parent node.
 * TODO prependTo ???
 * @method prependto
 * @chainable
 * @param {IXholon} newParentNode New parent of this node.
 * @return {IXholon} this
 * @example
 *     node.remove().prependto(newParentNode);
 */
api.prependto = $entry(function(newParentNode) {
  this.@org.primordion.xholon.base.IXholon::insertFirstChild(Lorg/primordion/xholon/base/IXholon;)(newParentNode);
  return this;
});

/**
 * Insert this node before its new next sibling node.
 * @method before
 * @chainable
 * @param {IXholon} newNextSibling New next sibling of this node.
 * @return {IXholon} this
 * @example
 *     node.before(otherNode);
 */
api.before = $entry(function(newNextSibling) {
  this.@org.primordion.xholon.base.IXholon::insertBefore(Lorg/primordion/xholon/base/IXholon;)(newNextSibling);
  return this;
});

/**
 * Insert this node after its new previous sibling node.
 * @method after
 * @chainable
 * @param {IXholon} newPreviousSibling New previous sibling of this node.
 * @return {IXholon} this
 * @example
 *     node.after(otherNode);
 */
api.after = $entry(function(newPreviousSibling) {
  this.@org.primordion.xholon.base.IXholon::insertAfter(Lorg/primordion/xholon/base/IXholon;)(newLeftSibling);
  return this;
});

/**
 * Get ID of this IXholon instance.
 * @method id
 * @return {Number} An integer ID, unique within this application, that is assigned when the IXholon is created.
 * @example
 *     var id = node.id();
 *     console.log("The id for node " + node.toString() + " is " + id);
 */
api.id = $entry(function() {
  return this.@org.primordion.xholon.base.IXholon::getId()();
});

/**
 * Get name, unique within this application, of this Xholon instance.
 * The name is a concatenation of the IXholonClass name and the Xholon unique ID.
 * The first letter is converted to lowercase, and a "_" is used to separate
 * the name and ID (ex: "helloWorld_123").
 * If the Xholon instance has a roleName,
 * it is included in front of the other two elements,
 * and separated from the IXholonClass name by a ":" (ex: "helloRole:helloWorld_123").
 * @method name
 * @param [template="r:c_i^"] {String} A fixed length template that specifies what elements are included
 * as part of the name.
 * ex: "r:c_i^" is roleName + ":" + className with first letter lowercase + "_" + id
 * The template fields are in fixed positions, and may have the following values:
 * (1) r include roleName, ^ don't include role name
 * (2) : ^
 * (3) c className lowercase, C classname lowercase, l local part, L local part
 * (4) _ ^
 * (5) i include id
 * (6) ^
 * If an invalid template is input, then the returned name will be in the default format,
 * as defined by the GETNAME_DEFAULT constant.
 * The default format is exactly the same as what would be returned by calling getName()
 * without specifying a template.
 * @return {String} The default or formatted name of this IXholon instance.
 * @example
 *     var name = node.name();
 *     console.log(name);
 *     name = node.name("R^^^^^");
 *     console.log(name);
 */
api.name = $entry(function(template) {
  if (!template) {
    return this.@org.primordion.xholon.base.IXholon::getName()();
  }
  else {
    return this.@org.primordion.xholon.base.IXholon::getName(Ljava/lang/String;)(template);
  }
});

/**
 * Get or set the name of the role played by this Xholon within a specific context.
 * @method role
 * @chainable
 * @param {String} [roleName] An optional string that identifies the role.
 * @return {String} A name that identifies the role, or null.
 * Or {IXholon} this
 * @example
 *     console.log(node.role());
 *     node.role("myNewRole");
 *     console.log(node.role());
 */
api.role = $entry(function(roleName) {
  if (roleName === undefined) {
    return this.@org.primordion.xholon.base.IXholon::getRoleName()();
  }
  else {
    this.@org.primordion.xholon.base.IXholon::setRoleName(Ljava/lang/String;)(roleName);
    return this;
  }
});

/**
 * Write an object to the out tab.
 * @method print
 * @chainable
 * @param {Object} obj The object to write. Currently, this must be a String.
 * @return {IXholon} this
 * @example
 *     node.print("Hello ");
 */
api.print = $entry(function(obj) {
  this.@org.primordion.xholon.base.IXholon::print(Ljava/lang/Object;)(obj);
  return this;
});

/**
 * Write an object to the out tab, terminated with an end-of-line.
 * @method println
 * @chainable
 * @param {Object} obj The object to write. Currently, this must be a String.
 * @return {IXholon} this
 * @example
 *     node.println("World");
 */
api.println = $entry(function(obj) {
  this.@org.primordion.xholon.base.IXholon::println(Ljava/lang/Object;)(obj);
  return this;
});

/**
 * Get the IXholonClass to which this Xholon instance is a member.
 * @method xhc
 * @return {IXholonClass} The IXholonClass, which is a subclass of IXholon.
 * @example
 *     var xhc = node.xhc();
 *     console.log(xhc.name());
 */
api.xhc = $entry(function() {
  return this.@org.primordion.xholon.base.IXholon::getXhc()();
});

/**
 * Get or set the contents of the annotation for this Xholon.
 * The annotation is intended to be read by a human user.
 * @method anno
 * @chainable
 * @param {String} [annotation] Some text.
 * @return {String} Some text, or null.
 * Or {IXholon} this
 * @example
 *     console.log(node.anno());
 *     node.anno("Here is my new description of the current node.");
 *     console.log(node.anno());
 */
api.anno = $entry(function(annotation) {
  if (annotation === undefined) {
    return this.@org.primordion.xholon.base.IXholon::getAnnotation()();
  }
  else {
    this.@org.primordion.xholon.base.IXholon::setAnnotation(Ljava/lang/String;)(annotation);
    return this;
  }
});

/**
 * Get or set a numeric value maintained by this xholon instance.
 * If a class that implements this interface does not maintain a numeric value, it should return 0.0 
 * @method val
 * @chainable
 * @param {Number} [val] A number.
 * @return {Number} A number.
 * Or {IXholon} this
 * @example
 *     console.log(node.val());
 *     node.val(1234.5678);
 *     console.log(node.val());
 */
api.val = $entry(function(val) {
  if (val === undefined) {
    return this.@org.primordion.xholon.base.IXholon::getVal()();
  }
  else {
    this.@org.primordion.xholon.base.IXholon::setVal(D)(val);
    return this;
  }
});

/**
 * Increment an internal number by a specified amount. This is a convenience method.
 * @method inc
 * @chainable
 * @param {Number} incAmount Increment amount.
 * @return {IXholon} this
 * @example
 *     console.log(node.val());
 *     node.val(1234.5678);
 *     console.log(node.val());
 *     node.inc(1111.1111);
 *     console.log(node.val());
 */
api.inc = $entry(function(incAmount) {
  this.@org.primordion.xholon.base.IXholon::incVal(D)(incAmount);
  return this;
});

/**
 * Decrement an internal number by a specified amount. This is a convenience method.
 * @method dec
 * @chainable
 * @param {Number} decAmount Decrement amount.
 * @return {IXholon} this
 * @example
 *     console.log(node.val());
 *     node.val(1234.5678);
 *     console.log(node.val());
 *     node.dec(1111.1111);
 *     console.log(node.val());
 */
api.dec = $entry(function(decAmount) {
  this.@org.primordion.xholon.base.IXholon::decVal(D)(decAmount);
  return this;
});

/**
 * Get or set the value of a String maintained by this xholon instance.
 * The text is intended to be read and processed by the application,
 * for its own internal purposes.
 * @method text
 * @chainable
 * @param {String} [val] Some text.
 * @return {String} Some text, or null.
 * Or {IXholon} this
 * @example
 *     console.log(node.text());
 *     node.val("one,two.three");
 *     console.log(node.text());
 */
api.text = $entry(function(val) {
  if (val === undefined) {
    return this.@org.primordion.xholon.base.IXholon::getVal_String()();
  }
  else {
    this.@org.primordion.xholon.base.IXholon::setVal_String(Ljava/lang/String;)(val);
    return this;
  }
});

/**
 * Get or set the value of a Object maintained by this xholon instance.
 * @method obj
 * @chainable
 * @param {Object} [val] An object.
 * @return {Object} An object.
 * Or {IXholon} this
 * @example
 *     var obj = node.obj();
 */
api.obj = $entry(function(val) {
  if (val === undefined) {
    return this.@org.primordion.xholon.base.IXholon::getVal_Object()();
  }
  else {
    this.@org.primordion.xholon.base.IXholon::setVal_Object(Ljava/lang/Object;)(val);
    return this;
  }
});

/**
 * Send a message to a receiving IXholon instance.
 * @method msg
 * @chainable
 * @param {Number} signal A distinguishing identifier for this message.
 * @param {Object} data Any data that needs to be sent, or null.
 * @param {IXholon} sender The sender of the message.
 * @param {Number} [index] The index of a replicated port.
 * @return {IXholon} this
 * @example
 *     receiverNode.msg(101, "This is some data", thisNode);
 */
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

/**
 * Send a Synchronous message to a receiving IXholon instance.
 * @method call
 * @param {Number} signal A distinguishing identifier for this message.
 * @param {Object} data Any data that needs to be sent, or null.
 * @param {IXholon} sender The sender of the message.
 * @param {Number} [index] The index of a replicated port.
 * @return {IMessage} A response message.
 * @example
 *     var responseMsg = receiverNode.call(102, "What is 2 + 3 ?", thisNode);
 *     console.log(responseMsg.obj().data);
 */
api.call = $entry(function(signal, data, sender, index) {
  if (index === undefined) {
    return this.@org.primordion.xholon.base.IXholon::sendSyncMessage(ILjava/lang/Object;Lorg/primordion/xholon/base/IXholon;)(signal, data, sender);
  }
  else {
    return this.@org.primordion.xholon.base.IXholon::sendSyncMessage(ILjava/lang/Object;Lorg/primordion/xholon/base/IXholon;I)(signal, data, sender, index);
  }
});

/**
 * Get a list of actions that this xholon can do.
 * These actions can be presented to users in a GUI, or used for other purposes.
 * @method actions
 * @return {String[]} A list of actions, or null.
 * @example
 *     console.log(node.actions());
 */
api.actions = $entry(function() {
  return this.@org.primordion.xholon.base.IXholon::getActionList()();
});

/**
 * Do a specific action that this xholon knows how to do.
 * @method action
 * @chainable
 * @param {String} action The name of a specific action.
 * @return {IXholon} this
 * @example
 *     node.action("Do your thing");
 */
api.action = $entry(function(action) {
  this.@org.primordion.xholon.base.IXholon::doAction(Ljava/lang/String;)(action);
  return this;
});

/**
 * Get or set the node referenced by a port with a specified name or index (0-based).
 * @method port
 * @chainable
 * @param {String|Number} portName The name of the port,
 * or the index of the port within this Xholon's port array.
 * @param {IXholon} [portRef] The IXholon instance that can be accessed through this port.
 * @return {IXholon} A reference to an IXholon instance that can be accessed through this port,
 * or null, or this.
 * @example
 *     console.log(node.port("world"));
 *     console.log(node.port(1));
 * @example
 *     node
 *       .port(0, nodeA)
 *       .port(1, nodeB)
 *       .port("world", nodeC);
 */
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

/**
 * Get all ports as an array.
 * @method ports
 * @return {PortInformation[]} An array of PortInformation objects, or an empty array.
 * @example
 *     console.log(node.ports()[0].obj().reffedNode.toString());
 */
api.ports = $entry(function() {
  return @org.client.XholonJsApi::ports(Lorg/primordion/xholon/base/IXholon;)(this);
});

/**
 * Get all ports as an array.
 * @method portspec
 * @return {PortInformation[]} An array of PortInformation objects, or an empty array.
 * @example
 *     console.log(node.portspec()[0].obj().reffedNode.toString());
 */
api.portspec = $entry(function() {
  return @org.client.XholonJsApi::portspec(Lorg/primordion/xholon/base/IXholon;)(this);
});

/**
 * Get the names of all ports.
 * @method portnames
 * @return {String} A comma-separated list of port names and indexes.
 * @example
 *     console.log(node.portnames());
 */
api.portnames = $entry(function() {
  var node = this;
  var clazz = node.@org.primordion.xholon.base.IXholon::getClass()();
  var app = $wnd.xh.app();
  return app.@org.primordion.xholon.app.Application::getAppSpecificObjectValNames(Lorg/primordion/xholon/base/IXholon;Ljava/lang/Class;)(node, clazz);
});

/**
 * Get or set the value of an attribute.
 * setter ex: root.attr("State", "9");
 * setter ex: root.attr("State", 17);
 * getter ex: Number(root.attr("state")) + 11;
 * @method attr
 * @chainable
 * @param {String} attrName An attribute name.
 * @param {String} [attrVal] An attribute value.
 * @return {String|IXholon} An attribute value, or null, or this.
 * @example
 *     console.log(node.attr("myAttr"));
 *     node.attr("myAttr", "myAttrVal");
 *     console.log(node.attr("myAttr"));
 */
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

/**
 * Is there an attribute with the specified name.
 * @method isattr
 * @param {String} attrName An attribute name.
 * @return {boolean} true or false
 * @example
 *     console.log(node.isattr("myAttr"));
 */
api.isattr = $entry(function(attrName) {
  var node = this;
  var clazz = node.@org.primordion.xholon.base.IXholon::getClass()();
  var app = $wnd.xh.app();
  return app.@org.primordion.xholon.app.Application::isAppSpecificAttribute(Lorg/primordion/xholon/base/IXholon;Ljava/lang/Class;Ljava/lang/String;)(node, clazz, attrName);
});

/**
 * Get all app-specific attributes.
 * @method attrs
 * @param {boolean} returnAll Whether to return just the app-specific attributes (false),
 * or also the attributes of base superclasses (true).
 * @return {Object[][]} 
 * @example
 *     console.log(node.attrs(false));
 */
api.attrs = $entry(function(returnAll) {
  var node = this;
  var clazz = node.@org.primordion.xholon.base.IXholon::getClass()();
  var app = $wnd.xh.app();
  return app.@org.primordion.xholon.app.Application::getAppSpecificAttributes(Lorg/primordion/xholon/base/IXholon;Ljava/lang/Class;Z)(node, clazz, returnAll);
});

/**
 * Evaluate an XPath 1.0 expression.
 * @method xpath
 * @param {String} expression An XPath 1.0 String expression (ex: "ancestor::HelloWorldSystem/World");
 * @return {IXholon} An IXholon node, or null.
 * @example
 *     var hello = $wnd.xh.root().first();
 *     var world = hello.xpath("ancestor::HelloWorldSystem/World");
 */
api.xpath = $entry(function(expression) {
  return @org.client.XholonJsApi::xpath(Ljava/lang/String;Lorg/primordion/xholon/base/IXholon;)(expression, this);
});

// TODO pcs(expression) and select(expression)

