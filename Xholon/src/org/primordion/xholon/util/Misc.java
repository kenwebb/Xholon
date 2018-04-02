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

package org.primordion.xholon.util;

import org.primordion.xholon.base.ContainmentData;

/**
 * This is a mix of static methods that don't have a home elsewhere.
 * The methods all have to do with random numbers, and conversion between
 * ASCII and various numeric formats.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jul 7, 2005)
 */
public class Misc implements IJavaTypes {

	/**
	 * Convert an ascii string into a double.
	 * @param str The string that contains the double.
	 * @param ixStart The start position within the string (0 indexed).
	 * @return A double.
	 */
	public static double atod(String str, int ixStart) {
		if ((str == null) || (str.equals(""))) {
			return 0.0;
		}
		if ((ixStart < 0) || (ixStart >= str.length())) {
			return 0.0;
		}
		int ixEnd = ixStart;
		char myChar = str.charAt(ixEnd);
		int intVal = getNumericValue(myChar);
		int len = str.length();
		while (isdigit(intVal) || myChar == '-' || myChar == '.' || myChar == 'e' || myChar == 'E') {
			ixEnd++;
			if (ixEnd >= len) {
				break; // exit loop if reach end of string
			}
			myChar = str.charAt(ixEnd);
			intVal = getNumericValue(myChar);
		}
		String digits = str.substring(ixStart, ixEnd);
		if (digits.equals("")) {
			return 0.0;
		}
		else if (digits.equals("-")) { // prevent java.lang.NumberFormatException
			return -0.0;
		}
		return Double.parseDouble(digits);
	}
	
	/**
	 * Convert an ascii string into a float.
	 * @param str The string that contains the float.
	 * @param ixStart The start position within the string (0 indexed).
	 * @return A float.
	 */
	public static float atof(String str, int ixStart) {
		if ((str == null) || (str.equals(""))) {
			return 0.0f;
		}
		if ((ixStart < 0) || (ixStart >= str.length())) {
			return 0.0f;
		}
		int ixEnd = ixStart;
		char myChar = str.charAt(ixEnd);
		int intVal = getNumericValue(myChar);
		int len = str.length();
		while (isdigit(intVal) || myChar == '-' || myChar == '.' || myChar == 'e' || myChar == 'E') {
			ixEnd++;
			if (ixEnd >= len) {
				break; // exit loop if reach end of string
			}
			myChar = str.charAt(ixEnd);
			intVal = getNumericValue(myChar);
		}
		String digits = str.substring(ixStart, ixEnd);
		if (digits.equals("")) {
			return 0.0f;
		}
		return Float.parseFloat(digits);
	}
	
	/**
	 * Convert an ascii string into an int.
	 * @param str The string that contains the int.
	 * @param ixStart The start position within the string (0 indexed).
	 * @return An int.
	 */
	public static int atoi(String str, int ixStart) {
		if ((str == null) || (str.equals(""))) {
			return 0;
		}
		if ((ixStart < 0) || (ixStart >= str.length())) {
			return 0;
		}
		int ixEnd = ixStart;
		char myChar = str.charAt(ixEnd);
		int intVal = getNumericValue(myChar);
		int len = str.length();
		while (isdigit(intVal) || myChar == '-') {
			ixEnd++;
			if (ixEnd >= len) {
				break; // exit loop if reach end of string
			}
			myChar = str.charAt(ixEnd);
			intVal = getNumericValue(myChar);
		}
		String digits = str.substring(ixStart, ixEnd);
		if (digits.equals("")) {
			return 0;
		}
		return Integer.parseInt(digits);
	}
	
	/**
	 * Convert an ascii string into a long.
	 * @param str The string that contains the long.
	 * @param ixStart The start position within the string (0 indexed).
	 * @return A long.
	 */
	public static long atol(String str, int ixStart) {
		if ((str == null) || (str.equals(""))) {
			return 0L;
		}
		if ((ixStart < 0) || (ixStart >= str.length())) {
			return 0L;
		}
		int ixEnd = ixStart;
		char myChar = str.charAt(ixEnd);
		int intVal = getNumericValue(myChar);
		int len = str.length();
		while (isdigit(intVal) || myChar == '-') {
			ixEnd++;
			if (ixEnd >= len) {
				break; // exit loop if reach end of string
			}
			myChar = str.charAt(ixEnd);
			intVal = getNumericValue(myChar);
		}
		String digits = str.substring(ixStart, ixEnd);
		if (digits.equals("")) {
			return 0L;
		}
		return Long.parseLong(digits);
	}
	
	/**
	 * Convert an ascii string into a boolean.
	 * @param str The string that contains the boolean.
	 * @param ixStart The start position within the string (0 indexed).
	 * @return A boolean.
	 */
	public static boolean atob(String str, int ixStart) {
		boolean rc = false;
		if ((str == null) || (str.equals(""))) {
			rc = false;
		}
		if ((ixStart < 0) || (ixStart >= str.length())) {
			rc = false;
		}
		if ((str.startsWith("true", ixStart)) || (str.startsWith("TRUE", ixStart))) {
			rc = true;
		}
		return rc;
	}
	
	/**
	 * Is this a digit, a value between 0 and 9.
	 * @param digit The digit being tested.
	 * @return true or false
	 */
	public static boolean isdigit(int digit) {
		return ((digit >= 0) && (digit <= 9));
	}
	
	/**
	 * Is this a math symbol?
	 * This method takes the place of calling if (Character.getType(symbol) == Character.MATH_SYMBOL)
	 * @param symbol A character to be tested to see if it's a math symbol.
	 * @return true or false
	 */
	public static boolean isMathSymbol(char symbol) {
		switch (symbol) {
		case '=': // =
		case '!': // !=
		case '<': // <  <=
		case '>': // >  >=
			return true;
		}
		return false;
	}
	
	/**
	 * Is this an even number?
	 * @param val An integer that may be even or odd.
	 * @return Whether or not the intgeger is even.
	 */
	public static boolean isEven(int val) {
		if ((val % 2) == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Get the numeric value of a char.
	 * This method takes the place of calling Character.getNumericValue(char),
	 * but only handles ASCII characters between '0' and '9'.
	 * It cannot handle unicode characters.
	 * @param ch A character.
	 * @return An integer between 0 and 9, or -1 if the character does not have a numeric value.
	 */
	public static int getNumericValue(char ch) {
		int numericValue = -1;
		switch (ch) {
		case '0': numericValue = 0; break;
		case '1': numericValue = 1; break;
		case '2': numericValue = 2; break;
		case '3': numericValue = 3; break;
		case '4': numericValue = 4; break;
		case '5': numericValue = 5; break;
		case '6': numericValue = 6; break;
		case '7': numericValue = 7; break;
		case '8': numericValue = 8; break;
		case '9': numericValue = 9; break;
		default: break;
		}
		return numericValue;
	}
	
	/**
	 * Return the boolean equivalent of a String.
	 * If the String does not encode a valid boolean, then return false.
	 * @param b A String that must have a value of "true", "false", "TRUE", or "FALSE".
	 * @return true or false
	 */
	public static boolean booleanValue(String b) {
		if (b.equals("true") || b.equals("TRUE")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Get the Java type of the input data.
	 * examples:
	 * JAVACLASS_boolean: true false
	 * JAVACLASS_double : 123.0 2.6e-5 123.0e+5 -123.0
	 * JAVACLASS_float  : 123.0f -123.0f
	 * JAVACLASS_int    : 123 -123
	 * JAVACLASS_long   : 9060404178364531554L 543210l (ends with upper or lowercase L)
	 * JAVACLASS_String : abc
	 * @param s Some data.
	 * @return The Java type.
	 */
	public static int getJavaDataType(String s) {
		int javaType = JAVACLASS_String;
		if ((s == null) || (s.length() == 0)) {return javaType;}
		if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
			javaType = JAVACLASS_boolean;
		}
		else {
			int ix = 0;
			if ((s.charAt(ix) == '-') || (s.charAt(ix) == '+')) { // initial - or +
				ix++;
			}
			while (ix < s.length() && getNumericValue(s.charAt(ix)) != -1) {
				ix++;
			}
			if (ix > 0) { // have found at least one digit, so it could be a numeric type
				if ((ix == s.length()) || (s.charAt(ix) == ContainmentData.CD_VARIABLE)) { // the entire string consists of digits
					javaType = JAVACLASS_int;
					// test if it's too large a positive or negative number to be an int
					try {
					  Integer.parseInt(s);
					} catch (NumberFormatException e) {
					  javaType = JAVACLASS_double;
					}
				}
				else if ((ix + 1 == s.length()) && ((s.charAt(ix) == 'L') || (s.charAt(ix) == 'l'))) {
					javaType = JAVACLASS_long;
				}
				else if ((s.charAt(ix) == '.') || (s.charAt(ix) == 'e')) {
					ix++;
					if (ix < s.length()) {
						while (ix < s.length()) {
							if (getNumericValue(s.charAt(ix)) == -1) { // not a digit
								if (s.charAt(ix) == 'e' || s.charAt(ix) == '-' || s.charAt(ix) == '+') {}
								else {break;}
							}
							ix++;
						}
						if (ix == s.length() || (s.charAt(ix) == ContainmentData.CD_VARIABLE)) { // the string must be a double
							javaType = JAVACLASS_double;
						}
						else if ((s.charAt(ix) == 'f') && ((ix+1 == s.length())) || (s.charAt(ix+1) == ContainmentData.CD_VARIABLE)) {
							javaType = JAVACLASS_float;
						}
					}
				}
			}
		}
		return javaType;
	}

}
