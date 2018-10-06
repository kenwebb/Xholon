<script implName="lang:BrowserJS:inline:"><![CDATA[
// jsTree with jQuery; this works by creating nodes within the divOne node
$(function () {
  $("#divOne").jstree({ 
    "json_data" : {
      "data" : [
        { 
          "data" : "A node", 
          "children" : [ "Child 1", "Child 2" ]
        },
        { 
          "attr" : { "id" : "li.node.id" }, 
          "data" : { 
            "title" : "Long format demo", 
            "attr" : { "href" : "#" } 
          } 
        }
      ]
    },
    "themes" : {
      "theme" : "classic",
      "dots" : true,
      "icons" : true
    },
    "plugins" : [ "themes", "json_data" ]
  });
});
nil;
]]></script>