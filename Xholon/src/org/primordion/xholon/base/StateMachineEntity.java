/* Xholon Runtime Framework - executes event-driven & dynamic applications
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

package org.primordion.xholon.base;

import java.util.Vector;
import org.primordion.xholon.common.mechanism.CeBehavior;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
//import org.primordion.xholon.common.mechanism.CeControl;

/**
 * A state machine entity is any XholonClass that has to do with state machines.
 * @see IStateMachineEntity
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Sep 25, 2005)
 * 
 * Note from August 28, 2007:
 * Two very similar types of state machines are now supported:
 * (1) UML2 state machine, as transformed from a UML model created using a UML modeling tool.
 * This is the type that's been supported all along. There are no changes.
 * (2) Xholon state machine, as specified in StateMachine_Xholon.dtd.
 * This type is easier to create manually.
 * The entire state machine is specified in a CompositeStructureHierarchy.xml file.
 * It uses states inside of states, rather than states inside of regions.
 * It uses transitions inside of states, rather than transitions inside of regions.
 * 
 */
 // TODO implement shallow history (done)
 // TODO implement deep history (done - currently works exactly the same as shallow history)
 // TODO implement TimeEvent 
 // TODO implement local transitions (TransitionLocal)
 // TODO implement message defer mechanism (done)
 // TODO implement other state machine features as per UML2 spec
 //      but don't implement connectionPointReference, protocol state machines
 // TODO Rumbaugh (p.431), says: "The junction state may have an internal action,
 //      but this is equivalent to attaching an action to the outgoing transition, which is the preferred form."
 //      Check the UML 2.1.1 spec. If this is true, implement internal action in junction.
public class StateMachineEntity extends Xholon implements IStateMachineEntity, CeBehavior, CeStateMachineEntity {
	private static final long serialVersionUID = 2926951857788147791L;
	
	// Connection Points
	public static final int CNPT_OUTGOING1  = 0; // State.outgoing (first instance)
	public static final int CNPT_OUTGOING2  = 1; // State.outgoing (second instance)
	public static final int CNPT_OUTGOING3  = 2; // State.outgoing (third instance)
	public static final int CNPT_OUTGOING4  = 3; // State.outgoing (fourth instance)
	public static final int CNPT_OUTGOING5  = 4; // State.outgoing (fifth instance)
	public static final int CNPT_OUTGOING6  = 5; // State.outgoing (6th instance)
	public static final int CNPT_OUTGOING7  = 6; // State.outgoing (7th instance)
	public static final int CNPT_OUTGOING8  = 7; // State.outgoing (8th instance)
	public static final int CNPT_OUTGOING9  = 8; // State.outgoing (9th instance)
	public static final int CNPT_OUTGOING10 = 9; // State.outgoing (10th instance)
	public static final int CNPT_TARGET = 0; // Transition.target
	
	// TODO Use different sizes for each type of StateMachineEntity.
	private static final int SIZE_CNPT = 6;
	
	// No activity has been defined for this state machine entity.
	public static final int ACTIVITYID_NONE = -1;
	
	// Number of active sub states for a state (> 1 if this is an orthogonal state)
	public static int MAX_ACTIVE_SUBSTATES = 3;
	
	// Maximum number of triggers per transition
	public static int MAX_TRIGGERS = 1;
	
	// whether or not to print active substate message
	public static final boolean PRINT_ACTIVE_SUBSTATE = Msg.infoM;
	
	protected IStateMachineEntity cnpt[] = null; // Connection Point
	protected static int maxCnpt = SIZE_CNPT; // default
	public String roleName = null;
	public String uid = null; // unique ID
	protected IStateMachineEntity activeSubState[] = null;
	// TODO Cut down on number of trigger and activity attributes.
	//      Not used by all types/instances of StateMachineEntity.
	protected int trigger[] = null; // for use by Transition
	protected int activityId = ACTIVITYID_NONE; // for use by Transition
	protected int entryActivityId = ACTIVITYID_NONE; // for use by State
	protected int exitActivityId = ACTIVITYID_NONE; // for use by State
	protected int doActivityId = ACTIVITYID_NONE; // for use by State
	protected int numRegions = 0; // for use by State to record how many Regions it has
	public int guardActivityId = ACTIVITYID_NONE; // for use by Transition from State or PseudostateChoice
	protected static Class xholonClass; // the xholon that owns this state machine
	
	// Junction pseudostate
	private static boolean isFollowingJunctionPathway = false;
	private static Vector junctionPathwayActivity = new Vector();
	
	// Vector temporarily containing all state machines, for use by initializeStateMachines().
	protected static Vector stateMachineV = new Vector();
	
	// History pseudostates; used to minimize processing if there are no history nodes in the current application
	protected static boolean hasHistoryPseudostates = false;
	
	/**
	 * Constructor.
	 */
	public StateMachineEntity() {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		cnpt = null;
		roleName = null;
		uid = null;
		trigger = null;
		activeSubState = null;
		activityId = ACTIVITYID_NONE;
		entryActivityId = ACTIVITYID_NONE;
		exitActivityId = ACTIVITYID_NONE;
		doActivityId = ACTIVITYID_NONE;
		numRegions = 0;
		guardActivityId = ACTIVITYID_NONE;
	}
	
	/**
	 * Is this State a composite state?
	 * "A composite state is a state that contains at least one region" (UML 2 spec., p. 532)
	 * @return true or false.
	 */
	public boolean isComposite()
	{
		return numRegions >= 1 ? true : false;
	}
	
	/**
	 * Is this State an orthogonal state?
	 * "An orthogonal composite state contains two or more regions" (UML 2 spec., p. 532)
	 * @return true or false.
	 */
	public boolean isOrthogonal()
	{
		return numRegions >= 2 ? true : false;
	}
	
	/**
	 * Is this State a simple state?
	 * "A simple state does not have any regions and it does not refer to any submachine state machine" (UML 2 spec., p. 532)
	 * @return true or false.
	 */
	public boolean isSimple()
	{
		return numRegions == 0 ? true : false;
	}
	
	/**
	 * Is this State a submachine state?
	 * "Such a state refers to a state machine (submachine)" (UML 2 spec., p. 532)
	 * Note: Xholon supports submachine states in UML models,
	 *       but transforms them to regular StateMachineEntity xholons.
	 * @return false.
	 */
	protected boolean isSubmachineState()
	{
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.base.IStateMachineEntity#isActiveSubState()
	 */
	public boolean isActiveSubState()
	{
		// if immediate parent is a Region, then go to its parent which should be a State or StateMachine
		IStateMachineEntity parentActiveSubstateState[];
		if (parentNode.getXhcId() == RegionCE) {
			parentActiveSubstateState = ((StateMachineEntity)parentNode.getParentNode()).activeSubState;
		}
		else { // StateCE or StateMachineCE
			parentActiveSubstateState = ((StateMachineEntity)parentNode).activeSubState;
		}
		if (parentActiveSubstateState != null) {
			for (int i = 0; i < parentActiveSubstateState.length; i++) {
				if (parentActiveSubstateState[i] == this) {
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.base.IStateMachineEntity#getOwningXholon()
	 */
	public IXholon getOwningXholon()
	{
		return getParentStateMachine().getParentNode();
	}
	
	/**
	 * Return the state machine that this StateMachineEntity is part of.
	 * @return The parent state machine, or self if there's been an error.
	 */
	protected IStateMachineEntity getParentStateMachine()
	{
		StateMachineEntity parentState = (StateMachineEntity)getParentNode();
		while (!(parentState.getXhcId() == StateMachineCE)) {
			if (parentState.isRootNode()) {
				// this should not happen; every state should belong to a state machine
				return this;
			}
			parentState = (StateMachineEntity)parentState.getParentNode();
		}
		return parentState;
	}
	
	/**
	 * Set the maximum number of ports for any xholon in the model.
	 * @param mPorts Maximum number of ports.
	 */
	public static void setMaxPorts(int mPorts) {maxCnpt = mPorts;}
	
	/**
	 * Get the maximum number of ports for any xholon in the model.
	 * @return Maximum number of ports.
	 */
	public static int getMaxPorts() {return maxCnpt;}
  
  /*
	 * @see org.primordion.xholon.base.IXholon#getPort()
	 */
  public IXholon[] getPort()
	{
		return cnpt;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getPort(int)
	 */
	public IXholon getPort(int cnptNum)
	{
		if (cnpt != null) {
			if (cnpt.length > cnptNum) {
				return cnpt[cnptNum];
			}
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 * This is intended to be used by the charting (line charts) package.
	 */
	public double getVal()
	{
		if (getXhcId() == StateMachineCE) {
			return getId();
		}
		else if (isActiveSubState()) {
			return getId();
		}
		else {
			return getParentStateMachine().getId();
		}
	}
	
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
	 * @see org.primordion.xholon.base.IXholon#setUid(java.lang.String)
	 */
	public void setUid(String uid)
	{
		this.uid = uid;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getUid()
	 */
	public String getUid()
	{
		return uid;
	}
	
	/**
	 * Return the uid or the roleName.
	 * @return Return roleName if uid == null. Else return uid.
	 */
	protected String getUidOrRoleName()
	{
		if (uid == null) {
			return roleName;
		}
		else {
			return uid;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IStateMachineEntity#getTrigger(int)
	 */
	public int getTrigger(int trigNum)
	{
		if (trigNum < trigger.length) {
			return trigger[trigNum];
		}
		else {
			// TODO make this a constant
			return -1000;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IStateMachineEntity#getActivityId()
	 */
	public int getActivityId() {return activityId;}
	/*
	 * @see org.primordion.xholon.base.IStateMachineEntity#getEntryActivityId()
	 */
	public int getEntryActivityId() {return entryActivityId;}
	/*
	 * @see org.primordion.xholon.base.IStateMachineEntity#getExitActivityId()
	 */
	public int getExitActivityId() {return exitActivityId;}
	/*
	 * @see org.primordion.xholon.base.IStateMachineEntity#getDoActivityId()
	 */
	public int getDoActivityId() {return doActivityId;}
	/*
	 * @see org.primordion.xholon.base.IStateMachineEntity#getGuardActivityId()
	 */
	public int getGuardActivityId() {return guardActivityId;}
	
	/**
	 * Set active sub-state of this and of all ancestor nodes (recursive).
	 * @param activeSubState The currently active substate of this state.
	 */
	protected void setActiveSubState(IStateMachineEntity activeSubState)
	{
		// this may be an orthogonal state
		// both the current and new active sub state must have the same Region as parent
		for (int i = 0; i < this.activeSubState.length; i++) {
			if (this.activeSubState[i] == null) { // should only happen at end of initial transition
				this.activeSubState[i] = activeSubState;
				break;
			}
			else {
				if (this.activeSubState[i].getParentNode() == activeSubState.getParentNode()) {
					if ((getXhcId() != StateMachineCE) && (this.activeSubState[i] != activeSubState)) {
						((StateMachineEntity)this.activeSubState[i]).clearActiveSubState();
					}
					this.activeSubState[i] = activeSubState;
					break;
				}
			}
		}
		// if this is not an orthogonal state
		if (PRINT_ACTIVE_SUBSTATE) {
			println(getName() + " activeSubState is " + activeSubState.getName());
		}
		
		if (getXhcId() == StateMachineCE) {
			if (PRINT_ACTIVE_SUBSTATE) {
				print("\n");
			}
			return; // end recursion
		}
		
		// recursively update active sub-state for all ancestor states
		StateMachineEntity parentState = (StateMachineEntity)getParentNode();
		while (!(parentState.getXhcId() == StateCE)) {
			if (parentState.getXhcId() == StateMachineCE) {
				break; // top ancestor has been found
			}
			parentState = (StateMachineEntity)parentState.getParentNode();
		}
		parentState.setActiveSubState(this);
	}
	
	/**
	 * Clear this state's active sub state(s) back to null.
	 * Recursively clear the activeSubStates of all sub states.
	 */
	protected void clearActiveSubState()
	{
		if (activeSubState == null) {return;}
		for (int i = 0; i < activeSubState.length; i++) {
			if (activeSubState[i] != null) {
				((StateMachineEntity)activeSubState[i]).clearActiveSubState();
				activeSubState[i] = null;
			}
		}
	}
	
	/**
	 * Should a completion transition be triggered?
	 * Called on a composite state, when a child FinalState is reached.
	 * A completion transition should be triggered when all FinalState are reached.
	 * @return true or false
	 */
	protected boolean isCompletionEvent()
	{
		/*for (int i = 0; i < MAX_ACTIVE_SUBSTATES; i++) {
			if ((activeSubState[i] != null) && (activeSubState[i].getXhcId() != FinalStateCE)) {
				return false;
			}
		}
		return true; */
		for (int i = 0; i < numRegions; i++) {
			if (activeSubState[i] == null) {
				// this region hasn't even been initially entered yet
				return false;
			}
			if (activeSubState[i].getXhcId() != FinalStateCE) {
				// this region is active, but hasn't reached a final state yet
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Look for a completion transition exiting from this composite or simple state,
	 * and if found, then take that transition.
	 * Behavior undefined if there is no completion transition.
	 * @param msg The message that caused the state machine to transition to a FinalState,
	 *        or to a state that has no explicitly triggered transitions.
	 */
	protected void doCompletionTransition(IMessage msg)
	{
		StateMachineEntity startOfChain = null;
		for (int i = 0; i < SIZE_CNPT; i++) {
			if ((cnpt != null) && (cnpt[i] != null)) {
				if (((StateMachineEntity)cnpt[i]).trigger[0] == 0) { // no trigger
					// does the optional guard condition allow this transition ?
					if ((((StateMachineEntity)cnpt[i]).guardActivityId == ACTIVITYID_NONE)
							|| (msg.getReceiver().performGuard(((StateMachineEntity)cnpt[i]).guardActivityId, msg))) {
						startOfChain = (StateMachineEntity)cnpt[i];
						break; // found it
					}
				}
			}
		} // end for
		if (startOfChain != null) {
			// about to exit this State, so do any exit activity if startOfChain is an external Transition
			if ((exitActivityId != ACTIVITYID_NONE)
					&& ((startOfChain.getXhcId() == TransitionCE)
						|| (startOfChain.getXhcId() == TransitionExternalCE))) {
				msg.getReceiver().performActivity(exitActivityId, msg);
			}
			startOfChain.doChainSegment(msg);
		}	
	}
	
	/**
	 * Is the current segment chain that's being followed, a junction pathway.
	 * @return true or false
	 */
	protected boolean isFollowingJunctionPathway()
	{
		switch (getXhcId()) {
		case TransitionCE:
		case TransitionExternalCE:
			if (isFollowingJunctionPathway) {
				return isFollowingJunctionPathway;
			}
			else { // not currently following a junction pathway
				if (((StateMachineEntity)cnpt[CNPT_TARGET]).getXhcId() == PseudostateJunctionCE) {
					setJunctionPathway(true);
					return isFollowingJunctionPathway;
				}
			}
			break;
		default:
			break;
		}
		return isFollowingJunctionPathway;
	}
	
	/**
	 * Set whether or not the current segment chain is a junction pathway.
	 * @param shouldFollowJunctionPathway true or false
	 */
	protected void setJunctionPathway(boolean shouldFollowJunctionPathway)
	{
		if (shouldFollowJunctionPathway) {
			if (!isFollowingJunctionPathway) {
				junctionPathwayActivity.removeAllElements(); // start with an empty Vector of activities
				isFollowingJunctionPathway = true;
			}
			// else, assume this is a duplicate method call, so do nothing
		}
		else {
			isFollowingJunctionPathway = false;
			junctionPathwayActivity.removeAllElements();
		}
	}
	
	/**
	 * Add an Activity to an internal data structure, for possible subsequent execution.
	 * It will be executed only if this junction pathway successfully reaches a target State or pseudostate.
	 * @param jpActivity A state machine activity node ID.
	 */
	protected void addActivityToJunctionPathway(int jpActivityId)
	{
		junctionPathwayActivity.addElement(new Integer(jpActivityId));
	}
	
	/**
	 * Execute any and all Activity nodes that may have been saved for subsequent execution.
	 * @param msg An incoming messge.
	 */
	protected void executeJunctionPathwayActivities(IMessage msg)
	{
		for (int i = 0; i < junctionPathwayActivity.size(); i++) {
			int jpActivityId = ((Integer)junctionPathwayActivity.elementAt(i)).intValue();
			msg.getReceiver().performActivity(jpActivityId, msg);
		}
		setJunctionPathway(false);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setPorts()
	 */
	public void setPorts()
	{
		// move up the inheritance hierarchy looking for first parent with a xhType
		IXholonClass iXhc = xhc;
		while ((!iXhc.isRootNode()) && (iXhc.getXhType() == IXholonClass.XhtypeNone)) {
			iXhc = (IXholonClass)iXhc.getParentNode();
		}
		// TODO Use different sizes for each type of StateMachineEntity.
		if (iXhc.isActiveObject()) {
			cnpt = new StateMachineEntity[SIZE_CNPT];
		}
	}
	
	/**
	 * Get a shallow history node contained within this composite state.
	 * This method should only be called on a composite state node.
	 * @return A shallow history node, or null if none exists.
	 */
	protected StateMachineEntity getShallowHistoryNode()
	{
		// get the region's left-most node; assume there is only one region in this composite state
		StateMachineEntity node = (StateMachineEntity)getFirstChild().getFirstChild();
		while (node != null) {
			switch (node.getXhcId()) {
			case PseudostateInitialCE: // first node may be this type
				node = (StateMachineEntity)node.getNextSibling();
				break;
			case PseudostateShallowHistoryCE: // first or second node may be this type
				return node;
			default: // PseudostateShallowHistory will always come before any other node type
				node = null;
				break;
			}
		}
		return null;
	}
	
	/**
	 * Get a deep history node contained within this composite state.
	 * This method should only be called on a composite state node.
	 * @return A deep history node, or null if none exists.
	 */
	protected StateMachineEntity getDeepHistoryNode()
	{
		// get the region's left-most node; assume there is only one region in this composite state
		StateMachineEntity node = (StateMachineEntity)getFirstChild().getFirstChild();
		while (node != null) {
			switch (node.getXhcId()) {
			case PseudostateInitialCE: // first node may be this type
			case PseudostateShallowHistoryCE: // second node may be this type
				node = (StateMachineEntity)node.getNextSibling();
				break;
			case PseudostateDeepHistoryCE: // first, second or third node may be this type
				return node;
			default: // PseudostateDeepHistory will always come before any other node type
				node = null;
				break;
			}
		}
		return null;
	}
	
	/**
	 * Point the outgoing transition of this shallow history node to the correct state.
	 * A history node has at most one outgoing transition.
	 * @param historyState A state that the history node's outgoing transition should chain to.
	 */
	protected void setShallowHistoryOutgoing(IStateMachineEntity historyState)
	{
		((StateMachineEntity)cnpt[0]).cnpt[0] = historyState;
	}
	
	/**
	 * Point the outgoing transition of this deep history node to the correct state.
	 * A history node has at most one outgoing transition.
	 * @param historyState A state that the history node's outgoing transition should chain to.
	 */
	protected void setDeepHistoryOutgoing(IStateMachineEntity historyState)
	{
		((StateMachineEntity)cnpt[0]).cnpt[0] = historyState;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#configure()
	 */
	public void configure()
	{
		if (xhc == null) {
			logger.error("StateMachineEntity::configure: xhClass is null.");
			return;
		}
		IXholonClass ciXhc = xhc;
		String instructions = ciXhc.getConfigurationInstructions();
		while ((!(ciXhc.isRootNode())) && (instructions.equals(""))) {
			ciXhc = (IXholonClass)ciXhc.getParentNode();
			instructions = ciXhc.getConfigurationInstructions();
		}
		//if (Msg.infoM) print( getName() + ": " + instructions );
		// carry out the instructions
		int len = instructions.length();
		int instructIx = 0;
		while (instructIx < len) {
			switch( instructions.charAt(instructIx)) {
			case 'c': // cnpt
				instructIx = configurePorts(instructions, instructIx);
				break;
			default:
				if (Msg.errorM)
					println( "StateMachineEntity configure: error [" + instructions.charAt(instructIx) + "]\n" );
				instructIx++;
				break;
			} // end switch
		} // end while
		
		// if this is a Composite or Orthogonal State (has 1+ regions), then it (probably) has sub states
		// if this is a Simple State, then it definitely has no sub states
		if ((xhc.hasAncestor("State"))
			&& ((getXPath().evaluate("Region", this) != null)
				|| (getXPath().evaluate("State", this) != null)
				|| (getXPath().evaluate("FinalState", this) != null))) {
			activeSubState = new IStateMachineEntity[MAX_ACTIVE_SUBSTATES];
			for (int i = 0; i < MAX_ACTIVE_SUBSTATES; i++) {
				activeSubState[i] = null;
			}
		}

		super.configure();
		
		// only initialize StateMachine after entire subtree has been configured
		if (getXhcId() == StateMachineCE) {
			activeSubState = new IStateMachineEntity[1];
			switch (getFirstChild().getXhcId()) {
			case StateCE:
				activeSubState[0] = (IStateMachineEntity)getFirstChild(); // Top state is an immediate child of StateMachine
				break;
			case RegionCE:
				activeSubState[0] = (IStateMachineEntity)getFirstChild().getFirstChild(); // Top state
				break;
			default:
				break;
			}
			xholonClass = getParentNode().getClass();
		}
	} // end configure()
	
	/**
	 * Configure ports.
	 * @param instructions
	 * @param instructIx
	 * @return
	 */
	protected int configurePorts(String instructions, int instructIx)
	{
		instructIx += 5; // point at char after opening [
		int startPos = instructIx;
		while (instructions.charAt(instructIx) != ']') {
			instructIx++;
		}
		String portId = instructions.substring(startPos, instructIx);
		int cnptNum = ReflectionFactory.instance().getAttributeIntVal(this, portId);
		instructIx += 2; // point to first char after =
		
		// handle multiple xpointers to same port; to allow alternatives if first one(s) don't work
		if (cnpt[cnptNum] == null) { // don't replace one that's already been assigned
			cnpt[cnptNum] = (IStateMachineEntity)getPortRef(instructions, instructIx);
		}
		
		while ((instructIx < instructions.length()) && (instructions.charAt(instructIx) != IInheritanceHierarchy.NAVINFO_SEPARATOR)) {
			instructIx++;
		}
		instructIx++;
		return instructIx;
	}
	
	/*
	 * There is no need to call postConfigure recursively within a state machine tree.
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure()
	{
		if (getXhcId() == StateMachineCE) {
			// initializeStateMachine();
			stateMachineV.addElement(this);
			// postConfigure any xholons that are siblings of the StateMachine
			if (nextSibling != null) {
				nextSibling.postConfigure();
			}
		}
	}
	
	/**
	 * Initialize all state machines.
	 * This can only be done after all links between xholons have been set up.
	 * Otherwise some initial transitions will be unable to send initial messages.
	 */
	public static void initializeStateMachines()
	{
		StateMachineEntity sme = null;
		IXPath xPath = null;
		for (int i = 0; i < stateMachineV.size(); i++) {
			sme = (StateMachineEntity)stateMachineV.elementAt(i);
			sme.initializeStateMachine();
			if (xPath == null) {
				xPath = sme.getXPath();
			}
			// find topmost instance of PseudostateInitial, and execute its chain segment
			// assumed that PseudostateInitial nodes are postioned before all their other siblings
			StateMachineEntity initialState
				= (StateMachineEntity)xPath.evaluate("descendant::PseudostateInitial", sme);
			if ((initialState != null)
					&& (initialState.cnpt != null)
					&& (initialState.cnpt.length >= 1)
					&& (initialState.cnpt[CNPT_OUTGOING1] != null)) {
				((StateMachineEntity)initialState.cnpt[CNPT_OUTGOING1]).doChainSegment(
						// create a simple message that the target state can use to find the receiver
						new Message(ISignal.SIGNAL_INIT_FSM, null, null, xPath.evaluate("ancestor::StateMachine/..", initialState))
						);
			}
			else {
				System.out.println("No PseudostateInitial for FSM: " + sme.getName());
			}
		}
		stateMachineV.removeAllElements(); // don't need the Vector elements anymore
	}
	
	/**
	 * Set up all the initial active states in the StateMachine hierarchy.
	 * Make that the activeSubState of that state's parent.
	 * If the init transition includes an Activity, then the code for that Activity will be normally executed.
	 */
	protected void initializeStateMachine()
	{
		// execute recursively; initialize from bottom up so active states are correctly set
		if (firstChild != null) {
			((StateMachineEntity)firstChild).initializeStateMachine();
		}
		// if this is a StateMachineCE, then if there's a next sibling, it will be a Xholon
		if ((nextSibling != null) && (getXhcId() != StateMachineCE)) {
			((StateMachineEntity)nextSibling).initializeStateMachine();
		}
		
		StateMachineEntity aChild;
		String uidRn;
		switch (getXhcId()) {
		case PseudostateInitialCE:
			aChild = (StateMachineEntity)getFirstChild();
			if (aChild != null) {
				switch (aChild.getXhcId()) {
				case TransitionExternalCE:
					cnpt[CNPT_OUTGOING1] = aChild;
					break;
				default:
					break;
				}
			}
			break;
		case StateCE:
		case FinalStateCE:
			aChild = (StateMachineEntity)getFirstChild();
			while (aChild != null) {
				uidRn = aChild.getUidOrRoleName();
				switch (aChild.getXhcId()) {
				case EntryActivityCE:
					if (uidRn.charAt(0) == '0') {
						if ((uidRn.charAt(1) == 'x') | (uidRn.charAt(1) == 'X')) {
							entryActivityId = Integer.parseInt(uidRn.substring(2), 16);
						}
						else {
							entryActivityId = Integer.parseInt(uidRn, 8);
						}
					}
					else {
						//entryActivityId = Integer.parseInt(uidRn);
						entryActivityId = getAttributeIntVal(uidRn);
					}
					break;
				case ExitActivityCE:
					if (uidRn.charAt(0) == '0') {
						if ((uidRn.charAt(1) == 'x') | (uidRn.charAt(1) == 'X')) {
							exitActivityId = Integer.parseInt(uidRn.substring(2), 16);
						}
						else {
							exitActivityId = Integer.parseInt(uidRn, 8);
						}
					}
					else {
						//exitActivityId = Integer.parseInt(uidRn);
						exitActivityId = getAttributeIntVal(uidRn);
					}
					break;
				case DoActivityCE:
					if (uidRn.charAt(0) == '0') {
						if ((uidRn.charAt(1) == 'x') | (uidRn.charAt(1) == 'X')) {
							doActivityId = Integer.parseInt(uidRn.substring(2), 16);
						}
						else {
							doActivityId = Integer.parseInt(uidRn, 8);
						}
					}
					else {
						//doActivityId = Integer.parseInt(uidRn);
						doActivityId = getAttributeIntVal(uidRn);
					}
					break;
				case DeferrableTriggerCE:
					//println("initializeStateMachine() DeferrableTriggerCE under StateCE");
					// set cnpt from State to DeferrableTrigger, which is really a type of tranistion
					for (int i = 0; i < maxCnpt; i++) {
						if (cnpt[i] == null) {
							cnpt[i] = aChild;
							break;
						}
					}
					// get triggers
					aChild.trigger = new int[MAX_TRIGGERS]; // assume only one trigger per transition (could be more)
					String trigStr = null;
					StateMachineEntity trigChild = (StateMachineEntity)aChild.getFirstChild();
					switch (trigChild.getXhcId()) {
					case TriggerCE:
						trigStr = trigChild.getRoleName();
						if (trigStr.startsWith("ISignal.")) {
							// ex: "ISignal.SIGNAL_TIMEOUT"
							aChild.trigger[0] = ReflectionFactory.instance().getAttributeIntVal(
									ISignal.class, trigStr.substring(8));
						}
						else {
							aChild.trigger[0] = ReflectionFactory.instance().getAttributeIntVal(
									xholonClass, trigStr);
						}
						break;
					default:
						break;
					}
					break;
				case RegionCE:
					numRegions++;
					break;
				case PseudostateExitPointCE:
				case PseudostateEntryPointCE:
					// none of these need to be initialized at this point ?
					break;
				case PseudostateInitialCE:
				case PseudostateShallowHistoryCE:
				case PseudostateDeepHistoryCE:
				case StateCE:
				case FinalStateCE:
				case PseudostateChoiceCE:
				case PseudostateForkCE:
				case PseudostateJoinCE:
				case PseudostateJunctionCE:
				case PseudostateTerminateCE:
					if (numRegions == 0) {
						numRegions = 1; // this is a composite state, or at least not a simple state
					}
					break;
				case TransitionExternalCE:
				case TransitionInternalCE:
					// locate an available cnpt to store this transition from the State
					for (int i = 0; i < cnpt.length; i++) {
						if (cnpt[i] == null) {
							cnpt[i] = aChild;
							break;
						}
					}
					break;
				default:
					if (Msg.errorM) {
						System.err.println("StateMachineEntity initializeStateMachine() child1 invalid xholon class id: " + aChild.getXhcId());
					}
					break;
				} // end switch
				aChild = (StateMachineEntity)aChild.getNextSibling();
			}
			break; // end case StateCE FinalStateCE
		case TransitionCE:
		case TransitionExternalCE:
		case TransitionInternalCE:
		case TransitionLocalCE:
			// get triggers
			trigger = new int[MAX_TRIGGERS];
			String trigStr = null;
			aChild = (StateMachineEntity)getFirstChild();
			while (aChild != null) {
				uidRn = aChild.getUidOrRoleName();
				switch (aChild.getXhcId()) {
				case TriggerCE:
					trigStr = aChild.getRoleName();
					if (trigStr.startsWith("ISignal.")) {
						// ex: "ISignal.SIGNAL_TIMEOUT"
						trigger[0] = ReflectionFactory.instance().getAttributeIntVal(
								ISignal.class, trigStr.substring(8));
					}
					else {
						trigger[0] = ReflectionFactory.instance().getAttributeIntVal(
								xholonClass, trigStr);
					}
					break;
				case GuardCE:
					if (uidRn.charAt(0) == '0') {
						if ((uidRn.charAt(1) == 'x') | (uidRn.charAt(1) == 'X')) {
							guardActivityId = Integer.parseInt(uidRn.substring(2), 16);
						}
						else {
							guardActivityId = Integer.parseInt(uidRn, 8);
						}
					}
					else {
						//guardActivityId = Integer.parseInt(uidRn);
						guardActivityId = getAttributeIntVal(uidRn);
					}
					break;
				case ActivityCE:
					if (uidRn.charAt(0) == '0') {
						if ((uidRn.charAt(1) == 'x') | (uidRn.charAt(1) == 'X')) {
							activityId = Integer.parseInt(uidRn.substring(2), 16);
						}
						else {
							activityId = Integer.parseInt(uidRn);
						}
					}
					else {
						//activityId = Integer.parseInt(uidRn);
						activityId = getAttributeIntVal(uidRn);
					}
					// activityId = Misc.atoi(uid, 1); // ex: "_527"
					break;
				case TargetCE:
					String rn = aChild.getRoleName();
					String xpathExpression;
					if ((rn.charAt(0) == '.') || (rn.charAt(0) == '/')) {
						// the roleName is the xpath expression (examples: ./Hello /World)
						xpathExpression = rn;
					}
					else {
						// the roleName is just part of the xpath expression (example: hello)
						xpathExpression = "ancestor::StateMachine/descendant::Vertex[@roleName='" + rn + "']";
					}
					cnpt[CNPT_OUTGOING1] = (IStateMachineEntity)getXPath().evaluate(xpathExpression, this);
					break;
				default:
					if (Msg.errorM) {
						System.err.println("StateMachineEntity initializeStateMachine() child2 invalid xholon class id: " + aChild.getXhcId());
					}
					break;
				} // end switch
				aChild = (StateMachineEntity)aChild.getNextSibling();
			}
			break;
		case PseudostateJoinCE:
			// Join needs to find out how many incoming transitions it has,
			// each from a different Region in an orthogonal state.
			//numRegions = 2;
			numRegions = countIncomingTransitions();
			break;
		case StateMachineCE:
		case RegionCE:
		case PseudostateTerminateCE:
		case PseudostateExitPointCE:
		case PseudostateEntryPointCE:
			break;
		case PseudostateChoiceCE:
		case PseudostateJunctionCE:
		case PseudostateForkCE:
			// locate an available cnpt to store this transition from the pseudostate
			aChild = (StateMachineEntity)getFirstChild();
			while (aChild != null) {
				switch (aChild.getXhcId()) {
				case TransitionExternalCE:
					for (int i = 0; i < cnpt.length; i++) {
						if (cnpt[i] == null) {
							cnpt[i] = aChild;
							break;
						}
					}
					break;
				default:
					break;
				} // end switch
				aChild = (StateMachineEntity)aChild.getNextSibling();
			}
			break;
		case PseudostateShallowHistoryCE:
		case PseudostateDeepHistoryCE:
			aChild = (StateMachineEntity)getFirstChild();
			if (aChild != null) {
				switch (aChild.getXhcId()) {
				case TransitionExternalCE:
					cnpt[CNPT_OUTGOING1] = aChild;
					break;
				default:
					break;
				}
			}
			hasHistoryPseudostates = true;
			break;
		case TriggerCE:
		case ActivityCE:
		case GuardCE:
		case EntryActivityCE:
		case ExitActivityCE:
		case DoActivityCE:
		case DeferrableTriggerCE:
		case TargetCE:
			// none of these need to be initialized ?
			break;
		default:
			if (Msg.errorM) {
				System.err.println("StateMachineEntity initializeStateMachine() invalid xholon class id: " + getXhcId());
			}
			break;
		} // end switch
	} // end initializeStateMachine()
	
	/**
	 * Get the value of an int attribute.
	 * @param stringVal The name of an attribute in the xholon class that owns this state machine, or an int.
	 * @return An int value.
	 */
	protected int getAttributeIntVal(String stringVal)
	{
		return ReflectionFactory.instance().getAttributeIntVal(xholonClass, stringVal);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 * 
	 * This overrides the act() function of this XholonClass's superclass.
	 * This prevents the StateMachine part of the Xholon tree from being invoked recursively.
	 * DO NOT REMOVE, even though it looks like this function is doing nothing.
	 */
	public void act() {}
	
	/*
	 * @see org.primordion.xholon.base.IStateMachineEntity#doStateMachine(org.primordion.xholon.base.Message)
	 */
	public void doStateMachine(IMessage msg)
	{
		//println("StateMachineEntity: Processing this message through a hierarchical FSM "
		//		+ getName());
		// don't need to handle orthogonal states; this function only called for "StateMachine" xholon
		if (activeSubState[0] != null) {
			((StateMachineEntity)activeSubState[0]).findChainStart(msg);
		}
	}
	
	/**
	 * Find start of chain.
	 * @param msg An incoming message.
	 * @return The Transition referenced by a currently active state.
	 */
	protected StateMachineEntity findChainStart(IMessage msg)
	{
		int i;
		// store history if may be exiting a composite state that contains a shallow history node
		if (hasHistoryPseudostates && isComposite()) {
			StateMachineEntity shallowHistoryNode = getShallowHistoryNode();
			if (shallowHistoryNode != null) {
				shallowHistoryNode.setShallowHistoryOutgoing(activeSubState[0]);
			}
			StateMachineEntity deepHistoryNode = getDeepHistoryNode();
			if (deepHistoryNode != null) {
				deepHistoryNode.setDeepHistoryOutgoing(activeSubState[0]);
			}
		}
		StateMachineEntity startOfChain = null;
		if (activeSubState != null) { // not yet the lowest nested level
			for (i = 0; i < MAX_ACTIVE_SUBSTATES; i++) {
				if (activeSubState[i] == null) {break;} // exit from loop when find end of possibilities
				startOfChain = ((StateMachineEntity)activeSubState[i]).findChainStart(msg);
			}
		}
		if (startOfChain == null) {
			// do activity
			if ((msg.getSignal() == ISignal.SIGNAL_DOACTIVITY) && (doActivityId != ACTIVITYID_NONE)) {
				msg.getReceiver().performActivity(doActivityId, msg);
			}
			else {
				// compare msg.signal with events that can trigger a transition from this state
				for (i = 0; i < SIZE_CNPT; i++) {
					// TODO break when find cnpt[i] == null; but in theory there may be null holes ???
					if ((cnpt != null) && (cnpt[i] != null)) {
						// does the received msg signal match the trigger ?
						// only 1 possible trigger for now
						if (((StateMachineEntity)cnpt[i]).trigger[0] == msg.getSignal()) {
							// does the optional guard condition allow this transition ?
							if ((((StateMachineEntity)cnpt[i]).guardActivityId == ACTIVITYID_NONE)
									|| (msg.getReceiver().performGuard(((StateMachineEntity)cnpt[i]).guardActivityId, msg))) {
								startOfChain = (StateMachineEntity)cnpt[i];
								break; // found it
							}
						}
						else if (((StateMachineEntity)cnpt[i]).trigger[0] == ISignal.SIGNAL_ANY){
							// does the optional guard condition allow this transition ?
							if ((((StateMachineEntity)cnpt[i]).guardActivityId == ACTIVITYID_NONE)
									|| (msg.getReceiver().performGuard(((StateMachineEntity)cnpt[i]).guardActivityId, msg))) {
								startOfChain = (StateMachineEntity)cnpt[i];
							}
							// don't break out of the while loop; only accept the ANY if nothing else matches
						}
					}
				} // end for
				if (startOfChain != null) {
					// about to exit this State, so do any exit activity if startOfChain is an external Transition
					if ((exitActivityId != ACTIVITYID_NONE)
							&& ((startOfChain.getXhcId() == TransitionCE)
								|| (startOfChain.getXhcId() == TransitionExternalCE))) {
						if (startOfChain.isFollowingJunctionPathway()) {
							// don't execute exitActivity unless the junction pathway succeeds
							addActivityToJunctionPathway(activityId);
						}
						else {
							msg.getReceiver().performActivity(exitActivityId, msg);
						}
					}
					startOfChain.doChainSegment(msg);			
				}
			} // end else
		}
		// return the start of chain
		return startOfChain;
	}
	
	/**
	 * Process one segment of a chain.
	 * TODO handle other pseudostates
	 * @param msg An incoming message.
	 */
	protected void doChainSegment(IMessage msg)
	{
		StateMachineEntity parentState;
		switch (getXhcId()) {
		case TransitionCE:
		case TransitionExternalCE:
			if (isFollowingJunctionPathway()) {
				if (activityId != ACTIVITYID_NONE) {
					addActivityToJunctionPathway(activityId);
				}
			}
			else {
				if (activityId != ACTIVITYID_NONE) {
					msg.getReceiver().performActivity(activityId, msg);
				}
			}
			((StateMachineEntity)cnpt[CNPT_TARGET]).doChainSegment(msg);
			break;
		case TransitionLocalCE: // TODO handle this correctly
			if (activityId != ACTIVITYID_NONE) {
				msg.getReceiver().performActivity(activityId, msg);
			}
			//((StateMachineEntity)cnpt[CNPT_TARGET]).doChainSegment(msg); // ?
			break;
		case TransitionInternalCE:
			if (activityId != ACTIVITYID_NONE) {
				msg.getReceiver().performActivity(activityId, msg);
			}
			// there's no need to continue on to the next chain segment
			// "an internal transition has a source state but no target state" (Rumbaugh (2005) p.420)
			break;
		case DeferrableTriggerCE:
			// put the message back into the message Q, effectively deferring it until later
			println("Message deferred: " + msg);
			msg.getReceiver().sendMessage(msg);
			break;
		case StateCE:
			// about to enter this State, so do any entry activity
			if (isFollowingJunctionPathway()) {
				if (activityId != ACTIVITYID_NONE) {
					addActivityToJunctionPathway(entryActivityId);
				}
			}
			else {
				if (entryActivityId != ACTIVITYID_NONE) {
					msg.getReceiver().performActivity(entryActivityId, msg);
				}
			}
			// has a transition terminated at a composite or orthogonal composite state?
			if (isComposite()) {
				//println("**** transition" + " has terminated at a composite state " + getName() + " " + msg);
				StateMachineEntity compRegion = (StateMachineEntity)getFirstChild();
				while (compRegion != null) {
					if ((compRegion.getXhcId() == RegionCE) || (compRegion.getXhcId() == StateCE)) {
						StateMachineEntity compPseudostate = (StateMachineEntity)compRegion.getFirstChild();
						while (compPseudostate != null) {
							if (compPseudostate.getXhcId() == PseudostateInitialCE) {
								//println("**** executing initial transition " + compPseudostate.getName());
								((StateMachineEntity)compPseudostate.cnpt[CNPT_OUTGOING1]).doChainSegment(msg);
								break; // "a region can have at most one initial vertex" (UML 2 spec. p.529)
							}
							compPseudostate = (StateMachineEntity)compPseudostate.getNextSibling();
							// TODO also handle history states
						}
						if ((compPseudostate == null) && (compRegion.getXhcId() == RegionCE)) {
							// this must be a Xholon type state machine, where Region just has a single State
							compPseudostate = (StateMachineEntity)compRegion.getFirstChild();
							if (compPseudostate.getXhcId() == StateCE) {
								compPseudostate = (StateMachineEntity)compPseudostate.getFirstChild();
								while (compPseudostate != null) {
									if (compPseudostate.getXhcId() == PseudostateInitialCE) {
										//println("**** executing initial transition " + compPseudostate.getName());
										((StateMachineEntity)compPseudostate.cnpt[CNPT_OUTGOING1]).doChainSegment(msg);
										break; // "a region can have at most one initial vertex" (UML 2 spec. p.529)
									}
									compPseudostate = (StateMachineEntity)compPseudostate.getNextSibling();
									// TODO also handle history states
								}
							}
						}
					}
					compRegion = (StateMachineEntity)compRegion.getNextSibling();
				}
			}
			else { // this is a simple state, and therefore the end of the chain
				// if the chain up to here was a junction pathway, then execute the saved activities
				if (isFollowingJunctionPathway()) {
					executeJunctionPathwayActivities(msg);
				}
			}
			// set this state as it's parent's active sub state
			parentState = (StateMachineEntity)getParentNode();
			while (!(parentState.getXhcId() == StateCE)) {
				parentState = (StateMachineEntity)parentState.getParentNode();
				if (parentState.getXhcId() == StateMachineCE) {
					break; // reached top of state machine nested hierarchy
				}
			}
			parentState.setActiveSubState(this);
			notifyStateChange();
			// completion event if this state has no outgoing transitions with explicit triggers
			// see UML spec, Transition: "A completion transition is a transition originating from a state
			//   or an exit point but which does not have an explicit trigger, although it may have a guard defined.
			//   In case of a leaf state, a completion event is generated
			//   once the entry actions and internal activities have been completed."
			// see Rumbaugh et al (2005) p. 246 "If a completion event occurs,
			//   an enabled completion transition fires immediately without being placed in the normal event pool."
			if (this.isSimple()) { // is this a leaf state?
				for (int i = 0; i < SIZE_CNPT; i++) {
					if ((cnpt != null) && (cnpt[i] != null)) {
						if (((StateMachineEntity)cnpt[i]).trigger[0] > 0) {
							// there's at least one explicity triggered transition, so stay in this state
							return;
						}
					}
				}
				// do completion transition, since this state has no transitions with explicit triggers
				doCompletionTransition(msg);
			}
			break;
		case FinalStateCE:
			// if the chain up to here was a junction pathway, then execute the saved activities
			if (isFollowingJunctionPathway()) {
				executeJunctionPathwayActivities(msg);
			}
			// about to enter this State, so do any entry activity
			if (entryActivityId != ACTIVITYID_NONE) {
				msg.getReceiver().performActivity(entryActivityId, msg);
			}
			
			
			parentState = (StateMachineEntity)getParentNode();
			if (parentState.getXhcId() == RegionCE) { // this is a UML-type state machine
				while (!(parentState.getXhcId() == StateCE)) {
					parentState = (StateMachineEntity)parentState.getParentNode();
					if (parentState.getXhcId() == StateMachineCE) {
						break; // reached top of state machine nested hierarchy
					}
				}
			}
			else { // this is a Xholon-type state machine
				parentState = (StateMachineEntity)parentState.getParentNode();
				while (!(parentState.getXhcId() == StateCE)) {
					if (parentState.getXhcId() == StateMachineCE) {
						break; // reached top of state machine nested hierarchy
					}
					parentState = (StateMachineEntity)parentState.getParentNode();
				}
			}
			
			
			parentState.setActiveSubState(this);
			notifyStateChange();
			// if all final states in this composite state have now been reached,
			// then trigger a completion transition (UML 2.1.1 spec, section 15.3.14 Transition)
			// "When the final state is entered, its containing region is completed,
			// which means that it satisfies the completion condition.
			// The containing state for this region is considered completed
			// when all contained regions are completed."
			// (UML 2.1.1 spec, section 15.3.2 FinalState)
			if (parentState.isCompletionEvent()) {
				if (parentState.getXhcId() == StateMachineCE) {
					// "If the region is contained in a state machine
					// and all other regions in the state machine also are completed,
					// the entire state machine terminates,
					// implying the termination of the context object of the state machine."
					// (UML 2.1.1 spec, section 15.3.2 FinalState)
					terminate();
				}
				else {
					parentState.doCompletionTransition(msg);
				}
			}
			break;
		case PseudostateChoiceCE:
			// if the chain up to here was a junction pathway, then execute the saved activities
			if (isFollowingJunctionPathway()) {
				executeJunctionPathwayActivities(msg);
			}
			// check the guards to find a transition from this choice point
			for (int i = 0; i < SIZE_CNPT; i++) {
				if ((cnpt != null) && (cnpt[i] != null)) {
					// does the guard condition allow this transition ?
					if ((((StateMachineEntity)cnpt[i]).guardActivityId == ACTIVITYID_NONE)
							|| (msg.getReceiver().performGuard(((StateMachineEntity)cnpt[i]).guardActivityId, msg))) {
						((StateMachineEntity)cnpt[i]).doChainSegment(msg);
						return; // found it
					}
				}
			}
			println("StateMachineEntity doChainSegment(): can't find transition from Choice");
			break;
		case PseudostateJunctionCE:
			// check the guards to find a transition from this junction
			for (int i = 0; i < SIZE_CNPT; i++) {
				if ((cnpt != null) && (cnpt[i] != null)) {
					// does the guard condition allow this transition ?
					if ((((StateMachineEntity)cnpt[i]).guardActivityId == ACTIVITYID_NONE)
							|| (msg.getReceiver().performGuard(((StateMachineEntity)cnpt[i]).guardActivityId, msg))) {
						((StateMachineEntity)cnpt[i]).doChainSegment(msg);
						return; // found it
					}
				}
			}
			// the junction pathway fails to find an end state; it should back off
			setJunctionPathway(false);
			println("StateMachineEntity doChainSegment(): can't find transition from PseudostateJunction");
			return; // TODO is this correct?
		case PseudostateForkCE:
			// if the chain up to here was a junction pathway, then execute the saved activities
			if (isFollowingJunctionPathway()) {
				executeJunctionPathwayActivities(msg);
			}
			// follow at least two transition segments from this fork
			for (int i = 0; i < SIZE_CNPT; i++) {
				if ((cnpt != null) && (cnpt[i] != null)) {
					((StateMachineEntity)cnpt[i]).doChainSegment(msg);
				}
			}
			break;
		case PseudostateJoinCE:
			//println("----> join");
			numRegions--;
			if (numRegions == 0) {
				// look for next segment in chain
				for (int i = 0; i < SIZE_CNPT; i++) {
					if ((cnpt != null) && (cnpt[i] != null)) {
						((StateMachineEntity)cnpt[i]).doChainSegment(msg);
						// set numRegions back to the number of regions that send transitions to the join
						numRegions = countIncomingTransitions();
						return; // found it
					}
				}
				println("StateMachineEntity doChainSegment(): can't find transition from Join");
			}
			break;
		case PseudostateShallowHistoryCE:
			// if the chain up to here was a junction pathway, then execute the saved activities
			if (isFollowingJunctionPathway()) {
				executeJunctionPathwayActivities(msg);
			}
			((StateMachineEntity)cnpt[0]).doChainSegment(msg);
			break;
		case PseudostateDeepHistoryCE:
			// if the chain up to here was a junction pathway, then execute the saved activities
			if (isFollowingJunctionPathway()) {
				executeJunctionPathwayActivities(msg);
			}
			((StateMachineEntity)cnpt[0]).doChainSegment(msg);
			break;
		case PseudostateEntryPointCE:
			// about to enter the entry point's State, so do any entry activity for that state
			if (((StateMachineEntity)parentNode).entryActivityId != ACTIVITYID_NONE) {
				if (isFollowingJunctionPathway()) {
					addActivityToJunctionPathway(entryActivityId);
				}
				else {
					msg.getReceiver().performActivity(((StateMachineEntity)parentNode).entryActivityId, msg);
				}
			}
			// look for next segment in chain
			for (int i = 0; i < SIZE_CNPT; i++) {
				if ((cnpt != null) && (cnpt[i] != null)) {
					((StateMachineEntity)cnpt[i]).doChainSegment(msg);
					return; // found it
				}
			}
			println("StateMachineEntity doChainSegment(): can't find transition from EntryPoint");
			break;
		case PseudostateExitPointCE:
			for (int i = 0; i < SIZE_CNPT; i++) {
				if ((cnpt != null) && (cnpt[i] != null)) {
					((StateMachineEntity)cnpt[i]).doChainSegment(msg);
					return; // found it
				}
			}
			println("StateMachineEntity doChainSegment(): can't find transition from ExitPoint");
			break;
		case PseudostateTerminateCE:
			// if the chain up to here was a junction pathway, then execute the saved activities
			if (isFollowingJunctionPathway()) {
				executeJunctionPathwayActivities(msg);
			}
			terminate();
			break;
		default:
			if (Msg.errorM) {
				System.err.println("StateMachineEntity doChainSegment() invalid xholon class id: " + getXhcId());
			}
			break;
		} // end switch
	}
	
	/*
	 * Recursively navigate up the state machine hierarchy.
	 * @see org.primordion.xholon.base.Xholon#terminate()
	 */
	public void terminate()
	{
		IXholon p = parentNode;
		if (getXhcId() == StateMachineCE) {
			removeChild();
			remove();
		}
		p.terminate();
	}
	
	/**
	 * Count the number of transitions incoming to this state or pseudostate.
	 * This is specifically required by the join pseudostate,
	 * so it can tell how many orthogonal region states may be transitioning to it.
	 * @return A number greater than or equal to zero.
	 */
	protected int countIncomingTransitions()
	{
		// get root of subtree
		StateMachineEntity parentState = (StateMachineEntity)getParentNode();
		while (!(parentState.getXhcId() == StateMachineCE)) {
			parentState = (StateMachineEntity)parentState.getParentNode();
		}
		int count = parentState.countIncomingTransitions(this);
		//println("count:" + count);
		return count;
		//return 2;
	}
	
	/**
	 * 
	 * @param reffedNode
	 * @return
	 */
	protected int countIncomingTransitions(StateMachineEntity reffedNode)
	{
		int count = 0;
		if (cnpt != null) {
			for (int i = 0; i < cnpt.length; i++) {
				if (cnpt[i] != null) {
					if (cnpt[i] == reffedNode) {
						count++;
					}
				}
			}
		}
		if (firstChild != null) {
			count += ((StateMachineEntity)firstChild).countIncomingTransitions(reffedNode);
		}
		if (nextSibling != null) {
			count += ((StateMachineEntity)nextSibling).countIncomingTransitions(reffedNode);
		}
		return count;
	}
	
	/**
	 * Notify external entities that a state has changed in the state machine.
	 */
	protected void notifyStateChange()
	{
		if (getInteractionsEnabled()) {
			getInteraction().addState(this);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IStateMachineEntity#notifyActiveSubStates()
	 */
	public void notifyActiveSubStates()
	{
		if (activeSubState == null) {return;}
		for (int i = 0; i < activeSubState.length; i++) {
			if (activeSubState[i] != null) {
				//((StateMachineEntity)activeSubState[i]).clearActiveSubState();
				//activeSubState[i] = null;
				if (((StateMachineEntity)activeSubState[i]).isSimple()) { // ???
					getInteraction().addState((StateMachineEntity)activeSubState[i]);
				}
				else {
					((StateMachineEntity)activeSubState[i]).notifyActiveSubStates();
				}
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#handleNodeSelection()
	 */
	/*public String handleNodeSelection()
	{
		if (getXhcId() == CeStateMachineEntity.StateMachineCE) {
			IApplication myApp = Application.getApplication(this);
			String myParams = "null,800,500,10,LAYOUT_KK,false,"
				+ getParentNode().getName() + " --- " + getName() + ",true";
			myApp.invokeGraphicalNetworkViewer(this, myParams);
		}
		return toString();
	} */
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		if ((cnpt != null) && (cnpt.length > 0)) {
			outStr += " [";
			for (int i = 0; i < cnpt.length; i++) {
				if (cnpt[i] != null) {
					outStr += " cnpt:" + cnpt[i].getName();
				}
			}
			outStr += "]";
		}
		if (activeSubState != null) {
			outStr += " activeSubState:";
			for (int i = 0; i < activeSubState.length; i++) {
				if (activeSubState[i] != null) {
					outStr += activeSubState[i].getName() + " ";
				}
			}
		}
		if (uid != null) {
			outStr += " uid:" + uid;
		}
		return outStr;
	}
}
