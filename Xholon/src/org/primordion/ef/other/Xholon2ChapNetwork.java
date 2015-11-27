/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

import java.util.Date;
import java.util.List;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;
import org.primordion.xholon.service.ef.IXholon2GraphFormat;

/**
 * Export a Xholon model in Chap Network format.
 * This implementation uses JavaScript arrays rather than Google DataTable,
 * so there is NO dependency on the Google Visualization API.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on July 30, 2013)
 * @see <a href="chap.almende.com">Almende CHAP site</a>
 * @see <a href="chap.almende.com/visualization/network/">CHAP Network</a>
 * @see <a href="almende.github.io/chap-links-library/index.html">CHAP doc and examples</a>
 * @see <a href="almende.github.io/chap-links-library/js/network/doc/">CHAP doc</a>
 */
@SuppressWarnings("serial")
public class Xholon2ChapNetwork extends AbstractXholon2ExternalFormat implements IXholon2GraphFormat {
	
	/** StringBuilder initial capacity. */
	protected static final int SB_INITIAL_CAPACITY = 1000; // default is 16
	
	private String outFileName;
	private String outPath = "./ef/chnet/";
	private String modelName;
	private IXholon root;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/**
	 * Start of the CHAP Network script.
	 */
	protected StringBuilder startSb = null;
	
	/**
	 * The accumulating text for all CHAP Network nodes.
	 */
	protected StringBuilder nodeSb = null;
	
	/**
	 * The accumulating text for all CHAP Network links.
	 */
	protected StringBuilder linkSb = null;
	
	/**
	 * CHAP Network options.
	 */
	protected StringBuilder optionsSb = null;
	
	/**
	 * End of the CHAP Network script.
	 */
	protected StringBuilder endSb = null;
	
	protected StringBuilder sb = null;
	
	/**
	 * HTML element ID where Graphical Network View will be displayed.
	 */
	protected String viewElementId = "networkview"; // or "treeview"
	
	/**
	 * Whether or not to show port links between nodes.
	 */
	protected boolean showNetwork = true;
	
	/**
	 * Whether or not to show hierarchical parent-child relationships.
	 */
	protected boolean showTree = false;
	
	//protected String width = "600px";
	
	//protected String height = "600px";
	
	/**
	 * The length of a link.
	 */
	//protected int linksLength = 50; // 100
	
	/**
	 * Whether or not a link should show the name of the port.
	 */
	//protected boolean showPortName = false;
	
	//protected String nodesStyle = "dot"; // rect text image
	
	//protected String stabilize = "false";
	
	/**
	 * Constructor.
	 */
	public Xholon2ChapNetwork() {}
	
	/**
	 * Constructor.
	 */
	public Xholon2ChapNetwork(boolean showNetwork, boolean showTree) {
	  this.showNetwork = showNetwork;
	  this.showTree = showTree;
	}
	
	@Override
	public String getVal_String() {
	  return sb.toString();
	}
	
	@Override
	public boolean initialize(String outFileName, String modelName, IXholon root) {
	  timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (outFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".js";
		}
		else {
			this.outFileName = outFileName;
		}
		this.modelName = modelName;
		this.root = root;
		
		if (showTree) {
		  setLinksLength(100);
		  viewElementId = "treeview";
		  outPath = "./ef/chtree/";
		}
		
		// TODO why are there 2 "var nodesTable" and 2 "var linksTable" ?
		this.startSb = new StringBuilder(128)
		.append("var nodesTable = null;\n")
		.append("var linksTable = null;\n")
		.append("var network = null;\n")
		.append("function draw() {\n");
		
		this.nodeSb = new StringBuilder(SB_INITIAL_CAPACITY)
		.append("// nodes\n")
		.append("var nodesTable = [];\n");
		
		this.linkSb = new StringBuilder(SB_INITIAL_CAPACITY)
		.append("// links\n")
		.append("var linksTable = [];\n");
		
		this.optionsSb = new StringBuilder(SB_INITIAL_CAPACITY)
		.append("// options\n")
		.append("var options = {\n")
		.append("  'width': '" + getWidth() + "',\n")
		.append("  'height': '" + getHeight() + "',\n")
		.append("  'links': {\n")
		.append("    'length': " + getLinksLength() + "\n")
		.append("  },\n")
		.append("  'nodes': {\n")
		.append("    'style': '" + getNodesStyle() + "'\n")
		.append("  },\n")
		.append("  'stabilize': " + getStabilize() + "\n")
		.append("};\n");
		
		this.endSb = new StringBuilder(128)
		.append("network = new links.Network(document.getElementById('")
		.append(viewElementId)
		.append("'));\n")
		.append("network.draw(nodesTable, linksTable, options);\n")
		.append("}\n")
		.append("draw();\n");
		
		if (!isDefinedChapLinksNetwork()) {
      loadChapLinksNetwork();
      return true; // do not return false; that just causes an error message
    }
		
		return true;
	}

	@Override
	public void writeAll() {
	  if (!isDefinedChapLinksNetwork()) {return;}
		writeNode(root);
		sb = new StringBuilder()
		.append("// ")
		.append(modelName)
		.append("\n// see http://almende.github.io/chap-links-library/network.html")
		.append("\n\n")
		.append(startSb.toString())
		.append(nodeSb.toString())
		.append(linkSb.toString())
		.append(optionsSb.toString())
		.append(endSb.toString());
		
		writeToTarget(sb.toString(), outFileName, outPath, root);
		
		if (root.getApp().isUseGwt()) {
			pasteScript("gnvScript", sb.toString());
		}
		else {
			// TODO possibly write to a new browser window; open window and then write data
			//System.out.println(sb.toString());
		}
	}

	@Override
	public void writeNode(IXholon xhNode) {
		// xhNode details
		nodeSb.append("nodesTable.push({'id': ")
		.append(xhNode.getId())
		.append(", 'text': '")
		.append(xhNode.getName())
		.append("'});\n");
		// xhNode outgoing edges
		writeEdges(xhNode);
		// xhNode children
		IXholon childNode = xhNode.getFirstChild();
		while (childNode != null) {
			writeNode(childNode);
			childNode = childNode.getNextSibling();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void writeEdges(IXholon xhNode) {
	  System.out.println("writeEdges");
	  // show network
	  if (showNetwork) {
		  List<PortInformation> portList =
				  new org.primordion.xholon.base.ReflectionJavaMicro().getAllPorts(xhNode, false);
		  for (int i = 0; i < portList.size(); i++) {
			  PortInformation pi = (PortInformation)portList.get(i);
		    linkSb.append("linksTable.push({'from': ")
			  .append(xhNode.getId())
			  .append(", 'to': ")
			  .append(pi.getReffedNode().getId())
			  .append(", 'text': '");
			  if (isShowPortName()) {
			    linkSb.append(pi.getFieldName());
			  }
			  else {
			    linkSb.append("");
			  }
			  linkSb.append("', 'style': 'arrow-end'")
			  .append("});\n");
		  }
		}
		
		// show tree
		if (showTree) {
		  if (xhNode != root) {
		    IXholon pNode = xhNode.getParentNode();
	      linkSb.append("linksTable.push({'from': ")
			  .append(pNode.getId())
			  .append(", 'to': ")
			  .append(xhNode.getId())
			  .append(", 'text': '")
			  .append("")
			  .append("', 'style': undefined")
			  .append("});\n");
		  }
		}
		
	}

	@Override
	public void writeNodeAttributes(IXholon xhNode) {
		// nothing to do for now
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
   * Load CHAP Links Network library asynchronously.
   */
  protected void loadChapLinksNetwork() {
    require(this);
  }
  
  /**
   * use requirejs
   */
  protected native void require(final IXholon2GraphFormat xh2Chap) /*-{
    $wnd.requirejs.config({
      enforceDefine: false,
      paths: {
        network: [
          "xholon/lib/network-min"
        ]
      }
    });
    $wnd.require(["network"], function(network) {
      xh2Chap.@org.primordion.xholon.service.ef.IXholon2GraphFormat::writeAll()();
    });
  }-*/;
  
  /**
   * Is $wnd.links.Network defined.
   * @return it is defined (true), it's not defined (false)
   */
  protected native boolean isDefinedChapLinksNetwork() /*-{
    return (typeof $wnd.links != "undefined") && (typeof $wnd.links.Network != "undefined");
  }-*/;
  
  public boolean isShowNetwork() {
    return this.showNetwork;
  }
  
  public void setShowNetwork(boolean showNetwork) {
    this.showNetwork = showNetwork;
  }
  
  public boolean isShowTree() {
    return this.showTree;
  }
  
  public void setShowTree(boolean showTree) {
    this.showTree = showTree;
  }
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.width = "600px";
    p.height = "600px";
    p.linksLength = 50;
    p.showPortName = false;
    p.nodesStyle = "dot";
    p.stabilize = "false";
    this.efParams = p;
  }-*/;


  public native String getWidth() /*-{return this.efParams.width;}-*/;
  //public native void setWidth(String width) /*-{this.efParams.width = width;}-*/;

  public native String getHeight() /*-{return this.efParams.height;}-*/;
  //public native void setHeight(String height) /*-{this.efParams.height = height;}-*/;

  /**
   * The length of a link.
   * 100
   */
  public native int getLinksLength() /*-{return this.efParams.linksLength;}-*/;
  public native void setLinksLength(int linksLength) /*-{this.efParams.linksLength = linksLength;}-*/;

  /**
   * Whether or not a link should show the name of the port.
   */
  public native boolean isShowPortName() /*-{return this.efParams.showPortName;}-*/;
  //public native void setShowPortName(boolean showPortName) /*-{this.efParams.showPortName = showPortName;}-*/;

  // rect text image
  public native String getNodesStyle() /*-{return this.efParams.nodesStyle;}-*/;
  //public native void setNodesStyle(String nodesStyle) /*-{this.efParams.nodesStyle = nodesStyle;}-*/;

  public native String getStabilize() /*-{return this.efParams.stabilize;}-*/;
  //public native void setStabilize(String stabilize) /*-{this.efParams.stabilize = stabilize;}-*/;
  
}
