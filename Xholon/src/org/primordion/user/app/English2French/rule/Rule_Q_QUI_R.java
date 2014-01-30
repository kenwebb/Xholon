package org.primordion.user.app.English2French.rule;

import org.primordion.user.app.English2French.ClosedBracket;
import org.primordion.user.app.English2French.XhEnglish2French;
import org.primordion.xholon.base.IXholon;

public class Rule_Q_QUI_R extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "Q_QUI_R";}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		// move preceding orphan OpenBracket "[" to matching next orphan ClosedBracket "]"
		IXholon openBracket = getPreviousOpenBracket();
		ClosedBracket closedBracket = (ClosedBracket)getNextClosedBracket();
		while ((closedBracket != null) && (closedBracket.getPreviousSibling().getXhcId() == OpenBracketCE)) {
			closedBracket = (ClosedBracket)closedBracket.getNextClosedBracket();
		}
		if ((closedBracket != null) && (openBracket != null)) {
			// remove OpenBracket from its current location
			openBracket.removeChild();
			// insert
			openBracket.insertBefore(closedBracket);
			openBracket.insertBefore("ClosedEllipse", null);
		}
		// If the next circle marker after "qui Q_QUI_R CM_np" is a CM_nn or CM_np
		// Then optionally change the lemma from "qui" to "que"
		XhEnglish2French node  = (XhEnglish2French)getNextSibling();
		IXholon circleMarker = node.getNextCircleMarker();
		if (circleMarker != null) {
			switch (circleMarker.getXhcId()) {
			case CM_nnCE:
			case CM_npCE:
				IXholon qui = getPreviousSibling();
				qui.setVal("que");
				return;
			default:
				break;
			}
		}
		
		// Else TODO
	}
	
}
