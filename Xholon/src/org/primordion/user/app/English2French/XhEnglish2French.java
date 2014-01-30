/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.user.app.English2French;

//import javax.swing.JOptionPane; // GWT
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;

/**
	English2French application - Xholon Java
	<p>Xholon 0.7 http://www.primordion.com/Xholon</p>
	TODO jump over ellipse pairs; ex: one ( two three four ) five
*/
public class XhEnglish2French extends XholonWithPorts implements CeEnglish2French {

// parts of speech constants
public static final String POS_NULL = "?";
public static final String POS_N    = "N";
public static final String POS_V    = "V";
public static final String POS_A    = "A";
public static final String POS_P    = "P";
public static final String POS_Pron = "Pron";
public static final String POS_Adv  = "Adv";
public static final String POS_Conj = "Conj";
public static final String POS_D    = "D";
public static final String POS_Q    = "Q";
public static final String POS_S    = "S";

// references to other Xholon instances; indices into the port array
public static final int SamplePort = 0;

// Signals, Events
public static final int SIG_SAMPLE = 100;

// Variables
public String roleName = null;
private String valString;
private int valInt;

// Constructor
public XhEnglish2French() {super();}

/**
 * Ask the human user for a yes/no answer.
 * @param question A yes/no question to ask the human
 * (ex: Does the following phrase still make sense if you cross out "am", "is", "are", or "be", and "ing")
 * @param xhNode A xholon whose toString() method will return a second line of text,
 * such as an English phrase or sentence (ex: I am reading a book.)
 * @return true (Yes) or false (No)
 */
protected boolean ask4YesNoAnswer(String question, IXholon xhNode)
{
	return ask4YesNoAnswer(question + "\n" + xhNode);
}

/**
 * Ask the human user for a yes/no answer.
 * @param question A yes/no question to ask the human
 * (ex: Does the following phrase still make sense if you cross out "am", "is", "are", or "be", and "ing")
 * @return true (Yes) or false (No)
 */
protected boolean ask4YesNoAnswer(String question)
{
  // GWT  TODO
	/*int answer = JOptionPane.showConfirmDialog(
			null,
			question,
			"Select Yes or No",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE);
	if (answer == JOptionPane.YES_OPTION) {return true;}
	else {return false;}*/
	return true;
}

/**
 * Get the next OpenEllipse node.
 * @return An OpenEllipse node, or null.
 */
public IXholon getNextOpenEllipse()
{
	return getNextSibling(OpenEllipseCE);
}

/**
 * Get the previous OpenEllipse node.
 * @return An OpenEllipse node, or null.
 */
public IXholon getPreviousOpenEllipse()
{
	return getPreviousSibling(OpenEllipseCE);
}

/**
 * Get the next ClosedEllipse node.
 * @return A ClosedEllipse node, or null.
 */
public IXholon getNextClosedEllipse()
{
	return getNextSibling(ClosedEllipseCE);
}

/**
 * Get the previous ClosedEllipse node.
 * @return A ClosedEllipse node, or null.
 */
public IXholon getPreviousClosedEllipse()
{
	return getPreviousSibling(ClosedEllipseCE);
}

/**
 * Is it an open ellipse node "(" ?
 * @return true or false
 */
public boolean isOpenEllipse()
{
	return isMatchingNode(OpenEllipseCE);
}

/**
 * Is it a closed ellipse node ")" ?
 * @return true or false
 */
public boolean isClosedEllipse()
{
	return isMatchingNode(ClosedEllipseCE);
}

/**
 * Get the next gender node (GenderM, GenderF, GenderMS, GenderFS, GenderMP, GenderFP).
 * @return A gender node, or null.
 */
public IXholon getNextGender()
{
	return getNextSiblingAncestor(GenderCE);
}

/**
 * Get the previous gender node (GenderM, GenderF, GenderMS, GenderFS, GenderMP, GenderFP).
 * @return A gender node, or null.
 */
public IXholon getPreviousGender()
{
	return getPreviousSiblingAncestor(GenderCE);
}

/**
 * Get the next Lemma node.
 * @return A lemma node, or null.
 */
public IXholon getNextLemma()
{
	return getNextSibling(LemmaCE);
}

/**
 * Get the previous Lemma node.
 * @return A lemma node, or null.
 */
public IXholon getPreviousLemma()
{
	return getPreviousSibling(LemmaCE);
}

/**
 * Get the next CircleMarker node (CM_nn, etc.).
 * @return A CircleMarker node, or null.
 */
public IXholon getNextCircleMarker()
{
	return getNextSiblingAncestor(CircleMarkerCE);
}

/**
 * Get the previous CircleMarker node (CM_nn, etc.).
 * @return A CircleMarker node, or null.
 */
public IXholon getPreviousCircleMarker()
{
	return getPreviousSiblingAncestor(CircleMarkerCE);
}

/**
 * Get the next Bracket node (OpenBracket, ClosedBracket).
 * @return A Bracket node, or null.
 */
public IXholon getNextBracket()
{
	return getNextSiblingAncestor(BracketCE);
}

/**
 * Get the previous Bracket node (OpenBracket, ClosedBracket).
 * @return A Bracket node, or null.
 */
public IXholon getPreviousBracket()
{
	return getPreviousSiblingAncestor(BracketCE);
}

/**
 * Get the next OpenBracket node.
 * @return An OpenBracket node, or null.
 */
public IXholon getNextOpenBracket()
{
	return getNextSibling(OpenBracketCE);
}

/**
 * Get the previous OpenBracket node.
 * @return An OpenBracket node, or null.
 */
public IXholon getPreviousOpenBracket()
{
	return getPreviousSibling(OpenBracketCE);
}

/**
 * Get the next ClosedBracket node.
 * @return A ClosedBracket node, or null.
 */
public IXholon getNextClosedBracket()
{
	return getNextSiblingAncestor(ClosedBracketCE);
}

/**
 * Get the previous ClosedBracket node.
 * @return A ClosedBracket node, or null.
 */
public IXholon getPreviousClosedBracket()
{
	return getPreviousSiblingAncestor(ClosedBracketCE);
}

/**
 * Get the next FourLetterCode node.
 * @return A FourLetterCode node, or null.
 */
public IXholon getNextFourLetterCode()
{
	IXholon openBracket = getNextOpenBracket();
	if (openBracket != null) {
		IXholon four = openBracket.getFirstChild();
		while (four != null) {
			if (((XhEnglish2French)four).isMatchingNode(FourLetterCodeCE)) {
				return four;
			}
			four = four.getNextSibling();
		}
	}
	return null;
}

/**
 * Get the previous FourLetterCode node.
 * @return A FourLetterCode node, or null.
 */
public IXholon getPreviousFourLetterCode()
{
	IXholon openBracket = getPreviousOpenBracket();
	if (openBracket != null) {
		IXholon four = openBracket.getFirstChild();
		while (four != null) {
			if (((XhEnglish2French)four).isMatchingNode(FourLetterCodeCE)) {
				return four;
			}
			four = four.getNextSibling();
		}
	}
	return null;
}

/**
 * Get the next Flag node (B_LEL_A, etc.).
 * @return A Flag node, or null.
 */
protected IXholon getNextFlag()
{
	return getNextSiblingAncestor(FlagCE);
}

/**
 * Get the previous Flag node (B_LEL_A, etc.).
 * @return A Flag node, or null.
 */
protected IXholon getPreviousFlag()
{
	return getPreviousSiblingAncestor(FlagCE);
}

/**
 * Get the next sibling that's an instance of the specified Xholon class.
 * @param xhcId A Xholon class ID.
 * @return A matching IXholon node, or null.
 */
protected IXholon getNextSibling(int xhcId)
{
	IXholon node = getNextSibling();
	while ((node != null)) {
		if (((XhEnglish2French)node).isMatchingNode(xhcId)) {
			return node;
		}
		// handle ellipse
		if (((XhEnglish2French)node).isOpenEllipse()) {
			node = ((XhEnglish2French)node).getNextClosedEllipse();
			if (node == null) {return null;}
			node = node.getNextSibling();
		}
		else if (((XhEnglish2French)node).isClosedEllipse()) {
			// next sibling within the current ellipse has not been found
			return null;
		}
		else {
			node = node.getNextSibling();
		}
	}
	return null;
}

/**
 * Get the previous sibling that's an instance of the specified Xholon class.
 * @param xhcId A Xholon class ID.
 * @return A matching IXholon node, or null.
 */
protected IXholon getPreviousSibling(int xhcId)
{
	IXholon node = getPreviousSibling();
	while ((node != null)) {
		if (((XhEnglish2French)node).isMatchingNode(xhcId)) {
			return node;
		}
		// handle ellipse
		if (((XhEnglish2French)node).isClosedEllipse()) {
			node = ((XhEnglish2French)node).getPreviousOpenEllipse();
			if (node == null) {return null;}
			node = node.getPreviousSibling();
		}
		else if (((XhEnglish2French)node).isOpenEllipse()) {
			// previous sibling within the current ellipse has not been found
			return null;
		}
		else {
			node = node.getPreviousSibling();
		}
	}
	return null;
}

/**
 * Is it a node of the specified Xholon class ID?
 * @param xhcId A Xholon class ID.
 * @return true or false
 */
protected boolean isMatchingNode(int xhcId)
{
	if (getXhcId() == xhcId) {
		return true;
	}
	else {
		return false;
	}
}

/**
 * Get the next sibling that's an instance of the specified Xholon class, or of some ancestor of that class.
 * @param xhcId An ID that specifies a Xholon class or superclass.
 * @return A matching IXholon node, or null.
 */
protected IXholon getNextSiblingAncestor(int xhcId)
{
	IXholon node = getNextSibling();
	while ((node != null)) {
		if (((XhEnglish2French)node).isMatchingNodeAncestor(xhcId)) {
			return node;
		}
		// handle ellipse
		if (((XhEnglish2French)node).isOpenEllipse()) {
			node = ((XhEnglish2French)node).getNextClosedEllipse();
			if (node == null) {return null;}
			node = node.getNextSibling();
		}
		else if (((XhEnglish2French)node).isClosedEllipse()) {
			// next sibling within the current ellipse has not been found
			return null;
		}
		else {
			node = node.getNextSibling();
		}
	}
	return null;
}

/**
 * Get the previous sibling that's an instance of the specified Xholon class, or of some ancestor of that class.
 * Skip any ellipses found along the way.
 * @param xhcId An ID that specifies a Xholon class or superclass.
 * @return A matching IXholon node, or null.
 */
protected IXholon getPreviousSiblingAncestor(int xhcId)
{
	IXholon node = getPreviousSibling();
	while ((node != null)) {
		if (((XhEnglish2French)node).isMatchingNodeAncestor(xhcId)) {
			return node;
		}
		// handle ellipse
		if (((XhEnglish2French)node).isClosedEllipse()) {
			node = ((XhEnglish2French)node).getPreviousOpenEllipse();
			if (node == null) {return null;}
			node = node.getPreviousSibling();
		}
		else if (((XhEnglish2French)node).isOpenEllipse()) {
			// previous sibling within the current ellipse has not been found
			return null;
		}
		else {
			node = node.getPreviousSibling();
		}
	}
	return null;
}

/**
 * Is it a node of the specified Xholon class ID?
 * @param xhcId A Xholon class ID.
 * @return true or false
 */
protected boolean isMatchingNodeAncestor(int xhcId)
{
	if (getXhc().hasAncestor(xhcId)) {
		return true;
	}
	else {
		return false;
	}
}

/**
 * Is this French letter a vowel or H?
 * @param c A single character.
 * @return true or false
 */
protected boolean isVowelOrH(char c)
{
	switch (Character.toLowerCase(c)) {
	case 'a': // unaccented vowels
	case 'e':
	case 'i':
	case 'o':
	case 'u':
	case 'h': // h
	case 'à': // accented vowels
	case 'â':
	case 'é':
	case 'è':
	case 'ê':
	case 'î':
	case 'ô':
	case 'ù':
	case 'û':
		return true;
	default:
		return false;
	}
}

/**
 * Convert a French accented character (àâ éèê î ô ùû ç) to an unaccented lower case one.
 * (ex: à to a)
 * (ex: A to a)
 * @param in A single French character.
 * @return An unaccented, lower case version of the in character.
 */
protected char toUnaccentedLower(char in)
{
	char out = Character.toLowerCase(in);
	switch (out) {
	// accented vowels
	case 'à': out = 'a'; break;
	case 'â': out = 'a'; break;
	case 'é': out = 'e'; break;
	case 'è': out = 'e'; break;
	case 'ê': out = 'e'; break;
	case 'î': out = 'i'; break;
	case 'ô': out = 'o'; break;
	case 'ù': out = 'u'; break;
	case 'û': out = 'u'; break;
	case 'ç': out = 'c'; break;
	default: break;
	}
	return out;
}

/**
 * Convert a French String to an unaccented lower case form.
 * @param inStr A French word or phrase.
 * @return An unaccented, lower case version of the in String.
 */
protected String toUnaccentedLower(String inStr)
{
	String outStr = "";
	for (int i = 0; i < inStr.length(); i++) {
		outStr += toUnaccentedLower(inStr.charAt(i));
	}
	return outStr;
}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void setVal(int val) {valInt = val;}
public int getVal_int() {return valInt;}

public void setVal(String val) {valString = val;}
public String getVal_String() {return valString;}

/*
 * @see org.primordion.xholon.base.XholonWithPorts#initialize()
 */
public void initialize()
{
	super.initialize();
}

/*
 * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
 */
public void postConfigure()
{
	switch(xhc.getId()) {
	case SEngCE:
		if (hasChildNodes()) {
			IXholon sfr = getFirstChild(); // this should be a SFr node
			sfr.insertBefore("TokenPass", "TOKEN_PASS", "org.primordion.user.app.English2French.TokenPass");
			sfr.insertBefore("WordPass", "WORD_PASS", "org.primordion.user.app.English2French.WordPass");
			sfr.insertBefore("FlagPass", "FLAG_PASS", "org.primordion.user.app.English2French.FlagPass");
		}
		else {
			appendChild("TokenPass", "TOKEN_PASS", "org.primordion.user.app.English2French.TokenPass");
			appendChild("WordPass", "WORD_PASS", "org.primordion.user.app.English2French.WordPass");
			appendChild("FlagPass", "FLAG_PASS", "org.primordion.user.app.English2French.FlagPass");
		}
		break;
	default:
		break;
	}
	super.postConfigure();
}

/*
 * @see org.primordion.xholon.base.Xholon#act()
 */
public void act()
{
	switch(xhc.getId()) {
	case Fr2EngSystemCE:
		processMessageQ();
		break;
	default:
		break;
	}
	super.act();
}

/*
 * @see org.primordion.xholon.base.Xholon#processReceivedMessage(org.primordion.xholon.base.Message)
 */
public void processReceivedMessage(IMessage msg)
{
	switch(xhc.getId()) {
	default:
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getClass() == StateMachineEntity.class) {
				((StateMachineEntity)node).doStateMachine(msg); // StateMachine
				break;
			}
			else {
				node = node.getNextSibling();
			}
		}
		if (node == null) {
			System.out.println("XhEnglish2French: message with no receiver " + msg);
		}
		break;
	}
}

/*
 * @see org.primordion.xholon.base.XholonWithPorts#toString()
 */
public String toString() {
	String outStr = "";
	switch (this.getXhcId()) {
	case LexiconCE:
		//((Lexicon)this).showAllNounPlurals();
		return "Number of lexemes (words) in this lexicon: " + getNumChildren(false);
	case LexemeCE:
	{
		Lexeme lexeme = (Lexeme)this;
		String lemmaStr = lexeme.getLemmaString();
		String posStr = lexeme.getPosString();
		String plural = null;
		if (lexeme.isNoun()) {
			plural = lexeme.getLemmaStringPluralized();
		}
		String transformStr = lexeme.getTransformRefString();
		String wordRuleStr = lexeme.getWordRuleString();
		
		outStr += lemmaStr;
		if (posStr != null) {outStr += " " + posStr;}
		if (plural != null) {outStr += " (pl: " + plural + ")";}
		if (transformStr != null) {outStr += " " + transformStr;}
		if (wordRuleStr != null) {outStr += " " + wordRuleStr;}
		return outStr;
	}
	case LemmaCE:
		return getVal_String();
	//case PosCE:
	//	return getVal_String();
	case FrqCE:
		return "" + getVal_float();
	case TransformRefCE:
		return getVal_String();
	case TransformsCE:
		return "Number of transforms: " + getNumChildren(false);
	case TransformCE:
		{
			IXholon node = getFirstChild();
			while (node != null) {
				outStr += node;
				node = node.getNextSibling();
				if (node != null) {
					outStr += " ";
				}
			}
		}
		return outStr;
	case OpenBracketCE:
		{
			IXholon node = getFirstChild();
			while (node != null) {
				outStr += node;
				node = node.getNextSibling();
				if (node != null) {
					outStr += " ";
				}
			}
		}
		return "[" + outStr;
	case ClosedBracketCE:
		{
			IXholon node = getFirstChild();
			while (node != null) {
				outStr += node;
				node = node.getNextSibling();
				if (node != null) {
					outStr += " ";
				}
			}
		}
		return outStr + "]";
	case GenderFCE:
	case GenderMCE:
	case GenderFSCE:
	case GenderMSCE:
	case GenderFPCE:
	case GenderMPCE:
		return getXhcName();
	case FourLetterCodeCE:
		return getVal_String();
	case VerbClassCE:
		return "" + getVal_int();
	case FlagsCE:
		return getRoleName() + ": Number of flags: " + getNumChildren(false);
	case SEngsCE:
		return getRoleName() + ": Number of English sample sentences: " + getNumChildren(false);
	case SEngCE:
	case SFrCE:
		return getVal_String();
	case TokenPassCE:
	{
		IXholon node = getFirstChild();
		while (node != null) {
			outStr += node;
			node = node.getNextSibling();
			if (node != null) {
				outStr += " ";
			}
		}
		return outStr;
	}
	case TokenCE:
		return getVal_String();
	case WordPassCE:
	{
		IXholon node = getFirstChild();
		while (node != null) {
			outStr += node;
			node = node.getNextSibling();
			if (node != null) {
				outStr += " ";
			}
		}
		return outStr;
	}
	case FlagPassCE:
	{
		IXholon node = getFirstChild();
		while (node != null) {
			outStr += node;
			node = node.getNextSibling();
			if (node != null) {
				outStr += " ";
			}
		}
		return outStr;
	}
	case WordRuleCE:
		return getVal_String();
	case OpenEllipseCE:
		return ("(");
	case ClosedEllipseCE:
		return ")";
	default:
		break;
	}
	
	//if (getXhc().hasAncestor(PosCE)) {
	if (isMatchingNodeAncestor(PosCE)) {
		switch (getXhcId()) {
		case PosNCE: return POS_N;
		case PosVCE: return POS_V;
		case PosACE: return POS_A;
		case PosPCE: return POS_P;
		case PosPronCE: return POS_Pron;
		case PosAdvCE: return POS_Adv;
		case PosConjCE: return POS_Conj;
		case PosDCE: return POS_D;
		case PosQCE: return POS_Q;
		case PosSCE: return POS_S;
		case PosCE: return getVal_String();
		default:
			return getVal_String();
		}
	}
	
	if (getXhc().hasAncestor(MorphCE)) {
		IXholon lexeme = getParentNode();
		if ((lexeme != null) && (lexeme.getXhcId() == LexemeCE)) {
			if (((Lexeme)lexeme).isNoun()) {
				return ((Lexeme)lexeme).getLemmaStringPluralized();
			}
		}
	}
	
	if (getXhc().hasAncestor(CircleMarkerCE)) {
		return getXhcName();
	}
	
	outStr = getName();
	if ((port != null) && (port.length > 0)) {
		outStr += " [";
		for (int i = 0; i < port.length; i++) {
			if (port[i] != null) {
				outStr += " port:" + port[i].getName();
			}
		}
		outStr += "]";
	}
	if (getVal_String() != null) {
		outStr += " val:" + getVal_String();
	}
	return outStr;
}

}
