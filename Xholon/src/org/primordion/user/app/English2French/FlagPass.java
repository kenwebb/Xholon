package org.primordion.user.app.English2French;

import org.primordion.user.app.English2French.rule.Rule_Flag;
import org.primordion.xholon.base.HandleNodeSelectionResult;
import org.primordion.xholon.base.IXholon;

public class FlagPass extends XhEnglish2French {
	
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
			doFlagPass();
			return new HandleNodeSelectionResult(toString(), HandleNodeSelectionResult.HNSR_REFRESH, null);
		}
	}
	
	/**
	 * Do flag pass.
	 * Transform each word that appears in the French word pass sub-tree,
	 * into a sequence of nodes that are children of FlagPass.
	 */
	public void doFlagPass()
	{
		Lexicon lexicon = (Lexicon)getXPath().evaluate("descendant::Lexicon", getRootNode());
		if (lexicon == null) {return;}
		
		// do all the flag rules
		IXholon frenchNode = getXPath().evaluate("descendant::WordPass/CM_xx", parentNode);
		// save the previous sibling, in case other nodes are removed
		IXholon previousFrenchNode = null;
		while (frenchNode != null) {
			// save the next sibling, in case the current frenchNode is removed
			IXholon nextFrenchNode = frenchNode.getNextSibling();
			if (isFlagNode(frenchNode)) {
				((Rule_Flag)frenchNode).doRule();
			}
			// get the next node, considering which nodes may have been removed
			if (hasBeenRemoved(frenchNode)) {
				if (hasBeenRemoved(nextFrenchNode)) {
					// both the current frenchNode and the saved nextFrenchNode have been removed
					if (previousFrenchNode == null) {return;} // have run out of options
					frenchNode = previousFrenchNode.getNextSibling();
				}
				else {
					frenchNode = nextFrenchNode;
				}
			}
			else {
				previousFrenchNode = frenchNode;
				frenchNode = frenchNode.getNextSibling();
			}
		}
		
		// create the resulting flag-pass lemma nodes
		frenchNode = getXPath().evaluate("descendant::WordPass/CM_xx", parentNode);
		boolean isFirstWord = true;
		while (frenchNode != null) {
			if (isLemmaNode(frenchNode)) {
				String word = frenchNode.getVal_String();
				if (isFirstWord) {
					// capitalize the first word in the sentence
					word = "" + Character.toUpperCase(word.charAt(0)) + word.substring(1);
					isFirstWord = false;
				}
				appendChild("Lemma", null, "org.primordion.user.app.English2French.XhEnglish2French")
					.setVal(word);
			}
			frenchNode = frenchNode.getNextSibling();
		}
	}
	
	/**
	 * Is it a flag node?
	 * @param node
	 * @return true or false
	 */
	protected boolean isFlagNode(IXholon node)
	{
		return ((XhEnglish2French)node).isMatchingNodeAncestor(FlagCE);
	}
	
	/**
	 * Is it a lemma node?
	 * @param node
	 * @return true or false
	 */
	protected boolean isLemmaNode(IXholon node)
	{
		return ((XhEnglish2French)node).isMatchingNodeAncestor(LemmaCE);
	}
	
	/**
	 * Has this node been removed from the Xholon tree?
	 * @param node A Xholon node.
	 * @return true or false
	 */
	protected boolean hasBeenRemoved(IXholon node)
	{
		// a node with no parent has been removed from the tree (unless it's the root node)
		if (node.hasParentNode()) {
			return false;
		}
		else {
			return true;
		}
	}
	
}
