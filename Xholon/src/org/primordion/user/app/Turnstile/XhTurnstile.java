/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2014 Ken Webb
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

package org.primordion.user.app.Turnstile;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.service.svg.SvgClient;

/**
 * Turnstile FSM.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on March 5, 2014)
 */
public class XhTurnstile extends XholonWithPorts implements CeTurnstile {
  
  public static final String colorActiveState = "#f5deb3";
  public static final String colorInactiveState = "#f4e3d7";
  
  // references to other Xholon instance; indices into the port array
  public static final int P_PARTNER = 0;
  
  // Signals, Events
  // signals for interface used between P_PARTNER ports
  public static final int coin1 = 100; // no data
  public static final int push2 = 200; // no data
  
  public static boolean testing = false;
  
  public String roleName = null;
    
  /**
   * Constructor.
   */
  public XhTurnstile() {}
  
  /*
   * @see org.primordion.xholon.base.IXholon#initialize()
   */
  public void initialize()
  {
    super.initialize();
    roleName = null;
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
   * @see org.primordion.xholon.base.IXholon#act()
   */
  public void act()
  {
    switch(xhc.getId()) {
    case TurnstileSystemCE:
      // Only process the messages that are currently in the Q.
      // Don't process messages added to Q during this timestep.
      processMessageQ();
      break;
    
    case PersonCE:
      // this optionally tests all possible state transitions, each time step
      if (testing) {
        port[P_PARTNER].sendMessage(push2, null, this);
        port[P_PARTNER].sendMessage(coin1, null, this);
        port[P_PARTNER].sendMessage(coin1, null, this);
        port[P_PARTNER].sendMessage(push2, null, this);
      }
      break;
    
    default:
      break;
    }

    super.act();
  }
  
  /*
   * @see org.primordion.xholon.base.IXholon#processReceivedMessage(org.primordion.xholon.base.Message)
   */
  public void processReceivedMessage(IMessage msg)
  {
    switch(xhc.getId()) {
    
    // This is the FSM being tested.
    case TurnstileCE:
      println(msg.toString());
      if (this.hasChildNodes()) {
        ((StateMachineEntity)getFirstChild()).doStateMachine(msg);
      }
      break;
    
    default:
      println("XhTurnstile: message with no receiver " + msg);
      break;
    }
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#performActivity(int, org.primordion.xholon.base.IMessage)
   */
  public void performActivity(int activityId, IMessage msg)
  {
    switch (activityId) {
    case 1: // init
      println("init transition");
      break;
    case 2: // Locked
      println("transitioning from Locked to Unlocked");
      ((SvgClient)getApp().getView().getFirstChild()).style("path2985,fill," + colorInactiveState);
      ((SvgClient)getApp().getView().getFirstChild()).style("path3755,fill," + colorActiveState);
      break;
    case 3: // Unlocked
      println("transitioning from Unlocked to Locked");
      ((SvgClient)getApp().getView().getFirstChild()).style("path3755,fill," + colorInactiveState);
      ((SvgClient)getApp().getView().getFirstChild()).style("path2985,fill," + colorActiveState);
      break;
    case 4: // Locked
      println("Locked self transition - push has no effect");
      break;
    case 5: // Unlocked
      println("Unlocked self transition - coin is returned");
      break;
    default:
      println("XhTurnstile: performActivity() unknown Activity " + activityId);
      break;
    }
  }

  /*
   * @see org.primordion.xholon.base.Xholon#performGuard(int, org.primordion.xholon.base.IMessage)
   */
  public boolean performGuard(int activityId, IMessage msg)
  {
    switch (activityId) {
    default:
      println("XhTurnstile: performGuard() unknown Activity " + activityId);
      return false;
    }
  }
  
  public Object handleNodeSelection()
  {
    switch(xhc.getId()) {
    case DepositCoinEventCE:
      this.getParentNode().getPort(P_PARTNER).sendMessage(coin1, null, this);
      break;
    case PushThroughEventCE:
      this.getParentNode().getPort(P_PARTNER).sendMessage(push2, null, this);
      break;
    default:
      return toString();
    }
    return "The " + this.getXhcName() + " button has been selected.";
  }
  
  /*
   * @see java.lang.Object#toString()
   */
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
    return outStr;
  }
}
