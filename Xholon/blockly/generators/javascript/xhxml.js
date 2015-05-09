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

goog.provide('Blockly.JavaScript.xhxml');

goog.require('Blockly.JavaScript');

Blockly.JavaScript['xhxml_avatar'] = function(block) {
  var value_avatar = Blockly.JavaScript.valueToCode(block, 'AVATAR', Blockly.JavaScript.ORDER_ATOMIC);
  var statements_behavior = Blockly.JavaScript.statementToCode(block, 'BEHAVIOR');
  var code = '<Avatar' + value_avatar + '>\n' + statements_behavior + '</Avatar>\n';
  return code;
};

Blockly.JavaScript['xhxml_behavior'] = function(block) {
  var statements_name = Blockly.JavaScript.statementToCode(block, 'NAME');
  var code = '<Attribute_String><![CDATA[\n' + statements_name + ']]></Attribute_String>\n';
  return code;
};

Blockly.JavaScript['xhxml_rolename'] = function(block) {
  var text_role = block.getFieldValue('ROLE');
  var code = ' roleName="' + text_role + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

