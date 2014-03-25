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
	Transform XMI to ClassNames.txt
	Author: Ken Webb
	Date:   November 8, 2005
	File:   xmi2cn.xsl
	Works with: MagicDraw 11.5
	@deprecated 0.3, but may still be useful to get a sorted list of class names for documentation
-->

<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:uml='http://schema.omg.org/spec/UML/2.0'
	xmlns:xmi='http://schema.omg.org/spec/XMI/2.1'>

<xsl:output method="text" omit-xml-declaration="yes" indent="no"/>

<xsl:template match="xmi:XMI">
	<xsl:apply-templates select="//ownedMember[@xmi:type='uml:Class']">
		<xsl:sort select="@name"/>
	</xsl:apply-templates>
</xsl:template>

<xsl:template match="ownedMember">
	<xsl:choose>
		<xsl:when test="generalization/general/xmi:Extension/referenceExtension[@referentPath='UML Standard Profile::UML2.0 Metamodel::CompositeStructures::Ports::Port']">
			<!-- ignore port classes -->
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="@name"/>
			<xsl:text>&#10;</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>