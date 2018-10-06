<ShowServiceDetails implName="org.primordion.xholon.base.Behavior_gwtjs" outTarget="outtab" searchName="RemoteNodeService"><![CDATA[
// Show the details of a specified Xholon service.
// For now, it only works correctly with RemoteNodeService.
// outTarget: "outtab" "consolelog"
var me, outstr, beh = {

postConfigure: function() {
  me = this.cnode;
  outstr = "";
  var service = $wnd.xh.service("ServiceLocatorService");
  while (service) {
    if (service.xhc().name() == me.searchName) {
      break;
    }
    service = service.next();
  }
  outstr += service.xhc().name() + "\n";
  var child = service.first();
  while (child) {
    outstr += child.name() + "\n";
    switch (child.xhc().name()) {
    case "XholonMap":
      var mapItem = child.first();
      while (mapItem) {
        outstr += " " + mapItem.role() + " " + mapItem.text() + "\n";
        mapItem = mapItem.next();
      }
      break;
    case "CrossApp":
    case "PostMessage":
    case "MessageChannel":
    case "WebRTC":
    case "PeerJS":
      if (child["subtrees"] && child["subtrees"]["TxRemoted"]) {
        var txRemoted = child["subtrees"]["TxRemoted"];
        outstr += " " + txRemoted.name() + "\n";
        var txrItem = txRemoted.first();
        while (txrItem) {
          outstr += "  " + txrItem.name() + " " + txrItem.uid() + "\n";
          txrItem = txrItem.next();
        }
      }
    default:
      break;
    }
    child = child.next();
  }
  switch (me.outTarget) {
  case "outtab":
    me.print(outstr);
    break;
  case "consolelog":
  default:
    $wnd.console.log(outstr);
    break;
  }
  me.remove();
}

}
//# sourceURL=ShowServiceDetails.js
]]></ShowServiceDetails>
