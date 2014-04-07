package org.primordion.cellontro.app.sbml;

import org.primordion.xholon.base.IIntegration;
import org.primordion.xholon.base.XholonClass;

/**
<html><head><meta name="qrichtext" content="1"></meta></head><body style="font-size:8ptfont-family:MS Shell Dlg">
<p style="margin-top:18px"><span style="font-size:16ptfont-weight:600">Example of very stiff biochemical model</span></p>
<p>This is a model of an allosteric enzyme following the MWC dimeric model, in which the substrate S interacts only with the enzyme species in the R-state. The system of ODEs of this model is very stiff, and correct results can only be obtained with a stiff ODE solver.</p>
<p>This example comes from Hayashi, K. and Sakamoto, N. (1986) <span style="font-style:italic">Dynamic Analysis of Enzyme Systems</span>, Japan Scientific Societies Press, Tokyo.</p>
<p><span style="font-weight:600">Example for</span>: Time course simulation, plotting</p>
<p><span style="font-weight:600">Actions</span>:</p>
<ol><li>examine the model (under <span style="font-style:italic">Model-Biochemical</span> and <span style="font-style:italic">Model-Mathematical</span>)</li>
<li>run a time course simulation: go to <span style="font-style:italic">Tasks-Time Course</span> and press the <span style="font-style:italic">Run</span> button.</li>
<li>There is a trajectory plot already defined with all variables this will appear when the simulation finished (very fast). Note that the S and P variables have values much higher than the other chemical species, to see the others in the plot, press the buttons for [S] and [P], and the graph rescales.</li></ol>
</body></html>
 */
public class XhDimericMWCstiff extends XhAbstractSbml {

// indices into the port array; refs to substrates & products
public static final int P_SM_SUB1 = 0;
public static final int P_SM_SUB2 = 1;
public static final int P_SM_PRD1 = 2;
public static final int P_SM_PRD2 = 3;

// No units are defined in this SBML model.

// Maximum number of reactants, products, modifiers in any reaction.
protected static int numReactants = 2;
protected static int numProducts = 2;
protected static int numModifiers = 0;

// Compartment sizes.
protected static double amtcell = 1.0E-15;

// Amount of each species, initialized to 0.0.
// Actual values are in DimericMWCstiff_CompositeStructureHierarchy.xml.
protected static double amtR0 = 0.0;
protected static double amtT0 = 0.0;
protected static double amtR1 = 0.0;
protected static double amtS = 0.0;
protected static double amtP = 0.0;
protected static double amtR2 = 0.0;

// time step multiplier (you may need to make this bigger or smaller)
public static int timeStepMultiplier = IIntegration.M_8192; // M_128

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
case CeDimericMWCstiff.reaction1CE:
	amtR0 = ((XhDimericMWCstiff)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 500.0) * amtR0) / timeStepMultiplier;
	((XhDimericMWCstiff)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhDimericMWCstiff)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeDimericMWCstiff.reaction2CE:
	amtT0 = ((XhDimericMWCstiff)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 0.5) * amtT0) / timeStepMultiplier;
	((XhDimericMWCstiff)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhDimericMWCstiff)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeDimericMWCstiff.reaction3CE:
	amtS = ((XhDimericMWCstiff)port[P_SM_SUB1]).getPheneVal();
	amtR0 = ((XhDimericMWCstiff)port[P_SM_SUB2]).getPheneVal();
	nTimes = (((amtcell * 2.0E7) * amtS) * amtR0) / timeStepMultiplier;
	((XhDimericMWCstiff)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhDimericMWCstiff)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhDimericMWCstiff)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeDimericMWCstiff.reaction4CE:
	amtR1 = ((XhDimericMWCstiff)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 500.0) * amtR1) / timeStepMultiplier;
	((XhDimericMWCstiff)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhDimericMWCstiff)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhDimericMWCstiff)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeDimericMWCstiff.reaction7CE:
	amtR2 = ((XhDimericMWCstiff)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 1000.0) * amtR2) / timeStepMultiplier;
	((XhDimericMWCstiff)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhDimericMWCstiff)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhDimericMWCstiff)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeDimericMWCstiff.reaction6CE:
	amtR1 = ((XhDimericMWCstiff)port[P_SM_SUB1]).getPheneVal();
	amtS = ((XhDimericMWCstiff)port[P_SM_SUB2]).getPheneVal();
	nTimes = (((amtcell * 1.0E7) * amtR1) * amtS) / timeStepMultiplier;
	((XhDimericMWCstiff)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhDimericMWCstiff)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhDimericMWCstiff)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeDimericMWCstiff.reaction5CE:
	amtR1 = ((XhDimericMWCstiff)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 1.0) * amtR1) / timeStepMultiplier;
	((XhDimericMWCstiff)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhDimericMWCstiff)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhDimericMWCstiff)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeDimericMWCstiff.reaction8CE:
	amtR2 = ((XhDimericMWCstiff)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcell * 2.0) * amtR2) / timeStepMultiplier;
	((XhDimericMWCstiff)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhDimericMWCstiff)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhDimericMWCstiff)port[P_SM_PRD2]).incPheneVal(nTimes);
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
//	System.err.println("XhDimericMWCstiff act() stack overflow: " + this + " ");
//	return;
//}
} // end method
} // end class
