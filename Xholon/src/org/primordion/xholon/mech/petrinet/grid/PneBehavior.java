package org.primordion.xholon.mech.petrinet.grid;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Petri net entity (Pne) Behavior.
 * A Pne can be a place or transition.
 * This class is intended for use in a Grid.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 30, 2012)
 */
public abstract class PneBehavior extends XholonWithPorts {
	private static final long serialVersionUID = 8073061888438810806L;
	
	/**
	 * The individual Petri net entity that has this behavior.
	 */
	private IXholon pne = null;
	
	/*
	 * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
	 */
	public void postConfigure() {
		createPne();
		super.postConfigure();
	}
	
	protected void createPne() {
		IXholon node = getParentNode();
		while (node != null) {
			if (node.getXhc().hasAncestor("PlacePN")) {
				setPne(node);
				break;
			}
			else if (node.getXhc().hasAncestor("TransitionPN")) {
				setPne(node);
				break;
			}
			else if (node.getParentNode().getXhc().hasAncestor("GridCell")) {
				setPne(node);
				break;
			}
			node = node.getParentNode();
		}
	}
	
	public IXholon getGridOwner() {
		return getPne().getParentNode().getParentNode().getParentNode().getParentNode();
	}
	
	public IXholon getPne() {
		if (pne == null) {
			// bug: pne is occasionally not set yet; race condition ?
			println("pne = null: " + this.getName());
			createPne();
		}
		return pne;
	}
	
	public void setPne(IXholon pne) {this.pne = pne;}
}
