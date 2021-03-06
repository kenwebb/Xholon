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
	Transform SysML  to App<appName>.java file
	Author: Ken Webb
	Date:   August 16, 2007
	File:   xmi2ja.xsl
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
		ex: public class XhHelloWorld extends XholonWithPorts implements CeHelloWorld {
	-->
	<xsl:text>package org.primordion.xholon.xmiappsTcSysML;&#10;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.app.Application;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.IXholon;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.XholonTime;&#10;&#10;</xsl:text>
	
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
	
	<xsl:text>public class App</xsl:text>
	<xsl:value-of select="@name"></xsl:value-of>
	<xsl:text> extends Application {&#10;&#10;</xsl:text>
	
	<!-- variables -->
	<xsl:text>// Variables&#10;</xsl:text>
	<xsl:apply-templates select="/sysML:ModelSYSML//packagedElement[@name='Application']/ownedAttribute[@xmi:type='uml:Property'][@name]"/>
	<xsl:text>&#10;</xsl:text>
	
	<!-- constructor -->
	<xsl:text>public App</xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text>() {super();}&#10;&#10;</xsl:text>
	
	<!-- initialize -->
	<xsl:call-template name="outputInitialize"/>
	
	<!-- Application-specific parameters - getters/setters -->
	<xsl:text>// Setters and Getters&#10;</xsl:text>
	<xsl:apply-templates select="/XholonStereotypes:GetterSetter"/>

	<!-- step -->
	<xsl:variable name="stepOperation" select="/sysML:ModelSYSML//ownedOperation[@xmi:type='uml:Operation'][@name='step'][not(@isAbstract='true')]"/>
	<xsl:choose>
	<xsl:when test="$stepOperation">
		<xsl:text>protected void step()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:if test="$stepOperation/ownedComment">
			<xsl:if test="string-length($stepOperation/ownedComment/@body)>0">
				<xsl:value-of select="$stepOperation/ownedComment/@body"/>
				<xsl:text>&#10;</xsl:text>
			</xsl:if>
		</xsl:if>				
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:when>
	<xsl:otherwise>
		<xsl:text>protected void step()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:if test="/sysML:ModelSYSML//ownedOperation[@xmi:type='uml:Operation'][@name='preAct'][not(@isAbstract='true')]">
			<xsl:text>    root.preAct();&#10;</xsl:text>
		</xsl:if>
		<xsl:text>	if (getUseDataPlotter()) {&#10;</xsl:text>
		<xsl:text>		chartViewer.capture(getTimeStep());&#10;</xsl:text>
		<xsl:text>	}&#10;</xsl:text>
		<xsl:text>    root.act();&#10;</xsl:text>
		<xsl:if test="/sysML:ModelSYSML//ownedOperation[@xmi:type='uml:Operation'][@name='postAct'][not(@isAbstract='true')]">
			<xsl:text>    root.postAct();&#10;</xsl:text>
		</xsl:if>
		<xsl:text>    XholonTime.sleep( getTimeStepInterval() );&#10;</xsl:text>
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:otherwise>
	</xsl:choose>
	
	<!-- wrapup -->
	<xsl:variable name="wrapupOperation" select="/sysML:ModelSYSML//ownedOperation[@xmi:type='uml:Operation'][@name='wrapup'][not(@isAbstract='true')]"/>
	<xsl:choose>
	<xsl:when test="$wrapupOperation">
		<xsl:text>public void wrapup()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:if test="$wrapupOperation/ownedComment">
			<xsl:if test="string-length($wrapupOperation/ownedComment/@body)>0">
				<xsl:value-of select="$wrapupOperation/ownedComment/@body"/>
				<xsl:text>&#10;</xsl:text>
			</xsl:if>
		</xsl:if>				
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:when>
	<xsl:otherwise>
		<xsl:text>public void wrapup()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:if test="/sysML:ModelSYSML//ownedOperation[@xmi:type='uml:Operation'][@name='preAct'][not(@isAbstract='true')]">
			<xsl:text>    root.preAct();&#10;</xsl:text>
		</xsl:if>
		<xsl:text>	if (getUseDataPlotter()) {&#10;</xsl:text>
		<xsl:text>		chartViewer.capture(getTimeStep());&#10;</xsl:text>
		<xsl:text>	}&#10;</xsl:text>
		<xsl:text>  super.wrapup();&#10;</xsl:text>
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:otherwise>
	</xsl:choose>
	
	<!-- shouldBePlotted -->
	<xsl:call-template name="outputShouldBePlotted"/>
	
	<!-- nvokeDataPlotter -->
	<xsl:call-template name="outputInvokeDataPlotter"/>
	
	<!-- main -->
	<xsl:text>public static void main(String[] args) {&#10;</xsl:text>
	<xsl:text>    appMain(args, "org.primordion.xholon.xmiappsTcSysML.App</xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text>",&#10;</xsl:text>
	<xsl:text>        "./config/xmiappsTcSysML/</xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text>/</xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text>_xhn.xml");&#10;</xsl:text>
	<xsl:text>}&#10;&#10;</xsl:text>
	
	<!-- end class -->
	<xsl:text>}</xsl:text>
</xsl:template>

<!--  match GetterSetter stereotype instances -->
<xsl:template match="XholonStereotypes:GetterSetter">
	<xsl:call-template name="outputGetterSetter">
		<xsl:with-param name="gsAttribute" select="/sysML:ModelSYSML//ownedAttribute[@xmi:id=current()/@base_Property]"/>
	</xsl:call-template>
</xsl:template>

<!-- match GetterSetter stereotype instances -->
<xsl:template name="outputGetterSetter">
	<xsl:param name="gsAttribute"/>
	<xsl:if test="$gsAttribute/ancestor::packagedElement[@name='XhnParams']">
		<!-- create setter -->
		<xsl:variable name="gsJavaType" select="substring($gsAttribute/type/@href, $jTypePosition)"/>
		<xsl:variable name="gsNameFirstLetterLower" select="concat(
			translate(substring($gsAttribute/@name,1,1), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ_', 'abcdefghijklmnopqrstuvwxyz_'),
			substring($gsAttribute/@name,2))"/>
		<xsl:variable name="gsClassName" select="/sysML:ModelSYSML/@name"/>
		<xsl:text>public void set</xsl:text>
		<xsl:value-of select="$gsAttribute/@name"/>
		<xsl:text>(</xsl:text>
		<xsl:call-template name="outputJavaType">
			<xsl:with-param name="javaType" select="$gsJavaType"/>
		</xsl:call-template>
		<xsl:text> </xsl:text>
		<xsl:value-of select="$gsAttribute/@name"/>
		<xsl:text>) {Xh</xsl:text>
		<xsl:value-of select="$gsClassName"/>
		<xsl:text>.set</xsl:text>
		<xsl:value-of select="$gsAttribute/@name"/>
		<xsl:text>(</xsl:text>
		<xsl:value-of select="$gsAttribute/@name"/>
		<xsl:text>);}&#10;</xsl:text>
		<!-- create getter -->
		<xsl:text>public </xsl:text>
		<xsl:call-template name="outputJavaType">
			<xsl:with-param name="javaType" select="$gsJavaType"/>
		</xsl:call-template>
		<xsl:text> get</xsl:text>
		<xsl:value-of select="$gsAttribute/@name"/>
		<xsl:text>() {return Xh</xsl:text>
		<xsl:value-of select="$gsClassName"/>
		<xsl:text>.get</xsl:text>
		<xsl:value-of select="$gsAttribute/@name"/>
		<xsl:text>();}&#10;&#10;</xsl:text>
		
		<xsl:text>public boolean setParam(String pName, String pValue)&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:text>&#9;if ("</xsl:text>
		<xsl:value-of select="$gsAttribute/@name"/>
		<xsl:text>".equals(pName)) {set</xsl:text>
		<xsl:value-of select="$gsAttribute/@name"/>
		<xsl:text>(</xsl:text>
		<xsl:choose>
			<xsl:when test="$gsJavaType='EBoolean'"> <!-- ex: false -->
				<xsl:text>Boolean.getBoolean(pValue)</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='EChar'"> <!-- ex: 'q' -->
				<xsl:text>pValue.charAt(0)</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='EByte'"> <!-- ex: -128 -->
				<xsl:text>Byte.parseByte(pValue)</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='EShort'"> <!-- ex: 32767 -->
				<xsl:text>Short.parseShort(pValue)</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='EInt'"> <!-- ex: 123456 -->
				<xsl:text>Integer.parseInt(pValue)</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='ELong'"> <!-- ex: 9223372036854775807L -->
				<xsl:text>Long.parseLong(pValue)</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='EFloat'"> <!-- ex: 123.45f -->
				<xsl:text>Float.valueOf(pValue).floatValue()</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='EDouble'"> <!-- ex: 1234567890.9876543210 -->
				<xsl:text>Double.valueOf(pValue).doubleValue()</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='EString'"> <!-- ex: This is a test parameter. -->
				<xsl:text>pValue</xsl:text>
			</xsl:when>
				<xsl:otherwise>
					<xsl:text>pValue</xsl:text>
				</xsl:otherwise>
		</xsl:choose>
		<xsl:text>); return true;}&#10;</xsl:text>
		<xsl:text>&#9;return super.setParam(pName, pValue);&#10;</xsl:text>
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:if>
	<xsl:if test="ancestor::packagedElement[@name='Application']">
		<!-- TODO handle Application variable setters/getters -->
	</xsl:if>
</xsl:template>

<xsl:template match="ownedAttribute">
	<xsl:if test="../@xmi:type='uml:Class' and not(@aggregation='composite')">
		<!-- Java type will either be in a sub-element (type) or in an attribute (@type) but not both.
			 So it's OK to concatenate the results from two XPath expressions. -->
		<xsl:variable name="myTypeId" select="@type"/>
		<xsl:variable name="javaType" select="concat(substring(type/xmi:Extension/referenceExtension/@referentPath, $jTypePosition),
			/sysML:ModelSYSML//packagedElement[@xmi:id=$myTypeId]/@name)"/>
		<xsl:if test="not(../@name='XhnParams')">
			<xsl:if test="type or @type"> <!-- don't create a Java variable if the attribute doesn't have a type -->
				<xsl:if test="ownedComment">
					<xsl:if test="string-length(ownedComment/@body)>0">
						<xsl:text>// </xsl:text>
						<xsl:value-of select="ownedComment/@body"/>
						<xsl:text>&#10;</xsl:text>
					</xsl:if>
				</xsl:if>
				<xsl:choose>
					<xsl:when test="@visibility='package'">
						<!-- package visibility -->
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="@visibility"/>
						<xsl:text> </xsl:text>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@isStatic">
					<xsl:text>static </xsl:text>
				</xsl:if>
				<xsl:if test="@isReadOnly">
					<xsl:text>final </xsl:text>
				</xsl:if>
				<xsl:value-of select="$javaType"/>
				<xsl:text> </xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:if test="xmi:Extension/modelExtension/appliedStereotypeInstance/slot/value[@value='[]']">
					<xsl:text>[]</xsl:text>
					<xsl:apply-templates select="upperValue">
						<xsl:with-param name="jType">
							<xsl:value-of select="$javaType"/>
						</xsl:with-param>
					</xsl:apply-templates>
				</xsl:if>
				<xsl:apply-templates select="defaultValue">
					<xsl:with-param name="jType">
						<xsl:value-of select="$javaType"/>
					</xsl:with-param>
				</xsl:apply-templates>
				<xsl:text>; // </xsl:text>
				<xsl:value-of select="../@name"/>
				<xsl:text>&#10;</xsl:text>
			</xsl:if>
		</xsl:if>
	</xsl:if>
</xsl:template>

<xsl:template match="defaultValue">
	<!-- OpaqueExpression uses @body, others use @value -->
	<xsl:param name="jType"/>
	<xsl:choose>
		<xsl:when test="not(@value) and not(@body)">
			<!-- do nothing if there's no value attribute -->
		</xsl:when>
		<xsl:when test="@value='' or @body=''">
			<!-- do nothing if the value attribute has no content -->
		</xsl:when>
		<xsl:otherwise>
			<xsl:text> = </xsl:text>
			<xsl:choose>
				<xsl:when test="$jType='boolean' or $jType='char' or $jType='byte' or $jType='short' or $jType='int' or $jType='long' or $jType='float' or $jType='double' or $jType='String'">
					<xsl:choose>
						<xsl:when test="$jType='String' and not(substring(@value,1,1)='&quot;')">
							<xsl:text>"</xsl:text><xsl:value-of select="concat(@value, @body)"/><xsl:text>"</xsl:text>
						</xsl:when>
						<xsl:when test="$jType='String' and not(substring(@body,1,1)='&quot;')">
							<xsl:text>"</xsl:text><xsl:value-of select="concat(@value, @body)"/><xsl:text>"</xsl:text>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="concat(@value, @body)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>new </xsl:text>
					<xsl:value-of select="$jType"/>
					<xsl:text>(</xsl:text>
					<xsl:value-of select="concat(@value, @body)"/>
					<xsl:text>)</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name="outputJavaType">
	<xsl:param name="javaType"/>
	<xsl:choose>
		<xsl:when test="$javaType='EBoolean'"> <!-- ex: false -->
			<xsl:text>boolean</xsl:text>
		</xsl:when>
		<xsl:when test="$javaType='EChar'"> <!-- ex: 'q' -->
			<xsl:text>char</xsl:text>
		</xsl:when>
		<xsl:when test="$javaType='EByte'"> <!-- ex: -128 -->
			<xsl:text>byte</xsl:text>
		</xsl:when>
		<xsl:when test="$javaType='EShort'"> <!-- ex: 32767 -->
			<xsl:text>short</xsl:text>
		</xsl:when>
		<xsl:when test="$javaType='EInt'"> <!-- ex: 123456 -->
			<xsl:text>int</xsl:text>
		</xsl:when>
		<xsl:when test="$javaType='ELong'"> <!-- ex: 9223372036854775807L -->
			<xsl:text>long</xsl:text>
		</xsl:when>
		<xsl:when test="$javaType='EFloat'"> <!-- ex: 123.45f -->
			<xsl:text>float</xsl:text>
		</xsl:when>
		<xsl:when test="$javaType='EDouble'"> <!-- ex: 1234567890.9876543210 -->
			<xsl:text>double</xsl:text>
		</xsl:when>
		<xsl:when test="$javaType='EString'"> <!-- ex: This is a test parameter. -->
			<xsl:text>String</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$javaType"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- Optionally output shouldBePlotted() -->
<xsl:template name="outputShouldBePlotted">
	<xsl:variable name="sbpOperation" select="/sysML:ModelSYSML//ownedOperation[@name='shouldBePlotted'][not(@isAbstract='true')]"/>
	<xsl:if test="$sbpOperation">
		<xsl:text>protected boolean shouldBePlotted(org.primordion.xholon.base.IXholon modelNode)&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:if test="$sbpOperation/@method">
				<xsl:value-of select="$sbpOperation/../ownedBehavior[@xmi:id=$sbpOperation/@method]/body"/>
				<xsl:text>&#10;</xsl:text>
		</xsl:if>
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:if>
</xsl:template>

<!-- Optionally output invokeDataPlotter() -->
<xsl:template name="outputInvokeDataPlotter">
	<xsl:variable name="sbpOperation" select="/sysML:ModelSYSML//ownedOperation[@name='invokeDataPlotter'][not(@isAbstract='true')]"/>
	<xsl:if test="$sbpOperation">
		<xsl:text>public void invokeDataPlotter()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:if test="$sbpOperation/@method">
				<xsl:value-of select="$sbpOperation/../ownedBehavior[@xmi:id=$sbpOperation/@method]/body"/>
				<xsl:text>&#10;</xsl:text>
		</xsl:if>
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:if>
</xsl:template>

<!-- Optionally output initialize() -->
<xsl:template name="outputInitialize">
	<xsl:variable name="initOperation" select="/sysML:ModelSYSML//ownedOperation[@xmi:type='uml:Operation'][@name='initialize'][not(@isAbstract='true')]"/>
	<xsl:if test="$initOperation">
		<xsl:text>public void initialize(String configFileName)&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:text>&#9;super.initialize(configFileName);&#10;</xsl:text>
		<xsl:if test="$initOperation/ownedComment">
			<xsl:if test="string-length($initOperation/ownedComment/@body)>0">
				<xsl:value-of select="$initOperation/ownedComment/@body"/>
				<xsl:text>&#10;</xsl:text>
			</xsl:if>
		</xsl:if>				
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:if>
</xsl:template>

</xsl:stylesheet>
