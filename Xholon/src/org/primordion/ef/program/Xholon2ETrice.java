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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
 * - multiplicity, replicated ports
 * - be able to handle all of the xmi apps (MagicDraw, TopCased, etc.)
 * - Behavior code
 * - Attributes
 * - Operations
 * - Data classes
 * - Layering (Xholon Services?)
 * - FSM TransitionPoint
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on December 2, 2015)
 * @see <a href="http://www.eclipse.org/etrice/">eTrice web site</a>
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
   * For now, I just have this one placeholder protocol.
   */
  private static final String PROTOCOL_DEFAULT = "TodoProtocol";
  
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
  
  protected Set<String> xhClassNameSet;
  
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
    xhClassNameSet = new HashSet<String>();
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
    sb.append(writeNode(root, new StringBuilder()));
    sb.append(writeSuperClasses(new StringBuilder()));
    sb.append(writeProtocols(new StringBuilder()));
    sb.append("}\n");
    writeToTarget(sb.toString(), outFileName, outPath, root);
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
    //.append("  //import room.basic.types.* from \"../../org.eclipse.etrice.modellib.java/model/Types.room\"\n")
    //.append("  //import room.basic.service.timing.* from \"../../org.eclipse.etrice.modellib.java/model/TimingService.room\"\n\n")
    .append("  import room.basic.types.* from \"Types.room\"\n")
    .append("  import room.basic.service.timing.* from \"TimingService.room\"\n\n")
    .append("  LogicalSystem LogSys {\n")
    .append("    SubSystemRef subSystemRef: SubSysClass\n")
    .append("  }\n\n")
    .append("  SubSystemClass SubSysClass {\n")
    .append("    ActorRef topActor: ").append(root.getXhcName()).append("\n")
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
    String nameIH = node.getName(nameTemplateIH);
    
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
    if (!xhClassNameSet.contains(nameIH)) {
      sbLocal.append(writeActorClassStartLine(node.getXhc(), nameIH, new StringBuilder()));
      List<PortInformation> portList = getXhPorts(node);
      List<IXholon> reffingNodesList = this.searchForReferencingNodes(node);
      sbLocal.append(writeInterface(node, portList, reffingNodesList, new StringBuilder()));
      StringBuilder sbFsm = new StringBuilder();
      sbLocal.append(writeStructure(node, portList, reffingNodesList, sbFsm, new StringBuilder()));
      sbLocal.append(sbFsm.toString());
      sbLocal.append("  }\n\n");
      xhClassNameSet.add(nameIH);
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
    if ((portList.size() > 0) || (reffingNodesList.size() > 0)) {
      sbLocal.append("    Interface {\n");
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
          sbLocal
          .append("      Port ")
          .append(pi.getFieldName())
          .append(fnIndex)
          .append(": ")
          .append(PROTOCOL_DEFAULT)
          .append("\n");
        }
      }
      sbLocal.append(writeInterfaceConjugatedPorts(node, reffingNodesList, new StringBuilder()));
      sbLocal.append("    }\n");
    }
    return sbLocal.toString();
  }
  
  /**
   * 
   */
  protected String writeInterfaceConjugatedPorts(IXholon node, List<IXholon> reffingNodesList, StringBuilder sbPorts) {
    for (int i = 0; i < reffingNodesList.size(); i++) {
      IXholon reffingNode = reffingNodesList.get(i);
      if (reffingNode.getParentNode() != node) {
        // this is an external conjugated end port
        sbPorts
        .append("      //conjugated Port ")
        .append(CONJUGATED_PORTNAME_DEFAULT)
        .append(": ")
        .append(PROTOCOL_DEFAULT)
        .append(" // ")
        .append(reffingNode.getName())
        .append("\n");
      }
    }
    return sbPorts.toString();
  }
  
  /**
   * Write an optional eTrice ActorClass Structure.
   * @param node
   */
  protected String writeStructure(IXholon node, List<PortInformation> portList, List<IXholon> reffingNodesList, StringBuilder sbFsm, StringBuilder sbLocal) {
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
          // this is an external end port
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
    for (int i = 0; i < reffingNodesList.size(); i++) {
      IXholon reffingNode = reffingNodesList.get(i);
      if (reffingNode.getParentNode() == node) {
        // this is an internal conjugated end port
        sbPorts
        .append("      //conjugated Port ")
        .append(CONJUGATED_PORTNAME_DEFAULT)
        .append(": ")
        .append(PROTOCOL_DEFAULT)
        .append(" // ")
        .append(reffingNode.getName())
        .append("\n");
      }
      else {
        // this is an external conjugated end port
        sbPorts
        .append("      //external Port ")
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
    sbFsm.append("    Behavior {\n");
    writeFsmNode(fsmNode, sbFsm, "     ");
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
    for (int i = 0; i < portList.size(); i++) {
      PortInformation pi = (PortInformation)portList.get(i);
      if (pi != null) {
        String fnIndex = pi.getFieldNameIndexStr();
        if (fnIndex == null) {
          fnIndex = "";
        }
        IXholon remoteNode = pi.getReffedNode();
        //if (remoteNode == null) {continue;} // already checked in app.getAppSpecificObjectVals()
        //if (!remoteNode.hasAncestor(root.getName())) {
        //  // remoteNode is outside the scope (not a descendant) of root
        //  continue;
        //}
        sbBindings
        .append("      // Binding ")
        .append(pi.getFieldName())
        .append(fnIndex)
        .append(" and ")
        .append(makeNameCSH(remoteNode))
        .append(".")
        .append(CONJUGATED_PORTNAME_DEFAULT) // TODO can I determine the actual port name?
        .append("\n");
      }
    }
    return sbBindings.toString();
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
    Set<String> tempSet = new HashSet<String>();
    Iterator<String> it = xhClassNameSet.iterator();
    while (it.hasNext()) {
      String nameIH = it.next();
      IXholonClass xhcIH = root.getClassNode(nameIH);
      if (xhcIH != null) {
        IXholonClass xhcParentIH = (IXholonClass)xhcIH.getParentNode();
        while ((xhcParentIH != null) && (!"XholonClass".equals(xhcParentIH.getName()))) {
          String nameParentIH = xhcParentIH.getName();
          if (!xhClassNameSet.contains(nameParentIH) && !tempSet.contains(nameParentIH)) {
            // write an ActorClass
            sbLocal.append(writeActorClassStartLine(xhcParentIH, nameParentIH, new StringBuilder()));
            sbLocal.append("  }\n\n");
            tempSet.add(nameParentIH);
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
          //Error: ERROR:Duplicate name 'port0' in ActorClass 'XholonClass' (file://home/ken/etrice/generator-java/Cell___BioSystems_paper5.room line : 289)
          //Error: ERROR:name 'port0' is already assigned to PortXholonClass.port0 (file://home/ken/etrice/generator-java/Cell___BioSystems_paper5.room line : 54)
          sbLocal
          .append("      // Port port")
          .append(i).append(": ")
          .append(PROTOCOL_DEFAULT)
          .append("\n");
        }
        sbLocal.append("    }\n");
        sbLocal.append("    Structure {\n");
        for (int j = 0; j < maxPorts; j++) {
          //Error: ERROR:mismatched input 'conjugated' expecting ':' (file://home/ken/etrice/generator-java/Cell___BioSystems_paper5.room line : 300)
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
			    consoleLog(port[i].getClass().getName());
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
    p.shouldShowPortLabels = false;
    this.efParams = p;
  }-*/;

  /** Whether or not to show state machine nodes. */
  public native boolean isShouldShowStateMachineEntities() /*-{return this.efParams.shouldShowStateMachineEntities;}-*/;
  //public native void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities) /*-{this.efParams.shouldShowStateMachineEntities = shouldShowStateMachineEntities;}-*/;

  /** Should connections between nodes be shown with their port labels. */
  public native boolean isShouldShowPortLabels() /*-{return this.efParams.shouldShowPortLabels;}-*/;
  //public native void setShouldShowPortLabels(boolean shouldShowPortLabels) /*-{this.efParams.shouldShowPortLabels = shouldShowPortLabels;}-*/;

}
