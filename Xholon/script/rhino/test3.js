//<script implName="lang:javascript:inline:"><![CDATA[
// Examples from www.w3schools.com

// 1
personObj=new Object();
personObj.firstname="John";
personObj.lastname="Doe";
personObj.age=50;
personObj.eyecolor="blue";
println(personObj.firstname + " is " + personObj.age + " years old.");

// 2
var d = new Date();
theDay=d.getDay();
switch (theDay)
{
case 5:
  println("<b>Finally Friday</b>");
  break;
case 6:
  println("<b>Super Saturday</b>");
  break;
case 0:
  println("<b>Sleepy Sunday</b>");
  break;
case 4:
  println("<b>It's only Thursday.</b>");
  break;
default:
  println("<b>I'm really looking forward to this weekend!</b>");
}
//]]></script>
