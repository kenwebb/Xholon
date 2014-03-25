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
	Transform SysML to CompositeStructureHierarchy.xml
	Author: Ken Webb
	Date:   August 16, 2007
	File:   xmi2csh.xsl
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
	xmlns:xi="http://www.w3.org/2001/XInclude">

<xsl:output method="xml" omit-xml-declaration="no" indent="yes"/>

<xsl:param name="rootEnt"/>
<xsl:param name="author"/>
<xsl:param name="dateNow"/>
<xsl:param name="fileName"/>

<!-- root xholon entity (ex: TheSystem) -->
<xsl:variable name="rootEntity"
	select="/sysML:ModelSYSML//packagedElement[@xmi:type='sysML:Block'][@name=$rootEnt]"/>
<!-- root class entity (ex: XholonClass) -->
<xsl:variable name="rootClassEntity"
	select="/sysML:ModelSYSML/packagedElement[@xmi:type='sysML:Block'][not(generalization)]"/>

<xsl:template match="sysML:ModelSYSML">
	<xsl:comment>
		<xsl:text>&#10;&#9;</xsl:text>
		<xsl:value-of select="@name"/>
		<xsl:text> application - Composite Structure Hierarchy&#10;</xsl:text>
		<xsl:text>&#9;Author: </xsl:text><xsl:value-of select="$author"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;Date:   </xsl:text><xsl:value-of select="$dateNow"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;File:   </xsl:text><xsl:value-of select="$fileName"/><xsl:text>&#10;</xsl:text>
		<xsl:text>&#9;Target: Xholon 0.6 http://www.primordion.com/Xholon&#10;</xsl:text>
		<!-- SysML -->
		<xsl:text>&#9;SysML: TOPCASED 1.0.0</xsl:text>
		<xsl:text>&#10;</xsl:text>
		<!-- XMI -->
		<xsl:text>&#9;XMI: </xsl:text>
		<xsl:value-of select="/xmi:XMI/@xmi:version"/>
		<xsl:text> </xsl:text>
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
	<xsl:element name="{$rootEntity/@name}">
		<xsl:apply-templates select="$rootEntity/ownedAttribute[@aggregation='composite']"/>
	</xsl:element>
</xsl:template>

<!-- multiplicity
	MagicDraw    CSH.xml
	=========    =======
	unspecified  implied "1"
	0            implied "1"
	0..1         multiplicity="1"
	0..*         multiplicity="0"
	1            multiplicity="1"
	1..*         multiplicity="1"
	*            implied "1"
	n            multiplicity="n"
-->

<xsl:template match="ownedAttribute">
	<xsl:param name="idref" select="@type"/>
	<xsl:apply-templates select="/sysML:ModelSYSML//packagedElement[$idref=@xmi:id]">
		<xsl:with-param name="multiplicity">
			<xsl:choose>
				<xsl:when test="upperValue/@value='*' and lowerValue/@value"><xsl:value-of select="lowerValue/@value"/></xsl:when>
				<xsl:when test="upperValue/@value='*'"><xsl:text>0..*</xsl:text></xsl:when>
				<xsl:when test="upperValue/@value"><xsl:value-of select="upperValue/@value"/></xsl:when>
				<xsl:otherwise><xsl:text>0</xsl:text></xsl:otherwise>
			</xsl:choose>
		</xsl:with-param>
		<xsl:with-param name="roleName">
			<xsl:value-of select="@name"/>
		</xsl:with-param>
		<xsl:with-param name="defVal">
			<xsl:value-of select="concat(defaultValue/@value, defaultValue/@body)"/>
		</xsl:with-param>
	</xsl:apply-templates>
</xsl:template>

<xsl:template match="packagedElement">
	<xsl:param name="multiplicity"/>
	<xsl:param name="roleName"/>
	<xsl:param name="defVal"/>
	<xsl:param name="idref" select="@signal"/>
	<xsl:choose>
		<xsl:when test="@xmi:type='sysML:Block'">
			<xsl:element name="{@name}">
				<xsl:choose>
					<!-- multiplicity = 0..* -->
					<xsl:when test="$multiplicity='0..*'">
						<xsl:attribute name="multiplicity">
							<xsl:text>0</xsl:text>
						</xsl:attribute>
					</xsl:when>
					<xsl:when test="$multiplicity='*'">
						<xsl:attribute name="multiplicity">
							<xsl:text>1</xsl:text>
						</xsl:attribute>
					</xsl:when>
					<xsl:when test="$multiplicity!='0'">
						<xsl:attribute name="multiplicity">
							<xsl:value-of select="$multiplicity"/>
						</xsl:attribute>
					</xsl:when>
				</xsl:choose>
				<xsl:attribute name="roleName">
					<xsl:value-of select="$roleName"/>
				</xsl:attribute>
				<xsl:if test="$defVal">
					<xsl:element name="attribute">
						<xsl:attribute name="name">val</xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="$defVal"/></xsl:attribute>
					</xsl:element>
				</xsl:if>
				<!-- get behavior for this class, or for first superclass with behavior, if any -->
				<xsl:choose>
					<xsl:when test="ownedBehavior">
						<xsl:apply-templates select="ownedBehavior"/>
					</xsl:when>
					<xsl:when test="generalization">
						<xsl:apply-templates select="generalization"/>
					</xsl:when>
				</xsl:choose>
				<!-- get composite structure parts -->
				<!--<xsl:apply-templates select="ownedAttribute[@xmi:type='uml:Property'][@aggregation='composite']"/>-->
				<xsl:apply-templates select="ownedAttribute[not(@xmi:type)][@aggregation='composite']"/>
			</xsl:element>
		</xsl:when>
		<xsl:when test="@xmi:type='uml:SignalEvent'">
			<xsl:apply-templates select="/sysML:ModelSYSML//packagedElement[$idref=@xmi:id]"/>
		</xsl:when>
		<xsl:when test="@xmi:type='uml:Signal'">
			<xsl:element name="Trigger">
				<xsl:attribute name="roleName">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
			</xsl:element>
		</xsl:when>
		<xsl:when test="@xmi:type='uml:TimeEvent'">
			<xsl:element name="Trigger">
				<xsl:attribute name="roleName">
					<xsl:text>ISignal.SIGNAL_TIMEOUT</xsl:text>
				</xsl:attribute>
			</xsl:element>
		</xsl:when>
	</xsl:choose>
</xsl:template>

<!-- look for behavior in superclasses -->
<xsl:template match="generalization">
	<xsl:param name="idref" select="@general"/>
	<xsl:for-each select="/sysML:ModelSYSML//packagedElement[$idref=@xmi:id]">
		<!-- get behavior for this class, or for first superclass with behavior, if any -->
		<xsl:choose>
			<xsl:when test="ownedBehavior">
				<xsl:apply-templates select="ownedBehavior"/>
			</xsl:when>
			<xsl:when test="generalization">
				<xsl:if test=".!=rootClassEntity">
					<xsl:apply-templates select="generalization"/>
				</xsl:if>
			</xsl:when>
		</xsl:choose>
	</xsl:for-each>
</xsl:template>

<xsl:template match="ownedBehavior">
	<!--<xsl:if test="@xmi:type='uml:StateMachine' and not(submachineState)"> MagicDraw-->
	<xsl:if test="@xmi:type='uml:StateMachine' and not(@submachineState)"> <!-- Topcased -->
		<xsl:element name="StateMachine">
			<xsl:apply-templates select="region"/>
		</xsl:element>
	</xsl:if>
</xsl:template>

<xsl:template match="region">
	<xsl:element name="Region">
		<!--
			The order of subvertex and transition can make a difference to the app.
			Position PseudostateInitial before other nodes in the Region.
			The single PseudostateShallowHistory node, if any, should be immeditately after PseudostateInitial,
			because it will be searched for. Any PseudostateDeepHistory node should come next.
		-->
		<xsl:apply-templates select="subvertex[@xmi:type='uml:Pseudostate'][not(@kind)]"/> <!-- initial -->
		<xsl:apply-templates select="subvertex[@xmi:type='uml:Pseudostate'][@kind='initial']"/>
		<xsl:apply-templates select="subvertex[@xmi:type='uml:Pseudostate'][@kind='shallowHistory']"/>
		<xsl:apply-templates select="subvertex[@xmi:type='uml:Pseudostate'][@kind='deepHistory']"/>
		<xsl:apply-templates select="subvertex[not(@xmi:type='uml:Pseudostate' and not(@kind)) and not(@kind='initial') and not(@kind='shallowHistory') and not(@kind='deepHistory')]"/>
		<xsl:apply-templates select="transition"/>
	</xsl:element>
</xsl:template>

<xsl:template match="subvertex">
	<xsl:choose>
		
		<!--  State -->
		<xsl:when test="@xmi:type='uml:State'">
			<!-- <xsl:apply-templates select="outgoing"/> -->
			<xsl:element name="State">
				<xsl:attribute name="uid">
					<xsl:call-template name="outputUniqueId">
						<xsl:with-param name="xmiId" select="@xmi:id"/>
					</xsl:call-template>
				</xsl:attribute>
				<xsl:attribute name="roleName">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
				<xsl:apply-templates select="entry"/>
				<xsl:apply-templates select="exit"/>
				<xsl:apply-templates select="doActivity"/>
				<xsl:apply-templates select="deferrableTrigger"/>
				<xsl:choose>
					<xsl:when test="@submachine">
						<!--<xsl:apply-templates select="/sysML:ModelSYSML//submachineState[@xmi:idref=current()/@xmi:id]"/> MagicDraw-->
						<xsl:apply-templates select="/sysML:ModelSYSML//ownedBehavior[@xmi:id=current()/@submachine]/@submachineState"/> <!-- Topcased -->
					</xsl:when>
					<xsl:otherwise>
						<xsl:apply-templates select="connectionPoint"/>
						<xsl:apply-templates select="region"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:element>
		</xsl:when>
		
		<!-- FinalState -->
		<xsl:when test="@xmi:type='uml:FinalState'">
			<xsl:element name="FinalState">
				<xsl:attribute name="uid">
					<xsl:call-template name="outputUniqueId">
						<xsl:with-param name="xmiId" select="@xmi:id"/>
					</xsl:call-template>
				</xsl:attribute>
				<xsl:attribute name="roleName">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
			</xsl:element>
		</xsl:when>
		
		<xsl:when test="@xmi:type='uml:Pseudostate'">
			<!-- <xsl:apply-templates select="outgoing"/> -->
			<xsl:choose>
				
				<!-- PseudostateInitial -->
				<xsl:when test="@kind='initial' or not(@kind)">
					<xsl:element name="PseudostateInitial">
						<xsl:attribute name="uid">
							<xsl:call-template name="outputUniqueId">
								<xsl:with-param name="xmiId" select="@xmi:id"/>
							</xsl:call-template>
						</xsl:attribute>
						<xsl:attribute name="roleName">
							<xsl:value-of select="@name"/>
						</xsl:attribute>
					</xsl:element>
				</xsl:when>
				
				<!-- PseudostateChoice -->
				<xsl:when test="@kind='choice'">
					<xsl:element name="PseudostateChoice">
						<xsl:attribute name="uid">
							<xsl:call-template name="outputUniqueId">
								<xsl:with-param name="xmiId" select="@xmi:id"/>
							</xsl:call-template>
						</xsl:attribute>
						<xsl:attribute name="roleName">
							<xsl:value-of select="@name"/>
						</xsl:attribute>
					</xsl:element>
				</xsl:when>
				
				<!-- PseudostateTerminate -->
				<xsl:when test="@kind='terminate'">
					<xsl:element name="PseudostateTerminate">
						<xsl:attribute name="uid">
							<xsl:call-template name="outputUniqueId">
								<xsl:with-param name="xmiId" select="@xmi:id"/>
							</xsl:call-template>
						</xsl:attribute>
						<xsl:attribute name="roleName">
							<xsl:value-of select="@name"/>
						</xsl:attribute>
					</xsl:element>
				</xsl:when>
				
				<!-- PseudostateJunction -->
				<xsl:when test="@kind='junction'">
					<xsl:element name="PseudostateJunction">
						<xsl:attribute name="uid">
							<xsl:call-template name="outputUniqueId">
								<xsl:with-param name="xmiId" select="@xmi:id"/>
							</xsl:call-template>
						</xsl:attribute>
						<xsl:attribute name="roleName">
							<xsl:value-of select="@name"/>
						</xsl:attribute>
					</xsl:element>
				</xsl:when>
				
				<!-- PseudostateFork -->
				<xsl:when test="@kind='fork'">
					<xsl:element name="PseudostateFork">
						<xsl:attribute name="uid">
							<xsl:call-template name="outputUniqueId">
								<xsl:with-param name="xmiId" select="@xmi:id"/>
							</xsl:call-template>
						</xsl:attribute>
						<xsl:attribute name="roleName">
							<xsl:value-of select="@name"/>
						</xsl:attribute>
					</xsl:element>
				</xsl:when>
				
				<!-- PseudostateJoin -->
				<xsl:when test="@kind='join'">
					<xsl:element name="PseudostateJoin">
						<xsl:attribute name="uid">
							<xsl:call-template name="outputUniqueId">
								<xsl:with-param name="xmiId" select="@xmi:id"/>
							</xsl:call-template>
						</xsl:attribute>
						<xsl:attribute name="roleName">
							<xsl:value-of select="@name"/>
						</xsl:attribute>
					</xsl:element>
				</xsl:when>
				
				<!-- PseudostateShallowHistory -->
				<xsl:when test="@kind='shallowHistory'">
					<xsl:element name="PseudostateShallowHistory">
						<xsl:attribute name="uid">
							<xsl:call-template name="outputUniqueId">
								<xsl:with-param name="xmiId" select="@xmi:id"/>
							</xsl:call-template>
						</xsl:attribute>
						<xsl:attribute name="roleName">
							<xsl:value-of select="@name"/>
						</xsl:attribute>
					</xsl:element>
				</xsl:when>
				
				<!-- PseudostateDeepHistory -->
				<xsl:when test="@kind='deepHistory'">
					<xsl:element name="PseudostateDeepHistory">
						<xsl:attribute name="uid">
							<xsl:call-template name="outputUniqueId">
								<xsl:with-param name="xmiId" select="@xmi:id"/>
							</xsl:call-template>
						</xsl:attribute>
						<xsl:attribute name="roleName">
							<xsl:value-of select="@name"/>
						</xsl:attribute>
					</xsl:element>
				</xsl:when>
				
				<!-- add other pseudostate kinds -->
				<xsl:otherwise>
					<xsl:element name="Pseudostate"/> <!-- this shouldn't be allowed to happen -->
				</xsl:otherwise>
				
			</xsl:choose>
		</xsl:when>
	</xsl:choose>
</xsl:template>

<xsl:template match="@submachineState">
	<xsl:apply-templates select="../region"/>
</xsl:template>

<xsl:template match="connectionPoint">
	<!--<xsl:choose>
		<xsl:when test="@xmi:type='uml:Pseudostate'">-->
			<xsl:choose>
				
				<!-- PseudostateEntryPoint -->
				<xsl:when test="@kind='entryPoint'">
					<xsl:element name="PseudostateEntryPoint">
						<xsl:attribute name="uid">
							<xsl:call-template name="outputUniqueId">
								<xsl:with-param name="xmiId" select="@xmi:id"/>
							</xsl:call-template>
						</xsl:attribute>
						<xsl:attribute name="roleName">
							<xsl:value-of select="@name"/>
						</xsl:attribute>
					</xsl:element>
				</xsl:when>
				
				<!-- PseudostateExitPoint -->
				<xsl:when test="@kind='exitPoint'">
					<xsl:element name="PseudostateExitPoint">
						<xsl:attribute name="uid">
							<xsl:call-template name="outputUniqueId">
								<xsl:with-param name="xmiId" select="@xmi:id"/>
							</xsl:call-template>
						</xsl:attribute>
						<xsl:attribute name="roleName">
							<xsl:value-of select="@name"/>
						</xsl:attribute>
					</xsl:element>
				</xsl:when>
				
				<xsl:otherwise>
					<xsl:element name="Pseudostate"/> <!-- this shouldn't be allowed to happen -->
				</xsl:otherwise>
				
			</xsl:choose>
		<!--</xsl:when>
	</xsl:choose>-->
</xsl:template>

<xsl:template match="entry">
	<xsl:element name="EntryActivity">
		<xsl:attribute name="uid">
			<xsl:call-template name="outputUniqueId">
				<xsl:with-param name="xmiId" select="@xmi:id"/>
			</xsl:call-template>
		</xsl:attribute>
	</xsl:element>
</xsl:template>

<xsl:template match="exit">
	<xsl:element name="ExitActivity">
		<xsl:attribute name="uid">
			<xsl:call-template name="outputUniqueId">
				<xsl:with-param name="xmiId" select="@xmi:id"/>
			</xsl:call-template>
		</xsl:attribute>
	</xsl:element>
</xsl:template>

<xsl:template match="doActivity">
	<xsl:element name="DoActivity">
		<xsl:attribute name="uid">
			<xsl:call-template name="outputUniqueId">
				<xsl:with-param name="xmiId" select="@xmi:id"/>
			</xsl:call-template>
		</xsl:attribute>
	</xsl:element>
</xsl:template>

<xsl:template match="deferrableTrigger">
	<xsl:param name="idref" select="@event"/>
	<xsl:element name="DeferrableTrigger">
		<xsl:attribute name="uid">
			<xsl:call-template name="outputUniqueId">
				<xsl:with-param name="xmiId" select="@xmi:id"/>
			</xsl:call-template>
		</xsl:attribute>
		<xsl:attribute name="roleName">
			<xsl:value-of select="@name"/>
		</xsl:attribute>
		<xsl:apply-templates select="/sysML:ModelSYSML//packagedElement[$idref=@xmi:id]"/>
	</xsl:element>
</xsl:template>

<xsl:template match="transition">
	<xsl:choose>
		<xsl:when test="@kind='external' or not(@kind)">
			<xsl:element name="TransitionExternal">
				<xsl:attribute name="uid">
					<xsl:call-template name="outputUniqueId">
						<xsl:with-param name="xmiId" select="@xmi:id"/>
					</xsl:call-template>
				</xsl:attribute>
				<xsl:attribute name="roleName">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
				<xsl:apply-templates select="trigger"/>
				<xsl:apply-templates select="ownedRule"/> <!-- guard -->
				<xsl:apply-templates select="effect"/>
			</xsl:element>
		</xsl:when>
		<xsl:when test="@kind='internal'">
			<xsl:element name="TransitionInternal">
				<xsl:attribute name="uid">
					<xsl:call-template name="outputUniqueId">
						<xsl:with-param name="xmiId" select="@xmi:id"/>
					</xsl:call-template>
				</xsl:attribute>
				<xsl:attribute name="roleName">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
				<xsl:apply-templates select="trigger"/>
				<xsl:apply-templates select="ownedRule"/> <!-- guard -->
				<xsl:apply-templates select="effect"/>
			</xsl:element>
		</xsl:when>
		<xsl:when test="@kind='local'">
			<xsl:element name="TransitionLocal">
				<xsl:attribute name="uid">
					<xsl:call-template name="outputUniqueId">
						<xsl:with-param name="xmiId" select="@xmi:id"/>
					</xsl:call-template>
				</xsl:attribute>
				<xsl:attribute name="roleName">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
				<xsl:apply-templates select="trigger"/>
				<xsl:apply-templates select="ownedRule"/> <!-- guard -->
				<xsl:apply-templates select="effect"/>
			</xsl:element>
		</xsl:when>
		<xsl:otherwise>
			<!-- this shouldn't be allowed to happen -->
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="trigger">
	<xsl:param name="idref" select="@event"/>
	<xsl:apply-templates select="/sysML:ModelSYSML//packagedElement[$idref=@xmi:id]"/>
</xsl:template>

<xsl:template match="ownedRule">
	<xsl:element name="Guard">
		<xsl:attribute name="uid">
			<xsl:call-template name="outputUniqueId">
				<xsl:with-param name="xmiId" select="@xmi:id"/>
			</xsl:call-template>
		</xsl:attribute>
	</xsl:element>
</xsl:template>

<xsl:template match="effect">
	<xsl:element name="Activity">
		<xsl:attribute name="uid">
			<xsl:call-template name="outputUniqueId">
				<xsl:with-param name="xmiId" select="@xmi:id"/>
			</xsl:call-template>
		</xsl:attribute>
	</xsl:element>
</xsl:template>

<!-- Output a unique ID. -->
<xsl:template name="outputUniqueId">
	<xsl:param name="xmiId"/>
	<xsl:choose>
		<xsl:when test="string-length($xmiId)>30"> <!-- MagicDraw length is around 37 -->
			<xsl:value-of select="substring(translate($xmiId, '_01', '119'), 32)"/> <!-- MagicDraw -->
		</xsl:when>
		<xsl:otherwise> <!-- Topcased length is around 23 -->
			<xsl:value-of select="substring(translate($xmiId, '_ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz', '11234567890123456789012345678901234567890123456789012'), 1, 10)"/> <!-- Topcased -->
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>
