package org.primordion.cellontro.app.sbml;

import org.primordion.xholon.base.IIntegration;
import org.primordion.xholon.base.XholonClass;

/**
<html><head><meta name="qrichtext" content="1"></meta></head><body style="font-size:8ptfont-family:MS Shell Dlg">
<p>This is a model of the Drosophila circadian clock based on the PER and TIM genes, as published in Leloup J.-C. & Goldbeter A. (1999) "Chaos and birhythmicity in a model of circadian oscillations of the PER and TIM proteins in Drosophila" J. Theor. Biol. 198, 445-459 </p>
</body></html>
 */
public class XhCircadianClock extends XhAbstractSbml {

// indices into the port array; refs to substrates & products
public static final int P_SM_SUB1 = 0;
public static final int P_SM_SUB2 = 1;
public static final int P_SM_PRD1 = 2;
public static final int P_SM_PRD2 = 3;
public static final int P_SM_MOD1 = 4;

// Unit definitions (not yet supported).
/*
<unitDefinition id="time">
  <listOfUnits>
    <unit kind="second" multiplier="3600" offset="0"/>
  </listOfUnits>
</unitDefinition>
<unitDefinition id="substance">
  <listOfUnits>
    <unit kind="mole" scale="-9" multiplier="1" offset="0"/>
  </listOfUnits>
</unitDefinition>
*/

// Maximum number of reactants, products, modifiers in any reaction.
protected static int numReactants = 2;
protected static int numProducts = 2;
protected static int numModifiers = 1;

// Compartment sizes.
protected static double amtcell = 1.0;

// Amount of each species, initialized to 0.0.
// Actual values are in CircadianClock_CompositeStructureHierarchy.xml.
protected static double amtP0 = 0.0;
protected static double amtMt = 0.0;
protected static double amtMp = 0.0;
protected static double amtT0 = 0.0;
protected static double amtP1 = 0.0;
protected static double amtP2 = 0.0;
protected static double amtT1 = 0.0;
protected static double amtT2 = 0.0;
protected static double amtC = 0.0;
protected static double amtCn = 0.0;

// time step multiplier (you may need to make this bigger or smaller)
public static int timeStepMultiplier = IIntegration.M_128; // M_128

// SBML user-defined functions.

protected double function_1(double V, double substrate, double Km) {
return (V * substrate / (Km + substrate));
}

protected double Transcription(double V, double K, double n, double modifier) {
return (V * Math.pow(K, n) / (Math.pow(K, n) + Math.pow(modifier, n)));
}

protected double function_0(double k, double modifier) {
return (k * modifier);
}

// Getters and setters.
public int getNumReactants() {return numReactants;}
public void setNumReactants(int numR) {numReactants = numR;}

public int getNumProducts() {return numProducts;}
public void setNumProducts(int numP) {numProducts = numP;}

public int getNumModifiers() {return numModifiers;}
public void setNumModifiers(int numM) {numModifiers = numM;}

public int getTimeStepMultiplier() {return timeStepMultiplier;}
public static void setTimeStepMultiplier(int tms) {timeStepMultiplier = tms;}

/*
 * @see org.primordion.xholon.base.IXholon#act()
 */
public void act() {
if ((xhc.getXhType() & XholonClass.XhtypePureActiveObject) == XholonClass.XhtypePureActiveObject) {
switch(getXhcId()) {
case CeCircadianClock.sPCE:
	amtMp = ((XhCircadianClock)port[P_SM_MOD1]).getPheneVal();
	nTimes = (amtcell * function_0(0.9, amtMp)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCircadianClock.Mp_transcriptionCE:
	amtCn = ((XhCircadianClock)port[P_SM_MOD1]).getPheneVal();
	nTimes = (amtcell * Transcription(1.0, 0.9, 4.0, amtCn)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCircadianClock.Mp_degradationCE:
	amtMp = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcell * function_1(0.7, amtMp, 0.2)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.Mt_transcriptionCE:
	amtCn = ((XhCircadianClock)port[P_SM_MOD1]).getPheneVal();
	nTimes = (amtcell * Transcription(1.0, 0.9, 4.0, amtCn)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCircadianClock.Mt_degradationCE:
	amtMt = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcell * function_1(0.7, amtMt, 0.2)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.sTCE:
	amtMt = ((XhCircadianClock)port[P_SM_MOD1]).getPheneVal();
	nTimes = (amtcell * function_0(0.9, amtMt)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCircadianClock._1PCE:
	amtP0 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcell * function_1(8.0, amtP0, 2.0)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCircadianClock._2PCE:
	amtP1 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcell * function_1(1.0, amtP1, 2.0)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCircadianClock._3PCE:
	amtP1 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcell * function_1(8.0, amtP1, 2.0)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCircadianClock._4PCE:
	amtP2 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcell * function_1(1.0, amtP2, 2.0)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCircadianClock._1TCE:
	amtT0 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcell * function_1(8.0, amtT0, 2.0)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCircadianClock._2TCE:
	amtT1 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcell * function_1(1.0, amtT1, 2.0)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCircadianClock._3TCE:
	amtT1 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcell * function_1(8.0, amtT1, 2.0)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCircadianClock._4TCE:
	amtT2 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcell * function_1(1.0, amtT2, 2.0)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCircadianClock.dPCE:
	amtP2 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcell * function_1(2.0, amtP2, 0.2)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.dTCE:
	amtT2 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcell * function_1(2.0, amtT2, 0.2)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.Mt_dilutionCE:
	amtMt = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 0.01) * amtMt) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.Mp_dilutionCE:
	amtMp = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 0.01) * amtMp) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.P0_dilutionCE:
	amtP0 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 0.01) * amtP0) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.P1_dilutionCE:
	amtP1 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 0.01) * amtP1) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.P2_dilutionCE:
	amtP2 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 0.01) * amtP2) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.T0_dilutionCE:
	amtT0 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 0.01) * amtT0) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.T1_dilutionCE:
	amtT1 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 0.01) * amtT1) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.T2_dilutionCE:
	amtT2 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 0.01) * amtT2) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.C_dilutionCE:
	amtC = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 0.01) * amtC) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.Cn_dilutionCE:
	amtCn = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 0.01) * amtCn) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.T2_degradationCE:
	amtT2 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcell * function_1(2.0, amtT2, 0.2)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.P2_degradationCE:
	amtP2 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcell * function_1(2.0, amtP2, 0.2)) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	break;

case CeCircadianClock.PT_binding_forwardCE:
	amtP2 = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	amtT2 = ((XhCircadianClock)port[P_SM_SUB2]).getPheneVal();
	nTimes = (((amtcell * 1.2) * amtP2) * amtT2) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCircadianClock)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCircadianClock.PT_binding_backwardCE:
	amtC = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 0.6) * amtC) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhCircadianClock)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeCircadianClock.C_transport_forwardCE:
	amtC = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 0.6) * amtC) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCircadianClock.C_transport_backwardCE:
	amtCn = ((XhCircadianClock)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 0.2) * amtCn) / timeStepMultiplier;
	((XhCircadianClock)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCircadianClock)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

default:
	break;
} // end switch
} // end if

// execute recursively
//try {
	if (firstChild != null) {
		firstChild.act();
	}
	if (nextSibling != null) {
		nextSibling.act();
	}
//} catch (StackOverflowError e) {
//	System.err.println("XhCircadianClock act() stack overflow: " + this + " ");
//	return;
//}
} // end method
} // end class
