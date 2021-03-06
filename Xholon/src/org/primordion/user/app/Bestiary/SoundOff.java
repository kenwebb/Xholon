package org.primordion.user.app.Bestiary;

import org.primordion.xholon.base.Xholon;

/**
 * Turn the annoying cat sounds off.
 * <p>
 * &lt;SoundOff/>
 * </p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
 */
public class SoundOff extends Xholon {
	
	public void postConfigure() {
		org.primordion.user.app.Bestiary.FreedomOfMovementCatbehavior.setSoundsEnabled(false);
		removeChild();
	}
	
}
