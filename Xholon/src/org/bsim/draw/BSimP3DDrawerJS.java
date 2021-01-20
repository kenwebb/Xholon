/**
 * BSimP3DDrawerJS.java
 * based on BSim BSimP3DDrawer.java
 * Processing-js based renderer for scene preview and visualisation.
 * 
 * Uses components of the Processing libraries:
 * 		www.processing.org
 * 		http://processingjs.org
 */

package org.bsim.draw;

//import java.awt.Color;
//import java.awt.Graphics2D;

// GWT
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.JavaScriptObject;

import org.javax.vecmath.Vector3d;
import org.javax.vecmath.Color3b;

import org.bsim.capsule.BSimCapsuleBacterium;
//import processing.core.PConstants;
//import processing.core.PFont;
//import processing.core.PGraphics3D;

import org.bsim.BSim;
import org.bsim.BSimChemicalField;
import org.bsim.BSimOctreeField;
import org.bsim.geometry.BSimMesh;
import org.bsim.geometry.BSimTriangle;
import org.bsim.particle.BSimParticle;
import org.bsim.particle.BSimVesicle;

/**
 * Scene preview and visualisation renderer (extends BSimDrawer). Uses 
 * Processing3D libraries to render the scene. The resulting image is 
 * then drawn to a native Java 2D graphics context which can be used 
 * within a GUI.
 */
public abstract class BSimP3DDrawerJS extends BSimDrawer {

	/** Processing graphics context used for drawing. */
	//protected PGraphics3D p3d; // KSW
	protected JavaScriptObject p3d; // KSW
	/** Font used when rendering text. */
	//protected PFont font;
	/** Size of the simulation. */
	protected Vector3d bound;
	/** Centre of the simulation. Used for camera positioning. */
	protected Vector3d boundCentre;

	// With updated Processing library (1.5.1), looks like begindraw() will reset the camera
	// after 1st frame has been drawn, so this provides a workaround for now.
	protected boolean cameraIsInitialised = false;
	
	/**
	 * Default constructor for initialising a Processing3D rendering context.
	 * @param sim	The simulation we wish to render.
	 * @param width	The desired horizontal resolution (pixels).
	 * @param height	The desired vertical resolution (pixels).
	 */
	public BSimP3DDrawerJS(BSim sim, int width, int height) {
		super(sim, width, height);
		this.consoleLog("BSimP3DDrawerJS constructor starting ...");
		bound = sim.getBound();
		boundCentre = new Vector3d();
		boundCentre.scale(0.5, bound);
		/* See 'Subclassing and initializing PGraphics objects'
		 * http://dev.processing.org/reference/core/ */
		//p3d = new PGraphics3D();
		//p3d.setPrimary(true);
		//p3d.setSize(width, height);
		//font = new PFont(PFont.findFont("Trebuchet MS").deriveFont((float)20), true, PFont.CHARSET);
		p3d = initProcessing(width, height);
		this.consoleLog("BSimP3DDrawerJS constructor ... done");
	}
	
	protected native JavaScriptObject initProcessing(int width, int height) /*-{
		var xhcanvas = $doc.querySelector("#xhcanvas");
		xhcanvas.innerHTML = '<canvas id="canvas2"' + '></canvas>';
  		var canvas = xhcanvas.lastElementChild;
  		
		var sketchProc = function(p3d) {
			//p3d.setPrimary(true); 
			p3d.size(width, height);
			p3d.background(0,127,0); // green
			//p3d.sphere(50);
			p3d.pushMatrix(); // Pushes the current transformation matrix onto the matrix stack. 
			p3d.text("For now, BSimP3DDrawerJS.java can only generate this stupid message, when running a BSim app such as BSimSimplest.java (Xholon).", 20, 20);
			p3d.text("See browser console window for results.", 20, 50);
			p3d.fill(128, 128, 255, 50); // Sets the color used to fill shapes.
			p3d.stroke(128, 128, 255); // Sets the color used to draw lines and borders around shapes. 
			p3d.translate(100,120); // Specifies an amount to displace objects within the display window.
			p3d.box(50, 60); // A box is an extruded rectangle. A box with equal dimension on all sides is a cube.
			p3d.popMatrix();
		}
		var p3d = new $wnd.Processing(canvas, sketchProc);
		if (typeof $wnd.xh == "undefined") {$wnd.xh = {};}
		$wnd.xh.p3d = p3d;
		return p3d;
	}-*/;
		
	/**
	 * Render all simulation and scene elements to the Processing3D graphics
	 * context 'p3d' (effectively the render buffer), then draw the rendered 
	 * contents to the native Java graphics context.
	 * @param g The native Java graphics context to which we wish to draw our
	 * 			rendered scene.
	 */
	//@Override
	/*public void draw(Graphics2D g) {  // ORIGINAL Java code
		p3d.beginDraw();

		if(!cameraIsInitialised){
			p3d.camera(-(float)bound.x*0.7f, -(float)bound.y*0.3f, -(float)bound.z*0.5f, 
				(float)bound.x, (float)bound.y, (float)bound.z, 
				0, 1, 0);
			cameraIsInitialised = true;
		}
		
		p3d.textFont(font);
		p3d.textMode(PConstants.SCREEN);

		p3d.sphereDetail(10);
		p3d.noStroke();		
		p3d.background(0, 0, 0);	

		scene(p3d);
		boundaries();
		time();

		p3d.endDraw();
		g.drawImage(p3d.image, 0,0, null);
	}*/
	
	protected native void consoleLog(Object obj) /*-{
		$wnd.console.log(obj);
	}-*/;
	
	@Override
	public void draw(Object g) {
		// TODO add processing-js JavaScript code
		this.consoleLog("void draw(Object g)");
		//this.drawNative(p3d); // this may overlay the graphics too quickly to see
		this.scene(p3d);
		this.boundaries(bound, boundCentre);
		this.time(sim.getFormattedTime());
	}
	
	protected native void drawNative(JavaScriptObject p3d) /*-{
		//p3d.sphereDetail(10); // runtime error
		//p3d.noStroke();
		p3d.background(0, 0, 0);
	}-*/;
			
	/**
	 * Draws remaining scene objects to the PGraphics3D object.
	 * Abstract method that should be overridden in a BSim_X_Example to render objects
	 * that are specific to a simulation.
	 */
	//public abstract void scene(PGraphics3D p3d);
	public abstract void scene(Object p3d);
	
	/**
	 * Draw the default cuboid boundary of the simulation as a partially transparent box
	 * with a wireframe outline surrounding it.
	 */
	public native void boundaries(Vector3d bound, Vector3d boundCentre) /*-{
		var p3d = $wnd.xh.p3d;
		p3d.fill(128, 128, 255, 50); // Sets the color used to fill shapes.
		p3d.stroke(128, 128, 255); // Sets the color used to draw lines and borders around shapes. 
		p3d.pushMatrix(); // Pushes the current transformation matrix onto the matrix stack. 
		p3d.translate(boundCentre.x,boundCentre.y,boundCentre.z); // Specifies an amount to displace objects within the display window.
		p3d.box(bound.x, bound.y, bound.z); // A box is an extruded rectangle. A box with equal dimension on all sides is a cube.
		p3d.popMatrix();
		p3d.noStroke(); // Disables drawing the stroke (outline).
	}-*/;
	
	/**
	 * Draw the default cuboid boundary of the simulation as a wireframe outline.
	 */
	public native void boundaryOutline(Vector3d bound, Vector3d boundCentre) /*-{
		var p3d = $wnd.xh.p3d;
		p3d.noFill();
		p3d.stroke(128, 128, 255);
		p3d.pushMatrix();
		p3d.translate(boundCentre.x,boundCentre.y,boundCentre.z);
		p3d.box(bound.x, bound.y, bound.z);
		p3d.popMatrix();
		p3d.noStroke();
	}-*/;
	
	/**
	 * Draw the formatted simulation time to screen.
	 */
	public native void time(String formattedTime) /*-{
		var p3d = $wnd.xh.p3d;
		//p3d.fill(255);
		//p3d.text(formattedTime, 50, 500);
	}-*/;

	/**
	 * Draw a BSimParticle as a point if it is very small (radius < 1), or a sphere otherwise.
	 * @param p	The BSimParticle to be rendered.
	 * @param c	The desired colour of the particle.
	 */
	public void draw(BSimParticle p, CssColor c) {
		this.consoleLog("draw(BSimParticle p, CssColor c)");
		Vector3d position = p.getPosition();
		if (p.getRadius() < 1) {
			this.point(position.x, position.y, position.z, (byte)126, (byte)0, (byte)0);
		}
		else {
			this.sphere(position.x, position.y, position.z, p.getRadius(), (byte)0, (byte)0, (byte)126, 126);
		}
		//this.drawNative(p.getPosition(), p.getRadius(), c);
	}
	
	// KSW not needed
	//protected native void drawNative(Vector3d position, double radius, CssColor c) /*-{
	//	$wnd.console.log("void drawNative(Vector3d position, double radius, CssColor c)");
	//	$wnd.console.log(position);
	//	$wnd.console.log(radius);
	//	$wnd.console.log(c);
	//	if (radius < 1) {
	//	  point(radius);
	//	}
	//	else sphere(p.getPosition(), p.getRadius(), c, 255);
	//}-*/;
		
	/**
	 * Draw a vesicle as a pixel surrounded by a 'halo' to make it easier to spot.
	 * @param v	The BSimVesicle to be rendered.
	 * @param c	The desired vesicle colour.
	 */
	public void draw(BSimVesicle v, CssColor c) {
		this.consoleLog("draw(BSimVesicle v, CssColor c)");
		Vector3d position = v.getPosition();
		this.sphere(position.x, position.y, position.z, 100*v.getRadius(), (byte)0, (byte)0, (byte)126, 50);
		this.point(position.x, position.y, position.z, (byte)126, (byte)0, (byte)0);
	}

	/**
	 * Draw a mesh with a given colour (draws each triangle of the mesh individually). 
	 * Also draws face normals as a red line from the face.
	 * @param mesh	The mesh you want to draw...
	 * @param c Mesh face colour.
	 * @param normalScaleFactor	Scale factor for normal vector length. Set to zero to disable normal drawing.
	 */
	public native void draw(BSimMesh mesh, CssColor c, double normalScaleFactor) /*-{
		$wnd.console.log("draw(BSimMesh mesh, CssColor c, double normalScaleFactor)");
		//p3d.fill(c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
		
		//int lineAlpha = c.getAlpha() + 50;
		//if (lineAlpha > 255) lineAlpha = 255;
		//p3d.stroke(c.getRed(),c.getGreen(),c.getBlue(),lineAlpha);
		
		//p3d.beginShape(PConstants.TRIANGLES);
		//for(BSimTriangle t:mesh.getFaces()){
		//	vertex(mesh.getVertCoordsOfTri(t,0));
		//	vertex(mesh.getVertCoordsOfTri(t,1));
		//	vertex(mesh.getVertCoordsOfTri(t,2));
		//}
		//p3d.endShape();
		
		//if(normalScaleFactor != 0.0){
		//	for(BSimTriangle t:mesh.getFaces()){
		//		vector(mesh.getTCentre(t),t.getNormal(),normalScaleFactor,(new Color(255,0,0,150)));
		//	}
		//}
	}-*/;
	
	/**
	 * Draw a mesh, default colour.
	 * @param mesh Mesh to draw.
	 * @param normalScaleFactor Scaling factor.
	 */
	public native void draw(BSimMesh mesh, double normalScaleFactor) /*-{
		$wnd.console.log("draw(BSimMesh mesh, double normalScaleFactor)");
		//draw(mesh, new Color(128,128,255,50), normalScaleFactor);
	}-*/;
		
	/**
	 * Draw a 'vector' originating at a point, represented by a line.
	 * @param origin		Point from which the vector originates.
	 * @param theVector		The vector.
	 * @param scaleFactor	Scalar by which the vector is multiplied for drawing purposes.
	 */
	public native void vector(Vector3d origin, Vector3d theVector, double scaleFactor, CssColor c) /*-{
		//p3d.stroke(c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
		//p3d.line((float)origin.x, (float)origin.y, (float)origin.z,
		//		(float)(origin.x + scaleFactor*theVector.x), (float)(origin.y + scaleFactor*theVector.y), (float)(origin.z + scaleFactor*theVector.z));
	}-*/;
	
	/**
	 * Define a p3d vertex when constructing shapes, directly from a Point3d.
	 * @param newPoint The Point3d representing the vertex coordinates.
	 */
	public native void vertex(Vector3d newPoint) /*-{
		var p3d = $wnd.xh.p3d;
		p3d.vertex(newPoint.x, newPoint.y, newPoint.z);
	}-*/;
	
	/**
	 * Draw sphere; helper function which draws a parametrised sphere.
	 * @param position	The Cartesian coordinates of the sphere's centre in 3-D space.
	 * @param radius	Sphere radius.
	 * @param c			Colour of the sphere.
	 * @param alpha		Sphere transparency.
	 */
	public native void sphere(double x, double y, double z, double radius, byte red, byte green, byte blue, int alpha) /*-{
		$wnd.console.log("sphere(");
		$wnd.console.log("x:" + x + " y:" + y + " z:" + z + " radius:" + radius);
		$wnd.console.log("red:" + red + " green:" + green + " blue:" + blue + " alpha:" + alpha);
		var p3d = $wnd.xh.p3d;
		p3d.pushMatrix();
		p3d.translate(x, y, z);
		p3d.fill(red, green, blue, alpha);
		p3d.sphere(radius);
		p3d.popMatrix();
	}-*/;
	
	/**
	 * Draw a point (pixel); parametrised helper function.
	 * @param position	The Cartesian coordinates of the point in 3-D space.
	 * @param c 		The colour of the point.
	 */
	public native void point(double x, double y, double z, byte red, byte green, byte blue) /*-{
		$wnd.console.log("point(");
		$wnd.console.log("x:" + x + " y:" + y + "z: " + z);
		$wnd.console.log("red:" + red + " green:" + green + "blue: " + blue);
		var p3d = $wnd.xh.p3d;
		p3d.stroke(red, green, blue);
		p3d.point(x, y, z);
		p3d.noStroke();
	}-*/;

	/**
	 * Draw a capsule bacterium as a cylinder with hemispherical caps.
	 * @param bac	The BSimCapsuleBacterium that we would like to draw.
	 * @param c		The desired colour of the BSimCapsuleBacterium.
     */
	public native void draw(BSimCapsuleBacterium bac, CssColor c) /*-{
		$wnd.console.log("draw(BSimCapsuleBacterium bac, CssColor c)");
		var p3d = $wnd.xh.p3d;
		//p3d.fill(c.getRed(), c.getGreen(), c.getBlue());

		//Vector3d worldY = new Vector3d(0, 1, 0);
		//Vector3d bacDirVector = new Vector3d();

		//bacDirVector.sub(bac.x2, bac.x1);

		//Vector3d u = new Vector3d();
		//u.scaleAdd(0.5, bacDirVector, bac.x1);

		//Vector3d bacRotVector = new Vector3d();
		//bacRotVector.cross(worldY, bacDirVector);

		//bacDirVector.normalize();

		//bacRotVector.normalize();

		//p3d.pushMatrix();
		//p3d.translate((float) u.x, (float) u.y, (float) u.z);
		//p3d.rotate((float) worldY.angle(bacDirVector), (float) bacRotVector.x, (float) bacRotVector.y, (float) bacRotVector.z);
		//drawRodShape((float) bac.radius, (float) bac.L, 32);
		//p3d.popMatrix();
		//sphere(bac.x1, bac.radius, c, 255);
		//sphere(bac.x2, bac.radius, c, 255);
	}-*/;


	/**
	 * A helper function for drawing the BSimCapsuleBacterium.
	 * Draws an uncapped cylinder or rod.
	 * the RodShape is drawn along the y axis.
	 * @param radius 	Desired cylinder radius.
	 * @param length 	Desired cylinder length.
	 * @param sides		Number of subdivisions around the outer surface (number of discretised sides in the final mesh).
     */
	public native void drawRodShape(float radius, float length, int sides) /*-{
		$wnd.console.log("drawRodShape(float radius, float length, int sides)");
		var p3d = $wnd.xh.p3d;
		var angle = 0;
		var angleIncrement = p3d.TWO_PI / sides;
		var lengthRatio = (length / 2.0);
		//p3d.beginShape(p3d.QUAD_STRIP);
		//for (int i = 0; i < sides + 1; ++i) {
		//	p3d.vertex((float) (radius * Math.cos(angle)), 0 - lengthRatio, (float) (radius * Math.sin(angle)));
		//	p3d.vertex((float) (radius * Math.cos(angle)), 0 + lengthRatio, (float) (radius * Math.sin(angle)));
		//	angle += angleIncrement;
		//}
		//p3d.endShape();
	}-*/;

	/**
	 * Draws a chemical field structure based on its defined parameters, with custom transparency (alpha) parameters.
	 * @param field		The chemical field structure to be rendered.
	 * @param c			Desired colour of the chemical field.
	 * @param alphaGrad	Alpha per unit concentration of the field.
	 * @param alphaMax	Maximum alpha value (enables better viewing).
	 */
	public native void draw(BSimChemicalField field, CssColor c, double alphaGrad, double alphaMax) /*-{
		//int[] boxes = field.getBoxes();
		//double[] boxSize = field.getBox();
		//double alpha = 0.0f;
		
		//for(int i=0; i < boxes[0]; i++) {
		//	for(int j=0; j < boxes[1]; j++) {
		//		for(int k=0; k < boxes[2]; k++) {							
		//			p3d.pushMatrix();					
		//			p3d.translate((float)(boxSize[0]*i+boxSize[0]/2), (float)(boxSize[1]*j+boxSize[1]/2), (float)(boxSize[2]*k+boxSize[2]/2));
		//			
		//			alpha = alphaGrad*field.getConc(i,j,k);
		//			if (alpha > alphaMax) alpha = alphaMax;
		//			
		//			p3d.fill(c.getRed(), c.getGreen(), c.getBlue(),(float)alpha);
		//			p3d.box((float)boxSize[0], (float)boxSize[1], (float)boxSize[2]);
		//			p3d.popMatrix();
		//		}
		//	}
		//}
	}-*/;
	
	/**
	 * Draw a chemical field structure based on its defined parameters (default alpha).
	 * @param field The chemical field to be drawn.
	 * @param c The desired colour.
	 * @param alphaGrad The alpha-per-unit-concentration.
	 */
	public native void draw(BSimChemicalField field, CssColor c, float alphaGrad) /*-{
		//draw(field, c, alphaGrad, 255);
	}-*/;

	/**	
	 * 	Draw a BSimOctreeField in given colour. Post order hierarchy used for drawing.
	 *	@param t Octree to be drawn.
	 *	@param c Desired colour.
	 *	@param alphaGrad The alpha-per-unit-concentration. 
	 */
	public native void draw(BSimOctreeField t, CssColor c, float alphaGrad) /*-{
		//if(t!=null){	
		//	for (int i=0;  i<8; i++){			
		//		draw(t.getsubNode(i), c,  alphaGrad);				
		//	}	
		//	p3d.pushMatrix();	
		//	p3d.translate((float)(t.getCentre().x), (float)(t.getCentre().y), (float)(t.getCentre().z));
		//	p3d.fill(c.getRed(),c.getGreen(),c.getBlue(),alphaGrad*(float)t.getQuantity());
		//	p3d.box((float)t.getLength(),(float)t.getLength(),(float)t.getLength());
		//	p3d.popMatrix();
		//}
	}-*/;

	/**	
	 * 	Draw a BSimOctreeField. Post order hierarchy used for drawing.
	 *	@param t Octree to be drawn.
	 *	@param alphaGrad The alpha-per-unit-concentration. 
	 */
	public native void draw(BSimOctreeField t, float alphaGrad) /*-{
		//if(t!=null){
		//	for (int i=0;  i<8; i++){			
		//		draw(t.getsubNode(i), alphaGrad);				
		//	}
		//	p3d.pushMatrix();	
		//	p3d.translate((float)(t.getCentre().x), (float)(t.getCentre().y), (float)(t.getCentre().z));	
		//	if(t.getnodeColor()==Color.cyan){
		//		p3d.fill(t.getnodeColor().getRed(), t.getnodeColor().getGreen(),t.getnodeColor().getBlue(), 100);
		//	} else {
		//		p3d.fill(t.getnodeColor().getRed(), t.getnodeColor().getGreen(),t.getnodeColor().getBlue(), alphaGrad);
		//	}
		//	p3d.box((float)t.getLength(),(float)t.getLength(),(float)t.getLength());
		//	p3d.popMatrix();
		//}
	}-*/;
}
