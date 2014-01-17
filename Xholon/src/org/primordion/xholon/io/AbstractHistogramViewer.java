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

import java.util.Vector;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Xholon;

/**
 * <p>This is the abstract superclass for concrete classes that capture data and display the data in a histogram.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.6 (Created on May 3, 2007)
 */
public abstract class AbstractHistogramViewer extends Xholon implements IHistogramViewer {
	
	protected IXholon histRootModel = null;
	protected IXholon histRootView = null;
	protected IXholonClass xhClass = null;
	protected int numBins = 0;
	//protected XholonWithPorts firstXYSeries = null;
	//protected int seriesCount;
	protected int nameConcatLevels = 1; // number of composite levels to concatenate in deriving series name
	// Write to data file as long, short, int, float, double.
	protected int writeType = IChartViewer.WRITE_AS_LONG; // default
	
	protected boolean setXRangeManually = false;
	protected double minX = 0.0;
	protected double maxX = 0.0;
	protected boolean setYRangeManually = false;
	protected double minY = 0.0;
	protected double maxY = 0.0;
	
	protected String yFormat = "%.0f";

	public abstract void chart(double[] values);
	
	/*
	 * @see org.primordion.xholon.io.AbstractHistogramViewer#chart()
	 */
	public void chart() {
		int i;
		Vector v = histRootModel.getChildNodes(true);
		v.addElement(histRootModel); // histRootModel might itself be of the specified xhClass
		int count = 0;
		for (i = 0; i < v.size(); i++) {
			if (((IXholon)v.elementAt(i)).getXhc() == xhClass) {
				count++;
			}
		}
		if (count == 0) { 
			System.out.println("AbstractHistogramViewer chart() There are no values to be charted.");
			return;
		}
		
		double values[] = new double[count];
		int valIx = 0;
		for (i = 0; i < v.size(); i++) {
			IXholon node = (IXholon)v.elementAt(i);
			if (node.getXhc() == xhClass) {
				if (!setXRangeManually) {
					values[valIx++] = node.getVal();
				}
				else {
					double myVal = node.getVal();
					if (myVal >= minX && myVal <= maxX) {
						values[valIx++] = myVal;
					}
				}
			}
		}
		chart(values);
	}
	
	/*
	 * @see org.primordion.xholon.io.IHistogramViewer#remove()
	 */
	public abstract void remove();
	
	/*
	 * @see org.primordion.xholon.io.IHistogramViewer#setXRange(double, double)
	 */
	public void setXRange(double min, double max) {
		setXRangeManually = true;
		minX = min;
		maxX = max;
	}
	
	/*
	 * @see org.primordion.xholon.io.IHistogramViewer#setYRange(double, double)
	 */
	public void setYRange(double min, double max) {
		setYRangeManually = true;
		minY = min;
		maxY = max;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#hasAnnotation()
	 */
	public boolean hasAnnotation() {return false;}
	
}
