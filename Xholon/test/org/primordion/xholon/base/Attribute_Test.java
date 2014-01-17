package org.primordion.xholon.base;

//import static org.junit.Assert.*;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
import com.google.gwt.junit.client.GWTTestCase;

import org.primordion.xholon.base.Attribute.Attribute_double;
import org.primordion.xholon.base.Attribute.Attribute_int;
import org.primordion.xholon.base.Attribute.Attribute_arraybyte;
import org.primordion.xholon.base.Attribute.Attribute_arrayint;

public class Attribute_Test extends GWTTestCase {
  
  public String getModuleName() {
    return "org.Xholon";
  }
  
	public void gwtSetUp() throws Exception {
	}

	public void gwtTearDown() throws Exception {
	}

	/*public void testGetName() {
		fail("Not yet implemented");
	}

	public void testIsContainer() {
		fail("Not yet implemented");
	}

	public void testIsActiveObject() {
		fail("Not yet implemented");
	}

	public void testIsPassiveObject() {
		fail("Not yet implemented");
	}

	public void testPostConfigure() {
		fail("Not yet implemented");
	}

	public void testSetRoleName() {
		fail("Not yet implemented");
	}

	public void testGetRoleName() {
		fail("Not yet implemented");
	}

	public void testToXml() {
		fail("Not yet implemented");
	}

	public void testToString() {
		fail("Not yet implemented");
	}

	public void testAttribute() {
		fail("Not yet implemented");
	}

	public void testGetAttributeJavaClass() {
		fail("Not yet implemented");
	}

	public void testGetAttributeJavaClassType() {
		fail("Not yet implemented");
	}*/

	public void testCountTokens() {
		Attribute_arrayint a = new Attribute_arrayint();
		assertEquals(4, a.countTokens("1,2,3,4"));
	}

	public void testAttribute_double() {
		IAttribute b = new Attribute_double();
		b.setRoleName("Testing_ThisIsADoubleVal");
		b.setVal(10.0);
		b.incVal(5.0);
		b.decVal(2.0);
		((Attribute_double)b).val += 20.0;
		System.out.println(b.getRoleName() + " result: " + b.getVal_double());
		System.out.println("double result: " + b.getVal() + " " + b.getVal_double());
		System.out.println("double result as String: " + b.getVal_String());
		System.out.println("double result as Object: " + b.getVal_Object());
		b.setVal("1234.4321");
		System.out.println("double result: " + b.getVal() + " " + b.getVal_double());
		b.setVal(new Double(5678.8765));
		System.out.println("double result: " + b.getVal() + " " + b.getVal_double());
	}
	
	public void testAttribute_int() {
		IAttribute a = new Attribute_int();
		a.setVal(10);
		a.incVal(5);
		a.decVal(2);
		((Attribute_int)a).val += 20;
		System.out.println("int result: " + a.getVal() + " " + a.getVal_int());
		System.out.println("int result as String: " + a.getVal_String());
		System.out.println("int result as Object: " + a.getVal_Object());
		a.setVal("1234");
		System.out.println("int result: " + a.getVal() + " " + a.getVal_int());
		a.setVal(new Integer(5678));
		System.out.println("int result: " + a.getVal() + " " + a.getVal_int());
	}
	
	public void testAttribute_arraybyte() {
		IAttribute a = new Attribute_arraybyte();
		a.setVal("1,2,3,4");
		System.out.println(a.toString());
		a.setVal("This is a normal String.");
		System.out.println(a.toString());
	}
	
	public void testAttribute_arrayint() {
		IAttribute a = new Attribute_arrayint();
		a.setVal("1,2,3,4");
		System.out.println(a.toString());
	}
	
}
