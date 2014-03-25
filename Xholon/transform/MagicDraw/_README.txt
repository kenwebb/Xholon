To transform a MagicDraw 11.5 model into an executable Xholon application
October 11, 2006
Ken Webb
-------------------------------------------------------------------------

Xholon applications can be automatically generated from UML models. The MagicDraw UML models contained in the transform/MagicDraw directory are examples that can be transformed into Xholon applications. This is especially useful if your application includes one or more complex state machines.

These instructions mention the xmi2xh.bat file that is specific to Windows platforms, and the xmi2xh shell file that is specific to Linux. If you are using another operating system, you will need to extract the simple commands from either of these files.

This directory (Eclipse folder) contains a number of XSL files that work together to convert UML models (.mdzip files) into the files required by the Xholon runtime framework.

You'll need an XSLT processor. Xholon uses the open-source xalan-j (version 2_7_0) available from:
    xml.apache.org/xalan-j

One way to do the transformations:
 * In Linux, open a terminal window. In Windows, open a DOS command prompt window.
 * cd to the Xholon/transform/MagicDraw or Xholon\transform\MagicDraw directory.
 * In Linux, start xmi2xh. In Windows, start xmi2xh.bat. Provide it with the type of action, and the name of the UML model file.
   for example:
       ./xmi2xh all TestFsm    (Linux)
       xmi2xh all TestFsm      (Windows)
       ./xmi2xh -h         (to get help in Linux)
       xmi2xh -h           (to get help in Windows)
 * The following five files will be generated in the config/xmiapps directory:
       TestFsm_ClassDetails.xml
       TestFsm_CompositeStructureHierarchy.xml
	     TestFsm_Information.xml
       TestFsm_InheritanceHierarchy.xml
       TestFsm_xhn.xml
 * The following three files will be generated in src org.primordion.xholon.xmiapps:
       AppTestFsm.java
       CeTestFsm.java
       XhTestFsm.java
 * Compile these, and then run the TestFsm application by running Xhn.java.

You can optionally view and edit the original model, including the UML diagrams, by downloading a trial version (Personal Edition or higher), or a free model Reader, from:
    www.magicdraw.com
Run MagicDraw, and load any of the .mdzip files, such as TestFsm.mdzip. Look especially at the state diagrams.

For more information, see the complete online tutorial "Xholon with MagicDraw HelloWorld Tutorial".
http://www.primordion.com/Xholon/doc/tutorial_helloworld_md.html

UML_Model             Root_Xholon
---------             -----------
__XholonTemplate__    TheSystem
Elevator              AnElevatorSystem
Fsm06ex1_Fsm          Membrane1
HelloWorld*           HelloWorldSystem
ProvidedRequiredTest  ClassA
Rcs_GP_FSM            GPaseSystem
Rcs_GP_FSM_Grid       GPaseSystemWithGrid
Rcs_GP_MM*            GPaseSystem
StopWatch             StopWatchSystem
TestFsm*              TestFsmSystem
