package org.primordion.user.app.English2French.rule;

import org.primordion.xholon.base.IXholon;

public class Rule_C_PLU extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {
		switch (this.getXhcId()) {
		case C_PLU_ACE: return "C_PLU_A";
		case C_PLU_LCE: return "C_PLU_L";
		case C_PLU_UCE: return "C_PLU_U";
		case C_PLU_XCE: return "C_PLU_X";
		default: return null;
		}
	}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		IXholon gender = getNextGender();
		if (gender == null) {return;}
		switch(gender.getXhcId()) {
		case GenderMCE:
		case GenderMSCE:
		case GenderFCE:
		case GenderFSCE:
			// no changes required if it's singular
			return;
		default:
			break;
		}
		// it's plural
		IXholon word = getPreviousSibling(); // the word that needs to be pluralized
		if (word.getXhcId() != LemmaCE) {return;}
		String wordStr = word.getVal_String();
		int len = wordStr.length();
		switch (getXhcId()) {
		case C_PLU_ACE:
			// add -s
			word.setVal(wordStr + 's');
			break;
		case C_PLU_LCE:
			// change "-al" or "-ail" to "aux"
			if (wordStr.endsWith("al")) {
				word.setVal(wordStr.substring(0, len-2) + "aux");
			}
			if (wordStr.endsWith("ail")) {
				word.setVal(wordStr.substring(0, len-3) + "aux");
			}
			break;
		case C_PLU_UCE:
			// add -x
			word.setVal(wordStr + 'x');
			break;
		case C_PLU_XCE:
			// irregular plurals
			if (wordStr.equalsIgnoreCase("oeil")) {word.setVal("yeux");}
			break;
		default: break;
		}
	}
	
}
