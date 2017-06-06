/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

package org.primordion.ef.fsm;

import java.util.Date;

import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.base.IStateMachineEntity;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XPath;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export an executing Xholon application as an XML file in SMC format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @author Thanks to David Atuahene-amankwa for several enhancements (December 29, 2007)
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://smc.sourceforge.net/">SMC website</a>
 * @since 0.7 (Created on August 14, 2007)
 * 
 */
public class Xholon2Smc extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat, CeStateMachineEntity {

  private String smcFileName;      // Name of the output .sm file.
  private String outPath = "./ef/sm/";
  private StringBuilder sb;
  private String modelName;
  private IXholon root;            // The xholon that owns the state machine.
  private XPath xpath = null; // A local instance of XPath.
  private IXholon owningXholon;    // The xholon that owns the state machine.
  private String owningXholonName; // The name of the xholon that owns the state machine.
  private IXholon topState;        // The top state in the state machine nested structure.
  
  /**
   * Current date and time.
   */
  private Date timeNow;
  private long timeStamp;
  
  /**
   * Constructor.
   */
  public Xholon2Smc() {}
  
  /**
   * Constructor.
   * @param smcFileName Name of the output SMC file.
   * @param modelName Name of the model.
   * @param root Root of the tree that will be written out.
   */
  public Xholon2Smc(String smcFileName, String modelName, IXholon root) {
    initialize(smcFileName, modelName, root);
  }
  
	@Override
	public String getVal_String() {
	  return sb.toString();
	}
	
  /*
   * @see org.primordion.xholon.io.IXholon2Smc#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String smcFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (smcFileName == null) {
      this.smcFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".sm";
    }
    else {
      this.smcFileName = smcFileName;
    }
    this.modelName = modelName;
    this.root = root;
    xpath = new XPath();
    return true;
  }

  /*
   * @see org.primordion.xholon.io.IXholon2Smc#writeAll()
   */
  public void writeAll() {
    topState = xpath.evaluate("descendant::StateMachine/descendant::State", root);
    if (topState == null) {return;}
    owningXholon = ((IStateMachineEntity)topState).getOwningXholon();
    owningXholonName = owningXholon.getXhcName();
    if (smcFileName == null) {
      // there is no default file name, so create a file name
      smcFileName = "./smc/" + owningXholonName + ".sm";
    }
    sb = new StringBuilder();
    sb.append("");
    sb.append(
      "// Automatically generated by Xholon version 0.7, using Xholon2Smc.java\n"
      + "// " + timeNow + "\n"
      + "// www.primordion.com/Xholon\n\n");
    
    writeSmc();
    writeToTarget(sb.toString(), smcFileName, outPath, root);
  }

  /**
   * Write the smc tag, and the entire structure contained within that element.
   */
  protected void writeSmc() {
    writeNotes();
    // get initial state
    IXholon initialState = xpath.evaluate("descendant::PseudostateInitial", topState);
    if (initialState != null) {
      initialState = initialState.getPort(0); // get the single transition leading from pseudostateInitial
    }
    if (initialState != null) {
      initialState = initialState.getPort(0); // get the single target of the transition
    }
    sb.append("\n");
    sb.append("%start " + owningXholonName + "::");
    if (initialState != null) {
      sb.append(initialState.getRoleName());
    }
    else {
      sb.append("unknownInitialState");
    }
    sb.append("\n");
    sb.append("%class " + owningXholonName + "\n");
    sb.append("\n");
    sb.append("%map " + owningXholonName + "\n");
    sb.append("%%\n");
    writeStates();
    sb.append("\n%%\n");
  }
  
  /**
   * Write notes, in XHTML format.
   */
  protected void writeNotes() {
    sb.append("// notes:\n");
  }
  
  /**
   * Write the state machine hierarchy.
   */
  protected void writeStates() {
    if (root.getFirstChild() != null) {
      writeState(root.getFirstChild());
    }
  }
  
  /**
   * Write one state, its transitions, and its child states.
   * @param node The current node in the Xholon composite structure hierarchy.
   */
  protected void writeState(IXholon node) {
    switch(node.getXhcId()) {
    case StateCE:
      // for now, ignore top state (can't handle nested states)
      if (node == topState) {break;};
      if (!(node.getRoleName().equals(""))) {
        sb.append("// State\n");
        sb.append(node.getRoleName() + "\n");
        
        //output entry and exit activities
        int actId = ((IStateMachineEntity)node).getEntryActivityId();
        if (actId != StateMachineEntity.ACTIVITYID_NONE) {
          sb.append("Entry {");
          sb.append("performActivity(" + actId + ", msg);" + "}\n");

        }
        actId = ((IStateMachineEntity)node).getExitActivityId();
        if (actId != StateMachineEntity.ACTIVITYID_NONE) {
          sb.append("Exit {");
          sb.append("performActivity(" + actId + ", msg);" + "}\n");
        }
        
        sb.append("{\n");
        //output transitions from this node
        writeTransitions(node); // transitions  
      }
      break;
    case FinalStateCE:
      sb.append(node.getName() + "\n");
      break;
    case RegionCE:
      break;
    default:
      //if (node.getXhcId() < 1000000) { // a normal Xholon
      //  // should open a new file at this point
      //  //sb.append("%class " + node.getName() + "\n");
      //  return;
      //}
      break;
    }
        
    // children
    if (node.getFirstChild() != null) {
      writeState(node.getFirstChild());
    }
    
    switch(node.getXhcId()) {
    case StateCE:
      // for now, ignore top state (can't handle nested states)
      if (node == topState) {break;};
      if (!(node.getRoleName().equals(""))) {
        sb.append("}\n");
      }
      break;
    case RegionCE:
      break;
    default:
      break;
    }
    
    // siblings
    if (node.getNextSibling() != null) {
      writeState(node.getNextSibling());
    }
  }
  
  /**
   * Write an initial pseudostate element, if the current state has one.
   * @param stateNode The current state node.
   */
  protected void writePseudostateInitial(IXholon stateNode) {
    IXholon initial = xpath.evaluate("Region/PseudostateInitial", stateNode);
    if (initial != null) {
      sb.append("// initial\n");
      writeTransitions(initial);
      sb.append("// end initial\n");
    }
  }
  
  /**
   * Write all transitions that eminate from this state.
   * @param stateNode The current state node.
   */
  protected void writeTransitions(IXholon stateNode) {
    // TODO get actual number of ports to use in for loop
    for (int i = 0; i < StateMachineEntity.getMaxPorts(); i++) {
      IXholon transition = stateNode.getPort(i);
      if (transition != null) {
        writeTransition(transition, stateNode);
      }
    }
  }
  
  /**
   * Write one transition.
   * @param transition The current transition node.
   * @param source The source state from which the transition exits.
   */
  protected void writeTransition(IXholon transition, IXholon source) {
    int event = ((IStateMachineEntity)transition).getTrigger(0);
    // TODO should use constants
    if ((event != -1000) && (event != 0)) {
      sb.append("\t");
      IXholon triggerNode = xpath.evaluate("Trigger", transition);
      if ((triggerNode != null) && (triggerNode.getXhcId() == TriggerCE)) {
        sb.append(triggerNode.getRoleName());
      }
      else {
        sb.append("T" + Integer.toString(event));
      }
      sb.append(" ");
      int guardId = ((IStateMachineEntity)transition).getGuardActivityId();
      if (guardId != StateMachineEntity.ACTIVITYID_NONE) {
        sb.append("[" + "performGuard(" + guardId + ", msg)" + "]\n");
      }
    }
    IXholon target = transition.getPort(0);
    if (target != null) {
      switch (target.getXhcId()) {
      case StateCE:
        if (source.getId()==target.getId()){
          sb.append("nil");
        }
        else{
          sb.append(target.getRoleName());
        }
        break;
      case FinalStateCE:
      case PseudostateTerminateCE:
        sb.append(target.getRoleName());
        break;
      case PseudostateChoiceCE:
        writeChoices(target);
        break;
      case PseudostateEntryPointCE:
      case PseudostateExitPointCE:
        target = target.getPort(0).getPort(0);
        if (target != null) {
          sb.append(target.getRoleName());
        }
        break;
      default:
        break;
      }
    }
    sb.append(" ");
    
    int activityId = ((IStateMachineEntity)transition).getActivityId();
    sb.append("{" + "performActivity(" + activityId + ", msg);" + "}\n"); // actions
  }
  
  /**
   * Write all transitions from a choice node.
   * @param choiceNode A UML choice node.
   */
  protected void writeChoices(IXholon choiceNode) {
    //for (int i = 0; i < StateMachineEntity.getMaxPorts(); i++) {
    int i = 0; // for now, only get first transition from choice
      IXholon transition = choiceNode.getPort(i);
      if (transition != null) {
        writeChoice(transition);
      }
    //}
  }
  
  /**
   * Write one transition from a choice node.
   * @param transition A transition leading out of a choice.
   */
  protected void writeChoice(IXholon transition) {
    IXholon target = transition.getPort(0);
    if (target != null) {
      switch (target.getXhcId()) {
      case StateCE:
        sb.append(target.getRoleName());
        break;
      case FinalStateCE:
      case PseudostateTerminateCE:
        sb.append(target.getRoleName());
        break;
      case PseudostateChoiceCE:
        writeChoices(target);
        break;
      case PseudostateEntryPointCE:
      case PseudostateExitPointCE:
        target = target.getPort(0).getPort(0);
        if (target != null) {
          sb.append(target.getRoleName());
        }
        break;
      default:
        break;
      }
    }
  }
}
