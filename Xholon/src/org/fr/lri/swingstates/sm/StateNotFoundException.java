/*  
 *   Authors: Caroline Appert (caroline.appert@lri.fr)
 *   Copyright (c) Universite Paris-Sud XI, 2007. All Rights Reserved
 *   Licensed under the GNU LGPL. For full terms see the file COPYING.
*/
package org.fr.lri.swingstates.sm;

/**
 * A <code>StateNotFoundException</code> exception is raised 
 * when attempting to retrieve a state that does not exist.
 * 
 * @author Caroline Appert
 *
 */
public class StateNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	/**
	 * The name of the state not found.
	 */
	private String stateName;
	
	/**
	 * Builds a <code>StateNotFoundException</code> exception.
	 */
	public StateNotFoundException() {
		super();
	}
	
	/**
	 * Builds a <code>StateNotFoundException</code> exception.
	 * @param state The name of the state that is not found.
	 */
	public StateNotFoundException(String state) {
		super("Could not find state "+state+" in state machine.");
		stateName = state;
	}
	
	/**
	 * Returns the name of the state that is not found.
	 * @return the name of the state that is not found.
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * Sets the name of the state that is not found.
	 * @param stateName The name of the state.
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
