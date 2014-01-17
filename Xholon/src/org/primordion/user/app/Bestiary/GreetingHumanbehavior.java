package org.primordion.user.app.Bestiary;

import org.primordion.xholon.base.IXholon;

/**
 * A human may greet another beast if it's in the same location as that beast.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
 */
public class GreetingHumanbehavior extends BeastBehavior {
	
	/*
	 * @see org.primordion.user.app.Bestiary.XhBestiary#act()
	 */
	public void act() {
		greet();
		super.act();
	}
	
	/**
	 * Greet a beast if it's in the same location as the human.
	 */
	protected void greet()
	{
		IXholon location = getBeast().getParentNode();
		IXholon otherBeast = location.getFirstChild();
		if ("Cat".equals(otherBeast.getXhcName())) {
			println("Nice kitty");
		}
		else if (("Human".equals(otherBeast.getXhcName())) && (otherBeast != getBeast())) {
			println("Hi");
		}
	}
	
}
