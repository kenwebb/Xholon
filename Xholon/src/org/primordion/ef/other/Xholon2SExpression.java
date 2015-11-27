/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2014 Ken Webb
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
//import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model in s-expression format.
 * Both Xholon and s-expressions use a prefix format,
 * and both are based on binary trees.
 * Code and data in Scheme and other Lisp-like languages are written as s-expressions.
 * Xholon siblings are written as a parenthesized list.
 * The single Xholon root node is itself a list and must be inside parentheses.
 * White space is required between siblings, and may be used elsewhere for pretty-printing.
 * TODO This class does not yet write out any attributes of Xholon nodes.
 * Examples of s-expressions:
<pre>
(One (Two Three))

(x y z)

((milk juice) (honey marmalade))

(plus 3.14 2.71)

(math (apply (plus cn cn cn apply (times cn cn apply (abs cn)))))

(
  ExtraCellularSpace (
    ExtraCellularSolution (
      Glucose
    )
    EukaryoticCell (
      CellMembrane (
        CellBilayer
      )
      Cytoplasm (
        Cytosol (
          Glucose
          Glucose_6_Phosphate
          more
          Pyruvate
        )
        Hexokinase
        PhosphoGlucoIsomerase
        more
        PyruvateKinase
      )
    )
  )
)
</pre>
 *
 * Example use with BiwaScheme:
<pre>
(car (cdr '(math (apply (plus cn cn cn apply (times cn cn apply (abs cn)))))))
</pre>
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on March 2, 2010)
 * @see <a href="http://en.wikipedia.org/wiki/S-expression">S-expression format</a>
 * @see <a href="http://www.biwascheme.org/">BiwaScheme online Scheme interpreter</a>
 * @see <a href="http://repl.it">another site that runs BiwaScheme</a>
 */
@SuppressWarnings("serial")
public class Xholon2SExpression extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  private String outFileName;
  private String outPath = "./ef/sexpr/";
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
  
  /** Whether or not to format the output by inserting extra spaces and new lines. */
  private boolean shouldPrettyPrint = true;
  
  /** Use for pretty-printing the output; 50 spaces. */
  private String indent = "                                                  ";

  /**
   * Constructor.
   */
  public Xholon2SExpression() {}
  
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
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".sexpr";
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
    sb.append("(");
    if (shouldPrettyPrint) {sb.append("\n");}
    writeNode(root, 0); // root is level 0
    sb.append("\n)\n");
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
    String tab = "";
    if (shouldPrettyPrint) {
      tab = indent;
      if (level < indent.length()) {
        tab = indent.substring(0, level+1);
      }
    }
    String nodeName = node.getName(nameTemplate);
    sb.append(tab);
    sb.append(nodeName);
    
    // children
    if (node.hasChildNodes()) {
      sb.append(" (");
      if (shouldPrettyPrint) {sb.append("\n");}
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode, level+1);
        childNode = childNode.getNextSibling();
        if (childNode != null) {
          if (shouldPrettyPrint) {sb.append("\n");}
          else {sb.append(" ");};
        }
      }
      
      if (shouldPrettyPrint) {sb.append("\n");}
      sb.append(tab).append(")");
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
  
  public boolean isShouldPrettyPrint() {
    return shouldPrettyPrint;
  }

  public void setShouldPrettyPrint(boolean shouldPrettyPrint) {
    this.shouldPrettyPrint = shouldPrettyPrint;
  }

}
