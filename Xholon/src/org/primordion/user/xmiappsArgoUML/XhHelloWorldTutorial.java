package org.primordion.user.xmiappsArgoUML;

import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.XholonWithPorts;

/**
	HelloWorldTutorial application - Xh Java class
	<p>Note: the code generation is currently incomplete.</p>
	<p>Author: Compaq_Administrator</p>
	<p>Date:   Wed Sep 12 10:58:06 EDT 2007</p>
	<p>File:   HelloWorldTutorial.xmi</p>
	<p>Target: Xholon 0.7 http://www.primordion.com/Xholon</p>
	<p>UML: ArgoUML 0.24</p>
	<p>XMI: 1.2, Tue Sep 11 19:25:34 EDT 2007, ArgoUML (using Netbeans XMI Writer version 1.0)</p>
	<p>XSLT: 1.0, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhHelloWorldTutorial extends XholonWithPorts implements CeHelloWorldTutorial {

// references to other Xholon instances; indices into the port array
public static final int Partner = 0;

// Signals, Events
public static final int SIGNAL_ONE = 123;

	public void performActivity(int activityId, Message msg)
	{
		switch (activityId) {
		case 0x0000829: // StateMachine_Hello
			print("{Hello ");
port[Partner].sendMessage(SIGNAL_ONE, null, this);
			break;
		case 0x0BA1: // StateMachine_Hello
			print("{Hello ");
port[Partner].sendMessage(SIGNAL_ONE, null, this);
			break;
		case 0x0000873: // SayingWorld
			println("World}");
port[Partner].sendMessage(SIGNAL_ONE, null, this);
			break;
		default:
			println("XhHelloWorldTutorial: unknown Activity" + activityId);
			break;
		}
	}
}
