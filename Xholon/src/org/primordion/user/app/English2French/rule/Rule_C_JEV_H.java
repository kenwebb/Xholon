package org.primordion.user.app.English2French.rule;

import org.primordion.user.app.English2French.ClosedBracket;
import org.primordion.xholon.base.IXholon;

public class Rule_C_JEV_H extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "C_JEV_H";}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		// je must agree with the following word/verb
		IXholon je = getPreviousSibling();
		// look for the next word
		IXholon nextWord = getNextSibling();
		while (nextWord != null) {
			switch (nextWord.getXhcId()) {
			case ClosedBracketCE:
				// a verb
				String verbStr = ((ClosedBracket)nextWord).getLemmaString();
				if (verbStr == null) {return;}
				if (verbStr.equals("aller")) {return;} // je vais
				if (isVowelOrH(verbStr.charAt(0))) {
					// first letter of next word is a vowel or h
					je.setVal("j'");
				}
				return;
			case LemmaCE:
				// some non-verb word (ex: ne)
				String word = nextWord.getVal_String();
				if (word == null) {return;}
				if (isVowelOrH(word.charAt(0))) {
					// first letter of next word is a vowel or h
					je.setVal("j'");
				}
				return;
			default:
				break;
			}
			nextWord = nextWord.getNextSibling();
		}
	}
	
}
