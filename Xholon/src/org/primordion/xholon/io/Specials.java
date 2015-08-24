package org.primordion.xholon.io;

import java.util.HashMap;
import java.util.Map;

import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.console.IXholonConsole;
import org.primordion.xholon.io.console.XholonConsole;
import org.primordion.xholon.io.xml.IXmlReader;
import org.primordion.xholon.io.xml.XmlReaderFactory_gwt;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.util.ClassHelper;

/**
 * Special actions.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on December 9, 2013)
 */
public class Specials {
  
  String[] actionList = null;
  
  Map<String, String> map = null;
  
  IXholon node = null;
  
  public Specials() {}
  
  public String[] getActionList() {
    return actionList;
  }
  
  public void doAction(String actionName) {
    //node.consoleLog(actionName);
    if (actionName == null) {return;}
    String actionContent = map.get(actionName);
    //node.consoleLog(actionContent);
    if (actionContent == null) {return;}
    
    if (ClassHelper.isAssignableFrom(XholonConsole.class, node.getClass())) {
      // paste into the XholonConsole command pane, replacing the existing text
      ((IXholonConsole)node).setResult(actionContent, true);
    }
    else {
      // paste into the IXholon as a last child
      node
      .getService(IXholonService.XHSRV_XHOLON_HELPER)
      .sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, actionContent, node);
    }
  }
  
  public void xmlString2Items(String xmlString, IXholon node) {
    this.node = node;
    if (xmlString.length() > 0) {
      final IXmlReader xmlReader = XmlReaderFactory_gwt.getXmlReader(xmlString);
      final int eventType = xmlReader.getEventType();
      map = new HashMap<String, String>();
      //try {
        readItemsFromXmlSource(node, xmlReader, eventType);
      //} catch(XholonConfigurationException e) {
      //  System.out.println("Parameters: unable to readConfigFromXmlSource");
      //}
    }
  }
  
  protected void readItemsFromXmlSource(IXholon node, IXmlReader xmlReader, int eventType) {
    String action = null;
    String xhc = null;
    String defaultXhc = "XholonClass";
    int attrCount = 0;
    int i = 0;
    StringBuilder sbal = null;
    while (eventType != IXmlReader.END_DOCUMENT) {
      if (eventType == IXmlReader.START_DOCUMENT) {}
      else if (eventType == IXmlReader.END_DOCUMENT) {}
      else if (eventType == IXmlReader.START_TAG) {
        if (xmlReader.getName().equals("Specials")) {
          attrCount = xmlReader.getAttributeCount();
          i = 0;
          while (i < attrCount) {
            if ("xhc".equals(xmlReader.getAttributeName(i))) {
              defaultXhc = xmlReader.getAttributeValue(i);
            }
            i++;
          }
        }
        else if (xmlReader.getName().equals("Special")) {
          attrCount = xmlReader.getAttributeCount();
          i = 0;
          while (i < attrCount) {
            if ("action".equals(xmlReader.getAttributeName(i))) {
              action = xmlReader.getAttributeValue(i);
              if (sbal == null) {
                sbal = new StringBuilder();
              }
              else {
                sbal.append(",");
              }
              sbal.append(action);
              //node.consoleLog(action);
            }
            else if ("xhc".equals(xmlReader.getAttributeName(i))) {
              xhc = xmlReader.getAttributeValue(i);
              //node.consoleLog(xhc);
            }
            i++;
          }
        }
      }
      else if (eventType == IXmlReader.END_TAG) {}
      else if (eventType == IXmlReader.TEXT) {
        String text = xmlReader.getText().trim();
        if (text.length() > 0) {
          // deal with nested escaped CDATA &lt; &gt;
          text = text
          .replaceAll("&lt;!\\[CDATA\\[", "<![CDATA[")
          .replaceAll("\\]\\]&gt;", "]]>");
          //node.consoleLog(text);
          // put the text into a Map, where action is the key
          map.put(action, text);
        }
      }
      eventType = xmlReader.next();
    } // end while
    actionList = sbal.toString().split(",");
  }
  
}
