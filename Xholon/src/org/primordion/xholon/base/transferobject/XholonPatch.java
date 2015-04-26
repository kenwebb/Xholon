/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2015 Ken Webb
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

package org.primordion.xholon.base.transferobject;

import com.google.gwt.core.client.JavaScriptObject;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;

/**
 * A standard format for patching Xholon apps at runtime.
 * This is based on RFC 5261, and may include ideas from RFC 6902.
 * Initially this is used by the Meteor Platform Service.
 * For now, I'm not supporting namespaces.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://tools.ietf.org/html/rfc5261">RFC 5261 XML Patch spec</a>
 * @see <a href="https://tools.ietf.org/html/rfc6902">RFC 6902 JSON Patch spec</a>
 * @since 0.9.1 (Created on April 24, 2015)
 */
public class XholonPatch extends Xholon implements IXholonPatch {
  
  /**
   * Constructor.
   * If you use this constructor, then you will need to call various setters to insert various fields.
   */
  public XholonPatch() {
    makePatch();
  }
  
  /**
   * Constructor.
   * @param sel .
   * @param content .
   */
  /*public XholonPatch(String sel, String content) {
    makePatch(PATCHOP_ADD, sel, POS_APPEND, content);
  }*/
  
  /**
   * Constructor.
   * TODO type is used when adding attributes and namespaces
   * @param op (ex: "add" "replace" "remove").
   * @param sel (ex: "Hello/World").
   * @param pos (ex: "append" "prepend" "before" "after").
   * @param content (ex: "<One><Two/></One>").
   */
  /*public XholonPatch(String op, String sel, String pos, String content) {
    if (op == null) {op = PATCHOP_ADD;}
    if ((op == PATCHOP_ADD) && (pos == null)) {pos = POS_APPEND;}
    makePatch(op, sel, pos, content);
  }*/
  
  /**
   * Constructor. Typically used to process received patches.
   * @param jso 
   */
  public XholonPatch(JavaScriptObject jso) {
    makePatch(jso);
  }
  
  @Override
  public native Object getVal_Object() /*-{
    return this.patch;
  }-*/;
  
  @Override
  public native void setVal_Object(Object obj) /*-{
    this.patch = obj;
  }-*/;
  
  /**
   * Make a JavaScript (JSON + XML content) patch object.
   */
  protected native void makePatch() /*-{
    this.patch = {};
  }-*/;
  
  /**
   * Make a JavaScript (JSON + XML content) patch object.
   */
  //protected native void makePatch(String op, String sel, String pos, String content) /*-{
  //  var p = {};
  //  p.op = op;
  //  p.sel = sel;
  //  p.pos = pos;
  //  p.content = content;
  //  //p.type = type
  //  this.patch = p;
  //}-*/;
  
  /**
   * Make a JavaScript (JSON + XML content) patch object.
   */
  protected native void makePatch(JavaScriptObject jso) /*-{
    this.patch = jso;
  }-*/;
  
  public void addElementAppend(String sel, String content) {
    addElement(sel, POS_APPEND, content);
  }
  
  public void addElementPrepend(String sel, String content) {
    addElement(sel, POS_PREPEND, content);
  }
  
  public void addElementBefore(String sel, String content) {
    addElement(sel, POS_BEFORE, content);
  }
  
  public void addElementAfter(String sel, String content) {
    addElement(sel, POS_AFTER, content);
  }
  
  /**
   * Add an element.
   */
  protected void addElement(String sel, String pos, String content) {
    makePatch();
    setOp(PATCHOP_ADD);
    setSel(sel);
    setPos(pos);
    setContent(content);
  }
  
  /**
   * Add an attribute.
   */
  public void addAttribute(String sel, String type, String content) {
    makePatch();
    setOp(PATCHOP_ADD);
    setSel(sel);
    setType(type);
    setContent(content);
  }
  
  /**
   * Replace an element, including it's entire subtree.
   */
  public void replaceElement(String sel, String content) {
    makePatch();
    setOp(PATCHOP_REPLACE);
    setSel(sel);
    setContent(content);
  }
  
  /**
   * Replace an attribute.
   * Use addAttribute() instead.
   */
  /*public void replaceAttribute(String sel, String content) {
    makePatch();
    setOp(PATCHOP_REPLACE);
    setSel(sel);
    setContent(content);
  }*/
  
  /**
   * Replace text.
   */
  public void replaceText(String sel, String content) {
    makePatch();
    setOp(PATCHOP_REPLACE);
    setSel(sel);
    setContent(content);
  }
  
  /**
   * Remove an element, including it's entire subtree.
   */
  public void removeElement(String sel) {
    makePatch();
    setOp(PATCHOP_REMOVE);
    setSel(sel);
  }
  
  /**
   * Remove an attribute.
   */
  /*public void removeAttribute(String sel) {
    makePatch();
    setOp(PATCHOP_REMOVE);
    setSel(sel);
  }*/
  
  /**
   * Remove text.
   */
  public void removeText(String sel) {
    makePatch();
    setOp(PATCHOP_REMOVE);
    setSel(sel);
  }
  
  /** op */
  public native String getOp() /*-{return this.patch.op;}-*/;
  public native void setOp(String op) /*-{this.patch.op = op;}-*/;

  /** sel */
  public native String getSel() /*-{return this.patch.sel;}-*/;
  public native void setSel(String sel) /*-{this.patch.sel = sel;}-*/;

  /** pos */
  public native String getPos() /*-{return this.patch.pos;}-*/;
  public native void setPos(String pos) /*-{this.patch.pos = pos;}-*/;

  /** content */
  public native String getContent() /*-{return this.patch.content;}-*/;
  public native void setContent(String content) /*-{this.patch.content = content;}-*/;
  
  /** type */
  public native String getType() /*-{return this.patch.type;}-*/;
  public native void setType(String type) /*-{this.patch.type = type;}-*/;
  
  /** _id  the Meteor platform adds an internal id to each item in a Meteor collection */
  public native String get_id() /*-{return this.patch._id;}-*/;
  
  /** sessionId  The Xholon MeteorPlatformService adds a sessionId to each item */
  public native String getSessionId() /*-{return this.patch.sessionId;}-*/;
  public native void setSessionId(String sessionId) /*-{this.patch.sessionId = sessionId;}-*/;
  
}
