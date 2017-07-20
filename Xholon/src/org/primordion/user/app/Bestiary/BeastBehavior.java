package org.primordion.user.app.Bestiary;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Beast Behavior
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
 */
public abstract class BeastBehavior extends XholonWithPorts implements CeBestiary {
	
	/**
	 * The individual beast that has this behavior.
	 */
	private IXholon beast = null;
	
	/*
	 * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
	 */
	public void postConfigure() {
		IXholon node = getParentNode();
		while (node != null) {
			if ((node.getXhc() != null) && (node.getXhc().hasAncestor("Beast"))) {
				setBeast(node);
				break;
			}
			else if ((node.getParentNode() != null) && (node.getParentNode().getXhc() != null) && (node.getParentNode().getXhc().hasAncestor("HabitatCell"))) {
				setBeast(node);
				break;
			}
			node = node.getParentNode();
		}
		super.postConfigure();
	}
	
	protected IXholon getBeast() {return beast;}
	protected void setBeast(IXholon beast) {this.beast = beast;}
}
