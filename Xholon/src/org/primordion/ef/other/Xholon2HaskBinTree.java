/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2020 Ken Webb
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

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model in a Haskell Binary Tree format.
 * John Whitington develops the Binary Tree format in his book "Haskell from the Very Beginning".
 * see haskell code at: Xholon/script/haskell/bt.hs
 * 
 * Examples:
<pre>
Br "HelloWorldSystem" (Br "Hello" Lf (Br "World" Lf Lf)) Lf

Br "ExtraCellularSpace" (Br "ExtraCellularSolution" (Br "Glucose" Lf Lf) (Br "EukaryoticCell" (Br "CellMembrane" (Br "CellBilayer" Lf Lf) (Br "Cytoplasm" (Br "Cytosol" (Br "Glucose" Lf (Br "Glucose_6_Phosphate" Lf (Br "Fructose_6_Phosphate" Lf (Br "Fructose_1x6_Biphosphate" Lf (Br "DihydroxyacetonePhosphate" Lf (Br "Glyceraldehyde_3_Phosphate" Lf (Br "X1x3_BisphosphoGlycerate" Lf (Br "X3_PhosphoGlycerate" Lf (Br "X2_PhosphoGlycerate" Lf (Br "PhosphoEnolPyruvate" Lf (Br "Pyruvate" Lf Lf))))))))))) (Br "Hexokinase" Lf (Br "PhosphoGlucoIsomerase" Lf (Br "PhosphoFructokinase" Lf (Br "Aldolase" Lf (Br "TriosePhosphateIsomerase" Lf (Br "Glyceraldehyde_3_phosphateDehydrogenase" Lf (Br "PhosphoGlycerokinase" Lf (Br "PhosphoGlyceromutase" Lf (Br "Enolase" Lf (Br "PyruvateKinase" Lf Lf))))))))))) Lf)) Lf)) Lf

</pre>
 *
 * Example use with Haskell ghci:
<pre>
:l bt
let tree1 = Br "HelloWorldSystem" (Br "Hello" Lf (Br "World" Lf Lf)) Lf
treeSize tree1 -- 3
listOfTree tree1 -- ["HelloWorldSystem","Hello","World"]
</pre>

See also:
http://learnyouahaskell.com/making-our-own-types-and-typeclasses#recursive-data-structures
An example binary tree from this book is:
Node 5 (Node 3 (Node 1 EmptyTree EmptyTree) (Node 4 EmptyTree EmptyTree)) (Node 7 (Node 6 EmptyTree EmptyTree) (Node 8 EmptyTree EmptyTree))
So instead of using "Br" and "Lf", it uses "Node" and "EmptyTree"

See also:
https://github.com/oisdk/binary-tree
https://hackage.haskell.org/package/binary-tree
it uses "Node" and "Leaf"
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on Frbruary 1, 2020)
 * @see <a href="http://en.wikipedia.org/wiki/S-expression">S-expression format</a>
 * @see <a href="https://www.haskellfromtheverybeginning.com/">Haskell from the Very Beginning, by John Whitington</a>
 */
@SuppressWarnings("serial")
public class Xholon2HaskBinTree extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  private static final String BT_BRANCH = "Br"; // binary tree Branch node
  private static final String BT_LEAF   = "Lf"; // binary tree Leaf node
  
  private String outFileName;
  private String outPath = "./ef/haskbt/";
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** Whether or not to show state machine nodes. */
  private boolean shouldShowStateMachineEntities = false;
  
  /** Template to use when writing out node names. */
  //protected String nameTemplate = "r:C^^^";
  //protected String nameTemplate = "^^C^^^"; // don't include role name
  protected String nameTemplate = "R^^^^^";
  
  /**
   * Constructor.
   */
  public Xholon2HaskBinTree() {}
  
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
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".hs";
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
    sb.append(BT_BRANCH).append(" ");
    writeNode(root);
    sb.append("\n");
    writeToTarget(sb.toString(), outFileName, outPath, root);
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon hierarchy.
   * ex: Br "HelloWorldSystem" (Br "Hello" Lf (Br "World" Lf Lf)) Lf
   */
  protected void writeNode(IXholon node) {
    // only show state machine nodes if should show them, or if root is a StateMachineCE
    if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
        && (shouldShowStateMachineEntities == false)
        && (node != root)) {
      return;
    }
    String nodeName = node.getName(nameTemplate);
    sb.append("\"")
    .append(nodeName)
    .append("\"");
    
    if (node.getFirstChild() != null) {
      sb
      .append(" (")
      .append(BT_BRANCH)
      .append(" ");
      writeNode(node.getFirstChild());
      sb.append(")");
    }
    else {
      sb
      .append(" ")
      .append(BT_LEAF);
    }
    
    // ignore any sibling of the root node
    if ((node.getNextSibling() != null) && (node != root)) {
      sb
      .append(" (")
      .append(BT_BRANCH)
      .append(" ");
      writeNode(node.getNextSibling());
      sb.append(")");
    }
    else {
      sb
      .append(" ")
      .append(BT_LEAF);
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

  public boolean isShouldShowStateMachineEntities() {
    return shouldShowStateMachineEntities;
  }

  public void setShouldShowStateMachineEntities(
      boolean shouldShowStateMachineEntities) {
    this.shouldShowStateMachineEntities = shouldShowStateMachineEntities;
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
