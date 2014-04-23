/* Cellontro - models & simulates cells and other biological entities
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.cellontro.app;

import java.util.List;
import java.util.Vector;
import org.primordion.cellontro.base.BioXholonClass;
import org.primordion.cellontro.base.IBioXholon;
import org.primordion.cellontro.base.IBioXholonClass;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.ContainmentData;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.Msg;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.util.MiscRandom;

/**
<p>An instance of XhLife represents some biological entity,
and exists as a node in a composite structure hierarchy tree.
Each such Xholon object goes through an initial configure() process,
and may subsequently go through a regular act() process at each time step.</p>

<p>At configure() time (Basic Cell Model):</p><ul type="square">
<li>If its xhType = XhtypePureActiveObject Then</li><ul type="disc">
<li>    It will have a gene value (geneVal) that it gets from its BioXholonClass.</li>
<li>    It will search for adjacent internal (P_SM_INT) and/or external (P_SM_EXT) spaces that
        contain phene values, using instructions from BioXholonClass.</li></ul>
<li>Else if its xhType = xhTypePurePassiveObject Then</li><ul type="disc">
<li>    It will have a phene value (pheneVal) that it gets from its BioXholonClass.</li>
<li>    It will probably be acted upon by one or more xhtypePureActiveObject instances
        at each time step.</li></ul>
<li>Else if its xhType = xhtypePureContainer Then</li><ul type="disc">
<li>    It is only a container for objects of the other two xhTypes.</li></ul>
<li>Endif</li></ul>

<p>At act() time (Basic Cell Model):</p><ul type="square">
<li>If its xhType = XhtypePureActiveObject Then</li><ul type="disc">
<li>    If it's an Enzyme Then</li><ul type="circle">
<li>        Increment or decrement the internal (P_SM_INT) pheneVal it points to,
            by an amount specified by its geneVal.</li></ul>
<li>    Else if it's a Bilayer or a TransportProtein Then</li><ul type="circle">
<li>        Move geneVal number of units between the internal (P_SM_INT) pheneVal it points to,
            and the external (P_SM_EXT) pheneVal it points to.</li></ul>
<li>    Endif</li></ul>
<li>Else if its xhType = xhTypePurePassiveObject Then</li><ul type="disc">
<li>    Print the current value of its pheneVal.</li></ul>
<li>Endif</li></ul>

<p>An enzyme active object is a simple abstraction of a biological enzyme that acts on small
molecules contained within the same internal space.
A bilayer is a simple abstraction of a biological membrane (lipid bilayer) that moves small
molecules between its internal space and the space external to it.
A transport protein is a simple abstraction that acts much like a bilayer.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on July 8, 2005)
 */
public class XhLife extends XholonWithPorts implements IBioXholon, CeLife {

	// references to other Xholon instances
	// indices into the port array
	// first set are used by bilayers and transport proteins
	public static final int P_SM_INT1 = 0; // a small molecule located internally within this membrane
	public static final int P_SM_EXT1 = 1; // a small molecule located externally outside this membrane
	public static final int P_SM_INT2 = 2;
	public static final int P_SM_EXT2 = 3;
	public static final int P_SM_INT3 = 4;
	public static final int P_SM_EXT3 = 5;
	public static final int P_SM_INT4 = 6;
	public static final int P_SM_EXT4 = 7;
	// next set are used by enzymes
	public static final int P_SM_SUB1 = 0; // substrate 1
	public static final int P_SM_PRD1 = 1; // product 1
	public static final int P_SM_SUB2 = 2; // substrate 2
	public static final int P_SM_PRD2 = 3; // product 2
	public static final int P_SM_ACT1 = 4; // activator
	public static final int P_SM_INH1 = 5; // inhibitor
	public static final int P_SM_COE1 = 6; // substrate coenzyme
	public static final int P_SM_COE2 = 7; // product coenzyme
	// Sun
	public static final int P_SOLAR_ENERGY = 0;
	// Lettuce
	public static final int P_CALCIUM = 4;
	public static final int P_REFLECTED_LIGHT = 6;
	public static final int P_NUTRIENTS = 7;
	// ConeOuterSegment
	public static final int P_NEURON_IN_SPACE_PASSAGEWAY = 0;
	// AxonHillock
	public static final int P_NEURON_OUT_SPACE_PASSAGEWAY = 0;
	public static final int P_MEMBRANE_POTENTIAL = 1;
	// Synaptotagmin
	public static final int P_FUSE = 3;
	// SynapticVesicleBilayer
	public static final int P_SYN_CLEFT = P_SM_EXT1;
	// MuscleFiber
	public static final int P_MOUTH = P_SM_EXT1;
	// Mouth
	public static final int P_EAT = 0;
	public static final int P_GUT = 1;
	public static final int P_DIGESTION = 2;
	// SmallIntestine
	public static final int P_FOOD = 0;
	public static final int P_DIGESTED_FOOD = 1;
	// small intestine also uses P_DIGESTION
	// Rectum
	public static final int P_COMPLETELY_DIGESTED_FOOD = 0;
	public static final int P_ORGANIC_MATTER = 1;
	// BoneMarrow
	public static final int P_LEFT_VENTRICLE = 0;
	// TheArrowOfTime
	public static final int P_BEAT_LEFT_VENTRICLE = 0;
	public static final int P_BEAT_RIGHT_VENTRICLE = 1;
	// Ventricle
	public static final int P_BLOOD_FLOW_1 = 0;
	public static final int P_BLOOD_FLOW_2 = 1;
	public static final int P_BLOOD_FLOW_3 = 2;
	// BloodVessel
	public static final int P_BLOOD_FLOW = 0;
	
	public int state = 0;
	public String roleName = null;
	// static variables used by singleton TheArrowOfTime
	public static final int ARROW_OF_TIME_FREQ = 5; // will send a message every n timesteps (default: 10)
	protected static int arrowOfTimeCounter = 0;
	
	// Signals, Events
	// SolarEnergy protocol
	public static final int SOUT_PHOTON = 100; // no data
	// Neuron In Space passageway protocol
	public static final int SOUT_POST_SYNAPTIC_POTENTIAL = 200; // int
	// Neuron Out Space passageway protocol
	public static final int SOUT_ACTION_POTENTIAL = 300; // no data
	// Fuse protocol
	public static final int SOUT_DO_EXOCYTOSIS = 400; // no data
	// Chew protocol
	public static final int SOUT_CHEW = 500; // no data
	// Digest protocol
	public static final int SOUT_DIGEST = 510; // no data
	// Beat protocol
	public static final int SOUT_TICK = 600; // no data
	// BloodFlow protocol
	public static final int SOUT_BLOOD_PLASMA = 700; // IXholon

	// Potentials (used by AxonHillock)
	public static final int THRESHOLD_POTENTIAL = -50; // mV
	public static final int RESTING_POTENTIAL = -65; // mV
	
	// number of units of bloodPlasma per bloodVessel
	public static final int NUM_BP_PER_BV = 5;
	
	public double pheneVal = 0.0; // Phene value contained within a passive object
	private static int smCount[] = new int[IBioXholonClass.SIZE_SMCOUNT];
	// enzyme kinetics
	private static int enzymeLevel; // number of enzyme instances represented by this instance of Xholon
	private static double nTimes;
	private static double s, s1, s2, sk, ik, a;
	// MucosalCell
	private static IXholon dynamicPort = null;

	// Whether or not should show certain application messages at runtime
	private static boolean shouldshowEnvironment   = true; // Sun, Lettuce
	private static boolean shouldshowLung          = true; // Lung
	private static boolean shouldshowCircSystem    = true; // CirculatorySystem
	private static boolean shouldshowDigSystem     = true; // DigestiveSystem
	private static boolean shouldshowVisualPathway = true; // Eye, pathway to visual cortex
	private static boolean shouldshowMotorPathway  = true; // motor cortex to muscle
	private static boolean shouldshowRcs_GP_FSM    = true; // Rcs_GP_FSM
	private static boolean showingSomething = shouldshowEnvironment | shouldshowLung | shouldshowCircSystem
		| shouldshowDigSystem | shouldshowVisualPathway | shouldshowMotorPathway | shouldshowRcs_GP_FSM;
	
	// from Rcs_GP_FSM
	// references to other Xholon instances; indices into the port array
	public static final int Substrate = 0;
	public static final int Product = 1;
	public static final int Regulation = 1;
	// Signals, Events
	public static final int S_ACTIVATE = 1100;
	public static final int S_DEACTIVATE = 1200;
	//
	protected static int rInt; // RcsEnzyme
	protected static final int MAX_SUB = 20; // RcsEnzyme
	
	// exceptions
	//public SecurityException securityException;
	
	/**
	 * Constructor.
	 */
	public XhLife() {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		state = 0;
		roleName = null;
		pheneVal = Double.MAX_VALUE;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getVal()
	 */
	public double getVal()
	{return getPheneVal();}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(double)
	 */
	public void setVal(double val)
	{setPheneVal(val);}
	
	/*
	 * @see org.primordion.cellontro.base.IBioXholon#getPheneVal()
	 */
	public double getPheneVal()
	{ return pheneVal; }
	
	/*
	 * @see org.primordion.cellontro.base.IBioXholon#setPheneVal(double)
	 */
	public void setPheneVal(double pheneVal)
	{this.pheneVal = pheneVal;}
	
	/*
	 * @see org.primordion.cellontro.base.IBioXholon#incPheneVal(double)
	 */
	public void incPheneVal( double incAmount )
	{ pheneVal += incAmount; }
	
	/*
	 * @see org.primordion.cellontro.base.IBioXholon#decPheneVal(double)
	 */
	public void decPheneVal( double decAmount )
	{ pheneVal -= decAmount; }

	// these functions are not currently used in this model
	public int getNumReactants() {return 0;}
	public void setNumReactants(int numR) {}
	public int getNumProducts() {return 0;}
	public void setNumProducts(int numP) {}
	public int getNumModifiers() {return 0;}
	public void setNumModifiers(int numM) {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 */
	public String getRoleName()
	{
		return roleName;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#configure()
	 * 
	 * TODO somehow confirm that the same node hasn't already been configured
	 *      - as with dynamically created enzymes such as Acetylcholinesterase
	 */
	public void configure()
	{
		if (xhc == null) {
			logger.error("XhLife.configure: xhClass is null\n");
			return;
		}
		IXholonClass ciBec = xhc;
		String instructions = ciBec.getConfigurationInstructions();
		while ((!(ciBec.isRootNode())) && (instructions.equals(""))) {
			ciBec = (IXholonClass)ciBec.getParentNode();
			instructions = ciBec.getConfigurationInstructions();
		}
		
		if (Msg.infoM) System.out.print( getName() + ": " + instructions );
		int instructIx = 0;
		while (instructIx < instructions.length()) {
			switch( instructions.charAt(instructIx)) {
			case 'p': // port
				instructIx = configurePorts(instructions, instructIx);
				/*instructIx += 5; // point at char after opening [
				int startPos = instructIx;
				while (instructions.charAt(instructIx) != ']') {
					instructIx++;
				}
				String portId = instructions.substring(startPos, instructIx);
				int portNum = ReflectionFactory.getReflectionSingleton().getAttributeIntVal(this, portId);
				instructIx += 2; // point to first char after =
				// handle multiple xpointers to same port; to allow alternatives if first one(s) don't work
				if (port[portNum] == null) { // don't replace one that's already been assigned
					port[portNum] = getPortRef(instructions, instructIx);
				}
				while ((instructIx < instructions.length()) && (instructions.charAt(instructIx) != InheritanceHierarchy.NAVINFO_SEPARATOR)) {
					instructIx++;
				}
				instructIx++; */
				break;
			
			case ContainmentData.CD_CONFIG: // 'C' configuration instructions
				instructIx = configure(instructions, instructIx);
				/*instructIx++;
				if (instructions.charAt(instructIx) == 'S') { // synapse
					createSynapse();
				}
				else if (instructions.charAt(instructIx) == 'E') { // erythrocytes in boneMarrow
					moveErythrocytes();
				}
				instructIx++; */
				break;

			default:
				if (Msg.errorM)
					System.err.println( "XhLife configure: error [" + instructions.charAt(instructIx) + "]\n" );
				instructIx++;
				break;
			} // end switch
		} // end while

		// Print header line for application data
		if (Msg.appM && !Msg.infoM) {
			// Passive Objects
			//if ((xhClass.getXhType() & IXholonClass.XhtypePurePassiveObject) == IXholonClass.XhtypePurePassiveObject) {
			if (isPassiveObject()) {
				System.out.print( getName()  + ",");
			}
		}
		
		// set the feature type for this XholonClass
		if ((port != null) && (((IBioXholonClass)xhc).getFeatureType() == IBioXholonClass.FeatureType_Null)
				&& (((IBioXholonClass)xhc).getActiveObjectType() == IBioXholonClass.AOT_ENZYME)) {
			if (port[P_SM_SUB2] != null) {smCount[IBioXholonClass.SMCOUNT_SUBSTRATE] = 2;}
			else {smCount[IBioXholonClass.SMCOUNT_SUBSTRATE] = 1;}
			if (port[P_SM_PRD2] != null) {smCount[IBioXholonClass.SMCOUNT_PRODUCT] = 2;}
			else {smCount[IBioXholonClass.SMCOUNT_PRODUCT] = 1;}
			if (port[P_SM_ACT1] != null) {smCount[IBioXholonClass.SMCOUNT_ACTIVATOR] = 1;}
			else {smCount[IBioXholonClass.SMCOUNT_ACTIVATOR] = 0;}
			if (port[P_SM_INH1] != null) {smCount[IBioXholonClass.SMCOUNT_INHIBITOR] = 1;}
			else {smCount[IBioXholonClass.SMCOUNT_INHIBITOR] = 0;}
			if (port[P_SM_COE1] != null) {smCount[IBioXholonClass.SMCOUNT_COENZYME] = 1;}
			else {smCount[IBioXholonClass.SMCOUNT_COENZYME] = 0;}
			((IBioXholonClass)xhc).setFeatureType(smCount);
		}
		
		//super.configure();
		// execute recursively
		//try {
			if (firstChild != null) {
				firstChild.configure();
			}
			if (nextSibling != null) {
				nextSibling.configure();
			}
		//} catch (StackOverflowError e) {
		//	System.err.println("XhLife configure() stack overflow: " + this + " ");
		//	return;
		//}

	} // end configure()
	
	/*
	 * @see org.primordion.xholon.base.IXholon#configure(java.lang.String, int)
	 */
	public int configure(String instructions, int instructIx)
	{
		instructIx++;
		if (instructions.charAt(instructIx) == 'S') { // synapse
			createSynapse();
		}
		else if (instructions.charAt(instructIx) == 'E') { // erythrocytes in boneMarrow
			moveErythrocytes();
		}
		instructIx++;
		return instructIx;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#preAct()
	 */
	public void preAct()
	{
		//if ((xhClass.getXhType() & IXholonClass.XhtypePurePassiveObject) == IXholonClass.XhtypePurePassiveObject) {
		//	if (Msg.appM) System.out.print( (long)pheneVal + "," );
		//}
		if (xhc.hasAncestor("StructuredClassifier")) {
			if (showingSomething) {
				println(""); // blank line between timesteps if showing messages
			}
		}

		// execute recursively
		// not necessary because the "StructuredClassifier" xholon is the root node in this model
		/*
		try {
			if (firstChild != null) {
				firstChild.preAct();
			}
			if (nextSibling != null) {
				nextSibling.preAct();
			}
		} catch (StackOverflowError e) {
			System.err.println("XhLife preAct() stack overflow: " + this + " ");
			return;
		} */
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 * recursive; application should call this only for root
	 * TODO should be checking to ensure the phene values can't become negative
	 * TODO should use CeLife values, but "Enzyme" is a superclass
	 */
	public void act()
	{
		if (xhc.hasAncestor("StructuredClassifier")) {
			// Only process the messages that are currently in the Q.
			// Don't process messages added to Q during this timestep.
			processMessageQ();
		}

		//else if ((xhClass.getXhType() & IXholonClass.XhtypePureActiveObject) == IXholonClass.XhtypePureActiveObject) {
		else if (isActiveObject()) {
			
			if (xhc.getName().equals("Sun")) {
				port[P_SOLAR_ENERGY].sendMessage(SOUT_PHOTON, null, this);
				if (shouldshowEnvironment) {
					println(getName() + " is shining.");
				}
			}
			
			else if (xhc.hasAncestor("Lettuce")) {
				// Lettuce also processes messages from Sun
				if (((XhLife)port[P_NUTRIENTS]).getPheneVal() > 10000) {
					((XhLife)port[P_NUTRIENTS]).decPheneVal(1000);
					((XhLife)port[P_CALCIUM]).incPheneVal(1000);
					if (shouldshowEnvironment) {
						println(getName() + " is absorbing nutrients from "
							+ port[P_NUTRIENTS].getParentNode().getName() + ".");
					}
				}
			}
			
			else if (xhc.hasAncestor("Lung")) {
				if (port[P_SM_INT1] != null) {
					if (port[P_SM_INT1].getXhcName().equals("Capillary")) {
						// get first instance of BloodPlasma/ExtraCellularSolution/Glucose in the Capillary
						if (port[P_SM_EXT1] != null) {
							dynamicPort = getXPath().evaluate(
									"BloodPlasma/ExtraCellularSolution/" + port[P_SM_EXT1].getXhcName(),
									port[P_SM_INT1]);
							if (dynamicPort != null) {
								// Oxygen
								((XhLife)port[P_SM_EXT1]).decPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] ); // dec
								((XhLife)dynamicPort).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] ); // inc
								if (shouldshowLung) {
									println(getName() + " is inhaling oxygen from "
										+ port[P_SM_EXT1].getParentNode().getName()
										+ " to " + dynamicPort.getParentNode().getParentNode().getName() + ".");
								}
							}
						}
					}
				}
				if (port[P_SM_INT2] != null) {
					if (port[P_SM_INT2].getXhcName().equals("Capillary")) {
						// get first instance of BloodPlasma/ExtraCellularSolution/Glucose in the Capillary
						if (port[P_SM_EXT2] != null) {
							dynamicPort = getXPath().evaluate(
									"BloodPlasma/ExtraCellularSolution/" + port[P_SM_EXT2].getXhcName(),
									port[P_SM_INT2]);
							if (dynamicPort != null) {
								// CarbonDioxide
								((XhLife)dynamicPort).decPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] ); // dec
								((XhLife)port[P_SM_EXT2]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] ); // inc
								if (shouldshowLung) {
									println(getName() + " is exhaling carbon dioxide to "
										+ port[P_SM_EXT2].getParentNode().getName()
										+ " from " + dynamicPort.getParentNode().getParentNode().getName() + ".");
								}
							}
						}
					}
				}
			}
			
			// from Rcs_GP_FSM
			else if (xhc.hasAncestor("RcsEnzyme")) {
				switch(xhc.getId()) {
				case GPaseCE:
					// Do Activity
					if (this.hasChildNodes()) {
						((StateMachineEntity)getFirstChild()).doStateMachine(new Message(ISignal.SIGNAL_DOACTIVITY,null,null,this));
					}
					else {
						println("Xhslice glycogen: doActivity with no receiver ");
					}
					break;
				case PKinaseCE:
					// Do Activity
					if (this.hasChildNodes()) {
						((StateMachineEntity)getFirstChild()).doStateMachine(new Message(ISignal.SIGNAL_DOACTIVITY,null,null,this));
					}
					else {
						println("Xhactivate GP: doActivity with no receiver ");
					}
					break;
				case PPhosphataseCE:
					// Do Activity
					if (this.hasChildNodes()) {
						((StateMachineEntity)getFirstChild()).doStateMachine(new Message(ISignal.SIGNAL_DOACTIVITY,null,null,this));
					}
					else {
						println("Xhdeactivate GP: doActivity with no receiver ");
					}
					break;
				} // end of switch
			}
			else if (xhc.hasAncestor("Enzyme")) {
				// enzyme kinetics
				if (pheneVal == Double.MAX_VALUE) {
					enzymeLevel = 1;
				}
				else {
					enzymeLevel = (int)pheneVal; // default
				}
				switch(((IBioXholonClass)xhc).getFeatureType()) {
				
				// Irreversible Michaelis-Menten kinetics (Gepasi)
				// Irreversible, 1 Substrate, 1 Product, 0 Activator, 0 Inhibitor, 0 Coenzyme
				case IBioXholonClass.Irr_Sb1_Pr1_Ac0_In0_Co0:
					s = ((XhLife)port[P_SM_SUB1]).getPheneVal();
					nTimes = enzymeLevel * ((((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_V] * s) / (((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] + s));
					((XhLife)port[P_SM_SUB1]).decPheneVal(nTimes);
					((XhLife)port[P_SM_PRD1]).incPheneVal(nTimes);
					break;
				
				// Irreversible Michaelis-Menten kinetics (Gepasi)
				// Irreversible, 1 Substrate, 2 Product, 0 Activator, 0 Inhibitor, 0 Coenzyme
				case IBioXholonClass.Irr_Sb1_Pr2_Ac0_In0_Co0:
					s = ((XhLife)port[P_SM_SUB1]).getPheneVal();
					nTimes = enzymeLevel * ((((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_V] * s) / (((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] + s));
					((XhLife)port[P_SM_SUB1]).decPheneVal(nTimes);
					((XhLife)port[P_SM_PRD1]).incPheneVal(nTimes);
					((XhLife)port[P_SM_PRD2]).incPheneVal(nTimes);
					break;
				
				// Irreversible Michaelis-Menten kinetics (Gepasi) - with coenzymes
				// Irreversible, 1 Substrate, 1 Product, 0 Activator, 0 Inhibitor, N Coenzyme
				case IBioXholonClass.Irr_Sb1_Pr1_Ac0_In0_CoN:
					s = ((XhLife)port[P_SM_SUB1]).getPheneVal();
					nTimes = enzymeLevel * ((((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_V] * s) / (((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] + s));
					((XhLife)port[P_SM_SUB1]).decPheneVal(nTimes);
					((XhLife)port[P_SM_PRD1]).incPheneVal(nTimes);
					// handle coenz; ex: Nad --> NadH; just like a substrate --> product
					if (port[P_SM_COE1] != null) {
						((XhLife)port[P_SM_COE1]).decPheneVal(nTimes);
						((XhLife)port[P_SM_COE2]).incPheneVal(nTimes);
					}
					break;

				// Irreversible Michaelis-Menten kinetics (Gepasi)
				// should use Ordered Bi Uni kinetics (Gepasi) - ex: TCA1
				// Irreversible, 2 Substrate, 1 Product, 0 Activator, 0 Inhibitor, N Coenzyme
				case IBioXholonClass.Irr_Sb2_Pr1_Ac0_In0_CoN:
					s1 = ((XhLife)port[P_SM_SUB1]).getPheneVal();
					s2 = ((XhLife)port[P_SM_SUB2]).getPheneVal();
					s = (s1 < s2) ? s1 : s2;
					nTimes = enzymeLevel * ((((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_V] * s) / (((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] + s));
					((XhLife)port[P_SM_SUB1]).decPheneVal(nTimes);
					((XhLife)port[P_SM_SUB2]).decPheneVal(nTimes);
					((XhLife)port[P_SM_PRD1]).incPheneVal(nTimes);
					if (port[P_SM_COE1] != null) {
						((XhLife)port[P_SM_COE1]).decPheneVal(nTimes); // coenzyme
						((XhLife)port[P_SM_COE2]).incPheneVal(nTimes);
					}
					break;

				// Noncompetitive inhibition kinetics (Gepasi); use for Hexokinase
				// Irreversible, 1 Substrate, 1 Product, 0 Activator, 1 Inhibitor, N Coenzyme
				case IBioXholonClass.Irr_Sb1_Pr1_Ac0_In1_CoN:
					sk = ((XhLife)port[P_SM_SUB1]).getPheneVal() / ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM];
					ik = ((XhLife)port[P_SM_INH1]).getPheneVal() / ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_EFF_KM];
					nTimes = enzymeLevel * ((((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_V] * sk) / (1+ik+sk*(1+ik)));
					((XhLife)port[P_SM_SUB1]).decPheneVal(nTimes);
					((XhLife)port[P_SM_PRD1]).incPheneVal(nTimes);
					if (port[P_SM_COE1] != null) {
						((XhLife)port[P_SM_COE1]).decPheneVal(nTimes); // coenzyme
						((XhLife)port[P_SM_COE2]).incPheneVal(nTimes);
					}
					break;

				// Catalytic activation kinetics (Gepasi)
				// Irreversible, 1 Substrate, 1 Product, 1 Activator, 0 Inhibitor, N Coenzyme
				case IBioXholonClass.Irr_Sb1_Pr1_Ac1_In0_CoN:
					s = ((XhLife)port[P_SM_SUB1]).getPheneVal();
					a = ((XhLife)port[P_SM_ACT1]).getPheneVal();
					nTimes = enzymeLevel * ((a * s * ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_V])
							/ ((a + ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_EFF_KM])
									* (s + ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM])));
					((XhLife)port[P_SM_SUB1]).decPheneVal(nTimes);
					((XhLife)port[P_SM_PRD1]).incPheneVal(nTimes);
					if (port[P_SM_COE1] != null) {
						((XhLife)port[P_SM_COE1]).decPheneVal(nTimes); // coenzyme
						((XhLife)port[P_SM_COE2]).incPheneVal(nTimes);
					}
					break;

				// Noncompetitive inhibition kinetics AND Catalytic activation kinetics (Gepasi)
				// Irreversible, 1 Substrate, 1 Product, N Activator, N Inhibitor, N Coenzyme
				case IBioXholonClass.Irr_Sb1_Pr1_AcN_InN_CoN:
					break;

				// Reversible Michaelis-Menten kinetics (Gepasi)
				// Reversible, 1 Substrate, 1 Product, 0 Activator, 0 Inhibitor, 0 Coenzyme
				case IBioXholonClass.Rev_Sb1_Pr1_Ac0_In0_Co0:
					break;
				
				// Reversible Michaelis-Menten kinetics (Gepasi) - with coenzymes
				// Reversible, 1 Substrate, 1 Product, 0 Activator, 0 Inhibitor, N Coenzyme
				case IBioXholonClass.Rev_Sb1_Pr1_Ac0_In0_CoN:
					break;

				// Reversible Michaelis-Menten kinetics (Gepasi) - should use Ordered Uni Bi kinetics
				// Reversible, 1 Substrate, 2 Product, 0 Activator, 0 Inhibitor, 0 Coenzyme
				case IBioXholonClass.Rev_Sb1_Pr2_Ac0_In0_Co0:
					break;

				default:
					break;
				} // end switch
			}
			else if (xhc.hasAncestor("SucroseTransporter")) {
				// Do Michaelis-Menten Passive Facilitated Transport calculation
				// this is a special subclass of TransportProtein
				enzymeLevel = 1;
				if (port[P_SM_EXT1] == null) {
					// TODO get the xpath locationPath from XholonClass navInfo, rather than hardcoding it
					port[P_SM_EXT1] = getXPath().evaluate("ancestor::SmallIntestine/DigestedFood/Sucrose", this);
				}
				if (port[P_SM_EXT1] != null) {
					s = ((XhLife)port[P_SM_EXT1]).getPheneVal();
					nTimes = enzymeLevel * ((((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_V] * s)
							/ (((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] + s));
					((XhLife)port[P_SM_EXT1]).decPheneVal(nTimes);
					((XhLife)port[P_SM_INT1]).incPheneVal(nTimes);
					if (shouldshowDigSystem) {
						println(getName() + " is transporting sucrose from "
							+ port[P_SM_EXT1].getParentNode().getName() + " to "
							+ port[P_SM_INT1].getParentNode().getParentNode().getParentNode().getName() + ".");
					}
				}
			}
			else if ((((BioXholonClass)xhc).getParentNode().getName().equals("TransportProtein"))
					|| (((BioXholonClass)xhc).getParentNode().getName().equals("LipidBilayer"))) {
				// transport protein or lipid bilayer
				if (port[P_SM_INT1] != null) {
					((XhLife)port[P_SM_INT1]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] ); // inc
				}
				if (port[P_SM_EXT1] != null) {
					if (port[P_SM_EXT1].getXhcName().equals("Capillary")) {
						// this must be a MucosalCell
						// get first instance of BloodPlasma/ExtraCellularSolution/Glucose in the Capillary
						if (port[P_SM_INT1] != null) {
							dynamicPort = getXPath().evaluate(
									"BloodPlasma/ExtraCellularSolution/" + port[P_SM_INT1].getXhcName(),
									port[P_SM_EXT1]);
							if (dynamicPort != null) {
								((XhLife)dynamicPort).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] * -1 );
								if (shouldshowDigSystem) {
								println(getParentNode().getParentNode().getName()
									+ " bilayer is transporting glucose to "
									+ dynamicPort.getParentNode().getParentNode().getName() + ".");
								}
							}
						}
					}
					else {
						((XhLife)port[P_SM_EXT1]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] * -1 ); // dec
					}
				}
				if (port[P_SM_INT2] != null) {
					((XhLife)port[P_SM_INT2]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] ); // inc
				}
				if (port[P_SM_EXT2] != null) {
					((XhLife)port[P_SM_EXT2]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] * -1 ); // dec
				}
				if (port[P_SM_INT3] != null) {
					((XhLife)port[P_SM_INT3]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] ); // inc
				}
				if (port[P_SM_EXT3] != null) {
					((XhLife)port[P_SM_EXT3]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] * -1 ); // dec
				}
				if (port[P_SM_INT4] != null) {
					((XhLife)port[P_SM_INT4]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] ); // inc
				}
				if (port[P_SM_EXT4] != null) {
					((XhLife)port[P_SM_EXT4]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] * -1 ); // dec
				}
			}
			else if (xhc.hasAncestor("VesFusionProtein")) { // Synaptotagmin
				if (port[P_SM_EXT1] != null) {
					if (((XhLife)port[P_SM_EXT1]).getPheneVal() >= 1000000000) {
						((XhLife)port[P_SM_EXT1]).decPheneVal(1000000000);
						port[P_FUSE].sendMessage(SOUT_DO_EXOCYTOSIS, null, this);
					}
				}
			}
			else if (xhc.hasAncestor("LgNeuroreceptor")) { // NmdaReceptor
				if (port[P_SYN_CLEFT] != null) {
					if (((XhLife)port[P_SYN_CLEFT]).getPheneVal() > 100000) {
						port[P_NEURON_IN_SPACE_PASSAGEWAY].sendMessage(SOUT_POST_SYNAPTIC_POTENTIAL, new Integer(10), this);
					}
				}
			}
			else if (xhc.hasAncestor("TheArrowOfTime")) { // TheArrowOfTime
				if (arrowOfTimeCounter >= ARROW_OF_TIME_FREQ) {
					port[P_BEAT_LEFT_VENTRICLE].sendMessage(SOUT_TICK, null, this);
					port[P_BEAT_RIGHT_VENTRICLE].sendMessage(SOUT_TICK, null, this);
					arrowOfTimeCounter = 0;
				}
				else {
					arrowOfTimeCounter++;
				}
			}
		}

		// execute recursively
		//try {
			if (firstChild != null) {
				firstChild.act();
			}
			if (nextSibling != null) {
				nextSibling.act();
			}
		//} catch (StackOverflowError e) {
		//	System.err.println("XhLife act() stack overflow: " + this + " ");
		//	return;
		//}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postAct()
	 */
	public void postAct()
	{
		// execute recursively
		/*
		try {
			if (firstChild != null) {
				firstChild.postAct();
			}
			if (nextSibling != null) {
				nextSibling.postAct();
			}
		} catch (StackOverflowError e) {
			System.err.println("XhLife postAct() stack overflow: " + this + " ");
			return;
		} */
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#processReceivedMessage(org.primordion.xholon.base.Message)
	 */
	public void processReceivedMessage(IMessage msg)
	{
		int event = msg.getSignal();
		int i;
		
		if (xhc.hasAncestor("Plant")) {
			switch (state) {
			case 0: // Living
				switch(event) {
				case SOUT_PHOTON:
					if (MiscRandom.getRandomInt(0, 2) == 0) {
						// Sometimes send the photon out the reflectedLight port.
						port[P_REFLECTED_LIGHT].sendMessage(SOUT_PHOTON, null, this);
						if (shouldshowEnvironment) {
							println(getName() + " is reflecting received light to "
								+ port[P_REFLECTED_LIGHT].getName() + ".");
						}
					}
					else {
						// Sometimes photosynthesize Glucose and Sucrose.
						// 6(CO2) + 6(H2O) --> (C6H12O6) + 6(O2)
						// 12(CO2) + 11(H2O) --> (C12H22O11) + 12(O2)
						if (port[P_SM_EXT1] != null) { // CO2 in Atmosphere
							((XhLife)port[P_SM_EXT1]).decPheneVal( 12600 ); // dec
						}
						if (port[P_SM_EXT2] != null) { // Water in BodyOfWater
							((XhLife)port[P_SM_EXT2]).decPheneVal( 11600 ); // dec
						}
						if (port[P_SM_INT1] != null) { // Glucose within Lettuce
							((XhLife)port[P_SM_INT1]).incPheneVal( 100 ); // inc
						}
						if (port[P_SM_INT2] != null) { // Sucrose within Lettuce
							((XhLife)port[P_SM_INT2]).incPheneVal( 1000 ); // inc
						}
						if (port[P_SM_EXT3] != null) { // Oxygen in Atmosphere
							((XhLife)port[P_SM_EXT3]).incPheneVal( 12600 ); // inc
						}
						if (shouldshowEnvironment) {
							println(getName() + " is photosynthesizing received light.");
						}
					}
					state = 0; // stay in same state
					break;
				default:
					// ignore
					break;
				}
				break;
			default:
				break;
			}
		} // end Lettuce
		
		else if (xhc.hasAncestor("Sensor")) { // ConeOuterSegment
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SOUT_PHOTON:
					port[P_NEURON_IN_SPACE_PASSAGEWAY].sendMessage(SOUT_POST_SYNAPTIC_POTENTIAL, new Integer(10), this);
					if (shouldshowVisualPathway) {
						println(getName() + " is sending a PSP to "
							+ port[P_NEURON_IN_SPACE_PASSAGEWAY].getName() + ".");
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		} // end ConeOuterSegment

		else if (xhc.hasAncestor("NeuronCellBody")) { // NeuronCellBody
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SOUT_POST_SYNAPTIC_POTENTIAL:
					port[P_NEURON_IN_SPACE_PASSAGEWAY].sendMessage(SOUT_POST_SYNAPTIC_POTENTIAL, msg.getData(), this);
					if (shouldshowVisualPathway) {
						println(getName() + " is sending a PSP to "
							+ port[P_NEURON_IN_SPACE_PASSAGEWAY].getName() + ".");
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		} // end NeuronCellBody

		else if (xhc.hasAncestor("AxonHillock")) { // AxonHillock
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SOUT_POST_SYNAPTIC_POTENTIAL:
					((XhLife)port[P_MEMBRANE_POTENTIAL]).pheneVal += ((Integer)msg.getData()).intValue();
					if (((XhLife)port[P_MEMBRANE_POTENTIAL]).pheneVal >= THRESHOLD_POTENTIAL) {
						port[P_NEURON_OUT_SPACE_PASSAGEWAY].sendMessage(SOUT_ACTION_POTENTIAL, null, this);
						((XhLife)port[P_MEMBRANE_POTENTIAL]).pheneVal = RESTING_POTENTIAL;
						if (shouldshowVisualPathway) {
							println(getName() + " is sending an action potential to "
								+ port[P_NEURON_OUT_SPACE_PASSAGEWAY].getName() + ".");
						}
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		} // end AxonHillock

		else if (xhc.hasAncestor("AxonSegment")) { // AxonSegment
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SOUT_ACTION_POTENTIAL:
					port[P_NEURON_OUT_SPACE_PASSAGEWAY].sendMessage(SOUT_ACTION_POTENTIAL, null, this);
					if (shouldshowVisualPathway) {
						println(getName() + " is sending an action potential to "
							+ port[P_NEURON_OUT_SPACE_PASSAGEWAY].getName() + ".");
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		} // end AxonSegment

		else if (xhc.hasAncestor("TerminalButtons")) { // TerminalButtons
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SOUT_ACTION_POTENTIAL:
					// forward to all instances of TerminalButton
					for (int ii = 0; ii < port.length; ii++) {
						if (port[ii] != null) {
							port[ii].sendMessage(SOUT_ACTION_POTENTIAL, null, this);
						}
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		} // end TerminalButtons

		else if (xhc.hasAncestor("VoltageGatedChannel")) { // VoltageGatedChannel
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SOUT_ACTION_POTENTIAL:
					// TODO need to decrement the value somewhere else
					((XhLife)port[P_SM_INT1]).incPheneVal( 1000000000 ); // inc
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		} // end VoltageGatedChannel

		
		else if (xhc.hasAncestor("SynapticVesicleBilayer")) { // SynapticVesicleBilayer
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SOUT_DO_EXOCYTOSIS:
					// Fuse with Terminal Button Membrane.
					// Transfer contents of Vesicle, including all types of neurotransmitters, outside the Cell.
					if ((port[P_SM_INT1] != null) && (port[P_SYN_CLEFT] != null)) {
						((XhLife)port[P_SM_INT1]).decPheneVal( 1000 );
						((XhLife)port[P_SYN_CLEFT]).incPheneVal( 1000 );
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		} // end SynapticVesicleBilayer

		else if (xhc.hasAncestor("MuscleFiber")) { // MuscleFiber
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SOUT_POST_SYNAPTIC_POTENTIAL:
					// send a Chew message to the Mouth
					port[P_MOUTH].sendMessage(SOUT_CHEW, null, this);
					if (shouldshowMotorPathway) {
						println(getName() + " is moving " + port[P_MOUTH].getName() + ".");
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		} // end MuscleFiber

		else if (xhc.hasAncestor("Mouth")) { // Mouth
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SOUT_CHEW:
					// chew on the lettuce and deliver the food to the digestive system
					XhLife smEat = null; // an SM in the Lettuce
					XhLife smGut = null; // an SM in the SmallIntestine
					double smVal = 0;
					Vector eatV = port[P_EAT].getChildNodes(false); // get all SM in lettuce
					if (shouldshowDigSystem) {
						println(getName() + " is chewing on " + port[P_EAT].getName() + ".");
					}
					boolean shouldDigest = false;
					//while (eatIt.hasNext()) {
					//	smEat = (XhLife)eatIt.next();
					for (i = 0; i < eatV.size(); i++) {
						smEat = (XhLife)eatV.elementAt(i);
						smGut = (XhLife)getXPath().evaluate(smEat.getXhcName(), port[P_GUT]);
						if (smGut == null) {
							// create an instance of this BioEnity
							try {
								smGut = (XhLife)getFactory().getXholonNode();
							} catch (XholonConfigurationException e) {
								logger.error(e.getMessage(), e.getCause());
								return;
							}
							smGut.setId(getNextId());
							if (port[P_GUT].hasChildNodes()) {
								smGut.setParentSiblingLinks(port[P_GUT].getLastChild());
							}
							else {
								smGut.setParentChildLinks( port[P_GUT] );
							}
							smGut.setXhc(smEat.getXhc());
							smGut.pheneVal = 0;
						}
						// only chew if there's at least a bites-worth left
						// move 1% of food from stomach to small intestine
						// if only a small amount left, move it all
						smVal = smEat.getPheneVal();
						if (smVal > 1000) {
							smVal = smVal / 100;
						}
						if (smVal > 0) {
							smEat.decPheneVal(smVal);
							smGut.incPheneVal(smVal);
							shouldDigest = true;
						}
					}
					if (shouldDigest) {
						port[P_DIGESTION].sendMessage(SOUT_DIGEST, null, this);
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		} // end Mouth

		else if (xhc.hasAncestor("SmallIntestine")) { // SmallIntestine
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SOUT_DIGEST:
					// digest the food by moving it from the stomach to the small intestine
					if (shouldshowDigSystem) {
						println(getName() + " is digesting " + port[P_FOOD].getName()
								+ " in " + port[P_FOOD].getParentNode().getName() + ".");
					}
					XhLife smFood = null; // an SM in the Stomach Food
					XhLife smDFood = null; // an SM in the SmallIntestine DigestedFood
					double smVal = 0;
					//String smName = null;
					Vector foodV = port[P_FOOD].getChildNodes(false); // get all SM in lettuce
					boolean shouldDigest = false;
					//while (foodIt.hasNext()) {
					//	smFood = (XhLife)foodIt.next();
					for (i = 0; i < foodV.size(); i++) {
						smFood = (XhLife)foodV.elementAt(i);
						smDFood = (XhLife)getXPath().evaluate(smFood.getXhcName(), port[P_DIGESTED_FOOD]);
						if (smDFood == null) {
							// create an instance of this BioEnity
							try {
								smDFood = (XhLife)getFactory().getXholonNode();
							} catch (XholonConfigurationException e) {
								logger.error(e.getMessage(), e.getCause());
								return;
							}
							smDFood.setId(getNextId());
							if (port[P_DIGESTED_FOOD].hasChildNodes()) {
								smDFood.setParentSiblingLinks(port[P_DIGESTED_FOOD].getLastChild());
							}
							else {
								smDFood.setParentChildLinks( port[P_DIGESTED_FOOD] );
							}
							smDFood.setXhc(smFood.getXhc());
							smDFood.pheneVal = 0;
						}
						// move 10% of food from stomach to small intestine
						// if only a small amount left, move it all
						smVal = smFood.getPheneVal();
						if (smVal > 100) {
							smVal = smVal / 10;
						}
						if (smVal > 0) {
							smFood.decPheneVal(smVal);
							smDFood.incPheneVal(smVal);
							shouldDigest = true;
						}
					}
					if (shouldDigest) {
						port[P_DIGESTION].sendMessage(SOUT_DIGEST, null, this);
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		} // end Mouth

		else if (xhc.hasAncestor("Rectum")) { // Rectum
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SOUT_DIGEST:
					//println("   ---> Rectum is working <---");
					XhLife smDFood = null; // an SM in the SmallIntestine DigestedFood
					double smVal = 0;
					Vector dFoodV = port[P_COMPLETELY_DIGESTED_FOOD].getChildNodes(false);
					//while (dFoodIt.hasNext()) {
					//	smDFood = (XhLife)dFoodIt.next();
					for (i = 0; i < dFoodV.size(); i++) {
						smDFood = (XhLife)dFoodV.elementAt(i);
						// move 10% of food from stomach to small intestine
						// if only a small amount left, move it all
						smVal = smDFood.getPheneVal();
						if (smVal > 100) {
							smVal = smVal / 10;
						}
						if (smVal > 0) {
							smDFood.decPheneVal(smVal);
							((XhLife)port[P_ORGANIC_MATTER]).incPheneVal(smVal);
						}
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		} // end Rectum
		
		else if (xhc.hasAncestor("Ventricle")) { // LeftVentricle RightVentricle
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SOUT_TICK:
					// move blood plasma to an artery
					if (getNumChildren(false) > NUM_BP_PER_BV * 2) { // Ventricles have more blood than BloodVessels
						IXholon bloodPlasma = null;
						for (int ii = 0; ii < 3; ii++) {
							bloodPlasma = getFirstChild();
							if (bloodPlasma != null) {
								switch(ii) {
								case 0:
									if (port[P_BLOOD_FLOW_1] != null) {
										bloodPlasma.removeChild();
										port[P_BLOOD_FLOW_1].sendMessage(SOUT_BLOOD_PLASMA, bloodPlasma, this);
										if (shouldshowCircSystem) {
											println(getName() + " is pumping " + bloodPlasma.getName()
												+ " to " + port[P_BLOOD_FLOW_1].getName() + ".");
										}
									}
									break;
								case 1:
									if (port[P_BLOOD_FLOW_2] != null) {
										bloodPlasma.removeChild();
										port[P_BLOOD_FLOW_2].sendMessage(SOUT_BLOOD_PLASMA, bloodPlasma, this);
									}
									break;
								case 2:
									if (port[P_BLOOD_FLOW_3] != null) {
										bloodPlasma.removeChild();
										port[P_BLOOD_FLOW_3].sendMessage(SOUT_BLOOD_PLASMA, bloodPlasma, this);
									}
									break;
								default:
									break;
								}
							}
						}
					}
					break;
				case SOUT_BLOOD_PLASMA:
					// get flowing blood plasma
					IXholon bloodPlasmaIn = (IXholon)msg.getData();
					bloodPlasmaIn.appendChild(this);
					break;
				default:
					System.err.println(getXhcName() + " invalid event " + event);
					break;
				}
				break;
			default:
				System.err.println(getXhcName() + " invalid state " + state);
				break;
			}
		} // end Ventricle

		else if (xhc.hasAncestor("BloodVessel")) { // Artery Capillary Vein
			// bloodPlasma units act like a LIFO queue, because of the ordering of the children of bloodVessel
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SOUT_BLOOD_PLASMA:
					// get flowing blood plasma
					IXholon bloodPlasmaIn = (IXholon)msg.getData();
					bloodPlasmaIn.appendChild(this);
					// possibly move other plasma plasma along to next blood vessel
					if (getNumChildren(false) > NUM_BP_PER_BV) {
						IXholon bloodPlasmaOut = getFirstChild();
						if (bloodPlasmaOut != null) {
							if (port[P_BLOOD_FLOW] != null) {
								bloodPlasmaOut.removeChild();
								port[P_BLOOD_FLOW].sendMessage(SOUT_BLOOD_PLASMA, bloodPlasmaOut, this);
								if (shouldshowCircSystem) {
									println(getName() + " is flowing " + bloodPlasmaOut.getName()
										+ " to " + port[P_BLOOD_FLOW].getName() + ".");
								}
							}
						}
					}
					break;
				default:
					System.err.println(getXhcName() + " invalid event " + event);
					break;
				}
				break;
			default:
				System.err.println(getXhcName() + " invalid state " + state);
				break;
			}
		} // end BloodVessel

		else {
			// from Rcs_GP_FSM
			switch(xhc.getId()) {
			default:
				IXholon node = getFirstChild();
				while (node != null) {
					if (node.getClass() == StateMachineEntity.class) {
						((StateMachineEntity)node).doStateMachine(msg); // StateMachine
						break;
					}
					else {
						node = node.getNextSibling();
					}
				}
				if (node == null) {
					println("XhRcs_GP_FSM: message with no receiver " + msg);
				}
				break;
			}
		}
	}
	
	// from Rcs_GP_FSM
	public void performActivity(int activityId, IMessage msg)
	{
		switch (activityId) {
		case 648819534: // GPase  
// initialize substrate and product amounts
//port[Product].setVal(0.0); // not needed
//port[Substrate].setVal(50.0); // not needed
			break;
		case 446219361: // doActivity Active
if (port[Substrate].getVal() > 0.0) {
  ((XhLife)port[Substrate]).decPheneVal(1);
  ((XhLife)port[Product]).incPheneVal(1);
}
if (shouldshowRcs_GP_FSM) {
	println("At timestep " + getApp().getTimeStep()
		+ ", " + getName() + " is slicing " + port[Substrate].getName()
		+ " into " + port[Product].getName() + " "
		+ port[Substrate].getVal() + "," + port[Product].getVal() + " .");
}
			break;
		case 856619429: // doActivity Active
rInt = org.primordion.xholon.util.MiscRandom.getRandomInt(0, (int)port[Substrate].getVal()+1);
if (rInt == 0) {
  port[Regulation].sendMessage(S_ACTIVATE, null, this);
}
			break;
		case 199619492: // doActivity Active
rInt = (int)port[Substrate].getVal();
if (rInt < MAX_SUB) {
  rInt = org.primordion.xholon.util.MiscRandom.getRandomInt(rInt, MAX_SUB+1);
}
if (rInt >= MAX_SUB) {
  port[Regulation].sendMessage(S_DEACTIVATE, null, this);
}
			break;
		default:
			println("XhRcs_GP_FSM: performActivity() unknown Activity " + activityId);
			break;
		}
	}

	// from Rcs_GP_FSM
	public boolean performGuard(int activityId, IMessage msg)
	{
		switch (activityId) {
		default:
			println("XhRcs_GP_FSM: performGuard() unknown Activity " + activityId);
			return false;
		}
	}
	
	/**
	 * Create a synapse.
	 * This should be invoked by an instance of SynapticVesicleBilayer.
	 * NmdaReceptor is a subclass of LgNeuroreceptor.
	 */
	protected void createSynapse() {
		//println("createSynapse() " + getName() + ": creating a synapse");
		IXholon peer = getXPath().evaluate("ancestor::TerminalButton/attribute::port[2]/descendant::LgNeuroreceptor", this);
		if (peer == null) {
			// don't create a synapse if there is no peer
			return;
		}
		IXholon synapses = getXPath().evaluate("ancestor::ExtraCellularSpace/Synapses", this);
		if (synapses == null) {
			return;
		}
		// create a synaptic cleft, along with its internal structure
		IXholon synParentNode = synapses;
		IXholon synCleft = null;
		
		try {
			// SynapticCleft
			synCleft = getFactory().getXholonNode();
			synCleft.setId(getNextId());
			// handle any exisitng synaptic clefts already in Synapses
			if (synapses.hasChildNodes()) {
				synCleft.setParentSiblingLinks(synParentNode.getLastChild());
			}
			else {
				synCleft.setParentChildLinks( synParentNode );
			}
			synCleft.setXhc(getClassNode("SynapticCleft"));
			
			// SynapticCleftFluid
			IXholon newNode = null;
			synParentNode = synCleft;
			newNode = getFactory().getXholonNode();
			newNode.setId(getNextId());
			newNode.setParentChildLinks( synParentNode );
			newNode.setXhc(getClassNode("SynapticCleftFluid"));
			
			// Choline
			synParentNode = newNode;
			newNode = getFactory().getXholonNode();
			newNode.setId(getNextId());
			newNode.setParentChildLinks( synParentNode );
			newNode.setXhc(getClassNode("Choline"));
			((XhLife)newNode).pheneVal = 100000;
			
			// Acetate
			newNode = getFactory().getXholonNode();
			newNode.setId(getNextId());
			newNode.setParentSiblingLinks(synParentNode.getLastChild());
			newNode.setXhc(getClassNode("Acetate"));
			((XhLife)newNode).pheneVal = 100000;
			
			// Glutamate
			newNode = getFactory().getXholonNode();
			newNode.setId(getNextId());
			newNode.setParentSiblingLinks(synParentNode.getLastChild());
			newNode.setXhc(getClassNode("Glutamate"));
			((XhLife)newNode).pheneVal = 100000;
			
			// Point SynapticVesicleBilayer to Glutamate in the synapse.
			port[P_SYN_CLEFT] = newNode;
			// Point peer LgNeuroreceptor to same Glutamate in the same synapse.
			((XhLife)peer).port[P_SYN_CLEFT] = newNode;
			
			// create an enzyme that can degrade the Glutamate
			synParentNode = synCleft;
			newNode = getFactory().getXholonNode();
			newNode.setId(getNextId());
			newNode.setParentSiblingLinks( synParentNode.getLastChild() );
			// wrong enzyme; Glutamate is actually removed by reuptake
			newNode.setXhc(getClassNode("Acetylcholinesterase"));
			((XhLife)newNode).pheneVal = 1;
			newNode.setPorts(); // set ports for this enzyme active object
		} catch (XholonConfigurationException e) {
			logger.error(e.getMessage(), e.getCause());
			return;
		}
		
		// do any required configuration in the new SynapticCleft subtree
		synCleft.configure();
	}
	
	/**
	 * Move erythrocytes from BoneMarrow into multiple instances of BloodPlasma in the LeftVentricle.
	 * This should be invoked by an instance of BoneMarrow.
	 */
	protected void moveErythrocytes()
	{
		//println("BoneMarrow is moving erythrocytes to leftVentricle/BloodPlasma");
		// get all erythrocytes in BoneMarrow
		Vector erV = getXPath().searchForClosestNeighbors(3, IXholon.NINCLUDE_xxC, null, "Erythrocyte", Integer.MAX_VALUE, false, this);
		Vector bpV = port[P_LEFT_VENTRICLE].getChildNodes(false); // get all bloodPlasma in LeftVentricle
		//Iterator erIt = erV.iterator();
		//Iterator bpIt = bpV.iterator();
		int numEr = erV.size();
		int numBp = bpV.size();
		int numErPerBp = numEr / numBp;
		int i,j;
		int k = 0; // will iterate through erV
		IXholon erythrocyte = null;
		IXholon bloodPlasma = null;
		IXholon nucleus = null;
		for (i = 0; i < numBp; i++) {
			//bloodPlasma = (IXholon)bpIt.next();
			bloodPlasma = (IXholon)bpV.elementAt(i);
			for (j = 0; j < numErPerBp; j++) {
				//erythrocyte = (IXholon)erIt.next();
				erythrocyte = (IXholon)erV.elementAt(k++);
				erythrocyte.removeChild();
				erythrocyte.appendChild(bloodPlasma);
				// remove nucleus of mature erythrocyte
				nucleus = getXPath().evaluate("Nucleus", erythrocyte);
				if (nucleus != null) {
					nucleus.removeChild();
					nucleus.remove();
				}
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(int, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{	
		toXmlAttributes_standard(xholon2xml, xmlWriter);
		if (getPheneVal() != Double.MAX_VALUE) {
			toXmlAttribute(xholon2xml, xmlWriter, "pheneVal", Double.toString(getPheneVal()), double.class);
		}
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		int i = 0;
		String tStr = getName();
		//if ((xhClass.getXhType() & IXholonClass.XhtypePurePassiveObject) == IXholonClass.XhtypePurePassiveObject) {
		if (isPassiveObject()) {
			tStr += " [pheneVal:" + pheneVal
					+ "]";
		}
		//if (((xhClass.getXhType() & IXholonClass.XhtypePureActiveObject) == IXholonClass.XhtypePureActiveObject)
		if (isActiveObject() || (xhc.getXhType() == IXholonClass.XhtypeConfigContainer)) {
			if (xhc.hasAncestor("Enzyme")) {
				tStr += " [reaction:";
				if (port[P_SM_SUB1] != null) {
					tStr += port[P_SM_SUB1].getName();
				}
				else {
					tStr += "null";
				}
				if (port[P_SM_SUB2] != null) {
					tStr += " + " + port[P_SM_SUB2].getName();
				}
				tStr += " --> ";
				if (port[P_SM_PRD1] != null) {
					tStr += port[P_SM_PRD1].getName();
				}
				else {
					tStr += "null";
				}
				if (port[P_SM_PRD2] != null) {
					tStr += " + " + port[P_SM_PRD2].getName();
				}
				tStr += "]";
			}
			else if (xhc.getName().equals("Sun")) {
				if (port[P_SOLAR_ENERGY] != null) {
					tStr += "[lighting:" + port[P_SOLAR_ENERGY].getName() + "]";
				}
			}
			else if ((xhc.hasAncestor("TransportProtein"))
					|| (xhc.hasAncestor("LipidBilayer"))) {
				// bilayer or transporter
				tStr += " [transfer:";
				if (port[P_SM_EXT1] != null) {
					tStr += port[P_SM_EXT1].getName();
				}
				else {
					tStr += "null";
				}
				tStr += " <--> ";
				if (port[P_SM_INT1] != null) {
					tStr += port[P_SM_INT1].getName();
				}
				else {
					tStr += "null";
				}
				if ((port[P_SM_EXT2] != null) && (port[P_SM_INT2] != null)) {
					tStr += ", " + port[P_SM_EXT2].getName();
					tStr += " <--> " + port[P_SM_INT2].getName();
				}
				if ((port[P_SM_EXT3] != null) && (port[P_SM_INT3] != null)) {
					tStr += ", " + port[P_SM_EXT3].getName();
					tStr += " <--> " + port[P_SM_INT3].getName();
				}
				if ((port[P_SM_EXT4] != null) && (port[P_SM_INT4] != null)) {
					tStr += ", " + port[P_SM_EXT4].getName();
					tStr += " <--> " + port[P_SM_INT4].getName();
				}
				tStr += "]";
			}
			else { // show any active ports
				if (port != null) {
					tStr += "[";
					for (i = 0; i < port.length; i++) {
						if (port[i] != null) {
							tStr += " port:" + port[i].getName();
						}
					}
					tStr += "]";
				}
				else {
					tStr += " [active object with no port array]";
				}
			}
		}
		// Show Xholon instances that reference this BioEnity instance
		List<IXholon> rnV = searchForReferencingNodes();
		if (!rnV.isEmpty()) {
			tStr += " # reffed by: ";
			for (i = 0; i < rnV.size(); i++) {
				tStr += rnV.get(i).getName() + " ";
			}
			tStr += "#";
		}
		return tStr;
	}
}
