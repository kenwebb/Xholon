<script implName="lang:javascript:inline:"><![CDATA[
/*
 * Write out a Xholon Text Tree.
 */
var cnode = contextNodeKey;
var app = applicationKey;
var textTreeService = app.getService("TextTreeViewerService");
if (textTreeService != null) {
  textTreeService.sendSyncMessage(-3998, cnode, app);
}
]]></script>
