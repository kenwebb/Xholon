/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2011 Ken Webb
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

package org.primordion.xholon.service.svg;

import org.primordion.xholon.base.ISignal;

/**
* Signals and parameters for use with SVG service and client classes.
* @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
* @since 0.8.1 (Created on October 10, 2011)
*/
public interface ISvgView {
	
	/**
	 * Load and setup an SVG image.
	 */
	public static final int SIG_SETUP_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 101;
	
	/**
	 * Setup the SVG URI.
	 * This is typically used to send an SVG String as a data URI. For example:
	 * <pre>
	 * data:image/svg+xml,&lt;svg xmlns="http://www.w3.org/2000/svg">&lt;circle r="50"/>&lt;/svg>
	 * </pre>
	 */
	public static final int SIG_SETUP_SVGURI_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 102;
	
	/**
	 * Display an SVG image, by adding it to a Swing JPanel and JFrame.
	 */
	public static final int SIG_DISPLAY_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 103;
	
	/**
	 * Act on an SVG image.
	 */
	public static final int SIG_ACT_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 104;
	
	/**
	 * Make new text in an SVG image.
	 * This is primarily used to make new text before an image is first displayed.
	 * The msg.data needs to include the text and the x and y position. ex:
	 * <p>"This early version is incomplete and inaccurate.,5.0,575.0"</p>
	 */
	public static final int SIG_MAKETEXT_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 105;
	
	/**
	 * Style an element in an SVG image.
	 * data: anId,styleName,styleValue
	 *   ex: <p>rect123,fill,#ff0000</p>
	 */
	public static final int SIG_STYLE_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 106;
	
	/**
	 * Set an XML attribute of an element in an SVG image.
	 * data: anId,styleName,styleValue
	 *   ex: <p>Ball,cx,123.4</p>
	 */
	public static final int SIG_XMLATTR_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 107;
	
	/**
	 * svgAttributesUri
	 */
	public static final int SIG_SETUP_SVGATTRIBUTESURI_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 108;
	
	/**
	 * addViewBehavior
	 */
	public static final int SIG_ADD_VIEWBEHAVIOR_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 109;
	
	/**
	 * The service informs the client that the SVG image has finished loading.
	 */
	public static final int SIG_STATUS_OK_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 301;
	
	/**
	 * The service informs the client that the SVG image could not be loaded.
	 */
	public static final int SIG_STATUS_NOT_OK_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 302;
	
	/**
	 * Process response.
	 */
	public static final int SIG_PROCESS_RSP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 201;
	
	/**
	 * Use the default model name, as defined in _xhn.xml.
	 * This value can be passed as part of the data in a SIG_SETUP_REQ message.
	 */
	public static final String MODELNAME_DEFAULT = "${MODELNAME_DEFAULT}";
	
	/**
	 * Use the default SVG image.
	 * This is a file in the same directory as the app, called default.svg.
	 */
	public static final String SVGURI_DEFAULT = "${SVGURI_DEFAULT}";
	
	/**
	 * Use the default fallback.
	 */
	public static final String SVGURIFALLBACK_DEFAULT = "";
	
	/**
	 * Use the default internationalization.
	 * Add the language code after this, ex: ${I18NURI_DEFAULT}en
	 * This will be a file in the i18n directory, called Labels_en.xml .
	 */
	public static final String I18NURI_DEFAULT = "${I18NURI_DEFAULT}";
	
	/**
	 * Use the default root for SVG viewables.
	 * The default root is the root node in the model.
	 */
	public static final String VIEWABLESROOT_DEFAULT = "./";
	
	/**
	 * Use the default SVG viewables URI.
	 * This is a file in the same directory as the app, called SvgViewables.xml.
	 */
	public static final String VIEWABLESURI_DEFAULT = "${VIEWABLESURI_DEFAULT}";
	
	/**
	 * Create SvgViewable nodes by scanning the SVG content for tspan and text nodes
	 * whose id references a node in the Xholon model.
	 */
	public static final String VIEWABLES_CREATE = "${VIEWABLES_CREATE}";
	
	/**
	 * Use all defaults.
	 */
	public static final String SVGALL_DEFAULTS = MODELNAME_DEFAULT + "," + SVGURI_DEFAULT;
	
	public static final String DATA_URI_SCHEME = "data:";
	public static final String SVG_MIME_TYPE = "image/svg+xml";
	/**
	 * Data URI header for inline SVG in a String.
	 */
	public static final String SVG_DATA_URI = DATA_URI_SCHEME + SVG_MIME_TYPE + ",";

/* SVG setup parameters (SIG_SETUP_REQ msg)
 * --------------------
0 modelName: used as title of JFrame
1 svgUri: SVG image URI (.svg)
2 svgUriFallback: fallback SVG image URI (.svg) (optional)
3 i18nUri: internationalization URI (.xml) (optional)
4 viewablesRoot: XPath to root viewables node, where the context is assumed to be the model root (optional)
5 viewablesUri: model viewables URI
  - correspondence between nodes in the model (the viewables), and nodes in the SVG view (.xml) (optional)
6 format: String.format(); and/or allow separate format for each item in SvgViewables.xml (optional)
7 shouldInitNumericText: should all numeric text be set to 0 at start? true or false (optional)

examples:
"Model Name,svgUri.svg,svgUriFallback.svg,i18nUri.xml,viewablesRoot,viewablesUri.xml,format"
"Model Name,svgUri.svg,,i18nUri.xml,viewablesRoot,viewablesUri.xml"
"Energy Budget,http://upload.wikimedia.org/wikipedia/commons/d/d2/Sun_climate_system_alternative_(German)_2008.svg" */
}
