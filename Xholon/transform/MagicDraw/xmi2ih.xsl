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
	Transform XMI to InheritanceHierarchy.xml
	Author: Ken Webb
	Date:   October 12, 2006
	File:   xmi2ih.xsl
	Works with: MagicDraw 11.5
-->

<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:uml='http://schema.omg.org/spec/UML/2.0'
	xmlns:xmi='http://schema.omg.org/spec/XMI/2.1'
	xmlns:xi="http://www.w3.org/2001/XInclude">

<xsl:output method="xml" omit-xml-declaration="no" indent="yes"/>

<xsl:param name="author"/>
<xsl:param name="dateNow"/>
<xsl:param name="fileName"/>

<!-- TODO search more intelligently for root.
	But for now this should work in almost all cases. -->
<!--<xsl:variable name="rootEntity"
	select="/xmi:XMI/uml:Model/ownedMember[@xmi:type='uml:Class']"/>-->
<xsl:variable name="rootEntity"
	select="/xmi:XMI/uml:Model/ownedMember[@xmi:type='uml:Class']
		| /xmi:XMI/uml:Model/ownedMember[@xmi:type='uml:Component']"/>

<xsl:template match="xmi:XMI">
		<xsl:apply-templates select="uml:Model"/>
</xsl:template>

<xsl:template match="uml:Model">
	<xsl:comment>
		<xsl:text>&#10;&#9;</xsl:text>
		<xsl:value-of select="@name"/>
		<xsl:text> application - Inheritance Hierarchy&#10;</xsl:text>
		<xsl:text>&#9;Author: </xsl:text><xsl:value-of select="$author"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;Date:   </xsl:text><xsl:value-of select="$dateNow"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;File:   </xsl:text><xsl:value-of select="$fileName"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;Target: Xholon 0.5 http://www.primordion.com/Xholon&#10;</xsl:text>
		<!-- UML -->
		<xsl:text>&#9;UML: </xsl:text>
		<xsl:value-of select="/xmi:XMI/xmi:Documentation/@xmi:Exporter"/>
		<xsl:text> </xsl:text>
		<xsl:value-of select="/xmi:XMI/xmi:Documentation/@xmi:ExporterVersion"/>
		<xsl:text>&#10;</xsl:text>
		<!-- XMI -->
		<xsl:text>&#9;XMI: </xsl:text>
		<xsl:value-of select="/xmi:XMI/@xmi:version"/>
		<xsl:text>, </xsl:text>
		<xsl:value-of select="/xmi:XMI/@timestamp"/>
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
	<xsl:element name="{$rootEntity/@name}">
		<!-- generalization hierarchy -->
		<xsl:apply-templates select="/xmi:XMI/uml:Model//generalization[@general=$rootEntity/@xmi:id]"/>
		<!-- StateMachineEntity -->
		<xsl:comment> StateMachineEntity </xsl:comment>
		<xsl:element name="xi:include">
			<xsl:attribute name="href">_mechanism/StateMachineEntity.xml</xsl:attribute>
		</xsl:element>
		<!-- Port -->
		<xsl:comment> Port </xsl:comment>
		<xsl:element name="xi:include">
			<xsl:attribute name="href">_mechanism/Port.xml</xsl:attribute>
		</xsl:element>
		<!-- Xholon Viewers -->
		<xsl:comment> Xholon Viewers </xsl:comment>
		<xsl:element name="xi:include">
			<xsl:attribute name="href">_viewer/XholonViewer.xml</xsl:attribute>
		</xsl:element>
	</xsl:element>
</xsl:template>

<xsl:template match="generalization">
	<xsl:apply-templates select=".."/>
</xsl:template>

<xsl:template match="@xmi:id">
	<xsl:param name="idref" select="."/>
	<xsl:value-of select="$idref"/>
	<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedMember[$idref=@xmi:id]"/>
</xsl:template>

<xsl:template match="ownedMember">
	<xsl:element name="{@name}">
		<xsl:apply-templates select="/xmi:XMI/uml:Model//generalization[@general=current()/@xmi:id]"/>
	</xsl:element>
</xsl:template>

</xsl:stylesheet>