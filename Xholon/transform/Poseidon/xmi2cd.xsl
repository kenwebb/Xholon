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
	Transform XMI to ClassDetails.xml
	Author: Ken Webb
	Date:   October 11, 2006
	File:   xmi2cd.xsl
	Works with: Poseidon 3.2 and 4.2
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
		<xsl:text> application - Class Details&#10;</xsl:text>
		<xsl:text>&#9;Author: </xsl:text><xsl:value-of select="$author"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;Date:   </xsl:text><xsl:value-of select="$dateNow"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;File:   </xsl:text><xsl:value-of select="$fileName"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;Target: Xholon 0.4 http://www.primordion.com/Xholon&#10;</xsl:text>
		<!-- UML -->
		<xsl:text>&#9;UML: </xsl:text>
		<xsl:text>Poseidon 3.2 or 4.2</xsl:text>
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
	<xsl:element name="xholonClassDetails">
	<xsl:for-each select="/XMI/XMI.content/UML:Model//UML:Class[@xmi.id]">
		<xsl:if test="/XMI/XMI.content/UML:Model//UML:Association/UML:Association.connection[count(UML:AssociationEnd[@aggregation='none'])=2]/UML:AssociationEnd[@name]/UML:AssociationEnd.participant/UML:Class[@xmi.idref=current()/@xmi.id]">
			<xsl:element name="{@name}">
				<xsl:attribute name="xhType">
					<xsl:text>XhtypePureActiveObject</xsl:text>
				</xsl:attribute>
				<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML:Association/UML:Association.connection[count(UML:AssociationEnd[@aggregation='none'])=2]/UML:AssociationEnd[@name]/UML:AssociationEnd.participant/UML:Class[@xmi.idref=current()/@xmi.id]/../../.."/>
			</xsl:element>
		</xsl:if>
	</xsl:for-each>
	
	<!-- if there are state machine entities, run-time needs to know about this -->
	<!-- States -->
	<xsl:element name="State">
		<xsl:attribute name="xhType">
			<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
		</xsl:attribute>
		<xsl:attribute name="implName">
			<xsl:text>org.primordion.xholon.base.ObservableStateMachineEntity</xsl:text>
		</xsl:attribute>
		<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML2:State"/>
	</xsl:element>
	<!-- Pseudostates -->
	<xsl:element name="PseudostateInitial">
		<xsl:attribute name="xhType">
			<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
		</xsl:attribute>
		<xsl:attribute name="implName">
			<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
		</xsl:attribute>
		<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML2:Pseudostate[@kind='initial']"/>
	</xsl:element>
	<!-- Transitions -->
	<xsl:element name="Transition">
		<xsl:attribute name="xhType">
			<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
		</xsl:attribute>
		<xsl:attribute name="implName">
			<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
		</xsl:attribute>
		<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML2:Transition[@xmi.id]"/>
	</xsl:element>
	<!-- StateMachineEntity -->
	<xsl:element name="StateMachineEntity">
		<xsl:attribute name="xhType">
			<xsl:text>XhtypeStateMachineEntity</xsl:text>
		</xsl:attribute>
		<xsl:attribute name="implName">
			<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
		</xsl:attribute>
	</xsl:element>

	<!-- run-time needs to know the name and xhtype of root in the class inheritance hierarchy -->
	<xsl:element name="{/XMI/XMI.content/UML:Model/UML:Namespace.ownedElement/UML:Class/@name}">
		<xsl:attribute name="xhType">
			<xsl:text>XhtypePureContainer</xsl:text>
		</xsl:attribute>
	</xsl:element>
	</xsl:element>
</xsl:template>

<xsl:template match="UML:Association.connection">
	<!-- fill in the XPath location path -->
	<xsl:choose>
		<!-- when model contains complete location path as a tagged value.
			 - may be multiple alternatives -->
		<xsl:when test="UML:AssociationEnd/UML:ModelElement.taggedValue">
			<xsl:for-each select="UML:AssociationEnd/UML:ModelElement.taggedValue/UML:TaggedValue">
				<xsl:element name="port">
				<xsl:attribute name="name">
					<xsl:text>port</xsl:text>
				</xsl:attribute>
				<xsl:attribute name="index">
					<xsl:value-of select="../../@name"/>
				</xsl:attribute>
				<xsl:attribute name="connector">
					<xsl:text>#xpointer(</xsl:text>
					<xsl:value-of select="UML:TaggedValue.dataValue"/>
					<xsl:text>)</xsl:text>
				</xsl:attribute>
				</xsl:element>
			</xsl:for-each>
		</xsl:when>
		<!-- otherwise construct default location path -->
		<xsl:otherwise>
			<xsl:element name="port">
				<xsl:attribute name="name">
					<xsl:text>port</xsl:text>
				</xsl:attribute>
				<xsl:attribute name="index">
					<xsl:value-of select="UML:AssociationEnd/@name"/>
				</xsl:attribute>
				<xsl:attribute name="connector">
					<xsl:text>#xpointer(</xsl:text>
					<xsl:text>ancestor::</xsl:text>
					<xsl:value-of select="$rootEntity/@name"/>
					<xsl:text>/descendant::</xsl:text>
					<xsl:apply-templates select="UML:AssociationEnd[not(@name)]/UML:AssociationEnd.participant"/>
					<xsl:text>)</xsl:text>
				</xsl:attribute>
				</xsl:element>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="UML:AssociationEnd.participant">
	<xsl:param name="idref" select="UML:Class/@xmi.idref"/>
	<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML:Class[@xmi.id=$idref]"/>
</xsl:template>

<xsl:template match="UML:Class">
	<xsl:value-of select="@name"/>
</xsl:template>

<xsl:template match="UML2:State">
	<xsl:apply-templates select="UML2:Vertex.outgoing"/>
</xsl:template>

<xsl:template match="UML2:Pseudostate[@kind='initial']">
	<xsl:element name="port">
		<xsl:attribute name="name">
			<xsl:text>cnpt</xsl:text>
		</xsl:attribute>
		<xsl:attribute name="index">
			<xsl:text>CNPT_OUTGOING1</xsl:text>
		</xsl:attribute>
		<xsl:attribute name="connector">
			<xsl:text>#xpointer(</xsl:text>
			<xsl:text>.[@uid=&apos;0x</xsl:text>
			<xsl:value-of select="substring(@xmi.id,23)"/> <!-- uid of originating state -->
			<xsl:text>&apos;]/ancestor::StateMachine/descendant::Transition[@uid=&apos;0x</xsl:text>
			<xsl:value-of select="substring(UML2:Vertex.outgoing/UML2:Transition/@xmi.idref,23)"/> <!-- uid of outgoing transition -->
			<xsl:text>&apos;])</xsl:text>
		</xsl:attribute>
	</xsl:element>
</xsl:template>

<xsl:template match="UML2:Vertex.outgoing">
	<!-- <xsl:param name="counter" select="count(UML2:Transition)"/> -->
	<xsl:for-each select="UML2:Transition">
		<xsl:element name="port">
		<xsl:attribute name="name">
			<xsl:text>cnpt</xsl:text>
		</xsl:attribute>
		<xsl:attribute name="index">
			<xsl:text>CNPT_OUTGOING</xsl:text>
			<xsl:value-of select="position()"/>
		</xsl:attribute>
		<xsl:attribute name="connector">
			<xsl:text>#xpointer(</xsl:text>
			<xsl:text>.[@uid=&apos;0x</xsl:text>
			<xsl:value-of select="substring(../../@xmi.id,23)"/> <!-- uid of originating state -->
			<xsl:text>&apos;]/ancestor::StateMachine/descendant::Transition[@uid=&apos;0x</xsl:text>
			<xsl:value-of select="substring(@xmi.idref,23)"/> <!-- uid of outgoing transition -->
			<xsl:text>&apos;])</xsl:text>
		</xsl:attribute>
		</xsl:element>
	</xsl:for-each>
</xsl:template>

<xsl:template match="UML2:Transition">
	<xsl:element name="port">
		<xsl:attribute name="name">
			<xsl:text>cnpt</xsl:text>
		</xsl:attribute>
		<xsl:attribute name="index">
			<xsl:text>CNPT_TARGET</xsl:text>
		</xsl:attribute>
		<xsl:attribute name="connector">
			<xsl:text>#xpointer(</xsl:text>
			<xsl:text>.[@uid=&apos;0x</xsl:text>
			<xsl:value-of select="substring(@xmi.id,23)"/> <!-- uid of originating state -->
			<xsl:text>&apos;]/ancestor::StateMachine/descendant::State[@uid=&apos;0x</xsl:text>
			<xsl:value-of select="substring(UML2:Transition.target/UML2:State/@xmi.idref,23)"/> <!-- uid of outgoing transition -->
			<xsl:text>&apos;])</xsl:text>
		</xsl:attribute>
		</xsl:element>
</xsl:template>

</xsl:stylesheet>
