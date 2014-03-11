package org.client;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Mechanism;
//import org.primordion.xholon.base.OrNode;
import org.primordion.xholon.base.Patch;
import org.primordion.xholon.base.PatchOwner;
import org.primordion.xholon.base.Port;
import org.primordion.xholon.base.Queue;
import org.primordion.xholon.base.QueueSynchronized;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.Turtle;
import org.primordion.xholon.base.XholonClass;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.mech.petrinet.AnalysisPetriNet;
import org.primordion.xholon.mech.petrinet.Arc;
import org.primordion.xholon.mech.petrinet.PetriNet;
import org.primordion.xholon.mech.petrinet.Place;
import org.primordion.xholon.mech.petrinet.QueueTransitions;
import org.primordion.xholon.mech.petrinet.Transition;
import org.primordion.xholon.service.mathscieng.Quantity;
import org.primordion.xholon.service.svg.SvgClient;

import java.util.ArrayList;
import java.util.List;

/**
 * This is manually generated from three AppGenerator-generated classes.
 * It needs to be a subclass of Application for AppGenerator to find it.
 */
public class SystemMechSpecific extends Application {
  
  public static final SystemMechSpecific instance = new SystemMechSpecific();
  
  @SuppressWarnings("unchecked")
public IXholon getAppSpecificObjectVal(IXholon node, Class<IXholon> clazz, String attrName) {
    if (node == null) {return null;}
    else if ("org.primordion.xholon.base.OrNode".equals(clazz.getName())) {
      if ("onlyChild".equalsIgnoreCase(attrName)) {
        return ((org.primordion.xholon.base.OrNode)node).getOnlyChild();
      }
    }
    else if ("org.primordion.xholon.base.Port".equals(clazz.getName())) {
      if ("link".equalsIgnoreCase(attrName)) {
        return ((org.primordion.xholon.base.Port)node).getLink();
      }
    }
    else if ("org.primordion.xholon.base.StateMachineEntity".equals(clazz.getName())) {
      if ("owningXholon".equalsIgnoreCase(attrName)) {
        return ((org.primordion.xholon.base.StateMachineEntity)node).getOwningXholon();
      }
    }
    else if ("org.primordion.xholon.mech.petrinet.Arc".equals(clazz.getName())) {
      if ("place".equalsIgnoreCase(attrName)) {
        return ((org.primordion.xholon.mech.petrinet.Arc)node).getPlace();
      }
    }
    else if ("org.primordion.xholon.mech.petrinet.Transition".equals(clazz.getName())) {
      if ("inputArcs".equalsIgnoreCase(attrName)) {
        return ((org.primordion.xholon.mech.petrinet.Transition)node).getInputArcs();
      }
      if ("outputArcs".equalsIgnoreCase(attrName)) {
        return ((org.primordion.xholon.mech.petrinet.Transition)node).getOutputArcs();
      }
    }
    else if ("org.primordion.xholon.mech.petrinet.QueueTransitions".equals(clazz.getName())) {
      if ("transitionsRoot".equalsIgnoreCase(attrName)) {
        return ((org.primordion.xholon.mech.petrinet.QueueTransitions)node).getTransitionsRoot();
      }
    }
    else if ("org.primordion.xholon.mech.petrinet.PetriNet".equals(clazz.getName())) {
      if ("transitionsRoot".equalsIgnoreCase(attrName)) {
        return ((org.primordion.xholon.mech.petrinet.PetriNet)node).getTransitionsRoot();
      }
    }
    else if ("org.primordion.xholon.mech.petrinet.grid.GridOwner".equals(clazz.getName())) {
      if ("petriNet".equalsIgnoreCase(attrName)) {
        return ((org.primordion.xholon.mech.petrinet.grid.GridOwner)node).getPetriNet();
      }
    }
    else if ("org.primordion.xholon.mech.petrinet.grid.PneBehavior".equals(clazz.getName())) {
      if ("pne".equalsIgnoreCase(attrName)) {
        return ((org.primordion.xholon.mech.petrinet.grid.PneBehavior)node).getPne();
      }
    }
    Class<?> superclass = (Class<?>)clazz.getSuperclass();
    if (superclass.getName().startsWith("org.primordion.xholon")) {
      return getAppSpecificObjectVal(node, (Class<IXholon>)superclass, attrName);
    }
    return null;
  }
  
  @SuppressWarnings("unchecked")
public String getAppSpecificObjectValNames(IXholon node, Class<IXholon> clazz) {
    String names = "";
    if (node == null) {return null;}
    else if ("org.primordion.xholon.base.OrNode".equals(clazz.getName())) {
      names = "onlyChild,";
    }
    else if ("org.primordion.xholon.base.Port".equals(clazz.getName())) {
      names = "link,";
    }
    else if ("org.primordion.xholon.base.StateMachineEntity".equals(clazz.getName())) {
      names = "owningXholon,";
    }
    else if ("org.primordion.xholon.mech.petrinet.Arc".equals(clazz.getName())) {
      names = "place,";
    }
    else if ("org.primordion.xholon.mech.petrinet.Transition".equals(clazz.getName())) {
      names = "inputArcs,outputArcs,";
    }
    else if ("org.primordion.xholon.mech.petrinet.QueueTransitions".equals(clazz.getName())) {
      names = "transitionsRoot,";
    }
    else if ("org.primordion.xholon.mech.petrinet.PetriNet".equals(clazz.getName())) {
      names = "transitionsRoot,";
    }
    else if ("org.primordion.xholon.mech.petrinet.grid.GridOwner".equals(clazz.getName())) {
      names = "petriNet,";
    }
    else if ("org.primordion.xholon.mech.petrinet.grid.PneBehavior".equals(clazz.getName())) {
      names = "pne,";
    }
    Class<?> superclass = (Class<?>)clazz.getSuperclass();
    if (superclass.getName().startsWith("org.primordion.xholon")) {
      names = names + getAppSpecificObjectValNames(node, (Class<IXholon>)superclass);
    }
    return names;
  }
  
  @SuppressWarnings("unchecked")
public boolean setAppSpecificObjectVal(IXholon node, Class<IXholon> clazz, String attrName, IXholon val) {
    if (node == null) {return false;}
    else if ("org.primordion.xholon.base.OrNode".equals(clazz.getName())) {
      if ("onlyChild".equalsIgnoreCase(attrName)) {
        ((org.primordion.xholon.base.OrNode)node).setOnlyChild((IXholon)val);
        return true;
      }
    }
    Class<?> superclass = (Class<?>)clazz.getSuperclass();
    if (superclass.getName().startsWith("org.primordion.xholon")) {
      return setAppSpecificObjectVal(node, (Class<IXholon>)superclass, attrName, val);
    }
    return false;
  }
  
  @SuppressWarnings("unchecked")
public Object getAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName) {
    if (node == null) {return null;}
    else if ("org.primordion.xholon.base.Turtle".equals(clazz.getName())) {
      if ("Breed".equalsIgnoreCase(attrName)) {return ((Turtle)node).getBreed();}
      if ("Color".equalsIgnoreCase(attrName)) {return ((Turtle)node).getColor();}
      if ("Heading".equalsIgnoreCase(attrName)) {return ((Turtle)node).getHeading();}
      if ("IsHidden".equalsIgnoreCase(attrName)) {return ((Turtle)node).getIsHidden();}
      if ("Label".equalsIgnoreCase(attrName)) {return ((Turtle)node).getLabel();}
      if ("PenMode".equalsIgnoreCase(attrName)) {return ((Turtle)node).getPenMode();}
      if ("Who".equalsIgnoreCase(attrName)) {return ((Turtle)node).getWho();}
      if ("Xcor".equalsIgnoreCase(attrName)) {return ((Turtle)node).getXcor();}
      if ("Ycor".equalsIgnoreCase(attrName)) {return ((Turtle)node).getYcor();}
      if ("Pcolor".equalsIgnoreCase(attrName)) {return ((Turtle)node).getPcolor();}
      if ("Plabel".equalsIgnoreCase(attrName)) {return ((Turtle)node).getPlabel();}
      if ("Pxcor".equalsIgnoreCase(attrName)) {return ((Turtle)node).getPxcor();}
      if ("Pycor".equalsIgnoreCase(attrName)) {return ((Turtle)node).getPycor();}
      if ("MaxPxcor".equalsIgnoreCase(attrName)) {return ((Turtle)node).getMaxPxcor();}
      if ("MaxPycor".equalsIgnoreCase(attrName)) {return ((Turtle)node).getMaxPycor();}
      if ("MinPxcor".equalsIgnoreCase(attrName)) {return ((Turtle)node).getMinPxcor();}
      if ("MinPycor".equalsIgnoreCase(attrName)) {return ((Turtle)node).getMinPycor();}
      if ("WorldWidth".equalsIgnoreCase(attrName)) {return ((Turtle)node).getWorldWidth();}
      if ("WorldHeight".equalsIgnoreCase(attrName)) {return ((Turtle)node).getWorldHeight();}
    }
    else if ("org.primordion.xholon.base.XholonClass".equals(clazz.getName())) {
      if ("QName".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getQName();}
      if ("PrefixedName".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getPrefixedName();}
      if ("Prefix".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getPrefix();}
      if ("LocalPart".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getLocalPart();}
      if ("Prefixed".equalsIgnoreCase(attrName)) {return ((XholonClass)node).isPrefixed();}
      if ("NavInfo".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getNavInfo();}
      if ("PortInformation".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getPortInformation();}
      if ("XhType".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getXhType();}
      if ("XhTypeName".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getXhTypeName();}
      if ("XhcName".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getXhcName();}
      if ("Uri".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getUri();}
      if ("Container".equalsIgnoreCase(attrName)) {return ((XholonClass)node).isContainer();}
      if ("ActiveObject".equalsIgnoreCase(attrName)) {return ((XholonClass)node).isActiveObject();}
      if ("PassiveObject".equalsIgnoreCase(attrName)) {return ((XholonClass)node).isPassiveObject();}
      if ("ImplName".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getImplName();}
      if ("ConfigurationInstructions".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getConfigurationInstructions();}
      if ("ChildSuperClass".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getChildSuperClass();}
      if ("Xml2Xholon".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getXml2Xholon();}
      if ("Color".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getColor();}
      if ("Font".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getFont();}
      if ("Icon".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getIcon();}
      if ("ToolTip".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getToolTip();}
      if ("Symbol".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getSymbol();}
      if ("Format".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getFormat();}
      if ("DefaultContent".equalsIgnoreCase(attrName)) {return ((XholonClass)node).getDefaultContent();}
    }
    else if ("org.primordion.xholon.base.QueueSynchronized".equals(clazz.getName())) {
      if ("Size".equalsIgnoreCase(attrName)) {return ((QueueSynchronized)node).getSize();}
      if ("MaxSize".equalsIgnoreCase(attrName)) {return ((QueueSynchronized)node).getMaxSize();}
      if ("Items".equalsIgnoreCase(attrName)) {return ((QueueSynchronized)node).getItems();}
    }
    else if ("org.primordion.xholon.base.Patch".equals(clazz.getName())) {
      if ("Pcolor".equalsIgnoreCase(attrName)) {return ((Patch)node).getPcolor();}
      if ("Plabel".equalsIgnoreCase(attrName)) {return ((Patch)node).getPlabel();}
      if ("Pxcor".equalsIgnoreCase(attrName)) {return ((Patch)node).getPxcor();}
      if ("Pycor".equalsIgnoreCase(attrName)) {return ((Patch)node).getPycor();}
    }
    else if ("org.primordion.xholon.base.Port".equals(clazz.getName())) {
      if ("ProvidedInterface".equalsIgnoreCase(attrName)) {return ((Port)node).getProvidedInterface();}
      if ("RequiredInterface".equalsIgnoreCase(attrName)) {return ((Port)node).getRequiredInterface();}
      if ("IsConjugated".equalsIgnoreCase(attrName)) {return ((Port)node).getIsConjugated();}
      if ("Name".equalsIgnoreCase(attrName)) {return ((Port)node).getName();}
    }
    else if ("org.primordion.xholon.base.StateMachineEntity".equals(clazz.getName())) {
      if ("Composite".equalsIgnoreCase(attrName)) {return ((StateMachineEntity)node).isComposite();}
      if ("Orthogonal".equalsIgnoreCase(attrName)) {return ((StateMachineEntity)node).isOrthogonal();}
      if ("Simple".equalsIgnoreCase(attrName)) {return ((StateMachineEntity)node).isSimple();}
      if ("ActiveSubState".equalsIgnoreCase(attrName)) {return ((StateMachineEntity)node).isActiveSubState();}
      if ("Uid".equalsIgnoreCase(attrName)) {return ((StateMachineEntity)node).getUid();}
      if ("ActivityId".equalsIgnoreCase(attrName)) {return ((StateMachineEntity)node).getActivityId();}
      if ("EntryActivityId".equalsIgnoreCase(attrName)) {return ((StateMachineEntity)node).getEntryActivityId();}
      if ("ExitActivityId".equalsIgnoreCase(attrName)) {return ((StateMachineEntity)node).getExitActivityId();}
      if ("DoActivityId".equalsIgnoreCase(attrName)) {return ((StateMachineEntity)node).getDoActivityId();}
      if ("GuardActivityId".equalsIgnoreCase(attrName)) {return ((StateMachineEntity)node).getGuardActivityId();}
    }
    else if ("org.primordion.xholon.base.Queue".equals(clazz.getName())) {
      if ("Size".equalsIgnoreCase(attrName)) {return ((Queue)node).getSize();}
      if ("MaxSize".equalsIgnoreCase(attrName)) {return ((Queue)node).getMaxSize();}
      if ("Items".equalsIgnoreCase(attrName)) {return ((Queue)node).getItems();}
    }
    else if ("org.primordion.xholon.base.Mechanism".equals(clazz.getName())) {
      if ("RoleName".equalsIgnoreCase(attrName)) {return ((Mechanism)node).getRoleName();}
      if ("NamespaceUri".equalsIgnoreCase(attrName)) {return ((Mechanism)node).getNamespaceUri();}
      if ("DefaultPrefix".equalsIgnoreCase(attrName)) {return ((Mechanism)node).getDefaultPrefix();}
      if ("RangeStart".equalsIgnoreCase(attrName)) {return ((Mechanism)node).getRangeStart();}
      if ("RangeEnd".equalsIgnoreCase(attrName)) {return ((Mechanism)node).getRangeEnd();}
      if ("Members".equalsIgnoreCase(attrName)) {return ((Mechanism)node).getMembers();}
      if ("Color".equalsIgnoreCase(attrName)) {return ((Mechanism)node).getColor();}
      if ("Font".equalsIgnoreCase(attrName)) {return ((Mechanism)node).getFont();}
      if ("Icon".equalsIgnoreCase(attrName)) {return ((Mechanism)node).getIcon();}
      if ("ToolTip".equalsIgnoreCase(attrName)) {return ((Mechanism)node).getToolTip();}
      if ("Symbol".equalsIgnoreCase(attrName)) {return ((Mechanism)node).getSymbol();}
      if ("Format".equalsIgnoreCase(attrName)) {return ((Mechanism)node).getFormat();}
      if ("Name".equalsIgnoreCase(attrName)) {return ((Mechanism)node).getName();}
    }
    else if ("org.primordion.xholon.base.XholonWithPorts".equals(clazz.getName())) {
      if ("Port".equalsIgnoreCase(attrName)) {return ((XholonWithPorts)node).getPort();}
    }
    else if ("org.primordion.xholon.base.AbstractGrid".equals(clazz.getName())) {
      if ("NeighType".equalsIgnoreCase(attrName)) {return ((AbstractGrid)node).getNeighType();}
      if ("NumNeighbors".equalsIgnoreCase(attrName)) {return ((AbstractGrid)node).getNumNeighbors();}
      if ("MaxPorts".equalsIgnoreCase(attrName)) {return ((AbstractGrid)node).getMaxPorts();}
      if ("AllPorts".equalsIgnoreCase(attrName)) {return ((AbstractGrid)node).getAllPorts();}
    }
    else if ("org.primordion.xholon.base.PatchOwner".equals(clazz.getName())) {
      if ("MaxPxcor".equalsIgnoreCase(attrName)) {return ((PatchOwner)node).getMaxPxcor();}
      if ("MaxPycor".equalsIgnoreCase(attrName)) {return ((PatchOwner)node).getMaxPycor();}
      if ("MinPxcor".equalsIgnoreCase(attrName)) {return ((PatchOwner)node).getMinPxcor();}
      if ("MinPycor".equalsIgnoreCase(attrName)) {return ((PatchOwner)node).getMinPycor();}
      if ("WorldWidth".equalsIgnoreCase(attrName)) {return ((PatchOwner)node).getWorldWidth();}
      if ("WorldHeight".equalsIgnoreCase(attrName)) {return ((PatchOwner)node).getWorldHeight();}
    }
    else if ("org.primordion.xholon.mech.petrinet.Arc".equals(clazz.getName())) {
      if ("Active".equalsIgnoreCase(attrName)) {return ((Arc)node).isActive();}
      if ("Connector".equalsIgnoreCase(attrName)) {return ((Arc)node).getConnector();}
    }
    else if ("org.primordion.xholon.mech.petrinet.Transition".equals(clazz.getName())) {
      if ("P".equalsIgnoreCase(attrName)) {return ((Transition)node).getP();}
      if ("K".equalsIgnoreCase(attrName)) {return ((Transition)node).getK();}
      if ("Count".equalsIgnoreCase(attrName)) {return ((Transition)node).getCount();}
      if ("KineticsType".equalsIgnoreCase(attrName)) {return ((Transition)node).getKineticsType();}
      if ("Vmax".equalsIgnoreCase(attrName)) {return ((Transition)node).getVmax();}
      if ("Km".equalsIgnoreCase(attrName)) {return ((Transition)node).getKm();}
      if ("Dt".equalsIgnoreCase(attrName)) {return ((Transition)node).getDt();}
      if ("Symbol".equalsIgnoreCase(attrName)) {return ((Transition)node).getSymbol();}
    }
    else if ("org.primordion.xholon.mech.petrinet.AnalysisPetriNet".equals(clazz.getName())) {
      if ("ActionList".equalsIgnoreCase(attrName)) {return ((AnalysisPetriNet)node).getActionList();}
      if ("Places".equalsIgnoreCase(attrName)) {return ((AnalysisPetriNet)node).getPlaces();}
      if ("Transitions".equalsIgnoreCase(attrName)) {return ((AnalysisPetriNet)node).getTransitions();}
      if ("TokensCutoff".equalsIgnoreCase(attrName)) {return ((AnalysisPetriNet)node).getTokensCutoff();}
    }
    else if ("org.primordion.xholon.mech.petrinet.QueueTransitions".equals(clazz.getName())) {
      if ("ShouldShuffle".equalsIgnoreCase(attrName)) {return ((QueueTransitions)node).isShouldShuffle();}
      if ("ShouldAct".equalsIgnoreCase(attrName)) {return ((QueueTransitions)node).isShouldAct();}
      if ("Connector".equalsIgnoreCase(attrName)) {return ((QueueTransitions)node).getConnector();}
      if ("TimeStepMultiplier".equalsIgnoreCase(attrName)) {return ((QueueTransitions)node).getTimeStepMultiplier();}
    }
    else if ("org.primordion.xholon.mech.petrinet.Place".equals(clazz.getName())) {
      if ("Token".equalsIgnoreCase(attrName)) {return ((Place)node).getToken();}
      if ("Units".equalsIgnoreCase(attrName)) {return ((Place)node).getUnits();}
    }
    else if ("org.primordion.xholon.mech.petrinet.PetriNet".equals(clazz.getName())) {
      if ("Transitions".equalsIgnoreCase(attrName)) {return ((PetriNet)node).getTransitions();}
      if ("TransitionsFromQ".equalsIgnoreCase(attrName)) {return ((PetriNet)node).getTransitionsFromQ();}
      if ("Places".equalsIgnoreCase(attrName)) {return ((PetriNet)node).getPlaces();}
      if ("KineticsType".equalsIgnoreCase(attrName)) {return ((PetriNet)node).getKineticsType();}
      if ("P".equalsIgnoreCase(attrName)) {return ((PetriNet)node).getP();}
      if ("K".equalsIgnoreCase(attrName)) {return ((PetriNet)node).getK();}
      if ("TimeStepMultiplier".equalsIgnoreCase(attrName)) {return ((PetriNet)node).getTimeStepMultiplier();}
      if ("Dt".equalsIgnoreCase(attrName)) {return ((PetriNet)node).getDt();}
      if ("ChartInterval".equalsIgnoreCase(attrName)) {return ((PetriNet)node).getChartInterval();}
    }
    else if ("org.primordion.xholon.service.mathscieng.Quantity".equals(clazz.getName())) {
      if ("Unit".equalsIgnoreCase(attrName)) {return ((Quantity)node).getUnit();}
    }
    else if ("org.primordion.xholon.service.svg.SvgClient".equals(clazz.getName())) {
      if ("Setup".equalsIgnoreCase(attrName)) {return ((SvgClient)node).getSetup();}
      if ("ViewBehavior".equalsIgnoreCase(attrName)) {return ((SvgClient)node).getViewBehavior();}
    }
    Class<?> superclass = (Class<?>)clazz.getSuperclass();
    if ((superclass != null) && superclass.getName().startsWith("org.primordion.xholon")) {
      return getAppSpecificAttribute(node, (Class<IXholon>)superclass, attrName);
    }
    return null;
  }
  
  @SuppressWarnings("unchecked")
public void setAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName, Object attrVal) {
    try {
      if (node == null) {return;}
      else if ("org.primordion.xholon.base.Turtle".equals(clazz.getName())) {
        if ("Breed".equalsIgnoreCase(attrName)) {((Turtle)node).setBreed(Integer.parseInt((String)attrVal));return;}
        if ("Color".equalsIgnoreCase(attrName)) {((Turtle)node).setColor(Integer.parseInt((String)attrVal));return;}
        if ("Heading".equalsIgnoreCase(attrName)) {((Turtle)node).setHeading(Double.parseDouble((String)attrVal));return;}
        if ("IsHidden".equalsIgnoreCase(attrName)) {((Turtle)node).setIsHidden(Boolean.parseBoolean((String)attrVal));return;}
        if ("Label".equalsIgnoreCase(attrName)) {((Turtle)node).setLabel((String)attrVal);return;}
        if ("PenMode".equalsIgnoreCase(attrName)) {((Turtle)node).setPenMode(Integer.parseInt((String)attrVal));return;}
        if ("Who".equalsIgnoreCase(attrName)) {((Turtle)node).setWho(Integer.parseInt((String)attrVal));return;}
        if ("Xcor".equalsIgnoreCase(attrName)) {((Turtle)node).setXcor(Double.parseDouble((String)attrVal));return;}
        if ("Ycor".equalsIgnoreCase(attrName)) {((Turtle)node).setYcor(Double.parseDouble((String)attrVal));return;}
        if ("Pcolor".equalsIgnoreCase(attrName)) {((Turtle)node).setPcolor(Integer.parseInt((String)attrVal));return;}
        if ("Plabel".equalsIgnoreCase(attrName)) {((Turtle)node).setPlabel((String)attrVal);return;}
        if ("Pxcor".equalsIgnoreCase(attrName)) {((Turtle)node).setPxcor(Integer.parseInt((String)attrVal));return;}
        if ("Pycor".equalsIgnoreCase(attrName)) {((Turtle)node).setPycor(Integer.parseInt((String)attrVal));return;}
      }
      else if ("org.primordion.xholon.base.XholonClass".equals(clazz.getName())) {
        if ("Color".equalsIgnoreCase(attrName)) {((XholonClass)node).setColor((String)attrVal);return;}
        if ("Font".equalsIgnoreCase(attrName)) {((XholonClass)node).setFont((String)attrVal);return;}
        if ("Icon".equalsIgnoreCase(attrName)) {((XholonClass)node).setIcon((String)attrVal);return;}
        if ("ToolTip".equalsIgnoreCase(attrName)) {((XholonClass)node).setToolTip((String)attrVal);return;}
        if ("Symbol".equalsIgnoreCase(attrName)) {((XholonClass)node).setSymbol((String)attrVal);return;}
        if ("Format".equalsIgnoreCase(attrName)) {((XholonClass)node).setFormat((String)attrVal);return;}
        if ("DefaultContent".equalsIgnoreCase(attrName)) {((XholonClass)node).setDefaultContent((String)attrVal);return;}
      }
      else if ("org.primordion.xholon.base.Patch".equals(clazz.getName())) {
        if ("Pcolor".equalsIgnoreCase(attrName)) {((Patch)node).setPcolor(Integer.parseInt((String)attrVal));return;}
        if ("Plabel".equalsIgnoreCase(attrName)) {((Patch)node).setPlabel((String)attrVal);return;}
        if ("Pxcor".equalsIgnoreCase(attrName)) {((Patch)node).setPxcor(Integer.parseInt((String)attrVal));return;}
        if ("Pycor".equalsIgnoreCase(attrName)) {((Patch)node).setPycor(Integer.parseInt((String)attrVal));return;}
      }
      else if ("org.primordion.xholon.base.Mechanism".equals(clazz.getName())) {
        if ("Color".equalsIgnoreCase(attrName)) {((Mechanism)node).setColor((String)attrVal);return;}
        if ("Font".equalsIgnoreCase(attrName)) {((Mechanism)node).setFont((String)attrVal);return;}
        if ("Icon".equalsIgnoreCase(attrName)) {((Mechanism)node).setIcon((String)attrVal);return;}
        if ("ToolTip".equalsIgnoreCase(attrName)) {((Mechanism)node).setToolTip((String)attrVal);return;}
        if ("Symbol".equalsIgnoreCase(attrName)) {((Mechanism)node).setSymbol((String)attrVal);return;}
        if ("Format".equalsIgnoreCase(attrName)) {((Mechanism)node).setFormat((String)attrVal);return;}
      }
      else if ("org.primordion.xholon.mech.petrinet.PetriNet".equals(clazz.getName())) {
        if ("KineticsType".equalsIgnoreCase(attrName)) {((PetriNet)node).setKineticsType(Integer.parseInt((String)attrVal));return;}
        if ("P".equalsIgnoreCase(attrName)) {((PetriNet)node).setP(Double.parseDouble((String)attrVal));return;}
        if ("K".equalsIgnoreCase(attrName)) {((PetriNet)node).setK(Double.parseDouble((String)attrVal));return;}
        if ("TimeStepMultiplier".equalsIgnoreCase(attrName)) {((PetriNet)node).setTimeStepMultiplier(Integer.parseInt((String)attrVal));return;}
        if ("ChartInterval".equalsIgnoreCase(attrName)) {((PetriNet)node).setChartInterval(Integer.parseInt((String)attrVal));return;}
      }
    } catch(java.lang.NumberFormatException e) {return;}
    Class<?> superclass = (Class<?>)clazz.getSuperclass();
    if (superclass.getName().startsWith("org.primordion.xholon")) {
      setAppSpecificAttribute(node, (Class<IXholon>)superclass, attrName, attrVal);
    }
  }
  
  public Object[][] getAppSpecificAttributes(IXholon node, Class<IXholon> clazz, boolean returnAll) {
    List<String> names = new ArrayList<String>();
    List<Object> values = new ArrayList<Object>();
    List<Class<?>> types = new ArrayList<Class<?>>();
    if (node == null) {return new Object[0][0];}
    else if ("org.primordion.xholon.base.Turtle".equals(clazz.getName())) {
      names.add("Breed");values.add(((Turtle)node).getBreed());types.add(int.class);
      names.add("Color");values.add(((Turtle)node).getColor());types.add(int.class);
      names.add("Heading");values.add(((Turtle)node).getHeading());types.add(double.class);
      names.add("IsHidden");values.add(((Turtle)node).getIsHidden());types.add(boolean.class);
      names.add("Label");values.add(((Turtle)node).getLabel());types.add(java.lang.String.class);
      names.add("PenMode");values.add(((Turtle)node).getPenMode());types.add(int.class);
      names.add("Who");values.add(((Turtle)node).getWho());types.add(int.class);
      names.add("Xcor");values.add(((Turtle)node).getXcor());types.add(double.class);
      names.add("Ycor");values.add(((Turtle)node).getYcor());types.add(double.class);
      names.add("Pcolor");values.add(((Turtle)node).getPcolor());types.add(int.class);
      names.add("Plabel");values.add(((Turtle)node).getPlabel());types.add(java.lang.String.class);
      names.add("Pxcor");values.add(((Turtle)node).getPxcor());types.add(int.class);
      names.add("Pycor");values.add(((Turtle)node).getPycor());types.add(int.class);
      names.add("MaxPxcor");values.add(((Turtle)node).getMaxPxcor());types.add(int.class);
      names.add("MaxPycor");values.add(((Turtle)node).getMaxPycor());types.add(int.class);
      names.add("MinPxcor");values.add(((Turtle)node).getMinPxcor());types.add(int.class);
      names.add("MinPycor");values.add(((Turtle)node).getMinPycor());types.add(int.class);
      names.add("WorldWidth");values.add(((Turtle)node).getWorldWidth());types.add(int.class);
      names.add("WorldHeight");values.add(((Turtle)node).getWorldHeight());types.add(int.class);
    }
    else if ("org.primordion.xholon.base.XholonClass".equals(clazz.getName())) {
      names.add("QName");values.add(((XholonClass)node).getQName());types.add(org.primordion.xholon.io.xml.namespace.QName.class);
      names.add("PrefixedName");values.add(((XholonClass)node).getPrefixedName());types.add(java.lang.String.class);
      names.add("Prefix");values.add(((XholonClass)node).getPrefix());types.add(java.lang.String.class);
      names.add("LocalPart");values.add(((XholonClass)node).getLocalPart());types.add(java.lang.String.class);
      names.add("Prefixed");values.add(((XholonClass)node).isPrefixed());types.add(boolean.class);
      names.add("NavInfo");values.add(((XholonClass)node).getNavInfo());types.add(java.lang.String.class);
      names.add("PortInformation");values.add(((XholonClass)node).getPortInformation());types.add(java.util.List.class);
      names.add("XhType");values.add(((XholonClass)node).getXhType());types.add(int.class);
      names.add("XhTypeName");values.add(((XholonClass)node).getXhTypeName());types.add(java.lang.String.class);
      names.add("XhcName");values.add(((XholonClass)node).getXhcName());types.add(java.lang.String.class);
      names.add("Uri");values.add(((XholonClass)node).getUri());types.add(java.lang.String.class);
      names.add("Container");values.add(((XholonClass)node).isContainer());types.add(boolean.class);
      names.add("ActiveObject");values.add(((XholonClass)node).isActiveObject());types.add(boolean.class);
      names.add("PassiveObject");values.add(((XholonClass)node).isPassiveObject());types.add(boolean.class);
      names.add("ImplName");values.add(((XholonClass)node).getImplName());types.add(java.lang.String.class);
      names.add("ConfigurationInstructions");values.add(((XholonClass)node).getConfigurationInstructions());types.add(java.lang.String.class);
      names.add("ChildSuperClass");values.add(((XholonClass)node).getChildSuperClass());types.add(java.lang.String.class);
      names.add("Xml2Xholon");values.add(((XholonClass)node).getXml2Xholon());types.add(org.primordion.xholon.io.xml.IXml2Xholon.class);
      names.add("Color");values.add(((XholonClass)node).getColor());types.add(java.lang.String.class);
      names.add("Font");values.add(((XholonClass)node).getFont());types.add(java.lang.String.class);
      names.add("Icon");values.add(((XholonClass)node).getIcon());types.add(java.lang.String.class);
      names.add("ToolTip");values.add(((XholonClass)node).getToolTip());types.add(java.lang.String.class);
      names.add("Symbol");values.add(((XholonClass)node).getSymbol());types.add(java.lang.String.class);
      names.add("Format");values.add(((XholonClass)node).getFormat());types.add(java.lang.String.class);
      names.add("DefaultContent");values.add(((XholonClass)node).getDefaultContent());types.add(java.lang.String.class);
    }
    else if ("org.primordion.xholon.base.QueueSynchronized".equals(clazz.getName())) {
      names.add("Size");values.add(((QueueSynchronized)node).getSize());types.add(int.class);
      names.add("MaxSize");values.add(((QueueSynchronized)node).getMaxSize());types.add(int.class);
      //names.add("Items");values.add(((QueueSynchronized)node).getItems());types.add(java.util.Vector.class);
    }
    else if ("org.primordion.xholon.base.Patch".equals(clazz.getName())) {
      names.add("Pcolor");values.add(((Patch)node).getPcolor());types.add(int.class);
      names.add("Plabel");values.add(((Patch)node).getPlabel());types.add(java.lang.String.class);
      names.add("Pxcor");values.add(((Patch)node).getPxcor());types.add(int.class);
      names.add("Pycor");values.add(((Patch)node).getPycor());types.add(int.class);
    }
    else if ("org.primordion.xholon.base.Port".equals(clazz.getName())) {
      names.add("ProvidedInterface");values.add(((Port)node).getProvidedInterface());types.add(org.primordion.xholon.base.IPortInterface.class);
      names.add("RequiredInterface");values.add(((Port)node).getRequiredInterface());types.add(org.primordion.xholon.base.IPortInterface.class);
      names.add("IsConjugated");values.add(((Port)node).getIsConjugated());types.add(boolean.class);
      names.add("Name");values.add(((Port)node).getName());types.add(java.lang.String.class);
    }
    else if ("org.primordion.xholon.base.StateMachineEntity".equals(clazz.getName())) {
      names.add("Composite");values.add(((StateMachineEntity)node).isComposite());types.add(boolean.class);
      names.add("Orthogonal");values.add(((StateMachineEntity)node).isOrthogonal());types.add(boolean.class);
      names.add("Simple");values.add(((StateMachineEntity)node).isSimple());types.add(boolean.class);
      names.add("ActiveSubState");values.add(((StateMachineEntity)node).isActiveSubState());types.add(boolean.class);
      names.add("Uid");values.add(((StateMachineEntity)node).getUid());types.add(java.lang.String.class);
      names.add("ActivityId");values.add(((StateMachineEntity)node).getActivityId());types.add(int.class);
      names.add("EntryActivityId");values.add(((StateMachineEntity)node).getEntryActivityId());types.add(int.class);
      names.add("ExitActivityId");values.add(((StateMachineEntity)node).getExitActivityId());types.add(int.class);
      names.add("DoActivityId");values.add(((StateMachineEntity)node).getDoActivityId());types.add(int.class);
      names.add("GuardActivityId");values.add(((StateMachineEntity)node).getGuardActivityId());types.add(int.class);
    }
    else if ("org.primordion.xholon.base.Queue".equals(clazz.getName())) {
      names.add("Size");values.add(((Queue)node).getSize());types.add(int.class);
      names.add("MaxSize");values.add(((Queue)node).getMaxSize());types.add(int.class);
      //names.add("Items");values.add(((Queue)node).getItems());types.add(java.util.Vector.class);
    }
    else if ("org.primordion.xholon.base.Mechanism".equals(clazz.getName())) {
      names.add("NamespaceUri");values.add(((Mechanism)node).getNamespaceUri());types.add(java.lang.String.class);
      names.add("DefaultPrefix");values.add(((Mechanism)node).getDefaultPrefix());types.add(java.lang.String.class);
      names.add("RangeStart");values.add(((Mechanism)node).getRangeStart());types.add(int.class);
      names.add("RangeEnd");values.add(((Mechanism)node).getRangeEnd());types.add(int.class);
      names.add("Members");values.add(((Mechanism)node).getMembers());types.add(org.primordion.xholon.base.IXholonClass[].class);
      names.add("Color");values.add(((Mechanism)node).getColor());types.add(java.lang.String.class);
      names.add("Font");values.add(((Mechanism)node).getFont());types.add(java.lang.String.class);
      names.add("Icon");values.add(((Mechanism)node).getIcon());types.add(java.lang.String.class);
      names.add("ToolTip");values.add(((Mechanism)node).getToolTip());types.add(java.lang.String.class);
      names.add("Symbol");values.add(((Mechanism)node).getSymbol());types.add(java.lang.String.class);
      names.add("Format");values.add(((Mechanism)node).getFormat());types.add(java.lang.String.class);
    }
    else if ("org.primordion.xholon.base.XholonWithPorts".equals(clazz.getName())) {
      names.add("Port");values.add(((XholonWithPorts)node).getPort());types.add(org.primordion.xholon.base.IXholon[].class);
    }
    else if ("org.primordion.xholon.base.AbstractGrid".equals(clazz.getName())) {
      names.add("NeighType");values.add(((AbstractGrid)node).getNeighType());types.add(int.class);
      names.add("NumNeighbors");values.add(((AbstractGrid)node).getNumNeighbors());types.add(int.class);
      names.add("MaxPorts");values.add(((AbstractGrid)node).getMaxPorts());types.add(int.class);
      names.add("AllPorts");values.add(((AbstractGrid)node).getAllPorts());types.add(java.util.List.class);
    }
    else if ("org.primordion.xholon.base.PatchOwner".equals(clazz.getName())) {
      names.add("MaxPxcor");values.add(((PatchOwner)node).getMaxPxcor());types.add(int.class);
      names.add("MaxPycor");values.add(((PatchOwner)node).getMaxPycor());types.add(int.class);
      names.add("MinPxcor");values.add(((PatchOwner)node).getMinPxcor());types.add(int.class);
      names.add("MinPycor");values.add(((PatchOwner)node).getMinPycor());types.add(int.class);
      names.add("WorldWidth");values.add(((PatchOwner)node).getWorldWidth());types.add(int.class);
      names.add("WorldHeight");values.add(((PatchOwner)node).getWorldHeight());types.add(int.class);
    }
    else if ("org.primordion.xholon.mech.petrinet.Arc".equals(clazz.getName())) {
      names.add("Active");values.add(((Arc)node).isActive());types.add(boolean.class);
      names.add("Connector");values.add(((Arc)node).getConnector());types.add(java.lang.String.class);
    }
    else if ("org.primordion.xholon.mech.petrinet.Transition".equals(clazz.getName())) {
      names.add("P");values.add(((Transition)node).getP());types.add(double.class);
      names.add("K");values.add(((Transition)node).getK());types.add(double.class);
      names.add("Count");values.add(((Transition)node).getCount());types.add(double.class);
      names.add("KineticsType");values.add(((Transition)node).getKineticsType());types.add(int.class);
      names.add("Vmax");values.add(((Transition)node).getVmax());types.add(double.class);
      names.add("Km");values.add(((Transition)node).getKm());types.add(double.class);
      names.add("Dt");values.add(((Transition)node).getDt());types.add(double.class);
      names.add("Symbol");values.add(((Transition)node).getSymbol());types.add(java.lang.String.class);
    }
    else if ("org.primordion.xholon.mech.petrinet.AnalysisPetriNet".equals(clazz.getName())) {
      names.add("ActionList");values.add(((AnalysisPetriNet)node).getActionList());types.add(java.lang.String[].class);
      names.add("Places");values.add(((AnalysisPetriNet)node).getPlaces());types.add(java.util.List.class);
      names.add("Transitions");values.add(((AnalysisPetriNet)node).getTransitions());types.add(java.util.List.class);
      names.add("TokensCutoff");values.add(((AnalysisPetriNet)node).getTokensCutoff());types.add(double.class);
    }
    else if ("org.primordion.xholon.mech.petrinet.QueueTransitions".equals(clazz.getName())) {
      names.add("ShouldShuffle");values.add(((QueueTransitions)node).isShouldShuffle());types.add(boolean.class);
      names.add("ShouldAct");values.add(((QueueTransitions)node).isShouldAct());types.add(boolean.class);
      names.add("Connector");values.add(((QueueTransitions)node).getConnector());types.add(java.lang.String.class);
      names.add("TimeStepMultiplier");values.add(((QueueTransitions)node).getTimeStepMultiplier());types.add(int.class);
    }
    else if ("org.primordion.xholon.mech.petrinet.Place".equals(clazz.getName())) {
      names.add("Token");values.add(((Place)node).getToken());types.add(java.lang.Object.class);
      names.add("Units");values.add(((Place)node).getUnits());types.add(java.lang.String.class);
    }
    else if ("org.primordion.xholon.mech.petrinet.PetriNet".equals(clazz.getName())) {
      names.add("Transitions");values.add(((PetriNet)node).getTransitions());types.add(java.util.List.class);
      names.add("TransitionsFromQ");values.add(((PetriNet)node).getTransitionsFromQ());types.add(java.util.List.class);
      names.add("Places");values.add(((PetriNet)node).getPlaces());types.add(java.util.List.class);
      names.add("KineticsType");values.add(((PetriNet)node).getKineticsType());types.add(int.class);
      names.add("P");values.add(((PetriNet)node).getP());types.add(double.class);
      names.add("K");values.add(((PetriNet)node).getK());types.add(double.class);
      names.add("TimeStepMultiplier");values.add(((PetriNet)node).getTimeStepMultiplier());types.add(int.class);
      names.add("Dt");values.add(((PetriNet)node).getDt());types.add(double.class);
      names.add("ChartInterval");values.add(((PetriNet)node).getChartInterval());types.add(int.class);
    }
    else if ("org.primordion.xholon.service.mathscieng.Quantity".equals(clazz.getName())) {
      names.add("Unit");values.add(((Quantity)node).getUnit());types.add(java.lang.String.class);
    }
    else if ("org.primordion.xholon.service.svg.SvgClient".equals(clazz.getName())) {
      names.add("Setup");values.add(((SvgClient)node).getSetup());types.add(java.lang.String.class);
      names.add("ViewBehavior");values.add(((SvgClient)node).getViewBehavior());types.add(java.util.List.class);
    }
    if (returnAll) {
      Class<?> superclass = (Class<?>)clazz.getSuperclass();
      if (superclass.getName().startsWith("org.primordion.xholon")) {
        @SuppressWarnings("unchecked")
		Object[][] scAttrs = getAppSpecificAttributes(node, (Class<IXholon>)superclass, returnAll);
        if (scAttrs != null) {
          for (int i = 0; i < scAttrs.length; i++) {
            names.add((String)scAttrs[i][0]);values.add(scAttrs[i][1]);types.add((Class<?>)scAttrs[i][2]);
          }
        }
      }
    }
    Object attributes[][] = new Object[names.size()][3];
    for (int attrIx = 0; attrIx < names.size(); attrIx++) {
      attributes[attrIx][0] = names.get(attrIx);
      attributes[attrIx][1] = values.get(attrIx);
      attributes[attrIx][2] = types.get(attrIx);
    }
    return attributes;
  }
  
  public boolean setAppSpecificObjectArrayVal(IXholon node, Class<IXholon> clazz, String attrName, int index, IXholon val) {
    if (node == null) {return false;}
    Class<?> superclass = (Class<?>)clazz.getSuperclass();
    if (superclass.getName().startsWith("org.primordion.xholon")) {
      return setAppSpecificObjectArrayVal(node, (Class<IXholon>)superclass, attrName, index, val);
    }
    return false;
  }
  
}
