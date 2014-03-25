<?xml version="1.0" encoding="UTF-8"?>
<!--
Xholon Runtime Framework - executes event-driven & dynamic applications
Copyright (C) 2005, 2006, 2007, 2008 Ken Webb

This library is free software; you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
-->

<!--
	Transform XMI to App<appName>.java file
	Author: Ken Webb
	Date:   October 11, 2006
	File:   xmi2ja.xsl
	Works with: Poseidon 3.2 and 4.2
-->

<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:UML = 'org.omg.xmi.namespace.UML'
	xmlns:UML2 = 'org.omg.xmi.namespace.UML2'>

<xsl:output method="text" omit-xml-declaration="yes" indent="no"/>

<xsl:param name="author"/>
<xsl:param name="dateNow"/>
<xsl:param name="fileName"/>

<xsl:template match="XMI">
		<xsl:apply-templates select="XMI.content"/>
</xsl:template>

<xsl:template match="XMI.content">
	<xsl:apply-templates select="UML:Model"/>
</xsl:template>

<xsl:template match="UML:Model">
	<!-- package -->
	<xsl:text>package org.primordion.xholon.xmiapps;&#10;&#10;</xsl:text>
	
	<!-- imports -->
	<xsl:text>import org.primordion.xholon.app.Application;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.XholonTime;&#10;&#10;</xsl:text>
	
	<!-- any additional imports -->
	<xsl:text>&#10;</xsl:text>

	<!-- comments at top of file -->
	<xsl:text>/**</xsl:text>
		<xsl:text>&#10;&#9;</xsl:text>
		<xsl:value-of select="@name"/>
		<xsl:text> application - Inheritance Hierarchy&#10;</xsl:text>
		<xsl:text>&#9;&lt;p&gt;Author: </xsl:text><xsl:value-of select="$author"/><xsl:text>&lt;/p&gt;&#10;</xsl:text>
		<xsl:text>&#9;&lt;p&gt;Date:   </xsl:text><xsl:value-of select="$dateNow"/><xsl:text>&lt;/p&gt;&#10;</xsl:text>
		<xsl:text>&#9;&lt;p&gt;File:   </xsl:text><xsl:value-of select="$fileName"/><xsl:text>&lt;/p&gt;&#10;</xsl:text>
		<xsl:text>&#9;&lt;p&gt;Target: Xholon 0.4 http://www.primordion.com/Xholon&lt;/p&gt;&#10;</xsl:text>
		<!-- UML -->
		<xsl:text>&#9;&lt;p&gt;UML: </xsl:text>
		<xsl:text>Poseidon 3.2 or 4.2</xsl:text>
		<xsl:text>&lt;/p&gt;&#10;</xsl:text>
		<!-- XMI -->
		<xsl:text>&#9;&lt;p&gt;XMI: </xsl:text>
		<xsl:value-of select="/XMI/@xmi.version"/>
		<xsl:text>, </xsl:text>
		<xsl:value-of select="/XMI/@timestamp"/>
		<xsl:text>, </xsl:text>
		<xsl:value-of select="/XMI/XMI.header/XMI.documentation/XMI.exporter"/>
		<xsl:text>&lt;/p&gt;&#10;</xsl:text>
		<!-- XSLT -->
		<xsl:text>&#9;&lt;p&gt;XSLT: </xsl:text>
		<xsl:value-of select="system-property('xsl:version')"/>
		<xsl:text>, </xsl:text>
		<xsl:value-of select="system-property('xsl:vendor')"/>
		<xsl:text>, </xsl:text>
		<xsl:value-of select="system-property('xsl:vendor-url')"/>
		<xsl:text>&lt;/p&gt;&#10;</xsl:text>
	<xsl:text>*/&#10;</xsl:text>
	
	<xsl:text>public class App</xsl:text>
	<xsl:value-of select="@name"></xsl:value-of>
	<xsl:text> extends Application {&#10;&#10;</xsl:text>
	
	<!-- constructor -->
	<xsl:text>public App</xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text>() {super();}&#10;&#10;</xsl:text>

	<!-- Application-specific parameters - getters/setters -->
	<xsl:text>// Setters and Getters&#10;</xsl:text>

	<!-- step -->
	<xsl:text>protected void step()&#10;</xsl:text>
	<xsl:text>{&#10;</xsl:text>
	<xsl:text>	if (getUseDataPlotter()) {&#10;</xsl:text>
	<xsl:text>		chartViewer.capture(getTimeStep());&#10;</xsl:text>
	<xsl:text>	}&#10;</xsl:text>
	<xsl:text>    root.act();&#10;</xsl:text>
	<xsl:text>    XholonTime.sleep( getTimeStepInterval() );&#10;</xsl:text>
	<xsl:text>}&#10;&#10;</xsl:text>
	
	<!-- main -->
	<xsl:text>public static void main(String[] args) {&#10;</xsl:text>
	<xsl:text>    appMain(args, "org.primordion.xholon.xmiapps.App</xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text>",&#10;</xsl:text>
	<xsl:text>        "./config/xmiapps/</xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text>/</xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text>_xhn.xml");&#10;</xsl:text>
	<xsl:text>}&#10;&#10;</xsl:text>
	
	<!-- end class -->
	<xsl:text>}</xsl:text>

</xsl:template>

</xsl:stylesheet>
