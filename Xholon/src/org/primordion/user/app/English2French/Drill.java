package org.primordion.user.app.English2French;

public class Drill extends XhEnglish2French {
	
	/** The English lexicon. */
	Lexicon lexicon = null;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#handleNodeSelection()
	 */
	public Object handleNodeSelection()
	{
		return doDrill();
	}
	
	/**
	 * Do one drill exercise.
	 */
	protected String doDrill()
	{
		if (getXhcId() == DrillCE) {
			return "English to French, and French to English vocabulary drill";
		}
		boolean notFound = true;
		int countDown = 10; // only try this many times, to prevent an infinite loop
		while (notFound) {
			countDown--;
			if (countDown <= 0) {break;}
			Lexeme lexeme = lexicon.getRandomLexeme();
			if (lexeme == null) {continue;}
			String engStr = lexeme.getLemmaString();
			if ((engStr == null) || (engStr.length() == 0)) {continue;}
			String frStr = lexeme.getTransformRefString();
			if ((frStr == null) || (frStr.length() == 0)) {continue;}
			if (getXhcId() == Drill_Eng2FrCE) {
				System.out.print("\n" + frStr); // print the answer word on the console
				return engStr; // return the question word, for display in the Xholon GUI
			}
			else { // Drill_Fr2EngCE
				System.out.print("\n" + engStr); // print the answer word on the console
				return frStr; // return the question word, for display in the Xholon GUI
			}
		}
		return "";
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getName()
	 */
	public String getName()
	{
		switch (getXhcId()) {
		case DrillCE:
			return "English to French, and French to English vocabulary drill";
		case Drill_Eng2FrCE:
		case Drill_Fr2EngCE:
			return getRoleName();
		default:
			return "Unknown";
		}
	}
	
}
