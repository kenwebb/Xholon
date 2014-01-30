package org.primordion.user.app.English2French.rule;

import org.primordion.user.app.English2French.XhEnglish2French;
import org.primordion.xholon.base.IXholon;

public class Rule_A_FPL extends Rule_Flag {
	
	// French special characters: �� ��� � � �� �
	
	/** possible endings */
	String[][] endings = {
			// Remove, MascSing, FemSing, MascPl, FemPl
			{"","","e","s","es"}, // A
			{"","","e","","es"}, // B
			{"","","he","s","hes"}, // C
			{"","","le","s","les"}, // D
			{"ais","ais","a�che","ais","a�ches"}, // E
			{"f","f","ve","fs","ves"}, // F
			{"ec","ec","�che","ecs","�ches"}, // G
			{"","","","s","s"}, // H
			{"","","ue","s","ues"}, // I
			{"eau","eau","elle","eaux","elles"}, // L
			{"","","ne","s","nes"}, // N
			{"er","er","�re","ers","�res"}, // R
			{"t","t","te","s","tes"}, // S
			{"","","te","s","tes"}, // T
			{"ux","ux","ille","ux","illes"}, // W
			{"x","x","ce","x","ces"}, // X
			{"eux","eux","euse","eux","euses"}, // Y
			{"aux","aux","ausse","aux","ausses"} // Z
	};
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#getRuleId()
	 */
	public String getRuleId() {
		switch (this.getXhcId()) {
		case A_FPL_ACE: return "A_FPL_A";
		case A_FPL_BCE: return "A_FPL_B";
		case A_FPL_CCE: return "A_FPL_C";
		case A_FPL_DCE: return "A_FPL_D";
		case A_FPL_ECE: return "A_FPL_E";
		case A_FPL_FCE: return "A_FPL_F";
		case A_FPL_GCE: return "A_FPL_G";
		case A_FPL_HCE: return "A_FPL_H";
		case A_FPL_ICE: return "A_FPL_I";
		case A_FPL_LCE: return "A_FPL_L";
		case A_FPL_NCE: return "A_FPL_N";
		case A_FPL_RCE: return "A_FPL_R";
		case A_FPL_SCE: return "A_FPL_S";
		case A_FPL_TCE: return "A_FPL_T";
		case A_FPL_WCE: return "A_FPL_W";
		case A_FPL_XCE: return "A_FPL_X";
		case A_FPL_YCE: return "A_FPL_Y";
		case A_FPL_ZCE: return "A_FPL_Z";
		default: return null;
		}
	}
	
	/*
	 * @see org.primordion.user.app.English2French.rule.Rule_Flag#doRule()
	 */
	public void doRule()
	{
		// is the next circle marker CM_nn
		IXholon circleMarker = getNextCircleMarker();
		if (circleMarker == null) {return;}
		if (circleMarker.getXhcId() == CM_nnCE) {
			doRuleDetailsNN();
			return;
		}
		
		// is the preceding circle marker CM_vc or CM_jj
		circleMarker = getPreviousCircleMarker();
		if (circleMarker == null) {return;}
		if (circleMarker.getXhcId() == CM_vcCE) {
			doRuleDetailsVC();
			return;
		}
		if (circleMarker.getXhcId() == CM_jjCE) {
			// TODO
			System.out.println("A_FPL jj");
			return;
		}
	}
	
	/**
	 * Do details of the rule, where next circle marker is CM_nn.
	 */
	protected void doRuleDetailsNN()
	{
		// get the next gender marker
		IXholon gender = getNextGender();
		if (gender == null) {return;}
		// get the previous lemma
		IXholon lemma = getPreviousLemma();
		if (lemma == null) {return;}
		// get the index into the endings array
		int index = getXhcId()-A_FPL_ACE;
		modifyLemma(gender, lemma, endings[index]);
	}
	
	/**
	 * Do details of the rule, where previous circle marker is CM_vc.
	 */
	protected void doRuleDetailsVC()
	{
		// get the previous gender marker
		IXholon gender = getPreviousGender();
		if (gender == null) {return;}
		// get the previous lemma
		IXholon lemma = getPreviousLemma();
		if (lemma == null) {return;}
		// get the index into the endings array
		int index = getXhcId()-A_FPL_ACE;
		modifyLemma(gender, lemma, endings[index]);
	}
	
	/**
	 * Modify the lemma.
	 * @param gender
	 * @param lemma
	 * @param endings
	 */
	protected void modifyLemma(IXholon gender, IXholon lemma, String[] endings)
	{
		String lemmaStr = lemma.getVal_String();
		if ((endings[0].length() > 0) && (lemmaStr.endsWith(endings[0]))) {
			lemmaStr = lemmaStr.substring(0, lemmaStr.length()-endings[0].length());
		}
		switch(gender.getXhcId()) {
		case GenderMCE:
		case GenderMSCE:
			if (getXhcId() == A_FPL_LCE) {
				// handle A_FPL_L special case for masc singular
				lemmaStr += handleSpecialCase(gender, "el");
			}
			else if (getXhcId() == A_FPL_WCE) {
				// handle A_FPL_W special case for masc singular
				lemmaStr += handleSpecialCase(gender, "il");
			}
			else {
				lemmaStr += endings[1];
			}
			break;
		case GenderFCE:
		case GenderFSCE:
			lemmaStr += endings[2];
			break;
		case GenderMPCE:
			lemmaStr += endings[3];
			break;
		case GenderFPCE:
			lemmaStr += endings[4];
			break;
		default:
			break;
		}
		lemma.setVal(lemmaStr);
	}
	
	/**
	 * Handle special cases.
	 * @param genderNode
	 * @param specialEnding
	 * @return
	 */
	protected String handleSpecialCase(IXholon genderNode, String specialEnding)
	{
		//IXholon word = getPreviousLemma(genderNode);
		IXholon word = ((XhEnglish2French)genderNode).getPreviousLemma();
		if (word != null) {
			String wordStr = word.getVal_String();
			if (wordStr != null) {
				if (isVowelOrH(wordStr.charAt(0))) {
					return specialEnding;
				}
			}
		}
		return "";
	}
	
}
