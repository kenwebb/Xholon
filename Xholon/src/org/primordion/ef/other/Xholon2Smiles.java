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

import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.Date;

import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model in Simplified Molecular Input Line Entry System (SMILES) format.
 * 
 * Examples:
[one]2[two]([three][three]3[three])[four]2[five]3
 * 
 * I haven't tested this example yet, need to remove whitespace:
 * <pre>
 [ExtraCellularSpace] (
  [EukaryoticCell] (
   [CellMembrane] (
    [CellBilayer]%01%02
   )
   [Cytoplasm] (
    [Hexokinase]%03%04.
    [PhosphoGlucoIsomerase].
    [PhosphoFructokinase].
    [Aldolase].
    [TriosePhosphateIsomerase].
    [Glyceraldehyde_3_phosphateDehydrogenase].
    [PhosphoGlycerokinase].
    [PhosphoGlyceromutase].
    [Enolase].
    [PyruvateKinase]
    [Cytosol] (
     [Glucose]%01%03.
     [Glucose_6_Phosphate]%04.
     [Fructose_6_Phosphate].
     [Fructose_1x6_Biphosphate].
     [DihydroxyacetonePhosphate].
     [Glyceraldehyde_3_Phosphate].
     [X1x3_BisphosphoGlycerate].
     [X3_PhosphoGlycerate].
     [X2_PhosphoGlycerate].
     [PhosphoEnolPyruvate].
     [Pyruvate]
    )
   )
  )
  [ExtraCellularSolution] (
   [Glucose]%02
  )
 )
 * </pre>
 * 
 * with whitespace removed, this is syntactically correct at depict site:
 * <pre>
[ExtraCellularSpace]([EukaryoticCell]([CellMembrane]([CellBilayer]%01%02)[Cytoplasm]([Hexokinase]%03%04.[PhosphoGlucoIsomerase].[PhosphoFructokinase].[Aldolase].[TriosePhosphateIsomerase].[Glyceraldehyde_3_phosphateDehydrogenase].[PhosphoGlycerokinase].[PhosphoGlyceromutase].[Enolase].[PyruvateKinase][Cytosol]([Glucose]%01%03.[Glucose_6_Phosphate]%04.[Fructose_6_Phosphate].[Fructose_1x6_Biphosphate].[DihydroxyacetonePhosphate].[Glyceraldehyde_3_Phosphate].[X1x3_BisphosphoGlycerate].[X3_PhosphoGlycerate].[X2_PhosphoGlycerate].[PhosphoEnolPyruvate].[Pyruvate])))[ExtraCellularSolution]([Glucose]%02))
 * </pre>
 * 
 * The following are all valid syntax:
 * <pre>
[HelloWorldSystem]([Hello][World])
[HelloWorldSystem](.[Hello][World])
[HelloWorldSystem]([Hello].[World])
[HelloWorldSystem](.[Hello].[World])
 * </pre>
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on June 24, 2014)
 * @see <a href="http://en.wikipedia.org/wiki/Simplified_molecular-input_line-entry_system">wikipedia</a>
 * @see <a href="http://www.daylight.com/dayhtml/doc/theory/theory.smiles.html">basic info</a>
 * @see <a href="http://www.daylight.com/daycgi/depict">online SMILES to image</a>
 * @see <a href="http://www.daylight.com/dayhtml/doc/theory/">theory</a>
 * @see <a href="https://gist.github.com/kenwebb/6c71b62ab83af820939a">SMILES parser for Xholons</a>
 * @see <a href="http://peter-ertl.com/jsme/">JSME Molecule Editor</a>
 */
@SuppressWarnings("serial")
public class Xholon2Smiles extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  private String outFileName;
  private String outPath = "./ef/smiles/";
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** Whether or not to show state machine nodes. */
  private boolean shouldShowStateMachineEntities = false;
  
  /**
   * Constructor.
   */
  public Xholon2Smiles() {}
  
	@Override
	public String getVal_String() {
	  return sb.toString();
	}
	
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String smiFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (smiFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".smi";
    }
    else {
      this.outFileName = smiFileName;
    }
    this.modelName = modelName;
    this.root = root;
    if (isTryJsme()) {
      loadJSME();
      return true; // do not return false; that just causes an error message
    }
    return true;
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
   */
  public void writeAll() {
    sb = new StringBuilder();
    writeNode(root, 0); // root is level 0
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
    
    sb.append(makeAtomOrBond(node));
    
    // children
    if (node.hasChildNodes()) {
      sb.append("(");
      IXholon childNode = node.getFirstChild();
      boolean isFirstNode = true;
      while (childNode != null) {
        if (isFirstNode && isDotBeforeFirst()) {
          sb.append(".");
        }
        else if (!isFirstNode && isDotBeforeNext()) {
          sb.append(".");
        }
        writeNode(childNode, level+1);
        childNode = childNode.getNextSibling();
        isFirstNode = false;
      }
      
      sb.append(")");
    }
  }
  
  /**
   * Make a SMILES atom or bond.
   * @param node 
   */
  protected String makeAtomOrBond(IXholon node) {
    StringBuilder sbAtom = new StringBuilder();
    String nodeName = node.getName(getNameTemplate());
    
    switch (nodeName) {
    
    // atoms
    case "B":
    case "Br":
    case "C":
    case "Cl":
    case "N":
    case "O":
    case "S":
    case "P":
    case "F":
    case "I":
    case "b":
    case "c":
    case "n":
    case "o":
    case "s":
    case "p":
      sbAtom.append(nodeName);
      break;
    
    // bonds
    case "Sngl": sbAtom.append("-"); break;
    case "Dobl": sbAtom.append("="); break;
    case "Trpl": sbAtom.append("#"); break;
    case "Rmtc": sbAtom.append(":"); break;
    
    // branch  ()
    case "Brch":
      break;
    
    default:
      // this is a bracketed atom  []
      sbAtom.append("[");
      if (nodeName.startsWith("_")) {
        // these are chemical atoms, and are one or two characters long
        sbAtom.append(nodeName.substring(1));
      }
      else {
        // this is a non-chemical "atom" (any valid Xholon node)
        sbAtom.append(nodeName);
      }
      sbAtom.append("]");
      break;
    }
    return sbAtom.toString();
  }
  
  /**
   * Load JSME library into a separate iframe.
   * JSME is a GWT app, completely separate from the Xholon GWT app.
   */
  protected void loadJSME() {
    // Make a new frame.
    Frame frame = new Frame("xholon/lib/JSME_minimal.html");
    frame.setSize("430px", "390px");

    // Add it to the root panel.
    RootPanel.get().add(frame);
  }
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.nameTemplate = "R^^^^^";
    p.dotBeforeFirst = true;
    p.dotBeforeNext = false;
    p.tryJsme = false;
    this.efParams = p;
  }-*/;
  
  public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  //public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;
  
  public native boolean isDotBeforeFirst() /*-{return this.efParams.dotBeforeFirst;}-*/;
  //public native void setDotBeforeFirst(boolean dotBeforeFirst) /*-{this.efParams.dotBeforeFirst = dotBeforeFirst;}-*/;
  
  public native boolean isDotBeforeNext() /*-{return this.efParams.dotBeforeNext;}-*/;
  //public native void setDotBeforeNext(boolean dotBeforeNext) /*-{this.efParams.dotBeforeNext = dotBeforeNext;}-*/;
  
  /**
   * Try loading and using JSME, which is a GWT version of JME.
   * This is basically a test of using a separate GWT library.
   * JSME does NOT import the SMILES format, but it does export it.
   */
  public native boolean isTryJsme() /*-{return this.efParams.tryJsme;}-*/;
  //public native void setTryJsme(boolean tryJsme) /*-{this.efParams.tryJsme = tryJsme;}-*/;
  
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

}
