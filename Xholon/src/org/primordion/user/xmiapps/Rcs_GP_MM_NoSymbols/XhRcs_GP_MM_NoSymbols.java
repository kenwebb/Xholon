package org.primordion.user.xmiapps.Rcs_GP_MM_NoSymbols;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;

/**
	Rcs_GP_MM_NoSymbols application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   Rcs_GP_MM_NoSymbols.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Thu Feb 22 12:59:18 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhRcs_GP_MM_NoSymbols extends XholonWithPorts implements CeRcs_GP_MM_NoSymbols {

// references to other Xholon instances; indices into the port array
public static final int Substrate = 0;
public static final int Product = 1;
public static final int Regulation = 2;
public static final int AxpPort = 3;

// Signals, Events

// Variables
public String roleName = null;
protected static int rInt; // RcsEnzyme
private static final int MAX_SUB = 20; // RcsEnzyme
protected static int numSubstrate; // RcsEnzyme
private static IXholon axp[] = new IXholon[2]; // RcsEnzyme

// Constructor
public XhRcs_GP_MM_NoSymbols() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void initialize()
{
	super.initialize();
}

// GPase
protected void activate(IXholon atp[])
{
if (!(hasChildNodes() && ((IXholon)getFirstChild()).getXhcId() == PGrpCE)) {
  for (int i = 0; i < atp.length; i++) {
    IXholon phosphorylGroup = (IXholon)atp[i].getFirstChild();
    phosphorylGroup.removeChild();
    phosphorylGroup.insertFirstChild(this);
    // set its type from Atp to Adp
    //atp[i].setXhc(adpXholonClass);
  }
  // set this type from GPaseBCE to GPaseACE
  //setXhc(gPaseAXholonClass);
}
}

// GPase
protected void deactivate(IXholon adp[])
{
if (hasChildNodes() && ((IXholon)getFirstChild()).getXhcId() == PGrpCE) {
  for (int i = 0; i < adp.length; i++) {
    IXholon phosphorylGroup = (IXholon)getFirstChild();
    phosphorylGroup.removeChild();
    phosphorylGroup.insertFirstChild(adp[i]);
    // set its type from Adp to Atp
    //adp[i].setXhc(atpXholonClass);
  }
  // set this type from GPaseACE to GPaseBCE
  //setXhc(gPaseBXholonClass);
}
}

// XholonClass
public double getVal()
{
// count number of children and return that
return getNumChildren(false);
}

public void act()
{
	switch(xhc.getId()) {
	case GPaseSystemCE:
		processMessageQ();
		break;
	case GPaseCE:
	{
// GPase is active only if it contains two PGrp
IXholon gPasePGrp = (IXholon)getFirstChild();
if (gPasePGrp != null) {
  gPasePGrp = (IXholon)gPasePGrp.getNextSibling();
}
if (gPasePGrp != null) {
  if (port[Substrate].hasChildNodes()) {
    // Remove a glucose from the end of the glycogen
    IXholon glucose = (IXholon)port[Substrate].getFirstChild();
    if (glucose != null) {
      glucose.removeChild();
      // Remove a PGrp from an atp; atp --> adp
      IXholon atp = (IXholon)port[AxpPort];
      if (atp != null) {
        IXholon pGrp = (IXholon)atp.getFirstChild();
        if (pGrp != null) {
          pGrp.removeChild();
          // Glucose + PhosphorylGroup --> Glucose_1_Phosphate
          pGrp.appendChild(glucose);
          // Put the Glucose_1_Phosphate into solution
          glucose.appendChild(port[Product]);
          // Set port so the catalyst will use the next atp in the next time step
          port[AxpPort] = (IXholon)atp.getNextSibling();
        }
      }
    }
  }
}
	}
		break;
	case PKinaseCE:
	{
numSubstrate = port[Substrate].getNumChildren(false);
rInt = org.primordion.xholon.util.MiscRandom.getRandomInt(0, numSubstrate+1);
if (rInt == 0) {
  axp[0] = port[AxpPort];
  axp[1] = (IXholon)port[AxpPort].getNextSibling();
  ((XhRcs_GP_MM_NoSymbols)port[Regulation]).activate(axp);
}
	}
		break;
	case PPhosphataseCE:
	{
numSubstrate = port[Substrate].getNumChildren(false);
rInt = numSubstrate;
if (rInt < MAX_SUB) {
  rInt = org.primordion.xholon.util.MiscRandom.getRandomInt(rInt, MAX_SUB+1);
}
if (rInt >= MAX_SUB) {
  axp[0] = port[AxpPort];
  axp[1] = (IXholon)port[AxpPort].getNextSibling();
  ((XhRcs_GP_MM_NoSymbols)port[Regulation]).deactivate(axp);
}
	}
		break;
	default:
		break;
	}
	super.act();
}

public void processReceivedMessage(IMessage msg)
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
			System.out.println("XhRcs_GP_MM_NoSymbols: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		System.out.println("XhRcs_GP_MM_NoSymbols: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		System.out.println("XhRcs_GP_MM_NoSymbols: performGuard() unknown Activity " + activityId);
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
