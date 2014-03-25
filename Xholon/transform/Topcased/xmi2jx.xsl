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
	Transform UML2  to Xh<appName>.java file
	Author: Ken Webb
	Date:   June 30, 2007
	File:   xmi2jx.xsl
	Works with: TOPCASED 1.0.0
-->

<!-- Eclipse, EMF UML -->
<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xmi="http://schema.omg.org/spec/XMI/2.1"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:Ecore="http://www.eclipse.org/uml2/schemas/Ecore/3"
	xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore"
	xmlns:uml="http://www.eclipse.org/uml2/2.1.0/UML"
	xmlns:xi="http://www.w3.org/2001/XInclude"
	xmlns:XholonStereotypes="http:///schemas/XholonStereotypes/_NhBU8Cm9EdyXztXVHo8fCQ/1">
	<!--xmlns:XholonStereotypes="http:///schemas/XholonStereotypes/_6yG0ECZhEdyPzLcl1Gj0vA/0"-->

<xsl:output method="text" omit-xml-declaration="yes" indent="yes"/>

<xsl:param name="rootEnt"/>
<xsl:param name="author"/>
<xsl:param name="dateNow"/>
<xsl:param name="fileName"/>

<!-- root xholon entity (ex: TheSystem) -->
<xsl:variable name="rootEntity"
	select="//uml:Model//packagedElement[@xmi:type='uml:Class'][@name=$rootEnt]
		| //uml:Model//packagedElement[@xmi:type='uml:Component'][@name=$rootEnt]
		| //uml:Model//packagedElement[@xmi:type='uml:StateMachine'][@name=$rootEnt]"/>

<!-- Position in the Topcased type href="" where Java type starts. ex: EDouble -->
<xsl:variable name="jTypePosition">46</xsl:variable>

<!-- Either xmi:XMI or uml:Model may be the root node in the .uml file -->
<xsl:template match="/">
	<xsl:apply-templates select="xmi:XMI | uml:Model"/>
</xsl:template>

<xsl:template match="xmi:XMI">
		<xsl:apply-templates select="uml:Model"/>
</xsl:template>

<xsl:template match="uml:Model">
	<!-- package -->
	<xsl:text>package org.primordion.xholon.xmiappsTc;&#10;&#10;</xsl:text>
	
	<!-- imports -->
	<xsl:text>import org.primordion.xholon.base.IMessage;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.Message;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.ISignal;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.StateMachineEntity;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.XholonWithPorts;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.IXholon;&#10;</xsl:text>
	<xsl:text>import org.primordion.xholon.base.IIntegration;&#10;</xsl:text>
	<!-- any additional imports -->
	<xsl:for-each select="//uml:Model//packagedElement[@xmi:type='uml:Class'][../@name='XholonBaseClasses']">
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
		<xsl:text>&#9;&lt;p&gt;Target: Xholon 0.6 http://www.primordion.com/Xholon&lt;/p&gt;&#10;</xsl:text>
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
	<xsl:for-each select="//uml:Model//packagedElement[@xmi:type='uml:Class'][@clientDependency or generalization/general/@href='pathmap://UML_METAMODELS/UML.metamodel.uml#Port']">  <!-- ??? TOPCASED -->
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
	<xsl:value-of select="count(//uml:Model//packagedElement[@xmi:type='uml:Class'][generalization/general/xmi:Extension/referenceExtension[@referentPath='UML Standard Profile::UML2.0 Metamodel::CompositeStructures::Ports::Port']])"/>
	<xsl:text>;&#10;&#10;</xsl:text>
	-->
	<xsl:text>&#10;</xsl:text>
	
	<!-- signals -->
	<xsl:text>// Signals, Events&#10;</xsl:text>
	<xsl:apply-templates select="//uml:Model//packagedElement[@xmi:type='uml:Signal']"/>
	<xsl:text>&#10;</xsl:text>
	
	<!-- variables -->
	<xsl:text>// Variables&#10;</xsl:text>
	<xsl:text>public String roleName = null;&#10;</xsl:text>
	<xsl:apply-templates select="//uml:Model//ownedAttribute[@name]"/>
	<xsl:text>&#10;</xsl:text>
	
	<!-- constructor -->
	<xsl:text>// Constructor&#10;</xsl:text>
	<xsl:text>public Xh</xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text>() {super();}&#10;&#10;</xsl:text>
	
	<!-- variables: setters, getters -->
	<xsl:text>// Setters and Getters&#10;</xsl:text>
	<xsl:text>public void setRoleName(String roleName) {this.roleName = roleName;}&#10;</xsl:text>
	<xsl:text>public String getRoleName() {return roleName;}&#10;&#10;</xsl:text>
	<xsl:apply-templates select="/xmi:XMI/XholonStereotypes:GetterSetter"/>
	
	<!-- initialize -->
	<xsl:text>public void initialize()&#10;</xsl:text>
	<xsl:text>{&#10;</xsl:text>
	<xsl:text>	super.initialize();&#10;</xsl:text>
	<xsl:for-each select="//uml:Model//ownedAttribute[@name]">
		<xsl:if test="../@xmi:type='uml:Class' and not(../@name='XhnParams') and not(../@name='Application')">
			<xsl:if test="(type or @type) and not(@isStatic) and not(@isReadOnly)">
				<xsl:if test="not(upperValue) or upperVale/@value='1'">
					<xsl:if test="defaultValue/@value or defaultValue/@body">
						<xsl:variable name="jType" select="substring(type/@href, $jTypePosition)"/>
						<xsl:text>	</xsl:text>
						<xsl:value-of select="@name"/>
						<xsl:text> = </xsl:text>
						<xsl:choose>
							<xsl:when test="$jType='EBoolean' or $jType='EChar' or $jType='EByte' or $jType='EShort' or $jType='EInt' or $jType='ELong' or $jType='EFloat' or $jType='EDouble' or $jType='EString'">
								<xsl:choose>
									<xsl:when test="$jType='EString' and not(substring(@value,1,1)='&quot;')">
										<xsl:text>"</xsl:text><xsl:value-of select="concat(defaultValue/@value, defaultValue/@body)"/><xsl:text>"</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="concat(defaultValue/@value, defaultValue/@body)"/>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>new </xsl:text>
								<!--<xsl:value-of select="$jType"/>-->
								<xsl:call-template name="outputJavaType">
									<xsl:with-param name="javaType" select="$jType"/>
								</xsl:call-template>
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
	<xsl:for-each select="//uml:Model//ownedOperation[not(../@isAbstract='true')]">
		<xsl:if test="not(@name='postConfigure') and not(@name='preAct') and not(@name='act') and not(@name='postAct') and not(@name='handleNodeSelection')">
			<!--<xsl:variable name="myTypeId" select="@type"/>-->
			<xsl:variable name="javaType" select="substring(ownedParameter[@direction='return']/type/@href, $jTypePosition)"/>
			<xsl:if test="ownedParameter[@direction='return']/type or ownedParameter[not(@direction)] or not(ownedParameter)"> <!-- don't create a Java operation unless it has a return type -->
				<xsl:text>// </xsl:text>
				<xsl:value-of select="../@name"/> <!-- class name -->
				<xsl:text>&#10;</xsl:text>
				<xsl:choose>
					<xsl:when test="@visibility='package'">
						<!-- package visibility -->
					</xsl:when>
					<xsl:when test="not(@visibility)"> <!-- default is 'public' -->
						<xsl:text>public </xsl:text>
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
				<!--<xsl:value-of select="$javaType"/>-->
				<xsl:call-template name="outputJavaType">
					<xsl:with-param name="javaType" select="$javaType"/>
				</xsl:call-template>
				<xsl:text> </xsl:text>
				<xsl:value-of select="@name"/>
				<xsl:text>(</xsl:text>
				<!--<xsl:for-each select="ownedParameter[@direction != 'return']">-->
				<xsl:for-each select="ownedParameter[not(@direction)]">
					<xsl:variable name="javaType" select="substring(type/@href, $jTypePosition)"/>
					<!--<xsl:value-of select="$javaType"/>-->
					<xsl:call-template name="outputJavaType">
						<xsl:with-param name="javaType" select="$javaType"/>
					</xsl:call-template>
					<xsl:text> </xsl:text>
					<xsl:value-of select="@name"/>
					<xsl:if test="upperValue and upperValue/@value > '1'">
						<xsl:text>[]</xsl:text>
					</xsl:if>
					<!-- add comma between 2 or more params -->
					<xsl:if test="last() > position()">
						<xsl:text>, </xsl:text>
					</xsl:if>
				</xsl:for-each>
				<xsl:text>)</xsl:text>
				<xsl:text>&#10;{&#10;</xsl:text>
				
				<xsl:if test="@method">
					<xsl:value-of select="../ownedBehavior[@xmi:id=current()/@method]/body"/>
					<xsl:text>&#10;</xsl:text>
				</xsl:if>
				<!-- deprecated -->
				<!--<xsl:if test="ownedComment">
					<xsl:if test="string-length(ownedComment/body)>0">
						<xsl:value-of select="ownedComment/body"/>
						<xsl:text>&#10;</xsl:text>
					</xsl:if>
				</xsl:if>-->
				
				<xsl:text>}</xsl:text>
				<xsl:text>&#10;</xsl:text>
			</xsl:if>
			<xsl:text>&#10;</xsl:text>
		</xsl:if>
	</xsl:for-each>
	
	<!-- preConfigure -->
	<xsl:if test="//uml:Model//ownedOperation[@name='preConfigure'][not(@isAbstract='true')]">
		<xsl:text>public void preConfigure()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:text>	switch(xhc.getId()) {&#10;</xsl:text>
		<xsl:apply-templates select="//uml:Model//ownedOperation[@name='preConfigure']"/>
		<xsl:text>	default:&#10;</xsl:text>
		<xsl:text>		break;&#10;</xsl:text>
		<xsl:text>	}&#10;</xsl:text>
		<xsl:text>	super.preConfigure();&#10;</xsl:text>
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:if>
	
	<!-- configure -->
	<xsl:if test="//uml:Model//ownedOperation[@name='configure'][not(@isAbstract='true')]">
		<xsl:text>public void configure()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:text>	switch(xhc.getId()) {&#10;</xsl:text>
		<xsl:apply-templates select="//uml:Model//ownedOperation[@name='configure']"/>
		<xsl:text>	default:&#10;</xsl:text>
		<xsl:text>		break;&#10;</xsl:text>
		<xsl:text>	}&#10;</xsl:text>
		<xsl:text>	super.configure();&#10;</xsl:text>
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:if>
	
	<!-- postConfigure -->
	<xsl:if test="//uml:Model//ownedOperation[@name='postConfigure'][not(@isAbstract='true')]">
		<xsl:text>public void postConfigure()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:text>	switch(xhc.getId()) {&#10;</xsl:text>
		<xsl:apply-templates select="//uml:Model//ownedOperation[@name='postConfigure']"/>
		<xsl:text>	default:&#10;</xsl:text>
		<xsl:text>		break;&#10;</xsl:text>
		<xsl:text>	}&#10;</xsl:text>
		<xsl:text>	super.postConfigure();&#10;</xsl:text>
		<xsl:text>}&#10;&#10;</xsl:text>
	</xsl:if>
	
	<!-- preAct -->
	<xsl:if test="//uml:Model//ownedOperation[@name='preAct'][not(@isAbstract='true')]">
		<xsl:text>public void preAct()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:text>	switch(xhc.getId()) {&#10;</xsl:text>
		<xsl:apply-templates select="//uml:Model//ownedOperation[@name='preAct']"/>
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
	<xsl:apply-templates select="//uml:Model//ownedOperation[@name='act']"/>
	
	<xsl:for-each select="//uml:Model//packagedElement[@xmi:type='uml:Class'][ownedBehavior//doActivity]">
	<xsl:call-template name="packagedElementWithDoActivity">
		<xsl:with-param name="packagedElement" select="."/>
	</xsl:call-template>
	</xsl:for-each>
	
	<xsl:text>	default:&#10;</xsl:text>
	<xsl:text>		break;&#10;</xsl:text>
	<xsl:text>	}&#10;</xsl:text>
	<xsl:text>	super.act();&#10;</xsl:text>
	<xsl:text>}&#10;&#10;</xsl:text>
	
	<!-- postAct -->
	<xsl:if test="//uml:Model//ownedOperation[@name='postAct'][not(@isAbstract='true')]">
		<xsl:text>public void postAct()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:text>	switch(xhc.getId()) {&#10;</xsl:text>
		<xsl:apply-templates select="//uml:Model//ownedOperation[@name='postAct']"/>
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
	<xsl:apply-templates select="//uml:Model//effect[@xmi:type='uml:OpaqueBehavior']"/>
	<xsl:apply-templates select="//uml:Model//entry[@xmi:type='uml:OpaqueBehavior']"/>
	<xsl:apply-templates select="//uml:Model//exit[@xmi:type='uml:OpaqueBehavior']"/>
	<xsl:for-each select="//uml:Model//doActivity[@xmi:type='uml:OpaqueBehavior']">
		<xsl:text>&#9;case </xsl:text>
		<xsl:call-template name="outputUniqueId">
			<xsl:with-param name="xmiId" select="@xmi:id"/>
		</xsl:call-template>
		<xsl:text>: // </xsl:text>
		<xsl:text>doActivity </xsl:text>
		<xsl:value-of select="../@name"/>
		<xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;{&#10;</xsl:text>
		
				
		<xsl:if test="@method">
			<xsl:value-of select="../ownedBehavior[@xmi:id=current()/@method]/body"/>
			<xsl:text>&#10;</xsl:text>
		</xsl:if>
		<!--<xsl:apply-templates select="ownedComment"/>-->
		
		<xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;}&#10;</xsl:text>
		<xsl:text>&#9;&#9;break;&#10;</xsl:text>
	</xsl:for-each>
	<xsl:text>&#9;default:&#10;</xsl:text>
	<xsl:text>&#9;&#9;System.out.println("Xh</xsl:text>
	<xsl:value-of select="//uml:Model/@name"/>
	<xsl:text>: performActivity() unknown Activity " + activityId);&#10;</xsl:text>
	<xsl:text>&#9;&#9;break;&#10;</xsl:text>
	<xsl:text>&#9;}&#10;</xsl:text>
	<xsl:text>}&#10;</xsl:text>
	<xsl:text>&#10;</xsl:text>
	
	<!-- performGuard -->
	<xsl:text>public boolean performGuard(int activityId, IMessage msg)&#10;</xsl:text>
	<xsl:text>{&#10;</xsl:text>
	<xsl:text>&#9;switch (activityId) {&#10;</xsl:text>
	<xsl:apply-templates select="//uml:Model//ownedRule[../@guard]"/>
	<xsl:text>&#9;default:&#10;</xsl:text>
	<xsl:text>&#9;&#9;System.out.println("Xh</xsl:text>
	<xsl:value-of select="//uml:Model/@name"/>
	<xsl:text>: performGuard() unknown Activity " + activityId);&#10;</xsl:text>
	<xsl:text>&#9;&#9;return false;&#10;</xsl:text>
	<xsl:text>&#9;}&#10;</xsl:text>
	<xsl:text>}&#10;&#10;</xsl:text>
	
	<!-- handleNodeSelection -->
	<xsl:if test="//uml:Model//ownedOperation[@name='handleNodeSelection'][not(@isAbstract='true')]">
		<xsl:text>public Object handleNodeSelection()&#10;</xsl:text>
		<xsl:text>{&#10;</xsl:text>
		<xsl:text>	switch(xhc.getId()) {&#10;</xsl:text>
		<xsl:apply-templates select="//uml:Model//ownedOperation[@name='handleNodeSelection']"/>
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
	<!--<xsl:apply-templates select="ownedComment"/>-->
	<xsl:value-of select="body"/>
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
	<xsl:apply-templates select="body"/>
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
	<xsl:apply-templates select="body"/>
	<xsl:text>&#10;</xsl:text>
	<xsl:text>&#9;&#9;&#9;break;&#10;</xsl:text>
</xsl:template>

<xsl:template match="ownedOperation">
	<xsl:if test="not(@isAbstract='true')">
		<xsl:text>&#9;case </xsl:text>
		<xsl:value-of select="../@name"/>
		<xsl:text>CE:&#10;</xsl:text>
		<xsl:text>&#9;{&#10;</xsl:text>
		
		<xsl:if test="@method">
			<xsl:value-of select="../ownedBehavior[@xmi:id=current()/@method]/body"/>
			<xsl:text>&#10;</xsl:text>
		</xsl:if>
		<!--<xsl:apply-templates select="ownedComment"/>-->
		
		<xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;}&#10;</xsl:text>
		<!-- if the Java code contains "return ", then don't output "break;" (TODO check return type instead)-->
		<!--<xsl:if test="not(contains(ownedComment/body, 'return '))">-->
		<xsl:if test="not(contains(../ownedBehavior[@xmi:id=current()/@method]/body, 'return'))">
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
		
		<xsl:if test="@method">
			<xsl:value-of select="../ownedBehavior[@xmi:id=current()/@method]/body"/>
			<xsl:text>&#10;</xsl:text>
		</xsl:if>
		<!--<xsl:apply-templates select="ownedComment"/>-->
		
		<xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;}&#10;</xsl:text>
		<!-- if the Java code contains "return ", then don't output "break;" (TODO check return type instead)-->
		<!--<xsl:if test="not(contains(ownedComment/@body, 'return '))">-->
		<xsl:if test="not(contains(../ownedBehavior[@xmi:id=current()/@method]/body, 'return'))">
			<xsl:text>&#9;&#9;break;&#10;</xsl:text>
		</xsl:if>
	</xsl:if>
</xsl:template>

<xsl:template match="ownedComment">
	<xsl:choose>
		<xsl:when test="string-length(body)>0">
			<xsl:value-of select="body"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>// no action specified</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="body">
	<xsl:choose>
		<xsl:when test="string-length(.)>0">
			<xsl:value-of select="."/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>// no action specified</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="ownedRule">
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
		<xsl:when test="string-length(body)>0">
			<xsl:choose>
				<xsl:when test="body='else'">
					<xsl:text>&#9;&#9;&#9;return true; // else</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>&#9;&#9;&#9;return </xsl:text>
					<xsl:value-of select="body"/>
					<xsl:text>;</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>&#9;&#9;&#9;return true; // no guard action specified</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="packagedElement[@xmi:type='uml:Signal']">
	<xsl:param name="sigId" select="@xmi:id"/>
	<xsl:text>public static final int </xsl:text>
	<xsl:value-of select="@name"/>
	<xsl:text> = </xsl:text>
	<!-- Signal ID will be either: (1) an owned attribute of the Signal, (2) owned attribute of a Port -->
	<xsl:value-of select="ownedAttribute/defaultValue/@value"/>
	<xsl:value-of select="//uml:Model//ownedAttribute[@type=$sigId]/defaultValue/@value"/>
	<xsl:value-of select="ownedAttribute/defaultValue/@body"/>
	<xsl:value-of select="//uml:Model//ownedAttribute[@type=$sigId]/defaultValue/@body"/>
	<xsl:text>;&#10;</xsl:text>
</xsl:template>

<xsl:template match="ownedAttribute">
	<xsl:if test="../@xmi:type='uml:Class' and not(@aggregation='composite')">
		<!-- Java type will be in a sub-element (type), or in an attribute called type if the type is application-specific. -->
		<xsl:variable name="javaType" select="concat(substring(type/@href, $jTypePosition), //uml:Model//packagedElement[@xmi:id=current()/@type]/@name)"/>
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
				<xsl:call-template name="outputJavaType">
					<xsl:with-param name="javaType" select="$javaType"/>
				</xsl:call-template>
				<xsl:text> </xsl:text>
				<xsl:value-of select="@name"/>
				<!-- [] -->
				<xsl:if test="upperValue/@value > '1'">
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
			<!--<xsl:variable name="getSetId" select="/xmi:XMI/XholonStereotypes:GetterSetter/@xmi:id"/>-->
			<xsl:if test="/xmi:XMI/XholonStereotypes:GetterSetter/@base_Property=current()/@xmi:id">
				<xsl:text>protected static </xsl:text>
				<!--<xsl:value-of select="$javaType"/>-->
				<xsl:call-template name="outputJavaType">
					<xsl:with-param name="javaType" select="$javaType"/>
				</xsl:call-template>
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
				<xsl:when test="$jType='EBoolean' or $jType='EChar' or $jType='EByte' or $jType='EShort' or $jType='EInt' or $jType='ELong' or $jType='EFloat' or $jType='EDouble' or $jType='EString'">
					<xsl:choose>
						<xsl:when test="$jType='EString' and not(substring(@value,1,1)='&quot;')">
							<xsl:text>"</xsl:text><xsl:value-of select="concat(@value, @body)"/><xsl:text>"</xsl:text>
						</xsl:when>
						<xsl:when test="$jType='EString' and not(substring(@body,1,1)='&quot;')">
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
			<!--<xsl:value-of select="$jType"/>-->
			<xsl:call-template name="outputJavaType">
				<xsl:with-param name="javaType" select="$jType"/>
			</xsl:call-template>
			<xsl:text>[</xsl:text>
			<xsl:value-of select="@value"/>
			<xsl:text>]</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--  match GetterSetter stereotype instances -->
<xsl:template match="XholonStereotypes:GetterSetter">
	<xsl:call-template name="outputGetterSetter">
		<xsl:with-param name="gsAttribute" select="//uml:Model//ownedAttribute[@xmi:id=current()/@base_Property]"/>
	</xsl:call-template>
</xsl:template>

<xsl:template name="outputSignalName">
	<xsl:param name="signalEventId"/>
	<xsl:param name="signalId" select="//uml:Model//packagedElement[@xmi:id=$signalEventId]/@signal"/>
	<xsl:value-of select="//uml:Model//packagedElement[@xmi:id=$signalId]/@name"/>
</xsl:template>

<xsl:template name="outputImport">
	<xsl:param name="importClass"/>
	<xsl:text>import org.primordion.xholon.base.</xsl:text>
	<xsl:value-of select="$importClass/@name"/>
	<xsl:text>;&#10;</xsl:text>
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
		<xsl:when test="not($javaType)">
			<xsl:text>void</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$javaType"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name="outputGetterSetter">
	<xsl:param name="gsAttribute"/>
	<xsl:variable name="gsJavaType" select="substring($gsAttribute/type/@href, $jTypePosition)"/>
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
		<xsl:call-template name="outputJavaType">
			<xsl:with-param name="javaType" select="$gsJavaType"/>
		</xsl:call-template>
		<xsl:text> </xsl:text>
		<xsl:value-of select="$gsAttribute/@name"/>
		<xsl:if test="$gsAttribute/@isStatic">
			<xsl:text>Arg</xsl:text>
		</xsl:if>
		<xsl:if test="$gsAttribute/upperValue/@value > '1'">
			<xsl:text>[]</xsl:text>
		</xsl:if>
		<xsl:text>) {</xsl:text>
		<xsl:if test="not($gsAttribute/@isStatic)">
			<xsl:text>this.</xsl:text>
		</xsl:if>
		<xsl:choose>
			<xsl:when test="$gsAttribute/ancestor::packagedElement[@name='XhnParams']">
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
	<xsl:call-template name="outputJavaType">
		<xsl:with-param name="javaType" select="$gsJavaType"/>
	</xsl:call-template>
	<xsl:if test="$gsAttribute/upperValue/@value > '1'">
		<xsl:text>[]</xsl:text>
	</xsl:if>
	<xsl:text> get</xsl:text>
	<xsl:value-of select="translate(substring($gsAttribute/@name,1,1), 'abcdefghijklmnopqrstuvwxyz_', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ_')"></xsl:value-of>
	<xsl:value-of select="substring($gsAttribute/@name,2)"/>
	
	<!-- val -->
	<xsl:if test="$gsAttribute/@name='val'">
		<xsl:choose>
			<xsl:when test="$gsJavaType='EBoolean'"> <!-- ex: false -->
				<xsl:text>_boolean</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='EChar'"> <!-- ex: 'q' -->
				<xsl:text>_char</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='EByte'"> <!-- ex: -128 -->
				<xsl:text>_byte</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='EShort'"> <!-- ex: 32767 -->
				<xsl:text>_short</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='EInt'"> <!-- ex: 123456 -->
				<xsl:text>_int</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='ELong'"> <!-- ex: 9223372036854775807L -->
				<xsl:text>_long</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='EFloat'"> <!-- ex: 123.45f -->
				<xsl:text>_float</xsl:text>
			</xsl:when>
			<xsl:when test="$gsJavaType='EDouble'"> <!-- ex: 1234567890.9876543210 -->
				<!--<xsl:text>_double</xsl:text>-->
			</xsl:when>
			<xsl:when test="$gsJavaType='EString'"> <!-- ex: This is a test parameter. -->
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
		<xsl:when test="$gsAttribute/ancestor::packagedElement[@name='XhnParams']">
			<xsl:value-of select="$gsNameFirstLetterLower"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$gsAttribute/@name"/>
		</xsl:otherwise>
	</xsl:choose>
	<xsl:text>;}&#10;&#10;</xsl:text>
</xsl:template>

<!-- Process an packagedElement that contains one or more do activities.
	 Only one of these needs to be written out to the Xh__.java file.
-->
<xsl:template name="packagedElementWithDoActivity">
	<xsl:param name="packagedElement"/>
	<xsl:if test="$packagedElement and not($packagedElement/ownedOperation[@xmi:type='uml:Operation'][@name='act'])">
		<xsl:text>&#9;case </xsl:text>
		<xsl:value-of select="$packagedElement/@name"/>
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

<!-- Output a unique ID. -->
<xsl:template name="outputUniqueId">
	<xsl:param name="xmiId"/>
	<xsl:choose>
		<xsl:when test="string-length($xmiId)>30"> <!-- MagicDraw length is around 37 -->
			<xsl:value-of select="substring(translate($xmiId, '_01', '119'), 32)"/> <!-- MagicDraw -->
		</xsl:when>
		<xsl:otherwise> <!-- Topcased length is around 23 -->
			<xsl:value-of select="substring(translate($xmiId, '_-ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz', '111234567890123456789012345678901234567890123456789012'), 1, 10)"/> <!-- Topcased -->
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>
