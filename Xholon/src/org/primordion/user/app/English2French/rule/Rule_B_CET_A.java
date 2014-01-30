package org.primordion.user.app.English2French.rule;

import org.primordion.xholon.base.IXholon;

public class Rule_B_CET_A extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {
		switch (this.getXhcId()) {
		case B_CET_ACE: return "B_CET_A"; // this
		default: return null;
		}
	}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		IXholon gender = getNextGender();
		if(gender == null) {return;}
		IXholon ce = getPreviousLemma();
		if (ce == null) {return;}
		switch (gender.getXhcId()) {
		case GenderMCE:
		case GenderMSCE:
			IXholon nextWord = getNextLemma();
			if (nextWord == null) {return;}
			String str = nextWord.getVal_String();
			if ((str == null) || (str.length() == 0)) {return;}
			if (isVowelOrH(str.charAt(0))) {
				ce.setVal("cet");
			}
			break;
		case GenderFCE:
		case GenderFSCE:
			ce.setVal("cette");
			break;
		default:
			break;
		}
	}
	
}
