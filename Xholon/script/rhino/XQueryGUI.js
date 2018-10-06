<script implName="lang:javascript:inline:"><![CDATA[
/*
 * Open a XQuery GUI.
 */
var cnode = contextNodeKey;
var app = applicationKey;
var service = app.getService("XholonHelperService");
if (service != null) {
  service.sendSyncMessage(-2010 , cnode, app);
}
]]></script>
