package org.primordion.user.app.English2French.rule;

import org.primordion.xholon.base.IXholon;

public class Rule_B_POS_A extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "B_POS_A";}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		IXholon gender = getNextGender();
		if (gender == null) {return;}
		IXholon xonXaLemma = getXonXaLemma();
		if (xonXaLemma == null) {return;}
		String xonXaLemmaStr = xonXaLemma.getVal_String();
		if (xonXaLemmaStr == null) {return;}
		
		switch (gender.getXhcId()) {
		case GenderMCE:
		case GenderMSCE:
			// no change
			break;
		case GenderFCE:
		case GenderFSCE:
			// change "xon" to "xa"
			String femForm = null;
			if      (xonXaLemmaStr.equals("mon"))   {femForm = "ma";}
			else if (xonXaLemmaStr.equals("ton"))   {femForm = "ta";}
			else if (xonXaLemmaStr.equals("son"))   {femForm = "sa";}
			else if (xonXaLemmaStr.equals("notre")) {femForm = "notre";}
			else if (xonXaLemmaStr.equals("votre")) {femForm = "votre";}
			else if (xonXaLemmaStr.equals("leur"))  {femForm = "leur";}
			else {return;} // error
			setXonXaLemmaSingular(femForm, xonXaLemma);
			break;
		case GenderMPCE:
		case GenderFPCE:
			// change "xon" or "xa" to "xes"
			String plForm = null;
			char firstLetter = xonXaLemmaStr.charAt(0);
			if      (firstLetter == 'm') {plForm = "mes";} // mon, ma
			else if (firstLetter == 't') {plForm = "tes";} // ton, ta
			else if (firstLetter == 's') {plForm = "ses";} // son, sa
			else if (firstLetter == 'n') {plForm = "nos";} // notre, notre
			else if (firstLetter == 'v') {plForm = "vos";} // votre, votre
			else if (firstLetter == 'l') {plForm = "leurs";} // leur, leur
			else {return;} // error
			xonXaLemma.setVal(plForm);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Get the lemma node ("mon", "ton", "son", "notre", "votre", "leur") associated with this B_POS_A node.
	 * @return A xon Lemma node, or null;
	 */
	protected IXholon getXonXaLemma()
	{
		return getPreviousSibling();
	}
	
	/**
	 * Get the noun Lemma node associated with this B_LEL_A node.
	 * @return A noun Lemma node, or null;
	 */
	protected IXholon getNounLemma()
	{
		return getNextLemma();
	}
	
	/**
	 * Set a singular Lemma node to "xon", "xa" or "xon".
	 * @param newVal Default new value for the xonXaLemma ("xon" or "xa").
	 * @param xonXaLemma A Lemma node that currently has a value of "xon".
	 */
	protected void setXonXaLemmaSingular(String newVal, IXholon xonXaLemma)
	{
		IXholon nounLemma = getNounLemma();
		if (nounLemma != null) {
			String str = nounLemma.getVal_String();
			if ((str != null) && (str.length() > 0)) {
				char firstLetter = str.charAt(0);
				if (isVowelOrH(firstLetter)) {
					// no change required
					return;
				}
			}
		}
		xonXaLemma.setVal(newVal);
	}
}
