package org.primordion.user.app.climatechange.carboncycle03;

import com.google.gwt.core.client.GWT;

//import java.io.BufferedReader;
//import java.io.IOException;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.util.MiscIoGwt;

public class LandUseChange extends Xhcarboncycle03 {
	
	// reservoirs
	private IXholon soil = null;
	private IXholon atmosphere = null;
	
	//private BufferedReader in = null;
	private MiscIoGwt in = null;
	private String[] token = null;
	private int dataStartYear = 0;
	
	public void postConfigure() {
		// in the IPCC model/figure, Veg and Soil are combined in the same Veg box
		soil = ((Flows)getParentNode()).getTerrestrialVegetation(); //.getSoil();
		atmosphere = ((Flows)getParentNode()).getAtmosphere();
		// open the data file
		//in = (BufferedReader)MiscIo.openInputFile("/org/primordion/user/app/climatechange/carboncycle03/data/landuse/1850-2005.txt");
		// open the remote data file
		in = new MiscIoGwt();
		//boolean rc = 
		in.openInputSync(GWT.getHostPageBaseURL()
		  + "config/climatechange/carboncycle03/data/landuse/1850-2005.txt");
		// discard the first 15 lines
		if (in != null) {
			//try {
				for (int i = 0; i < 15; i++) {
					in.readLine();
				}
				String buffer = in.readLine();
				token = buffer.split("\\s+");
				dataStartYear = Integer.parseInt(token[1]);  // Year
			//} catch (IOException e) {
			//	logger.warn("", e);
			//}
		}
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		if (soil.getVal() > rate) {
			atmosphere.incVal(rate);
			soil.decVal(rate);
		}
		else {
			// soil is all used up
			this.setVal(0.0);
		}
		super.act();
	}
	
	public void postAct() {
		// read and process a line of data
		if (in != null) {
			//try {
				// wait for the year 1850, which is the year the data starts
				if (getApp().getTimeStep() >= dataStartYear) {
					this.setVal(Double.parseDouble(token[2]) / 1000.0); // TgC to GtC
					String buffer = in.readLine();
					if (buffer == null) {
						//MiscIo.closeInputFile(in);
						in = null;
					}
					else {
						token = buffer.split("\\s+");
					}
				}
			//} catch (IOException e) {
			//	logger.warn("", e);
			//}
		}
		super.postAct();
	}
	
}
