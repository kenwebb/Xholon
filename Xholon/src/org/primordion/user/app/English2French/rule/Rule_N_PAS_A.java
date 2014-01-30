package org.primordion.user.app.English2French.rule;

import org.primordion.user.app.English2French.ClosedBracket;
import org.primordion.xholon.base.IXholon;

public class Rule_N_PAS_A extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "N_PAS_A";}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		// ex: [avxr ne N_PAS_A 3 aimer] CM_vv
		
		// "ne" must always precede N_PAS_A
		IXholon ne = getPreviousLemma();
		if (ne == null) {return;}
		String neStr = ne.getVal_String();
		if ((neStr == null) || (!neStr.equals("ne"))) {return;}
		
		// write "pas" after the next "] CM_vv"
		ClosedBracket closedBracket = (ClosedBracket)getNextClosedBracket();
		if (closedBracket == null) {return;}
		IXholon cm = closedBracket.getNextCircleMarker();
		if ((cm == null) || (cm.getXhcId() != CM_vvCE)) {return;}
		cm.insertAfter("Lemma", null).setVal("pas");
		
		// if the verb before this "]" begins with a vowel or H, change "ne" to "n'"
		String verbStr = closedBracket.getLemmaString();
		if ((verbStr == null) || (verbStr.length() == 0)) {return;}
		if (isVowelOrH(verbStr.charAt(0))) {
			neStr = "n'";
			ne.setVal(neStr);
		}
		
		// move the "ne" or "n'" before the preceding "["
		IXholon openBracket = getPreviousOpenBracket();
		if (openBracket != null) {
			ne.removeChild();
			ne.insertBefore(openBracket);
		}
		
		// remove this N_PAS flag from the WordPass tree
		removeChild();
	}
	
}
