/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primordion.xholon.common.mechanism.CeAttribute;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.util.Misc;
import org.primordion.xholon.util.IJavaTypes;
import org.primordion.xholon.util.MiscRandom;
import org.primordion.xholon.util.StringTokenizer;

/**
 * An Attribute is a passive object.
 * TODO An attribute should optionally not have a Xholon ID,
 * such as when the attribute will be removed by postConfigure().
 * TODO An attribute should optionally be removed in postConfigure().
 * TODO Each attribute class should have its own postConfigure().
 * TODO By default, postConfigure() will use reflection to call a setter on its parent,
 * and will then remove itself from the Xholon sub-tree.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on March 5, 2007)
 * @see IAttribute
 */
public abstract class Attribute extends Xholon implements IAttribute {
	private static final long serialVersionUID = -4446997600412434700L;
	protected String roleName = null;
	public Attribute() {id = IXholon.XHOLON_ID_NULL;}
	public String getRoleName() {return roleName;}
	public void setRoleName(String roleName) {this.roleName = roleName;}
	public String getName() {
		if (id == IXholon.XHOLON_ID_NULL) {
			return getName("r:c^^^");
		}
		else {
			return super.getName();
		}
	}
	public String toString() {return getName() + " " + getVal();}
	public boolean isContainer() {return xhc.isContainer();}
	public boolean isActiveObject() {return xhc.isActiveObject();}
	public boolean isPassiveObject() {return xhc.isPassiveObject();}
	public Class<?> getAttributeJavaClass() {return Object.class;}
	public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_UNKNOWN;}
	public void postConfigure()
	{
	  switch (getApp().getAttributePostConfigAction()) {
		case ATTR_DEFAULT:
		  System.out.println("ATTR_DEFAULT " + this);
			if (setJavaAttribute()) {
			  System.out.println("ATTR_DEFAULT 1");
				super.postConfigure();
				removeChild();
			}
			else {
			  System.out.println("ATTR_DEFAULT 2");
				setId(getNextId());
				super.postConfigure();
			}
			break;
		case ATTR_SETPARENT:
		  System.out.println("ATTR_SETPARENT");
			setJavaAttribute();
			super.postConfigure();
			break;
		case ATTR_REMOVE:
		  System.out.println("ATTR_REMOVE");
			super.postConfigure();
			removeChild();
			break;
		case ATTR_SETID:
		  System.out.println("ATTR_SETID");
			setId(getNextId());
			super.postConfigure();
			break;
		case ATTR_SETPARENT_REMOVE:
		  System.out.println("ATTR_SETPARENT_REMOVE");
			setJavaAttribute();
			super.postConfigure();
			removeChild();
			break;
		case ATTR_SETPARENT_SETID:
		  System.out.println("ATTR_SETPARENT_SETID");
			setJavaAttribute();
			setId(getNextId());
			super.postConfigure();
			break;
		default:
		  System.out.println("ATTR_???");
			// do nothing
			break;
		}
	}
	
	/**
	 * Set a Java attribute, based on this Attribute_ instance.
	 * @return true or false, depending on whether or not the Java attribute could be set.
	 */
	protected boolean setJavaAttribute()
	{
		if (getParentNode().isAttributeHandler()) {return false;} // overrides the default action
		IReflection ir = ReflectionFactory.instance();
		if (ir.isAttribute(getParentNode(), getRoleName(), getAttributeJavaClass())) {
			switch (getAttributeJavaClassType()) {
			case IJavaTypes.JAVACLASS_List:
				ir.setAttributeVal_UsingSetter(getParentNode(), getRoleName(),
						((Attribute_List)this).getVal_List(), List.class);
				break;
			case IJavaTypes.JAVACLASS_Map:
				ir.setAttributeVal_UsingSetter(getParentNode(), getRoleName(),
						((Attribute_Map)this).getVal_Map(), Map.class);
				break;
			case IJavaTypes.JAVACLASS_Set:
				ir.setAttributeVal_UsingSetter(getParentNode(), getRoleName(),
						((Attribute_Set)this).getVal_Set(), Set.class);
				break;
			default:
			  ir.setAttributeVal_UsingSetter(getParentNode(), getRoleName(),
						getVal_String(), 0, getAttributeJavaClassType());
				break;
			}
			return true;
		}
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXml(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
		String xhcName = getXhcName();
		if (getXhcId() == CeAttribute.Attribute_attributeCE) {
			xhcName = "attribute";
		}
		xmlWriter.writeStartElement(xhcName);
		toXmlAttributes(xholon2xml, xmlWriter);
		if (getXhc() != null) {
			if (getXhcId() != CeAttribute.Attribute_attributeCE) {
				xmlWriter.writeText(makeTextSafe(getVal_String()));
			}
		}
		IXholon childNode = getFirstChild();
		while (childNode != null) {
			childNode.toXml(xholon2xml, xmlWriter);
			childNode = childNode.getNextSibling();
		}
		xmlWriter.writeEndElement(xhcName);
	}
	
	/**
	 * If the input string contains any of these characters: < &
	 * then return the original string embedded in a CDATA tag.
	 * Else return the original string.
	 */
	protected String makeTextSafe(String str) {
		for (int i = 0; i < str.length(); i++) {
			switch (str.charAt(i)) {
			case '<':
			case '&':
				return "<![CDATA[" + str + "]]>";
			default: break;
			}
		}
		return str;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.IXmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
		xmlWriter.writeAttribute("roleName", this.getRoleName());
	}
	
	/**
	 * Is this a Xholon Attribute node?
	 * @param node
	 * @return true or false
	 */
	public static boolean isAttribute(IXholon node)
	{
		if (node == null) {return false;}
		if (node.getXhcName() == null) {return false;}
		return node.getXhcName().startsWith("Attribute_");
	}
	
	/**
	 * Count the number of tokens in a comma-separated String.
	 * ex: "one,two,three,four" has 4 tokens.
	 * @param str
	 * @return
	 */
	protected int countTokens(String str)
	{
		return new StringTokenizer(str, ",").countTokens();
	}
	
	/**
	 * <p>This is a simple attribute node that is only used for configuration.
	 * It provides a value for the named Java attribute in its parent.
	 * It will not be given a Xholon ID, and will not be retained in the Xholon tree.
	 * It will be available at configuration if its parent has a multiplicity > 1.
	 * In the first example below, it would cause the pheneVal variable in the Water node
	 * to be set to a value of 5000001.</p>
	 * <p>&lt;Water&gt;&lt;attribute name="pheneVal" value="5000001"/&gt;&lt;/Water&gt;</p>
	 * <p>Some other examples:</p>
	 * <p>&lt;attribute name="rate" value="0.0029137847"/&gt;</p>
	 * <p>&lt;attribute name="val" value="NY"/&gt;</p>
	 * 
	 * @see IAttribute
	 */
	public static class Attribute_attribute extends Attribute {
		private static final long serialVersionUID = 6375622248743474049L;
		protected String val;
		public String getVal_String() {return val;}
		public void setVal(String val) {this.val = val;}
		public String getValue() {return val;}
		public void setValue(String val) {this.val = val;}
		public String getName() {return roleName;}
		public void setName(String roleName) {this.roleName = roleName;}
		public String toString() {return getName() + " " + getVal_String();}
		public void postConfigure()
		{
		  if ("!".equals(val)) { // random boolean, for GameOfLife etc.
				getParentNode().setVal((MiscRandom.getRandomInt(0, 2) == 0) ? false : true);
			}
			else {
				// use reflection to set the value
				IReflection ir = ReflectionFactory.instance();
				ir.setAttributeVal(getParentNode(), getRoleName(), val, 0);
			}
			// do postConfigure() for all children and siblings before removing this node
			if (firstChild != null) {
				firstChild.postConfigure();
			}
			if (nextSibling != null) {
				nextSibling.postConfigure();
			}
			// remove this attribute node from the Xholon tree
			removeChild();
		}
		
		/*
		 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
		 */
		public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
		{
			xmlWriter.writeAttribute("name", getRoleName());
			xmlWriter.writeAttribute("value", getVal_String());
		}
	} // end Attribute_attribute
	
	/** @see IAttribute */
	public static class Attribute_boolean extends Attribute {
		private static final long serialVersionUID = 7048598301305003671L;
		protected boolean val;
		public Class<?> getAttributeJavaClass() {return boolean.class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_boolean;}
		public boolean getVal_boolean() {return val;}
		public void setVal(boolean val) {this.val = val;}
		public String getVal_String() {return Boolean.toString(val);}
		public void setVal(String val) {this.val = Boolean.valueOf(val).booleanValue();}
		public Object getVal_Object() {return Boolean.valueOf(val);}
		public void setVal(Object val) {this.val = ((Boolean)val).booleanValue();}
		public String toString() {return getName() + " " + getVal_boolean();}
	} // end Attribute_boolean
	
	/** @see IAttribute */
	public static class Attribute_byte extends Attribute {
		private static final long serialVersionUID = -8039045864733733940L;
		protected byte val;
		public Class<?> getAttributeJavaClass() {return byte.class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_byte;}
		public byte getVal_byte() {return val;}
		public void setVal(byte val) {this.val = val;}
		public String getVal_String() {return Byte.toString(val);}
		public void setVal(String val) {this.val = Byte.parseByte(val);}
		public Object getVal_Object() {return new Byte(val);}
		public void setVal(Object val) {this.val = ((Byte)val).byteValue();}
		public String toString() {return getName() + " " + getVal_byte();}
	} // end Attribute_byte
	
	/** @see IAttribute */
	public static class Attribute_char extends Attribute {
		private static final long serialVersionUID = 7008226625600629544L;
		protected char val;
		public Class<?> getAttributeJavaClass() {return char.class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_char;}
		public char getVal_char() {return val;}
		public void setVal(char val) {this.val = val;}
		public String getVal_String() {return Character.toString(val);}
		public void setVal(String val) {this.val = val.charAt(0);}
		public Object getVal_Object() {return new Character(val);}
		public void setVal(Object val) {this.val = ((Character)val).charValue();}
		public String toString() {return getName() + " " + getVal_char();}
	} // end Attribute_double
	
	/** @see IAttribute */
	public static class Attribute_double extends Attribute {
		private static final long serialVersionUID = 7945138659891206923L;
		protected double val;
		public Class<?> getAttributeJavaClass() {return double.class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_double;}
		public double getVal() {return val;}
		public double getVal_double() {return val;}
		public void setVal(double val) {this.val = val;}
		public void incVal(double incAmount) {val += incAmount;}
		public void decVal(double decAmount) {val -= decAmount;}
		public String getVal_String() {return Double.toString(val);}
		public void setVal(String val) {this.val = Double.parseDouble(val);}
		public Object getVal_Object() {return new Double(val);}
		public void setVal(Object val) {this.val = ((Double)val).doubleValue();}
		public String toString() {return getName() + " " + getVal_double();}
	} // end Attribute_double
	
	/** @see IAttribute */
	public static class Attribute_float extends Attribute {
		private static final long serialVersionUID = 8229921751807872667L;
		protected float val;
		public Class<?> getAttributeJavaClass() {return float.class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_float;}
		public double getVal() {return val;}
		public float getVal_float() {return val;}
		public void setVal(float val) {this.val = val;}
		public void incVal(double incAmount) {val += incAmount;}
		public void decVal(double decAmount) {val -= decAmount;}
		public String getVal_String() {return Float.toString(val);}
		public void setVal(String val) {this.val = Float.parseFloat(val);}
		public Object getVal_Object() {return new Float(val);}
		public void setVal(Object val) {this.val = ((Float)val).floatValue();}
		public String toString() {return getName() + " " + getVal_float();}
	} // end Attribute_float
	
	/** @see IAttribute */
	public static class Attribute_int extends Attribute {
		private static final long serialVersionUID = -6173634077939670129L;
		protected int val;
		public Class<?> getAttributeJavaClass() {return int.class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_int;}
		public double getVal() {return val;}
		public int getVal_int() {return val;}
		public void setVal(int val) {this.val = val;}
		public void incVal(int incAmount) {val += incAmount;}
		public void decVal(int decAmount) {val -= decAmount;}
		public String getVal_String() {return Integer.toString(val);}
		public void setVal(String val) {this.val = Integer.parseInt(val);}
		public Object getVal_Object() {return new Integer(val);}
		public void setVal(Object val) {this.val = ((Integer)val).intValue();}
		public String toString() {return getName() + " " + getVal_int();}
	} // end Attribute_int
	
	/** @see IAttribute */
	public static class Attribute_long extends Attribute {
		private static final long serialVersionUID = 3506829186754046366L;
		protected long val;
		public Class<?> getAttributeJavaClass() {return long.class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_long;}
		public double getVal() {return val;}
		public long getVal_long() {return val;}
		public void setVal(long val) {this.val = val;}
		public void incVal(int incAmount) {val += incAmount;}
		public void decVal(int decAmount) {val -= decAmount;}
		public String getVal_String() {return Long.toString(val);}
		public void setVal(String val) {this.val = Long.parseLong(val);}
		public Object getVal_Object() {return new Long(val);}
		public void setVal(Object val) {this.val = ((Long)val).longValue();}
		public String toString() {return getName() + " " + getVal_long();}
	} // end Attribute_long
	
	/** @see IAttribute */
	public static class Attribute_Object extends Attribute {
		private static final long serialVersionUID = -5707382871115794115L;
		protected Object val;
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = val;}
		public String getVal_String() {return "" + val;} // TODO
		public void setVal(String val) {this.val = val;} // TODO
		public String toString() {return getName() + " " + getVal_Object();}
	} // end Attribute_Object
	
	/** @see IAttribute */
	public static class Attribute_short extends Attribute {
		private static final long serialVersionUID = 2846492124747564125L;
		protected short val;
		public Class<?> getAttributeJavaClass() {return short.class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_short;}
		public double getVal() {return val;}
		public short getVal_short() {return val;}
		public void setVal(short val) {this.val = val;}
		public void incVal(int incAmount) {val += incAmount;}
		public void decVal(int decAmount) {val -= decAmount;}
		public String getVal_String() {return Short.toString(val);}
		public void setVal(String val) {this.val = Short.parseShort(val);}
		public Object getVal_Object() {return new Short(val);}
		public void setVal(Object val) {this.val = ((Short)val).shortValue();}
		public String toString() {return getName() + " " + getVal_short();}
	} // end Attribute_short
	
	/** @see IAttribute */
	public static class Attribute_String extends Attribute {
		private static final long serialVersionUID = -1388653838650753866L;
		protected String val;
		public Class<?> getAttributeJavaClass() {return String.class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_String;}
		public String getVal_String() {return val;}
		public void setVal(String val) {this.val = val;}
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = val.toString();}
		public String toString() {return getName() + " " + getVal_String();}
	} // end Attribute_String
	
	/** @see IAttribute */
	public static class Attribute_arrayboolean extends Attribute {
		private static final long serialVersionUID = -3805032920122827420L;
		protected boolean[] val;
		public Class<?> getAttributeJavaClass() {return boolean[].class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_arrayboolean;}
		public boolean[] getVal_arrayboolean() {return val;}
		public void setVal(boolean[] val) {this.val = val;}
		public String getVal_String() {
			String out = "";
			for (int i = 0; i < val.length; i++) {
				out += val[i];
				if (i < val.length - 1) {
					out += ",";
				}
			}
			return out;
		}
		public void setVal(String val) {
			int count = countTokens(val);
			this.val = new boolean[count];
			StringTokenizer st = new StringTokenizer(val, ",");
			for (int i = 0; i < count; i++) {
				this.val[i] = Boolean.valueOf(st.nextToken()).booleanValue();
			}
		}
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = (boolean[])val;}
		public String toString() {
			return getName() + " " + getVal_String();
		}
	} // end Attribute_arrayboolean
	
	/** @see IAttribute */
	public static class Attribute_arraybyte extends Attribute {
		private static final long serialVersionUID = 7270303052548363525L;
		protected byte[] val;
		public Class<?> getAttributeJavaClass() {return byte[].class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_arraybyte;}
		public byte[] getVal_arraybyte() {return val;}
		public void setVal(byte[] val) {this.val = val;}
		public String getVal_String() {
			String out = "";
			if ((val.length > 0) && (val[0] >= 0) && (val[0] <= 9) && (val[0] != '-')) {
				// these are normal numberic byte values
				for (int i = 0; i < val.length; i++) {
					out += val[i];
					if (i < val.length - 1) {
						out += ",";
					}
				}
			}
			else {
				// these are bytes in a String
				out = new String(val);
			}
			return out;
		}
		public void setVal(String val) {
			if (val.length() == 0) {
				this.val = new byte[0];
				return;
			}
			int count = countTokens(val);
			if (!Misc.isdigit(val.charAt(0) - '0')) {
				// this is a normal String whose characters should all be converted to bytes
				this.val = val.getBytes();
				return;
			}
			this.val = new byte[count];
			StringTokenizer st = new StringTokenizer(val, ",");
			for (int i = 0; i < count; i++) {
				this.val[i] = Byte.parseByte(st.nextToken());
			}
		}
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = (byte[])val;}
		public String toString() {
			return getName() + " " + getVal_String();
		}
	} // end Attribute_arraybyte
	
	/** @see IAttribute */
	public static class Attribute_arraychar extends Attribute {
		private static final long serialVersionUID = -8448083022364276840L;
		protected char[] val;
		public Class<?> getAttributeJavaClass() {return char[].class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_arraychar;}
		public char[] getVal_arraychar() {return val;}
		public void setVal(char[] val) {this.val = val;}
		public String getVal_String() {
			return new String(val);
		}
		public void setVal(String val) {
			this.val = val.toCharArray();
		}
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = (char[])val;}
		public String toString() {
			return getName() + " " + getVal_String();
		}
	} // end Attribute_arraychar
	
	/** @see IAttribute */
	public static class Attribute_arraydouble extends Attribute {
		private static final long serialVersionUID = -4396009851153953473L;
		protected double[] val;
		public Class<?> getAttributeJavaClass() {return double[].class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_arraydouble;}
		public double[] getVal_arraydouble() {return val;}
		public void setVal(double[] val) {this.val = val;}
		public String getVal_String() {
			String out = "";
			for (int i = 0; i < val.length; i++) {
				out += val[i];
				if (i < val.length - 1) {
					out += ",";
				}
			}
			return out;
		}
		public void setVal(String val) {
			int count = countTokens(val);
			this.val = new double[count];
			StringTokenizer st = new StringTokenizer(val, ",");
			for (int i = 0; i < count; i++) {
				this.val[i] = Double.parseDouble(st.nextToken());
			}
		}
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = (double[])val;}
		public String toString() {
			return getName() + " " + getVal_String();
		}
	} // end Attribute_arraydouble
	
	/** @see IAttribute */
	public static class Attribute_arrayfloat extends Attribute {
		private static final long serialVersionUID = -5139669355797913930L;
		protected float[] val;
		public Class<?> getAttributeJavaClass() {return float[].class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_arrayfloat;}
		public float[] getVal_arrayfloat() {return val;}
		public void setVal(float[] val) {this.val = val;}
		public String getVal_String() {
			String out = "";
			for (int i = 0; i < val.length; i++) {
				out += val[i];
				if (i < val.length - 1) {
					out += ",";
				}
			}
			return out;
		}
		public void setVal(String val) {
			int count = countTokens(val);
			this.val = new float[count];
			StringTokenizer st = new StringTokenizer(val, ",");
			for (int i = 0; i < count; i++) {
				this.val[i] = Float.parseFloat(st.nextToken());
			}
		}
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = (float[])val;}
		public String toString() {
			return getName() + " " + getVal_String();
		}
	} // end Attribute_arrayfloat
	
	/** @see IAttribute */
	public static class Attribute_arrayint extends Attribute {
		private static final long serialVersionUID = 780579722186155691L;
		protected int[] val;
		public Class<?> getAttributeJavaClass() {return int[].class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_arrayint;}
		public int[] getVal_arrayint() {return val;}
		public void setVal(int[] val) {this.val = val;}
		public String getVal_String() {
			String out = "";
			for (int i = 0; i < val.length; i++) {
				out += val[i];
				if (i < val.length - 1) {
					out += ",";
				}
			}
			return out;
		}
		public void setVal(String val) {
			int count = countTokens(val);
			this.val = new int[count];
			StringTokenizer st = new StringTokenizer(val, ",");
			for (int i = 0; i < count; i++) {
				this.val[i] = Integer.parseInt(st.nextToken());
			}
		}
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = (int[])val;}
		public String toString() {
			return getName() + " " + getVal_String();
		}
	} // end Attribute_arrayint
	
	/** @see IAttribute */
	public static class Attribute_arraylong extends Attribute {
		private static final long serialVersionUID = 6924362071678279130L;
		protected long[] val;
		public Class<?> getAttributeJavaClass() {return long[].class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_arraylong;}
		public long[] getVal_arraylong() {return val;}
		public void setVal(long[] val) {this.val = val;}
		public String getVal_String() {
			String out = "";
			for (int i = 0; i < val.length; i++) {
				out += val[i];
				if (i < val.length - 1) {
					out += ",";
				}
			}
			return out;
		}
		public void setVal(String val) {
			int count = countTokens(val);
			this.val = new long[count];
			StringTokenizer st = new StringTokenizer(val, ",");
			for (int i = 0; i < count; i++) {
				this.val[i] = Long.parseLong(st.nextToken());
			}
		}
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = (long[])val;}
		public String toString() {
			return getName() + " " + getVal_String();
		}
	} // end Attribute_arraylong
	
	/** @see IAttribute */
	public static class Attribute_arrayObject extends Attribute {
		private static final long serialVersionUID = -8105781732002105944L;
		protected Object[] val;
		public Class<?> getAttributeJavaClass() {return Object[].class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_UNKNOWN;}
		public Object[] getVal_arrayObject() {return val;}
		public void setVal(Object[] val) {this.val = val;}
		public String getVal_String() {
			String out = "";
			for (int i = 0; i < val.length; i++) {
				out += val[i];
				if (i < val.length - 1) {
					out += ",";
				}
			}
			return out;
		}
		public void setVal(String val) {
			// TODO not sure what to do here
			int count = countTokens(val);
			this.val = new Object[count];
			StringTokenizer st = new StringTokenizer(val, ",");
			for (int i = 0; i < count; i++) {
				this.val[i] = st.nextToken();
			}
		}
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = (Object[])val;}
		public String toString() {
			return getName() + " " + getVal_String();
		}
	} // end Attribute_arrayObject
	
	/** @see IAttribute */
	public static class Attribute_arrayshort extends Attribute {
		private static final long serialVersionUID = 8467934378037451576L;
		protected short[] val;
		public Class<?> getAttributeJavaClass() {return short[].class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_arrayshort;}
		public short[] getVal_arrayshort() {return val;}
		public void setVal(short[] val) {this.val = val;}
		public String getVal_String() {
			String out = "";
			for (int i = 0; i < val.length; i++) {
				out += val[i];
				if (i < val.length - 1) {
					out += ",";
				}
			}
			return out;
		}
		public void setVal(String val) {
			int count = countTokens(val);
			this.val = new short[count];
			StringTokenizer st = new StringTokenizer(val, ",");
			for (int i = 0; i < count; i++) {
				this.val[i] = Short.parseShort(st.nextToken());
			}
		}
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = (short[])val;}
		public String toString() {
			return getName() + " " + getVal_String();
		}
	} // end Attribute_arrayshort
	
	/** @see IAttribute */
	public static class Attribute_arrayString extends Attribute {
		private static final long serialVersionUID = 1792938530317052361L;
		protected String[] val;
		public Class<?> getAttributeJavaClass() {return String[].class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_arrayString;}
		public String[] getVal_arrayString() {return val;}
		public void setVal(String[] val) {this.val = val;}
		public String getVal_String() {
			String out = "";
			for (int i = 0; i < val.length; i++) {
				out += val[i];
				if (i < val.length - 1) {
					out += ",";
				}
			}
			return out;
		}
		public void setVal(String val) {
			int count = countTokens(val);
			this.val = new String[count];
			StringTokenizer st = new StringTokenizer(val, ",");
			for (int i = 0; i < count; i++) {
				this.val[i] = st.nextToken();
			}
		}
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = (String[])val;}
		public String toString() {
			return getName() + " " + getVal_String();
		}
	} // end Attribute_arrayString
	
	/** @see IAttribute */
	public static class Attribute_List extends Attribute {
		private static final long serialVersionUID = 4122140090663123477L;
		protected List<?> val = null;
		public Class<?> getAttributeJavaClass() {return List.class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_List;}
		public List<?> getVal_List() {return val;}
		public void setVal(List<?> val) {this.val = val;}
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = (List<?>)val;}
		public String toString() {return getName() + " " + val;}
		public void postConfigure()
		{
			if (hasChildNodes()) {
				val = (List<?>)getFirstChild();
			}
			super.postConfigure();
		}
	} // end Attribute_List
	
	/** @see IAttribute */
	public static class Attribute_Map extends Attribute {
		private static final long serialVersionUID = -2628519405219874453L;
		protected Map<?, ?> val = null;
		public Class<?> getAttributeJavaClass() {return Map.class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_Map;}
		public Map<?, ?> getVal_Map() {return val;}
		public void setVal(Map<?, ?> val) {this.val = val;}
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = (Map<?, ?>)val;}
		public String toString() {return getName() + " " + val;}
		public void postConfigure()
		{
			if (hasChildNodes()) {
				val = (Map<?, ?>)getFirstChild();
			}
			super.postConfigure();
		}
	} // end Attribute_Map
	
	/** @see IAttribute */
	public static class Attribute_Set extends Attribute {
		private static final long serialVersionUID = 6460252108608830570L;
		protected Set<?> val = null;
		public Class<?> getAttributeJavaClass() {return Map.class;}
		public int getAttributeJavaClassType() {return IJavaTypes.JAVACLASS_Set;}
		public Set<?> getVal_Set() {return val;}
		public void setVal(Set<?> val) {this.val = val;}
		public Object getVal_Object() {return val;}
		public void setVal(Object val) {this.val = (Set<?>)val;}
		public String toString() {return getName() + " " + val;}
		public void postConfigure()
		{
			if (hasChildNodes()) {
				val = (Set<?>)getFirstChild();
			}
			super.postConfigure();
		}
	} // end Attribute_Set
	
} // end Attribute
