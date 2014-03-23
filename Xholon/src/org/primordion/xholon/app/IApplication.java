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

//import java.io.Writer;
import java.util.List;

//import com.google.gwt.resources.client.ClientBundleWithLookup;

import org.primordion.xholon.io.GridViewerDetails;
import org.primordion.xholon.base.IControl;
import org.primordion.xholon.base.IInteraction;
import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IQueue;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IInheritanceHierarchy;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.IChartViewer;
import org.primordion.xholon.io.IGraphicalNetworkViewer;
import org.primordion.xholon.io.IGraphicalTreeViewer;
import org.primordion.xholon.io.IHistogramViewer;
import org.primordion.xholon.io.IViewer;
import org.primordion.xholon.io.IXholon2Gui;
import org.primordion.xholon.service.creation.ITreeNodeFactory;

/**
 * Every Xholon application proceeds through three phases,
 * which are declared in this interface:
 *     (1) initialize,
 *     (2) process,
 *     (3) wrapup.
 * Applications also need to read their configuration from a file.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jul 28, 2005)
 */
public interface IApplication extends IXholon {
	
	/**
	 * Get the instance of Application that the Xholon node is part of.
	 * There may be multiple instances of Application, each with its own set of xholons.
	 * See also the static equivalent of this method.
	 * @param xhNode An instance of IXholon.
	 * @return An instance of Application, or null (should never happen).
	 */
	public abstract IApplication getApplicationOther(IXholon xhNode);
	
	/**
	 * Get the first instance of Application that has the specified name.
	 * See also the static equivalent of this method.
	 * @param appName The application name. This is the model name in the _xhn.xml file.
	 * @return An IApplication, or null.
	 */
	public abstract IApplication getApplicationOther(String appName);
	
	/**
	 * Initialize the application.
	 * @param configFileName Name of the configuration file.
	 * @throws XholonConfigurationException
	 */
	public abstract void initialize(String configFileName) throws XholonConfigurationException;

  /**
   * Read parameter values from the default configuration file, and
   * read parameter values from main configuration file/String for this specific application.
   * @param configFileName Name of the configuration file.
	 * @throws XholonConfigurationException
   */
  public abstract void readParameters(String configFileName) throws XholonConfigurationException;

	/**
	 * Process time steps during the lifetime of the application.
	 */
	public abstract void process();

	/**
	 * Wrapup any unfinished business in the application.
	 */
	public abstract void wrapup();

	/**
	 * Read configuration values from an XML file.
	 * @param fileName Name of the configuration file. ex: Config_HelloWorld.xml
	 * @throws XholonConfigurationException 
	 */
	public abstract void readConfigFromFileXml(String fileName) throws XholonConfigurationException;
	
	/**
	 * Set the value of a parameter.
	 * @param pName Parameter name.
	 * @param pValue Parameter value.
	 */
	public abstract boolean setParam(String pName, String pValue);
	
	/**
	 * Get the value of a parameter.
	 * @param pName Parameter name.
	 * @return A parameter value, or null.
	 */
	public abstract String getParam(String pName);
	
	/**
	 * Set state of the controller for this app.
	 * @param controllerState The current controller state.
	 */
	public abstract void setControllerState(int controllerState);
	
	/**
	 * Get state of the controller.
	 * @return The current controller state.
	 */
	public abstract int getControllerState();
	
	/**
	 * Get displayable name of the state.
	 * @return A displayable name.
	 */
	public abstract String getControllerStateName();
	
	/**
	 * Set the current time step of the executing application.
	 * @param ts The current time step.
	 */
	public abstract void setTimeStep(int ts);
	
	/**
	 * Get the current time step of the executing application.
	 * @return The current time step.
	 */
	public abstract int getTimeStep();
	
	/**
	 * Set the model root.
	 * @param root The model root.
	 */
	public abstract void setRoot(IXholon root);
	
	/**
	 * Get the model root.
	 * @return The model root.
	 */
	public abstract IXholon getRoot();
	
	/**
	 * Get the root View node.
	 * @return The view root.
	 */
	public IControl getView();
	
	/**
	 * Set the view root.
	 * @param view The view root.
	 */
	public void setView(IControl view);
	
	/**
	 * Set the application root.
	 * @param appRoot The application root.
	 */
	public abstract void setAppRoot(IXholon appRoot);
	
	/**
	 * Get the application root.
	 * @return The application root.
	 */
	public abstract IXholon getAppRoot();
	
	/**
	 * Set the the Inheritance Hierarchy XholonClass root.
	 * @param xhcRoot The XholonClass root.
	 */
	public abstract void setXhcRoot(IXholonClass xhcRoot);
	
	/**
	 * Get the Inheritance Hierarchy XholonClass root.
	 * @return The XholonClass root.
	 */
	public abstract IXholonClass getXhcRoot();
	
	/**
	 * Set the XholonService root.
	 * @param srvRoot The XholonService root.
	 */
	public abstract void setSrvRoot(IXholon srvRoot);
	
	/**
	 * Get the XholonService root.
	 * @return The XholonService root.
	 */
	public abstract IXholon getSrvRoot();
	
	/**
	 * Get the root of the Control hierarchy.
	 * @return The Control root.
	 */
	public abstract IControl getControlRoot();
	
	/**
	 * Set the Mechanism root.
	 * @param mechRoot The Mechanism root.
	 */
	public abstract void setMechRoot(IMechanism mechRoot);
	
	/**
	 * Get the Mechanism root.
	 * @return The Mechanism root.
	 */
	public abstract IMechanism getMechRoot();
	
	/**
	 * Set the default Mechanism.
	 * @param defaultMechanism The default Mechanism.
	 */
	public abstract void setDefaultMechanism(IMechanism defaultMechanism);
	
	/**
	 * Get the default Mechanism.
	 * @return The default Mechanism.
	 */
	public abstract IMechanism getDefaultMechanism();
	
	/**
	 * Get the next available id for assignment to an instance of Xholon.
	 * @return A unique ID.
	 */
	public abstract int getNextId();
	
	/**
	 * Set the next available id for assignment to an instance of Xholon.
	 * This method should ordinarily never be used.
	 * It's required if an application is loaded externally such as through Spring.
	 * @param nextId A new next id.
	 */
	public abstract void setNextId(int nextId);
	
	/**
	 * Set the next available Xholon id back to -1.
	 * This should only be called when loading a new model in, to replace an existing one.
	 */
	public abstract void resetNextId();
	
	/**
	 * Get the next available id for assignment to an instance of XholonClass.
	 * @return A unique ID.
	 */
	public abstract int getNextXholonClassId();
	
	/**
	 * Set the next available id that can be assigned to an instance of XholonClass.
	 * This should only be called by some other class, such as Xml2XholonClass,
	 * that independently increments the id.
	 * @param nextXholonClassId A unique id.
	 */
	public abstract void setNextXholonClassId(int nextXholonClassId);
	
	/**
	 * Set the next available XholonClass id back to 0.
	 * This should only be called when loading a new model in, to replace an existing one.
	 */
	public abstract void resetNextXholonClassId();
	
	/**
	 * Initialize the GUI.
	 * @param gui The JTree gui.
	 */
	public abstract void initGui(Object gui);
	
	/**
	 * Initialize the control aspects of the application.
	 */
	public abstract void initControl();
	
	/**
	 * Reset all viewers to their default values.
	 * This should be done when a new model is run after a previous one.
	 */
	public abstract void resetViewers();

	/**
	 * Initialize any View tools to be used by the application.
	 */
	public abstract void initViewers();
	
	/**
	 * Save snapshot of the xholon tree.
	 */
	public void saveSnapshot();
	
	/**
	 * Display brief information about Xholon and the application.
	 */
	public abstract void about();
	
	/**
	 * Get some text that can be used to describe what Xholon is about.
	 * Typically this text will be displayed in a Help window.
	 * @return Some text.
	 */
	public abstract String getAbout();
	
	/**
	 * Display detailed information about the application.
	 */
	public abstract void information();
	
	/**
	 * Display optional SVG (or other) image.
	 */
	public abstract void image();
	
	/**
	 * Get the model name.
	 * @return The name of this model.
	 */
	public abstract String getModelName();
	
	/**
	 * Get the name of the config file.
	 */
	public abstract String getConfigFileName();
	
	/** 
	 * Set the name of the config file.
	 */
	public abstract void setConfigFileName(String configFileName);
	
	/**
	 * Invoke the graphical tree viewer.
	 */
	public abstract IViewer invokeGraphicalTreeViewer();
	
	/**
	 * Invoke the graphical tree viewer.
	 * @param xhStart The start node whose contents will be graphed.
	 * @param graphicalNetworkViewerParams A set of comma-delimited parameters.
	 */
	public abstract IViewer invokeGraphicalTreeViewer(IXholon xhStart, String graphicalNetworkViewerParams);
	
	/**
	 * Invoke the graphical network viewer.
	 */
	public abstract IViewer invokeGraphicalNetworkViewer();
	
	/**
	 * Invoke the graphical network viewer.
	 * @param xhStart The start node whose contents will be graphed.
	 * @param graphicalNetworkViewerParams A set of comma-delimited parameters.
	 */
	public abstract IViewer invokeGraphicalNetworkViewer(IXholon xhStart, String graphicalNetworkViewerParams);
	
	/**
	 * Invoke the data plotter.
	 */
	public abstract IViewer invokeDataPlotter();
	
	/**
	 * Invoke the histogram plotter.
	 */
	public abstract IViewer invokeHistogramPlotter();
	
	/**
	 * Invoke the interaction viewer.
	 */
	public abstract IViewer invokeInteraction();
	
	/**
	 * Invoke the optional Xholon2Gui GUI generator and launcher.
	 * @param The root node in the GUI.
	 * @param The name of the output file that describes the GUI.
	 * This could be null, in which case it will generate a default name that includes a timestamp.
	 * @return The instance of IXholon2Gui that is created, or null.
	 */
	public abstract IXholon2Gui invokeXholon2Gui(IXholon guiRoot, String guiFileName);
	
	public abstract int getMaxPorts();
	public abstract int getAttributePostConfigAction();
	public abstract ITreeNodeFactory getFactory();
	public abstract IQueue getMsgQ();
	public abstract IQueue getSystemMsgQ();
	public abstract IInteraction getInteraction();
	public abstract String getIQueueImplName();
	public abstract boolean getUseInteractions();
	public abstract String getInteractionParams();
	
	/** @return Returns the maxStateMachineEntities. */
	public abstract int getMaxStateMachineEntities();

	/** @return Returns the maxActivities. */
	public abstract int getMaxActivities();

	/** @return Returns the maxXholonClasses. */
	public abstract int getMaxXholonClasses();

	/** @return Returns the maxXholons. */
	public abstract int getMaxXholons();
	
	/**
	 * Returns whether the factory type is TreeNodeFactoryDynamic.
	 * @return true (TreeNodeFactoryDynamic) or false (TreeNodeFactoryStatic)
	 */
	public abstract boolean getTreeNodeFactoryDynamic();
	
	/** @return Returns the javaClassName, the name of the concrete application class. */
	public abstract String getJavaClassName();
	
	/**
	 * Get the name of the default Java IXholon class for this application.
	 * @return A complete package name for the Java class.
	 * ex: org.primordion.xholon.tutorials.XhHelloWorld
	 */
	public abstract String getJavaXhClassName();
	
	/**
	 * Get the name of the concrete Java IXholonClass class for this application.
	 * @return A complete package name for the Java class.
	 * ex: org.primordion.xholon.base.XholonClass
	 */
	public abstract String getJavaXhClassClassName();
	
	/**
	 * Get the name of the default Java Activity class for this application.
	 * @return A complete package name for the Java class.
	 */
	public abstract String getJavaActivityClassName();
	
	/**
	 * Get this application's InheritanceHierarchy object.
	 * @return
	 */
	public abstract IInheritanceHierarchy getInherHier();
	
	/**
	 * Get whether or not to call view.act() each timeStep.
	 * @return true or false
	 */
	public abstract boolean isShouldStepView();
	
	/**
	 * Set whether or not to call view.act() each timeStep.
	 * @param shouldStepView true or false
	 */
	public abstract void setShouldStepView(boolean shouldStepView);
	
	/**
	 * Get script parameters, for use by ScriptService.
	 * @return A comma-separated String with one or more param values.
	 */
	public abstract String getScriptParams();
	
	/**
	 * Set script parameters, for use by ScriptService.
	 * @param scriptParams A comma-separated String with one or more param values.
	 */
	public abstract void setScriptParams(String scriptParams);
	
	/**
	 * Set whether or not this Xholon app is running in an Applet environment.
	 * The default value will typically be false, so if it is an applet environment,
	 * and something in the app needs to know that,
	 * then call setApplet(true) .
	 * @param applet true or false
	 */
	public void setApplet(boolean applet);
	
	/**
	 * Is this Xholon app running in an Applet environment?
	 * @return true or false
	 */
	public abstract boolean isApplet();
	
	/** @param useGridViewer The useGridViewer to set. */
	public abstract void setUseGridViewer(boolean useGridViewer);
	
	/** @param gridPanelClassName The name of the GridViewer Panel Java class. */
	public abstract void setGridPanelClassName(String gridPanelClassName);
	
	/** @param gridViewerParams GridViewer parameters. */
	public abstract void setGridViewerParams(String gridViewerParams);
	
	/**
	 * Get a grid viewer.
	 * @param vIx The index of the grid viewer.
	 * If there is only one grid viewer, it will have index 0.
	 * @return An instance of GridViewerDetails, or null.
	 * The details of the requested grid viewer may not yet have been created.
	 */
	public abstract GridViewerDetails getGridViewer(int vIx);
	/**
	 * Create a grid viewer.
	 * @param vIx An index into the GridViewerDetails gridViewer Vector.
	 * If xIx = -1, then take the last gridViewer in the Vector.
	 * @return The actual index into the gridViewer vector.
	 */
	public abstract int createGridViewer(int vIx);
	
	public boolean getUseDataPlotter();
	public boolean getUseJFreeChart();
	public boolean getUseGnuplot();
	public boolean getUseJdbc();
	public boolean getUseJpa();
	public boolean getUseGoogle();
	public boolean getUseGoogle2();
	public boolean getUseC3();
	public boolean getUseNVD3();
	public String getDataPlotterParams();
	
	public boolean getUseHistogramPlotter();
	public boolean getUseJFreeChart_Hist();
	public boolean getUseGnuplot_Hist();
	public String getHistogramPlotterParams();
	
	/** @param useDataPlotter The useDataPlotter to set. */
	public abstract void setUseDataPlotter(String useDataPlotter);
	
	/** dataPlotterParams */
	public abstract void setDataPlotterParams(String dataPlotterParams);
	
	/** @param useHistogramPlotter The useHistogramPlotter to set. */
	public abstract void setUseHistogramPlotter(String useHistogramPlotter);
	
	public abstract void setUseInteractions(boolean useInteractions);
	
	public abstract void setInteractionParams(String interactionParams);
	
	/**
	 * Create a JFreeChart. GnuPlot, or other chart.
	 * @param chartRoot The root of the subtree that will provide values for the chart.
	 * If this value is null, then the model root will be used.
	 */
	public abstract void createChart(IXholon chartRoot);
	
	/**
	 * Enable the creation of interactions while the app is running.
	 * The cached interactions can later be used to create a sequence diagram.
	 */
	public abstract void createInteractions();
	
	/** @return Returns the maxProcessLoops. */
	public abstract int getMaxProcessLoops();
	
	/** @param maxProcessLoops The maxProcessLoops to set. */
	public abstract void setMaxProcessLoops(int maxProcessLoops);
	
	/**
	 * Get the time step interval defined for this application.
	 * @return Time step interval in ms.
	 */
	public abstract int getTimeStepInterval();
	
	/**
	 * Set time step interval defined for this application.
	 * @param timeStepInterval The time step interval in ms.
	 */
	public abstract void setTimeStepInterval(int timeStepInterval);
	
	/** @return Returns the classDetailsFile. */
	public abstract String getClassDetailsFile();

	/** @return Returns the compositeStructureHierarchyFile. */
	public abstract String getCompositeStructureHierarchyFile();

	/** @return Returns the inheritanceHierarchyFile. */
	public abstract String getInheritanceHierarchyFile();
	
	/** @return Returns the informationFile. */
	public abstract String getInformationFile();
	
	/** @return Returns the imageFile. */
	public abstract String getImageFile();
	
	/** @param classDetailsFile The classDetailsFile to set. */
	public abstract void setClassDetailsFile(String classDetailsFile);

	/** @param compositeStructureHierarchyFile The compositeStructureHierarchyFile to set. */
	public abstract void setCompositeStructureHierarchyFile(String compositeStructureHierarchyFile);

	/** @param inheritanceHierarchyFile The inheritanceHierarchyFile to set. */
	public abstract void setInheritanceHierarchyFile(String inheritanceHierarchyFile);
	
	/** @param informationFile The informationFile to set. */
	public abstract void setInformationFile(String informationFile);
	
	/** @param imageFile The imageFile to set. */
	public abstract void setImageFile(String imageFile);

	/**
	 * Get the default writer for use with Xholon.print() and Xholon.println().
	 * @return An instance of some subclass of Writer.
	 */
	//public abstract Writer getOut();

	/**
	 * Set the default writer for use with Xholon.print() and Xholon.println().
	 * @param out An instance of some subclass of Writer.
	 */
	//public abstract void setOut(Writer out);
	
	/**
	 * Run this instance of IApplication.
	 * This is common code that can be executed no matter what thread, or how many threads,
	 * the application is using.
	 */
	public abstract void runApp();
	
	/**
	 * Whether or not to use Application.out when exporting to external formats,
	 * and in other situations where data might be instead written to a file.
	 * @return
	 */
	public abstract boolean isUseAppOut();

	/**
	 * Whether or not to use Application.out when exporting to external formats,
	 * and in other situations where data might be instead written to a file.
	 * @param useAppOut true or false
	 */
	public abstract void setUseAppOut(boolean useAppOut);
	
	/**
	 * Is the app is running in a Google Web Toolkit (GWT) environment.
	 */
	public abstract boolean isUseGwt();

	/**
	 * Set whether or not the app is running in a Google Web Toolkit (GWT) environment.
	 */
	public abstract void setUseGwt(boolean useGwt);
	
	/**
	 * Make an app-specific IXholon node.
	 * This is especially intended for apps running in a Google Web Toolkit (GWT) environment.
	 * Nodes are created using new.
	 * To be useful, this method must be overriden in a subclass of Application.
	 * Example of usage:
	 * <pre>IXholon node = this.getApp().makeAppSpecificNode("org.primordion.user.app.abc.MyAbc");</pre>
	 * @param implName The full name of an IXholon Java class (ex: "org.primordion.user.app.abc.MyAbc").
	 * @return An instance of an IXholon Java class (ex: MyAbc).
	 */
	public abstract IXholon makeAppSpecificNode(String implName);
	
	/**
	 * Find an app-specific class that corresponds to a class name.
	 * This is especially intended for apps running in a Google Web Toolkit (GWT) environment.
	 * @param className (ex: "org.primordion.user.app.helloworld.XhHelloWorld")
	 * @return A Java Class object, or null.
	 */
	public abstract Class<IXholon> findAppSpecificClass(String className);
	
	/**
	 * Find a class Integer constant, given the name of the constant.
	 * This is especially intended for apps running in a Google Web Toolkit (GWT) environment.
	 * This is especially intended to be called by Reflection,
	 * to get the value of a port index constant.
	 * @param clazz The class that contains the constant.
	 * @param constName (ex: "P_SM_SUB2").
	 * @return (ex: 1) If no match can be found, it returns Integer.MIN_VALUE.
	 */
	public abstract int findAppSpecificConstantValue(Class<?> clazz, String constName);
	
	/**
	 * Get the value of an Object attribute, using a getter method.
	 * This is especially intended for apps running in a Google Web Toolkit (GWT) environment.
	 * This is currently used to find a port with name other than "port",
	 * and is called by IReflection.
	 * @param node An IXholon instance that has a port.
	 * @param clazz  The node's Java class.
	 * @param attrName The name of an IXholon port.
	 * @return The IXholon that this port references.
	 */
	public abstract IXholon getAppSpecificObjectVal(IXholon node, Class<IXholon> clazz, String attrName);
	
	/**
	 * Set the value of an Object attribute, using a setter method.
	 * This is especially intended for apps running in a Google Web Toolkit (GWT) environment.
	 * This is currently used to configure a port with name other than "port",
	 * and is called by IReflection.
	 * @param node An IXholon instance that has a port.
	 * @param clazz  The node's Java class.
	 * @param attrName The name of an IXholon port.
	 * @param val The IXholon that this port references.
	 * @return Whether or not the attribute could be set.
	 */
	public abstract boolean setAppSpecificObjectVal(IXholon node, Class<IXholon> clazz,
	    String attrName, IXholon val);
	
	/**
	 * Get a list of port names for a node.
	 * This is especially intended for apps running in a Google Web Toolkit (GWT) environment.
	 * @param node An IXholon instance that may have ports.
	 * @param clazz  The node's Java class.
	 * @return A comma-delimited String containing a list of port names (ex: "abc,def,ghi").
	 */
	public abstract String getAppSpecificObjectValNames(IXholon node, Class<IXholon> clazz);
	
	/**
	 * keep it consistent with getAllPorts, or return an array ?
	 * Get a list of ports for a node.
	 * This is especially intended for apps running in a Google Web Toolkit (GWT) environment.
	 * @param node An IXholon instance that may have ports.
	 * @param clazz  The node's Java class.
	 * @return A list of PortInformation objects, or null.
	 */
	public abstract List getAppSpecificObjectVals(IXholon node, Class<IXholon> clazz);
	
	/**
	 * Determine whether a specified class name could be instantiated using
	 * Class.forName(implName) which is not available in GWT.
	 * This is especially intended for apps running in a Google Web Toolkit (GWT) environment.
	 * @param implName (ex: "org.primordion.user.app.climatechange.carboncycle03.Photosynthesis")
	 * @return true or false
	 */
	public abstract boolean isAppSpecificClassFindable(String implName);
	
	/**
	 * Get the value of an application-specific non-port attribute.
	 * This is especially intended for apps running in a Google Web Toolkit (GWT) environment.
	 * @param node The instance of clazz that contains the attribute.
	 * @param clazz The node's Java class, which must follow the Java bean getter/setter convention.
	 * The class is required so this method can optionally recursively call itself
	 * to search a chain of superclasses for AttrName.
	 * The recursion will only find superclasses that are in the same package as the implementation class.
	 * @param attrName The name of the attribute (ex: "state", to be accessed using getState()).
	 * @return The value of the attribute (or null).
	 * If the attribute is a Java primitive (boolean byte char float int long short),
	 * it will be returned as the corresponding Object type
	 * (Boolean Byte Character Float Integer Long Short).
	 */
	public abstract Object getAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName);
	
	/**
	 * Set the value of an application-specific non-port attribute.
	 * This is especially intended for apps running in a Google Web Toolkit (GWT) environment.
	 * @param node The instance of clazz that contains the attribute.
	 * @param clazz The node's Java class, which must follow the Java bean getter/setter convention.
	 * The class is required so this method can optionally recursively call itself
	 * to search a chain of superclasses for attrName.
	 * The recursion will only find superclasses that are in the same package as the implementation class.
	 * @param attrName  The name of the attribute (ex: "state", to be accessed using setState(attrVal)).
	 * @param attrVal The intended new value of the attribute (or null).
	 * If the attribute is a Java primitive (boolean byte char float int long short),
	 * it will be autoboxed as the corresponding Object type
	 * (Boolean Byte Character Float Integer Long Short).
	 */
	public abstract void setAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName, Object attrVal);
	
	/**
	 * Get all application-specific attributes that are accessible using a method that starts with "get" or "is".
	 * Only those get methods that have no input parameters are handled.
	 * ex: getState()
	 * @param node The instance of clazz that contains the attribute.
	 * @param clazz The node's Java class, which must follow the Java bean getter/setter convention.
	 * The class is required so this method can optionally recursively call itself
	 * to search a chain of superclasses for attributes.
	 * The recursion will only find superclasses that are in the same package as the implementation class.
	 * @param returnAll Whether or not to return all attributes, rather than only those of immediate class.
	 * This determines whether or not this method will search recursively for attributes.
	 * NO @param returnStatics Whether or not attributes returned by static methods should be returned.
	 * NO @param returnIfUnPaired Whether or not unpaired attributes will be returned.
	 * NO An unpaired attribute has a getter (getAbc() isAbc()) but no matching setter (setAbc(...)).
	 * @return A three-dimensional array with a variable number of rows,
	 * each containing a Name/Value/Type triplet.
	 * @see IReflection.getAppSpecificAttributes
	 */
	public abstract Object[][] getAppSpecificAttributes(IXholon node, Class<IXholon> clazz, boolean returnAll);
	
	/**
	 * Determine if a clazz contains an application-specific non-port attribute.
	 * This is especially intended for apps running in a Google Web Toolkit (GWT) environment.
	 * @param node The instance of clazz that contains the attribute.
	 * @param clazz The node's Java class, which must follow the Java bean getter/setter convention.
	 * The class is required so this method can optionally recursively call itself
	 * to search a chain of superclasses for attrName.
	 * The recursion will only find superclasses that are in the same package as the implementation class.
	 * @param attrName  The name of the attribute (ex: "state", to be accessed using setState(attrVal)).
	 * @return true or false
	 */
	public abstract boolean isAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName);
	
  /**
	 * Set the value of an Object attribute (port array), using a setter method.
	 * This is especially intended for apps running in a Google Web Toolkit (GWT) environment.
	 * This is currently used to configure a port with name other than "port",
	 * and is called by IReflection.
	 * @param node An IXholon instance that has a port array.
	 * @param clazz  The node's Java class.
	 * @param attrName The name of an IXholon port array.
	 * @param index An array index, which must be less than the size of the port array.
	 * @param val The IXholon that this port references.
	 * @return Whether or not the attribute could be set.
	 */
	public abstract boolean setAppSpecificObjectArrayVal(IXholon node, Class<IXholon> clazz,
	    String attrName, int index, IXholon val);
	
	/**
	 * Return an instance of com.google.gwt.resources.client.ClientBundleWithLookup .
	 * Typically, in a GWT environment, it contains the app-apecific config content (_xhn ih cd csh).
	 * @return An instance of ClientBundleWithLookup, or null.
	 */
	public abstract Object findGwtClientBundle();
	
	public abstract String getWorkbookId();
	public abstract void setWorkbookId(String workbookId);
	public abstract String getWorkbookFileName();
	public abstract void setWorkbookFileName(String workbookFileName);
	public abstract IXholon getWorkbookBundle();
	public abstract void setWorkbookBundle(IXholon workbookBundle);
	public abstract boolean loadWorkbook();
	public abstract void wbCallback(String data);
	
	/**
	 * Retrieve resource content from RCConfig.
	 * @param The name of the resource in RCConfig.
	 * @return The resource content.
	 */
	public abstract String rcConfig(String resourceName);
	
	/**
	 * Retrieve resource content from an app-specific resources class.
	 * @param The name of the resource in the resources class.
	 * @param An instance of the resources class.
	 * TODO remove dependency on ClientBundleWithLookup
	 * @return The resource content.
	 */
	public abstract String rcConfig(String resourceName, Object resources);
	
	/**
	 * Whether or not users are allowed to configure a Service or ExternalFormat writer,
	 * just before the service or writer starts to do its things.
	 * return true or false
	 */
	public abstract boolean isAllowConfigSrv();
	
	/**
	 * Whether or not to allow users to configure a Service or ExternalFormat writer,
	 * just before the service or writer starts to do its things.
	 * @param allowConfigSrv true or false
	 */
	public abstract void setAllowConfigSrv(boolean allowConfigSrv);
	
	public abstract IGraphicalTreeViewer getTreeViewerJung();

	public abstract IGraphicalNetworkViewer getNetworkViewerJung();

	public abstract IChartViewer getChartViewer();

	public abstract IHistogramViewer getHistogramViewer();
	
	public abstract void setXincludePath(String xincludePath);
	public abstract String getXincludePath();
	
	/**
	 * Cache the value of GWT.getHostPageBaseURL().
	 * @param hostPageBaseURL
	 */
	public abstract void setHostPageBaseURL(String hostPageBaseURL);
	
	/**
	 * Return the cached value of GWT.getHostPageBaseURL().
	 * @return
	 */
	public abstract String getHostPageBaseURL();
	
}
