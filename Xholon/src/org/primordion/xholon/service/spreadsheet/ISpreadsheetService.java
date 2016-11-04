/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2016 Ken Webb
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

package org.primordion.xholon.service.spreadsheet;

import org.primordion.xholon.base.ISignal;

/**
 * SpreadsheetService interface, including types of messages.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on November 3, 2016)
 */
public interface ISpreadsheetService {
  
  // REQUESTS
  // --------
  
  /**
   * Get a formula parser.
   */
  public static final int SIG_GET_FORMULA_PARSER_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 101; // -3898
    
  // RESPONSES
  // ---------
  
  /**
   * A generic response to a request to do something.
   */
  public static final int SIG_SPREADSHEET_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 202; // -3797
  
}
