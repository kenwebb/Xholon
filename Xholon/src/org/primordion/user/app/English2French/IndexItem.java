package org.primordion.user.app.English2French;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;

public class IndexItem extends Xholon {
	
	/** The first letter. */
	private char firstLetter = ' ';
	
	/**
	 * The first item (lexeme/transform) in the lexicon or transform list
	 * whose lemma string begins with firstLetter.
	 */
	private IXholon item = null;
	
	public char getFirstLetter() {
		return firstLetter;
	}
	
	public IXholon getItem() {
		return item;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_char()
	 */
	public char getVal_char() {
		return firstLetter;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(char)
	 */
	public void setVal(char firstLetter) {
		this.firstLetter = firstLetter;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_Object()
	 */
	public Object getVal_Object() {
		return item;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(java.lang.Object)
	 */
	public void setVal(Object item) {
		this.item = (IXholon)item;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		return getFirstLetter() + getItem().getRoleName() + getItem();
	}
}
