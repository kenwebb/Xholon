package org.primordion.xholon.base;

//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
import com.google.gwt.junit.client.GWTTestCase;

public class XholonMap_Test extends GWTTestCase {
  
  public String getModuleName() {
    return "org.Xholon";
  }
  
	public void gwtSetUp() throws Exception {
	}

	public void gwtTearDown() throws Exception {
	}

	/**
	 * TODO this test will require a full Xholon app, including the Application node
	 */
	public void test1()
	{
		java.util.Map mapA = new XholonMap();
		IXholonClass xhc = makeXholonClass();
		System.out.println(xhc.getName());
		
		System.out.println(mapA.size() + " " + mapA.isEmpty());
		
		for (int i = 0; i < 10; i++) {
			String key = "key" + i;
			IXholon value = new XholonNull();
			value.setId(i);
			value.setXhc(xhc);
			System.out.println(value.toString());
			// TODO GWT following causes java.lang.NullPointerException in Xholon.appendChild(Xholon.java:527)
			//mapA.put(key, value);
		}
		
		System.out.println(mapA.size() + " " + mapA.isEmpty());
	}
	
	/**
	 * Make a IXholonClass for testing.
	 * @return
	 */
	protected IXholonClass makeXholonClass()
	{
		IXholonClass xhc = new XholonClass();
		xhc.setId(0);
		xhc.setName("Testing");
		return xhc;
	}
	
}
