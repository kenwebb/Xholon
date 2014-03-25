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
	Transform SysML to GridPanel<appName>.java file
	Author: Ken Webb
	Date:  August 16, 2007
	File:   xmi2jg.xsl
	Works with: TOPCASED 1.0.0
-->

<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xmi="http://www.omg.org/XMI"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:Ecore="http://www.eclipse.org/uml2/schemas/Ecore/3"
	xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore"
	xmlns:sysML="http://www.topcased.org/1.0/sysML"
	xmlns:uml="http://www.eclipse.org/uml2/2.1.0/UML"
	xmlns:xi="http://www.w3.org/2001/XInclude"
	xmlns:XholonStereotypes="http:///schemas/XholonStereotypes/_NhBU8Cm9EdyXztXVHo8fCQ/1">

<xsl:output method="text" omit-xml-declaration="yes" indent="yes"/>

<xsl:param name="author"/>
<xsl:param name="dateNow"/>
<xsl:param name="fileName"/>

<!-- Position in the Topcased type href="" where Java type starts. ex: EDouble -->
<xsl:variable name="jTypePosition">46</xsl:variable>

<xsl:template match="sysML:ModelSYSML">

	<!-- package, class, etc.
		ex: public class GridPanelXyz extends GridPanel implements IGridPanel, CeXyz {
	-->
	<xsl:text>package org.primordion.xholon.xmiappsTcSysML;&#10;&#10;</xsl:text>
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
		<xsl:text>&#9;&lt;p&gt;Target: Xholon 0.6 http://www.primordion.com/Xholon&lt;/p&gt;&#10;</xsl:text>
		<!-- SysML -->
		<xsl:text>&#9;&lt;p&gt;SysML: </xsl:text>
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
