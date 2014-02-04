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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.dom.client.Element;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IMessage;
//import org.primordion.xholon.io.ISwingEntity;
import org.primordion.xholon.io.xml.IXml2Xholon;

/**
 * Xholon MeTTTa
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on May 10, 2008)
*/
public class MeTTTaController extends XhMeTTTa implements IMeTTTaConstants {

	// ports
	private MeTTTaModel model = null;
	private IXholon worker = null;
	
	@Override
	public void postConfigure() {
    MeTTTaView view = (MeTTTaView)model.getView();
    
    Button[] button = view.getButton();
    for (int i = 0; i < button.length; i++) {
	    button[i].addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          handleClick(event);
        }
      });
	  }

    Button newgame = view.getNewgame();
	  newgame.addClickHandler(new ClickHandler() {
	    @Override
      public void onClick(ClickEvent event) {
        handleClick(event);
      }
    });
	
	  Button computerfirst = view.getComputerfirst();
	  computerfirst.addClickHandler(new ClickHandler() {
	    @Override
      public void onClick(ClickEvent event) {
        handleClick(event);
      }
    });
    super.postConfigure();
	}
	
	@Override
	public void processReceivedMessage(IMessage msg)
	{
		switch (msg.getSignal()) {
		case SIG_SET_COMPUTER_MOVE:
			if (model.getGameState() == TURN_COMPUTER) {
				int position = ((Integer)msg.getData()).intValue();
				// send an unnecessary message so it will show up in sequence diagrams
				model.sendMessage(SIG_SET_COMPUTER_MOVE, new Integer(position), this);
				model.setPlayer(position, G_COMPUTER);
				model.setGameState(TURN_HUMAN);
			}
			break;
		default:
			logger.warn("Controller received an unexpected message:" + msg);
			break;
		}
	}
	
	/**
	 * Handle button click events.
	 */
	protected void handleClick(ClickEvent e) {
    Element ele = Element.as(e.getNativeEvent().getEventTarget());
    String command = ele.getId();
    System.out.println(command);
    if ("newgame".equals(command)) {
			model.clearGrid();
			model.setGameState(TURN_HUMAN);
		}
		else if ("computerfirst".equals(command)) {
			model.setGameState(TURN_COMPUTER);
			worker.sendMessage(SIG_GET_COMPUTER_MOVE, model.getGrid(), this);
		}
		else if (command.charAt(0) == '<') {
			// This is a script formatted as XML.
			System.out.println("script: " + command);
			IXml2Xholon xml2Xholon = getXml2Xholon();
			IXholon myRootXhNode = xml2Xholon.xmlString2Xholon(command, null);
			if (myRootXhNode == null) {return;}
			//System.out.println(myRootXhNode);
			myRootXhNode.appendChild(this);
			myRootXhNode.configure();
			myRootXhNode.postConfigure();
		}
		else if ((model.getGameState() == TURN_HUMAN) && (command.length() > 0)) {
			// transform last character in the string into an int
			int position = command.charAt(command.length()-1) - '0';
			if (!model.isPlayed(position)) {
				// send an unnecessary message so it will show up in sequence diagrams
				model.sendMessage(SIG_HUMAN_SELECTION, new Integer(position), this);
				model.setPlayer(position, G_HUMAN);
				if (!model.isGameOver()) {
					worker.sendMessage(SIG_GET_COMPUTER_MOVE, model.getGrid(), this);
					model.setGameState(TURN_COMPUTER);
				}
			}
		}
  }

	public MeTTTaModel getModel() {
		return model;
	}

	public void setModel(MeTTTaModel model) {
		this.model = model;
	}

	public IXholon getWorker() {
		return worker;
	}

	public void setWorker(IXholon worker) {
		this.worker = worker;
	}
}
