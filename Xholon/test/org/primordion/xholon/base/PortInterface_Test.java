/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2006, 2007, 2008 Ken Webb
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

//import static org.junit.Assert.*;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * JUnit test case.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3
 */
public class PortInterface_Test extends GWTTestCase {

	// test signals
	public static final int SIG_A = 100;
	public static final int SIG_B = 110;
	public static final int SIG_C = 120;
	public static final int SIG_D = 200;
	public static final int SIG_E = 210;
	
	IPortInterface portIf;
	
	public String getModuleName() {
    return "org.Xholon";
  }
  
	public void gwtSetUp() throws Exception {
		portIf = new PortInterface();
		int[] nterface = {SIG_A, SIG_B, SIG_C};
		portIf.setInterface(nterface);
		String[] nterfaceNames = {"SIG_A", "SIG_B", "SIG_C"};
		portIf.setInterfaceNames(nterfaceNames);
	}

	public void gwtTearDown() throws Exception {
	}

	public void testGetSignalId() {
		assertEquals("sig A", SIG_A, portIf.getSignalId("SIG_A"));
		assertEquals("sig B", SIG_B, portIf.getSignalId("SIG_B"));
		assertEquals("sig C", SIG_C, portIf.getSignalId("SIG_C"));
		assertEquals("sig BAD", IPortInterface.SIGNAL_ID_NOT_FOUND, portIf.getSignalId("SIG_BAD"));
		// 
		portIf.setInterfaceNames(null);
		assertEquals("interface names null", IPortInterface.SIGNAL_ID_NOT_FOUND, portIf.getSignalId("SIG_A"));
		// 
		String[] nterfaceNames = {"SIG_A", "SIG_B"};
		portIf.setInterfaceNames(nterfaceNames);
		assertEquals("interface names different length from interface IDs",
				IPortInterface.SIGNAL_ID_NOT_FOUND, portIf.getSignalId("SIG_A"));
	}

	public void testGetSignalName() {
		assertEquals("sig A", "SIG_A", portIf.getSignalName(SIG_A));
		assertEquals("sig B", "SIG_B", portIf.getSignalName(SIG_B));
		assertEquals("sig C", "SIG_C", portIf.getSignalName(SIG_C));
		assertEquals("sig BAD", "1234", portIf.getSignalName(1234));
		// 
		portIf.setInterfaceNames(null);
		assertEquals("interface names null", "100", portIf.getSignalName(SIG_A));
		// 
		String[] nterfaceNames = {"SIG_A", "SIG_B"};
		portIf.setInterfaceNames(nterfaceNames);
		assertEquals("sig A short", "SIG_A", portIf.getSignalName(SIG_A));
		assertEquals("interface names different length from interface IDs",
				"120", portIf.getSignalName(SIG_C));
	}

}
