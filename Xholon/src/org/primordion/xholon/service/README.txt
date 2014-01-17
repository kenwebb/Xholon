Checklist - How to create a new service
---------------------------------------

Decide on a name for the new service.
Use this name in the various XML files, and in the Java packages and classes described below.
Example name: MyService

A. Create a new mechanism, or viewer mechanism.
   1. (optional) Add an entry to config/_common/XhMechanisms.xml
   2. (optional) Add a new .xml file in config/_common/_mechanism or config/_common/_viewer .
       This is the inheritance hierarchy of one or more Xholon classes that collaborate to provide the service.
   3. (optional) Add an entry to:
       config/_common/_mechanism/XholonMechanism.xml , or to
       config/_common/_viewer/XholonViewer.xml .
   4. Add an entry to the XholonService part of:
       config/_common/_mechanism/XholonMechanismCd.xml .

B. Create the service itself.
   1. Add an entry to config/_common/_mechanism/XholonService.xml .
   2. Add an entry to config/_common/_service/Service_CompositeStructureHierarchy.xml .
   3. Add a new Java class to org.primordion.xholon.service.
   4. (optional) create a new Java package in org.primordion.xholon.service, with one or more support classes.
   5. Add the name of the service to org.primordion.xholon.service.IXholonService .
   
   6. Add to TreeNodeFactoryNew.java
