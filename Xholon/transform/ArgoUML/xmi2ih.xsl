<?xml version="1.0" encoding="UTF-8"?>
<!--
Xholon Runtime Framework - executes event-driven & dynamic applications
Copyright (C) 2007, 2008 Ken Webb

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
	Date:   September 11, 2007
	File:   xmi2ih.xsl
	Works with: ArgoUML 0.24
-->

<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:UML = 'org.omg.xmi.namespace.UML'
	xmlns:UML2 = 'org.omg.xmi.namespace.UML2'
	xmlns:xi="http://www.w3.org/2001/XInclude">

<xsl:output method="xml" omit-xml-declaration="no" indent="yes"/>

<xsl:param name="author"/>
<xsl:param name="dateNow"/>
<xsl:param name="fileName"/>

<!-- TODO search more intelligently for root.
	But for now this should work in almost all cases. -->
<xsl:variable name="rootEntity"
	select="/XMI/XMI.content/UML:Model/UML:Namespace.ownedElement/UML:Package/UML:Namespace.ownedElement/UML:Class[@name='XholonClass']"/>

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
		<xsl:text>&#9;Target: Xholon 0.7 http://www.primordion.com/Xholon&#10;</xsl:text>
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
	<xsl:element name="{$rootEntity/@name}">
		<xsl:apply-templates select="UML:Namespace.ownedElement"/>
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

<xsl:template match="UML:Namespace.ownedElement">
		<xsl:apply-templates select="UML:Generalization[UML:Generalization.parent/UML:Class/@xmi.idref=$rootEntity/@xmi.id]">
			<!--<xsl:with-param name="parentEntity">
				<xsl:value-of select="$rootEntity"/>
			</xsl:with-param>-->
		</xsl:apply-templates>
</xsl:template>

<xsl:template match="UML:Generalization">
	<!--<xsl:param name="parentEntity"/>-->
	<!--<xsl:if test="UML:Generalization.parent/UML:Class/@xmi.idref=$parentEntity/@xmi.id">-->
		<xsl:apply-templates select="UML:Generalization.child"/>
	<!--</xsl:if>-->
</xsl:template>

<xsl:template match="UML:Generalization.child">
	<xsl:apply-templates select="UML:Class/@xmi.idref"/>
</xsl:template>

<xsl:template match="@xmi.idref">
	<xsl:param name="idref" select="."/>
	<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML:Class[$idref=@xmi.id]"/>
</xsl:template>

<xsl:template match="UML:Class">
	<!-- ignore StateMachineEntity and its subclasses -->
	<xsl:if test="not(@name='StateMachineEntity')">
	<xsl:element name="{@name}">
		<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML:Generalization[@xmi.id][UML:Generalization.parent/UML:Class/@xmi.idref=current()/@xmi.id]">
			<!--<xsl:with-param name="parentEntity">
				<xsl:value-of select="."/>
			</xsl:with-param>-->
		</xsl:apply-templates>
	</xsl:element>
	</xsl:if>
</xsl:template>

</xsl:stylesheet>