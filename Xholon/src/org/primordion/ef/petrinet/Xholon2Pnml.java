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
import java.util.Map;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.mech.petrinet.Arc;
import org.primordion.xholon.mech.petrinet.Transition;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon model in Petri Net Markup Language (PNML) XML format.
 * The resulting file can be loaded and animated by Platform Independent Petri net Editor 2 (PIPE).
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 25, 2012)
 * @see <a href="http://www.pnml.org/">PNML website</a>
 * @see <a href="http://pipe2.sourceforge.net/index.html">PIPE website</a>
 */
@SuppressWarnings("serial")
public class Xholon2Pnml extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
	
	private String outFileName;
	private String outPath = "./ef/pnml/";
	private String modelName;
	private IXholon root;
	//private Writer out;
	private StringBuilder sb;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/** Template to use when writing out node names. */
	protected String nameTemplate = "r:C^^^";

	// default x and y coordinates for Place and Transition
	/*private double placeX = 200.0;
	private double transitionX = 700.0;
	private double nextY = 10.0;
	private double yInc = 50.0;*/
	private int placeX = 200;
	private int transitionX = 700;
	private int nextY = 10;
	private int yInc = 50;
	
	// PIPE uses various node types that are non-standard according to PNML Document Checker
	// see pnml.lip6.fr/pnmlvalidation
	// by default, the settings are those that work with PIPE
	private boolean usePnmlXmlns = false; // PNML Document Checker requires this; PIPE fails with this
	private boolean useNetTypeUrl = false; // PNML Document Checker requires this
	private boolean usePage = false; // PNML Document Checker requires this
	private boolean useToken = true; // <token> PIPE requires this
	private boolean useCapacity = false; // <capacity> PIPE option
	private boolean useOrientation = false; // <orientation> PIPE option
	private boolean useTimed = false; // <timed> PIPE option
	private boolean useTagged = false; // <tagged> PIPE option
	private boolean useType = false; // <type> PIPE option
	// other problems found by PNML Document Checker, that can/must be used with PIPE
	//  <rate> is invalid
	//  <value> is invalid; use <text> instead
	//  "Default,1" is invalid; use "1" instead
	//  spaces in id are invalid; use "_to_" instead of " to "
	private boolean useRate = true; // <rate> useful PIPE option
	private static final String VALUE_VALUE = "value";
	private static final String VALUE_TEXT = "text";
	private String valueStart = "<" + VALUE_VALUE + ">";
	private String valueEnd = "</" + VALUE_VALUE + ">";
	private boolean useValueValue = true;
	private String defaultStr = "Default,";
	private boolean useDefaultStr = true;
	private boolean allowSpacesInId = true;
	private char spaceChar = ' '; // space char is space by default
	private String pnmlXmlns = "";
	
	// required by VANTED Petri net add-on
	private boolean useDimension = false;
	
	private boolean usePipeSettings = true;
	private boolean useDocCheckSettings = false;
	private boolean useVantedSettings = false;
	
	/**
	 * For use with BlackboardService.
	 */
	public static final String PNML_USE_TOOL_SETTINGS = "PnmlUseToolSettings";
	
	/**
	 * Constructor.
	 */
	public Xholon2Pnml() {}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	@SuppressWarnings("unchecked")
	public boolean initialize(String mmFileName, String modelName, IXholon root) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (mmFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".xml";
		}
		else {
			this.outFileName = mmFileName;
		}
		this.modelName = modelName;
		this.root = root;
		
		// check with the BlackboardService to see if settings have been requested
		Map<String,String> blackboardService =
			(Map<String,String>)root.getService(IXholonService.XHSRV_BLACKBOARD);
		String uts = blackboardService.get(PNML_USE_TOOL_SETTINGS);
		if ((uts != null) && (uts.length() > 0)) {
			usePipeSettings = false;
			useDocCheckSettings = false;
			useVantedSettings = false;
			if ("useDocCheckSettings".equals(uts)) {useDocCheckSettings = true;}
			else if ("useVantedSettings".equals(uts)) {useVantedSettings = true;}
			else {usePipeSettings = true;}
		}
		
		if (usePipeSettings) {}
		else if (useDocCheckSettings) {
			usePnmlXmlns = true;
			useNetTypeUrl = true;
			usePage = true;
			useToken = false;
			useCapacity = false;
			useOrientation = false;
			useTimed = false;
			useTagged = false;
			useType = false;
			useRate = false;
			useValueValue = false;
			useDefaultStr = false;
			allowSpacesInId = false;
			pnmlXmlns = "http://www.pnml.org/version-2009/grammar/pnml";
		}
		else if (useVantedSettings) {
			usePnmlXmlns = true;
			//useNetTypeUrl = true;
			useToken = false;
			useCapacity = false;
			useOrientation = false;
			useTimed = false;
			useTagged = false;
			useType = false;
			useRate = false;
			useValueValue = false;
			useDefaultStr = false;
			allowSpacesInId = false;
			useDimension = true;
			pnmlXmlns = "http://www.informatik.hu-berlin.de/top/pnml/ptNetb";
		}
		if (!allowSpacesInId) {
			spaceChar = '_';
		}
		if (!useValueValue) {
			valueStart = "<" + VALUE_TEXT + ">";
			valueEnd = "</" + VALUE_TEXT + ">";
		}
		if (!useDefaultStr) {
			defaultStr = "";
		}
		return true;
	}
	
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
		//try {
			sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
			
			if (usePipeSettings) {
				sb.append("<!-- To view this file, download open-source PIPE from http://pipe2.sourceforge.net/ -->\n");
				sb.append("<!--\nAutomatically generated by Xholon version 0.8.1, using Xholon2Pnml.java\n");
			}
			else if (useDocCheckSettings) {
				sb.append("<!-- To view this file, download PNML Document Checker from http://pnml.lip6.fr/pnmlvalidation/ -->\n");
				sb.append("<!--\nAutomatically generated by Xholon version 0.8.1, using Xholon2PnmlDocCheck.java\n");
			}
			else if (useVantedSettings) {
				sb.append("<!-- To view this file, download open-source Vanted from http://vanted.ipk-gatersleben.de/ -->\n");
				sb.append("<!--\nAutomatically generated by Xholon version 0.8.1, using Xholon2PnmlVanted.java\n");
			}
			sb.append(new Date() + " " + timeStamp + "\n"
				+ "model: " + modelName + "\n"
				+ "www.primordion.com/Xholon\n-->\n");
			if (usePnmlXmlns) {
				sb.append("<pnml xmlns=\"" + pnmlXmlns + "\">\n");
			}
			else {
				sb.append("<pnml>\n");
			}
			if (useNetTypeUrl) {
				sb.append("<net id=\"Net-One\" type=\"http://www.pnml.org/version-2009/grammar/ptnet\">\n");
			}
			else {
				sb.append("<net id=\"Net-One\" type=\"P/T net\">\n");
			}
			if (useToken) {
				sb.append("<token id=\"Default\" enabled=\"true\" red=\"0\" green=\"0\" blue=\"0\"/>\n\n");
			}
			if (usePage) {
				sb.append("<page id=\"page01\">");
			}
			writeNode(root, 0); // root is level 0
			if (usePage) {
				sb.append("</page>");
			}
			sb.append("</net>\n");
			sb.append("</pnml>\n");
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
				String namePrefix = makeNamePrefix(node);
				String xhcOrRoleName = makeXhcOrRoleName(node);
				sb.append("<place id=\"" + namePrefix + xhcOrRoleName + "\">\n");
				sb.append(" <graphics>\n");
				sb.append("  <position x=\"" + placeX + "\" y=\"" + nextY + "\"/>\n");
				if (useDimension) {
					sb.append("  <dimension x=\"25\" y=\"25\"/>\n");
				}
				sb.append(" </graphics>\n");
				nextY += yInc;
				sb.append(" <name>\n");
				sb.append("  " + valueStart + namePrefix + xhcOrRoleName + valueEnd + "\n");
				sb.append("  <graphics>\n");
				sb.append("   <offset x=\"0\" y=\"0\"/>\n");
				sb.append("  </graphics>\n");
				sb.append(" </name>\n");
				sb.append(" <initialMarking>\n");
				sb.append("  " + valueStart + defaultStr + (int)node.getVal() + valueEnd + "\n");
				sb.append(" </initialMarking>\n");
				if (useCapacity) {
					sb.append(" <capacity>\n");
					sb.append("  " + valueStart + "0" + valueEnd + "\n");
					sb.append(" </capacity>\n");
				}
				sb.append("</place>\n\n");
			}
			
			else if (node.getXhc().hasAncestor("TransitionPN")) {
				String namePrefix = makeNamePrefix(node);
				String xhcOrRoleName = makeXhcOrRoleName(node);
				sb.append("<transition id=\"" + namePrefix + xhcOrRoleName + "\">\n");
				sb.append(" <graphics>\n");
				sb.append("  <position x=\"" + transitionX + "\" y=\"" + nextY + "\"/>\n");
				if (useDimension) {
					sb.append("  <dimension x=\"25\" y=\"25\"/>\n");
				}
				sb.append(" </graphics>\n");
				nextY += yInc;
				sb.append(" <name>\n");
				sb.append("  " + valueStart + namePrefix + xhcOrRoleName + valueEnd + "\n");
				sb.append("  <graphics>\n");
				sb.append("   <offset x=\"0\" y=\"0\"/>\n");
				sb.append("  </graphics>\n");
				sb.append(" </name>\n");
				if (useOrientation) {
					sb.append(" <orientation>\n");
					sb.append("  " + valueStart + "0" + valueEnd + "\n");
					sb.append(" </orientation>\n");
				}
				if (useRate) {
					sb.append(" <rate>\n");
					sb.append("  " + valueStart + ((Transition)node).getP() + valueEnd + "\n");
					sb.append(" </rate>\n");
				}
				if (useTimed) {
					sb.append(" <timed>\n");
					sb.append("  " + valueStart + "false" + valueEnd + "\n");
					sb.append(" </timed>\n");
				}
				sb.append("</transition>\n\n");
			}
			
			else if ("InputArc".equals(node.getXhcName())) {
				String source = makeNamePrefix(((Arc)node).getPlace())
					+ makeXhcOrRoleName(((Arc)node).getPlace());
				String target = makeNamePrefix(node.getParentNode().getParentNode())
					+ makeXhcOrRoleName(node.getParentNode().getParentNode());
				sb.append("<arc id=\"" + source + spaceChar + "to" + spaceChar + target
						+ "\" source=\"" + source
						+ "\" target=\"" + target
						+ "\">\n");
				sb.append(" <inscription>\n");
				sb.append("  " + valueStart + defaultStr + (int)node.getVal() + valueEnd + "\n");
				sb.append(" </inscription>\n");
				if (useTagged) {
					sb.append(" <tagged>\n");
					sb.append("  " + valueStart + "false" + valueEnd + "\n");
					sb.append(" </tagged>\n");
				}
				if (useType) {
					sb.append(" <type value=\"normal\"/>\n");
				}
				sb.append("</arc>\n\n");
			}
			
			else if ("OutputArc".equals(node.getXhcName())) {
				String source = makeNamePrefix(node.getParentNode().getParentNode())
					+ makeXhcOrRoleName(node.getParentNode().getParentNode());
				String target = makeNamePrefix(((Arc)node).getPlace())
					+ makeXhcOrRoleName(((Arc)node).getPlace());
				sb.append("<arc id=\"" + source + spaceChar + "to" + spaceChar + target
						+ "\" source=\"" + source
						+ "\" target=\"" + target
						+ "\">\n");
				sb.append(" <inscription>\n");
				sb.append("  " + valueStart + defaultStr + (int)node.getVal() + valueEnd + "\n");
				sb.append(" </inscription>\n");
				if (useTagged) {
					sb.append(" <tagged>\n");
					sb.append("  " + valueStart + "false" + valueEnd + "\n");
					sb.append(" </tagged>\n");
				}
				if (useType) {
					sb.append(" <type value=\"normal\"/>\n");
				}
				sb.append("</arc>\n\n");
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

	public boolean isUsePipeSettings() {
		return usePipeSettings;
	}

	public void setUsePipeSettings(boolean usePipeSettings) {
		this.usePipeSettings = usePipeSettings;
	}

	public boolean isUseDocCheckSettings() {
		return useDocCheckSettings;
	}

	public void setUseDocCheckSettings(boolean useDocCheckSettings) {
		this.useDocCheckSettings = useDocCheckSettings;
	}

	public boolean isUseVantedSettings() {
		return useVantedSettings;
	}

	public void setUseVantedSettings(boolean useVantedSettings) {
		this.useVantedSettings = useVantedSettings;
	}

}
