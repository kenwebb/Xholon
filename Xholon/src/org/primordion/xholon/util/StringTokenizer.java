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

package org.primordion.xholon.util;

/**
 * This is a replacement for the Java StringTokenizer class, for use with MIDP, GWT, J2SE.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on December 4, 2006)
 */
public class StringTokenizer {

	private char stBuff[] = null;
	private char dmBuff[] = null;
	private int stIx = 0; // index into stBuff
	private int stBuffLen = 0;
	private int dmBuffLen = 0;
	private int currentTokenCount = 0;
	private int maxTokens = 0;
	
	/**
	 * constructor - not yet implemented
	 * @param str A String that needs to be tokenized.
	 * @param delim A list of delimiter characters (ex: ",").
	 * @param returnDelims flag indicating whether to return the delimiters as tokens
	 */
	public StringTokenizer(String str, String delim, boolean returnDelims) {
		// TODO
	}
	
	/**
	 * constructor
	 * @param str A String that needs to be tokenized.
	 * @param delim A list of delimiter characters (ex: ",").
	 */
	public StringTokenizer(String str, String delim) {
		stBuff = str.toCharArray();
		dmBuff = delim.toCharArray();
		stIx = 0;
		stBuffLen = str.length();
		dmBuffLen = delim.length();
		setMaxTokens();
	}
	
	/**
	 * constructor
	 * Uses a default set of whitespace delimiters.
	 * @param str A String that needs to be tokenized.
	 */
	public StringTokenizer(String str) {
		stBuff = str.toCharArray();
		String delim = " \t\n\r\f"; // default delimiters are whitespace
		dmBuff = delim.toCharArray();
		stIx = 0;
		stBuffLen = str.length();
		dmBuffLen = delim.length();
		setMaxTokens();
	}
	
	/**
	 * Does the String contain any more tokens?
	 * @return true or false
	 */
	public boolean hasMoreTokens() {
		if (currentTokenCount >= maxTokens) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Returns next token, or null.
	 * @return Next token.
	 */
	public String nextToken() {
		int offset = stIx;
		while ((stIx < stBuffLen) && (!isDelim(stBuff[stIx]))) {
			stIx++;
		}
		int length = stIx - offset;
		stIx++;
		// bipass any additional delimiters
		while ((stIx < stBuffLen) && (isDelim(stBuff[stIx]))) {
			stIx++;
		}
		currentTokenCount++;
		return new String(stBuff, offset, length);
	}
	
	/**
	 * Returns next token, or null.
	 * @param delim the new delimiters
	 * @return Next token, after switching to the new delimiters.
	 */
	public String nextToken(String delim) {
		dmBuff = delim.toCharArray();
		dmBuffLen = delim.length();
		return nextToken();
	}
	
	/**
	 * Returns the number of tokens in the String.
	 * @return Token count.
	 */
	public int countTokens() {
		return maxTokens;
	}
	
	/**
	 * Set the maximum number of tokens in the String.
	 */
	protected void setMaxTokens() {
		maxTokens = 0;
		int state = 0; // initially OFF
		for (int i = 0; i < stBuffLen; i++) {
			switch (state) {
			case 0: // OFF
				if (!isDelim(stBuff[i])) {
					maxTokens++;
					state = 1;
				}
				break;
			case 1: // ON
				if (isDelim(stBuff[i])) {
					state = 0;
				}
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * Is this a delimiter character?
	 * @param c A char from the String.
	 * @return true or false
	 */
	protected boolean isDelim(char c) {
		for (int i = 0; i < dmBuffLen; i++) {
			if (c == dmBuff[i]) {
				return true;
			}
		}
		return false;
	}
}
