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

package org.primordion.ef.alien;

import java.util.Date;
import java.util.List;

import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model to the Future or to Extraterrestrials.
 * This is a generalized format, that will make it easier for people in the future to process it.
 * This is the first in a possible series of "alien" exports.
 * The format is not especially easy for people to read, and yet it can be manually read and analyzed.
 * The format is not intended for people to manually write.
 * It's more of an archive format, that can be deciphered without needing any outside user manual.
 * Once the format has been deciphered, it should be relatively straight forward to write
 * a computer program or other algorithmic procedure to reconstruct the original model.
 * The names (bracketed by [ and ]) could be translated into other languages,
 * assuming a knowledge of English.
 * It should be possible to create classes in any programming language using a model specified in Future.
 * The Future format is similar to the chemistry SMILES format:
 *   - both are one long string
 *   - both allow grouping/nesting, and both allow links to other nodes
 *   - both use "(" and ")" for grouping
 *   - both use controlled-length node IDs
 *     - SMILES uses 1- or 2-letter chemistry symbols, or longer bracketed names
 *     - Future node IDs are all the same length
 * See notes in my notebooks for June 30 (Universal Xholon Format) and July 17 2014
 * See wikipedia:
 *   http://en.wikipedia.org/wiki/Communication_with_extraterrestrial_intelligence
 *   http://en.wikipedia.org/wiki/Lincos_%28artificial_language%29
 *   http://en.wikipedia.org/wiki/Lone_Signal
 *   http://en.wikipedia.org/wiki/Alien_language
 *   http://en.wikipedia.org/wiki/CosmicOS
 *   Energy Systems Language,  Energetics
 *
 * Examples:
((entire IH tree)(entire CSH tree starting with the ef root))

HelloWorldSystem IH and CSH
((0000000[XholonClass](0000001[HelloWorldSystem]0000002[Hello]0000003[World]))(1000000>0000001(1000002>0000002>10000031000003>0000003>1000002)))
 *
 * TODO
 *   - be able to capture attributes, probably as child nodes using Attribute_String etc.
 *   - the following example includes type, name, value
 *     (00f4adf[pheneVal=9876.5432]00f4ae3[myString=this is a string]00f4ada[myBool=true])
 *   - or use anonymous IDs, which are OK because no ports are involved with attributes
 *     ([pheneVal=9876.5432]>00f4adf[myString=this is a string]>00f4ae3[myBool=true]>00f4ada)
 * 
 * TODO
 *   - I'm not sure yet how to specify behavior.
 *   - it should be at a higher level than a programming language; declarative in some way
 *   - possibly provide before and after structures, as with Bigraphs
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on July 17, 2014)
 */
@SuppressWarnings("serial")
public class Xholon2Future extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  private String outFileName;
  private String outPath = "./ef/future/";
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;

  /**
   * Constructor.
   */
  public Xholon2Future() {}
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String fileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (fileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".fut";
    }
    else {
      this.outFileName = fileName;
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
    sb.append(getBracketOpen()).append(getBracketOpen());
    writeIhNode(root.getApp().getXhcRoot());
    sb.append(getBracketClosed());
    if (isShouldInsertNewlines()) {sb.append("\n");}
    sb.append(getBracketOpen());
    writeNode(root);
    sb.append(getBracketClosed()).append(getBracketClosed());
    writeToTarget(sb.toString(), outFileName, outPath, root);
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon composite structure hierarchy (CSH).
   */
  protected void writeNode(IXholon node) {
    sb.append(node2HexId(getCshPrefix(), node.getId()));
    String rn = node.getRoleName();
    if ((rn != null) && (rn.length() > 0)) {
      sb.append(getTextBracketOpen()).append(rn).append(getTextBracketClosed());
    }
    sb.append(getPortSymbol()).append(node2HexId(getIhPrefix(), node.getXhcId()));
    writeLinks(node);
    if (node.hasChildNodes()) {
      sb.append(getBracketOpen());
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode);
        childNode = childNode.getNextSibling();
      }
      sb.append(getBracketClosed());
      if (isShouldInsertNewlines()) {sb.append("\n");}
    }
  }
  
  /**
   * Write an inheritance hierarchy (IH) node, and its child nodes.
   * Optionally only include app-specific IH nodes.
   * @param xhcNode The current node in the hierarchy.
   */
  protected void writeIhNode(IXholon xhcNode) {
    if (isShouldShowMechanismIhNodes() || (xhcNode.getId() < IMechanism.MECHANISM_ID_START)) {
      sb.append(node2HexId(getIhPrefix(), xhcNode.getId()));
      sb.append(getTextBracketOpen()).append(xhcNode.getName()).append(getTextBracketClosed());
      if (xhcNode.hasChildNodes()) {
        sb.append(getBracketOpen());
        IXholon childXhcNode = xhcNode.getFirstChild();
        while (childXhcNode != null) {
          writeIhNode(childXhcNode);
          childXhcNode = childXhcNode.getNextSibling();
        }
        sb.append(getBracketClosed());
        if (isShouldInsertNewlines()) {sb.append("\n");}
      }
    }
    else {
      // this is a mechanism node that should not be shown, but should still be navigated
      if (xhcNode.hasChildNodes()) {
        IXholon childXhcNode = xhcNode.getFirstChild();
        while (childXhcNode != null) {
          writeIhNode(childXhcNode);
          childXhcNode = childXhcNode.getNextSibling();
        }
      }
    }
  }
  
  /**
   * Write links from this node to any others, where the Xholon node has connected ports.
   * @param node The current Xholon node.
   */
  protected void writeLinks(IXholon node) {
    if (isShouldShowLinks() == false) {return;}
    List<PortInformation> portList = node.getAllPorts();
    for (int i = 0; i < portList.size(); i++) {
      writeLink(node, (PortInformation)portList.get(i));
    }
  }
  
  /**
   * Write one link.
   * @param node The node where the link originates.
   * @param portInfo Information about the port that represents the link.
   */
  protected void writeLink(IXholon node, final PortInformation portInfo) {
    if (portInfo == null) {return;}
    IXholon remoteNode = portInfo.getReffedNode();
    if (remoteNode == null) {return;}
    if (!remoteNode.hasAncestor(root.getName())) {
      // remoteNode is outside the scope (not a descendant) of root
      return;
    }
    sb.append(getPortSymbol()).append(node2HexId(getCshPrefix(), remoteNode.getId()));
  }
  
  /**
   * Convert an integer to a lowercase hex string.
   * The result should be padded with leading zeros.
   * @param prefix A string to prepend to the result.
   * @param nodeId A node ID.
   */
  protected String node2HexId(String prefix, int nodeId) {
    String hexString = Integer.toHexString(nodeId); //.toUpperCase();
    while (hexString.length() < getHexStrLen()) {
      hexString = getPadCharacter() + hexString;
    }
    return prefix + hexString;
  }
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.bracketOpen = "(";
    p.bracketClosed = ")";
    p.textBracketOpen = "[";
    p.textBracketClosed = "]";
    p.portSymbol = ">";
    p.ihPrefix = "0";
    p.cshPrefix = "1";
    p.padCharacter = "0";
    p.hexStrLen = 6;
    p.shouldInsertNewlines = false;
    p.shouldShowLinks = true;
    p.shouldShowMechanismIhNodes = false;
    this.efParams = p;
  }-*/;

  /** Open bracket for containment. */
  public native String getBracketOpen() /*-{return this.efParams.bracketOpen;}-*/;
  //public native void setBracketOpen(String bracketOpen) /*-{this.efParams.bracketOpen = bracketOpen;}-*/;

  /** Closed bracket for containment. */
  public native String getBracketClosed() /*-{return this.efParams.bracketClosed;}-*/;
  //public native void setBracketClosed(String bracketClosed) /*-{this.efParams.bracketClosed = bracketClosed;}-*/;

  /** Open bracket for text. */
  public native String getTextBracketOpen() /*-{return this.efParams.textBracketOpen;}-*/;
  //public native void setTextBracketOpen(String textBracketOpen) /*-{this.efParams.textBracketOpen = textBracketOpen;}-*/;

  /** Closed bracket for text. */
  public native String getTextBracketClosed() /*-{return this.efParams.textBracketClosed;}-*/;
  //public native void setTextBracketClosed(String textBracketClosed) /*-{this.efParams.textBracketClosed = textBracketClosed;}-*/;

  /** Symbol indicating that the following ID is referenced by a port. */
  public native String getPortSymbol() /*-{return this.efParams.portSymbol;}-*/;
  //public native void setPortSymbol(String portSymbol) /*-{this.efParams.portSymbol = portSymbol;}-*/;

  /** Prefix to ensure that IH IDs are unique among all IH and CSH IDs.e */
  public native String getIhPrefix() /*-{return this.efParams.ihPrefix;}-*/;
  //public native void setIhPrefix(String ihPrefix) /*-{this.efParams.ihPrefix = ihPrefix;}-*/;

  /** Prefix to ensure that CSH IDs are unique among all IH and CSH IDs. */
  public native String getCshPrefix() /*-{return this.efParams.cshPrefix;}-*/;
  //public native void setCshPrefix(String cshPrefix) /*-{this.efParams.cshPrefix = cshPrefix;}-*/;

  /** Character to use when left-padding the hex string. */
  public native String getPadCharacter() /*-{return this.efParams.padCharacter;}-*/;
  //public native void setPadCharacter(String padCharacter) /*-{this.efParams.padCharacter = padCharacter;}-*/;

  /** Length of hex string ID, before prefixing it. */
  public native int getHexStrLen() /*-{return this.efParams.hexStrLen;}-*/;
  //public native void setHexStrLen(int hexStrLen) /*-{this.efParams.hexStrLen = hexStrLen;}-*/;

  /**
   * Whether or not to format the output by inserting new lines.
   * This can be used for debugging.
   */
  public native boolean isShouldInsertNewlines() /*-{return this.efParams.shouldInsertNewlines;}-*/;
  //public native void setShouldInsertNewlines(boolean shouldInsertNewlines) /*-{this.efParams.shouldInsertNewlines = shouldInsertNewlines;}-*/;

  /**
   * Whether or not to show links/ports between nodes.
   */
  public native boolean isShouldShowLinks() /*-{return this.efParams.shouldShowLinks;}-*/;
  //public native void setShouldShowLinks(boolean shouldShowLinks) /*-{this.efParams.shouldShowLinks = shouldShowLinks;}-*/;

  /**
   * Whether or not to show IH nodes that are part of a Xholon mechanism.
   * If this value is false, then only app-specific IH nodes will be shown.
   */
  public native boolean isShouldShowMechanismIhNodes() /*-{return this.efParams.shouldShowMechanismIhNodes;}-*/;
  //public native void setShouldShowMechanismIhNodes(boolean shouldShowMechanismIhNodes) /*-{this.efParams.shouldShowMechanismIhNodes = shouldShowMechanismIhNodes;}-*/;
  
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

  public String getOutPath() {
    return outPath;
  }

  public void setOutPath(String outPath) {
    this.outPath = outPath;
  }

}
