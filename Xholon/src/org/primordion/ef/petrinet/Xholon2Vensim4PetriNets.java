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

//import java.io.File;
//import java.io.IOException;
//import java.io.Writer;
//import java.security.AccessControlException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.Xholon;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.mech.petrinet.Arc;
import org.primordion.xholon.mech.petrinet.PetriNet;
import org.primordion.xholon.mech.petrinet.Place;
import org.primordion.xholon.mech.petrinet.Transition;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon Petri net model in Vensim format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on September 4, 2012)
 * @see <a href="http://www.vensim.com/">Vensim website</a>
 * @see <a href="http://tools.systemdynamics.org/sdm-doc/">SDM-Doc website</a>
 */
@SuppressWarnings("serial")
public class Xholon2Vensim4PetriNets extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {

	// suffixes to add to Vensim names to ensure no duplicates across the various Vensim types
	protected static final String CMPT = "c"; // compartment
	protected static final String SPEC = "s"; // species
	protected static final String REAC = "r"; // reaction
	
	public static final String VENSIM_DEFAULT_STOCK_UNITS = "things";
	public static final String VENSIM_DEFAULT_FLOW_UNITS = "things/s";
	public static final String VENSIM_DEFAULT_TIME_UNITS = "s";
	
	private String outFileName;
	private String outPath = "./ef/vensim/";
	private String modelName;
	private IXholon root;
	//private Writer out;
	private StringBuilder sb = null;
	private StringBuilder sbControl = null;
	private StringBuilder sbSketch = null;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/** Template to use when writing out node names. */
	protected String nameTemplate = "r_c_i^";
	
	/**
	 * TODO
	 * Warning: this will currently only have a value if the PetriNet node is early enough in the Xholon tree.
	 */
	private List<IXholon> reactions = null;

	/**
	 * Constructor.
	 */
	public Xholon2Vensim4PetriNets() {}
	
	@Override
	public String getVal_String() {
	  return sb.toString();
	}
	
	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String fileName, String modelName, IXholon root) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (fileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId()
			+ "_" + timeStamp + ".mdl";
		}
		else {
			this.outFileName = fileName;
		}
		this.modelName = modelName;
		this.root = root;
		
		return true;
	}

	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#writeAll()
	 */
	public void writeAll() {
	  /* GWT
		boolean shouldClose = true;
		if (root.getApp().isUseAppOut()) {
			out = root.getApp().getOut();
			shouldClose = false;
		}
		else {
			try {
				// create any missing output directories
				File dirOut = new File(outPath);
				dirOut.mkdirs(); // will create a new directory only if there is no existing one
				out = MiscIo.openOutputFile(outFileName);
			} catch(AccessControlException e) {
				//out = new PrintWriter(System.out);
				out = root.getApp().getOut();
				shouldClose = false;
			}
		}*/
		sb = new StringBuilder()
		.append("{UTF-8}\n");
		sbControl = new StringBuilder();
		sbSketch = new StringBuilder()
		.append("\n\\\\\\---/// Sketch information - do not modify anything except names\n")
		.append("V300  Do not put anything below this section - it will be ignored\n")
		.append("*View 1\n")
		.append("$0-0-0,0,Arial|12||0-0-0|0-0-0|0-0-0|-1--1--1|-1--1--1|96,96,5\n");

		writeNode(root);
		//writeControl(); // called by PetriNet node
		writeSketchEnd();
		//try {
			//out.write(sb.toString());
			//out.write(sbControl.toString());
			//out.write(sbSketch.toString());
			writeToTarget(sb.toString() + sbControl.toString() + sbSketch.toString(), outFileName, outPath, root);
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		//if (shouldClose) {
		//	MiscIo.closeOutputFile(out);
		//}
	}
	
	/**
	 * Write one node, and its child nodes.
	 * @param node The current node in the Xholon hierarchy.
	 */
	protected void writeNode(IXholon node) {
		if (node.getXhc().hasAncestor("PlacePN")) {
			String xhcOrRoleName = makeXhcOrRoleName(node);
			String units = ((Place)node).getUnits();
			if (units == null) {
				units = VENSIM_DEFAULT_STOCK_UNITS;
			}
			
			sb.append(xhcOrRoleName)
			.append(" = INTEG(");
			
			// get all reactions that involve this species/place
			List<IXholon> reactions = getReactionsRaw();
			if (reactions != null) {
				Iterator<IXholon> reactionsIt = getReactionsRaw().iterator();
				while (reactionsIt.hasNext()) {
					Transition aReaction = (Transition)reactionsIt.next();
					// an inputArc for a reaction is a minus for the species
					if (aReaction.getInputArcs() != null) {
						sb.append(this.getTerm(node, aReaction, aReaction.getInputArcs(), "-"));
					}
					// an outputArc for a reaction is a plus for the species
					if (aReaction.getOutputArcs() != null) {
						sb.append(this.getTerm(node, aReaction, aReaction.getOutputArcs(), "+"));
					}
				}
			}
			
			sb.append(",")
			.append(node.getVal())
			.append(")\n  ~  ")
			.append(units)
			.append("\n  ~    |\n\n");
			
			sbSketch
			.append("10,")
			.append(node.getId())
			.append(",")
			.append(xhcOrRoleName)
			.append(",")
			//TODO add x y
			.append("100,100")
			.append(",53,22,3,3,0,0,0,0,0,0\n");
		}
		
		else if (node.getXhc().hasAncestor("TransitionPN")) {
			Transition transition = (Transition)node;
			String xhcOrRoleName = makeXhcOrRoleName(node) + "_" + node.getId() + REAC;
			String xhcOrRoleNameK = xhcOrRoleName + "_K";
			String units = null; //((Transition)node).getUnits();
			if (units == null) {
				units = VENSIM_DEFAULT_FLOW_UNITS;
			}
			// 
			sb.append(xhcOrRoleNameK)
			.append(" = ")
			.append(transition.getK())
			.append("\n  ~  ")
			.append(units)
			.append("\n  ~    |\n\n");
			
			// 
			String complex = "";
			if (transition.getInputArcs() != null) {
				IXholon arc = transition.getInputArcs().getFirstChild();
				while (arc != null) {
					IXholon place = ((Arc)arc).getPlace();
					if (place != null) {
						complex += " * " + makeXhcOrRoleName(place);
						if (arc.getVal() > 1.0) {
							complex += "^" + (int)arc.getVal();
						}
					}
					arc = arc.getNextSibling();
				}
			}
			sb.append(xhcOrRoleName)
			.append(" = ")
			.append(xhcOrRoleNameK + complex)
			.append("\n  ~  ")
			.append(units)
			.append("\n  ~    |\n\n");
			
			sbSketch
			.append("11,")
			.append(node.getId())
			.append(",48,")
			//TODO add x y
			.append("200,180")
			.append(",5,6,34,3,0,0,1,0,0,0\n");
			
			sbSketch
			.append("10,")
			.append(node.getId() + 1)
			.append(",")
			.append(xhcOrRoleName)
			.append(",")
			//TODO add x y
			.append("200,200")
			.append(",41,8,40,3,0,0,0,0,0,0\n");
		}
		else {
			if (node.getXhc().hasAncestor("PetriNet")) {
				reactions = ((PetriNet)node).getTransitions();
				writeControl(node);
			}
			
			// children
			IXholon childNode = node.getFirstChild();
			while (childNode != null) {
				writeNode(childNode);
				childNode = childNode.getNextSibling();
			}
		}
	}
	
	/**
	 * Write Simulation Control Parameters.
	 */
	protected void writeControl(IXholon node) {
		sbControl
		.append("********************************************************\n")
		.append("	.Control\n")
		.append("********************************************************~\n")
		.append("		Simulation Control Paramaters\n")
		.append("	|\n");

		int maxProcessLoops = node.getApp().getMaxProcessLoops();
		if (maxProcessLoops == -1) {
			maxProcessLoops = 1000;
		}
		sbControl
		.append("FINAL TIME = ").append(maxProcessLoops).append("\n")
		.append("	~	").append(VENSIM_DEFAULT_TIME_UNITS).append("\n")
		.append("	~	The final time for the simulation.\n")
		.append("	|\n");

		sbControl
		.append("INITIAL TIME = ").append(node.getApp().getTimeStep()).append("\n")
		.append("	~	").append(VENSIM_DEFAULT_TIME_UNITS).append("\n")
		.append("	~	The initial time for the simulation.\n")
		.append("	|\n");

		double dt = ((PetriNet)node).getDt();
		double savePer = dt * ((PetriNet)node).getChartInterval();
		sbControl
		.append("SAVEPER = ").append(savePer).append("\n")
		.append("	~	").append(VENSIM_DEFAULT_TIME_UNITS).append("\n")
		.append("	~	The frequency with which output is stored.\n")
		.append("	|\n");

		sbControl
		.append("TIME STEP = ").append(dt).append("\n")
		.append("	~	").append(VENSIM_DEFAULT_TIME_UNITS).append("\n")
		.append("	~	The time step for the simulation.\n")
		.append("	|\n");
	
	}
	
	/**
	 * Write end of sketch information.
	 */
	protected void writeSketchEnd() {
		// TODO
		sbSketch
		.append("///---\\\\\\\n")
		.append(":L<%^E!@\n");;
	}

	/**
	 * Make a name prefix, to distinguish multiple instances with the same name.
	 * @param node
	 * @return
	 */
	protected String makeNamePrefix(IXholon node) {
		IXholon p = node.getParentNode().getParentNode();
		if (p != null && !"PetriNet".equals(p.getXhcName())) {
			return node.getParentNode().getXhcName() + "__";
		}
		return "";
	}
	
	/**
	 * Make a name, either an xhcName or a roleName.
	 * @param node
	 * @return
	 */
	protected String makeXhcOrRoleName(IXholon node) {
		if (node.getRoleName() == null) {
			return node.getXhcName();
		}
		else {
			return node.getRoleName();
		}
	}
	
	/**
	 * Get list of CRN reactions.
	 * @return
	 */
	protected List<IXholon> getReactionsRaw() {
		return reactions;
	}
	
	/**
	 * Get one term in a differential equation (DE).
	 * @param aSpecies The species that the DE is being constructed for.
	 * @param aReaction The reaction that this term is being constructed for.
	 * @param arcs The inputArcs or outputArcs that will be searched for matches with aSpecies.
	 * @param plusMinus Whether a plus or minus sign will prepend the term. 
	 * @return The term, for example:
	 * <p>-a_BB_13r</p>
	 * <p>+bE_AC_41r</p>
	 */
	protected String getTerm(IXholon aSpecies, Transition aReaction, IXholon arcs, String plusMinus) {
		String termName = makeXhcOrRoleName(aReaction) + "_" + aReaction.getId() + REAC;
		StringBuilder sb = new StringBuilder();
		StringBuilder sbSubTerm1 = new StringBuilder();
		boolean found = false;
		Arc arc = (Arc)arcs.getFirstChild();
		while (arc != null) {
			IXholon someSpecies = arc.getPlace();
			if ((someSpecies != null) && (someSpecies == aSpecies)) {
				found = true;
				double w = arc.getVal();
				if (w > 1.0) {
					sbSubTerm1.append((int)w).append("*");
				}
				sb.append(" ").append(plusMinus);
			}
			arc = (Arc)arc.getNextSibling();
		}
		if (found) {
			sb.append(sbSubTerm1.toString()).append(termName);
		}
		return sb.toString();
	}
	
	public String getOutFileName() {
		return outFileName;
	}

	public void setOutFileName(String outFileName) {
		this.outFileName = outFileName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public IXholon getRoot() {
		return root;
	}

	public void setRoot(IXholon root) {
		this.root = root;
	}

	public String getNameTemplate() {
		return nameTemplate;
	}

	public void setNameTemplate(String nameTemplate) {
		this.nameTemplate = nameTemplate;
	}

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}
	
}
