package org.primordion.cellontro.app.sbml;

import org.primordion.xholon.base.XholonClass;

/**
<body xmlns="http://www.w3.org/1999/xhtml">This model has the same values as RRT model.
July 23, 2003

December 10, 2005
Changed the "Quantity unit" from mMole to #.
Removed Fructose_2x6_Biphosphate which wasn't being used by any reaction.
</body>
 */
public class XhGlycolysis extends XhAbstractSbml {

// indices into the port array; refs to substrates & products
public static final int P_SM_SUB1 = 0;
public static final int P_SM_PRD1 = 1;
public static final int P_SM_PRD2 = 2;

// Unit definitions (not yet supported).
/*
<unitDefinition id="volume">
  <listOfUnits>
    <unit kind="litre" scale="-3" multiplier="1" offset="0"/>
  </listOfUnits>
</unitDefinition>
<unitDefinition id="substance">
  <listOfUnits>
    <unit kind="item" scale="1" multiplier="1" offset="0"/>
  </listOfUnits>
</unitDefinition>
*/

// Maximum number of reactants, products, modifiers in any reaction.
protected static int numReactants = 1;
protected static int numProducts = 2;
protected static int numModifiers = 0;

// Compartment sizes.
protected static double amtCytosol = 1.0;

// Amount of each species, initialized to 0.0.
// Actual values are in Glycolysis_CompositeStructureHierarchy.xml.
protected static double amtGlucose = 0.0;
protected static double amtGlucose_6_Phosphate = 0.0;
protected static double amtFructose_6_Phosphate = 0.0;
protected static double amtFructose_1x6_Biphosphate = 0.0;
protected static double amtDihydroxyacetonePhosphate = 0.0;
protected static double amtGlyceraldehyde_3_Phosphate = 0.0;
protected static double amtX1x3_BisphosphoGlycerate = 0.0;
protected static double amtX3_PhosphoGlycerate = 0.0;
protected static double amtX2_PhosphoGlycerate = 0.0;
protected static double amtPhosphoEnolPyruvate = 0.0;
protected static double amtPyruvate = 0.0;

// SBML user-defined functions.

protected double function_0(double substrate, double Km, double V) {
return (V * substrate / (Km + substrate));
}

// Getters and setters.
public int getNumReactants() {return numReactants;}
public void setNumReactants(int numR) {numReactants = numR;}

public int getNumProducts() {return numProducts;}
public void setNumProducts(int numP) {numProducts = numP;}

public int getNumModifiers() {return numModifiers;}
public void setNumModifiers(int numM) {numModifiers = numM;}

/*
 * @see org.primordion.xholon.base.IXholon#act()
 */
public void act() {
if ((xhc.getXhType() & XholonClass.XhtypePureActiveObject) == XholonClass.XhtypePureActiveObject) {
switch(getXhcId()) {
case CeGlycolysis.Gly1CE:
	amtGlucose = ((XhGlycolysis)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtCytosol * function_0(amtGlucose, 1500.0, 100.0));
	((XhGlycolysis)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhGlycolysis)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeGlycolysis.Gly2CE:
	amtGlucose_6_Phosphate = ((XhGlycolysis)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtCytosol * function_0(amtGlucose_6_Phosphate, 10000.0, 10.0));
	((XhGlycolysis)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhGlycolysis)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeGlycolysis.Gly3CE:
	amtFructose_6_Phosphate = ((XhGlycolysis)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtCytosol * function_0(amtFructose_6_Phosphate, 10000.0, 40.0));
	((XhGlycolysis)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhGlycolysis)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeGlycolysis.Gly4CE:
	amtFructose_1x6_Biphosphate = ((XhGlycolysis)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtCytosol * function_0(amtFructose_1x6_Biphosphate, 10000.0, 70.0));
	((XhGlycolysis)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhGlycolysis)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhGlycolysis)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeGlycolysis.Gly5CE:
	amtDihydroxyacetonePhosphate = ((XhGlycolysis)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtCytosol * function_0(amtDihydroxyacetonePhosphate, 10000.0, 400.0));
	((XhGlycolysis)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhGlycolysis)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeGlycolysis.Gly6CE:
	amtGlyceraldehyde_3_Phosphate = ((XhGlycolysis)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtCytosol * function_0(amtGlyceraldehyde_3_Phosphate, 10000.0, 300.0));
	((XhGlycolysis)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhGlycolysis)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeGlycolysis.Gly7CE:
	amtX1x3_BisphosphoGlycerate = ((XhGlycolysis)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtCytosol * function_0(amtX1x3_BisphosphoGlycerate, 10000.0, 300.0));
	((XhGlycolysis)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhGlycolysis)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeGlycolysis.Gly8CE:
	amtX3_PhosphoGlycerate = ((XhGlycolysis)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtCytosol * function_0(amtX3_PhosphoGlycerate, 10000.0, 500.0));
	((XhGlycolysis)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhGlycolysis)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeGlycolysis.Gly9CE:
	amtX2_PhosphoGlycerate = ((XhGlycolysis)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtCytosol * function_0(amtX2_PhosphoGlycerate, 10000.0, 150.0));
	((XhGlycolysis)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhGlycolysis)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeGlycolysis.Gly10CE:
	amtPhosphoEnolPyruvate = ((XhGlycolysis)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtCytosol * function_0(amtPhosphoEnolPyruvate, 10000.0, 180.0));
	((XhGlycolysis)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhGlycolysis)port[P_SM_PRD1]).incPheneVal(nTimes);
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
//	System.err.println("XhGlycolysis act() stack overflow: " + this + " ");
//	return;
//}
} // end method
} // end class
