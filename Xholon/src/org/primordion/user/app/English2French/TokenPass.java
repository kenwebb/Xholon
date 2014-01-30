package org.primordion.user.app.English2French;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.util.StringTokenizer;

public class TokenPass extends XhEnglish2French {
	
	/** question verbs */
	String[] questionVerb = {
			"am","is","are","was","were",
			"do","does","did",
			"can","must","could",
			"has","have","had",
			"will","would"
	};
	
	/** Has the sentence been normalized yet? */
	private boolean normalized = false;
	
	/** Has the sentence been normalized yet? */
	public boolean isNormalized() {
		return normalized;
	}
	
	/** Set whether the sentence been normalized yet. */
	public void setNormalized(boolean normalized) {
		this.normalized = normalized;
	}

	/*
	 * @see org.primordion.user.app.English2French.XhEnglish2French#postConfigure()
	 */
	public void postConfigure()
	{
		doTokenPass();
		super.postConfigure();
	}
	
	/**
	 * Do token pass.
	 * Transform each word that appears in the English sentence,
	 * into a separate token in its own Xholon instance.
	 */
	protected void doTokenPass()
	{
		if (hasChildNodes()) {return;} // only do this one time
		String sentence = parentNode.getVal_String();
		StringTokenizer st = new StringTokenizer(sentence, " ");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			IXholon node = appendChild("Token", null, "org.primordion.user.app.English2French.XhEnglish2French");
			if (isPunctuated(token)) {
				node.setVal(token.substring(0, token.length()-1));
				node = appendChild("Token", null, "org.primordion.user.app.English2French.XhEnglish2French");
				node.setVal(token.substring(token.length()-1));
			}
			else {
				node.setVal(token);
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#handleNodeSelection()
	 */
	public Object handleNodeSelection()
	{
		normalize();
		return toString();
	}
	
	/**
	 * Does this String end with a punctuation mark (. or ? or ,)
	 * @param str A word that may end with a punctuation mark.
	 * @return true or false
	 */
	protected boolean isPunctuated(String str)
	{
		if (str.endsWith(".")) {
			return true;
		}
		if (str.endsWith("?")) {
			return true;
		}
		if (str.endsWith(",")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Normalize the sentence.
	 * This only applies if the sentence is a question.
	 */
	public void normalize()
	{
		// only normalize the sentence once
		if (normalized == true) {return;}
		if (!isQuestion()) {return;}
		if (isYesNoQuestion()) {
			IXholon tagPart = getTagPart();
			if (tagPart != null) {
				// cross out the tag part at the end of the question.
				deleteTagPart(tagPart);
				// in its place write "_NEGQUESTION"
				getLastChild().insertBefore("Token", null).setVal("_NEGQUESTION");
			}
			else {
				if (isObviousQuestion()) {
					// write "_QUESTION" in front of the sentence
					getFirstChild().insertBefore("Token", null).setVal("_QUESTION");
				}
				else {
					// do nothing; leaving "?" at the end of the sentence
				}
			}
		}
		else {
			IXholon prep = getEndsWithPreposition();
			if (prep != null) {
				// move the preposition to the beginning of the sentence
				prep.removeChild();
				prep.insertFirstChild(this);
				IXholon qVerb = getQuestionVerb();
				if (qVerb != null) {
					// write "_WH_QUESTION" before the first occurrence of any of the words in the verb list
					qVerb.insertBefore("Token", null).setVal("_WH_QUESTION");
				}
			}
			else {
				IXholon qVerb = getQuestionVerb();
				if (qVerb != null) {
					// write "_WH_QUESTION" before the first occurrence of any of the words in the verb list
					qVerb.insertBefore("Token", null).setVal("_WH_QUESTION");
				}
			}
		}
		normalized = true;
	}
	
	/**
	 * Is the sentence a question?
	 * @return true or false
	 */
	protected boolean isQuestion()
	{
		IXholon lastToken = getLastChild();
		if (lastToken == null) {return false;}
		String str = lastToken.getVal_String();
		if ((str == null) || (str.length() == 0)) {return false;}
		if (str.charAt(0) == '?') {
			return true;
		}
		return false;
	}
	
	/**
	 * Can the question be answered by a simple "yes" or "no"?
	 * @return true or false
	 */
	protected boolean isYesNoQuestion()
	{
		return ask4YesNoAnswer("Can the following question be answered by a simple \"yes\" or \"no\"?", this);
	}
	
	/**
	 * Does the question end with a preposition?
	 * @return 
	 */
	protected IXholon getEndsWithPreposition()
	{
		// get end of sentence
		IXholon node = getLastChild();
		// locate the question mark at the end
		while (node != null) {
			String str = node.getVal_String();
			if ((str != null) && (str.equals("?"))) {
				break;
			}
			node = node.getPreviousSibling();
		}
		if (node == null) {return null;} // question mark not found
		// get the node just before the question mark
		node = node.getPreviousSibling();
		if (node == null) {return null;}
		// is this node a preposition
		if (isPrepostion(node)) {
			return node;
		}
		return null;
	}
	
	/**
	 * Is the specified node an English preposition?
	 * @param node A token node.
	 * @return true or false
	 */
	protected boolean isPrepostion(IXholon node)
	{
		String str = node.getVal_String();
		if (str == null) {return false;}
		// search through the English lexicon for this node
		IXholon lexicon = getXPath().evaluate("ancestor::Fr2EngSystem/Lexicon", this);
		if (lexicon != null) {
			Lexeme lexeme = ((Lexicon)lexicon).getLexeme(str);
			if (lexeme != null) {
				IXholon pos = lexeme.getPos();
				if (pos != null) {
					if (pos.getXhcId() == PosPCE) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Does the question contain one of the words in a list of verbs?
	 * @return The first occurring question verb, or null.
	 */
	protected IXholon getQuestionVerb()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			String str = node.getVal_String();
			if (str == null) {continue;}
			for (int i = 0; i < questionVerb.length; i++) {
				if (str.equalsIgnoreCase(questionVerb[i])) {
					return node;
				}
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Is it a tag question?
	 * @return The first node (a comma) in the tag part of the question, or null.
	 */
	protected IXholon getTagPart()
	{
		boolean answer = ask4YesNoAnswer(
			"Is the following a tag question (ex: She's happy, isn't she?)?", this);
		if (answer == false) {return null;}
		// find last comma in the sentence, working back from the question mark
		IXholon node = getLastChild();
		while (node != null) {
			String str = node.getVal_String();
			if ((str != null) && (str.length() > 0)) {
				if (str.charAt(0) == ',') {
					return node;
				}
			}
			node = node.getPreviousSibling();
		}
		return null;
	}
	
	/**
	 * Delete the tag part of the question,
	 * while keeping the starting comma and the final question mark.
	 * @param tagPart The first node (a comma) of the tag part.
	 */
	protected void deleteTagPart(IXholon tagPart)
	{
		IXholon node = tagPart.getNextSibling();
		while (node != null) {
			IXholon nextNode = node.getNextSibling();
			String str = node.getVal_String();
			if (str != null && str.equals("?")) {return;}
			node.removeChild();
			node = nextNode;
		}
	}
	
	/**
	 * If the "?" is removed, can you still tell if it's a question?
	 * @return true or false
	 */
	protected boolean isObviousQuestion()
	{
		return true;
	}
	
}
