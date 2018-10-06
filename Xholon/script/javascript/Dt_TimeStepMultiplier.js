var root = xh.root(); //undefined
console.log(root.name()); //"chameleon_0"
root = root.first().first(); //XhChameleon_1_g$ ...
console.log(root.name()); //"block_44"
console.log(root.attrs()); //RoleName Val AllPorts
console.log(root.attr("dt")); //null
console.log(root.attr("timeStepMultiplier")); //null
// xh.attrs(root); // nothing useful
var app = xh.app(); //undefined
console.log(app.name()); //"Application"
//console.log(app.attrs()); // system stuff in an array
console.log(xh.param("TimeStepMultiplier")); // 1
console.log(xh.param("Dt")); // 1

xh.param("TimeStepMultiplier", "4");
console.log(xh.param("TimeStepMultiplier")); // 4
console.log(xh.param("Dt")); // 0.25

