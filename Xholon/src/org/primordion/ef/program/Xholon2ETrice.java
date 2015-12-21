/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2015 Ken Webb
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.base.Control;
import org.primordion.xholon.base.IPort;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Port;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
import org.primordion.xholon.util.ClassHelper;

/**
 * Export a Xholon model in eTrice .room format.
 * To run the Java Standalone Generator on the resulting .room content (Linux):
 *   Save it to a file by copy and pasting from the Xholon tab.
 *   cd ~/etrice/generator-java
 *   java -jar org.eclipse.etrice.generator.java.jar HelloWorld.room
 * 
 * TODO 
 * - relay ports
 * - do more complete job with conjugated ports
 * - Protocol classes
 * - bindings
 * - relay port bindings
 * - multiplicity, replicated ports
 * - be able to handle all of the xmi apps (MagicDraw, TopCased, etc.)
 * - Behavior code
 * - Attributes
 * - Operations
 * - Data classes
 * - Layering (Xholon Services?)
 * - FSM TransitionPoint
 * - efParams
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on December 2, 2015)
 * @see <a href="http://www.eclipse.org/etrice/">eTrice web site</a>
 * @see <a href="http://www.primordion.com/Xholon/gwt/exports/etrice/index.html">samples of generated .room files</a>
 */
@SuppressWarnings("serial")
public class Xholon2ETrice extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat, CeStateMachineEntity {
  
  /**
   * In (almost) all Xholon apps, the conjugated (incoming) ports are implied,
   * and in fact the Actor itself receives messages directly and not through a port.
   * This is the default name of the Actor masquerading as a port.
   */
  private static final String CONJUGATED_PORTNAME_DEFAULT = "actorPort"; // port0 nullPort
  
  /**
   * All (?) conjugated actorPort ports should be replicated.
   */
  private static final String REPLICATED_PORT_DEFAULT = "[*]";
  
  /**
   * For now, I just have this one placeholder protocol.
   */
  private static final String PROTOCOL_DEFAULT = "TodoProtocol";
  
  // states involved in constructing a hierarchy of bindings
  //private static final int BINDING_STATE_INIT = 0; // this state may not be needed
  private static final int BINDING_STATE_UP_FIRST = 1; // processing the source node
  private static final int BINDING_STATE_UP = 2; // processing an intermediate node on the way up
  private static final int BINDING_STATE_COMMON_UP = 3; // processing the common ancestor of the source and remote nodes
  private static final int BINDING_STATE_COMMON_DOWN = 4; // processing the common ancestor of the source and remote nodes
  private static final int BINDING_STATE_DOWN = 5; // processing an intermediate node on the way down
  private static final int BINDING_STATE_DOWN_LAST = 6; // processing the remote node
  private static final int BINDING_STATE_FINAL = 9; // this state may not be needed
  
  private String outFileName;
  private String outPath = "./ef/etrice/";
  private String modelName;
  private String modelNameNoSpecials; // modelName with no special characters - space dash
  private IXholon root;
  private StringBuilder sb; // the top-level sb
  private StringBuilder sbTrans = null; // write transitions at the current level
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** Whether or not to show state machine nodes. */
  private boolean shouldShowStateMachineEntities = false;
  
  /** Template to use when writing out node names. */
  protected String nameTemplateCSH = "^^c_i^"; // TODO don't include the id ? maybe do include the role? "R^^^^^"
  protected String nameTemplateIH  = "^^C^^^"; // don't include role name
  
  protected Set<IXholonClass> xhClassSet;
  
  protected Map<IXholonClass,String> mapRelayPorts;
  protected Map<IXholonClass,String> mapBindings;
  protected Map<IXholonClass,String> mapInterface;
  protected Map<IXholonClass,String> mapStructure;
  protected Map<IXholonClass,String> mapBehavior;
  
  /**
   * Constructor.
   */
  public Xholon2ETrice() {}
  
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
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".room";
    }
    else {
      this.outFileName = fileName;
    }
    this.modelName = modelName;
    this.modelNameNoSpecials = replaceSpecials(modelName);
    this.root = root;
    
    xhClassSet = new LinkedHashSet<IXholonClass>(); // a Set that maintains the insertion order
    mapRelayPorts = new HashMap<IXholonClass,String>();
    mapBindings = new HashMap<IXholonClass,String>();
    mapInterface = new HashMap<IXholonClass,String>();
    mapStructure = new HashMap<IXholonClass,String>();
    mapBehavior = new HashMap<IXholonClass,String>();
    return true;
  }
  
  /**
   * Replace special characters in model name and role name.
   * @param str
   * @return 
   */
  protected String replaceSpecials(String str) {
    return str.replace(" ", "_").replace("-", "_");
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
   */
  public void writeAll() {
    sb = new StringBuilder(); // top-level sb
    sb.append(writeComment(new StringBuilder()));
    sb.append(writeRoomModelHeader(new StringBuilder()));
    //sb.append(writeNode(root, new StringBuilder()));
    writeNode(root, new StringBuilder()); // collect all the info in a set of Map objects
    writeMaps(); // write out the info in the Map objects
    sb.append(writeSuperClasses(new StringBuilder()));
    sb.append(writeProtocols(new StringBuilder()));
    sb.append("}\n");
    writeToTarget(sb.toString(), outFileName, outPath, root);
  }
  
  /**
   * Write out all the info, from the set of Map objects.
   */
  protected void writeMaps() {
    /*for (Map.Entry<IXholonClass, String> entry : mapInterface.entrySet()) {
        root.println(entry.getKey().getName() + "\n" + entry.getValue());
    }*/
    
    Iterator<IXholonClass> it = xhClassSet.iterator();
    while (it.hasNext()) {
      IXholonClass xhc = it.next();
      sb.append(writeActorClassStartLine(xhc, xhc.getName(), new StringBuilder()));
      
      // Interface
      String strInterface = mapInterface.get(xhc);
      String strRelayPorts = mapRelayPorts.get(xhc);
      if ((strInterface + strRelayPorts).length() > 0) {
        sb.append("    Interface {\n");
        if (strInterface != null && strInterface.length() > 0) {sb.append(strInterface);}
        if (strRelayPorts != null && strRelayPorts.length() > 0) {sb.append(strRelayPorts);}
        sb.append("    }\n");
      }
      
      // Structure
      String strStructure = mapStructure.get(xhc);
      String strBindings = mapBindings.get(xhc);
      if ((strStructure + strBindings).length() > 0) {
        sb.append("    Structure {\n");
        if (strStructure != null && strStructure.length() > 0) {sb.append(strStructure);}
        if (strBindings != null && strBindings.length() > 0) {sb.append(strBindings);}
        sb.append("    }\n");
      }
      
      // Behavior
      String strBehavior = mapBehavior.get(xhc);
      if ((strBehavior != null) && (strBehavior.length() > 0)) {
        sb.append("    Behavior {\n");
        sb.append(strBehavior);
        sb.append("    }\n");
      }
      
      sb.append("  }\n\n");
    }
  }
  
  /**
   * 
   */
  protected String writeComment(StringBuilder sbComment) {
    sbComment
    .append("/*\n")
    .append(" * Automatically generated by Xholon version 0.9.1, using Xholon2ETrice.java\n")
    .append(" * ").append(new Date()).append(" ").append(timeStamp).append("\n")
    .append(" * model: " + modelName + "\n")
    .append(" * www.primordion.com/Xholon\n")
    .append(" * \n")
    .append(" * To view and process this file, use eTrice from eclipse.org/etrice\n")
    .append(" */\n");
    return sbComment.toString();
  }
  
  /**
   * 
   */
  protected String writeRoomModelHeader(StringBuilder sbHeader) {
    sbHeader
    .append("RoomModel ").append(modelNameNoSpecials).append(" {\n\n")
    .append("  //import room.basic.types.* from \"../../org.eclipse.etrice.modellib.java/model/Types.room\"\n") // use inside Eclipse eTrice tool
    .append("  //import room.basic.service.timing.* from \"../../org.eclipse.etrice.modellib.java/model/TimingService.room\"\n")
    .append("  import room.basic.types.* from \"Types.room\"\n") // use with eTrice stand-alone generator
    .append("  import room.basic.service.timing.* from \"TimingService.room\"\n\n")
    .append("  LogicalSystem LogSys {\n")
    .append("    SubSystemRef subSystemRef: SubSysClass\n")
    .append("  }\n\n")
    .append("  SubSystemClass SubSysClass {\n")
    .append("    ActorRef topActor: ").append(root.getXhcName()).append("\n")
    .append("    ActorRef timingService: ATimingService\n")
    .append("    LayerConnection ref topActor satisfied_by timingService.timer\n")
    .append("    LogicalThread defaultThread\n")
    .append("  }\n\n")
    ;
    return sbHeader.toString();
  }
  
  /**
   * Composite Structure
   * @param node
   */
  @SuppressWarnings("unchecked")
  protected String writeNode(IXholon node, StringBuilder sbLocal) {
    // ignore state machine nodes; they are handled elsewhere in the code
    if (node.getXhcId() == StateMachineCE) {
      return "";
    }
    IXholonClass nodeIH = node.getXhc();
    
    // process children, and retrieve data about them
    StringBuilder sbChildren = new StringBuilder();
    if (node.hasChildNodes()) {
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        sbChildren.append(writeNode(childNode, new StringBuilder()));
        childNode = childNode.getNextSibling();
      }
    }
    
    // write info about this node
    List<PortInformation> portList = getXhPorts(node);
    List<IXholon> reffingNodesList = this.searchForReferencingNodes(node);
    if (xhClassSet.contains(nodeIH)) {
      // add possible entries to the relayPort and binding Map objects
      // ignore the values returned by these 3 methods
      writeInterfacePorts(node, portList, new StringBuilder());
      writeInterfaceConjugatedPorts(node, reffingNodesList, new StringBuilder());
      cacheStructureBindings(node, portList, new StringBuilder());
    }
    else {
      sbLocal.append(writeActorClassStartLine(node.getXhc(), nodeIH.getName(), new StringBuilder()));
      sbLocal.append(writeInterface(node, portList, reffingNodesList, new StringBuilder()));
      StringBuilder sbFsm = new StringBuilder();
      sbLocal.append(writeStructure(node, portList, reffingNodesList, sbFsm, new StringBuilder()));
      sbLocal.append(sbFsm.toString());
      sbLocal.append("  }\n\n");
      xhClassSet.add(nodeIH);
    }
    
    // write processed children
    sbLocal.append(sbChildren.toString());
    return sbLocal.toString();
  }
  
  /**
   * Get all outgoing ports associated with the current Xholon node.
   */
  protected List<PortInformation> getXhPorts(IXholon node) {
    List<PortInformation> portList = node.getAllPorts();
    // remove ports whose remoteNode is outside the scope (not a descendant) of root
    Iterator<PortInformation> it = portList.iterator();
    while (it.hasNext()) {
      PortInformation pi = (PortInformation)it.next();
      if (pi != null) {
        IXholon remoteNode = pi.getReffedNode();
        if (!remoteNode.hasAncestor(root.getName())) {
          it.remove();
        }
      }
    }
    return portList;
  }
  
  /**
   * Write an optional eTrice ActorClass Interface.
   * Do this only if there are ports.
   * @param node
   */
  protected String writeInterface(IXholon node, List<PortInformation> portList, List<IXholon> reffingNodesList, StringBuilder sbLocal) {
    String relayPortStr = mapRelayPorts.get(node.getXhc());
    if ((portList.size() > 0) || (reffingNodesList.size() > 0) || (relayPortStr != null)) {
      StringBuilder sbRelayPort = new StringBuilder();
      if (relayPortStr != null) {
        sbRelayPort.append(relayPortStr);
        //mapRelayPorts.remove(node.getXhc());
      }
      String interfacePorts = writeInterfacePorts(node, portList, new StringBuilder());
      String interfaceConjPorts = writeInterfaceConjugatedPorts(node, reffingNodesList, new StringBuilder());
      sbLocal.append("    Interface {\n");
      sbLocal.append(interfacePorts);
      sbLocal.append(sbRelayPort.toString());
      sbLocal.append(interfaceConjPorts);
      sbLocal.append("    }\n");
      
      // new Map
      putMap(mapInterface, node.getXhc(), interfacePorts);
      putMap(mapInterface, node.getXhc(), interfaceConjPorts);
      
    }
    return sbLocal.toString();
  }
  
  /**
   * Possibly save a port string to mapRelayPorts.
   * The port string is "sent" up the Xholon containment hierarchy to node+remoteNode's common ancestor.
   * Regular and conjugated ports are both written only to the Interface, and not to the Structure.
   */
  protected void cacheRelayPort(String localPortName, IXholon node, IXholon remoteNode, boolean isConjugated, String replication) {
    String conj = "";
    if (isConjugated) {
      conj = "conjugated ";
    }
    String portStr = new StringBuilder()
    .append("      ")
    //.append("// ")
    .append(conj)
    .append("Port ")
    .append(makeNameCSH(node))
    .append("_")
    .append(localPortName)
    .append(replication)
    .append(": ")
    .append(PROTOCOL_DEFAULT)
    .append(" // RELAY PORT ")
    .append("\n")
    .toString();
    
    IXholon commonAncestorNode = this.findFirstCommonAncestor(node, remoteNode);
    
    // create an entry in mapRelayPorts for each ancestor of node, above node and below commonAncestorNode
    IXholon ancestorNode = node.getParentNode();
    while ((ancestorNode != null) && (ancestorNode != commonAncestorNode)) {
      putMap(mapRelayPorts, ancestorNode.getXhc(), portStr);
      ancestorNode = ancestorNode.getParentNode();
    }
  }
  
  /**
   * Put a value into a Map.
   * @param map 
   * @param key 
   * @param value 
   */
  protected void putMap(final Map<IXholonClass,String> map, final IXholonClass key, final String value) {
    String v = value;
    String contents = map.get(key);
    if (contents != null) {
      v = contents + v;
    }
    map.put(key, v);
  }
  
  /**
   * Write Interface non-conjugated ports.
   */
  protected String writeInterfacePorts(IXholon node, List<PortInformation> portList, StringBuilder sbPorts) {
    for (int i = 0; i < portList.size(); i++) {
      PortInformation pi = (PortInformation)portList.get(i);
      if (pi != null) {
         String fnIndex = pi.getFieldNameIndexStr();
         if (fnIndex == null) {
           fnIndex = "";
         }
         IXholon remoteNode = pi.getReffedNode();
         // TODO use public boolean hasAncestor(String tnName) in Xholon.java, instead of getParentNode() ?
         if (remoteNode.getParentNode() == node) {
           // this is an internal end port, and should therefore not be added to the Interface
           continue;
         }
         sbPorts
         .append("      Port ")
         .append(pi.getFieldName())
         .append(fnIndex)
         .append(": ")
         .append(PROTOCOL_DEFAULT)
         .append("\n");
         cacheRelayPort(pi.getFieldName()+fnIndex, node, remoteNode, false, "");
      }
    }
    return sbPorts.toString();
  }
  
  /**
   * Write Interface conjugated ports.
   * @param reffedNode The node that is being referenced, the node that will have any conjugated ports
   * @param reffingNodesList A list of nodes that reference the reffedNode
   * @param sbPorts 
   */
  protected String writeInterfaceConjugatedPorts(IXholon reffedNode, List<IXholon> reffingNodesList, StringBuilder sbPorts) {
    boolean duplicate = false; // CONJUGATED_PORTNAME_DEFAULT is a replicated port, so it should only be written out once
    for (int i = 0; i < reffingNodesList.size(); i++) {
      IXholon reffingNode = reffingNodesList.get(i);
      if (reffingNode.getParentNode() != reffedNode) {
        // this is an external conjugated end port
        sbPorts
        .append("      ");
        if (duplicate) {
          sbPorts.append("// ");
        }
        sbPorts
        .append("conjugated Port ")
        .append(CONJUGATED_PORTNAME_DEFAULT)
        .append(REPLICATED_PORT_DEFAULT)
        .append(": ")
        .append(PROTOCOL_DEFAULT)
        .append(" // ")
        .append(reffingNode.getName())
        .append("\n");
        if (!duplicate) {
          cacheRelayPort(CONJUGATED_PORTNAME_DEFAULT, reffedNode, reffingNode, true, REPLICATED_PORT_DEFAULT);
          duplicate = true;
        }
      }
    }
    return sbPorts.toString();
  }
  
  /**
   * Write an optional eTrice ActorClass Structure.
   * @param node
   */
  protected String writeStructure(IXholon node, List<PortInformation> portList, List<IXholon> reffingNodesList, StringBuilder sbFsm, StringBuilder sbLocal) {
    cacheStructureBindings(node, portList, new StringBuilder());
    String ports = writeStructurePorts(node, portList, new StringBuilder());
    String conjPorts = writeStructureConjugatedPorts(node, reffingNodesList, new StringBuilder());
    String actorRefs = writeStructureActorRefs(node, new StringBuilder(), sbFsm);
    String bindings = writeStructureBindings(node, portList, new StringBuilder());
    if ((ports + conjPorts + actorRefs + bindings).length() > 0) {
      sbLocal
      .append("    Structure {\n")
      .append(ports)
      .append(conjPorts)
      .append(actorRefs)
      .append(bindings)
      .append("    }\n");
    }
    
    // new Map
    putMap(mapStructure, node.getXhc(), ports);
    putMap(mapStructure, node.getXhc(), conjPorts);
    putMap(mapStructure, node.getXhc(), actorRefs);
    
    return sbLocal.toString();
  }
  
  /**
   * 
   */
  protected String writeStructurePorts(IXholon node, List<PortInformation> portList, StringBuilder sbPorts) {
    for (int i = 0; i < portList.size(); i++) {
      PortInformation pi = (PortInformation)portList.get(i);
      if (pi != null) {
        String fnIndex = pi.getFieldNameIndexStr();
        if (fnIndex == null) {
          fnIndex = "";
        }
        sbPorts.append("      ");
        IXholon remoteNode = pi.getReffedNode();
        if (remoteNode.getParentNode() == node) {
          // this is an external end port
          sbPorts
          .append("Port ")
          .append(pi.getFieldName())
          .append(fnIndex)
          .append(": ")
          .append(PROTOCOL_DEFAULT)
          .append("\n");
        }
        else {
          // this is an internal end port
          sbPorts
          .append("external ")
          .append("Port ")
          .append(pi.getFieldName())
          .append(fnIndex)
          .append("\n");
        }
      }
    }
    return sbPorts.toString();
  }
  
  /**
   * 
   */
  protected String writeStructureConjugatedPorts(IXholon node, List<IXholon> reffingNodesList, StringBuilder sbPorts) {
    boolean duplicate = false;
    String duplicateStr = "// ";
    for (int i = 0; i < reffingNodesList.size(); i++) {
      IXholon reffingNode = reffingNodesList.get(i);
      if (reffingNode.getParentNode() == node) {
        // this is an internal conjugated end port
        sbPorts
        .append("      ");
        if (duplicate) {sbPorts.append(duplicateStr);} else {duplicate = true;}
        sbPorts
        .append("conjugated Port ")
        .append(CONJUGATED_PORTNAME_DEFAULT)
        .append(REPLICATED_PORT_DEFAULT)
        .append(": ")
        .append(PROTOCOL_DEFAULT)
        .append(" // ")
        .append(reffingNode.getName())
        .append("\n");
      }
      else {
        // this is an external conjugated end port
        sbPorts
        .append("      ");
        if (duplicate) {sbPorts.append(duplicateStr);} else {duplicate = true;}
        sbPorts
        .append("external Port ")
        .append(CONJUGATED_PORTNAME_DEFAULT)
        .append(" // ")
        .append(reffingNode.getName())
        .append("\n");
      }
    }
    return sbPorts.toString();
  }
  
  /**
   * 
   */
  protected String writeStructureActorRefs(IXholon node, StringBuilder sbActorRefs, StringBuilder sbFsm) {
    if (node.hasChildNodes()) {
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        if (childNode.getXhcId() == StateMachineCE) {
          writeBehaviorFsm(childNode, sbFsm);
        }
        else {
          sbActorRefs
          .append("      ActorRef ")
          .append(makeNameCSH(childNode))
          .append(": ")
          .append(childNode.getName(nameTemplateIH))
          .append("\n");
        }
        childNode = childNode.getNextSibling();
      }
    }
    return sbActorRefs.toString();
  }
  
  /**
   * 
   */
  protected void writeBehaviorFsm(IXholon fsmNode, StringBuilder sbFsm) {
    //sbFsm.append("    Behavior {\n");
    writeFsmNode(fsmNode, sbFsm, "     ");
    putMap(mapBehavior, fsmNode.getParentNode().getXhc(), sbFsm.toString());
    sbFsm.insert(0, "    Behavior {\n");
    sbFsm.append("    }\n");
  }
  
  /**
   * Write an FSM node and its children.
   */
  protected void writeFsmNode(IXholon fsmNode, StringBuilder sbFsm, String tab) {
    boolean shouldWriteEndBracket = true;
    switch (fsmNode.getXhcId()) {
    //case StateMachineEntityCE:
    case StateMachineCE:
      sbFsm
      .append(tab)
      .append("StateMachine {\n");
      sbTrans = new StringBuilder();
      break;
    case RegionCE:
      sbFsm
      .append(tab)
      .append("subgraph {\n");
      break;
    //case VertexCE:
    //case ConnectionPointReferenceCE: break;
    case StateCE:
    case FinalStateCE:
      sbFsm
      .append(tab)
      .append("State ")
      .append(makeNameCSH(fsmNode)) //fsmNode.getRoleName())
      .append(" {\n");
      // write transitions that exit from this State
      sbTrans.append(writeTransitions(fsmNode, new StringBuilder(), tab));
      break;
    //case PseudostateCE:
    //case PseudostateTerminateCE: break;
    //case PseudostateShallowHistoryCE: break;
    //case PseudostateJunctionCE: break;
    //case PseudostateJoinCE: break;
    case PseudostateInitialCE:
      // InitialPoint is implicit in the eTrice Trextual Notation, and does not need to be explicitly specified
      sbTrans.append(writeTransitions(fsmNode, new StringBuilder(), tab));
      shouldWriteEndBracket = false;
      break;
    //case PseudostateForkCE: break;
    case PseudostateExitPointCE:
      sbFsm
      .append(tab)
      .append("ExitPoint ")
      .append(makeNameCSH(fsmNode))
      .append(" {\n");
      sbTrans.append(writeTransitions(fsmNode, new StringBuilder(), tab));
      break;
    case PseudostateEntryPointCE:
      sbFsm
      .append(tab)
      .append("EntryPoint ")
      .append(makeNameCSH(fsmNode))
      .append(" {\n");
      sbTrans.append(writeTransitions(fsmNode, new StringBuilder(), tab));
      break;
    //case PseudostateDeepHistoryCE: break;
    case PseudostateChoiceCE:
      sbFsm
      .append(tab)
      .append("ChoicePoint ")
      .append(makeNameCSH(fsmNode))
      .append(" {\n");
      sbTrans.append(writeTransitions(fsmNode, new StringBuilder(), tab));
      break;
    //case TriggerCE: break; //triggers are handled as part of writeTransitions()
    case TransitionCE:
    case TransitionExternalCE:
    case TransitionInternalCE:
    case TransitionLocalCE:
      // transitions are handled by the State that links to them
      return;
    //case ActivityCE: break;
    //case GuardCE: break;
    case EntryActivityCE:
      sbFsm
      .append(tab)
      .append("entry")
      .append(" {\n")
      .append(tab).append(" ").append("\"// TODO\"\n");
      break;
    case ExitActivityCE:
      sbFsm
      .append(tab)
      .append("exit")
      .append(" {\n")
      .append(tab).append(" ").append("\"// TODO\"\n");
      break;
    //case DoActivityCE: break;
    //case DeferrableTriggerCE: break;
    //case TargetCE: break;
    
    default:
      shouldWriteEndBracket = false;
      sbFsm
      .append(tab)
      .append("// fsmNode - ")
      .append(fsmNode.getName())
      .append("\n");
      return;
    }
    IXholon childNode = fsmNode.getFirstChild();
    while (childNode != null) {
      writeFsmNode(childNode, sbFsm, tab + " ");
      childNode = childNode.getNextSibling();
    }
    switch (fsmNode.getXhcId()) {
    case StateMachineCE:
    case RegionCE:
      // write all the Transitions that have been cached for that level
      sbFsm.append(sbTrans.toString());
      sbTrans = new StringBuilder();
      break;
    default: break;
    }
    if (shouldWriteEndBracket) {
      sbFsm.append(tab).append("}\n");
    }
  }
  
  /**
   * Write all Transitions exiting from a specified State.
   * @param stNode A Xholon State or Final State
   */
  protected String writeTransitions(IXholon stNode, StringBuilder sbLocal, String tab) {
    IXholon[] cnpt = stNode.getPort();
    for (int i = 0; i < cnpt.length; i++) {
      IXholon transNode = cnpt[i];
      if (transNode != null) {
        IXholon destNode = transNode.getPort(0);
        if (destNode != null) {
          sbLocal
          .append(tab)
          .append("Transition ");
          
          switch (stNode.getXhcId()) {
          case PseudostateInitialCE:
            sbLocal
            .append("init: initial");
            break;
          default:
            sbLocal
            .append(makeNameCSH(transNode)) //transNode.getRoleName())
            .append(": ")
            .append(makeNameCSH(stNode));
            break;
          }
          
          sbLocal
          .append(" -> ")
          .append(makeNameCSH(destNode))
          .append(" {\n");
          
          // write triggers and other children of the Transition
          IXholon childNode = transNode.getFirstChild();
          while (childNode != null) {
            //writeFsmNode(childNode, sbLocal, tab + " ");
            switch (childNode.getXhcId()) {
            case TriggerCE:
              sbLocal
              .append(tab)
              .append(" triggers")
              .append(" {\n")
              .append(tab)
              .append("  ")
              .append("\"// TODO\"\n")
              .append(tab)
              .append(" }\n");
              break;
            default:
              sbLocal
              .append(tab)
              .append(" // Transition child - ")
              .append(childNode.getName())
              .append("\n");
              break;
            }
            childNode = childNode.getNextSibling();
          }
          sbLocal.append(tab).append("}\n");
        }
      }
    }
    return sbLocal.toString();
  }
  
  /**
   * 
   */
  protected String writeStructureBindings(IXholon node, List<PortInformation> portList, StringBuilder sbBindings) {
    String bindingStr = mapBindings.get(node.getXhc());
    if (bindingStr != null) {
      sbBindings.append(bindingStr);
      //mapBindings.remove(node.getXhc());
    }
    return sbBindings.toString();
  }
  
  /**
   * Cache bindings between nodes, by associating them with the 2 end nodes' common ancestor
   */
  protected void cacheStructureBindings(IXholon node, List<PortInformation> portList, StringBuilder sbBindings) {
    if (!isShouldShowBindings()) {return;}
    for (int i = 0; i < portList.size(); i++) {
      PortInformation pi = (PortInformation)portList.get(i);
      if (pi != null) {
        IXholon remoteNode = pi.getReffedNode();
        String fnIndex = pi.getFieldNameIndexStr();
        if (fnIndex == null) {
          fnIndex = "";
        }
        IXholon commonAncestorNode = this.findFirstCommonAncestor(node, remoteNode);
        
        int state = BINDING_STATE_UP_FIRST;
        int maxLoops = 100; // prevent an infinite loop
        IXholon bnode = node;
        IXholon commonSourceNode = null; // c_46
        String  commonSourcePortFn = "";
        String  commonSourcePortFnIx = "";
        IXholon commonTargetNode = null; // h_49
        IXholon ownerNode = bnode.getParentNode(); // b_47
        
        String srcNodeName = makeNameCSH(bnode);
        String srcNodePortSep = ".";
        String srcPortFn = pi.getFieldName();
        String srcPortFnIx = fnIndex;
        String trgtNodeName = "";
        String trgtNodePortSep = "";
        String trgtPortFn = srcNodeName + "_" + srcPortFn;
        String trgtPortFnIx = fnIndex;
        
        while ((state != BINDING_STATE_FINAL) && (maxLoops-- > 0)) {
          switch (state) {
          case BINDING_STATE_UP_FIRST:
          {
            if (ownerNode == commonAncestorNode) {
              state = BINDING_STATE_COMMON_UP;
              break;
            }
            String bindingStr = makeBindingStr(srcNodeName, srcNodePortSep, srcPortFn, srcPortFnIx, trgtNodeName, trgtNodePortSep, trgtPortFn, trgtPortFnIx);
            putBinding(ownerNode, bindingStr);
            bnode = ownerNode;
            ownerNode = bnode.getParentNode();
            srcNodeName = makeNameCSH(bnode);
            srcPortFn = trgtPortFn;
            state = BINDING_STATE_UP;
            break;
          }
          case BINDING_STATE_UP:
          {
            if (ownerNode == commonAncestorNode) {
              state = BINDING_STATE_COMMON_UP;
              break;
            }
            String bindingStr = makeBindingStr(srcNodeName, srcNodePortSep, srcPortFn, srcPortFnIx, trgtNodeName, trgtNodePortSep, trgtPortFn, trgtPortFnIx);
            putBinding(ownerNode, bindingStr);
            bnode = ownerNode;
            ownerNode = bnode.getParentNode();
            srcNodeName = makeNameCSH(bnode);
            state = BINDING_STATE_COMMON_UP;
            break;
          }
          case BINDING_STATE_COMMON_UP:
          {
            commonSourceNode = bnode; // cache these for later
            commonSourcePortFn = srcPortFn;
            commonSourcePortFnIx = srcPortFnIx;
            bnode = remoteNode;
            ownerNode = bnode.getParentNode();
            srcNodeName = "";
            srcNodePortSep = "";
            srcPortFn = makeNameCSH(bnode) + "_" + CONJUGATED_PORTNAME_DEFAULT;
            srcPortFnIx = "";
            trgtNodeName = makeNameCSH(bnode);
            trgtNodePortSep = ".";
            trgtPortFn = CONJUGATED_PORTNAME_DEFAULT;
            trgtPortFnIx = "";
            state = BINDING_STATE_DOWN_LAST;
            break;
          }
          case BINDING_STATE_COMMON_DOWN:
          {
            ownerNode = commonSourceNode.getParentNode();
            srcNodeName = makeNameCSH(commonSourceNode);
            srcNodePortSep = ".";
            srcPortFn = commonSourcePortFn;
            srcPortFnIx = commonSourcePortFnIx;
            trgtNodeName = makeNameCSH(commonTargetNode);
            String bindingStr = makeBindingStr(srcNodeName, srcNodePortSep, srcPortFn, srcPortFnIx, trgtNodeName, trgtNodePortSep, trgtPortFn, trgtPortFnIx);
            putBinding(ownerNode, bindingStr);
            state = BINDING_STATE_FINAL;
            break;
          }
          case BINDING_STATE_DOWN:
          {
            if (ownerNode == commonAncestorNode) {
              commonTargetNode = bnode;
              state = BINDING_STATE_COMMON_DOWN;
              break;
            }
            String bindingStr = makeBindingStr(srcNodeName, srcNodePortSep, srcPortFn, srcPortFnIx, trgtNodeName, trgtNodePortSep, trgtPortFn, trgtPortFnIx);
            putBinding(ownerNode, bindingStr);
            bnode = ownerNode;
            ownerNode = bnode.getParentNode();
            if (ownerNode == commonAncestorNode) {
              commonTargetNode = bnode;
              state = BINDING_STATE_COMMON_DOWN;
            }
            else {
              trgtNodeName = makeNameCSH(bnode);
            }
            break;
          }
          case BINDING_STATE_DOWN_LAST:
          {
            if (ownerNode == commonAncestorNode) {
              commonTargetNode = bnode;
              state = BINDING_STATE_COMMON_DOWN;
              break;
            }
            String bindingStr = makeBindingStr(srcNodeName, srcNodePortSep, srcPortFn, srcPortFnIx, trgtNodeName, trgtNodePortSep, trgtPortFn, trgtPortFnIx);
            putBinding(ownerNode, bindingStr);
            bnode = ownerNode;
            ownerNode = bnode.getParentNode();
            trgtNodeName = makeNameCSH(bnode);
            trgtPortFn = srcPortFn;
            state = BINDING_STATE_DOWN;
            break;
          }
          default: break;
          } // end switch
          
        } // end while
        
      } // end if
    } // end for
  } // end cacheStructureBindings()
  
  protected void putBinding(IXholon ownerNode, String bindingStr) {
    ownerNode.consoleLog(makeNameCSH(ownerNode) + " " + bindingStr);
    putMap(mapBindings, ownerNode.getXhc(), bindingStr);
  }
  
  /**
   * Make a Binding string.
   * @param srcNodeName Name of the source node (ex: "glucose_8")
   * @param srcNodePortSep Either of "." or ""
   * @param srcPortFn The srcNode field name used for this port (ex: "port" "world")
   * @param srcPortFnIx A "port" port array index, or ""
   * @param trgtNodeName Name of the target node (ex: "cellBilayer_5")
   * @param trgtNodePortSep Either of "." or ""
   * @param trgtPortFn 
   * @param trgtPortFnIx 
   * @return a Binding string that combines all the input params
   */
  protected String makeBindingStr(
      String srcNodeName,  String srcNodePortSep,  String srcPortFn,  String srcPortFnIx,
      String trgtNodeName, String trgtNodePortSep, String trgtPortFn, String trgtPortFnIx) {
    return new StringBuilder()
    .append("      ")
    .append("Binding ")
    .append(srcNodeName)
    .append(srcNodePortSep)
    .append(srcPortFn)
    .append(srcPortFnIx)
    .append(" and ")
    .append(trgtNodeName)
    .append(trgtNodePortSep)
    .append(trgtPortFn)
    .append(trgtPortFnIx)
    .append("\n")
    .toString();
  }
  
  /**
   * Make a CSH name for a node.
   */
  protected String makeNameCSH(IXholon node) {
    String rn = node.getRoleName();
    if ((rn == null) || (rn.length() == 0)) {
      return node.getName(nameTemplateCSH);
    }
    else {
      return replaceSpecials(rn);
    }
  }
  
  /**
   * Write the start line of an ActorClass.
   */
  protected String writeActorClassStartLine(IXholonClass xhcNode, String nameIH, StringBuilder sbLocal) {
    sbLocal
    .append("  ActorClass ")
    .append(nameIH);
    String nameSuperclassIH = xhcNode.getParentNode().getName();
    if (!"XholonClass".equals(nameIH)) {
      sbLocal.append(" extends ").append(nameSuperclassIH);
    }
    sbLocal.append(" {\n");
    return sbLocal.toString();
  }
  
  /**
   * Write all superclasses for each concrete class in xhClassNameSet.
   */
  protected String writeSuperClasses(StringBuilder sbLocal) {
    Set<IXholonClass> tempSet = new LinkedHashSet<IXholonClass>();
    Iterator<IXholonClass> it = xhClassSet.iterator();
    while (it.hasNext()) {
      IXholonClass xhcIH = it.next();
      if (xhcIH != null) {
        IXholonClass xhcParentIH = (IXholonClass)xhcIH.getParentNode();
        while ((xhcParentIH != null) && (!"XholonClass".equals(xhcParentIH.getName()))) {
          if (!xhClassSet.contains(xhcParentIH) && !tempSet.contains(xhcParentIH)) {
            // write an ActorClass
            sbLocal.append(writeActorClassStartLine(xhcParentIH, xhcParentIH.getName(), new StringBuilder()));
            sbLocal.append("  }\n\n");
            tempSet.add(xhcParentIH);
          }
          xhcParentIH = (IXholonClass)xhcParentIH.getParentNode();
        }
      }
    }
    sbLocal.append(writeXholonClassSuperClass(new StringBuilder()));
    return sbLocal.toString();
  }
  
  /**
   * Write XholonClass superclass at the end of the file.
   * I assume that the ports in XholonClass will be inherited by all subclasses
   */
  protected String writeXholonClassSuperClass(StringBuilder sbLocal) {
    IXholonClass xc = root.getClassNode("XholonClass");
    if (xc != null) {
      int maxPorts = Integer.parseInt(root.getApp().getParam("MaxPorts"));
      sbLocal.append(writeActorClassStartLine(xc, "XholonClass", new StringBuilder()));
      if (maxPorts > 0) {
        sbLocal.append("    Interface {\n");
        for (int i = 0; i < maxPorts; i++) {
          sbLocal
          .append("      // Port port")
          .append(i).append(": ")
          .append(PROTOCOL_DEFAULT)
          .append("\n");
        }
        sbLocal.append("    }\n");
        sbLocal.append("    Structure {\n");
        for (int j = 0; j < maxPorts; j++) {
          sbLocal.append("      // conjugated Port port")
          .append(j)
          .append("\n");
        }
        sbLocal.append("    }\n");
      }
      sbLocal.append("  }\n\n");
    }
    return sbLocal.toString();
  }
  
  /**
   * Write potocol classes.
   * For now, it writes a dummyy protocol.
   */
  protected String writeProtocols(StringBuilder sbLocal) {
    sbLocal
    .append("  ProtocolClass ").append(PROTOCOL_DEFAULT).append(" {\n")
    .append("    incoming {\n")
    .append("      Message sigA()\n")
    .append("    }\n")
    .append("    outgoing {\n")
    .append("      Message sigB()\n")
    .append("    }\n")
    .append("  }\n\n")
    ;
    return sbLocal.toString();
  }
  
  /**
   * see IXholon/Xholon searchForReferencingNodes()
   */
  public List<IXholon> searchForReferencingNodes(IXholon reffedNode)
  {
    List<IXholon> reffingNodes = new ArrayList<IXholon>();
    IXholon myRoot = reffedNode.getRootNode();
    if (myRoot.getClass() != Control.class) {
      this.searchForReferencingNodesRecurse(myRoot, reffedNode, reffingNodes);
    }
    return reffingNodes;
  }
  
  /**
   * Search for instances of Xholon with ports that reference this instance.
   * see IXholon/Xholon searchForReferencingNodesRecurse(...)
   * @param candidate A possible reffingNode.
   * @param reffedNode The Xholon node that we're looking for references to.
   * @param reffingNodes A list that is being filled with references.
   */
  public void searchForReferencingNodesRecurse(IXholon candidate, IXholon reffedNode, List<IXholon> reffingNodes)
  {
    IXholon[] port = candidate.getPort();
    if (port != null) {
      for (int i = 0; i < port.length; i++) {
        if (port[i] != null) {
          if (port[i] == reffedNode) {
            reffingNodes.add(candidate);
          }
          else if (ClassHelper.isAssignableFrom(Port.class, port[i].getClass())) {
            /*if (((IPort)port[i]).getLink() != null) {
              consoleLog(((IPort)port[i]).getLink().getName());
              consoleLog(((IPort)port[i]).getLink().getParentNode().getName());
              consoleLog(((IPort)port[i]).getLink().getParentNode().getParentNode());
            }
            if (((IPort)port[i]).getLink(0) != null) {
              consoleLog(((IPort)port[i]).getLink(0).getName());
              consoleLog(((IPort)port[i]).getLink(0).getParentNode().getName());
              consoleLog(((IPort)port[i]).getLink(0).getParentNode().getParentNode());
            }*/
            
            if ((((IPort)port[i]).getLink(0) != null) && (((IPort)port[i]).getLink(0).getParentNode() != null)) {
              //if (((IPort)port[i]).getLink() == reffedNode) {
              //  reffingNodes.add(this);
              //}
              /*if (((IPort)port[i]).getLink(0) == reffedNode) {
                reffingNodes.add(this);
              }*/
              //else if (((IPort)port[i]).getLink().getParentNode().getParentNode() == reffedNode) {
              //  reffingNodes.add(this);
              //}
              if (((IPort)port[i]).getLink(0).getParentNode().getParentNode() == reffedNode) {
                reffingNodes.add(candidate);
              }
            }
          }
        }
      }
    }
    if (candidate.getFirstChild() != null) {
      this.searchForReferencingNodesRecurse(candidate.getFirstChild(), reffedNode, reffingNodes);
    }
    // don't search nextSibling if this is xhRoot and nextSibling is srvRoot
    if ((candidate.getNextSibling() != null) && (candidate.getId() != 0)) {
      this.searchForReferencingNodesRecurse(candidate.getNextSibling(), reffedNode, reffingNodes);
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

  public String getOutPath() {
    return outPath;
  }

  public void setOutPath(String outPath) {
    this.outPath = outPath;
  }

  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.shouldShowStateMachineEntities = false;
    p.shouldShowBindings = true;
    this.efParams = p;
  }-*/;

  /** Whether or not to show state machine nodes. */
  public native boolean isShouldShowStateMachineEntities() /*-{return this.efParams.shouldShowStateMachineEntities;}-*/;
  //public native void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities) /*-{this.efParams.shouldShowStateMachineEntities = shouldShowStateMachineEntities;}-*/;

  /** Should eTrice Bindings be shown. */
  public native boolean isShouldShowBindings() /*-{return this.efParams.shouldShowBindings;}-*/;
  //public native void setShouldShowBindings(boolean shouldShowBindings) /*-{this.efParams.shouldShowBindings = shouldShowBindings;}-*/;

}
