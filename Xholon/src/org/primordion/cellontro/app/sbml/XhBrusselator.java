package org.primordion.cellontro.app.sbml;

import org.primordion.xholon.base.IIntegration;
import org.primordion.xholon.base.XholonClass;

/**
<html><head><meta name="qrichtext" content="1"></meta></head><body style="font-size:8ptfont-family:MS Shell Dlg">
<p>The famous Brusselator model in which a chemical species X catalyses its own formation. In a certain region of parameter space the Brusselator can function in a limit cycle regime around an unstable steady state. Run this simulation and then check the report file to see how Gepasi can find an unstable steady state, and calculate the eigenvalues of the Jacobian matrix. </p>
<p>Adapted from Copasi example, January 18, 2007, Ken Webb.</p>
</body></html>
 */
public class XhBrusselator extends XhAbstractSbml {

// indices into the port array; refs to substrates & products
public static final int P_SM_SUB1 = 0;
public static final int P_SM_SUB2 = 1;
public static final int P_SM_PRD1 = 2;
public static final int P_SM_PRD2 = 3;

// Unit definitions (not yet supported).
/*
<unitDefinition id="volume">
  <listOfUnits>
    <unit kind="litre" scale="-3" multiplier="1" offset="0"/>
  </listOfUnits>
</unitDefinition>
<unitDefinition id="substance">
  <listOfUnits>
    <unit kind="mole" scale="-3" multiplier="1" offset="0"/>
  </listOfUnits>
</unitDefinition>
*/

// Maximum number of reactants, products, modifiers in any reaction.
protected static int numReactants = 2;
protected static int numProducts = 2;
protected static int numModifiers = 0;

// Compartment sizes.
protected static double amtcompartment = 1.0;

// Amount of each species, initialized to 0.0.
// Actual values are in Brusselator_CompositeStructureHierarchy.xml.
protected static double amtX = 0.0;
protected static double amtY = 0.0;
protected static double amtE = 0.0;
protected static double amtD = 0.0;
protected static double amtB = 0.0;
protected static double amtA = 0.0;

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
case CeBrusselator.R1CE:
	amtA = ((XhBrusselator)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcompartment * 1.0) * amtA) / timeStepMultiplier;
	((XhBrusselator)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeBrusselator.R2CE:
	amtX = ((XhBrusselator)port[P_SM_SUB1]).getPheneVal();
	amtY = ((XhBrusselator)port[P_SM_SUB2]).getPheneVal();
	nTimes = (((amtcompartment * 1.0) * Math.pow(amtX,2.0)) * amtY) / timeStepMultiplier;
	((XhBrusselator)port[P_SM_SUB1]).decPheneVal(nTimes * 2.0);
	((XhBrusselator)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhBrusselator)port[P_SM_PRD1]).incPheneVal(nTimes * 3.0);
	break;

case CeBrusselator.R3CE:
	amtX = ((XhBrusselator)port[P_SM_SUB1]).getPheneVal();
	amtB = ((XhBrusselator)port[P_SM_SUB2]).getPheneVal();
	nTimes = (((amtcompartment * 1.0) * amtX) * amtB) / timeStepMultiplier;
	((XhBrusselator)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhBrusselator)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeBrusselator.R4CE:
	amtX = ((XhBrusselator)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((amtcompartment * 1.0) * amtX) / timeStepMultiplier;
	((XhBrusselator)port[P_SM_SUB1]).decPheneVal(nTimes);
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
//	System.err.println("XhBrusselator act() stack overflow: " + this + " ");
//	return;
//}
} // end method
} // end class
