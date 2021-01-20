package org.primordion.user.app.bsim.brownian;

import org.bsim.examples.BSimBrownianMotion.BSimBrownianMotion;
import org.bsim.examples.BSimSimplest.BSimSimplest;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.io.GridViewerDetails;

/**
 * bsim brownian - Xholon Java
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://cellsimulationlabs.github.io/tools/bsim/">BSim website</a>
 * @since 0.9.1 (Created on May 25, 2020)
*/
public class Appbrownian extends Application {

	public Appbrownian() {
		super();
		// for now, test out two BSim applications
		// BrownianMotion displays results to the browser console
		// Simplest generates a visible HTML5 canvas using processingjs, but I can't get updates to work, so it's just a placeholder for now
		consoleLog("Xholon org.primordion.user.app.bsim.brownian.Appbrownian is about to run org.bsim.examples.BSimBrownianMotion.BSimBrownianMotion.main ...");
		BSimBrownianMotion.main(null);
		consoleLog("See browser console window for brownian csv results.");
		consoleLog("Xholon org.primordion.user.app.bsim.brownian.Appbrownian is about to run org.bsim.examples.BSimSimplest.BSimSimplest.main ...");
		BSimSimplest.main(null);
		consoleLog("Observe the beautiful green rectangle and lame message as simplest results.");
	}
	
	//protected native void print2xhtab(String str) /*-{
	//	if ($wnd.xh && $wnd.xh.root()) {
	//		$wnd.xh.root().println(str);
	//	}
	//	else {
	//		$wnd.console.log(str);
	//	}
	//}-*/;
	
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
					gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics());
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
	
	public Object findGwtClientBundle() {
		return Resources.INSTANCE;
	}
  
	/** main
	 * @param args
	 */
	public static void main(String[] args) {
	    appMain(args, "org.primordion.user.app.bsim.brownian.Appbrownian",
	        "/org/primordion/user/app/bsim/brownian/_xhn.xml");
	}
}
