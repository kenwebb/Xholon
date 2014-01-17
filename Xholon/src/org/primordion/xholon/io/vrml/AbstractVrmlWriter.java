/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.xholon.io.vrml;

//import java.io.IOException;
//import java.io.Writer;
import java.util.Random;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.io.vrml.Rotations;
import org.primordion.xholon.io.vrml.SFRotation;
import org.primordion.xholon.io.vrml.SFVec3f;
import org.primordion.xholon.io.vrml.VrmlBuffer;

/**
 * VRML Writer. Writes a file in VRML 97 (VRML 2) format.
 * Each Xholon in the current application is written out as a VRML node.
 * The file can subsequently be viewed in 3D using a VRML tool,
 * such as Cortona from Parallel Graphics.
 * To view it completely, a number of other VRML (.wrl)
 * and PNG (.png) files are required.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Oct 26, 2005)
 */
public abstract class AbstractVrmlWriter extends Xholon implements IVrmlWriter {

	// Style
	// true: use 1999 style, showing cellular spaces only, with no enzymes or small molecules
	// false: use 2003 style, with various rings showing enzymes and small molecules
	protected static boolean doClassicStyle = true;
	protected static String vrmlWriteDir = "./3d/"; // directory to write VRML files into
	protected static boolean useLOD = false; // whether or not to use VRML Level of Detail
	// whether to create various .wrl files:
	// true: creates these files
	// false: assumes that these files have been previously created
	protected static boolean createProtoFiles = true; // false;

	protected VrmlBuffer v;
	protected Rotations rot;
	protected Random random;
	
	protected IXholon root = null;

	/**
	 * Constructor.
	 */
	public AbstractVrmlWriter() {}
	
	// parameters
	public static void setVrmlWriteDir(String vWriteDir) {vrmlWriteDir = vWriteDir;}
	public static String getVrmlWriteDir() {return vrmlWriteDir;}
	
	public static void setUseLOD(boolean useLod) {useLOD = useLod;}
	public static boolean getUseLOD() {return useLOD;}
	
	public static void setCreateProtoFiles(boolean crProtoFiles) {createProtoFiles = crProtoFiles;}
	public static boolean getCreateProtoFiles() {return createProtoFiles;}
	
	public static void setDoClassicStyle(boolean doClassikStyle) {doClassicStyle = doClassikStyle;}
	public static boolean getDoClassicStyle() {return doClassicStyle;}
	
	/*
	 * @see org.primordion.xholon.io.vrml.IVrmlWriter#writeAsVrml(org.primordion.xholon.base.IXholon)
	 */
	public void writeAsVrml(IXholon xhNode) {
	  root = xhNode;
		writeWrlFile(xhNode);
	}
	
	/**
	 * Write VRML .wrl file
	 * @param xhNode Xholon node from which to start outputting the VRML.
	 */
	protected abstract void writeWrlFile(IXholon xhNode);
	
	/**
	 * Show structure of current Xholon node.
	 * @param sbOut Output StringBuilder.
	 * @param xhNode Current Xholon node.
	 */
	protected void showStructure(StringBuilder sbOut, IXholon xhNode) {
		// ignore it if it's a small molecule; already handled by CytosolCE or other solution
		// SmallMolecule and all its subclasses should be XhtypePurePassiveObject
		if (!xhNode.isPassiveObject()) {
			describeXholon( sbOut, xhNode );			
		}
		IXholon leftChild    = xhNode.getFirstChild();
		IXholon rightSibling = xhNode.getNextSibling();
		if (xhNode == root) {
		  // the root node can't have any siblings
		  rightSibling = null;
		}

		if (leftChild != null) {
			showStructure( sbOut, leftChild );
		}
		
		//try {
			if (!xhNode.isPassiveObject()) {
				sbOut.append( "]\n}\n" );
			}
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
		
		if (rightSibling != null)
			showStructure( sbOut, rightSibling );
	}
	
	/**
	 * Describe an individual Xholon.
	 * @param sbOut Output StringBuilder.
	 * @param xhNode Xholon node.
	 */
	protected abstract void describeXholon(StringBuilder sbOut, IXholon xhNode);


	/**
	 * Write random rotation.
	 */
	protected void writeRandomRotation() {
		v.write( "\nrotation " );
		SFRotation sfr = rot.get();
		v.write( sfr.x, sfr.y, sfr.z );
		v.write( " " );
		v.write( sfr.angle );
		v.write( "\n" );
	}

	/**
	 * Write a shape, such as an enzyme or a small molecule.
	 * Nothing is written if the radius is 0.
	 * @param xhNode Xholon node.
	 * @param radius Radius of the shape.
	 * @param xlat Translation of the shape.
	 * @param redVal RGB red value.
	 * @param greenVal RGB green value.
	 * @param blueVal RGB blue value.
	 * @param transparency Transparency.
	 */
	protected void writeShape(
			IXholon xhNode,
			double radius, SFVec3f xlat,
			double redVal, double greenVal, double blueVal,
			double transparency )
	{
		// write nothing if radius = 0.0
		if (radius == 0.0) {
			return;
		}
		v.write( "scale " );
		v.write( radius, radius, radius );
		v.write( "\ntranslation " );
		v.write( xlat.x, xlat.y, xlat.z );
		v.write( "\nchildren [\n" );
		if (radius > 0.0) {
			v.write( "Shape { ");
			v.write( "geometry Sphere { } ");
			v.write( "appearance Appearance { ");
			v.write( "material Material { diffuseColor ");
			v.write( redVal, greenVal, blueVal );
			v.write( " transparency ");
			v.write( transparency );
			v.write( "} } }\n");
		}
	}
}
