package org.primordion.user.app.English2French;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonList;

public class Transforms extends XhEnglish2French {
	
	/** An index of the contents of this lexicon. */
	private XholonList index = null;
	
	private static final String isAlphaOrderedAction = "Is alpha ordered";
	private static final String showIndex            = "Show index";
	/** action list */
	private String[] actions = {isAlphaOrderedAction, showIndex};
	
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
		if (action.equals(isAlphaOrderedAction)) {
			System.out.println("Is the French transform list in proper alphabetical order: " + isAlphaOrdered());
		}
		else if (action.equals(showIndex)) {
			showIndex();
		}
	}
	
	/*
	 * @see org.primordion.user.app.English2French.XhEnglish2French#postConfigure()
	 */
	public void postConfigure()
	{
		createIndex();
		super.postConfigure();
	}
	
	/**
	 * Create an index of the items in the transform list.
	 */
	protected void createIndex()
	{
		// remove index if it already exists
		IXholon node = getIndexNode();
		if (node != null) {
			node.removeChild();
			node.remove();
		}
		// create an index
		index = new XholonList();
		index.setId(getNextId());
		index.setXhc(this.getClassNode(LexiconIndexCE));
		char firstLetter = ' '; // lowest ASCII character
		Transform transform = (Transform)getFirstTransform();
		while (transform != null) {
			String lemmaStr = transform.getSimpleLemma();
			if ((lemmaStr != null) && (lemmaStr.length() > 0)) {
				char lemmaStrFirstLetter = toUnaccentedLower(lemmaStr.charAt(0));
				if (lemmaStrFirstLetter != firstLetter) {
					// there's a new first letter
					firstLetter = lemmaStrFirstLetter; // new first letter
					// create a new index node
					IXholon indexNode = new IndexItem();
					indexNode.setId(getNextId());
					indexNode.setXhc(this.getClassNode(LexiconIndexItemCE));
					// store the first letter, and the first lexeme that begins with that letter
					indexNode.setVal(firstLetter);
					indexNode.setVal(transform);
					// add the index node to the index
					index.add(indexNode);
					// set the roleName of that lexeme, to help the user
					transform.setRoleName("" + Character.toUpperCase(firstLetter) + "   ");
				}
			}
			transform = (Transform)transform.getNextSibling();
		}
		// make the index the first child of the lexicon
		index.insertFirstChild(this);
	}
	
	/**
	 * Get the first transform in the lexicon.
	 * Note that the first child may be a LexiconIndex node rather than a Transform node,
	 * so it's best to call this method rather than getFirstChild().
	 * @return The first transform, or null.
	 */
	protected Transform getFirstTransform()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == TransformCE) {
				return (Transform)node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the LexiconIndex node.
	 * @return The LexiconIndex node, or null.
	 */
	protected XholonList getIndexNode()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == LexiconIndexCE) {
				return (XholonList)node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get a transform that matches a specified lemma.
	 * @param lemmaStr 
	 * @return A matching transform, or null.
	 */
	public Transform getTransform(String lemmaStr)
	{
		if (lemmaStr == null) {return null;}
		if ((lemmaStr == null) || (lemmaStr.length() == 0)) {return null;}
		//Transform transform = (Transform)getFirstChild();
		Transform transform = getIndexedTransform(lemmaStr.charAt(0));
		while (transform != null) {
			if (lemmaStr.equalsIgnoreCase(transform.getCompleteLemma())) {
				return transform;
			}
			transform = (Transform)transform.getNextSibling();
		}
		// still no match; check any supplementary French transforms
		IXholon nextNode = getNextSibling();
		if ((nextNode != null) && (nextNode.getXhcId() == TransformsCE)) {
			return ((Transforms)nextNode).getTransform(lemmaStr);
		}
		return null;
	}
	
	/**
	 * Get the first transform whose first letter matches the specified value.
	 * This method makes use of an index to speed up the operation.
	 * @param firstLetter The first letter of a transform's lemma.
	 * @return A matching transform, or null.
	 */
	protected Transform getIndexedTransform(char firstLetter)
	{
		if (index != null) {
			char firstLetterLower = toUnaccentedLower(firstLetter);
			IXholon[] items = (IXholon[])index.toArray();
			for (int i = 0; i < items.length; i++) {
				IndexItem item = (IndexItem)items[i];
				if (item.getFirstLetter() == firstLetterLower) {
					return (Transform)item.getItem();
				}
			}
		}
		// if not found, just return the very first transform in the transform list
		return (Transform)getFirstTransform();
	}
	
	/**
	 * Show all items in the transform index.
	 */
	public void showIndex()
	{
		System.out.println("Number of items in the transform index: " + index.size());
		IXholon[] items = (IXholon[])index.toArray();
		for (int i = 0; i < items.length; i++) {
			IndexItem item = (IndexItem)items[i];
			System.out.println(item);
		}
	}
	
	/**
	 * Are the elements in this transform list in proper alphabetical order.
	 * Out-of-order or null elements are written to the log.
	 * @return true or false
	 */
	public boolean isAlphaOrdered()
	{
		boolean rc = true;
		Transform transform = (Transform)getFirstTransform();
		while (transform != null) {
			Transform otherTransform = (Transform)transform.getNextSibling();
			if (otherTransform == null) {break;}
			int compareResult = transform.compareTo(otherTransform);
			if (compareResult > 0) {
				rc = false;
				getLogger().error(transform + " and " + otherTransform + " are out of order.");
			}
			transform = otherTransform;
		}
		return rc;
	}
	
}
