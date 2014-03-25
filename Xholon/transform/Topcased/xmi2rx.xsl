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
	Transform UML2 to list of possible root xholons
	Author: Ken Webb
	Date:   August 14, 2007
	File:   xmi2rx.xsl
	Works with: TOPCASED 1.0.0
-->

<!-- Eclipse, EMF UML -->
<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xmi="http://schema.omg.org/spec/XMI/2.1"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:Ecore="http://www.eclipse.org/uml2/schemas/Ecore/3"
	xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore"
	xmlns:uml="http://www.eclipse.org/uml2/2.1.0/UML"
	xmlns:xi="http://www.w3.org/2001/XInclude">

<xsl:output method="text" omit-xml-declaration="yes" indent="no"/>

<!-- Either xmi:XMI or uml:Model may be the root node in the .uml file -->
<xsl:template match="/">
	<xsl:apply-templates select="xmi:XMI | uml:Model"/>
</xsl:template>

<xsl:template match="xmi:XMI">
	<xsl:apply-templates select="uml:Model"/>
</xsl:template>

<xsl:template match="uml:Model">
	<xsl:choose>
	
		<xsl:when test="//packagedElement[@xmi:type='uml:Class']">
			<!-- Look for most likely root xholon(s), and put these at top of the list. -->
			<xsl:for-each select="//packagedElement[@xmi:type='uml:Class'][ownedAttribute[not(@xmi:type)][@aggregation='composite']]">
				<xsl:sort select="@name"/>
				<xsl:call-template name="outputMostLikely">
					<xsl:with-param name="candidate" select=".">
					</xsl:with-param>
				</xsl:call-template>
			</xsl:for-each>
			
			<!-- separator -->
			<xsl:text>-----&#10;</xsl:text>
			
			<!-- List all other classes as well. -->
			<xsl:apply-templates select="//packagedElement[@xmi:type='uml:Class']">
				<xsl:sort select="@name"/>
			</xsl:apply-templates>
		</xsl:when>
	
		<!-- If there are no classes, list any state machines that are packaged elements (that aren't owned by any class) -->
		<xsl:otherwise>
			<xsl:for-each select="//packagedElement[@xmi:type='uml:StateMachine']">
				<xsl:value-of select="@name"/>
				<xsl:text>&#10;</xsl:text>
			</xsl:for-each>
		</xsl:otherwise>
		
	</xsl:choose>
</xsl:template>

<!-- match -->
<xsl:template match="packagedElement[@xmi:type='uml:Class']">
	<xsl:value-of select="@name"/>
	<xsl:text>&#10;</xsl:text>
</xsl:template>

<!-- Look for most likely root xholon(s), and put these at top of the list. -->
<xsl:template name="outputMostLikely">
	<xsl:param name="candidate"/>
	<xsl:if test="not(//ownedAttribute[@type=current()/@xmi:id])">
		<xsl:value-of select="$candidate/@name"/>
		<xsl:text>&#10;</xsl:text>
	</xsl:if>
</xsl:template>

</xsl:stylesheet>