package org.primordion.user.app.Bestiary;

/**
 * IntegralBeast is a Beast that knows how to do numerical integration.
 * The Bestiary environment permits subtrees that require integration,
 * but doesn't do it itself, because most of the Berstiary nodes don't need it.
 * The root node of a subtree that does require integration should be an instance of this class.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on Feb 6, 2012)
 */
public class IntegralBeast extends Beast {
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
		for (int i = 0; i < XhBestiary.timeStepMultiplier; i++) {
			if (this.hasChildNodes()) {
				this.getFirstChild().act();
			}
		}
		if (this.hasNextSibling()) {
			this.getNextSibling().act();
		}
	}
	
}
