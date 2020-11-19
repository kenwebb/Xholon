// This library provides a functional programming interface (API) for Xholon node functions, especially those found in XholonJsApi.java .
// xhFunctionalApi.js
// Ken Webb  January 8, 2020
// MIT License, Copyright (C) 2020 Ken Webb
// 
// Conventions:
// - the names of all IMPURE functions, end in the letter "I"
// - function type signatures use the Ramda style
//   see: https://github.com/ramda/ramda/wiki/Type-Signatures
// 
// I am testing with:
//  http://127.0.0.1:8080/war/Xholon.html?app=HelloWorld&gui=clsc&jslib=xhFunctionalApi,ramda.min
//  and Firefox Dev Tools
// 
// see also: Xholon workbook "Haskell - Hutton - Binary string transmitter" 7eea6b043213653408b492f6a2de329c
// 
// with ideas and code from: Composing Software, by Eric Elliott
// https://medium.com/javascript-scene/composing-software-the-book-f31c77fc3ddc
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
// with (xhf) {R.compose(name, traceI("TEST "), first, root)()}
  // TEST hello_2 [ port:world_3] state=0
  // "hello_2"
// with (xhf) {pipe(root, first, name)()} // "Alice:hello_2"


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

// removeI :: IXholon -> IXholon  IMPURE
xhf.removeI = node => node.remove()
// ex: xhf.removeI(node);

// moveI :: IXholon -> IXholon -> IXholon  IMPURE
xhf.moveI = container => content => container.append(content.remove())
// ex: with (xhf) {compose(traceI("container: "), moveI(compose(first, root)()), avatar)()}

// moveToI :: IXholon -> IXholon -> IXholon  IMPURE
xhf.moveToI = content => container => content.remove().appendTo(container)
// ex: with (xhf) {compose(traceI("content: "), moveToI(avatar), first, root)().name()} // ???


// appendI :: IXholon -> IXholon -> IXholon  IMPURE
xhf.appendI = container => content => container.append(content)
// ex: with (xhf) {compose(traceI("container: "), appendI(compose(first, root)()), removeI, avatar)()} // YES
// ex: with (xhf) {compose(traceI("container: "), appendI(xhf.root().first()), removeI, avatar)()} // YES
// ex: with (xhf) {compose(traceI("container: "), appendI(compose(first, root)()), traceI("avatar: "), actionI("become this role Alice"), removeI, avatar)()} // YES
/*
with (xhf) {
  compose(
    name,
    traceI("container: "),
    appendI(
      compose(
        roleI("Bonjour"),
        first,
        root
      )()
    ),
    traceI("avatar: "),
    actionI("become this role Alicia"),
    removeI,
    avatar
  )()
}

avatar: Alicia:avatar_49
container: Bonjour:hello_2 [ port:world_3] state=0
"Bonjour:hello_2"
*/

// appendToI :: IXholon -> IXholon -> IXholon  IMPURE
xhf.appendToI = content => container => content.appendTo(container)
// ex: with (xhf) {compose(traceI("content: "), appendToI(compose(removeI, avatar)()), first, root)().name()} // "Alice:avatar_49"
/*
with (xhf) {
  compose(
    traceI("content: "),
    appendToI(
      compose(
        removeI,
        avatar
      )()
    ),
    first,
    root
  )()
  .name()
}

OR

with (xhf) {
  compose(
    name,
    traceI("content: "),
    appendToI(
      compose(
        removeI,
        avatar
      )()
    ),
    first,
    root
  )()
}

OR

with (xhf) {
  pipe(
    root,
    first,
    appendToI(
      pipe(
        avatar,
        removeI
      )()
    ),
    traceI("content: "),
    name
  )()
}

content: Alice:avatar_49
"Alice:avatar_49"
*/

// TODO prepend etc.

// id :: IXholon -> Number
xhf.id = node => node.id()
// ex: xhf.id(xhf.root()); //=> 0

// _idI :: Number -> IXholon -> IXholon  IMPURE (in general, this is for system use only)
xhf._idI = num => node => node._id(num)

// identity :: IXholon -> IXholon
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

// roleI :: String -> IXholon -> IXholon  IMPURE
xhf.roleI = roleName => node => node.role(roleName)
// ex: xhf.roleI("Alice")(xhf.root().first()); // returns the node

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
// ex: xhf.val(xhf.root()); // 123.45

// incI :: Number -> IXholon -> IXholon  IMPURE
xhf.incI = num => node => node.inc(num)

// decI :: Number -> IXholon -> IXholon  IMPURE
xhf.decI = num => node => node.dec(num)

// text :: IXholon -> String
xhf.text = node => node.text()
// ex: xhf.text(xhf.root()); // null

// bool :: IXholon -> Boolean
xhf.bool = node => node.bool()
// ex: xhf.bool(xhf.root()); // false

// obj :: IXholon -> Object
xhf.obj = node => node.obj()
// ex: xhf.obj(xhf.root()); // null

// msg :: TODO
// call :: TODO
// actions :: TODO

// port :: (Number, IXholon) -> IXholon
xhf.port = (portNum, node) => node.port(portNum)
// ex: xhf.port(0, xhf.root()); // null

// ports :: TODO
// portSpec :: TODO
// portNames :: TODO

// links :: (Boolean, Boolean, IXholon) -> [PortInformation]
xhf.links = (placeGraph, linkGraph, node) => node.links(placeGraph, linkGraph)
// ex: xhf.links(false, true, xhf.root().first());
// ex: xhf.links(true, true, xhf.root().first());
// ex: xhf.links(true, true, xhf.root().first()).map((item) => item.reffedNode.name());
//  ["helloWorldSystem_0", "Bonjour:role_51", "world_3", "Hello", "world_3"]
// ex: xhf.links(true, true, xhf.root().first()).map((item) => item.fieldName);
//  [ "parent", "first", "next", "xhc", "port" ]
/*
with (xhf) {
  links(
    true,
    true,
    pipe(
      root,
      first
    )()
  ).map((item) => item.reffedNode.name())
}
*/

// links2 :: (Boolean, Boolean, IXholon) -> [PortInformation]
xhf.links2 = (placeGraph, linkGraph, node) => node.links(placeGraph, linkGraph).map((lnk) => ({
  fieldName: lnk.fieldName,
  fieldNameIndex: lnk.fieldNameIndex,
	reffedNode: lnk.reffedNode ? lnk.reffedNode.name() : null,
  xpathExpression: lnk.xpathExpression
}))
// ex: xhf.links2(true, true, xhf.root())
// ex: xhf.links2(true, true, xhf.root().first())
// ex: xhf.links2(true, true, xhf.root().xhc())
// ex: xhf.links2(true, true, xhf.pipe(xhf.root, xhf.first, xhf.xhc)())
// ex: with (xhf) {links2(true, true, pipe(root, first, xhc)())}

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

// xhconsole :: TODO

// select :: node -> String
xhf.select = node => node.select()
// ex: xhf.select(xhf.root()); // "helloWorldSystem_0 state=0"

// subtreez :: TODO
// subtree :: TODO
// uid :: TODO

// color :: IXholon -> String
xhf.color = node => node.color()
// ex: xhf.color(xhf.root()); // "red"

// colorI :: String -> IXholon -> IXholon  IMPURE
xhf.colorI = str => node => node.color(str)
// ex: xhf.colorI("yellow", xhf.root());

// opacity :: IXholon -> String
xhf.opacity = node => node.opacity()
// ex: xhf.opacity(xhf.root()); // null

// font :: IXholon -> String
xhf.font = node => node.font()
// ex: xhf.font(xhf.root()); // null

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

// actionI :: String -> IXholon -> IXholon  IMPURE
xhf.actionI = str => node => node.action(str)
// ex: xhf.actionI("who;where", xhf.avatar);

// tick :: IXholon -> Object -> Object
xhf.tick = (node, obj) => node.tick(obj)
// ex: xhf.tick(node, obj);

// visit :: TODO

// childrenAsCsv :: TODO
// siblingsAsCsv :: TODO
// includes :: TODO
// arrayify :: TODO
// unarrayify :: TODO
// childrenAsArray :: TODO

// subtreeAsArray :: IXholon -> [IXholon]
//  xh.root().subtreeAsArray(); // Array of length 3
//  xh.app().subtreeAsArray(); // Array of length 483
//  TODO combine with a links array for each of these nodes, using an array or Object of Objects  {hello: [LINKS]}
xhf.subtreeAsArray = node => node.subtreeAsArray()
// ex: xhf.subtreeAsArray(xhf.root()).length;
// ex: xhf.subtreeAsArray(xhf.root()).map((node) => node.name());
/*
xhf.subtreeAsArray(xhf.root()).map((node) => `{
  name: ${node.name()},
  role: ${node.role()}
}`
)
*/
//OR
/*var result = xhf.subtreeAsArray(xhf.root()).map((node) => JSON.parse(`{
  "name": "${node.name()}",
  "role": "${node.role()}"
}`)
)
console.log(result);

// OR
var result = xhf.subtreeAsArray(xhf.app()).map((node) => JSON.parse(`{
  "name": "${node.name()}",
  "role": "${node.role()}",
  "id": "${node.id()}",
  "xhcName": "${node.xhc() ? node.xhc().name() : null}"
}`)
)
console.log(result);

// OR  this works
var result = xhf.subtreeAsArray(xhf.app()).map((node) => ({
  id: node.id(),
  xhcName: node.xhc() ? node.xhc().name() : null,
  role: node.role(),
  name: node.name(),
  links: node.links(false, true)
})
)
console.log(result);

// OR  this works
var links = (node) => {
  var lnks = node.links(true, true);
  for (var i = 0; i < lnks.length; i++) {
    var lnk = lnks[i];
    lnk.reffedNode = lnk.reffedNode ? lnk.reffedNode.name() : null;
  }
  return lnks;
}

var result = xhf.subtreeAsArray(xhf.app()).map((node) => ({
  id: node.id(),
  xhcName: node.xhc() ? node.xhc().name() : null,
  role: node.role(),
  name: node.name(),
  links: links(node)
})
)
console.log(result);

// OR
const links = (node) => {
  const lnks = node.links(true, true);
  lnks.forEach(
    (lnk) => lnk.reffedNode = lnk.reffedNode ? lnk.reffedNode.name() : null
  )
  return lnks;
}

const result = xhf.subtreeAsArray(xhf.app()).map((node) => ({
  id: node.id(),
  xhcName: node.xhc() ? node.xhc().name() : null,
  role: node.role(),
  name: node.name(),
  links: links(node)
})
)
console.log(result);

*/

// TODO
// xhf.subtreeAsArray = node => node.subtreeAsArray()
/*xhf.subtreeAsArray2 = (placeGraph, linkGraph, node) => node.subtreeAsArray().map(node) => ({
  id: node.id(),
  role: node.role(),
  name: node.name(),
  links: xhf.links2(placeGraph, linkGraph, node)
})*/

// cache :: TODO
// uncache :: TODO
// javaClassnames :: TODO
// treeSize :: TODO
// numChildren :: TODO
// numbering :: TODO

// logI :: (String, Object) -> Object  IMPURE
//xhf.logI = function(label, data) {console.log(label + data); return data;}
xhf.logI = (label, data) => {console.log(label + data); return data;}
// ex: xhf.logI("current data: ", [1,2,3,4]);

// traceI :: String -> Object -> Object  IMPURE
xhf.traceI = label => data => {console.log(label + data); return data;}
// OR use: console.log(`${ label }: ${ data }`);
// ex: xhf.traceI("TRACE: ")(xhf.root());
// ex: xhf.traceI("")(xhf.root());
// ex: with (xhf) {compose(name, first, traceI("after root: "), root)()}

// printlnI :: (String, Object) -> Object  IMPURE
//xhf.printlnI = function(label, data) {const node = xh.root(); node.println(label + data); return data;}
xhf.printlnI = (label, data) => {xhf.root().println(label + data); return data;}
// ex: xhf.printlnI("current data: ", [1,2,3,4])

// printI :: (String, Object) -> Object  IMPURE
//xhf.printI = function(label, data) {const node = xh.root(); node.print(label + data); return data;}
xhf.printI = (label, data) => {xhf.root().print(label + data); return data;}
// ex: xhf.printI("current data: ", [1,2,3,4]);

// app :: () -> IXholon
xhf.app = () => xh.app();
// ex: xhf.app();
// ex: xhf.app().name(); // "Application"

// root :: () -> IXholon
xhf.root = () => xh.root();
// ex: xhf.root();

// attrssI :: IXholon => IXholon  IMPURE
xhf.attrssI = (node) => {xh.attrs(node); return node;}
// ex: xhf.attrssI(xhf.root()); // displays attributes in the console

// xpath :: String -> IXholon -> IXholon
xhf.xpathh = expression => node => xh.xpath(expression, node)

// xpathExpr :: IXholon -> IXholon -> String
xhf.xpathExpr = (descendant, ancestor) => xh.xpathExpr(descendant, ancestor)

// service :: String -> IXholon
xhf.service = serviceName => xh.service(serviceName)

// servicesI :: () -> ()  IMPURE
xhf.servicesI = () => {xh.services(); return null;}

// param :: String -> String
xhf.param = pName => xh.param(pName)

// paramI :: String -> String -> Boolean  IMPURE
xhf.paramI = pName => pValue => xh.param(pName, pValue)

// TODO
// state :: 
// require :: 
// test ::
// xport ::
// xports :: 

// avatar :: () -> IXholon
xhf.avatar = () => xh.avatar()
// ex: xhf.avatar();
// ex: xhf.avatar().name(); // "avatar_49"
// ex: xhf.avatar().actionI("who");

// avatarKeyMap :: () -> String
xhf.avatarKeyMap = () => xh.avatarKeyMap()
// ex: xhf.avatarKeyMap();
// ex: JSON.parse(xhf.avatarKeyMap());
// ex: JSON.stringify(JSON.parse(xhf.avatarKeyMap()), null, 2);

// TODO
// speechRecognition :: 
// webRTC :: 
// showLocalStorage :: 

// isXholonNode :: Object -> Boolean
xhf.isXholonNode = obj => xh.isXholonNode(obj)
// ex: xhf.isXholonNode({}); // false
// ex: xhf.isXholonNode(xhf.root()); // true

// TODO
// isXholonNodeCanonical :: 
// random :: 
// seed :: 
// matchGraph :: 
// html.toggle :: 
// html.xhElements :: 
// html.popup :: 
// html.urlparam :: 
// html.selectTab :: 
// css.style :: 

// mmzxpath :: (String, IXholon) -> IXholon
xhf.mmzxpath = (expression, node) => {
  const cache = {};
  const argStr = node.name() + expression;
  cache[argStr] = cache[argStr] || node.xpath(expression);
  return cache[argStr];
}
// ex: xhf.mmzxpath("..", xhf.root().first());
// ex: 

// https://medium.com/javascript-scene/curry-and-function-composition-2c208d774983
// compose
xhf.compose = (...fns) => x => fns.reduceRight((y, f) => f(y), x)
// ex: with (xhf) {compose(name, first, root)()} // "Alice:hello_2"

// pipe
xhf.pipe = (...fns) => x => fns.reduce((y, f) => f(y), x)
// ex: with (xhf) {pipe(root, first, name)()} // "Alice:hello_2"

// flip - flip the order of two input parameters
xhf.flip = fn => a => b => fn(b)(a);
// var sub = a => b => a -b; var fsub = xhf.flip(sub); console.log(sub(2)(1), fsub(2)(1));

/*
// Eric Elliott gist AutoCurry.js
const curry = fn => (...args1) => {
  if (args1.length === fn.length) {
    return fn(...args1);
  }

  return (...args2) => {
    const args = [...args1, ...args2];

    if (args.length >= fn.length) {
      return fn(...args);
    }

    return curry(fn)(...args);
  };
};

const add3 = curry((a, b, c) => a + b + c);

add3(1,2,3); // 6
add3(1,2)(3); // 6
add3(1)(2)(3); // 6

// --------------- another Eric Elliot implementation
// Tiny, recursive autocurry
const curry = (
  f, arr = []
) => (...args) => (
  a => a.length === f.length ?
    f(...a) :
    curry(f, a)
)([...arr, ...args]);

// --------------------
// see also: ramda implementation

// --------------------
// see also: http://rosettacode.org/wiki/Currying#JavaScript
(() => {
 
    // (arbitrary arity to fully curried)
    // extraCurry :: Function -> Function
    let extraCurry = (f, ...args) => {
        let intArgs = f.length;
 
        // Recursive currying
        let _curry = (xs, ...arguments) =>
            xs.length >= intArgs ? (
                f.apply(null, xs)
            ) : function () {
                return _curry(xs.concat([].slice.apply(arguments)));
            };
 
        return _curry([].slice.call(args, 1));
    };
 
    // TEST
 
    // product3:: Num -> Num -> Num -> Num
    let product3 = (a, b, c) => a * b * c;
 
    return [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
        .map(extraCurry(product3)(7)(2))
 
    // [14, 28, 42, 56, 70, 84, 98, 112, 126, 140]
 
})();
*/

// curry :: Function -> args
xhf.curry = fn => (...args1) => {
  if (args1.length === fn.length) {
    return fn(...args1);
  }
  return (...args2) => {
    const args = [...args1, ...args2];
    if (args.length >= fn.length) {
      return fn(...args);
    }
    return xhf.curry(fn)(...args);
  };
};
/* examples:
// 1  OK
var add3 = xhf.curry((a, b, c) => a + b + c);
console.log(add3(1,2,3)); // 6
console.log(add3(1,2)(3)); // 6
console.log(add3(1)(2)(3)); // 6

// 2  OK
// product3 :: Num -> Num -> Num -> Num
var product3 = (a, b, c) => a * b * c;
console.log([1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map(xhf.curry(product3)(7)(2)))
// [14, 28, 42, 56, 70, 84, 98, 112, 126, 140]
*/

