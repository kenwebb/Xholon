/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2017 Ken Webb
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
 * NRC Word-Emotion Association Lexicon aka NRC Emotion Lexicon aka EmoLex is 'association of words with eight emotions (anger, fear, anticipation, trust, surprise, sadness, joy, and disgust) and two sentiments (negative and positive) manually annotated on Amazon's Mechanical Turk'.
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://saifmohammad.com/WebPages/AccessResource.htm">EmoLex website</a>
 * @since 0.9.1 (Created on March 19, 2017)
 */
public class SentiEmoLex extends XholonWithPorts implements INaturalLanguage {
  
  /**
   * A JavaScript object.
   */
  protected Object sentiEmoLex = null;
  
  public SentiEmoLex() {
    this.requireSentiEmoLex();
  }
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    if (sentiEmoLex == null) {
      sentiEmoLex = initSentiEmoLex();
    }
    switch (msg.getSignal()) {
    case SIG_ANALYZE_SENTIMENT_REQ:
      return new Message(SIG_ANALYZE_SENTIMENT_RESP, this.analyzeSentiment((String)msg.getData(), this.sentiEmoLex), this, msg.getSender());
    default:
      return super.processReceivedSyncMessage(msg);
    } // end switch
  } // end processReceivedSyncMessage()
  
  /**
   * Analyze Sentiment
   */
  protected native String analyzeSentimentOLD(Object jso, Object sentiEmoLex) /*-{
    $wnd.console.log("analyzing sentiment ...");
    $wnd.console.log(jso);
    var result = "NO RESULT";
    if (sentiEmoLex) {
      result = sentiEmoLex[jso]; // TODO
    }
    $wnd.console.log(result);
    return result;
  }-*/;
  
  protected native Object analyzeSentiment(String str, Object sentiEmoLex) /*-{
    var resultsObj = {anger:0,fear:0,anticipation:0,trust:0,surprise:0,sadness:0,joy:0,disgust:0,negative:0,positive:0};
    if (!str) {return resultsObj;}
    if (!sentiEmoLex) {return resultsObj;}
    // trim, make lowercase, and remove trailing and other punctuation
    str = str.toLowerCase().replace(/[.,\/#!$%\^&\*;:{}=\-_`~()]/g,"").trim();
    var arr = str.split(" ");
    for (var i = 0; i < arr.length; i++) {
      var word = arr[i];
      var resultArr = sentiEmoLex[word];
      if (resultArr) {
        for (var j = 0; j < resultArr.length; j++) {
          var senti = resultArr[j].sentiment;
          resultsObj[senti]++;
        }
      }
    }
    return resultsObj;
  }-*/;
  
  /**
   * Load the sentiEmoLex JavaScript files.
   */
  protected native void requireSentiEmoLex() /*-{
    $wnd.xh.require("sentiEmoLex");
  }-*/;
  
  protected native Object initSentiEmoLex() /*-{
    if ($wnd.SentiEmoLex) {
      return $wnd.SentiEmoLex;
    }
    return null;
  }-*/;

}
