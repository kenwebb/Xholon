/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2016 Ken Webb
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

package org.primordion.xholon.service.naturallanguage;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.util.ClassHelper;

/**
 * A NaturalLanguage concrete class.
 * 
 * Quick test (from Developer Tools):
var nls = xh.service("NaturalLanguageService");
var resp = nls.call(-3898, "This is a test.", xh.root());
 * 
 * source: https://cloud.google.com/natural-language/docs/basics
The Natural Language API has several methods for performing analysis and annotation on your text. Each level of analysis provides valuable information for language understanding. These methods are listed below:

 - Sentiment analysis inspects the given text and identifies the prevailing emotional opinion within the text, especially to determine a writer's attitude as positive, negative, or neutral. Sentiment analysis is performed through the analyzeSentiment method. Currently, only English is supported for sentiment analysis.

 - Entity analysis inspects the given text for known entities (proper nouns such as public figures, landmarks, etc.) and returns information about those entities. Entity analysis is performed with the analyzeEntities method.

 - Syntactic analysis extracts linguistic information, breaking up the given text into a series of sentences and tokens (generally, word boundaries), providing further analysis on those tokens. Syntactic Analysis is performed with the analyzeSyntax method.
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://cloud.google.com/natural-language/">Google Cloud Natural Language API</a>
 * @since 0.9.1 (Created on December 23, 2016)
 */
public class GoogleNL extends XholonWithPorts implements INaturalLanguage {
  
  public GoogleNL() {}
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    switch (msg.getSignal()) {
    case SIG_ANALYZE_ENTITIES_REQ:
      return new Message(SIG_ANALYZE_ENTITIES_RESP, this.analyzeEntities(msg.getData()), this, msg.getSender());
    case SIG_ANALYZE_SYNTAX_REQ:
      return new Message(SIG_ANALYZE_SYNTAX_RESP, this.analyzeSyntax(msg.getData()), this, msg.getSender());
    case SIG_ANALYZE_SENTIMENT_REQ:
      return new Message(SIG_ANALYZE_SENTIMENT_RESP, this.analyzeSentiment(msg.getData()), this, msg.getSender());
    default:
      return super.processReceivedSyncMessage(msg);
    } // end switch
  } // end processReceivedSyncMessage()
  
  /**
   * Analyze Entities.
   * pass in "Locations" and "People" Xholon container nodes
   * pass in a list of types to look for in the JSON, and indicate which Xholon container they should go in
   * search the input JSON, and create Xholon content for each "PERSON" and "LOCATION"
   */
  protected native String analyzeEntities(Object jso) /*-{
    var dests = jso.xhDestinations;
    $wnd.console.log(dests);
    var entities = jso.googleEntityResponse.entities;
    $wnd.console.log(entities);
    for (var i = 0; i < entities.length; i++) {
      var entity = entities[i];
      $wnd.console.log(entity.name + " " + entity.type);
      var pnode = dests[entity.type];
      if (pnode) {
        var xmlStr = "<";
        var tagName = pnode.xhc().attr("childSuperClass");
        if (tagName) {
          xmlStr += tagName;
        }
        else {
          xmlStr += entity.type.charAt(0) + entity.type.substring(1).toLowerCase() ;
        }
        xmlStr += ' roleName="' + entity.name + '"/>';
        $wnd.console.log(xmlStr);
        pnode.append(xmlStr);
      }
    }
    
    return "analyzing entities ...";
  }-*/;
  
  /**
   * Analyze Syntax
   */
  protected native String analyzeSyntax(Object jso) /*-{
    
    return "analyzing syntax ...";
  }-*/;
  
  /**
   * Analyze Sentiment
   */
  protected native String analyzeSentiment(Object jso) /*-{
    
    return "analyzing sentiment ...";
  }-*/;

}
