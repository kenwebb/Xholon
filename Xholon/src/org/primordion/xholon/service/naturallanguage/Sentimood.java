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
 * Sentimood is 'a minimal sentiment analyzer based on @thinkroth's "Sentimental" and written in CoffeeScript'.
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://github.com/soops/sentimood">Sentimood website</a>
 * @since 0.9.1 (Created on March 17, 2017)
 */
public class Sentimood extends XholonWithPorts implements INaturalLanguage {
  
  /**
   * A JavaScript object.
   */
  protected Object sentimood = null;
  
  public Sentimood() {
    this.requireSentimood();
  }
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    if (sentimood == null) {
      sentimood = initSentimood();
    }
    switch (msg.getSignal()) {
    case SIG_ANALYZE_SENTIMENT_REQ:
      return new Message(SIG_ANALYZE_SENTIMENT_RESP, this.analyzeSentiment(msg.getData(), this.sentimood), this, msg.getSender());
    default:
      return super.processReceivedSyncMessage(msg);
    } // end switch
  } // end processReceivedSyncMessage()
  
  /**
   * Analyze Sentiment
   */
  protected native String analyzeSentiment(Object jso, Object sentimood) /*-{
    $wnd.console.log("analyzing sentiment ...");
    $wnd.console.log(jso);
    var result = "NO RESULT";
    if (sentimood) {
      result = sentimood.analyze(jso);
    }
    $wnd.console.log(result);
    return result;
  }-*/;
  
  /**
   * Load the sentimood JavaScript files.
   */
  protected native void requireSentimood() /*-{
    $wnd.xh.require("sentimood");
  }-*/;
  
  protected native Object initSentimood() /*-{
    if ($wnd.Sentimood) {
      return new $wnd.Sentimood();
    }
    return null;
  }-*/;

}
