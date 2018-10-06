// Add a new search engine to the Xholon SearchEngineService.
//<script><![CDATA[
// Add a new search engine to the Xholon SearchEngineService.
(function (seName, seUrl) {
  var sesMap = $wnd.xh.service('SearchEngineService').first();
  var xmlStr = '<Attribute_String roleName="' + seName + '">' + seUrl + '</Attribute_String>';
  sesMap.append(xmlStr);
  //$wnd.xh.service('SearchEngineService').first().append('<Attribute_String roleName="' + seName + '">' + seUrl + '</Attribute_String>');
}("lotr.wikia", "http://lotr.wikia.com/wiki/"))
//]]></script>