
var v = new java.lang.Runnable() {
  run: function() { print('hello'); }
}
v.run();

/*
 * The following works to a limited extent. I would need to implement all the methods in IXholon.
 */
<script implName="lang:javascript:inline:">
var xh = new org.primordion.xholon.base.IXholon() {
  postConfigure: function() { print('pc '); },
  act: function() { print('act '); },
  getId: function() { return 13; },
  setId: function(id) {},
  setXhc: function(xhc) {},
  setPorts: function() {},
  getXhcName: function() {return 'name';}
}
print(xh.getId());
xh.postConfigure();
xh.act();
xh
</script>
