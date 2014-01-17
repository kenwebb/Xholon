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

import java.util.Date;
import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.Msg;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Continuous-Time Recurrent Neural Network (CTRNN).
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Feb 9, 2006)
 */
public abstract class XhAbstractCtrnn extends XholonWithPorts implements ICtrnn {
  
  // GWT - constants were moved from ICtrnn
  	// Ports
	public static final int P_OUT1 = 0;
	public static final int P_OUT2 = 1;
	public static final int P_OUT3 = 2;
	public static final int SIZE_MYAPP_PORTS = 3;
	
	// Signals sent in messages
	public static final int SIG_SET_MOTOR_POWER   = 100; // set motor power
	public static final int SIG_READ_AD = 200; // read AD from photoreceptor

	public static final String NEURON_CLASS_NAME = "Neuron";
	public static final double DELTA_T = 0.1;
  
	public int state = 0;
	public String roleName = null;
	// Neuron variables
	public double activation = 0.0;
	public double bias = 0.0;
	public double timeConstant = 0.0;
	public double input = 0.0;
	public double output = 0.0;
	// Connection variables
	public double weight = 0.0;
	
	// time
	protected static Date lastDate = null; // to allow keeping track of "tenths of seconds"
	
	public double getActivation() {
		return activation;
	}

	public void setActivation(final double activation) {
		this.activation = activation;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(final double bias) {
		this.bias = bias;
	}

	public double getTimeConstant() {
		return timeConstant;
	}

	public void setTimeConstant(final double timeConstant) {
		this.timeConstant = timeConstant;
	}

	public double getInput() {
		return input;
	}

	public void setInput(final double input) {
		this.input = input;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(final double output) {
		this.output = output;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		roleName = null;
		state = 0;
		activation = 0.0;
		bias = 0.0;
		timeConstant = 0.0;
		input = 0.0;
		output = 0.0;
		weight = 0.0;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getVal()
	 */
	public double getVal()
	{return activation;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 */
	public String getRoleName()
	{
		return roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postConfigure()
	 */
	public void postConfigure()
	{
		if (xhc.getName().equals("Ctrnn")) {
			lastDate = new Date(0L); // set current time to earliest possible time
		}
		else if (xhc.getName().equals("Connection")) {
			
			// setup port from source neuron to this connection
			final String neuronFromStr = getNeuronRoleNameSource();
			XhAbstractCtrnn neuronFrom = (XhAbstractCtrnn)getXPath().evaluate("ancestor::Ctrnn/Neuron[@roleName='" + neuronFromStr + "']", this);
			if (neuronFrom == null) {
				neuronFrom = (XhAbstractCtrnn)getXPath().evaluate("ancestor::Ctrnn/SensorNeuron[@roleName='" + neuronFromStr + "']", this);
			}
			// use the first available port
			for (int i = 0; i < SIZE_MYAPP_PORTS; i++) {
				if (neuronFrom.getPort(i) == null) {
					neuronFrom.port[i] = this;
					break;
				}
			}
			
			// setup port from this connection to the destination neuron
			final String neuronToStr = getNeuronRoleNameDest();
			port[P_OUT1] = getXPath().evaluate("ancestor::Ctrnn/Neuron[@roleName='" + neuronToStr + "']", this);
		}
		super.postConfigure();
	}
	
	/**
	 * Get role name of the neuron that will be the source of this connection.
	 * @return A neuron role name, or null.
	 */
	protected String getNeuronRoleNameSource()
	{
		int ixStart = roleName.indexOf('_');
		if (ixStart == -1) {return null;}
		ixStart++; // point to first char after '_'
		final int ixEnd = roleName.lastIndexOf('_');
		if (ixEnd == -1) {return null;}
		return NEURON_CLASS_NAME + roleName.substring(ixStart, ixEnd);
	}

	/**
	 * Get role name of the neuron that will be the destination of this connection.
	 * @return A neuron role name, or null.
	 */
	protected String getNeuronRoleNameDest()
	{
		int ixStart = roleName.lastIndexOf('_');
		if (ixStart == -1) {return null;}
		ixStart++; // point to first char after '_'
		return NEURON_CLASS_NAME + roleName.substring(ixStart);
	}
	
	/**
	 * Broadcast weighted output to all destination neurons.
	 */
	protected void broadcastWeightedOutput() {
		int i;
		double w;
		XhAbstractCtrnn destNeuron = null;
		for (i = 0; i < SIZE_MYAPP_PORTS; i++) {
			if (port[i] != null) {
				//if (port[i].getXhcId() == ConnectionCE) {
				if (port[i].getXhcName().equals("Connection")) {
					w = ((XhAbstractCtrnn)port[i]).weight;
					destNeuron = (XhAbstractCtrnn)((XhAbstractCtrnn)port[i]).port[P_OUT1];
					destNeuron.input += output * w;
				}
				else {
					// this connects directly to a Motor
					port[i].sendMessage(SIG_SET_MOTOR_POWER, new Double(output), this);
				}
			}
			else {
				break;
			}
		}
	}
	
	public int setAttributeVal(String attrName, String attrVal) {
	  IApplication app = this.getApp();
	  Class clazz = XhAbstractCtrnn.class;
	  if (app.isAppSpecificAttribute(this, clazz, attrName)) {
	    app.setAppSpecificAttribute(this, clazz, attrName, attrVal);
	  }
	  return 0;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#processReceivedMessage(org.primordion.xholon.base.Message)
	 */
	public void processReceivedMessage(IMessage msg)
	{
		//System.out.println(msg);
		final int event = msg.getSignal();
		//Double msgData;
		//double rawLight = 0.0;
		
		if (xhc.getName().equals("SensorNeuron")) {
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SIG_READ_AD: // read value from photoreceptor
					input = ((Double)msg.getData()).doubleValue();
					if (Msg.appM) {
						println(getRoleName() + " input: " + input);
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}
		else if (xhc.getName().equals("Motor")) {
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SIG_SET_MOTOR_POWER: // 
					input = ((Double)msg.getData()).doubleValue();
					if (Msg.appM) {
						println("    " + getRoleName() + " input: " + input);
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}
		else {
			if (Msg.errorM) {
				System.out.println("processReceivedMessage: unhandled msg " + msg + " received by " + getName());
			}
		}
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		if ((port != null) && (port.length > 0)) {
			outStr += " [";
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					outStr += " port:" + port[i].getName();
				}
			}
			outStr += "]";
		}
		if (xhc.getName().equals("Neuron")) {
			outStr += " activation:" + activation;
			outStr += " bias:" + bias;
			outStr += " timeConstant:" + timeConstant;
			outStr += " input:" + input;
			outStr += " output:" + output;
		}
		else if (xhc.getName().equals("SensorNeuron")) {
			outStr += " activation:" + activation;
			outStr += " input:" + input;
			outStr += " output:" + output;
		}
		else if (xhc.getName().equals("Connection")) {
			outStr += " weight:" + weight;
		}
		else if (xhc.getName().equals("Photoreceptor")) {
			outStr += " output:" + output;
		}
		else if (xhc.getName().equals("Motor")) {
			outStr += " input:" + input;
		}
		return outStr;
	}
}
