/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.xholon.base;

import org.primordion.xholon.exception.XholonConfigurationException;

/**
 * An activity is a type of behavior. This class can be thought of as an activity machine,
 * analogous to a state machine which is another type of behavior.
 * In UML 2.0, Activity and StateMachine are two subclasses of Behavior.
 * Because it directly executes Java code, this implementation of Activity is perhaps
 * closer to being an implementation of OpaqueBehavior, another UML 2.0 subclass of Behavior.
 * @see IActivity
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jul 25, 2005)
 */
public class Activity extends Xholon implements IActivity {
	private static final long serialVersionUID = 3858664681234166956L;

	/**
	 * Constructor.
	 */
	public Activity() {}
	
	/**
	 * Clone this instance of Activity, with cloned children as well.
	 * @param parent The Xholon instance that will be the parent of the new clone.
	 * @return The new clone.
	 */
	public Activity clone(IXholon parent)
	{
		Activity aClone = null;
		try {
			aClone = (Activity)getFactory().getNode(Activity.class); //.getActivityNode();
		} catch (XholonConfigurationException e) {
			logger.error(e.getMessage(), e.getCause());
			return null;
		}
		aClone.xhc = xhc;
		aClone.id = getNextId();
		if (firstChild != null) {
			aClone.firstChild = ((Activity)firstChild).clone(aClone);
		}
		aClone.parentNode = parent;
		if (nextSibling != null) {
			aClone.nextSibling = ((Activity)nextSibling).clone(parent);
		}
		return aClone;
	}
}
