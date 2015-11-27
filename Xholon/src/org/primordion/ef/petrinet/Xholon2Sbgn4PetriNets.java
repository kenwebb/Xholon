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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.Xholon;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.mech.petrinet.Arc;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon Petri net model in Systems Biology Graphical Notation (SBGN) XML format.
 * The resulting file can be viewed and edited by SBGN-ED,
 * a Visualization and Analysis of Networks containing Experimental Data (VANTED) add-on.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on August 5, 2012)
 * @see <a href="http://www.sbgn.org/">SBGN website</a>
 * @see <a href="http://vanted.ipk-gatersleben.de/addons/sbgn-ed/">SBGN-ED website</a>
 * @see <a href="http://vanted.ipk-gatersleben.de/">VANTED website</a>
 */
@SuppressWarnings("serial")
public class Xholon2Sbgn4PetriNets extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
	
	private String outFileName;
	private String outPath = "./ef/sbgn/";
	private String modelName;
	private IXholon root;
	//private Writer out;
	private StringBuilder sb;
	private StringBuilder arcSb;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/** Template to use when writing out node names. */
	protected String nameTemplate = "r_c_i^";

	// default x and y coordinates for Place and Transition
	private double placeX = 200.0;
	private double placeH = 60.0;
	private double placeW = 120.0;
	private double transitionX = 700.0;
	private double transitionH = 40.0;
	private double transitionW = 120.0;
	private double processX = 600.0;
	private double processH = 20.0;
	private double processW = 20.0;	
	private double nextY = -50.0;
	private double yInc = 60.0;
	
	// by default, the settings are those that work with SBGN-ED
	private boolean useSbgnXmlns = true;
	
	//private boolean shouldClonePlaces = true;
	//private boolean shouldCloneTransitions = false;
	//private XholonSortedNode placeList = null;
	//private XholonSortedNode transitionList = null;
	
	/**
	 * For use with BlackboardService.
	 */
	public static final String SBGN_PLACE_CLONE_LIST = "SbgnPlaceCloneList";
	private List<String> placeCloneList = null;
	
	/**
	 * Constructor.
	 */
	public Xholon2Sbgn4PetriNets() {}
	
	@Override
	public String getVal_String() {
	  return sb.toString();
	}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	@SuppressWarnings("unchecked")
	public boolean initialize(String mmFileName, String modelName, IXholon root) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (mmFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".sbgn";
		}
		else {
			this.outFileName = mmFileName;
		}
		this.modelName = modelName;
		this.root = root;
		/*if (shouldClonePlaces) {
			createNodeLists(root);
			if (placeList != null) {
				println(placeList.size());
				placeList.inOrderPrint(XholonSortedNode.DEFAULT_LEVEL);
			}
		}*/
		
		Map<String,String> blackboardService =
			(Map<String,String>)root.getService(IXholonService.XHSRV_BLACKBOARD);
		String cl = blackboardService.get(SBGN_PLACE_CLONE_LIST);
		if ((cl != null) && (cl.length() > 0)) {
			placeCloneList = Arrays.asList(cl.split(","));
			//for (int i = 0; i < placeCloneList.size(); i++) {
			//	println(placeCloneList.get(i));
			//}
		}
		
		return true;
	}
	
	/**
	 * Create a list of Places, and optionally a list of Transitions.
	 */
	/*protected void createNodeLists(IXholon node) {
		if (node.getXhc().hasAncestor("PlacePN")) {
			addToPlaceList(node);
		}
		else if (node.getXhc().hasAncestor("TransitionPN")) {
			if (shouldCloneTransitions) {
				// TODO separate Transition list?
			}
		}
		else if ("InputArc".equals(node.getXhcName())) {
			addToPlaceList(((Arc)node).getPlace());
		}
		else if ("OutputArc".equals(node.getXhcName())) {
			// TODO don't add if place already added for this Transition ?
			addToPlaceList(((Arc)node).getPlace());
		}
		// children
		IXholon childNode = node.getFirstChild();
		while (childNode != null) {
			createNodeLists(childNode);
			childNode = childNode.getNextSibling();
		}
	}*/
	
	/*protected void addToPlaceList(IXholon node) {
		if (placeList == null) {
			placeList = new XholonSortedNode();
			placeList.setVal((Object)node.getName("r_c^^^"));
		}
		else {
			placeList.add(node.getName("r_c^^^"));
		}
	}*/
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
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
		sb = new StringBuilder();
		arcSb = new StringBuilder();
		//try {
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
			if (useSbgnXmlns) {
				sb.append("<sbgn xmlns=\"http://sbgn.org/libsbgn/pd/0.1\">\n");
			}
			else {
				sb.append("<sbgn>\n");
			}
			sb.append("<!-- To view this file, download open-source SBGN-ED from http://vanted.ipk-gatersleben.de/addons/sbgn-ed/ -->\n");
			sb.append(
				"<!--\nAutomatically generated by Xholon version 0.8.1, using Xholon2Sbgn4PetriNets.java\n"
				+ new Date() + " " + timeStamp + "\n"
				+ "model: " + modelName + "\n"
				+ "www.primordion.com/Xholon\n-->\n");
			sb.append("<map>\n");
			
			writeNode(root, 0); // root is level 0
			sb.append(arcSb.toString());
			sb.append("</map>\n");
			sb.append("</sbgn>\n");
			//out.write(sb.toString());
			//out.flush();
			writeToTarget(sb.toString(), outFileName, outPath, root);
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
	 * @param level Current level in the hierarchy.
	 */
	protected void writeNode(IXholon node, int level) {
		//try {
			if (node.getXhc().hasAncestor("PlacePN")) {
				String xhcOrRoleName = makeXhcOrRoleName(node);
				if ((placeCloneList == null)
						|| ((placeCloneList != null) && (!placeCloneList.contains(xhcOrRoleName)))) {
					//sb.append(" <clone/>\n");
					nextY += yInc;
					String nodeName = node.getName(nameTemplate);
					String namePrefix = makeNamePrefix(node);
					
					// simple chemical node
					sb.append("<glyph id=\"" + nodeName + "\" class=\"simple chemical\">\n");
					sb.append(" <label text=\"" + namePrefix + xhcOrRoleName + "\"/>\n");
					sb.append(" <bbox y=\"" + nextY + "\" x=\"" + placeX + "\" h=\"" + placeH + "\" w=\"" + placeW + "\"/>\n");
					sb.append("</glyph>\n\n");
				}
			}
			
			else if (node.getXhc().hasAncestor("TransitionPN")) {
				nextY += yInc;
				String nodeName = node.getName(nameTemplate);
				String namePrefix = makeNamePrefix(node);
				String xhcOrRoleName = makeXhcOrRoleName(node);
				
				// macromolecule node
				sb.append("<glyph id=\"" + nodeName + "\" class=\"macromolecule\">\n");
				sb.append(" <label text=\"" + namePrefix + xhcOrRoleName + "\"/>\n");
				sb.append(" <bbox y=\"" + nextY + "\" x=\"" + transitionX
						+ "\" h=\"" + transitionH + "\" w=\"" + transitionW + "\"/>\n");
				sb.append("</glyph>\n\n");
				
				// process node
				sb.append("<glyph id=\"p" + nodeName + "\" class=\"process\">\n");
				sb.append(" <bbox y=\"" + nextY + "\" x=\"" + processX
						+ "\" h=\"" + processH + "\" w=\"" + processW + "\"/>\n");
				sb.append(" <port id=\"" + nodeName + ".1\" y=\"" + nextY + "\" x=\"" + processX + "\"/>\n");
				sb.append(" <port id=\"" + nodeName + ".2\" y=\"" + nextY + "\" x=\"" + processX + "\"/>\n");
				sb.append("</glyph>\n\n");
				
				// catalysis arc
				drawArcCatalysis(node, node, transitionX, nextY, processX, nextY);
			}
			
			else if ("InputArc".equals(node.getXhcName())) {
				IXholon place = ((Arc)node).getPlace();
				String xhcOrRoleName = makeXhcOrRoleName(place);
				if ((placeCloneList != null) && (placeCloneList.contains(xhcOrRoleName))) {
					nextY += yInc;
					String nodeName = node.getName(nameTemplate);
					String namePrefix = makeNamePrefix(place);
					
					// simple chemical node
					sb.append("<glyph id=\"" + nodeName + "\" class=\"simple chemical\">\n");
					sb.append(" <label text=\"" + namePrefix + xhcOrRoleName + "\"/>\n");
					sb.append(" <clone/>\n");
					sb.append(" <bbox y=\"" + nextY + "\" x=\"" + placeX + "\" h=\"" + placeH + "\" w=\"" + placeW + "\"/>\n");
					sb.append("</glyph>\n\n");
					drawArcConsumption(node.getParentNode().getParentNode(), node,
							placeX, nextY, processX, nextY);
				}
				else {
					drawArcConsumption(node.getParentNode().getParentNode(), place,
							placeX, nextY, processX, nextY);
				}
			}
			
			else if ("OutputArc".equals(node.getXhcName())) {
				IXholon place = ((Arc)node).getPlace();
				String xhcOrRoleName = makeXhcOrRoleName(place);
				if ((placeCloneList != null) && (placeCloneList.contains(xhcOrRoleName))) {
					nextY += yInc;
					String nodeName = node.getName(nameTemplate);
					String namePrefix = makeNamePrefix(place);
					
					// simple chemical node
					sb.append("<glyph id=\"" + nodeName + "\" class=\"simple chemical\">\n");
					sb.append(" <label text=\"" + namePrefix + xhcOrRoleName + "\"/>\n");
					sb.append(" <clone/>\n");
					sb.append(" <bbox y=\"" + nextY + "\" x=\"" + placeX + "\" h=\"" + placeH + "\" w=\"" + placeW + "\"/>\n");
					sb.append("</glyph>\n\n");
					drawArcProduction(node, node.getParentNode().getParentNode(),
							processX, nextY, placeX, nextY);
				}
				else {
					drawArcProduction(place, node.getParentNode().getParentNode(),
							processX, nextY, placeX, nextY);
				}
			}
		
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		
		// children
		IXholon childNode = node.getFirstChild();
		while (childNode != null) {
			writeNode(childNode, level+1);
			childNode = childNode.getNextSibling();
		}

	}
	
	/**
	 * Draw catalysis arc.
	 */
	protected void drawArcCatalysis(IXholon target, IXholon source,
			double startX, double startY, double endX, double endY) {
		arcSb.append("<arc target=\"p" + target.getName(nameTemplate)
				+ "\" source=\"" + source.getName(nameTemplate)
				+ "\" class=\"catalysis\">\n");
		arcSb.append(" <start y=\"" + startY + "\" x=\"" + startX + "\"/>\n");
		arcSb.append(" <end y=\"" + endY + "\" x=\"" + endX + "\"/>\n");
		arcSb.append("</arc>\n\n");		
	}
	
	/**
	 * Draw consumption arc.
	 */
	protected void drawArcConsumption(IXholon target, IXholon source,
			double startX, double startY, double endX, double endY) {
		arcSb.append("<arc target=\"" + target.getName(nameTemplate)
				+ ".1\" source=\"" + source.getName(nameTemplate)
				+ "\" class=\"consumption\">\n");
		arcSb.append(" <start y=\"" + startY + "\" x=\"" + startX + "\"/>\n");
		arcSb.append(" <end y=\"" + endY + "\" x=\"" + endX + "\"/>\n");
		arcSb.append("</arc>\n\n");		
	}
	
	/**
	 * Draw production arc.
	 */
	protected void drawArcProduction(IXholon target, IXholon source,
			double startX, double startY, double endX, double endY) {
		arcSb.append("<arc target=\"" + target.getName(nameTemplate)
				+ "\" source=\"" + source.getName(nameTemplate)
				+ ".2\" class=\"production\">\n");
		arcSb.append(" <start y=\"" + startY + "\" x=\"" + startX + "\"/>\n");
		arcSb.append(" <end y=\"" + endY + "\" x=\"" + endX + "\"/>\n");
		arcSb.append("</arc>\n\n");		
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
