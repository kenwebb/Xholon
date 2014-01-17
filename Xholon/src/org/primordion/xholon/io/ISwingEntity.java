/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2008 Ken Webb
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

package org.primordion.xholon.io;

import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;

/**
 * Create a subtree of Swing components, and react to Swing events.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on January 5, 2008)
 */
public interface ISwingEntity extends IXholon {
	
	/* Correspondences between Swing and Xholon.
	Message.signal - identifies the type of EventObject
	Message.sender - the Xholon SwingEntity that contains the EventObject.source
	Message.data   - some additional data provided by the EventObject
	*/
	
	// Swing Signals, Events, based on subclasses of the java.util.EventObject and java.awt.AWTEvent class.
	// These are messages that are sent from an instance of SwingEntity an instance of some other Xholon class.
	public static final int SWIG_ACTION_EVENT = ISignal.SWIG_START -  1;
	//
	public static final int SWIG_ANCESTOR_EVENT_ADDED   = ISignal.SWIG_START -  2;
	public static final int SWIG_ANCESTOR_EVENT_MOVED   = ISignal.SWIG_START -  3;
	public static final int SWIG_ANCESTOR_EVENT_REMOVED = ISignal.SWIG_START -  4;
	//
	public static final int SWIG_CARET_EVENT            = ISignal.SWIG_START -  5;
	//
	public static final int SWIG_CHANGE_EVENT_EDITINGSTOPPED   = ISignal.SWIG_START -  6;
	public static final int SWIG_CHANGE_EVENT_EDITING_CANCELED = ISignal.SWIG_START -  7;
	public static final int SWIG_CHANGE_EVENT_STATECHANGED     = ISignal.SWIG_START -  8;
	//
	public static final int SWIG_COMPONENT_EVENT_HIDDEN  = ISignal.SWIG_START -   9;
	public static final int SWIG_COMPONENT_EVENT_MOVED   = ISignal.SWIG_START -  10;
	public static final int SWIG_COMPONENT_EVENT_RESIZED = ISignal.SWIG_START -  11;
	public static final int SWIG_COMPONENT_EVENT_SHOWN   = ISignal.SWIG_START -  12;
	//
	public static final int SWIG_CONTAINER_EVENT_ADDED   = ISignal.SWIG_START -  13;
	public static final int SWIG_CONTAINER_EVENT_REMOVED = ISignal.SWIG_START -  14;
	//
	public static final int SWIG_DOCUMENT_EVENT_CHANGED = ISignal.SWIG_START -  15;
	public static final int SWIG_DOCUMENT_EVENT_INSERT  = ISignal.SWIG_START -  16;
	public static final int SWIG_DOCUMENT_EVENT_REMOVE  = ISignal.SWIG_START -  17;
	//
	public static final int SWIG_EXCEPTION_EVENT = ISignal.SWIG_START -  18;
	//
	public static final int SWIG_FOCUS_EVENT_GAINED = ISignal.SWIG_START -  19;
	public static final int SWIG_FOCUS_EVENT_LOST   = ISignal.SWIG_START -  20;
	//
	public static final int SWIG_HIERARCHY_EVENT_ANCESTORMOVED    = ISignal.SWIG_START -  21;
	public static final int SWIG_HIERARCHY_EVENT_ANCESTORRESIZED  = ISignal.SWIG_START -  22;
	public static final int SWIG_HIERARCHY_EVENT_HIERARCHYCHANGED = ISignal.SWIG_START -  23;
	//
	public static final int SWIG_HYPERLINK_EVENT = ISignal.SWIG_START - 24;
	//
	public static final int SWIG_INPUTMETHOD_EVENT_CARETPOSITIONCHANGED = ISignal.SWIG_START - 25;
	public static final int SWIG_INPUTMETHOD_EVENT_TEXTCHANGED          = ISignal.SWIG_START - 26;
	//
	public static final int SWIG_INTERNALFRAME_EVENT_ACTIVATED   = ISignal.SWIG_START - 27;
	public static final int SWIG_INTERNALFRAME_EVENT_CLOSED      = ISignal.SWIG_START - 28;
	public static final int SWIG_INTERNALFRAME_EVENT_CLOSING     = ISignal.SWIG_START - 29;
	public static final int SWIG_INTERNALFRAME_EVENT_DEACTIVATED = ISignal.SWIG_START - 30;
	public static final int SWIG_INTERNALFRAME_EVENT_DEICONIFIED = ISignal.SWIG_START - 31;
	public static final int SWIG_INTERNALFRAME_EVENT_ICONIFIED   = ISignal.SWIG_START - 32;
	public static final int SWIG_INTERNALFRAME_EVENT_OPENED      = ISignal.SWIG_START - 33;
	//
	public static final int SWIG_ITEM_EVENT = ISignal.SWIG_START - 34;
	//
	public static final int SWIG_KEY_EVENT_PRESSED  = ISignal.SWIG_START - 35;
	public static final int SWIG_KEY_EVENT_RELEASED = ISignal.SWIG_START - 36;
	public static final int SWIG_KEY_EVENT_TYPED    = ISignal.SWIG_START - 37;
	//
	public static final int SWIG_LISTDATA_EVENT_CONTENTSCHANGED = ISignal.SWIG_START - 38;
	public static final int SWIG_LISTDATA_EVENT_INTERVALADDED   = ISignal.SWIG_START - 39;
	public static final int SWIG_LISTDATA_EVENT_INTERVALREMOVED = ISignal.SWIG_START - 40;
	//
	public static final int SWIG_LISTSELECTION_EVENT = ISignal.SWIG_START - 41;
	//
	public static final int SWIG_MENUDRAGMOUSE_EVENT_DRAGGED  = ISignal.SWIG_START - 42;
	public static final int SWIG_MENUDRAGMOUSE_EVENT_ENTERED  = ISignal.SWIG_START - 43;
	public static final int SWIG_MENUDRAGMOUSE_EVENT_EXITED   = ISignal.SWIG_START - 44;
	public static final int SWIG_MENUDRAGMOUSE_EVENT_RELEASED = ISignal.SWIG_START - 45;
	//
	public static final int SWIG_MENUKEY_EVENT_PRESSED  = ISignal.SWIG_START - 46;
	public static final int SWIG_MENUKEY_EVENT_RELEASED = ISignal.SWIG_START - 47;
	public static final int SWIG_MENUKEY_EVENT_TYPED    = ISignal.SWIG_START - 48;
	//
	public static final int SWIG_MENU_EVENT_CANCELED   = ISignal.SWIG_START - 49;
	public static final int SWIG_MENU_EVENT_DESELECTED = ISignal.SWIG_START - 50;
	public static final int SWIG_MENU_EVENT_SELECTED   = ISignal.SWIG_START - 51;
	//
	public static final int SWIG_MOUSE_EVENT_CLICKED  = ISignal.SWIG_START - 52;
	public static final int SWIG_MOUSE_EVENT_ENTERED  = ISignal.SWIG_START - 53;
	public static final int SWIG_MOUSE_EVENT_EXITED   = ISignal.SWIG_START - 54;
	public static final int SWIG_MOUSE_EVENT_PRESSED  = ISignal.SWIG_START - 55;
	public static final int SWIG_MOUSE_EVENT_RELEASED = ISignal.SWIG_START - 56;
	public static final int SWIG_MOUSE_EVENT_DRAGGED  = ISignal.SWIG_START - 57;
	public static final int SWIG_MOUSE_EVENT_MOVED    = ISignal.SWIG_START - 58;
	//
	public static final int SWIG_MOUSEWHEEL_EVENT = ISignal.SWIG_START - 59;
	//
	public static final int SWIG_POPUPMENU_EVENT_CANCELED            = ISignal.SWIG_START - 60;
	public static final int SWIG_POPUPMENU_EVENT_WILLBECOMEINVISIBLE = ISignal.SWIG_START - 61;
	public static final int SWIG_POPUPMENU_EVENT_WILLBECOMEVISIBLE   = ISignal.SWIG_START - 62;
	//
	public static final int SWIG_PROPERTYCHANGE_EVENT          = ISignal.SWIG_START - 63;
	public static final int SWIG_PROPERTYCHANGE_EVENT_VETOABLE = ISignal.SWIG_START - 64;
	//
	public static final int SWIG_TABLECOLUMNMODEL_EVENT_ADDED            = ISignal.SWIG_START - 65;
	public static final int SWIG_TABLECOLUMNMODEL_EVENT_MOVED            = ISignal.SWIG_START - 66;
	public static final int SWIG_TABLECOLUMNMODEL_EVENT_REMOVED          = ISignal.SWIG_START - 67;
	public static final int SWIG_TABLECOLUMNMODEL_EVENT_MARGINCHANGED    = ISignal.SWIG_START - 68;
	public static final int SWIG_TABLECOLUMNMODEL_EVENT_SELECTIONCHANGED = ISignal.SWIG_START - 69;
	//
	public static final int SWIG_TABLEMODEL_EVENT = ISignal.SWIG_START - 70;
	//
	public static final int SWIG_TREEEXPANSION_EVENT_COLLAPSED    = ISignal.SWIG_START - 71;
	public static final int SWIG_TREEEXPANSION_EVENT_EXPANDED     = ISignal.SWIG_START - 72;
	public static final int SWIG_TREEEXPANSION_EVENT_WILLCOLLAPSE = ISignal.SWIG_START - 73;
	public static final int SWIG_TREEEXPANSION_EVENT_WILLEXPAND   = ISignal.SWIG_START - 74;
	//
	public static final int SWIG_TREEMODEL_EVENT_NODESCHANGED     = ISignal.SWIG_START - 75;
	public static final int SWIG_TREEMODEL_EVENT_NODESINSERTED    = ISignal.SWIG_START - 76;
	public static final int SWIG_TREEMODEL_EVENT_NODESREMOVED     = ISignal.SWIG_START - 77;
	public static final int SWIG_TREEMODEL_EVENT_STRUCTURECHANGED = ISignal.SWIG_START - 78;
	//
	public static final int SWIG_TREESELECTION_EVENT = ISignal.SWIG_START - 79;
	//
	public static final int SWIG_UNDOABLEEDIT_EVENT = ISignal.SWIG_START - 80;
	//
	public static final int SWIG_WINDOW_EVENT_GAINEDFOCUS  = ISignal.SWIG_START - 81;
	public static final int SWIG_WINDOW_EVENT_LOSTFOCUS    = ISignal.SWIG_START - 82;
	public static final int SWIG_WINDOW_EVENT_ACTIVATED    = ISignal.SWIG_START - 83;
	public static final int SWIG_WINDOW_EVENT_CLOSED       = ISignal.SWIG_START - 84;
	public static final int SWIG_WINDOW_EVENT_CLOSING      = ISignal.SWIG_START - 85;
	public static final int SWIG_WINDOW_EVENT_DEACTIVATED  = ISignal.SWIG_START - 86;
	public static final int SWIG_WINDOW_EVENT_DEICONIFIED  = ISignal.SWIG_START - 87;
	public static final int SWIG_WINDOW_EVENT_ICONIFIED    = ISignal.SWIG_START - 88;
	public static final int SWIG_WINDOW_EVENT_OPENED       = ISignal.SWIG_START - 89;
	public static final int SWIG_WINDOW_EVENT_STATECHANGED = ISignal.SWIG_START - 90;
	
	// These are messages that are sent from a Xholon instance to a SwingEntity instance.
	public static final int SWIG_FROMXHOLON_REQUEST = ISignal.SWIG_START - 91;
	public static final int SWIG_SET_NAME_REQUEST          = SWIG_FROMXHOLON_REQUEST -  1; // Name
	public static final int SWIG_SET_TEXT_REQUEST          = SWIG_FROMXHOLON_REQUEST -  2; // Text
	public static final int SWIG_SET_TITLE_REQUEST         = SWIG_FROMXHOLON_REQUEST -  3; // Title
	public static final int SWIG_SET_TOOLTIPTEXT_REQUEST   = SWIG_FROMXHOLON_REQUEST -  4; // ToolTipText
	public static final int SWIG_SET_FONT_REQUEST          = SWIG_FROMXHOLON_REQUEST -  5; // Font
	public static final int SWIG_SET_ACTIONCOMMAND_REQUEST = SWIG_FROMXHOLON_REQUEST -  6; // ActionCommand
	public static final int SWIG_SET_EDITABLE_REQUEST      = SWIG_FROMXHOLON_REQUEST -  7; // Editable
	public static final int SWIG_SET_ENABLED_REQUEST       = SWIG_FROMXHOLON_REQUEST -  8; // Enabled
	public static final int SWIG_SET_FOCUSABLE_REQUEST     = SWIG_FROMXHOLON_REQUEST -  9; // Focusable
	public static final int SWIG_SET_OPAQUE_REQUEST        = SWIG_FROMXHOLON_REQUEST - 10; // Opaque
	public static final int SWIG_SET_VISIBLE_REQUEST       = SWIG_FROMXHOLON_REQUEST - 11; // Visible
	public static final int SWIG_SET_BACKGROUND_REQUEST    = SWIG_FROMXHOLON_REQUEST - 12; // Background
	public static final int SWIG_SET_FOREGROUND_REQUEST    = SWIG_FROMXHOLON_REQUEST - 13; // Foreground
	public static final int SWIG_SET_COLOR_REQUEST         = SWIG_FROMXHOLON_REQUEST - 14; // Color
	public static final int SWIG_SET_MINIMUMSIZE_REQUEST   = SWIG_FROMXHOLON_REQUEST - 15; // MinimumSize
	public static final int SWIG_SET_MAXIMUMSIZE_REQUEST   = SWIG_FROMXHOLON_REQUEST - 16; // MaximumSize
	public static final int SWIG_SET_PREFERREDSIZE_REQUEST = SWIG_FROMXHOLON_REQUEST - 17; // PreferredSize
	public static final int SWIG_SET_SIZE_REQUEST          = SWIG_FROMXHOLON_REQUEST - 18; // Size
	public static final int SWIG_SET_LOCATION_REQUEST      = SWIG_FROMXHOLON_REQUEST - 19; // Location
	public static final int SWIG_FROMXHOLON_REQUEST_END = SWIG_FROMXHOLON_REQUEST - 20;
	
	/** <p>End of the range of values for Swing signals.</p> */
	public static final int SWIG_END = SWIG_FROMXHOLON_REQUEST_END;
	
	/** Name of each signal/event. */
	public static final String[] signalNames = {
		// Swing --> Xholon (events that originate inside Swing)
		"actionEvent",
		"ancestorEventAdded",
		"ancestorEventMoved",
		"ancestorEventRemoved",
		"caretEvent",
		"changeEventEditingStopped",
		"changeEventEditingCanceled",
		"changeEventStateChanged",
		"componentEventHidden",
		"componentEventMoved",
		"componentEventResized",
		"componentEventShown",
		"containerEventAdded",
		"containerEventRemoved",
		"documentEventChanged",
		"documentEventInsert",
		"documentEventRemove",
		"exceptionEvent",
		"focusEventGained",
		"focusEventLost",
		"hierarchyEventAncestorMoved",
		"hierarchyEventAncestorResized",
		"hierarchyEventHierarchyChanged",
		"hyperlinkEvent",
		"inputMethodEventCaretPositionChanged",
		"inputMethodEventTextChanged",
		"internalFrameEventActivated",
		"internalFrameEventClosed",
		"internalFrameEventClosing",
		"internalFrameEventDeactivated",
		"internalFrameEventDeiconified",
		"internalFrameEventIconified",
		"internalFrameEventOpened",
		"itemEvent",
		"keyEventPressed",
		"keyEventReleased",
		"keyEventTyped",
		"listDataEventContentsChanged",
		"listDataEventIntervalAdded",
		"listDataEventIntervalRemoved",
		"listSelectionEvent",
		"menuDragMouseEventDragged",
		"menuDragMouseEventEntered",
		"menuDragMouseEventExited",
		"menuDragMouseEventReleased",
		"menuKeyEventPressed",
		"menuKeyEventReleased",
		"menuKeyEventTyped",
		"menuEventCanceled",
		"menuEventDeselected",
		"menuEventSelected",
		"mouseEventClicked",
		"mouseEventEntered",
		"mouseEventExited",
		"mouseEventPressed",
		"mouseEventReleased",
		"mouseEventDragged",
		"mouseEventMoved",
		"mouseWheelEvent",
		"popupMenuEventCanceled",
		"popupMenuEventWillBecomeInvisible",
		"popupMenuEventWillBecomeVisible",
		"propertyChangeEvent",
		"propertyChangeEventVetoable",
		"tableColumnModelEventAdded",
		"tableColumnModelEventMoved",
		"tableColumnModelEventRemoved",
		"tableColumnModelEventMarginChanged",
		"tableColumnModelEventSelectionChanged",
		"tableModelEvent",
		"treeExpansionEventCollapsed",
		"treeExpansionEventExpanded",
		"treeExpansionEventWillCollapse",
		"treeExpansionEventWillExpand",
		"treeModelEventNodesChanged",
		"treeModelEventNodesInserted",
		"treeModelEventNodesRemoved",
		"treeModelEventStructureChanged",
		"treeSelectionEvent",
		"undoableEditEvent",
		"windowEventGainedFocus",
		"windowEventLostFocus",
		"windowEventActivated",
		"windowEventClosed",
		"windowEventClosing",
		"windowEventDeactivated",
		"windowEventDeiconified",
		"windowEventIconified",
		"windowEventOpened",
		"windowEventStateChanged",
		// Xholon --> Swing (requests that originate inside Xholon)
		"fromXholonRequest",
		"setNameRequest",
		"setTextRequest",
		"setTitleRequest",
		"setToolTipTextRequest",
		"setFontRequest",
		"setActionCommandRequest",
		"setEditableRequest",
		"setEnabledRequest",
		"setFocusableRequest",
		"setOpaqueRequest",
		"setVisibleRequest",
		"setBackgroundRequest",
		"setForegroundRequest",
		"setColorRequest",
		"setMinimumSizeRequest",
		"setMaximumSizeRequest",
		"setPreferredSizeRequest",
		"setSizeRequest",
		"setLocationRequest"
	};
	
	/**
	 * Set the value of an attribute.
	 * @param requestType A request type, as defined in ISwingEntity.
	 * @param attrVal The requested value of that type.
	 */
	public abstract void setAttribute(int requestType, Object attrVal);
	
	/**
	 * Get the name of an event/signal, given its ID.
	 * @param id An event/signal ID.
	 * @return The name corresponding to that ID, or a string version of the ID if no name is available,
	 * or SIGNAL_NAME_NOT_FOUND if it's an invalid ID.
	 */
	//public abstract String getSignalName(int id);
}
