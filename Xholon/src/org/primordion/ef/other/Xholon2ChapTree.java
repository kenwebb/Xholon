/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

package org.primordion.ef.other;

import org.primordion.xholon.service.ef.IXholon2GraphFormat;

/**
 * Export a Xholon model in Chap Network/Tree format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on November 23, 2013)
 * @see <a href="chap.almende.com">Almende CHAP site</a>
 * @see <a href="chap.almende.com/visualization/network/">CHAP Network</a>
 * @see <a href="almende.github.io/chap-links-library/index.html">CHAP doc and examples</a>
 * @see <a href="almende.github.io/chap-links-library/js/network/doc/">CHAP doc</a>
 */
@SuppressWarnings("serial")
public class Xholon2ChapTree extends Xholon2ChapNetwork implements IXholon2GraphFormat {
	
	/**
	 * Constructor.
	 */
	public Xholon2ChapTree() {
	  super(false, true, 99, "treeview");
	}
  
}
