/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2018 Ken Webb
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

package org.primordion.xholon.service.recipe;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Ceptre json-rules-engine Recipe Book.
 * Given a set of rules in Ceptre textual format, provide the same rules in json-rules-engine JSON format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://github.com/chrisamaphone/interactive-lp">Ceptre repo</a>
 * @see <a href="https://github.com/CacheControl/json-rules-engine">json-rules-engine repo</a>
 * @since 0.9.1 (Created on July 25, 2018)
 */
public class CeptreJsonRulesEngineRecipeBook extends XholonWithPorts {
  
  private String roleName = null;
  
  @Override
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  @Override
  public String getRoleName() {
    return roleName;
  }
  
  /**
    * The recipe book JSON string content will be in this variable.
    */
  private String val = null;
  
  @Override
  public String getVal_String() {
    return val;
  }
  
  @Override
  public void setVal(String val) {
    this.val = val;
  }
  
  @Override
  public void setVal_String(String val) {
    this.val = val;
  }
  
  @Override
  public Object getVal_Object() {
    return val;
  }
  
  @Override
  public void setVal(Object val) {
    this.val = val.toString();
  }
  
  @Override
  public void setVal_Object(Object val) {
    this.val = val.toString();
  }
  
  @Override
  public void postConfigure() {
    if (this.val == null) {
      // the content may be inside a <Attribute_String/> node
      // this may occur if there's a <xi:include ... />
      IXholon contentNode = this.getFirstChild();
      if (contentNode != null) {
        this.val = contentNode.getVal_String();
      }
    }
    if (this.val == null) {
      this.println("CeptreJsonRulesEngineRecipeBook does not contain any content.");
    }
    else {
      this.val = this.val.trim();
      if (this.val.length() > 0) {
        // send the JSON or JSON string content to RecipeService, and remove this CeptreJsonRulesEngineRecipeBook instance
        IXholon rserv = this.getService("RecipeService");
        if (rserv != null) {
          String[] strArr = new String[2];
          strArr[0] = "RecipeService-CeptreJsonRulesEngineRecipeBook-" + this.getRoleName();
          strArr[1] = this.ceptre2json(this.val);
          IMessage rmsg = rserv.sendSyncMessage(IRecipe.SIG_ADD_RECIPE_BOOK_REQ, strArr, this);
        }
      }
    }
    super.postConfigure();
    this.removeChild();
  }
  
  /**
   * Convert rules in Ceptre textual format to rules in json-rules-engine JSON string format.
   */
  protected native String ceptre2json(String ceptre) /*-{
var LOLLI = "-o";
var TENSOR = "*";
var NOT = "!"; // new actions start with the NOT symbol
var beh = {
postConfigure: function(str) {
  var openBracketPos = str.indexOf("{");
  var closedBracketPos = str.indexOf("}");
  var ceptreRules = str.substring(openBracketPos+1, closedBracketPos).trim();
  var arr = ceptreRules.split(".");
  var rules = []; // rules-engine rules
  for (var i = 0; i < arr.length; i++) {
    // Ceptre syntax   NAME ":" CONDITIONS LOLLI ACTIONS "."
    var ceptreRule = arr[i];
    if (ceptreRule) {
      ceptreRule = ceptreRule.trim().substring(0, ceptreRule.length-1); // remove trailing "."
      var nameBodyArr = ceptreRule.split(":");
      var rname = nameBodyArr[0].trim();
      var rbody = nameBodyArr[1].trim();
      var lhsRhsArr = rbody.split(LOLLI);
      var lhs = lhsRhsArr[0].trim();
      var rhs = lhsRhsArr[1].trim();
      var rule = {};
      var mcArr = this.makeConditions(lhs);
      rule.conditions = mcArr[0];
      rule.event = this.makeEvent(rhs, mcArr[1], rname);
      //var ruleStr = $wnd.JSON.stringify(rule);
      //$wnd.console.log(ruleStr);
      rules.push(rule);
      //rules.push(ruleStr);
    }
  }
  $wnd.console.log(rules);
  var rulesStr = $wnd.JSON.stringify(rules);
  $wnd.console.log(rulesStr);
  return rulesStr;
},

makeConditions: function(ceptreLHS) {
  var NOT_ExtraRHS = ["at","adjacent","turn"];
  var arr = ceptreLHS.split(TENSOR);
  var conditions = {};
  conditions.any = [];
  var anyObj = {};
  anyObj.all = [];
  var extraRHS = ""; // ex: condition "strong C'" becomes new action "!strong C'"
  for (var i = 0; i < arr.length; i++) {
    var cond = arr[i].trim();
    var tarr = cond.split(" ");
    var operator = null;
    var operand1 = null;
    var operand2 = null;
    switch (tarr.length) {
      case 0:
      case 1:
        break;
      case 2:
        operator = tarr[0];
        operand1 = tarr[1];
        operand2 = true;
        break;
      case 3:
        operator = tarr[0];
        operand1 = tarr[1];
        operand2 = tarr[2];
        break;
      default:
        break;
    }
    if (operator) {
      if (operator.charAt(0) == "$") {
        operator = operator.substring(1); // remove initial "$"
      }
      else if (NOT_ExtraRHS.indexOf(operator) == -1) {
        if (extraRHS) {
          extraRHS += " " + TENSOR + " ";
        }
        extraRHS += NOT + cond;
      }
      var obj = {};
      obj.fact = operand1;
      obj.operator = operator;
      obj.value = operand2;
      anyObj.all.push(obj);
    }
  }
  conditions.any.push(anyObj);
  return [conditions, extraRHS];
},

makeEvent: function(ceptreRHS, extraRHS, rname) {
  var rhs = ceptreRHS;
  if (extraRHS) {
    if (rhs) {
      rhs += " " + TENSOR + " ";
    }
    rhs += extraRHS;
  }
  var event = {};
  event.type = rname;
  event.params = {};
  event.params.message = rname;
  event.params.rhs = rhs;
  return event;
}

} // end beh

var rules = beh.postConfigure(ceptre.trim());
return rules;
//# sourceURL=StageCeptreRunnerbehavior.js
  }-*/;

  /*@Override
  public void act() {
    // is there anything to do?
    super.act();
  }*/
  
}
