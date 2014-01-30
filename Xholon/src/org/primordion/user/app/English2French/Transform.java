package org.primordion.user.app.English2French;

import org.primordion.xholon.base.IXholon;

public class Transform extends XhEnglish2French {
	
	/**
	 * Get the complete lemma (ex: gar�on)
	 * This may be a concatenation of two lemmas (ex: avoir,�crit).
	 * @return The complete lemma, or null.
	 */
	public String getCompleteLemma()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == KeyCE) {
				return node.getVal_String();
			}
			node = node.getNextSibling();
		}
		// no key found, so search for a lemma
		node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == LemmaCE) {
				IXholon circleMarker = getCircleMarker();
				if ((circleMarker != null)
						&& ((circleMarker.getXhcId() == CM_vcCE)
								|| (circleMarker.getXhcId() == CM_vvCE))) {
					// the lemma is part of a verb
					ClosedBracket closedBracket = (ClosedBracket)getClosedBracket();
					if (closedBracket != null) {
						return closedBracket.getLemmaString() + "," + node.getVal_String();
					}
				}
				return node.getVal_String();
			}
			node = node.getNextSibling();
		}
		// no lemma found, so search for a lemma within a bracket
		node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == ClosedBracketCE) {
				return ((ClosedBracket)node).getLemmaString();
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the simple lemma (ex: gar�on) (ex: �crit).
	 * @return The simple lemma, or null.
	 */
	public String getSimpleLemma()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == KeyCE) {
				return node.getVal_String();
			}
			node = node.getNextSibling();
		}
		// no key found, so search for a lemma
		node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == LemmaCE) {
				return node.getVal_String();
			}
			node = node.getNextSibling();
		}
		// no lemma found, so search for a lemma within a bracket
		node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == ClosedBracketCE) {
				return ((ClosedBracket)node).getLemmaString();
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the lemma child node itself, with no additional checking.
	 * @return The lemma node, or null.
	 */
	public IXholon getLemma()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhc().hasAncestor(LemmaCE)) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the flag.
	 * @return The flag, or null.
	 */
	public IXholon getFlag()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhc().hasAncestor(FlagCE)) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the gender (masculine or feminine).
	 * @return The gender, or null.
	 */
	public IXholon getGender()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhc().hasAncestor(GenderCE)) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the circle marker.
	 * @return The circle marker, or null.
	 */
	public IXholon getCircleMarker()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhc().hasAncestor(CircleMarkerCE)) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the bracket (closed or open).
	 * @return The bracket, or null.
	 */
	public IXholon getBracket()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhc().hasAncestor(BracketCE)) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the closed bracket.
	 * @return The closed bracket, or null.
	 */
	public IXholon getClosedBracket()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == ClosedBracketCE) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}

	/**
	 * Get the open bracket.
	 * @return The open bracket, or null.
	 */
	public IXholon getOpenBracket()
	{
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getXhcId() == OpenBracketCE) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#compareTo(java.lang.Object)
	 */
	public int compareTo(Object otherTransform)
	{
		String lemmaStr = this.getSimpleLemma();
		if (lemmaStr == null) {
			getLogger().error("Null lemma string in " + getName());
			return 0;
		}
		if (otherTransform == null) {
			getLogger().error("Null transform being compared with " + getName());
			return 0;
		}
		String otherLemmaStr = ((Transform)otherTransform).getSimpleLemma();
		if (otherLemmaStr == null) {
			getLogger().error("Null other lemma string in " + ((Transform)otherTransform).getName());
			return 0;
		}
		return toUnaccentedLower(lemmaStr).compareTo(toUnaccentedLower(otherLemmaStr));
	}
	
}
