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
	Transform XMI to CompositeStructureHierarchy.xml
	Author: Ken Webb
	Date:   September 11, 2007
	File:   xmi2csh.xsl
	Works with: ArgoUML 0.24
	Note: incomplete
-->

<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:UML = 'org.omg.xmi.namespace.UML'
	xmlns:UML2 = 'org.omg.xmi.namespace.UML2'>

<xsl:output method="xml" omit-xml-declaration="no" indent="yes"/>

<xsl:param name="rootEnt"/>
<xsl:param name="author"/>
<xsl:param name="dateNow"/>
<xsl:param name="fileName"/>

<xsl:variable name="rootEntity"
	select="/XMI/XMI.content/UML:Model//UML:Class[@xmi.id][@name=$rootEnt]"/>

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
		<xsl:text> application - Composite Structure Hierarchy&#10;</xsl:text>
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
		<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML:AssociationEnd.participant[UML:Class/@xmi.idref=$rootEntity/@xmi.id]">
			<!--<xsl:with-param name="parentEntity">
				<xsl:value-of select="$rootEntity"/>
			</xsl:with-param>-->
		</xsl:apply-templates>
	</xsl:element>
</xsl:template>

<xsl:template match="UML:AssociationEnd.participant">
	<!--<xsl:param name="parentEntity"/>-->
	<!--<xsl:if test="UML:Class/@xmi.idref=$parentEntity/@xmi.id">-->
		<xsl:apply-templates select=".."/>
	<!--</xsl:if>-->
</xsl:template>

<xsl:template match="UML:AssociationEnd">
	<xsl:if test="@aggregation='composite'">
		<xsl:apply-templates select=".."/>
	</xsl:if>
</xsl:template>

<xsl:template match="UML:Association.connection">
	<xsl:apply-templates
		select="UML:AssociationEnd[@aggregation='none']/UML:AssociationEnd.participant/UML:Class/@xmi.idref"/>
</xsl:template>

<xsl:template match="@xmi.idref">
	<xsl:param name="idref" select="."/>
	<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML:Class[$idref=@xmi.id]"/>
	<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML2:Transition[$idref=@xmi.id]"/>
</xsl:template>

<!-- create multiple instances from tagged values; ex: InjectButton -->
<xsl:template match="UML:Class[UML:Classifier.feature/UML:Attribute/@name='roleName']">
	<xsl:apply-templates select="UML:Classifier.feature/UML:Attribute/UML:ModelElement.taggedValue/UML:TaggedValue"/>
	<xsl:apply-templates select="UML:Namespace.ownedElement/UML:StateMachine"/>
	<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML:AssociationEnd.participant[UML:Class/@xmi.idref=current()/@xmi.id]">
		<!--<xsl:with-param name="parentEntity">
			<xsl:value-of select="."/>
		</xsl:with-param>-->
	</xsl:apply-templates>
</xsl:template>

<xsl:template match="UML:TaggedValue">
	<xsl:element name="{../../../../@name}">
		<xsl:attribute name="{../../@name}"> <!-- name of the UML:Attribute -->
			<xsl:value-of select="UML:TaggedValue.dataValue"/>
		</xsl:attribute>
	</xsl:element>
</xsl:template>

<xsl:template match="UML:Class">
	<xsl:element name="{@name}">
		<xsl:apply-templates select="UML:Namespace.ownedElement/UML:StateMachine"/>
		<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML:AssociationEnd.participant[UML:Class/@xmi.idref=current()/@xmi.id]">
			<!--<xsl:with-param name="parentEntity">
				<xsl:value-of select="."/>
			</xsl:with-param>-->
		</xsl:apply-templates>
	</xsl:element>
</xsl:template>

<xsl:template match="UML:StateMachine">
	<xsl:element name="StateMachine">
		<xsl:apply-templates select="UML:StateMachine.top"/>
	</xsl:element>
</xsl:template>

<xsl:template match="UML:StateMachine.top">
	<xsl:element name="Region">
		<xsl:apply-templates select="UML:CompositeState"/>
	</xsl:element>
</xsl:template>

<xsl:template match="UML:CompositeState.subvertex">
	<xsl:element name="Region">
		<xsl:apply-templates select="UML:Pseudostate"/>
		<xsl:apply-templates select="UML:CompositeState"/>
		<xsl:apply-templates select="UML:SimpleState"/>
	</xsl:element>
</xsl:template>

<xsl:template match="UML:CompositeState">
	<xsl:apply-templates select="UML:StateVertex.outgoing/UML:Transition"/>
	<xsl:element name="State">
		<xsl:attribute name="uid">
			<!-- mark as a hex number by preceeding with 0x -->
			<xsl:value-of select="concat('0x',substring(@xmi.id,53))"/>
		</xsl:attribute>
		<xsl:attribute name="roleName">
			<xsl:value-of select="@name"/>
		</xsl:attribute>
		<xsl:apply-templates select="UML:CompositeState.subvertex"/>
	</xsl:element>
</xsl:template>

<xsl:template match="UML:SimpleState">
	<xsl:apply-templates select="UML:StateVertex.outgoing/UML:Transition"/>
	<xsl:element name="State">
		<xsl:attribute name="uid">
			<!-- mark as a hex number by preceeding with 0x -->
			<xsl:value-of select="concat('0x',substring(@xmi.id,53))"/>
		</xsl:attribute>
		<xsl:attribute name="roleName">
			<xsl:value-of select="@name"/>
		</xsl:attribute>
	</xsl:element>
</xsl:template>

<xsl:template match="UML:Pseudostate">
	<xsl:apply-templates select="UML:StateVertex.outgoing/UML:Transition"/>
	<xsl:choose>
		<xsl:when test="@kind='initial'">
			<xsl:element name="PseudostateInitial">
				<xsl:attribute name="uid">
					<!-- mark as a hex number by preceeding with 0x -->
					<xsl:value-of select="concat('0x',substring(@xmi.id,53))"/>
				</xsl:attribute>
				<xsl:attribute name="roleName">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
			</xsl:element>
		</xsl:when>
		<xsl:when test="@kind='choice'">
			<xsl:element name="PseudostateChoice">
				<xsl:attribute name="uid">
					<!-- mark as a hex number by preceeding with 0x -->
					<xsl:value-of select="concat('0x',substring(@xmi.id,53))"/>
				</xsl:attribute>
				<xsl:attribute name="roleName">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
			</xsl:element>
		</xsl:when>
		<!-- add other pseudostate kinds -->
		<xsl:otherwise>
			<xsl:element name="Pseudostate"/> <!-- this shouldn't be allowed to happen -->
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="UML:Transition">
	<xsl:choose>
		<xsl:when test="@xmi.idref">
			<xsl:apply-templates select="//UML:StateMachine.transitions/UML:Transition[@xmi.id=current()/@xmi.idref]"/>
		</xsl:when>
		<xsl:when test="@xmi.id">
			<xsl:element name="Transition">
				<xsl:attribute name="uid">
					<!-- mark as a hex number by preceeding with 0x -->
					<xsl:value-of select="concat('0x',substring(@xmi.id,53))"/>
				</xsl:attribute>
				<xsl:attribute name="roleName">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
				<xsl:apply-templates select="UML:Transition.trigger"/>
				<xsl:apply-templates select="UML:Transition.effect/UML2:Activity"/>
				<xsl:apply-templates select="UML:Transition.effect/UML2:OpaqueBehavior"/>
			</xsl:element>
		</xsl:when>
	</xsl:choose>
</xsl:template>

<xsl:template match="UML:Transition.trigger">
	<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML2:BehavioredClassifier.ownedTrigger">
		<xsl:with-param name="idref" select="UML2:CallTrigger/@xmi.idref"/>
	</xsl:apply-templates>
</xsl:template>

<xsl:template match="UML2:BehavioredClassifier.ownedTrigger">
	<xsl:param name="idref"/>
	<xsl:for-each select="UML2:CallTrigger">
		<xsl:if test="@xmi.id=$idref">
			<xsl:element name="Trigger">
				<xsl:attribute name="roleName">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
			</xsl:element>
		</xsl:if>
	</xsl:for-each>
</xsl:template>

<xsl:template match="UML2:Activity">
	<xsl:element name="Activity">
		<xsl:attribute name="uid">
			<xsl:value-of select="concat('0x',substring(@xmi.id,53))"/>
		</xsl:attribute>
	</xsl:element>
</xsl:template>

<xsl:template match="UML2:OpaqueBehavior">
	<xsl:element name="Activity">
		<xsl:attribute name="uid">
			<xsl:value-of select="concat('0x',substring(@xmi.id,53))"/>
		</xsl:attribute>
	</xsl:element>
</xsl:template>

</xsl:stylesheet>