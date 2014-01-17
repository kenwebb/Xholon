package org.primordion.user.app.petrinet.feinberg1;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.io.GridViewerDetails;

/**
 * petrinet feinberg1 - Xholon Java
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on August 4, 2012)
*/
public class Appfeinberg1 extends Application {

	public Appfeinberg1() {
		super();
		//Transition.setShouldWriteSequenceDiagram(true);
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep);
		}
		root.act();
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
		super.wrapup();
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#shouldBePlotted(org.primordion.xholon.base.IXholon)
	 */
	protected boolean shouldBePlotted(org.primordion.xholon.base.IXholon modelNode) {
		if ((modelNode.getXhType() & org.primordion.xholon.base.IXholonClass.XhtypePurePassiveObject) == org.primordion.xholon.base.IXholonClass.XhtypePurePassiveObject) {
			return true;
		} else {
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
	    appMain(args, "org.primordion.user.app.petrinet.feinberg1.Appfeinberg1",
	        "/org/primordion/user/app/petrinet/feinberg1/_xhn.xml");
	}
}
