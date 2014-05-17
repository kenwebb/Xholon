/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2014 Ken Webb
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

package org.primordion.xholon.service.timeline;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.i18n.shared.DateTimeFormat;

import java.util.Date;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;

/**
 * <p>Capture data, and display the data in a web browser as a Chap Links timeline.</p>
 * <p>Example of invoking this class from a JavaScript environment (such as Firebug/Dev Tools):</p>
 * <pre>
//------------------- start of part 1
var service = xh.service("TimelineService-ChapJSON");
var root = xh.root();
// init
service.call(-3898, [null, "Hello World", root], root);
// capture (months are 0-indexed)
service.call(-3896, ["3018,0,6", "One"], root);
service.call(-3896, ["3018,1,7", "Two"], root);
service.call(-3896, ["3018,2,8", "Three"], root);
service.call(-3896, ["3018,3,9", "Four"], root);
service.call(-3896, ["3018,10,10", "Five"], root);
//------------------- end of part 1

//------------------- start of part 2
// draw (wait until timeline .js .css have loaded)
service.call(-3895, null, root);
//------------------- end of part 2
 * </pre>
 * 
 * <p>As a Xholon script in a XholonConsole (in two parts):</p>
 * <pre>

<script><![CDATA[
//------------------- start of part 1
var service = $wnd.xh.service("TimelineService-ChapJSON");
var root = $wnd.xh.root();
// init
service.call(-3898, [null, "Hello World", root], root);
// capture (months are 0-indexed)
service.call(-3896, ["3018,0,6", "One"], root);
service.call(-3896, ["3018,1,7", "Two"], root);
service.call(-3896, ["3018,2,8", "Three"], root);
service.call(-3896, ["3018,3,9", "Four"], root);
service.call(-3896, ["3018,10,10", "Five"], root);
$wnd.xh.tlSrvc = service;
//------------------- end of part 1
]]></script>

<script><![CDATA[
//------------------- start of part 2
// draw (wait until timeline .js .css have loaded)
var service = $wnd.xh.tlSrvc;
var root = $wnd.xh.root();
$wnd.console.log(service);
service.call(-3895, null, root);
//------------------- end of part 2
]]></script>

 * </pre>
 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://almende.github.io/chap-links-library/timeline.html">Chap Links timeline website</a>
 * @since 0.9.0 (Created on January 5, 2014)
 */
@SuppressWarnings("serial")
public class TimelineViewerChapJSON extends AbstractTimelineViewer implements ITimelineViewer {
  
  /** StringBuilder initial capacity. */
  protected static final int SB_INITIAL_CAPACITY = 1000; // default is 16
  
  private String outFileName;
  private String outPath = "./ef/chtimeline/";
  private String modelName;
  private IXholon root;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  // options
  protected String width = "100%";
  protected String height = "600px";
  protected String editable = "true";
  protected String style = "box";
  
  // startDate must be Y,M,D or Y,M,D,H,M,S  examples: "2010,7,23" "2010,7,23,23,0,0"
  DateTimeFormat dateFmt = DateTimeFormat.getFormat("yyyy,MM,dd,HH,mm,ss");

  /**
   * HTML element ID where Timeline will be displayed.
   */
  protected String viewElementId = "networkview";
  
  /**
   * Start of the CHAP Timeline script.
   */
  protected StringBuilder startSb = null;
  
  /**
   * The accumulating text for all CHAP Timeline JSON data.
   */
  protected StringBuilder dataSb = null;
  
  /**
   * CHAP Timeline options.
   */
  protected StringBuilder optionsSb = null;
  
  /**
   * End of the CHAP Timeline script.
   */
  protected StringBuilder endSb = null;
  
  /**
   * default constructor
   */
  public TimelineViewerChapJSON() {}
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    Object response = null;
    Object data = msg.getData();
    
    switch (msg.getSignal()) {
    case SIG_INITIALIZE_REQ:
    {
      // the data is an array of three items
      String outFileName = null;
      String modelName = null;
      Object root = null;
      if (data instanceof JavaScriptObject) {
        JavaScriptObject jso = (JavaScriptObject)data;
        JsArrayMixed arr = jso.cast();
        outFileName = arr.getString(0);
        modelName = arr.getString(1);
        root = (Object)arr.getObject(2);
      }
      else if (data instanceof Object[]) {
        Object[] iobj = (Object[]) data;
        outFileName = (String)(iobj[0]);
        modelName = (String)(iobj[1]);
        root = (Object)(iobj[2]);
      }
      else {
        break;
      }
      initialize(outFileName, modelName, (IXholon)root);
      break;
    }
    case SIG_PARAMETERS_REQ:
      // there are no parameters yet
      break;
    case SIG_CAPTURE_REQ:
    {
      Object dateTimeObj = null;
      String content = null;
      if (data instanceof JavaScriptObject) {
        JavaScriptObject jso = (JavaScriptObject)data;
        JsArrayMixed arr = jso.cast();
        // dateTimeObj can be a String or Date; for now assume it's a String
        dateTimeObj = arr.getString(0);
        content = arr.getString(1);
      }
      else if (data instanceof Object[]) {
        Object[] cobj = (Object[])data;
        dateTimeObj = cobj[0];
        content = (String)(cobj[1]);
      }
      capture(dateTimeObj, content);
      break;
    }
    case SIG_DRAW_REQ:
      drawTimeline();
      break;
    default:
      return super.processReceivedSyncMessage(msg);
    }
    return new Message(SIG_PROCESS_RSP, response, this, msg.getSender());
  }
  
  @Override
  public void initialize(String outFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (outFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".js";
    }
    else {
      this.outFileName = outFileName;
    }
    this.modelName = modelName;
    this.root = root;
    
    this.startSb = new StringBuilder(128)
    .append("var timeline = null;\n")
    .append("var data = null;\n")
    .append("function draw() {\n");
    
    this.dataSb = new StringBuilder(SB_INITIAL_CAPACITY)
    .append("// Create a JSON data table\n")
    .append("data = [") // don't use a trailing \n here
    ;
    
    this.optionsSb = new StringBuilder(SB_INITIAL_CAPACITY)
    .append("// options\n")
    .append("var options = {\n")
    .append("  'width': '" + width + "',\n")
    .append("  'height': '" + height + "',\n")
    .append("  'editable': " + editable + ",\n")
    .append("  'style': '" + style + "'\n")
    .append("};\n");
    
    this.endSb = new StringBuilder(128)
    .append("timeline = new links.Timeline(document.getElementById('")
    .append(viewElementId)
    .append("'));\n")
    .append("timeline.draw(data, options);\n")
    .append("}\n")
    .append("draw();\n")
    ;
    
    if (!isDefinedChapLinksTimeline()) {
      //root.consoleLog("about to call loadChapLinksTimeline()");
      loadChapLinksTimeline();
    }
  }
  
  @Override
  public void capture(Object dateTimeObj, String content) {
    String startDate = null;
    if (dateTimeObj instanceof Date) {
      startDate = "new Date(" + dateFmt.format((Date)dateTimeObj) + ")";
    }
    else if (dateTimeObj instanceof String) {
      // assume that the String is in the right format
      startDate = "new Date(" + dateTimeObj + ")";
    }
    else {
      startDate = "new Date(" + dateFmt.format(new Date()) + ")"; // current date and time
    }
    
    dataSb
    .append("\n{\n")
    .append("  \"start\": ").append(startDate).append(",\n")
    .append("  \"content\": \"").append(content).append("\"\n")
    .append("},")
    ;
  }
  
  @Override
  public void drawTimeline() {
    if (!isDefinedChapLinksTimeline()) {return;}
    
    // JSON does not allow dangling commas
		// if existing last char == ',' then replace that last char
		int len = dataSb.length();
		if (dataSb.charAt(len-1) == ',') {
		  dataSb.setCharAt(len-1, ' ');
		}
		dataSb.append("\n];\n");
    
    StringBuilder sb = new StringBuilder()
    .append("// ")
    .append(modelName)
    .append("\n\n")
    .append(startSb.toString())
    .append(dataSb.toString())
    .append(optionsSb.toString())
    .append(endSb.toString());
    
    writeToTarget(sb.toString(), outFileName, outPath, root);
    
    if (root.getApp().isUseGwt()) {
      pasteScript("tvScript", sb.toString());
    }
    else {
      // TODO possibly write to a new browser window; open window and then write data
      System.out.println(sb.toString());
    }
  }
  
  protected void pasteScript(String scriptId, String scriptContent) {
    HtmlScriptHelper.fromString(scriptContent, true);
  }
  
  /**
   * Load CHAP Links Timeline library asynchronously.
   */
  protected void loadChapLinksTimeline() {
    loadCss("xholon/lib/timeline.css");
    require(this);
  }
  
  /**
   * use requirejs
   */
  protected native void require(final ITimelineViewer tv) /*-{
    $wnd.requirejs.config({
      enforceDefine: false,
      paths: {
        timeline: [
          "xholon/lib/timeline-min"
        ]
      }
    });
    $wnd.require(["timeline"], function(timeline) {
      //tv.@org.primordion.xholon.service.timeline.ITimelineViewer::drawTimeline()();
    });
  }-*/;
  
  /**
   * Is $wnd.links.Network defined.
   * @return it is defined (true), it's not defined (false)
   */
  protected native boolean isDefinedChapLinksTimeline() /*-{
    return (typeof $wnd.links != "undefined") && (typeof $wnd.links.Timeline != "undefined");
  }-*/;
  
  protected native void loadCss(String url) /*-{
    var link = $doc.createElement("link");
    link.type = "text/css";
    link.rel = "stylesheet";
    link.href = url;
    $doc.getElementsByTagName("head")[0].appendChild(link);
  }-*/;
  
}
