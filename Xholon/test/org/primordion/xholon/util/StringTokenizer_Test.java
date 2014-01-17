package org.primordion.xholon.util;

//import static org.junit.Assert.*;
//import org.junit.Test;
import com.google.gwt.junit.client.GWTTestCase;

/**

test1:
one
two
three
four
five

test2:
one.two,three.four.five

test3:
0 []
1 [one]
2 [two]
3 [three]
4 [four]

 */
public class StringTokenizer_Test extends GWTTestCase {
  
  public String getModuleName() {
    return "org.Xholon";
  }
  
	public void test1()
	{
		System.out.println("\ntest1:");
		StringTokenizer st = new StringTokenizer("one.two,three.four.five", ".,");
		assertEquals("[test1]", 5, st.countTokens());
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			System.out.println(token);
		}
	}

	public void test2()
	{
		System.out.println("\ntest2:");
		StringTokenizer st = new StringTokenizer("one.two,three.four.five");
		assertEquals("test2", 1, st.countTokens());
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			System.out.println(token);
		}
	}

	public void test3()
	{
		System.out.println("\ntest3:");
		StringTokenizer st = new StringTokenizer(".one.two,,three.four.five.,", ".,");
		assertEquals("[test1]", 5, st.countTokens());
		int index = 0;
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			System.out.println(index + " [" + token + "]");
			index++;
		}
	}
}
