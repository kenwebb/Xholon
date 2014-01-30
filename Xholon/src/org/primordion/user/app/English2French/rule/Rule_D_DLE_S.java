package org.primordion.user.app.English2French.rule;

import org.primordion.xholon.base.IXholon;

public class Rule_D_DLE_S extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "D_DLE_S";}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		// get next word
		IXholon nextWord = getNextLemma(); // ex: "le"
		if (nextWord == null) {return;}
		String nextWordStr = nextWord.getVal_String();
		if (nextWordStr == null) {return;}
		
		// get previous word, which should be "de"
		IXholon de = getPreviousLemma(); // ex: de
		if (de == null) {return;}
		String deStr = de.getVal_String();
		if ((deStr == null) || (deStr.length() < 2)) {return;}
		
		if (isDefiniteArticle(nextWord)) {
			// remove the B_LEL_A flag after the next "le" "la" "les"
			IXholon flag = nextWord.getNextSibling(); // ex: B_LEL_A
			if (flag.getXhcId() == B_LEL_ACE) {
				flag.removeChild();
			}
			IXholon genderNode = getNextGender(); // ex: GenderF
			int genderId = genderNode.getXhcId();
			// is the gender node plural
			if (genderId == GenderMPCE || genderId == GenderFPCE) {
				// change de+le to des, de+la to des, de+les to des
				makeChanges(de, nextWord, "des");
			}
			else if (isWordAfterLeVowelOrH(nextWord)) {
				// change de+le to de l' , de+la to de l'
				makeChanges(de, nextWord, "de l'");
			}
			else if ((genderId == GenderFCE) || (genderId == GenderFSCE)) {
				// change de+le to de+la
				makeChanges(de, nextWord, "de la");
			}
			else if ((genderId == GenderMCE) || (genderId == GenderMSCE)) {
				// change de+le to du
				makeChanges(de, nextWord, "du");
			}
		}
		else if (isVowelOrH(nextWordStr.charAt(0))) {
			if (deStr.equalsIgnoreCase("de")) {
				de.setVal("d'");
			}
		}
		else {
			// no change
		}
	}
	
	/**
	 * Is this node a French definite article (le la les).
	 * @param node A lemma node.
	 * @return true or false
	 */
	protected boolean isDefiniteArticle(IXholon node)
	{
		String str = node.getVal_String();
		if (str == null) {return false;}
		if (str.equalsIgnoreCase("le")) {return true;}
		if (str.equalsIgnoreCase("la")) {return true;}
		if (str.equalsIgnoreCase("les")) {return true;}
		return false;
	}
	
	/**
	 * Does the word after the specified le/la word, begin with a vowel or H.
	 * @param leLaWord
	 * @return true or false
	 */
	protected boolean isWordAfterLeVowelOrH(IXholon leLaWord)
	{
		IXholon node = leLaWord.getNextSibling();
		while (node != null) {
			if (node.getXhcId() == LemmaCE) {
				String str = node.getVal_String();
				if ((str == null) || (str.length() == 0)) {return false;}
				if (isVowelOrH(str.charAt(0))) {
					return true;
				}
			}
			node = node.getNextSibling();
		}
		return false;
	}
	
	/**
	 * Make the necessary changes to the "de" word, and remove the leLaLes word.
	 * @param de The word that contains "de".
	 * @param leLaLes The le/la/les word to remove.
	 * @param newVal The value that will replace the content in the "de" word.
	 */
	protected void makeChanges(IXholon de, IXholon leLaLes, String newVal)
	{
		String deStr = de.getVal_String();
		// the "de" word might be more than just "de" (ex: pr�s de); just remove the "de" part
		deStr = deStr.substring(0, deStr.length()-2);
		deStr += newVal;
		de.setVal(deStr);
		// remove the redundant leLaLes node
		leLaLes.removeChild();
	}
	
}
