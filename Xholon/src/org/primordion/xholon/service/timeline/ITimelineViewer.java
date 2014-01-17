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

package org.primordion.xholon.service.timeline;

import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;

/**
 * <p>This interface should be implemented by concrete classes that capture data
 * and display the data in a timeline.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on January 5, 2014)
 */
public interface ITimelineViewer extends IXholon {
  
  /**
	 * Initialize. -3898
	 */
	public static final int SIG_INITIALIZE_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 101;
	
  /**
	 * Set parameters (there are no parameters yet). -3897
	 */
	public static final int SIG_PARAMETERS_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 102;
	
  /**
	 * Capture timeline data for one event. -3896
	 */
	public static final int SIG_CAPTURE_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 103;
	
  /**
	 * Draw the timeline on an output device. -3895
	 */
	public static final int SIG_DRAW_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 104;
	
	/**
	 * Process response. -3798
	 */
	public static final int SIG_PROCESS_RSP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 201;
	
  public abstract void initialize(String outFileName, String modelName, IXholon root);
  
  public abstract void capture(Object dateTimeObj, String content);
  
  public abstract void drawTimeline();
  
}
