package org.primordion.user.app.English2French.rule;

import org.primordion.xholon.base.IXholon;

/**
 * <p>Some words in French can be either masculine or feminine,
 * depending on whether the referenced person is male or female.</p>
 * <p>ex: ami tourist nous vous</p>
 * <p>To determine if the person(s) is male or female, requires asking the user.</p>
 * @author Ken Webb
 *
 */
public class Rule_C_FEM_A extends Rule_Flag {
	
	private static final String askAction  = "Ask if male/female?";
	private static final String showRuleId = "Show rule ID";
	/** action list */
	private String[] actions = {askAction, showRuleId};
	
	/** 
	 * Whether or not to ask user if the referenced person is male or female.
	 */
	private boolean askMaleFemale = false;
	
	/** Whether or not can ask user if the referenced person is male or female. */
	public boolean isAskMaleFemale() {
		return askMaleFemale;
	}
	
	/** Set whether or not can ask user if the referenced person is male or female. */
	public void setAskMaleFemale(boolean askMaleFemale) {
		this.askMaleFemale = askMaleFemale;
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
		if (action.equals(askAction)) {
			if (askMaleFemale == true) {askMaleFemale = false;}
			else {askMaleFemale = true;}
			System.out.println(askAction + ":" + askMaleFemale);
		}
		else if (action.equals(showRuleId)) {
			System.out.println(showRuleId + ":" + getRuleId());
		}
	}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "C_FEM_A";}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		IXholon sengNode = getXPath().evaluate("ancestor::SEng", this);
		if (sengNode == null) {return;}
		
		// don't bother trying to do anything, if can't find out whether person is male or female
		if (askMaleFemale == false) {return;}
		
		// get the next gender node
		IXholon genderNode = getNextGender();
		if (genderNode == null) {return;}
		// if gender is already feminine, then no processing is required
		switch (genderNode.getXhcId()) {
		case GenderFCE:
		case GenderFSCE:
		case GenderFPCE:
			return;
		default:
			break;
		}
		
		// get the word that this flag is associated with
		IXholon ref = getPreviousLemma();
		
		switch (genderNode.getXhcId()) {
		case GenderMCE:
		case GenderMSCE:
		{
			boolean answer = ask4YesNoAnswer(
					"In the following sentence, does the word \"" + ref + "\" refer to a female?",
					sengNode);
			if (answer == true) {
				// change M or MS to FS
				genderNode.setXhc(getClassNode(GenderFSCE));
			}
			break;
		}
		case GenderMPCE:
		{
			boolean answer = ask4YesNoAnswer(
					"In the following sentence, do all the words refer to females?",
					sengNode);
			if (answer == true) {
				// change MP to FP
				genderNode.setXhc(getClassNode(GenderFPCE));
			}
			break;
		}
		default:
			break;
		}
		
	}
	
}
