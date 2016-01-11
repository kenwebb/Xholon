/**
 * Xholon JavaScript API - Unit Tests with QUnit
 *
 * These tests are designed to work with "Application --> Xholon Furcifer [timeStep=0]"
 *
 * IXholon nodes aspects include:
 * Applicable, Attributable, Classifiable, Clientable, Composable, Connectable, Identifiable, Sociable
 * see Java classes in package org.primordion.xholon.api; (in original Xholon, but not in GWT version)
 *
 * QUnit assertions:
 * ok, equal, notEqual, strictEqual, notStrictEqual, deepEqual, notDeepEqual, raises
 * equal( actual, expected [, message ] )
 * notEqual( actual, expected, message )
 *
 */

//------------------------ sanity check ---------------------
module("sanity check");

test("hello test", 2, function() {
  ok( 1 == "1", "Passed!" );
  equal( 0, 0, "Zero; equal succeeds" );
});

//------------------------ xh ---------------------
module("xh");

test("Existence", 3, function() {
  notEqual(xh, null, "xh != null");
  notEqual(typeof xh, 'undefined', "typeof xh != 'undefined'");
  equal(typeof xh, "object", "xh is an Object");
});

test("Application", 2, function() {
  var app = xh.app();
  equal(typeof app, "object", "app is an Object");
  equal(app.name(), "Application", "app name is 'Application'");
});

test("Parameters", 35, function() {
  var appM = xh.param("AppM");
  var infoM = xh.param("InfoM");
  var errorM = xh.param("ErrorM");
  equal(appM, "true", "AppM is initially true");
  equal(infoM, "false", "InfoM is initially false");
  equal(errorM, "true", "ErrorM is initially true");
  xh.param("AppM", "false");
  xh.param("InfoM", "true");
  xh.param("ErrorM", "false");
  equal(xh.param("AppM"), "false", "AppM can be set to false");
  equal(xh.param("InfoM"), "true", "InfoM can be set to true");
  equal(xh.param("ErrorM"), "false", "ErrorM can be set to false");
  xh.param("AppM", "true");
  xh.param("InfoM", "false");
  xh.param("ErrorM", "true");
  // use boolean rather than String to set the value
  equal(xh.param("ErrorM"), "true", "ErrorM is true");
  xh.param("ErrorM", "false");
  equal(xh.param("ErrorM"), "false", "ErrorM can be set to false using a boolean");
  xh.param("ErrorM", "true");
  equal(xh.param("ErrorM"), "true", "ErrorM can be set to true using a boolean");
  // restore initial values
  xh.param("AppM", appM);
  xh.param("InfoM", infoM);
  xh.param("ErrorM", errorM);
  // other params
  notEqual(xh.param("DataPlotterParams"), null, "DataPlotterParams has a non-null value");
  notEqual(xh.param("GridPanelClassName"), null, "GridPanelClassName has a non-null value");
  notEqual(xh.param("HistogramPlotterParams"), null, "HistogramPlotterParams has a non-null value");
  equal(xh.param("ImageFile"), null, "ImageFile has a null value");
  notEqual(xh.param("InitialControllerState"), null, "InitialControllerState has a non-null value");
  notEqual(xh.param("InteractionParams"), null, "InteractionParams has a non-null value");
  notEqual(xh.param("IQueueImplName"), null, "IQueueImplName has a non-null value");
  notEqual(xh.param("JavaClassName"), null, "JavaClassName has a non-null value");
  notEqual(xh.param("JavaXhClassName"), null, "JavaXhClassName has a non-null value");
  notEqual(xh.param("JavaXhClassClassName"), null, "JavaXhClassClassName has a non-null value");
  notEqual(xh.param("ModelName"), null, "ModelName has a non-null value");
  notEqual(xh.param("MaxProcessLoops"), null, "MaxProcessLoops has a non-null value");
  notEqual(xh.param("MaxPorts"), null, "MaxPorts has a non-null value");
  notEqual(xh.param("RandomNumberSeed"), null, "RandomNumberSeed has a non-null value");
  notEqual(xh.param("SizeMessageQueue"), null, "SizeMessageQueue has a non-null value");
  notEqual(xh.param("TimeStep"), null, "TimeStep has a non-null value");
  notEqual(xh.param("TimeStepInterval"), null, "TimeStepInterval has a non-null value");
  notEqual(xh.param("UseInteractions"), null, "UseInteractions has a non-null value");
  notEqual(xh.param("UseDataPlotter"), null, "UseDataPlotter has a non-null value");
  notEqual(xh.param("UseHistogramPlotter"), null, "UseHistogramPlotter has a non-null value");
  notEqual(xh.param("UseVrml"), null, "UseVrml has a non-null value");
  notEqual(xh.param("UseGridViewer"), null, "UseGridViewer has a non-null value");
  notEqual(xh.param("VrmlWriterClassName"), null, "VrmlWriterClassName has a non-null value");
  notEqual(xh.param("VrmlParams"), null, "VrmlParams has a non-null value");
  notEqual(xh.param("XincludePath"), null, "XincludePath has a non-null value");
  equal(xh.param("BadParamName"), null, "BadParamName does not exist");
});

test("Service", 4, function() {
  var service = xh.service("XholonHelperService");
  notEqual(service, null, "The XholonHelperService is locateable");
  equal(service.xhc().name(), "XholonHelperService", "The service is a XholonHelperService");
  //
  service = xh.service("TimelineService");
  notEqual(service, null, "The TimelineService is locateable");
  equal(service.xhc().name(), "XholonServiceImpl", "The service is a XholonServiceImpl");
});

test("Services", 1, function() {
  xh.services();
  ok(true, "List of service names and statuses are written to the Xholon out tab.");
});

test("XPath 1.0", 1, function() {
  var root = xh.root();
  var first = root.first();
  var next = first.next();
  // child axis, wildcard
  var wildcard = xh.xpath("*", root);
  equal(wildcard, first, "XPath expressions can include the wildcard character (*)");
});

test("Attrs", 1, function() {
  xh.attrs(xh.root());
  ok(true, "JavaScript attributes of root are written to the browser console.");
});

test("html.xhElements", 1, function() {
  var eleNames = xh.html.xhElements();
  var expected = "xhtop,xhgui,xhappspecific,xhconsole,xhtabs,xhchart,xhcanvas,xhgraph,xhsvg,xhimg,xhunittest,xhfooter";
  notEqual(eleNames, null,
    "List of top-level HTML div elements can be generated and returned: " + eleNames);
});

//------------------------ ixholon ---------------------
module("ixholon");

test("xh.root", 1, function() {
  var root = xh.root();
  equal(typeof root, "object", "root is an Object");
});

/**
 * Attributable
 * An IXholon node may have String attributes, each with a name and a value.
 * this tests attr() getter and setter
 */
test("Attributable", 11, function() {
  var root = xh.root();
  var first = root.first();
  equal(typeof root.attrs(), "object", "node.attrs() shall return a non-null value");
  ok(Array.isArray(root.attrs()), "node.attrs() shall return an Array, possible empty");
  equal(root.attr("notAnAttrName"), null,
    "node.attr(attrName) getter shall return null if there is no such named attribute");
  // ok to try to set value of a non-existent attribute
  ok(root.attr("notAnAttrName", "junk") == root,
    "node.attr(attrName, attrValue) setter shall return node (chainable)");
  equal(root.attr(), null, "node.attr() with no attrName shall return null");
  
  equal(first.isAttr("state"), true, 'first has an attribute called "state"');
  equal(first.isAttr("State"), true, 'first has an attribute called "State" (case-insensitive)');
  equal(first.isAttr("abc"), false, 'first does not have an attribute called "abc"');
  equal(root.attr("state"), 0, 'the "state" attribute in root has a value of 0');
  root.attr("state", "18");
  equal(root.attr("state"), "18", 'the value of the "state" attribute in root can be set using a String');
  root.attr("state", 17);
  equal(root.attr("state"), 17, 'the value of the "state" attribute in root can be set using a Number');
  
  // TODO test attr() with a non-String attrName ex:
  // following produces: Object 23 has no method 'toLowerCase' in Chrome stack trace
  //root.attr(23);
  
  // TODO test attr() with a non-String attrValue  Object, Array
  root.attr("state", {});
  console.log(root.attr("state"));
  root.attr("state", []);
  console.log(root.attr("state"));
});

/**
 * Classifiable
 * An IXholon node shall have an associated IXholonClass.
 * this tests xhc()
 */
test("Classifiable", 7, function() {
  var root = xh.root();
  var xhc = root.xhc();
  notEqual(xhc, null, "each IXholon node shall reference a IXholonClass class node");
  equal(typeof xhc.id(), "number", "xhc.id() shall return a unique identifying number");
  notEqual(xhc.name(), null, "xhc.name() shall not == null");
  // hasClass()
  var galaxy = root.last().last().first();
  equal(galaxy.hasClass("Galaxy"), true, "a galaxy is a Galaxy");
  equal(galaxy.hasClass("AstronomicalObject"), true, "a galaxy is an AstronomicalObject");
  equal(galaxy.hasClass("XholonClass"), true, "a galaxy is a XholonClass");
  equal(galaxy.hasClass("Asteroid"), false, "a galaxy is not an Asteroid");
});

/**
 * Composable
 * IXholon nodes are composed into a hierarchy (a tree structure, with a single unique root).
 * for testing, assume that root has at least 2 children
 * this tests first() last() next() prev() parent()
 */
test("Composable", 4, function() {
  var root = xh.root();
  var first = root.first();
  var last = root.last();
  var next = first.next();
  var prev = next.prev();
  var parentFirst = first.parent();
  var parentNext = next.parent();
  var parentLast = last.parent();
  equal(parentFirst, root, "root.first().parent() shall == root");
  equal(parentNext, root, "root.first().next().parent() shall == root");
  equal(parentLast, root, "root.last().parent() shall == root");
  equal(prev, first, "first.next().prev() shall == first");
});

/**
 * Connectable
 * An IXholon node may be connected to other nodes by ports.
 * for testing, assume that root has at least 2 children,
 * and that the first 2 children are bidirectionally connected by ports
 * this tests port(0) port("x") portNames() ports() portSpec()
 */
test("Connectable", 17, function() {
  var root = xh.root();
  var first = root.first();
  var next = first.next();
  var firstPort = first.port(0);
  var nextPort = next.port(0);
  equal(nextPort, first, "next.port(0) == first");
  equal(firstPort, next, "first.port(0) == next");
  console.log(first.portNames()); // 0,
  console.log(first.ports()); // array of 1 "org.primordion.xholon.base.PortInformation"
  console.log(first.portSpec()); // array of 1 "org.primordion.xholon.base.PortInformation"
  console.log(first.ports()[0].obj());
  console.log(first.portSpec()[0].obj());
  equal(first.portNames(), "0,", 'portNames() == "0,"');
  notEqual(first.ports(), null, "ports()");
  notEqual(first.portSpec(), null, "portSpec()");
  
  // ports()[0].obj()
  var portInfoObj = first.ports()[0].obj();
  equal(portInfoObj.fieldName, "port", 'first.ports()[0].obj().fieldName == "port"');
  equal(portInfoObj.fieldNameIndex, 0, 'first.ports()[0].obj().fieldNameIndex == 0');
  equal(portInfoObj.fieldNameIndexStr, "P_PARTNER",
    'first.ports()[0].obj().fieldNameIndexStr == "P_PARTNER"');
  equal(portInfoObj.reffedNode.xhc().name(), "World",
    'first.ports()[0].obj().reffedNode.xhc().name() == "World"');
  equal(portInfoObj.xpathExpression, "ancestor::HelloWorldSystem/World",
    'first.ports()[0].obj().xpathExpression == "ancestor::HelloWorldSystem/World"');
  
  // node with named ports
  var gal = root.xpath("descendant::Universe/Galaxy[@roleName='two']");
  // following fails
  //var otherGal = gal.port('otherGal');
  var otherGalPI = gal.ports()[0]; // a PortInformation
  equal(otherGalPI.obj().reffedNode.role(), 'one',
    "Galaxy[@roleName='two'].ports()[0].obj().reffedNode.role() == 'one'");
  
  // non-existent port nums/names; port array size is 5
  equal(first.port(1), null, "first.port(1) == null; Furcifer MaxPorts = 5");
  equal(first.port(4), null, "first.port(4) == null");
  equal(first.port(5), null, "first.port(5) == null");
  equal(first.port(99), null, "first.port(99) == null");
  equal(first.port(-1), null, "first.port(-1) == null");
  equal(first.port("notAPort"), null, 'first.port("notAPort") == null');
});

/**
 * Identifiable
 * Each IXholon node shall have a unique numeric id and non-null name.
 * An IXholon node may have a role name.
 * If a node does not have a role name, then node.role() shall return null.
 * this tests id() name() role()
 */
test("Identifiable", 10, function() {
  var root = xh.root();
  var first = root.first();
  var next = first.next();
  // id()
  equal(root.id(), 0, "root id shall == 0");
  notEqual(first.id(), root.id(), "each node in the CSH shall have a unique id");
  notEqual(next.id(), root.id(), "each node in the CSH shall have a unique id");
  notEqual(first.id(), next.id(), "each node in the CSH shall have a unique id");
  // name()
  notEqual(root.name(), null, "root name shall not == null");
  notEqual(first.name(), root.name(), "each node in the CSH shall have a unique name");
  notEqual(next.name(), root.name(), "each node in the CSH shall have a unique name");
  notEqual(first.name(), next.name(), "each node in the CSH shall have a unique name");
  // role()
  equal(root.role(), null, "in Furcifer app, root.role() == null");
  equal(root.role("BLAH").role(), "BLAH", 'node.role("BLAH") is chainable');
});

/**
 * Sociable
 * An IXholon node may be able to send async (msg()) and sync (call()) messages to other nodes.
 * this tests msg() call()
 */
test("Sociable", 8, function() {
  var root = xh.root();
  var signal = 1379; // an unknown signal
  var data = null;
  var sender = root.first();
  var receiver = sender.next();
  var node = receiver.msg(signal, data, sender);
  equal(receiver, node, "node.msg() is chainable");
  
  var respMsg = receiver.call(signal, data, sender); // response to an unknown signal
  notEqual(respMsg, null, "The response to node.call() shall be a valid IMessage.");
  var obj = respMsg; //.obj();
  notEqual(obj, null, "A response message shall be accessable as a human-readable and processable JavaScript object.");
  equal(obj.signal, -8,
    "Signal received in a response to an unknown request message shall == ISignal.SIGNAL_UNKNOWN (-8).");
  equal(obj.data, null, "Data received in a response to an unknown request message shall == null.");
  equal(obj.sender, receiver,
    "Sender received in a response to an unknown request message shall == original receiver.");
  equal(obj.receiver, sender,
    "Receiver received in a response to an unknown request message shall == original sender.");
  equal(obj.index, 0, "Index received in a response to an unknown request message shall == 0.");
});

/**
 * Annotation
 * A IXholon node may have a descriptive annotation.
 * this tests anno()
 */
test("Annotation", 7, function() {
  var root = xh.root();
  var first = root.first();
  notEqual(root.anno(), null, "In Furcifer, the root node has an annotation.");
  equal(first.anno(), null, "In Furcifer, the child nodes do not have an annotation.");
  var anno = "Now I have an annotation.";
  root.anno(null);
  first.anno(anno);
  equal(root.anno(), null, "Now the root node has no annotation.");
  notEqual(first.anno(), null, "Now the child node has an annotation.");
  
  // these also work (Firefox, Chrome), although they may not be useful
  root.anno(123);
  console.log(root.anno());  // Number 123
  root.println(root.anno()); // 123
  equal(root.anno(), 123, "root.anno(123) is valid");
  root.anno(["abc","def"]);
  console.log(root.anno());  // Array ["abc","def"]
  root.println(root.anno()); // abc,def
  equal(root.anno(), "abc,def", 'root.anno(["abc","def"]) is valid, and returns "abc,def"');
  root.anno(root);
  console.log(root.anno());  // Object root
  root.println(root.anno()); // helloWorldSystem_0 state=0
  equal(root.anno(), root, "root.anno(root) is valid");
});

/**
 * Obj
 * A IXholon node may contain an Object.
 * this tests obj()
 */
test("Obj", 7, function() {
  var root = xh.root();
  var first = root.first();
  equal(root.obj(), null, "In Furcifer, the root node has no object.");
  equal(first.obj(), null, "In Furcifer, the child nodes do not have an object.");
  var obj = {
    name: { first: "Licorice", last: "Cat" },
    age: 6,
    fur: "black",
    eyes: "green"
  };
  root.obj(null);
  first.obj(obj);
  equal(root.obj(), null, "The root node still has no object.");
  notEqual(first.obj(), null, "Now the child node has an object.");
  equal(first.obj().fur, "black", "My cat has black fur.");
  equal(first.obj().eyes, "green", "My cat has green eyes.");
  equal(first.obj().name.first, "Licorice", "My cat is named Licorice.");
});

/**
 * Text
 * A IXholon node may have text.
 * this tests text()
 */
test("Text", 7, function() {
  var root = xh.root();
  var first = root.first();
  equal(root.text(), null, "In Furcifer, the root node has no text.");
  equal(first.text(), null, "In Furcifer, the child nodes do not have text.");
  var text = "Now I have text.";
  root.text(null);
  first.text(text);
  equal(root.text(), null, "Now the root node has no text.");
  notEqual(first.text(), null, "Now the child node has text.");
  
  // these also work (Firefox, Chrome), although they may not be useful
  root.text(456.789);
  console.log(root.text());  // Number 456.789
  root.println(root.text()); // 456.789
  equal(root.text(), 456.789, "root.text(456.789) is valid");
  root.text(["ghi","JKL","987"]);
  console.log(root.text());  // Array ["ghi","JKL","987"]
  root.println(root.text()); // ghi,JKL,987
  equal(root.text(), "ghi,JKL,987", 'root.text(["ghi","JKL","987"]) is valid, and returns "ghi,JKL,987"');
  root.text(root);
  console.log(root.text());  // Object root
  root.println(root.text()); // helloWorldSystem_0 state=0
  equal(root.text(), root, "root.text(root) is valid");
});

/**
 * Val
 * A IXholon node may have a numeric value.
 * this tests val() inc() dec()
 */
test("Val", 10, function() {
  var root = xh.root();
  var first = root.first();
  equal(root.val(), 0.0, "In Furcifer, the root node has no val.");
  equal(first.val(), 0, "In Furcifer, the child nodes do not have a val.");
  var val1 = 17.71;
  var val2 = -13
  root.val(val1);
  first.val(val2);
  equal(root.val(), val1, "Now the root node has a val.");
  notEqual(first.val(), 0, "Now the child node a val.");
  
  // these also work (Firefox, Chrome), although they may not be useful
  root.val("456.789");
  console.log(root.val());  // Number 456.789
  root.println(root.val()); // 456.789
  equal(root.val(), 456.789, 'root.val("456.789") is valid');
  root.val(["ghi","JKL","987"]);
  console.log(root.val());  // Array ["ghi","JKL","987"]
  root.println(root.val()); // ghi,JKL,987
  equal(root.val(), "ghi,JKL,987", 'root.val(["ghi","JKL","987"]) is valid, and returns "ghi,JKL,987"');
  //root.val(root);
  //console.log(root.val());  // Object root
  //root.println(root.val()); // helloWorldSystem_0 state=0
  //equal(root.val(), root, "root.text(root) is valid");
  
  // inc dec
  root.val(1);
  root.inc(5);
  equal(root.val(), 1+5, "root.val(1); root.inc(5) = 6");
  
  root.val(123.456).inc(1);
  equal(root.val(), 123.456+1, "root.val(123.456).inc(1); = 124.456 (chainable)");
  
  root.val(123.456).dec(1);
  equal(root.val(), 123.456-1, "root.val(123.456).dec(1); = 122.456 (chainable)");
  
  root.val(11.1).dec(0.5).inc(17).dec(0).dec(2.02);
  root.println(root.val()); // 25.580000000000002
  notEqual(root.val(), 0, "val() inc() dec() are chainable");
  
});

/**
 * XPath 1.0
 * A IXholon node can locate other nodes using XPath 1.0 expressions.
 * this tests xpath()
 */
test("XPath 1.0", 18, function() {
  var root = xh.root();
  var first = root.first();
  var next = first.next();
  // child axis, wildcard
  var wildcard = root.xpath("*");
  equal(wildcard, first, "XPath expressions can include the wildcard character (*)");
  // child axis, indexes
  var one = root.xpath("*[1]");
  var two = root.xpath("*[2]");
  equal(one, first, "XPath expressions can include indexes (1-based) *[1]");
  equal(two, next, "XPath expressions can include indexes (1-based) *[2]");
  // child axis, IXholonClass node names
  var hello = root.xpath("Hello");
  var world = root.xpath("World");
  equal(hello, first, "XPath expressions can locate children");
  equal(world, next, "XPath expressions can locate children");
  // parent axis
  var parent = hello.xpath("..");
  equal(parent, root, "XPath expressions can locate parent");
  // descendant axis
  var desc = root.xpath("descendant::World");
  equal(desc, next, "XPath expressions can locate a descendant");
  var universe = root.xpath("descendant::Universe");
  equal(universe.xhc().name(), "Universe", "XPath expressions can locate a deep descendant");
  // ancestor axis
  var anc = first.xpath("ancestor::HelloWorldSystem");
  equal(anc, root, "XPath expressions can locate an ancestor");
  // not found
  var notFound = root.xpath("Junk");
  equal(notFound, null, "XPath returns null with expressions that don't find a match");
  // child axis, multiple steps
  var hello2 = root.xpath("HelloWorldSystem/Hello");
  equal(hello2.xhc().name(), "Hello", "XPath expressions can include multiple steps");
  // ancestor + descendant
  var hello3 = hello.xpath("ancestor::HelloWorldSystem/HelloWorldSystem/descendant::Hello");
  equal(hello3.xhc().name(), "Hello", "XPath expressions can combine ancestor and descendant axes");
  // node with roleName
  var galThree = universe.xpath("Galaxy[@roleName='three']");
  equal(galThree.name(), "three:galaxy_11", "XPath expressions can include node attributes");
  // node with index
  var galIndexed = universe.xpath("Galaxy[3]");
  equal(galIndexed.name(), "galaxy_10", "XPath expressions can include indexes Galaxy[3]");
  // following-sibling and preceding-sibling axes
  var fsib = universe.first();
  fsib = fsib.xpath("following-sibling::*");
  equal(fsib.name(), "two:galaxy_9", "XPath expressions can locate a following-sibling using wildcard");
  fsib = fsib.xpath("preceding-sibling::*");
  equal(fsib.name(), "one:galaxy_8", "XPath expressions can locate a preceding-sibling using wildcard");
  fsib = fsib.xpath("following-sibling::Galaxy");
  equal(fsib.name(), "two:galaxy_9", "XPath expressions can locate a following-sibling using XholonClass name");
  fsib = fsib.xpath("preceding-sibling::Galaxy");
  equal(fsib.name(), "one:galaxy_8", "XPath expressions can locate a preceding-sibling using XholonClass name");
});

/**
 * Restructuring the tree
 * A IXholon node can be moved around in the tree.
 * this tests remove() append() appendTo() prepend() prependTo() before() insertBefore() after() insertAfter()
 */
test("Restructuring the tree", 22, function() {
  var root = xh.root();
  var universe = root.xpath("descendant::Universe");
  // initial location
  equal(universe.parent().name(), "helloWorldSystem_4", "Universe parent is helloWorldSystem_4");
  equal(universe.prev().name(), "world_6", "Universe prev is world_6");
  var hello = universe.parent().first();
  
  // remove() insertBefore()
  universe.remove().insertBefore(hello);
  equal(universe.next().name(), "hello_5", "insertBefore() Universe next is hello_5");
  // remove() insertAfter()
  universe.remove().insertAfter(hello);
  equal(universe.prev().name(), "hello_5", "insertAfter() Universe prev is hello_5");
  equal(universe.next().name(), "world_6", "insertAfter() Universe next is world_6");
  equal(universe.parent().name(), "helloWorldSystem_4", "insertAfter() Universe parent is still helloWorldSystem_4");
  // remove() prependTo()
  var hws = universe.parent();
  universe.remove().prependTo(hws);
  equal(hws.first().name(), "universe_7", "prependTo() Universe is first child of its parent");
  // remove() appendTo()
  universe.remove().appendTo(hws);
  equal(hws.first().next().next().name(), "universe_7", "appendTo() Universe is last child of its parent");
  
  // final location should be same as initial location
  equal(universe.parent().name(), "helloWorldSystem_4", "Universe parent is again helloWorldSystem_4");
  equal(universe.prev().name(), "world_6", "Universe prev is again world_6");
  
  // remove() before()
  hello.before(universe.remove());
  equal(universe.next().name(), "hello_5", "before() Universe next is hello_5");
  // remove() after()
  hello.after(universe.remove());
  equal(universe.prev().name(), "hello_5", "after() Universe prev is hello_5");
  equal(universe.next().name(), "world_6", "after() Universe next is world_6");
  equal(universe.parent().name(), "helloWorldSystem_4", "after() Universe parent is still helloWorldSystem_4");
  // remove() prepend()
  var hws = universe.parent();
  hws.prepend(universe.remove());
  equal(hws.first().name(), "universe_7", "prepend() Universe is first child of its parent");
  // remove() append()
  hws.append(universe.remove());
  equal(hws.first().next().next().name(), "universe_7", "append() Universe is last child of its parent");
  
  // final location should be same as initial location
  equal(universe.parent().name(), "helloWorldSystem_4", "Universe parent is once again helloWorldSystem_4");
  equal(universe.prev().name(), "world_6", "Universe prev is once again world_6");
  
  // prepend(xmlString) append(xmlString) before(xmlString) after(xmlString)
  universe.prepend('<Galaxy roleName="zero"/>');
  equal(universe.first().role(), "zero", "prepend(xmlString)");
  universe.append('<Galaxy roleName="five"/>');
  var last = universe.first().next().next().next().next().next().next();
  equal(last.role(), "five", "append(xmlString)");
  last.before('<Galaxy roleName="beforeFive"/>');
  equal(last.prev().role(), "beforeFive", "before(xmlString)");
  last.after('<Galaxy roleName="afterFive"/>');
  equal(last.next().role(), "afterFive", "after(xmlString)");
});

/**
 * Print
 * A IXholon node can print to the HTML out tag.
 * this tests print() println()
 */
test("Print", 1, function() {
  var root = xh.root();
  equal(root.print("one").println(" two").print("three").println(" four five."), root,
    "print() and println() are chainable");
});

/**
 * Actions
 * A IXholon node can perform named actions.
 * this tests actions() action()
 */
test("Actions", 7, function() {
  var root = xh.root();
  //var service = xh.service("RecordPlaybackService");
  // can't get the service directly
  var service = xh.service("ServiceLocatorService").xpath("../RecordPlaybackService");
  var actions = service.actions();
  root.println(actions);
  console.log(actions);
  // ["Toggle Record", "Show Storage Names", "Show Storage Values", "Show Status", "Remove all items from Storage"]
  equal(actions[0], "Toggle Record",
    '"Toggle Record" is a valid RecordPlaybackService action');
  equal(actions[1], "Show Storage Names",
    '"Show Storage Names" is a valid RecordPlaybackService action');
  equal(actions[2], "Show Storage Values",
    '"Show Storage Values" is a valid RecordPlaybackService action');
  equal(actions[3], "Show Status",
    '"Show Status" is a valid RecordPlaybackService action');
  equal(actions[4], "Remove all items from Storage",
    '"Remove all items from Storage" is a valid RecordPlaybackService action');
  notEqual(actions, null, "RecordPlaybackService can perform actions.");
  equal(service.action("Show Status").action("Toggle Record")
    .action("Show Status").action("Toggle Record")
    .action("Show Status"),
    service, "action() is chainable");
});


