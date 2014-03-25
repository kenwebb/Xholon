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
	Transform XMI to ClassNames.txt
	Author: Ken Webb
	Date:   September 11, 2007
	File:   xmi2cn.xsl
	Works with: ArgoUML 0.24
-->

<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:UML = 'org.omg.xmi.namespace.UML'
	xmlns:UML2 = 'org.omg.xmi.namespace.UML2'>

<xsl:output method="text" omit-xml-declaration="yes" indent="no"/>

<xsl:template match="XMI">
	<!-- ignore any classes from java.lang -->
	<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML:Class[@xmi.id][../../@name!='lang']">
		<xsl:sort select="@name"/>
	</xsl:apply-templates>
</xsl:template>

<xsl:template match="UML:Class">
	<xsl:value-of select="@name"/>
	<xsl:text>&#10;</xsl:text>
</xsl:template>

</xsl:stylesheet>