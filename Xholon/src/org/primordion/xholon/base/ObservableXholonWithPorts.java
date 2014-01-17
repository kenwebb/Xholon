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

package org.primordion.xholon.base;

import java.util.Vector;

/**
 * Observable Xholon with ports.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on March 12, 2007)
 * @see based on Java.util.Observable class; inspired by Gamma et al. (1995). Design Patterns. (Observer pattern)
 */
public class ObservableXholonWithPorts extends XholonWithPorts implements IObservable {
	private static final long serialVersionUID = -7089456064587726025L;

	/** Observers that register themselves with this Observable Xholon, to be notified of changes. */
	protected Vector observers;
	
	/** Indicates whether this object has changed. */
	protected boolean hasChanged = false;
	
	/*
	 * @see org.primordion.xholon.base.IObservable#addObserver(org.primordion.xholon.base.IXholon)
	 */
	public void addObserver(IXholon o) {
		if (observers == null) {
			observers = new Vector();
		}
		observers.addElement(o);
	}
	
	/*
	 * @see org.primordion.xholon.base.IObservable#countObservers()
	 */
	public int countObservers() {return observers.size();}
	
	/*
	 * @see org.primordion.xholon.base.IObservable#deleteObserver(org.primordion.xholon.base.IXholon)
	 */
	public void deleteObserver(IXholon o) {observers.removeElement(o);}
	
	/*
	 * @see org.primordion.xholon.base.IObservable#deleteObservers()
	 */
	public void deleteObservers() {observers.removeAllElements();}
	
	/*
	 * @see org.primordion.xholon.base.IObservable#notifyObservers()
	 */
	public void notifyObservers() {
		if (hasChanged()) {
			for (int i = 0; i < observers.size(); i++) {
				IXholon receiver = (IXholon)observers.elementAt(i);
				receiver.sendMessage(ISignal.SIGNAL_UPDATE, null, this);
			}
			clearChanged();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IObservable#notifyObservers(java.lang.Object)
	 */
	public void notifyObservers(Object arg) {
		if (hasChanged()) {
			for (int i = 0; i < observers.size(); i++) {
				IXholon receiver = (IXholon)observers.elementAt(i);
				receiver.sendMessage(ISignal.SIGNAL_UPDATE, arg, this);
			}
			clearChanged();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IObservable#hasChanged()
	 */
	public boolean hasChanged()
	{
		return hasChanged;
	}
	
	/**
	 * Marks this Observable object as having been changed; the hasChanged method will now return true.
	 */
	protected void setChanged()
	{
		hasChanged = true;
	}
	
	/**
	 * Indicates that this object has no longer changed, or that it has already notified all of its
	 * observers of its most recent change, so that the hasChanged method will now return false.
	 * This method is called automatically by the notifyObservers methods.
	 */
	protected void clearChanged()
	{
		hasChanged = false;
	}
	
	/*
	 * @see org.primordion.xholon.base.IObservable#getChangedData()
	 */
	public Object getChangedData()
	{
		return null;
	}
}
