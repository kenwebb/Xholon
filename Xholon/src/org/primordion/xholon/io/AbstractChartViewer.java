/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2006, 2007, 2008 Ken Webb
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

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.XholonGwtTabPanelHelper;

/**
 * <p>This is the abstract superclass for concrete classes that capture data and display the data in a chart.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on April 24, 2006)
 */
@SuppressWarnings("serial")
public abstract class AbstractChartViewer extends Xholon implements IChartViewer {

	protected XholonWithPorts chartRoot = null;
	protected XholonWithPorts firstXYSeries = null;
	protected int seriesCount;
	protected int nameConcatLevels = 1; // number of composite levels to concatenate in deriving series name
	// Write to data file as long, short, int, float, double.
	protected int writeType = WRITE_AS_LONG; // default
	
	protected boolean setXRangeManually = false;
	protected double minX = 0.0;
	protected double maxX = 0.0;
	protected boolean setYRangeManually = false;
	protected double minY = 0.0;
	protected double maxY = 0.0;
	
	protected String yFormat = "%.0f";
	
	protected boolean writeToTab = true;

	/*
	 * @see org.primordion.xholon.io.IChartViewer#capture(double)
	 */
	public abstract void capture(double timeStep);
	
	/*
	 * @see org.primordion.xholon.io.IChartViewer#capture(int, double, double[])
	 */
	public abstract void capture(int numTimeSeries, double xVal, double yVal[]);
	
	/*
	 * @see org.primordion.xholon.io.IChartViewer#chart()
	 */
	public abstract void chart();
	
	/*
	 * @see org.primordion.xholon.io.IChartViewer#createChart(boolean)
	 */
	public Object createChart(boolean showLegend) {
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.io.IChartViewer#setXRange(double, double)
	 */
	public void setXRange(double min, double max)
	{
		setXRangeManually = true;
		minX = min;
		maxX = max;
	}
	
	/*
	 * @see org.primordion.xholon.io.IChartViewer#setYRange(double, double)
	 */
	public void setYRange(double min, double max)
	{
		setYRangeManually = true;
		minY = min;
		maxY = max;
	}
	
	/*
	 * @see org.primordion.xholon.io.IChartViewer#setYFormat(java.lang.String)
	 */
	public void setYFormat(String yFormat)
	{
		this.yFormat = yFormat;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#hasAnnotation()
	 */
	public boolean hasAnnotation() {return false;}
	
	/*
	 * @see org.primordion.xholon.io.IChartViewer#remove()
	 */
	public abstract void remove();
	
	/**
   * Write the entire external-format text to an appropriate target.
   * @param efText The external-format text.
   * @param uri A file name, or a GWT-usable tooltip.
   * @param outPath A file system path name, or GWT-usable content type (ex: "_xhn"), or equivalent.
   * @param root The root node of the subtree being written out.
   */
  protected void writeToTarget(String efText, String uri, String outPath, IXholon root) {
    if (root.getApp().isUseGwt()) {
      if (writeToTab) {
        XholonGwtTabPanelHelper.addTab(efText, outPath, uri, false);
      }
      else {
        root.println(efText);
      }
    }
    else {
      //writeToTargetFile(efText, uri, outPath, root);
    }
  }
  
}
