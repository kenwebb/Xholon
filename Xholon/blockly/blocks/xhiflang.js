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
    this.setHelpUrl('http://www.example.com/');
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
    this.setHelpUrl('http://www.example.com/');
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
    this.setHelpUrl('http://www.example.com/');
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
    this.setHelpUrl('http://www.example.com/');
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
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("appear");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

/**
 * https://blockly-demo.appspot.com/static/demos/blockfactory/index.html#kh7fd7
 */
Blockly.Blocks['xhiflang_become'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("become")
        .appendField(new Blockly.FieldTextInput(""), "THING")
        .appendField("role")
        .appendField(new Blockly.FieldTextInput(""), "ROLE");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
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
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendValueInput("ROLE")
        .setCheck("String")
        .appendField(new Blockly.FieldDropdown([["build", "BUILD"], ["append", "APPEND"], ["prepend", "PREPEND"], ["before", "BEFORE"], ["after", "AFTER"]]), "COMMAND")
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_comment'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("comment")
        .appendField(new Blockly.FieldTextInput(""), "COMMENT");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_drop'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("drop")
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_enter'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("enter")
        .appendField(new Blockly.FieldTextInput(""), "PLACE");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_examine'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("examine")
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_exit'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("exit")
        .appendField(new Blockly.FieldTextInput(""), "PLACE"); // optional
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_follow'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("follow")
        .appendField(new Blockly.FieldTextInput(""), "LEADER");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_go'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("go")
        .appendField(new Blockly.FieldTextInput(""), "PLACE");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_group'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("group")
        .appendField(new Blockly.FieldTextInput(""), "THING1")
        .appendField(new Blockly.FieldDropdown([["in", "OPTIONIN"], ["on", "OPTIONON"], ["under", "OPTIONUNDER"]]), "WHERE")
        .appendField(new Blockly.FieldTextInput(""), "THING2");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_inventory'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("inventory");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_lead'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("lead")
        .appendField(new Blockly.FieldTextInput(""), "FOLLOWER");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_look'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("look");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_next'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("next")
        .appendField(new Blockly.FieldTextInput(""), "TARGET"); // optional
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

/**
 * https://blockly-demo.appspot.com/static/demos/blockfactory/index.html#vbcy5n
 */
Blockly.Blocks['xhiflang_out'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("out")
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
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_param'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("param")
        .appendField(new Blockly.FieldDropdown([["speech", "SPEECH"], ["transcript", "TRANSCRIPT"], ["debug", "DEBUG"], ["loop", "LOOP"], ["repeat", "REPEAT"], ["meteor", "METEOR"], ["meteormove", "METEORMOVE"], ["meteor+move", "METEOR+MOVE"]]), "NAME")
        .appendField(new Blockly.FieldCheckbox("TRUE"), "VALUE");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_prev'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("prev")
        .appendField(new Blockly.FieldTextInput(""), "TARGET"); // optional
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_put'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("put")
        .appendField(new Blockly.FieldTextInput(""), "THING1")
        .appendField(new Blockly.FieldDropdown([["in", "OPTIONIN"], ["on", "OPTIONON"], ["under", "OPTIONUNDER"]]), "WHERE")
        .appendField(new Blockly.FieldTextInput(""), "THING2");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_smash'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("smash")
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

/**
 * https://blockly-demo.appspot.com/static/demos/blockfactory/index.html#ub57qx
 */
Blockly.Blocks['xhiflang_take'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("take")
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

// or eat
Blockly.Blocks['xhiflang_unbuild'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField('unbuild')
        .appendField(new Blockly.FieldTextInput(""), "THING");
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_unfollow'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("unfollow");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_unlead'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("unlead");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_vanish'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendDummyInput()
        .appendField("vanish");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

Blockly.Blocks['xhiflang_wait'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhiflang.HUE);
    this.appendValueInput("TIMESTEPS")
        .setCheck("Number")
        .appendField("wait");
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('');
  }
};

