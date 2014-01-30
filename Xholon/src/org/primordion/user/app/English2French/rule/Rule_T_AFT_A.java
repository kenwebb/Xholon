package org.primordion.user.app.English2French.rule;

import org.primordion.xholon.base.IXholon;

public class Rule_T_AFT_A extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "T_AFT_A";}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		// move this word to a position immediately before the next Gender marker
		// In English this adjective comes before the noun,
		// while in French it comes after.
		
		IXholon newNextSibling = getNextGender();
		if (newNextSibling == null) {return;}
		
		// get the word that's associated with this flag
		IXholon word = getWordToBeMoved();
		if (word == null) {return;}
		word.removeChild();
		word.insertBefore(newNextSibling);
	}
	
	/**
	 * Get the word that needs to be moved.
	 * @return A lemma node, or null.
	 */
	protected IXholon getWordToBeMoved()
	{
		IXholon word = getPreviousSibling();
		while (word != null) {
			if (word.getXhcId() == LemmaCE) {
				return word;
			}
			word = word.getPreviousSibling();
		}
		return null;
	}
}
