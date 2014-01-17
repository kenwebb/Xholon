/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2011 Ken Webb
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

package org.primordion.dynsys.app.stability;

import java.util.List;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.service.svg.SvgClient;

/**
 * Ball View is implemented using SVG, and the SVG Salamander library.
 * Paste in an instance of this class as the lastChild of StabilitySystem.
 * <pre>&lt;BallView/></pre>
 * TODO get this working with multiple balls.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 17, 2011)
 */
public class BallView extends SvgClient {
	
	/**
	 * The position of a Ball.
	 */
	private IXholon position = null;
	
	/**
	 * The constant force exerted on the Ball.
	 */
	private IXholon constant = null;
	
	public BallView() {
	  super();
	  this.setup = ""; // GWT temporary fix for reflection to work
	}
	
	public void postConfigure() {
		if (position == null) {
			position = this.getXPath().evaluate("ancestor::StabilitySystem/Ball/Position", this);
		}
		if (constant == null) {
			constant = this.getXPath().evaluate("ancestor::StabilitySystem/Ball/Constant", this);
		}
		super.postConfigure();
	}
	
	public void act() {
	  // [-1.0 1.0] becomes [0.0 500.0]
		// parabola y = ax^2 (for now a=1.0)
		/*if (position != null) {
			double pos = position.getVal();
			if (pos >= -1.0 && pos <= 1.0) {
				double xPos = pos * 250.0 + 250.0;
				double yPos = 0.0;
				if (constant.getVal() > 0.0) {
					yPos = (pos * pos) * 250.0; // + 250.0;
				}
				else {
					yPos = (1.0 - (pos * pos)) * 250.0; // + 250.0;
				}
				// combine "Ball,cx,123.45,Ball,cy,987.65"
				xmlAttr(new StringBuilder()
				.append(toCsv("Ball", "cx", Double.toString(xPos)))
				.append(",")
				.append(toCsv("Ball", "cy", Double.toString(yPos)))
				.toString());
			}
		}*/
		super.act();
	}

	public IXholon getPosition() {
		return position;
	}

	public void setPosition(IXholon position) {
		this.position = position;
	}
	
	// required for AppGenerator
	public String getSetup() {
		return super.getSetup();
	}

  // required for AppGenerator
	public void setSetup(String setup) {
		super.setSetup(setup);
	}
	
	// required for AppGenerator
	public List<String> getViewBehavior() {
		return super.getViewBehavior();
	}

	// required for AppGenerator
	public void setViewBehavior(String viewBehavior) {
		super.setViewBehavior(viewBehavior);
	}
	
}
