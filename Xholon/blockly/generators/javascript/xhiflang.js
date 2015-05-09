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

goog.provide('Blockly.JavaScript.xhiflang');

goog.require('Blockly.JavaScript');

Blockly.JavaScript['xhiflang_role'] = function(block) {
  var text_role = block.getFieldValue('ROLE');
  var code = ' role ' + text_role;
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.JavaScript['xhiflang_appear'] = function(block) {
  return 'appear;\n';
};

Blockly.JavaScript['xhiflang_become'] = function(block) {
  var text_thing = block.getFieldValue('THING');
  var text_role = block.getFieldValue('ROLE');
  var code = 'become ' + text_thing + ' role ' + text_role + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_breakpoint'] = function(block) {
  return 'breakpoint;\n';
};

/*Blockly.JavaScript['xhiflang_build'] = function(block) {
  var value_build = Blockly.JavaScript.valueToCode(block, 'BUILD', Blockly.JavaScript.ORDER_ATOMIC);
  var code = 'build ' + block.getFieldValue('THING') + value_build + ';\n';
  return code;
};*/
Blockly.JavaScript['xhiflang_build'] = function(block) {
  var value_role = Blockly.JavaScript.valueToCode(block, 'ROLE', Blockly.JavaScript.ORDER_ATOMIC);
  var dropdown_command = block.getFieldValue('COMMAND');
  var command = '';
  switch (dropdown_command) {
  case 'BUILD': command = 'build'; break;
  case 'APPEND': command = 'append'; break;
  case 'PREPEND': command = 'prepend'; break;
  case 'BEFORE': command = 'before'; break;
  case 'AFTER': command = 'after'; break;
  default: break;
  }
  var text_thing = block.getFieldValue('THING');
  var code = command + ' ' + text_thing + value_role + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_comment'] = function(block) {
  var code = '[' + block.getFieldValue('COMMENT') + '];\n';
  return code;
};

Blockly.JavaScript['xhiflang_drop'] = function(block) {
  var code = 'drop ' + block.getFieldValue('THING') + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_enter'] = function(block) {
  var code = 'enter ' + block.getFieldValue('THING') + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_examine'] = function(block) {
  var dropdown_command = block.getFieldValue('COMMAND');
  var command = '';
  switch (dropdown_command) {
  case 'EXAMINE': command = 'examine'; break;
  case 'X': command = 'x'; break;
  default: break;
  }
  var code = command + ' ' + block.getFieldValue('THING') + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_exit'] = function(block) {
  var place = block.getFieldValue('THING');
  if (place.length > 0) {
    place = ' ' + place;
  }
  var code = 'exit' + place + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_follow'] = function(block) {
  var code = 'follow ' + block.getFieldValue('LEADER') + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_get'] = function(block) {
  var text_thing = block.getFieldValue('THING');
  var text_name = block.getFieldValue('NAME');
  var code = 'get ' + text_thing + ' ' + text_name + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_go'] = function(block) {
  var code = 'go ' + block.getFieldValue('THING') + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_group'] = function(block) {
  var text_thing1 = block.getFieldValue('THING1');
  var dropdown_where = block.getFieldValue('WHERE');
  var where = 'in';
  if (dropdown_where == 'ON') {where = 'on';}
  else if (dropdown_where == 'UNDER') {where = 'under';}
  var text_thing2 = block.getFieldValue('THING2');
  var code = 'group ' + text_thing1 + ' ' + where + ' ' + text_thing2 + ';\n';
  return code;
};

// if ifeq etc. TODO

Blockly.JavaScript['xhiflang_inventory'] = function(block) {
  var dropdown_command = block.getFieldValue('COMMAND');
  var command = '';
  switch (dropdown_command) {
  case 'INVENTORY': command = 'inventory'; break;
  case 'I': command = 'i'; break;
  default: break;
  }
  return command + ';\n';
};

Blockly.JavaScript['xhiflang_lead'] = function(block) {
  var code = 'lead ' + block.getFieldValue('FOLLOWER') + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_look'] = function(block) {
  return 'look;\n';
};

Blockly.JavaScript['xhiflang_next'] = function(block) {
  var code = 'next ' + block.getFieldValue('THING') + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_out'] = function(block) {
  var checkbox_speech = block.getFieldValue('SPEECH') == 'TRUE';
  var checkbox_caption = block.getFieldValue('CAPTION') == 'TRUE';
  var checkbox_transcript = block.getFieldValue('TRANSCRIPT') == 'TRUE';
  var checkbox_debug = block.getFieldValue('DEBUG') == 'TRUE';
  var text_text = block.getFieldValue('TEXT');
  var comma = false;
  var code = 'out ';
  if (checkbox_speech) {
    code += "speech";
    comma = true;
  }
  if (checkbox_caption) {
    if (comma) {code += ",";}
    code += "caption";
    comma = true;
  }
  if (checkbox_transcript) {
    if (comma) {code += ",";}
    code += "transcript";
    comma = true;
  }
  if (checkbox_debug) {
    if (comma) {code += ",";}
    code += "debug";
    comma = true;
  }
  code += " " + text_text + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_param'] = function(block) {
  var dropdown_name = block.getFieldValue('NAME');
  var param_name = '';
  switch (dropdown_name) {
  case 'SPEECH': param_name = 'speech'; break;
  case 'TRANSCRIPT': param_name = 'transcript'; break;
  case 'DEBUG': param_name = 'debug'; break;
  case 'LOOP': param_name = 'loop'; break;
  case 'REPEAT': param_name = 'repeat'; break;
  case 'METEOR': param_name = 'meteor'; break;
  case 'METEORMOVE': param_name = 'meteormove'; break;
  case 'METEOR+MOVE': param_name = 'meteor+move'; break;
  default: break;
  }
  var checkbox_value = block.getFieldValue('VALUE') == 'TRUE';
  var code = 'param ' + param_name + ' ' + checkbox_value + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_paramcaption'] = function(block) {
  var code = 'param caption ' + block.getFieldValue('SELECTION') + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_paramspeech'] = function(block) {
  var code = 'param speech ' + block.getFieldValue('OBJECT') + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_prev'] = function(block) {
  var code = 'prev ' + block.getFieldValue('THING') + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_put'] = function(block) {
  var text_thing1 = block.getFieldValue('THING1');
  var dropdown_where = block.getFieldValue('WHERE');
  var where = 'in';
  if (dropdown_where == 'ON') {where = 'on';}
  else if (dropdown_where == 'UNDER') {where = 'under';}
  var text_thing2 = block.getFieldValue('THING2');
  var code = 'put ' + text_thing1 + ' ' + where + ' ' + text_thing2 + ';\n';
  return code;
};

// xhiflang_search  TODO ?

Blockly.JavaScript['xhiflang_set'] = function(block) {
  var text_thing = block.getFieldValue('THING');
  var text_name = block.getFieldValue('NAME');
  var text_value = block.getFieldValue('VALUE');
  var code = 'set ' + text_thing + ' ' + text_name + ' ' + text_value + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_smash'] = function(block) {
  var code = 'smash ' + block.getFieldValue('THING') + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_take'] = function(block) {
  var code = 'take ' + block.getFieldValue('THING') + ';\n';
  return code;
};

// also eat
Blockly.JavaScript['xhiflang_unbuild'] = function(block) {
  var dropdown_command = block.getFieldValue('COMMAND');
  var command = '';
  switch (dropdown_command) {
  case 'UNBUILD': command = 'unbuild'; break;
  case 'EAT': command = 'eat'; break;
  default: break;
  }
  var code = command + ' ' + block.getFieldValue('THING') + ';\n';
  return code;
};

Blockly.JavaScript['xhiflang_unfollow'] = function(block) {
  return 'unfollow;\n';
};

Blockly.JavaScript['xhiflang_unlead'] = function(block) {
  return 'unlead;\n';
};

Blockly.JavaScript['xhiflang_vanish'] = function(block) {
  return 'vanish;\n';
};

Blockly.JavaScript['xhiflang_wait'] = function(block) {
  var timesteps = Blockly.JavaScript.valueToCode(block, 'DURATION', Blockly.JavaScript.ORDER_ATOMIC) || '1';
  if (Number(timesteps) < 1) {
    timesteps = '1';
  }
  var code = 'wait ' + timesteps + ';\n';
  return code;
};

