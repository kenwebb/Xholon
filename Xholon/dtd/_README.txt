Document Type Definition (DTD)
March 29, 2006
------------------------------
This directory includes various DTD files that define the valid syntax in Xholon composite structure XML files.
The DTD files are not read by the Xholon tool itself.
They are intended to be used with XML editing tools such as XMLBuddy and XMLSpy.

Example:
-------
Download, install, and use the free version of XMLBuddy in Eclipse.
Create a new XML file in Eclipse. (File --> New --> File  test.xml in the dtd/examples folder)
Type the following as the first few lines of the new test.xml file:
	<?xml version="1.0" encoding="UTF-8" standalone="no"?>
	<!DOCTYPE StateMachine SYSTEM "../StateMachine_UML2.dtd">
	<StateMachine>
		
	</StateMachine>
In the blank line after <StateMachine>, type a  <  character.
XMLBuddy will present a menu of all currently valid tags as defined in the dtd file.
In this case the only valid tag is Region. Select this.
Continue to create the file by selecting from the lists of currently available tags,
and filling in the values of attributes.
XMLBuddy will continuously let you know if your file is valid.
This assisted process makes it very easy and quick to create an XML file that describes the composite structure of your system.

XML editing tools:
-----------------
XMLBuddy: xmlbuddy.com/     free version, and inexpensive professional version
XMLSpy:   www.altova.com/   free home version, and professional versions