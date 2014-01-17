package org.primordion.user.app.Bestiary;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonList;
import org.primordion.xholon.util.StringTokenizer;

public class LatLonPattern extends Xholon {
	
	/**
	 * X coordinate of the origin of the pattern.
	 */
	private int x = 0;
	
	/**
	 * Y coordinate of the origin of the pattern.
	 */
	private int y = 0;
	
	/**
	 * The gridCell at the upper left corner of the pattern.
	 */
	private IXholon origin = null;
	
	/**
	 * Pattern text read in from XML text.
	 */
	//private String patternText = null;
	
	/**
	 * An action can be specified in the XML.
	 * ex: action="clear"
	 */
	private String action = null;
	
	/** Number of rows in the Xholon grid. */
	private int nRows = 0;
	
	/** Number of columns (grid cells in a row) in the Xholon grid. */
	private int nCols = 0;
	
	/** Minimum latitude in the GIS data. */
	private double minlat = 0.0;
	
	/** Minimum longitude in the GIS data. */
	private double minlon = 0.0;
	
	/** Maximum latitude in the GIS data. */
	private double maxlat = 0.0;
	
	/** Maximum longitude in the GIS data. */
	private double maxlon = 0.0;
	
	/** Factor for converting latitude to row. */
	private double latFactor = 0.0;
	
	/** Factor for converting longitude to column. */
	private double lonFactor = 0.0;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(java.lang.String)
	 */
	//public void setVal(String val) {
	//	patternText = val;
	//}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_String()
	 */
	//public String getVal_String() {
	//	return patternText;
	//}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure() {
		System.out.println("postConfigure()");
		switch (getParentNode().getXhcId()) {
		case CeBestiary.GridCE:
			calcNRowsNCols(getParentNode());
			break;
		case CeBestiary.HabitatCellCE:
			calcNRowsNCols(getParentNode().getParentNode().getParentNode());
			// use this gridCell as the origin of the pattern
			origin = getParentNode();
			break;
		default:
			break;
		}
		latFactor = (nRows - 1) / (maxlat - minlat);
		lonFactor = (nCols - 1) / (maxlon - minlon);
		if (hasChildNodes()) {
			createPattern();
		}
		removeChild();
	}
	
	/**
	 * Create a pattern in the grid.
	 */
	protected void createPattern() {
		System.out.println("creating a pattern ...");
		XholonList list = (XholonList)getFirstChild();
		IXholon item = list.getFirstChild();
		while (item != null) {
			//System.out.print(item.getRoleName());
			String str = item.getVal_String();
			//System.out.println(" " + str);
			
			StringTokenizer st = new StringTokenizer(str, ", ");
			double lat = 0.0;
			double lon = 0.0;
			int row = 0;
			int col = 0;
			while (st.hasMoreTokens()) {
				lat = Double.parseDouble(st.nextToken());
				lon = Double.parseDouble(st.nextToken());
				row = calcRow(lat);
				//System.out.println("lat:" + lat + " row:" + row);
				col = calcColumn(lon);
				pasteWallSection(row, col);
			}
			item = item.getNextSibling();
		}
	}
	
	protected void pasteWallSection(int row, int col) {
		findGridCell(row, col);
		// TODO don't paste in a new WallSection if there's already one there
		origin.appendChild("WallSection", null);
	}
	
	protected void findGridCell(int y, int x) {
		// use x,y to determine the origin of the pattern
		origin = getParentNode().getFirstChild();
		for (int row = 0; row < y; row++) {
			if (origin != null) {
				origin = origin.getNextSibling();
			}
		}
		if (origin != null) {
			origin = origin.getFirstChild();
			for (int col = 0; col < x; col++) {
				if (origin != null) {
					origin = origin.getNextSibling();
				}
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(final String attrName, final String attrValue) {
		if ("x".equals(attrName)) {
			setX(Integer.parseInt(attrValue));
		}
		else if ("y".equals(attrName)) {
			setY(Integer.parseInt(attrValue));
		}
		else if ("minlat".equals(attrName)) {
			setMinlat(Double.parseDouble(attrValue));
		}
		else if ("minlon".equals(attrName)) {
			setMinlon(Double.parseDouble(attrValue));
		}
		else if ("maxlat".equals(attrName)) {
			setMaxlat(Double.parseDouble(attrValue));
		}
		else if ("maxlon".equals(attrName)) {
			setMaxlon(Double.parseDouble(attrValue));
		}
		else if ("action".equals(attrName)) {
			setAction(attrValue);
		}
		else {
			return super.setAttributeVal(attrName, attrValue);
		}
		return 0;
	}

	/**
	 * Calculate the Xholon row from a GIS latitude.
	 * @param lat A latitude between -90.0 and +90.0 .
	 * @return A row between 0 and (nRows - 1).
	 * If the latitude argument is invalid/out of bounds, returns -1 .
	 */
	protected int calcRow(final double lat) {
		int row = -1;
		if (lat < minlat) {return row;}
		if (lat > maxlat) {return row;}
		return (int)((lat - minlat) * latFactor);
	}

	/**
	 * Calculate the Xholon column from a GIS longitude.
	 * @param lon A longitude between -180.0 and +180.0 .
	 * @return A column between 0 and (nCols - 1).
	 * If the longitude argument is invalid/out of bounds, returns -1 .
	 */
	protected int calcColumn(final double lon) {
		int col = -1;
		if (lon < minlon) {return col;}
		if (lon > maxlon) {return col;}
		return (int)((lon - minlon) * lonFactor);
	}

	/**
	 * Calculate the number of rows and number of columns in the Xholon grid.
	 * @param gridNode The node that owns the entire grid (the parent of the rows).
	 */
	protected void calcNRowsNCols(final IXholon gridNode) {
		nRows = 0;
		IXholon row = gridNode.getFirstChild();
		while (row != null) {
			if (row != this) { // exclude this LatLonPattern node
				nRows++;
			}
			row = row.getNextSibling();
		}
		nCols = 0;
		IXholon col = gridNode.getFirstChild().getFirstChild();
		while (col != null) {
			nCols++;
			col = col.getNextSibling();
		}
		System.out.println("nRows:" + nRows);
		System.out.println("nCols:" + nCols);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getMinlat() {
		return minlat;
	}

	public void setMinlat(double minlat) {
		this.minlat = minlat;
	}

	public double getMinlon() {
		return minlon;
	}

	public void setMinlon(double minlon) {
		this.minlon = minlon;
	}

	public double getMaxlat() {
		return maxlat;
	}

	public void setMaxlat(double maxlat) {
		this.maxlat = maxlat;
	}

	public double getMaxlon() {
		return maxlon;
	}

	public void setMaxlon(double maxlon) {
		this.maxlon = maxlon;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
