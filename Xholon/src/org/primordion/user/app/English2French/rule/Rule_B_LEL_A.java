package org.primordion.user.app.English2French.rule;

import org.primordion.xholon.base.IXholon;

public class Rule_B_LEL_A extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "B_LEL_A";}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		IXholon gender = getNextGender();
		if (gender == null) {return;}
		IXholon leLaLemma = getLeLaLemma();
		if (leLaLemma == null) {return;}
		
		switch (gender.getXhcId()) {
		case GenderMCE:
		case GenderMSCE:
			// no change
			setLeLaLemmaSingular("le", leLaLemma);
			break;
		case GenderFCE:
		case GenderFSCE:
			// change "le" to "la"
			setLeLaLemmaSingular("la", leLaLemma);
			break;
		case GenderMPCE:
		case GenderFPCE:
			// change "le" or "la" to "les"
			leLaLemma.setVal("les");
			break;
		default:
			break;
		}
	}
	
	/**
	 * Get the le/la Lemma node ("le", "la", "les") associated with this B_LEL_A node.
	 * @return A le/la Lemma node, or null;
	 */
	protected IXholon getLeLaLemma()
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
	 * Set a singular Lemma node to "le", "la" or "l'".
	 * @param newVal Default new value for the leLaLemma ("le" or "la").
	 * @param leLaLemma A Lemma node that currently has a value of "le".
	 */
	protected void setLeLaLemmaSingular(String newVal, IXholon leLaLemma)
	{
		IXholon nounLemma = getNounLemma();
		if (nounLemma != null) {
			String str = nounLemma.getVal_String();
			if ((str != null) && (str.length() > 0)) {
				char firstLetter = str.charAt(0);
				if (isVowelOrH(firstLetter)) {
					leLaLemma.setVal("l'");
					return;
				}
			}
		}
		leLaLemma.setVal(newVal);
	}
	
}
