package org.bsim.export;

import com.google.gwt.canvas.dom.client.CssColor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bsim.BSim;

/**
 * Ken Webb
 * JavaScript console.log exporter.
 * Can be used to write simulation data to a web browser's console.
 * The during() method must be overwritten to write the required data to file.
 */
public abstract class BSimConsoleLoggerJS extends BSimExporter {
	
	/** Object to write output to. */
	//protected BufferedWriter bufferedWriter;
	/** Filename of output. */
	protected String filename;
	
	/**
	 * Constructor for a JS console logger.
	 * @param sim Associated simulation.
	 * @param filename Output filename.
	 */
	public BSimConsoleLoggerJS(BSim sim, String filename) {
		super(sim);
		this.filename = filename;
		this.write(filename);
		
		// test of CssColor
		String str = "#7f1234";
		CssColor clr = CssColor.make(str);
		write("COLOR TEST: " + str + " " + clr.value());
		
		// test of CssColor
		String str2 = "rgb(45,67,89)";
		CssColor clr2 = CssColor.make(str2);
		write("COLOR TEST2: " + str2 + " " + clr2.value());
	}
	
	/**
	 * Called before a simulation starts. Can be extended by a user if necessary.
	 */
	@Override
	public void before() {
		/*try {
			bufferedWriter = new BufferedWriter(new FileWriter(new File(filename)));
		}
		catch(IOException e){ 
			e.printStackTrace();
		} */
	}
	
	/**
	 * Writes text to the output file.
	 * @param text Text to write to file.
	 */
	public native void write(String text) /*-{		
		//try {			
		//	bufferedWriter.write(text);
		//	bufferedWriter.newLine();
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
		$wnd.console.log(text);
	}-*/;
	
	/**
	 * Called after a simulation ends. Can be extended by a user if necessary.
	 */
	@Override
	public void after() {
		/*try {
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
}
