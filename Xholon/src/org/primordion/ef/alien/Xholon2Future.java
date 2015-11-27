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
import org.primordion.xholon.base.IReflection;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.util.ClassHelper;
import org.primordion.xholon.util.IJavaTypes;
import org.primordion.xholon.util.Misc;
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
 *   - I'm not sure yet how to specify behavior.
 *   - it should be at a higher level than a programming language; declarative in some way
 *   - possibly provide before and after structures, as with Bigraphs
 *   - For example, with the original Cell model, it would be possible to export a complete
       Xholon2Future string each time step. An algorithmic process is implied by the systematically
       changing values.
 *   - A better example would be the Fibonacci model. The fibonacci sequence is the same at
       any point in space or time.
       The following shows the changing composite structure hierarchy over multiple time steps:
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=0]>00f4adf)1000003>0000007([Val_int=0]>00f4adf)1000004>0000009([Val_int=0]>00f4adf)))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=0]>00f4adf)1000003>0000007([Val_int=0]>00f4adf)1000004>0000009([Val_int=0]>00f4adf)))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=0]>00f4adf)1000003>0000007([Val_int=1]>00f4adf)1000004>0000009([Val_int=1]>00f4adf)))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=1]>00f4adf)1000003>0000007([Val_int=1]>00f4adf)1000004>0000009([Val_int=1]>00f4adf)))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=1]>00f4adf)1000003>0000007([Val_int=2]>00f4adf)1000004>0000009([Val_int=2]>00f4adf)))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=2]>00f4adf)1000003>0000007([Val_int=3]>00f4adf)1000004>0000009([Val_int=3]>00f4adf)))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=3]>00f4adf)1000003>0000007([Val_int=5]>00f4adf)1000004>0000009([Val_int=5]>00f4adf)))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=5]>00f4adf)1000003>0000007([Val_int=8]>00f4adf)1000004>0000009([Val_int=8]>00f4adf))))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=8]>00f4adf)1000003>0000007([Val_int=13]>00f4adf)1000004>0000009([Val_int=13]>00f4adf)))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=13]>00f4adf)1000003>0000007([Val_int=21]>00f4adf)1000004>0000009([Val_int=21]>00f4adf)))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=21]>00f4adf)1000003>0000007([Val_int=34]>00f4adf)1000004>0000009([Val_int=34]>00f4adf)))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=34]>00f4adf)1000003>0000007([Val_int=55]>00f4adf)1000004>0000009([Val_int=55]>00f4adf)))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=55]>00f4adf)1000003>0000007([Val_int=89]>00f4adf)1000004>0000009([Val_int=89]>00f4adf)))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=89]>00f4adf)1000003>0000007([Val_int=144]>00f4adf)1000004>0000009([Val_int=144]>00f4adf)))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=144]>00f4adf)1000003>0000007([Val_int=233]>00f4adf)1000004>0000009([Val_int=233]>00f4adf)))
(1000000>0000001([Val_int=0]>00f4adf)(1000001>0000003>1000003>1000002>1000004([Val_int=0]>00f4adf)1000002>0000006([Val_int=233]>00f4adf)1000003>0000007([Val_int=377]>00f4adf)1000004>0000009([Val_int=377]>00f4adf)))
       A partial inheritance hierarchy for this model is:
(0000000[XholonClass](00f4ad8[Attribute](00f4ad9[Attribute_attribute]00f4ada[Attribute_boolean]00f4adb[Attribute_byte]00f4adc[Attribute_char]00f4add[Attribute_double]00f4ade[Attribute_float]00f4adf[Attribute_int]00f4ae0[Attribute_long]00f4ae1[Attribute_Object]00f4ae2[Attribute_short]00f4ae3[Attribute_String])0000001[FibonacciSystem]0000002[Function](0000003[FibonacciFunction])0000004[Variable](0000005[IndependentVariable](0000006[NMinus2]0000007[NMinus1])0000008[DependentVariable](0000009[N]))))
 * 
 * Simple Tokenizer:
var str = "((0000000[XholonClass](0000001[HelloWorldSystem]0000002[Hello]0000003[World]))(1000000>0000001([State=0]>00f4adf)(1000002>0000002>1000003([State=0]>00f4adf)1000003>0000003>1000002([State=0]>00f4adf))))";
console.log(str.split(/([>()[\]])/g).filter(function(el){ return el.trim(); }));
  ["(", "(", "0000000", "[", "XholonClass", "]", "(", "0000001", "[", "HelloWorldSystem", "]",
   "0000002", "[", "Hello", "]", "0000003", "[", "World", "]", ")", ")", "(", "1000000", ">", "0000001",
   "(", "[", "State=0", "]", ">", "00f4adf", ")", "(", "1000002", ">", "0000002", ">", "1000003",
   "(", "[", "State=0", "]", ">", "00f4adf", ")", "1000003", ">", "0000003", ">", "1000002",
   "(", "[", "State=0", "]", ">", "00f4adf", ")", ")", ")", ")"]

// this has two IDs concatenated together; there should be two tokens
// if a token begins with a digit (such as 0 or 1), then its length must be 7 (or whatever)
var str = "((0000000[XholonClass](0000001[PingPongSystem]0000002[PingPongEntity]))(1000000>0000001(1000001>0000002>10000021000002>0000002>1000001)))";
console.log(str.split(/([>()[\]])/g).filter(function(el){ return el.trim(); }));
  ["(", "(", "0000000", "[", "XholonClass", "]", "(", "0000001", "[", "PingPongSystem", "]",
   "0000002", "[", "PingPongEntity", "]", ")", ")", "(", "1000000", ">", "0000001",
   "(", "1000001", ">", "0000002", ">", "10000021000002", ">", "0000002", ">", "1000001", ")", ")", ")"]

// these work for fixed-length substrings
"123456712345671234567".match(/[0-1].{1,6}/g);
"123456792345671234567".match(/[0-1].{1,6}/g);
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on July 17, 2014)
 */
@SuppressWarnings("serial")
public class Xholon2Future extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat, IXmlWriter {
  
  private String outFileName;
  private String outPath = "./ef/future/";
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  private StringBuilder sbAttrs;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** Use GWT reflection to get node attributes. */
  private IReflection ir = null;
  
  private String nodeIdBoolean = null; // 00f4ada[Attribute_boolean]
  private String nodeIdByte = null; // 00f4adb[Attribute_byte]
  private String nodeIdChar = null; // 00f4adc[Attribute_char]
  private String nodeIdDouble = null; // 00f4add[Attribute_double]
  private String nodeIdFloat = null; // 00f4ade[Attribute_float]
  private String nodeIdInt = null; // 00f4adf[Attribute_int]
  private String nodeIdLong = null; // 00f4ae0[Attribute_long]
  private String nodeIdShort = null; // 00f4ae2[Attribute_short]
  private String nodeIdString = null; // 00f4ae3[Attribute_String]
  
  /**
   * Constructor.
   */
  public Xholon2Future() {}
  
	@Override
	public String getVal_String() {
	  return sb.toString();
	}
	
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
    nodeIdBoolean = node2HexId(getIhPrefix(), root.getClassNode("Attribute_boolean").getId());
    nodeIdByte    = node2HexId(getIhPrefix(), root.getClassNode("Attribute_byte").getId());
    nodeIdChar    = node2HexId(getIhPrefix(), root.getClassNode("Attribute_char").getId());
    nodeIdDouble  = node2HexId(getIhPrefix(), root.getClassNode("Attribute_double").getId());
    nodeIdFloat   = node2HexId(getIhPrefix(), root.getClassNode("Attribute_float").getId());
    nodeIdInt     = node2HexId(getIhPrefix(), root.getClassNode("Attribute_int").getId());
    nodeIdLong    = node2HexId(getIhPrefix(), root.getClassNode("Attribute_long").getId());
    nodeIdShort   = node2HexId(getIhPrefix(), root.getClassNode("Attribute_short").getId());
    nodeIdString  = node2HexId(getIhPrefix(), root.getClassNode("Attribute_String").getId());
    root.consoleLog(nodeIdString + " " + nodeIdInt + " " + nodeIdBoolean);
    return true;
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
   */
  public void writeAll() {
    ir = ReflectionFactory.instance();
    sb = new StringBuilder();
    sb.append(getBracketOpen()).append(getBracketOpen());
    writeIhNode(root.getApp().getXhcRoot());
    sb.append(getBracketClosed());
    sb.append(getBracketOpen());
    writeNode(root);
    sb.append(getBracketClosed()).append(getBracketClosed());
    setWriteToTab(isWriteToNewTab());
    writeToTarget(sb.toString(), outFileName, outPath, root);
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon composite structure hierarchy (CSH).
   */
  protected void writeNode(IXholon node) {
    if (isShouldInsertNewlines()) {sb.append("\n");}
    sb.append(node2HexId(getCshPrefix(), node.getId()));
    String rn = node.getRoleName();
    if ((rn != null) && (rn.length() > 0)) {
      sb.append(getTextBracketOpen()).append(rn).append(getTextBracketClosed());
    }
    sb.append(getPortSymbol()).append(node2HexId(getIhPrefix(), node.getXhcId()));
    writeLinks(node);
    writeAttributes(node);
    if (node.hasChildNodes()) {
      sb.append(getBracketOpen());
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode);
        childNode = childNode.getNextSibling();
      }
      sb.append(getBracketClosed());
    }
  }
  
  /**
   * Write an inheritance hierarchy (IH) node, and its child nodes.
   * Optionally only include app-specific IH nodes.
   * @param xhcNode The current node in the hierarchy.
   */
  protected void writeIhNode(IXholon xhcNode) {
    if (isShouldShowMechanismIhNodes() || (xhcNode.getId() < IMechanism.MECHANISM_ID_START)) {
      if (isShouldInsertNewlines()) {sb.append("\n");}
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
   * Write app-specific attributes.
   * Example:
   *   ([pheneVal=9876.5432]>00f4add[myString=this is a string]>00f4ae3[myBool=true]>00f4ada)
   * @param node The node whose attributes should be written.
   */
  public void writeAttributes(IXholon node) {
    sbAttrs = new StringBuilder();
    IXholon2Xml xholon2xml = node.getXholon2Xml();
    xholon2xml.setXhAttrStyle(IXholon2Xml.XHATTR_TO_XMLATTR);
    node.toXmlAttributes(xholon2xml, this);
    if (sbAttrs.length() > 0) {
      sb
      .append(getBracketOpen())
      .append(sbAttrs.toString())
      .append(getBracketClosed());
    }
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
    p.shouldShowAttributes = true;
    p.shouldShowMechanismIhNodes = false;
    p.shouldWriteVal = true;
    p.shouldWriteAllPorts = true;
    p.writeToNewTab = true;
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
   * Whether or not to show app-specific attributes of nodes.
   */
  public native boolean isShouldShowAttributes() /*-{return this.efParams.shouldShowAttributes;}-*/;
  //public native void sethouldShowAttributes(boolean shouldShowAttributes) /*-{this.efParams.shouldShowAttributes = shouldShowAttributes;}-*/;

  /**
   * Whether or not to show IH nodes that are part of a Xholon mechanism.
   * If this value is false, then only app-specific IH nodes will be shown.
   */
  public native boolean isShouldShowMechanismIhNodes() /*-{return this.efParams.shouldShowMechanismIhNodes;}-*/;
  //public native void setShouldShowMechanismIhNodes(boolean shouldShowMechanismIhNodes) /*-{this.efParams.shouldShowMechanismIhNodes = shouldShowMechanismIhNodes;}-*/;
  
  /**
   * Whether or not to write to a new tab each time step.
   * true create and write to a new tab
   * false write to the existing out tab
   */
  public native boolean isWriteToNewTab() /*-{return this.efParams.writeToNewTab;}-*/;
  //public native void setWriteToNewTab(boolean writeToNewTab) /*-{this.efParams.writeToNewTab = writeToNewTab;}-*/;
    
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
  
  // #############################################################################
  // methods required to implement IXmlWriter
  
  @Override
  // DO NOT IMPLEMENT THIS
  public void createNew(Object out) {}

  @Override
  public void writeStartDocument() {}

  @Override
  public void writeEndDocument() {}

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeStartElement(String prefix, String localName, String namespaceURI) {}

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeStartElement(String name) {}

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeEndElement(String name, String namespaceURI) {}

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeEndElement(String name) {}

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeNamespace(String prefix, String namespaceURI) {}

  @Override
  // This is for use by Xholon.toXmlAttributes() only
  public void writeAttribute(String name, String value) {
    if ("Val".equalsIgnoreCase(name) && !isShouldWriteVal()) {return;}
    if ("AllPorts".equalsIgnoreCase(name) && !isShouldWriteAllPorts()) {return;}
    if ("roleName".equalsIgnoreCase(name)) {return;} // roleName is already written out
    if ("implName".equalsIgnoreCase(name)) {return;}
    sbAttrs
    .append(getTextBracketOpen())
    .append(name)
    .append("=")
    .append(value)
    .append(getTextBracketClosed())
    .append(getPortSymbol());
    String ihId = nodeIdString;
    switch(Misc.getJavaDataType(value)) {
    case IJavaTypes.JAVACLASS_UNKNOWN: break;
    case IJavaTypes.JAVACLASS_int: ihId = nodeIdInt; break;
    case IJavaTypes.JAVACLASS_long: ihId = nodeIdLong; break;
    case IJavaTypes.JAVACLASS_double: ihId = nodeIdDouble; break;
    case IJavaTypes.JAVACLASS_float: ihId = nodeIdFloat; break;
    case IJavaTypes.JAVACLASS_boolean: ihId = nodeIdBoolean; break;
    case IJavaTypes.JAVACLASS_String: break;
    case IJavaTypes.JAVACLASS_byte: ihId = nodeIdByte; break;
    case IJavaTypes.JAVACLASS_char: ihId = nodeIdChar; break;
    case IJavaTypes.JAVACLASS_short: ihId = nodeIdShort; break;
    default: break;
    }
    sbAttrs.append(ihId);
  }

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeText(String text) {}

  @Override
  public void writeComment(String data) {}

  @Override
  public String getWriterName() {
    return "Xholon2Future";
  }

  @Override
  // DO NOT IMPLEMENT THIS
  public void flush() {}

  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#isShouldWriteVal()
   */
  public native boolean isShouldWriteVal() /*-{
    return this.efParams.shouldWriteVal;
  }-*/;
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#setShouldWriteVal()
   */
  public native void setShouldWriteVal(boolean shouldWriteVal) /*-{
    this.efParams.shouldWriteVal = shouldWriteVal;
  }-*/;
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#isShouldWriteAllPorts()
   */
  public native boolean isShouldWriteAllPorts() /*-{
    return this.efParams.shouldWriteAllPorts;
  }-*/;
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#shouldWriteAllPorts()
   */
  public native void setShouldWriteAllPorts(boolean shouldWriteAllPorts) /*-{
    this.efParams.shouldWriteAllPorts = shouldWriteAllPorts;
  }-*/;

}
