/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2010 Ken Webb
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

package org.primordion.ef.other;

/*import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.security.AccessControlException;
import java.text.ParseException;*/
import java.util.Date;

/*import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;*/

import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon model in Google TreeMap format.
 * 
 * TODO dynamically load the Google Visualization API.
 * "https://www.google.com/jsapi?autoload={\"modules\":[{\"name\":\"visualization\",\"version\":\"1.0\",\"packages\":[\"corechart\",\"treemap\"]}]}"
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on February 12, 2010)
 */
@SuppressWarnings("serial")
public class Xholon2GoogleTreeMap extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
	
	private String outFileName;
	private String outPath = "./ef/treemap/";
	private String modelName;
	private IXholon root;
	//private Writer out;
	private StringBuilder sb;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/** Whether or not to show state machine nodes. */
	private boolean shouldShowStateMachineEntities = false;
	
	/** Template to use when writing out node names. */
	//protected String nameTemplate = "R^^_i^"; //"r:C^^^";

	/** Whether or not to include a &lt;declarations element at the top. */
	private boolean shouldShowDeclarations = true;
	
	/** Whether or not to run the separate TreeMap application to display the treemap. */
	//private boolean shouldRunTreeMap = true;
	
	/** Maximum number of children of any node in the subtree. */
	private int maxChildren = 0;
	
	/** color value is the ID of the node's XholonClass */
	public final static int COLOR_ALGORITHM_XHCID = 0;
	
	/** color value is based roughly on the node's position in the tree, relative to immediate neighbors */
	public final static int COLOR_ALGORITHM_POS   = 1;
	
	/** Algorithm to use in calculating a color value for a node. */
	private int colorAlgorithm = COLOR_ALGORITHM_XHCID;
	
	/** size value is 1 */
	public final static int SIZE_ALGORITHM_ONE = 1;
	
	/** size value is the node's val */
	public final static int SIZE_ALGORITHM_VAL = 2;
	
	private int sizeAlgorithm = SIZE_ALGORITHM_VAL;
	
	//private int maxDepth = 12; // 5
	
	/**
	 * Constructor.
	 */
	public Xholon2GoogleTreeMap() {
	  if (!isGoogleVisLoaded()) {
	    // some callback is required by Google Vis even though it's only   ,\"callback\":\"console.log\"
	    HtmlScriptHelper.fromUrl("https://www.google.com/jsapi?autoload={\"modules\":[{\"name\":\"visualization\",\"version\":\"1.0\",\"packages\":[\"corechart\",\"treemap\"],\"callback\":\"console.log\"}]}", false);
	  }
	}
	
	@Override
	public String getVal_String() {
	  return sb.toString();
	}
	
	/**
	 * Is the google visualization library already loaded?
	 */
	protected native boolean isGoogleVisLoaded() /*-{
	  if (($wnd.google !== undefined) && ($wnd.google.visualization !== undefined)) {
	    return true;
	  }
	  return false;
	}-*/;
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
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
		root.visit(this); // find maxChildren
		//System.out.println("maxChildren:" + maxChildren);
		return true;
	}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
	 */
	public void writeAll() {
		/*boolean shouldClose = true;
		if (root.getApp().isUseAppOut()) {
			out = root.getApp().getOut();
			sb = new StringBuilder(1000);
			shouldClose = false;
		}
		else {
			try {
				// create any missing output directories
				File dirOut = new File(outPath);
				dirOut.mkdirs(); // will create a new directory only if there is no existing one
				out = MiscIo.openOutputFile(outFileName);
				sb = new StringBuilder(1000);
			} catch(AccessControlException e) {
				//out = new PrintWriter(System.out);
				out = root.getApp().getOut();
				sb = new StringBuilder(1000);
				shouldClose = false;
			}
		}*/
		sb = new StringBuilder(1000);
		//try {
			sb.append(
				"// Automatically generated by Xholon version 0.8.1, using Xholon2GoogleTreeMap.java\n"
				+ "// " + new Date() + " " + timeStamp + "\n"
				+ "// model: " + modelName + "\n"
				+ "// www.primordion.com/Xholon\n\n")
		  .append("function drawChart() {\n")
			.append("  // Create and populate the data table.\n")
			.append("  var data = google.visualization.arrayToDataTable([\n")
			.append("    ['node name', 'parent name', 'value (size)', 'value (color)'],\n");
			writeNode(root, 0, null); // root is level 0
			sb.append("  ]);\n\n")
			.append("  // Create and draw the visualization.\n")
			.append("  var tree = new google.visualization.TreeMap(document.getElementById('xhtreemap'));\n")
			.append("  tree.draw(data, {\n");
			sb
			.append("    maxDepth: '").append(getMaxDepth()).append("',\n")
			.append("    maxPostDepth: '1',\n")
			//.append("    minColor: '#f00',\n")
			//.append("    midColor: '#ddd',\n")
			//.append("    maxColor: '#0d0',\n")
			//.append("    headerHeight: 15,\n")
			//.append("    fontColor: 'black',\n")
			//.append("    showScale: true\n")
			;
			sb.append("  });\n")
			.append("}\n")
			.append("drawChart();");
			//out.write(sb.toString());
			//out.flush();
			writeToTarget(sb.toString(), outFileName, outPath, root);
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		//if (shouldClose) {
		//	MiscIo.closeOutputFile(out);
			if (isShouldRunTreeMap()) {
				runTreeMap();
			}
		//}
		
	}
	
	/**
	 * Write one node, and its child nodes.
	 * Example data lines:
	 ['node name', 'parent name', 'value (size)', 'value (color)'],
	 ['Global',    null,                 0,                               0],
   ['America',   'Global',             0,                               0],
   ['USA',       'America',            52,                              31],
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeNode(IXholon node, int level, String parentName) {
		// only show state machine nodes if should show them, or if root is a StateMachineCE
		if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
				&& (shouldShowStateMachineEntities == false)
				&& (level > 0)) {
			return;
		}
		String nodeName = node.getName(getNameTemplate());
		int sizeValue;
		if (node.hasChildNodes()) {
			sizeValue = 0;
		}
		else {
		  sizeValue = findSizeValue(node);
		}
		double colorValue = findColorValue(node);
		
		sb
		.append("    [")
		.append("'").append(nodeName).append("', ");
		if (parentName == null) {
		  sb.append((String)null).append(", ");
		}
		else {
		  sb.append("'").append(parentName).append("', ");
		}
		sb.append(sizeValue).append(", ")
		.append(colorValue).append("],\n");
		
		// children
		IXholon childNode = node.getFirstChild();
		while (childNode != null) {
			writeNode(childNode, level+1, nodeName);
			childNode = childNode.getNextSibling();
		}
	}
	
	/**
	 * Find a reasonable color value for a node.
	 * @param node
	 * @return
	 */
	protected double findColorValue(IXholon node) {
	  double colorValue = 1.0;
	  switch(colorAlgorithm) {
	  case COLOR_ALGORITHM_XHCID:
	    int xhcId = node.getXhcId();
	    if (xhcId >= IMechanism.MECHANISM_ID_START) {
	      xhcId -= IMechanism.MECHANISM_ID_START;
	    }
	    colorValue = xhcId;
	    break;
	  case COLOR_ALGORITHM_POS:
	    colorValue = maxChildren/node.getParentNode().getNumChildren(false)
					* node.getSelfAndSiblingsIndex(false)
					- (maxChildren/2.0);
	    break;
	  default:
	    break;
		}
		return colorValue;
	}
	
	/**
	 * Find a reasonable size value for a node.
	 * @param node
	 * @return
	 */
	protected int findSizeValue(IXholon node) {
	  int sizeValue = 1;
	  switch(sizeAlgorithm) {
	  case SIZE_ALGORITHM_ONE:
	    sizeValue = 1;
	    break;
	  case SIZE_ALGORITHM_VAL:
	    sizeValue = (int)node.getVal();
	    if (sizeValue < 1) {
	      sizeValue = 1;
	    }
	    break;
	  default:
	    break;
		}
		return sizeValue;
	}
	
	/*protected void writeAttributeNodes(IXholon node, int level) {
		//try {
			sb.append("<label>" + node.getName(nameTemplate) + "</label>\n");
			if (!node.hasChildNodes()) { // leaf
				sb.append("<weight>" + (node.getAllPorts().size() + 1) + "</weight>\n");
				double value = maxChildren/node.getParentNode().getNumChildren(false)
					* node.getSelfAndSiblingsIndex(false)
					- (maxChildren/2.0);
				sb.append("<value>" + value + "</value>\n");
			}
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}*/
	
	public boolean visit(IXholon visitee) {
		int numChildren = visitee.getNumChildren(false);
		if (numChildren > maxChildren) {
			maxChildren = numChildren;
		}
		return true;
	}
	
	/**
	 * Run the TreeMap software to display and interact with the Xholon-generated file.
	 */
	protected void runTreeMap() {
		/*try {
			net.sf.jtreemap.swing.example.BuilderXML builderXml =
				new net.sf.jtreemap.swing.example.BuilderXML(outFileName);
			net.sf.jtreemap.swing.TreeMapNode treeMapRoot = builderXml.getRoot();
			display(treeMapRoot);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		if (root.getApp().isUseGwt()) {
			pasteScript("treemapScript", sb.toString());
		}
	}
	
	protected void pasteScript(String scriptId, String scriptContent) {
	  HtmlScriptHelper.fromString(scriptContent, true);
	}
	
	//protected native void pasteScript(String scriptId, String scriptContent)
	/*-{
	  // add script
    var script = $doc.createElement('script');
    script.setAttribute('id', scriptId);
    script.setAttribute('type', 'text/javascript');
    try {
      // fails with IE
      script.appendChild(document.createTextNode(scriptContent));      
    } catch(e) {
      script.text = scriptContent;
    }
    
    $doc.getElementsByTagName('head')[0].appendChild(script);
	}-*/
	//;
	
	/**
	 * Display a generated tree map.
	 * This code is adapted from: http://jtreemap.sourceforge.net/
	 * @param treeMapRoot
	 */
	/*protected void display(net.sf.jtreemap.swing.TreeMapNode treeMapRoot) {
		//
		// Build the JTreeMap
		//
		final net.sf.jtreemap.swing.JTreeMap jTreeMap =
			new net.sf.jtreemap.swing.JTreeMap(treeMapRoot);
		jTreeMap.setFont(new Font(null, Font.BOLD, 10));
		jTreeMap.setPreferredSize(new Dimension(600, 400));
		jTreeMap.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		// add a popup menu to zoom the JTreeMap
		new net.sf.jtreemap.swing.provider.ZoomPopupMenu(jTreeMap);

		final net.sf.jtreemap.swing.provider.HSBTreeMapColorProvider provider =
			new net.sf.jtreemap.swing.provider.HSBTreeMapColorProvider(jTreeMap,
					net.sf.jtreemap.swing.provider.HSBTreeMapColorProvider.ColorDistributionTypes.Log, Color.GREEN, Color.RED);
		jTreeMap.setColorProvider(provider);

		//
		// put in a tree view with a JTree on the left and a JTreeMap on the right
		//
		JPanel view = new JPanel();

		JSplitPane splitPaneCenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		view.add(splitPaneCenter, BorderLayout.CENTER);

		JScrollPane jScrollPane1 = new JScrollPane();
		splitPaneCenter.setLeftComponent(jScrollPane1);
		splitPaneCenter.setRightComponent(jTreeMap);

		DefaultTreeModel treeModel = new DefaultTreeModel(treeMapRoot);
		final JTree treeView = new JTree(treeModel);
		jScrollPane1.getViewport().add(treeView);
		jScrollPane1.setPreferredSize(new Dimension(140, jTreeMap.getRoot().getHeight()));
		treeView.addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
			// for each selected elements ont the treeView, we zoom the JTreeMap
		    	net.sf.jtreemap.swing.TreeMapNode dest =
		    		(net.sf.jtreemap.swing.TreeMapNode) treeView.getLastSelectedPathComponent();

			// if the element is a leaf, we select the parent
			if (dest != null && dest.isLeaf()) {
			    dest = (net.sf.jtreemap.swing.TreeMapNode) dest.getParent();
			}
			if (dest == null) {
			    return;
			}

			jTreeMap.zoom(dest);
			jTreeMap.repaint();
		    }
		});
		
		final JFrame treeMapFrame = new JFrame("title");
		treeMapFrame.getContentPane().add(view);
		treeMapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//aboutFrame.setSize(width+10, height+10);
		treeMapFrame.pack();
		treeMapFrame.setVisible(true);
	}*/
	
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

	public boolean isShouldShowStateMachineEntities() {
		return shouldShowStateMachineEntities;
	}

	public void setShouldShowStateMachineEntities(
			boolean shouldShowStateMachineEntities) {
		this.shouldShowStateMachineEntities = shouldShowStateMachineEntities;
	}
	
	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

	public boolean isShouldShowDeclarations() {
		return shouldShowDeclarations;
	}

	public void setShouldShowDeclarations(boolean shouldShowDeclarations) {
		this.shouldShowDeclarations = shouldShowDeclarations;
	}

	public int getColorAlgorithm() {
	  return colorAlgorithm;
	}
	
	public void setColorAlgorithm(int colorAlgorithm) {
	  this.colorAlgorithm = colorAlgorithm;
	}
	
	public int getSizeAlgorithm() {
	  return sizeAlgorithm;
	}
	
	public void setSizeAlgorithm(int sizeAlgorithm) {
	  this.sizeAlgorithm = sizeAlgorithm;
	}
	
	/**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.nameTemplate = "R^^_i^";
    p.shouldRunTreeMap = true;
    p.maxDepth = 12;
    this.efParams = p;
  }-*/;


  /** Template to use when writing out node names. "r:C^^^" */
  public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  //public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;

  /** Whether or not to run the separate TreeMap application to display the treemap. */
  public native boolean isShouldRunTreeMap() /*-{return this.efParams.shouldRunTreeMap;}-*/;
  //public native void setShouldRunTreeMap(boolean shouldRunTreeMap) /*-{this.efParams.shouldRunTreeMap = shouldRunTreeMap;}-*/;

  public native int getMaxDepth() /*-{return this.efParams.maxDepth;}-*/;
  //public native void setMaxDepth(int maxDepth) /*-{this.efParams.maxDepth = maxDepth;}-*/;
	
}
