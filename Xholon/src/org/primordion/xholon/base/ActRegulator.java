/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2015 Ken Webb
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

/**
 * Regulates the act() method for other nodes.
 * Multiple instances of this class could be used inline, to handle multiple situations.
 * Typically it prevents child and/or sibling nodes from running their act().
 * TODO could I handle DT integration by calling act() a specified number of times?
 *
<ActRegulator val="1"/>
 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on February 24, 2015)
 */
public class ActRegulator extends Xholon {
  
  // User selectable reasons for regulating
  
  /** Prevent children and siblings from running their act(). */
  public static final int PREVENT_ALL = 1;
  
  /** Prevent children from running their act(). */
  public static final int PREVENT_CHILDREN = 2;
  
  /** Prevent siblings from running their act(). */
  public static final int PREVENT_SIBLINGS = 3;
  
  /** Prevent if the JavaScript SpeechSynthesis interface is speaking or pending, or idle. */
  public static final int SPEECH_ACTIVE = 4;
  
  private boolean speechSupported = false;
  
  private int val = 0;
  
  @Override
  public void setVal(double val) {this.val = (int)val;}
  
  @Override
	public double getVal() {return val;}
	
  @Override
  public void postConfigure() {
		speechSupported = isSpeechSupported();
		super.postConfigure();
	}
	
	@Override
	public void act() {
		switch (val) {
		case PREVENT_ALL:
		  return;
		case SPEECH_ACTIVE:
		  if (speechSupported && speechActive()) {
		    // prevent child and sibling nodes from running their act() methods
		    return;
		  }
		  break;
		default:
		  break;
		}
		super.act();
	}
	
	/**
   * Determine whether or not speech synthesis is supported for this browser.
   * @return true or false
   */
  protected native boolean isSpeechSupported() /*-{
    return typeof $wnd.SpeechSynthesisUtterance != "undefined";
  }-*/;
  
  /**
   * Determine whether there is currently unuttered speech.
   * return true or false
   */
	protected native boolean speechActive() /*-{
	  var sp = $wnd.speechSynthesis;
    if (sp.speaking || sp.pending) {
      return true;
    }
	  return false;
	}-*/;
	
}
