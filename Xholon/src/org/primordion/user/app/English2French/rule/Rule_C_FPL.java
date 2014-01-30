package org.primordion.user.app.English2French.rule;

import org.primordion.xholon.base.IXholon;

public class Rule_C_FPL extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {
		switch (this.getXhcId()) {
		case C_FPL_ACE: return "C_FPL_A";
		default: return null;
		}
	}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		//System.out.print("Rule C_FPL_A is being handled in sentence: ");
		IXholon sengNode = getXPath().evaluate("ancestor::SEng", this);
		if (sengNode == null) {return;}
		//System.out.print(sengNode.getName() + " ");
		//System.out.println(sengNode);
		
		IXholon word = getPreviousLemma();
		if (word == null) {return;}
		IXholon genderNode = getNextGender();
		if (genderNode == null) {return;}
		
		switch (genderNode.getXhcId()) {
		case GenderMCE:
		case GenderMSCE:
			// no change
			break;
		case GenderFCE:
		case GenderFSCE:
			// add -e
			modifyWord(word, "e");
			break;
		case GenderMPCE:
			// add -s
			modifyWord(word, "s");
			break;
		case GenderFPCE:
			// add -es
			modifyWord(word, "es");
			break;
		default:
			break;
		}
	}
	
	/**
	 * Modify the word by adding the new ending to the end of the word.
	 * @param word A word node.
	 * @param newEnding A new adding.
	 */
	protected void modifyWord(IXholon word, String newEnding)
	{
		String wordStr = word.getVal_String();
		if (wordStr == null) {return;}
		wordStr += newEnding;
		word.setVal(wordStr);
	}
	
}
