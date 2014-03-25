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
	Transform XMI to GridPanel<appName>.java file
	Author: Ken Webb
	Date:   February 8, 2007
	File:   xmi2jg.xsl
	Works with: MagicDraw 11.5
-->

<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:uml='http://schema.omg.org/spec/UML/2.0'
	xmlns:xmi='http://schema.omg.org/spec/XMI/2.1'
	xmlns:MagicDraw_Profile='http://www.magicdraw.com/schemas/MagicDraw_Profile.xmi'
	xmlns:XholonStereotypes='http://www.magicdraw.com/schemas/XholonStereotypes.xmi'>

<xsl:output method="text" omit-xml-declaration="yes" indent="yes"/>

<xsl:param name="author"/>
<xsl:param name="dateNow"/>
<xsl:param name="fileName"/>

<!-- Position in the MD type/xmi:Extension/referenceExtension/@referentPath String
	where Java type starts. ex: double -->
<xsl:variable name="jTypePosition">
	<xsl:choose>
		<xsl:when test="/xmi:XMI/xmi:Documentation/@xmi:ExporterVersion=12.0">53</xsl:when>
		<xsl:otherwise>56</xsl:otherwise>
	</xsl:choose>
</xsl:variable>

<xsl:template match="xmi:XMI">
		<xsl:apply-templates select="uml:Model"/>
</xsl:template>

<xsl:template match="uml:Model">

	<!-- package, class, etc.
		ex: public class GridPanelXyz extends GridPanel implements IGridPanel, CeXyz {
	-->
	<xsl:text>package org.primordion.xholon.xmiapps;&#10;&#10;</xsl:text>
	<xsl:text>import java.awt.Color;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.IXholon;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.io.GridPanel;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.io.IGridPanel;&#10;&#10;</xsl:text>
	
	<!-- comments at top of file -->
	<xsl:text>/**</xsl:text>
		<xsl:text>&#10;&#9;</xsl:text>
		<xsl:value-of select="@name"/>
		<xsl:text> application - Xholon Java&#10;</xsl:text>
		<xsl:text>&#9;&lt;p&gt;Author: </xsl:text><xsl:value-of select="$author"/><xsl:text>&lt;/p&gt;&#10;</xsl:text>
		<xsl:text>&#9;&lt;p&gt;Date:   </xsl:text><xsl:value-of select="$dateNow"/><xsl:text>&lt;/p&gt;&#10;</xsl:text>
		<xsl:text>&#9;&lt;p&gt;File:   </xsl:text><xsl:value-of select="$fileName"/><xsl:text>&lt;/p&gt;&#10;</xsl:text>
		<xsl:text>&#9;&lt;p&gt;Target: Xholon 0.5 http://www.primordion.com/Xholon&lt;/p&gt;&#10;</xsl:text>
		<!-- UML -->
		<xsl:text>&#9;&lt;p&gt;UML: </xsl:text>
		<xsl:value-of select="/xmi:XMI/xmi:Documentation/@xmi:Exporter"/>
		<xsl:text> </xsl:text>
		<xsl:value-of select="/xmi:XMI/xmi:Documentation/@xmi:ExporterVersion"/>
		<xsl:text>&lt;/p&gt;&#10;</xsl:text>
		<!-- XMI -->
		<xsl:text>&#9;&lt;p&gt;XMI: </xsl:text>
		<xsl:value-of select="/xmi:XMI/@xmi:version"/>
		<xsl:text>, </xsl:text>
		<xsl:value-of select="/xmi:XMI/@timestamp"/>
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
	
	<xsl:text>&#10;/**&#10; * A graphic panel in which to display the 2D grid.&#10; */&#10;</xsl:text>
	<xsl:text>public class GridPanel</xsl:text>
	<xsl:value-of select="@name"></xsl:value-of>
	<xsl:text> extends GridPanel implements IGridPanel, Ce</xsl:text>
	<xsl:value-of select="@name"></xsl:value-of>
	<xsl:text> {&#10;</xsl:text>
	<xsl:text>private static final long serialVersionUID = 1L;&#10;&#10;</xsl:text>
	
	<!-- constructor -->
	<xsl:text>/**&#10; * Constructor&#10; */&#10;</xsl:text>
	<xsl:text>public GridPanel</xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text>() {super();}&#10;&#10;</xsl:text>
	<!-- constructor -->
	<xsl:text>/**&#10; * Constructor&#10; * @param gridOwner Xholon that owns the grid. */&#10;</xsl:text>
	<xsl:text>public GridPanel</xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text>(IXholon gridOwner) {super(gridOwner);}&#10;&#10;</xsl:text>
	
	<!-- get color -->
	<xsl:call-template name="outputGetColor"/>
	
	<xsl:text>}&#10;</xsl:text>
</xsl:template>

<!-- Optionally output getColor() -->
<!--
public Color getColor(IXholon xhNode)
{
	switch(xhNode.getXhcId()) {
	case GridCellCE: // TODO edit this
		if (xhNode.hasChildNodes()) {
			switch (((IXholon)xhNode.getFirstChild()).getXhcId()) {
			default:
				return Color.BLUE;
			}
		}
		else {
			return Color.WHITE;
		}
	default:
		return Color.GRAY;
	}
}
-->
<xsl:template name="outputGetColor">
	<xsl:text>/*&#10; * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)&#10; */&#10;</xsl:text>
	<xsl:text>public Color getColor(IXholon xhNode)&#10;</xsl:text>
	<xsl:text>{&#10;</xsl:text>
	<xsl:text>&#9;switch(xhNode.getXhcId()) {&#10;</xsl:text>
	<xsl:text>&#9;//case GridCellCE: // TODO edit this&#10;</xsl:text>
	<xsl:text>&#9;//&#9;if (xhNode.hasChildNodes()) {&#10;</xsl:text>
	<xsl:text>&#9;//&#9;&#9;switch (((IXholon)xhNode.getFirstChild()).getXhcId()) {&#10;</xsl:text>
	<xsl:text>&#9;//&#9;&#9;default:&#10;</xsl:text>
	<xsl:text>&#9;//&#9;&#9;&#9;return Color.BLUE;&#10;</xsl:text>
	<xsl:text>&#9;//&#9;&#9;}&#10;</xsl:text>
	<xsl:text>&#9;//&#9;}&#10;</xsl:text>
	<xsl:text>&#9;//&#9;else {&#10;</xsl:text>
	<xsl:text>&#9;//&#9;&#9;return Color.WHITE;&#10;</xsl:text>
	<xsl:text>&#9;//&#9;}&#10;</xsl:text>
	<xsl:text>&#9;default:&#10;</xsl:text>
	<xsl:text>&#9;&#9;return Color.GRAY;&#10;</xsl:text>
	<xsl:text>&#9;}&#10;</xsl:text>
	<xsl:text>}&#10;</xsl:text>
</xsl:template>

</xsl:stylesheet>
