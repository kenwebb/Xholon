package org.primordion.cellontro.app.sbml;

import org.primordion.xholon.base.XholonClass;

/**
<body xmlns="http://www.w3.org/1999/xhtml">
<p>This SBML file has been generated by an early version of the Cellontro SBML exporter.
The file contains only structure, and no real kinetics.
KineticLaw parameters are included, along with dummy plus functions
that may make it easier for other software to read in the parameters.
</p></body>
 */
public class XhCellModel extends XhAbstractSbml {

// indices into the port array; refs to substrates & products
public static final int P_SM_SUB1 = 0;
public static final int P_SM_SUB2 = 1;
public static final int P_SM_PRD1 = 2;
public static final int P_SM_PRD2 = 3;
public static final int P_SM_MOD1 = 4;

// Unit definitions (not yet supported).
/*
<unitDefinition id="volume">
  <listOfUnits>
    <unit kind="litre" scale="-3" multiplier="1" offset="0"/>
  </listOfUnits>
</unitDefinition>
<unitDefinition id="substance">
  <listOfUnits>
    <unit kind="item" multiplier="1" offset="0"/>
  </listOfUnits>
</unitDefinition>
*/

// Maximum number of reactants, products, modifiers in any reaction.
protected static int numReactants = 2;
protected static int numProducts = 2;
protected static int numModifiers = 1;

// Compartment sizes.
protected static double amtExtraCellularSpace = 1.0;
protected static double amtExtraCellularSolution = 1.0;
protected static double amtEukaryoticCell = 1.0;
protected static double amtCellMembrane = 1.0;
protected static double amtCytoplasm = 1.0;
protected static double amtCytosol = 1.0;
protected static double amtMitochondrion = 1.0;
protected static double amtMitochondrialDualMembrane = 1.0;
protected static double amtMitochondrialOuterMembrane = 1.0;
protected static double amtMitochondrialIntermembraneSpace = 1.0;
protected static double amtMitochondrialIntermembranesol = 1.0;
protected static double amtMitochondrialInnerMembrane = 1.0;
protected static double amtMatrix = 1.0;
protected static double amtMatrixsol = 1.0;
protected static double amtNucleus = 1.0;

// Amount of each species, initialized to 0.0.
// Actual values are in CellModel_CompositeStructureHierarchy.xml.
protected static double amtWater = 0.0;
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
protected static double amtAcetylCoA = 0.0;
protected static double amtCitrate = 0.0;
protected static double amtIsocitrate = 0.0;
protected static double amtA_Ketoglutarate = 0.0;
protected static double amtSuccinylCoa = 0.0;
protected static double amtSuccinate = 0.0;
protected static double amtFumarate = 0.0;
protected static double amtMalate = 0.0;
protected static double amtOxaloacetate = 0.0;

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
case CeCellModel.CellBilayerCE:
	amtGlucose = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	amtWater = ((XhCellModel)port[P_SM_SUB2]).getPheneVal();
	amtGlucose = ((XhCellModel)port[P_SM_PRD1]).getPheneVal();
	amtWater = ((XhCellModel)port[P_SM_PRD2]).getPheneVal();
	nTimes = (0.0 + 10.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeCellModel.MitochondrialOuterBilayerCE:
	amtPyruvate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	amtPyruvate = ((XhCellModel)port[P_SM_PRD1]).getPheneVal();
	nTimes = (0.0 + 10.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.MitochondrialInnerBilayerCE:
	amtWater = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	amtWater = ((XhCellModel)port[P_SM_PRD1]).getPheneVal();
	nTimes = (0.0 + 10.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.PyruvateTransporterCE:
	amtPyruvate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	amtPyruvate = ((XhCellModel)port[P_SM_PRD1]).getPheneVal();
	nTimes = (0.0 + 10.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.PyruvateDehydrogenaseCE:
	amtPyruvate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 1000.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.CitrateSynthaseCE:
	amtAcetylCoA = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	amtOxaloacetate = ((XhCellModel)port[P_SM_SUB2]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 900.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.AconitaseCE:
	amtCitrate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 800.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.IsocitrateDehydrogenaseCE:
	amtIsocitrate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 700.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.A_KetoglutarateDehydrogenaseCE:
	amtA_Ketoglutarate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 600.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.SuccinylCoASynthetaseCE:
	amtSuccinylCoa = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 500.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.SuccinateDehydrogenaseCE:
	amtSuccinate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 400.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.FumarateHydrataseCE:
	amtFumarate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 300.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.MalateDehydrogenaseCE:
	amtMalate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 200.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.HexokinaseCE:
	amtGlucose = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	amtGlucose_6_Phosphate = ((XhCellModel)port[P_SM_MOD1]).getPheneVal();
	nTimes = (((0.0 + 1500.0) + 100.0) + 500.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.PhosphoGlucoIsomeraseCE:
	amtGlucose_6_Phosphate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 10.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.PhosphoFructokinaseCE:
	amtFructose_6_Phosphate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 40.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.AldolaseCE:
	amtFructose_1x6_Biphosphate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 70.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeCellModel.TriosePhosphateIsomeraseCE:
	amtDihydroxyacetonePhosphate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 400.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.Glyceraldehyde_3_phosphateDehydrogenaseCE:
	amtGlyceraldehyde_3_Phosphate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 200.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.PhosphoGlycerokinaseCE:
	amtX1x3_BisphosphoGlycerate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 300.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.PhosphoGlyceromutaseCE:
	amtX3_PhosphoGlycerate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 500.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.EnolaseCE:
	amtX2_PhosphoGlycerate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 150.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeCellModel.PyruvateKinaseCE:
	amtPhosphoEnolPyruvate = ((XhCellModel)port[P_SM_SUB1]).getPheneVal();
	nTimes = ((0.0 + 10000.0) + 180.0);
	((XhCellModel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhCellModel)port[P_SM_PRD1]).incPheneVal(nTimes);
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
//	System.err.println("XhCellModel act() stack overflow: " + this + " ");
//	return;
//}
} // end method
} // end class