package org.primordion.user.app.English2French.rule;

import org.primordion.xholon.base.IXholon;

public class Rule_V_IMP_A extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "V_IMP_A";}
	
	public void doRule()
	{
		switch (this.getXhcId()) {
		case V_IMP_ACE:
		{
			// change [__xr to {__xm
			IXholon fourLetterCode = getPreviousFourLetterCode();
			if (fourLetterCode == null) {return;}
			String codeStr = fourLetterCode.getVal_String();
			if ((codeStr == null) || (codeStr.length() != 4)) {return;}
			if (codeStr.endsWith("xr")) {
				fourLetterCode.setVal(codeStr.substring(0, 3) + "m");
			}
			break;
		}
		default:
			break;
		}
	}
	
}
