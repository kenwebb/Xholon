/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

package org.primordion.xholon.service.creation;

import com.google.gwt.core.client.GWT;

//import org.primordion.cellontro.base.BioXholonClass;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonClass;
import org.primordion.xholon.exception.XholonConfigurationException;

/**
 * This Tree Node factory creates IXholon nodes using new,
 * as required by Google Web Toolkit (GWT).
 * Example:
 * <pre>return new org.primordion.xholon.base.Control();</pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on July 8, 2013)
 */
@SuppressWarnings("serial")
public class TreeNodeFactoryNew extends Xholon implements ITreeNodeFactory {
	
	// an adequately large current number of available nodes, as if they were actual nodes in a Q
	protected static final int DEFAULT_CURRENT_SIZE = 1000;

	// concrete subclasses that should be returned by getNode(int xhType)
	protected Class<IXholon> xholonSubclass = null;
	protected Class<?> xholonClassSubclass = null;
	//protected Class<?> activitySubclass = null; // unused
	
	//protected IScriptService scriptService = null;
	//protected String scriptServiceImplName = "org.primordion.xholon.service.ScriptService";
	
	/**
	 * Constructor. The three classes passed as parameters, are just defaults.
	 * It's still possible to call getNode(Class xholonSubclass) directly to get an instance of any Java class.
	 * @param xholonSubclass Which subclass of Xholon should be instantiated.
	 * @param xholonClassSubclass Which subclass of XholonClass should be instantiated.
	 * @param activitySubclass Which subclass of Activity should be instantiated.
	 */
	public TreeNodeFactoryNew(
			Class xholonSubclass,
			Class xholonClassSubclass,
			Class activitySubclass )
	{
	  System.out.println("TreeNodeFactoryNew constructor:" + xholonSubclass);
	  System.out.println("TreeNodeFactoryNew constructor:" + xholonClassSubclass);
	  System.out.println("TreeNodeFactoryNew constructor:" + activitySubclass);
		this.xholonSubclass = xholonSubclass;
		if (xholonClassSubclass == null) {
			this.xholonClassSubclass = XholonClass.class;
		}
		else {
			this.xholonClassSubclass = xholonClassSubclass;
		}
		//if (activitySubclass == null) {
		//	this.activitySubclass = Activity.class;
		//}
		//else {
		//	this.activitySubclass = activitySubclass;
		//}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getXhc()
	 */
	public IXholonClass getXhc()
	{
		if (xhc == null) {
			xhc = getClassNode(getId());
		}
		return xhc;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getXhcName()
	 */
	public String getXhcName()
	{
		return getXhc().getName();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getName()
	 */
	public String getName()
	{
		getXhc(); // make sure the XholonClass has been set
		return super.getName();
	}

	@Override
	public int getNumAvailNodes(int resourceType) {
		return DEFAULT_CURRENT_SIZE;
	}

	@Override
	public IXholon getNode(Class xholonSubclass)
			throws XholonConfigurationException {
		IXholon instance = null;
		
		System.out.println("TreeNodeFactoryNew.getNode " + this.getApp() + " " + xholonSubclass);
		// TODO needs to call app-specific method in Application
		instance = this.getApp().makeAppSpecificNode(xholonSubclass.getName());
		
		/*try {
			instance = (IXholon)xholonSubclass.newInstance();
		} catch (NullPointerException e) {
			throw new XholonConfigurationException("TreeNodeFactoryNew getNode(): xholonSubclass is null", e);
		} catch (InstantiationException e) {
			throw new XholonConfigurationException("TreeNodeFactoryNew getNode(): Unable to instantiate " + xholonSubclass.getName(), e);
		} catch (IllegalAccessException e) {
			throw new XholonConfigurationException("TreeNodeFactoryNew getNode(): Unable to instantiate " + xholonSubclass.getName(), e);
		}*/
		instance.initialize(); // set all links to NULL
		return instance;
	}

	@Override
	public IXholon getNode(int xhType) throws XholonConfigurationException {
		return null;
	}

	@Override
	public IXholon getXholonNode() throws XholonConfigurationException {
		return getNode(xholonSubclass);
	}

	@Override
	public IXholon getXholonNode(String implName)
			throws XholonConfigurationException {
		//this.consoleLog(implName);
		if (implName == null || (implName.length() == 0)) {return null;}
		IXholon newNode = null;
		
		// base.Attribute
		if (implName.startsWith("org.primordion.xholon.base.Attribute$")) {
			if (implName.endsWith("_String")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_String();
			}
			else if (implName.endsWith("_attribute")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_attribute();
			}
			else if (implName.endsWith("_int")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_int();
			}
			else if (implName.endsWith("_double")) {
			  System.out.println("WolfSheepGrass: " + implName);
				newNode = new org.primordion.xholon.base.Attribute.Attribute_double();
			}
			else if (implName.endsWith("_boolean")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_boolean();
			}
			else if (implName.endsWith("_byte")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_byte();
			}
			else if (implName.endsWith("_char")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_char();
			}
			else if (implName.endsWith("_float")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_float();
			}
			else if (implName.endsWith("_long")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_long();
			}
			else if (implName.endsWith("_short")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_short();
			}
			else if (implName.endsWith("_Object")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_Object();
			}
			else if (implName.endsWith("_arrayboolean")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_arrayboolean();
			}
			else if (implName.endsWith("_arraybyte")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_arraybyte();
			}
			else if (implName.endsWith("_arraychar")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_arraychar();
			}
			else if (implName.endsWith("_arraydouble")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_arraydouble();
			}
			else if (implName.endsWith("_arrayfloat")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_arrayfloat();
			}
			else if (implName.endsWith("_arrayint")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_arrayint();
			}
			else if (implName.endsWith("_arraylong")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_arraylong();
			}
			else if (implName.endsWith("_arrayObject")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_arrayObject();
			}
			else if (implName.endsWith("_arrayshort")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_arrayshort();
			}
			else if (implName.endsWith("_arrayString")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_arrayString();
			}
			else if (implName.endsWith("_List")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_List();
			}
			else if (implName.endsWith("_Map")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_Map();
			}
			else if (implName.endsWith("_Set")) {
				newNode = new org.primordion.xholon.base.Attribute.Attribute_Set();
			}
			else {
				System.out.println(implName);
			}
		}
		
		// base
		else if (implName.startsWith("org.primordion.xholon.base")) {
			if (implName.endsWith("Control")) {
				newNode = new org.primordion.xholon.base.Control();
			}
			else if (implName.endsWith("Mechanism")) {
				newNode = new org.primordion.xholon.base.Mechanism();
			}
			else if (implName.endsWith("Behavior_gwtjs")) {
				newNode = new org.primordion.xholon.base.Behavior_gwtjs();
			}
			else if (implName.endsWith("Behavior_gwtjsproto")) {
				newNode = new org.primordion.xholon.base.Behavior_gwtjsproto();
			}
			else if (implName.endsWith("Behavior_gwtpython")) {
				newNode = new org.primordion.xholon.base.Behavior_gwtpython();
			}
			else if (implName.endsWith("XholonList")) {
				newNode = new org.primordion.xholon.base.XholonList();
			}
			else if (implName.endsWith("XholonMap")) {
				newNode = new org.primordion.xholon.base.XholonMap();
			}
			else if (implName.endsWith("Annotation")) {
				newNode = new org.primordion.xholon.base.Annotation();
			}
			else if (implName.endsWith("GridEntity")) {
				newNode = new org.primordion.xholon.base.GridEntity();
			}
			else if (implName.endsWith("Role")) {
				newNode = new org.primordion.xholon.base.Role();
			}
			else if (implName.endsWith("StateMachineEntity")) {
				newNode = new org.primordion.xholon.base.StateMachineEntity();
			}
			else if (implName.endsWith("ObservableStateMachineEntity")) {
				newNode = new org.primordion.xholon.base.ObservableStateMachineEntity();
			}
			else if (implName.endsWith("ObservableXholonWithPorts")) {
				newNode = new org.primordion.xholon.base.ObservableXholonWithPorts();
			}
			else if (implName.endsWith("Activity")) {
				newNode = new org.primordion.xholon.base.Activity();
			}
			else if (implName.endsWith("Queue")) {
				newNode = new org.primordion.xholon.base.Queue(100);
			}
			else if (implName.endsWith("QueueSynchronized")) {
				newNode = new org.primordion.xholon.base.QueueSynchronized(100);
			}
			
			// Turtle Geometry
			else if (implName.endsWith("Patch")) {
				newNode = new org.primordion.xholon.base.Patch();
			}
			else if (implName.endsWith("PatchOwner")) {
				newNode = new org.primordion.xholon.base.PatchOwner();
			}
			else if (implName.endsWith("Turtle")) {
				newNode = new org.primordion.xholon.base.Turtle();
			}
			
			// OrNode
			else if (implName.endsWith("OrNode")) {
				newNode = new org.primordion.xholon.base.OrNode();
			}
			else if (implName.endsWith("OrNodeNext")) {
				newNode = new org.primordion.xholon.base.OrNodeNext();
			}
			else if (implName.endsWith("OrNodeRandom")) {
				newNode = new org.primordion.xholon.base.OrNodeRandom();
			}
			
			// Avatar, Sprite, Stage
			else if (implName.endsWith("Avatar")) {
				newNode = new org.primordion.xholon.base.Avatar();
			}
			else if (implName.endsWith("Sprite")) {
				newNode = new org.primordion.xholon.base.Sprite();
			}
			else if (implName.endsWith("Stage")) {
				newNode = new org.primordion.xholon.base.Stage();
			}
			
			// ActRegulator
			else if (implName.endsWith("ActRegulator")) {
				newNode = new org.primordion.xholon.base.ActRegulator();
			}
			
			// Chatbot
			else if (implName.endsWith("Chatbot")) {
				newNode = new org.primordion.xholon.base.Chatbot();
			}
			
			else {
				System.out.println(implName);
			}
		}

    // io
		else if (implName.startsWith("org.primordion.xholon.io")) {
			if (implName.endsWith("GridPanelGeneric")) {
				newNode = new org.primordion.xholon.io.GridPanelGeneric();
			}
		}
		
		// mech.petrinet
		else if (implName.startsWith("org.primordion.xholon.mech.petrinet")) {
			if (implName.endsWith("AnalysisPetriNet")) {
				newNode = new org.primordion.xholon.mech.petrinet.AnalysisPetriNet();
			}
			else if (implName.endsWith("InputArc")) {
				newNode = new org.primordion.xholon.mech.petrinet.InputArc();
			}
			else if (implName.endsWith("OutputArc")) {
				newNode = new org.primordion.xholon.mech.petrinet.OutputArc();
			}
			else if (implName.endsWith("PetriNet")) {
				newNode = new org.primordion.xholon.mech.petrinet.PetriNet();
			}
			else if (implName.endsWith("PetriNetEntity")) {
				newNode = new org.primordion.xholon.mech.petrinet.PetriNetEntity();
			}
			else if (implName.endsWith("Place")) {
				newNode = new org.primordion.xholon.mech.petrinet.Place();
			}
			else if (implName.endsWith("QueueTransitions")) {
				newNode = new org.primordion.xholon.mech.petrinet.QueueTransitions();
			}
			else if (implName.endsWith("Transition")) {
				newNode = new org.primordion.xholon.mech.petrinet.Transition();
			}
			else if (implName.endsWith("AnalysisCat")) {
				newNode = new org.primordion.xholon.mech.petrinet.cat.AnalysisCat();
			}
			else if (implName.endsWith("AnalysisCRN")) {
				newNode = new org.primordion.xholon.mech.petrinet.crn.AnalysisCRN();
			}
			else if (implName.endsWith("GridOwner")) {
				newNode = new org.primordion.xholon.mech.petrinet.grid.GridOwner();
			}
			else if (implName.endsWith("FiringTransitionBehavior")) {
				newNode = new org.primordion.xholon.mech.petrinet.grid.FiringTransitionBehavior();
			}
			else if (implName.endsWith("MovingBehavior")) {
				newNode = new org.primordion.xholon.mech.petrinet.grid.MovingBehavior();
			}
			else if (implName.endsWith("QueueNodesInGrid")) {
				newNode = new org.primordion.xholon.mech.petrinet.grid.QueueNodesInGrid();
			}
			else if (implName.endsWith("AnalysisMemComp")) {
				newNode = new org.primordion.xholon.mech.petrinet.memcomp.AnalysisMemComp();
			}
		}
		
		// mech.spreadsheet
		else if (implName.startsWith("org.primordion.xholon.mech.spreadsheet")) {
		  if (implName.endsWith("Spreadsheet")) {
			  newNode = new org.primordion.xholon.mech.spreadsheet.Spreadsheet();
			}
		  else if (implName.endsWith("SpreadsheetRow")) {
			  newNode = new org.primordion.xholon.mech.spreadsheet.SpreadsheetRow();
			}
		  else if (implName.endsWith("SpreadsheetCell")) {
			  newNode = new org.primordion.xholon.mech.spreadsheet.SpreadsheetCell();
			}
		  else if (implName.endsWith("SpreadsheetFormula")) {
			  newNode = new org.primordion.xholon.mech.spreadsheet.SpreadsheetFormula();
			}
    }
    
		// mech.story
		else if (implName.startsWith("org.primordion.xholon.mech.story")) {
		  if (implName.endsWith("StorySystem")) {
			  newNode = new org.primordion.xholon.mech.story.StorySystem();
			}
		  else if (implName.endsWith("Screenplay")) {
			  newNode = new org.primordion.xholon.mech.story.Screenplay();
			}
		  else if (implName.endsWith("Scenes")) {
			  newNode = new org.primordion.xholon.mech.story.Scenes();
			}
		  else if (implName.endsWith("Director")) {
			  newNode = new org.primordion.xholon.mech.story.Director();
			}
    }
    
		// mech.mathml
		else if (implName.startsWith("org.primordion.xholon.mech.mathml")) {
		  if (implName.endsWith("content.JavaMath")) {
			  newNode = new org.primordion.xholon.mech.mathml.content.JavaMath();
			}
			else if (implName.endsWith("Math")) {
				newNode = new org.primordion.xholon.mech.mathml.Math();
			}
			else if (implName.endsWith("content.Apply")) {
				newNode = new org.primordion.xholon.mech.mathml.content.Apply();
			}
			else if (implName.endsWith("content.Ci")) {
				newNode = new org.primordion.xholon.mech.mathml.content.Ci();
			}
			else if (implName.endsWith("content.Cn")) {
				newNode = new org.primordion.xholon.mech.mathml.content.Cn();
			}
			else if (implName.endsWith("content.Divide")) {
				newNode = new org.primordion.xholon.mech.mathml.content.Divide();
			}
			else if (implName.endsWith("content.Eq")) {
				newNode = new org.primordion.xholon.mech.mathml.content.Eq();
			}
			else if (implName.endsWith("content.Factorial")) {
				newNode = new org.primordion.xholon.mech.mathml.content.Factorial();
			}
			else if (implName.endsWith("content.Minus")) {
				newNode = new org.primordion.xholon.mech.mathml.content.Minus();
			}
			else if (implName.endsWith("content.Plus")) {
				newNode = new org.primordion.xholon.mech.mathml.content.Plus();
			}
			else if (implName.endsWith("content.Semantics")) {
				newNode = new org.primordion.xholon.mech.mathml.content.Semantics();
			}
			else if (implName.endsWith("content.Times")) {
				newNode = new org.primordion.xholon.mech.mathml.content.Times();
			}
		}
		
		// mech.html5audio
		else if (implName.startsWith("org.primordion.xholon.mech.html5audio")) {
		  if (implName.endsWith("AudioContext")) {
			  newNode = new org.primordion.xholon.mech.html5audio.AudioContext();
			}
			else if (implName.endsWith("AudioNode")) {
				newNode = new org.primordion.xholon.mech.html5audio.AudioNode();
			}
		}
		
		// mech.gexf
		else if (implName.startsWith("org.primordion.xholon.mech.gexf")) {
			if (implName.endsWith("Gexf")) {
				newNode = new org.primordion.xholon.mech.gexf.Gexf();
			}
		}
		
		// mech.catt
		else if (implName.startsWith("org.primordion.xholon.mech.catt")) {
			if (implName.endsWith("CatAql")) {
				newNode = new org.primordion.xholon.mech.catt.CatAql();
			}
		}
		
		// script
		// remember to add the script to the isClassFindable() method as well
		else if (implName.startsWith("org.primordion.xholon.script")) {
			if (implName.endsWith("EfParamsGenerator")) {
				newNode = new org.primordion.xholon.script.EfParamsGenerator();
			}
			else if (implName.endsWith("InteractionsViewer")) {
				newNode = new org.primordion.xholon.script.InteractionsViewer();
			}
			else if (implName.endsWith("Lojban")) {
				newNode = new org.primordion.xholon.script.Lojban();
			}
			else if (implName.endsWith("Plot")) {
				newNode = new org.primordion.xholon.script.Plot();
			}
			else if (implName.endsWith("Animate")) {
				newNode = new org.primordion.xholon.script.Animate();
			}
			else if (implName.endsWith("port")) {
				newNode = new org.primordion.xholon.script.port();
			}
			else if (implName.endsWith("Tabulator")) {
				newNode = new org.primordion.xholon.script.Tabulator();
			}
			else if (implName.endsWith("Renumber")) {
				newNode = new org.primordion.xholon.script.Renumber();
			}
			else if (implName.endsWith("TreeIntegrityChecker")) {
				newNode = new org.primordion.xholon.script.TreeIntegrityChecker();
			}
			else if (implName.endsWith("TreeTraversal")) {
				newNode = new org.primordion.xholon.script.TreeTraversal();
			}
			else if (implName.endsWith("TreeWanderer")) {
				newNode = new org.primordion.xholon.script.TreeWanderer();
			}
			else if (implName.endsWith("XholonModule")) {
				newNode = new org.primordion.xholon.script.XholonModule();
			}
			else if (implName.endsWith("GridGenerator")) {
				newNode = new org.primordion.xholon.script.GridGenerator();
			}
			else if (implName.endsWith("MechanismEnabler")) {
				newNode = new org.primordion.xholon.script.MechanismEnabler();
			}
			else if (implName.endsWith("RelationshipBuilder")) {
				newNode = new org.primordion.xholon.script.RelationshipBuilder();
			}
		}
		
		// service.mathscieng
		else if (implName.startsWith("org.primordion.xholon.service.mathscieng")) {
			if (implName.endsWith(".Quantity")) {
				newNode = new org.primordion.xholon.service.mathscieng.Quantity();
			}
		}
		
		// service.broadcast
		else if (implName.startsWith("org.primordion.xholon.service.broadcast")) {
			if (implName.endsWith(".Broadcaster")) {
				newNode = new org.primordion.xholon.service.broadcast.Broadcaster();
			}
		}
		
		// service
		else if (implName.startsWith("org.primordion.xholon.service")) {
			if (implName.endsWith("DefaultXholonService")) {
				newNode = new org.primordion.xholon.service.DefaultXholonService();
			}
			else if (implName.endsWith("ServiceLocatorService")) {
				newNode = new org.primordion.xholon.service.ServiceLocatorService();
			}
			else if (implName.endsWith("XPathService")) {
				newNode = new org.primordion.xholon.service.XPathService();
			}
			else if (implName.endsWith("XholonDirectoryService")) {
				newNode = new org.primordion.xholon.service.XholonDirectoryService();
			}
			else if (implName.endsWith("DummyService")) {
				newNode = new org.primordion.xholon.service.DummyService();
			}
			else if (implName.endsWith("AboutService")) {
				newNode = new org.primordion.xholon.service.AboutService();
			}
			else if (implName.endsWith("ChartViewerService")) {
				newNode = new org.primordion.xholon.service.ChartViewerService();
			}
			else if (implName.endsWith("GenericViewerService")) {
				newNode = new org.primordion.xholon.service.GenericViewerService();
			}
			else if (implName.endsWith("TreeProcessorService")) {
				newNode = new org.primordion.xholon.service.TreeProcessorService();
			}
			else if (implName.endsWith("TreeProcessorService")) {
				newNode = new org.primordion.xholon.service.TreeProcessorService();
			}
			else if (implName.endsWith("SpringFrameworkService")) {
				//newNode = new org.primordion.xholon.service.SpringFrameworkService();
			}
			else if (implName.endsWith("XholonCreationService")) {
				newNode = new org.primordion.xholon.service.XholonCreationService();
			}
			else if (implName.endsWith("NodeSelectionService")) {
				newNode = new org.primordion.xholon.service.NodeSelectionService();
			}
			else if (implName.endsWith("XholonHelperService")) {
				newNode = new org.primordion.xholon.service.XholonHelperService();
			}
			else if (implName.endsWith("WiringService")) {
				newNode = new org.primordion.xholon.service.WiringService();
			}
			else if (implName.endsWith("ControlService")) {
				//newNode = new org.primordion.xholon.service.ControlService();
			}
			else if (implName.endsWith("ScriptService")) {
				//newNode = new org.primordion.xholon.service.ScriptService();
			}
			else if (implName.endsWith("MediaService")) {
				//newNode = new org.primordion.xholon.service.MediaService();
			}
			else if (implName.endsWith("GameEngineService")) {
				//newNode = new org.primordion.xholon.service.GameEngineService();
			}
			else if (implName.endsWith("NetLogoService")) {
				//newNode = new org.primordion.xholon.service.NetLogoService();
			}
			else if (implName.endsWith("SvgViewService")) {
				newNode = new org.primordion.xholon.service.SvgViewService();
			}
			else if (implName.endsWith("SvgClient")) {
     	  newNode = new org.primordion.xholon.service.svg.SvgClient();
      }
      else if (implName.endsWith("SvgLabel")) {
        newNode = new org.primordion.xholon.service.svg.SvgLabel();
      }
      else if (implName.endsWith("SvgViewable")) {
        newNode = new org.primordion.xholon.service.svg.SvgViewable();
      }
      else if (implName.endsWith("SvgAttribute")) {
        newNode = new org.primordion.xholon.service.svg.SvgAttribute();
      }
      else if (implName.endsWith("GistService")) {
				newNode = new org.primordion.xholon.service.GistService();
			}
			else if (implName.endsWith("TimelineService")) {
				newNode = new org.primordion.xholon.service.TimelineService();
			}
			else if (implName.endsWith("ExternalFormatService")) {
				newNode = GWT.create(org.primordion.xholon.service.ExternalFormatService.class);
			}
			else if (implName.endsWith("SearchEngineService")) {
				newNode = new org.primordion.xholon.service.SearchEngineService();
			}
			else if (implName.endsWith("RecordPlaybackService")) {
				newNode = new org.primordion.xholon.service.RecordPlaybackService();
			}
			else if (implName.endsWith("SemanticWebService")) {
				//newNode = new org.primordion.xholon.service.SemanticWebService();
			}
			else if (implName.endsWith("SbmlService")) {
				//newNode = new org.primordion.xholon.service.SbmlService();
			}
			else if (implName.endsWith("MathSciEngService")) {
				newNode = new org.primordion.xholon.service.MathSciEngService();
			}
			else if (implName.endsWith("BlackboardService")) {
				newNode = new org.primordion.xholon.service.BlackboardService();
			}
			else if (implName.endsWith("VfsService")) {
				//newNode = new org.primordion.xholon.service.VfsService();
			}
			else if (implName.endsWith("NoSqlService")) {
				newNode = new org.primordion.xholon.service.NoSqlService();
			}
			else if (implName.endsWith("MeteorPlatformService")) {
				newNode = new org.primordion.xholon.service.MeteorPlatformService();
			}
			else if (implName.endsWith("RemoteNodeService")) {
				newNode = new org.primordion.xholon.service.RemoteNodeService();
			}
			else if (implName.endsWith("BroadcastService")) {
				newNode = new org.primordion.xholon.service.BroadcastService();
			}
			else if (implName.endsWith("SpreadsheetService")) {
				newNode = new org.primordion.xholon.service.SpreadsheetService();
			}
			else if (implName.endsWith("NaturalLanguageService")) {
				newNode = new org.primordion.xholon.service.NaturalLanguageService();
			}
			else if (implName.endsWith("RestService")) {
				//newNode = new org.primordion.xholon.service.RestService();
			}
			else if (implName.endsWith("BlueprintsService")) {
				//newNode = new org.primordion.xholon.service.BlueprintsService();
			}
			else if (implName.endsWith("XmlDatabaseService")) {
				//newNode = new org.primordion.xholon.service.XmlDatabaseService();
			}
			else if (implName.endsWith("JcrService")) {
				//newNode = new org.primordion.xholon.service.JcrService();
			}
			else if (implName.endsWith("JavaSerializationService")) {
				//newNode = new org.primordion.xholon.service.JavaSerializationService();
			}
			else if (implName.endsWith("BerkeleyDbService")) {
				//newNode = new org.primordion.xholon.service.BerkeleyDbService();
			}
			else if (implName.endsWith("ValidationService")) {
				//newNode = new org.primordion.xholon.service.ValidationService();
			}
			else if (implName.endsWith("XmlValidationService")) {
				//newNode = new org.primordion.xholon.service.XmlValidationService();
			}
			else if (implName.endsWith("DatabaseService")) {
				//newNode = new org.primordion.xholon.service.DatabaseService();
			}
			
			else {
				System.out.println("else if (implName.endsWith(\""
						+ implName.substring(implName.lastIndexOf('.')+1) + "\")) {");
				System.out.println("\tnewNode = new " + implName + "();\n}");
				newNode = this.getApp().makeAppSpecificNode(implName);
			}
		}
		
		// cellontro
		else if (implName.startsWith("org.primordion.cellontro")) {
		  if (implName.endsWith("VrmlWriterCell")) {
		    newNode = new org.primordion.cellontro.io.vrml.VrmlWriterCell();
		  }
		}
	  
		// this is probably an app-specific class
		else {
			System.out.println("TreeNodeFactoryNew.getXholonNode( " + implName);
			newNode = this.getApp().makeAppSpecificNode(implName);
		}
		
		return newNode;
	}

	@Override
	public IXholon getXholonNode(String implName, String methodName)
			throws XholonConfigurationException {
		System.out.println("TreeNodeFactoryNew.getXholonNode(" + implName + ", " + methodName + ")");
		return null;
	}

	@Override
	public IXholon getXholonScriptNode(String implName)
			throws XholonConfigurationException {
		return null;
	}

	@Override
	public IXholon getNonXholonNode(Class clazz)
			throws XholonConfigurationException {
		return null;
	}

	@Override
	public IXholonClass getXholonClassNode()
			throws XholonConfigurationException {
		IXholonClass xhcEnt = null;
		if (xholonClassSubclass == XholonClass.class) {
			xhcEnt = new XholonClass();
		}
		else if (xholonClassSubclass == org.primordion.cellontro.base.BioXholonClass.class) {
			xhcEnt = new org.primordion.cellontro.base.BioXholonClass();
		}
		else {
			System.out.println("TreeNodeFactoryNew.getXholonClassNode() xholonClassSubclass:" + xholonClassSubclass);
		}
		xhcEnt.initialize();
		return xhcEnt;
		
	}

	@Override
	public boolean isClassFindable(String implName) {
	  System.out.println("TreeNodeFactoryNew isClassFindable() " + implName);
	  /* GWT
		try {
			Class.forName(implName);
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;*/
		
		// script
		if (implName.startsWith("org.primordion.xholon.script")) {
		  if (implName.endsWith("EfParamsGenerator")) {
		    return true;
		  }
		  if (implName.endsWith("InteractionsViewer")) {
		    return true;
		  }
		  if (implName.endsWith("Lojban")) {
				return true;
			}
			if (implName.endsWith("Plot")) {
				return true;
			}
			if (implName.endsWith("Animate")) {
				return true;
			}
			if (implName.endsWith("port")) {
				return true;
			}
			if (implName.endsWith("Tabulator")) {
				return true;
			}
			if (implName.endsWith("Renumber")) {
				return true;
			}
			if (implName.endsWith("TreeIntegrityChecker")) {
				return true;
			}
			if (implName.endsWith("TreeTraversal")) {
				return true;
			}
			if (implName.endsWith("TreeWanderer")) {
				return true;
			}
			if (implName.endsWith("XholonModule")) {
				return true;
			}
			if (implName.endsWith("GridGenerator")) {
				return true;
			}
			if (implName.endsWith("MechanismEnabler")) {
				return true;
			}
			if (implName.endsWith("RelationshipBuilder")) {
				return true;
			}
		}
		// script API
		else if (implName.startsWith("org.primordion.xholon.base.Behavior")) {
			if (implName.endsWith("Behavior_gwtjs")) {
				return true;
			}
			if (implName.endsWith("Behavior_gwtjsproto")) {
				return true;
			}
			if (implName.endsWith("Behavior_gwtpython")) {
				return true;
			}
		}
		
		return this.getApp().isAppSpecificClassFindable(implName);
		
		//return true;
	}

	@Override
	public void returnTreeNode(IXholon node) {
		node = null;
	}
	
	/*public IScriptService getScriptService() {
		return scriptService;
	}

	public void setScriptService(IScriptService scriptService) {
		this.scriptService = scriptService;
	}

	public String getScriptServiceImplName() {
		return scriptServiceImplName;
	}

	public void setScriptServiceImplName(String scriptServiceImplName) {
		this.scriptServiceImplName = scriptServiceImplName;
	}*/

}
