package org.bsim.examples.BSimSimplest;

//import java.awt.Color;

import com.google.gwt.canvas.dom.client.CssColor;

import org.javax.vecmath.Vector3d;

//import processing.core.PGraphics3D;
import org.bsim.BSim;
import org.bsim.BSimTicker;
import org.bsim.draw.BSimP3DDrawerJS;
import org.bsim.particle.BSimBacterium;

/**
 * Super-simple example of a BSim simulation. 
 * 
 * Creates and draws a single bacterium which swims around in a fluid environment with classic run-and-tumble motion.
 */
public class BSimSimplest {
	
	protected static native void consoleLog(Object obj) /*-{
		$wnd.console.log(obj);
	}-*/;
	
	public static void main(String[] args) {
		
		// create the simulation object
		BSim sim = new BSim();
		consoleLog("BSimSimplest 1");
				
		/*********************************************************
		 * Create the bacterium
		 */
		final BSimBacterium bacterium = new BSimBacterium(sim, new Vector3d(50,50,50));
		bacterium.setRadius(20); // KSW
		consoleLog("BSimSimplest 2");
		
		/*********************************************************
		 * Set up the ticker
		 */
		sim.setTicker(new BSimTicker() {
			@Override
			public void tick() {
				bacterium.action();		
				bacterium.updatePosition();
			}
		});
		consoleLog("BSimSimplest 3");
		
		/*********************************************************
		 * Set up the drawer
		 */
		sim.setDrawer(new BSimP3DDrawerJS(sim, 800,600) {
			@Override
			public void scene(Object p3d) {						
				draw(bacterium, CssColor.make("yellow"));
			}
		});	
		consoleLog("BSimSimplest 4");
		
		// Run the simulation
		sim.preview();
		consoleLog("BSimSimplest 5");
	} 

}
