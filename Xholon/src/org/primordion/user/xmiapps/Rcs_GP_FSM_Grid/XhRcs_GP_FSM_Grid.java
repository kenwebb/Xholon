package org.primordion.user.xmiapps.Rcs_GP_FSM_Grid;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.base.AbstractGrid;

/**
	Rcs_GP_FSM_Grid application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   Rcs_GP_FSM_Grid.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed May 23 13:17:28 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhRcs_GP_FSM_Grid extends XholonWithPorts implements CeRcs_GP_FSM_Grid {

// references to other Xholon instances; indices into the port array
public static final int Substrate = 0;
public static final int Product = 1;
public static final int Regulation = 1;
public static final int Aggregate = 0;

// Signals, Events
public static final int S_ACTIVATE = 100;
public static final int S_DEACTIVATE = 200;

// Variables
public String roleName = null;
// number of neighbors of the grid cell that a model entity is contained in
public static int numNeighbors = 0; // GPaseSystem
// whether xholon has had its turn to move this timestep
protected boolean hasMoved = false; // GPaseSystem
protected static int rInt; // RcsEnzyme
protected static final int MAX_SUB = 20; // RcsEnzyme
public double val = 0; // XholonClass

// Constructor
public XhRcs_GP_FSM_Grid() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void initialize()
{
	super.initialize();
	hasMoved = false;
	val = 0;
}

// GPaseSystem
protected void moveAdjacent(int direction)
{
	IXholon newParentNode = getParentNode();
	newParentNode = ((AbstractGrid)newParentNode).port[direction]; // choose a new parent
	if ((newParentNode != null) && (true)) {
		removeChild(); // detach subtree from parent and sibling links
		appendChild(newParentNode); // insert as the last child of the new parent
		hasMoved = true;
	}
}

// XholonClass
public double getVal()
{
return val;
}

// XholonClass
public void incVal(int incAmount)
{
val += incAmount;
}

// XholonClass
public void decVal(int decAmount)
{
val -= decAmount;
}

// XholonClass
public void setVal(int amount)
{
val = amount;
}

public void postConfigure()
{
	switch(xhc.getId()) {
	case G1PCE:
	{
	port = new IXholon[1];
	port[Aggregate] = getXPath().evaluate("ancestor::GPaseSystemWithGrid/Aggregator/Aggregator_G1P", this);
	port[Aggregate].incVal(1);
	}
		break;
	case GPaseSystemWithGridCE:
	{
		// Move all children of GPaseSystem to random positions within the grid.
		IXholon grid = getXPath().evaluate("descendant::Grid", this);
		IXholon xhParent = getXPath().evaluate("descendant::GPaseSystem", this);
		((IGrid)grid).moveXholonsToGrid(xhParent, false);
		// get number of neighbors that each grid cell has
		switch(((IGrid)grid).getNeighType()) {
		case IGrid.NEIGHBORHOOD_VON_NEUMANN:
			numNeighbors = 4;
			break;
		case IGrid.NEIGHBORHOOD_MOORE:
			numNeighbors = 8;
			break;
		case IGrid.NEIGHBORHOOD_HEXAGONAL:
			numNeighbors = 6;
			break;
		default:
			break;
		}
	}
		break;
	case GlcCE:
	{
	port = new IXholon[1];
	port[Aggregate] = getXPath().evaluate("ancestor::GPaseSystemWithGrid/Aggregator/Aggregator_Glc", this);
	port[Aggregate].incVal(1);
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
	case GPaseCE:
	{
		setPortsDynamically();
		hasMoved = false;
	}
		break;
	case PKinaseCE:
	{
		setPortsDynamically();
		hasMoved = false;
	}
		break;
	case PPhosphataseCE:
	{
		setPortsDynamically();
		hasMoved = false;
	}
		break;
	case G1PCE:
	{
		hasMoved = false;
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
	case GPaseSystemWithGridCE:
		processMessageQ();
		break;
	case GPaseCE:
	{
		// Do Activity
		if (this.hasChildNodes()) {
			((StateMachineEntity)getFirstChild()).doStateMachine(new Message(ISignal.SIGNAL_DOACTIVITY,null,null,this));
		}
		else {
			System.out.println("XhGPase: doActivity with no receiver ");
		}
	}
		break;
	case PKinaseCE:
	{
		// Do Activity
		if (this.hasChildNodes()) {
			((StateMachineEntity)getFirstChild()).doStateMachine(new Message(ISignal.SIGNAL_DOACTIVITY,null,null,this));
		}
		else {
			System.out.println("XhPKinase: doActivity with no receiver ");
		}
	}
		break;
	case PPhosphataseCE:
	{
		// Do Activity
		if (this.hasChildNodes()) {
			((StateMachineEntity)getFirstChild()).doStateMachine(new Message(ISignal.SIGNAL_DOACTIVITY,null,null,this));
		}
		else {
			System.out.println("XhPPhosphatase: doActivity with no receiver ");
		}
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
	case GPaseCE:
	{
		if (hasMoved == false) {
			int r = org.primordion.xholon.util.MiscRandom.getRandomInt(0, numNeighbors); // get random integer between 0 and numNeighbors-1 inclusive
			moveAdjacent(r);
		}

	}
		break;
	case PKinaseCE:
	{
		if (hasMoved == false) {
			int r = org.primordion.xholon.util.MiscRandom.getRandomInt(0, numNeighbors); // get random integer between 0 and numNeighbors-1 inclusive
			moveAdjacent(r);
		}

	}
		break;
	case PPhosphataseCE:
	{
		if (hasMoved == false) {
			int r = org.primordion.xholon.util.MiscRandom.getRandomInt(0, numNeighbors); // get random integer between 0 and numNeighbors-1 inclusive
			moveAdjacent(r);
		}

	}
		break;
	case G1PCE:
	{
		if (hasMoved == false) {
			int r = org.primordion.xholon.util.MiscRandom.getRandomInt(0, numNeighbors); // get random integer between 0 and numNeighbors-1 inclusive
			moveAdjacent(r);
		}

	}
		break;
	case GlyCE:
	{
		// stays in one place throughout the simulation
	}
		break;
	default:
		break;
	}
	super.postAct();
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
			System.out.println("XhRcs_GP_FSM_Grid: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
	case 446219361: // doActivity Active
	{
// GPase must be colocated with Gly, the substrate chain
if (port[Substrate] != null) {
  if (port[Substrate].getParentNode() == getParentNode()) {
    XhRcs_GP_FSM_Grid glc = (XhRcs_GP_FSM_Grid)port[Substrate].getFirstChild();
    if (glc != null) {
      // remove from Gly
      glc.removeChild();
      // place it directly in the GridCell
      IXholon newParentNode = getParentNode();
      glc.appendChild(newParentNode);
      // change its type to GiP
      glc.setXhc(getClassNode("G1P"));
      // decrement the GLC aggregator
      IXholon agg = glc.port[Aggregate];
      agg.decVal(1);
      // change the aggregator to Aggregator_G1P; assume the G1P is the next aggregator
      agg = agg.getNextSibling();
      glc.port[Aggregate] = agg;
      // increment the G1P aggregator
      agg.incVal(1);
    }
  }
}
	}
		break;
	case 856619429: // doActivity Active
	{
boolean doFsm = true;
if (port[Substrate] != null) {
  if (port[Substrate].getParentNode() == getParentNode()) {
    doFsm = false;
  }
}
if (port[Regulation] != null) {
  if (port[Regulation].getParentNode() != getParentNode()) {
    doFsm = false;
  }
}
if (doFsm) {
  port[Regulation].sendMessage(S_ACTIVATE, null, this);
}
	}
		break;
	case 199619492: // doActivity Active
	{
boolean doFsm = true;
if (port[Substrate] != null) {
  if (port[Substrate].getParentNode() != getParentNode()) {
    doFsm = false;
  }
}
if (port[Regulation] != null) {
  if (port[Regulation].getParentNode() != getParentNode()) {
    doFsm = false;
  }
}
if (doFsm) {
  port[Regulation].sendMessage(S_DEACTIVATE, null, this);
}
	}
		break;
	default:
		System.out.println("XhRcs_GP_FSM_Grid: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		System.out.println("XhRcs_GP_FSM_Grid: performGuard() unknown Activity " + activityId);
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
