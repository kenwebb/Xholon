<!--
Circle prototype
source: Flanagan, p. 160 to 161

//usage (in Firebug):
var c = new xh.Circle(1.0);
c.r = 2.2;
var a = c.area();
var x = Math.exp(xh.Circle.PI);
var d = new xh.Circle(1.2);
var bigger = xh.Circle.max(c, d);
console.log(bigger);

//usage (pasted in as last child of Chameleon CSH node):
<script>
var c = new $wnd.xh.Circle(1.0);
c.r = 2.2;
var a = c.area();
var x = Math.exp($wnd.xh.Circle.PI);
var d = new $wnd.xh.Circle(1.2);
var bigger = $wnd.xh.Circle.max(c, d);
console.log(bigger);
</script>

//the following works when pasted in as last child of Chameleon CSH node:
-->
<script>

// constructor
$wnd.xh.Circle = function Circle(radius) {
  console.log("Circle constructor");
  this.r = radius;
}

// a class property; a property of the constructor function
$wnd.xh.Circle.PI = 3.14159;

// an instance method
$wnd.xh.Circle.prototype.area = function() {
  return $wnd.xh.Circle.PI * this.r * this.r;
}

// a class method
$wnd.xh.Circle.max = function(a, b) {
  if (a.r > b.r) {
    return a;
  }
  else {
    return b;
  }
}

</script>

