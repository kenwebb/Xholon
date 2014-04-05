/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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

package org.primordion.xholon.mech.petrinet.grid;

import java.util.Iterator;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.io.GridViewerDetails;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IQueue;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.mech.petrinet.IKinetics;
import org.primordion.xholon.mech.petrinet.PetriNet;
import org.primordion.xholon.mech.petrinet.QueueTransitions;
import org.primordion.xholon.mech.petrinet.Transition;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.NodeSelectionService;
import org.primordion.xholon.service.XholonHelperService;
import org.primordion.xholon.service.xholonhelper.ICutCopyPaste;
import org.primordion.xholon.service.xholonhelper.IFindChildSibWith;
import org.primordion.xholon.util.MiscRandom;

/**
 * Grid owner (ex: Cytoplasm)
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 31, 2012)
*/
public class GridOwner extends XholonWithPorts {
	private static final long serialVersionUID = 8432702866465277701L;
	
	private static final String generateGrid = "Generate grid";
	private static final String populateGrid = "Populate grid";
	/** action list */
	private String[] actions = {generateGrid,populateGrid};
	
	private String gridGenerator = null; //"./org/primordion/xholon/mech/petrinet/grid/GridGenerator.js";
	
	private IXholon xholonHelperService = null;
	private IXholon nodeSelectionService = null;
	
	/**
	 * The Petri net whose nodes serve as a template for the Grid.
	 */
	private IXholon petriNet = null;
	
	private int rows = 100;
	private int cols = 100;
	private int gridCellColor = 0x000000;
	private String gridViewerParams = null;
	
	private IQueue q = null;
	
	/**
	 * Should Place nodes in the Grid have a move behavior.
	 * If a Place in the PN has more than one input arc (or weight), then it must have a move behavior.
	 */
	private boolean shouldPlacesMove = true;
	
	/**
	 * Default tokenFactor. This default value will have no effect because:
	 * <p>x * 1.0 = x</p>
	 */
	private static final double TOKENFACTOR_DEFAULT = 1.0;
	/**
	 * A value that's multiplied by the number of tokens in a Place,
	 * to determine how many nodes to create in the Grid.
	 * For example, this is used in the Cell model where each small molecule (places)
	 * has a token value of 100000.0.
	 */
	private double tokenFactor = TOKENFACTOR_DEFAULT;
	
	private String roleName = null;
	public void setRoleName(String roleName) {this.roleName = roleName;}
	public String getRoleName() {return roleName;}
	
	/*
	 * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
	 */
	public void postConfigure() {
	  xholonHelperService = this.getService("XholonHelperService");
		nodeSelectionService = this.getService("NodeSelectionService");
		// the following creates 3 grids; maybe Application does some final stuff?
		/*if ((petriNet != null) && (((PetriNet)petriNet).getKineticsType() == IKinetics.KINETICS_GRID)) {
			generateGridAndPopulate();
		}*/
		super.postConfigure();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
	  //consoleLog("GridOwner act()1 petriNet:" + petriNet);
		if (this.getApp().getTimeStep() == 0) {
		  if (petriNet == null) {
		    Iterator<PortInformation> it =
		      ReflectionFactory.instance().getAllPorts(this, true, "petriNet").iterator();
		    if (it.hasNext()) {
		      PortInformation pi = it.next();
		      petriNet = pi.getReffedNode();
		    }
		  }
			if ((petriNet != null) && (((PetriNet)petriNet).getKineticsType() == IKinetics.KINETICS_GRID)) {
				generateGridAndPopulate();
			}
		}
		super.act();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getActionList()
	 */
	public String[] getActionList() {
		return actions;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setActionList(java.lang.String[])
	 */
	public void setActionList(String[] actionList)
	{
		actions = actionList;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#doAction(java.lang.String)
	 */
	public void doAction(String action) {
		if (generateGrid.equals(action)) {
			generateGridAndPopulate();
		}
		else if (populateGrid.equals(action)) {
			populateGrid();
		}
	}
	
	/**
	 * Generate a grid, and populate it with existing places and transitions in the Petri net.
	 */
	protected void generateGridAndPopulate() {
	  //consoleLog("GridOwner generateGridAndPopulate() starting");
		if (gridGenerator == null) {
			generateGrid();
		}
		else {
			((XholonHelperService)xholonHelperService).pasteLastChild(this, gridGenerator);
		}
		q = (IQueue)this.findFirstChildWithXhClass("QueueNodesInGrid");
		
		// populate grid from values in the Petri net
		// search known locations for places and transitions
		IXholon nodesRoot = ((IFindChildSibWith)getService(
			    IXholonService.XHSRV_XHOLON_HELPER))
			    .findFirstChildWithAncestorXhClass(petriNet, "Places");
		
		if (nodesRoot != null) {
			nodesRoot.visit(this);
		}
		nodesRoot = petriNet.findFirstChildWithXhClass("QueueTransitions");
		if (nodesRoot != null) {
			nodesRoot = ((QueueTransitions)nodesRoot).getTransitionsRoot();
			if (nodesRoot != null) {
				nodesRoot.visit(this);
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#visit(org.primordion.xholon.base.IXholon)
	 */
	public boolean visit(IXholon visitor) {
	  //consoleLog("GridOwner visit() starting");
		if (visitor == null || visitor.getXhc() == null) {
			return true;
		}
		if (visitor.getXhc().hasAncestor("PlacePN")) {
			double tokens = visitor.getVal();
			if (tokenFactor != TOKENFACTOR_DEFAULT) {
				tokens = tokens * tokenFactor;
				visitor.setVal(tokens);
			}
			for (int i = 0; i < (int)tokens; i++) {
				createPlaceInGrid(visitor, null);
			}
		}
		else if (visitor.getXhc().hasAncestor("TransitionPN")) {
			for (int i = 0; i < visitor.getVal(); i++) {
				createTransitionInGrid(visitor, null);
			}
		}
		return true;
	}
	
	/**
	 * Generate a grid.
	 */
	protected void generateGrid() {
	  //consoleLog("GridOwner generateGrid() starting");
		IApplication app = this.getApp();
		IXholon xhcRoot = app.getXhcRoot();
		IXholon cnode = this;
		ICutCopyPaste service = (ICutCopyPaste)cnode.getService("XholonHelperService");

		// (1) Generate the xholon classes.
		service.pasteLastChild(xhcRoot,
		  "<Grid xhType='XhtypeGridEntity' implName='org.primordion.xholon.base.GridEntity'/>");
		service.pasteLastChild(xhcRoot,
		  "<Row xhType='XhtypeGridEntity' implName='org.primordion.xholon.base.GridEntity'/>");
		service.pasteLastChild(xhcRoot, "<GridCell/>");
		service.pasteLastChild(xhcRoot,
		  "<xholonClassDetails><GridCell xhType='XhtypeGridEntityActivePassive' implName='org.primordion.xholon.base.GridEntity'><config instruction='Gmt'/></GridCell></xholonClassDetails>");

		// (2) Generate the grid.
		service.pasteLastChild(cnode, "<QueueNodesInGrid/>");
		service.pasteLastChild(cnode,
		  "<Grid><Row multiplicity='" + rows + "'><GridCell multiplicity='" + cols + "'></GridCell></Row></Grid>");

		// (3) Generate and display the GridPanel.
		app.setUseGridViewer(true);
		app.setGridPanelClassName("org.primordion.xholon.io.GridPanelGeneric");
		if (gridViewerParams == null) {
			app.setGridViewerParams("descendant::Row/..,5,Xholon - " + cnode.getXhcName() + " - Grid Viewer,false");
		}
		else {
			app.setGridViewerParams(gridViewerParams);
		}
		int gvIndex = app.createGridViewer(-1);
		// create a gridViewer node in the Xholon GUI View, to allow toggling frozen
		app.getView().appendChild("GridViewer", null);

		// (4) Optionally set the grid background color.
		GridViewerDetails gv = app.getGridViewer(gvIndex);
		gv.gridPanel.setGridCellColor(gridCellColor); // an RGB color 0xF0F0FF
	}
	
	/**
	 * Populate a grid with places and transions,
	 * based on which places and transitions in the Petri net are selected.
	 */
	protected void populateGrid() {
	  //consoleLog("GridOwner populateGrid() starting");
		IMessage msg = ((NodeSelectionService)nodeSelectionService)
		  .sendSyncMessage(NodeSelectionService.SIG_GET_SELECTED_NODES_REQ, null, this);
		IXholon[] nssNodes = (IXholon[])msg.getData();
		for (int i = 0; i < nssNodes.length; i++) {
			IXholon nssNode = nssNodes[i];
			if (nssNode.getXhc().hasAncestor("PlacePN")) {
				createPlaceInGrid(nssNode, null);
				nssNode.incVal(1.0); // increment the token count in the Place
			}
			else if (nssNode.getXhc().hasAncestor("TransitionPN")) {
				createTransitionInGrid(nssNode, null);
				nssNode.incVal(1.0); // increment the count in the Transition
			}
			else if (nssNode.getXhc().hasAncestor("Places")) {}
			else if (nssNode.getXhc().hasAncestor("Transitions")) {}
		}
	}
	
	/**
	 * Create a new Place node in the Grid.
	 * @param pnNode The Place node in the Petri net,
	 * that serves as a template for making a Place node in the Grid.
	 * @param gridCell The gridCell where the new Place node will be located.
	 * If null, then a random location will be selected.
	 */
	protected void createPlaceInGrid(IXholon pnNode, IXholon gridCell) {
	  //consoleLog("GridOwner createPlaceInGrid() starting");
		if (gridCell == null) {
			gridCell = randomGridCell();
		}
		String nodeName = pnNode.getXhcName();
		String newNodeStr = null;
		if (shouldPlacesMove) {
			newNodeStr = "<" + nodeName + "><MovingPlaceBehavior/></" + nodeName + ">";
		}
		else {
			newNodeStr = "<" + nodeName + "/>";
		}
		((XholonHelperService)xholonHelperService).pasteLastChild(gridCell, newNodeStr);
		if (shouldPlacesMove) {
			IXholon newNode = gridCell.getLastChild();
			q.enqueue(newNode);
		}
	}
	
	/**
	 * Create a new Transition node in the Grid.
	 * @param pnNode The Transition node in the Petri net,
	 * that serves as a template for making a Transition node in the Grid.
	 * @param gridCell The gridCell where the new Transition node will be located.
	 * If null, then a random location will be selected.
	 */
	protected void createTransitionInGrid(IXholon pnNode, IXholon gridCell) {
	  //consoleLog("GridOwner createTransitionInGrid() starting");
		if (gridCell == null) {
			gridCell = randomGridCell();
		}
		String nodeName = pnNode.getXhcName();
		String newNodeStr = "<" + nodeName
		  + "><MovingTransitionBehavior/><FiringTransitionBehavior/></"
		  + nodeName + ">";
		((XholonHelperService)xholonHelperService).pasteLastChild(gridCell, newNodeStr);
		IXholon newNode = gridCell.getLastChild();
		if ((newNode != null) && (newNode.getXhc().hasAncestor("TransitionPN"))) {
			((Transition)newNode).setInputArcs(((Transition)pnNode).getInputArcs());
			((Transition)newNode).setOutputArcs(((Transition)pnNode).getOutputArcs());
		}
		q.enqueue(newNode);
	}
	
	/**
	 * Get a random gridCell.
	 * @return A random gridCell.
	 */
	protected IXholon randomGridCell() {
	  //consoleLog("GridOwner randomGridCell() starting");
		int row = MiscRandom.getRandomInt(0,
				this.findFirstChildWithXhClass("Grid").getNumChildren(false)) + 1;
		int col = MiscRandom.getRandomInt(0,
				this.findFirstChildWithXhClass("Grid").getFirstChild().getNumChildren(false)) + 1;
		IXholon gridCell = this.getXPath()
		  .evaluate("Grid/Row[" + row + "]/GridCell[" + col + "]", this);
		return gridCell;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrVal) {
	  //consoleLog("GridOwner setAttributeVal() starting " + attrName + " " + attrVal);
		if (attrName.equals("gridGenerator")) {
			setGridGenerator(attrVal);
		}
		else if (attrName.equals("rows")) {
			setRows(Integer.parseInt(attrVal));
		}
		else if (attrName.equals("cols")) {
			setCols(Integer.parseInt(attrVal));
		}
		else if (attrName.equals("gridCellColor")) {
			setGridCellColor(Integer.parseInt(attrVal, 16));
		}
		else if (attrName.equals("gridViewerParams")) {
			setGridViewerParams(attrVal);
		}
		else if (attrName.equals("shouldPlacesMove")) {
			setShouldPlacesMove(Boolean.parseBoolean(attrVal));
		}
		else if (attrName.equals("tokenFactor")) {
			setTokenFactor(Double.parseDouble(attrVal));
		}
		return 0;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.IXmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
		if (roleName != null) {xmlWriter.writeAttribute("roleName", roleName);}
		xmlWriter.writeAttribute("rows", Integer.toString(rows));
		xmlWriter.writeAttribute("cols", Integer.toString(cols));
		xmlWriter.writeAttribute("gridCellColor", Integer.toString(gridCellColor));
		if (gridViewerParams != null) {xmlWriter.writeAttribute("gridViewerParams", gridViewerParams);}
		xmlWriter.writeAttribute("tokenFactor", Double.toString(tokenFactor));
	}

	public String getGridGenerator() {
		return gridGenerator;
	}

	public void setGridGenerator(String gridGenerator) {
		this.gridGenerator = gridGenerator;
	}

	public IXholon getPetriNet() {
		return petriNet;
	}

	public void setPetriNet(IXholon petriNet) {
		this.petriNet = petriNet;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getGridCellColor() {
		return gridCellColor;
	}

	public void setGridCellColor(int gridCellColor) {
		this.gridCellColor = gridCellColor;
	}

	public String getGridViewerParams() {
		return gridViewerParams;
	}

	public void setGridViewerParams(String gridViewerParams) {
		this.gridViewerParams = gridViewerParams;
	}

	public IQueue getQ() {
		return q;
	}

	public void setQ(IQueue q) {
		this.q = q;
	}

	public boolean isShouldPlacesMove() {
		return shouldPlacesMove;
	}

	public void setShouldPlacesMove(boolean shouldPlacesMove) {
		this.shouldPlacesMove = shouldPlacesMove;
	}
	public double getTokenFactor() {
		return tokenFactor;
	}
	public void setTokenFactor(double tokenFactor) {
		this.tokenFactor = tokenFactor;
	}
	
}
