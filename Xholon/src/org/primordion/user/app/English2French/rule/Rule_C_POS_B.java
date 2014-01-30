package org.primordion.user.app.English2French.rule;

/**
 * Tests and procedures to determine if the noun referred to
 * is masculine or feminine, and if it's singular or plural.
 * This rule may need to refer to a previous phrase or sentence.
 * @author Ken Webb
 *
 */
public class Rule_C_POS_B extends Rule_Flag {
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "C_POS_B";}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		System.out.println("Rule_C_POS_B is not yet implemented.");
	}
	
}
