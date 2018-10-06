<script implName="lang:javascript:inline:"><![CDATA[
// Test using the JSON object. This works with the Oracle Java 7 JVM.
// http://stackoverflow.com/questions/10523916/android-javascript-rhino-json
// also see:
// http://stackoverflow.com/questions/11080037/java-7-rhino-1-7r3-support-for-commonjs-modules?rq=1

// this may be needed on earlier versions of the Oracle JVM?
//load("/your/path/json2.js");

//After that call your script can use json2 library.
var testStr = '{"test" : {"a": "aval", "b" : "bval"}}';
var jsonObj = JSON.parse(testStr);
var b = jsonObj.test.b;
println(b);
]]></script>
