/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

package org.primordion.xholon.base;

import com.google.gwt.core.client.JavaScriptObject;

//import java.util.Iterator;
import java.util.List;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
//import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.util.IJavaTypes;
import org.primordion.xholon.util.Misc;

/**
 * GWT JavaScript.
 * Encapsulate a behavior implemented in some scripting language.
 * Invoke the script engine each time step, when the act() method is called.
 * The script engine will evaluate the script.
 * The scripting language can be any language supported by a Java 1.6 script engine,
 * when the jar files are in the classpath.
 * <p>Examples:</p>
<pre>
&lt;Dummy implName="org.primordion.script.Behavior" lang="javascript">&lt;![CDATA[
var x = 20;
println(x);
]]>&lt;/Dummy>
</pre>

<pre>
&lt;Dummy implName="org.primordion.script.Behavior" lang="beanshell">&lt;![CDATA[
int x = 25;
System.out.println(x);
]]>&lt;/Dummy>
</pre>

<pre>
&lt;Dummy implName="org.primordion.script.Behavior" lang="beanshell">&lt;![CDATA[
import org.primordion.xholon.base.IXholon;
int a = 25;
int b = 33;
int c = a + b;
System.out.println(c);
IXholon cnode = contextNodeKey;
System.out.println(cnode.getName());
]]>&lt;/Dummy>
</pre>

<pre>
&lt;Dummy implName="org.primordion.script.Behavior" lang="javascript">&lt;![CDATA[
function act() {
  var x = 20;
  println(x);
}
]]>&lt;/Dummy>
</pre>

Code in an existing script can be replaced,
using a second script that's pasted in as a lastChild of the first script.
The various scripts can be in any of the scripting languages.
For example, try either of these to replace the contents of a beanshell script:
<pre>
&lt;script implName="lang:groovy:inline:">
import org.primordion.xholon.base.IXholon;
IXholon cnode = contextNodeKey;
cnode.setVal("System.out.println(\"Licorice is bored.\");");
&lt;/script>
</pre>
<pre>
&lt;Test1 implName="lang:python:inline:">&lt;![CDATA[
contextNodeKey.setVal("System.out.println(\"Licorice is gone.\");");
]]>&lt;/Test1>
</pre>

 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on September 26, 2013)
 */
public class Behavior_gwtjs extends Xholon {
	
	/**
	 * The scripting language used.
	 * <p>ex: "javascript" "beanshell" "groovy"</p>
	 */
	protected String scriptLanguage = "gwtjs";
	
	/**
	 * The actual script content.
	 */
	private String scriptContent = null;
	
	private boolean scriptConfigured = false;
	
	/**
	 * The JavaScript object.
	 */
	private JavaScriptObject beh = null;
	
	/**
	 * Does the script contain a preAct() method?
	 */
	private boolean hasPreAct = false;
	
	/**
	 * Does the script contain an act() method?
	 */
	private boolean hasAct = false;
	
	/**
	 * Does the script contain a postAct() method?
	 */
	private boolean hasPostAct = false;
	
	/**
	 * Does the script contain a processReceivedMessage(msg) method?
	 */
	private boolean hasProcessReceivedMessage = false;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getAllPorts()
	 */
	@SuppressWarnings("rawtypes")
	public List getAllPorts() {
		return getParentNode().getAllPorts();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure() {
		super.postConfigure();
		if (scriptLanguage == null) {
			getLogger().error("You must specify a scripting language for the Behavior. ex: lang=\"javascript\" ");
			removeChild();
			return;
		}
		if (scriptContent == null) {
			getLogger().error("You must specify some script content for the Behavior.");
			this.removeChild();
			return;
		}
		beh = configureJso(this, scriptContent);
		if (beh == null) {
		  // this is a one-time script, so remove it from the tree
		  //println("beh is null; calling removeChild()");
		  this.removeChild();
		  return;
		}
		hasPreAct = hasFunction(beh, "preAct");
		hasAct = hasFunction(beh, "act");
		hasPostAct = hasFunction(beh, "postAct");
		hasProcessReceivedMessage = hasFunction(beh, "processReceivedMessage");
		if (hasFunction(beh, "postConfigure")) {
		  postConfigure(beh);
		}
		scriptConfigured = true;
		// register this behavior to receive all otherwise unhandled messages
		// TODO determine if there's a specific list of signals that this behavior can handle
		this.getParentNode().registerMessageForwardee(this, null);
	}
	
	protected native void postConfigure(JavaScriptObject bobj) /*-{
	  bobj.postConfigure();
	}-*/;
	
	protected native JavaScriptObject configureJso(IXholon cnode, String scriptContent) /*-{
	  console.log(scriptContent);
	  eval(scriptContent);
	  
	  try {
	    if (beh === undefined) {
	      //console.log("beh was undefined");
	      return null;
	    }
	    else {
	      //console.log(beh);
	      beh.cnode = cnode;
	      return beh;
	    }
	  } catch(e) {
	    //console.log("beh was non-existent");
	    return null;
	  }
	}-*/;
	
	protected native boolean hasFunction(JavaScriptObject bobj, String fname) /*-{
	  //console.log("bobj[fname] " + fname);
	  if (bobj && bobj[fname]) {
	    //console.log(true);
	    return true;
	  }
	  //console.log(false);
	  return false;
	}-*/;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#preAct()
	 */
	public void preAct() {
	  //println("Behavior_gwtjs preAct start");
		if (hasPreAct) {
			if (beh != null) {
			  preAct(beh);
			}
		}
		super.preAct();
	}
	
	protected native void preAct(JavaScriptObject bobj) /*-{
	  bobj.preAct();
	}-*/;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
	  if (hasAct) {
		  if (beh != null) {
			  act(beh);
			}
		}
		super.act();
	}
	
	protected native void act(JavaScriptObject bobj) /*-{
	  bobj.act();
	}-*/;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postAct()
	 */
	public void postAct() {
	  //println("Behavior_gwtjs postAct start");
		if (hasPostAct) {
			if (beh != null) {
			  postAct(beh);
			}
		}
		super.postAct();
	}
	
	protected native void postAct(JavaScriptObject bobj) /*-{
	  bobj.postAct();
	}-*/;
	
	/**
	 * The message data must contain the name of a beh method,
	 * and any arguments required by that method.
	 * @see org.primordion.xholon.base.Xholon#processReceivedMessage(org.primordion.xholon.base.IMessage)
	 */
	public void processReceivedMessage(IMessage msg) {
	  //println(msg.toString());
	  switch (msg.getSignal()) {
		case ISignal.SIGNAL_XHOLON_CONSOLE_REQ:
		  // GWT ex (Chrome Developer Tools console):
		  // var cnode = xh.xpath("descendant::Zombie/*", xh.root()); cnode.sendMessage(-9, "testing, 123, 456, 789", xh.app());
		  // var cnode = xh.xpath("descendant::Zombie/*", xh.root()); cnode.msg(-9, "testing, 123, 456, 789", xh.app());
      //println("processReceivedMessage 1");
			Object returnObj = null;
			if (beh != null) {
			  //println(msg.getData());
				returnObj = invokeMethod((String)msg.getData());
			}
			msg.getSender().sendMessage(ISignal.SIGNAL_XHOLON_CONSOLE_RSP, returnObj, this);
			break;
		default:
			if (hasProcessReceivedMessage) {
			  if (beh != null) {
			    processReceivedMessage(beh, msg);
			  }
		  }
			break;
		}
	}
	
	protected native void processReceivedMessage(JavaScriptObject bobj, IMessage msg) /*-{
	  bobj.processReceivedMessage(msg.obj());
	}-*/;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder()
		.append(super.toString()).append(" ")
		.append("scriptConfigured:").append(scriptConfigured)
		.append(" hasPreAct:").append(hasPreAct)
		.append(" hasAct:").append(hasAct)
		.append(" hasPostAct:").append(hasPostAct)
		.append(" hasProcessReceivedMessage:").append(hasProcessReceivedMessage);
		if (hasFunction(beh, "toString")) {
		  // returns "[object Object]" if the JavaScript object does not override toString()
		  sb.append(" ").append(this.toString(beh));
		}
		return sb.toString();
	}
	
	protected native String toString(JavaScriptObject bobj) /*-{
	  return bobj.toString();
	}-*/;
	
	/**
	 * Invoke a method of the beh.
	 * @param methodNameAndArgs
	 * ex: "myMethod(arg1Val,arg2Val)"
	 * ex: "setFossilFuelBurning(6.0)"
	 * ex: "x(hello,1,2.0,3.3f,true,4L)" --> [x, hello, 1, 2.0, 3.3f, true, 4L]
	 * @return 
	 */
	protected Object invokeMethod(String methodNameAndArgs) {
		String[] argsStr = methodNameAndArgs.split("[(,)]");
		String methodName = argsStr[0];
		println(methodName);
		Object[] args = findArgs(argsStr, 1);
		println(args);
		Object returnObj = invokeMethod(beh, methodName, args);
		/*try {
			returnObj = invocable.invokeMethod(beh, methodName, args);
		} catch (ScriptException e) {
			returnObj = "script exception";
			logger.error(e.getMessage());
		} catch (NoSuchMethodException e) {
			returnObj = e.getMessage();
		}*/
		return returnObj;
	}
	
	protected native Object invokeMethod(JavaScriptObject bobj, String methodName, Object[] args) /*-{
	  try {
	    var returnObj = bobj[methodName].apply(bobj, args);
	    if (returnObj === undefined) {
	      return null;
	    }
	    else {
	      return returnObj;
	    }
	  } catch(e) {
	    return null;
	  }
	}-*/;
	
	/**
	 * Find the list of args required by invokeMethod().
	 * @param argsStr
	 * @param startIx
	 * @return 
	 */
	protected Object[] findArgs(String[] argsStr, int startIx) {
		Object[] args = new Object[argsStr.length-startIx];
		for (int i = startIx; i < argsStr.length; i++) {
			int type = Misc.getJavaDataType(argsStr[i]);
			switch (type) {
			case IJavaTypes.JAVACLASS_boolean:
				args[i-startIx] = Boolean.valueOf(argsStr[i]);
				break;
			case IJavaTypes.JAVACLASS_double:
				args[i-startIx] = Double.valueOf(argsStr[i]);
				break;
			case IJavaTypes.JAVACLASS_float:
				args[i-startIx] = Float.valueOf(argsStr[i]);
				break;
			case IJavaTypes.JAVACLASS_int:
				args[i-startIx] = Integer.valueOf(argsStr[i]);
				break;
			case IJavaTypes.JAVACLASS_long:
			  // TODO this may not work in GWT
				args[i-startIx] = Long.valueOf(argsStr[i]);
				break;
			case IJavaTypes.JAVACLASS_String:
				args[i-startIx] = argsStr[i];
				break;
			default:
				break;
			}
		}
		return args;
	}
	
	// actions
	private static final String showScript = "Show Script";
	
	/** action list */
	private String[] actions = {showScript};
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getActionList()
	 */
	public String[] getActionList()
	{
		return actions;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setActionList(java.lang.String[])
	 */
	public void setActionList(String[] actionList)
	{
		actions = actionList;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#doAction(java.lang.String)
	 */
	public void doAction(String action)
	{
		if (action == null) {return;}
		if (showScript.equals(action)) {
			println(getScriptContent());
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.IXmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
		xmlWriter.writeAttribute("implName", "org.primordion.xholon.base.Behavior_" + scriptLanguage);
		xmlWriter.writeText("<![CDATA[\n" + scriptContent + "\n]]>");
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(java.lang.String)
	 */
	public void setVal(String val) {
		setScriptContent(val);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_String()
	 */
	public String getVal_String() {
		return scriptContent;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrVal) {
		if ("lang".equals(attrName)) {
			scriptLanguage = attrVal;
		}
		else if ("val".equals(attrName)) {
			// is this good/necessary?
			setVal(attrVal);
		}
		return 0;
	}

	public String getScriptContent() {
		return scriptContent;
	}

	public void setScriptContent(String scriptContent) {
		this.scriptContent = scriptContent;
		if (scriptConfigured) {
			// this is a replacement for the existing script
			beh = configureJso(this, scriptContent);
		}
	}

	public boolean isHasAct() {
		return hasAct;
	}

	public void setHasAct(boolean hasAct) {
		this.hasAct = hasAct;
	}

	public boolean isHasPostAct() {
		return hasPostAct;
	}

	public void setHasPostAct(boolean hasPostAct) {
		this.hasPostAct = hasPostAct;
	}

	public String getScriptLanguage() {
		return scriptLanguage;
	}

	public void setScriptLanguage(String scriptLanguage) {
		this.scriptLanguage = scriptLanguage;
	}
	
}
