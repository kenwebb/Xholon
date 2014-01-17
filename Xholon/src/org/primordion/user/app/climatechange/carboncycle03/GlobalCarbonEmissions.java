package org.primordion.user.app.climatechange.carboncycle03;

import com.google.gwt.core.client.GWT;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.util.MiscIoGwt;

/**
 * Read a file containing global carbon emissions since 1752.
 * @author ken
 * @see <a href="http://cdiac.ornl.gov/ftp/ndp030/CSV-FILES/global.1751_2008.csv" >csv file</a>
 */
public class GlobalCarbonEmissions extends Xhcarboncycle03 {
	
	// reservoirs
	private IXholon atmosphere = null;
	private IXholon fossilFuels = null;
	
	private MiscIoGwt in = null;
	
	public void postConfigure() {
		atmosphere = ((Flows)getParentNode()).getAtmosphere();
		fossilFuels = ((Flows)getParentNode()).getCoal();
		// open the remote data file
		in = new MiscIoGwt();
		boolean rc = in.openInputSync(GWT.getHostPageBaseURL()
		  + "config/climatechange/carboncycle03/data/emissions/global.1751_2008.csv");
		// discard the first 2 lines
		if (rc == true) {
			in.readLine();
			in.readLine();
			String buffer = in.readLine();
			if (buffer != null) {
			  String[] token = buffer.split(",");
			  // get "Total carbon emissions from fossil-fuels (million metric tons of C)"
			  this.setVal(Double.parseDouble(token[1]) / 1000.0);  // MtC to GtC
			}
		}
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		if (fossilFuels.getVal() > rate) {
			atmosphere.incVal(rate);
			fossilFuels.decVal(rate);
		}
		else {
			// fossil fuels are all used up
			this.setVal(0.0);
		}
		super.act();
	}
	
	public void postAct() {
		// read and process a line of data
		if (in != null) {
			String buffer = in.readLine();
			if (buffer == null) {
				in = null;
			}
			else {
				String[] token = buffer.split(",");
				this.setVal(Double.parseDouble(token[1]) / 1000.0); // MtC to GtC
			}
		}
		super.postAct();
	}
}
