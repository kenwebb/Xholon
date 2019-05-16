/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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

package org.primordion.xholon.mech.petrinet;

import com.google.gwt.core.client.JsArrayNumber;

import java.util.List;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * Transition
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 20, 2012)
 */
public class Transition extends XholonWithPorts implements IKinetics {
	private static final long serialVersionUID = 1001658950909890589L;

	/**
	 * The type of kinetics used by this instance of Transition.
	 */
	private int kineticsType = KINETICS_UNSPECIFIED;
	
	private String roleName = null;
	
	private IXholon inputArcs = null;
	private IXholon outputArcs = null;
	
	/**
	 * Probability that an enabled transition will fire.
	 * A real number between 0.0 and 1.0.
	 * 0.0 means the transition will never fire, even when enabled.
	 * 1.0 means the transition will always fire,when enabled.
	 */
	private double p = P_UNSPECIFIED; //1.0;
	
	/**
	 * Reaction rate; k
	 * <p>k is the rate constant for a reaction (Feinberg)</p>
	 * <p>see Baez, Network Theory (Part 3)</p>
	 * <p>0 <= reactionRate < infinity</p>
	 * <p>how is this related to probability, count ???</p>
	 * <p>each Transition should have its own rate</p>
	 * <p>for Glycolysis model:
	 *   0.005 causes chaos
	 *   0.01 causes complete failure (NaN)</p>
	 */
	private double k = K_UNSPECIFIED; //0.004; //0.001;
	
	/**
	 * Michaelis-Menten vmax
	 */
	private double vmax = VMAX_UNSPECIFIED;
	
	/**
	 * Michaelis-Menten km
	 */
	private double km = KM_UNSPECIFIED;
	
	/**
	 * Number of instances of this type of Transition.
	 * The count is related to the probability or reactionRate.
	 * For use by Grid.
	 */
	private double count = 0;
	
	/**
	 * delta t (an increment in time)
	 * This should only be set by the Petri net (QueueTransitions),
	 * so it's consistent with all transitions in the Petri net.
	 */
	private double dt = 1.0;
	
	/**
	 * An optional symbol that typically refers to the rate k.
	 */
	private String symbol = null;
	
	private static boolean shouldWriteSequenceDiagram = false;
	private static boolean sequenceDiagramTitleWritten = false;

	/**
	 * An XPath expression that identifies a Xholon node that will derive output values from input values.
	 * For use when kineticsType == KINETICS_FUNCTION.
	 */
	private String functionXPath = null;
	
	/**
	 * A Xholon node that will derive output values from input values.
	 * For use when kineticsType == KINETICS_FUNCTION.
	 */
	private IXholon function = null;
	
	public void setRoleName(String roleName) {this.roleName = roleName;}
	public String getRoleName() {return roleName;}
	
	public void setVal(double val) {
		count = val;
	}
	
	public void setVal(int val) {
		count = val;
	}
	
	public void setVal(String val) {
		count = new Double(val);
	}
	
	public double getVal() {
		return count;
	}
	
	public int getVal_int() {
		return (int)count;
	}
	
	public void incVal(double inc) {
		count += inc;
	}
	
	public void decVal(double dec) {
		count -= dec;
	}
	
	public void postConfigure() {
		// Transition in a Petri net has InputArcs/OutputArcs children; Transition in a Grid doesn't
		inputArcs = this.findFirstChildWithXhClass("InputArcs");
		outputArcs = this.findFirstChildWithXhClass("OutputArcs");
		super.postConfigure();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getAllPorts()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getAllPorts() {
		List ports = super.getAllPorts();
		IXholon arc = null;
		if (inputArcs != null) {
			arc = inputArcs.getFirstChild();
			while (arc != null) {
				PortInformation portInfo = new PortInformation("in", arc);
				ports.add(portInfo);
				arc = arc.getNextSibling();
			}
		}
		if (outputArcs != null) {
			arc = outputArcs.getFirstChild();
			while (arc != null) {
				PortInformation portInfo = new PortInformation("out", arc);
				ports.add(portInfo);
				arc = arc.getNextSibling();
			}
		}
		return ports;
	}
	
	/**
	 * This method is intended to be called by QueueTransitions.
	 * @see org.primordion.xholon.base.Xholon#actNr()
	 */
	public void actNr() {
		switch (kineticsType) {
		case KINETICS_BASIC_PTNET:
			kBasicPtnet();
			break;
		case KINETICS_MASS_ACTION:
			kMassAction();
			break;
		case KINETICS_MICHAELIS_MENTEN:
			kMichaelisMenten();
			break;
		case KINETICS_DIFFUSION:
			kDiffusion();
			break;
		case KINETICS_MAXIMAL_PARALLELISM:
			break;
		case KINETICS_GRID:
			break;
		case KINETICS_CUSTOM:
			break;
		case KINETICS_LOGIC_AND:
			kLogicAnd();
			break;
		case KINETICS_LOGIC_OR:
			kLogicOr();
			break;
		case KINETICS_LOGIC_NOT:
			kLogicNot();
			break;
		case KINETICS_FUNCTION:
			kFunction();
			break;		
		case KINETICS_NULL:
		default:
			break;
		}
	}
	
	/**
	 * Basic place/transition net kinetics as described in wikipedia "Petri net" article.
	 */
	protected void kBasicPtnet() {
		// check that all input arcs are enabled
		// if there are no input arcs, then the transition is automatically enabled
		if (p > Math.random()) {
			boolean enabled = true;
			IXholon arc = null;
			if (inputArcs != null) {
				arc = inputArcs.getFirstChild();
				while (arc != null) {
					if (!arc.getVal_boolean()) {
						enabled = false;
						break;
					}
					arc = arc.getNextSibling();
				}
			}
			if (enabled) {
				// fire the transition; invoke all arcs
				if (inputArcs != null) {
					arc = inputArcs.getFirstChild();
					while (arc != null) {
						arc.performVoidActivity(null);
						arc = arc.getNextSibling();
					}
				}
				if (outputArcs != null) {
					arc = outputArcs.getFirstChild();
					while (arc != null) {
						arc.performVoidActivity(null);
						arc = arc.getNextSibling();
					}
				}
				writeSequenceDiagram(this);
			}
		}
	}
	
	/**
	 * Mass action kinetics.
	 */
	protected void kMassAction() {
		IXholon arc = null;
		double product = k * dt;
		if (inputArcs != null) {
			arc = inputArcs.getFirstChild();
			while (arc != null) {
				if (((Arc)arc).isActive()) {
					product *= Math.pow(((Arc)arc).getPlace().getVal(), arc.getVal());
				}
				arc = arc.getNextSibling();
			}
			arc = inputArcs.getFirstChild();
			while (arc != null) {
				if (((Arc)arc).isActive()) {
					((Arc)arc).getPlace().decVal(arc.getVal() * product);
				}
				arc = arc.getNextSibling();
			}
		}
		if (outputArcs != null) {
			arc = outputArcs.getFirstChild();
			while (arc != null) {
				if (((Arc)arc).isActive()) {
					((Arc)arc).getPlace().incVal(arc.getVal() * product);
				}
				arc = arc.getNextSibling();
			}
		}
	}
	
	/**
	 * Michaelis-Menten enzyme/catalyst kienetics.
	 * Assume just one substrate for now; only one input arc.
	 * Allow any number of products.
	 * Taken from code in org.primordion.cellontro.app.XhCell .
	 *   Irreversible, 1 Substrate, 1 Product, 0 Activator, 0 Inhibitor, 0 Coenzyme
	 *   Irreversible, 1 Substrate, 2 Product, 0 Activator, 0 Inhibitor, 0 Coenzyme
	 *   Irreversible, 1 Substrate, 1 Product, 0 Activator, 0 Inhibitor, N Coenzyme
	 * TODO add other types from cellontro XhCell
	 */
	protected void kMichaelisMenten() {
		IXholon arc = null;
		double nTimes = dt;
		if (inputArcs != null) {
			arc = inputArcs.getFirstChild();
			if (arc != null) {
				double s = ((Arc)arc).getPlace().getVal();
				nTimes *= (getVmax() * s) / (getKm() + s);
				while (arc != null) {
					if (((Arc)arc).isActive()) {
						((Arc)arc).getPlace().decVal(arc.getVal() * nTimes);
					}
					arc = arc.getNextSibling();
				}
			}
		}
		if (outputArcs != null) {
			arc = outputArcs.getFirstChild();
			while (arc != null) {
				if (((Arc)arc).isActive()) {
					((Arc)arc).getPlace().incVal(arc.getVal() * nTimes);
				}
				arc = arc.getNextSibling();
			}
		}
	}
	
	/**
	 * Diffusion requires an equal number of input places and output places.
	 * This uses the Mass Action k value.
	 * k * (inputPlace - outputPlace)
	 */
	protected void kDiffusion() {
		if (inputArcs == null) {return;}
		if (outputArcs == null) {return;}
		IXholon inputArc = inputArcs.getFirstChild();
		IXholon outputArc = outputArcs.getFirstChild();
		while ((inputArc != null) && (outputArc != null)) {
			IXholon inputPlace = ((Arc)inputArc).getPlace();
			if (inputPlace == null) {return;}
			IXholon outputPlace = ((Arc)outputArc).getPlace();
			if (outputPlace == null) {return;}
			double x = getK() * (inputPlace.getVal() - outputPlace.getVal());
			inputPlace.decVal(inputArc.getVal() * x);
			outputPlace.incVal(outputArc.getVal() * x);
			inputArc = inputArc.getNextSibling();
			outputArc = outputArc.getNextSibling();
		}
	}
	
	/**
	 * Could this transition use diffusion kinetics?
	 * // TODO should the in/out arcs have the same weight ?
	 * @return
	 * <p>true if</p>
	 * <p>- there are the same number of inputPlace and outputPlace, and</p>
	 * <p>- all inputPlace and outputPlace are the same type</p>
	 * <p>else false</p>
	 */
	public boolean couldUseDiffusion() {
		if (inputArcs == null) {return false;}
		if (outputArcs == null) {return false;}
		IXholon inputArc = inputArcs.getFirstChild();
		IXholon outputArc = outputArcs.getFirstChild();
		while ((inputArc != null) && (outputArc != null)) {
			IXholon inputPlace = ((Arc)inputArc).getPlace();
			if (inputPlace == null) {return false;}
			IXholon outputPlace = ((Arc)outputArc).getPlace();
			if (outputPlace == null) {return false;}
			if (inputPlace.getXhc() != outputPlace.getXhc()) {
				// they have different class names, so can't use diffusion
				return false;
			}
			else if ((inputPlace.getXhcId() >= IMechanism.MECHANISM_ID_START)
					&& outputPlace.getXhcId() >= IMechanism.MECHANISM_ID_START) {
				// They are generic types, so check their roleNames. examples:
				// aRoleName:PlacePN myRoleName:Species
				if ((inputPlace.getRoleName() != null)
						&& (outputPlace.getRoleName() != null)) {
					if (!inputPlace.getRoleName().equals(outputPlace.getRoleName())) {
						// they have different role names, so can't use diffusion
						return false;
					}
				}
			}
			inputArc = inputArc.getNextSibling();
			outputArc = outputArc.getNextSibling();
		}
		// check that both inputArc and outputArc are null
		if ((inputArc != null) || (outputArc != null)) {
			// there are a different number of inputPlaces and outputPlaces, so can't use diffusion
			return false;
		}
		return true;
	}
	
	/**
	 * AND
	 */
	protected void kLogicAnd() {
		double outputVal = LOGIC_TRUE; // LOGIC_FALSE=0 or LOGIC_TRUE=1
		if ((inputArcs != null) && (outputArcs != null)) {
			Arc inputArc = (Arc)inputArcs.getFirstChild();
			while (inputArc != null) {
				if (inputArc.getPlace().getVal() == LOGIC_FALSE) {
					outputVal = LOGIC_FALSE;
					break;
				}
				inputArc = (Arc)inputArc.getNextSibling();
			}
			Arc outputArc = (Arc)outputArcs.getFirstChild();
			while (outputArc != null) {
				outputArc.getPlace().setVal(outputVal);
				outputArc = (Arc)outputArc.getNextSibling();
			}
		}
	}
	
	/**
	 * OR
	 */
	protected void kLogicOr() {
		double outputVal = LOGIC_FALSE; // LOGIC_FALSE=0 or LOGIC_TRUE=1
		if ((inputArcs != null) && (outputArcs != null)) {
			Arc inputArc = (Arc)inputArcs.getFirstChild();
			while (inputArc != null) {
				if (inputArc.getPlace().getVal() != LOGIC_FALSE) {
					outputVal = LOGIC_TRUE;
					break;
				}
				inputArc = (Arc)inputArc.getNextSibling();
			}
			Arc outputArc = (Arc)outputArcs.getFirstChild();
			while (outputArc != null) {
				outputArc.getPlace().setVal(outputVal);
				outputArc = (Arc)outputArc.getNextSibling();
			}
		}
	}
	
	/**
	 * NOT
	 */
	protected void kLogicNot() {
		double outputVal = LOGIC_FALSE; // 0 or 1
		if ((inputArcs != null) && (outputArcs != null)) {
			Arc inputArc = (Arc)inputArcs.getFirstChild();
			if (inputArc != null) {
				if (inputArc.getPlace().getVal() == LOGIC_FALSE) {
					outputVal = LOGIC_TRUE;
				}
			}
			Arc outputArc = (Arc)outputArcs.getFirstChild();
			while (outputArc != null) {
				outputArc.getPlace().setVal(outputVal);
				outputArc = (Arc)outputArc.getNextSibling();
			}
		}
	}
	
	/**
	 * Invoke a Xholon function node.
	 */
	protected void kFunction() {
		if (inputArcs == null) {return;}
		if (outputArcs == null) {return;}
		Arc outputArc = (Arc)outputArcs.getFirstChild();
		if (outputArc == null) {return;}
		if (function == null) {
			if (functionXPath == null) {
				return;
			}
			else {
				function = this.getXPath().evaluate(functionXPath, this);
				if (function == null) {return;}
			}
		}
		JsArrayNumber arr = this.pushInputsToArray(inputArcs.getFirstChild());
		IMessage result = function.sendSyncMessage(101, arr, this);
		Object outputVal = result.getData();
		if (this.isArray(outputVal)) {
			this.pushArrayToOutputs(outputVal, outputArc);
		}
		else {
			outputArc.getPlace().setVal((double)outputVal);
		}
	}
	
	protected native boolean isArray(Object obj) /*-{
		if (Array.isArray(obj)) {return true;}
		else {return false;}
	}-*/;
	
	/**
	 * Push each value in a JavaScript array to a separate output arc.
	 * @param An array of double.
	 * @param The first child of an instance of OutputArcs.
	 */
	protected native void pushArrayToOutputs(Object arr, IXholon outarc) /*-{
		var arc = outarc;
		for (var ix = 0; ix < arr.length; ix++) {
			if (arc) {
				var linkArr = arc.links(false,true);
				if (linkArr && linkArr.length) {
					var rnode = linkArr[0].reffedNode;
					if (rnode) {
						rnode.val(arr[ix]);
					}
				}
				arc = arc.next();
			}
			else {return;}
		}
	}-*/;
	
	/**
	 * Push the values of all input arcs onto a JavaScript array.
	 * @param The first child of an instance of InputArcs.
	 * @return An array of numeric values, possibly empty.
	 */
	protected native JsArrayNumber pushInputsToArray(IXholon inarc) /*-{
		var arr = [];
		while (inarc) {
			var linkArr = inarc.links(false,true);
			if (linkArr && linkArr.length) {
				var rnode = linkArr[0].reffedNode;
				if (rnode) {
					var inval = rnode.val();
					arr.push(inval);
				}
			}
			inarc = inarc.next();
		}
		return arr;
	}-*/;
	
	/**
	 * This is a simple prototype of one way to do Sequence Diagrams with a Petri net.
	 * Using www.websequencediagrams.com format.
	 * example
title Petri net - protocol
sender_2->network_20: SendPacket
network_20->receiver_39: TransmitPacket
receiver_39->network_20: ReceivePacket
network_20->sender_2: TransmitAck
sender_2->sender_2: ReceiveAck
	 * @param transNode
	 */
	protected void writeSequenceDiagram(IXholon transNode) {
		if (transNode == null) {return;}
		if (!shouldWriteSequenceDiagram) {return;}
		if (!sequenceDiagramTitleWritten) {
			// write title
			println("title " + this.getApp().getModelName());
			sequenceDiagramTitleWritten = true;
		}
		
		IXholon tx = null;
		if (inputArcs != null) {
			tx = inputArcs.getFirstChild();
		}
		if (tx == null) {
			tx = transNode;
		}
		else {
			tx = ((InputArc)tx).getPlace();
		}
		if (tx == null) {return;}
		
		IXholon rx = null;
		if (outputArcs != null) {
			rx = outputArcs.getFirstChild();
		}
		if (rx == null) {
			rx = transNode;
		}
		else {
			rx = ((OutputArc)rx).getPlace();
		}
		if (rx == null) {return;}
		
		String transNodeName = transNode.getRoleName();
		if (transNodeName == null) {
			transNodeName = transNode.getXhcName();
		}
		
		println(tx.getParentNode().getName()
				+ "->" + rx.getParentNode().getName()
				+ ": "
				+ transNodeName);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrVal) {
		if (attrName.equals("p")) {
			setP(Double.parseDouble(attrVal));
		}
		else if (attrName.equals("k")) {
			setK(Double.parseDouble(attrVal));
		}
		else if (attrName.equals("count")) {
			setVal(attrVal);
		}
		else if (attrName.equals("kineticsType")) {
			if (attrVal.startsWith("KINETICS_")) {
				if (attrVal.endsWith("BASIC_PTNET")) {setKineticsType(KINETICS_BASIC_PTNET);}
				else if (attrVal.endsWith("MASS_ACTION")) {setKineticsType(KINETICS_MASS_ACTION);}
				else if (attrVal.endsWith("GRID")) {setKineticsType(KINETICS_GRID);}
				else if (attrVal.endsWith("MICHAELIS_MENTEN")) {setKineticsType(KINETICS_MICHAELIS_MENTEN);}
				else if (attrVal.endsWith("MAXIMAL_PARALLELISM")) {setKineticsType(KINETICS_MAXIMAL_PARALLELISM);}
				else if (attrVal.endsWith("CUSTOM")) {setKineticsType(KINETICS_CUSTOM);}
				else if (attrVal.endsWith("DIFFUSION")) {setKineticsType(KINETICS_DIFFUSION);}
				else if (attrVal.endsWith("LOGIC_AND")) {setKineticsType(KINETICS_LOGIC_AND);}
				else if (attrVal.endsWith("LOGIC_OR")) {setKineticsType(KINETICS_LOGIC_OR);}
				else if (attrVal.endsWith("LOGIC_NOT")) {setKineticsType(KINETICS_LOGIC_NOT);}
				else if (attrVal.endsWith("FUNCTION")) {setKineticsType(KINETICS_FUNCTION);}
			}
			else {
				setKineticsType(Integer.parseInt(attrVal));
			}
		}
		// Michaelis-Menten vmax
		else if (attrName.equals("vmax")) {
			setVmax(Double.parseDouble(attrVal));
		}
		// Michaelis-Menten km
		else if (attrName.equals("km")) {
			setKm(Double.parseDouble(attrVal));
		}
		else if (attrName.equals("symbol")) {
			//((IDecoration)this.getXhc()).setSymbol(attrVal);
			setSymbol(attrVal);
		}
		else if (attrName.equals("functionXPath")) {
			setFunctionXPath(attrVal);
		}
		return 0;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.IXmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
		if (roleName != null) {xmlWriter.writeAttribute("roleName", roleName);}
		
		// The kineticsType is usually specifed once for the entire Petri Net, so this is usually redundant.
		// But with Petri Nets that use the KINETICS_LOGIC_ types, each node must be individually specified.
		if ((kineticsType == KINETICS_LOGIC_AND)
		 || (kineticsType == KINETICS_LOGIC_OR)
		 || (kineticsType == KINETICS_LOGIC_NOT)) {
			xmlWriter.writeAttribute("kineticsType", Integer.toString(kineticsType));
		}
		
		xmlWriter.writeAttribute("p", Double.toString(p));
		xmlWriter.writeAttribute("k", Double.toString(k));
		xmlWriter.writeAttribute("count", Double.toString(count));
		/*String symbol = ((IDecoration)this.getXhc()).getSymbol();
		if (symbol != null) {
			xmlWriter.writeAttribute("symbol", symbol);
		}*/
		if (functionXPath != null) {
			xmlWriter.writeAttribute("functionXPath", functionXPath);
		}
	}
	
	public IXholon getInputArcs() {
		return inputArcs;
	}
	
	public void setInputArcs(IXholon inputArcs) {
		this.inputArcs = inputArcs;
	}
	
	public IXholon getOutputArcs() {
		return outputArcs;
	}
	
	public void setOutputArcs(IXholon outputArcs) {
		this.outputArcs = outputArcs;
	}
	
	/**
	 * Get probability p.
	 * @return
	 */
	public double getP() {
		return p;
	}
	
	/**
	 * Set probability p.
	 * @param p
	 */
	public void setP(double p) {
		this.p = p;
	}
	
	/**
	 * Get reaction rate/constant k.
	 * @return
	 */
	public double getK() {
		return k;
	}
	
	/**
	 * Set reaction rate/constant k.
	 * @param k
	 */
	public void setK(double k) {
		this.k = k;
	}
	
	public double getCount() {
		return count;
	}
	
	public void setCount(double count) {
		this.count = count;
	}
	
	public static boolean isShouldWriteSequenceDiagram() {
		return shouldWriteSequenceDiagram;
	}
	
	public static void setShouldWriteSequenceDiagram(
			boolean shouldWriteSequenceDiagram) {
		Transition.shouldWriteSequenceDiagram = shouldWriteSequenceDiagram;
	}
	
	/*
	 * @see org.primordion.xholon.mech.petrinet.IKinetics#getKineticsType()
	 */
	public int getKineticsType() {
		return kineticsType;
	}
	
	/*
	 * @see org.primordion.xholon.mech.petrinet.IKinetics#setKineticsType(int)
	 */
	public void setKineticsType(int kineticsType) {
		this.kineticsType = kineticsType;
	}
	
	/**
	 * Get Michaelis-Menten Vmax.
	 * @return
	 */
	public double getVmax() {
		return vmax;
	}
	
	/**
	 * Set Michaelis-Menten Vmax.
	 * @param vmax
	 */
	public void setVmax(double vmax) {
		this.vmax = vmax;
	}
	
	/**
	 * Get Michaelis-Menten Km.
	 * @return
	 */
	public double getKm() {
		return km;
	}
	
	/**
	 * Set Michaelis-Menten Km.
	 * @param km
	 */
	public void setKm(double km) {
		this.km = km;
	}
	
	public double getDt() {
		return dt;
	}
	
	/**
	 * This should only be called by the Petri net (QueueTransitions).
	 * @param dt
	 */
	public void setDt(double dt) {
		this.dt = dt;
	}
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getFunctionXPath() {
		return functionXPath;
	}
	
	public void setFunctionXPath(String functionXPath) {
		this.functionXPath = functionXPath;
	}
	
	public IXholon getFunction() {
		return function;
	}
	
	public void setFunction(IXholon function) {
		this.function = function;
	}
	
}
