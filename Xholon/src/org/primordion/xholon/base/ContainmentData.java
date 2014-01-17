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

package org.primordion.xholon.base;

/**
 * ContainmentData is used at configuration time as a temporary holding place for
 * data read in from a CompositeStructureHierarchy.xml file. Each instance of
 * ContainmentData contains data read in from one element in the XML file.
 * But as of version 0.8, this class is only used to package some constants
 * that have to do with configuration.
 * TODO this should be deprecated
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jun 21, 2005)
 */
public class ContainmentData {
	
	// Constants derived from CSH.xml, used in Xholon.setVariableValue()
	/** ex: V(val)r V(avgVehicleKilometers)n5551V(proportionLicensed)n0.492 */
	public static final char CD_VARIABLE = 0x0001; //'V';
	/** ex: V(val)r CSH.xml <attribute name="val" value="r"/> */
	public static final char CD_RANDOM   = '!'; // 'r'
	/** ex: V(pheneVal)n100000 CSH.xml <attribute name="pheneVal" value="100000"/> */
	public static final char CD_NUMBER   = 0x0002; //'n';
	
	// Constants derived from CD.xml, used in XholonWithPorts.postConfigure()
	/** ex: CGmt CD.xml <config instruction="Gmt"/> */
	public static final char CD_CONFIG      = 0x0003; //'C';
	/** ex: port[P_SM_PRD1]="#xpointer(ancestor::Cytoplasm/Cytosol/Pyruvate)" */
	public static final char CD_PORT        = 'p'; // (p)ort
	/** ex: port[Ticks0]{1}{false}{}{SOUT_TICK,}^replication[0]="#xpointer(ancestor::Hoist)" */
	public static final char CD_REPLICATION = 'r'; // (r)eplication
	/** ex: port[ONE]="..."^port[TWO]="..."^ */
	public static final char CD_SEPARATOR   = 0x0004; //'^';
	
}

