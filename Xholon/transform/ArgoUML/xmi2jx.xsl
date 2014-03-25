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
	Transform XMI to Xh java code
	Author: Ken Webb
	Date:   September 11, 2007
	File:   xmi2jx.xsl
	Works with: ArgoUML 0.24
	Note: the code generation is currently incomplete
-->

<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:UML="org.omg.xmi.namespace.UML"
	xmlns:xmi="http://schema.omg.org/spec/XMI/1.2">

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
	<xsl:text>package org.primordion.xholon.xmiappsArgoUML;&#10;&#10;</xsl:text>
	
	<!-- imports -->
	<xsl:text>import org.primordion.xholon.base.Message;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.ISignal;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.StateMachineEntity;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.XholonWithPorts;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.IXholon;&#10;</xsl:text>
	<!-- any additional imports -->
	<xsl:text>&#10;</xsl:text>

	<!-- comments at top of file -->
	<xsl:text>/**</xsl:text>
		<xsl:text>&#10;&#9;</xsl:text>
		<xsl:value-of select="@name"/>
		<xsl:text> application - Xh Java class&#10;</xsl:text>
		<xsl:text>&#9;&lt;p&gt;Note: the code generation is currently incomplete.&lt;/p&gt;&#10;</xsl:text>
		<xsl:text>&#9;&lt;p&gt;Author: </xsl:text><xsl:value-of select="$author"/><xsl:text>&lt;/p&gt;&#10;</xsl:text>
		<xsl:text>&#9;&lt;p&gt;Date:   </xsl:text><xsl:value-of select="$dateNow"/><xsl:text>&lt;/p&gt;&#10;</xsl:text>
		<xsl:text>&#9;&lt;p&gt;File:   </xsl:text><xsl:value-of select="$fileName"/><xsl:text>&lt;/p&gt;&#10;</xsl:text>
		<xsl:text>&#9;&lt;p&gt;Target: Xholon 0.7 http://www.primordion.com/Xholon&lt;/p&gt;&#10;</xsl:text>
		<!-- UML -->
		<xsl:text>&#9;&lt;p&gt;UML: </xsl:text>
		<xsl:text>ArgoUML 0.24</xsl:text>
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
	
	<!-- class declaration
		ex: public class XhHelloWorld extends XholonWithPorts implements CeHelloWorld {
	-->
	<xsl:text>public class Xh</xsl:text>
	<xsl:value-of select="@name"></xsl:value-of>
	<xsl:text> extends XholonWithPorts implements Ce</xsl:text>
	<xsl:value-of select="@name"></xsl:value-of>
	<xsl:text> {&#10;&#10;</xsl:text>
	
	<!-- ports -->
	<xsl:text>// references to other Xholon instances; indices into the port array&#10;</xsl:text>
	<xsl:call-template name="outputPortClasses"/>
	<xsl:text>&#10;</xsl:text>
	
	<!-- signals -->
	<xsl:text>// Signals, Events&#10;</xsl:text>
	<xsl:call-template name="outputSignalClasses"/>
	<xsl:text>&#10;</xsl:text>

	<xsl:text>&#9;public void performActivity(int activityId, Message msg)&#10;</xsl:text>
	<xsl:text>&#9;{&#10;</xsl:text>
	<xsl:text>&#9;&#9;switch (activityId) {&#10;</xsl:text>
	<xsl:apply-templates select="/XMI/XMI.content/UML:Model//UML:Transition.effect/UML:CallAction/UML:Action.script/UML:ActionExpression"/>
	<xsl:text>&#9;&#9;default:&#10;</xsl:text>
	<xsl:text>&#9;&#9;&#9;System.out.println("Xh</xsl:text>
	<xsl:value-of select="/XMI/XMI.content/UML:Model/@name"/>
	<xsl:text>: unknown Activity" + activityId);&#10;</xsl:text>
	<xsl:text>&#9;&#9;&#9;break;&#10;</xsl:text>
	<xsl:text>&#9;&#9;}&#10;</xsl:text>
	<xsl:text>&#9;}&#10;</xsl:text>
	<xsl:text>}&#10;</xsl:text>
</xsl:template>

<!-- ArgoUML -->
<xsl:template match="UML:ActionExpression">
	<xsl:text>&#9;&#9;case 0x</xsl:text>
	<xsl:value-of select="substring(@xmi.id,53)"/>
	<xsl:text>: // </xsl:text>
	<xsl:value-of select="../../../../../../@name"/>
	<xsl:text>&#10;</xsl:text>
	<xsl:text>&#9;&#9;&#9;</xsl:text>
	<xsl:choose>
		<xsl:when test="string-length(@body)>0">
			<xsl:value-of select="@body"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>// no action specified</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
	<xsl:text>&#10;</xsl:text>
	<xsl:text>&#9;&#9;&#9;break;&#10;</xsl:text>
</xsl:template>

<!-- output constants to represent Port classes -->
<xsl:template name="outputPortClasses">
	<xsl:for-each select="/XMI/XMI.content/UML:Model//UML:Association[@name]">
		<xsl:if test="string-length(@name)>0">
			<xsl:text>public static final int </xsl:text>
			<xsl:value-of select="@name"/>
			<xsl:text> = </xsl:text>
			<xsl:text>0</xsl:text> <!-- unable to set a value in the model, so use 0 -->
			<xsl:text>;</xsl:text>
			<xsl:text>&#10;</xsl:text>
		</xsl:if>
	</xsl:for-each>
</xsl:template>

<!-- output constants to represent Signal classes -->
<xsl:template name="outputSignalClasses">
	<xsl:for-each select="/XMI/XMI.content/UML:Model//UML:Signal[@xmi.id]">
		<xsl:text>public static final int </xsl:text>
		<xsl:value-of select="@name"/>
		<xsl:text> = </xsl:text>
		<xsl:text>123</xsl:text> <!-- unable to set a value for a signal in the model, so use 123 -->
		<xsl:text>;</xsl:text>
		<xsl:text>&#10;</xsl:text>
	</xsl:for-each>
</xsl:template>

</xsl:stylesheet>
