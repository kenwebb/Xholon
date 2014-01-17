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

import java.util.ArrayList;
import java.util.List;

import org.primordion.xholon.base.IIntegration;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.xholonhelper.IFindChildSibWith;
import org.primordion.xholon.util.ClassHelper;

/**
 * Petri net
 * <p>An instance of &lt;QueueTransitions> must be the firstChild of an instance of &lt;PetriNet>.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on August 7, 2012)
 */
public class PetriNet extends XholonWithPorts implements IKinetics {
	private static final long serialVersionUID = 7625420962194811618L;

	/**
	 * The type of kinetics used by instances of Transition in this Petri net,
	 * unless individual Transition nodes override it.
	 */
	private int kineticsType = KINETICS_DEFAULT;
	
	/**
	 * Probability that an enabled transition will fire.
	 * A real number between 0.0 and 1.0.
	 * 0.0 means the transition will never fire, even when enabled.
	 * 1.0 means the transition will always fire,when enabled.
	 */
	private double p = 1.0;
	
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
	private double k = 0.004; //0.001;
	
	/**
	 * time step multiplier
	 * <p>Each Petri net can have its own value.</p>
	 */
	private int timeStepMultiplier = IIntegration.M_1;
	
	/**
	 * delta t (an increment in time)
	 * <p>This is dependent on and must be consistent with timeStepMultiplier.</p>
	 */
	private double dt = 1.0 / (double)timeStepMultiplier;
	
	/** how many time step intervals to chart */
	private int chartInterval = 1;
	
	private String roleName = null;

	public void setRoleName(String roleName) {this.roleName = roleName;}
	public String getRoleName() {return roleName;}
	
	/**
	 * Get root node of a subtree that contains Transition nodes.
	 * This may be a "Transitions" node whose children are all "Transition" nodes.
	 * Or it may be a domain-specific node, some of whose descendants are "Transition" nodes.
	 * @return The root node, or null.
	 */
	public IXholon getTransitionsRoot() {
		IXholon qt = this.getFirstChild(); // "QueueTransitions" node
		if ((qt != null) && (ClassHelper.isAssignableFrom(QueueTransitions.class, qt.getClass()))) {
			return ((QueueTransitions)qt).getTransitionsRoot();
		}
		return null;
	}
	
	/**
	 * Get root node of a subtree that contains Place nodes.
	 * This may be a "Places" node whose children are all "Place" nodes.
	 * Or it may be a domain-specific node, some of whose descendants are "Place" nodes.
	 * @param usedInTransitions Whether to get the root of Places that are actually used in Transitions (true),
	 * or of the declared Places (false).
	 * @return The root node, or null.
	 */
	public IXholon getPlacesRoot(boolean usedInTransitions) {
		if (usedInTransitions) {
			IXholon tRoot = getTransitionsRoot();
			if (!tRoot.getXhc().hasAncestor("Transitions")) {
				return tRoot;
			}
		}
		return ((IFindChildSibWith)getService(
			    IXholonService.XHSRV_XHOLON_HELPER))
			    .findFirstChildWithAncestorXhClass(this, "Places");
	}
	
	/**
	 * Get list of transitions in the PN.
	 * The transitions are returned in the order they were originally specified in.
	 * @return A list of Transition nodes, possibly empty.
	 */
	public List<IXholon> getTransitions() {
		List<IXholon> list = new ArrayList<IXholon>();
		getNodesRecurse(getTransitionsRoot(), "TransitionPN", list);
		return list;
	}
	
	/**
	 * Get list of transitions in the PN.
	 * The transitions are returned in random order.
	 * @return A list of Transition nodes, possibly empty.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<IXholon> getTransitionsFromQ() {
		IXholon qt = this.getFirstChild(); // "QueueTransitions" node
		if ((qt != null) && (ClassHelper.isAssignableFrom(QueueTransitions.class, qt.getClass()))) {
			return (List)((QueueTransitions)qt).getItems(); // a Vector
		}
		return new ArrayList<IXholon>();
	}
	
	/**
	 * Get list of actual Places used in a Transition in the PN.
	 * TODO avoid duplicates
	 * TODO get places from Transition arcs (dupes, no unused; use Set or XholonSortedNode tree),
	 *      or directly as nodes in the tree (no-dupes, possible unused places) ?
	 * @return A list of Place nodes, possibly empty.
	 */
	public List<IXholon> getPlaces() {
		List<IXholon> list = new ArrayList<IXholon>();
		getNodesRecurse(getPlacesRoot(true), "PlacePN", list);
		return list;
	}
	
	/**
	 * Get nodes from the tree, using recursion.
	 * @param node The current node in the tree.
	 * @param xhcName The XholonClass being searched for.
	 * @param list The list being constructed.
	 */
	protected void getNodesRecurse(IXholon node, String xhcName, List<IXholon> list) {
		if (node == null) {return;}
		if (node.getXhc().hasAncestor(xhcName)) {
			list.add(node);
		}
		IXholon childNode = node.getFirstChild();
		while (childNode != null) {
			getNodesRecurse(childNode, xhcName, list);
			childNode = childNode.getNextSibling();
		}
	}
	
	/**
	 * Initialize a Transition node from values (k p kineticsType) specified for the entire Petri net.
	 * @param node A Transition node that's part of this Petri net.
	 */
	protected void initTransition(IXholon node) {
		Transition t = (Transition)node;
		if (t.getK() == K_UNSPECIFIED) {
			t.setK(this.getK());
		}
		if (t.getP() == P_UNSPECIFIED) {
			t.setP(this.getP());
		}
		if (t.getKineticsType() == KINETICS_UNSPECIFIED) {
			t.setKineticsType(this.getKineticsType());
			if ((this.getKineticsType() == KINETICS_MASS_ACTION)
					|| (this.getKineticsType() == KINETICS_MICHAELIS_MENTEN)) {
				// use KINETICS_DIFFUSION if inputPlace and outputPlace are the same type
				if (t.couldUseDiffusion()) {
					t.setKineticsType(KINETICS_DIFFUSION);
				}
			}
		}
		if (this.getTimeStepMultiplier() != IIntegration.M_1) {
			t.setDt(this.getDt());
		}
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
		else if (attrName.equals("kineticsType")) {
			setKineticsType(Integer.parseInt(attrVal));
		}
		else if (attrName.equals("timeStepMultiplier")) {
			setTimeStepMultiplier(Integer.parseInt(attrVal));
			dt = 1.0 / (double)getTimeStepMultiplier();
		}
		else if (attrName.equals("chartInterval")) {
			setChartInterval(Integer.parseInt(attrVal));
		}
		return 0;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXml(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
		// write a comment to explain the different kinetic types
		xmlWriter.writeComment("\nkineticsType:\n1 = Basic place/transition net kinetics (default)\n2 = Mass action kinetics\n");
		xmlWriter.writeText("\n");
		super.toXml(xholon2xml, xmlWriter);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.IXmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
		if (roleName != null) {xmlWriter.writeAttribute("roleName", roleName);}
		xmlWriter.writeAttribute("kineticsType", Integer.toString(kineticsType));
		xmlWriter.writeAttribute("p", Double.toString(p));
		xmlWriter.writeAttribute("k", Double.toString(k));
		xmlWriter.writeAttribute("timeStepMultiplier", Integer.toString(timeStepMultiplier));
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
		if ((kineticsType < KINETICS_MIN_VALID) || (kineticsType > KINETICS_MAX_VALID)) {return;}
		this.kineticsType = kineticsType;
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
	
	public int getTimeStepMultiplier() {
		return timeStepMultiplier;
	}
	
	public void setTimeStepMultiplier(int timeStepMultiplier) {
		this.timeStepMultiplier = timeStepMultiplier;
	}
	
	public double getDt() {
		return dt;
	}
	
	public int getChartInterval() {
		return chartInterval;
	}

	public void setChartInterval(int chartInterval) {
		this.chartInterval = chartInterval;
	}
	
}
