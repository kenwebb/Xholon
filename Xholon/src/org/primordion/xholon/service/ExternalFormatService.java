/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2010 Ken Webb
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
package org.primordion.xholon.service;

//import java.io.File;
//import java.io.IOException;
//import java.io.Writer;
//import java.net.URL;
//import java.security.AccessControlException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
/**
 * External Format Service.
 * Typically this class provides access to Model-to-Text (m2t) transformations.
 * It can provide a list of available external formats,
 * and can write a Xholon subtree in a selected format.
 * To add a new transformer, write a class that implements IXholon2ExternalFormat,
 * give the new class a name that starts with "Xholon2",
 * and place the new class in this package (org.primordion.ef).
 * <p>TODO this should probably be a XholonService</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on April 24, 2010)
 */
@SuppressWarnings("serial")
public class ExternalFormatService extends Xholon {

	/**
	 * Default action names to use, in case the format class file names are unavailable,
	 * such as when running under Java Web Start (JNLP).
	 */
	protected static final String[] DEFAULT_ACTION_NAMES = {
		"CherryTree",
		"Csv",
		"GraphML",
		"Graphviz",
		"HTModL",
		"HtmlForm",
		"Json",
		"MindMap",
		"PlantUML",
		"PlantUML_Composite",
		"Sbml",
		"SnapshotXml",
		"SnapshotYaml",
		"Svg",
		"Xml",
		"XmlIh",
		"Yaml",
		"Yuml",
		"_other,Extravis,Gui,JTreeMap,JsTree,ManyEyes,Newick,Nwb,Pajek,Properties,TextTree,TreeML,Umple,Xgmml",
		"_petrinet,Graphviz4PetriNets,MindMap4PetriNets,Pnml,PnmlDocCheck,PnmlVanted,Sbgn4PetriNets,Sbml4PetriNets,Vensim4PetriNets",
		"_xholon,Cd,Csh,Ih,Xhn",
		"_serialize,XStream"
	};
	
	/**
	 * Cached action names.
	 */
	protected String[] actionNames = null;
	
	/**
	 * Possible subdirectories.
	 */
	protected String[] subdirNames = {"other","xholon","serialize","petrinet"};
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getActionList()
	 */
	public String[] getActionList() {
		if (actionNames == null) {
			//actionNames = getActionNames(this.getClass().getPackage().getName());
		}
		return actionNames;
	}
	
	/**
	 * Get an array of action names, from the names of classes in this package.
	 * @param packageName The fully-qualified name of a package.
	 * ex: "org.primordion.ef"
	 * @return An array of action names, or null.
	 */
	/* GWT
	protected String[] getActionNames(String packageName) {
		List<String> actionNames = new ArrayList<String>();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			return DEFAULT_ACTION_NAMES;
		}
		String path = packageName.replace('.', '/');
		
		URL resource = classLoader.getResource(path);
		if (resource == null) {
			return DEFAULT_ACTION_NAMES;
		}
		File dir = new File(resource.getFile());
		try {
			if (dir.exists()) {
				String[] files = dir.list();
				for (int i = 0; i < files.length; i++) {
					// only include .class files that start with "Xholon2"
					if ((files[i].startsWith("Xholon2")) && (files[i].endsWith(".class"))) {
						String actionName = files[i].substring(7, files[i].length() - 6);
						// exclude inner classes that contain the '$' character
						if (actionName.indexOf('$') == -1) {
							actionNames.add(actionName);
						}
					}
					else if (!files[i].endsWith(".class")) {
						// this must be a directory
						String[] subFiles = getActionNames(packageName + "." + files[i]);
						String subFilesStr = "_" + files[i];
						for (int j = 0; j < subFiles.length; j++) {
							subFilesStr += "," + subFiles[j];
						}
						actionNames.add(subFilesStr);
					}
				}
			}
		} catch(AccessControlException e) {
			return DEFAULT_ACTION_NAMES;
		}
		if (actionNames.size() == 0) {
			return DEFAULT_ACTION_NAMES;
		}
		Collections.sort(actionNames);
		
		// print a list I can use in DEFAULT_ACTION_NAMES
		//java.util.Iterator it = actionNames.iterator();
		//while (it.hasNext()) {
		//	System.out.println("\"" + it.next() + "\",");
		//}
		
		return (String[])actionNames.toArray(new String[actionNames.size()]);
	}*/
	
	/**
	 * Initialize an external format writer,
	 * such as for one of the Network Workbench (NWB) supported formats.
	 * @param node The root node of the Xholon subtree to be written out.
	 * @param formatName The name of an external format (ex: TreeML).
	 * This is used as part of the Java class name.
	 * ex: TreeML --> org.primordion.ef.Xholon2TreeML
	 */
	public IXholon2ExternalFormat initExternalFormatWriter(IXholon node, String formatName)
	{
		String modelName = "";
		IApplication app = node.getApp();
		if (app != null) {
			modelName = app.getModelName();
		}
		/* GWT
		String subdirName = "";
		if (formatName.startsWith("_")) {
			String[] fileItem = formatName.substring(1).split(",");
			subdirName = fileItem[0] + ".";
			formatName = fileItem[1];
		}*/
		IXholon2ExternalFormat xholon2ef = null;
		
		if (formatName == null) {return null;}

		// examples of generated code
		else if ("Xholon2HTModL".equals(formatName)) {
		  //xholon2ef = new org.primordion.ef.Xholon2HTModL();
		}
		else if ("_other,Xholon2ChapNetwork".equals(formatName)) {
		  //xholon2ef = new org.primordion.ef.other.Xholon2ChapNetwork();
		}
		
	  if (xholon2ef.initialize(null, modelName, node)) {
			return xholon2ef;
		}
		else {
			writeUserErrorMessage(node, formatName, "Unable to initialize.");
		}
		
		/* GWT
		try {
			Class<IXholon2ExternalFormat> clazz = doForName(node, subdirName, formatName);
			if (clazz == null) {return null;}
			xholon2ef = clazz.newInstance();
			if (xholon2ef.initialize(null, modelName, node)) {
				return xholon2ef;
			}
			else {
				writeUserErrorMessage(node, formatName, "Unable to initialize.");
			}
		} catch (InstantiationException excp) {
			String m = "Unable to instantiate Xholon2" + formatName + ".";
			writeLogErrorMessage(m, excp);
			writeUserErrorMessage(node, formatName, m);
		} catch (IllegalAccessException excp) {
			String m = "Unable to access Xholon2" + formatName + ".";
			writeLogErrorMessage(m, excp);
			writeUserErrorMessage(node, formatName, m);
		} catch (NoClassDefFoundError excp) {
			String m = "Unable to find Xholon2" + formatName + ".";
			writeLogErrorMessage(m, excp);
			writeUserErrorMessage(node, formatName, m);
		} finally {}
		*/
		return null;
	}
	
	/**
	 * Write an IXholon subtree in an external format.
	 * @param xholon2ef An initialized and configured external format writer.
	 */
	public void writeAll(IXholon2ExternalFormat xholon2ef) {
		if (xholon2ef == null) {return;}
		xholon2ef.writeAll();
	}

	/**
	 * Try to find a class that matches the input name.
	 * @param node
	 * @param subdirName
	 * @param formatName
	 * @return A class, or null.
	 */
  /* GWT
	@SuppressWarnings("unchecked")
	protected Class<IXholon2ExternalFormat> doForName(IXholon node, String subdirName, String formatName) {
		Class<IXholon2ExternalFormat> clazz = null;
		try {
			clazz = (Class<IXholon2ExternalFormat>)Class.forName(
					"org.primordion.ef." + subdirName + "Xholon2" + formatName);
		} catch (ClassNotFoundException excp) {}
		if (clazz == null) {
			// try the sub-directories
			int len = subdirNames.length;
			for (int i = 0; i < len; i++) {
				try {
					clazz = (Class<IXholon2ExternalFormat>)Class.forName(
							"org.primordion.ef." + subdirNames[i] + ".Xholon2" + formatName);
				} catch (ClassNotFoundException excp) {
					if (len == i+1) {
						String m = "Unable to load Xholon2" + formatName + ".";
						writeLogErrorMessage(m, excp);
						writeUserErrorMessage(node, formatName, m);
					}
				}
				if (clazz != null) {break;}
			}
		}
		return clazz;
	}*/
	
	/**
	 * Write an error message for the user.
	 * @param node
	 * @param formatName
	 * @param errorMsg
	 */
	protected void writeUserErrorMessage(IXholon node, String formatName, String errorMsg) {
	  /* GWT
		Writer out = node.getApp().getOut();
		try {
			out.write("This application cannot be saved in the " + formatName + " external format. ");
			out.write(errorMsg);
			out.flush();
		} catch (IOException e) {
			Xholon.getLogger().error("", e);
		}*/
		StringBuilder sb = new StringBuilder()
		.append("This application cannot be saved in the ")
		.append(formatName)
		.append(" external format. ")
		.append(errorMsg);
		node.println(sb.toString());
	}
	
	/**
	 * Write an error message to the log.
	 * @param errorMsg
	 * @param e
	 */
	protected void writeLogErrorMessage(String errorMsg, Throwable e) {
		Xholon.getLogger().error(errorMsg, e);
	}
	
	/**
	 * test
	 * @param args
	 */
	 /* GWT
	public static void main(String[] args) {
		ExternalFormatService efs = new ExternalFormatService();
		String[] actionList = efs.getActionList();
		for (int i = 0; i < actionList.length; i++) {
			System.out.println(actionList[i]);
		}
	}*/

}
