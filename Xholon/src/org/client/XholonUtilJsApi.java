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

package org.client;

/**
 * Xholon Util JavaScript API.
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on May 9, 2015)
 */
public class XholonUtilJsApi {
  
  /**
   * Export a static API that can be used by external JavaScript.
   */
  public static native void exportUtilApi() /*-{
    
    if (typeof $wnd.xh == "undefined") {
  	  $wnd.xh = {};
  	}
  	$wnd.xh.util = {};
  	
    var api = $wnd.xh.util;
    
    // new XholonSortedNode()
    api.newXholonSortedNode = $entry(function() {
      return @org.primordion.xholon.util.XholonSortedNode::new()();
    });
    
    // obj
    api.obj = $entry(function(val) {
      if (val === undefined) {
        return this.@org.primordion.xholon.util.XholonSortedNode::getVal_Object()();
      }
      else {
        this.@org.primordion.xholon.util.XholonSortedNode::setVal(Ljava/lang/Object;)(val);
        return this;
      }
    });
    
    // count
    api.count = $entry(function(o) {
      if (o === undefined) {
        return this.@org.primordion.xholon.util.XholonSortedNode::getCount()();
      }
      else {
        return this.@org.primordion.xholon.util.XholonSortedNode::getCount(Ljava/lang/Object;)(o);
      }
    });
    
    // add
    api.add = $entry(function(o) {
      return this.@org.primordion.xholon.util.XholonSortedNode::add(Ljava/lang/Object;)(o);
    });
    
    // addAll
    api.addAll = $entry(function(c) {
      return this.@org.primordion.xholon.util.XholonSortedNode::addAll(Ljava/util/Collection;)(c);
    });
    
    // clear
    api.clear = $entry(function() {
      this.@org.primordion.xholon.util.XholonSortedNode::clear()();
      return this;
    });
    
    // contains
    api.contains = $entry(function(o) {
      return this.@org.primordion.xholon.util.XholonSortedNode::contains(Ljava/lang/Object;)(o);
    });
    
    // containsAll
    api.containsAll = $entry(function(c) {
      return this.@org.primordion.xholon.util.XholonSortedNode::containsAll(Ljava/util/Collection;)(c);
    });
    
    // isEmpty
    api.isEmpty = $entry(function() {
      return this.@org.primordion.xholon.util.XholonSortedNode::isEmpty()();
    });
    
    // sortedListOfNodes
    api.sortedListOfNodes = $entry(function(ls) {
      return this.@org.primordion.xholon.util.XholonSortedNode::getSortedListOfNodes(Ljava/util/List;)(ls);
    });
    
    // remove
    api.remove = $entry(function(o) {
      return this.@org.primordion.xholon.util.XholonSortedNode::remove(Ljava/lang/Object;)(o);
    });
    
    // removeChild
    
    // removeAll  NOT YET IMPLEMENTED
    api.removeAll = $entry(function(c) {
      return this.@org.primordion.xholon.util.XholonSortedNode::removeAll(Ljava/util/Collection;)(c);
    });
    
    // size
    api.size = $entry(function() {
      return this.@org.primordion.xholon.util.XholonSortedNode::size()();
    });
    
    // toArray
    api.toArray = $entry(function() {
      return this.@org.primordion.xholon.util.XholonSortedNode::toArray()();
    });
    
    // inOrderPrint
    api.inOrderPrint = $entry(function(level) {
      return this.@org.primordion.xholon.util.XholonSortedNode::inOrderPrint(I)(level);
    });
    
  }-*/;
  
  /**
   * Initialize the IXholon API.
   * Copy all the API functions from Application to the org.primordion.xholon.base.Xholon prototype.
   */
  public static native void initUtilApi() /*-{
    var xsn = @org.primordion.xholon.util.XholonSortedNode::new()();
    var api = $wnd.xh.util;
    
    var prot = Object.getPrototypeOf(xsn);
    
    // copy all the API functions from api to prototype
    for (fname in api) {
      prot[fname] = api[fname];
    }
    
  }-*/;
  
}
