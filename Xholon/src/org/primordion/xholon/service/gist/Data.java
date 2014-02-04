package org.primordion.xholon.service.gist;

import com.google.gwt.core.client.JavaScriptObject;
//import com.google.gwt.core.client.JsArray;

/**
 * JavaScript overlay of a JSONP gist Data object.
 * json["data"]["files"][fileName]["content"];
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on December 16, 2013)
 */
public class Data extends JavaScriptObject {
	
	// Required protected constructor
	protected Data() {}
	
	public final native void saveToSessionStorage() /*-{
		var jsonStr = $wnd.JSON.stringify(this);
    $wnd.sessionStorage.setItem("workbookJsonStr", jsonStr);
	}-*/;
	
	// Returns a JSArray of files from the data by directly accessing the underlying element
	// "files": { "xholonWorkbook.xml": {
	public final native File getFile(String fileName) /*-{
		return this.data.files[fileName];
	}-*/;
	
	// "description": "Flashcards (SVG images)"
	public final native String getDescription() /*-{
		return this.data.description;
	}-*/;
  
  // "html_url": "https://gist.github.com/2470836"
  public final native String getHtmlUrl() /*-{
		return this.data.html_url;
	}-*/;
	
}
