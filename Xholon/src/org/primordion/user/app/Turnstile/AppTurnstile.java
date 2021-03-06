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

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;

/**
 * Turnstile FSM.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on March 5, 2014)
 */
public class AppTurnstile extends Application {

  /** Constructor. */
  public AppTurnstile() {super();}
  
  /*
   * @see org.primordion.xholon.app.Application#step()
   */
  protected void step()
  {
    if (getUseDataPlotter()) {
      chartViewer.capture(timeStep);
    }
    root.act();
  }
  
  /*
   * @see org.primordion.xholon.app.IApplication#wrapup()
   */
  public void wrapup()
  {
    if (getUseDataPlotter()) {
      chartViewer.capture(timeStep);
    }
    super.wrapup();
  }
  
  /*
   * @see org.primordion.xholon.app.Application#shouldBePlotted(org.primordion.xholon.base.IXholon)
   */
  protected boolean shouldBePlotted(IXholon modelNode)
  {
    if ((modelNode.getXhcId() == CeStateMachineEntity.StateCE)
        || (modelNode.getXhcId() == CeStateMachineEntity.FinalStateCE)
        || (modelNode.getXhcId() == CeStateMachineEntity.StateMachineCE)) {
      return true;
    }
    else {
      return false;
    }
  }
  
  /*
	 * @see org.primordion.xholon.app.IApplication#findGwtClientBundle()
	 */
	public Object findGwtClientBundle() {
	  return Resources.INSTANCE;
	}
	
  public static void main(String[] args) {
  
  }
  
}
