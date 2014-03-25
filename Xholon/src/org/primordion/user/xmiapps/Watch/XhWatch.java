package org.primordion.user.xmiapps.Watch;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.XholonWithPorts;

/**
	Watch application - Xh Java class
	<p>Note: the code generation is currently incomplete.</p>
	<p>Author: Compaq_Administrator</p>
	<p>Date:   23/03/2007</p>
	<p>File:   Watch.zuml</p>
	<p>Target: Xholon 0.4 http://www.primordion.com/Xholon</p>
	<p>UML: Poseidon 3.2 or 4.2</p>
	<p>XMI: 1.2, Thu Oct 12 18:38:39 EDT 2006, Netbeans XMI Writer</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhWatch extends XholonWithPorts implements CeWatch {

// references to other Xholon instances; indices into the port array
public static final int P_LIGHT_CMD = 0;
public static final int P_BEEP_CMD = 0;
public static final int P_TIMEDATE_CNTRL = 0;
public static final int P_TIMER_TICK = 0;
public static final int P_DISPLAY_CMD = 0;
public static final int P_BUTTON_S1 = 0;
public static final int P_BUTTON_S2 = 1;
public static final int P_BUTTON_S3 = 2;
public static final int P_BUTTON_S4 = 3;
public static final int P_GUIEVENT123 = 0;
public static final int P_GUIEVENT4 = 1;
public static final int P_GUIEVENT12 = 2;

// Signals, Events
public static final int SHOW_DATE = 370;
public static final int SHOW_TIME = 360;
public static final int INCREMENT_MONTH = 330;
public static final int INCREMENT_SEC = 300;
public static final int INCREMENT_MIN = 310;
public static final int INCREMENT_HOUR = 320;
public static final int INCREMENT_DAY = 340;
public static final int INCREMENT_DATE = 350;
public static final int BEEP_CONTINUOUSLY = 700;
public static final int STOP_BEEPING = 720;
public static final int S1 = 200;
public static final int S2 = 210;
public static final int S3 = 220;
public static final int S4 = 230;
public static final int NOTS1 = 240;
public static final int NOTS2 = 250;
public static final int NOTS3 = 260;
public static final int NOTS4 = 270;
public static final int S3FOR2SECONDS = 280;

	public void performActivity(int activityId, IMessage msg)
	{
		switch (activityId) {
		case 0x294: // s1
			port[P_TIMEDATE_CNTRL].sendMessage(SHOW_DATE, null, this);
			break;
		case 0x28d: // notS1
			port[P_TIMEDATE_CNTRL].sendMessage(SHOW_TIME, null, this);
			break;
		case 0x7c22: // s1
			// no action specified
			break;
		case 0x7c42: // s1
			// no action specified
			break;
		case 0x7cc6: // s2
			port[P_TIMEDATE_CNTRL].sendMessage(INCREMENT_MONTH, null, this);
			break;
		case 0x7c14: // s1
			// no action specified
			break;
		case 0x7c0d: // s1
			// no action specified
			break;
		case 0x7bff: // s1
			// no action specified
			break;
		case 0x7c29: // s1
			// no action specified
			break;
		case 0x307: // s2
			port[P_TIMEDATE_CNTRL].sendMessage(INCREMENT_DAY, null, this);
			break;
		case 0x2f3: // s2
			port[P_TIMEDATE_CNTRL].sendMessage(INCREMENT_DATE, null, this);
			break;
		case 0x2df: // s2
			port[P_TIMEDATE_CNTRL].sendMessage(INCREMENT_SEC, null, this);
			break;
		case 0x2cf: // s2
			port[P_TIMEDATE_CNTRL].sendMessage(INCREMENT_MIN, null, this);
			break;
		case 0x2b7: // s2
			port[P_TIMEDATE_CNTRL].sendMessage(INCREMENT_HOUR, null, this);
			break;
		case 0x67a2: // s2
			port[P_BEEP_CMD].sendMessage(BEEP_CONTINUOUSLY, null, this);
			break;
		case 0x67a9: // s1
			port[P_BEEP_CMD].sendMessage(BEEP_CONTINUOUSLY, null, this);
			break;
		case 0x679b: // notS2
			port[P_BEEP_CMD].sendMessage(STOP_BEEPING, null, this);
			break;
		case 0x6794: // notS1
			port[P_BEEP_CMD].sendMessage(STOP_BEEPING, null, this);
			break;
		default:
			println("XhWatch: unknown Activity" + activityId);
			break;
		}
	}
}
