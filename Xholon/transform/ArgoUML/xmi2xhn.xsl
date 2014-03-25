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
	Transform XMI to _xhn.xml
	Author: Ken Webb
	Date:   September 11, 2007
	File:   xmi2xhn.xsl
	Works with: ArgoUML 0.24
-->

<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:UML = 'org.omg.xmi.namespace.UML'
	xmlns:UML2 = 'org.omg.xmi.namespace.UML2'>

<xsl:output method="xml" omit-xml-declaration="no" indent="yes"/>

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
	<xsl:comment>
		<xsl:text>&#10;&#9;</xsl:text>
		<xsl:value-of select="@name"/>
		<xsl:text> application - Inheritance Hierarchy&#10;</xsl:text>
		<xsl:text>&#9;Author: </xsl:text><xsl:value-of select="$author"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;Date:   </xsl:text><xsl:value-of select="$dateNow"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;File:   </xsl:text><xsl:value-of select="$fileName"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;Target: Xholon 0.4 http://www.primordion.com/Xholon&#10;</xsl:text>
		<!-- UML -->
		<xsl:text>&#9;UML: </xsl:text>
		<xsl:text>ArgoUML 0.24</xsl:text>
		<xsl:text>&#10;</xsl:text>
		<!-- XMI -->
		<xsl:text>&#9;XMI: </xsl:text>
		<xsl:value-of select="/XMI/@xmi.version"/>
		<xsl:text>, </xsl:text>
		<xsl:value-of select="/XMI/@timestamp"/>
		<xsl:text>, </xsl:text>
		<xsl:value-of select="/XMI/XMI.header/XMI.documentation/XMI.exporter"/>
		<xsl:text>&#10;</xsl:text>
		<!-- XSLT -->
		<xsl:text>&#9;XSLT: </xsl:text>
		<xsl:value-of select="system-property('xsl:version')"/>
		<xsl:text>, </xsl:text>
		<xsl:value-of select="system-property('xsl:vendor')"/>
		<xsl:text>, </xsl:text>
		<xsl:value-of select="system-property('xsl:vendor-url')"/>
		<xsl:text>&#10;</xsl:text>
	</xsl:comment>
	<params>
		<xsl:element name="param">
			<xsl:attribute name="name">ShowParams</xsl:attribute>
			<xsl:attribute name="value">false</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">ModelName</xsl:attribute>
			<xsl:attribute name="value"><xsl:value-of select="@name"/></xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">AppM</xsl:attribute>
			<xsl:attribute name="value">true</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">InfoM</xsl:attribute>
			<xsl:attribute name="value">false</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">ErrorM</xsl:attribute>
			<xsl:attribute name="value">true</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">MaxProcessLoops</xsl:attribute>
			<xsl:attribute name="value">10</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">TimeStepInterval</xsl:attribute>
			<xsl:attribute name="value">10</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">SizeMessageQueue</xsl:attribute>
			<xsl:attribute name="value">10</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">InheritanceHierarchyFile</xsl:attribute>
			<xsl:attribute name="value">
				<xsl:text>./config/xmiappsArgoUML/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>_InheritanceHierarchy.xml</xsl:text>
			</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">CompositeStructureHierarchyFile</xsl:attribute>
			<xsl:attribute name="value">
				<xsl:text>./config/xmiappsArgoUML/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>_CompositeStructureHierarchy.xml</xsl:text>
			</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">ClassDetailsFile</xsl:attribute>
			<xsl:attribute name="value">
				<xsl:text>./config/xmiappsArgoUML/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>_ClassDetails.xml</xsl:text>
			</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">InformationFile</xsl:attribute>
			<xsl:attribute name="value">
				<xsl:text>./config/xmiappsArgoUML/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>_Information.xml</xsl:text>
			</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">JavaClassName</xsl:attribute>
			<xsl:attribute name="value">
				<xsl:text>org.primordion.xholon.xmiappsArgoUML.App</xsl:text>
				<xsl:value-of select="@name"/>
			</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">JavaXhClassName</xsl:attribute>
			<xsl:attribute name="value">
				<xsl:text>org.primordion.xholon.xmiappsArgoUML.Xh</xsl:text>
				<xsl:value-of select="@name"/>
			</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">RandomNumberSeed</xsl:attribute>
			<xsl:attribute name="value">1234</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">MaxPorts</xsl:attribute>
			<xsl:attribute name="value">5</xsl:attribute>
		</xsl:element>
		<xsl:comment> enable/disable all viewers </xsl:comment>
		<xsl:element name="param">
			<xsl:attribute name="name">UseDataPlotter</xsl:attribute>
			<xsl:attribute name="value">false</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">UseGraphicalTreeViewer</xsl:attribute>
			<xsl:attribute name="value">false</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">UseGraphicalNetworkViewer</xsl:attribute>
			<xsl:attribute name="value">false</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">UseGridViewer</xsl:attribute>
			<xsl:attribute name="value">false</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">UseInteractions</xsl:attribute>
			<xsl:attribute name="value">false</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">UseVrml</xsl:attribute>
			<xsl:attribute name="value">false</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">UseTextTree</xsl:attribute>
			<xsl:attribute name="value">false</xsl:attribute>
		</xsl:element>
		<xsl:element name="param">
			<xsl:attribute name="name">SaveSnapshots</xsl:attribute>
			<xsl:attribute name="value">false</xsl:attribute>
		</xsl:element>
	</params>
</xsl:template>

</xsl:stylesheet>