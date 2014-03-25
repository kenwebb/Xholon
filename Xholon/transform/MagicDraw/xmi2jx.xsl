<?xml version="1.0" encoding="UTF-8"?>
<!--
Xholon Runtime Framework - executes event-driven & dynamic applications
Copyright (C) 2005, 2006, 2007, 2008, 2009 Ken Webb

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
	Transform XMI to Xh<appName>.java file
	Author: Ken Webb
	Date:   November 16, 2005
	File:   xmi2jx.xsl
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

<xsl:param name="rootEnt"/>
<xsl:param name="author"/>
<xsl:param name="dateNow"/>
<xsl:param name="fileName"/>

<!-- root xholon entity (ex: TheSystem) -->
<xsl:variable name="rootEntity"
	select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Class'][@name=$rootEnt]
		| /xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Component'][@name=$rootEnt]"/>

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
	<!-- package -->
	<xsl:text>package org.primordion.xholon.xmiapps;&#10;&#10;</xsl:text>
	
	<!-- imports -->
	<xsl:text>import org.primordion.xholon.base.IMessage;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.Message;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.ISignal;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.StateMachineEntity;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.XholonWithPorts;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.IXholon;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.IIntegration;&#10;</xsl:text>
	<!-- any additional imports -->
	<xsl:for-each select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Class'][../@name='XholonBaseClasses']">
		<xsl:call-template name="outputImport">
			<xsl:with-param name="importClass" select="."/>
		</xsl:call-template>
	</xsl:for-each>
	<xsl:text>&#10;</xsl:text>
	
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
	<xsl:for-each select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Class'][generalization/general/xmi:Extension/referenceExtension[@referentPath='UML Standard Profile::UML2.0 Metamodel::CompositeStructures::Ports::Port']]">
		<xsl:text>public static final int </xsl:text>
		<xsl:value-of select="@name"/>
		<xsl:text> = </xsl:text>
		<xsl:choose>
			<xsl:when test="ownedAttribute/defaultValue/@value or ownedAttribute/defaultValue/@body">
				<xsl:choose>
					<xsl:when test="not(ownedAttribute/@name) or ownedAttribute[@name='']">  <!-- must be an unnamed attribute -->
						<xsl:value-of select="concat(ownedAttribute/defaultValue/@value, ownedAttribute/defaultValue/@body)"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>0</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>0</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:text>;</xsl:text>
		<xsl:text>&#10;</xsl:text>
	</xsl:for-each>
	<!--
	<xsl:text>&#10;// maximum number of ports found in any Xholon class&#10;</xsl:text>
	<xsl:text>private static final int SIZE_MYAPP_PORTS = </xsl:text>
	<xsl:value-of select="count(/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Class'][generalization/general/xmi:Extension/referenceExtension[@referentPath='UML Standard Profile::UML2.0 Metamodel::CompositeStructures::Ports::Port']])"/>
	<xsl:text>;&#10;&#10;</xsl:text>
	-->
	<xsl:text>&#10;</xsl:text>
	
	<!-- signals -->
	<xsl:text>// Signals, Events&#10;</xsl:text>
	<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Signal']"/>
	<xsl:text>&#10;</xsl:text>
	
	<!-- variables -->
	<xsl:text>// Variables&#10;</xsl:text>
	<xsl:text>public String roleName = null;&#10;</xsl:text>
	<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedAttribute[@xmi:type='uml:Property'][@name]"/>
	<xsl:text>&#10;</xsl:text>
	
	<!-- constructor -->
	<xsl:text>// Constructor&#10;</xsl:text>
	<xsl:text>public Xh</xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text>() {super();}&#10;&#10;</xsl:text>
	
	<!-- variables: setters, getters -->
	<xsl:variable name="getterSetterId" select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Stereotype'][@name='GetterSetter']/@xmi:id"/>
	<xsl:text>// Setters and Getters&#10;</xsl:text>
	<xsl:text>public void setRoleName(String roleName) {this.roleName = roleName;}&#10;</xsl:text>
	<xsl:text>public String getRoleName() {return roleName;}&#10;&#10;</xsl:text>
	<!--<xsl:if test="/xmi:XMI/uml:Model//appliedStereotypeInstance/classifier[@xmi:idref=$getterSetterId]">-->
		<xsl:apply-templates select="//appliedStereotypeInstance/classifier[@xmi:idref=$getterSetterId]"/>
	<!--</xsl:if>-->
	<!--  MD 12.0 -->
	<xsl:apply-templates select="/xmi:XMI/XholonStereotypes:GetterSetter"/>
	
	<!-- initialize -->
	<xsl:text>public void initialize()&#10;</xsl:text>
	<xsl:text>{&#10;</xsl:text>
	<xsl:text>	super.initialize();&#10;</xsl:text>
	<xsl:for-each select="/xmi:XMI/uml:Model//ownedAttribute[@xmi:type='uml:Property'][@name]">
		<xsl:if test="../@xmi:type='uml:Class' and not(../@name='XhnParams') and not(../@name='Application')">
			<xsl:if test="(type or @type) and not(@isStatic) and not(@isReadOnly)">
				<xsl:if test="not(xmi:Extension/modelExtension/appliedStereotypeInstance/slot/value[@value='[]'])
					and not(/xmi:XMI/MagicDraw_Profile:typeModifier[@base_Element=current()/@xmi:id][@typeModifier='[]'])">
					<xsl:if test="defaultValue/@value or defaultValue/@body">
						<xsl:variable name="myTypeId" select="@type"/>
						<xsl:variable name="jType" select="concat(substring(type/xmi:Extension/referenceExtension/@referentPath, $jTypePosition),
							/xmi:XMI/uml:Model//ownedMember[@xmi:id=$myTypeId]/@name)"/>
						<xsl:text>	</xsl:text>
						<xsl:value-of select="@name"/>
						<xsl:text> = </xsl:text>
						<xsl:choose>
							<xsl:when test="$jType='boolean' or $jType='char' or $jType='byte' or $jType='short' or $jType='int' or $jType='long' or $jType='float' or $jType='double' or $jType='String'">
								<!--<xsl:value-of select="concat(defaultValue/@value, defaultValue/@body)"/>-->
								<xsl:choose>
									<xsl:when test="$jType='String' and not(substring(@value,1,1)='&quot;')">
										<xsl:text>"</xsl:text><xsl:value-of select="concat(defaultValue/@value, defaultValue/@body)"/><xsl:text>"</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="concat(defaultValue/@value, defaultValue/@body)"/>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>new </xsl:text>
								<xsl:value-of select="$jType"/>
								<xsl:text>(</xsl:text>
								<xsl:value-of select="concat(defaultValue/@value, defaultValue/@body)"/>
								<xsl:text>)</xsl:text>
							</xsl:otherwise>
						</xsl:choose>
						<xsl:text>;&#10;</xsl:text>
					</xsl:if>
				</xsl:if>
			</xsl:if>
		</xsl:if>
	</xsl:for-each>
	<xsl:text>}&#10;&#10;</xsl:text>
	
	<!-- application-specific operations -->
	<xsl:for-each select="/xmi:XMI/uml:Model//ownedOperation[@xmi:type='uml:Operation'][not(../@isAbstract='true')]">
		<xsl:if test="not(@name='postConfigure') and not(@name='preAct') and not(@name='act') and not(@name='postAct') and not(@name='handleNodeSelection')">
			<xsl:variable name="myTypeId" select="@type"/>
			<xsl:variable name="javaType" select="concat(substring(ownedParameter[@direction='return']/type/xmi:Extension/referenceExtension/@referentPath, $jTypePosition),
			/xmi:XMI/uml:Model//ownedMember[@xmi:id=$myTypeId]/@name)"/>
			<xsl:if test="ownedParameter[@direction='return']/type"> <!-- don't create a Java operation unless it has a return type -->
				<xsl:text>// </xsl:text>
				<xsl:value-of select="../@name"/> <!-- class name -->
				<xsl:text>&#10;</xsl:text>
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
				<xsl:text>(</xsl:text>
				<xsl:for-each select="ownedParameter[@direction != 'return']">
					<xsl:variable name="myTypeId" select="@type"/>
					<xsl:variable name="javaType" select="concat(substring(type/xmi:Extension/referenceExtension/@referentPath, $jTypePosition),
					/xmi:XMI/uml:Model//ownedMember[@xmi:id=$myTypeId]/@name)"/>
					<xsl:value-of select="$javaType"/>
					<xsl:text> </xsl:text>
					<xsl:value-of select="@name"/>
					<xsl:if test="xmi:Extension/modelExtension/appliedStereotypeInstance/slot/value[@value='[]']">
						<xsl:text>[]</xsl:text>
					</xsl:if>
					<!-- add comma between 2 or more params -->
					<xsl:if test="last() > position()">
						<xsl:text>, </xsl:text>
					</xsl:if>
				</xsl:for-each>
				<xsl:text>)</xsl:text>
				<xsl:text>&#10;{&#10;</xsl:text>
				<xsl:if test="ownedComment">
					<xsl:if test="string-length(ownedComment/@body)>0">
						<xsl:value-of select="ownedComment/@body"/>
						<xsl:text>&#10;</xsl:text>
					</xsl:if>
				</xsl:if>
				<xsl:text>}</xsl:text>
				<xsl:text>&#10;</xsl:text>
			</xsl:if>
			<xsl:text>&#10;</xsl:text>
		</xsl:if>
	</xsl:for-each>
	
	<!-- preConfigure -->
	<xsl:if test="/xmi:XMI/uml:Model//ownedOperation[@xmi:type='uml:Operation'][@name='preConfigure'][not(@isAbstract='true')]">
		<xsl:text>public void preConfigure()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:text>	switch(xhc.getId()) {&#10;</xsl:text>
		<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedOperation[@xmi:type='uml:Operation'][@name='preConfigure']"/>
		<xsl:text>	default:&#10;</xsl:text>
		<xsl:text>		break;&#10;</xsl:text>
		<xsl:text>	}&#10;</xsl:text>
		<xsl:text>	super.preConfigure();&#10;</xsl:text>
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:if>
	
	<!-- configure -->
	<xsl:if test="/xmi:XMI/uml:Model//ownedOperation[@xmi:type='uml:Operation'][@name='configure'][not(@isAbstract='true')]">
		<xsl:text>public void configure()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:text>	switch(xhc.getId()) {&#10;</xsl:text>
		<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedOperation[@xmi:type='uml:Operation'][@name='configure']"/>
		<xsl:text>	default:&#10;</xsl:text>
		<xsl:text>		break;&#10;</xsl:text>
		<xsl:text>	}&#10;</xsl:text>
		<xsl:text>	super.configure();&#10;</xsl:text>
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:if>
	
	<!-- postConfigure -->
	<xsl:if test="/xmi:XMI/uml:Model//ownedOperation[@xmi:type='uml:Operation'][@name='postConfigure'][not(@isAbstract='true')]">
		<xsl:text>public void postConfigure()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:text>	switch(xhc.getId()) {&#10;</xsl:text>
		<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedOperation[@xmi:type='uml:Operation'][@name='postConfigure']"/>
		<xsl:text>	default:&#10;</xsl:text>
		<xsl:text>		break;&#10;</xsl:text>
		<xsl:text>	}&#10;</xsl:text>
		<xsl:text>	super.postConfigure();&#10;</xsl:text>
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:if>
	
	<!-- preAct -->
	<xsl:if test="/xmi:XMI/uml:Model//ownedOperation[@xmi:type='uml:Operation'][@name='preAct'][not(@isAbstract='true')]">
		<xsl:text>public void preAct()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:text>	switch(xhc.getId()) {&#10;</xsl:text>
		<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedOperation[@xmi:type='uml:Operation'][@name='preAct']"/>
		<xsl:text>	default:&#10;</xsl:text>
		<xsl:text>		break;&#10;</xsl:text>
		<xsl:text>	}&#10;</xsl:text>
		<xsl:text>	super.preAct();&#10;</xsl:text>
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:if>
	
	<!-- act -->
	<xsl:text>public void act()&#10;</xsl:text>
	<xsl:text>{&#10;</xsl:text>
	<xsl:text>	switch(xhc.getId()) {&#10;</xsl:text>
	<xsl:text>	case </xsl:text>
	<xsl:value-of select="$rootEntity/@name"/>
	<xsl:text>CE:&#10;</xsl:text>
	<xsl:text>		processMessageQ();&#10;</xsl:text>
	<xsl:text>		break;&#10;</xsl:text>
	<!-- a xholon might have only act(), only do(), or it may have both -->
	<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedOperation[@xmi:type='uml:Operation'][@name='act']"/>
	
	<xsl:for-each select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Class'][ownedBehavior//doActivity]">
	<xsl:call-template name="ownedMemberWithDoActivity">
		<xsl:with-param name="ownedMember" select="."/>
		<!--<xsl:with-param name="ownedMember" select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Class'][ownedBehavior//doActivity]"/>-->
	</xsl:call-template>
	</xsl:for-each>
	
	<xsl:text>	default:&#10;</xsl:text>
	<xsl:text>		break;&#10;</xsl:text>
	<xsl:text>	}&#10;</xsl:text>
	<xsl:text>	super.act();&#10;</xsl:text>
	<xsl:text>}&#10;&#10;</xsl:text>
	
	<!-- postAct -->
	<xsl:if test="/xmi:XMI/uml:Model//ownedOperation[@xmi:type='uml:Operation'][@name='postAct'][not(@isAbstract='true')]">
		<xsl:text>public void postAct()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:text>	switch(xhc.getId()) {&#10;</xsl:text>
		<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedOperation[@xmi:type='uml:Operation'][@name='postAct']"/>
		<xsl:text>	default:&#10;</xsl:text>
		<xsl:text>		break;&#10;</xsl:text>
		<xsl:text>	}&#10;</xsl:text>
		<xsl:text>	super.postAct();&#10;</xsl:text>
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:if>
	
	<!-- processReceivedMessage -->
	<xsl:text>public void processReceivedMessage(IMessage msg)&#10;</xsl:text>
	<xsl:text>{&#10;</xsl:text>
	<xsl:text>	switch(xhc.getId()) {&#10;</xsl:text>
	<xsl:text>	default:&#10;</xsl:text>
	<xsl:text>		IXholon node = getFirstChild();&#10;</xsl:text>
	<xsl:text>		while (node != null) {&#10;</xsl:text>
	<xsl:text>			if (node.getClass() == StateMachineEntity.class) {&#10;</xsl:text>
	<xsl:text>				((StateMachineEntity)node).doStateMachine(msg); // StateMachine&#10;</xsl:text>
	<xsl:text>				break;&#10;</xsl:text>
	<xsl:text>			}&#10;</xsl:text>
	<xsl:text>			else {&#10;</xsl:text>
	<xsl:text>				node = node.getNextSibling();&#10;</xsl:text>
	<xsl:text>			}&#10;</xsl:text>
	<xsl:text>		}&#10;</xsl:text>
	<xsl:text>		if (node == null) {&#10;</xsl:text>
	<xsl:text>			System.out.println("Xh</xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text>: message with no receiver " + msg);&#10;</xsl:text>
	<xsl:text>		}&#10;</xsl:text>
	<!--
	<xsl:text>		if (this.hasChildNodes()) {&#10;</xsl:text>
	<xsl:text>			((StateMachineEntity)getFirstChild()).doStateMachine(msg); // StateMachine&#10;</xsl:text>
	<xsl:text>		}&#10;</xsl:text>
	<xsl:text>		else {&#10;</xsl:text>
	<xsl:text>			System.out.println("Xh</xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text>: message with no receiver " + msg);&#10;</xsl:text>
	<xsl:text>		}&#10;</xsl:text>
	-->
	<xsl:text>		break;&#10;</xsl:text>
	<xsl:text>	}&#10;</xsl:text>
	<xsl:text>}&#10;&#10;</xsl:text>

	<!-- performActivity -->
	<xsl:text>public void performActivity(int activityId, IMessage msg)&#10;</xsl:text>
	<xsl:text>{&#10;</xsl:text>
	<xsl:text>&#9;switch (activityId) {&#10;</xsl:text>
	<xsl:apply-templates select="/xmi:XMI/uml:Model//effect[@xmi:type='uml:Activity']"/>
	<xsl:apply-templates select="/xmi:XMI/uml:Model//entry[@xmi:type='uml:Activity']"/>
	<xsl:apply-templates select="/xmi:XMI/uml:Model//exit[@xmi:type='uml:Activity']"/>
	<xsl:for-each select="/xmi:XMI/uml:Model//doActivity[@xmi:type='uml:Activity']">
		<xsl:text>&#9;case </xsl:text>
		<xsl:call-template name="outputUniqueId">
			<xsl:with-param name="xmiId" select="@xmi:id"/>
		</xsl:call-template>
		<xsl:text>: // </xsl:text>
		<xsl:text>doActivity </xsl:text>
		<xsl:value-of select="../@name"/>
		<xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;{&#10;</xsl:text>
		<xsl:apply-templates select="ownedComment"/>
		<xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;}&#10;</xsl:text>
		<xsl:text>&#9;&#9;break;&#10;</xsl:text>
	</xsl:for-each>
	<xsl:text>&#9;default:&#10;</xsl:text>
	<xsl:text>&#9;&#9;System.out.println("Xh</xsl:text>
	<xsl:value-of select="/xmi:XMI/uml:Model/@name"/>
	<xsl:text>: performActivity() unknown Activity " + activityId);&#10;</xsl:text>
	<xsl:text>&#9;&#9;break;&#10;</xsl:text>
	<xsl:text>&#9;}&#10;</xsl:text>
	<xsl:text>}&#10;</xsl:text>
	<xsl:text>&#10;</xsl:text>
	
	<!-- performGuard -->
	<xsl:text>public boolean performGuard(int activityId, IMessage msg)&#10;</xsl:text>
	<xsl:text>{&#10;</xsl:text>
	<xsl:text>&#9;switch (activityId) {&#10;</xsl:text>
	<xsl:apply-templates select="/xmi:XMI/uml:Model//guard[@xmi:type='uml:Constraint']"/>
	<xsl:text>&#9;default:&#10;</xsl:text>
	<xsl:text>&#9;&#9;System.out.println("Xh</xsl:text>
	<xsl:value-of select="/xmi:XMI/uml:Model/@name"/>
	<xsl:text>: performGuard() unknown Activity " + activityId);&#10;</xsl:text>
	<xsl:text>&#9;&#9;return false;&#10;</xsl:text>
	<xsl:text>&#9;}&#10;</xsl:text>
	<xsl:text>}&#10;&#10;</xsl:text>
	
	<!-- handleNodeSelection -->
	<xsl:if test="/xmi:XMI/uml:Model//ownedOperation[@xmi:type='uml:Operation'][@name='handleNodeSelection'][not(@isAbstract='true')]">
		<xsl:text>public Object handleNodeSelection()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:text>	switch(xhc.getId()) {&#10;</xsl:text>
		<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedOperation[@xmi:type='uml:Operation'][@name='handleNodeSelection']"/>
		<xsl:text>	default:&#10;</xsl:text>
		<xsl:text>		return toString();&#10;</xsl:text>
		<xsl:text>	}&#10;</xsl:text>
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:if>
	
	<!-- toString -->
	<xsl:text>public String toString() {&#10;</xsl:text>
	<xsl:text>	String outStr = getName();&#10;</xsl:text>
	<xsl:text>	if ((port != null) &amp;&amp; (port.length > 0)) {&#10;</xsl:text>
	<xsl:text>		outStr += " [";&#10;</xsl:text>
	<xsl:text>		for (int i = 0; i &lt; port.length; i++) {&#10;</xsl:text>
	<xsl:text>			if (port[i] != null) {&#10;</xsl:text>
	<xsl:text>				outStr += " port:" + port[i].getName();&#10;</xsl:text>
	<xsl:text>			}&#10;</xsl:text>
	<xsl:text>		}&#10;</xsl:text>
	<xsl:text>		outStr += "]";&#10;</xsl:text>
	<xsl:text>	}&#10;</xsl:text>
	<xsl:text>	if (getVal() != 0.0) {&#10;</xsl:text>
	<xsl:text>		outStr += " val:" + getVal();&#10;</xsl:text>
	<xsl:text>	}&#10;</xsl:text>
	<xsl:text>	return outStr;&#10;</xsl:text>
	<xsl:text>}&#10;&#10;</xsl:text>
	
	<!-- end class -->
	<xsl:text>}</xsl:text>
</xsl:template>

<xsl:template match="effect">
	<xsl:text>&#9;&#9;case </xsl:text>
	<xsl:call-template name="outputUniqueId">
		<xsl:with-param name="xmiId" select="@xmi:id"/>
	</xsl:call-template>
	<xsl:text>: // </xsl:text>
	<xsl:value-of select="ancestor::ownedBehavior/../@name"/>
	<xsl:text> </xsl:text>
	<xsl:value-of select="../../../@name"/>
	<xsl:text> </xsl:text>
	<xsl:call-template name="outputSignalName">
		<xsl:with-param name="signalEventId" select="../trigger/@event"/>
	</xsl:call-template>
	<xsl:text>&#10;</xsl:text>
	<xsl:apply-templates select="ownedComment"/>
	<xsl:text>&#10;</xsl:text>
	<xsl:text>&#9;&#9;&#9;break;&#10;</xsl:text>
</xsl:template>

<xsl:template match="entry">
	<xsl:text>&#9;&#9;case </xsl:text>
	<xsl:call-template name="outputUniqueId">
		<xsl:with-param name="xmiId" select="@xmi:id"/>
	</xsl:call-template>
	<xsl:text>: // entry </xsl:text>
	<xsl:value-of select="../@name"/>
	<xsl:text>&#10;</xsl:text>
	<xsl:apply-templates select="ownedComment"/>
	<xsl:text>&#10;</xsl:text>
	<xsl:text>&#9;&#9;&#9;break;&#10;</xsl:text>
</xsl:template>

<xsl:template match="exit">
	<xsl:text>&#9;&#9;case </xsl:text>
	<xsl:call-template name="outputUniqueId">
		<xsl:with-param name="xmiId" select="@xmi:id"/>
	</xsl:call-template>
	<xsl:text>: // exit </xsl:text>
	<xsl:value-of select="../@name"/>
	<xsl:text>&#10;</xsl:text>
	<xsl:apply-templates select="ownedComment"/>
	<xsl:text>&#10;</xsl:text>
	<xsl:text>&#9;&#9;&#9;break;&#10;</xsl:text>
</xsl:template>

<xsl:template match="ownedOperation">
	<xsl:if test="not(@isAbstract='true')">
		<xsl:text>&#9;case </xsl:text>
		<xsl:value-of select="../@name"/>
		<xsl:text>CE:&#10;</xsl:text>
		<xsl:text>&#9;{&#10;</xsl:text>
		<xsl:apply-templates select="ownedComment"/>
		<xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;}&#10;</xsl:text>
		<!-- if the Java code contains "return ", then don't output "break;" (TODO check return type instead)-->
		<xsl:if test="not(contains(ownedComment/@body, 'return '))">
			<xsl:text>&#9;&#9;break;&#10;</xsl:text>
		</xsl:if>
	</xsl:if>
</xsl:template>

<xsl:template match="ownedOperation[@name='act']">
	<xsl:if test="not(@isAbstract='true')">
		<xsl:text>&#9;case </xsl:text>
		<xsl:value-of select="../@name"/>
		<xsl:text>CE:&#10;</xsl:text>
		<xsl:text>&#9;{&#10;</xsl:text>
		<xsl:if test="..//doActivity">
			<xsl:text>		// Do Activity&#10;</xsl:text>
			<xsl:text>		if (this.hasChildNodes()) {&#10;</xsl:text>
			<xsl:text>			((StateMachineEntity)getFirstChild()).doStateMachine(new Message(ISignal.SIGNAL_DOACTIVITY,null,null,this));&#10;</xsl:text>
			<xsl:text>		}&#10;</xsl:text>
			<xsl:text>		else {&#10;</xsl:text>
			<xsl:text>			System.out.println("Xh</xsl:text>
			<xsl:value-of select="@name"/>
			<xsl:text>: doActivity with no receiver ");&#10;</xsl:text>
			<xsl:text>		}&#10;</xsl:text>
		</xsl:if>
		<xsl:apply-templates select="ownedComment"/>
		<xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;}&#10;</xsl:text>
		<!-- if the Java code contains "return ", then don't output "break;" (TODO check return type instead)-->
		<xsl:if test="not(contains(ownedComment/@body, 'return '))">
			<xsl:text>&#9;&#9;break;&#10;</xsl:text>
		</xsl:if>
	</xsl:if>
</xsl:template>

<xsl:template match="ownedComment">
	<xsl:choose>
		<xsl:when test="string-length(@body)>0">
			<xsl:value-of select="@body"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>// no action specified</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="guard">
	<xsl:text>&#9;&#9;case </xsl:text>
	<xsl:call-template name="outputUniqueId">
		<xsl:with-param name="xmiId" select="@xmi:id"/>
	</xsl:call-template>
	<xsl:text>: // </xsl:text>
	<xsl:value-of select="../@name"/>
	<xsl:text>&#10;</xsl:text>
	<xsl:apply-templates select="specification[@xmi:type='uml:OpaqueExpression']"/>
	<xsl:text>&#10;</xsl:text>
</xsl:template>

<xsl:template match="specification">
	<xsl:choose>
		<xsl:when test="string-length(@body)>0">
			<xsl:choose>
				<xsl:when test="@body='else'">
					<xsl:text>&#9;&#9;&#9;return true; // else</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>&#9;&#9;&#9;return </xsl:text>
					<xsl:value-of select="@body"/>
					<xsl:text>;</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>&#9;&#9;&#9;return true; // no guard action specified</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="ownedMember[@xmi:type='uml:Signal']">
	<xsl:param name="sigId" select="@xmi:id"/>
	<xsl:text>public static final int </xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text> = </xsl:text>
	<!-- Signal ID will be either: (1) an owned attribute of the Signal, (2) owned attribute of a Port -->
	<xsl:value-of select="ownedAttribute/defaultValue/@value"/>
	<xsl:value-of select="/xmi:XMI/uml:Model//ownedAttribute[@type=$sigId]/defaultValue/@value"/>
	<xsl:value-of select="ownedAttribute/defaultValue/@body"/>
	<xsl:value-of select="/xmi:XMI/uml:Model//ownedAttribute[@type=$sigId]/defaultValue/@body"/>
	<xsl:text>;&#10;</xsl:text>
</xsl:template>

<xsl:template match="ownedAttribute">
	<xsl:if test="../@xmi:type='uml:Class' and not(@aggregation='composite')">
		<!-- Java type will either be in a sub-element (type) or in an attribute (@type) but not both.
			 So it's OK to concatenate the results from two XPath expressions. -->
		<xsl:variable name="myTypeId" select="@type"/>
		<xsl:variable name="javaType" select="concat(substring(type/xmi:Extension/referenceExtension/@referentPath, $jTypePosition),
			/xmi:XMI/uml:Model//ownedMember[@xmi:id=$myTypeId]/@name)"/>
		<xsl:if test="not(../@name='XhnParams') and not(../@name='Application')">
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
				<!-- MD 11.5 [] -->
				<xsl:if test="xmi:Extension/modelExtension/appliedStereotypeInstance/slot/value[@value='[]']">
					<xsl:text>[]</xsl:text>
					<xsl:apply-templates select="upperValue">
						<xsl:with-param name="jType">
							<xsl:value-of select="$javaType"/>
						</xsl:with-param>
					</xsl:apply-templates>
				</xsl:if>
				<!-- MD 12.0 [] - has a stereotype cross reference -->
				<xsl:if test="/xmi:XMI/MagicDraw_Profile:typeModifier[@base_Element=current()/@xmi:id][@typeModifier='[]']">
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
		<xsl:if test="../@name='XhnParams'">
			<xsl:variable name="getSetId" select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Stereotype'][@name='GetterSetter']/@xmi:id"/>
			<xsl:if test="xmi:Extension/modelExtension/appliedStereotypeInstance/classifier[@xmi:idref=$getSetId]">
				<xsl:text>protected static </xsl:text>
				<xsl:value-of select="$javaType"/>
				<xsl:text> </xsl:text>
				<xsl:value-of select="translate(substring(@name,1,1), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')"/>
				<xsl:value-of select="substring(@name,2)"/>
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

<xsl:template match="upperValue">
	<xsl:param name="jType"/>
	<xsl:choose>
		<xsl:when test="not(@value)">
			<!-- do nothing if there's no value attribute -->
		</xsl:when>
		<xsl:when test="@value=''">
			<!-- do nothing if the value attribute has no content -->
		</xsl:when>
		<xsl:otherwise>
			<xsl:text> = new </xsl:text>
			<xsl:value-of select="$jType"/>
			<xsl:text>[</xsl:text>
			<xsl:value-of select="@value"/>
			<xsl:text>]</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- MD 11.5 - match GetterSetter stereotype instances -->
<xsl:template match="appliedStereotypeInstance/classifier">
	<xsl:call-template name="outputGetterSetter">
		<xsl:with-param name="gsAttribute" select="ancestor::ownedAttribute"/>
	</xsl:call-template>
</xsl:template>

<!-- MD 12.0 - match GetterSetter stereotype instances -->
<xsl:template match="XholonStereotypes:GetterSetter">
	<xsl:call-template name="outputGetterSetter">
		<xsl:with-param name="gsAttribute" select="/xmi:XMI/uml:Model//ownedAttribute[@xmi:id=current()/@base_Property]"/>
	</xsl:call-template>
</xsl:template>

<xsl:template name="outputSignalName">
	<xsl:param name="signalEventId"/>
	<xsl:param name="signalId" select="/xmi:XMI/uml:Model//ownedMember[@xmi:id=$signalEventId]/@signal"/>
	<xsl:value-of select="/xmi:XMI/uml:Model//ownedMember[@xmi:id=$signalId]/@name"/>
</xsl:template>

<xsl:template name="outputImport">
	<xsl:param name="importClass"/>
	<xsl:text>import org.primordion.xholon.base.</xsl:text>
	<xsl:value-of select="$importClass/@name"/>
	<xsl:text>;&#10;</xsl:text>
</xsl:template>

<!-- Output a unique ID.
	 Take last 8 or 9 digits and:
	   convert _ to 1
	   convert 0 to 1
	   convert 1 to 9
-->
<xsl:template name="outputUniqueId">
	<xsl:param name="xmiId"/>
	<xsl:value-of select="substring(translate($xmiId, '_01', '119'), 32)"/>
</xsl:template>

<xsl:template name="outputGetterSetter">
	<xsl:param name="gsAttribute"/>
	<xsl:variable name="gsTypeId" select="$gsAttribute/@type"/>
	<xsl:variable name="gsJavaType" select="concat(substring($gsAttribute/type/xmi:Extension/referenceExtension/@referentPath, $jTypePosition),
		/xmi:XMI/uml:Model//ownedMember[@xmi:id=$gsTypeId]/@name)"/>
	<xsl:variable name="gsNameFirstLetterLower" select="concat(
		translate(substring($gsAttribute/@name,1,1), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ_', 'abcdefghijklmnopqrstuvwxyz_'),
		substring($gsAttribute/@name,2))"/>
	<!-- create setter -->
	<xsl:if test="not($gsAttribute/@isReadOnly)"> <!-- no setter if it's read only (final) -->
		<xsl:text>public </xsl:text>
		<xsl:if test="$gsAttribute/@isStatic">
			<xsl:text>static </xsl:text>
		</xsl:if>
		<xsl:text>void set</xsl:text>
		<xsl:value-of select="translate(substring($gsAttribute/@name,1,1), 'abcdefghijklmnopqrstuvwxyz_', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ_')"></xsl:value-of>
		<xsl:value-of select="substring($gsAttribute/@name,2)"/>
		<xsl:text>(</xsl:text>
		<xsl:value-of select="$gsJavaType"/>
		<xsl:text> </xsl:text>
		<xsl:value-of select="$gsAttribute/@name"/>
		<xsl:if test="$gsAttribute/@isStatic">
			<xsl:text>Arg</xsl:text>
		</xsl:if>
		<xsl:if test="$gsAttribute/xmi:Extension/modelExtension/appliedStereotypeInstance/slot/value[@value='[]']">
			<xsl:text>[]</xsl:text>
		</xsl:if>
		<xsl:text>) {</xsl:text>
		<xsl:if test="not($gsAttribute/@isStatic)">
			<xsl:text>this.</xsl:text>
		</xsl:if>
		<xsl:choose>
			<xsl:when test="ancestor::ownedMember[@name='XhnParams']">
				<xsl:value-of select="$gsNameFirstLetterLower"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$gsAttribute/@name"/>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:text> = </xsl:text>
		<xsl:value-of select="$gsAttribute/@name"/>
		<xsl:if test="$gsAttribute/@isStatic">
			<xsl:text>Arg</xsl:text>
		</xsl:if>
		<xsl:text>;}&#10;</xsl:text>
	</xsl:if>
	<!-- create getter -->
	<xsl:text>public </xsl:text>
	<xsl:if test="$gsAttribute/@isStatic">
		<xsl:text>static </xsl:text>
	</xsl:if>
	<xsl:value-of select="$gsJavaType"/>
	<xsl:if test="$gsAttribute/xmi:Extension/modelExtension/appliedStereotypeInstance/slot/value[@value='[]']">
		<xsl:text>[]</xsl:text>
	</xsl:if>
	<xsl:text> get</xsl:text>
	<xsl:value-of select="translate(substring($gsAttribute/@name,1,1), 'abcdefghijklmnopqrstuvwxyz_', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ_')"></xsl:value-of>
	<xsl:value-of select="substring($gsAttribute/@name,2)"/>
	
	<!-- val -->
	<xsl:if test="$gsAttribute/@name='val'">
		<xsl:choose>
			<xsl:when test="$gsJavaType='boolean'"> <!-- ex: false -->
				<xsl:text>_boolean</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='char'"> <!-- ex: 'q' -->
				<xsl:text>_char</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='byte'"> <!-- ex: -128 -->
				<xsl:text>_byte</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='short'"> <!-- ex: 32767 -->
				<xsl:text>_short</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='int'"> <!-- ex: 123456 -->
				<xsl:text>_int</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='long'"> <!-- ex: 9223372036854775807L -->
				<xsl:text>_long</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='float'"> <!-- ex: 123.45f -->
				<xsl:text>_float</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='double'"> <!-- ex: 1234567890.9876543210 -->
				<!--<xsl:text>_double</xsl:text>-->
			</xsl:when>
			<xsl:when test="$gsJavaType='String'"> <!-- ex: This is a test parameter. -->
				<xsl:text>_String</xsl:text>
			</xsl:when>
				<xsl:otherwise>
					<xsl:text>_</xsl:text>
					<xsl:value-of select="$gsJavaType"/>
				</xsl:otherwise>
		</xsl:choose>
	</xsl:if>
	
	<xsl:text>() {return </xsl:text>
	<xsl:choose>
		<xsl:when test="ancestor::ownedMember[@name='XhnParams']">
			<xsl:value-of select="$gsNameFirstLetterLower"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$gsAttribute/@name"/>
		</xsl:otherwise>
	</xsl:choose>
	<xsl:text>;}&#10;&#10;</xsl:text>
</xsl:template>

<!-- Process an ownedMember that contains one or more do activities.
	 Only one of these needs to be written out to the Xh__.java file.
-->
<xsl:template name="ownedMemberWithDoActivity">
	<xsl:param name="ownedMember"/>
	<xsl:if test="$ownedMember and not($ownedMember/ownedOperation[@xmi:type='uml:Operation'][@name='act'])">
		<xsl:text>&#9;case </xsl:text>
		<xsl:value-of select="$ownedMember/@name"/>
		<xsl:text>CE:&#10;</xsl:text>
		<xsl:text>&#9;{&#10;</xsl:text>
		<xsl:text>		// Do Activity&#10;</xsl:text>
		<xsl:text>		if (this.hasChildNodes()) {&#10;</xsl:text>
		<xsl:text>			((StateMachineEntity)getFirstChild()).doStateMachine(new Message(ISignal.SIGNAL_DOACTIVITY,null,null,this));&#10;</xsl:text>
		<xsl:text>		}&#10;</xsl:text>
		<xsl:text>		else {&#10;</xsl:text>
		<xsl:text>			System.out.println("Xh</xsl:text>
		<xsl:value-of select="@name"/>
		<xsl:text>: doActivity with no receiver ");&#10;</xsl:text>
		<xsl:text>		}&#10;</xsl:text>
		<xsl:text>&#9;}&#10;</xsl:text>
		<xsl:text>&#9;&#9;break;&#10;</xsl:text>
	</xsl:if>
</xsl:template>

</xsl:stylesheet>
