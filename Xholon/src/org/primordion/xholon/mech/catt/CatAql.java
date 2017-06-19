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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Xholon;
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
  static final private String TERM_INSTANCE_MULTI_EQUATIONS = "multi_equations";
  static final private String TERM_INSTANCE_OPTIONS = "options";
  
  static final private String TERM_COMMENT = "//";
  
  static final private String DEFAULT_XHCNAME_SYSTEM = "CatTheorySystem";
  static final private String DEFAULT_XHCNAME_SCHEMA = "CatTheorySchema";
  static final private String DEFAULT_XHCNAME_INSTANCE = "CatTheoryInstance";
  static final private String DEFAULT_SCHEMA_NODE_SUFFIX = "s"; // "s" "Set" "Schema" ""  example: "Employee" becomes "Employees"
  
  private String xhcName_System = DEFAULT_XHCNAME_SYSTEM;
  private String xhcName_Schema = DEFAULT_XHCNAME_SCHEMA;
  private String xhcName_Instance = DEFAULT_XHCNAME_INSTANCE;
  private String schemaNodeSuffix = DEFAULT_SCHEMA_NODE_SUFFIX;
  private boolean separateSchemaInstance = true;
  private boolean pointToSchema = false; // whether or not an instance should point to the corresponding Set in the schema
  private boolean schemaRoleNameNoSuffix = true; // TODO whether or not a schema (set) node has a roleName, and that the roleName is the xhc name without the suffix
  
  // exports
  private boolean exportGraphviz = false;
  private boolean exportSql = false;
  private boolean exportXml = false;
  private boolean exportYaml = false;
  
  // TODO other terms and states
  
  private StringBuilder sbIh = null;
  private StringBuilder sbCd = null;
  private StringBuilder sbCsh = null;
  
  private String indent = "  ";
  
  private IApplication app = null;
  private IXholon xholonHelperService = null;
  private IXholon xhRoot = null;
  private IXholonClass xhcRoot = null;
  private IXholon cattSystem = null;
  
  private String roleName = null;
  
  private Map<String,String> xhClassNameReplacementMap = null;
  private Map<String,IXholon> aqlGenerator2XhNodeMap = null;
  
  /**
   * Whether or not to parse the AQL instance kind.
   */
  private boolean instances = true;
  
  /**
   * Whether or not to retain the schema nodes in the final Xholon tree.
   * This variable only has meaning if separateSchemaInstance = true .
   */
  private boolean schemas = true;
  
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
    if (this.val == null) {
      // the content may be inside a <Attribute_String/> node
      // this may occur if there's a <xi:include ... />
      IXholon contentNode = this.getFirstChild();
      if (contentNode != null) {
        this.val = contentNode.getVal_String();
      }
    }
    if (this.val == null) {
      this.println("CatAql does not contain any content.");
    }
    else {
      this.val = this.val.trim();
      if (this.val.length() > 100) {
        this.app = this.getApp();
        this.xhRoot = app.getRoot();
        this.xhcRoot = app.getXhcRoot();
        this.xhClassNameReplacementMap = new HashMap<String,String>();
        this.aqlGenerator2XhNodeMap = new HashMap<String,IXholon>();
        sbIh = new StringBuilder();
        sbCd = new StringBuilder();
        sbCsh = new StringBuilder();
        //this.println(val);
        parseAqlContent(val);
        //sbIh.append("</_-.XholonClass>\n");
        //sbCd.append("</xholonClassDetails>\n");
        //sbCsh.append("</CatTheorySystem>\n");
        //this.println("<XholonWorkbook>\n");
        //this.println(sbIh.toString());
        //this.println(sbCd.toString());
        //this.println(sbCsh.toString());
        //this.println("</XholonWorkbook>\n");
        if (this.exportXml) {
          this.exportXml(this.cattSystem, xhcName_Instance);
        }
        if (this.exportYaml) {
          this.exportYaml(this.cattSystem, xhcName_Instance);
        }
        if (this.exportSql) {
          this.exportSql(this.cattSystem, xhcName_Instance);
        }
        if (this.exportGraphviz) {
          this.exportGraphviz(this.cattSystem);
        }
      }
      else {
        this.println("CatAql does not contain any usable AQL content.");
      }
    }
    super.postConfigure();
  }
  
  @Override
  public void act() {
    // is there anything to do?
    super.act();
  }
  
  /**
   * Parse the AQL content.
   * This approach is temporary.
   * It parses exactly one "typeside", then exactly one "schema", and then exactly one "instance".
   */
  protected void parseAqlContent(String aqlContent) {
    sbIh.append("<_-.XholonClass>\n").append("<").append(xhcName_System).append("/>\n");
    if (separateSchemaInstance) {
      sbIh.append("<").append(xhcName_Schema).append("/>\n");
      sbIh.append("<").append(xhcName_Instance).append("/>\n");
    }
    sbCd.append("<xholonClassDetails>\n");
    sbCsh.append("<").append(xhcName_System).append(">\n");
    
    // typeside
    int posStart = aqlContent.indexOf(KIND_TYPESIDE, 0);
    if (posStart == -1) {return;}
    // "}" must be at the start of a new line
    int posEnd = aqlContent.indexOf("\n}", posStart);
    if (posEnd == -1) {return;}
    parseTypeside(aqlContent.substring(posStart,posEnd));
    
    // schema
    posStart = aqlContent.indexOf(KIND_SCHEMA, posEnd);
    if (posStart == -1) {return;}
    // "}" must be at the start of a new line
    posEnd = aqlContent.indexOf("\n}", posStart);
    if (posEnd == -1) {return;}
    parseSchema(aqlContent.substring(posStart,posEnd));
    
    sbIh.append("</_-.XholonClass>\n");
    sbCd.append("</xholonClassDetails>\n");
    sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, sbIh.toString(), xhcRoot);
    sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, sbCd.toString(), xhcRoot);
    
    sbCsh.append("</").append(xhcName_System).append(">\n");
    // paste in the System node and its collection/set children
    // specific instances will subsequently be pasted as children of the collecttion/set nodes
    sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, sbCsh.toString(), xhRoot);
    cattSystem = xhRoot.getLastChild();
    
    // instance
    if (instances) {
      posStart = aqlContent.indexOf(KIND_INSTANCE, posEnd);
      if (posStart == -1) {return;}
      // the content within "multi_equations" contains "{" and "}" within and at end of each line; so look for "}" preceded by "\n"
      posEnd = aqlContent.indexOf("\n}", posStart) + 1;
      if (posEnd == -1) {return;}
      parseInstance(aqlContent.substring(posStart,posEnd));
    }
    
    if (separateSchemaInstance) {
      // move the Instance nodes, and the Schema nodes, into separate subtrees
      //IXholon cattSystem = ((Xholon)xhRoot).getXPath().evaluate("descendant::" + xhcName_System, xhRoot);
      if (cattSystem != null) {
        IXholon sNode = null;
        if (this.schemas) {
          String sStr = new StringBuilder()
          .append("<").append(xhcName_Schema).append("/>")
          .toString();
          sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, sStr, cattSystem);
          sNode = cattSystem.getLastChild();
        }
        
        String iStr = new StringBuilder()
        .append("<").append(xhcName_Instance).append("/>")
        .toString();
        sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, iStr, cattSystem);
        IXholon iNode = cattSystem.getLastChild();
        
        IXholon schemaNode = cattSystem.getFirstChild();
        while ((schemaNode != null) && (schemaNode != sNode) && (schemaNode != iNode)) {
          this.consoleLog(schemaNode.getName());
          IXholon instanceNode = schemaNode.getFirstChild();
          while (instanceNode != null) {
            // move the instanceNode
            this.consoleLog(instanceNode.getName());
            IXholon instanceNodeNext = instanceNode.getNextSibling();
            instanceNode.removeChild();
            instanceNode.appendChild(iNode);
            instanceNode = instanceNodeNext;
          }
          // move the schemaNode
          IXholon schemaNodeNext = schemaNode.getNextSibling();
          schemaNode.removeChild();
          if (this.schemas) {
            schemaNode.appendChild(sNode);
          }
          schemaNode = schemaNodeNext;
        }
      }
    }
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
        //this.println("types");
        if (tokens.length > 1) {
          parseLineTypesideTypes(tokens, 1);
        }
        break;
      case TERM_TYPESIDE_CONSTANTS:
        state = TERM_TYPESIDE_CONSTANTS;
        //this.println("constants");
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
      case TERM_TYPESIDE_JAVA_TYPES:
        state = TERM_TYPESIDE_JAVA_TYPES;
        break;
      case TERM_TYPESIDE_JAVA_CONSTANTS:
        state = TERM_TYPESIDE_JAVA_CONSTANTS;
        break;
      case TERM_TYPESIDE_JAVA_FUNCTIONS:
        state = TERM_TYPESIDE_JAVA_FUNCTIONS;
        break;
      case TERM_TYPESIDE_OPTIONS:
        state = TERM_TYPESIDE_OPTIONS;
        //this.println("options");
        break;
      case TERM_COMMENT:
        // ignore rest of this line
        break;
      default:
        switch (state) {
        case TERM_TYPESIDE_TYPES: parseLineTypesideTypes(tokens, 0); break;
        case TERM_TYPESIDE_CONSTANTS: parseLineTypesideConstants(tokens, 0); break;
        case TERM_TYPESIDE_JAVA_TYPES: parseLineTypesideJavaTypes(tokens, 0); break;
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
      //this.println("  " + tokens[i]);
      String xhcName = makeXholonClassName(tokens[i]);
      sbIh.append(indent).append("<").append(xhcName).append("/>\n");
      //sbIh.append(indent).append("<").append(xhcName).append(schemaNodeSuffix).append("/>\n");
      sbCsh.append(indent).append("<").append(xhcName)
      //.append(schemaNodeSuffix)
      .append("/>\n");
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
        //this.println("  TYPE=" + token);
        break;
      }
      //this.println("  " + token);
    }
  }

  /**
   * string = "java.lang.String"    <Attribute_String/>
   * Integer = "java.lang.Integer"  <Attribute_int/>
   * String = "java.lang.String"
   * Double = "java.lang.Double"    <Attribute_double/>
   * Boolean = "java.lang.String" 
   * Date = "java.lang.String"
   */
  protected void parseLineTypesideJavaTypes(String[] tokens, int startIx) {
    String xhcName = makeXholonClassName(tokens[0]);
    sbIh.append(indent).append("<").append(xhcName).append("/>\n");
    //shIh.append(indent).append("<").append(xhcName).append(schemaNodeSuffix).append("/>\n");
    sbCsh.append(indent).append("<").append(xhcName)
    //.append(schemaNodeSuffix)
    .append("/>\n");
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
        //this.println("entities");
        if (tokens.length > 1) {
          parseLineSchemaEntities(tokens, 1);
        }
        break;
      case TERM_SCHEMA_FOREIGN_KEYS:
        state = TERM_SCHEMA_FOREIGN_KEYS;
        //this.println("foreign_keys");
        break;
      case TERM_SCHEMA_ATTRIBUTES:
        state = TERM_SCHEMA_ATTRIBUTES;
        //this.println("attributes");
        break;
      case TERM_SCHEMA_PATH_EQUATIONS:
        state = TERM_SCHEMA_PATH_EQUATIONS;
        //this.println("path_equations");
        break;
      case TERM_SCHEMA_OBSERVATION_EQUATIONS:
        state = TERM_SCHEMA_OBSERVATION_EQUATIONS;
        //this.println("observation_equations");
        break;
      case TERM_SCHEMA_OPTIONS:
        state = TERM_SCHEMA_OPTIONS;
        //this.println("options");
        break;
      case TERM_COMMENT:
        // ignore rest of this line
        break;
      default:
        switch (state) {
        case TERM_SCHEMA_ENTITIES: parseLineSchemaEntities(tokens, 0); break;
        case TERM_SCHEMA_FOREIGN_KEYS: parseLineSchemaForeignKeys(tokens, 0); break;
        case TERM_SCHEMA_ATTRIBUTES: parseLinesSchemaAttributes(tokens, 0); break;
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
      //this.println("  " + tokens[i]);
      String xhcName = makeXholonClassName(tokens[i]);
      sbIh.append(indent).append("<").append(xhcName).append("/>\n")
      .append(indent).append("<").append(xhcName).append(schemaNodeSuffix).append("/>\n");
      sbCd.append(indent).append("<").append(xhcName).append(" xhType=\"XhtypePureActiveObject\"").append("/>\n")
      .append(indent).append("<").append(xhcName).append(schemaNodeSuffix).append(" xhType=\"XhtypePureActiveObject\"").append("/>\n");
      sbCsh.append(indent).append("<").append(xhcName).append(schemaNodeSuffix);
      if (schemaRoleNameNoSuffix) {
        sbCsh.append(" roleName=\"").append(xhcName).append("\"");
      }
      sbCsh.append("/>\n");
    }
  }
  
  /**
   * manager   : Employee -> Employee
   * worksIn   : Employee -> Department
   * secretary : Department -> Employee
   */
  protected void parseLineSchemaForeignKeys(String[] tokens, int startIx) {
    sbCd.append(indent)
    .append("<").append(makeXholonClassName(tokens[2])).append(schemaNodeSuffix).append(">")
    .append("<port name=\"").append(makeXholonAttributeName(tokens[0])).append("\" connector=\"").append("../")
    .append(makeXholonClassName(tokens[4])).append(schemaNodeSuffix).append("\"/>")
    .append("</").append(makeXholonClassName(tokens[2])).append(schemaNodeSuffix).append(">\n");
  }
  
  /**
   * first last      : Employee -> string
   * age             : Employee -> nat
   * cummulative_age : Employee -> nat
   * name            : Department -> string
   */
  protected void parseLinesSchemaAttributes(String[] tokens, int startIx) {
    if (tokens.length - startIx < 5) {return;}
    if (!"->".equals(tokens[tokens.length - 2])) {return;}
    if (!":".equals(tokens[tokens.length - 4])) {return;}
    String xhcName = makeXholonClassName(tokens[tokens.length - 3]);
    
    String xhcTypeName = makeXholonClassName(tokens[tokens.length - 1]);
    
    for (int i = startIx; i < (tokens.length - 4); i++) {
      sbCd.append(indent)
      .append("<").append(xhcName).append(schemaNodeSuffix).append(">")
      .append("<port name=\"").append(makeXholonAttributeName(tokens[i])).append("\" connector=\"").append("../")
      .append(xhcTypeName)
      //.append(schemaNodeSuffix)
      .append("\"/>")
      .append("</").append(xhcName).append(schemaNodeSuffix).append(">\n");
    }
  }
  
  protected void parseInstance(String inst) {
    //this.println("parseInstance:\n" + inst + "\n");
    String[] lines = inst.split("\n");
    String state = TERM_INSTANCE_NULL;
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i].trim();
      String[] tokens = line.split("\\s+");
      if (tokens.length == 0) {continue;}
      switch (tokens[0]) {
      case TERM_INSTANCE_IMPORTS: break;
      case TERM_INSTANCE_GENERATORS:
        state = TERM_INSTANCE_GENERATORS;
        //this.println("generators");
        if (tokens.length > 1) {
          parseLineInstanceGenerators(tokens, 1);
        }
        break;
      case TERM_INSTANCE_EQUATIONS:
        state = TERM_INSTANCE_EQUATIONS;
        //this.println("equations");
        break;
      case TERM_INSTANCE_MULTI_EQUATIONS:
        state = TERM_INSTANCE_MULTI_EQUATIONS;
        //this.println("multi_equations");
        break;
      case TERM_INSTANCE_OPTIONS:
        state = TERM_INSTANCE_OPTIONS;
        //this.println("options");
        break;
      case TERM_COMMENT:
        // ignore rest of this line
        break;
      default:
        switch (state) {
        case TERM_INSTANCE_GENERATORS: parseLineInstanceGenerators(tokens, 0); break;
        case TERM_INSTANCE_EQUATIONS: parseLineInstanceEquations(tokens, 0); break;
        case TERM_INSTANCE_MULTI_EQUATIONS: parseLineInstanceMultiEquations(line.split("\\s+", 3), 0); break;
        default: break;
        }
        break;
      } // end switch(tokens[0])
    } // end for
  } // end method
  
  /**
   * a b c : Employee
   * m s : Department
   * d : Dummy
   */
  protected void parseLineInstanceGenerators(String[] tokens, int startIx) {
    if (tokens.length - startIx < 3) {return;}
    if (!":".equals(tokens[tokens.length - 2])) {return;}
    String xhcName = makeXholonClassName(tokens[tokens.length - 1]);
    IXholon xhSetNode = ((Xholon)xhRoot).getXPath().evaluate("descendant::" + xhcName + schemaNodeSuffix, cattSystem); //xhRoot);
    if (xhSetNode == null) {return;}
    for (int i = startIx; i < (tokens.length - 2); i++) {
      //this.println("  " + tokens[i]);
      //String str = "<" + xhcName + " roleName=\"" + tokens[i] + "\"/>";
      String endStr = "/>";
      if (pointToSchema) {
        // create a port that refs the node's parent
        endStr = "><port name=\"schema\" connector=\"../\"/></" + xhcName + ">";
      }
      String str = new StringBuilder()
      .append("<")
      .append(xhcName)
      .append(" roleName=\"")
      .append(tokens[i])
      .append("\"")
      .append(endStr)
      .toString();
      sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, str, xhSetNode);
      IXholon node = xhSetNode.getLastChild();
      this.aqlGenerator2XhNodeMap.put(tokens[i], node);
    }
  }
  
  /**
   * Handle parentheses notation:
   * first(a) = Al
   * first(b) = Bob
   * last(b) = Bo
   * first(c) = Carl 
   * name(m)  = Math
   * name(s) = CS
   * 
   * Handle other equation types:
   * manager(a) = b
   * 
   * Handle dot notation:
   * ps1.strategy_id = "1"
   * a.first = Al
   * a.manager = b
   */
  protected void parseLineInstanceEquations(String[] tokens, int startIx) {
    if (tokens.length != 3) {return;}
    String[] arr = null;
    if (tokens[0].indexOf("(") == -1) {
      // a.first = Al
      arr = this.splitDot(tokens[0]);
    }
    else {
      // first(a) = Al
      arr = this.splitParentheses(tokens[0]);
    }
    // a.first_ = "Al"
    String attributeNameArr0 = makeXholonAttributeName(arr[0]);
    //this.println(arr[1] + "." + attributeNameArr0 + " " + tokens[1] + " \"" + tokens[2] + "\"");
    IXholon node = this.aqlGenerator2XhNodeMap.get(arr[1]);
    if (node != null) {
      IXholon pnode = node.getParentNode();
      // Remove embedded double and single quote marks.
      String tokens2 = tokens[2].replace("\"", "").replace("'", "");
      // If pnode has a port/foreign_key called makeXholonAttributeName(arr[0]),
      // Then token[2] is the aqlName of a Xholon remoteNode which I can find in the Map.
      if (this.hasPort(pnode, attributeNameArr0)) {
        IXholon remoteNode = this.aqlGenerator2XhNodeMap.get(tokens2);
        if (remoteNode == null) {
          // the port is probably to a AQL type such as "string" or "nat"
          this.setInstanceAttribute(node, attributeNameArr0, tokens2);
        }
        else {
          setInstancePort(node, attributeNameArr0, remoteNode);
        }
      }
      else {
        this.setInstanceAttribute(node, attributeNameArr0, tokens2);
      }
    }
  }
  
  /**
   * Handle a "multi_equations" line. This is shorthand for multiple "equations" lines.
   * client_id -> {cc1 "1", cc2 "2", cc3 "3"}  CONVERT TO client_id(cc1) = "1"
   * client_name -> {cc1 Tom, cc2 Dick, cc3 Harry}
   * client_description -> {cc1 "Tom Client", cc2 "Dick Client", cc3 "Harry Client"}
   * abc -> {d E}
   */
  protected void parseLineInstanceMultiEquations(String[] tokens, int startIx) {
    if (tokens.length != 3) {return;}
    if (tokens[2].startsWith("{")) {tokens[2] = tokens[2].substring(1);} else {return;}
    if (tokens[2].endsWith("}")) {tokens[2] = tokens[2].substring(0, tokens[2].length()-1);} else {return;}
    String[] items = tokens[2].split(",");
    for (int i = 0; i < items.length; i++) {
      // convert into the format required by this.parseLineInstanceEquations(a,b)
      String[] eqTokens = new String[3];
      String[] item = items[i].trim().split(" ", 2);
      eqTokens[0] = tokens[0] + "(" + item[0] + ")";
      eqTokens[1] = "=";
      eqTokens[2] = item[1];
      this.parseLineInstanceEquations(eqTokens, 0);
    }
  }
  
  protected native void setInstanceAttribute(IXholon node, String attrName, String attrValue) /*-{
    node[attrName] = attrValue;
  }-*/;
  
  protected native boolean hasPort(IXholon node, String portName) /*-{
    var p = node[portName];
    if ((typeof p == "object") && (p != null)) {
      return true;
    }
    return false;
  }-*/;
  
  protected native void setInstancePort(IXholon node, String portName, IXholon remoteNode) /*-{
    node[portName] = remoteNode;
  }-*/;
  
  /**
   * @param "one(two)"
   * @return "one","two"
   */
  protected String[] splitParentheses(String str) {
    String[] arr = new String[2];
    arr[0] = arr[1] = "";
    int ix = 0;
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      switch (ch) {
      case '(': ix = 1; break;
      case ')': break;
      default:
        arr[ix] += ch;
        break;
      }
    }
    return arr;
  }
  
  /**
   * @param "two.one"
   * @return "one","two"
   */
  protected String[] splitDot(String str) {
    String[] arr = new String[2];
    arr[0] = arr[1] = "";
    int ix = 1;
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      switch (ch) {
      case '.': ix = 0; break;
      default:
        arr[ix] += ch;
        break;
      }
    }
    return arr;
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
    String outName = this.xhClassNameReplacementMap.get(inName);
    if (outName != null) {
      return outName;
    }
    
    IXholonClass ixhc = this.getClassNode(inName);
    if (ixhc != null) {
      outName = inName + "_";
      this.xhClassNameReplacementMap.put(inName, outName);
      return outName;
    }
    else {
      outName = inName;
      this.xhClassNameReplacementMap.put(inName, outName);
      return outName;
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
  
  protected native void exportGraphviz(IXholon cattSystem) /*-{
    $wnd.xh.xport("Graphviz", cattSystem, '{"shouldShowLinkLabels":true,"shouldSpecifyShape":true,"shouldSpecifyArrowhead":true,"shouldDisplayGraph":true}');
  }-*/;
  
  protected native void exportSql(IXholon cattSystem, String xhcName_Instance) /*-{
    $wnd.xh.xport("_nosql,Sql", cattSystem.xpath(xhcName_Instance), '{"parent":false,"idRoleXhcNames":"ID,,xhcID","idRoleXhcFormats":"^^^^i^,,^^^^i^","parentForeignKeys":false,"mechanismIhNodes":false}');
  }-*/;
  
  protected native void exportXml(IXholon cattSystem, String xhcName_Instance) /*-{
    $wnd.xh.xport("Xml", cattSystem, '{"writeStartDocument":false,"writePorts":true,"shouldWriteVal":false,"shouldWriteAllPorts":false}');
  }-*/;
  
  protected native void exportYaml(IXholon cattSystem, String xhcName_Instance) /*-{
    $wnd.xh.xport("Yaml", cattSystem,'{"writeStartDocument":true,"shouldWriteVal":false,"shouldWriteAllPorts":false}');
  }-*/;
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("val".equals(attrName)) {
      // this is the AQL content
      this.val = attrVal;
    }
    else if ("instances".equals(attrName)) {
      this.instances = Boolean.parseBoolean(attrVal);
    }
    else if ("schemas".equals(attrName)) {
      this.schemas = Boolean.parseBoolean(attrVal);
    }
    else if ("xhcName_System".equals(attrName)) {
      this.xhcName_System = attrVal;
    }
    else if ("xhcName_Schema".equals(attrName)) {
      this.xhcName_Schema = attrVal;
    }
    else if ("xhcName_Instance".equals(attrName)) {
      this.xhcName_Instance = attrVal;
    }
    else if (("schemaNodeSuffix".equals(attrName)) || ("setNodeSuffix".equals(attrName))) { // TODO change name from "set" to "schema", or allow both "set" and "schema"
      this.schemaNodeSuffix = attrVal;
    }
    else if ("pointToSchema".equals(attrName)) {
      this.pointToSchema = Boolean.parseBoolean(attrVal);
    }
    else if ("separateSchemaInstance".equals(attrName)) {
      this.separateSchemaInstance = Boolean.parseBoolean(attrVal);
    }
    else if ("schemaRoleNameNoSuffix".equals(attrName)) {
      this.schemaRoleNameNoSuffix = Boolean.parseBoolean(attrVal);
    }
    else if ("exportGraphviz".equals(attrName)) {
      this.exportGraphviz = Boolean.parseBoolean(attrVal);
    }
    else if ("exportSql".equals(attrName)) {
      this.exportSql = Boolean.parseBoolean(attrVal);
    }
    else if ("exportYaml".equals(attrName)) {
      this.exportYaml = Boolean.parseBoolean(attrVal);
    }
    else if ("exportXml".equals(attrName)) {
      this.exportXml = Boolean.parseBoolean(attrVal);
    }
    return 0;
  }
  
  @Override
  public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
    xmlWriter.writeAttribute("instances", Boolean.toString(this.instances));
    xmlWriter.writeAttribute("schemas", Boolean.toString(this.schemas));
    xmlWriter.writeAttribute("xhcName_System", this.xhcName_System);
    xmlWriter.writeAttribute("xhcName_Schema", this.xhcName_Schema);
    xmlWriter.writeAttribute("xhcName_Instance", this.xhcName_Instance);
    xmlWriter.writeAttribute("schemaNodeSuffix", this.schemaNodeSuffix);
    xmlWriter.writeAttribute("pointToSchema", Boolean.toString(this.pointToSchema));
    xmlWriter.writeAttribute("separateSchemaInstance", Boolean.toString(this.separateSchemaInstance));
    xmlWriter.writeAttribute("schemaRoleNameNoSuffix", Boolean.toString(this.schemaRoleNameNoSuffix));
    xmlWriter.writeAttribute("exportGraphviz", Boolean.toString(this.exportGraphviz));
    xmlWriter.writeAttribute("exportSql", Boolean.toString(this.exportSql));
    xmlWriter.writeAttribute("exportYaml", Boolean.toString(this.exportYaml));
    xmlWriter.writeAttribute("exportXml", Boolean.toString(this.exportXml));
  }
  
}
