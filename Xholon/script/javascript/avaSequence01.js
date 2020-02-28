/**
 * December 3, 2019
 * Construct a simple numeric sequence based on the set {0,1,2,3,...}
 * http://127.0.0.1:8888/Xholon.html?app=Chameleon&gui=none
 */
const ava = xh.avatar();
let index = 0;
ava.action("build <Animate\tselection='#xhgraph'/>;appear;build universe;enter universe;step;");
ava.action("build block role " + index + ";step;");
// repeat the following line any number of times
ava.action("takeclone " + index + ";set xpath(Avatar/block) role " + ++index + ";drop;step;");
// extras
ava.action("flip;get next role;");

