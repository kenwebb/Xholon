/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2006, 2007, 2008 Ken Webb
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

package org.primordion.ctrnn.AdapSysLab;

//import org.primordion.ctrnn.base.ICtrnn;
//import org.primordion.ctrnn.base.XhAbstractCtrnn;
import org.primordion.xholon.base.Msg;
import org.primordion.xholon.util.MiscRandom;

/**
 * Continuous-Time Recurrent Neural Network (CTRNN).
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Feb 9, 2006)
 */
public class XhAdapSysLab extends XhAbstractCtrnn implements ICtrnn, CeAdapSysLab {

	// How many lights are on
	private static final int LIGHTS_BOTH_OFF = 0;
	private static final int LIGHTS_ONE_ON   = 1;
	private static final int LIGHTS_BOTH_ON  = 2;
	private static final int LIGHTS_RANDOM   = 3;
	protected static int numLightsOn = LIGHTS_ONE_ON;
	
	/*
	 * @see org.primordion.xholon.base.IXholon#preAct()
	 */
	public void preAct()
	{
		switch(xhc.getId()) {
		
		// Neuron
		case NeuronCE:
			input = 0.0;
			output = 1.0 / (1.0 + Math.exp(-activation - bias));
			if (Msg.appM) {
				println("  " + getRoleName() + " output: " + output);
			}
			break;
		// SensorNeuron
		case SensorNeuronCE:
			//input = 0.0;
			output = (activation - 0.15) * 1.3;
			if (output < 0.0) { // make sure output doesn't go below zero
				output = 0.0;
			}
			if (Msg.appM) {
				println(getRoleName() + " output: " + output);
			}
			break;
		default:
			break;
		}
		
		super.preAct();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		switch(xhc.getId()) {
		case RobotCE:
			// Only process the messages that are currently in the Q.
			// Don't process messages added to Q during this timestep.
			processMessageQ();
			break;
		// Photoreceptor
		case PhotoreceptorCE:
			switch (numLightsOn) {
			case LIGHTS_BOTH_OFF:
				port[P_OUT1].sendMessage(SIG_READ_AD, new Double(output), this);
				break;
			case LIGHTS_ONE_ON:
				if (getRoleName().equals("SensorR")) {
					output = 0.0; // 0.0
					port[P_OUT1].sendMessage(SIG_READ_AD, new Double(output), this);
				}
				else if (getRoleName().equals("SensorL")) {
					output = 500.0; // 500.0
					port[P_OUT1].sendMessage(SIG_READ_AD, new Double(output), this);
				}
				else {
					if (Msg.errorM) {
						System.err.println("---> error");
					}
				}
				break;
			case LIGHTS_BOTH_ON:
				// don't send anything
				break;
			case LIGHTS_RANDOM:
				output += MiscRandom.getRandomDouble(-20.0, 20.0); // less jagged random test value
				port[P_OUT1].sendMessage(SIG_READ_AD, new Double(output), this);
				break;
			default:
				break;
			}
			break;
		// Neuron
		case NeuronCE:
			broadcastWeightedOutput();
			break;
		case SensorNeuronCE:
			broadcastWeightedOutput();
			break;
		case MotorCE:
			break;
		default:
			break;
		}
		
		super.act();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postAct()
	 */
	public void postAct()
	{
		switch(xhc.getId()) {
		case NeuronCE:
			activation += (input - activation) * DELTA_T / timeConstant;
			if (Msg.appM) {
				println("  " + getRoleName() + " input: " + input);
				println("  " + getRoleName() + " activation: " + activation);
			}
			break;
		case SensorNeuronCE:
			// I'm using activation slightly differently here
			activation = 1.0 - input / 255.0; // rawLight
			if (Msg.appM) {
				println(getRoleName() + " activation: " + activation);
			}
			break;
		default:
			break;
		}
		
		super.postAct();
	}
	
	/**
	 * Set the number of lights that are turned on, or random.
	 * @param nLightsOn The number of lights.
	 */
	public static void setNumLightsOn(final int nLightsOn)
	{
		numLightsOn = nLightsOn;
	}
	
	/**
	 * Get the number of lights turned on.
	 * @return The number of lights.
	 */
	public static int getNumLightsOn()
	{
		return numLightsOn;
	}
}
