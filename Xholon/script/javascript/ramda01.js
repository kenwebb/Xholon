// Ramda JavaScript library
// March 23, 2019
// http://127.0.0.1:8888/Xholon.html?app=HelloWorld&gui=clsc&jslib=ramda.min

const rava = R.clone(xh.avatar());
rava.name();
rava.action("param transcript false;param debug true");
rava.action("who");
rava.action("where");
rava.action("look");
rava.action("first");
rava.action("go 0");
rava.action("help");
rava.action("build Zebra"); // FIXED  TypeError: xhcEnt_0_g$ is null,  TreeNodeFactoryNew .getXholonClassNode() line 925
rava.action("appear"); // causes rava to have a parent node, to actually be part of the Xholon tree
R.toPairs(rava);
rava.action("build Hello"); // this is OK; Hello already exists in the IH

