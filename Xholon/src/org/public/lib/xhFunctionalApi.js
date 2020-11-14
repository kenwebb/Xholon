// This library provides a functional programming interface (API) for Xholon node functions, especially those found in XholonJsApi.java .
// xhFunctionalApi.js
// Ken Webb  January 8, 2020
// MIT License, Copyright (C) 2020 Ken Webb
// 
// see also: Xholon workbook "Haskell - Hutton - Binary string transmitter" 7eea6b043213653408b492f6a2de329c
// 
// ramda examples:
// var txtext = R.pipe(xhf.parent, xhf.role)(xhf.first(xhf.root()));
// var txtext2 = R.compose(xhf.role, xhf.parent)(xhf.first(xhf.root()));
// var txtext3 = R.compose(xhf.name, xhf.parent)(xhf.first(xhf.root())); // works in Firefox Dev Tools
// xhf.btparent(R.pipe(xhf.root, xhf.first, xhf.next)()).name(); // "hello_2"
// R.pipe(xhf.root, xhf.first, xhf.next, xhf.btparent, xhf.name)(); // "hello_2"
// with (xhf) {R.pipe(root, first, next)().name()};
// with (xhf) {R.pipe(root, first, next, name)()}; // "world_3"
// with (xhf) {R.compose(name, next, first, root)()}; // "world_3"


if (typeof xh == "undefined") {
  xh = {};
}

if (typeof xhf == "undefined") {
  xhf = {};
}

// parent :: IXholon -> IXholon
xhf.parent = node => node.parent()
// ex: xhf.parent(node)

// first :: IXholon -> IXholon
xhf.first = node => node.first()
// ex: xhf.first(node)

// last :: IXholon -> IXholon
xhf.last = node => node.last()
// ex: xhf.last(node)

// next :: IXholon -> IXholon
xhf.next = node => node.next()
// ex: xhf.next(node);

// prev :: IXholon -> IXholon
xhf.prev = node => node.prev()
// ex: xhf.prev(node);

// btparent :: IXholon -> IXholon
xhf.btparent = node => node.btparent()
// ex: xhf.btparent(node);

// btleft :: IXholon -> IXholon
xhf.btleft = node => node.btleft()
// ex: xhf.btleft(node);

// btright :: IXholon -> IXholon
xhf.btright = node => node.btright()
// ex: xhf.btright(node);

// remove :: IXholon -> IXholon
xhf.remove = node => node.remove()
// ex: xhf.remove(node);

// append TODO

// id :: IXholon -> Number
xhf.id = node => node.id()
// ex: xhf.id(xhf.root()); //=> 0

// identity
xhf.identity = node => node.identity()
// ex: xhf.identity(xhf.root());

// name :: IXholon -> String
xhf.name = node => node.name()
// ex: xhf.name(xhf.root()); //=> chameleon_0

// role :: IXholon -> String
xhf.role = node => node.role()
// ex: xhf.role(xhf.root()); //=> null
// ex: xhf.role(xhf.root().first()); //=> "helloworld"
// ex: xhf.name(xhf.root()); // "helloWorldSystem_0"

// TODO how to mutate/change the value of node.role(); IMPURE role
// roleI :: (String, IXholon) -> IXholon
xhf.roleI = (roleName, node) => node.role(roleName)
// ex: xhf.roleI("JUNK", xhf.root().first());

// xhType :: IXholon -> Number
xhf.xhType = node => node.xhType()
// ex: xhf.xhType(xhf.root());

// xhc :: IXholon -> IXholonClass
xhf.xhc = node => node.xhc()
// ex: xhf.xhc(xhf.root());

// anno :: IXholon -> String
xhf.anno = node => node.anno()
// ex: xhf.anno(xhf.root());

// val :: IXholon -> Number
xhf.val = node => node.val()
// ex: xhf.val(xhf.root()); //=> 123.45

// text :: IXholon -> String
xhf.text = node => node.text()
// ex: xhf.text(xhf.root()); //=> null

// bool :: IXholon -> Boolean
xhf.bool = node => node.bool()
// ex: xhf.bool(xhf.root()); //=> false

// obj :: IXholon -> Object
xhf.obj = node => node.obj()
// ex: xhf.obj(xhf.root()); //=> null

// port :: (Number, IXholon) -> IXholon
xhf.port = (portNum, node) => node.port(portNum)
// ex: xhf.port(0, xhf.root()); //=> null

// links :: (Boolean, Boolean, IXholon) -> [PortInformation]
xhf.links = (placeGraph, linkGraph, node) => node.links(placeGraph, linkGraph)
// ex: xhf.links(false, true, xhf.root().first());

// neighbors :: IXholon -> [IXholon]
xhf.neighbors = node => node.neighbors()
// ex: xhf.neighbors(xhf.root().first());

// attr :: (String, IXholon) -> Object || VALUE  ???
xhf.attr = (attrName, node) => node.attr(attrName)
// ex: xhf.attr("State", xhf.root()); // {a: 0}

// isAttr :: (String, IXholon) -> Boolean
xhf.isAttr = (attrName, node) => node.isAttr(attrName)
// ex: xhf.isAttr("State", xhf.root()); // true

// attrs :: IXholon -> [[String, Object, Object]]  NAME,VALUE,TYPE
xhf.attrs = node => node.attrs()
// ex: xhf.attrs(xhf.root());

// attrz :: IXholon -> Object
xhf.attrz = node => node.attrz()
// ex: xhf.attrz(xhf.root());

// xpath :: (String, IXholon) -> IXholon
xhf.xpath = (expression, node) => node.xpath(expression)
// ex: xhf.xpath("../World", xhf.root().first());
// ex: xhf.xpath("../World", xhf.root().first()).name(); // "world_3"

// hasClass :: (String, IXholon) -> Boolean
xhf.hasClass = (className, node) => node.hasClass(className)
// ex: xhf.hasClass("Hello", xhf.root().first()); // true

// classAttr :: (String, IXholon) -> Object || VALUE  ???
xhf.classAttr = (attrName, node) => node.classAttr(attrName)
// ex: xhf.classAttr("QName", xhf.root()); // undefined

// hashify :: IXholon -> String
xhf.hashify = node => node.hashify()
// ex: xhf.hashify(xhf.root()); // "(0 (2 3))"

// select :: node -> String
xhf.select = node => node.select()
// ex: xhf.select(xhf.root()); // "helloWorldSystem_0 state=0"

// color :: IXholon -> String
xhf.color = node => node.color()
// ex: xhf.color(xhf.root()); //=> "red"

// opacity :: IXholon -> String
xhf.opacity = node => node.opacity()
// ex: xhf.opacity(xhf.root()); //=> null

// font :: IXholon -> String
xhf.font = node => node.font()
// ex: xhf.font(xhf.root()); //=> null

// icon :: IXholon -> String
xhf.icon = node => node.icon()

// toolTip :: IXholon -> String
xhf.toolTip = node => node.toolTip()

// symbol :: IXholon -> String
xhf.symbol = node => node.symbol()

// format :: IXholon -> String
xhf.format = node => node.format()

// geo :: IXholon -> String
xhf.geo = node => node.geo()

// sound :: IXholon -> String
xhf.sound = node => node.sound()

// tick :: IXholon -> Object -> Object
xhf.tick = (node, obj) => node.tick(obj)
// ex: xhf.tick(node, obj);

// log :: (String, Object) -> Object  IMPURE
//xhf.log = function(str, data) {console.log(str + data); return data;}
xhf.log = (str, data) => {console.log(str + data); return data;}
// ex: xhf.log("current data: ", [1,2,3,4]);

// println :: (String, Object) -> Object  IMPURE
//xhf.println = function(str, data) {const node = xh.root(); node.println(str + data); return data;}
xhf.println = (str, data) => {xhf.root().println(str + data); return data;}
// ex: xhf.println("current data: ", [1,2,3,4])

// print :: (String, Object) -> Object  IMPURE
//xhf.print = function(str, data) {const node = xh.root(); node.print(str + data); return data;}
xhf.print = (str, data) => {xhf.root().print(str + data); return data;}
// ex: xhf.print("current data: ", [1,2,3,4]);

// root :: () -> IXholon
xhf.root = () => xh.root();
// ex: xhf.root();

// app :: () -> IXholon
xhf.app = () => xh.app();
// ex: xhf.app();
// ex: xhf.app().name(); // "Application"

// attrss :: IXholon => IXholon  IMPURE
xhf.attrss = (node) => {xh.attrs(node); return node;}
// ex: xhf.attrss(xhf.root()); // displays attributes in the console

// avatar :: () -> IXholon
xhf.avatar = () => xh.avatar()
// ex: xhf.avatar();
// ex: xhf.avatar().name(); // "avatar_49"
// ex: xhf.avatar().action("who");

// avatarKeyMap :: () -> String
xhf.avatarKeyMap = () => xh.avatarKeyMap()
// ex: xhf.avatarKeyMap();
// ex: JSON.parse(xhf.avatarKeyMap());
// ex: JSON.stringify(JSON.parse(xhf.avatarKeyMap()), null, 2);

// isXholonNode :: Object -> Boolean
xhf.isXholonNode = obj => xh.isXholonNode(obj)
// ex: xhf.isXholonNode({}); // false
// ex: xhf.isXholonNode(xhf.root()); // true

// TODO all the other top-level functions

// mmzxpath :: (String, IXholon) -> IXholon
xhf.mmzxpath = (expression, node) => {
  const cache = {};
  const argStr = node.name() + expression;
  cache[argStr] = cache[argStr] || node.xpath(expression);
  return cache[argStr];
}
// ex: xhf.mmzxpath("..", xhf.root().first());
// ex: 


