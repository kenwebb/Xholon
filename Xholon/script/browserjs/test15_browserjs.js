<script implName="lang:BrowserJS:inline:"><![CDATA[
  // Step 1: add a script with JS content to a div in the HTML head
  // this works in Firefox, using jQuery
  $('div#divHeadOne').html("<script type='text/javascript'>function square(x) { return x*x; }</script>");
  nil;
]]></script>

<script implName="lang:BrowserJS:inline:"><![CDATA[
  // Step 2: invoke the new JS content; works
  alert(square(2));
]]></script>

<script implName="lang:BrowserJS:inline:"><![CDATA[
  // get and display the contents of divHeadOne, using jQuery; this works, but it returns a blank value ?
  alert($('div#divHeadOne').html());
]]></script>