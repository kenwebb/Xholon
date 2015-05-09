/**
 * @license
 * Visual Blocks Editor
 * Xholon Interactive Fiction (IF) language
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

goog.provide('Blockly.Blocks.xhiflang');

goog.require('Blockly.Blocks');

Blockly.Blocks.xhiflang.HUE = 345;

/**
 * Avatar node.
 * https://blockly-demo.appspot.com/static/demos/blockfactory/index.html#gxuutf
 */
/*Blockly.Blocks['xhiflang_avatar'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(120);
    this.appendValueInput("AVATAR")
        .setCheck("ROLE")
        .appendField("Avatar");
    this.appendStatementInput("BEHAVIOR");
    this.setTooltip('Create a new Avatar, with optional roleName and optional behavior.');
  }
};*/

/**
 * An Avatar's behavior.
 * https://blockly-demo.appspot.com/static/demos/blockfactory/index.html#mfcig5
 */
/*Blockly.Blocks['xhiflang_behavior'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(260);
    this.appendDummyInput()
        .appendField("behavior");
    this.appendStatementInput("NAME");
    this.setPreviousStatement(true);
    this.setTooltip('');
  }
};*/

/**
 * https://blockly-demo.appspot.com/static/demos/blockfactory/index.html#epurp2
 */
/*Blockly.Blocks['xhiflang_role'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(345);
    this.appendValueInput("ROLE")
        .setCheck("String")
        .appendField("role");
    this.setInputsInline(true);
    this.setOutput(true);
    this.setTooltip('');
  }
};*/
Blockly.Blocks['xhiflang_role'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("role")
        .appendField(new Blockly.FieldTextInput(""), "ROLE");
    this.setInputsInline(true);
    this.setOutput(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_appear'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_APPEAR_TITLE);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_APPEAR_TOOLTIP);
  }
};

/**
 * https://blockly-demo.appspot.com/static/demos/blockfactory/index.html#kh7fd7
 */
Blockly.Blocks['xhiflang_become'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_BECOME_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING")
        .appendField("role")
        .appendField(new Blockly.FieldTextInput(""), "ROLE");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_BECOME_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_breakpoint'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_BREAKPOINT_TITLE);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_BREAKPOINT_TOOLTIP);
  }
};

/**
 * Build command.
 * @this Blockly.Block
 * from Block Factory
 * https://blockly-demo.appspot.com/static/demos/blockfactory/index.html#4c2jab
 */
/*Blockly.Blocks['xhiflang_build'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_BUILD_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendValueInput("BUILD")
        .setCheck("String")
        .appendField(Blockly.Msg.XHIFLANG_BUILD_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_BUILD_TOOLTIP);
  }
};*/
/**
 * https://blockly-demo.appspot.com/static/demos/blockfactory/index.html#wwg9wo
 */
Blockly.Blocks['xhiflang_build'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendValueInput("ROLE")
        .setCheck("String")
        .appendField(new Blockly.FieldDropdown([["build", "BUILD"], ["append", "APPEND"], ["prepend", "PREPEND"], ["before", "BEFORE"], ["after", "AFTER"]]), "COMMAND")
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_BUILD_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_comment'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_COMMENT_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "COMMENT");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_COMMENT_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_drop'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_DROP_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_DROP_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_enter'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_ENTER_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_ENTER_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_examine'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(new Blockly.FieldDropdown([["examine", "EXAMINE"], ["x", "X"]]), "COMMAND")
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_EXAMINE_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_exit'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_EXIT_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING"); // optional
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_EXIT_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_follow'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_FOLLOW_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "LEADER");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_FOLLOW_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_get'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_GET_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING")
        .appendField(new Blockly.FieldTextInput(""), "NAME");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_GET_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_go'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_GO_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_GO_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_group'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_GROUP_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING1")
        .appendField(new Blockly.FieldDropdown([["in", "IN"], ["on", "ON"], ["under", "UNDER"]]), "WHERE")
        .appendField(new Blockly.FieldTextInput(""), "THING2");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_GROUP_TOOLTIP);
  }
};

// if ifeq etc. TODO

Blockly.Blocks['xhiflang_inventory'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(new Blockly.FieldDropdown([["inventory", "INVENTORY"], ["i", "I"]]), "COMMAND");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_INVENTORY_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_lead'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_LEAD_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "FOLLOWER");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_LEAD_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_look'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_LOOK_TITLE);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_LOOK_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_next'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_NEXT_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING"); // optional
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_NEXT_TOOLTIP);
  }
};

/**
 * https://blockly-demo.appspot.com/static/demos/blockfactory/index.html#vbcy5n
 */
Blockly.Blocks['xhiflang_out'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_OUT_TITLE)
        .appendField("speech")
        .appendField(new Blockly.FieldCheckbox("FALSE"), "SPEECH")
        .appendField("caption")
        .appendField(new Blockly.FieldCheckbox("FALSE"), "CAPTION")
        .appendField("transcript")
        .appendField(new Blockly.FieldCheckbox("FALSE"), "TRANSCRIPT")
        .appendField("debug")
        .appendField(new Blockly.FieldCheckbox("FALSE"), "DEBUG");
    this.appendDummyInput()
        .appendField(new Blockly.FieldTextInput(""), "TEXT");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_OUT_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_param'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_PARAM_TITLE)
        .appendField(new Blockly.FieldDropdown([["speech", "SPEECH"], ["transcript", "TRANSCRIPT"], ["debug", "DEBUG"], ["loop", "LOOP"], ["repeat", "REPEAT"], ["meteor", "METEOR"], ["meteormove", "METEORMOVE"], ["meteor+move", "METEOR+MOVE"]]), "NAME")
        .appendField(new Blockly.FieldCheckbox("TRUE"), "VALUE");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_PARAM_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_paramcaption'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_PARAM_TITLE)
        .appendField("caption")
        .appendField(new Blockly.FieldTextInput(""), "SELECTION");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_paramspeech'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_PARAM_TITLE)
        .appendField("speech")
        .appendField(new Blockly.FieldTextInput(""), "OBJECT");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_prev'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_PREV_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING"); // optional
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_PREV_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_put'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_PUT_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING1")
        .appendField(new Blockly.FieldDropdown([["in", "IN"], ["on", "ON"], ["under", "UNDER"]]), "WHERE")
        .appendField(new Blockly.FieldTextInput(""), "THING2");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_PUT_TOOLTIP);
  }
};

// xhiflang_search  TODO ?

Blockly.Blocks['xhiflang_set'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_SET_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING")
        .appendField(new Blockly.FieldTextInput(""), "NAME")
        .appendField(new Blockly.FieldTextInput(""), "VALUE");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_SET_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_smash'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_SMASH_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_SMASH_TOOLTIP);
  }
};

/**
 * https://blockly-demo.appspot.com/static/demos/blockfactory/index.html#ub57qx
 */
Blockly.Blocks['xhiflang_take'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_TAKE_TITLE)
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_TAKE_TOOLTIP);
  }
};

// or eat
Blockly.Blocks['xhiflang_unbuild'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(new Blockly.FieldDropdown([["unbuild", "UNBUILD"], ["eat", "EAT"]]), "COMMAND")
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_UNBUILD_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_unfollow'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_UNFOLLOW_TITLE);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_UNFOLLOW_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_unlead'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_UNLEAD_TITLE);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_UNLEAD_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_vanish'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField(Blockly.Msg.XHIFLANG_VANISH_TITLE);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_VANISH_TOOLTIP);
  }
};

Blockly.Blocks['xhiflang_wait'] = {
  init: function() {
    this.setHelpUrl(Blockly.Msg.XHIFLANG_HELPURL);
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendValueInput("DURATION")
        .setCheck("Number")
        .appendField(Blockly.Msg.XHIFLANG_WAIT_TITLE);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip(Blockly.Msg.XHIFLANG_WAIT_TOOLTIP);
  }
};

