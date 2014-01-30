package org.primordion.user.app.English2French.rule;

import org.primordion.xholon.base.IXholon;

public class Rule_B_UNE_A extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "B_UNE_A";}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		// un must agree with the following word/verb
		IXholon un = getPreviousSibling();
		// search for next gender marker
		IXholon node = getNextSibling();
		while (node != null) {
			switch (node.getXhcId()) {
			case GenderFCE:
			case GenderFSCE:
			case GenderFPCE:
				un.setVal("une");
				return;
			case GenderMCE:
			case GenderMSCE:
			case GenderMPCE:
				return; // no change required
			default:
				break;
			}
			node = node.getNextSibling();
		}
	}
}
