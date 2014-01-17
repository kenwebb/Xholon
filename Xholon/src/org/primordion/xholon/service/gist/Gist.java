package org.primordion.xholon.service.gist;

import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.Xholon;
//import org.primordion.xholon.io.IXholonGui;
import org.primordion.xholon.service.IXholonService;

/*
// wb callback from github gist request
    $wnd.xh.wb = $entry(function(json) {
      //console.log("callback from github ...");
      //console.log(JSON);
      //console.log(JSON.stringify);
      //console.log($wnd.JSON);
      var jsonStr = $wnd.JSON.stringify(json);
      //console.log(jsonStr);
      $wnd.sessionStorage.setItem("workbookJsonStr", jsonStr);
      var fileName = app.@org.primordion.xholon.app.Application::getWorkbookFileName()();
      console.log(fileName);
      var content = json["data"]["files"][fileName]["content"];
      console.log(content);
      app.@org.primordion.xholon.app.Application::wbCallback(Ljava/lang/String;)(content);
    });
*/

/**
 * Get a Xholon Workbook as a gist from github.com .
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on December 16, 2013)
 */
public class Gist extends Xholon {
  
  /**
   * Process a synchronous message set by XholonGui, or by any other class running in another thread.
   * @see org.primordion.xholon.base.Xholon#processReceivedSyncMessage(org.primordion.xholon.base.IMessage)
   */
  public IMessage processReceivedSyncMessage(IMessage msg) {
    //consoleLog(msg.getVal_Object());
    switch (msg.getSignal()) {
    case 101:
    default:
      getWorkbook((String)msg.getData());
      break;
    }
    return new Message(102, null, this, msg.getSender());
  }
  
  protected void getWorkbook(String gistId){
    final IApplication app = getApp();
    
    String url = new StringBuilder()
    .append("https://api.github.com/gists/")
    .append(gistId)
    .append("?callback=xh.wb")
    .toString();
    //consoleLog(url);
    
    // Build a JsonpRequestBuilder object
    JsonpRequestBuilder req = new JsonpRequestBuilder();
    // Set timeout of request to be 3 seconds
    req.setTimeout(3000);
    // Make request
    req.requestObject(url, new AsyncCallback<Data>() {
      
      // Handle a failure
      public void onFailure(Throwable caught) {
        consoleLog("Error: " + caught);
      }

      // Handle success
      public void onSuccess(Data result) {
        //consoleLog("JSONP success");
        // save json result to sessionStorage
        result.saveToSessionStorage();
        String fileName = app.getWorkbookFileName();
        String content = result.getFile(fileName).getContent();
        app.wbCallback(content);
      }
    });
  }
  
}
