package org.primordion.user.app.English2French.rule;

/**
 * Test and procedure to determine if "celui" or "ceux" refers to
 * a masculine or feminine noun.
 * This rule may need to refer to a previous phrase or sentence.
 * @author Ken Webb
 */
public class Rule_C_CEL_A extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "C_CEL_A";}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		System.out.println("Rule_C_CEL_A is not yet implemented.");
	}
	
}
