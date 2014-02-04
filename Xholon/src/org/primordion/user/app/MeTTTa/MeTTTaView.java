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

import com.google.gwt.core.client.GWT;
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
//import com.google.gwt.uibinder.client.UiHandler;
//import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
//import com.google.gwt.dom.client.Element;

//import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IMessage;
//import org.primordion.xholon.base.XholonWithPorts;
//import org.primordion.xholon.common.viewer.CeSwingEntity;
//import org.primordion.xholon.io.ISwingEntity;

/**
 * Xholon MeTTTa
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on May 10, 2008)
*/
public class MeTTTaView extends XhMeTTTa implements IMeTTTaConstants {
	
	/** Buttons that are used to select a position, and to display human and computer selections. */
	private Button[] button;
	
	// X's and O's, to be displayed in the GUI.
	protected static final String PLAYER_NULL     = " ";
	protected static final String PLAYER_HUMAN    = "O";
	protected static final String PLAYER_COMPUTER = "X";

	interface MyUiBinder extends UiBinder<Widget, MeTTTaView> {}
  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
  
  @UiField Button select0;
  @UiField Button select1;
  @UiField Button select2;
  
  @UiField Button select3;
  @UiField Button select4;
  @UiField Button select5;
  
  @UiField Button select6;
  @UiField Button select7;
  @UiField Button select8;
  
  @UiField Button computerfirst;
  
  @UiField Button newgame;
	
	@Override
	public void postConfigure() {
		
		button = new Button[9];
		
		Widget widget = uiBinder.createAndBindUi(this);
		//GWT.log(widget.toString());
		RootPanel.get("xhappspecific").add(widget);
		
		button[0] = select0;
		button[1] = select1;
		button[2] = select2;
		button[3] = select3;
		button[4] = select4;
		button[5] = select5;
		button[6] = select6;
		button[7] = select7;
		button[8] = select8;
		
		for (int i = 0; i < button.length; i++) {
		  button[i].getElement().setId("select" + i);
		}
		newgame.getElement().setId("newgame");
    computerfirst.getElement().setId("computerfirst");
    
		super.postConfigure();
	}
	
	@Override
	public void processReceivedMessage(IMessage msg)
	{
		switch(xhc.getId()) {
		case MeTTTaViewCE:
			switch (msg.getSignal()) {
			case SIG_MODEL_UPDATED:
				MeTTTaModel model = (MeTTTaModel)msg.getSender();
				String playerStr = "?";
				boolean enabled = true;
				boolean gameOver = model.isGameOver();
				// update all JButtons
				for (int position = 0; position < 9; position++) {
					int player = model.getPlayer(position);
					switch (player) {
					case G_NULL:
						playerStr = PLAYER_NULL;
						if (gameOver) {
							enabled = false;
						}
						else {
							enabled = true;
						}
						break;
					case G_HUMAN:
						playerStr = PLAYER_HUMAN;
						enabled = false;
						break;
					case G_COMPUTER:
						playerStr = PLAYER_COMPUTER;
						enabled = false;
						break;
					default:
						break;
					}
					button[position].setText(playerStr);
					button[position].setEnabled(enabled);
					if (model.getNumPositionsPlayed() == 0) {
						computerfirst.setEnabled(true); //sendMessage(ISwingEntity.SWIG_SET_ENABLED_REQUEST, new Boolean(true), this);
					}
					else if (model.getNumPositionsPlayed() == 1){
						computerfirst.setEnabled(false); //sendMessage(ISwingEntity.SWIG_SET_ENABLED_REQUEST, new Boolean(false), this);
					}
				}
				break;
			default:
				logger.warn("View received an unexpected message:" + msg);
			break;
			}
			break;
		default:
			break;
		}
	}

	public Button[] getButton() {
		return button;
	}

	public void setButton(Button[] button) {
		this.button = button;
	}

	public Button getComputerfirst() {
		return computerfirst;
	}

	public void setComputerfirst(Button computerfirst) {
		this.computerfirst = computerfirst;
	}
	
	public Button getNewgame() {
		return newgame;
	}

	public void setNewgame(Button newgame) {
		this.newgame = newgame;
	}
	
}
