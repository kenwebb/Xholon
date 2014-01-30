<?xml version="1.0" encoding="UTF-8"?>
<!--
	Xholon project
	Ken Webb
	June 22, 2006
	Convert from a Xholon Information.xml file to HTML.
	This works with both Firefox and Internet Explorer on Windows,
	and with Firefox, Mozilla, and Konqueror (partly) on Linux.
	The tags are consistent with Apache Forrest.
	www.primordion.com
-->
<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output
method="html"
doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
doctype-system="http://www.w3.org/TR/xhtml1/DTD/strict.dtd"
omit-xml-declaration="yes"
indent="no"/>

<xsl:template match="document">
	<HTML>
		<HEAD>
			<TITLE><xsl:value-of select="header/title"/></TITLE>
			<STYLE type="text/css">
				<xsl:text>&lt;!--</xsl:text>
				<xsl:text>body {background-color: #f0f8ff}</xsl:text>
				<xsl:text>h1 {font-family: "Arial"; text-align: center; color: blue}</xsl:text>
				<xsl:text>h3 {font-family: "Arial"; color: green}</xsl:text>
				<xsl:text>code {white-space: pre}</xsl:text>
				<xsl:text>ul {list-style-type: square;}</xsl:text>
				<xsl:text>--&gt;</xsl:text>
			</STYLE>
			<!--<link rel="stylesheet" href="../../information/infoXsl.css"></link>-->
		</HEAD>
		<xsl:apply-templates select="body"/>
	</HTML>
</xsl:template>

<xsl:template match="body">
<BODY>
	<H1><xsl:value-of select="../header/title"/></H1>
	<xsl:apply-templates select="section[@id]"/>
</BODY>	
</xsl:template>

<xsl:template match="section[@id]">
	<H3><xsl:value-of select="title"/></H3>
	<xsl:apply-templates select="section"/>
	<xsl:apply-templates select="p"/>
	<xsl:apply-templates select="ol"/>
	<xsl:apply-templates select="ul"/>
	<xsl:apply-templates select="source"/>
	<xsl:apply-templates select="figure"/>
</xsl:template>

<xsl:template match="section">
	<xsl:apply-templates select="section"/>
	<xsl:apply-templates select="p"/>
	<xsl:apply-templates select="source"/>
	<xsl:apply-templates select="note"/>
	<xsl:apply-templates select="warning"/>
	<xsl:apply-templates select="fixme"/>
	<xsl:apply-templates select="table"/>
	<xsl:apply-templates select="ol"/>
	<xsl:apply-templates select="ul"/>
	<xsl:apply-templates select="dl"/>
	<xsl:apply-templates select="figure"/>
	<xsl:apply-templates select="anchor"/>
</xsl:template>

<xsl:template match="p">
	<p><xsl:value-of select="."/></p>
	<xsl:apply-templates select="a"/>
</xsl:template>

<xsl:template match="source">
	<code><xsl:value-of select="."/></code>
</xsl:template>

<xsl:template match="note">
	<p><xsl:value-of select="."/></p>
</xsl:template>

<xsl:template match="warning">
	<p><xsl:value-of select="."/></p>
</xsl:template>

<xsl:template match="fixme">
	<p><xsl:value-of select="."/></p>
</xsl:template>

<xsl:template match="table">
	<p><xsl:value-of select="."/></p>
</xsl:template>

<xsl:template match="ol">
	<ol><xsl:apply-templates select="li"/></ol>
</xsl:template>

<xsl:template match="ul">
	<ul><xsl:apply-templates select="li"/></ul>
</xsl:template>

<xsl:template match="li">
	<li><xsl:value-of select="."/></li>
</xsl:template>

<xsl:template match="dl">
	<p><xsl:value-of select="."/></p>
</xsl:template>

<xsl:template match="figure">
	<xsl:element name="img">
		<xsl:attribute name="src"><xsl:value-of select="@src"/></xsl:attribute>
		<xsl:attribute name="alt"><xsl:value-of select="@alt"/></xsl:attribute>
		<xsl:if test="@width">
			<xsl:attribute name="width"><xsl:value-of select="@width"/></xsl:attribute>
		</xsl:if>
	</xsl:element>
</xsl:template>

<xsl:template match="anchor">
	<p><xsl:value-of select="."/></p>
</xsl:template>

<xsl:template match="a">
	<xsl:element name="a">
		<xsl:attribute name="href"><xsl:value-of select="@href"/></xsl:attribute>
		<xsl:value-of select="."/>
	</xsl:element>
</xsl:template>

</xsl:stylesheet>