/**
 * @license
 * Visual Blocks Editor
 * Xholon Interactive Fiction (IF) language for Kindergarten+ (ages 3-7)
 *
 * Copyright 2012 Google Inc. (original Blockly content)
 * Copyright 2015 Ken Webb  (Xholon-specific content)
 * https://developers.google.com/blockly/
 * http://www.primordion.com/Xholon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @fileoverview Text blocks for Blockly.
 * @author fraser@google.com (Neil Fraser)
 * @author ken@primordion.com (Ken Webb)
 */
'use strict';

goog.provide('Blockly.Blocks.xhkinder');

goog.require('Blockly.Blocks');

Blockly.Blocks.xhkinder.HUE = 345;

// anim THING turnright|turnleft|hop|duck|grow|shrink|mirror PARAMS
/*Blockly.Blocks['xhkinder_anim'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhkinder.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_ANIM_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING")
        .appendField(new Blockly.FieldDropdown([["turnright", "TURNRIGHT"], ["turnleft", "TURNLEFT"], ["hop", "HOP"], ["duck", "DUCK"], ["grow", "GROW"], ["shrink", "SHRINK"], ["mirror", "MIRROR"]]), "WHICH")
        .appendField(new Blockly.FieldTextInput(""), "PARAMS");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_ANIM_TOOLTIP);
  }
};*/

Blockly.Blocks['xhkinder_turnright'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(180);
    this.appendDummyInput()
        .appendField("\u21B7") // "R"
        .appendField(new Blockly.FieldDropdown([["_", ""], ["1", "1"], ["2", "2"], ["3", "3"], ["4", "4"], ["5", "5"], ["6", "6"], ["7", "7"], ["8", "8"], ["9", "9"]]), "WHICH");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_ANIM_TOOLTIP);
  }
};

Blockly.Blocks['xhkinder_turnleft'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(185);
    this.appendDummyInput()
        .appendField("\u21B6") // "L"
        .appendField(new Blockly.FieldDropdown([["_", ""], ["1", "1"], ["2", "2"], ["3", "3"], ["4", "4"], ["5", "5"], ["6", "6"], ["7", "7"], ["8", "8"], ["9", "9"]]), "WHICH");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_ANIM_TOOLTIP);
  }
};

Blockly.Blocks['xhkinder_hop'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(220);
    this.appendDummyInput()
        .appendField("\u21B1"); // "H"
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_ANIM_TOOLTIP);
  }
};

Blockly.Blocks['xhkinder_duck'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(225);
    this.appendDummyInput()
        .appendField("\u21B2"); // "D"
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_ANIM_TOOLTIP);
  }
};

Blockly.Blocks['xhkinder_grow'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(260);
    this.appendDummyInput()
        .appendField("\u21F1"); // "G"
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_ANIM_TOOLTIP);
  }
};

Blockly.Blocks['xhkinder_shrink'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(265);
    this.appendDummyInput()
        .appendField("\u21F2"); // "S"
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_ANIM_TOOLTIP);
  }
};

Blockly.Blocks['xhkinder_mirror'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(300);
    this.appendDummyInput()
        .appendField("\u21D4"); // "M"
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_ANIM_TOOLTIP);
  }
};

/*Blockly.Blocks['xhkinder_appear'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhkinder.HUE);
    this.appendDummyInput()
        .appendField("A"); //Blockly.Msg.XHIFLANG_APPEAR_TITLE);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_APPEAR_TOOLTIP);
  }
};*/

Blockly.Blocks['xhkinder_enter'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(80);
    this.appendDummyInput()
        .appendField("\u21E3"); // "T" Blockly.Msg.XHIFLANG_ENTER_TITLE);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_ENTER_TOOLTIP);
  }
};

Blockly.Blocks['xhkinder_exit'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(85);
    this.appendDummyInput()
        .appendField("\u21E1"); // "X" Blockly.Msg.XHIFLANG_EXIT_TITLE);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_EXIT_TOOLTIP);
  }
};

/*Blockly.Blocks['xhkinder_inventory'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhkinder.HUE);
    this.appendDummyInput()
        .appendField(new Blockly.FieldDropdown([["inventory", "INVENTORY"], ["i", "I"]]), "COMMAND");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_INVENTORY_TOOLTIP);
  }
};*/

/*Blockly.Blocks['xhkinder_look'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhkinder.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_LOOK_TITLE);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_LOOK_TOOLTIP);
  }
};*/

Blockly.Blocks['xhkinder_next'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(90);
    this.appendDummyInput()
        .appendField("\u21E2"); // "N" Blockly.Msg.XHIFLANG_NEXT_TITLE)
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_NEXT_TOOLTIP);
  }
};

Blockly.Blocks['xhkinder_prev'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(95);
    this.appendDummyInput()
        .appendField("\u21E0"); // "P" Blockly.Msg.XHIFLANG_PREV_TITLE)
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_PREV_TOOLTIP);
  }
};

/*Blockly.Blocks['xhkinder_wait'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhkinder.HUE);
    this.appendValueInput("DURATION")
        .setCheck("Number")
        .appendField(Blockly.Msg.XHIFLANG_WAIT_TITLE);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_WAIT_TOOLTIP);
  }
};*/
