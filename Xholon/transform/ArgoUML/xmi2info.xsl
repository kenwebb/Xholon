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
	Transform XMI to Information.xml
	Author: Ken Webb
	Date:   September 11, 2007
	File:   xmi2info.xsl
	Works with: ArgoUML 0.24
-->

<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:UML = 'org.omg.xmi.namespace.UML'
	xmlns:UML2 = 'org.omg.xmi.namespace.UML2'>

<xsl:output
	method="xml"
	omit-xml-declaration="no"
	indent="yes"/>

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
		<xsl:text>
		&lt;!DOCTYPE document PUBLIC "-//APACHE//DTD Documentation V2.0//EN" "http://forrest.apache.org/dtd/document-v20.dtd"&gt;
		</xsl:text>
	</xsl:comment>
	<xsl:processing-instruction name="xml-stylesheet">
		<xsl:text>type="text/xsl"</xsl:text>
		<xsl:text> href="../../../information/info2html.xsl"</xsl:text>
	</xsl:processing-instruction>
	<xsl:processing-instruction name="xml-stylesheet">
		<xsl:text>type="text/css"</xsl:text>
		<xsl:text> href="../../../information/info.css"</xsl:text>
	</xsl:processing-instruction>
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
	<xsl:text>&#10;</xsl:text>
	<document>
		<header>
			<title><xsl:value-of select="@name"/></title>
		</header>
		<body>
			<xsl:text>&#10;&#10;</xsl:text>
			<section id="whatisit">
				<title>What is it</title>
				<p>
					<xsl:apply-templates select="UML:ModelElement.taggedValue/UML:TaggedValue/UML:TaggedValue.dataValue"/>
				</p>
			</section>
			<xsl:text>&#10;&#10;</xsl:text>
			<section id="howtouseit">
				<title>How to use it</title>
				<p></p>
			</section>
			<xsl:text>&#10;&#10;</xsl:text>
			<section id="thingstonotice">
				<title>Things to notice</title>
				<p></p>
			</section>
			<xsl:text>&#10;&#10;</xsl:text>
			<section id="thingstotry">
				<title>Things to try</title>
				<p></p>
			</section>
			<xsl:text>&#10;&#10;</xsl:text>
			<section id="extendingthemodel">
				<title>Extending the model</title>
				<p></p>
			</section>
			<xsl:text>&#10;&#10;</xsl:text>
			<section id="xholonfeatures">
				<title>Xholon features</title>
				<p></p>
			</section>
			<xsl:text>&#10;&#10;</xsl:text>
			<section id="creditsandreferences">
				<title>Credits and references</title>
				<p></p>
			</section>
			<xsl:text>&#10;&#10;</xsl:text>
		</body>
	</document>
</xsl:template>

<xsl:template match="UML:TaggedValue.dataValue">
	<xsl:value-of select="." disable-output-escaping="yes"/>
</xsl:template>

</xsl:stylesheet>