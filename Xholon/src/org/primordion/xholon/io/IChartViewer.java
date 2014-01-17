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

/**
 * <p>This interface should be implemented by concrete classes that capture data and display the data in a chart.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on April 24, 2006)
 */
public interface IChartViewer extends IXholon {

	public static final int WRITE_TIME_IN_HEADING = -1; // write "time," at start of heading line
	
	// Write to data file as int, long, short, float or double.
	public static final int WRITE_AS_NULL   = 0; // don't write anything
	public static final int WRITE_AS_INT    = 1;
	public static final int WRITE_AS_LONG   = 2;
	public static final int WRITE_AS_SHORT  = 3;
	public static final int WRITE_AS_FLOAT  = 4;
	public static final int WRITE_AS_DOUBLE = 5;
	
	/**
	 * 
	 * @param chartRoot XYChart node.
	 * @param nameConcatLevels Number of composite levels to concatenate in deriving series name.
	 * @param typeOfData The type of data will be included as part of the file name.
	 * @param writeType Whether the data will be written out as int, long, float, double.
	 * @param params One or more additional comma-separated parameters.
	 */
	public abstract void initialize(IXholon chartRoot, int nameConcatLevels, String typeOfData, int writeType, String params);
	
	/**
	 * Capture data at each timestep.
	 * @param timeStep Time step, or other time interval.
	 */
	public abstract void capture(double timeStep);
	
	/**
	 * Capture specified data at each time interval.
	 * @param numTimeSeries Number of time series indices.
	 * @param xVal X Value.
	 * @param yVal Y Values.
	 */
	public abstract void capture(int numTimeSeries, double xVal, double yVal[]);

	/**
	 * Produce a chart from the captured data.
	 */
	public abstract void chart();
	
	/**
	 * Create a chart from the captured data,
	 * and return the chart object without displaying it.
	 * This is mostly intended to be used with JFreeChart.
	 * @param showLegend
	 * @return
	 */
	public abstract Object createChart(boolean showLegend);
	
	/**
	 * Produce a chart from the captured data.
	 * @param showLegend Whether or not to show the legend. The legend shows the name and color of all data series.
	 * If there are a lot of different series, then the legend can get too big.
	 */
	public abstract void chart(boolean showLegend);
	
	/**
	 * Create one or more xy data series.
	 * @param seriesCount Number of xy series.
	 * @param xySeriesName Name of each xy series.
	 */
	public abstract void createXySeries(int seriesCount, String xySeriesName[]);
	
	/**
	 * Set the X range of the chart.
	 * @param min Minimum cutoff value to show in the chart.
	 * @param max Maximum cutoff value to show in the chart.
	 */
	public abstract void setXRange(double min, double max);
	
	/**
	 * Set the Y range of the chart.
	 * @param min Minimum cutoff value to show in the chart.
	 * @param max Maximum cutoff value to show in the chart.
	 */
	public abstract void setYRange(double min, double max);
	
	/**
	 * Set the format of the y axis of the chart. This is specific to gnuplot.
	 * @param yFormat A format string (ex: "%.4f" or "%.0f")
	 */
	public abstract void setYFormat(String yFormat);
	
	/**
	 * Remove the chart from the screen.
	 */
	public abstract void remove();
}