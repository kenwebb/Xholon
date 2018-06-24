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

//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.HashMap;

//import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IMessage;
//import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.IXholonClass;
//import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonWithPorts;
//import org.primordion.xholon.io.xml.IXholon2Xml;
//import org.primordion.xholon.io.xml.IXmlWriter;
//import org.primordion.xholon.service.IXholonService;

/**
 * Minecraft-style Recipe Book
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="">Minecraft website</a>
 * @since 0.9.1 (Created on June 23, 2018)
 */
public class MinecraftStyleRecipeBook extends XholonWithPorts {
  
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
      this.println("MinecraftStyleRecipeBook does not contain any content.");
    }
    else {
      this.val = this.val.trim();
      if (this.val.length() > 0) {
        // send the JSON or JSON string content to RecipeService, and remove this MinecraftStyleRecipeBook instance
        IXholon rserv = this.getService("RecipeService");
        if (rserv != null) {
          String[] strArr = new String[2];
          strArr[0] = "RecipeService-MinecraftStyleRecipeBook-" + this.getRoleName();
          strArr[1] = this.val;
          IMessage rmsg = rserv.sendSyncMessage(IRecipe.SIG_ADD_RECIPE_BOOK_REQ, strArr, this);
        }
      }
    }
    super.postConfigure();
    this.removeChild();
  }

  /*@Override
  public void act() {
    // is there anything to do?
    super.act();
  }*/
  
}
