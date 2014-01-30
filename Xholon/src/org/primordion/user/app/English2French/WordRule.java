package org.primordion.user.app.English2French;

import org.primordion.xholon.base.IXholon;

public class WordRule extends XhEnglish2French {
	
	// French special characters: �� ��� � � �� �
	
	/**
	 * Do a word rule.
	 * @param tokenNode
	 * @return
	 */
	public WordRuleResult doRule(IXholon tokenNode)
	{
		if (tokenNode == null) {return null;}
		String ruleName = getVal_String();
		if (ruleName == null) {return null;}
		//System.out.println("Doing wordRule: " + ruleName);
		if (ruleName.equals("BE_PRESENT")) {return doRule_BePresent(tokenNode);}
		if (ruleName.equals("BE_PAST")) {return doRule_BePast(tokenNode);}
		if (ruleName.equals("HAVE_PRESENT")) {return doRule_HavePresent(tokenNode);}
		if (ruleName.equals("LEFT")) {return doRule_Left(tokenNode);}
		if (ruleName.equals("THIS_THAT")) {return doRule_ThisThat(tokenNode);}
		if (ruleName.equals("QUESTION")) {return doRule_Question(tokenNode);}
		if (ruleName.equals("NEGQUESTION")) {return doRule_NegQuestion(tokenNode);}
		if (ruleName.equals("WH_QUESTION")) {return doRule_WhQuestion(tokenNode);}
		return null;
	}
	
	/**
	 * Do word rule BE_PRESENT.
	 * @param tokenNode A token in the English sentence.
	 * @return A transform ref.
	 */
	protected WordRuleResult doRule_BePresent(IXholon tokenNode)
	{
		// Does one of the remaining words in the sentence end in "ing"?
		IXholon nextTokenNode = tokenNode.getNextSibling();
		while (nextTokenNode != null) {
			String str = nextTokenNode.getVal_String();
			if (str.endsWith("ing")) {
				// ask the user a question
				boolean answer = ask4YesNoAnswer("Does the following phrase still make sense if you cross out \"am\", \"is\", \"are\", or \"be\", and \"ing\"?",
						tokenNode.getParentNode().getParentNode());
				if (answer == true) {
					//System.out.println(str.substring(0, str.length()-3));
					Lexeme lexeme = getLexeme(str.substring(0, str.length()-3));
					if (lexeme == null) {return null;}
					else {return new WordRuleResult(lexeme.getTransformRefString(), WordRuleResult.MI_NULL);}
				}
				else {
					return new WordRuleResult("�tre", WordRuleResult.MI_NULL);
				}
			}
			nextTokenNode = nextTokenNode.getNextSibling();
		}
		return new WordRuleResult("�tre", WordRuleResult.MI_NULL);
	}
	
	/**
	 * Do word rule BE_PAST.
	 * @param tokenNode A token in the English sentence.
	 * @return A transform ref.
	 */
	protected WordRuleResult doRule_BePast(IXholon tokenNode)
	{
		// Does one of the remaining words in the sentence end in "ing"?
		IXholon nextTokenNode = tokenNode.getNextSibling();
		while (nextTokenNode != null) {
			String str = nextTokenNode.getVal_String();
			if (str.endsWith("ing")) {
				// ask the user a question
				boolean answer = ask4YesNoAnswer("Does the following phrase still make sense if you cross out \"was\", or \"were\", and \"ing\"?",
						tokenNode.getParentNode().getParentNode());
				if (answer == true) {
					//System.out.println(str.substring(0, str.length()-3));
					Lexeme lexeme = getLexeme(str.substring(0, str.length()-3));
					if (lexeme == null) {return null;}
					else {return new WordRuleResult(lexeme.getTransformRefString(), WordRuleResult.MI_VERB_PASTTENSE);}
				}
				else {
					return new WordRuleResult("�tre", WordRuleResult.MI_VERB_PASTTENSE);
				}
			}
			nextTokenNode = nextTokenNode.getNextSibling();
		}
		return new WordRuleResult("�tre", WordRuleResult.MI_VERB_PASTTENSE);
	}
	
	/**
	 * Do word rule HAVE_PRESENT.
	 * @param tokenNode A token in the English sentence.
	 * @return A transform ref.
	 */
	protected WordRuleResult doRule_HavePresent(IXholon tokenNode)
	{
		IXholon nextTokenNode = tokenNode.getNextSibling();
		if (nextTokenNode != null) {
			Lexeme nextLexeme = getLexeme(nextTokenNode.getVal_String());
			String nextPosStr = nextLexeme.getPosString();
			if ((nextPosStr != null) && (nextPosStr.equals("V"))) {
				return null; // ignore "have" or "has"
			}
			String lemmaStr = nextLexeme.getLemmaString();
			if (lemmaStr != null) {
				if (lemmaStr.equals("not")) {return null;}
				if (lemmaStr.equals("never")) {return null;}
				if (lemmaStr.equals("always")) {return null;}
				// TODO add other words that indicate "have" or "has" should be ignored,
				// or should all adverbs be ignored?
			}
			WordRule wordRule = nextLexeme.getWordRule();
			if (wordRule != null) {
				// execute the rule and see if the WordRuleResult is MI_VERB_PASTTENSE
				WordRuleResult wordRuleResult = wordRule.doRule(nextTokenNode);
				if (wordRuleResult != null) {
					if (wordRuleResult.getMetaInfo() == WordRuleResult.MI_VERB_PASTTENSE) {
						return null; // ignore "have" or "has"
					}
				}
			}
		}
		return new WordRuleResult("avoir", WordRuleResult.MI_NULL);
	}
	
	/**
	 * Do word rule THIS_THAT.
	 * Distinguish between the English "this" and "that".
	 * @param tokenNode A token in the English sentence.
	 * @return A transform ref.
	 */
	protected WordRuleResult doRule_ThisThat(IXholon tokenNode)
	{
		// some sentences that contain "that":
		// ----------------------------------
		// (1) I do not like that. (replaceable by "it") --> �a
		// (2) I do not like that room. (replaceable by "this") --> ce
		// (3) The town that we saw yesterday is beautiful. (replaceable by "who" or "which") --> (qui

		// some sentences that contain "this":
		// ----------------------------------
		// (4) I do not like this. (replaceable by "it")
		// (5) I do not like this room. (replaceable by "that")

		String str = tokenNode.getVal_String();
		if (str.equalsIgnoreCase("this")) {
			return new WordRuleResult("ce", WordRuleResult.MI_NULL);
		}
		else if (str.equalsIgnoreCase("that")) {
			return new WordRuleResult("ce", WordRuleResult.MI_THAT);
		}
		return null;
	}
	
	/**
	 * Do word rule LEFT.
	 * @param tokenNode A token in the English sentence.
	 * @return A transform ref.
	 */
	protected WordRuleResult doRule_Left(IXholon tokenNode)
	{
		boolean answer = ask4YesNoAnswer("In the following sentence, is \"left\" the opposite of \"right\"?",
				tokenNode.getParentNode().getParentNode());
		if (answer == true) {
			return new WordRuleResult("gauche", WordRuleResult.MI_NULL);
		}
		return new WordRuleResult("�tre,parti", WordRuleResult.MI_NULL);
	}
	
	/**
	 * Do word rule QUESTION.
	 * @param tokenNode A token in the English sentence.
	 * @return A transform ref.
	 */
	protected WordRuleResult doRule_Question(IXholon tokenNode)
	{
		IXholon nextTokenNode = tokenNode.getNextSibling();
		int resultCode = WordRuleResult.MI_NULL;
		if (nextTokenNode != null) {
			String str = nextTokenNode.getVal_String();
			if (str != null) {
				if (str.equalsIgnoreCase("am")) {
					resultCode = WordRuleResult.MI_Q_AMAREIS;
				}
				else if (str.equalsIgnoreCase("are")) {
					resultCode = WordRuleResult.MI_Q_AMAREIS;
				}
				else if (str.equalsIgnoreCase("is")) {
					resultCode = WordRuleResult.MI_Q_AMAREIS;
				}
				else if (str.equalsIgnoreCase("can")) {
					resultCode = WordRuleResult.MI_Q_CAN;
				}
				else if (str.equalsIgnoreCase("did")) {
					resultCode = WordRuleResult.MI_Q_DID;
				}
				else if (str.equalsIgnoreCase("do")) {
					resultCode = WordRuleResult.MI_Q_DODOES;
				}
				else if (str.equalsIgnoreCase("does")) {
					resultCode = WordRuleResult.MI_Q_DODOES;
				}
				else if (str.equalsIgnoreCase("has")) {
					resultCode = WordRuleResult.MI_Q_HASHAVE;
				}
				else if (str.equalsIgnoreCase("have")) {
					resultCode = WordRuleResult.MI_Q_HASHAVE;
				}
				else if (str.equalsIgnoreCase("may")) {
					resultCode = WordRuleResult.MI_Q_MAY;
				}
				else if (str.equalsIgnoreCase("must")) {
					resultCode = WordRuleResult.MI_Q_MUST;
				}
				else if (str.equalsIgnoreCase("was")) {
					resultCode = WordRuleResult.MI_Q_WASWERE;
				}
				else if (str.equalsIgnoreCase("were")) {
					resultCode = WordRuleResult.MI_Q_WASWERE;
				}
				else if (str.equalsIgnoreCase("will")) {
					resultCode = WordRuleResult.MI_Q_WILL;
				}
			}
		}
		return new WordRuleResult("est-ce que", resultCode);
	}
	
	/**
	 * Do word rule NEGQUESTION.
	 * @param tokenNode A token in the English sentence.
	 * @return A transform ref.
	 */
	protected WordRuleResult doRule_NegQuestion(IXholon tokenNode)
	{
		return new WordRuleResult("n'est-ce pas", WordRuleResult.MI_NULL);
	}
	
	/**
	 * Do word rule WHQUESTION.
	 * @param tokenNode A token in the English sentence.
	 * @return A transform ref.
	 */
	protected WordRuleResult doRule_WhQuestion(IXholon tokenNode)
	{
		System.out.println("unhandled WHQUESTION in WordRule.java");
		return null;
	}
	
	/**
	 * Get a lexeme, given an English word contained within the lexicon.
	 * @param lemmaString An English word, as a Sttring.
	 * @return A lexeme, or null.
	 */
	protected Lexeme getLexeme(String lemmaString)
	{
		if (lemmaString == null) {return null;}
		// search for the lexicon node, which is an ancestor of this WordRule node
		IXholon node = getParentNode();
		while (node != null) {
			if (node.getXhcId() == LexiconCE) {
				return ((Lexicon)node).getLexeme(lemmaString);
			}
			node = node.getParentNode();
		}
		return null;
	}
}
