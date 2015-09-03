/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2009 Ken Webb
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

import org.primordion.xholon.base.ISignal;

/**
 * An interface that contains the names of Xholon internal services.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 31, 2009)
 */
public interface IXholonService {
	
	// The names of XholonService objects.
	public static final String XHSRV_SERVICE_LOCATOR = "ServiceLocatorService";
	//
	public static final String XHSRV_ABOUT = "AboutService";
	public static final String XHSRV_BERKELEY_DB = "BerkeleyDbService";
	public static final String XHSRV_BLACKBOARD = "BlackboardService";
	public static final String XHSRV_BLUEPRINTS = "BlueprintsService";
	public static final String XHSRV_CHART_VIEWER = "ChartViewerService";
	public static final String XHSRV_CSS = "CssService";
	public static final String XHSRV_DATABASE = "DatabaseService";
	public static final String XHSRV_DUMMY = "DummyService";
	public static final String XHSRV_EXTERNAL_FORMAT = "ExternalFormatService";
	public static final String XHSRV_GAME_ENGINE = "GameEngineService";
	public static final String XHSRV_GIST = "GistService";
	public static final String XHSRV_GRAPHICAL_NETWORK_VIEWER = "GraphicalNetworkViewerService";
	public static final String XHSRV_GRAPHICAL_TREE_VIEWER = "GraphicalTreeViewerService";
	public static final String XHSRV_GRID_VIEWER = "GridViewerService";
	public static final String XHSRV_HISTOGRAM = "HistogramService";
	public static final String XHSRV_JAVA_SERIALIZATION = "JavaSerializationService";
	public static final String XHSRV_JAXEN = "JaxenService";
	public static final String XHSRV_JCR = "JcrService";
	public static final String XHSRV_JXPATH = "JXPathService";
	public static final String XHSRV_LOG = "LogService";
	public static final String XHSRV_MATHSCIENG = "MathSciEngService";
	public static final String XHSRV_MEDIA = "MediaService";
	public static final String XHSRV_METEOR_PLATFORM = "MeteorPlatformService";
	public static final String XHSRV_NETLOGO = "NetLogoService";
	public static final String XHSRV_NODE_SELECTION = "NodeSelectionService";
	public static final String XHSRV_NOSQL = "NoSqlService";
	public static final String XHSRV_PCSPATH = "PcsPathService";
	public static final String XHSRV_PRE_CONFIGURATION = "PreConfigurationService";
	public static final String XHSRV_REFLECTION = "ReflectionService";
	public static final String XHSRV_REST = "RestService";
	public static final String XHSRV_REMOTE_NODE = "RemoteNodeService";
	public static final String XHSRV_SCRIPT = "ScriptService";
	public static final String XHSRV_SEARCH_ENGINE = "SearchEngineService";
	public static final String XHSRV_RECORD_PLAYBACK = "RecordPlaybackService";
	public static final String XHSRV_SBML = "SbmlService";
	public static final String XHSRV_SEMANTIC_WEB = "SemanticWebService";
	public static final String XHSRV_SPRING_FRAMEWORK = "SpringFrameworkService";
	public static final String XHSRV_SVG_VIEW = "SvgViewService";
	public static final String XHSRV_TEXT_TREE_VIEWER = "TextTreeViewerService";
	public static final String XHSRV_TIMELINE = "TimelineService";
	public static final String XHSRV_VALIDATION = "ValidationService";
	public static final String XHSRV_VFS = "VfsService";
	public static final String XHSRV_VRML = "VrmlService";
	public static final String XHSRV_WIRING = "WiringService";
	public static final String XHSRV_XHOLON2XML = "Xholon2XmlService";
	public static final String XHSRV_XHOLON_CREATION = "XholonCreationService";
	public static final String XHSRV_XHOLON_DIRECTORY = "XholonDirectoryService";
	public static final String XHSRV_XHOLON_HELPER = "XholonHelperService";
	public static final String XHSRV_XML_DATABASE = "XmlDatabaseService";
	public static final String XHSRV_XML_READER = "XmlReaderService";
	public static final String XHSRV_XML_VALIDATION = "XmlValidationService";
	public static final String XHSRV_XML_WRITER = "XmlWriterService";
	public static final String XHSRV_XML2XHOLON = "Xml2XholonService";
	public static final String XHSRV_XPATH = "XPathService";
	public static final String XHSRV_XQUERY = "XQueryService";
	
	// signals
	/** Request the processing that this service normally does. */
	public static final int SIG_PROCESS_REQUEST = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 1;
	
	/** Request that the service add a new capability. */
	public static final int SIG_ADD_REQUEST = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 2;
	
	/** Respond to a request, optionally with returned data. */
	public static final int SIG_RESPONSE = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 91;
	
	/** Respond to a request, by reporting an error. */
	public static final int SIG_RESPONSE_ERROR = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 92;
	
}
