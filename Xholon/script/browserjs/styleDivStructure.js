<script implName="lang:BrowserJS:inline:"><![CDATA[
// Style the div structure of the current page in a web browser.
(function(){
var css =
  'div#appControls,div#usercontent div,div#game{float:left;border:1px solid #228B22;padding:1px;margin:1px;background-color:#D3E8D3;font-size:80%}'
  + 'div:after{content: attr(class);}';
var style=document.createElement('style');
style.setAttribute('id','bestiaryStyle');
style.setAttribute('type','text/css');
var textNode = document.createTextNode(css);
if(style.styleSheet) {
  style.styleSheet.cssText = textNode.nodeValue;
}
else {
  style.appendChild(textNode);
}
document.getElementsByTagName('head')[0].appendChild(style);
})();
]]></script>
