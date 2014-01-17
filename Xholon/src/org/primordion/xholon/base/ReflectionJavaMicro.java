/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

package org.primordion.xholon.base;

//import com.google.gwt.core.client.GWT;
//import com.google.gwt.user.client.Window.Navigator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.client.GwtEnvironment;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.util.ClassHelper;
import org.primordion.xholon.util.Misc;

/**
 * Reflection supports a metadata capability.
 * ReflectionJavaMicro implements a limited form of reflection.
 * It does NOT use the Java Standard Edition reflection mechanism.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on December 7, 2006)
 */
public class ReflectionJavaMicro implements IReflection {

	public boolean setParam(String pName, String pValue, Object obj) {
		Application app = (Application)obj;
		switch (pName.charAt(0)) {
		case 'A':
			if ("AppM".equals(pName)) {app.setAppM(Misc.booleanValue(pValue)); return true;}
		  if ("AttributePostConfigAction".equals(pName)) {app.setAttributePostConfigAction(Integer.parseInt(pValue)); return true;}
			break;
		case 'B':
			break;
		case 'C':
			if ("CompositeStructureHierarchyFile".equals(pName)) {app.setCompositeStructureHierarchyFile(pValue); return true;}
			if ("ClassDetailsFile".equals(pName)) {app.setClassDetailsFile(pValue); return true;}
			break;
		case 'D':
			if ("DataPlotterParams".equals(pName)) {app.setDataPlotterParams(pValue); return true;}
			break;
		case 'E':
			if ("ErrorM".equals(pName)) {app.setErrorM(Misc.booleanValue(pValue)); return true;}
			break;
		case 'F':
			break;
		case 'G':
			if ("GraphicalTreeViewerParams".equals(pName)) {app.setGraphicalTreeViewerParams(pValue); return true;}
			if ("GraphicalNetworkViewerParams".equals(pName)) {app.setGraphicalNetworkViewerParams(pValue); return true;}
			if ("GraphicalNetworkViewerClassName".equals(pName)) {app.setGraphicalNetworkViewerClassName(pValue); return true;}
			if ("GridPanelClassName".equals(pName)) {app.setGridPanelClassName(pValue); return true;}
			if ("GridViewerParams".equals(pName)) {app.setGridViewerParams(pValue); return true;}
			break;
		case 'H':
		  if ("HistogramPlotterParams".equals(pName)) {app.setHistogramPlotterParams(pValue); return true;}
			break;
		case 'I':
		  if ("ImageFile".equals(pName)) {app.setImageFile(pValue); return true;}
			if ("InfoM".equals(pName)) {app.setInfoM(Misc.booleanValue(pValue)); return true;}
			if ("InheritanceHierarchyFile".equals(pName)) {app.setInheritanceHierarchyFile(pValue); return true;}
			if ("InformationFile".equals(pName)) {app.setInformationFile(pValue); return true;}
		  if ("InitialControllerState".equals(pName)) {app.setInitialControllerState(Integer.parseInt(pValue)); return true;}
		  if ("InteractionParams".equals(pName)) {app.setInteractionParams(pValue); return true;}
			if ("IQueueImplName".equals(pName)) {app.setIQueueImplName(pValue); return true;}
			break;
		case 'J':
			if ("JavaClassName".equals(pName)) {app.setJavaClassName(pValue); return true;}
			if ("JavaXhClassName".equals(pName)) {app.setJavaXhClassName(pValue); return true;}
			if ("JavaXhClassClassName".equals(pName)) {app.setJavaXhClassClassName(pValue); return true;}
			if ("JavaActivityClassName".equals(pName)) {app.setJavaActivityClassName(pValue); return true;}
		  if ("JdbcParams".equals(pName)) {app.setJdbcParams(pValue); return true;}
		  if ("JpaParams".equals(pName)) {app.setJpaParams(pValue); return true;}
			break;
		case 'K':
			break;
		case 'L':
			break;
		case 'M':
			if ("ModelName".equals(pName)) {app.setModelName(pValue); return true;}
			if ("MaxProcessLoops".equals(pName)) {app.setMaxProcessLoops(Integer.parseInt(pValue)); return true;}
			if ("MaxPorts".equals(pName)) {app.setMaxPorts(Integer.parseInt(pValue)); return true;}
			if ("MaxXholons".equals(pName)) {app.setMaxXholons(Integer.parseInt(pValue)); return true;}
			if ("MaxXholonClasses".equals(pName)) {app.setMaxXholonClasses(Integer.parseInt(pValue)); return true;}
			if ("MaxStateMachineEntities".equals(pName)) {app.setMaxStateMachineEntities(Integer.parseInt(pValue)); return true;}
			break;
		case 'N':
			break;
		case 'O':
			break;
		case 'P':
			break;
		case 'Q':
			break;
		case 'R':
			if ("RandomNumberSeed".equals(pName)) {app.setRandomNumberSeed(Integer.parseInt(pValue)); return true;}
			break;
		case 'S':
			if ("SizeMessageQueue".equals(pName)) {app.setSizeMessageQueue(Integer.parseInt(pValue)); return true;}
			if ("SaveSnapshots".equals(pName)) {app.setSaveSnapshots(Misc.booleanValue(pValue)); return true;}
			if ("SnapshotParams".equals(pName)) {app.setSnapshotParams(pValue); return true;}
			break;
		case 'T':
			if ("TimeStep".equals(pName)) {app.setTimeStep(Integer.parseInt(pValue)); return true;}
			if ("TimeStepInterval".equals(pName)) {app.setTimeStepInterval(Integer.parseInt(pValue)); return true;}
			if ("TreeNodeFactoryDynamic".equals(pName)) {app.setTreeNodeFactoryDynamic(Misc.booleanValue(pValue)); return true;}
			break;
		case 'U':
			if ("UseInteractions".equals(pName)) {app.setUseInteractions(Misc.booleanValue(pValue)); return true;}
			if ("UseDataPlotter".equals(pName)) {app.setUseDataPlotter(pValue); return true;}
			if ("UseTextTree".equals(pName)) {app.setUseTextTree(Misc.booleanValue(pValue)); return true;}
			if ("UseGraphicalTreeViewer".equals(pName)) {app.setUseGraphicalTreeViewer(Misc.booleanValue(pValue)); return true;}
			if ("UseGraphicalNetworkViewer".equals(pName)) {app.setUseGraphicalNetworkViewer(Misc.booleanValue(pValue)); return true;}
			if ("UseHistogramPlotter".equals(pName)) {app.setUseHistogramPlotter(pValue); return true;}
			if ("UseVrml".equals(pName)) {app.setUseVrml(Misc.booleanValue(pValue)); return true;}
			if ("UseGridViewer".equals(pName)) {app.setUseGridViewer(Misc.booleanValue(pValue)); return true;}
		  if ("UseXholon2Gui".equals(pName)) {app.setUseXholon2Gui(Misc.booleanValue(pValue)); return true;}
			break;
		case 'V':
			if ("VrmlWriterClassName".equals(pName)) {app.setVrmlWriterClassName(pValue); return true;}
			if ("VrmlParams".equals(pName)) {app.setVrmlParams(pValue); return true;}
			break;
		case 'W':
			break;
		case 'X':
			if ("XincludePath".equals(pName)) {app.setXincludePath(pValue); return true;}
		  if ("Xholon2GuiClassName".equals(pName)) {app.setXholon2GuiClassName(pValue); return true;}
		  if ("Xholon2GuiParams".equals(pName)) {app.setXholon2GuiParams(pValue); return true;}
			break;
		case 'Y':
			break;
		case 'Z':
			break;
		default:
			break;
		}
		// this may be an app-specific attribute
		// this is already handled by app.setParam()
		/*if (app.getAppSpecificAttribute(app, null, pName) != null) {
		  app.setAppSpecificAttribute(app, null, pName, pValue);
		  return true;
		}*/
		System.err.println("Unknown input parameter: " + pName + " " + pValue);
		return false;
	}
	
	public String getParam(String pName, Object obj)
	{
	  Application app = (Application)obj;
		switch (pName.charAt(0)) {
		case 'A':
			if ("AppM".equals(pName)) {return Boolean.toString(app.getAppM());}
		  //if ("AttributePostConfigAction".equals(pName)) {app.setAttributePostConfigAction(Integer.parseInt(pValue)); return true;}
			break;
		case 'B':
			break;
		case 'C':
			if ("CompositeStructureHierarchyFile".equals(pName)) {return app.getCompositeStructureHierarchyFile();}
			if ("ClassDetailsFile".equals(pName)) {return app.getClassDetailsFile();}
			break;
		case 'D':
			if ("DataPlotterParams".equals(pName)) {return app.getDataPlotterParams();}
			break;
		case 'E':
			if ("ErrorM".equals(pName)) {return Boolean.toString(app.getErrorM());}
			break;
		case 'F':
			break;
		case 'G':
			if ("GraphicalTreeViewerParams".equals(pName)) {return app.getGraphicalTreeViewerParams();}
			if ("GraphicalNetworkViewerParams".equals(pName)) {return app.getGraphicalNetworkViewerParams();}
			if ("GraphicalNetworkViewerClassName".equals(pName)) {return app.getGraphicalNetworkViewerClassName();}
			if ("GridPanelClassName".equals(pName)) {return app.getGridPanelClassName();}
			
			// there may be multiple grid viewers
			//if ("GridViewerParams".equals(pName)) {return app.getGridViewerParams();}
			break;
		case 'H':
		  if ("HistogramPlotterParams".equals(pName)) {return app.getHistogramPlotterParams();}
			break;
		case 'I':
		  if ("ImageFile".equals(pName)) {return app.getImageFile();}
			if ("InfoM".equals(pName)) {return Boolean.toString(app.getInfoM());}
			if ("InheritanceHierarchyFile".equals(pName)) {return app.getInheritanceHierarchyFile();}
			if ("InformationFile".equals(pName)) {return app.getInformationFile();}
		  if ("InitialControllerState".equals(pName)) {return Integer.toString(app.getInitialControllerState());}
		  if ("InteractionParams".equals(pName)) {return app.getInteractionParams();}
			if ("IQueueImplName".equals(pName)) {return app.getIQueueImplName();}
			break;
		case 'J':
			if ("JavaClassName".equals(pName)) {return app.getJavaClassName();}
			if ("JavaXhClassName".equals(pName)) {return app.getJavaXhClassName();}
			if ("JavaXhClassClassName".equals(pName)) {return app.getJavaXhClassClassName();}
			//if ("JavaActivityClassName".equals(pName)) {app.setJavaActivityClassName(pValue); return true;}
		  //if ("JdbcParams".equals(pName)) {app.setJdbcParams(pValue); return true;}
		  //if ("JpaParams".equals(pName)) {app.setJpaParams(pValue); return true;}
			break;
		case 'K':
			break;
		case 'L':
			break;
		case 'M':
			if ("ModelName".equals(pName)) {return app.getModelName();}
			if ("MaxProcessLoops".equals(pName)) {return Integer.toString(app.getMaxProcessLoops());}
			if ("MaxPorts".equals(pName)) {return Integer.toString(app.getMaxPorts());}
			//if ("MaxXholons".equals(pName)) {app.setMaxXholons(Integer.parseInt(pValue)); return true;}
			//if ("MaxXholonClasses".equals(pName)) {app.setMaxXholonClasses(Integer.parseInt(pValue)); return true;}
			//if ("MaxStateMachineEntities".equals(pName)) {app.setMaxStateMachineEntities(Integer.parseInt(pValue)); return true;}
			break;
		case 'N':
			break;
		case 'O':
			break;
		case 'P':
			break;
		case 'Q':
			break;
		case 'R':
			if ("RandomNumberSeed".equals(pName)) {return Long.toString(app.getRandomNumberSeed());}
			break;
		case 'S':
			if ("SizeMessageQueue".equals(pName)) {return Integer.toString(app.getSizeMessageQueue());}
			//if ("SaveSnapshots".equals(pName)) {app.setSaveSnapshots(Misc.booleanValue(pValue)); return true;}
			//if ("SnapshotParams".equals(pName)) {app.setSnapshotParams(pValue); return true;}
			break;
		case 'T':
			if ("TimeStep".equals(pName)) {return Integer.toString(app.getTimeStep());}
			if ("TimeStepInterval".equals(pName)) {return Integer.toString(app.getTimeStepInterval());}
			//if ("TreeNodeFactoryDynamic".equals(pName)) {app.setTreeNodeFactoryDynamic(Misc.booleanValue(pValue)); return true;}
			break;
		case 'U':
			if ("UseInteractions".equals(pName)) {return Boolean.toString(app.getUseInteractions());}
			if ("UseDataPlotter".equals(pName)) {return Boolean.toString(app.getUseDataPlotter());}
			if ("UseTextTree".equals(pName)) {return Boolean.toString(app.getUseTextTree());}
			if ("UseGraphicalTreeViewer".equals(pName)) {return Boolean.toString(app.getUseGraphicalTreeViewer());}
			if ("UseGraphicalNetworkViewer".equals(pName)) {return Boolean.toString(app.getUseGraphicalNetworkViewer());}
			if ("UseHistogramPlotter".equals(pName)) {return Boolean.toString(app.getUseHistogramPlotter());}
			if ("UseVrml".equals(pName)) {return Boolean.toString(app.getUseVrml());}
			if ("UseGridViewer".equals(pName)) {return Boolean.toString(app.getUseGridViewer());}
		  //if ("UseXholon2Gui".equals(pName)) {app.setUseXholon2Gui(Misc.booleanValue(pValue)); return true;}
			break;
		case 'V':
			if ("VrmlWriterClassName".equals(pName)) {return app.getVrmlWriterClassName();}
			if ("VrmlParams".equals(pName)) {return app.getVrmlParams();}
			break;
		case 'W':
			break;
		case 'X':
			if ("XincludePath".equals(pName)) {return app.getXincludePath();}
		  //if ("Xholon2GuiClassName".equals(pName)) {app.setXholon2GuiClassName(pValue); return true;}
		  //if ("Xholon2GuiParams".equals(pName)) {app.setXholon2GuiParams(pValue); return true;}
			break;
		case 'Y':
			break;
		case 'Z':
			break;
		default:
			break;
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#getAttributes(org.primordion.xholon.base.IXholon)
	 */
	public Object[][] getAttributes(IXholon node)
	{
	  if (node == null) {return new Object[0][0];}
	  if (node.getClass().getName().equals("org.primordion.xholon.base.Control")) {
			if (node.getRoleName().equals("Application")) {
				node = Application.getApplication(node);
				// TODO some subclasses of Application (ex: sbml) don't return the Application node
				if (node == null) {return new Object[0][0];}
			}
		}
		
		List names = new ArrayList();
		List values = new ArrayList();
		//getAttributes(node, names, values);
		
		if (node == node.getApp()) {
		  // this is the Application node
		  IApplication app = (IApplication)node;
		  Object asAttrs[][] = app.getAppSpecificAttributes(node, (Class<IXholon>)node.getClass(), true);
		  if (asAttrs != null) {
		    for (int i = 0; i < asAttrs.length; i++) {
		      names.add(asAttrs[i][0]);values.add(asAttrs[i][1]); //types.add(asAttrs[i][2]);
		    }
		  }
		  //names.add("UseTextTree"); values.add(app.getUseTextTree());
		  //names.add("NumApplications"); values.add(app.getNumApplications());
		  names.add("MaxPorts"); values.add(app.getMaxPorts());
		  names.add("UseInteractions"); values.add(app.getUseInteractions());
		  names.add("UseDataPlotter"); values.add(app.getUseDataPlotter());
		  names.add("UseGnuplot"); values.add(app.getUseGnuplot());
		  names.add("UseGoogle2"); values.add(app.getUseGoogle2());
		  names.add("MaxProcessLoops"); values.add(app.getMaxProcessLoops());
		  names.add("TimeStepInterval"); values.add(app.getTimeStepInterval());
		  names.add("ModelName"); values.add(app.getModelName());
		  
		  //names.add("UseGridViewer"); values.add(app.getUseGridViewer());
		  //names.add(""); values.add(app.get());
		}
		else if (node.getXhc() == null) {
		  // this is a XholonClass node
		  IXholonClass xhcNode = (IXholonClass)node;
		  names.add("QName"); values.add(xhcNode.getQName());
		  names.add("PrefixedName"); values.add(xhcNode.getPrefixedName());
		  names.add("Prefix"); values.add(xhcNode.getPrefix());
		  names.add("NavInfo"); values.add(xhcNode.getNavInfo());
		  names.add("PortInformation"); values.add(xhcNode.getPortInformation());
		  names.add("XhTypeName"); values.add(xhcNode.getXhTypeName());
		  names.add("DefaultContent"); values.add(xhcNode.getDefaultContent());
		  names.add("ConfigurationInstructions"); values.add(xhcNode.getConfigurationInstructions());
		  names.add("ImplName"); values.add(xhcNode.getImplName());
		  names.add("Mechanism"); values.add(xhcNode.getMechanism());
		  names.add("Prefixed"); values.add(xhcNode.isPrefixed());
		  names.add("XhType"); values.add(xhcNode.getXhType());
		}
		else {
		  Object asAttrs[][] = node.getApp().getAppSpecificAttributes(node, (Class<IXholon>)node.getClass(), true);
		  if (asAttrs != null) {
		    for (int i = 0; i < asAttrs.length; i++) {
		      names.add(asAttrs[i][0]);values.add(asAttrs[i][1]); //types.add(asAttrs[i][2]);
		    }
		  }
		}

		//names.add("Port"); values.add(node.getPort());
		names.add("Container"); values.add(node.isContainer());
		names.add("ActiveObject"); values.add(node.isActiveObject());
		names.add("PassiveObject"); values.add(node.isPassiveObject());
		//names.add("InteractionsEnabled"); values.add(node.isInteractionsEnabled());
		//names.add("XPath"); values.add(node.getXPath());
		//names.add("MsgQ"); values.add(node.getMsgQ());
		//names.add("SystemMsgQ"); values.add(node.getSystemMsgQ());
		//names.add("Interaction"); values.add(node.getInteraction());
		//names.add("IQueueImplName"); values.add(node.getIQueueImplName());
		//names.add("Logger"); values.add(node.getLogger());
		names.add("App"); values.add(node.getApp());
		names.add("ParentNode"); values.add(node.getParentNode());
		names.add("FirstChild"); values.add(node.getFirstChild());
		names.add("NextSibling"); values.add(node.getNextSibling());
		names.add("PreviousSibling"); values.add(node.getPreviousSibling());
		names.add("RootNode"); values.add(node.getRootNode());
		names.add("NumLevels"); values.add(node.getNumLevels());
		names.add("RootNode"); values.add(node.isRootNode());
		names.add("External"); values.add(node.isExternal());
		names.add("Internal"); values.add(node.isInternal());
		names.add("Leaf"); values.add(node.isLeaf());
		//names.add("AttributeHandler"); values.add(node.getAttributeHandler());
		names.add("LastChild"); values.add(node.getLastChild());
		names.add("LastSibling"); values.add(node.getLastSibling());
		names.add("FirstSibling"); values.add(node.getFirstSibling());
		names.add("Siblings"); values.add(node.getSiblings());
		names.add("NumSiblings"); values.add(node.getNumSiblings());
		names.add("UniqueSibling"); values.add(node.isUniqueSibling());
		names.add("UniqueSiblingRoleName"); values.add(node.isUniqueSiblingRoleName());
		names.add("Xhc"); values.add(node.getXhc());
		names.add("XhcId"); values.add(node.getXhcId());
		names.add("XhcName"); values.add(node.getXhcName());
		names.add("XhType"); values.add(node.getXhType());
		names.add("AllPorts"); values.add(node.getAllPorts());
		names.add("RoleName"); values.add(node.getRoleName());
		names.add("Uid"); values.add(node.getUid());
		names.add("Uri"); values.add(node.getUri());
		names.add("Val"); values.add(new Double(node.getVal()));
		names.add("ActionList"); values.add(node.getActionList());
		names.add("Xml2Xholon"); values.add(node.getXml2Xholon());
		names.add("Xholon2Xml"); values.add(node.getXholon2Xml());
		names.add("Name"); values.add(node.getName());
		names.add("Annotation"); values.add(node.getAnnotation());
		//names.add("Factory"); values.add(node.getFactory());
		names.add("Id"); values.add(node.getId());
		names.add("Class"); values.add(node.getClass());
		
		// GwtEnvironment
		/*names.add("HostPageBaseURL"); values.add(GWT.getHostPageBaseURL());
		names.add("ModuleBaseForStaticFiles"); values.add(GWT.getModuleBaseForStaticFiles());
		names.add("ModuleBaseURL"); values.add(GWT.getModuleBaseURL());
		names.add("ModuleName"); values.add(GWT.getModuleName());
		names.add("Version"); values.add(GWT.getVersion());
		
		names.add("AppCodeName"); values.add(Navigator.getAppCodeName());
		names.add("AppName"); values.add(Navigator.getAppName());
		names.add("AppVersion"); values.add(Navigator.getAppVersion());
		names.add("Platform"); values.add(Navigator.getPlatform());
		names.add("UserAgent"); values.add(Navigator.getUserAgent());*/
		
		names.add("HostPageBaseURL"); values.add(GwtEnvironment.gwtHostPageBaseURL);
		names.add("ModuleBaseForStaticFiles"); values.add(GwtEnvironment.gwtModuleBaseForStaticFiles);
		names.add("ModuleBaseURL"); values.add(GwtEnvironment.gwtModuleBaseURL);
		names.add("ModuleName"); values.add(GwtEnvironment.gwtModuleName);
		names.add("Version"); values.add(GwtEnvironment.gwtVersion);
		
		names.add("AppCodeName"); values.add(GwtEnvironment.navAppCodeName);
		names.add("AppName"); values.add(GwtEnvironment.navAppName);
		names.add("AppVersion"); values.add(GwtEnvironment.navAppVersion);
		names.add("Platform"); values.add(GwtEnvironment.navPlatform);
		names.add("UserAgent"); values.add(GwtEnvironment.navUserAgent);
		
		// Put all these attributes into an n by 2 array
		Object attributes[][] = new Object[names.size()][2];
		for (int attrIx = 0; attrIx < names.size(); attrIx++) {
			attributes[attrIx][0] = names.get(attrIx);
			attributes[attrIx][1] = values.get(attrIx);
		}
		return attributes;
	}
	
	private native void getAttributes(IXholon cnode, List names, List values) /*-{
	  console.log("Starting getAttributes ...");
	  //console.log(cnode);
	  //for (var prop in cnode.@org.primordion.xholon.base.IXholon) {
    //  if (prop.substr(0,3) == 'set') {
    //    var pname = prop.substr(3);
    //    var pname = pname.charAt(0).toLowerCase() + pname.substr(1);
    //    var pval = cnode[pname];
    //    if (pval !== undefined) {
    //      console.log(pname + ": " + pval);
    //    }
    //    else {
    //      console.log(pname + ":");
    //    }
    //  }
    //}
	}-*/;

	/*
	 * @see org.primordion.xholon.base.IReflection#getAppSpecificAttributes(org.primordion.xholon.base.IXholon, boolean, boolean, boolean)
	 */
	public Object[][] getAppSpecificAttributes(IXholon node,
			boolean returnAll, boolean returnStatics, boolean returnIfUnPaired)
	{
	  List names = new ArrayList();
		List values = new ArrayList();
		List types = new ArrayList();
		//println(node.getClass().getName());
		if (node.getClass().getName().equals("org.primordion.xholon.base.Control")) {
			if (node.getRoleName().equals("Application")) {
				node = Application.getApplication(node);
				// TODO some subclasses of Application (ex: sbml) don't return the Application node
				if (node == null) {return new Object[0][0];}
			}
		}
		
		// testing
		//names.add("Val");values.add(node.getVal());types.add(Double.class);
		//names.add("RoleName");values.add(node.getRoleName());types.add(String.class);
		//names.add("Uid");values.add(node.getUid());types.add(String.class);
	
		//Object attributes[][] = new Object[3][2];
		//attributes[0][0] = "Val"; attributes[0][1] = node.getVal(); //new Double(node.getVal());
		//attributes[1][0] = "RoleName"; attributes[1][1] = node.getRoleName();
		//attributes[2][0] = "Uid"; attributes[2][1] = node.getUid();
		
		Object asAttrs[][] = node.getApp().getAppSpecificAttributes(node, (Class<IXholon>)node.getClass(), returnAll);
		if (asAttrs != null) {
		  for (int i = 0; i < asAttrs.length; i++) {
		    names.add(asAttrs[i][0]);values.add(asAttrs[i][1]);types.add(asAttrs[i][2]);
		  }
		}
		
		// Put all these attributes into an n by 3 array
		Object attributes[][] = new Object[names.size()][3];
		for (int attrIx = 0; attrIx < names.size(); attrIx++) {
			attributes[attrIx][0] = names.get(attrIx);
			attributes[attrIx][1] = values.get(attrIx);
			attributes[attrIx][2] = types.get(attrIx);
		}
		return attributes;
	}

	/*
	 * @see org.primordion.xholon.base.IReflection#getAttributeStringVal(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public String getAttributeStringVal(IXholon xhNode, String attrName)
	{
		String currentStrVal = null;
		if ("val".equals(attrName)) {
			currentStrVal = xhNode.getVal_String();
		}
		else if ("uid".equals(attrName)) {
			currentStrVal = xhNode.getUid();
		}
		else if ("roleName".equals(attrName)) {
			currentStrVal = xhNode.getRoleName();
		}
		else {
		  currentStrVal = (String)xhNode.getApp().getAppSpecificAttribute(xhNode, (Class<IXholon>)xhNode.getClass(), attrName);
			//System.err.println("ReflectionJavaMicro getAttributeStringVal(): not val or uid or rolename but " + attrName);
		}
		return currentStrVal;
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#getAttributeIntVal(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public int getAttributeIntVal(IXholon xhNode, String attrName)
	{
		return getAttributeIntVal(xhNode.getClass(), attrName);
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#getAttributeIntVal(java.lang.Class, java.lang.String)
	 */
	public int getAttributeIntVal(Class aClass, String attrName)
	{
	  // if the attrName is actually an attribute value, then just return it as an int
		if ((Misc.isdigit(Misc.getNumericValue(attrName.charAt(0)))) || (attrName.charAt(0) == '-')) {
			return Misc.atoi(attrName, 0);
		}
		
		int iVal = 0;
		int len = attrName.length();
		
		if (ClassHelper.isAssignableFrom(Xholon.class, aClass)) {
		  iVal = Application.getApplication().findAppSpecificConstantValue(aClass, attrName);
		  if (iVal != Integer.MIN_VALUE) {
		    return iVal;
		  }
		  else {iVal = 0;}
		}
		
		// search for the value at the end of the String
		// ex: Port17 2 P_PARTNER5 ConnectionPoint1
		// is this an ISignal constant ?
		if (aClass == ISignal.class) {
			if ("SIGNAL_INIT_FSM".equals(attrName))        {iVal = ISignal.SIGNAL_INIT_FSM;}
			else if ("SIGNAL_TIMEOUT".equals(attrName))    {iVal = ISignal.SIGNAL_TIMEOUT;}
			else if ("SIGNAL_DOACTIVITY".equals(attrName)) {iVal = ISignal.SIGNAL_DOACTIVITY;}
			else if ("SIGNAL_DUMMY".equals(attrName))      {iVal = ISignal.SIGNAL_DUMMY;}
			else if ("SIGNAL_ANY".equals(attrName))        {iVal = ISignal.SIGNAL_ANY;}
			else {}
			return iVal;
		}
		// check if attrName is null or has no trailing numeric value
		if ((attrName != null) && (len > 0) && (Misc.getNumericValue(attrName.charAt(len-1)) != -1)) {
			for (int ix = len - 1; ix >= 0; ix--) {
				if (Misc.getNumericValue(attrName.charAt(ix)) == -1) { // found a non-digit
					iVal = Misc.atoi(attrName, ix+1);
					break;
				}
				else if (ix == 0) { // found beginning of string
					iVal = Misc.atoi(attrName, ix);
					break;
				}
			}
		}
		// CNPT constants have a number at the end of them that is +1 of the actual value
		// but CNPT_TARGET has no number at the end, so it will have a correct value of 0
		if (aClass == StateMachineEntity.class) {
			//System.out.println(aClass + " " + attrName);
			if (iVal > 0) {
				iVal--;
			}
		}
		return iVal;
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#getAttributeDoubleVal(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public double getAttributeDoubleVal(IXholon xhNode, String attrName)
	{
	  // if the attrName is actually an attribute value, then just return it as an int
		if ((Misc.isdigit(Misc.getNumericValue(attrName.charAt(0)))) || (attrName.charAt(0) == '-')) {
			return Misc.atod(attrName, 0);
		}
		double dVal = 0.0;
		Object dValObj = xhNode.getApp().getAppSpecificAttribute(xhNode, (Class<IXholon>)xhNode.getClass(), attrName);
		if (dValObj != null) {
	    dVal = (Double)dValObj;
		}
		return dVal;
		//return getAttributeIntVal(xhNode.getClass(), attrName);
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#getAttributeDoubleVal(java.lang.Class, java.lang.String)
	 */
	public double getAttributeDoubleVal(Class aClass, String attrName)
	{
	  // not implemented in ReflectionJavaStandard.java
		return 0.0;
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#getAttributeSynthetic(org.primordion.xholon.base.IXholon)
	 */
	public IXholon getAttributeSynthetic(IXholon tNode)
	{
		return null;
	}
	
	public String getAttributeStringVal_UsingGetter(Object tNode, String attrName)
	{
	  if (tNode == null) {return null;}
		if (attrName == null) {return null;}
		String currentStrVal = null;
		Class<IXholon> myClass = (Class<IXholon>)tNode.getClass();
	  currentStrVal = (String)((IXholon)tNode).getApp().getAppSpecificAttribute((IXholon)tNode, myClass, attrName);
		return currentStrVal;
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#setAttributeVal(org.primordion.xholon.base.IXholon, java.lang.String, java.lang.String, int)
	 */
	public int setAttributeVal(Object tNode, String attrName, String val, int valIx)
	{
		return ((IXholon)tNode).setAttributeVal(attrName, val.substring(valIx));
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#setAttributeVal_UsingSetter(java.lang.Object, java.lang.String, java.lang.String, int)
	 */
	public int setAttributeVal_UsingSetter(Object tNode, String attrName, String val, int valIx)
	{
		return ((IXholon)tNode).setAttributeVal(attrName, val.substring(valIx));
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#setAttributeVal_UsingSetter(java.lang.Object, java.lang.String, java.lang.String, int, int)
	 */
	public int setAttributeVal_UsingSetter(Object tNode, String attrName, String val, int valIx, int classType)
	{
	  IXholon node = (IXholon)tNode;
	  IApplication app = node.getApp();
		if (app.getAppSpecificAttribute(node, (Class<IXholon>)node.getClass(), attrName) != null) {
		  app.setAppSpecificAttribute(node, (Class<IXholon>)node.getClass(), attrName, val.substring(valIx));
		  return 0;
		}
		else {
		  return node.setAttributeVal(attrName, val.substring(valIx));
		}
	}
	
	public void setAttributeVal_UsingSetter(Object tNode, String attrName, Object val, Class clazz)
	{
		
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#setAttributeObjectVal(org.primordion.xholon.base.IXholon, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean setAttributeObjectVal(IXholon tNode, String attrName, IXholon val)
	{
	  //System.out.println("Reflect setAttributeObjectVal( " + tNode + " " + attrName + " " + val);
		boolean rc = true; // assume success unless there's an exception
		if (attrName.equals("val")) {
			tNode.setVal(val);
			return rc;
		}
		return tNode.getApp().setAppSpecificObjectVal(tNode, (Class<IXholon>)tNode.getClass(), attrName, val);
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#setAttributeObjectVal_UsingSetter(org.primordion.xholon.base.IXholon, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean setAttributeObjectVal_UsingSetter(IXholon tNode, String attrName, IXholon val)
	{
	  //System.out.println("Reflect setAttributeObjectVal_UsingSetter( " + tNode + " " + attrName + " " + val);
		boolean rc = true; // assume success unless there's an exception
		if (attrName.equals("val")) {
			tNode.setVal(val);
			return rc;
		}
		return tNode.getApp().setAppSpecificObjectVal(tNode, (Class<IXholon>)tNode.getClass(), attrName, val);
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#setAttributeBooleanVal(org.primordion.xholon.base.IXholon, java.lang.String, boolean)
	 */
	public boolean setAttributeBooleanVal(IXholon tNode, String attrName, boolean val)
	{
		boolean rc = true; // assume success unless there's an exception
		if (attrName.equals("val")) {
			tNode.setVal(val);
			return rc;
		}
		else {
		  System.out.println("Reflection.setAttributeBooleanVal( "
	      + tNode.getClass().getName() + " " + attrName + " " + val);
		}
		return rc;
	}

	/*
	 * @see org.primordion.xholon.base.IReflection#setAttributeArrayVal(org.primordion.xholon.base.IXholon, java.lang.String, java.lang.String, int, int)
	 */
	public int setAttributeArrayVal(IXholon tNode, String attrName, String val, int valIx, int arraySubscript)
	{
	  //System.out.println("Reflection.setAttributeArrayVal( " + tNode.getClass().getName() + " " + attrName
	  //  + " " + Misc.atoi(val, valIx) + " " + arraySubscript);
	  
	  if (tNode.getClass() == org.primordion.cellontro.base.BioXholonClass.class) {
	    if ("geneVal".equals(attrName)) {
	      ((org.primordion.cellontro.base.BioXholonClass)tNode).geneVal[arraySubscript]
	        = Misc.atoi(val, valIx);
	    }
	  }
	  
	  else {
	    System.out.println("Reflection.setAttributeArrayVal( "
	      + tNode.getClass().getName() + " " + attrName
	      + " " + Misc.atoi(val, valIx) + " " + arraySubscript);
	  }
	  
		int classType = Misc.JAVACLASS_UNKNOWN;
		return classType;
	}

	/*
	 * @see org.primordion.xholon.base.IReflection#setAttributeArrayVal(org.primordion.xholon.base.IXholon, java.lang.String, java.lang.Object, int)
	 */
	public boolean setAttributeObjectArrayVal(IXholon tNode, String attrName, Object val, int arraySubscript)
	{
		boolean rc = true; // assume success unless there's an exception
		return rc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.primordion.xholon.base.IReflection#createObject_UsingConstructor(java.lang.Class, org.primordion.xholon.base.IXholon)
	 */
	public Object createObject_UsingConstructor(Class clazz, IXholon argsParent)
	{
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#createObject_UsingConstructor(java.lang.Class, java.lang.Object[], java.lang.Class[])
	 */
	public Object createObject_UsingConstructor(Class clazz, Object[] val, Class[] argType)
	{
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#getAllPorts(org.primordion.xholon.base.IXholon, boolean)
	 */
	public List getAllPorts(IXholon xhNode, boolean includeUnboundPorts) {
		List<PortInformation> realPortList = new ArrayList<PortInformation>();
	  if ((xhNode == null) || (xhNode.getXhc() == null)) {return realPortList;};
		List xhcPortList = xhNode.getXhc().getPortInformation();
		// eval the XPath expressions to determine the reffed nodes
		Iterator portIt = xhcPortList.iterator();
		while (portIt.hasNext()) {
			PortInformation pi = (PortInformation)portIt.next();
			if (pi.getReffedNode() == null) {
			  IXholon reffedNode = ((Xholon)xhNode).getXPath().evaluate(pi.getXpathExpression().trim(), xhNode);
			  if (reffedNode == null) {
			    if (includeUnboundPorts) {
			    	realPortList.add(pi);
				  }
			  }
			  else {
				  pi.setReffedNode(reffedNode);
				  realPortList.add(pi);
			  }
			}
			else {
			  realPortList.add(pi);
			}
		}
		return realPortList;
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#getAllPorts(org.primordion.xholon.base.IXholon, boolean, java.lang.String)
	 */
	public List getAllPorts(IXholon xhNode, boolean includeUnboundPorts, String portName) {
		List<PortInformation> realPortList = new ArrayList<PortInformation>();
	  if ((xhNode == null) || (xhNode.getXhc() == null)) {return realPortList;};
		List<PortInformation> xhcPortList = xhNode.getXhc().getPortInformation();
		// eval the XPath expressions to determine the reffed nodes
		Iterator<PortInformation> portIt = xhcPortList.iterator();
		while (portIt.hasNext()) {
			PortInformation pi = (PortInformation)portIt.next();
			if (pi.getFieldName().equals(portName)) {
			  if (pi.getReffedNode() == null) {
			    IXholon reffedNode = ((Xholon)xhNode).getXPath().evaluate(pi.getXpathExpression().trim(), xhNode);
			    if (reffedNode == null) {
				    if (includeUnboundPorts) {
				    	realPortList.add(pi);
				    }
			    }
			    else {
				    pi.setReffedNode(reffedNode);
				    realPortList.add(pi);
			    }
			  }
			  else {
			    realPortList.add(pi);
			  }
			}
		}
		return realPortList;
	}
	
	/*
	 * @see org.primordion.xholon.base.IReflection#isAttribute(org.primordion.xholon.base.IXholon, java.lang.String, java.lang.Class)
	 */
	public boolean isAttribute(IXholon tNode, String attrName, Class clazz)
	{
	  if (tNode == null) {return false;}
		if (attrName == null) {return false;}
		if (clazz == null) {return false;}
		boolean rc = true;
		String tNodeXhcName = tNode.getXhcName();
		// the Xholon collection classes always retain their Attribute_ tags as children
		// ex: ir.isAttribute( suspendedMass_1 [ port:earth_12] position:0.0 velocity:0.0 SuspendedMass position double
		System.out.println("ir.isAttribute( " + tNode + " " + tNodeXhcName + " " + attrName + " " + clazz.getName());
		if ("XholonMap".equals(tNodeXhcName)) {rc = false;}
		else if ("XholonList".equals(tNodeXhcName)) {rc = false;}
		else if ("XholonSet".equals(tNodeXhcName)) {rc = false;}
		// a method in AppGenerator isAppSpecificAttribute()
		else if (!tNode.getApp().isAppSpecificAttribute(tNode, (Class<IXholon>)tNode.getClass(), attrName)) {
		  System.out.println("ir.isAttribute( false");
		  // TODO this could be a system/service class such as SvgClient;
		  // return true in that case on the assumption that these are well-known classes
		  // This list should include any such class that may have <Attribute children used to set an internal attribute
		  // The list at this point is taken from TreeNodeFactoryNew
		  String tNodeClassName = tNode.getClass().getName();
		  if (tNodeClassName.startsWith("org.primordion.xholon.base")) {rc = true;}
		  else if (tNodeClassName.startsWith("org.primordion.xholon.io")) {rc = true;}
		  else if (tNodeClassName.startsWith("org.primordion.xholon.mech.petrinet")) {rc = true;}
		  else if (tNodeClassName.startsWith("org.primordion.xholon.service")) {rc = true;} // ?
		  else if ("SvgClient".equals(tNodeXhcName)) {rc = true;}
		  else {rc = false;}
		}
		System.out.println("ir.isAttribute( rc = " + rc);
		return rc;
	}

	/*
	 * @see org.primordion.xholon.base.IReflection#isAttribute(java.lang.Object, java.lang.String, java.lang.Class)
	 */
	public boolean isAttribute(Object obj, String attrName, Class clazz)
	{
	  if (obj == null) {return false;}
		if (attrName == null) {return false;}
		if (clazz == null) {return false;}
		boolean rc = true;
		return rc;
	}
}
