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

package org.primordion.xholon.util;

//import static org.junit.Assert.*;
//import org.junit.Test;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * JUnit test case.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3
 */
public class Misc_Test extends GWTTestCase {
  
  public String getModuleName() {
    return "org.Xholon";
  }
  
	public void testAtod() {
		assertEquals("[atod 1]", 1234.0, Misc.atod("1234.0", 0), 0.0);
		assertEquals("[atod 2]", 1234.0, Misc.atod("hello1234.0", 5), 0.0);
		assertEquals("[atod 3]", 1234.0, Misc.atod("hello1234.0world", 5), 0.0);
		assertEquals("[atod 5]", -4321.0, Misc.atod("-4321.0", 0), 0.0);
	}
	
	public void testAtodNullStr() {
		//assertEquals("[atod 4]", 0.0, Misc.atod("", 0), 0.0); // bug
	}
	
	public void testAtodNoNums() {
		//assertEquals("[atod 6]", 0.0, Misc.atod("helloworld", 5), 0.0); // bug
	}

	public void testAtof() {
		assertEquals("[atof 1]", 1234.0f, Misc.atof("1234.0", 0), 0.0);
		assertEquals("[atof 2]", 1234.0f, Misc.atof("hello1234.0", 5), 0.0);
		assertEquals("[atof 3]", 1234.0f, Misc.atof("hello1234.0world", 5), 0.0);
		assertEquals("[atof 5]", -4321.0f, Misc.atof("-4321.0", 0), 0.0);
	}
	
	public void testAtofNullStr() {
		//assertEquals("[atof 4]", 0.0f, Misc.atof("", 0), 0.0); // bug
	}
	
	public void testAtofNoNums() {
		//assertEquals("[atof 6]", 0.0f, Misc.atof("helloworld", 5), 0.0); // bug
	}

	public void testAtoi() {
		assertEquals("[atoi 1]", 1234, Misc.atoi("1234", 0));
		assertEquals("[atoi 2]", 1234, Misc.atoi("hello1234", 5));
		assertEquals("[atoi 3]", 1234, Misc.atoi("hello1234world", 5));
		assertEquals("[atoi 5]", -4321, Misc.atoi("-4321", 0));
	}
	
	public void testAtoiNullStr() {
		//assertEquals("[atoi 4]", 0, Misc.atoi("", 0)); // bug
	}
	
	public void testAtoiNoNums() {
		//assertEquals("[atoi 6]", 0, Misc.atoi("helloworld", 5)); // bug
	}

	public void testIsdigit() {
		assertEquals("[isDigit 1]", true, Misc.isdigit(0));
		assertEquals("[isDigit 2]", true, Misc.isdigit(9));
		assertEquals("[isDigit 3]", true, Misc.isdigit(5));
		assertEquals("[isDigit 4]", false, Misc.isdigit(-1));
		assertEquals("[isDigit 5]", false, Misc.isdigit(10));
	}

}
