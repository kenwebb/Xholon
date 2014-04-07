package org.primordion.cellontro.app.sbml;

import org.primordion.xholon.base.IIntegration;
import org.primordion.xholon.base.XholonClass;

/**
<html><head><meta name="qrichtext" content="1"></meta></head><body style="font-size:8ptfont-family:MS Shell Dlg">
<p>From Pritchard and Kell (2002) Eur. J. Biochem. , modified from Teusink et al. (2000) Eur J Biochem 267, 5313-5329. This version uses the Vmaxes found by the best fit (R1) of Table 1 of the Pritchard and Kell paper, and simulates a decrease of external glucose concentration from 100 to 2 mM (More model files at http://users.aber.ac.uk/lep/models.shtml) </p>
<p>Original model name: Yeast glycolysis model of Pritchard and Kell</p>
</body></html>
 */
public class XhYeastGlyPritchardKel extends XhAbstractSbml {

// indices into the port array; refs to substrates & products
public static final int P_SM_SUB1 = 0;
public static final int P_SM_SUB2 = 1;
public static final int P_SM_PRD1 = 2;
public static final int P_SM_PRD2 = 3;
public static final int P_SM_MOD1 = 4;
public static final int P_SM_MOD2 = 5;

// Unit definitions (not yet supported).
/*
<unitDefinition id="volume">
  <listOfUnits>
    <unit kind="litre" scale="-3" multiplier="1" offset="0"/>
  </listOfUnits>
</unitDefinition>
<unitDefinition id="time">
  <listOfUnits>
    <unit kind="second" multiplier="60" offset="0"/>
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
protected static int numModifiers = 2;

// Compartment sizes.
protected static double amtcompartment = 1.0;

// Amount of each species, initialized to 0.0.
// Actual values are in YeastGlyPritchardKel_CompositeStructureHierarchy.xml.
protected static double amtGLCi = 0.0;
protected static double amtATP = 0.0;
protected static double amtG6P = 0.0;
protected static double amtADP = 0.0;
protected static double amtF6P = 0.0;
protected static double amtF16bP = 0.0;
protected static double amtAMP = 0.0;
protected static double amtDHAP = 0.0;
protected static double amtGAP = 0.0;
protected static double amtNAD = 0.0;
protected static double amtBPG = 0.0;
protected static double amtNADH = 0.0;
protected static double amtP3G = 0.0;
protected static double amtP2G = 0.0;
protected static double amtPEP = 0.0;
protected static double amtPYR = 0.0;
protected static double amtAcAld = 0.0;
protected static double amtSuccinate = 0.0;
protected static double amtTrehalose = 0.0;
protected static double amtGlycogen = 0.0;
protected static double amtGlycerol = 0.0;
protected static double amtEtOH = 0.0;
protected static double amtCO2 = 0.0;
protected static double amtF26bP = 0.0;
protected static double amtGLCo = 0.0;

// time step multiplier (you may need to make this bigger or smaller)
public static int timeStepMultiplier = IIntegration.M_128; // M_128

// SBML user-defined functions.

protected double function_15(double A, double k) {
return (k * A);
}

protected double function_14(double Ktrehalose) {
return (Ktrehalose);
}

protected double function_13(double KGLYCOGEN) {
return (KGLYCOGEN);
}

protected double function_12(double A, double B, double P, double Q, double Vmax, double Kdhap, double Knadh, double Keq, double Kglycerol, double Knad) {
return (Vmax * (A / Kdhap * (B / Knadh) - P / Kdhap * (Q / Knadh) * (1 / Keq)) / ((1 + A / Kdhap + P / Kglycerol) * (1 + B / Knadh + Q / Knad)));
}

protected double ATPase(double ATP, double Katpase) {
return (Katpase * ATP);
}

protected double function_11(double A, double B, double P, double Q, double Vmax, double Ketoh, double Kinad, double Keq, double Knad, double Knadh, double Kinadh, double Kacald, double Kiacald, double Kietoh) {
return (Vmax * (A * B / (Ketoh * Kinad) - P * Q / (Ketoh * Kinad * Keq)) / (1 + B / Kinad + A * Knad / (Kinad * Ketoh) + P * Knadh / (Kinadh * Kacald) + Q / Kinadh + A * B / (Kinad * Ketoh) + B * P * Knadh / (Kinad * Kinadh * Kacald) + A * Q * Knad / (Kinad * Kinadh * Ketoh) + P * Q / (Kacald * Kinadh) + A * B * P / (Kinad * Kiacald * Ketoh) + A * P * Q / (Kietoh * Kinadh * Kacald)));
}

protected double function_10(double A, double Vmax, double Kpyr, double nH) {
return (Vmax * Math.pow(A / Kpyr, nH) / (1 + Math.pow(A / Kpyr, nH)));
}

protected double function_9(double A, double B, double P, double Q, double Vmax, double Kpep, double Kadp, double Keq, double Kpyr, double Katp) {
return (Vmax * (A * B / (Kpep * Kadp) - P * Q / (Kpep * Kadp * Keq)) / ((1 + A / Kpep + P / Kpyr) * (1 + B / Kadp + Q / Katp)));
}

protected double function_8(double A, double P, double Vmax, double Kp2g, double Keq, double Kpep) {
return (Vmax * (A / Kp2g - P / (Kp2g * Keq)) / (1 + A / Kp2g + P / Kpep));
}

protected double function_7(double A, double P, double Vmax, double Kp3g, double Keq, double Kp2g) {
return (Vmax * (A / Kp3g - P / (Kp3g * Keq)) / (1 + A / Kp3g + P / Kp2g));
}

protected double function_6(double A, double B, double P, double Q, double Vmax, double Keq, double Kp3g, double Katp, double Kbpg, double Kadp) {
return (Vmax * ((Keq * A * B - P * Q) / (Kp3g * Katp)) / ((1 + A / Kbpg + P / Kp3g) * (1 + B / Kadp + Q / Katp)));
}

protected double function_5(double A, double B, double P, double Q, double C, double Vmaxf, double Kgap, double Knad, double Vmaxr, double Kbpg, double Knadh) {
return (C * (Vmaxf * A * B / (Kgap * Knad) - Vmaxr * P * Q / (Kbpg * Knadh)) / ((1 + A / Kgap + P / Kbpg) * (1 + B / Knad + Q / Knadh)));
}

protected double function_4(double A, double P, double Q, double Vmax, double Kf16bp, double Keq, double Kdhap, double Kgap, double Kigap) {
return (Vmax * (A / Kf16bp - P * Q / (Kf16bp * Keq)) / (1 + A / Kf16bp + P / Kdhap + Q / Kgap + A * Q / (Kf16bp * Kigap) + P * Q / (Kdhap * Kgap)));
}

protected double function_3(double F6P, double ATP, double F16P, double AMP, double F26BP, double Vmax, double gR, double Kf6p, double Katp, double L0, double Ciatp, double Kiatp, double Camp, double Kamp, double Cf26, double Kf26, double Cf16, double Kf16, double Catp) {
return (Vmax * (gR * (F6P / Kf6p) * (ATP / Katp) * (1 + F6P / Kf6p + ATP / Katp + gR * F6P / Kf6p * ATP / Katp) / (Math.pow(1 + F6P / Kf6p + ATP / Katp + gR * F6P / Kf6p * ATP / Katp, 2) + L0 * Math.pow((1 + Ciatp * ATP / Kiatp) / (1 + ATP / Kiatp), 2) * Math.pow((1 + Camp * AMP / Kamp) / (1 + AMP / Kamp), 2) * Math.pow((1 + Cf26 * F26BP / Kf26 + Cf16 * F16P / Kf16) / (1 + F26BP / Kf26 + F16P / Kf16), 2) * Math.pow(1 + Catp * ATP / Katp, 2))));
}

protected double function_2(double A, double P, double Vmax, double Kg6p, double Keq, double Kf6p) {
return (Vmax * (A / Kg6p - P / (Kg6p * Keq)) / (1 + A / Kg6p + P / Kf6p));
}

protected double function_1(double A, double B, double P, double Q, double Vmax, double Kglc, double Katp, double Keq, double Kg6p, double Kadp) {
return (Vmax * (A * B / (Kglc * Katp) - P * Q / (Kglc * Katp * Keq)) / ((1 + A / Kglc + P / Kg6p) * (1 + B / Katp + Q / Kadp)));
}

protected double function_0(double A, double B, double Vmax, double Kglc, double Ki) {
return (Vmax * (A - B) / Kglc / (1 + (A + B) / Kglc + Ki * A * B / Math.pow(Kglc, 2)));
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
case CeYeastGlyPritchardKel.HXTCE:
	amtGLCo = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtGLCi = ((XhYeastGlyPritchardKel)port[P_SM_PRD1]).getPheneVal();
	nTimes = (amtcompartment * function_0(amtGLCo, amtGLCi, 97.24, 1.1918, 0.91)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.HKCE:
	amtGLCi = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtATP = ((XhYeastGlyPritchardKel)port[P_SM_SUB2]).getPheneVal();
	amtG6P = ((XhYeastGlyPritchardKel)port[P_SM_PRD1]).getPheneVal();
	amtADP = ((XhYeastGlyPritchardKel)port[P_SM_PRD2]).getPheneVal();
	nTimes = (amtcompartment * function_1(amtGLCi, amtATP, amtG6P, amtADP, 236.7, 0.08, 0.15, 2000.0, 30.0, 0.23)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.PGICE:
	amtG6P = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtF6P = ((XhYeastGlyPritchardKel)port[P_SM_PRD1]).getPheneVal();
	nTimes = (amtcompartment * function_2(amtG6P, amtF6P, 1056.0, 1.4, 0.29, 0.3)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.PFKCE:
	amtF6P = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtATP = ((XhYeastGlyPritchardKel)port[P_SM_SUB2]).getPheneVal();
	amtAMP = ((XhYeastGlyPritchardKel)port[P_SM_MOD1]).getPheneVal();
	amtF26bP = ((XhYeastGlyPritchardKel)port[P_SM_MOD2]).getPheneVal();
	nTimes = (amtcompartment * function_3(amtF6P, amtATP, amtF16bP, amtAMP, amtF26bP, 110.0, 5.12, 0.1, 0.71, 0.66, 100.0, 0.65, 0.0845, 0.0995, 0.0174, 6.82E-4, 0.397, 0.111, 3.0)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.ALDCE:
	amtF16bP = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtDHAP = ((XhYeastGlyPritchardKel)port[P_SM_PRD1]).getPheneVal();
	amtGAP = ((XhYeastGlyPritchardKel)port[P_SM_PRD2]).getPheneVal();
	nTimes = (amtcompartment * function_4(amtF16bP, amtDHAP, amtGAP, 94.69, 0.3, 0.069, 2.0, 2.4, 10.0)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.TPICE:
	amtDHAP = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtGAP = ((XhYeastGlyPritchardKel)port[P_SM_PRD1]).getPheneVal();
	nTimes = (amtcompartment * ((450000.0 * amtDHAP) - (1.0E7 * amtGAP))) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.GAPDHCE:
	amtGAP = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtNAD = ((XhYeastGlyPritchardKel)port[P_SM_SUB2]).getPheneVal();
	amtBPG = ((XhYeastGlyPritchardKel)port[P_SM_PRD1]).getPheneVal();
	amtNADH = ((XhYeastGlyPritchardKel)port[P_SM_PRD2]).getPheneVal();
	nTimes = (amtcompartment * function_5(amtGAP, amtNAD, amtBPG, amtNADH, 1.0, 1152.0, 0.21, 0.09, 6719.0, 0.0098, 0.06)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.PGKCE:
	amtBPG = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtADP = ((XhYeastGlyPritchardKel)port[P_SM_SUB2]).getPheneVal();
	amtP3G = ((XhYeastGlyPritchardKel)port[P_SM_PRD1]).getPheneVal();
	amtATP = ((XhYeastGlyPritchardKel)port[P_SM_PRD2]).getPheneVal();
	nTimes = (amtcompartment * function_6(amtBPG, amtADP, amtP3G, amtATP, 1288.0, 3200.0, 0.53, 0.3, 0.0030, 0.2)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.PGMCE:
	amtP3G = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtP2G = ((XhYeastGlyPritchardKel)port[P_SM_PRD1]).getPheneVal();
	nTimes = (amtcompartment * function_7(amtP3G, amtP2G, 2585.0, 1.2, 0.19, 0.08)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.ENOCE:
	amtP2G = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtPEP = ((XhYeastGlyPritchardKel)port[P_SM_PRD1]).getPheneVal();
	nTimes = (amtcompartment * function_8(amtP2G, amtPEP, 201.6, 0.04, 6.7, 0.5)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.PYKCE:
	amtPEP = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtADP = ((XhYeastGlyPritchardKel)port[P_SM_SUB2]).getPheneVal();
	amtPYR = ((XhYeastGlyPritchardKel)port[P_SM_PRD1]).getPheneVal();
	amtATP = ((XhYeastGlyPritchardKel)port[P_SM_PRD2]).getPheneVal();
	nTimes = (amtcompartment * function_9(amtPEP, amtADP, amtPYR, amtATP, 1000.0, 0.14, 0.53, 6500.0, 21.0, 1.5)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.PDCCE:
	amtPYR = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcompartment * function_10(amtPYR, 857.8, 4.33, 1.9)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.ADHCE:
	amtEtOH = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtNAD = ((XhYeastGlyPritchardKel)port[P_SM_SUB2]).getPheneVal();
	amtAcAld = ((XhYeastGlyPritchardKel)port[P_SM_PRD1]).getPheneVal();
	amtNADH = ((XhYeastGlyPritchardKel)port[P_SM_PRD2]).getPheneVal();
	nTimes = (amtcompartment * function_11(amtEtOH, amtNAD, amtAcAld, amtNADH, 209.5, 17.0, 0.92, 6.9E-5, 0.17, 0.11, 0.031, 1.11, 1.1, 90.0)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.ATPaseCE:
	amtATP = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	nTimes = (amtcompartment * ATPase(amtATP, 39.5)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.AKCE:
	amtADP = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtATP = ((XhYeastGlyPritchardKel)port[P_SM_PRD1]).getPheneVal();
	amtAMP = ((XhYeastGlyPritchardKel)port[P_SM_PRD2]).getPheneVal();
	nTimes = (amtcompartment * ((45.0 * Math.pow(amtADP,2.0)) - ((100.0 * amtATP) * amtAMP))) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes * 2.0);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.G3PDHCE:
	amtDHAP = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtNADH = ((XhYeastGlyPritchardKel)port[P_SM_SUB2]).getPheneVal();
	nTimes = (amtcompartment * function_12(amtDHAP, amtNADH, amtGlycerol, amtNAD, 47.11, 0.4, 0.023, 4300.0, 1.0, 0.93)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD2]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.Glycogen_BranchCE:
	amtG6P = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtATP = ((XhYeastGlyPritchardKel)port[P_SM_SUB2]).getPheneVal();
	nTimes = (amtcompartment * function_13(6)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.Trehalose_BranchCE:
	amtG6P = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtATP = ((XhYeastGlyPritchardKel)port[P_SM_SUB2]).getPheneVal();
	nTimes = (amtcompartment * function_14(2.4)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes * 2.0);
	((XhYeastGlyPritchardKel)port[P_SM_SUB2]).decPheneVal(nTimes);
	((XhYeastGlyPritchardKel)port[P_SM_PRD1]).incPheneVal(nTimes);
	break;

case CeYeastGlyPritchardKel.Succinate_BranchCE:
	amtAcAld = ((XhYeastGlyPritchardKel)port[P_SM_SUB1]).getPheneVal();
	amtNAD = ((XhYeastGlyPritchardKel)port[P_SM_SUB2]).getPheneVal();
	nTimes = (amtcompartment * function_15(amtAcAld, 21.4)) / timeStepMultiplier;
	((XhYeastGlyPritchardKel)port[P_SM_SUB1]).decPheneVal(nTimes * 2.0);
	((XhYeastGlyPritchardKel)port[P_SM_SUB2]).decPheneVal(nTimes * 3.0);
	((XhYeastGlyPritchardKel)port[P_SM_PRD2]).incPheneVal(nTimes * 3.0);
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
//	System.err.println("XhYeastGlyPritchardKel act() stack overflow: " + this + " ");
//	return;
//}
} // end method
} // end class
