package org.primordion.user.app.Bestiary;

/**
 * A cat usually likes to sleep a lot.
 * <p>
 * &lt;SleepingCatbehavior/>
 * </p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
 */
public class SleepingCatbehavior extends BeastBehavior {
	
	/**
	 * Whether or not the beast is sleeping.
	 */
	private boolean isSleeping = true;
	
	/*
	 * @see org.primordion.user.app.Bestiary.XhBestiary#act()
	 */
	public void act() {
		if (!isSleeping) {
			// sleeping blocks other behaviors such as moving
			super.act();
		}
	}
	
	public boolean isSleeping() {
		return isSleeping;
	}

	public void setSleeping(boolean isSleeping) {
		this.isSleeping = isSleeping;
	}

}
