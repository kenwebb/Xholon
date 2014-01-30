package org.primordion.user.app.English2French;

import org.primordion.xholon.base.HandleNodeSelectionResult;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;

public class WordPass extends XhEnglish2French {
	
	/*
	 * @see org.primordion.xholon.base.Xholon#handleNodeSelection()
	 */
	public Object handleNodeSelection()
	{
		if (hasChildNodes()) {
			return toString();
		}
		else {
			// only do this one time
			doWordPass();
			return new HandleNodeSelectionResult(toString(), HandleNodeSelectionResult.HNSR_REFRESH, null);
		}
	}
	
	/**
	 * Do word pass.
	 * Transform each word that appears in the English sentence,
	 * into a sequence of nodes that are children of WordPass.
	 */
	public void doWordPass()
	{
		Lexicon lexicon = (Lexicon)getXPath().evaluate("descendant::Lexicon", getRootNode());
		if (lexicon == null) {return;}
		IXholon tokenNode = getXPath().evaluate("descendant::TokenPass/Token", parentNode);
		appendChild("CM_xx", null, "org.primordion.user.app.English2French.XhEnglish2French");
		while (tokenNode != null) {
			String token = tokenNode.getVal_String();
			if (token.equals(".")) {break;}
			if (token.equals("?")) {break;}
			Lexeme lexeme = lexicon.getLexeme(tokenNode);
			if (lexeme != null) {
				//System.out.print(lexeme.getLemma() + " " + lexeme.getPos() + " ");
				// get the current last node
				IXholon lastChild = getLastChild();
				translateWord(lexeme, tokenNode);
				// handle plurals and other different forms
				handleDifferentForm(lexeme, tokenNode, lastChild);
				// the returned lexeme will have matched one or more tokens in the sentence
				// advance to the next token that has not already been matched
				for (int i = 0; i < lexeme.getWordCount(); i++) {
					tokenNode = tokenNode.getNextSibling();
				}
			}
			else { // lexeme == null
				appendChild("Lemma", null).setVal("[" + token + "]");
				tokenNode = tokenNode.getNextSibling();
			}
		}
		//System.out.print("{CM_xx . X-XXX-X}");
		appendChild("CM_xx", null, "org.primordion.user.app.English2French.XhEnglish2French");
		if (tokenNode == null) {
			appendChild("Lemma", null, "org.primordion.user.app.English2French.XhEnglish2French")
				.setVal("."); // . or ?
		}
		else {
			appendChild("Lemma", null, "org.primordion.user.app.English2French.XhEnglish2French")
				.setVal(tokenNode.getVal_String()); // . or ?
			if (tokenNode.getVal_String().equals("?")) {
				appendChild("X_QQQ_Q", null, "org.primordion.user.app.English2French.rule.Rule_X_QQQ_Q");
			}
		}
		appendChild("X_XXX_X", null, "org.primordion.user.app.English2French.rule.Rule_X_XXX_X");
		//System.out.println();
	}
	
	/**
	 * Translate an English word or expression into French words and symbols.
	 * @param lexeme An English lexeme.
	 * @param tokenNode A token in an English sentence.
	 */
	protected void translateWord(Lexeme lexeme, IXholon tokenNode)
	{
		if (lexeme == null) {return;}
		String transformRef = lexeme.getTransformRefString();
		if (transformRef == null) {
			WordRule wordRule = lexeme.getWordRule();
			if (wordRule == null) {return;}
			WordRuleResult wordRuleResult = wordRule.doRule(tokenNode);
			if (wordRuleResult == null) {return;}
			transformRef = wordRuleResult.getTransformRef();
			if (handleMetaInfo(wordRuleResult) == false) {return;}
		}
		handleTransform(transformRef, lexeme.getPos(), tokenNode);
	}
	
	/**
	 * Handle the transform.
	 * @param transformRef A reference to a transform.
	 * @param pos Part of speech of the English token.
	 * @param tokenNode The English token.
	 */
	protected void handleTransform(String transformRef, IXholon pos, IXholon tokenNode)
	{
		if (transformRef == null) {return;}
		//System.out.print("{" + transformRef + "} ");
		Transforms transforms = (Transforms)getXPath().evaluate("descendant::Transforms", getRootNode());
		if (transforms == null) {return;}
		Transform transform = transforms.getTransform(transformRef);
		if (transform == null) {
			// no match was found in the transform list
			//IXholon newNode = appendChild("Lemma", null, "org.primordion.user.app.English2French.XhEnglish2French");
			//newNode.setVal(transformRef);
			generateTransformNodes(transformRef, pos);
			return;
		}
		//System.out.print("{"  + transform + "} ");
		preprocessCircleMarker(transform);
		IXholon node = transform.getFirstChild();
		while (node != null) {
			if (node.getXhcId() != KeyCE) { // don't clone a Key node
				cloneNode(node, this);
			}
			node = node.getNextSibling();
		}
	}
	
	/**
	 * Generate a best-guess French transform based on the French String and the English part of speech.
	 * @param frenchStr A French word or other String.
	 * @param englishPos The English part of speech, or null.
	 */
	protected void generateTransformNodes(String frenchStr, IXholon englishPos) {
		if (englishPos == null) {
			generateTransformNodeLemma(frenchStr);
			return;
		}
		switch (englishPos.getXhcId()) {
		case PosNCE:    // noun
			/*
			<Lemma>abricot</Lemma>
			<C_PLU_A/>
			<GenderM/>
			<CM_nn/>
			<OpenBracket><FourLetterCode>cvxr</FourLetterCode></OpenBracket>
			 */
			generateTransformNodeLemma(frenchStr);
			appendChild("C_PLU_A", null, "org.primordion.user.app.English2French.rule.Rule_C_PLU");
			appendChild("GenderM", null);
			appendChild("CM_nn", null, "org.primordion.user.app.English2French.XhEnglish2French");
			generateTransformOpenBracket();
			break;
		case PosVCE:    // verb
			/*
			<ClosedBracket><VerbClass>3</VerbClass><Lemma>aimer</Lemma></ClosedBracket>
			<CM_vv/>
			 */
			generateTransformClosedBracket(frenchStr);
			appendChild("CM_vv", null);
			break;
		case PosACE:    // adjective
			/*
			<Lemma>beau</Lemma>
			<A_FPL_A/>
			 */
			generateTransformNodeLemma(frenchStr);
			appendChild("A_FPL_A", null, "org.primordion.user.app.English2French.rule.Rule_A_FPL");
			break;
		case PosPCE:    // preposition
			/*
			<CM_pp/>
			<Lemma>apr�s</Lemma>
			 */
			appendChild("CM_pp", null);
			generateTransformNodeLemma(frenchStr);
			break;
		case PosPronCE: // pronoun
			/*
			<Lemma>ceci</Lemma>
			<GenderM/>
			<CM_np/>
			<OpenBracket><FourLetterCode>cvxr</FourLetterCode></OpenBracket>
			 */
			generateTransformNodeLemma(frenchStr);
			appendChild("GenderM", null);
			appendChild("CM_np", null);
			generateTransformOpenBracket();
			break;
		case PosAdvCE:  // adverb
			/*
			<Lemma>bien</Lemma>
			 */
			generateTransformNodeLemma(frenchStr);
			break;
		case PosConjCE: // conjunction
			/*
			<CM_jj/>
			<Lemma>et</Lemma>
			<J_AND_A/>
			 */
			appendChild("CM_jj", null);
			generateTransformNodeLemma(frenchStr);
			appendChild("J_AND_A", null, "org.primordion.user.app.English2French.rule.Rule_J_AND_A");
			break;
		case PosDCE:    // determiner
			/*
			<Lemma>le</Lemma>
			<B_LEL_A/>
			 */
			generateTransformNodeLemma(frenchStr);
			appendChild("B_LEL_A", null, "org.primordion.user.app.English2French.rule.Rule_B_LEL_A");
			break;
		case PosQCE:    // quantifier
			/*
			<Lemma>deux</Lemma>
			 */
			generateTransformNodeLemma(frenchStr);
			break;
		case PosSCE:    // sentence or phrase
			/*
			<Lemma>Au revoir.</Lemma>
			 */
			generateTransformNodeLemma(frenchStr);
			break;
		default:
			generateTransformNodeLemma(frenchStr);
			break;
		}
	}
	
	/**
	 * Generate a French lemma node.
	 * @param frenchStr A French word or other String.
	 */
	protected void generateTransformNodeLemma(String frenchStr)
	{
		appendChild("Lemma", null).setVal(frenchStr);
	}
	
	/**
	 * Generate a complete OpenBracket node.
	 */
	protected void generateTransformOpenBracket()
	{
		IXholon openBracket = appendChild("OpenBracket", null);
		openBracket.appendChild("FourLetterCode", null).setVal("cvxr");
	}
	
	/**
	 * Generate a complete ClosedBracket node.
	 */
	protected void generateTransformClosedBracket(String frenchStr)
	{
		IXholon closedBracket = appendChild("ClosedBracket", null, "org.primordion.user.app.English2French.ClosedBracket");
		closedBracket.appendChild("VerbClass", null).setVal(3);
		closedBracket.appendChild("Lemma", null).setVal(frenchStr);
	}
	
	/**
	 * Do any required pre-processing based on the circle marker(s) contained within the transform.
	 * ex: handle passe compose such as �tre,parti and avoir,achet�
	 * @param transform 
	 */
	protected void preprocessCircleMarker(Transform transform)
	{
		// get the first circle marker in the transform
		IXholon circleMarker = transform.getCircleMarker();
		if (circleMarker == null) {return;}
		switch (circleMarker.getXhcId()) {
		case CM_vvCE: // ex: might be "avoir,achet�", or might be just "acheter"
		{
			IXholon lemmaNode = transform.getLemma();
			if (lemmaNode != null) {
				// there's a direct lemma node, so it's probably of the "avoir,achet�" form
				ClosedBracket closedBracket = (ClosedBracket)transform.getClosedBracket();
				if (closedBracket != null) {
					int verbClass = closedBracket.getVerbClassInt();
					if (verbClass == 1) { // 1 avoir
						// it's definitely of the "avoir,achet�" form
						removePrecedingAvoir();
					}
				}
			}
			break;
		}
		case CM_vcCE: // ex: "�tre,parti"
		{
			// remove any immediately preceding "1 avoir] CM_vv"
			removePrecedingAvoir();
			break;
		}
		default:
			break;
		}
	}
	
	/**
	 * Remove any immediately preceding "1 avoir] CM_vv".
	 */
	protected void removePrecedingAvoir()
	{
		IXholon lastChild = getLastChild();
		if ((lastChild != null) && (lastChild.getXhcId() == CM_vvCE)) {
			IXholon closedBracket = lastChild.getPreviousSibling();
			if ((closedBracket != null) && (closedBracket.getXhcId() == ClosedBracketCE)) {
				int verbClass = ((ClosedBracket)closedBracket).getVerbClassInt();
				if (verbClass == 1) { // 1 avoir
					lastChild.removeChild();
					closedBracket.removeChild();
				}
			}
		}
	}
	
	/**
	 * Handle any meta information returned from a WordRule.
	 * @param wordRuleResult 
	 * @return true (caller should continue processing), or false (caller should cease processing)
	 */
	protected boolean handleMetaInfo(WordRuleResult wordRuleResult)
	{
		if (wordRuleResult == null) {return false;}
		int metaInfo = wordRuleResult.getMetaInfo();
		switch (metaInfo) {
		
		case WordRuleResult.MI_VERB_PASTTENSE:
		{
			// modify the previous OpenBracket FourLetterCode. ( [___r --> [___m )
			// where "r" = pResent and "m" = iMperfect
			IXholon node = getLastChild();
			//System.out.println(node);
			if (node.getXhcId() == OpenBracketCE) {
				node = node.getFirstChild();
				//System.out.println(node);
				if ((node != null) && (node.getXhcId() == FourLetterCodeCE)) {
					String str = node.getVal_String();
					if (str != null) {
						str = str.substring(0, str.length()-1) + "m";
						node.setVal(str);
					}
				}
			}
			return true;
		}
		
		case WordRuleResult.MI_THAT:
		{
			IXholon node = getLastChild();
			
			// handle an ELLIPSIS sentence, such as:
			// The town that we saw yesterday is beautiful.
			IXholon prevCircleMarker = ((XhEnglish2French)node).getPreviousCircleMarker();
			if (prevCircleMarker != null) {
				if (prevCircleMarker.getXhcId() == CM_nnCE) { // a noun
					if (node.getXhcId() == OpenBracketCE) {
						// put the existing OpenBracket inside the new OpenEllipse
						node.insertBefore("OpenEllipse", null);
					}
					else {
						appendChild("OpenEllipse", null);
					}
					appendChild("Lemma", null).setVal("qui");
					appendChild("Q_QUI_R", null, "org.primordion.user.app.English2French.rule.Rule_Q_QUI_R");
					appendChild("CM_np", null);
					return false;
				}
			}
			
			handleTransform(wordRuleResult.getTransformRef(), null, null);
			// modify the G_CIL flag node from G_CIL_A (this) to G_CIL_B (that)
			node = node.getNextSibling();
			while (node != null) {
				if (node.getXhcId() == G_CIL_ACE) {
					node.setXhc(getClassNode(G_CIL_BCE));
					break;
				}
				node = node.getNextSibling();
			}
			return false;
		}
		
		// QUESTION results
		case WordRuleResult.MI_Q_AMAREIS:
		case WordRuleResult.MI_Q_CAN:
		case WordRuleResult.MI_Q_DID:
		case WordRuleResult.MI_Q_DODOES:
		case WordRuleResult.MI_Q_HASHAVE:
		case WordRuleResult.MI_Q_MAY:
		case WordRuleResult.MI_Q_MUST:
		case WordRuleResult.MI_Q_WASWERE:
		case WordRuleResult.MI_Q_WILL:
			handleQuestion(wordRuleResult);
			return false;
		
		case WordRuleResult.MI_NULL:
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * Clone a node, and all of its children.
	 * @param node The node that needs to be cloned, and attached to the parent.
	 * @param aParent The parent of the new node.
	 */
	protected void cloneNode(IXholon node, IXholon aParent)
	{
		if (node == null) {return;}
		IXholon newNode = aParent.appendChild(node.getXhcName(), node.getRoleName(), node.getClass().getName());
		newNode.setVal(node.getVal_String());
		newNode.setVal(node.getVal_int());
		// recursive
		node = node.getFirstChild();
		while (node != null) {
			cloneNode(node, newNode);
			node = node.getNextSibling();
		}
	}
	
	/**
	 * Handle any differences between the tokenNode form, and the lexeme form.
	 * For example, the tokenNode form might be a plural such as "girls",
	 * while the matched lexeme form might be the singular "girl".
	 * @param lexeme An English lexeme.
	 * @param tokenNode A token in an English sentence.
	 * @param lastChild The current last child of this WordPass node.
	 */
	protected void handleDifferentForm(Lexeme lexeme, IXholon tokenNode, IXholon lastChild)
	{
		String lexemeForm = lexeme.getLemmaString();
		String tokenNodeForm = tokenNode.getVal_String();
		if (lexemeForm.equalsIgnoreCase(tokenNodeForm)) {return;} // no difference
		// confirm that the lexeme is a noun, and that the tokenNode is a pluralized form
		if ((lexeme.isNoun()) && (lexeme.getLemmaStringPluralized().equalsIgnoreCase(tokenNodeForm))) {
			IXholon genderNode = getGender(lastChild);
			if (genderNode != null) {
				switch (genderNode.getXhcId()) {
				case GenderMCE:
				case GenderMSCE:
					genderNode.setXhc(getClassNode(GenderMPCE));
					break;
				case GenderFCE:
				case GenderFSCE:
					genderNode.setXhc(getClassNode(GenderFPCE));
					break;
				default:
					break;
				}
			}
			IXholon fourLetterCode = getFourLetterCode(lastChild);
			if (fourLetterCode != null) {
				String str = fourLetterCode.getVal_String();
				if ((str != null) && (str.length() == 4)) {
					str = "" + str.charAt(0) + 'w' + str.charAt(2) + str.charAt(3);
				}
				fourLetterCode.setVal(str);
			}
		}
	}
	
	/**
	 * Handle QUESTION.
	 * @param wordRuleResult
	 */
	protected void handleQuestion(WordRuleResult wordRuleResult)
	{
		// create and add a Lemma node with the default transformRef value of "est-ce que"
		appendChild("Lemma", null).setVal(wordRuleResult.getTransformRef());
		switch(wordRuleResult.getMetaInfo()) {
		case WordRuleResult.MI_Q_AMAREIS:
			//appendChild("V_QUE_C", null, getClassNode(V_QUE_CCE).getImplName());
			appendChildVQue("V_QUE_C");
			break;
		case WordRuleResult.MI_Q_CAN:
			appendChildVQue("V_QUE_E");
			break;
		case WordRuleResult.MI_Q_DID:
			break;
		case WordRuleResult.MI_Q_DODOES:
			break;
		case WordRuleResult.MI_Q_HASHAVE:
			break;
		case WordRuleResult.MI_Q_MAY:
			appendChildVQue("V_QUE_E");
			break;
		case WordRuleResult.MI_Q_MUST:
			appendChildVQue("V_QUE_F");
			break;
		case WordRuleResult.MI_Q_WASWERE:
			appendChildVQue("V_QUE_B");
			break;
		case WordRuleResult.MI_Q_WILL:
			appendChildVQue("V_QUE_D");
			break;
		default:
			break;
		}
	}
	
	/**
	 * Append child node, of one of the V_QUE class types.
	 * @param xhClassName The name of the V_QUE class type
	 */
	protected void appendChildVQue(String xhClassName)
	{
		IXholonClass xc = getClassNode(xhClassName);
		if (xc == null) {return;}
		String implName = xc.getImplName();
		if (implName == null) {return;}
		appendChild(xhClassName, null, implName);
	}
	
	/**
	 * Get the gender node (GenderM, GenderF, GenderMS, GenderFS, GenderMP, GenderFP)
	 * associated with the specified node.
	 * @return A gender node, or null.
	 */
	protected IXholon getGender(IXholon node)
	{
		return ((XhEnglish2French)node).getNextGender();
	}
	
	/**
	 * Get the OpenBracket node associated with the specified node.
	 * @return A OpenBracket node, or null.
	 */
	protected IXholon getOpenBracket(IXholon node)
	{
		return ((XhEnglish2French)node).getNextOpenBracket();
	}
	
	/**
	 * Get the FourLetterCode node associated with the specified node.
	 * @return A FourLetterCode node, or null.
	 */
	protected IXholon getFourLetterCode(IXholon node)
	{
		return ((XhEnglish2French)node).getNextFourLetterCode();
	}
	
}
