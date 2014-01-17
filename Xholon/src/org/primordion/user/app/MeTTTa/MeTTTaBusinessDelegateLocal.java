/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2008 Ken Webb
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

package org.primordion.user.app.MeTTTa;

import java.util.List;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.Message;

/**
 * Xholon MeTTTa
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on May 10, 2008)
*/
public class MeTTTaBusinessDelegateLocal extends XhMeTTTa implements IMeTTTaPojo {

	/** A POJO that contains the basic move logic. */
	private MeTTTaPojo meTTTaPojo = new MeTTTaPojo();
	
	@Override
	public IMessage processReceivedSyncMessage(IMessage msg)
	{
		int move = getCalculatedMove((List)msg.getData());
		return new Message(SIG_SET_COMPUTER_MOVE, new Integer(move), this, msg.getSender());
	}
	
	@Override
	public int getCalculatedMove(List grid) {
		return meTTTaPojo.getCalculatedMove(grid);
	}
}
