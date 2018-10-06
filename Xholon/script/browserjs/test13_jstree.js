<script implName="lang:BrowserJS:inline:"><![CDATA[
// jsTree with jQuery; this works by creating two nodes within the divOne node
$(function () {
  $("#divOne").jstree({ 
    "core" : { "initially_open" : [ "root" ] },
    "html_data" : {
      "data" : "<li id='root'><a href='#'>Root node</a><ul><li><a href='#'>Child node</a></li></ul></li>"
    },
    "plugins" : [ "themes", "html_data" ]
  });
});
nil;
]]></script>