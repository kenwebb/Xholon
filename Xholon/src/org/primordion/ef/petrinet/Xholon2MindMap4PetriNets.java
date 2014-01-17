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

package org.primordion.ef.petrinet;

import java.util.List;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.ef.Xholon2MindMap;

/**
 * Export a Xholon model in a mind map (FreeMind) format.
 * This is intended to be used with a model that contains Petri nets.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 25, 2012)
 */
@SuppressWarnings("serial")
public class Xholon2MindMap4PetriNets extends Xholon2MindMap {
	
	/**
	 * constructor
	 */
	public Xholon2MindMap4PetriNets() {
		super();
		this.setLinkColor("#000080");
		this.setReverseLinkColor("#B22222");
		this.setShouldShowClouds(false);
		this.setMaxFoldLevel(1);
	}

	/*
	 * @see org.primordion.xholon.io.Xholon2MindMap#writeNode(org.primordion.xholon.base.IXholon, int)
	 */
	protected void writeNode(IXholon node, int level) {
		if ("InputArcs".equals(node.getXhcName())) {return;}
		if ("OutputArcs".equals(node.getXhcName())) {return;}
		if ("QueueTransitions".equals(node.getXhcName())) {return;}
		super.writeNode(node, level);
	}
	
	/*
	 * @see org.primordion.xholon.io.Xholon2MindMap#writeFolded(org.primordion.xholon.base.IXholon, int)
	 */
	protected void writeFolded(IXholon node, int level) {
		//if ("TransitionPN".equals(node.getXhcName())) {return;}
		if (node.getXhc().hasAncestor("TransitionPN")) {return;}
		super.writeFolded(node, level);
	}
	
	/*
	 * @see org.primordion.xholon.io.Xholon2MindMap#writeLinks(org.primordion.xholon.base.IXholon)
	 */
	@SuppressWarnings("unchecked")
	protected void writeLinks(IXholon node)
	{
		if (isShouldShowLinks() == false) {return;}
		//if ("TransitionPN".equals(node.getXhcName())) {
		if (node.getXhc().hasAncestor("TransitionPN")) {
			// input arcs
			IXholon inputArcs = node.findFirstChildWithXhClass("InputArcs");
			if (inputArcs != null) {
				IXholon inputArc = inputArcs.getFirstChild();
				while (inputArc != null) {
					int nodeNum = 0;
					List<PortInformation> portList = inputArc.getAllPorts();
					for (int i = 0; i < portList.size(); i++) {
						writeLink(nodeNum, node, portList.get(i), true);
						nodeNum++;
					}
					inputArc = inputArc.getNextSibling();
				}
			}
			
			// output arcs
			IXholon outputArcs = node.findFirstChildWithXhClass("OutputArcs");
			if (outputArcs != null) {
				IXholon outputArc = outputArcs.getFirstChild();
				while (outputArc != null) {
					int nodeNum = 0;
					List<PortInformation> portList = outputArc.getAllPorts();
					for (int i = 0; i < portList.size(); i++) {
						writeLink(nodeNum, node, portList.get(i), false);
						nodeNum++;
					}
					outputArc = outputArc.getNextSibling();
				}
			}
			
		}
		else {
			super.writeLinks(node);
		}
	}
	
}
