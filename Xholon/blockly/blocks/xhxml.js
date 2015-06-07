/**
 * @license
 * Visual Blocks Editor
 * Xholon XML (in support of Xholon IF language)
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

goog.provide('Blockly.Blocks.xhxml');

goog.require('Blockly.Blocks');

Blockly.Blocks.xhxml.HUE = 120;

/**
 * Avatar node.
 * https://blockly-demo.appspot.com/static/demos/blockfactory/index.html#gxuutf
 */
Blockly.Blocks['xhxml_avatar'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhxml.HUE);
    this.appendValueInput("AVATAR")
        .setCheck("ROLE")
        .appendField("Avatar")
        .appendField(new Blockly.FieldTextInput(""), "START")
        .appendField(new Blockly.FieldDropdown([["append", "APPEND"], ["script", "SCRIPT"]]), "STARTPOS")
        .appendField(new Blockly.FieldTextInput("vanish"), "END");
    this.appendStatementInput("BEHAVIOR");
    // <Avatar roleName="Harry" start="World" startPos="append">
    // <Avatar start="Harry" startPos="script">
    this.setTooltip('Create a new Avatar, with optional roleName and optional behavior. Edit the values for start, startPos, and end. "append" creates a new Avatar as a last child of start (ex: <Avatar start="School" startPos="append" roleName="Harry">). "script" replaces the script inside the existing Avatar specified by start (ex: <Avatar start="Harry" startPos="script" end="vanish">).');
  }
};

/**
 * An Avatar's behavior.
 * https://blockly-demo.appspot.com/static/demos/blockfactory/index.html#mfcig5
 */
Blockly.Blocks['xhxml_behavior'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhxml.HUE);
    this.appendDummyInput()
        .appendField("behavior");
    this.appendStatementInput("NAME");
    this.setPreviousStatement(true);
    this.setTooltip('');
  }
};

/**
 * https://blockly-demo.appspot.com/static/demos/blockfactory/index.html#epurp2
 */
Blockly.Blocks['xhxml_rolename'] = {
  init: function() {
    this.setHelpUrl('http://www.example.com/');
    this.setColour(Blockly.Blocks.xhxml.HUE);
    this.appendDummyInput()
        .appendField("roleName")
        .appendField(new Blockly.FieldTextInput(""), "ROLE");
    this.setInputsInline(true);
    this.setOutput(true);
    this.setTooltip('');
  }
};

