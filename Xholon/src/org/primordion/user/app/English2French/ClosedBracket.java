package org.primordion.user.app.English2French;

import org.primordion.xholon.base.IXholon;

public class ClosedBracket extends XhEnglish2French {
	
	/**
	 * Get the lemma as a node (ex: gar�on).
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
	 * Get the lemma as a String (ex: avoir).
	 * @return The lemma as a String, or null.
	 */
	public String getLemmaString()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == LemmaCE) {
				return node.getVal_String();
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the verb class as a node.
	 * @return The verb class, or null.
	 */
	public IXholon getVerbClass()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == VerbClassCE) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the verb class as an int.
	 * @return The verb class, or -1.
	 */
	public int getVerbClassInt()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == VerbClassCE) {
				return node.getVal_int();
			}
			node = node.getNextSibling();
		}
		return -1;
	}

}
