package org.primordion.user.app.English2French;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.XholonClass;

public class Lexeme extends XhEnglish2French implements Comparable {
	
	/*
	 * @see org.primordion.user.app.English2French.XhEnglish2French#postConfigure()
	 */
	public void postConfigure()
	{
		// fix "true" and "false" lemmas, if they exist
		String str = getLemmaString();
		if (str != null) {
			if (str.equals("false_")) {getLemma().setVal("false");}
			if (str.equals("true_")) {getLemma().setVal("true");}
		}
		super.postConfigure();
	}
	
	/**
	 * Get the lemma (ex: girl).
	 * @return The lemma, or null.
	 */
	public IXholon getLemma()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == LemmaCE) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the lemma as a String (ex: girl).
	 * @return The lemma, or null.
	 */
	public String getLemmaString()
	{
		IXholon lemma = getLemma();
		if (lemma == null) {
			return null;
		}
		else {
			return lemma.getVal_String();
		}
	}
	
	/**
	 * Get the plural form of this lemma.
	 * For this to be meaningful, the word should be a noun.
	 * But this method will return a pluralized version of any word.
	 * @return A String, or null (if the base word is null).
	 */
	public String getLemmaStringPluralized()
	{
		String base = getLemmaString();
		if ((base == null) || (base.length() == 0)) {
			return null;
		}
		int morphType = getMorphPluralType();
		switch(morphType) {
		
		case MorphPlural_RegularCE:
			return base + 's';
			
		case MorphPlural_NoChangeCE:
			return base;
			
		case MorphPlural_SibilantCE:
			return base + "es";
			
		case MorphPlural_FfeCE:
		{
			// word ends in f(elf) or fe(knife)
			if (base.length() < 3) {return base;}
			char c = base.charAt(base.length()-1);
			if (c == 'f') {return base.substring(0, base.length()-1) + "ves";}
			else if (c == 'e') {return base.substring(0, base.length()-2) + "ves";}
			else return base; // error
		}
		
		case MorphPlural_YCE:
		{
			// change y to ies when preceded or consonant, or ys when preceded by a vowel
			// ex: berry --> berries     monkey --> monkeys
			if (base.length() < 3) {return base;}
			if (!base.endsWith("y")) {return base;}
			switch (base.charAt(base.length()-2)) {
			case 'a':
			case 'e':
			case 'i':
			case 'o':
			case 'u':
				// vowel
				return base + 's';
			default:
				// consonant
				return base.substring(0, base.length()-1) + "ies";
			}
		}
		
		case MorphPlural_OesCE:
			// ex: hero --> heroes
			return base + "es";
			
		case MorphPlural_IrregularCE:
			// irregular
			if      ("child".equals(base)) {return "children";}
			else if ("foot".equals(base)) {return "feet";}
			else if ("goose".equals(base)) {return "geese";}
			else if ("man".equals(base)) {return "men";}
			else if ("mouse".equals(base)) {return "mice";}
			else if ("ox".equals(base)) {return "oxen";}
			else if ("tooth".equals(base)) {return "teeth";}
			else if ("woman".equals(base)) {return "women";}
			else return base;
			
		case MorphPlural_AlreadyPluralCE:
			return base;
			
		default:
			// return the default regular plural
			return base + 's';
		}
		
	}
	
	/**
	 * Get the part of speech (POS) (ex: PosN, a noun).
	 * @return The part of speech, or null.
	 */
	public IXholon getPos()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhc().hasAncestor(PosCE)) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the part of speech type.
	 * @return A part of speech type, or generic Pos if no POS is specified in the lexicon.
	 */
	public int getPosType()
	{
		IXholon pos = getPos();
		if (pos == null) {return PosCE;}
		else {return pos.getXhcId();}
	}
	
	/**
	 * Get the part of speech (POS) as a String (ex: N).
	 * @return The part of speech, or null.
	 */
	public String getPosString()
	{
		IXholon pos = getPos();
		if (pos == null) {
			return null;
		}
		else {
			return pos.toString();
		}
	}
	
	/**
	 * Is this lexeme a noun?
	 * @return true or false
	 */
	public boolean isNoun()
	{
		if (getPosType() == PosNCE) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Get a reference to the transform.
	 * @return The transform, or null.
	 */
	public IXholon getTransformRef()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == TransformRefCE) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}

	/**
	 * Get a reference to the transform, as a String.
	 * @return A String, or null.
	 */
	public String getTransformRefString()
	{
		IXholon tr = getTransformRef();
		if (tr == null) {
			return null;
		}
		else {
			return tr.getVal_String();
		}
	}
	
	/**
	 * Get a word rule.
	 * @return A word rule node, or null.
	 */
	public WordRule getWordRule()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == WordRuleCE) {
				return (WordRule)node;
			}
			node = node.getNextSibling();
		}
		return null;
	}

	/**
	 * Get a word rule, as a String.
	 * @return A String, or null.
	 */
	public String getWordRuleString()
	{
		IXholon wr = getWordRule();
		if (wr == null) {
			return null;
		}
		else {
			return wr.getVal_String();
		}
	}
	
	/**
	 * Get the morphology type of this lexeme.
	 * @return A morphology type, or null
	 */
	public IXholon getMorph()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhc().hasAncestor(MorphCE)) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the morphology type of this lexeme, as an int.
	 * ex: MorphPlural_SibilantCE
	 * @return A morphology type, or MorphCE is none is specified.
	 */
	public int getMorphType()
	{
		IXholon xhType = getMorph();
		if (xhType == null) {
			return MorphCE;
		}
		int type = xhType.getXhcId();
		return type;
	}
	
	/**
	 * Get the plural morphology type of this noun lexeme, as an int.
	 * ex: MorphPlural_SibilantCE
	 * @return A morphology type, or MorphPlural_RegularCE is none is specified for the lexeme.
	 */
	public int getMorphPluralType()
	{
		IXholon xhType = getMorph();
		if (xhType == null) {
			return MorphPlural_RegularCE;
		}
		int type = xhType.getXhcId();
		return type;
	}
	
	/**
	 * <p>Get the number of words in the lemma of this lexeme,
	 * where a word is defined as text separated by white space.
	 * If the lemma is missing from the lexeme, or if the lemma is zero length, then return 0.</p>
	 * <p>ex: "hello" has one word, and "at the house of" has four words.</P>
	 * @return A count.
	 */
	public int getWordCount()
	{
		String lemmaString = getLemmaString();
		if ((lemmaString == null) || (lemmaString.length() == 0)) {return 0;}
		int count = 1; // there is at least one word
		for (int i = 0; i < lemmaString.length(); i++) {
			if (lemmaString.charAt(i) == ' ') {
				count++;
			}
		}
		return count;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#compareTo(java.lang.Object)
	 */
	public int compareTo(Object otherLexeme)
	{
		String lemmaStr = this.getLemmaString();
		if (lemmaStr == null) {
			getLogger().error("Null lemma string in " + getName());
			return 0;
		}
		if (otherLexeme == null) {
			getLogger().error("Null lexeme being compared with " + getName());
			return 0;
		}
		String otherLemmaStr = ((Lexeme)otherLexeme).getLemmaString();
		if (otherLemmaStr == null) {
			getLogger().error("Null other lemma string in " + ((Lexeme)otherLexeme).getName());
			return 0;
		}
		return lemmaStr.compareToIgnoreCase(otherLemmaStr);
	}
	
	/**
	 * main (for testing)
	 * @param args
	 */
	public static void main(String[] args) {
		IXholonClass lexemeClass = new XholonClass();
		lexemeClass.setId(LexemeCE);
		IXholonClass lemmaClass = new XholonClass();
		lemmaClass.setId(LemmaCE);
		
		IXholonClass morphGrandparentClass = new XholonClass();
		morphGrandparentClass.setId(MorphCE);
		IXholonClass morphParentClass = new XholonClass();
		morphParentClass.setId(MorphPluralCE);
		
		IXholonClass morphClass = new XholonClass();
		/*
		 * MorphPlural_RegularCE
		 * MorphPlural_NoChangeCE
		 * MorphPlural_SibilantCE
		 * MorphPlural_FfeCE
		 * MorphPlural_YCE
		 * MorphPlural_IrregularCE
		 * MorphPlural_OesCE
		*/
		morphClass.setId(MorphPlural_IrregularCE);
		
		morphClass.appendChild(morphParentClass);
		morphParentClass.appendChild(morphGrandparentClass);
		
		Lexeme lexeme = new Lexeme();
		lexeme.setXhc(lexemeClass);
		
		IXholon lemma = new XhEnglish2French();
		lemma.setXhc(lemmaClass);
		lemma.setVal("tooth"); // girl box deer elf knife berry monkey man
		
		IXholon morph = new XhEnglish2French();
		morph.setXhc(morphClass);
		
		lemma.appendChild(lexeme);
		morph.appendChild(lexeme);
		
		System.out.println(lexeme.getLemmaString()
				+ " " + lexeme.getLemmaStringPluralized());
	}

}
