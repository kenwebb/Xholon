package org.primordion.user.xmiapps.beard41;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IIntegration;

/**
	Beard2005_UML_Xholon_Step4_v1 application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   22/02/2007</p>
	<p>File:   Beard2005_UML_Xholon_Step4_v1.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Thu Feb 22 12:38:46 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhBeard2005_UML_Xholon_Step4_v1 extends XholonWithPorts implements CeBeard2005_UML_Xholon_Step4_v1 {

// references to other Xholon instances; indices into the port array
public static final int P = 0;
public static final int P0 = 0;
public static final int P1 = 1;
public static final int P2 = 2;
public static final int P3 = 3;
public static final int P4 = 4;
public static final int P5 = 5;
public static final int P6 = 6;
public static final int P7 = 7;
public static final int P8 = 8;
public static final int P9 = 9;
public static final int P10 = 10;
public static final int P11 = 11;
public static final int P12 = 12;
public static final int P13 = 13;

// Signals, Events

// Variables
public String roleName = null;
public double val = 0.0; // XholonClass
// Mitochondrion size in liters. Based on my estimate of size as 0.000001 * 0.000001 * 0.000002 cubic meters. A liter is 0.001 cubic meters.
private static double sizeMito = 2e-15; // XholonClass
// time step multiplier; M_8
public static final int timeStepMultiplier = IIntegration.M_128; // XholonClass
// r, Dehydrogenase model parameter, Unitless/dimensionless
protected static double r = 4.5807; // MMParams_Simulink
// k Pi1, Dehydrogenase model parameter, molar
protected static double k_Pi1 = 1.3413E-4; // MMParams_Simulink
// k Pi2, Dehydrogenase model parameter, molar
protected static double k_Pi2 = 6.7668E-4; // MMParams_Simulink
// X DH, Dehydrogenase activity, mol s -1 M -1
protected static double X_DH = 0.09183; // MMParams_Simulink
// X C1, Complex I activity, mol s -1 M -1 (1 mito volume) -1
protected static double X_C1 = 0.36923; // MMParams_Simulink
// X C3, Complex III activity, mol s -1 M -1 (1 mito volume) -1
protected static double X_C3 = 0.091737; // MMParams_Simulink
// X C4, Complex IV activity, mol s -1 M -1 (1 mito volume) -1
protected static double X_C4 = 3.2562E-5; // MMParams_Simulink
// X F1, F1Fo ATPase activity, mol s -1 M -2 (1 mito volume) -1
protected static double X_F1 = 150.93; // MMParams_Simulink
// X ANT, ANT activity, mol s -1 (1 mito volume) -1
protected static double X_ANT = 0.0079204; // MMParams_Simulink
// X PiHt, H+/Pi- co-transport activity, mol s -1 M -1 (1 mito volume) -1
protected static double X_PiHt = 339430.0; // MMParams_Simulink
// k PiHt, H+/Pi- co-transport parameter, mM
protected static double k_PiHt = 4.5082E-4; // MMParams_Simulink
// X KH, K+/H+ antiporter activity, mol s -1 M -2 (1 mito volume)
protected static double X_KH = 2.9802E7; // MMParams_Simulink
// X K, Passive potassium transport activity, mol s -1 mV -1 M -1 (1 mito volume) -1
protected static double X_K = 0.0; // MMParams_Simulink
// X Hle, Proton leak activity, mol s -1 mV -1 M -1 (1 mito volume) -1
protected static double X_Hle = 250.0; // MMParams_Simulink
// k Pi3, Complex III/Pi parameter, mM
protected static double k_Pi3 = 1.9172E-4; // MMParams_Simulink
// k Pi4, Complex III/Pi parameter, mM
protected static double k_Pi4 = 0.02531; // MMParams_Simulink
// n A, H+ stoichiometric coefficient for F1Fo ATPase, Unitless
protected static double n_A = 3.0; // MMParams_Simulink
// K Mg-ATP, Mg-ATP binding constant, M, not in CellML?
protected static double K_MgATP = 1.0E-6; // MMParams_Simulink
// K Mg-ADP, Mg-ADP binding constant, M, not in CellML?
protected static double K_MgADP = 1.0E-6; // MMParams_Simulink
// K dH, H2PO4-proton dissociative constant, M, not in CellML?
protected static double k_dH = 1.0E-6; // MMParams_Simulink
// P Pi, Mitochondrial membrane permeability to inorganic phosphate, micron per second
protected static double Phi_Pi = 327.0; // MMParams_Simulink
// P A, Mitochondrial outer membrane permeability to nucleotides, micron per second
protected static double Phi_A = 85.0; // MMParams_Simulink
// K AK, AK equilibrium constant, Unitless/dimensionless
protected static double K_AK = 0.4331; // MMParams_Simulink
// k mADP, ANT Michaelis-Menten constant, M/molar, has a different value in paper and in CellML
protected static double k_mADP = 3.5E-6; // MMParams_Simulink
// k02, Saturation constant for oxygen consumption, M/molar, has a different value in paper and in CellML
protected static double k_O2 = 1.2E-4; // MMParams_Simulink
// NAD tot, Total matrix NAD(H) concentration, mM/molar
protected static double NADtot = 0.00297; // MMParams_Simulink
// Q tot, Total matrix ubiquinol concentration, mM/molar
protected static double Qtot = 0.00135; // MMParams_Simulink
// cytC tot, Total IM cytochrome c concentration, mM/molar
protected static double cytCtot = 0.0027; // MMParams_Simulink
// A tot, Total matrix ATP + ADP concentration, mM, not in CellML ?
protected static double Atot = 10.0; // MMParams_Simulink
// V x, Matrix water volume per total mito volume, Unitless, not in CellML?
protected static double Vx = 0.6435; // MMParams_Simulink
// V i, IM water fraction per total cell volume, Unitless, not in CellML ?
protected static double Vi = 0.0715; // MMParams_Simulink
// gamma symbol, Outer membrane area per mito volume, per micron
protected static double gamma = 5.99; // MMParams_Simulink
// C IM, Capacitance of inner membrane, mol (1 mito volume) per mV per, not in CellML ?
protected static double C_IM = 1.0E-6; // MMParams_Simulink
// X AK, AK activity, mol s -1 M -2 (1 mito volume) -1, not in CellML ?
protected static double X_AK = 1.0E-6; // MMParams_Simulink
// X MgA, Mg2+ binding activity, mole per second per litre mito, different value in paper from CellML ?
protected static double X_MgA = 1.0E-6; // MMParams_Simulink
protected static double Delta_Psi = 161.3; // MMParams_Simulink
// Temperature = 37 Celcius + 273.15 Kelvin
protected static double T = 37 + 273.15; // MMParams_Simulink
// Faraday Constant
protected static double F = 0.096500; // MMParams_Simulink
// R
protected static double R = 0.008314; // MMParams_Simulink
protected static double RT = R * T; // MMParams_Simulink
// Concentration molaire H2O (mol/L)
protected static double Cw = 55.5; // MMParams_Simulink
protected static double Vw = 18.02e-3; // MMParams_Simulink
public static double jAtp; // Mitochondrion
public static double jAdp; // Mitochondrion
public static double jAmp; // Mitochondrion
public static double jPi; // Mitochondrion
public static double jHle; // Mitochondrion
public static double jK; // Mitochondrion
public static double jC1; // Mitochondrion
public static double jC4; // Mitochondrion
public static double jC3; // Mitochondrion
public static double jF1f0; // Mitochondrion
public static double jAnt; // Mitochondrion
public static double jPiht; // Mitochondrion
public static double jKh; // Mitochondrion
public static double jAKi; // Mitochondrion
public static double jMgAtpI; // Mitochondrion
public static double jMgAtpX; // Mitochondrion
public static double jMgAdpI; // Mitochondrion
public static double jMgAdpX; // Mitochondrion
public static double jDh; // Mitochondrion

// Constructor
public XhBeard2005_UML_Xholon_Step4_v1() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void initialize()
{
	super.initialize();
	val = 0.0;
}

// XholonClass
public double getVal()
{
//if (Double.isNaN(val)) {
//	System.out.println("NaN get: " + getName());
//}
return val;
}

// XholonClass
public void setVal(double amount)
{
if (Double.isNaN(amount)) {
	//System.out.println("NaN set: " + getName());
	amount = 0.0001234;
}
//System.out.println(amount + " " + getName());
val = amount;
}

// XholonClass
public void incVal(double incAmount)
{
val += incAmount;
}

// XholonClass
public void decVal(double decAmount)
{
val -= decAmount;
}

// XholonClass
public void configure()
{
	switch(this.getXhcId()) {
	case CardiacCellModelCE:
		//Phi_A = Phi_A * sizeMito;
		//Phi_Pi = Phi_Pi * sizeMito;
		break;
	case PHCE:
		// pH should not be adjusted
		break;
	default:
		//setVal(getVal() * sizeMito);
		break;
	}
	super.configure();

}

// Mitochondrion
protected double funcA_J_DH(double X_DH, double r, double NADx, double NADHx, double Pix, double k_Pi1, double k_Pi2)
{
// Functions from EquationsBeard.mdl Simulink model.
// These mathematical functions are created using a combination of:
//   1. The Beard CellML file transformed to SBML transformed to Xholon.
//   2. The Simulink model.
// When there is a conflict between the two, the Simulink model is taken as definitive.

	// A. Dehydrogenase_Flux x
	// @return
	double J_DH = (X_DH * ((r * NADx) - NADHx)) * ((1.0 + (Pix / k_Pi1)) / (1.0 + (Pix / k_Pi2)));
	return J_DH;
}

// Mitochondrion
protected double funcB_J_c1_(double X_c1, double NADHx, double NADx, double RT, double Q, double QH2, double Hx, double dG_c1_0, double dG_H)
{
	// B. Flux d'électrons à travers le complexe I
	// @return
	double J_c1 = X_c1 * ((Math.exp(-((func_dG_c1_op(RT, Q, QH2, Hx, dG_c1_0)
			+ (4.0 * dG_H)) / RT)) * NADHx) - NADx);
	return J_c1;
}

// Mitochondrion
protected double funcC_dG_H(double F, double dPsi, double RT, double Hx, double Hi)
{
	// C. Energie motrice des protons
	// @return
	double dG_H = (F * dPsi) + (RT * Math.log((Hi / Hx)));
	return dG_H;
}

// Mitochondrion
protected double funcD_J_c3(double X_c3, double dG_H, double RT, double dG_c3_0, double QH2, double Q, double F, double dPsi, double cytC_ox, double cytC_red, double Pix, double k_Pi3, double k_Pi4, double Hx)
{
	// D. Flux d'électrons à travers le complexe III
	// @return
	double J_c3 = (X_c3 * ((1.0 + (Pix / k_Pi3)) / (1.0 + (Pix / k_Pi4))))
			* ((Math.exp(-(((func_dG_c3_op(RT, dG_c3_0, QH2, Q, Hx)
					+ (4.0 * dG_H)) - ((2.0 * F) * dPsi)) / (2.0 * RT))) * cytC_ox) - cytC_red);
	return J_c3;
}

// Mitochondrion
protected double funcE_J_c4(double X_c4, double dG_c4_0, double RT, double Hx, double O2, double k_O2, double cytC_red, double cytC_tot, double dG_H, double cytC_ox, double F, double dPsi)
{
	// E. Flux d'électrons à travers le complexe IV
	// @return
	double J_c4 = ((X_c4 * (1.0 / (1.0 + (k_O2 / O2)))) * (cytC_red / cytC_tot))
			* ((Math.exp(-((func_dG_c4_op(RT, Hx, dG_c4_0, O2)
					+ (2.0 * dG_H)) / (2.0 * RT)))
					* cytC_red) - (cytC_ox * Math.exp((F * (dPsi / RT)))));
	return J_c4;
}

// Mitochondrion
protected double funcF_J_F1(double X_F1, double dG_F1_0, double RT, double n_A, double mADP_x, double Pix, double mATP_x, double dG_H, double K_Mg_ATP, double K_Mg_ADP)
{
	// F. Flux de synthèse d'ATP ix
	// @return
	double J_F1 = X_F1 * ((((Math.exp(-((dG_F1_0 - (n_A * dG_H)) / RT))
			* (K_Mg_ADP / K_Mg_ATP)) * mADP_x) * Pix) - mATP_x);
	return J_F1;
}

// Mitochondrion
protected double funcG_J_ANT(double X_ANT, double fADP_i, double fATP_i, double fATP_x, double F, double dPsi, double RT, double Km_ADP, double fADP_x)
{
	/*
	G. Flux de l'antiport ATP/ADP ix
	@return
	
	source: Beard, dCdT.m
	Psi_x = -0.65*dPsi;
	Psi_i = +0.35*dPsi;
	if (ADP_fi > 1e-12) || (ATP_fi > 1e-12)
	J_ANT  = x_ANT*( ADP_fi/(ADP_fi+ATP_fi*exp(-F*Psi_i/RT)) - ADP_fx/(ADP_fx+ATP_fx*exp(-F*Psi_x/RT)) )*(ADP_fi/(ADP_fi+k_mADP));
	else
	J_ANT  = 0;
	end
	*/
	double J_ANT = 0.0;
	if (fADP_i > 1e-12 || fATP_i > 1e-12) {
		J_ANT = func_J_ANT(X_ANT, fADP_i, fATP_i, fATP_x, F, dPsi, RT, Km_ADP, fADP_x);
	}
	return J_ANT;
}

// Mitochondrion
protected double funcH_J_PiHt(double X_PiHt, double k_PiHt, double H2PO4_i, double H2PO4_x, double Hi, double Hx)
{
	/*
	H. Flux du cotransporteur Pi/H ix
	@return
	TODO Beard CellML/SBML/Xholon has:
	amtJ_Pi1 = (x_Pi1 * (((amtH_x * amtH2PIi) - (amtH_i * amtH2PIx)) / (amtH2PIi + k_PiH)));
	while Simulink model has:
	H2PO4_i * Hi  and  H2PO4_x * Hx
	I'm using the Simulink way here
	*/
	double J_PiHt = X_PiHt * (((Hi * H2PO4_i) - (Hx * H2PO4_x)) / (H2PO4_i + k_PiHt));
	return J_PiHt;
}

// Mitochondrion
protected double funcI_H2PO4(double Pi, double H, double k_dH)
{
	// I. H2PO4
	// TODO Beard CellML/SBML/Xholon has:
	// amtH2PIi = (amtPi_i * (amtH_i / (amtH_i + k_dHPi)));
	// @return
	double H2PO4 = (Pi * H) / (H + k_dH);
	return H2PO4;
}

// Mitochondrion
protected double funcJ_J_AK_i(double X_AK, double K_AK, double ADP_i, double AMP_i, double ATP_i)
{
	// J. Flux de l'adénylate kinase i
	// @return
	double J_AK_i = X_AK * ((K_AK * ADP_i * ADP_i) - (AMP_i * ATP_i));
	return J_AK_i;
}

// Mitochondrion
protected double funcK_J_Hle(double X_Hle, double Hi, double dPsi, double F, double RT, double Hx)
{
	// K. Flux de H+ à travers les pores de la mb interne ix
	// @return
	double J_Hle = X_Hle * dPsi
		* (((Hi * Math.exp((F * (dPsi / RT)))) - Hx) / (Math.exp((F * (dPsi / RT))) - 1.0));
	return J_Hle;
}

// Mitochondrion
protected double funcL_J_K(double X_K, double Ki, double dPsi, double F, double RT, double Kx)
{
	// L. Flux de K+ à travers les pores de la mb interne ix
	// @return
	double J_K = X_K * dPsi * (((Ki * Math.exp((F * (dPsi / RT)))) - Kx) / (Math.exp((F * (dPsi / RT))) - 1.0));
	return J_K;
}

// Mitochondrion
protected double funcM_J_KH(double X_KH, double Ki, double Hx, double Kx, double Hi)
{
	// M. Flux à travers l'antiport H/K ix
	// @return
	double J_KH = X_KH * ((Ki * Hx) - (Kx * Hi));
	return J_KH;
}

// Mitochondrion
protected double funcN_mADP(double K_MgADP, double ADP_e, double Mg_tot)
{
	// N. Liaison de l'ADP au Mg ds le cytosol
	// @return mADP

	// mADPtest	0.0018508512230862861	
	// mADP	    0.001299648776913714
	// Matlab function with same inputs 0.00129964877691371
	// Matlab computes same result as Xholon if I break the function into pieces 0.00185085122308629
	// therefore, I should use the single long computation
	//double sum1 = K_MgADP + ADP_e + Mg_tot;
	//double pow1 = Math.pow(sum1,2.0);
	//double prod1 = 4.0 * (Mg_tot * ADP_e);
	//double sum2 = pow1 - prod1;
	//double pow2 = Math.pow(sum2, 0.5);
	//double mADPtest = (sum1 - pow2) / 2.0;
	
	double mADP = (((K_MgADP + ADP_e + Mg_tot) - Math.pow((Math.pow((K_MgADP + ADP_e + Mg_tot),2.0) - (4.0 * (Mg_tot * ADP_e))),0.5)) / 2.0);
	return mADP;
}

// Mitochondrion
protected double funcO_J_MgA(double X_MgA, double fA, double mA, double Mg, double K_MgA)
{
	// O. Flux de liaison d'un nucléotide au Mg
	// @return
	double J_MgA = X_MgA * ((fA * Mg) - (mA * K_MgA));
	return J_MgA;
}

// Mitochondrion
protected double funcP_Jt(double gamma, double S_ext, double Phi, double S_int)
{
	// P. Flux à travers un pore
	// @return
	double Jt = gamma * (S_ext - S_int) * Phi;
	return Jt;
}

// Mitochondrion
protected double func_dG_c1_op(double RT, double Q, double QH2, double Hx, double dG_c1_0)
{
	// dG_c1_op
	// @return
	// TODO why (Hx / 10.0) in CellML Beard file; Simulink has (Hx / 1e-7) ?
	double dG_c1_op = (dG_c1_0 - ((1.0 * RT) * Math.log((Hx / 1e-7)))) - (RT * Math.log((Q / QH2)));
	return dG_c1_op;
}

// Mitochondrion
protected double func_dG_c3_op(double RT, double dG_c3_0, double QH2, double Q, double Hx)
{
	// dG_c3_op
	// @return
	// TODO why (Hx / 10.0) in CellML Beard file; Simulink has (Hx / 1e-7) ?
	double dG_c3_op = (dG_c3_0 + ((2.0 * RT) * Math.log((Hx / 1e-7)))) - (RT * Math.log((QH2 / Q)));
	return dG_c3_op;
}

// Mitochondrion
protected double func_dG_c4_op(double RT, double Hx, double dG_c4_0, double O2)
{
	// dG_c4_op
	// @return
	// TODO why (Hx / 10.0) in CellML Beard file; Simulink has (Hx / 1e-7) ?
	double dG_c4_op = (dG_c4_0 - ((2.0 * RT) * Math.log((Hx / 1e-7)))) - ((RT / 2.0) * Math.log(O2));
	return dG_c4_op;
}

// Mitochondrion
protected double func_J_ANT(double X_ANT, double fADP_i, double fATP_i, double fATP_x, double F, double dPsi, double RT, double Km_ADP, double fADP_x)
{
	// G. Subsystem
	// @return
	double dPsi_F_RT = F * dPsi * RT;
	double dPsi_F_RT_i = fATP_i * Math.exp(-(0.35 * dPsi_F_RT));
	double dPsi_F_RT_x = fATP_x * Math.exp(-(-0.65 * dPsi_F_RT));
	double J_ANT = X_ANT
		* ((fADP_i / (fADP_i + dPsi_F_RT_i)) - (fADP_x / (fADP_x + dPsi_F_RT_x)))
		* (fADP_i / (fADP_i + Km_ADP));
	return J_ANT;

}

public void postConfigure()
{
	switch(xhc.getId()) {
	case XholonClassCE:
	{
	switch(this.getXhcId()) {
	case CardiacCellModelCE:
		//Phi_A = Phi_A * sizeMito;
		//Phi_Pi = Phi_Pi * sizeMito;
		break;
	case PHCE:
		// pH should not be adjusted
		break;
	default:
		//setVal(getVal() * sizeMito);
		break;
	}
	super.postConfigure();

	}
		break;
	default:
		break;
	}
	super.postConfigure();
}

public void preAct()
{
	switch(xhc.getId()) {
	case ExternalSpaceCE:
	{
		double adp = port[P0].getVal();
		double mgtot = port[P3].getVal();
		double pH = port[P6].getVal();
		double adpm = funcN_mADP(K_MgADP, adp, mgtot);
		double adpf = adp - adpm;
		double mg = mgtot - adpm;
		double h = Math.pow(10.0, -pH);
		port[P1].setVal(adpm);
		port[P2].setVal(adpf);
		port[P4].setVal(mg);
		port[P5].setVal(h);
	}
		break;
	case OuterMembraneCE:
	{
		// Note: Matlab/Simulink directly asign value in I from the current value in E.
		//       In Xholon, the OuterMembrane has to do this.
		// H, Mg, K
		double hE = port[P8].getVal();
		double mgE = port[P10].getVal();
		double kE = port[P12].getVal();
		port[P9].setVal(hE);   // hI
		port[P11].setVal(mgE); // mgI
		port[P13].setVal(kE);  // kI
	}
		break;
	case IntermembraneSpaceCE:
	{
		double cytcred = port[P0].getVal();
		double adp = port[P2].getVal();
		double adpm = port[P3].getVal();
		double atp = port[P5].getVal();
		double atpm = port[P6].getVal();
		//
		double cytcox = cytCtot - cytcred;
		double adpf = adp - adpm;
		double atpf = atp - atpm;
		//
		port[P1].setVal(cytcox);
		port[P4].setVal(adpf);
		port[P7].setVal(atpf);
	}
		break;
	case MitochondrialMatrixCE:
	{
		double nadh = port[P0].getVal();
		double qh = port[P2].getVal();
		double adp = port[P4].getVal();
		double adpm = port[P5].getVal();
		double atp = port[P7].getVal();
		double atpm = port[P8].getVal();
		double pH = port[P10].getVal();
		//
		double nad = NADtot - nadh;
		double q = Qtot - qh;
		double adpf = adp - adpm;
		double atpf = atp - atpm;
		double h = Math.pow(10.0, -pH);
		//
		port[P1].setVal(nad);
		port[P3].setVal(q);
		port[P6].setVal(adpf);
		port[P9].setVal(atpf);
		port[P11].setVal(h);
	}
		break;
	default:
		break;
	}
	super.preAct();
}

public void act()
{
	switch(xhc.getId()) {
	case CardiacCellModelCE:
		processMessageQ();
		break;
	case OuterMembraneCE:
	{
		// ATP
		double atpE = port[P0].getVal();
		double atpI = port[P1].getVal();
		jAtp = funcP_Jt(gamma, atpE, Phi_A, atpI);
		// ADP
		double adpE = port[P2].getVal();
		double adpI = port[P3].getVal();
		jAdp = funcP_Jt(gamma, adpE, Phi_A, adpI);
		// AMP
		double ampE = port[P4].getVal();
		double ampI = port[P5].getVal();
		jAmp = funcP_Jt(gamma, ampE, Phi_A, ampI);
		// Pi
		double piE = port[P6].getVal();
		double piI = port[P7].getVal();
		jPi = funcP_Jt(gamma, piE, Phi_A, piI);
	}
		break;
	case InnerMembraneCE:
	{
		// should transport H and K
		// H
		double hI = port[P0].getVal();
		double hX = port[P1].getVal();
		jHle = funcK_J_Hle(X_Hle, hI, Delta_Psi, F, RT, hX);
		// K
		double kI = port[P2].getVal();
		double kX = port[P3].getVal();
		//double xk = X_K; // alternatively, has value of 1e-6
		double xk = 1e-6;
		jK = funcL_J_K(xk, kI, Delta_Psi, F, RT, kX);
	}
		break;
	case ComplexICE:
	{
		double hI = port[P0].getVal();
		double hX = port[P1].getVal();
		double q = port[P2].getVal();
		double qh = port[P3].getVal();
		double nadh = port[P5].getVal();
		double nad = port[P4].getVal();
		double dg_h = funcC_dG_H(F, Delta_Psi, RT, hX, hI);
		double dG_c1_0 = -69.370; // comes from [Delta_G0_c1]; TODO make this a MMParam
		jC1 = funcB_J_c1_(X_C1, nadh, nad, RT, q, qh, hX, dG_c1_0, dg_h);

	}
		break;
	case ComplexIVCE:
	{
		double hI = port[P0].getVal();
		double hX = port[P1].getVal();
		double cytcox = port[P2].getVal();
		double cytcred = port[P3].getVal();
		double o2 = port[P4].getVal();
		double dg_h = funcC_dG_H(F, Delta_Psi, RT, hX, hI);
		double dG_c4_0 = -122.940; // comes from [Delta_G0_c4]; TODO make this a MMParam
		jC4 = funcE_J_c4(X_C4, dG_c4_0, RT, hX, o2, k_O2,
				cytcred, cytCtot, dg_h, cytcox, F, Delta_Psi);

	}
		break;
	case ComplexIIICE:
	{
		double hI = port[P0].getVal();
		double hX = port[P1].getVal();
		double q = port[P4].getVal();
		double qh = port[P5].getVal();
		double cytcox = port[P2].getVal();
		double cytcred = port[P3].getVal();
		double pix = port[P6].getVal();
		double dg_h = funcC_dG_H(F, Delta_Psi, RT, hX, hI);
		double dG_c3_0 = -32.530; // comes from [Delta_G0_c3]; TODO make this a MMParam
		jC3 = funcD_J_c3(X_C3, dg_h, RT, dG_c3_0, qh,
				q, F, Delta_Psi, cytcox, cytcred,
				pix, k_Pi3, k_Pi4, hX);

	}
		break;
	case ATPsynthaseCE:
	{
		double atpmX = port[P3].getVal();
		double pix = port[P4].getVal();
		double adpmX = port[P2].getVal();
		double hX = port[P1].getVal();
		double hI = port[P0].getVal();
		double dg_h = funcC_dG_H(F, Delta_Psi, RT, hX, hI);
		double dG_f1_0 = 36.030; // comes from [Delta_G0_ATP]; TODO make this a MMParam
		jF1f0 = funcF_J_F1(X_F1, dG_f1_0, RT, n_A, adpmX,
				pix, atpmX, dg_h, K_MgATP, K_MgADP);
	}
		break;
	case ANTCE:
	{
		double adpfI = port[P0].getVal();
		double atpfI = port[P2].getVal();
		double adpfX = port[P1].getVal();
		double atpfX = port[P3].getVal();
		jAnt = funcG_J_ANT(X_ANT, adpfI, atpfI,
			atpfX, F, Delta_Psi, RT, k_mADP, adpfX);
	}
		break;
	case PiHtCE:
	{
		double h2po4I = port[P2].getVal(); // piI
		double hI = port[P0].getVal();
		double h2po4X = port[P3].getVal(); // piX
		double hX = port[P1].getVal();
		jPiht = funcH_J_PiHt(X_PiHt, k_PiHt, h2po4I, h2po4X, hI, hX);
	}
		break;
	case KHAntiporterCE:
	{
		double kI = port[P2].getVal();
		double hX = port[P1].getVal();
		double kX = port[P3].getVal();
		double hI = port[P0].getVal();
		jKh = funcM_J_KH(X_KH, kI, hX, kX, hI);
	}
		break;
	case AKCE:
	{
		double adpI = port[P1].getVal();
		double ampI = port[P2].getVal();
		double atpI = port[P0].getVal();
		jAKi = funcJ_J_AK_i(X_AK, K_AK, adpI, ampI, atpI);
	}
		break;
	case MagnesiumBindingCE:
	{
		double atpf = port[P0].getVal();
		double mg = port[P4].getVal();
		double atpm  = port[P1].getVal();
		double adpf = port[P2].getVal();
		double adpm = port[P3].getVal();
		if (((IXholon)getParentNode()).getXhcId() == IntermembraneSpaceCE) {
			jMgAtpI = funcO_J_MgA(X_MgA, atpf, atpm, mg, K_MgATP); // ATP
			jMgAdpI = funcO_J_MgA(X_MgA, adpf, adpm, mg, K_MgADP); // ADP
		}
		else { // MitochondrialMatrixCE
			jMgAtpX = funcO_J_MgA(X_MgA, atpf, atpm, mg, K_MgATP); // ATP
			jMgAdpX = funcO_J_MgA(X_MgA, adpf, adpm, mg, K_MgADP); // ADP
		}
	}
		break;
	case DehydrogenaseFluxCE:
	{
		double nadX = port[P0].getVal();
		double piX = port[P2].getVal();
		double nadhX = port[P1].getVal();
		jDh = funcA_J_DH(X_DH, r, nadX, nadhX, piX, k_Pi1, k_Pi2);
	}
		break;
	default:
		break;
	}
	super.act();
}

public void postAct()
{
	switch(xhc.getId()) {
	case InnerMembraneCE:
	{
		double factor = 1.0; // Matlab version uses a multiplier of 1.48e5; CellML/SBML has 44.69528546194984
		double sumOfFluxes = factor * ((4.0 * jC1) + (2.0 * jC3) + (4.0 * jC4) - (n_A * jF1f0) - jAnt - jHle - jK);
		Delta_Psi = Delta_Psi + ((sumOfFluxes  / C_IM) / timeStepMultiplier);
	}
		break;
	case NADHCE:
	{
		if (((IXholon)getParentNode()).getXhcId() == MitochondrialMatrixCE) {
			double sumOfFluxes = jDh - jC1;
			setVal(((val * Vx) + (sumOfFluxes / timeStepMultiplier)) / Vx);
		}
	}
		break;
	case HCE:
	{
		if (((IXholon)getParentNode()).getXhcId() == MitochondrialMatrixCE) {
			double sumOfFluxes = jDh - 5*jC1 - 2*jC3 - 4*jC4
				+ (n_A-1)*jF1f0 + 2*jPiht + jHle + jKh;
			//val = ((val * Vx) + (sumOfFluxes / timeStepMultiplier)) / Vx;
			setVal(((val / Vx) + (sumOfFluxes*100 / timeStepMultiplier)) * Vx);
		}
	}
		break;
	case QHCE:
	{
		if (((IXholon)getParentNode()).getXhcId() == MitochondrialMatrixCE) {
			double sumOfFluxes = jC1 - jC3;
			setVal(((val * Vx) + (sumOfFluxes / timeStepMultiplier)) / Vx);
		}
	}
		break;
	case ATPCE:
	{
		if (((IXholon)getParentNode()).getXhcId() == MitochondrialMatrixCE) {
			double sumOfFluxes = jF1f0 - jAnt;
			setVal(((val * Vx) + (sumOfFluxes / timeStepMultiplier)) / Vx);
		}
		else if (((IXholon)getParentNode()).getXhcId() ==  IntermembraneSpaceCE) {
			double sumOfFluxes = jAtp + jAnt + jAKi;
			setVal(((val * Vi) + (sumOfFluxes / timeStepMultiplier)) / Vi);
		}
	}
		break;
	case AMPCE:
	{
		if (((IXholon)getParentNode()).getXhcId() == IntermembraneSpaceCE) {
			double sumOfFluxes = jAmp + jAKi;
			setVal(((val * Vi) + (sumOfFluxes / timeStepMultiplier)) / Vi);
		}
	}
		break;
	case ADPCE:
	{
		// TODO Simulink model also has ADPx calculation for Matrix; not in Beard model
		if (((IXholon)getParentNode()).getXhcId() == IntermembraneSpaceCE) {
			double sumOfFluxes = jAdp - jAnt - 2*jAKi;
			setVal(((val * Vi) + (sumOfFluxes / timeStepMultiplier)) / Vi);
		}
	}
		break;
	case ATPmCE:
	{
		if (((IXholon)getParentNode()).getXhcId() == MitochondrialMatrixCE) {
			double sumOfFluxes = jMgAtpX;
			setVal(((val * Vx) + (sumOfFluxes / timeStepMultiplier)) / Vx);
		}
		else if (((IXholon)getParentNode()).getXhcId() ==  IntermembraneSpaceCE) {
			// TODO Simulink version is implemented in a quite different way
			double sumOfFluxes = jMgAtpI;
			setVal(((val * Vi) + (sumOfFluxes / timeStepMultiplier)) / Vi);
		}
	}
		break;
	case ADPmCE:
	{
		if (((IXholon)getParentNode()).getXhcId() == MitochondrialMatrixCE) {
			double sumOfFluxes = jMgAdpX;
			setVal(((val * Vx) + (sumOfFluxes / timeStepMultiplier)) / Vx);
		}
		else if (((IXholon)getParentNode()).getXhcId() ==  IntermembraneSpaceCE) {
			// TODO Simulink version is implemented in a quite different way
			double sumOfFluxes = jMgAdpI;
			setVal(((val * Vi) + (sumOfFluxes / timeStepMultiplier)) / Vi);
		}
	}
		break;
	case PiCE:
	{
		if (((IXholon)getParentNode()).getXhcId() == MitochondrialMatrixCE) {
			double sumOfFluxes = -jF1f0 + jPiht;
			setVal(((val * Vx) + (sumOfFluxes / timeStepMultiplier)) / Vx);
		}
		else if (((IXholon)getParentNode()).getXhcId() ==  IntermembraneSpaceCE) {
			double sumOfFluxes = jPi - jPiht;
			setVal(((val * Vi) + (sumOfFluxes / timeStepMultiplier)) / Vi);
		}
		else {
			// TODO Pi in ExternalSpace
		}
	}
		break;
	case CytCRedCE:
	{
		if (((IXholon)getParentNode()).getXhcId() == MitochondrialMatrixCE) {
			// TODO not implemented in Simulink model?
			double sumOfFluxes = 2*jC3 - 2*jC4;
			setVal(((val * Vx) + (sumOfFluxes / timeStepMultiplier)) / Vx);
		}
	}
		break;
	case Mg2CE:
	{
		if (((IXholon)getParentNode()).getXhcId() == MitochondrialMatrixCE) {
			double sumOfFluxes = -jMgAtpX - jMgAdpX;
			setVal(((val * Vx) + (sumOfFluxes / timeStepMultiplier)) / Vx);
		}
	}
		break;
	case KCE:
	{
		if (((IXholon)getParentNode()).getXhcId() == MitochondrialMatrixCE) {
			double sumOfFluxes = jKh + jK;
			setVal(((val * Vx) + (sumOfFluxes / timeStepMultiplier)) / Vx);
		}
	}
		break;
	default:
		break;
	}
	super.postAct();
}

public void processReceivedMessage(Message msg)
{
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
			System.out.println("XhBeard2005_UML_Xholon_Step4_v1: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		System.out.println("XhBeard2005_UML_Xholon_Step4_v1: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		System.out.println("XhBeard2005_UML_Xholon_Step4_v1: performGuard() unknown Activity " + activityId);
		return false;
	}
}

public String toString() {
	String outStr = getName();
	if ((port != null) && (port.length > 0)) {
		outStr += " [";
		for (int i = 0; i < port.length; i++) {
			if (port[i] != null) {
				outStr += " port:" + port[i].getName();
			}
		}
		outStr += "]";
	}
	if (getVal() != 0.0) {
		outStr += " val:" + getVal();
	}
	return outStr;
}

}
