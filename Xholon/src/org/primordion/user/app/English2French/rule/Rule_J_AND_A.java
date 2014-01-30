package org.primordion.user.app.English2French.rule;

import org.primordion.user.app.English2French.XhEnglish2French;
import org.primordion.xholon.base.IXholon;

public class Rule_J_AND_A extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "J_AND_A";}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		// Examine the circle markers just before and after CM_jj
		// If they are both CM_nn
		// Then change the first four-letter code after CM_jj from [_v__ to [_w__ .
		// Do nothing if it's already [_w__ .
		IXholon jj = getPreviousCircleMarker();
		if ((jj == null) || (jj.getXhcId() != CM_jjCE)) {return;}
		IXholon previousCircleMarker = ((XhEnglish2French)jj).getPreviousCircleMarker();
		if (previousCircleMarker == null) {return;}
		IXholon nextCircleMarker = getNextCircleMarker();
		if (nextCircleMarker == null) {return;}
		int previousCMType = previousCircleMarker.getXhcId();
		int nextCMType = nextCircleMarker.getXhcId();
		if ((previousCMType == CM_nnCE) && (nextCMType == CM_nnCE)) {
			makePlural();
		}
		
		// If they are both CM_np, or if the first is CM_nn and the second CM_np
		// Then do CONJ_PRONOUN
		
		// If they are both CM_vv, then do CONJ_VERBS
		
		// TODO do other tests and procedures
	}
	
	/**
	 * Make the next four-letter code plural.
	 * Change from [_v__ to [_w__ .
	 */
	protected void makePlural()
	{
		IXholon fourLetterCode = getNextFourLetterCode();
		if (fourLetterCode == null) {return;}
		String str = fourLetterCode.getVal_String();
		if ((str == null) || (str.length() != 4)) {return;}
		fourLetterCode.setVal("" + str.charAt(0) + 'w' + str.charAt(2) + str.charAt(3));
	}
	
}
