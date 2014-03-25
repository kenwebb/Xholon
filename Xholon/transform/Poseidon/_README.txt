To transform a Poseidon 3.2 or 4.2 XMI file to Xholon format
October 1, 2007
Ken Webb
------------------------------------------------------------

This document describes work in progress. The goal is to produce Xholon applications automatically from UML models. Currently, selected portions of a Poseidon UML model can be transformed into part of a Xholon application. This is especially useful if your application includes one or more complex state machines.

These instructions mention the xmi2xh.bat file that is specific to Windows platforms, and the xmi2xh shell file that is specific to Linux. If you are using another operating system, you will need to extract the simple commands from either of these files.

Watch.zuml contains a UML model created using the Poseidon UML modeling tool (version 3.2/4.2/6.0). This directory (Eclipse folder) contains a number of XSL files that convert Watch.zuml into files required by the Xholon runtime framework.

You'll need an XSLT processor. I'm using the open-source xalan-j (version 2_7_0) available from:
    xml.apache.org/xalan-j

To do the transformations:
 * In Linux, open a terminal window. In Windows, open a DOS command prompt window.
 * cd to the Xholon/transform/Poseidon or Xholon\transform\Poseidon directory.
 * In Linux, start xmi2xh. In Windows, start xmi2xh.bat. Provide it with the type of action, and the name of the UML model file.
   for example:
       ./xmi2xh all Watch   (Linux)
       xmi2xh all Watch     (Windows)
	   ./xmi2xh -h         (to get help in Linux)
       xmi2xh -h           (to get help in Windows)
 * The following five files will be generated in the config/xmiapps directory:
       Watch_ClassDetails.xml
       Watch_CompositeStructureHierarchy.xml
	   Watch_Information.xml
       Watch_InheritanceHierarchy.xml
       Watch_xhn.xml
 * The following three files will be generated in src org.primordion.xholon.xmiapps:
		AppWatch.java
		CeWatch.java
		XhWatch.java
 * Compile and run the digital watch application by running Xhn.java.

You can optionally view and edit the original model, including the UML diagrams, by downloading the free Community version of Poseidon from:
    www.gentleware.com
Run Poseidon, and load the Watch.zuml file. Look especially at the state diagram.

UML_Model             Root_Xholon
---------             -----------
Watch                 WatchUserSystem
