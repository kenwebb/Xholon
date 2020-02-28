/**
Generate SQL tables from the entire Xholon tree.
genSql01.js
Ken Webb
December 27, 2019

 */
var app = xh.app();
var objkSql = "INSERT INTO objk (id, nmbrng_id, label) VALUES\n";
var linkSql = "INSERT INTO link (src_id, src_nmbrng_id, trg_id, trg_nmbrng_id, arrow_label, arrow_index, arrow_xpath) VALUES\n";
// link (0, 'n', 1, 't', 'type', NULL, ''),

var writeNode = function(node) {
  var id = node.id();
  var nmbrng_id = node.numbering();
  
  var label = "";
  if (nmbrng_id == "t")  {
    label = node.name();
  }
  else {
    label = node.role();
    if (label == null) {
      label = "";
    }
  }
  
  objkSql += " (" + id + ", '" + nmbrng_id + "', '" + label + "'),\n";
  writeLinks(node, id, nmbrng_id);
  writeProps(node, id, nmbrng_id);
  var chnode = node.first();
  while (chnode) {
    writeNode(chnode);
    chnode = chnode.next();
  }
};

var writeLinks = function(node, src_id, src_nmbrng_id) {
  // write type
  writeType(node, src_id, src_nmbrng_id);
  
  // write parent
  writePrnt(node, src_id, src_nmbrng_id);
  
  // write other links
  // TODO
};

// link (0, 'n', 1, 't', 'type', NULL, ''),
var writeType = function(node, src_id, src_nmbrng_id) {
  var type = node.xhc();
  if (type) {
    var trg_id = type.id();
    var trg_nmbrng_id = type.numbering();
    var arrow_label = "type";
    //var arrow_index = NULL;
    //var arrow_xpath = NULL;
    linkSql += " (" + src_id + ", '" + src_nmbrng_id + "', '" + trg_id + "', '" + trg_nmbrng_id + "', '" + arrow_label  + "'" + ", NULL, ''" + "),\n";
  }
}

var writePrnt = function(node, src_id, src_nmbrng_id) {
  var prnt = node.parent();
  if (prnt) {
    var trg_id = prnt.id();
    var trg_nmbrng_id = prnt.numbering();
    var arrow_label = "prnt";
    //var arrow_index = NULL;
    //var arrow_xpath = NULL;
    linkSql += " (" + src_id + ", '" + src_nmbrng_id + "', '" + trg_id + "', '" + trg_nmbrng_id + "', '" + arrow_label  + "'" + ", NULL, ''" + "),\n";
  }
}

var writeLink = function(node, src_id, src_nmbrng_id) {
  // TODO
};

var writeProps = function(node, src_id, src_nmbrng_id) {
  // TODO
}

writeNode(app);
objkSql += ";";
linkSql += ";";
console.log(objkSql);
console.log(linkSql);

