package org.primordion.user.app.English2French.rule;

import org.primordion.xholon.base.IXholon;

public class Rule_D_ALE_A extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "D_ALE_A";}
	
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
		
		// get previous word, which should be "�"
		IXholon ah = getPreviousLemma(); // ex: �
		if (ah == null) {return;}
		String ahStr = ah.getVal_String();
		if ((ahStr == null) || (ahStr.length() < 1)) {return;}
		
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
				// change �+le to aux, �+la to aux, �+les to aux
				makeChanges(ah, nextWord, "aux");
			}
			else if (isWordAfterLeVowelOrH(nextWord)) {
				// change �+le to � l' , �+la to � l'
				makeChanges(ah, nextWord, "� l'");
			}
			else if ((genderId == GenderFCE) || (genderId == GenderFSCE)) {
				// change �+le to �+la
				makeChanges(ah, nextWord, "� la");
			}
			else if ((genderId == GenderMCE) || (genderId == GenderMSCE)) {
				// change �+le to au
				makeChanges(ah, nextWord, "au");
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
	 * Make the necessary changes to the "�" word, and remove the leLaLes word.
	 * @param ah The word that contains "�".
	 * @param leLaLes The le/la/les word to remove.
	 * @param newVal The value that will replace the content in the "�" word.
	 */
	protected void makeChanges(IXholon ah, IXholon leLaLes, String newVal)
	{
		String ahStr = ah.getVal_String();
		// the "�" word might be more than just "�" (ex: xxxx �); just remove the "�" part
		ahStr = ahStr.substring(0, ahStr.length()-1);
		ahStr += newVal;
		ah.setVal(ahStr);
		// remove the redundant leLaLes node
		leLaLes.removeChild();
	}
	
}
