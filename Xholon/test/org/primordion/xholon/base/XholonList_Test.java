package org.primordion.xholon.base;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
import com.google.gwt.junit.client.GWTTestCase;

import org.primordion.user.app.helloworldjnlp.XhHelloWorld;

public class XholonList_Test extends GWTTestCase {
  
  public String getModuleName() {
    return "org.Xholon";
  }
  
	public void gwtSetUp() throws Exception {
	}

	public void gwtTearDown() throws Exception {
	}
	
	public void testBig() {
		//List listA = new java.util.ArrayList();
		List listA = new XholonList();
		
		System.out.println(listA.size() + " " + listA.isEmpty());
		IXholon node;
		
		// add(Object o)
		for (int i = 0; i < 10; i++) {
			node = new XhHelloWorld();
			node.setId(i);
			listA.add(node);
		}
		node = new XhHelloWorld();
		node.setId(10);
		listA.add(node);
		System.out.println(listA.size() + " " + listA.isEmpty());
		System.out.println(listA.get(5));
		System.out.println(listA.contains(node));
		
		// add(int index, Object element)
		node = new XhHelloWorld();
		node.setId(999);
		listA.add(5, node);
		System.out.println(listA.indexOf(node) + " " + listA.lastIndexOf(node));
		
		// addAll(Collection c)
		Collection c1 = new java.util.HashSet();
		for (int i = 0; i < 3; i++) {
			node = new XhHelloWorld();
			node.setId(i + 100);
			c1.add(node);
		}
		listA.addAll(c1);
		System.out.println(listA.containsAll(c1));
		
		// addAll(int index, Collection c)
		Collection c2 = new java.util.ArrayList();
		for (int i = 0; i < 3; i++) {
			node = new XhHelloWorld();
			node.setId(i + 200);
			c2.add(node);
		}
		listA.addAll(3, c2);
		
		// View the list as xholons.
		if (listA instanceof IXholon) {
			System.out.println("\nView the list as xholons:");
			IXholon xhRoot = (IXholon)listA;
			IXholon xhNode = xhRoot.getFirstChild();
			while (xhNode != null) {
				System.out.println("\t" + xhNode);
				xhNode = xhNode.getNextSibling();
			}
		}
		
		// View the list as list elements.
		System.out.println("\nView the list as list elements:");
		for (int i = 0; i < listA.size(); i++) {
			System.out.println("\t" + listA.get(i));
		}
		
		// View the list using an iterator.
		System.out.println("\nView the list using an iterator:");
		Iterator it = listA.iterator();
		while (it.hasNext()) {
			System.out.println("\t" + it.next());
		}
		
		listA.remove(10);
		listA.remove(node);
		// View the list as array elements.
		System.out.println("\nView the list as array elements, after removing 2 elements:");
		Object[] arrayA = listA.toArray();
		for (int i = 0; i < listA.size(); i++) {
			System.out.println("\t" + arrayA[i]);
		}
		
		listA.removeAll(c1);
		// View the list as array elements.
		System.out.println("\nView the list as array elements in a passed in array, after removing a collection:");
		Object[] arrayB = listA.toArray(new Object[100]);
		for (int i = 0; i < listA.size(); i++) {
			System.out.println("\t" + arrayB[i]);
		}
		
		listA.retainAll(c2);
		node = new XhHelloWorld();
		node.setId(1001);
		listA.add(node); // add
		node = new XhHelloWorld();
		node.setId(1002);
		listA.add(node); // add
		node = new XhHelloWorld();
		node.setId(1003);
		listA.set(3, node); // set
		node = new XhHelloWorld();
		node.setId(1004);
		listA.set(2, node); // set
		// view the list as list elements
		System.out.println("\nView the list as list elements, after retaining only 2 elements, and adding/setting 2 more:");
		for (int i = 0; i < listA.size(); i++) {
			System.out.println("\t" + listA.get(i));
		}
		
		// View a subList
		Iterator subList = listA.subList(1, 3).iterator();
		System.out.println("\nView a subList:");
		while(subList.hasNext()) {
			System.out.println("\t" + subList.next());
		}
		System.out.println();
		
		listA.clear();
		System.out.println(listA.size() + " " + listA.isEmpty());
		
		// exceptions
		try {
			listA.add(null);
		} catch (NullPointerException e) {
			System.err.println("Unable to add null to listA.");
		}
		try {
			listA.add(new Integer(12345));
		} catch (ClassCastException e) {
			System.err.println("Unable to add an Integer to listA.");
		}
		node = new XhHelloWorld();
		node.setId(9999);
		try {
			listA.add(100, node);
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Unable to add node to index 100 of listA.");
		}
		for (int i = 0; i < 5; i++) {
			node = new XhHelloWorld();
			node.setId(i + 500);
			listA.add(node);
		}
		Iterator itEx = listA.iterator();
		while (itEx.hasNext()) {
			itEx.next();
			//try {
			//	itEx.remove();
			//} catch (UnsupportedOperationException e) {
			//	System.err.println("Unsupported operation XholonList.iterator().remove().");
			//}
		}
		try {
			itEx.next();
		} catch(NoSuchElementException e) {
			System.err.println("Unable to iterate to a next node in listA.");
		}
	}
}
