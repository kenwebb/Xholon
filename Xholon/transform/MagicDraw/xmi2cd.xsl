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
	Transform XMI to ClassDetails.xml
	Author: Ken Webb
	Date:   November 11, 2005
	File:   xmi2cd.xsl
	Works with: MagicDraw 11.5
	
	Note: This won't work with MagicDraw 10.0
	 - it doesn't seem to have had the partWithPort attribute on <end> elements
	 - if your model was created with 10.0, you will need to delete all connectors and recreate them
	   - do this using MagicDraw 11.5
	 - possibly this problem was corrected with MagicDraw 10.5 or 11.0, but I haven't tried either
	
	TODO
	- check superclasses for ports (NO - not necessary; executing app will do this  ??? )
	  - done using "generalization" template, for immediate superclass only (Sep. 20, 2006)
	- check superclasses for state machines ( ??? )
-->

<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:uml='http://schema.omg.org/spec/UML/2.0'
	xmlns:xmi='http://schema.omg.org/spec/XMI/2.1'
	xmlns:XholonStereotypes='http://www.magicdraw.com/schemas/XholonStereotypes.xmi'>

<xsl:output method="xml" omit-xml-declaration="no" indent="yes"/>

<xsl:param name="rootEnt"/>
<xsl:param name="author"/>
<xsl:param name="dateNow"/>
<xsl:param name="fileName"/>
<xsl:param name="useIPorts"/> <!-- use Java IPort/Port class ? -->

<!-- root xholon entity (ex: TheSystem) -->
<xsl:variable name="rootEntity"
	select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Class'][@name=$rootEnt]
		| /xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Component'][@name=$rootEnt]"/>
<!-- root class entity (ex: XholonClass) -->
<xsl:variable name="rootClassEntity"
	select="/xmi:XMI/uml:Model/ownedMember[@xmi:type='uml:Class']
		| /xmi:XMI/uml:Model/ownedMember[@xmi:type='uml:Component']"/>

<xsl:variable name="portImplementation">
	<xsl:choose>
		<xsl:when test="$useIPorts='usemodeldefault'">
			<xsl:value-of select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Artifact'][@name='XholonModel']/ownedAttribute[@name='portImplementation']/defaultValue/@value"/>
		</xsl:when>
		<xsl:when test="$useIPorts">
			<xsl:value-of select="$useIPorts"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>ixholon</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:variable>

<xsl:template match="xmi:XMI">
	<!--<xsl:message><xsl:value-of select="$portImplementation"/></xsl:message>-->
	<xsl:apply-templates select="uml:Model"/>
</xsl:template>

<xsl:template match="uml:Model">
	<xsl:param name="pureActiveObjId" select="/xmi:XMI/uml:Model//ownedMember[@name='PureActiveObject']/@xmi:id"/>
	<xsl:param name="purePassiveObjId" select="/xmi:XMI/uml:Model//ownedMember[@name='PurePassiveObject']/@xmi:id"/>
	<xsl:param name="xhtypeBehFgsxxxId" select="/xmi:XMI/uml:Model//ownedMember[@name='XhtypeBehFgsxxx']/@xmi:id"/>
	<xsl:param name="xhtypeGridEntityId" select="/xmi:XMI/uml:Model//ownedMember[@name='XhtypeGridEntity']/@xmi:id"/>
	<xsl:param name="xhtypeGridEntityActiveId" select="/xmi:XMI/uml:Model//ownedMember[@name='XhtypeGridEntityActive']/@xmi:id"/>
	<xsl:param name="xhtypeGridEntityActivePassiveId" select="/xmi:XMI/uml:Model//ownedMember[@name='XhtypeGridEntityActivePassive']/@xmi:id"/>
	<xsl:comment>
		<xsl:text>&#10;&#9;</xsl:text>
		<xsl:value-of select="@name"/>
		<xsl:text> application - Class Details&#10;</xsl:text>
		<xsl:text>&#9;Author: </xsl:text><xsl:value-of select="$author"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;Date:   </xsl:text><xsl:value-of select="$dateNow"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;File:   </xsl:text><xsl:value-of select="$fileName"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;Target: Xholon 0.5 http://www.primordion.com/Xholon&#10;</xsl:text>
		<xsl:text>&#9;portImplementation: </xsl:text><xsl:value-of select="$portImplementation"/><xsl:text>&#10;</xsl:text>
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
	<xsl:element name="xholonClassDetails">
	
	<!-- MD 11.5 has stereotype information within the ownedMember element -->
	<xsl:for-each select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Class']
			| /xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Component']">
		<xsl:choose>
		<xsl:when test="xmi:Extension/modelExtension/appliedStereotypeInstance[@classifier=$pureActiveObjId]
			| xmi:Extension/modelExtension/appliedStereotypeInstance/classifier[@xmi:idref=$pureActiveObjId]">
			<!--<xsl:message terminate="no">
				<xsl:text>class(A): </xsl:text>
				<xsl:value-of select="@name"/>
			</xsl:message>-->
			<xsl:element name="{@name}">
				<xsl:attribute name="xhType">
					<xsl:text>XhtypePureActiveObject</xsl:text>
				</xsl:attribute>
				<xsl:if test="not(@isAbstract='true')"> <!-- don't create ports for abstract xholon classes -->
					<xsl:apply-templates select="ownedPort[@isBehavior='true']"/>
				</xsl:if>
				<!--<xsl:apply-templates select="generalization"/>-->
			</xsl:element>
		</xsl:when>
		<xsl:when test="xmi:Extension/modelExtension/appliedStereotypeInstance[@classifier=$purePassiveObjId]
			| xmi:Extension/modelExtension/appliedStereotypeInstance/classifier[@xmi:idref=$purePassiveObjId]">
			<xsl:element name="{@name}">
				<xsl:attribute name="xhType">
					<xsl:text>XhtypePurePassiveObject</xsl:text>
				</xsl:attribute>
			</xsl:element>
		</xsl:when>
		<xsl:when test="xmi:Extension/modelExtension/appliedStereotypeInstance[@classifier=$xhtypeBehFgsxxxId]
			| xmi:Extension/modelExtension/appliedStereotypeInstance/classifier[@xmi:idref=$xhtypeBehFgsxxxId]">
			<!--<xsl:message terminate="no">
				<xsl:text>class(B): </xsl:text>
				<xsl:value-of select="@name"/>
			</xsl:message>-->
			<xsl:element name="{@name}">
				<xsl:attribute name="xhType">
					<xsl:text>XhtypeBehFgsxxx</xsl:text>
				</xsl:attribute>
				<xsl:if test="not(@isAbstract='true')">
					<xsl:apply-templates select="ownedPort[@isBehavior='true']"/>
				</xsl:if>
			</xsl:element>
		</xsl:when>
		<xsl:when test="ownedBehavior[@xmi:type='uml:StateMachine']">
			<!--<xsl:message terminate="no">
				<xsl:text>class(SM): </xsl:text>
				<xsl:value-of select="@name"/>
			</xsl:message>-->
			<xsl:element name="{@name}">
				<xsl:attribute name="xhType">
					<xsl:text>XhtypePureActiveObject</xsl:text>
				</xsl:attribute>
				<xsl:if test="not(@isAbstract='true')">
					<xsl:apply-templates select="ownedPort[@isBehavior='true']"/>
				</xsl:if>
			</xsl:element>
		</xsl:when>
		<xsl:when test="xmi:Extension/modelExtension/appliedStereotypeInstance[@classifier=$xhtypeGridEntityId]
			| xmi:Extension/modelExtension/appliedStereotypeInstance/classifier[@xmi:idref=$xhtypeGridEntityId]">
			<xsl:element name="{@name}">
				<xsl:attribute name="xhType">
					<xsl:text>XhtypeGridEntity</xsl:text>
				</xsl:attribute>
				<xsl:attribute name="implName">
					<xsl:text>org.primordion.xholon.base.GridEntity</xsl:text>
				</xsl:attribute>
			</xsl:element>
		</xsl:when>
		<xsl:when test="xmi:Extension/modelExtension/appliedStereotypeInstance[@classifier=$xhtypeGridEntityActivePassiveId]
			| xmi:Extension/modelExtension/appliedStereotypeInstance/classifier[@xmi:idref=$xhtypeGridEntityActivePassiveId]">
			<xsl:element name="{@name}">
				<xsl:attribute name="xhType">
					<xsl:text>XhtypeGridEntityActivePassive</xsl:text>
				</xsl:attribute>
				<xsl:attribute name="implName">
					<xsl:text>org.primordion.xholon.base.GridEntity</xsl:text>
				</xsl:attribute>
				<xsl:comment>TODO set the instruction to the correct value: Gmt Gvt Gmg Gvg Ght Ghg etc.</xsl:comment>
				<xsl:element name="config">
					<xsl:attribute name="instruction">
						<xsl:text>Gmt</xsl:text>
					</xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:when>
		<!-- MD 12.0 has a stereotype cross reference -->
		<xsl:when test="/xmi:XMI/XholonStereotypes:PureActiveObject[@base_Class=current()/@xmi:id]">
			<xsl:element name="{@name}">
				<xsl:attribute name="xhType">
					<xsl:text>XhtypePureActiveObject</xsl:text>
				</xsl:attribute>
				<xsl:if test="not(@isAbstract='true')">
					<xsl:apply-templates select="ownedPort[@isBehavior='true']"/>
				</xsl:if>
			</xsl:element>			
		</xsl:when>
		<xsl:when test="/xmi:XMI/XholonStereotypes:PurePassiveObject[@base_Class=current()/@xmi:id]">
			<xsl:element name="{@name}">
				<xsl:attribute name="xhType">
					<xsl:text>XhtypePurePassiveObject</xsl:text>
				</xsl:attribute>
			</xsl:element>			
		</xsl:when>
		<xsl:when test="/xmi:XMI/XholonStereotypes:XhtypeBehFgsxxx[@base_Class=current()/@xmi:id]">
			<xsl:element name="{@name}">
				<xsl:attribute name="xhType">
					<xsl:text>XhtypeBehFgsxxx</xsl:text>
				</xsl:attribute>
				<xsl:if test="not(@isAbstract='true')">
					<xsl:apply-templates select="ownedPort[@isBehavior='true']"/>
				</xsl:if>
			</xsl:element>			
		</xsl:when>
		</xsl:choose>
	</xsl:for-each>
	
	<!-- if there are state machine entities, run-time needs to know about this -->
	<!--<xsl:if test="/xmi:XMI/uml:Model//ownedMember[@name='StateMachineEntity']">-->
		<!-- States -->
		<xsl:element name="State">
			<xsl:attribute name="xhType">
				<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="implName">
				<xsl:text>org.primordion.xholon.base.ObservableStateMachineEntity</xsl:text>
			</xsl:attribute>
			<xsl:apply-templates select="/xmi:XMI/uml:Model//subvertex[@xmi:type='uml:State']"/>
		</xsl:element>
		
		<!-- Pseudostates initial -->
		<xsl:element name="PseudostateInitial">
			<xsl:attribute name="xhType">
				<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="implName">
				<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
			</xsl:attribute>
			<xsl:apply-templates select="/xmi:XMI/uml:Model//subvertex[@xmi:type='uml:Pseudostate'][@kind='initial']"/>
		</xsl:element>
		
		<!-- Pseudostates choice -->
		<xsl:element name="PseudostateChoice">
			<xsl:attribute name="xhType">
				<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="implName">
				<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
			</xsl:attribute>
			<xsl:apply-templates select="/xmi:XMI/uml:Model//subvertex[@xmi:type='uml:Pseudostate'][@kind='choice']"/>
		</xsl:element>
		
		<!-- Pseudostates junction -->
		<xsl:element name="PseudostateJunction">
			<xsl:attribute name="xhType">
				<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="implName">
				<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
			</xsl:attribute>
			<xsl:apply-templates select="/xmi:XMI/uml:Model//subvertex[@xmi:type='uml:Pseudostate'][@kind='junction']"/>
		</xsl:element>
		
		<!-- Pseudostates fork -->
		<xsl:element name="PseudostateFork">
			<xsl:attribute name="xhType">
				<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="implName">
				<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
			</xsl:attribute>
			<xsl:apply-templates select="/xmi:XMI/uml:Model//subvertex[@xmi:type='uml:Pseudostate'][@kind='fork']"/>
		</xsl:element>
		
		<!-- Pseudostates join -->
		<xsl:element name="PseudostateJoin">
			<xsl:attribute name="xhType">
				<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="implName">
				<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
			</xsl:attribute>
			<xsl:apply-templates select="/xmi:XMI/uml:Model//subvertex[@xmi:type='uml:Pseudostate'][@kind='join']"/>
		</xsl:element>
		
		<!-- Pseudostates shallowHistory -->
		<xsl:element name="PseudostateShallowHistory">
			<xsl:attribute name="xhType">
				<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="implName">
				<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
			</xsl:attribute>
			<xsl:apply-templates select="/xmi:XMI/uml:Model//subvertex[@xmi:type='uml:Pseudostate'][@kind='shallowHistory']"/>
		</xsl:element>
		
		<!-- Pseudostates deepHistory -->
		<xsl:element name="PseudostateDeepHistory">
			<xsl:attribute name="xhType">
				<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="implName">
				<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
			</xsl:attribute>
			<xsl:apply-templates select="/xmi:XMI/uml:Model//subvertex[@xmi:type='uml:Pseudostate'][@kind='deepHistory']"/>
		</xsl:element>
		
		<!-- Pseudostates entryPoint -->
		<xsl:element name="PseudostateEntryPoint">
			<xsl:attribute name="xhType">
				<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="implName">
				<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
			</xsl:attribute>
			<xsl:apply-templates select="/xmi:XMI/uml:Model//connectionPoint[@xmi:type='uml:Pseudostate'][@kind='entryPoint']"/>
		</xsl:element>
		
		<!-- Pseudostates exitPoint -->
		<xsl:element name="PseudostateExitPoint">
			<xsl:attribute name="xhType">
				<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="implName">
				<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
			</xsl:attribute>
			<xsl:apply-templates select="/xmi:XMI/uml:Model//connectionPoint[@xmi:type='uml:Pseudostate'][@kind='exitPoint']"/>
		</xsl:element>
		
		<!-- Transitions external -->
		<xsl:element name="TransitionExternal">
			<xsl:attribute name="xhType">
				<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="implName">
				<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
			</xsl:attribute>
			<xsl:apply-templates select="/xmi:XMI/uml:Model//transition[@kind='external']"/>
		</xsl:element>
		
		<!-- Transitions internal -->
		<xsl:element name="TransitionInternal">
			<xsl:attribute name="xhType">
				<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="implName">
				<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
			</xsl:attribute>
			<!-- internal transitions don't have target states, so they don't need connectionPoint ports -->
		</xsl:element>
		
		<!-- Transitions local -->
		<xsl:element name="TransitionLocal">
			<xsl:attribute name="xhType">
				<xsl:text>XhtypeStateMachineEntityActive</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="implName">
				<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
			</xsl:attribute>
			<xsl:apply-templates select="/xmi:XMI/uml:Model//transition[@kind='local']"/>
		</xsl:element>
		
		<!-- StateMachineEntity -->
		<xsl:element name="StateMachineEntity">
			<xsl:attribute name="xhType">
				<xsl:text>XhtypeStateMachineEntity</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="implName">
				<xsl:text>org.primordion.xholon.base.StateMachineEntity</xsl:text>
			</xsl:attribute>
		</xsl:element>
		
	<!--</xsl:if>-->
	<!-- run-time needs to know the name and xhtype of root in the class inheritance hierarchy -->
	<xsl:element name="{$rootClassEntity/@name}">
		<xsl:attribute name="xhType">
			<xsl:text>XhtypePureContainer</xsl:text>
		</xsl:attribute>
	</xsl:element>
	</xsl:element>
</xsl:template>

<!-- match start of a port chain
	 ports with @isBehavior='true' also have a single @end
	-->
<xsl:template match="ownedPort[@isBehavior='true']">
	<xsl:param name="endref" select="@end"/>
	<!--<xsl:message terminate="no">
		<xsl:text>ownedPort: </xsl:text>
		<xsl:value-of select="@name"/>
	</xsl:message>-->
	<xsl:choose>
		<xsl:when test="@end">
			<!-- fill in the XPath location path -->
			<xsl:element name="port">
				<xsl:attribute name="name">
					<xsl:text>port</xsl:text>
				</xsl:attribute>
				<xsl:attribute name="index">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
				<xsl:choose>
					<!-- TODO needs to be identical code to what is in "multiple end" -->
					<xsl:when test="$portImplementation='iport'">
						<xsl:attribute name="multiplicity">
							<xsl:choose>
								<xsl:when test="upperValue/@value">
									<xsl:value-of select="upperValue/@value"/>
								</xsl:when>
								<xsl:otherwise>
									<xsl:text>1</xsl:text>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="connector">
							<xsl:text>#xpointer(</xsl:text>
							<xsl:choose>
								<xsl:when test="@visibility='protected'">
									<!-- an internal port, connected to behavior and to an internal part -->
									<!-- select opposite end of connector leading out from this port -->
									<xsl:apply-templates select="/xmi:XMI/uml:Model//end[$endref=@xmi:id]/../end[$endref!=@xmi:id]">
										<xsl:with-param name="seekingCommonAncestor">
											<!--<xsl:value-of select="false()"/>-->
											<xsl:text>false</xsl:text>
										</xsl:with-param>
										<xsl:with-param name="isStartOfChain">
											<!--<xsl:value-of select="true()"/>-->
											<xsl:text>true</xsl:text>
										</xsl:with-param>
									</xsl:apply-templates>
								</xsl:when>
								<xsl:otherwise>
									<!-- select opposite end of connector leading out from this port -->
									<xsl:apply-templates select="/xmi:XMI/uml:Model//end[$endref=@xmi:id]/../end[$endref!=@xmi:id]">
										<xsl:with-param name="seekingCommonAncestor">
											<xsl:text>true</xsl:text>
										</xsl:with-param>
										<xsl:with-param name="isStartOfChain">
											<xsl:text>true</xsl:text>
										</xsl:with-param>
									</xsl:apply-templates>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:text>)</xsl:text>
						</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:element>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="end"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- ownedPort with multiple end -->
<xsl:template match="end[@xmi:idref]">
	<xsl:param name="endref" select="@xmi:idref"/>
	<xsl:param name="portClass" select="../@type"/>
	<xsl:comment><xsl:text> port: </xsl:text><xsl:value-of select="../@name"/><xsl:text> </xsl:text></xsl:comment>
	<xsl:element name="port">
		<xsl:attribute name="name">
			<xsl:text>port</xsl:text>
		</xsl:attribute>
		<xsl:attribute name="index">
			<!--<xsl:value-of select="../@name"/>-->
			<xsl:value-of select="/xmi:XMI/uml:Model//ownedMember[$portClass=@xmi:id]/@name"/>
		</xsl:attribute>
		<!-- don't output roleName for now
		<xsl:attribute name="roleName">
			<xsl:value-of select="../@name"/>
		</xsl:attribute>
		-->
		<xsl:choose>
			<xsl:when test="$portImplementation='iport'">
				<xsl:attribute name="multiplicity">
					<xsl:choose>
						<xsl:when test="../lowerValue/@value">
							<xsl:value-of select="../lowerValue/@value"/>
							<xsl:if test="../upperValue/@value and not(../upperValue/@value=../lowerValue/@value)">
								<xsl:text>..</xsl:text>
								<xsl:value-of select="../upperValue/@value"/>
							</xsl:if>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text>1</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:attribute>
				<xsl:attribute name="isConjugated">
					<xsl:choose>
						<xsl:when test="../@isService='false'"> <!-- a client -->
							<xsl:text>true</xsl:text>
						</xsl:when>
						<xsl:otherwise> <!-- a server -->
							<xsl:text>false</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:attribute>
				<xsl:attribute name="providedInterface"> <!-- Realized Interface -->
					<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedMember[$portClass=@xmi:id]/interfaceRealization"/>
				</xsl:attribute>
				<xsl:attribute name="requiredInterface">
					<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Usage']/client[$portClass=@xmi:idref]"/>
				</xsl:attribute>
				<xsl:element name="portReplication">
					<xsl:attribute name="name">
						<xsl:text>replication</xsl:text>
					</xsl:attribute>
					<xsl:attribute name="index">
						<xsl:text>0</xsl:text>
					</xsl:attribute>
					<xsl:attribute name="connector">
						<xsl:text>#xpointer(</xsl:text>
						
						<!-- Output roleName at start of XPath expression if there is a chance of ambiguity -->
						<xsl:if test="count(../end) > 1">
							<xsl:variable name="partWithPortAmb" select="/xmi:XMI/uml:Model//end[$endref=@xmi:id]/@partWithPort"/>
							<xsl:variable name="roleNameAmb" select="$partWithPortAmb/../../../ownedAttribute[$partWithPortAmb=@xmi:id]/@name"/>
							<xsl:text>.[@roleName='</xsl:text>
							<xsl:value-of select="$roleNameAmb"/>
							<xsl:text>']/</xsl:text>
						</xsl:if>
						
						<xsl:choose>
							<xsl:when test="../@visibility='protected'">
								<!--<xsl:text>*</xsl:text>--> <!-- debug -->
								<!-- an internal port, connected to behavior and to an internal part -->
								<!-- select opposite end of connector leading out from this port -->
								<xsl:apply-templates select="/xmi:XMI/uml:Model//end[$endref=@xmi:id]/../end[$endref!=@xmi:id]">
									<xsl:with-param name="seekingCommonAncestor">
										<xsl:text>false</xsl:text>
									</xsl:with-param>
									<xsl:with-param name="isStartOfChain">
										<xsl:text>true</xsl:text>
									</xsl:with-param>
								</xsl:apply-templates>
							</xsl:when>
							<xsl:otherwise>
								<!--<xsl:text>#</xsl:text>--> <!-- debug -->
								<xsl:apply-templates select="/xmi:XMI/uml:Model//end[$endref=@xmi:id]/../end[$endref!=@xmi:id]">
									<xsl:with-param name="seekingCommonAncestor">
										<xsl:text>true</xsl:text>
									</xsl:with-param>
									<xsl:with-param name="isStartOfChain">
										<xsl:text>true</xsl:text>
									</xsl:with-param>
								</xsl:apply-templates>
							</xsl:otherwise>
						</xsl:choose>
					<xsl:text>)</xsl:text>
					</xsl:attribute>
				</xsl:element>
			</xsl:when>
			<xsl:otherwise> <!-- $portImplementation='ixholon' -->
				<xsl:attribute name="connector">
					<xsl:text>#xpointer(</xsl:text>
							<xsl:choose>
								<xsl:when test="../@visibility='protected'"> <!-- added Jan 24, 2007 -->
									<!-- an internal port, connected to behavior and to an internal part -->
									<!-- select opposite end of connector leading out from this port -->
									<xsl:apply-templates select="/xmi:XMI/uml:Model//end[$endref=@xmi:id]/../end[$endref!=@xmi:id]">
										<xsl:with-param name="seekingCommonAncestor">
											<!--<xsl:value-of select="false()"/>-->
											<xsl:text>false</xsl:text>
										</xsl:with-param>
										<xsl:with-param name="isStartOfChain">
											<!--<xsl:value-of select="true()"/>-->
											<xsl:text>true</xsl:text>
										</xsl:with-param>
									</xsl:apply-templates>
								</xsl:when>
								<xsl:otherwise>
									<!-- select opposite end of connector leading out from this port -->
									<xsl:apply-templates select="/xmi:XMI/uml:Model//end[$endref=@xmi:id]/../end[$endref!=@xmi:id]">
										<xsl:with-param name="seekingCommonAncestor">
											<xsl:text>true</xsl:text>
										</xsl:with-param>
										<xsl:with-param name="isStartOfChain">
											<xsl:text>true</xsl:text>
										</xsl:with-param>
									</xsl:apply-templates>
								</xsl:otherwise>
							</xsl:choose>
				<xsl:text>)</xsl:text>
				</xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:element>
</xsl:template>

<!-- match subsequent ports in a relay chain -->
<xsl:template match="ownedPort">
	<xsl:param name="seekingCommonAncestor"/>
	<xsl:param name="isStartOfChain"/>
	<xsl:param name="endref"/>
	<!-- select opposite end of connector leading out from this port -->
	<!--<xsl:text>P[</xsl:text>--> <!-- debug -->
	<!--<xsl:value-of select="$seekingCommonAncestor"/>
	<xsl:text>*</xsl:text>
	<xsl:value-of select="$isStartOfChain"/>
	<xsl:text>*</xsl:text>
	<xsl:value-of select="$endref"/>
	<xsl:text>]</xsl:text>-->
	<!--<xsl:message terminate="no">
		<xsl:text>Port: </xsl:text>
		<xsl:value-of select="@name"/>
	</xsl:message>-->
	<xsl:apply-templates select="/xmi:XMI/uml:Model//end[$endref=@xmi:id]/../end[$endref!=@xmi:id]">
		<xsl:with-param name="seekingCommonAncestor">
			<xsl:value-of select="$seekingCommonAncestor"/>
		</xsl:with-param>
		<xsl:with-param name="isStartOfChain">
			<xsl:value-of select="$isStartOfChain"/>
		</xsl:with-param>
	</xsl:apply-templates>
</xsl:template>

<!-- match connector end -->
<xsl:template match="end">
	<xsl:param name="seekingCommonAncestor"/>
	<xsl:param name="isStartOfChain"/>
	<!--<xsl:param name="portref" select="@role"/>-->
	<xsl:param name="endref" select="@xmi:id"/>
	<xsl:variable name="myPortTypeId" select="/xmi:XMI/uml:Model//ownedPort[@xmi:id=current()/@role][@isBehavior='true']/@type"/>
	<xsl:choose>
		<xsl:when test="../@kind='assembly'">
			<xsl:text>ancestor::</xsl:text>
			<xsl:value-of select="../../@name"/>
			<xsl:text>/</xsl:text>
			<!--<xsl:text>1</xsl:text>--> <!-- debug -->
			<!--<xsl:message terminate="no">
				<xsl:text>1</xsl:text>
			</xsl:message>-->
			<xsl:call-template name="outputPortzOwningClass">
				<xsl:with-param name="connectorEnd" select="."/>
			</xsl:call-template>
			<xsl:choose>
				<xsl:when test="$myPortTypeId"> <!-- end of chain -->
					<!--<xsl:text>2</xsl:text>--> <!-- debug -->
					<!--<xsl:message terminate="no">
						<xsl:text>2</xsl:text>
					</xsl:message>-->
					<xsl:if test="$portImplementation='iport'">
						<!--<xsl:text>3</xsl:text>--> <!-- debug -->
						<xsl:call-template name="outputPortAndReplication">
							<xsl:with-param name="portTypeId" select="$myPortTypeId"/>
						</xsl:call-template>
					</xsl:if>
				</xsl:when>
				<xsl:otherwise>
					<!--<xsl:text>4</xsl:text>--> <!-- debug -->
					<!--<xsl:message terminate="no">
						<xsl:text>4</xsl:text>
						<xsl:value-of select="$endref"/>
					</xsl:message>-->
					<xsl:variable name="oPort" select="/xmi:XMI/uml:Model//ownedPort[@xmi:id=current()/@role]"></xsl:variable>
					<xsl:choose>
						<xsl:when test="count($oPort/end) > 2">
							<!-- <ownedPort> has 3 or more <end> elements
							This is not the end of the chain, so one of these <end> must be of kind "delegation" -->
							<xsl:for-each select="$oPort/end">
								<xsl:if test="/xmi:XMI/uml:Model//ownedConnector[@kind='delegation']/end[@xmi:id=current()/@xmi:idref]">
									<xsl:apply-templates select="$oPort">
										<xsl:with-param name="seekingCommonAncestor">
											<xsl:text>false</xsl:text>
										</xsl:with-param>
										<xsl:with-param name="isStartOfChain">
											<xsl:text>false</xsl:text>
										</xsl:with-param>
										<xsl:with-param name="endref">
											<xsl:value-of select="@xmi:idref"/>
										</xsl:with-param>
									</xsl:apply-templates>
								</xsl:if>
							</xsl:for-each>
						</xsl:when>
						<xsl:otherwise>
							<xsl:apply-templates select="$oPort">
								<xsl:with-param name="seekingCommonAncestor">
									<xsl:text>false</xsl:text>
								</xsl:with-param>
								<xsl:with-param name="isStartOfChain">
									<xsl:text>false</xsl:text>
								</xsl:with-param>
								<xsl:with-param name="endref">
									<xsl:value-of select="/xmi:XMI/uml:Model//end[$endref=@xmi:idref]/../end[$endref!=@xmi:idref]/@xmi:idref"/>
								</xsl:with-param>
							</xsl:apply-templates>							
						</xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise> <!-- kind='delegation' -->
			<!--<xsl:text>5</xsl:text>--> <!-- debug -->
			<!--<xsl:message terminate="no">
				<xsl:text>5</xsl:text>
			</xsl:message>-->
			<xsl:choose>
				<xsl:when test="$seekingCommonAncestor='false'"> <!-- going down -->
					<!--<xsl:text>6</xsl:text>--> <!-- debug -->
					<!--<xsl:message terminate="no">
						<xsl:text>6</xsl:text>
					</xsl:message>-->
					<xsl:if test="$isStartOfChain='false'">
						<xsl:text>/</xsl:text>
					</xsl:if>
					<xsl:call-template name="outputPortzOwningClass">
						<xsl:with-param name="connectorEnd" select="."/>
					</xsl:call-template>
					
					<xsl:choose>
						<xsl:when test="$myPortTypeId"> <!-- end of chain -->
							<xsl:if test="$portImplementation='iport'">
								<xsl:call-template name="outputPortAndReplication">
									<xsl:with-param name="portTypeId" select="$myPortTypeId"/>
								</xsl:call-template>
							</xsl:if>
						</xsl:when>

						<xsl:otherwise>
							<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedPort[@xmi:id=current()/@role]">
								<xsl:with-param name="seekingCommonAncestor">
									<xsl:text>false</xsl:text>
								</xsl:with-param>
								<xsl:with-param name="isStartOfChain">
									<xsl:text>false</xsl:text>
								</xsl:with-param>
								<xsl:with-param name="endref">
									<xsl:value-of select="/xmi:XMI/uml:Model//end[$endref=@xmi:idref]/../end[$endref!=@xmi:idref]/@xmi:idref"/>
								</xsl:with-param>
							</xsl:apply-templates>
						</xsl:otherwise>
					</xsl:choose>

				</xsl:when>
				
				<xsl:otherwise> <!-- $seekingCommonAncestor='true' -->
					<!--<xsl:text>7</xsl:text>--> <!-- debug -->
					<!--<xsl:message terminate="no">
						<xsl:text>7</xsl:text>
					</xsl:message>-->
					<xsl:choose>
						<xsl:when test="/xmi:XMI/uml:Model//ownedPort[@xmi:id=current()/@role]/@isBehavior=true()"> <!-- end of chain -->
							<!--<xsl:text>A</xsl:text>--> <!-- debug -->
							<!--<xsl:message terminate="no">
								<xsl:text>A</xsl:text>
							</xsl:message>-->
							<xsl:for-each select="/xmi:XMI/uml:Model//ownedPort[@xmi:id=current()/@role][@isBehavior=true()]">
								<xsl:text>ancestor::</xsl:text>
								<xsl:value-of select="../@name"/>
							</xsl:for-each>
						</xsl:when>
						<xsl:otherwise> <!-- going up; this is a relay port -->
							<!--<xsl:text>B</xsl:text>--> <!-- debug -->
							<!--<xsl:message terminate="no">
								<xsl:text>B</xsl:text>
							</xsl:message>-->
							<!-- don't output anything -->
							<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedPort[@xmi:id=current()/@role]">
								<xsl:with-param name="seekingCommonAncestor">
									<xsl:value-of select="$seekingCommonAncestor"/>
								</xsl:with-param>
								<xsl:with-param name="isStartOfChain">
									<xsl:text>false</xsl:text>
								</xsl:with-param>
								<xsl:with-param name="endref">
									<xsl:value-of select="/xmi:XMI/uml:Model//end[$endref=@xmi:idref]/../end[$endref!=@xmi:idref]/@xmi:idref"/>
								</xsl:with-param>
							</xsl:apply-templates>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="interfaceRealization">
	<xsl:param name="supplierId" select="supplier/@xmi:idref"/>
	<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Interface'][@xmi:id=$supplierId]"/>
</xsl:template>

<xsl:template match="ownedMember[@xmi:type='uml:Interface']">
	<xsl:for-each select="ownedAttribute[@xmi:type='uml:Property']">
		<xsl:variable name="signalId" select="@type"/>
		<xsl:value-of select="/xmi:XMI/uml:Model//ownedMember[@xmi:id=$signalId]/@name"/>
		<xsl:text>,</xsl:text>
	</xsl:for-each>
</xsl:template>

<xsl:template match="ownedMember[@xmi:type='uml:Usage']/client">
	<xsl:param name="supplierId" select="../supplier/@xmi:idref"/>
	<xsl:apply-templates select="/xmi:XMI/uml:Model//ownedMember[@xmi:type='uml:Interface'][@xmi:id=$supplierId]"/>
</xsl:template>

<xsl:template match="subvertex[@xmi:type='uml:State']">
	<xsl:apply-templates select="@outgoing"/>
	<xsl:apply-templates select="outgoing"/>
</xsl:template>

<xsl:template match="subvertex[@xmi:type='uml:Pseudostate'][@kind='initial']">
	<xsl:apply-templates select="@outgoing"/>
	<xsl:apply-templates select="outgoing"/>
</xsl:template>

<xsl:template match="subvertex[@xmi:type='uml:Pseudostate'][@kind='choice']">
	<xsl:apply-templates select="outgoing"/>
</xsl:template>

<xsl:template match="subvertex[@xmi:type='uml:Pseudostate'][@kind='junction']">
	<xsl:apply-templates select="@outgoing"/>
	<xsl:apply-templates select="outgoing"/>
</xsl:template>

<xsl:template match="subvertex[@xmi:type='uml:Pseudostate'][@kind='fork']">
	<xsl:apply-templates select="@outgoing"/>
	<xsl:apply-templates select="outgoing"/>
</xsl:template>

<xsl:template match="subvertex[@xmi:type='uml:Pseudostate'][@kind='join']">
	<xsl:apply-templates select="outgoing"/>
</xsl:template>

<xsl:template match="subvertex[@xmi:type='uml:Pseudostate'][@kind='shallowHistory']">
	<xsl:apply-templates select="@outgoing"/>
	<xsl:apply-templates select="outgoing"/>
</xsl:template>

<xsl:template match="subvertex[@xmi:type='uml:Pseudostate'][@kind='deepHistory']">
	<xsl:apply-templates select="@outgoing"/>
	<xsl:apply-templates select="outgoing"/>
</xsl:template>

<xsl:template match="connectionPoint[@xmi:type='uml:Pseudostate'][@kind='entryPoint']">
	<xsl:apply-templates select="@outgoing"/>
	<xsl:apply-templates select="outgoing"/>
</xsl:template>

<xsl:template match="connectionPoint[@xmi:type='uml:Pseudostate'][@kind='exitPoint']">
	<xsl:apply-templates select="@outgoing"/>
	<xsl:apply-templates select="outgoing"/>
</xsl:template>

<xsl:template match="@outgoing">
	<xsl:param name="idref" select="."/>
	<xsl:param name="sourceref" select="../@xmi:id"/>
	<xsl:for-each select="/xmi:XMI/uml:Model//transition[@xmi:id=$idref]">
		<xsl:element name="port">
			<xsl:attribute name="name">
				<xsl:text>cnpt</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="index">
				<xsl:text>CNPT_OUTGOING</xsl:text>
				<xsl:value-of select="position()"/>
			</xsl:attribute>
			<xsl:attribute name="connector">
				<xsl:text>#xpointer(</xsl:text>
				<xsl:text>.[@uid=&apos;</xsl:text>
				<xsl:call-template name="outputUniqueId">
					<xsl:with-param name="xmiId" select="$sourceref"/>
				</xsl:call-template>
				<xsl:text>&apos;]/ancestor::StateMachine/descendant::Transition[@uid=&apos;</xsl:text>
				<xsl:call-template name="outputUniqueId">
					<xsl:with-param name="xmiId" select="@xmi:id"/>
				</xsl:call-template>
				<xsl:text>&apos;])</xsl:text>
			</xsl:attribute>
		</xsl:element>
	</xsl:for-each>
</xsl:template>

<xsl:template match="outgoing">
	<xsl:param name="idref" select="@xmi:idref"/>
	<xsl:param name="sourceref" select="../@xmi:id"/>
	<xsl:for-each select="/xmi:XMI/uml:Model//transition[@xmi:id=$idref]">
		<xsl:element name="port">
			<xsl:attribute name="name">
				<xsl:text>cnpt</xsl:text>
			</xsl:attribute>
			<xsl:attribute name="index">
				<xsl:text>CNPT_OUTGOING</xsl:text>
				<xsl:choose>
					<xsl:when test="string-length(@name)=0">
						<xsl:value-of select="position()"/> <!-- always gives 1; should be 1 2 3 4 5 -->
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="substring(@name, string-length(@name), 1)"/> <!-- get last char -->				
					</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
			<xsl:attribute name="connector">
				<xsl:text>#xpointer(</xsl:text>
				<xsl:text>.[@uid=&apos;</xsl:text>
				<xsl:call-template name="outputUniqueId">
					<xsl:with-param name="xmiId" select="$sourceref"/>
				</xsl:call-template>
				<xsl:text>&apos;]/ancestor::StateMachine/descendant::Transition[@uid=&apos;</xsl:text>
				<xsl:call-template name="outputUniqueId">
					<xsl:with-param name="xmiId" select="@xmi:id"/>
				</xsl:call-template>
				<xsl:text>&apos;])</xsl:text>
			</xsl:attribute>
		</xsl:element>
	</xsl:for-each>
</xsl:template>

<xsl:template match="transition">
	<xsl:param name="targetRef" select="@target"/>
	<xsl:param name="targetType" select="/xmi:XMI/uml:Model//subvertex[@xmi:id=$targetRef]/@xmi:type
		| /xmi:XMI/uml:Model//connectionPoint[@xmi:id=$targetRef]/@xmi:type
		| /xmi:XMI/uml:Model//connection[@xmi:id=$targetRef]/@xmi:type"/>
	<xsl:param name="targetKind" select="/xmi:XMI/uml:Model//subvertex[@xmi:id=$targetRef]/@kind
		| /xmi:XMI/uml:Model//connectionPoint[@xmi:id=$targetRef]/@kind"/>
	
	<!--<xsl:text>&#9;cnpt[CNPT_TARGET]=&quot;#xpointer(</xsl:text>-->
	<xsl:element name="port">
		<xsl:attribute name="name">
			<xsl:text>cnpt</xsl:text>
		</xsl:attribute>
		<xsl:attribute name="index">
			<xsl:text>CNPT_TARGET</xsl:text>
		</xsl:attribute>
		<xsl:attribute name="connector">
			<xsl:text>#xpointer(.[@uid=&apos;</xsl:text>
			<xsl:call-template name="outputUniqueId">
				<xsl:with-param name="xmiId" select="@xmi:id"/>
			</xsl:call-template>
			<xsl:text>&apos;]/ancestor::StateMachine/descendant::</xsl:text>
			<!-- State or PseudostateChoice or other pseudostate -->
			<xsl:choose>
				<xsl:when test="$targetType='uml:ConnectionPointReference'">
					<xsl:text>PseudostateEntryPoint</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="substring-after($targetType, 'uml:')"/>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="$targetKind='choice'">
					<xsl:text>Choice</xsl:text>
				</xsl:when>
				<xsl:when test="$targetKind='junction'">
					<xsl:text>Junction</xsl:text>
				</xsl:when>
				<xsl:when test="$targetKind='terminate'">
					<xsl:text>Terminate</xsl:text>
				</xsl:when>
				<xsl:when test="$targetKind='entryPoint'">
					<xsl:text>EntryPoint</xsl:text>
				</xsl:when>
				<xsl:when test="$targetKind='exitPoint'">
					<xsl:text>ExitPoint</xsl:text>
				</xsl:when>
			</xsl:choose>
			<xsl:text>[@uid=&apos;</xsl:text>
			<xsl:choose>
				<xsl:when test="$targetType='uml:ConnectionPointReference'">
					<xsl:variable name="connectionEntryId" select="$targetType/../entry/@xmi:idref"/>
					<xsl:variable name="entryPointTargetId" select="/xmi:XMI/uml:Model//connectionPoint[@xmi:id=$connectionEntryId]/@xmi:id"/>
					<xsl:call-template name="outputUniqueId">
						<xsl:with-param name="xmiId" select="$entryPointTargetId"/>
					</xsl:call-template>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="outputUniqueId">
						<xsl:with-param name="xmiId" select="@target"/>
					</xsl:call-template>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:text>&apos;])</xsl:text>
		</xsl:attribute>
	</xsl:element>
</xsl:template>

<!-- Output a port's owning Class: its name and an optional range specification -->
<xsl:template name="outputPortzOwningClass">
	<xsl:param name="connectorEnd"/> <!-- an <end> element whose parent is an <ownedConnector> -->
	<!--<xsl:param name="roleId" select="$connectorEnd/@role"/>-->
	<xsl:param name="part" select="../../ownedAttribute[@xmi:id=$connectorEnd/@partWithPort]"/>
	<xsl:param name="classId" select="$part/@type"/>
	<xsl:param name="upperValue" select="$part/upperValue/@value"/>
	<xsl:param name="lowerValue" select="$part/upperValue/@value"/>
	<!--<xsl:message terminate="no">
		<xsl:value-of select="/xmi:XMI/uml:Model//ownedMember[@xmi:id=$classId]/@name"/>
	</xsl:message>-->
	<xsl:value-of select="/xmi:XMI/uml:Model//ownedMember[@xmi:id=$classId]/@name"/> <!-- output the class name -->
	<!-- Output roleName if there is potential ambiguity -->
	<xsl:if test="count(../../ownedAttribute[@type=$classId]) > 1">
		<xsl:text>[@roleName='</xsl:text>
		<xsl:value-of select="$part/@name"/>
		<xsl:text>']</xsl:text>
	</xsl:if>
	<!-- Output optional range at end of the name of a Class (ex: [1..8]) -->
	<xsl:if test="$upperValue > 1">
		<xsl:text>[1..</xsl:text>
		<xsl:value-of select="$upperValue"/>
		<xsl:text>]</xsl:text>
	</xsl:if>
</xsl:template>

<!-- Output attribute::port[x] and attribute::replication[x] -->
<xsl:template name="outputPortAndReplication">
	<xsl:param name="portTypeId"/> <!-- an ownedPort type -->
	<!--<xsl:message terminate="no">
		<xsl:text>outputPortAndReplication</xsl:text>
	</xsl:message>-->
	<xsl:text>/attribute::port[</xsl:text>
	<xsl:variable name="myDefaultValue" select="/xmi:XMI/uml:Model//ownedMember[@xmi:id=$portTypeId]/ownedAttribute/defaultValue"/>
	<xsl:choose>
		<xsl:when test="$myDefaultValue/@value">
			<!-- add 1 cause Xpath is 1-indexed -->
			<xsl:value-of select="$myDefaultValue/@value + 1"/>
		</xsl:when>
		<xsl:otherwise>
			<!-- no @value:
				so use the default for integers which is 0,
				and add 1 to this cause Xpath is 1-indexed -->
			<xsl:text>1</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
	<xsl:text>]</xsl:text>
	<xsl:text>/</xsl:text>
	<!-- TODO if multiplicity at other end is > 1, then use [1..*] here, else use [1] -->
	<xsl:text>attribute::replication[1..*]</xsl:text>
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

</xsl:stylesheet>
