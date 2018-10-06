<script implName="lang:javascript:inline:"><![CDATA[
var app = applicationKey;
println('old time step interval: ' + app.getTimeStepInterval());
app.setTimeStepInterval(10);
println('new time step interval: ' + app.getTimeStepInterval());
]]></script>