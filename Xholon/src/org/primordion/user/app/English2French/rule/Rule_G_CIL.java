package org.primordion.user.app.English2French.rule;

import org.primordion.xholon.base.IXholon;

/**
 * <p>Rule G_CIL.</p>
 * <p>The word rules for "this" and "that" in the English lexicon.
 * To record whether the English word is "this" or "that",
 * use G_CIL_A and G_CIL_B .</p>
 * @author Ken Webb
 * 
 */
public class Rule_G_CIL extends Rule_Flag {
	
	private static final String emphasizeAction = "Emphasize this that";
	private static final String showRuleId      = "Show rule ID";
	/** action list */
	private String[] actions = {emphasizeAction, showRuleId};
	
	/** 
	 * Whether or not to emphasize the difference between this and that in French.
	 * Whether to add "-ci" or "-l�".
	 */
	private boolean emphasizeThisThat = false;
	
	/** Whether or not the difference between this and that in French, is to be emphasized. */
	public boolean isEmphasizeThisThat() {
		return emphasizeThisThat;
	}
	
	/** Set whether or not to emphasize the difference between this and that in French. */
	public void setEmphasizeThisThat(boolean emphasizeThisThat) {
		this.emphasizeThisThat = emphasizeThisThat;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getActionList()
	 */
	public String[] getActionList()
	{
		return actions;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setActionList(java.lang.String[])
	 */
	public void setActionList(String[] actionList)
	{
		actions = actionList;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#doAction(java.lang.String)
	 */
	public void doAction(String action)
	{
		if (action == null) {return;}
		if (action.equals(emphasizeAction)) {
			if (emphasizeThisThat == true) {emphasizeThisThat = false;}
			else {emphasizeThisThat = true;}
			System.out.println(emphasizeAction + ":" + emphasizeThisThat);
		}
		else if (action.equals(showRuleId)) {
			System.out.println(showRuleId + ":" + getRuleId());
		}
	}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {
		switch (getXhcId()) {
		case G_CIL_ACE: return "G_CIL_A"; // this
		case G_CIL_BCE: return "G_CIL_B"; // that
		default: return null;
		}
	}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		if (!emphasizeThisThat) {return;}
		IXholon genderNode = getNextGender();
		if (genderNode == null) {return;}
		String cila = "";
		switch (getXhcId()) {
		case G_CIL_ACE:
			// this; insert "-ci" before the next gender marker
			cila = "-ci";
			break;
		case G_CIL_BCE:
			// that; insert "-l�" before the next gender marker
			cila = "-l�";
			break;
		default:
			break;
		}
		genderNode.insertBefore("Lemma", null).setVal(cila);
	}
	
}
