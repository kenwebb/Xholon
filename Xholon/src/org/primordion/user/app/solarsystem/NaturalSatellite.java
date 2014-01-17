package org.primordion.user.app.solarsystem;

import org.primordion.xholon.base.IMessage;

public class NaturalSatellite extends AstronomicalObject {
	
	public void processReceivedMessage(IMessage msg) {
		switch (msg.getSignal()) {
		case ISolarSystem.SIG_LUMINOSITY:
			double solarConstant = (Double)msg.getData();
			// TODO forward to the surface (no atmosphere) or top of atmosphere ?
			println(roleName + ": " + solarConstant);
			break;
		default:
			break;
		}
	}
	
}
