config_gwt folder
July 16, 2013
Ken Webb
=================
This folder includes GWT versions of Xholon .xml files.
The GWT version of Xholon does not support XInclude (xi:include) processing.
GWT does not support Java Swing.
The GWT version of Xholon does not support many of the Xholon viewers.

Xholon XML chunks:
-----------------
- this is a list of XML chunks that Xholon can independently load using GWT RequestBuilder
- a Xholon chunk is loaded by calling a public method on AbstractXml2Xholon
- IH = inheritance hierarchy
- CSH = composite structure hierarchy
- CD = class details
Control             CSH [config/_common/_control/Control_CompositeHierarchy.xml]
default params      XHN [config/_common/_default_xhn.xml]
XholonClass root    IH  ???
XhMechanisms            [config/_common/XhMechanisms.xml]
XholonMechanism     IH  [config/_common/_mechanism/XholonMechanism.xml]
XholonMechanism     CD  [config/_common/_mechanism/XholonMechanismCd.xml]
XholonViewer        IH  [config/_common/_viewer/XholonViewer.xml] no content for now
XholonViewer        CD  [config/_common/_viewer/XholonViewerCd.xml] no content for now
XholonService       CSH [config/_common/_service/Service_CompositeStructureHierarchy.xml]
modules                 ???
app-specific        IH  [config/MYAPP/MYIH.xml]
app-specific        CSH [config/MYAPP/MYCSH.xml]
app-specific        CD  [config/MYAPP/MYCD.xml]
app-specific params XHN [config/MYAPP/MYXHN.xml]

To create a concatenated single file:
(1) use cat
cat *.xml > out1.xml
    and then edit the file, and add a single outermost tag
(2) or use the XIPr XSLT 2.0 stylesheeet at dret.net/projects/xipr/
    "an implementation of XML Inclusions (XInclude)"
    (I haven't tested this yet)

