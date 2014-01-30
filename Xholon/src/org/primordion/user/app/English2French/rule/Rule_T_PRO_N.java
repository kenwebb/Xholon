package org.primordion.user.app.English2French.rule;

import org.primordion.xholon.base.IXholon;

public class Rule_T_PRO_N extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "T_PRO_N";}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		// ex: CM_vv [nfnv donner 3 donner] CM_vv la B_LEL_A carte C_PLU_A GenderF CM_nn [cvxr lui T_PRO_N CM_np CM_xx
		
		// if there's an open bracket "[" and four-letter code just before this word,
		// then cross out the "[" and four-letter code
		IXholon endWord = getPreviousLemma(); // lui, etc., or possibly the end word in a group of words
		if (endWord == null) {return;}
		IXholon node = endWord.getPreviousSibling();
		if (node == null) {return;}
		if (node.getXhcId() == OpenBracketCE) {
			node.removeChild();
		}
		
		// TODO is the next circle marker after CM_np also a CM_np
		IXholon nextCircleMarker = getNextCircleMarker();
		if (nextCircleMarker == null) {return;}
		if (nextCircleMarker.getXhcId() != CM_npCE) {return;}
		
		// cross out the word or group of words between CM_np and the preceding circle marker
		// rewrite these words in the original or reversed order before the preceding "[" in the sentence or ellipse
		IXholon prevCircleMarker = getPreviousCircleMarker();
		if (prevCircleMarker == null) {return;}
		IXholon prevOpenBracket = getPreviousOpenBracket();
		if (prevOpenBracket == null) {return;}
		IXholon aWord = prevCircleMarker.getNextSibling();
		while ((aWord != null) && (aWord != this)) {
			// get next word before moving this word
			IXholon nextWord = aWord.getNextSibling();
			// move this word
			aWord.removeChild();
			aWord.insertBefore(prevOpenBracket);
			aWord = nextWord;
		}
		
		
		// TODO does the verb in [] begin with a vowel
		// TODO is "le" "la" or "te" now just before "["
		// TODO make some changes
	}
	
}
