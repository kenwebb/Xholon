package org.primordion.xholon.service.gist;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * JavaScript overlay of a JSONP gist File object.
 * json["data"]["files"][fileName]["content"];
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on December 16, 2013)
 */
public class File extends JavaScriptObject {
	
	// Required protected constructor
	protected File() {}
	
	public final native String getContent() /*-{
		return this.content;
	}-*/;
	
}
