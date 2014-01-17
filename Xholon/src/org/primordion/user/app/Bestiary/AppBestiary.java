package org.primordion.user.app.Bestiary;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.io.GridViewerDetails;

/**
 * Bestiary application - Xholon Java
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
*/
public class AppBestiary extends Application {

	/** how many time step intervals to chart */
	protected int chartInterval = 1;
	
	public AppBestiary() {super();}
	
	public int getChartInterval() {
		return chartInterval;
	}

	public void setChartInterval(int chartInterval) {
		this.chartInterval = chartInterval;
	}

	public boolean setParam(String pName, String pValue)
	{
		if ("TimeStepMultiplier".equals(pName)) {
			XhBestiary.setTimeStepMultiplier(Integer.parseInt(pValue));
			return true;
		}
		else if ("ChartInterval".equals(pName)) {
			setChartInterval(Integer.parseInt(pValue));
			return true;
		}
		else if ("MaxFoodProductionRate".equals(pName)) {
			setMaxFoodProductionRate(Double.parseDouble(pValue));
			return true;
		}
		return super.setParam(pName, pValue);
	}
	
	public double getMaxFoodProductionRate() {return HabitatCellBestiary.maxFoodProductionRate;}

	public void setMaxFoodProductionRate(double maxFoodProductionRate)
	{
		HabitatCellBestiary.maxFoodProductionRate = maxFoodProductionRate;
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		root.processMessageQ();
		// only certain self-contained subtrees need to use timeStepMultiplier
		/*for (int i = 0; i < XhBestiary.timeStepMultiplier; i++) {
			if (getUseDataPlotter() && (chartViewer != null) && ((i % chartInterval) == 0)) {
				chartViewer.capture((((double)i / XhBestiary.timeStepMultiplier)) + timeStep);
			}
			root.act();
		}*/
		if (getUseDataPlotter() && (chartViewer != null)) {
			chartViewer.capture(timeStep);
		}
		root.act();
		if (shouldStepView) {
			view.act();
		}
		XholonTime.sleep( getTimeStepInterval() );
		if (getUseGridViewer()) {
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				if (gvd.useGridViewer) {
					//gvd.gridFrame.setInfoLabel("Time step: " + timeStep);
					gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics()); // GWT
				}
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#wrapup()
	 */
	public void wrapup()
	{
		if (getUseGridViewer()) { // close the grid viewer window
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				if (gvd.useGridViewer) {
					//gvd.gridFrame.wrapup(); //removeAll();
				}
			}
		}
		super.wrapup();
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#shouldBePlotted(org.primordion.xholon.base.IXholon)
	 */
	protected boolean shouldBePlotted(org.primordion.xholon.base.IXholon modelNode)
	{
		if ((modelNode.getXhType() & org.primordion.xholon.base.IXholonClass.XhtypePurePassiveObject)
		    == org.primordion.xholon.base.IXholonClass.XhtypePurePassiveObject) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#findGwtClientBundle()
	 */
	public Object findGwtClientBundle() {
	  return Resources.INSTANCE;
	}
	
	/** main
	 * @param args
	 */
	public static void main(String[] args) {
	    appMain(args, "org.primordion.user.app.Bestiary.AppBestiary",
	        "/org/primordion/user/app/Bestiary/Bestiary_xhn.xml");
	}
}
