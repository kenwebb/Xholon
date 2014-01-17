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

package org.primordion.xholon.mech.petrinet.cat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.mech.petrinet.Arc;
import org.primordion.xholon.mech.petrinet.PetriNet;

/**
 * Analysis and Reporting for Mathematical Categories.
 * <p>"If we ignore the tokens, a Petri net is a way of specifying
 * a free strict symmetric monoidal category by giving a set of
 * objects x_1,...,x_n and a set of morphisms between tensor products
 * of these objects[1]"</p>
 * <p>"1 denotes the tensor product of no objects"[1]</p>
 * <p>⊗ is the tensor product symbol; it means + in Petri net notation[1]</p>
 * <p>Analysis nodes are optional.
 * If used, an Analysis node must be a child of the PetriNet (PN) node,
 * typically located just after the QueueTransitions node.
 * An Analysis node might contain other nodes that provide additional analysis functionality.
 * These child nodes could for example by domain-specific or app-specific script nodes.
 * </p>
 * <p>Correspondences between PN and Category terminology:</p>
<pre>
Petri net     Category
------------- -------------------------
place         object
input/output arcs   ?
input arcs    ?
output arcs   ?
transition    morphism
marking       ?
weight        ?
rate          a label (nonnegative number) on a morphism 
</pre>
 * <p>To add an Analysis node to a running Petri net app,
 * paste the following as last child of PetriNet node:</p>
<pre>&lt;AnalysisCat/></pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://www.azimuthproject.org/azimuth/show/Petri+net">(1) Petri net (Azimuth Project)</a>
 * @see <a href="http://johncarlosbaez.wordpress.com/2011/01/18/petri-nets/">(2) Petri nets (Baez - Azimuth Project)</a>
 * @see <a href="http://ncatlab.org/nlab/show/symmetric+monoidal+category">(3) symmetric monoidal category</a>
 * @since 0.8.1 (Created on August 14, 2012)
 */
public class AnalysisCat extends XholonWithPorts {
	private static final long serialVersionUID = -1767787590187506822L;
	
	private static final String showAll = "Show all";
	private static final String showObjects = "Show objects";
	private static final String showMorphisms = "Show morphisms";
	/** action list */
	private String[] actions = {showAll,showObjects,showMorphisms};
	
	/**
	 * The cutoff in number of tokens to decide if a place exists.
	 * If there are no instances of a place, then that place does not appear in the list returned.
	 * Normally, with a Xholon app, we want to see a place even if it's current token count is 0.0.
	 * So by default, this variable has a value of -1.0,
	 * which means use the place if it has a concentration greater than -1.0 .
	 * To exclude places that have a token count of 0.0, set this variable to 0.0 .
	 */
	private double tokensCutoff = -1.0;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getActionList()
	 */
	public String[] getActionList() {
		return actions;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setActionList(java.lang.String[])
	 */
	public void setActionList(String[] actionList)
	{
		actions = actionList;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#doAction(java.lang.String)
	 */
	public void doAction(String action) {
		if (showAll.equals(action)) {
			println("objects " + getObjects());
			println("morphisms " + getMorphisms());
		}
		else if (showObjects.equals(action)) {
			println("objects " + getObjects());
		}
		else if (showMorphisms.equals(action)) {
			println("morphisms " + getMorphisms());
		}
	}
	
	/**
	 * Get list of objects in the Category = list of places in the PN.
	 * @return A list, for example: [A, B, C, D, E]
	 */
	public List<String> getObjects() {
		Iterator<IXholon> objectsIt = getObjectsRaw().iterator();
		List<String> objects = new ArrayList<String>();
		while (objectsIt.hasNext()) {
			IXholon anObject = objectsIt.next();
			if (anObject.getVal() > tokensCutoff) {
				objects.add(makeNodeName(anObject, "PlacePN"));
			}
		}
		return objects;
	}
	
	/**
	 * Get raw list of objects in the Category = list of places in the PN.
	 * @return
	 */
	protected List<IXholon> getObjectsRaw() {
		return ((PetriNet)this.getParentNode()).getPlaces();
	}
	
	/**
	 * Get list of morphisms in the Category = list of transitions in the PN.
	 * @return A list, for example: [
	 * A_BB:A → B ⊗ B,
	 * BB_A:B ⊗ B → A,
	 * AC_D:A ⊗ C → D,
	 * D_AC,
	 * D_BE,
	 * BE_AC,
	 * F_:F → 1,
	 * _F:1 → F
	 * ]
	 */
	public List<String> getMorphisms() {
		Iterator<IXholon> morphismsIt = getMorphismsRaw().iterator();
		List<String> morphisms = new ArrayList<String>();
		while (morphismsIt.hasNext()) {
			IXholon morphism = morphismsIt.next();
			morphisms.add(makeNodeName(morphism, "TransitionPN") + makeMorphismStr(morphism));
		}
		return morphisms;
	}
	
	/**
	 * Get list of reactions in the Category = list of transitions in the PN.
	 * @return
	 */
	protected List<IXholon> getMorphismsRaw() {
		return ((PetriNet)this.getParentNode()).getTransitions();
	}
	
	/**
	 * Construct a node's name.
	 * @param node The node.
	 * @param baseXhcName The base XholonClass name for this type of node (PlacePN, TransitionPN).
	 * @return The node's xhcName or roleName.
	 */
	protected String makeNodeName(IXholon node, String baseXhcName) {
		if (node.getXhcId() < IMechanism.MECHANISM_ID_START) {
			// this is not a base class for this type of node (PlacePN, TransitionPN, etc.)
			return node.getXhcName();
		}
		else if (node.getRoleName() == null) {
			// this is a base class with not role name
			return node.getXhcName();
		}
		/*if (!baseXhcName.equals(node.getXhcName())) {
			return node.getXhcName();
		}
		else if (node.getRoleName() == null) {
			return baseXhcName;
		}*/
		// this is a base class with a role name
		return node.getRoleName();
	}
	
	/**
	 * 
	 */
	protected String makeMorphismStr(IXholon node) {
		StringBuilder sb = new StringBuilder();
		sb.append(":");
		sb.append(makeComplexStr(node, "InputArcs"));
		sb.append(" → ");
		sb.append(makeComplexStr(node, "OutputArcs"));
		return sb.toString();
	}
	
	/**
	 * 
	 * @param node
	 * @param inOutArcsName
	 * @return
	 */
	protected String makeComplexStr(IXholon node, String inOutArcsName) {
		IXholon arc = null;
		StringBuilder sb = new StringBuilder();
		IXholon arcs = node.findFirstChildWithXhClass(inOutArcsName);
		if (arcs == null) {
		//	sb.append("1");
		}
		else {
			arc = arcs.getFirstChild();
			//if (arc == null) {
			//	sb.append("1");
			//}
			while (arc != null) {
				int weight = (int)arc.getVal(); // weight
				if (weight == 0) {}
				else {
					while (weight > 0) {
						//sb.append(((Arc)arc).getPlace().getXhcName());
						sb.append(makeNodeName(((Arc)arc).getPlace(), "PlacePN"));
						if (weight > 1) {
							sb.append(" ⊗ ");
						}
						weight--;
					}
				}
				arc = arc.getNextSibling();
				if (arc != null) {
					sb.append(" ⊗ ");
				}
			}
		}
		String complexStr = sb.toString();
		if (complexStr.length() == 0) {
			return "1";
		}
		return complexStr;
	}
	
	/**
	 * Prevent tokensCutoff from being written out as an attribute.
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.IXmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {}

	public double getTokensCutoff() {
		return tokensCutoff;
	}

	public void setTokensCutoff(double tokensCutoff) {
		this.tokensCutoff = tokensCutoff;
	}
}
