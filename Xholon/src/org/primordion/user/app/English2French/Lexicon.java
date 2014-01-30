package org.primordion.user.app.English2French;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonList;
import org.primordion.xholon.util.MiscRandom;

public class Lexicon extends XhEnglish2French {
	
	/** An index of the contents of this lexicon. */
	private XholonList index = null;
	
	private static final String isAlphaOrderedAction = "Is alpha ordered";
	private static final String showNounPlurals      = "Show noun plurals";
	private static final String showIndex            = "Show index";
	/** action list */
	private String[] actions = {isAlphaOrderedAction, showNounPlurals, showIndex};
	
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
			System.out.println("Is the English lexicon in proper alphabetical order: " + isAlphaOrdered());
		}
		else if (action.equals(showNounPlurals)) {
			showAllNounPlurals();
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
	 * Get the first lexeme in the lexicon.
	 * Note that the first child may be a LexiconIndex node rather than a Lexeme node,
	 * so it's best to call this method rather than getFirstChild().
	 * @return The first lexeme, or null.
	 */
	protected Lexeme getFirstLexeme()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == LexemeCE) {
				return (Lexeme)node;
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
	 * Create an index of the items in the lexicon.
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
		Lexeme lexeme = (Lexeme)getFirstLexeme();
		while (lexeme != null) {
			String lemmaStr = lexeme.getLemmaString();
			if ((lemmaStr != null) && (lemmaStr.length() > 0)) {
				char lemmaStrFirstLetter = Character.toLowerCase(lemmaStr.charAt(0));
				if (lemmaStrFirstLetter != firstLetter) {
					// there's a new first letter
					firstLetter = lemmaStrFirstLetter; // new first letter
					// create a new index node
					IXholon indexNode = new IndexItem();
					indexNode.setId(getNextId());
					indexNode.setXhc(this.getClassNode(LexiconIndexItemCE));
					// store the first letter, and the first lexeme that begins with that letter
					indexNode.setVal(firstLetter);
					indexNode.setVal(lexeme);
					// add the index node to the index
					index.add(indexNode);
					// set the roleName of that lexeme, to help the user
					lexeme.setRoleName("" + Character.toUpperCase(firstLetter) + "   ");
				}
			}
			lexeme = (Lexeme)lexeme.getNextSibling();
		}
		// make the index the first child of the lexicon
		index.insertFirstChild(this);
	}
	
	/**
	 * Get a lexeme that matches a specified lemma.
	 * @param lemma An English word from the sentence being translated.
	 * @return A matching lexeme, or null.
	 */
	public Lexeme getLexeme(IXholon lemma)
	{
		//System.out.println("getLexeme() 1: " + lemma + " " + this);
		if (lemma == null) {return null;}
		String lemmaStr = lemma.getVal_String();
		if ((lemmaStr == null) || (lemmaStr.length() == 0)) {return null;}
		//Lexeme lexeme = (Lexeme)getFirstChild();
		Lexeme lexeme = getIndexedLexeme(lemmaStr.charAt(0));
		while (lexeme != null) {
			if (lemmaStr.equalsIgnoreCase(lexeme.getLemmaString())) {
				return getLongestMatch(lemmaStr, lemma, lexeme);
			}
			lexeme = (Lexeme)lexeme.getNextSibling();
		}
		// no match; check if this might be a plural noun
		lexeme = getLexemeMayBePlural(lemmaStr);
		if (lexeme != null ) {
			return lexeme;
		}
		// still no match; check any supplementary English lexicons
		IXholon nextNode = getNextSibling();
		if ((nextNode != null) && (nextNode.getXhcId() == LexiconCE)) {
			return ((Lexicon)nextNode).getLexeme(lemma);
		}
		return null;
	}
	
	/**
	 * Get the first lexeme whose first letter matches the specified value.
	 * This method makes use of an index to speed up the operation.
	 * @param firstLetter The first letter of a lexeme's lemma.
	 * @return A matching lexeme, or null.
	 */
	protected Lexeme getIndexedLexeme(char firstLetter)
	{
		if (index != null) {
			char firstLetterLower = Character.toLowerCase(firstLetter);
			IXholon[] items = (IXholon[])index.toArray();
			for (int i = 0; i < items.length; i++) {
				IndexItem item = (IndexItem)items[i];
				if (item.getFirstLetter() == firstLetterLower) {
					return (Lexeme)item.getItem();
				}
			}
		}
		// if not found, just return the very first lexeme in the lexicon
		return (Lexeme)getFirstLexeme();
	}
	
	/**
	 * Get a lexeme that matches a possibly plural noun.
	 * This method is only designed to handle nouns.
	 * @param lemmaStr A single word (ex: girls)
	 * @return A matching lexeme, or null
	 */
	protected Lexeme getLexemeMayBePlural(String lemmaString)
	{
		Lexeme lexeme = null;
		int len = lemmaString.length();
		if (lemmaString.endsWith("s")) {
			// it ends with s, so it might be a plural noun
			lexeme = getLexeme(lemmaString.substring(0, len-1));
			if ((lexeme != null) && (lexeme.getPos().getXhcId() == PosNCE)) {return lexeme;}
		}
		else if (lemmaString.endsWith("es")) {
			// it ends with es, so it might be a plural noun
			lexeme = getLexeme(lemmaString.substring(0, len-2));
			if ((lexeme != null) && (lexeme.getPos().getXhcId() == PosNCE)) {return lexeme;}
		}
		else if (lemmaString.endsWith("ies")) {
			// it ends with ies, so it might be a plural noun
			lexeme = getLexeme(lemmaString.substring(0, len-3)+"y");
			if ((lexeme != null) && (lexeme.getPos().getXhcId() == PosNCE)) {return lexeme;}
		}
		//return lexeme;
		return null;
	}
	
	/**
	 * Get a lexeme that matches a specified String.
	 * The String may consist of only one word.
	 * @param lemmaString A single word (ex: girl)
	 * @return A matching lexeme, or null.
	 */
	public Lexeme getLexeme(String lemmaString)
	{
		//System.out.println("getLexeme() 2: " + lemmaString);
		if ((lemmaString == null) || (lemmaString.length() == 0)) {return null;}
		//Lexeme lexeme = (Lexeme)getFirstChild();
		Lexeme lexeme = getIndexedLexeme(lemmaString.charAt(0));
		while (lexeme != null) {
			if (lemmaString.equalsIgnoreCase(lexeme.getLemmaString())) {
				return lexeme;
				// TODO be able to use the following
				//return getLongestMatch(lemmaString, lemma, lexeme);
			}
			lexeme = (Lexeme)lexeme.getNextSibling();
		}
		// no match; check if this might be a plural noun
		lexeme = getLexemeMayBePlural(lemmaString);
		if (lexeme != null ) {
			return lexeme;
		}
		// still no match; check any supplementary English lexicons
		IXholon nextNode = getNextSibling();
		if ((nextNode != null) && (nextNode.getXhcId() == LexiconCE)) {
			return ((Lexicon)nextNode).getLexeme(lemmaString);
		}
		return null;
	}
	
	/**
	 * Get a lexeme that matches a specified lemma and part of speech.
	 * @param lemma An English word from the sentence being translated.
	 * @param pos English part of speech (POS) for that word.
	 * @return A matching lexeme, or null.
	 */
	public Lexeme getLexeme(IXholon lemma, String pos)
	{
		//System.out.println("getLexeme() 3");
		if (lemma == null) {return null;}
		String lemmaStr = lemma.getVal_String();
		if ((lemmaStr == null) || (lemmaStr.length() == 0)) {return null;}
		if (pos == null) {return null;}
		//Lexeme lexeme = (Lexeme)getFirstChild();
		Lexeme lexeme = getIndexedLexeme(lemmaStr.charAt(0));
		while (lexeme != null) {
			if ((lemmaStr.equalsIgnoreCase(lexeme.getLemmaString())) && (pos.equals(lexeme.getPosString()))) {
				// TODO need a new version of getLongestMatch() that handles POS
				return getLongestMatch(lemmaStr, lemma, lexeme);
			}
			lexeme = (Lexeme)lexeme.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the lexeme in the lexicon that matches this lemma and its siblings.
	 * This method assumes that the lexicon is in alphabetical order.
	 * @param concatLemmas A concatenated String containing one or more English words from the sentence.
	 * @param lemma The current English word from the sentence being translated.
	 * @param lexeme A word or phrase from the English lexicon.
	 * @return The argument lexeme, or a longer one.
	 */
	protected Lexeme getLongestMatch(String concatLemmas, IXholon lemma, Lexeme lexeme)
	{
		IXholon newLemma = lemma.getNextSibling();
		if (newLemma == null) {return lexeme;}
		Lexeme newLexeme = (Lexeme)lexeme.getNextSibling();
		System.out.print("lemma:" + lemma + "\nlexeme:" + lexeme);
		System.out.print("\nnewLemma:" + newLemma + "\nnewLexeme:" + newLexeme);
		while ((newLexeme != null) && (newLexeme.getLemmaString().startsWith(concatLemmas + " "))) {
			System.out.print(" --> " + newLexeme.getLemmaString() + " | " + concatLemmas + " " + newLemma.getVal_String());
			if (newLexeme.getLemmaString().equalsIgnoreCase(concatLemmas + " " + newLemma.getVal_String())) {
				System.out.print("\n\n");
				return getLongestMatch(concatLemmas + " " + newLemma.getVal_String(), newLemma, newLexeme);
			}
			newLexeme = (Lexeme)newLexeme.getNextSibling();
			System.out.print("\nnewLexeme:" + newLexeme);
		}
		System.out.print("\n\n");
		return lexeme;
	}
	
	/**
	 * Print out the plurals of all nouns in the lexicon.
	 */
	public void showAllNounPlurals()
	{
		Lexeme lexeme = (Lexeme)getFirstLexeme();
		while (lexeme != null) {
			if (lexeme.isNoun()) {
				System.out.println(lexeme.getLemmaString() + " " + lexeme.getLemmaStringPluralized());
			}
			lexeme = (Lexeme)lexeme.getNextSibling();
		}
	}
	
	/**
	 * Show all items in the lexicon index.
	 */
	public void showIndex()
	{
		System.out.println("Number of items in the lexicon index: " + index.size());
		IXholon[] items = (IXholon[])index.toArray();
		for (int i = 0; i < items.length; i++) {
			IndexItem item = (IndexItem)items[i];
			System.out.println(item);
		}
	}
	
	/**
	 * Are the elements in this lexicon in proper alphabetical order.
	 * Out-of-order or null elements are written to the log.
	 * @return true or false
	 */
	public boolean isAlphaOrdered()
	{
		boolean rc = true;
		Lexeme lexeme = (Lexeme)getFirstLexeme();
		while (lexeme != null) {
			Lexeme otherLexeme = (Lexeme)lexeme.getNextSibling();
			if (otherLexeme == null) {break;}
			int compareResult = lexeme.compareTo(otherLexeme);
			if (compareResult > 0) {
				rc = false;
				getLogger().error(lexeme + " and " + otherLexeme + " are out of order.");
			}
			lexeme = otherLexeme;
		}
		return rc;
	}
	
	/**
	 * Get a random lexeme from the English lexicon.
	 * @return A random lexeme, or null.
	 */
	public Lexeme getRandomLexeme()
	{
		IXholon lexeme = getFirstLexeme();
		int numLexemes = lexeme.getNumSiblings();
		int rNum = MiscRandom.getRandomInt(0, numLexemes);
		return (Lexeme)lexeme.getNthSibling(rNum);
	}

	
}
