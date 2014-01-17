/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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
import org.primordion.xholon.base.IXholonClass;

/**
 * <p>This interface should be implemented by concrete classes that capture data,
 * and display the data in a histogram.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.6 (Created on May 2, 2007)
 */
public interface IHistogramViewer extends IXholon {
	
	/**
	 * Produce a histogram chart.
	 * @param values Values that will be grouped into bins and displayed as a histogram.
	 */
	public abstract void chart(double values[]);
	
	/**
	 * Produce a histogram chart.
	 */
	public abstract void chart();
	
	/**
	 * Initialize the histogram viewer.
	 * @param histRootModel The root node of a Xholon subtree in the model,
	 * that will be traversed looking for instances of the xholon class.
	 * @param histRootView Root node in the view. This is the "HistogramViewer" node.
	 * @param xhClass A Xholon class whose instances will be queried for values, using getVal().
	 * @param numBins Number of bins.
	 * @param nameConcatLevels Number of composite levels to concatenate in deriving name.
	 */
	public abstract void initialize(IXholon histRootModel, IXholon histRootView, IXholonClass xhClass, int numBins, int nameConcatLevels);

	/**
	 * Set the X range of the histogram.
	 * @param min Minimum cutoff value to show in the chart.
	 * @param max Maximum cutoff value to show in the chart.
	 */
	public abstract void setXRange(double min, double max);
	
	/**
	 * Set the Y range of the histogram.
	 * @param min Minimum cutoff value to show in the chart.
	 * @param max Maximum cutoff value to show in the chart.
	 */
	public abstract void setYRange(double min, double max);

	/**
	 * Remove the chart from the screen.
	 */
	public abstract void remove();
}
