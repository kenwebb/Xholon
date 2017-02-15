/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2017 Ken Webb
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

package org.primordion.ef.program;

import java.util.Date;
import java.util.List;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model in igraph R format.
 * "igraph is a collection of network analysis tools with the emphasis on efficiency, portability and ease of use. igraph is open source and free. igraph can be programmed in R, Python and C/C++."
 * "R is a free software environment for statistical computing and graphics."
 * 
 * <pre>
<XingCytoplasmbehavior implName="org.primordion.xholon.base.Behavior_gwtjs">
var me, beh = {
  postConfigure: function() {
    me = this.cnode.parent();
    this.cnode.remove();
    var node = me.first();
    me.print("megraph &lt;- graph_from_literal(");
    while (node != null) {
      me.print(node.xhc().name());
      var remote = node.port(0);
      if (remote) {
        me.print("-+" + remote.xhc().name() + ",");
      }
      node = node.next();
    }
    me.print(")\nplot(megraph)");
  }
}
</XingCytoplasmbehavior>
 * </pre>
 * 
 * <pre>
megraph <- graph_from_literal(CytosolHexokinase-+Glucose,PhosphoGlucoIsomerase-+Glucose_6_Phosphate,PhosphoFructokinase-+Fructose_6_Phosphate,Aldolase-+Fructose_1x6_Biphosphate,TriosePhosphateIsomerase-+DihydroxyacetonePhosphate,Glyceraldehyde_3_phosphateDehydrogenase-+Glyceraldehyde_3_Phosphate,PhosphoGlycerokinase-+X1x3_BisphosphoGlycerate,PhosphoGlyceromutase-+X3_PhosphoGlycerate,Enolase-+X2_PhosphoGlycerate,PyruvateKinase-+PhosphoEnolPyruvate,)
plot(megraph)
 * </pre>
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on February 14, 2017)
 * @see <a href="https://www.r-project.org/">R website</a>
 * @see <a href="http://igraph.org/">igraph website</a>
 * @see <a href="http://igraph.org/r/doc/graph_from_literal.html">igraph graph_from_literal</a>
 * @see <a href="http://127.0.0.1:8888/wb/editwb.html?app=1747846c4df9299c34d66c42010141b1&src=gist">Xholon workbook example</a>
 */
@SuppressWarnings("serial")
public class Xholon2IgraphR extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  private static final String EDGE_OPERATOR_UNDIRECTED = "-";
  private static final String EDGE_OPERATOR_DIRECTED   = "-+";
  
  private String outFileName;
  private String outPath = "./ef/igraphR/";
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** Whether or not to show state machine nodes. */
  private boolean shouldShowStateMachineEntities = false;
  
  /** Whether or not labels/names should be quoted. */
  private boolean shouldQuoteLabels = false;
  
  /** Whether or not to format the output by inserting new lines. */
  private boolean shouldInsertNewlines = true;

  /**
   * Constructor.
   */
  public Xholon2IgraphR() {}
  
  @Override
  public String getVal_String() {
    return sb.toString();
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String mmFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (mmFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".r";
    }
    else {
      this.outFileName = mmFileName;
    }
    this.modelName = modelName;
    this.root = root;
    return true;
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
   */
  public void writeAll() {
    sb = new StringBuilder();
    sb
    .append("library(igraph)\n")
    .append("megraph <- graph_from_literal(");
    if (shouldInsertNewlines) {
      sb.append("\n");
    }
    writeNode(root, 0); // root is level 0
    sb.append(")\nplot(megraph)\n");
    writeToTarget(sb.toString(), outFileName, outPath, root);
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   */
  protected void writeNode(IXholon node, int level) {
    // only show state machine nodes if should show them, or if root is a StateMachineCE
    if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
        && (shouldShowStateMachineEntities == false)
        && (level > 0)) {
      return;
    }
    //String nodeName = makeName(node);
    //sb.append(nodeName);
    writeLinks(node);
    if (node.hasChildNodes()) {
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode, level+1);
        childNode = childNode.getNextSibling();
        if (childNode != null) {
          //sb.append(",");
          if (shouldInsertNewlines) {
            //sb.append("\n");
          }
        }
      }
      //sb.append(",");
    }
  }
  
  /**
   * Write links from this node to any others, where Xholon has connected ports.
   * @param node The current node.
   */
  @SuppressWarnings("unchecked")
  protected void writeLinks(IXholon node) {
    //if (isShouldShowLinks() == false) {return;}
    //int nodeNum = 0;
    //List<PortInformation> portList = node.getAllPorts();
    List<PortInformation> portList = node.getLinks(false, true);
    for (int i = 0; i < portList.size(); i++) {
      writeLink(node, (PortInformation)portList.get(i), false);
      //nodeNum++;
    }
  }
  
  /**
   * Write one link.
   * @param node The node where the link originates.
   * @param portInfo Information about the port that represents the link.
   * @param reverseArrows Whether or not to reverse the direction of the arrows.
   */
  protected void writeLink(IXholon node, PortInformation portInfo, boolean reverseArrows) {
    if (portInfo == null) {return;}
    IXholon remoteNode = portInfo.getReffedNode();
    if (remoteNode != null) {
      String nodeName = makeName(node);
      String remoteNodeName = makeName(remoteNode);
      sb.append(nodeName + EDGE_OPERATOR_DIRECTED + remoteNodeName + ",");
      if (shouldInsertNewlines) {
        sb.append("\n");
      }
    }
  }
  
  protected String makeName(IXholon node) {
    String nodeName = node.getName(getNameTemplate());
    nodeName = nodeName.replace(" ", "_");
    return nodeName;
  }
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.nameTemplate = "R^^_i^"; //"r:c^^^";
    
    this.efParams = p;
  }-*/;

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

  public void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities) {
    this.shouldShowStateMachineEntities = shouldShowStateMachineEntities;
  }
  
  public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;

  public String getOutPath() {
    return outPath;
  }

  public void setOutPath(String outPath) {
    this.outPath = outPath;
  }

  public boolean isShouldQuoteLabels() {
    return shouldQuoteLabels;
  }

  public void setShouldQuoteLabels(boolean shouldQuoteLabels) {
    this.shouldQuoteLabels = shouldQuoteLabels;
  }

}
