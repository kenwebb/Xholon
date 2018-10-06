<script implName="lang:BrowserJS:inline:"><![CDATA[
var treeWindow = window.open(null, 'Tree Window', 'height=300,width=200,resizable,scrollbars,status');
var treeDoc = treeWindow.document;
treeDoc.open();
treeDoc.write("<html><head>\
<!-- jQuery, and jQuery plugins -->\
<script type='text/javascript' src='../web/jquery.js'></script>\
<script type='text/javascript' src='../web/jquery.cookie.js'></script>\
<script type='text/javascript' src='../web/jquery.hotkeys.js'></script>\
<script type='text/javascript' src='../web/jquery.jstree.js'></script>\
<title>Xholon GUI</title>\
</head><body>\
<h3>Xholon GUI</h3>\
<div id='divOne'></div>\
</body></html>");
treeDoc.close();
]]></script>