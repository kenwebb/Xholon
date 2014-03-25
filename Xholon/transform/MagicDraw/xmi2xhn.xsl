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
	Transform XMI to _xhn.xml
	Author: Ken Webb
	Date:   November 8, 2005
	File:   xmi2xhn.xsl
	Works with: MagicDraw 11.5
-->

<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:uml='http://schema.omg.org/spec/UML/2.0'
	xmlns:xmi='http://schema.omg.org/spec/XMI/2.1'>

<xsl:output method="xml" omit-xml-declaration="no" indent="yes"/>

<xsl:param name="author"/>
<xsl:param name="dateNow"/>
<xsl:param name="fileName"/>

<xsl:template match="xmi:XMI">
		<xsl:apply-templates select="uml:Model"/>
</xsl:template>

<xsl:template match="uml:Model">
	<!--<xsl:param name="xhnParameters" select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Class'][@name='XhnParams']"/>-->
	<xsl:comment>
		<xsl:text>&#10;&#9;</xsl:text>
		<xsl:value-of select="@name"/>
		<xsl:text> application - _xhn configuration&#10;</xsl:text>
		<xsl:text>&#9;Author: </xsl:text><xsl:value-of select="$author"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;Date:   </xsl:text><xsl:value-of select="$dateNow"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;File:   </xsl:text><xsl:value-of select="$fileName"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;Target: Xholon 0.5 http://www.primordion.com/Xholon&#10;</xsl:text>
		<!-- UML -->
		<xsl:text>&#9;UML: </xsl:text>
		<xsl:value-of select="/xmi:XMI/xmi:Documentation/@xmi:Exporter"/>
		<xsl:text> </xsl:text>
		<xsl:value-of select="/xmi:XMI/xmi:Documentation/@xmi:ExporterVersion"/>
		<xsl:text>&#10;</xsl:text>
		<!-- XMI -->
		<xsl:text>&#9;XMI: </xsl:text>
		<xsl:value-of select="/xmi:XMI/@xmi:version"/>
		<xsl:text>, </xsl:text>
		<xsl:value-of select="/xmi:XMI/@timestamp"/>
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
	<params>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>ShowParams</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>false</xsl:text></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>ModelName</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:value-of select="@name"/></xsl:with-param>
		</xsl:call-template>
		
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>AppM</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>true</xsl:text></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>InfoM</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>false</xsl:text></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>ErrorM</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>true</xsl:text></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>MaxProcessLoops</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>10</xsl:text></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>TimeStepInterval</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>10</xsl:text></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>SizeMessageQueue</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>10</xsl:text></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>InheritanceHierarchyFile</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue">
				<xsl:text>./config/xmiapps/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>_InheritanceHierarchy.xml</xsl:text>				
			</xsl:with-param>
		</xsl:call-template>
		
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>CompositeStructureHierarchyFile</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue">
				<xsl:text>./config/xmiapps/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>_CompositeStructureHierarchy.xml</xsl:text>				
			</xsl:with-param>
		</xsl:call-template>
		
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>ClassDetailsFile</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue">
				<xsl:text>./config/xmiapps/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>_ClassDetails.xml</xsl:text>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>InformationFile</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue">
				<xsl:text>./config/xmiapps/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>/</xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>_Information.xml</xsl:text>
			</xsl:with-param>
		</xsl:call-template>
		
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>JavaClassName</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue">
				<xsl:text>org.primordion.xholon.xmiapps.App</xsl:text>
				<xsl:value-of select="@name"/>
			</xsl:with-param>
		</xsl:call-template>		
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>JavaXhClassName</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue">
				<xsl:text>org.primordion.xholon.xmiapps.Xh</xsl:text>
				<xsl:value-of select="@name"/>
			</xsl:with-param>
		</xsl:call-template>		
		
		<!-- RandomNumberSeed commented out by default -->
		<!--
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>RandomNumberSeed</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>1234</xsl:text></xsl:with-param>
		</xsl:call-template>
		-->
		
		<!-- MaxPorts -->
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>MaxPorts</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue">
				<xsl:value-of select="count(//ownedMember[@xmi:type='uml:Class'][generalization/general/xmi:Extension/referenceExtension[@referentPath='UML Standard Profile::UML2.0 Metamodel::CompositeStructures::Ports::Port']])"/>
			</xsl:with-param>
		</xsl:call-template>
		
		<!-- Viewers -->
		<xsl:comment> Viewers </xsl:comment>
		
		<!-- Data Plotter -->
		<xsl:comment> UseDataPlotter: none JFreeChart gnuplot </xsl:comment>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>UseDataPlotter</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>none</xsl:text></xsl:with-param>
		</xsl:call-template>
		<xsl:comment> DataPlotterParams: Title,XAxisLabel,YAxisLabel,Path,TypeOfData,NameConcatLevels,WriteType </xsl:comment>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>DataPlotterParams</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>Title,Time (timesteps),Y,./statistics/,stats,1,WRITE_AS_LONG</xsl:text></xsl:with-param>
		</xsl:call-template>

		<!-- Graphical Tree Viewer -->
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>UseGraphicalTreeViewer</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>false</xsl:text></xsl:with-param>
		</xsl:call-template>
		<xsl:comment> GraphicalTreeViewerParams: XPathExpression,DistX,DistY,SizeX,SizeY,FontSize,Title,ShowNodeLabels </xsl:comment>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>GraphicalTreeViewerParams</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>/,20,20,800,500,9,Application Composite Structure,false</xsl:text></xsl:with-param>
		</xsl:call-template>
		
		<!-- Graphical Network Viewer -->
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>UseGraphicalNetworkViewer</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>false</xsl:text></xsl:with-param>
		</xsl:call-template>
		<xsl:comment> GraphicalNetworkViewerParams: XPathExpression,SizeX,SizeY,FontSize,LayoutType,ShowContainers,Title,ShowNodeLabels </xsl:comment>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>GraphicalNetworkViewerParams</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>/,800,500,9,LAYOUT_KK,false,Application Port Connections,false</xsl:text></xsl:with-param>
		</xsl:call-template>
		
		<!-- Grid Viewer -->
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>UseGridViewer</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>false</xsl:text></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>GridPanelClassName</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>org.primordion.xholon.samples.GridPanelMyApp</xsl:text></xsl:with-param>
		</xsl:call-template>
		<xsl:comment> GridViewerParams: XPathExpression,CellSize,Title </xsl:comment>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>GridViewerParams</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>descendant::Row/..,5,Grid Viewer App</xsl:text></xsl:with-param>
		</xsl:call-template>
		
		<!-- Interactions -->
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>UseInteractions</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>true</xsl:text></xsl:with-param>
		</xsl:call-template>
		<xsl:comment> InteractionParams: OutputFormat,ShowStates,SocketHost,SocketPort </xsl:comment>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>InteractionParams</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>4,true,localhost,60001</xsl:text></xsl:with-param>
		</xsl:call-template>
		
		<!-- VRML -->
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>UseVrml</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>false</xsl:text></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>VrmlWriterClassName</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>org.primordion.xholon.tutorials.VrmlWriterMyApp</xsl:text></xsl:with-param>
		</xsl:call-template>
		<xsl:comment> VrmlParams: DoClassicStyle,VrmlWriteDir,UseLOD,CreateProtoFiles </xsl:comment>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>VrmlParams</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>true,./3d/,false,false</xsl:text></xsl:with-param>
		</xsl:call-template>
		
		<!-- Text Tree -->
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>UseTextTree</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>false</xsl:text></xsl:with-param>
		</xsl:call-template>
		
		<!-- Snapshots -->
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>SaveSnapshots</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>false</xsl:text></xsl:with-param>
		</xsl:call-template>
		<xsl:comment> SnapshotParams: SnapshotSource(0 1),SnapshotTostring,Path </xsl:comment>
		<xsl:call-template name="ownedMember">
			<xsl:with-param name="paramName"><xsl:text>SnapshotParams</xsl:text></xsl:with-param>
			<xsl:with-param name="paramDefaultValue"><xsl:text>0,true,./snapshot/</xsl:text></xsl:with-param>
		</xsl:call-template>
		
		<!-- parameters specific to one application -->
		<xsl:comment> The following parameters, if any, are specific to this application </xsl:comment>
		<xsl:for-each select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Class'][@name='XhnParams']/ownedAttribute">
			<xsl:choose>
				<xsl:when test="@name='ShowParams'"/>
				<xsl:when test="@name='ModelName'"/>
				<xsl:when test="@name='AppM'"/>
				<xsl:when test="@name='InfoM'"/>
				<xsl:when test="@name='ErrorM'"/>
				<xsl:when test="@name='MaxProcessLoops'"/>
				<xsl:when test="@name='TimeStepInterval'"/>
				<xsl:when test="@name='SizeMessageQueue'"/>
				<xsl:when test="@name='InheritanceHierarchyFile'"/>
				<xsl:when test="@name='CompositeStructureHierarchyFile'"/>
				<xsl:when test="@name='ClassDetailsFile'"/>
				<xsl:when test="@name='InformationFile'"/>
				<xsl:when test="@name='JavaClassName'"/>
				<xsl:when test="@name='JavaXhClassName'"/>
				<xsl:when test="@name='RandomNumberSeed'"/>
				<xsl:when test="@name='MaxPorts'"/>
				<xsl:when test="@name='UseDataPlotter'"/>
				<xsl:when test="@name='DataPlotterParams'"/>
				<xsl:when test="@name='UseGraphicalTreeViewer'"/>
				<xsl:when test="@name='GraphicalTreeViewerParams'"/>
				<xsl:when test="@name='UseGraphicalNetworkViewer'"/>
				<xsl:when test="@name='GraphicalNetworkViewerParams'"/>
				<xsl:when test="@name='UseGridViewer'"/>
				<xsl:when test="@name='GridPanelClassName'"/>
				<xsl:when test="@name='GridViewerParams'"/>
				<xsl:when test="@name='UseInteractions'"/>
				<xsl:when test="@name='InteractionParams'"/>
				<xsl:when test="@name='UseVrml'"/>
				<xsl:when test="@name='VrmlWriterClassName'"/>
				<xsl:when test="@name='VrmlParams'"/>
				<xsl:when test="@name='UseTextTree'"/>
				<xsl:when test="@name='SaveSnapshots'"/>
				<xsl:when test="@name='SnapshotParams'"/>
				<!-- these are common parameters not yet part of the XhnParams class in MD -->
				<xsl:when test="@name='MaxXholons'"/>
				<xsl:when test="@name='MaxXholonClasses'"/>
				<xsl:when test="@name='MaxStateMachineEntities'"/>
				<xsl:when test="@name='MaxActivities'"/>
				<xsl:when test="@name='TreeNodeFactoryDynamic'"/>
				<xsl:when test="@name='JavaXhClassClassName'"/>
				<xsl:when test="@name='JavaActivityClassName'"/>
				<xsl:when test="@name='XincludePath'"/>
				<xsl:otherwise>
					<xsl:element name="param">
						<xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="defaultValue/@value"/></xsl:attribute>
					</xsl:element>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</params>
</xsl:template>

<!-- ownedMember[@xmi:type='uml:Class'][@name='XhnParams'] -->
<!--<xsl:template match="ownedMember">-->
<xsl:template name="ownedMember">
	<xsl:param name="paramName"/>
	<xsl:param name="paramDefaultValue"/>
	<!--<xsl:param name="xhnParams"/>-->
	<xsl:element name="param">
		<xsl:attribute name="name"><xsl:value-of select="$paramName"/></xsl:attribute>
		<!--<xsl:attribute name="value"><xsl:value-of select="$paramDefaultValue"/></xsl:attribute>-->
		<xsl:attribute name="value">
			<xsl:choose>
				<xsl:when test="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Class'][@name='XhnParams']/ownedAttribute[@name=$paramName]">
					<!-- xalan-j crashes if I use $xhnParams in the following statement -->
					<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Class'][@name='XhnParams']/ownedAttribute[@name=$paramName]">
						<xsl:with-param name="paramDefValue">
							<xsl:value-of select="$paramDefaultValue"/>
						</xsl:with-param>
					</xsl:apply-templates>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$paramDefaultValue"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
	</xsl:element>
</xsl:template>

<xsl:template match="ownedAttribute">
	<xsl:param name="paramDefValue"/>
	<xsl:choose>
		<xsl:when test="defaultValue">
			<xsl:apply-templates select="defaultValue">
				<xsl:with-param name="pDefValue"><xsl:value-of select="$paramDefValue"/></xsl:with-param>
			</xsl:apply-templates>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$paramDefValue"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="defaultValue">
	<xsl:param name="pDefValue"/>
	<xsl:choose>
		<xsl:when test="not(@value)">
			<!-- if there's no value attribute -->
			<xsl:value-of select="$pDefValue"></xsl:value-of>
		</xsl:when>
		<xsl:when test="@value=''">
			<!-- if the value attribute has no content -->
			<xsl:value-of select="$pDefValue"></xsl:value-of>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="@value"></xsl:value-of>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>


</xsl:stylesheet>