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

package org.primordion.xholon.mech.catt;

import java.util.List;
import java.util.ArrayList;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.service.IXholonService;

/**
 * Category Theory - AQL
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="">AQL website</a>
 * @since 0.9.1 (Created on June 1, 2017)
 */
public class CatAql extends XholonWithPorts {
  
  // AQL kinds
  static final private String KIND_TYPESIDE = "typeside";
  static final private String KIND_SCHEMA = "schema";
  static final private String KIND_INSTANCE = "instance";
  static final private String KIND_MAPPING = "mapping";
  static final private String KIND_TRANSFORM = "transform";
  static final private String KIND_QUERY = "query";
  static final private String KIND_GRAPH = "graph";
  static final private String KIND_PRAGMA = "pragma";
  static final private String KIND_CONSTRAINTS = "constraints";
  
  // typeside terms and states
  static final private String TERM_TYPESIDE_NULL = null;
  static final private String TERM_TYPESIDE_IMPORTS = "imports";
  static final private String TERM_TYPESIDE_TYPES = "types";
  static final private String TERM_TYPESIDE_CONSTANTS = "constants";
  static final private String TERM_TYPESIDE_FUNCTIONS = "functions";
  static final private String TERM_TYPESIDE_EQUATIONS = "equations";
  static final private String TERM_TYPESIDE_JAVA_TYPES = "java_types";
  static final private String TERM_TYPESIDE_JAVA_CONSTANTS = "java_constants";
  static final private String TERM_TYPESIDE_JAVA_FUNCTIONS = "java_functions";
  static final private String TERM_TYPESIDE_OPTIONS = "options";
  
  // schema terms and states
  static final private String TERM_SCHEMA_NULL = null;
  static final private String TERM_SCHEMA_IMPORTS = "imports";
  static final private String TERM_SCHEMA_ENTITIES = "entities";
  static final private String TERM_SCHEMA_FOREIGN_KEYS = "foreign_keys";
  static final private String TERM_SCHEMA_ATTRIBUTES = "attributes";
  static final private String TERM_SCHEMA_PATH_EQUATIONS = "path_equations";
  static final private String TERM_SCHEMA_OBSERVATION_EQUATIONS = "observation_equations";
  static final private String TERM_SCHEMA_OPTIONS = "options";
  
  // instance terms and states
  static final private String TERM_INSTANCE_NULL = null;
  static final private String TERM_INSTANCE_IMPORTS = "imports";
  static final private String TERM_INSTANCE_GENERATORS = "generators";
  static final private String TERM_INSTANCE_EQUATIONS = "equations";
  static final private String TERM_INSTANCE_MULTI_EQUATIONS = "multi equations";
  static final private String TERM_INSTANCE_OPTIONS = "options";
  
  // TODO other terms and states
  
  private StringBuilder sbIh = null;
  private StringBuilder sbCd = null;
  private StringBuilder sbCsh = null;
  
  private String indent = "  ";
  
  private IApplication app = null;
  private IXholon xholonHelperService = null;
  
  private String roleName = null;
  
  @Override
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  @Override
  public String getRoleName() {
    return roleName;
  }
  
  /**
    * The AQL content will be in this variable.
    */
  private String val = null;
  
  @Override
  public String getVal_String() {
    return val;
  }
  
  @Override
  public void setVal(String val) {
    this.val = val;
  }
  
  @Override
  public void setVal_String(String val) {
    this.val = val;
  }
  
  @Override
  public Object getVal_Object() {
    return val;
  }
  
  @Override
  public void setVal(Object val) {
    this.val = val.toString();
  }
  
  @Override
  public void setVal_Object(Object val) {
    this.val = val.toString();
  }
  
  @Override
  public void postConfigure() {
    this.app = this.getApp();
    sbIh = new StringBuilder().append("<_-.XholonClass>\n").append("<CatTheorySystem/>\n");
    sbCd = new StringBuilder().append("<xholonClassDetails>\n");
    sbCsh = new StringBuilder().append("<CatTheorySystem>\n");
    //this.println(val);
    parseAqlContent(val.trim());
    //sbIh.append("</_-.XholonClass>\n");
    //sbCd.append("</xholonClassDetails>\n");
    //sbCsh.append("</CatTheorySystem>\n");
    this.println("<XholonWorkbook>\n");
    this.println(sbIh.toString());
    this.println(sbCd.toString());
    this.println(sbCsh.toString());
    this.println("</XholonWorkbook>\n");
    super.postConfigure();
  }
  
  @Override
  public void act() {
    // is there anything to do?
    super.act();
  }
  
  protected void parseAqlContent(String aqlContent) {
    // typeside
    int posStart = aqlContent.indexOf(KIND_TYPESIDE, 0);
    if (posStart == -1) {return;}
    int posEnd = aqlContent.indexOf("}", posStart);
    if (posEnd == -1) {return;}
    parseTypeside(aqlContent.substring(posStart,posEnd));
    
    // schema
    posStart = aqlContent.indexOf(KIND_SCHEMA, posEnd);
    if (posStart == -1) {return;}
    posEnd = aqlContent.indexOf("}", posStart);
    if (posEnd == -1) {return;}
    parseSchema(aqlContent.substring(posStart,posEnd));
    
    sbIh.append("</_-.XholonClass>\n");
    sbCd.append("</xholonClassDetails>\n");
    IXholonClass xhcRoot = app.getXhcRoot();
		sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, sbIh.toString(), xhcRoot);
		sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, sbCd.toString(), xhcRoot);
    
    sbCsh.append("</CatTheorySystem>\n");
    // paste in the System node and its collection/set children
    // specific instances will subsequently be pasted as children of the collecttion/set nodes
    IXholon xhRoot = app.getRoot();
		sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, sbCsh.toString(), xhRoot);
		
    // instance
    posStart = aqlContent.indexOf(KIND_INSTANCE, posEnd);
    if (posStart == -1) {return;}
    posEnd = aqlContent.indexOf("}", posStart);
    if (posEnd == -1) {return;}
    parseInstance(aqlContent.substring(posStart,posEnd));
  }
  
  protected void parseTypeside(String ts) {
    //this.println("parseTypeside:\n" + ts + "\n");
    sbIh.append("<!-- typeside types -->\n");
    String[] lines = ts.split("\n");
    String state = TERM_TYPESIDE_NULL;
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i].trim();
      String[] tokens = line.split("\\s+");
      if (tokens.length == 0) {continue;}
      switch (tokens[0]) {
      case TERM_TYPESIDE_IMPORTS: break;
      case TERM_TYPESIDE_TYPES:
        state = TERM_TYPESIDE_TYPES;
        this.println("types");
        if (tokens.length > 1) {
          parseLineTypesideTypes(tokens, 1);
        }
        break;
      case TERM_TYPESIDE_CONSTANTS:
        state = TERM_TYPESIDE_CONSTANTS;
        this.println("constants");
        if (tokens.length > 1) {
          parseLineTypesideConstants(tokens, 1);
        }
        break;
      case TERM_TYPESIDE_FUNCTIONS:
        state = TERM_TYPESIDE_FUNCTIONS;
        //this.println("functions");
        break;
      case TERM_TYPESIDE_EQUATIONS:
        state = TERM_TYPESIDE_EQUATIONS;
        //this.println("equations");
        break;
      case TERM_TYPESIDE_OPTIONS:
        state = TERM_TYPESIDE_OPTIONS;
        //this.println("options");
        break;
      default:
        switch (state) {
        case TERM_TYPESIDE_TYPES: parseLineTypesideTypes(tokens, 0); break;
        case TERM_TYPESIDE_CONSTANTS: parseLineTypesideConstants(tokens, 0); break;
        default: break;
        }
        break;
      } // end switch(tokens[0])
    } // end for
  } // end method
  
  /**
   * string 
 	 * nat
   */
  protected void parseLineTypesideTypes(String[] tokens, int startIx) {
    for (int i = startIx; i < tokens.length; i++) {
      this.println("  " + tokens[i]);
      String xhcName = makeXholonClassName(tokens[i]);
      sbIh.append(indent).append("<").append(xhcName).append("/>\n")
      .append(indent).append("<").append(xhcName).append("s/>\n");
      sbCsh.append(indent).append("<").append(xhcName).append("s/>\n");
    }
  }
  
  /**
   * Al Akin Bob Bo Carl Cork Dan Dunn Math CS : string
   * zero : nat
   */
  protected void parseLineTypesideConstants(String[] tokens, int startIx) {
    for (int i = startIx; i < tokens.length; i++) {
      String token = tokens[i];
      if (":".equals(token)) {
        token = tokens[i+1];
        this.println("  TYPE=" + token);
        break;
      }
      this.println("  " + token);
    }
  }
  
  protected void parseSchema(String sch) {
    //this.println("parseSchema:\n" + sch + "\n");
    sbIh.append("<!-- schema entities -->\n");
    String[] lines = sch.split("\n");
    String state = TERM_SCHEMA_NULL;
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i].trim();
      String[] tokens = line.split("\\s+");
      if (tokens.length == 0) {continue;}
      switch (tokens[0]) {
      case TERM_SCHEMA_IMPORTS: break;
      case TERM_SCHEMA_ENTITIES:
        state = TERM_SCHEMA_ENTITIES;
        this.println("entities");
        if (tokens.length > 1) {
          parseLineSchemaEntities(tokens, 1);
        }
        break;
      case TERM_SCHEMA_FOREIGN_KEYS:
        state = TERM_SCHEMA_FOREIGN_KEYS;
        this.println("foreign_keys");
        break;
      case TERM_SCHEMA_ATTRIBUTES:
        state = TERM_SCHEMA_ATTRIBUTES;
        this.println("attributes");
        break;
      case TERM_SCHEMA_PATH_EQUATIONS:
        state = TERM_SCHEMA_PATH_EQUATIONS;
        this.println("path_equations");
        break;
      case TERM_SCHEMA_OBSERVATION_EQUATIONS:
        state = TERM_SCHEMA_OBSERVATION_EQUATIONS;
        this.println("observation_equations");
        break;
      case TERM_SCHEMA_OPTIONS:
        state = TERM_SCHEMA_OPTIONS;
        this.println("options");
        break;
      default:
        switch (state) {
        case TERM_SCHEMA_ENTITIES: parseLineSchemaEntities(tokens, 0); break;
        case TERM_SCHEMA_FOREIGN_KEYS: parseLineSchemaForeignKeys(tokens, 0); break;
        default: break;
        }
        break;
      } // end switch(tokens[0])
    } // end for
  } // end method
  
  /**
   * Employee
 	 * Department
   */
  protected void parseLineSchemaEntities(String[] tokens, int startIx) {
    for (int i = startIx; i < tokens.length; i++) {
      this.println("  " + tokens[i]);
      String xhcName = makeXholonClassName(tokens[i]);
      sbIh.append(indent).append("<").append(xhcName).append("/>\n")
      .append(indent).append("<").append(xhcName).append("s/>\n");
      sbCd.append(indent).append("<").append(xhcName).append(" xhType=\"XhtypePureActiveObject\"").append("/>\n")
      .append(indent).append("<").append(xhcName).append("s xhType=\"XhtypePureActiveObject\"").append("/>\n");
      sbCsh.append(indent).append("<").append(xhcName).append("s/>\n");
    }
  }
  
  /**
   * manager   : Employee -> Employee
   * worksIn   : Employee -> Department
   * secretary : Department -> Employee
   */
  protected void parseLineSchemaForeignKeys(String[] tokens, int startIx) {
    //for (int i = startIx; i < tokens.length; i++) {
      //this.println("  " + tokens[i]);
      sbCd.append(indent)
      .append("<").append(makeXholonClassName(tokens[2])).append("s>")
      // <port name="manager" connector="."/>
      .append("<port name=\"").append(makeXholonAttributeName(tokens[0])).append("\" connector=\"").append("../").append(makeXholonClassName(tokens[4])).append("s\"/>")
      .append("</").append(makeXholonClassName(tokens[2])).append("s>\n");
    //}
  }
  
  protected void parseInstance(String inst) {
    //this.println("parseInstance:\n" + inst + "\n");
    String[] lines = inst.split("\n");
    for (int i = 0; i < lines.length; i++) {
      
    }
  }
  
  /**
   * If inName is already the name of a XholonClass,
   * then return that name with a "_" appended to the end
   * else return inName.
   * example: "Chameleon" -> "Chameleon_"
   * @param inName 
   * @return 
   */
  protected String makeXholonClassName(String inName) {
    IXholonClass ixhc = this.getClassNode(inName);
    if (ixhc != null) {
      return inName + "_";
    }
    else {
      return inName;
    }
  }
  
  /**
   * If inName is already the name of a Xholon JavaScript attribute or function,
   * then return that name with a "_" appended to the end
   * else return inName.
   * example: "first" -> "first_"
   * @param inName 
   * @return 
   */
  protected native String makeXholonAttributeName(String inName) /*-{
    if ($wnd.xh.ixholonapi[inName]) {
      return inName + "_";
    }
    else {
      return inName;
    }
  }-*/;
  
	/**
	 * Send a synchronous message to the XholonHelperService.
	 * @param signal
	 * @param data
	 * @param sender
	 * @return A response message.
	 */
	protected IMessage sendXholonHelperService(int signal, Object data, IXholon sender)
	{
		// send the request to the XholonHelperService by sending it a sync message
		if (xholonHelperService == null) {
			xholonHelperService = app.getService(IXholonService.XHSRV_XHOLON_HELPER);
		}
		if (xholonHelperService == null) {
			return null;
		}
		else {
			if (sender == null) {sender = app;}
			return xholonHelperService.sendSyncMessage(signal, data, sender);
		}
	}
	  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("val".equals(attrName)) {
      // this is the AQL content
      this.val = attrVal;
    }
    return 0;
  }
  
  @Override
  public void toXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
    if (this.getXhc().hasAncestor("CatAql")) {
      // write start element
      xmlWriter.writeStartElement(this.getXhcName());
      xholon2xml.writeSpecial(this);
      // write children
      IXholon childNode = getFirstChild();
      while (childNode != null) {
        childNode.toXml(xholon2xml, xmlWriter);
        childNode = childNode.getNextSibling();
      }
      // write end element
      xmlWriter.writeEndElement(this.getXhcName());
    }
    else {
      super.toXml(xholon2xml, xmlWriter);
    }
  }
  
  @Override
  public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {}
  
}
