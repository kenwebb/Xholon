/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.xholon.app;

//import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
//import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.TextAreaElement;
//import com.google.gwt.http.client.RequestBuilder;
//import com.google.gwt.http.client.RequestCallback;
//import com.google.gwt.http.client.RequestBuilder.Method;
//import com.google.gwt.http.client.RequestException;
//import com.google.gwt.http.client.Request;
//import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;

//import java.io.PrintWriter; // GWT
//import java.io.Writer; // GWT
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.client.GwtEnvironment;
import org.client.HtmlElementCache;
import org.client.RCConfig;
import org.client.SystemMechSpecific;
import org.client.XholonJsApi;

import org.primordion.xholon.base.IAttribute;
import org.primordion.xholon.base.IControl;
import org.primordion.xholon.base.IInheritanceHierarchy;
import org.primordion.xholon.base.IInteraction;
import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IQueue;
import org.primordion.xholon.base.IReflection;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.InheritanceHierarchy;
import org.primordion.xholon.base.Interaction;
import org.primordion.xholon.base.Msg;
import org.primordion.xholon.base.Parameters;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.Queue;
import org.primordion.xholon.base.QueueSynchronized;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.XholonClass;
//import org.primordion.xholon.base.XholonTime; // GWT ant build
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.Attribute.Attribute_Object;
import org.primordion.xholon.common.mechanism.CeControl;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.io.GridViewerDetails;
import org.primordion.xholon.io.HistogramViewerD3;
import org.primordion.xholon.io.HistogramViewerGnuplot;
import org.primordion.xholon.io.HistogramViewerGoogle2;
import org.primordion.xholon.io.IAbout;
import org.primordion.xholon.io.IChartViewer;
import org.primordion.xholon.io.IGraphicalNetworkViewer;
import org.primordion.xholon.io.IGraphicalTreeViewer;
import org.primordion.xholon.io.IGridPanel;
import org.primordion.xholon.io.IHistogramViewer;
import org.primordion.xholon.io.ISnapshot;
import org.primordion.xholon.io.IViewer;
import org.primordion.xholon.io.IXholonGui;
import org.primordion.xholon.io.IXholon2Gui;
//import org.primordion.xholon.io.Snapshot; // GWT
//import org.primordion.xholon.io.SnapshotXML; // GWT
import org.primordion.xholon.io.XholonGwtTabPanelHelper;
import org.primordion.xholon.io.XholonWorkbookBundle;
//import org.primordion.xholon.io.ef.other.Xholon2ChapNetwork;
//import org.primordion.xholon.io.vrml.AbstractVrmlWriter;
import org.primordion.xholon.io.vrml.IVrmlWriter;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXml2Xholon;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.io.xml.Xml2Mechanism;
import org.primordion.xholon.io.xml.Xml2Xholon;
import org.primordion.xholon.io.xml.Xml2XholonClass;
import org.primordion.xholon.service.AbstractXholonService;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.XholonCreationService;
import org.primordion.xholon.service.creation.ITreeNodeFactory;
//import org.primordion.xholon.service.ef.IXholon2GraphFormat;
import org.primordion.xholon.util.ClassHelper;
import org.primordion.xholon.util.Misc;
import org.primordion.xholon.util.MiscRandom;
import org.primordion.xholon.util.StringTokenizer;

/**
 * This is an abstract class from which all Xholon applications should subclass.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on October 13, 2005)
 */
public abstract class Application extends AbstractApplication implements IApplication { //, Runnable {  GWT
	private static final long serialVersionUID = 5911227929337012857L;
	
	// constants
	protected static final int LOOP_FOREVER = -1;
	protected static final long DEFAULT_RANDOM_NUMBER_SEED = -1L; // don't use a seed
	protected static final boolean INVOKE_VIEWERS_ON_WRAPUP = false; // unconditionally invoke graphical viewers
	protected static final String MECHANISM_XML_FILENAME = "XholonMechanism"; //"_mechanism/XholonMechanism.xml";
	protected static final String MECHANISM_CD_XML_FILENAME = "XholonMechanismCd"; //"_mechanism/XholonMechanismCd.xml"; // class details
	protected static final String MECHANISMHIERARCHY_FILENAME = "XhMechanisms"; //"XhMechanisms.xml";
	protected static final String MECHVIEWER_XML_FILENAME = "XholonViewer"; //"_viewer/XholonViewer.xml";
	protected static final String MECHVIEWER_CD_XML_FILENAME = "XholonViewerCd"; //"_viewer/XholonViewerCd.xml"; // class details
	protected static final String SERVICEHIERARCHY_FILENAME = "Service_CompositeStructureHierarchy"; //"_service/Service_CompositeStructureHierarchy.xml"; // services
	protected static final String CONTROLHIERARCHY_FILENAME = "Control_CompositeHierarchy"; //"_control/Control_CompositeHierarchy.xml"; // control nodes
	
	/**
	 * Whether or not this application can use multiple threads when starting up.
	 * Single- vs. multi-threaded may be of concern when Xholon is running in certain environments.
	 * This is the default value, which can be overriden with a command line argument.
	 * ex: -appMultiThreaded false
	 */
	protected static final boolean DEFAULT_APP_MULTI_THREADED = true;
	
	//                                 Input parameters.
	
	/** Name of the inheritance hierachy file. */
	protected String inheritanceHierarchyFile = null;
	
	/** Name of the composite structure hierarchy file. */
	protected String compositeStructureHierarchyFile = null;
	
	/** Name of the class details file. */
	protected String classDetailsFile = null;
	
	/** Name of the information file. */
	protected String informationFile = null;
	
	/** Name of an optional image file, typically SVG such as "default.svg". */
	protected String imageFile = null;
	
	/** Maximum number of Xholons to use in the application. */
	protected int maxXholons = 0;
	
	/** Maximum number of Xholon classes to use in the application. */
	protected int maxXholonClasses = 0;
	
	/** Maximum number of State Machine Entities to use in the application. */
	protected int maxStateMachineEntities = 0;
	
	/** Maximum number of Activities to use in the application. */
	protected int maxActivities = 0;
	
	/** Number of times to loop within process() = maximum number of time steps.
	 * -1 = loop forever = infinite number of time steps */
	protected int maxProcessLoops = 10;
	
	/** Time step interval. Number of milliseconds per time step.
	 * 0 = max speed.
	 * 10 = 10 milliseconds per time step = 100 time steps per second.
	 * 1000 = 1000 milliseconds per time step = 1 second per time step = 1 time step per second.
	 * 0 = logical time. Any other positive value = physical time (clock time). 
	 */
	protected int timeStepInterval = 10;
	
	/** Whether or not to call view.act() each timeStep. */
	protected boolean shouldStepView = false;
	
	/** @deprecated Whether to use the default tree viewer. */
	// protected boolean useDefaultViewer = true;
	
	/** Whether to use simple 2D text tree drawer. */
	protected boolean useTextTree = false;
	
	/** Whether to use the JUNG graphical tree viewer. */
	protected boolean useGraphicalTreeViewer = false;
	
	/** Graphical tree viewer parameters. */
	protected String graphicalTreeViewerParams
		= "/,20,20,800,500,9,Application Composite Structure,false";
	
	/** Whether to use the JUNG graphical network viewer. */
	protected boolean useGraphicalNetworkViewer = false;
	
	/**  */
	protected String graphicalNetworkViewerClassName
		= "org.primordion.xholon.io.NetworkViewerJung";
	
	/** Graphical tree viewer parameters. */
	protected String graphicalNetworkViewerParams
		= "/,800,500,9,LAYOUT_KK,false,Application Port Connections,false";
	
	/** Whether to use a graphical grid viewer. */
	protected boolean useGridViewer = false;
	
	/** Name of the GridViewer Panel Java class. */
	protected String gridPanelClassName = null;
	
	/** Grid viewer parameters. */
	//protected String gridViewerParams
	//	= "descendant::Row/..,5,Grid Viewer App";
	
	/** Whether to use a data plotter (google2, gnuplot, c3, nvd3, none). */
	protected boolean useDataPlotter = false;
	
	/**  */
	protected String dataPlotterParams
		= "Title,Time (timesteps),Y,./statistics/,stats,1,WRITE_AS_LONG";
	
	protected boolean useJFreeChart = false;
	protected boolean useGnuplot = false;
	protected boolean useJdbc = false;
	protected boolean useJpa = false;
	protected boolean useGoogle = false;
	protected boolean useGoogle2 = false;
	protected boolean useC3 = false;
	protected boolean useNVD3 = false;
	
	/** Whether to use a histogram plotter (google2, gnuplot, none). */
	protected boolean useHistogramPlotter = false;
	
	/**  */
	protected String histogramPlotterParams
		= "Title,Time (timesteps),Y";
	
	protected boolean useJFreeChart_Hist = false;
	protected boolean useGnuplot_Hist = false;
	protected boolean useGoogle2_Hist = false;
	protected boolean useD3_Hist = false;
	
	/** whether or not to capture Interactions */
	protected boolean interactionsEnabled = false;
	
	/** Interaction params */
	protected String interactionParams = "2,false,localhost,60001";
	
	/** Whether to use VRML 3D graphics output. */
	protected boolean useVrml = false;
	
	/** Name of the VrmlWriter Java class. */
	protected String vrmlWriterClassName = null;
	
	/** VRML 3D parameters */
	protected String vrmlParams = "true,./3d/,false,false";
	
	/** Name of the concrete application class. */
	protected String javaClassName = null;
	
	/** Name of the concrete Xh class. */
	protected String javaXhClassName = null;
	
	/** Name of the concrete IXholonClass. */
	protected String javaXhClassClassName
		= "org.primordion.xholon.base.XholonClass"; // default
	
	/** Name of the concrete activity class. */
	protected String javaActivityClassName = null;
	
	/** Whether to save snapshots to XML files. */
	protected boolean saveSnapshots = false;
	
	/** Snapshot parameters. */
	protected String snapshotParams = "0,true,./snapshot/";
	
	/** Script parameters, for use by ScriptService. */
	protected String scriptParams = null;
	
	/** Name of the model. */
	protected String modelName = "Unknown model name";
	
	/** Whether to use TreeNodeFactoryDynamic or TreeNodeFactoryStatic. */
	protected boolean treeNodeFactoryDynamic = true;
	
	/** Seed for random number generator. */
	protected long randomNumberSeed = DEFAULT_RANDOM_NUMBER_SEED;
	
	/** Maximum number of ports that a xholon instance can have. */
	protected int maxPorts = 0;
	
	/** xincludePath */
	//protected String xincludePath = "/config/_common/"; // Java Standard Edition
	//protected String xincludePath = "./config/_common/"; // Java Standard Edition
	//protected String xincludePath = "/config/_common/"; // Java Micro Edition, MIDP
	protected String xincludePath = "http://127.0.0.1:8888/config/_common/"; // GWT
	
	/** Xhym agent. */
	protected String xhymAgent = null;
	
	/** Whether or not to create a web or other special purpose GUI. */
	protected boolean useXholon2Gui = false;
	
	/** Xholon2Gui parameters. */
	protected String xholon2GuiParams
		= "/jetty-6.1.5/webapps/xholon/,1,200,http://localhost:8080/xholon/,false,false,true";
	
	/** The concrete class that implements the IXholon2Gui interface. */
	protected String xholon2GuiClassName = "org.primordion.xholon.io.Xholon2Xul";
	
	/** JDBC parameters */
	protected String jdbcParams = "org.postgresql.Driver,postgresql,localhost,5432,postgres,postgres,password";
	
	/** JPA parameters */
	protected String jpaParams = "org.postgresql.Driver,postgresql,localhost,5432,postgres,postgres,password";
	
	/** Name of the Java class that implements IQueue. */
	protected String iQueueImplName = "org.primordion.xholon.base.Queue";
	
	/** Controller state when an app first starts up.
	 * The possible values are found in IControl.
	 * The most common initial values are:
	 * <pre>IControl.CS_INITIALIZED = 2</pre>
	 * <pre>IControl.CS_RUNNING = 3</pre>
	 */
	protected int initialControllerState = 2;
	
	/** Action that an Attribute node should take during its postConfigure() method.
	 *  See IAttribute.java for a list of possible values.
	 */
	protected int attributePostConfigAction = IAttribute.ATTR_DEFAULT ; //1
	
	/** Singleton instance of IApplication. */
	protected static IApplication application = null;
	
	/** Multiple instances of IApplication. */
	protected static HashMap<IXholon, IApplication> applicationH
		= new HashMap<IXholon, IApplication>();
	
	/** Last inserted application. This is useful when there is more than one application. */
	protected static IApplication lastInsertedApplication = null;
	
	/** role name */
	protected String roleName = null;
	
	/** Factory. */
	protected ITreeNodeFactory factory = null;
	
	/** Inheritance hierarchy. */
	protected transient IInheritanceHierarchy inherHier = null;
	
	/** Root of Mechanism node hierarchy. */
	protected transient IMechanism mechRoot = null;
	
	/** Root of XholonService node hierarchy.
	 */
	protected IXholon srvRoot = null;
	
	/** Root of the top level Control hierarchy. */
	protected IControl controlRoot = null;
	
	/**
	 * The default mechanism if no other one is specified.
	 * ex: http://www.primordion.com/namespace/Xholon
	 */
	protected IMechanism defaultMechanism = null;
	
	/** Root of XholonClass node hierarchy. */
	protected IXholonClass xhcRoot = null;
	
	/** Root of Xholon node hierarchy. */
	protected IXholon root = null;
	
	/** Tree viewer implemented by the JUNG software. */
	protected IGraphicalTreeViewer treeViewerJung = null;
	
	/** Network viewer implemented by the JUNG software. */
	protected IGraphicalNetworkViewer networkViewerJung = null;
	
	/** Zero or more instances of GridViewerDetails. */
	protected transient Vector<GridViewerDetails> gridViewers = null;
	
	/** Controller. */
	//protected transient IControl controller = null;
	
	/** View. */
	protected IControl view = null; // 
	
	/** Application root. */
	protected IXholon appRoot = null;
	
	/** Whether or not this app is running in an applet environment. */
	protected boolean applet = false;
	
	/** Xhym Space root. */
	protected transient IXholon xhymSpace = null;
	
	/** An external GUI, if there is one. */
	protected transient Object gui = null;
	
	/** Chart. */
	protected IChartViewer chartViewer = null;
	
	/** Histogram viewer. */
	protected IHistogramViewer histogramViewer = null;
	
	/** Snapshot. */
	protected transient ISnapshot snapshot = null;
	
	/** Interaction. */
	protected transient IInteraction interaction = new Interaction(); // just one Interaction can be captured
	
	/** Application Message Queue. */
	protected transient IQueue msgQ = new Queue(DEFAULT_SIZE_MSG_Q);
	
	/** System Message Queue. */
	protected transient IQueue systemMsgQ = new QueueSynchronized(DEFAULT_SIZE_SYSTEM_MSG_Q);
	
	/** Next ID used by Xholon. */
	protected transient int nextId = -1;
	
	/** Next ID used by XholonClass */
	protected transient int nextXholonClassId = -1; // = 0; with old InheritanceHierarchy approach
	
	/** Current time step. */
	protected transient int timeStep = 0;
	
	/** Current state of the application controller. */
	protected transient int controllerState = IControl.CS_NO_MODEL_LOADED;
	
	/** Text about Xholon. */
	protected transient String aboutText =
		  "    Xholon library\n"
		+ "    version 0.9.0\n"
		+ "    Copyright (C) 2005 - 2014 Ken Webb\n"
		+ "    Licensed under GNU Lesser General Public License\n"
		+ "    www.primordion.com/Xholon";
	
	protected transient String configFileName = null; // name of the _xhn.xml config file; used at app startup
	
	/**
	 * Default writer for use with Xholon.print() and Xholon.println()
	 */
	//protected transient Writer out = new PrintWriter(System.out, true); // GWT ant build
	
	/**
	 * Whether or not to use Application.out when exporting to external formats,
	 * and in other situations where data might be instead written to a file.
	 */
	protected transient boolean useAppOut = false;
	
	/**
	 * Whether or not users are allowed to configure a Service or ExternalFormat writer,
	 * just before the service or writer starts to do its things.
	 */
	protected transient boolean allowConfigSrv = false;
	
	/**
	 * Whether or not the app is running in a Google Web Toolkit (GWT) environment.
	 */
	protected transient boolean useGwt = true; // GWT
	
	/**
	 * GWT host page base URL.
	 */
	protected transient String hostPageBaseURL = "";
	
	/**
	 * GWT Timer used by process() loop.
	 */
	private Timer processTimer = null;
	
	/**
	 * Whether or not the tree GUI is being used. GWT
	 */
	private boolean noGui = false;
	
	/**
	 * Optional ID of a XholonWorkbook that should be loaded at config-time,
	 * typically as a last child of root in a Chameleon app.
	 * ex: "gist123456" "gist3457105"
	 */
	private String workbookId = null;
	
	/**
	 * Name of the file to be loaded from a gist (XholonWorkbook).
	 * Typically, this is just the default "xholonWorkbook.xml".
	 * ex: "crn_1987_6_7_csh.xml"
	 */
	private String workbookFileName = "xholonWorkbook.xml";
	
	/**
	 * An optional XholonWorkbook bundle,
	 * containing alternate user-defined content for the current Java-based app.
	 * This can be used while initializing the app.
	 */
	private XholonWorkbookBundle workbookBundle = null;
	
	/**
	 * Constructor.
	 */
	public Application() {}
		
	//                                        Setters for input parameters
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setClassDetailsFile(java.lang.String)
	 */
	public void setClassDetailsFile(String classDetailsFile) {
	  if (classDetailsFile.length() == 0) {this.classDetailsFile = null;}
		else if (useGwt) {
	    this.classDetailsFile = hostPageBaseURL + classDetailsFile;
	  }
	  else {
	    this.classDetailsFile = classDetailsFile;
	  }
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#setCompositeStructureHierarchyFile(java.lang.String)
	 */
	public void setCompositeStructureHierarchyFile(String compositeStructureHierarchyFile) {
	  if (compositeStructureHierarchyFile.length() == 0) {this.compositeStructureHierarchyFile = null;}
		else if (useGwt) {
	    this.compositeStructureHierarchyFile = hostPageBaseURL + compositeStructureHierarchyFile;
	  }
	  else {
	    this.compositeStructureHierarchyFile = compositeStructureHierarchyFile;
	  }
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#setInheritanceHierarchyFile(java.lang.String)
	 */
	public void setInheritanceHierarchyFile(String inheritanceHierarchyFile) {
	  if (inheritanceHierarchyFile.length() == 0) {this.inheritanceHierarchyFile = null;}
	  else if (useGwt) {
		  this.inheritanceHierarchyFile = hostPageBaseURL + inheritanceHierarchyFile;
		}
		else {
		  this.inheritanceHierarchyFile = inheritanceHierarchyFile;
		}
  }
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setInformationFile(java.lang.String)
	 */
	public void setInformationFile(String informationFile) {this.informationFile = informationFile;}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setImageFile(java.lang.String)
	 */
	public void setImageFile(String imageFile) {this.imageFile = imageFile;}

	/*
	 * @see org.primordion.xholon.app.IApplication#setMaxProcessLoops(int)
	 */
	public void setMaxProcessLoops(int maxProcessLoops) {this.maxProcessLoops = maxProcessLoops;}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setTimeStepInterval(int)
	 */
	public void setTimeStepInterval(int timeStepInterval) {this.timeStepInterval = timeStepInterval;}

	/** @param maxStateMachineEntities The maxStateMachineEntities to set. */
	public void setMaxStateMachineEntities(int maxStateMachineEntities) {
		this.maxStateMachineEntities = maxStateMachineEntities;}

	/** @param maxActivities The maxActivities to set. */
	public void setMaxActivities(int maxActivities) {
		this.maxActivities = maxActivities;}

	/** @param maxXholonClasses The maxXholonClasses to set. */
	public void setMaxXholonClasses(int maxXholonClasses) {
		this.maxXholonClasses = maxXholonClasses;}

	/** @param maxXholons The maxXholons to set. */
	public void setMaxXholons(int maxXholons) {this.maxXholons = maxXholons;}
	
	/** @param appM The appM to set. */
	public void setAppM(boolean appM) {Msg.appM = appM;}

	/** @param infoM The infoM to set. */
	public void setInfoM(boolean infoM) {Msg.infoM = infoM;}

	/** @param errorM The errorM to set. */
	public void setErrorM(boolean errorM) {Msg.errorM = errorM;}
	
	/** @param debugM The debugM to set. */
	public void setDebugM(boolean debugM) {Msg.debugM = debugM;}

	/** @deprecated @param useDefaultViewer The useDefaultViewer to set. */
	//public void setUseDefaultViewer(boolean useDefaultViewer) {this.useDefaultViewer = useDefaultViewer;}

	/** @param useTextTree The useTextTree to set. */
	public void setUseTextTree(boolean useTextTree) {this.useTextTree = useTextTree;}

	/**
	 * @param shouldStepView the shouldStepView to set
	 */
	public void setShouldStepView(boolean shouldStepView) {
		this.shouldStepView = shouldStepView;
	}

	/** @param useGraphicalTreeViewer The useGraphicalTreeViewer to set. */
	public void setUseGraphicalTreeViewer(boolean useGraphicalTreeViewer)
		{this.useGraphicalTreeViewer = useGraphicalTreeViewer;}
	
	/**  */
	public void setGraphicalTreeViewerParams(String graphicalTreeViewerParams)
		{this.graphicalTreeViewerParams = graphicalTreeViewerParams;}

	/** @param useGraphicalNetworkViewer The useGraphicalNetworkViewer to set. */
	public void setUseGraphicalNetworkViewer(boolean useGraphicalNetworkViewer)
		{this.useGraphicalNetworkViewer = useGraphicalNetworkViewer;}
	
	/** graphicalNetworkViewerClassName */
	public void setGraphicalNetworkViewerClassName(String graphicalNetworkViewerClassName)
		{this.graphicalNetworkViewerClassName = graphicalNetworkViewerClassName;}
	
	/**  */
	public void setGraphicalNetworkViewerParams(String graphicalNetworkViewerParams)
		{this.graphicalNetworkViewerParams = graphicalNetworkViewerParams;}

	/*
	 * @see org.primordion.xholon.app.IApplication#setUseGridViewer(boolean)
	 */
	public void setUseGridViewer(boolean useGridViewer) {
		this.useGridViewer = useGridViewer;
		if (gridViewers == null) {
			gridViewers = new Vector<GridViewerDetails>();
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setGridPanelClassName(java.lang.String)
	 */
	public void setGridPanelClassName(String gridPanelClassName)
		{this.gridPanelClassName = gridPanelClassName;}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setGridViewerParams(java.lang.String)
	 */
	public void setGridViewerParams(String gridViewerParams)
	{
	  consoleLog("Application setGridViewerParams " + gridViewerParams);
		GridViewerDetails gvd = new GridViewerDetails();
		gvd.useGridViewer = useGridViewer;
		gvd.gridPanelClassName = gridPanelClassName;
		gvd.gridViewerParams = gridViewerParams;
		gridViewers.addElement(gvd);
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setUseDataPlotter(java.lang.String)
	 */
	public void setUseDataPlotter(String useDataPlotter)
	{
		// default values "none"
		this.useDataPlotter = false;
		this.useJFreeChart = false;
		this.useGnuplot = false;
		this.useJdbc = false;
		this.useJpa = false;
		this.useGoogle = false;
		this.useGoogle2 = false;
		this.useC3 = false;
		this.useNVD3 = false;
		// other possible values
		if (useDataPlotter.equals("JFreeChart")) {
			//this.useJFreeChart = true; // can't use JFreeChart with GWT
			this.useGoogle2 = true; // substitute "google2"
			this.useDataPlotter = true;
		}
		else if (useDataPlotter.equals("gnuplot")) {
			this.useGnuplot = true;
			this.useDataPlotter = true;
		}
		else if (useDataPlotter.equals("jdbc")) {
			this.useJdbc = true;
			this.useDataPlotter = true;
		}
		else if (useDataPlotter.equals("jpa")) {
			this.useJpa = true;
			this.useDataPlotter = true;
		}
		else if (useDataPlotter.equals("google")) {
			this.useGoogle = true;
			this.useDataPlotter = true;
		}
		else if (useDataPlotter.equals("google2")) {
			this.useGoogle2 = true;
			this.useDataPlotter = true;
		}
		else if (useDataPlotter.equals("c3")) {
			this.useC3 = true;
			this.useDataPlotter = true;
		}
		else if (useDataPlotter.equals("nvd3")) {
			this.useNVD3 = true;
			this.useDataPlotter = true;
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setDataPlotterParams(java.lang.String)
	 */
	public void setDataPlotterParams(String dataPlotterParams) {this.dataPlotterParams = dataPlotterParams;}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setUseHistogramPlotter(java.lang.String)
	 */
	public void setUseHistogramPlotter(String useHistogramPlotter)
	{
	  consoleLog("App setUseHistogramPlotter: " + useHistogramPlotter);
		// default values "none"
		this.useHistogramPlotter = false;
		this.useJFreeChart_Hist = false;
		this.useGnuplot_Hist = false;
		this.useGoogle2_Hist = false;
		this.useD3_Hist = false;
		// other possible values
		if (useHistogramPlotter.equals("JFreeChart")) {
			//this.useJFreeChart_Hist = true; // can't use JFreeChart with GWT
			this.useD3_Hist = true; // substitute "d3"
			this.useHistogramPlotter = true;
		}
		else if (useHistogramPlotter.equals("gnuplot")) {
		  //consoleLog("App setUseHistogramPlotter: " + "gnuplot");
			this.useGnuplot_Hist = true;
			this.useHistogramPlotter = true;
		}
		else if (useHistogramPlotter.equals("google2")) {
		  //consoleLog("App setUseHistogramPlotter: " + "google2");
			this.useGoogle2_Hist = true;
			this.useHistogramPlotter = true;
		}
		else if (useHistogramPlotter.equals("d3")) {
		  //consoleLog("App setUseHistogramPlotter: " + "d3");
			this.useD3_Hist = true;
			this.useHistogramPlotter = true;
		}
	}
	
	/** histogramPlotterParams */
	public void setHistogramPlotterParams(String histogramPlotterParams) {
	  consoleLog(histogramPlotterParams);
		this.histogramPlotterParams = histogramPlotterParams;
	}
	
	/** @param useInteractions The useInteractions to set. */
	public void setUseInteractions(boolean useInteractions) {interactionsEnabled = useInteractions;}
	
	/** @param interactionParams */
	public void setInteractionParams(String interactionParams)
	{
		this.interactionParams = interactionParams;
	}
	
	/** @param useVrml The useVrml to set. */
	public void setUseVrml(boolean useVrml) {this.useVrml = useVrml;}
	
	/** @param vrmlWriterClassName */
	public void setVrmlWriterClassName(String vrmlWriterClassName)
		{this.vrmlWriterClassName = vrmlWriterClassName;}
	
	/** @param vrmlParams */
	public void setVrmlParams(String vrmlParams)
		{this.vrmlParams = vrmlParams;}
	
	/** @param javaClassName The javaClassName to set. */
	public void setJavaClassName(String javaClassName) {this.javaClassName = javaClassName;}

	/** @param javaXhClassName The javaXhClassName to set. */
	public void setJavaXhClassName(String javaXhClassName) {this.javaXhClassName = javaXhClassName;}
	
	/** @param javaXhClassClassName The javaXhClassClassName to set. */
	public void setJavaXhClassClassName(String javaXhClassClassName)
	{this.javaXhClassClassName = javaXhClassClassName;}
	
	/** @param javaActivityClassName */
	public void setJavaActivityClassName(String javaActivityClassName)
	{this.javaActivityClassName = javaActivityClassName;}
	
	/** @param saveSnapshots The saveSnapshots to set. */
	public void setSaveSnapshots(boolean saveSnapshots)
	{
		this.saveSnapshots = saveSnapshots;
		if (saveSnapshots) {
			//snapshot = new SnapshotXML(); // GWT
		}
	}
	
	/** @param snapshotParams */
	public void setSnapshotParams(String snapshotParams) {this.snapshotParams = snapshotParams;}
	
	/** @param scriptParams */
	public void setScriptParams(String scriptParams) {this.scriptParams = scriptParams;}
	
	/** modelName The modelName to set. */
	public void setModelName(String modelName) {this.modelName = modelName;}
	
	/**
	 * Set the size of the message queue, and allocate a queue of that size.
	 * This should only be called when the application starts up, to initially set the size.
	 * If the message queue is not empty, then any items in the queue will be lost.
	 * @param sizeMessageQueue The size of the queue.
	 */
	public void setSizeMessageQueue(int sizeMessageQueue)
	{
		//Xholon.setSizeMessageQ(sizeMessageQueue);
		// TODO iQueueImplName should be able to have the name of any valid class
		if (getIQueueImplName().equals("org.primordion.xholon.base.Queue")) {
			msgQ = new Queue(sizeMessageQueue);
		}
		else if (getIQueueImplName().equals("org.primordion.xholon.base.QueueSynchronized")) {
			msgQ = new QueueSynchronized(sizeMessageQueue);
		}
		else {
			logger.error("Invalid IQueue implementation class specified: " + getIQueueImplName());
			logger.info("Using org.primordion.xholon.base.Queue as implementing class.");
			msgQ = new Queue(sizeMessageQueue);
		}
	}
	
	/** treeNodeFactoryDynamic */
	public void setTreeNodeFactoryDynamic(boolean treeNodeFactoryDynamic)
	{this.treeNodeFactoryDynamic = treeNodeFactoryDynamic;}
	
	/** randomNumberSeed */
	public void setRandomNumberSeed(long randomNumberSeed)
	{this.randomNumberSeed = randomNumberSeed;}
	
	/** maxPorts */
	public void setMaxPorts(int maxPorts) {this.maxPorts = maxPorts;}
	
	/** xincludePath */
	public void setXincludePath(String xincludePath)
	{
		this.xincludePath = xincludePath;
	}
	
	/** xhymAgent */
	public void setXhymAgent(String xhymAgent)
	{
		this.xhymAgent = xhymAgent;
	}
	
	/** useXholon2Gui */
	public void setUseXholon2Gui(boolean useXholon2Gui) {this.useXholon2Gui = useXholon2Gui;}
	/** xholon2GuiParams */
	public void setXholon2GuiParams(String xholon2GuiParams) {this.xholon2GuiParams = xholon2GuiParams;}
	/** xholon2GuiClassName */
	public void setXholon2GuiClassName(String xholon2GuiClassName) {this.xholon2GuiClassName = xholon2GuiClassName;}
	
	/** jdbcParams */
	public void setJdbcParams(String jdbcParams) {this.jdbcParams = jdbcParams;}
	/** jpaParams */
	public void setJpaParams(String jpaParams) {this.jpaParams = jpaParams;}
	
	/** iQueueImplName */
	public void setIQueueImplName(String iQueueImplName) {this.iQueueImplName = iQueueImplName;}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setConfigFileName(java.lang.String)
	 */
	public void setConfigFileName(String configFileName) {this.configFileName = configFileName;}
	
	/** initialControllerState */
	public void setInitialControllerState(int initialControllerState)
		{this.initialControllerState = initialControllerState;}
	
	/** attributePostConfigAction */
	public void setAttributePostConfigAction(int attributePostConfigAction) {
		this.attributePostConfigAction = attributePostConfigAction;
	}

	//                                        Getters for input parameters
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getClassDetailsFile()
	 */
	public String getClassDetailsFile() {return classDetailsFile;}

	/*
	 * @see org.primordion.xholon.app.IApplication#getCompositeStructureHierarchyFile()
	 */
	public String getCompositeStructureHierarchyFile() {return compositeStructureHierarchyFile;}

	/*
	 * @see org.primordion.xholon.app.IApplication#getInheritanceHierarchyFile()
	 */
	public String getInheritanceHierarchyFile() {return inheritanceHierarchyFile;}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getInformationFile()
	 */
	public String getInformationFile() {return informationFile;}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getImageFile()
	 */
	public String getImageFile() {return imageFile;}

	/*
	 * @see org.primordion.xholon.app.IApplication#getMaxProcessLoops()
	 */
	public int getMaxProcessLoops() {return maxProcessLoops;}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getTimeStepInterval()
	 */
	public int getTimeStepInterval() {return timeStepInterval;}

	/** @return Returns the maxStateMachineEntities. */
	public int getMaxStateMachineEntities() {return maxStateMachineEntities;}

	/** @return Returns the maxActivities. */
	public int getMaxActivities() {return maxActivities;}

	/** @return Returns the maxXholonClasses. */
	public int getMaxXholonClasses() {return maxXholonClasses;}

	/** @return Returns the maxXholons. */
	public int getMaxXholons() {return maxXholons;}

	/** @return Returns the appM. */
	public boolean getAppM() {return Msg.appM;}

	/** @return Returns the infoM. */
	public boolean getInfoM() {return Msg.infoM;}

	/** @return Returns the errorM. */
	public boolean getErrorM() {return Msg.errorM;}
	
	/** @return Returns the debugM. */
	public boolean getDebugM() {return Msg.debugM;}
	
	/** @deprecated @return Returns the useDefaultViewer. */
	//public boolean getUseDefaultViewer() {return useDefaultViewer;}

	/** @return Returns the useTextTree. */
	public boolean getUseTextTree() {return useTextTree;}

	/**
	 * @return the shouldStepView
	 */
	public boolean isShouldStepView() {
		return shouldStepView;
	}

	/** @return Returns the useGraphicalTreeViewer. */
	public boolean getUseGraphicalTreeViewer() {return useGraphicalTreeViewer;}
	
	/**  */
	public String getGraphicalTreeViewerParams() {return graphicalTreeViewerParams;}

	/** @return Returns the useGraphicalNetworkViewer. */
	public boolean getUseGraphicalNetworkViewer() {return useGraphicalNetworkViewer;}

	/** graphicalNetworkViewerClassName */
	public String getGraphicalNetworkViewerClassName() {return graphicalNetworkViewerClassName;}
	
	/** graphicalNetworkViewerParams */
	public String getGraphicalNetworkViewerParams() {return graphicalNetworkViewerParams;}

	/** @return Returns the useGridViewer. */
	public boolean getUseGridViewer() {return useGridViewer;}
	
	/** @return Returns the gridPanelClassName. */
	public String getGridPanelClassName() {return gridPanelClassName;}
	
	/** @return gridViewerParams Returns the GridViewer parameters. */
	//public String getGridViewerParams() {return gridViewerParams;}

	/** @return Returns the useDataPlotter. */
	public boolean getUseDataPlotter() {return useDataPlotter;}
	public boolean getUseJFreeChart() {return useJFreeChart;}
	public boolean getUseGnuplot() {return useGnuplot;}
	public boolean getUseJdbc() {return useJdbc;}
	public boolean getUseJpa() {return useJpa;}
	public boolean getUseGoogle() {return useGoogle;}
	public boolean getUseGoogle2() {return useGoogle2;}
	public boolean getUseC3() {return useC3;}
	public boolean getUseNVD3() {return useNVD3;}
	public String getDataPlotterParams() {return dataPlotterParams;}

	/** @return Returns the useHistogramPlotter. */
	public boolean getUseHistogramPlotter() {return useHistogramPlotter;}
	public boolean getUseJFreeChart_Hist() {return useJFreeChart_Hist;}
	public boolean getUseGnuplot_Hist() {return useGnuplot_Hist;}
	public boolean getUseGoogle2_Hist() {return useGoogle2_Hist;}
	public boolean getUseD3_Hist() {return useD3_Hist;}
	public String getHistogramPlotterParams() {return histogramPlotterParams;}

	/** @return Returns the useInteractions. */
	public boolean getUseInteractions() {return interactionsEnabled;}
	
	/** @return Returns the interactionParams. */
	public String getInteractionParams() {return interactionParams;}

	/** @return Returns the useVrml. */
	public boolean getUseVrml() {return useVrml;}
	
	/** @return Returns the name of the VrmlWriter class. */
	public String getVrmlWriterClassName() {return vrmlWriterClassName;}
	
	/** @return vrmlParams */
	public String getVrmlParams() {return vrmlParams;}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getJavaClassName()
	 */
	public String getJavaClassName() {return javaClassName;}

	/*
	 * @see org.primordion.xholon.app.IApplication#getJavaXhClassName()
	 */
	public String getJavaXhClassName() {return javaXhClassName;}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getJavaXhClassClassName()
	 */
	public String getJavaXhClassClassName() {return javaXhClassClassName;}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getJavaActivityClassName()
	 */
	public String getJavaActivityClassName() {return javaActivityClassName;}
	
	/** @return Returns the saveSnapshots. */
	public boolean getSaveSnapshots() {return saveSnapshots;}
	
	/** @return snapshotParams */
	public String getSnapshotParams() {return snapshotParams;}
	
	/** @return scriptParams */
	public String getScriptParams() {return scriptParams;}
	
	/** @return Returns the modelName. */
	public String getModelName() {return modelName;}
	
	/** @return Returns the current maximum size of the message queue. */
	public int getSizeMessageQueue() {return msgQ.getMaxSize();}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getTreeNodeFactoryDynamic()
	 */
	public boolean getTreeNodeFactoryDynamic() {return treeNodeFactoryDynamic;}
	
	/** @return Random number seed. */
	public long getRandomNumberSeed() {return randomNumberSeed;}
	
	/** @return Maximum number of ports for any xholon in the model. */
	public int getMaxPorts() {return maxPorts;}
	
	/** @return XInclude path (for InheritanceHierarchy only; could be different in ContainmentHierarchy. */
	public String getXincludePath() {return xincludePath;}
	
	/** xhymAgent */
	public String getXhymAgent() {return xhymAgent;}
	
	/** useXholon2Gui */
	public boolean getUseXholon2Gui() {return useXholon2Gui;}
	/** xholon2GuiParams */
	public String getXholon2GuiParams() {return xholon2GuiParams;}
	/** xholon2GuiClassName */
	public String getXholon2GuiClassName() {return xholon2GuiClassName;}
	
	/** jdbcParams */
	public String getJdbcParams() {return jdbcParams;}
	/** jpaParams */
	public String getJpaParams() {return jpaParams;}
	
	/** iQueueImplName */
	public String getIQueueImplName() {return iQueueImplName;}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getConfigFileName()
	 */
	public String getConfigFileName() {return configFileName;}
	
	/** initialControllerState */
	public int getInitialControllerState() {return initialControllerState;}
	
	/** attributePostConfigAction */
	public int getAttributePostConfigAction() {return attributePostConfigAction;}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getFactory()
	 */
	public ITreeNodeFactory getFactory() {return factory;}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getMsgQ()
	 */
	public IQueue getMsgQ() {return msgQ;}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getSystemMsgQ()
	 */
	public IQueue getSystemMsgQ() {return systemMsgQ;}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getInteraction()
	 */
	public IInteraction getInteraction() {
	  return interaction;
	}
	
	/**
	 * Get the name of the path (folder) that contains the _xhn.xml config file.
	 * This is the standard location for all config files for a Xholon app.
	 * @return A path name, or null if the path name is unknown.
	 */
	public String getConfigPathName()
	{
		if (configFileName == null) {return null;}
		// the file name may be stored using either the forward or backward slash as a separator
		int endIndex = configFileName.lastIndexOf('/');
		if (endIndex == -1) {
			endIndex = configFileName.lastIndexOf('\\');
		}
		if (endIndex == -1) {return null;}
		return configFileName.substring(0, endIndex);
	}
	
	/**
	 * Get the name of this application based on the _xhn.xml config file name.
	 * ex: "./config/HelloWorld/HelloWorld_xhn.xml"
	 * @return A name, or null. ex: "HelloWorld"
	 */
	public String getConfigAppName()
	{
		if (configFileName == null) {return null;}
		// the file name may be stored using either the forward or backward slash as a separator
		int startIndex = configFileName.lastIndexOf('/');
		if (startIndex == -1) {
			startIndex = configFileName.lastIndexOf('\\');
		}
		if (startIndex == -1) {return null;}
		startIndex++;
		int endIndex = configFileName.indexOf("_xhn.xml", startIndex);
		if (endIndex == -1) {return null;}
		return configFileName.substring(startIndex, endIndex);
	}

	/**
	 * Return the class given the class name.
	 * @param className The name of a Java class.
	 * @return The class.
	 */
	/*public Class getClass(String className)
	{
		if (className == null) {return null;}
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			logger.error("Application getClass(): Unable to get class of " + className, e);
			//e.printStackTrace();
		}
		return clazz;
	}*/
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getName()
	 */
	public String getName()
	{
		return getRoleName();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getRoleName()
	 */
	public String getRoleName()
	{
		return roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getXhcName()
	 */
	public String getXhcName() {
		String xhcName = this.getClass().getName();
		int pos = xhcName.lastIndexOf('.');
		if (pos != -1) {
			xhcName = xhcName.substring(pos+1);
		}
		return xhcName;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getUri()
	 */
	public String getUri() {
		StringBuilder uri = new StringBuilder()
		.append(this.getRoot().getXhc().getMechanism().getNamespaceUri())
		.append("/")
		.append("Application");
		return uri.toString();
	}
	
	/**
	 * Get the instance of Application that the Xholon node is part of.
	 * There may be multiple instances of Application, each with its own set of xholons.
	 * See also the non-static equivalent of this method.
	 * @param xhNode An instance of IXholon.
	 * @return An instance of Application, or null (should never happen).
	 */
	public static IApplication getApplication(IXholon xhNode)
	{
		if (xhNode == null) {
			return application;
		}
		else {
			return (IApplication)applicationH.get(xhNode.getRootNode());
		}
	}
	
	/**
	 * Get the first instance of Application that has the specified name.
	 * See also the non-static equivalent of this method.
	 * @param appName The application name. This is the model name in the _xhn.xml file.
	 * @return An IApplication, or null.
	 */
	public static IApplication getApplication(String appName)
	{
		IApplication[] apps = Application.getApplications();
		for (int i = 0; i < apps.length; i++) {
			IApplication app = apps[i];
			if (appName.equals(app.getModelName())) {
				return app;
			}
		}
		return null;
	}
	
	/**
	 * Get the last inserted application.
	 * This is useful when there is more than one application.
	 * @return
	 */
	public static IApplication getLastInsertedApplication() {
		return lastInsertedApplication;
	}

	/**
	 * Get the singleton instance of Application.
	 * @return An instance of Application, or null (should never happen).
	 */
	public static IApplication getApplication()
	{
		return application;
	}
	
	/**
	 * Get all current applications.
	 * @return An array of Application instances.
	 */
	public static IApplication[] getApplications()
	{
		Collection<IApplication> apps = applicationH.values();
		IApplication[] appsIapp = new IApplication[apps.size()];
		Iterator<IApplication> it = apps.iterator();
		int index = 0;
		while (it.hasNext()) {
			IApplication app = (Application)it.next();
			appsIapp[index] = app;
			index++;
		}
		return appsIapp;
	}
	
	/**
	 * Get the names of all current applications.
	 * @return An array of names, not necessarily in any order.
	 */
	public static String[] getApplicationNames()
	{
		Collection<IApplication> apps = applicationH.values();
		String[] appsStr = new String[apps.size()];
		Iterator<IApplication> it = apps.iterator();
		int index = 0;
		while (it.hasNext()) {
			IApplication app = (Application)it.next();
			appsStr[index] = app.getModelName();
			index++;
		}
		return appsStr;
	}
	
	/**
	 * Get the number of separate applications currently loaded.
	 * @return The number of applications (0 or more).
	 */
	public static int getNumApplications()
	{
		return applicationH.size();
	}
	
	/**
	 * Set an instance of IApplication, where there may be multiple IApplication instances.
	 * @param app An instance of IApplication.
	 * @param root The root node in a Xholon composite structure hierarchy.
	 */
	public static void setApplication(IApplication app, IXholon root) {
		if (root == null) {
			logger.error("Unable to set null as IApplication root.");
			return;
		}
		application = app;
		lastInsertedApplication = app;
		applicationH.put(root, app);
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getApplicationOther(org.primordion.xholon.base.IXholon)
	 */
	public IApplication getApplicationOther(IXholon xhNode)
	{
		return getApplication(xhNode);
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getApplicationOther(java.lang.String)
	 */
	public IApplication getApplicationOther(String appName)
	{
		return getApplication(appName);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getApp()
	 */
	public IApplication getApp() {
	  //System.out.println("Starting getApp() ... " + this);
		return this;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		if (srvRoot == null) {return null;}
		IXholon serviceLocator = srvRoot.getFirstChild();
		if (serviceLocator == null) {return null;}
		return serviceLocator.getService(serviceName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.primordion.xholon.app.IApplication#getInherHier()
	 */
	public IInheritanceHierarchy getInherHier()
	{
		return inherHier;
	}
	
	/**
	 * Reset various static variables that are part of Application or other system classes.
	 * TODO There may be other static variables that should be reset.
	 */
	public static void resetStaticVariables()
	{
		applicationH = new HashMap<IXholon, IApplication>();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getNextId()
	 */
	public int getNextId()
	{
		nextId++;
		return nextId;
	}
	
	public void setNextId(int nextId) {
		this.nextId = nextId;
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#resetNextId()
	 */
	public void resetNextId()
	{
		nextId = -1;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getNextXholonClassId()
	 */
	public int getNextXholonClassId()
	{
		nextXholonClassId++;
		return nextXholonClassId;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setNextXholonClassId(int)
	 */
	public void setNextXholonClassId(int nextXholonClassId)
	{
		this.nextXholonClassId = nextXholonClassId;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#resetNextXholonClassId()
	 */
	public void resetNextXholonClassId()
	{
		nextXholonClassId = -1;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#initGui(org.primordion.xholon.io.XholonGui)
	 */
	public void initGui(Object gui)
	{
		this.gui = gui;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#initControl()
	 */
	public void initControl()
	{
		setControllerState(IControl.CS_INITIALIZED);
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setControllerState(int)
	 */
	public void setControllerState(int controllerState)
	{
		this.controllerState = controllerState;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getControllerState()
	 */
	public int getControllerState()
	{
		return controllerState;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getControllerStateName()
	 */
	public String getControllerStateName() {
		return IControl.stateName[controllerState];
	}
	
	/**
	 * Set the current time step of the executing application.
	 * @param ts The current time step.
	 */
	public void setTimeStep(int timeStep)
	{
		this.timeStep = timeStep;
	}
	
	/**
	 * Get the current time step of the executing application.
	 * @return The current time step.
	 */
	public int getTimeStep()
	{
		return timeStep;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getClassNode(java.lang.String)
	 */
	public IXholonClass getClassNode( String cName ) {
	  return inherHier.getClassNode(cName);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getClassNode(int)
	 */
	public IXholonClass getClassNode( int id ) {
		return inherHier.getClassNode(id);
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setRoot(org.primordion.xholon.base.IXholon)
	 */
	public void setRoot(IXholon root)
	{
		this.root = root;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getRoot()
	 */
	public IXholon getRoot()
	{
		return root;
	}
	
	public IControl getView() {
		return view;
	}

	public void setView(IControl view) {
		this.view = view;
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#setAppRoot(org.primordion.xholon.base.IControl)
	 */
	public void setAppRoot(IXholon appRoot)
	{
		this.appRoot = appRoot;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getAppRoot()
	 */
	public IXholon getAppRoot()
	{
		if (appRoot == null) {
			return this;
		}
		else {
			return appRoot;
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#isApplet()
	 */
	public boolean isApplet() {
		return applet;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setApplet(boolean)
	 */
	public void setApplet(boolean applet) {
		this.applet = applet;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setXhcRoot(org.primordion.xholon.base.IXholonClass)
	 */
	public void setXhcRoot(IXholonClass xhcRoot)
	{
		this.xhcRoot = xhcRoot;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getXhcRoot()
	 */
	public IXholonClass getXhcRoot()
	{
		return xhcRoot;
	}
	
	
	public void setSrvRoot(IXholon srvRoot)
	{
		this.srvRoot = srvRoot;
	}
	
	
	public IXholon getSrvRoot()
	{
		return srvRoot;
	}
	
	public void setControlRoot(IControl controlRoot)
	{
		this.controlRoot = controlRoot;
	}
	
	public IControl getControlRoot()
	{
		return controlRoot;
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#setMechRoot(org.primordion.xholon.base.IMechanism)
	 */
	public void setMechRoot(IMechanism mechRoot)
	{
		this.mechRoot = mechRoot;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getMechRoot()
	 */
	public IMechanism getMechRoot()
	{
		return mechRoot;
	}
		
	/*
	 * @see org.primordion.xholon.app.IApplication#setDefaultMechanism(org.primordion.xholon.base.IMechanism)
	 */
	public void setDefaultMechanism(IMechanism defaultMechanism)
	{
		this.defaultMechanism = defaultMechanism;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getDefaultMechanism()
	 */
	public IMechanism getDefaultMechanism()
	{
		return defaultMechanism;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#initialize(java.lang.String)
	 */
	public void initialize( String configFileName ) throws XholonConfigurationException
	{
	  clearConsole(); println("initializing ...");
	  System.out.println("App.initialize() starting ...");
		// make sure configFileName is saved for possible future use
		if (this.configFileName == null) {
			this.configFileName = configFileName;
		}
		System.out.println("App.initialize() this.configFileName: " + this.configFileName);
		System.out.println("App.initialize() xincludePath: " + xincludePath);
		
		//StringTokenizer st;
		
		/*
		try {
			// Read values from the default configuration file.
			//readConfigFromFileXml(xincludePath + "_default_xhn.xml");
			Parameters.xmlString2Params(rcConfig("_default_xhn"), this);
		
			// Read values from main configuration file/String for this specific application.
			if (configFileName == null) {
			  Parameters.xmlString2Params(rcConfig("_xhn", findGwtClientBundle()), this);
			}
			else {
			  readConfigFromFileXml(configFileName);
			}
		} catch (XholonConfigurationException e) {
			logger.error(e.getMessage(), e.getCause());
			throw new XholonConfigurationException();
		}
		*/
		readParameters(configFileName);
		
		clearConsole(); println("read config files");
		
    setHtmlTitles();
    showTimeStep();
    showMaxProcessLoops();
		
		createTreeNodeFactory();
		
		// Seed the random number generator.
		if (this.getRandomNumberSeed() == DEFAULT_RANDOM_NUMBER_SEED) {
			MiscRandom.seedRandomNumberGenerator();
		}
		else {
			MiscRandom.seedRandomNumberGenerator(getRandomNumberSeed());
		}
				
		inherHier = new InheritanceHierarchy();
		System.out.println("App.initialize() inherHier: " + inherHier);
		
		createControlHierarchy();
		clearConsole(); println("created control hierarchy");
		
		createMechanismHierarchy();
		
		createInheritanceHierarchy();
		clearConsole(); println("created inheritance hierarchy");
		
		xhcRoot.configure();
		
		// set IXholonClass for each XholonMechanism instance
		mechRoot.setXhc(inherHier.getClassNode("XholonMechanism"));
		mechRoot.configure();
		
		createXholonServiceHierarchy();
		
		createContainmentHierarchy();
		clearConsole(); println("created containment hierarchy");
		
		if (root == null) {
			logger.error("Application root is null.");
			return;
		}

		setApplication(this, root);
		root.initStatics(); // initialize any static variables that need initializing
		
		// preConfiguration root and its tree
		IXholon preConfigurationService = getService(IXholonService.XHSRV_PRE_CONFIGURATION);
		if (preConfigurationService != null) {
			preConfigurationService.sendSyncMessage(IXholonService.SIG_PROCESS_REQUEST, root, this);
		}
		
		clearConsole(); println("configuring ...");
		root.configure(); // recursive configure
		clearConsole();
		makeDefaultConsoles();
		root.postConfigure(); // recursive postConfigure
		StateMachineEntity.initializeStateMachines(); // initialize any state machines in the app
		
		// optionally create a Notes tab
		createNotesTab();
		
		// optionally display SVG image
		image();

		// Initialize the optional Grid Viewer vector (required in certain apps).
		if (getUseGridViewer() && (getGridPanelClassName() != null) && (gridViewers != null)) {
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				createGridViewer(vIx);
			}
		}
		
		// Process a list of xh divs to hide, as specified on the url
		String hideStr = Location.getParameter("hide");
		if (hideStr != null) {
		  String[] hideArr = hideStr.split(",");
		  for (int h = 0; h < hideArr.length; h++) {
		    Element ele = Document.get().getElementById(hideArr[h]);
		    if (ele != null) {
		      ele.getStyle().setDisplay(Display.NONE);
		    }
		  }
		}
		
		// Initialize the IXholon scripting API.
		//println("XholonJsApi.initIXholonApi();");
		XholonJsApi.initIXholonApi();
				
		//System.out.println("App.initialize() root: " + root);
		//if (root != null) {
		//  root.preOrderPrint(0);
		//}
		
		//System.out.println("App.initialize() xhcRoot: " + xhcRoot);
		//if (xhcRoot != null) {
		//  xhcRoot.preOrderPrint(0);
		//}
		
	}
	
	@Override
	public void readParameters(String configFileName)  throws XholonConfigurationException {
	  try {
			// Read values from the default configuration file.
			//readConfigFromFileXml(xincludePath + "_default_xhn.xml");
			Parameters.xmlString2Params(rcConfig("_default_xhn"), this);
		
			// Read values from main configuration file/String for this specific application.
			String _xhn = null;
			if (workbookBundle != null) {
		    _xhn = workbookBundle.getResource(XholonWorkbookBundle.RESOURCE__XHN);
		  }
		  if (_xhn != null) {
		    Parameters.xmlString2Params(_xhn, this);
		  }
			else if (configFileName == null) {
			  Parameters.xmlString2Params(rcConfig("_xhn", findGwtClientBundle()), this);
			}
			else {
			  readConfigFromFileXml(configFileName);
			}
		} catch (XholonConfigurationException e) {
			logger.error(e.getMessage(), e.getCause());
			throw new XholonConfigurationException();
		}
	}
	
	/**
	 * Set the HTML head title, and the HTML body h2/h3 title.
	 */
	public void setHtmlTitles() {
	  String title = "Xholon - " + getModelName();
    Window.setTitle(title);
    Element element = HtmlElementCache.xholontitle;
    if (element != null) {
        element.setInnerText(title);
    }
	}
	
	/**
	 * Create the tree node factory.
	 */
	protected void createTreeNodeFactory()
	{
	  XholonCreationService xcs = new XholonCreationService();
		xcs.setApp(this);
		factory = xcs.getFactory();
	}
	
	/**
	 * Retrieve resource content from RCConfig.
	 * @param The name of the resource in RCConfig.
	 * @return The resource content.
	 */
	public String rcConfig(String resourceName) {
	  return ((TextResource)RCConfig.INSTANCE.getResource(resourceName)).getText();
	}
	
	/**
	 * Retrieve resource content from an app-specific resources class.
	 * @param The name of the resource in the resources class.
	 * @param An instance of the resources class.
	 * @return The resource content.
	 */
	public String rcConfig(String resourceName, Object resources) {
	  if (resources == null) {return null;}
	  TextResource tr = (TextResource)((ClientBundleWithLookup)resources).getResource(resourceName);
	  if (tr == null) {
	    return null;
	  }
	  return tr.getText();
	}
	
	/**
	 * Create the Control hierarchy.
	 */
	protected void createControlHierarchy() throws XholonConfigurationException
	{
		System.out.println("App.createControlHierarchy() starting ...");
		// this bit of code has to bootstrap itself because nothing else is set up yet
		IXml2Xholon xml2Control = new Xml2Xholon();
		xml2Control.setTreeNodeFactory(factory);
		xml2Control.setApp(this);
		xml2Control.setXincludePath(xincludePath);
		// add Control to the internal inheritance hierarchy structure
		IXholonClass controlXholonClass = factory.getXholonClassNode();
		controlXholonClass.setId(CeControl.ControlCE); // don't call getNextXholonClassId()
		controlXholonClass.setName("Control");
		controlXholonClass.setImplName("org.primordion.xholon.base.Control");
		controlXholonClass.setApp(this);
		inherHier.createHashEntry(controlXholonClass);
		xml2Control.setInheritanceHierarchy(inherHier);
		controlRoot = (IControl)xml2Control.xmlString2Xholon(rcConfig(CONTROLHIERARCHY_FILENAME), this);
	  this.setRoleName(controlRoot.getRoleName());
		// get Application's true first child, and adjust other nodes; remove Application's initial first child
		IXholon controlNode = this.getFirstChild().getFirstChild();
		while (controlNode != null) {
			controlNode.setParentNode(this);
			controlNode = controlNode.getNextSibling();
		}
		this.setFirstChild(this.getFirstChild().getFirstChild());
		controlRoot.setParentNode(null);
		controlRoot.setFirstChild(null);
		controlRoot = (IControl)this.getFirstChild();
		appRoot = this;
		IControl controller = (IControl)appRoot.getFirstChild();
		view = (IControl)controller.getNextSibling();
		this.resetNextId(); // reset the next id back to its default value
	}
	
	/**
	 * Create Mechanism hierarchy.
	 */
	protected void createMechanismHierarchy()
	{
		System.out.println("App.createMechanismHierarchy() starting ...");
		IXml2Xholon xml2Mechanism = new Xml2Mechanism();
		xml2Mechanism.setTreeNodeFactory(factory);
		xml2Mechanism.setApp(this);
		xml2Mechanism.setXincludePath(xincludePath);
		xml2Mechanism.setInheritanceHierarchy(inherHier);
		IXholon mechControlRoot = controlRoot
			.getNextSibling().getNextSibling().getFirstChild().getNextSibling().getNextSibling();
		//mechRoot = (IMechanism)xml2Mechanism.xmlUri2Xholon(xincludePath + MECHANISMHIERARCHY_FILENAME, mechControlRoot);
		mechRoot = (IMechanism)xml2Mechanism.xmlString2Xholon(rcConfig(MECHANISMHIERARCHY_FILENAME), mechControlRoot);
		defaultMechanism = mechRoot.findMechanism(IMechanism.DEFAULT_NAMESPACE);
	}
	
	/**
	 * Create the inheritance hierarchy.
	 */
	protected void createInheritanceHierarchy()
	{
		System.out.println("App.createInheritanceHierarchy() starting ...");
		IXml2Xholon xml2XholonClass = new Xml2XholonClass();
		xml2XholonClass.setTreeNodeFactory(factory);
		xml2XholonClass.setApp(this);
		xml2XholonClass.setXincludePath(xincludePath);
		xml2XholonClass.setInheritanceHierarchy(inherHier);
		IXholon xhcControlRoot = controlRoot
			.getNextSibling().getNextSibling().getFirstChild().getNextSibling();
		xhcRoot = (XholonClass)xml2XholonClass.xmlString2Xholon("<XholonClass/>", xhcControlRoot);
		/*
		// common mechanisms
		xml2XholonClass.xmlUri2Xholon(xincludePath + MECHANISM_XML_FILENAME, xhcRoot);
		xml2XholonClass.xmlUri2Xholon(xincludePath + MECHANISM_CD_XML_FILENAME, xhcRoot);
		// common viewers
		xml2XholonClass.xmlUri2Xholon(xincludePath + MECHVIEWER_XML_FILENAME, xhcRoot);
		xml2XholonClass.xmlUri2Xholon(xincludePath + MECHVIEWER_CD_XML_FILENAME, xhcRoot);
		// application classes
		xml2XholonClass.xmlUri2Xholon(inheritanceHierarchyFile, xhcRoot);
		xml2XholonClass.xmlUri2Xholon(classDetailsFile, xhcRoot);*/
		
		// common mechanisms
		xml2XholonClass.xmlString2Xholon(rcConfig(MECHANISM_XML_FILENAME), xhcRoot);
		xml2XholonClass.xmlString2Xholon(rcConfig(MECHANISM_CD_XML_FILENAME), xhcRoot);
		// common viewers
		xml2XholonClass.xmlString2Xholon(rcConfig(MECHVIEWER_XML_FILENAME), xhcRoot);
		xml2XholonClass.xmlString2Xholon(rcConfig(MECHVIEWER_CD_XML_FILENAME), xhcRoot);
		// application classes IH
		String ih = null;
		if (workbookBundle != null) {
		  ih = workbookBundle.getResource(XholonWorkbookBundle.RESOURCE_IH);
		  consoleLog(ih);
		}
		if (ih != null) {
		  xml2XholonClass.xmlString2Xholon(ih, xhcRoot);
		}
		else if (inheritanceHierarchyFile == null) {
		  xml2XholonClass.xmlString2Xholon(rcConfig("ih", findGwtClientBundle()), xhcRoot);
		}
		else {
		  xml2XholonClass.xmlUri2Xholon(inheritanceHierarchyFile, xhcRoot);
		}
		// CD
		String cd = null;
		if (workbookBundle != null) {
		  cd = workbookBundle.getResource(XholonWorkbookBundle.RESOURCE_CD);
		  consoleLog(cd);
		}
		if (cd != null) {
		  xml2XholonClass.xmlString2Xholon(cd, xhcRoot);
		}
		else if (classDetailsFile == null) {
		  xml2XholonClass.xmlString2Xholon(rcConfig("cd", findGwtClientBundle()), xhcRoot);
		}
		else {
		  xml2XholonClass.xmlUri2Xholon(classDetailsFile, xhcRoot);
		}
	}
	
	/**
	 * Create the XholonService hierarchy.
	 */
	protected void createXholonServiceHierarchy()
	{
	  System.out.println("App.createXholonServiceHierarchy() starting ...");
		IXml2Xholon xml2Service = new Xml2Xholon();
		xml2Service.setTreeNodeFactory( factory );
		xml2Service.setApp(this);
		xml2Service.setXincludePath(xincludePath);
		xml2Service.setInheritanceHierarchy(inherHier);
		// set the temporary Composite Structure control root
		IXholon csControlRoot = controlRoot
			.getNextSibling().getNextSibling().getFirstChild();
		//srvRoot = xml2Service.xmlUri2Xholon(xincludePath + SERVICEHIERARCHY_FILENAME, csControlRoot);
		srvRoot = xml2Service.xmlString2Xholon(rcConfig(SERVICEHIERARCHY_FILENAME), csControlRoot);
		//System.out.println("App.createXholonServiceHierarchy() srvRoot: " + srvRoot);
		//if (srvRoot != null) {
		//  srvRoot.preOrderPrint(0);
		//}
		this.resetNextId(); // reset the next id back to its default value
	}
	
	/**
	 * Create the domain-specific containment hierarchy.
	 */
	protected void createContainmentHierarchy()
	{
		System.out.println("App.createContainmentHierarchy() starting ...");
		IXml2Xholon xml2Xholon = new Xml2Xholon();
		xml2Xholon.setTreeNodeFactory( factory );
		xml2Xholon.setApp(this);
		xml2Xholon.setXincludePath(xincludePath);
		xml2Xholon.setInheritanceHierarchy(inherHier);
		IXholon csControlRoot = controlRoot
			.getNextSibling().getNextSibling().getFirstChild();
		String csh = null;
	  consoleLog(workbookBundle);
		if (workbookBundle != null) {
		  csh = workbookBundle.getResource(XholonWorkbookBundle.RESOURCE_CSH);
		  consoleLog(csh);
		}
		if (csh != null) {
		  root = xml2Xholon.xmlString2Xholon(csh, csControlRoot);
		}
	  else if (compositeStructureHierarchyFile == null) {
	    root = xml2Xholon.xmlString2Xholon(rcConfig("csh", findGwtClientBundle()), csControlRoot);
	  }
	  else {
		  root = xml2Xholon.xmlUri2Xholon(compositeStructureHierarchyFile, csControlRoot);
		}
		if (root != null) { // TODO this will cause problems later
			// swap the position of root and srvRoot, so root is the first child
			csControlRoot.setFirstChild(root);
			root.setNextSibling(srvRoot);
			srvRoot.setNextSibling(null);
		}

	}
	
	/**
	 * Create a Notes tab in the GUI, if there are notes in a XholonWorkbookBundle.
	 */
	protected void createNotesTab() {
	  if (workbookBundle != null) {
		  String notes = workbookBundle.getResource(XholonWorkbookBundle.RESOURCE_NOTES);
		  if ((notes != null) && (notes.length() > 0)) {
		    int notesStart = "<Notes><![CDATA[".length();
			  int notesEnd = notes.indexOf("]]></Notes>", notesStart);
			  if (notesEnd != -1) {
			    notes = notes.substring(notesStart, notesEnd);
			  }
		    XholonGwtTabPanelHelper.addTab(notes, "notes", "Workbook Notes", false);
		  }
		}
	}
	
	protected void showTimeStep() {
	  Element element = HtmlElementCache.timestep;
    if (element != null) {
      element.setInnerText(Integer.toString(getTimeStep()));
    }
	}
	
	protected void showMaxProcessLoops() {
	  Element element = HtmlElementCache.maxprocessloops;
    if ((element != null) && (maxProcessLoops > -1)) {
      element.setInnerText(Integer.toString(maxProcessLoops));
    }

	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#createGridViewer(int)
	 */
	public int createGridViewer(int vIx)
	{
	  consoleLog("Application.createGridViewer( " + vIx);
		if (vIx == -1) {
			vIx = gridViewers.size() - 1;
		}
		GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
		consoleLog("Application.createGridViewer( " + gvd.gridViewerParams);
		StringTokenizer st = new StringTokenizer(gvd.gridViewerParams, ",");
		/* GWT
		try {
			gvd.gridPanel = (IGridPanel)Class.forName(getGridPanelClassName()).newInstance();
		} catch (InstantiationException e) {
			logger.error("Unable to load grid viewer ", e);
		} catch (IllegalAccessException e) {
			logger.error("Unable to load grid viewer ", e);
		} catch (ClassNotFoundException e) {
			logger.error("Unable to load grid viewer " + getGridPanelClassName() + e.toString());
		} catch (NoClassDefFoundError e) {
			logger.error("Unable to load grid viewer ", e);
		}*/
		gvd.gridPanel = (IGridPanel)makeAppSpecificNode(getGridPanelClassName());
		if (gvd.gridPanel == null) {
		  // an app-specific class was not found, so look for a generic framework class
		  try {
		    gvd.gridPanel = (IGridPanel)factory.getXholonNode(getGridPanelClassName());
		  } catch(XholonConfigurationException e) {}
		}
		//println("Application.createGridViewer( gvd.gridPanel:" + gvd.gridPanel);
		if (gvd.gridPanel == null) {return vIx;}
		gvd.gridOwner = getXPath().evaluate(st.nextToken(), root);
		gvd.gridPanel.setCellSize(Integer.parseInt(st.nextToken()));
		gvd.gridPanel.initGridPanel(gvd.gridOwner);
		//String title = st.nextToken();
		st.nextToken(); // discard the title
		if (st.hasMoreTokens()) { // useShapes is optional
		  String useShapes = st.nextToken();
		  //consoleLog("Application.createGridViewer() st.hasMoreTokens() " + useShapes);
			gvd.gridPanel.setUseShapes(Boolean.valueOf(useShapes).booleanValue());
		}
		//gvd.gridFrame = new org.primordion.xholon.io.GridFrame(title, gvd.gridPanel);
		return vIx;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getGridViewer(int)
	 */
	public GridViewerDetails getGridViewer(int vIx) {
		if ((vIx < 0) || (vIx >= gridViewers.size())) {
			return null;
		}
		return (GridViewerDetails)gridViewers.get(vIx);
	}
	
	// TODO
	public void process()
	{
	  // (1) do app-specific stuff
    processOnce();
    
    // (2) create first timer instance
	  processTimer = new Timer() {
      @Override
      public void run() {
        loopOnce();
      }
    };
    
    // (3) schedule the timer to run the first time
    processTimer.schedule(500);
	}
	
	/**
	 * Loop once
	 * @see http://code.google.com/p/alienstronghold/source/browse/trunk/src/com/blueflame/alienstronghold/client/GameLooper.java
	 */
	private void loopOnce() {
	  // (1) cancel timer
	  processTimer.cancel();
	  
	  // (2) do app-specific stuff
	  processOnce();
	  
	  // (3) create new timer instance
	  processTimer = new Timer() {
      @Override
      public void run() {
        loopOnce();
      }
    };
	  
	  // (4) schedule the new timer
    switch (getControllerState()) {
    case IControl.CS_RUNNING:
    case IControl.CS_STEPPING:
      processTimer.schedule(timeStepInterval);
      break;
    default:
      processTimer.schedule(500);
      break;
    }
	}
	
	/**
	 * If you override this method in a subclass, you should either call super.process()
	 * or call root.processSystemMessageQ() directly, to ensure that messages on the system
	 * message queue are continuously being processed. If you do not do this, your applicaion
	 * may generate a lot of Q_FULL log items.
	 * @see org.primordion.xholon.app.IApplication#process()
	 */
	public void processOnce()
	{
		// Initialize the optional web-based or other special purpose GUI
		//if (useXholon2Gui) {
		//	if (getXholon2GuiClassName().indexOf("Zuml") != -1) {
		//		invokeXholon2Gui(getRoot(), null); // Zuml web-based GUI needs to be loaded at start 
		//	}
		//}
		
		//while (true) {
			root.processSystemMessageQ();
			switch (getControllerState()) {
			case IControl.CS_INITIALIZED:
				//XholonTime.sleep(500); // GWT ant build
				break;
			case IControl.CS_RUNNING:
				if (xhymSpace != null) {
					xhymSpace.act(); // invoke the top-level xholon in Xhym Space
				}
				step();
				timeStep++;
				showTimeStep();
				break;
			case IControl.CS_PAUSED:
				//XholonTime.sleep(500); // GWT ant build
				break;
			case IControl.CS_STEPPING:
				if (xhymSpace != null) {
					xhymSpace.act(); // invoke the top-level xholon in Xhym Space
				}
				step();
				// don't change state if the step() has caused it to become CS_STOPPED
				if (getControllerState() == IControl.CS_STEPPING) {
					setControllerState(IControl.CS_PAUSED);
				}
				timeStep++;
				showTimeStep();
				break;
			case IControl.CS_STOPPED:
				return;
			default:
				break;
			}
			if ((maxProcessLoops != LOOP_FOREVER) && (maxProcessLoops == timeStep)) {
			  wrapup(); // GWT
				setControllerState(IControl.CS_STOPPED);
			}
		//}
	}
	
	/**
	 * Process one time step. A concrete class should override this method,
	 * unless it only needs to do the simple root.preAct(), root.act(), root.postAct() cycle.
	 */
	protected void step()
	{
		root.preAct();
		root.act();
		root.postAct();
		if (shouldStepView) {
			view.act();
		}
		if (timeStepInterval > 0) {
			//XholonTime.sleep( timeStepInterval );  // GWT ant build
		}
	}
	
	/**
	 * Only invoke a viewer on wrapup if:
	 * - the unconditional INVOKE_VIEWERS_ON_WRAPUP is set to true, or
	 * - the viewer is non-graphical and writes to a file or stdout
	 * @see org.primordion.xholon.app.IApplication#wrapup()
	 */
	public void wrapup()
	{
	  consoleLog("Application.wrapup() starting ...");
		// Draw simple 2D text-based tree.
		if (getUseTextTree()) {
			//root.draw();
			IXholon textTreeService = getService(IXholonService.XHSRV_TEXT_TREE_VIEWER);
			if (textTreeService != null) {
				textTreeService.sendSyncMessage(IXholonService.SIG_PROCESS_REQUEST, root, this);
			}
		}
		
		// Create a sequence diagram (optional message trace).
		if (INVOKE_VIEWERS_ON_WRAPUP
			|| ((interaction.getOutputFormat() & IInteraction.FORMAT_SDEDIT) == IInteraction.FORMAT_SDEDIT)
			|| ((interaction.getOutputFormat() & IInteraction.FORMAT_WEBSD) == IInteraction.FORMAT_WEBSD)
			|| ((interaction.getOutputFormat() & IInteraction.FORMAT_UML_GRAPH) == IInteraction.FORMAT_UML_GRAPH)) {
			consoleLog("do sequence diagram on wrapup");
			invokeInteraction();
		}
		
		// Create a JFreeChart or gnuplot chart.
		if (INVOKE_VIEWERS_ON_WRAPUP || noGui || (getUseGnuplot())) {
		  consoleLog("do data plotter on wrapup");
			invokeDataPlotter();
		}
		
		// Create a JFreeChart or gnuplot histogram.
		if (INVOKE_VIEWERS_ON_WRAPUP || (getUseGnuplot_Hist())) {
		  consoleLog("do histogram on wrapup");
			invokeHistogramPlotter();
		}
		
		// GWT
		/*
		// Initialize and create a JUNG graphical tree viewer.
		if (INVOKE_VIEWERS_ON_WRAPUP) {
			invokeGraphicalTreeViewer();
		}
		
		// Initialize and create a JUNG graphical network viewer.
		if (INVOKE_VIEWERS_ON_WRAPUP) {
			invokeGraphicalNetworkViewer();
		}*/
		
		// Create a 3d VRML output file.
		if (getUseVrml() && (getVrmlWriterClassName() != null)) {
		  /* GWT ant build
			IVrmlWriter vw = null;
			try {
			vw = (IVrmlWriter)Class.forName(getVrmlWriterClassName()).newInstance();
			} catch (InstantiationException e) {
				logger.error("Unable to find optional VRML writer ", e);
			} catch (IllegalAccessException e) {
				logger.error("Unable to find optional VRML writer ", e);
			} catch (ClassNotFoundException e) {
				logger.error("Unable to find optional VRML writer {}" + getVrmlWriterClassName());
			} catch (NoClassDefFoundError e) {
				logger.error("Unable to find optional VRML writer ", e);
			}*/
			
			consoleLog("about to create instance of IVrmlWriter vw");
			IVrmlWriter vw = null;
			try {
			  consoleLog(getVrmlWriterClassName());
			  vw = (IVrmlWriter)factory.getXholonNode(getVrmlWriterClassName());
			} catch(XholonConfigurationException e) {}
			if (vw != null) {
			  consoleLog("about to vw.writeAsVrml(root);");
				vw.writeAsVrml(root);
			}
			
		}
	}
	
	/**
	 * Use XPath to find a start tree node for a graph.
	 * @param theRoot The start root as specified in the configuration.
	 * @return A root IXholon node.
	 */
	protected IXholon getXhStart(String theRoot)
	{
		IXholon xhStart = null;
		if ((theRoot.length() > 7) && (theRoot.substring(0, 7).equals("appRoot"))) {
			theRoot = theRoot.substring(7);
			xhStart = getXPath().evaluate(theRoot, appRoot);
		}
		else {
			xhStart = getXPath().evaluate(theRoot, root);
		}
		return xhStart;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#readConfigFromFileXml(java.lang.String)
	 */
	public void readConfigFromFileXml(String fileName) throws XholonConfigurationException
	{
	  System.out.println("App.readConfigFromFileXml() starting ... " + fileName);
		Parameters.xmlUri2Params(fileName, this); // GWT
		//Parameters.xmlString2Params("<params><param name='ModelName' value='Testing'/></params>", this);
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setParam(java.lang.String, java.lang.String)
	 */
	public boolean setParam(String pName, String pValue)
	{
		return ReflectionFactory.instance().setParam(pName, pValue, this);
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getParam(java.lang.String)
	 */
	public String getParam(String pName)
	{
	  return ReflectionFactory.instance().getParam(pName, this);
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#resetViewers()
	 */
	public void resetViewers()
	{
		if ((getUseDataPlotter()) && (chartViewer != null)) {
			chartViewer.remove();
			chartViewer = null;
		}
		useDataPlotter = false;
		if ((getUseHistogramPlotter()) && (histogramViewer != null)) {
			histogramViewer.remove();
			histogramViewer = null;
		}
		useHistogramPlotter = false;
		if ((getUseGraphicalNetworkViewer()) && (networkViewerJung != null)) {
			networkViewerJung.remove();
			networkViewerJung = null;
		}
		useGraphicalNetworkViewer = false;
		if ((getUseGraphicalTreeViewer()) && (treeViewerJung != null)) {
			treeViewerJung.remove();
			treeViewerJung = null;
		}
		useGraphicalTreeViewer = false;
		if ((getUseGridViewer()) && (gridViewers != null)) {
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				gvd.gridPanel = null;
				//gvd.gridFrame.reset();
			}
			gridViewers.removeAllElements();
			gridViewers = null;
		}
		useGridViewer = false;
		GridPanel.setIsFrozen(false);
		interactionsEnabled = false;
		useTextTree = false;
		useVrml = false;
		saveSnapshots = false;
		xhymAgent = null;
		// TODO remove any xhym entities
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#initViewers()
	 */
	public void initViewers()
	{
	  consoleLog("Application starting initViewers() ...");
		// XYChart
		if (getUseDataPlotter()) {
		  System.out.println("Application initViewers() calling createChart(null)");
			createChart(null);
		}
		
		// Histogram
		if (getUseHistogramPlotter()) {
		  consoleLog("calling createHistogram()");
			createHistogram();
			consoleLog("finished call to createHistogram()");
		}
		
		// GraphicalNetworkViewer
		if (getUseGraphicalNetworkViewer()) {
			view.appendChild("GraphicalNetworkViewer", null);
		}
		
		// GraphicalTreeViewer
		if (getUseGraphicalTreeViewer()) {
			view.appendChild("GraphicalTreeViewer", null);
		}
		
		// GridViewer
		if (getUseGridViewer()) {
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				view.appendChild("GridViewer", null);
			}
		}
		
		// Interactions
		if (getUseInteractions()) {
  		createInteractions();
		}
		
		// TextTree
		if (getUseTextTree()) {
			view.appendChild("TextTree", null);
		}
		
		// Vrml
		if (getUseVrml()) {
		  // TODO add VRML viewer mechanism ?
		  //consoleLog("about to view.appendChild(\"Vrml\", null);");
			//view.appendChild("Vrml", null);
		  //consoleLog("called view.appendChild(\"Vrml\", null);");
		}
		
		// Snapshot
		if (getSaveSnapshots()) {
			view.appendChild("Snapshot", null);
		}
		
		// Xhym
		if (xhymAgent != null) {
			if (xhymSpace == null) {
				xhymSpace = view.appendChild("XhymSpace", null);
			}
			IXholon xs = xhymSpace.appendChild("XhymSpace", null);
			xs.appendChild("XhymAgent", null, "org.primordion.xholon.base.XhymAgent");
			xhymSpace.postConfigure();
		}
		consoleLog("Application ending initViewers()");
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#createInteractions()
	 */
	public void createInteractions()
	{
	  view.appendChild("Interactions", null);
		StringTokenizer st = new StringTokenizer(getInteractionParams(), ",");
		try {
			interaction.setOutputFormat(Integer.parseInt(st.nextToken()));
			interaction.setShowStates(Misc.booleanValue(st.nextToken()));
			interaction.setSocketHost(st.nextToken());
			interaction.setSocketPort(Integer.parseInt(st.nextToken()));
		} catch (NumberFormatException e) {
			logger.error("Application initViewers() Interactions", e);
		}
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#createChart(org.primordion.xholon.base.IXholon)
	 */
	public void createChart(IXholon chartRoot)
	{
	  System.out.println("Application createChart( " + chartRoot);
		StringTokenizer st = new StringTokenizer(getDataPlotterParams(), ",");
		if (st.countTokens() != 7) {
			st = new StringTokenizer("Title,Time (timesteps),Y,./statistics/,stats,1,WRITE_AS_LONG", ",");
		}
		
		createChart(
				chartRoot,
				st.nextToken(),
				st.nextToken(),
				st.nextToken(),
				st.nextToken(),
				st.nextToken(),
				//Integer.parseInt(st.nextToken()),
				Misc.atoi(st.nextToken(), 0),
				getWriteType(st.nextToken()));
	}
	
	/**
	 * Return the type of plotter data to write, given a string representation of this.
	 * @param writeTypeStr The write type as a string.
	 * @return The write type as an int.
	 */
	protected int getWriteType(String writeTypeStr)
	{
		int writeType = IChartViewer.WRITE_AS_NULL;
		if (writeTypeStr.equals("WRITE_AS_INT")) {
			writeType = IChartViewer.WRITE_AS_INT;
		}
		else if (writeTypeStr.equals("WRITE_AS_LONG")) {
			writeType = IChartViewer.WRITE_AS_LONG;
		}
		else if (writeTypeStr.equals("WRITE_AS_SHORT")) {
			writeType = IChartViewer.WRITE_AS_SHORT;
		}
		else if (writeTypeStr.equals("WRITE_AS_FLOAT")) {
			writeType = IChartViewer.WRITE_AS_FLOAT;
		}
		else if (writeTypeStr.equals("WRITE_AS_DOUBLE")) {
			writeType = IChartViewer.WRITE_AS_DOUBLE;
		}
		return writeType;
	}

	/**
	 * Create a google2 or JFreeChart or GnuPlot or C3 or NVD3 chart.
	 * @param chartRoot The root of the subtree that will provide values for the chart.
	 * If this value is null, then the model root will be used.
	 * @param xyChart Chart title.
	 * @param xAxisLabel X Axis title.
	 * @param yAxisLabel Y axis title.
	 * @param pathName Directory that data should be written to (gnuplot only).
	 * @param typeOfData A short string that will be used as the prefix for the names of output files (gnuplot only.
	 * @param nameConcatLevels When the name of a data element is added to the legend of a chart,
	 * it can optionally include the names of n-1 levels of parents. This is to help identify where
	 * this data element is in the Xholon tree. Default is 1.
	 * @param writeType int, long, short, float, double (gnuplot only).
	 */
	protected void createChart(
			IXholon chartRoot,
			String xyChart,
			String xAxisLabel,
			String yAxisLabel,
			String pathName,
			String typeOfData,
			int nameConcatLevels,
			int writeType)
	{
	  System.out.println("Application createChart( " + xyChart + "|" + xAxisLabel + "|" + yAxisLabel + "|" + pathName + "|" + typeOfData + "|" + writeType);
		if (nameConcatLevels < 1 || nameConcatLevels > 2) {
			nameConcatLevels = 1;
		}
		// add chart parameters to tree under View
		IXholon aParentNode = view;
		
		// XYChart
		IXholon newNode = aParentNode.appendChild("XYChart", xyChart);
		IXholon xyChartNode = newNode;
		
		// XAxisLabel, YAxisLabel
		xyChartNode.appendChild("XAxisLabel", xAxisLabel);
		xyChartNode.appendChild("YAxisLabel", yAxisLabel);
		
		// Reference all passive objects in model, using XYSeries and ports.
		// Each passive object is a separate dataset.
		if (chartRoot == null) {chartRoot = root;}
		Vector<?> treeV = chartRoot.getChildNodes(true);
		IXholon modelNode = null;
		for (int i = 0; i < treeV.size(); i++) {
			modelNode = (IXholon)treeV.elementAt(i);
			if (shouldBePlotted(modelNode)) {
				newNode = xyChartNode.appendChild("XYSeries", null);
				((XholonWithPorts)newNode).port = new Xholon[1];
				((XholonWithPorts)newNode).port[0] = modelNode; // have the XYSeries xholon ref the model xholon
			}
		}
		if (getUseJFreeChart()) {
			chartViewer = (IChartViewer)getService(AbstractXholonService.XHSRV_CHART_VIEWER + "-JFreeChart");
			if (chartViewer == null) {
				setUseDataPlotter("none");
			}
			else {
				chartViewer.initialize(xyChartNode, nameConcatLevels, null, 0, null);
			}
			if (!getUseJFreeChart()) {
				logger.info("JFreeChart is a free download from http://www.jfree.org/jfreechart/");
			}
		}
		else if (getUseGnuplot()) {
			chartViewer = (IChartViewer)getService(AbstractXholonService.XHSRV_CHART_VIEWER + "-gnuplot");
			System.out.println("Application createChart( using Gnuplot " + chartViewer);
			if (chartViewer == null) {
				setUseDataPlotter("none");
			}
			else {
				chartViewer.initialize(xyChartNode, nameConcatLevels, typeOfData, writeType, pathName);
			}
		}
		else if (getUseJdbc()) {
			chartViewer = (IChartViewer)getService(AbstractXholonService.XHSRV_CHART_VIEWER + "-jdbc");
			if (chartViewer == null) {
				setUseDataPlotter("none");
			}
			else {
				chartViewer.initialize(xyChartNode, nameConcatLevels, typeOfData, writeType, jdbcParams);
			}
		}
		else if (getUseJpa()) {
			chartViewer = (IChartViewer)getService(AbstractXholonService.XHSRV_CHART_VIEWER + "-jpa");
			if (chartViewer == null) {
				setUseDataPlotter("none");
			}
			else {
				chartViewer.initialize(xyChartNode, nameConcatLevels, typeOfData, writeType, jpaParams);
			}
		}
		else if (getUseGoogle()) {
			chartViewer = (IChartViewer)getService(AbstractXholonService.XHSRV_CHART_VIEWER + "-google");
			if (chartViewer == null) {
				setUseDataPlotter("none");
			}
			else {
				chartViewer.initialize(xyChartNode, nameConcatLevels, typeOfData, writeType, "");
			}
		}
		else if (getUseGoogle2()) {
			chartViewer = (IChartViewer)getService(AbstractXholonService.XHSRV_CHART_VIEWER + "-google2");
			System.out.println("Application createChart( using google2 " + chartViewer);
			if (chartViewer == null) {
				setUseDataPlotter("none");
			}
			else {
				chartViewer.initialize(xyChartNode, nameConcatLevels, typeOfData, writeType, "");
			}
		}
		else if (getUseC3()) {
			chartViewer = (IChartViewer)getService(AbstractXholonService.XHSRV_CHART_VIEWER + "-c3");
			System.out.println("Application createChart( using c3 " + chartViewer);
			if (chartViewer == null) {
				setUseDataPlotter("none");
			}
			else {
				chartViewer.initialize(xyChartNode, nameConcatLevels, typeOfData, writeType, "");
			}
		}
		else if (getUseNVD3()) {
			chartViewer = (IChartViewer)getService(AbstractXholonService.XHSRV_CHART_VIEWER + "-nvd3");
			System.out.println("Application createChart( using nvd3 " + chartViewer);
			if (chartViewer == null) {
				setUseDataPlotter("none");
			}
			else {
				chartViewer.initialize(xyChartNode, nameConcatLevels, typeOfData, writeType, "");
			}
		}
	}
	
	/**
	 * Determine if the input model node should be plotted on a chart.
	 * Override this method to use some other criteria.
	 * @param modelNode
	 * @return true if the modelNode is a passive object, otherwise false
	 */
	protected boolean shouldBePlotted(IXholon modelNode)
	{
		//if ((modelNode.getXhType() & IXholonClass.XhtypePurePassiveObject) == IXholonClass.XhtypePurePassiveObject) {
		if (modelNode.isPassiveObject()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	protected void createHistogram()
	{
	  consoleLog("createHistogram() 1");
		StringTokenizer st = new StringTokenizer(getHistogramPlotterParams(), ",");
		createHistogram(
				st.nextToken(),
				st.nextToken(),
				Integer.parseInt(st.nextToken()),
				st.nextToken(),
				st.nextToken(),
				st.nextToken(),
				st.nextToken(),
				st.nextToken(),
				Integer.parseInt(st.nextToken()),
				getWriteType(st.nextToken())
				);
	}
	
	protected void createHistogram(
			String histRootStr,
			String xholonClass,
			int numBins,
			String histogramTitle,
			String xAxisLabel,
			String yAxisLabel,
			String pathName,
			String typeOfData,
			int nameConcatLevels,
			int writeType
			)
	{
	  consoleLog("createHistogram() 2");
		// add chart parameters to tree under View
		IXholon aParentNode = view;
		
		// XYChart
		IXholon histRootView = aParentNode.appendChild("HistogramViewer", histogramTitle);
		
		consoleLog("createHistogram() 3");
		// XAxisLabel_Hist, YAxisLabel_Hist
		histRootView.appendChild("XAxisLabel_Hist", xAxisLabel);
		histRootView.appendChild("YAxisLabel_Hist", yAxisLabel);
		
		IXholon histRootModel = getXPath().evaluate(histRootStr, root);
		
		if (getUseJFreeChart_Hist()) {
			//String className = "org.primordion.xholon.io.HistogramViewerJFreeChart";
			//try {
				//histogramViewer = (IHistogramViewer)Class.forName(className).newInstance();
				histogramViewer = (IHistogramViewer)getService("HistogramViewerService");
				histogramViewer.initialize(
				    histRootModel, histRootView, inherHier.getClassNode(xholonClass), numBins, nameConcatLevels);
				//((org.primordion.xholon.io.HistogramViewerJFreeChart)histogramViewer).chart();
			//} catch (InstantiationException e) {
			//	setUseHistogramPlotter("none");
			//	logger.error("Unable to instantiate optional histogram viewer JFreeChart ", e);
			//} catch (IllegalAccessException e) {
			//	setUseHistogramPlotter("none");
			//	logger.error("Unable to load optional histogram viewer JFreeChart ", e);
			//} catch (ClassNotFoundException e) {
			//	setUseHistogramPlotter("none");
			//	logger.error("Unable to load optional histogram viewer JFreeChart " + className);
			//} catch (NoClassDefFoundError e) {
			//	setUseHistogramPlotter("none");
			//	logger.error("Unable to find optional histogram viewer JFreeChart ", e);
			//}
		}
		else if (getUseGnuplot_Hist()) {
		  consoleLog("createHistogram() 10");
		  histogramViewer = new HistogramViewerGnuplot();
			((org.primordion.xholon.io.HistogramViewerGnuplot)histogramViewer).initialize(
					histRootModel, histRootView, inherHier.getClassNode(xholonClass), numBins,
					nameConcatLevels, pathName, typeOfData, writeType
					);
		}
		else if (getUseGoogle2_Hist()) {
		  consoleLog("createHistogram() 11");
		  histogramViewer = new HistogramViewerGoogle2();
			((org.primordion.xholon.io.HistogramViewerGoogle2)histogramViewer).initialize(
					histRootModel, histRootView, inherHier.getClassNode(xholonClass), numBins,
					nameConcatLevels
					//, pathName, typeOfData, writeType
					);
		}
		else if (getUseD3_Hist()) {
		  consoleLog("createHistogram() 12");
		  histogramViewer = new HistogramViewerD3();
			((org.primordion.xholon.io.HistogramViewerD3)histogramViewer).initialize(
					histRootModel, histRootView, inherHier.getClassNode(xholonClass), numBins,
					nameConcatLevels, pathName, typeOfData, writeType
					);
		}
	}
	
	/**
	 *  Save snapshot of xholon tree.
	 */
	public void saveSnapshot()
	{
		if (getSaveSnapshots()) {
			snapshot.saveSnapshot(appRoot, modelName);
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#about()
	 */
	public void about()
	{
		String title = "About Xholon";
		IAbout about = (IAbout)getService(AbstractXholonService.XHSRV_ABOUT);
		if (about != null) {
			about.about(title, getAbout(), 350, 200);
		}
		else {
			println(title);
			println(getAbout());
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getAbout()
	 */
	public String getAbout()
	{
		String text;
		if (modelName == null) {
			text = aboutText;
		}
		else {
			text = modelName + "\n" + aboutText;
		}
		return text;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#information()
	 * 
	 * example lines in _xhn.xml config file:
	 * <param name="InformationFile" value="./config/HelloWorld/Information_helloworld.xml"/>
	 * <param name="InformationFile" value="file:///C:/Xholon/config/HelloWorld/Information_helloworld.xml"/>
	 * <param name="InformationFile" value="http://www.primordion.com/Xholon/samples/helloworld.html"/>
	 */
	public void information()
	{
		if (informationFile == null) {
			println("No information can be diplayed."
				+ " Please provide a valid file name using the InformationFile parameter in the _xhn.xml config file.");
			return;
		}
		String url = "";
		if (informationFile.startsWith("./")) {
		  /* GWT
			java.io.File infoFile = new java.io.File(informationFile.substring(2));
			String infoFileName = "file://" + infoFile.getAbsolutePath();
			for (int i = 0; i < infoFileName.length(); i++) {
				if (infoFileName.charAt(i) == '\\') {
					url += '/';
				}
				else {
					url += infoFileName.charAt(i);
				}
			}*/
			return;
		}
		else if (informationFile.startsWith("http")) {
		  url = informationFile;
		}
		else if (informationFile.indexOf("javascript:") != -1) {
		  // this may be a malicious script
		  return;
		}
		else {
		  // look for SVG content in the app's optional GWT ClientBundle
      String infoStr = rcConfig("info", findGwtClientBundle());
      // display this infoStr
      if ((infoStr != null) && (infoStr.length() != 0)) {
        infoStr = infoStr.trim();
        if (infoStr.startsWith("<!doctype html>") || infoStr.startsWith("<html>")) {
          url = "data:text/html," + URL.encode(infoStr);
        }
        else {
          url = "data:text/xml," + URL.encode(infoStr);
        }
      }
		  else {
			  url = GwtEnvironment.gwtHostPageBaseURL + informationFile;
			}
		}
		Window.open(url, "_blank", ""); // checked if it's "javascript:"
		
		//org.primordion.xholon.io.BrowserLauncher.launch(url); // GWT
	}
	
	/**
	 * @see org.primordion.xholon.app.IApplication#image()
	 */
	public void image() {
	  if (imageFile == null) {return;}
	  if ("default.svg".equals(imageFile)) {
	    String svgStr1 = null;
	    if (workbookBundle != null) {
		    svgStr1 = workbookBundle.getResource(XholonWorkbookBundle.RESOURCE_SVG);
		    consoleLog(svgStr1);
		  }
		  if (svgStr1 != null) {
		    imageFile = null; // no SVG file is specified
		    if ((svgStr1 != null) && (svgStr1.length() != 0)) {
	        //makeSvgClient(svgStr1);
	        // svgStr1 is already wrapped in SvgClient
	        IXholon xholonHelperService = getService(IXholonService.XHSRV_XHOLON_HELPER);
		      if (xholonHelperService != null) {
			      xholonHelperService.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, svgStr1, root);
		      }
	      }
		  }
	    else if (configFileName == null) {
	      imageFile = null; // no SVG file is specified
	      // look for SVG content in the app's optional GWT ClientBundle
	      String svgStr2 = rcConfig("svg", findGwtClientBundle());
	      // display this svgStr
	      if ((svgStr2 != null) && (svgStr2.length() != 0)) {
	        makeSvgClient(svgStr2);
	      }
	    }
	    else {
	      imageFile = this.configFileName.substring(0, this.configFileName.lastIndexOf("/")) + "/" + imageFile;
	      System.out.println("imageFile:" + imageFile);
	    }
	  }
	  if (imageFile == null) {return;}
	  if (useGwt) {
	    makeSvgClient();
	    /*try {
		    new RequestBuilder(RequestBuilder.GET, imageFile).sendRequest("", new RequestCallback() {
          @Override
          public void onResponseReceived(Request req, Response resp) {
            if (resp.getStatusCode() == resp.SC_OK) {
              //root.println(resp.getText());
              showSvgImage(resp.getText());
            }
            else {
              //root.println("status code:" + resp.getStatusCode());
              //root.println("status text:" + resp.getStatusText());
              //root.println("text:\n" + resp.getText());
            }
          }

          @Override
          public void onError(Request req, Throwable e) {
            //root.println("onError:" + e.getMessage());
          }
        });
      } catch(RequestException e) {
        //root.println("RequestException:" + e.getMessage());
      }*/
	  }
	}
	
	/*
<SvgClient>
  <Attribute_String roleName="setup">${MODELNAME_DEFAULT},${SVGURI_DEFAULT}</Attribute_String>
</SvgClient>

${MODELNAME_DEFAULT},${SVGURI_DEFAULT},,,./,${VIEWABLES_CREATE}
	*/
	protected void makeSvgClient() {
	  StringBuilder sb = new StringBuilder(128)
	  .append("<SvgClient>\n")
	  .append("  <Attribute_String roleName=\"setup\">${MODELNAME_DEFAULT},")
	  .append(imageFile)
	  .append(",,,./,${VIEWABLES_CREATE}")
	  .append("</Attribute_String>\n")
	  .append("</SvgClient>\n");
	  
	  IXholon xholonHelperService = getService(IXholonService.XHSRV_XHOLON_HELPER);
		if (xholonHelperService != null) {
			xholonHelperService.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, sb.toString(), root);
		}
	}
	
		/*
<SvgClient>
<Attribute_String roleName="svgUri"><![CDATA[data:image/svg+xml,
<svg width="100" height="50" xmlns="http://www.w3.org/2000/svg">
  <g>
    <title>Feinberg - Chemical Reaction Networks</title>
    <rect id="ReactionNetworkSystem" fill="#98FB98" height="50" width="50" x="25" y="0"/>
  </g>
</svg>
]]></Attribute_String>
<Attribute_String roleName="setup">${MODELNAME_DEFAULT},${SVGURI_DEFAULT}</Attribute_String>
</SvgClient>
	*/
	protected void makeSvgClient(String svgStr) {
	  StringBuilder sb = new StringBuilder(128)
	  .append("<SvgClient>\n")
	  .append("<Attribute_String roleName=\"svgUri\"><![CDATA[data:image/svg+xml,")
	  .append(svgStr)
	  .append("]]></Attribute_String>\n")
	  .append("<Attribute_String roleName=\"setup\">${MODELNAME_DEFAULT},${SVGURI_DEFAULT}</Attribute_String>\n")
	  .append("</SvgClient>\n");
	  
	  IXholon xholonHelperService = getService(IXholonService.XHSRV_XHOLON_HELPER);
		if (xholonHelperService != null) {
			xholonHelperService.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, sb.toString(), root);
		}
	  //return sb.toString();
	}
	
	/**
	 * Show the SVG as a viewable image in the browser page.
	 */
	protected native void showSvgImage(String text) /*-{
	  //var element = $doc.getElementById("xhsvg");
	  // create a new div element as a child of "xhsvg" div.
	  var element = $doc.createElement("div");	  
    if (element) {
  	  element.id = "defaultsvg";
	    var div = $doc.getElementById("xhsvg");
	    if (div) {
	      div.appendChild(element);
        element.innerHTML = text;
      }
    }
	}-*/;
	
	/*
	 * @see org.primordion.xholon.app.IApplication#invokeGraphicalTreeViewer(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public IViewer invokeGraphicalTreeViewer(IXholon xhStart, String graphicalNetworkViewerParams)
	{
	  /*IXholon2GraphFormat treeViewer = new Xholon2ChapNetwork(false, true);
	  treeViewer.initialize(null, this.getModelName(), xhStart);
	  treeViewer.writeAll();*/
	  /* GWT
		StringTokenizer st = new StringTokenizer(this.getGraphicalTreeViewerParams(), ",");
		st.nextToken(); // discard first token, which has xhStart
		try {
			treeViewerJung = (IGraphicalTreeViewer)getService("GraphicalTreeViewerService");
			treeViewerJung.setDistances(
					Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()));
			treeViewerJung.setGraphSize(
					Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()));
			treeViewerJung.setLabelFontSize(Integer.parseInt(st.nextToken()));
			treeViewerJung.createGraph(
					xhStart,
					st.nextToken(),
					Misc.booleanValue(st.nextToken()));
		} catch (NumberFormatException e) {
			logger.error("Application invokeGraphicalTreeViewer()", e);
		}*/
		return treeViewerJung;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#invokeGraphicalTreeViewer()
	 */
	public IViewer invokeGraphicalTreeViewer()
	{
		if (getUseGraphicalTreeViewer()) {
			StringTokenizer st = new StringTokenizer(getGraphicalTreeViewerParams(), ",");
			
			// could use XPath to find a start tree node for a graph
			IXholon xhStart = getXhStart(st.nextToken());
			return invokeGraphicalTreeViewer(xhStart, getGraphicalTreeViewerParams());
		}
		else {
			return null;
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#invokeGraphicalNetworkViewer(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public IViewer invokeGraphicalNetworkViewer(IXholon xhStart, String graphicalNetworkViewerParams)
	{
	  /*IXholon2GraphFormat networkViewer = new Xholon2ChapNetwork();
	  networkViewer.initialize(null, this.getModelName(), xhStart);
	  networkViewer.writeAll();*/
	  /* GWT ant build
		StringTokenizer st = new StringTokenizer(graphicalNetworkViewerParams, ",");
		st.nextToken(); // discard first token, which has xhStart
		try {
			networkViewerJung =
				(IGraphicalNetworkViewer)Class.forName(getGraphicalNetworkViewerClassName()).newInstance();
			networkViewerJung.setGraphSize(
					Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()));
			networkViewerJung.setLabelFontSize(Integer.parseInt(st.nextToken()));
			String layoutTypeStr = st.nextToken();
			int layoutType = IGraphicalNetworkViewer.LAYOUT_NONE;
			if (layoutTypeStr.equals("LAYOUT_FR"))
				{layoutType = IGraphicalNetworkViewer.LAYOUT_FR;}
			else if (layoutTypeStr.equals("LAYOUT_ISOM"))
				{layoutType = IGraphicalNetworkViewer.LAYOUT_ISOM;}
			else if (layoutTypeStr.equals("LAYOUT_KK"))
				{layoutType = IGraphicalNetworkViewer.LAYOUT_KK;}
			else if (layoutTypeStr.equals("LAYOUT_KKINT"))
				{layoutType = IGraphicalNetworkViewer.LAYOUT_KKINT;}
			else if (layoutTypeStr.equals("LAYOUT_SPRING"))
				{layoutType = IGraphicalNetworkViewer.LAYOUT_SPRING;}
			else if (layoutTypeStr.equals("LAYOUT_STATIC"))
				{layoutType = IGraphicalNetworkViewer.LAYOUT_STATIC;}
			else if (layoutTypeStr.equals("LAYOUT_CIRCLE"))
				{layoutType = IGraphicalNetworkViewer.LAYOUT_CIRCLE;}
			else if (layoutTypeStr.equals("LAYOUT_DAG"))
				{layoutType = IGraphicalNetworkViewer.LAYOUT_DAG;}
			networkViewerJung.setLayoutType(layoutType);
			networkViewerJung.setShowContainers(Misc.booleanValue(st.nextToken()));
			networkViewerJung.createGraph(
					xhStart,
					st.nextToken(),
					Misc.booleanValue(st.nextToken()));
		} catch (InstantiationException e) {
			logger.error("Unable to find optional graphical network viewer ", e);
		} catch (IllegalAccessException e) {
			logger.error("Unable to find optional graphical network viewer ", e);
		} catch (ClassNotFoundException e) {
			logger.error("Unable to load optional graphical network viewer {}"
				+ getGraphicalNetworkViewerClassName());
		} catch (NoClassDefFoundError e) {
			logger.error("Unable to find optional graphical network viewer ", e);
		} catch (NumberFormatException e) {
			logger.error("Application invokeGraphicalNetworkViewer()", e);
		}
		*/
		return networkViewerJung;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#invokeGraphicalNetworkViewer()
	 */
	public IViewer invokeGraphicalNetworkViewer()
	{
		if (getUseGraphicalNetworkViewer()) {
			StringTokenizer st = new StringTokenizer(getGraphicalNetworkViewerParams(), ",");
			// could use XPath to find a start tree node for a graph
			IXholon xhStart = getXhStart(st.nextToken());
			return invokeGraphicalNetworkViewer(xhStart, getGraphicalNetworkViewerParams());
		}
		else {
			return null;
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#invokeDataPlotter()
	 */
	public IViewer invokeDataPlotter()
	{
	  consoleLog("App.invokeDataPlotter() starting ...");
		if (getUseDataPlotter()) {
  	  consoleLog("App.invokeDataPlotter() 1");
  	  consoleLog(chartViewer);
			chartViewer.chart();
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#invokeHistogramPlotter()
	 */
	public IViewer invokeHistogramPlotter()
	{
	  consoleLog("App.invokeHistogramPlotter() starting ...");
		if (getUseHistogramPlotter()) {
		  consoleLog("invokeHistogramPlotter() 1");
		  consoleLog(histogramViewer);
			histogramViewer.chart();
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#invokeInteraction()
	 */
	public IViewer invokeInteraction()
	{
		if (getUseInteractions()) {
			interaction.createSequenceDiagram(modelName);
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#invokeXholon2Gui(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public IXholon2Gui invokeXholon2Gui(IXholon guiRoot, String guiFileName)
	{
	  /* GWT ant build
		// This could be called before the application is properly initialized,
		// such as from an external GUI startup routine (ex: a web page).
		if (getControllerState() == IControl.CS_NO_MODEL_LOADED) {return null;}
		// It probably doesn't make sense right now to write a .zul file,
		// unless it's been requested by name from a web page.
		String guiClassName = getXholon2GuiClassName();
		if (guiClassName.indexOf("Zuml") != -1) {
			if (guiFileName == null) {return null;} // for Zuml to work, there must be a file name
		}
		IXholon2Gui xholon2gui = null;
		try {
			xholon2gui = (IXholon2Gui)Class.forName(guiClassName).newInstance();
		} catch (InstantiationException e) {
			logger.error("Unable to load grid viewer ", e);
		} catch (IllegalAccessException e) {
			logger.error("Unable to load grid viewer ", e);
		} catch (ClassNotFoundException e) {
			logger.error("Unable to load grid viewer " + getGridPanelClassName() + e.toString());
		} catch (NoClassDefFoundError e) {
			logger.error("Unable to load grid viewer ", e);
		}
		if (xholon2gui != null) {
			xholon2gui.setParams(getXholon2GuiParams());
			if (xholon2gui.initialize(guiFileName, getModelName(), guiRoot)) {
				xholon2gui.writeAll();
			}
		}
		return xholon2gui;*/
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString()
	{
		return getName(); //this.modelName;
	}
	
	/*
	 * @see java.lang.Runnable#run()
	 */
	/* GWT
	public void run()
	{
		runApp();
	}*/
	
	/*
	 * @see org.primordion.xholon.app.IApplication#runApp()
	 */
	public void runApp()
	{
	  noGui = true;
		try {
			initialize(configFileName);
		} catch (XholonConfigurationException e) {return;}
		if (root == null) {return;}
		initControl();
		initViewers();
		//clearConsole();
		//resizeConsole(150, -1);
		if (useXholon2Gui) {
			setControllerState(IControl.CS_INITIALIZED);
		}
		else {
			setControllerState(IControl.CS_RUNNING);
			// setControllerState(getInitialControllerState());
		}
		boolean rc = loadWorkbook();
		if (rc == false) {
		  // no workbook is being asynchronously loaded
		  process();
		}
		//wrapup(); // GWT (wrapup() is called in a different way
	}
	
	/*
	 * Clear the contents of the xhconsole xhout.
	 */
	public void clearConsole() {
    //Element element = Document.get().getElementById("xhout");
    //element = element.getFirstChildElement();
    Element element = HtmlElementCache.xhout.getFirstChildElement();
    if (element != null) {
        TextAreaElement textfield = element.cast();
        textfield.setValue("");
    }
	}
	
	/**
	 * Resize the console.
	 * @param height A non-negative height in pixels.
	 * @param width A non-negative width in pixels.
	 */
	//public native void resizeConsole(int height, int width) /*-{
	//  var element = $doc.getElementById("xhconsole").firstChild;
	//  if (element) {
	//    if (height > -1) {element.style.height = height + "px"};
	//    if (width > -1) {element.style.width = width + "px";}
	//  }
	//}-*/;
	
	// TODO is this still needed ?
	/*public void resizeConsole(int height, int width) {
	  Element element = HtmlElementCache.xhconsole.getFirstChildElement();
	  if (element != null) {
	    if (height > -1) {element.getStyle().setHeight(height, Unit.PX);}
	    if (width > -1) {element.getStyle().setWidth(width, Unit.PX);}
	  }
	}*/
	
	/**
	 * Clear the contents of the load-time console.
	 * Add xhout and xhclipboard tabs to a new TabLayoutPanel.
	 */
	public void makeDefaultConsoles() {
	  XholonGwtTabPanelHelper.clearConsoleContents();
	  //addTab(String text, String tabText, String tooltip)
	  //String style = "style=\"width: 100%; height: 140px; border: 1px;\"";
	  String style = " style=\"width: 100%; height: 100%; border: 1px;\"";
	  String rows = " rows=8";
	  String outHtml = "<div id=\"xhout\"><textarea" + rows + style + "></textarea></div>";
	  XholonGwtTabPanelHelper.addTab(outHtml, "out", null, true);
	  HtmlElementCache.xhout = Document.get().getElementById("xhout");
	  
	  String cbHtml = "<div id=\"xhclipboard\"><textarea" + rows + style + "></textarea></div>";
	  XholonGwtTabPanelHelper.addTab(cbHtml, "clipboard", null, true);
	  HtmlElementCache.xhclipboard = Document.get().getElementById("xhclipboard");
	  
	  // make the contents of the "out" tab visible
	  XholonGwtTabPanelHelper.selectTab(0);
	}
	
	/**
	 * Perform most or all of the work required of main() in an Application subclass.
	 * @param args Arguments passed in from the command line.
	 * <p>ex: -configFileName /myFolder/MyApp_xhn.xml -appMultiThreaded false</p>
	 * @param className Name of the Application subclass.
	 * <p>ex: "org.primordion.xholon.tutorials.AppHelloWorld"</p>
	 * @param defaultConfigFileName Name and path of a default _xhn.xml configuration file.
	 * <p>ex: "./config/HelloWorld/HelloWorld_xhn.xml"</p>
	 */
	public static void appMain(String[] args, String className, String defaultConfigFileName)
	{
	  /*
		Application app = null;
		try {
			app = (Application)Class.forName(className).newInstance();
			// set some default values
			app.setConfigFileName(defaultConfigFileName);
			boolean appMultiThreaded = DEFAULT_APP_MULTI_THREADED;
			// handle optional command line arguments
			if (args.length > 0) {
				int i = 0;
				String arg;
				while ((i < args.length) && (args[i].startsWith("-"))) {
					arg = args[i++];
					if (arg.equals("-configFileName")) {
						if (i < args.length) {
							app.setConfigFileName(args[i++]);
						}
					} else if (arg.equals("-appMultiThreaded")) {
						if (i < args.length) {
							appMultiThreaded = Boolean.parseBoolean(args[i++]);
						}
					} else {
						logger.warn("Unknown command line argument found in appMain: " + arg);
					}
				}
			}
			// optionally run the app in a separate thread
			if (appMultiThreaded) {
				String threadName = app.getConfigAppName();
				if ((threadName == null) || (threadName.length() == 0)) {
					threadName = "xholonMain";
				}
				(new Thread(app, threadName)).start();
			}
			else {
				app.runApp();
			}
		} catch (ClassNotFoundException e) {
			logger.error("Unable to create instance of " + className, e);
			return;
		} catch (InstantiationException e) {
			logger.error("Unable to create instance of " + className, e);
			return;
		} catch (IllegalAccessException e) {
			logger.error("Unable to create instance of " + className, e);
			return;
		}
		if (app.getRoot() == null) {
			return;
		}
		//System.out.print(app.getControllerState());
		while (app.getControllerState() == IControl.CS_NO_MODEL_LOADED) {
			//print(".");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Xholon.getLogger().error("Application appMain(): Unable to sleep() ", e);
			}
		}
		*/
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#getOut()
	 */
	/* GWT ant build
	public Writer getOut() {
		//return out; // GWT ant build
		return null;
	}*/

	/*
	 * @see org.primordion.xholon.app.IApplication#setOut(java.io.Writer)
	 */
	/* GWT ant build
	public void setOut(Writer out) {
		//this.out = out; // GWT ant build
	}*/

	/*
	 * @see org.primordion.xholon.app.IApplication#isUseAppOut()
	 */
	public boolean isUseAppOut() {
		return useAppOut;
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#setUseAppOut(boolean)
	 */
	public void setUseAppOut(boolean useAppOut) {
		this.useAppOut = useAppOut;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#isUseGwt()
	 */
	public boolean isUseGwt() {
		return useGwt;
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#setUseGwt(boolean)
	 */
	public void setUseGwt(boolean useGwt) {
		this.useGwt = useGwt;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setHostPageBaseURL(java.lang.String)
	 */
	public void setHostPageBaseURL(String hostPageBaseURL) {
	  this.hostPageBaseURL = hostPageBaseURL;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getHostPageBaseURL()
	 */
	public String getHostPageBaseURL() {
	  return hostPageBaseURL;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#makeAppSpecificNode(java.lang.String)
	 */
	public IXholon makeAppSpecificNode(String implName) {
		return null;
	}
	
	/**
	 * Create a new IXholon instance, given a JavaScript constructor object.
	 * This is intended to be called from the code generated by AppGenerator.
	 */
	protected native JavaScriptObject makeAppSpecificNode(JavaScriptObject constructor) /*-{
	  console.log(constructor);
	  var obj = new constructor();
	  console.log(obj);
	  return obj;
	}-*/;
	
	/*
	 * @see org.primordion.xholon.app.IApplication#findAppSpecificClass(java.lang.String)
	 */
	public Class<IXholon> findAppSpecificClass(String className) {
	  return null;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#findAppSpecificConstantValue(Class, java.lang.String)
	 */
	public int findAppSpecificConstantValue(Class<?> clazz, String constName) {
	  return Integer.MIN_VALUE;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getAppSpecificObjectVal(IXholon, java.lang.String)
	 */
	public IXholon getAppSpecificObjectVal(IXholon node, Class<IXholon> clazz, String attrName) {
	  //return null;
	  return SystemMechSpecific.instance.getAppSpecificObjectVal(node, clazz, attrName);
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setAppSpecificObjectVal(IXholon, Class, java.lang.String, IXholon)
	 */
	public boolean setAppSpecificObjectVal(IXholon node, Class<IXholon> clazz, String attrName, IXholon val) {
	  //return false;
	  return SystemMechSpecific.instance.setAppSpecificObjectVal(node, clazz, attrName, val);
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getAppSpecificObjectValNames(IXholon, Class)
	 */
	public String getAppSpecificObjectValNames(IXholon node, Class<IXholon> clazz) {
	  String names = "";
	  IXholon[] port = node.getPort();
	  if (port != null) {
	    for (int portNum = 0; portNum < port.length; portNum++) {
	      if (port[portNum] != null) {
	        names = names + portNum + ",";
	      }
	    }
	  }
	  names = names + SystemMechSpecific.instance.getAppSpecificObjectValNames(node, clazz);
	  return names;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getAppSpecificObjectVals(IXholon)
	 */
	public List getAppSpecificObjectVals(IXholon node, Class<IXholon> clazz) {
	  List list = new ArrayList();
	  String[] portNames = getAppSpecificObjectValNames(node, clazz).split(",");
	  for (int i = 0; i < portNames.length; i++) {
	    String portName = portNames[i];
	    IXholon reffedNode = null;
	    if ((portName != null) && (portName.length() > 0) && (Misc.getNumericValue(portName.charAt(0)) != -1)) {
	      int portNum = Integer.parseInt(portName);
	      reffedNode = node.getPort(portNum);
        if (reffedNode != null) {
          list.add(new PortInformation("port", portNum, reffedNode, null));
	      }
	    }
	    else {
	      reffedNode = getAppSpecificObjectVal(node, clazz, portName);
	      if (reffedNode != null) {
	        //PortInformation(String fieldName, int fieldNameIndex, IXholon reffedNode, String xpathExpression)
	        list.add(new PortInformation(portName, reffedNode));
	      }
	    }
	    
	  }
	  return list;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#isAppSpecificClassFindable(java.lang.String)
	 */
	public boolean isAppSpecificClassFindable(String implName) {
	  return false; //true;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getAppSpecificAttribute(IXholon, Class, java.lang.String)
	 */
	public Object getAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName) {
	  //return null;
	  return SystemMechSpecific.instance.getAppSpecificAttribute(node, clazz, attrName);
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setAppSpecificAttribute(IXholon, Class, java.lang.String, Object)
	 */
	public void setAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName, Object attrVal) {
	  SystemMechSpecific.instance.setAppSpecificAttribute(node, clazz, attrName, attrVal);
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#getAppSpecificAttributes(IXholon, Class, boolean)
	 */
	public Object[][] getAppSpecificAttributes(IXholon node, Class<IXholon> clazz, boolean returnAll) {
	  //return null;
	  return SystemMechSpecific.instance.getAppSpecificAttributes(node, clazz, returnAll);
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#isAppSpecificAttribute(IXholon, Class, java.lang.String)
	 */
	public boolean isAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName) {
	  return false;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setAppSpecificObjectVal(IXholon, Class, java.lang.String, int, IXholon)
	 */
	public boolean setAppSpecificObjectArrayVal(IXholon node, Class<IXholon> clazz, String attrName, int index, IXholon val) {
	  return SystemMechSpecific.instance.setAppSpecificObjectArrayVal(node, clazz, attrName, index, val);
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#findGwtClientBundle()
	 */
	public Object findGwtClientBundle() {
	  return null;
	}
	
	public String getWorkbookId() {
	  return workbookId;
	}
	
	public void setWorkbookId(String workbookId) {
	  this.workbookId = workbookId;
	}
	
	public String getWorkbookFileName() {
	  return workbookFileName;
	}
	
	public void setWorkbookFileName(String workbookFileName) {
	  this.workbookFileName = workbookFileName;
	}
	
	public IXholon getWorkbookBundle() {
	  return (XholonWorkbookBundle)workbookBundle;
	}
	
	public void setWorkbookBundle(IXholon workbookBundle) {
	  this.workbookBundle = (XholonWorkbookBundle)workbookBundle;
	}
	
	/**
	 * Load a XholonWorkbook from some source,
	 * typically as a last child of root in a Chameleon app.
	 * @return
	 *  true  if a workbook is being asynchronously loaded (such as from github),
	 *  false if a workbook has been synchronously loaded (such as from localStorage),
	 *        or if the workbook could not be loaded (async or sync).
	 */
	public boolean loadWorkbook() {
	  if (workbookId == null) {return false;}
	  if (workbookId.startsWith("gist")) {
	    return loadWorkbookGist();
	  }
	  else if (workbookId.startsWith("lstr")) {
	    loadWorkbookLstr();
	    return false;
	  }
	  return false;
	}
	
	/**
	 * Try to load a workbook from the browser's localStorage.
	 * If unable to load a workbook, it quietly returns.
	 */
	protected void loadWorkbookLstr() {
	  Storage storage = Storage.getLocalStorageIfSupported();
	  if (storage != null) {
	    String key = workbookId.substring(4); // remove "lstr" at start of string
	    String wbStr = storage.getItem(key);
	    if (wbStr != null) {
	      IXholon xholonHelperService = getService(IXholonService.XHSRV_XHOLON_HELPER);
		    if (xholonHelperService != null) {
			    xholonHelperService.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, wbStr, root);
		    }
	    }
	  }
	}
	
	/**
	 * Load a workbook asynchronously from the github site, using JSONP.
	 */
	protected boolean loadWorkbookGist() {
	  String gistId = workbookId.substring(4); // remove "gist" at start of string
	  IXholon gistService = getService(IXholonService.XHSRV_GIST);
	  if (gistService != null) {
			gistService.sendSyncMessage(101, gistId, this);
		}
	  return true;
	}
	
	/**
	 * Load a XholonWorkbook gist from github using JSONP.
	 * @param src Example URL
	 *   https://gist.github.com/raw/3377945/xholonWorkbook.xml?callback=xh.wb
	 *   https://gist.github.com/kenwebb/3457105/raw/crn_1987_6_7_csh.xml?callback=xh.wb
	 *   https://gist.github.com/kenwebb/3377945/raw/xholonWorkbook.xml
	 *   https://api.github.com/gists/3377945?callback=xh.wb
	 *   https://api.github.com/gists/3457105#file-crn_2_44_csh-xml?callback=xh.wb
	 */
	//protected native void loadGistJsonp(String src) /*-{
	//  var script = $doc.createElement("script");
	//  script.setAttribute("src", src);
	//  script.setAttribute("type", "text/javascript");
	//  console.log(script);
	//  $doc.body.appendChild(script);
	//}-*/;
	
	/**
	 * This callback will be called by $wnd.xh.wb(data)
	 *
	 * json.data.files."xholonWorkbook.xml".content
	 * json["data"]["files"]["xholonWorkbook.xml"]["content"]
	 * 
	 * @param data The JSON data returned by github,
	 * either a XholonWorkbook or some error string.
	 */
	public void wbCallback(String data) {
	  IXholon xholonHelperService = getService(IXholonService.XHSRV_XHOLON_HELPER);
		if (xholonHelperService != null) {
			xholonHelperService.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, data, root);
		}
	  if (this.gui != null) {
	    ((IXholonGui)gui).showTree(this);
	  }
	  // update in case these values have changed by reading the XholonWorkbook
		showTimeStep();
    showMaxProcessLoops();
		
	  process();
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#isAllowConfigSrv()
	 */
	public boolean isAllowConfigSrv() {
		return allowConfigSrv;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#setAllowConfigSrv(boolean)
	 */
	public void setAllowConfigSrv(boolean allowConfigSrv) {
		this.allowConfigSrv = allowConfigSrv;
	}

	public IGraphicalTreeViewer getTreeViewerJung() {
		return treeViewerJung;
	}

	public IGraphicalNetworkViewer getNetworkViewerJung() {
		return networkViewerJung;
	}

	public IChartViewer getChartViewer() {
		return chartViewer;
	}

	public IHistogramViewer getHistogramViewer() {
		return histogramViewer;
	}
	
	/**
	 * Temporarily set attributes that ref IXholon nodes, to null.
	 * Return an array with their actual values.
	 * This is intended for use by Boxer, such as during serialization.
	 * @return An array that should later be passed
	 * to restoreIXholonAttributes() to restore the values.
	 */
	public IXholon cacheIXholonAttributes() {
		IXholon[] attrs = new IXholon[11];
		attrs[0] = this.chartViewer; this.chartViewer = null;
		attrs[1] = this.controlRoot; this.controlRoot = null;
		attrs[2] = this.defaultMechanism; this.defaultMechanism = null;
		attrs[3] = this.factory; this.factory = null;
		attrs[4] = this.histogramViewer; this.histogramViewer = null;
		attrs[5] = this.networkViewerJung; this.networkViewerJung = null;
		attrs[6] = this.root; this.root = null;
		attrs[7] = this.srvRoot; this.srvRoot = null;
		attrs[8] = this.treeViewerJung; this.treeViewerJung = null;
		attrs[9] = this.view; this.view = null;
		attrs[10] = this.xhcRoot; this.xhcRoot = null;
		
		// others
		//attrs[] = this.appRoot; // points to itself; don't bother controlling it
		//attrs[] = this.controller; // deleted
		// gridViewers
		// gui
		//attrs[] = this.inherHier; // keep transient, and regen on deserialize
		//attrs[] = this.interaction; // keep transient, regen
		//attrs[] = this.mechRoot; // keep transient, redundant
		//attrs[] = this.msgQ; // keep transient for now
		//attrs[] = this.snapshot; // keep transient
		//attrs[] = this.systemMsgQ; // keep transient for now
		//attrs[] = this.xhymSpace; // keep transient, ignore
		//for (int i = 0; i < attrs.length; i++) {
		//	System.out.println(attrs[i]);
		//}
		
		Attribute_Object ao = new Attribute_Object();
		ao.setVal(attrs);
		return ao;
	}
	
	/**
	 * Restore values of previously nulled IXholon attributes.
	 * @param attrs
	 */
	public void restoreIXholonAttributes(IXholon attrsArg) {
		IXholon[] attrs = (IXholon[]) attrsArg.getVal_Object();
		this.chartViewer = (IChartViewer) attrs[0];
		this.controlRoot = (IControl) attrs[1];
		this.defaultMechanism = (IMechanism) attrs[2];
		this.factory = (ITreeNodeFactory) attrs[3];
		this.histogramViewer = (IHistogramViewer) attrs[4];
		this.networkViewerJung = (IGraphicalNetworkViewer) attrs[5];
		this.root = attrs[6];
		this.srvRoot = attrs[7];
		this.treeViewerJung = (IGraphicalTreeViewer) attrs[8];
		this.view = (IControl) attrs[9];
		this.xhcRoot = (IXholonClass) attrs[10];
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
	  IReflection ir = ReflectionFactory.instance();
		// get attributes that belong directly to the concrete Java class, excluding statics
		Object attribute[][] = ir.getAppSpecificAttributes(this, xholon2xml.getXhAttrReturnAll(), false, false);
		xmlWriter.writeStartElement("params");
		for (int i = 0; i < attribute.length; i++) {
			Class<?> clazz = (Class<?>)attribute[i][2];
			if (clazz.isArray()) {
				// for now, ignore arrays
				continue;
			}
			// TODO for now, should ignore collections and anything else that is not a primitive
			String name = (String)attribute[i][0];
			if (name == null) {continue;}
			if ("roleName".equalsIgnoreCase(name)) {continue;}
			else if ("uid".equalsIgnoreCase(name)) {continue;}
			else if ("uri".equalsIgnoreCase(name)) {continue;}
			Object value = attribute[i][1];
			if (value == null) {continue;}
			// don't write attributes of type IXholon
			if (ClassHelper.isAssignableFrom(Xholon.class, value.getClass())) {
				continue;
			}
			toXmlAttribute(xholon2xml, xmlWriter, name, value.toString(), null);
		}
		xmlWriter.writeEndElement("params");
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXmlAttribute(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter, java.lang.String, java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	public void toXmlAttribute(IXholon2Xml xholon2xml, IXmlWriter xmlWriter, String name, String value, Class clazz)
	{
		// <param name="ModelName" value="adoptatree"/>
		xmlWriter.writeStartElement("param");
		xmlWriter.writeAttribute("name", name);
		xmlWriter.writeAttribute("value", value);
		xmlWriter.writeEndElement("param");
	}
	
}
