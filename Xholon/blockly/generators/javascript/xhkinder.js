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

goog.provide('Blockly.JavaScript.xhkinder');

goog.require('Blockly.JavaScript');

// anim THING turnright|turnleft|hop|duck|grow|shrink|mirror PARAMS
/*Blockly.JavaScript['xhkinder_anim'] = function(block) {
  var text_thing = block.getFieldValue('THING');
  var dropdown_which = block.getFieldValue('WHICH');
  var which = 'turnright';
  switch (dropdown_which) {
  case 'TURNRIGHT': which = 'turnright'; break;
  case 'TURNLEFT': which = 'turnleft'; break;
  case 'HOP': which = 'hop'; break;
  case 'DUCK': which = 'duck'; break;
  case 'GROW': which = 'grow'; break;
  case 'SHRINK': which = 'shrink'; break;
  case 'MIRROR': which = 'mirror'; break;
  default: break;
  }
  var text_params = block.getFieldValue('PARAMS');
  var code = 'anim ' + text_thing + ' ' + which;
  if (text_params) {
    code += ' ' + text_params;
  }
  code += ';\n';
  return code;
};*/

Blockly.JavaScript['xhkinder_turnright'] = function(block) {
  var code =  'anim this turnright';
  var dropdown_which = block.getFieldValue('WHICH');
  if (dropdown_which) {
    code += ' ' + (dropdown_which * 20);
  }
  return code + ';\n';
};

Blockly.JavaScript['xhkinder_turnleft'] = function(block) {
  var code =  'anim this turnleft';
  var dropdown_which = block.getFieldValue('WHICH');
  if (dropdown_which) {
    code += ' ' + (dropdown_which * 20);
  }
  return code + ';\n';
};

Blockly.JavaScript['xhkinder_hop'] = function(block) {
  //return 'anim this hop;\n';
  var code =  'anim this hop';
  var dropdown_which = block.getFieldValue('WHICH');
  if (dropdown_which) {
    code += ' ' + (dropdown_which * 50);
  }
  return code + ';\n';
};

Blockly.JavaScript['xhkinder_duck'] = function(block) {
  //return 'anim this duck;\n';
  var code =  'anim this duck';
  var dropdown_which = block.getFieldValue('WHICH');
  if (dropdown_which) {
    code += ' ' + (dropdown_which * 50);
  }
  return code + ';\n';
};

Blockly.JavaScript['xhkinder_grow'] = function(block) {
  //return 'anim this grow;\n';
  var code =  'anim this grow';
  var dropdown_which = block.getFieldValue('WHICH');
  if (dropdown_which) {
    code += ' ' + (Math.pow(2, dropdown_which));
  }
  return code + ';\n';
};

Blockly.JavaScript['xhkinder_shrink'] = function(block) {
  //return 'anim this shrink;\n';
  var code =  'anim this shrink';
  var dropdown_which = block.getFieldValue('WHICH');
  if (dropdown_which) {
    code += ' ' + (Math.pow(2, dropdown_which));
  }
  return code + ';\n';
};

Blockly.JavaScript['xhkinder_mirror'] = function(block) {
  //return 'anim this mirror;\n';
  var code =  'anim this mirror';
  var dropdown_which = block.getFieldValue('WHICH');
  if (dropdown_which) {
    code += ' ' + (dropdown_which);
  }
  return code + ';\n';
};

/*Blockly.JavaScript['xhkinder_appear'] = function(block) {
  return 'appear;\n';
};*/

Blockly.JavaScript['xhkinder_enter'] = function(block) {
  var code = 'enter' + ';\n';
  return code;
};

Blockly.JavaScript['xhkinder_exit'] = function(block) {
  var code = 'exit' + ';\n';
  return code;
};

/*Blockly.JavaScript['xhkinder_inventory'] = function(block) {
  var dropdown_command = block.getFieldValue('COMMAND');
  var command = '';
  switch (dropdown_command) {
  case 'INVENTORY': command = 'inventory'; break;
  case 'I': command = 'i'; break;
  default: break;
  }
  return command + ';\n';
};*/

/*Blockly.JavaScript['xhkinder_look'] = function(block) {
  return 'look;\n';
};*/

Blockly.JavaScript['xhkinder_next'] = function(block) {
  var code = 'next' + ';\n';
  return code;
};

Blockly.JavaScript['xhkinder_prev'] = function(block) {
  var code = 'prev' + ';\n';
  return code;
};

/*Blockly.JavaScript['xhkinder_vanish'] = function(block) {
  return 'vanish;\n';
};*/

/*Blockly.JavaScript['xhkinder_wait'] = function(block) {
  var timesteps = Blockly.JavaScript.valueToCode(block, 'DURATION', Blockly.JavaScript.ORDER_ATOMIC) || '1';
  if (Number(timesteps) < 1) {
    timesteps = '1';
  }
  var code = 'wait ' + timesteps + ';\n';
  return code;
};*/

