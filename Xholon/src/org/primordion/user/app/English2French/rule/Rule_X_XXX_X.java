package org.primordion.user.app.English2French.rule;

import org.primordion.user.app.English2French.ClosedBracket;
import org.primordion.xholon.base.IXholon;

public class Rule_X_XXX_X extends Rule_Flag {
	
	// French special characters: �� ��� � � �� �
	
	/** French verb endings, present tense */
	private String[][] verbEndings_present = {
		{"","","","","","",""}, // ex: 0 
		{"avoir","ai","as","a","avons","avez","ont"}, // ex: 1 avoir
		{"�tre","suis","es","est","sommes","�tes","sont"}, // ex: 2 �tre
		// verbs ending in -er
		{"er","e","es","e","ons","ez","ent"}, // ex: 3 arriver, parler
		{"aller","vais","vas","va","allons","allez","vont"}, // ex: 4 aller
		{"yer","ie","ies","ie","yons","yez","ient"}, // ex: 5 employer
		{"er","le","les","le","ons","ez","lent"}, // ex: 6 appeler
		{"er","e","es","e","eons","ez","ent"}, // ex: 7 manger, voyager
		{"cer","ce","ces","ce","�ons","cez","cent"}, // ex: 8 commencer
		{"eter","�te","�tes","�te","etons","etez","�tent"}, // ex: 9 acheter
		// verbs ending in -ir
		{"ir","is","is","it","issons","issez","issent"}, // ex: 10 choisir, finir
		{"ir","s","s","t","ons","ez","ent"}, // ex: 11 courir
		{"tir","s","s","t","tons","tez","tent"}, // ex: 12 sentir
		{"mir","s","s","t","mons","mez","ment"}, // ex: 13 dormir
		{"vir","s","s","t","vons","vez","vent"}, // ex: 14 servir
		{"enir","iens","iens","ient","enons","enez","iennent"}, // ex: 15 venir
		{"ourir","eurs","eurs","eurt","ourons","ourez","eurent"}, // ex: 16 mourir
		{"","","","","","",""}, // ex: 17 
		{"","","","","","",""}, // ex: 18 
		{"","","","","","",""}, // ex: 19 
		{"","","","","","",""}, // ex: 20 
		{"","","","","","",""}, // ex: 21 
		{"","","","","","",""}, // ex: 22 
		{"","","","","","",""}, // ex: 23 
		{"","","","","","",""}, // ex: 24 
		{"","","","","","",""}, // ex: 25 
		// verbs ending in -oir
		{"cevoir","�ois","�ois","�oit","cevons","cevez","�oivent"}, // ex: 26 recevoir
		{"evoir","ois","ois","oit","evons","evez","oivent"}, // ex: 27 devoir
		{"ouvoir","eux","eux","eut","ouvons","ouvez","euvent"}, // ex: 28 pouvoir
		{"ouloir","eux","eux","eut","oulons","oulez","eulent"}, // ex: 29 vouloir
		{"voir","is","is","it","vons","vez","vent"}, // ex: 30 savoir
		{"oir,oire","ois","ois","oit","oyons","oyez","oient"}, //ex: 31 voir, croire
		{"oire","ois","ois","oit","uvons","uvez","oivent"}, // ex: 32 boire
		{"eoir","ieds","ieds","ied","eyons","eyez","eyent"}, // ex: 33 asseoir
		{"voir","","","t","","",""}, // ex: 34 pleuvoir
		{"","","","","","",""}, // ex: 35 
		{"","","","","","",""}, // ex: 36 
		{"","","","","","",""}, // ex: 37 
		{"","","","","","",""}, // ex: 38 
		{"","","","","","",""}, // ex: 39 
		// verbs ending in -re
		{"re","s","s","t","ons","ez","ent"}, // ex: 40 rendre
		{"tre","s","s","","tons","tez","tent"}, // ex: 41 mettre
		{"vre","s","s","t","vons","vez","vent"}, // ex: 42 suivre
		{"dre","ds","ds","d","ons","ez","nent"}, // ex: 43 apprendre, prendre
		{"re","s","s","t","sons","tes","sent"}, // ex: 44 dire
		{"re","s","s","t","sons","sez","sent"}, // ex: 45 lire
		{"re","s","s","t","vons","vez","vent"}, // ex: 46 �crire
		{"aire","ais","ais","ait","aisons","aites","ont"}, // ex: 47 faire
		{"a�tre","ais","ais","a�t","aissons","aissez","aissent"} // ex: 48 conna�tre
	};
	
	/** French verb endings, imperfect tense */
	private String[][] verbEndings_imperfect = {
		{"","","","","","",""}, // ex: 0 
		{"avoir","avais","avais","avait","avions","aviez","avaient"}, // ex: 1 avoir
		{"�tre","�tais","�tais","�tait","�tions","�tiez","�taient"}, // ex: 2 �tre
		// verbs ending in -er
		{"er","ais","ais","ait","ions","iez","aient"}, // ex: 3 arriver
		{"er","ais","ais","ait","ions","iez","aient"}, // ex: 4 aller
		{"er","ais","ais","ait","ions","iez","aient"}, // ex: 5 employer
		{"er","","","","","",""}, // ex: 6 
		{"er","","","","","",""}, // ex: 7 manger
		{"cer","","","","","",""}, // ex: 8 commencer
		{"eter","","","","","",""}, // ex: 9 acheter
		// verbs ending in -ir
		{"ir","issais","issais","issait","issions","issiez","issaient"}, // ex: 10 choisir
		{"ir","ais","ais","ait","ions","iez","aient"}, // ex: 11 courir
		{"ir","ais","ais","ait","ions","iez","aient"}, // ex: 12 sentir
		{"ir","ais","ais","ait","ions","iez","aient"}, // ex: 13 dormir
		{"ir","ais","ais","ait","ions","iez","aient"}, // ex: 14 servir
		{"ir","ais","ais","ait","ions","iez","aient"}, // ex: 15 venir
		{"ir","ais","ais","ait","ions","iez","aient"}, // ex: 16 mourir
		{"","","","","","",""}, // ex: 17 
		{"","","","","","",""}, // ex: 18 
		{"","","","","","",""}, // ex: 19 
		{"","","","","","",""}, // ex: 20 
		{"","","","","","",""}, // ex: 21 
		{"","","","","","",""}, // ex: 22 
		{"","","","","","",""}, // ex: 23 
		{"","","","","","",""}, // ex: 24 
		{"","","","","","",""}, // ex: 25 
		// verbs ending in -oir
		{"oir","ais","ais","ait","ions","iez","aient"}, // ex: 26 recevoir
		{"oir","ais","ais","ait","ions","iez","aient"}, // ex: 27 devoir
		{"oir","ais","ais","ait","ions","iez","aient"}, // ex: 28 pouvoir
		{"oir","ais","ais","ait","ions","iez","aient"}, // ex: 29 vouloir
		{"oir","ais","ais","ait","ions","iez","aient"}, // ex: 30 savoir
		{"oir,oire","oyais","oyais","oyait","oyions","oyiez","oyaient"}, // ex: 31 voir, croire
		{"oire","uvais","uvais","uvait","uvions","uviez","uvaient"}, // ex: 32 boire
		{"eoir","eyais","eyais","eyait","eyions","eyiez","eyaient"}, // ex: 33 asseoir
		{"oir","","","ait","","",""}, // ex: 34 pleuvoir ???
		{"","","","","","",""}, // ex: 35 
		{"","","","","","",""}, // ex: 36 
		{"","","","","","",""}, // ex: 37 
		{"","","","","","",""}, // ex: 38 
		{"","","","","","",""}, // ex: 39 
		// verbs ending in -re
		{"re","ais","ais","ait","ions","iez","aient"}, // ex: 40 rendre
		{"re","ais","ais","ait","ions","iez","aient"}, // ex: 41 mettre
		{"re","ais","ais","ait","ions","iez","aient"}, // ex: 42 suivre
		{"dre","ais","ais","ait","ions","iez","aient"}, // ex: 43 apprendre
		{"re","sais","sais","sait","sions","siez","saient"}, // ex: 44 dire
		{"re","sais","sais","sait","sions","siez","saient"}, // ex: 45 lire
		{"re","vais","vais","vait","vions","viez","vaient"}, // ex: 46 �crire
		{"aire","aisais","aisais","aisait","aisions","aisiez","aisaient"}, // ex: 47 faire
		{"a�tre","aissais","aissais","aissait","aissions","aissiez","aissaient"} // ex: 48 conna�tre
	};
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {return "X_XXX_X";}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		IXholon openBracket = getNextBracketPair(this);
		while (openBracket != null) {
			ClosedBracket closedBracket = (ClosedBracket)openBracket.getNextSibling();
			IXholon verb = closedBracket.getLemma();
			if (verb == null) {return;}
			int verbClass = closedBracket.getVerbClassInt();
			IXholon fourLetterCode = openBracket.getFirstChild();
			if (fourLetterCode == null) {return;}
			openBracket.insertAfter("Lemma", null)
				.setVal(getConjugatedVerb(fourLetterCode.getVal_String(), verbClass, verb.getVal_String()));
			openBracket = getNextBracketPair(openBracket);
		}
	}
	
	/**
	 * Get the next pair of OpenBracket ClosedBracket pairs.
	 * @param startNode The node from which to start the search.
	 * @return An instance of OpenBracket that's paired with a ClosedBracket, or null.
	 */
	protected IXholon getNextBracketPair(IXholon startNode)
	{
		IXholon node = null;
		if (startNode.getXhcId() == X_XXX_XCE) {
			node = startNode.getFirstSibling();
		}
		else {
			node = startNode.getNextSibling();
		}
		while (node != null) {
			if (node.getXhcId() == OpenBracketCE) {
				IXholon nextNode = node.getNextSibling();
				if ((nextNode != null) && (nextNode.getXhcId() == ClosedBracketCE)) {
					return node;
				}
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the conjugated form of a verb
	 * @param fourLetterCode A four letter code that specifies:
	 * (1) first a, second b, or third c person.
	 * (2) singular v, or plural w.
	 * (3) x
	 * (4) present r, or imperfect m tense.
	 * ex: avxm cvxr nfnv
	 * @param verbClass A number that identifies the conjugation class.
	 * @param infinitive The infinitive form of the verb.
	 * @return The conjugated form of the verb, or the infinitive form is the conjugaison couldn't be done.
	 */
	protected String getConjugatedVerb(String fourLetterCode, int verbClass, String infinitive) {
		//System.out.println("" + fourLetterCode + " " + verbClass + " " + infinitive);
		if ((fourLetterCode == null) || (fourLetterCode.length() != 4)) {return infinitive;}
		if (fourLetterCode.equals("nfnv")) {return infinitive;}
		if (verbClass >= verbEndings_present.length) {return infinitive;}
		char person = fourLetterCode.charAt(0);
		char number = fourLetterCode.charAt(1);
		char tense = fourLetterCode.charAt(3);
		String conjVerb = null;
		String[] endings = null;
		switch (verbClass) {
		case 31: // ex: voir
			// cross out the -oir(e)
			if (infinitive.endsWith("oir")) {
				conjVerb = infinitive.substring(0, infinitive.length()-3);
			}
			else if (infinitive.endsWith("oire")){
				conjVerb = infinitive.substring(0, infinitive.length()-4);
			}
			else {return infinitive;}
			if (tense == 'r') { // present
				endings = verbEndings_present[verbClass]; //{"oir","ois","ois","oit","oyons","oyez","oient"};
				conjVerb += getVerbEnding(person, number, endings);
			}
			else { // imperfect
				endings = verbEndings_imperfect[verbClass]; //{"oir","oyais","oyais","oyait","oyions","oyiez","oyaient"};
				conjVerb += getVerbEnding(person, number, endings);
			}
			return conjVerb;
		default:
			if (tense == 'r') { // present
				endings = verbEndings_present[verbClass];
			}
			else { // 'm' imperfect
				endings = verbEndings_imperfect[verbClass];
			}
			if (infinitive.endsWith(endings[0])) {
				conjVerb = infinitive.substring(0, infinitive.length()-endings[0].length());
			}
			else {return infinitive;}
			conjVerb += getVerbEnding(person, number, endings);
			return conjVerb;
		}
	}
	
	/**
	 * Get the correct verb ending.
	 * @param person first person 'a', second person 'b', or third person 'c'
	 * @param number singular 'v', or plural 'w'
	 * @param endings The six possible verb endings based on person and number.
	 * @return One of the six possible verb endings.
	 */
	protected String getVerbEnding(char person, char number, String[] endings)
	{
		switch (person) {
		case 'a':
			if (number == 'v') {return endings[1];}
			else {return endings[4];}
		case 'b':
			if (number == 'v') {return endings[2];}
			else {return endings[5];}
		case 'c':
			if (number == 'v') {return endings[3];}
			else {return endings[6];}
		default:
			return null;
		}
	}
	
}
