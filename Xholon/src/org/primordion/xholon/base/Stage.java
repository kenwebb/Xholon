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

package org.primordion.xholon.base;

/**
 * Stage as understood in Scratch and Snap.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://scratch.mit.edu">Scratch website</a>
 * @see <a href="http://snap.berkeley.edu">Snap website</a>
 * @since 0.9.1 (Created on January 29, 2016)
 */
public class Stage extends AbstractScratchNode {
  
  /**
   * The Stage object created in phosphorus.
   */
  protected Object phosStage = null;
  
  /**
   * These blocks are specific to Sprites.
   */
  protected String[] stageBlockNameArr = {
  // naiveName, scratchName, snapName, EnglishSentenceOrPhrase
  // Motion none
  // Looks  the Snap names are probably the same as the costume names
  "switchbackdroptoandwait", "startSceneAndWait", "doSwitchToCostume", "Switch backdrop to _ and wait.",
  "nextbackdrop", "nextScene", "doWearNextCostume", "Next backdrop.",
  "backdrop#", "UNKNOWN", "getCostumeIdx", "backdrop #",
  // Sound same
  // Pen nothing Stage-specific
  // Data same
  // Events
  "whenStageclicked", "whenClicked", "receiveInteraction", "When Stage clicked:"
  // Control
  // Sensing missing some
  // Operators same
  // More Blocks same
  // Xholon same
  };
  
  /**
   * Constructor.
   */
  public Stage() {}
  
  /**
   * Make the Sprite- or Stage-specific array of block names.
   * Combine spriteBlockNameArr and blockNameArr ito one array.
   */
  protected String[] makeBlockNameArr() {
    int len = blockNameArr.length + stageBlockNameArr.length;
    String[] arr = new String[len];
    int arrIx = 0;
    int i;
    for (i = 0; i < blockNameArr.length; i++) {
      arr[arrIx++] = blockNameArr[i];
    }
    for (i = 0; i < stageBlockNameArr.length; i++) {
      arr[arrIx++] = stageBlockNameArr[i];
    }
    return arr;
  }
  
  @Override
  public void act() {
    if (phosStage == null) {
      phosStage = this.findPhosStage();
    }
    if (phosStage != null) {
      controlPhosStage(phosStage);
    }
    super.act();
  }
  
  protected native Object findPhosStage() /*-{
    return $wnd.stage;
  }-*/;
  
  /**
   * Toggle the phosphorus Stage between paused and running.
   */
  protected native void controlPhosStage(Object stage) /*-{
    if (stage.isRunning) {
      stage.pause();
    } else {
      stage.start();
    }
  }-*/;
  
}

