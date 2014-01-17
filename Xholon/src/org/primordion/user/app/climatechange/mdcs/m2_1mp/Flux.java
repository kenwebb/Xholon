package org.primordion.user.app.climatechange.mdcs.m2_1mp;

import org.primordion.xholon.util.StringHelper;

/**
 * A flux is an object (a mathematical vector)
 * that contains a magnitude and a direction.
 * It's roleName must be a specially-formatted combination of its initial point, terminal point, and optional subscript.
 * @author ken
 *
 */
public class Flux extends Xhmdcs {
	
	/**
	 * Get a tex representation of this flux object. ex:
	 * <p>roleName: SunSpc_sw</p>
	 * <p>tex: {{Sun}{Spc}}_{sw}</p>
	 * @return
	 */
	public String getTex() {
		String regex = StringHelper.format("%s|%s|%s",
		         "(?<=[A-Z])(?=[A-Z][a-z])",
		         "(?<=[^A-Z])(?=[A-Z])",
		         "(?<=[A-Za-z])(?=[^A-Za-z])"
		      );
		String[] str = this.getRoleName().split(regex);
		String outStr = "{{" + str[0] + "}{" + str[1]+ "}}";
		if (str.length > 2) {
			outStr += "_" + "{" + str[2].substring(1) + "}";
		}
		return outStr;
	}
	
	/**
	 * Get a wikipedia-compatible tex representation of this flux object. ex:
	 * <p>roleName: SunSpc_sw</p>
	 * <p>tex: &lt;math>\overrightarrow{{Sun}{Spc}}_{sw}&lt;/math></p>
	 * @return
	 */
	public String getWikiTex() {
		return "<math>\\overrightarrow" + getTex() + "</math>";
	}
	
	public String getSvgText() {
		return "TODO";
	}
	
	public String toString() {
		String outStr = super.toString();
		if (this.getRoleName() != null) {
			outStr += " " + this.getWikiTex();
		}
		return outStr;
	}
	
}
