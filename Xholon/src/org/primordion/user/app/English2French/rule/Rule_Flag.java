package org.primordion.user.app.English2French.rule;

import org.primordion.user.app.English2French.XhEnglish2French;

/**
 * Superclass for all concrete flag rules.
 * @author Ken Webb
 *
 */
public class Rule_Flag extends XhEnglish2French {
	
	/**
	 * Get the ID of this rule flag.
	 * @return A String identifier (ex: "A_FPL_A").
	 */
	public String getRuleId() {return "flag";}
	
	/**
	 * Do the rule associated with this flag.
	 */
	public void doRule()
	{
		System.out.println("Rule " + toString() + " is unhandled.");
	}
	
	/*
	 * @see org.primordion.user.app.English2French.XhEnglish2French#toString()
	 */
	public String toString() {return getRuleId();}
	
}
