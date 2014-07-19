/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2009 Ken Webb
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

package org.primordion.xholon.io.xml;

/**
 * Pretty-print an XML string, using vkbeautify.
 *
 * vkBeautify - javascript plugin to pretty-print or minify text in XML, JSON, CSS and SQL formats.
 * Version - 0.99.00.beta 
 * Copyright (c) 2012 Vadim Kiryukhin
 * vkiryukhin @ gmail.com
 * http://www.eslinstructor.net/vkbeautify/
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on September 5, 2012)
 */
public class XmlPrettyPrinter {
	
	private int indentAmount = 2;
	private String omitXmlDeclaration = "no";
	private String encoding = "UTF-8";
	
  public String format(String xml) {
    vkbeautifyInit();
    return vkbeautify(xml, indentAmount);
  }
  
  public native String vkbeautify(String xml, int indent) /*-{
    if (typeof $wnd.vkbeautify === 'undefined') {return xml;}
    return $wnd.vkbeautify.xml(xml, indent);
  }-*/;
  
  public native void vkbeautifyInit() /*-{
if (typeof $wnd.vkbeautify !== 'undefined') {return;}

(function() {

function createShiftArr(step) {
  var space = '    ';
  if ( isNaN(parseInt(step)) ) {  // argument is string
    space = step;
  } else { // argument is integer
    switch(step) {
      case 1: space = ' '; break;
      case 2: space = '  '; break;
      case 3: space = '   '; break;
      case 4: space = '    '; break;
      case 5: space = '     '; break;
      case 6: space = '      '; break;
      case 7: space = '       '; break;
      case 8: space = '        '; break;
      case 9: space = '         '; break;
      case 10: space = '          '; break;
      case 11: space = '           '; break;
      case 12: space = '            '; break;
    }
  }
  var shift = ['\n']; // array of shifts
  for(ix=0;ix<100;ix++){
    shift.push(shift[ix]+space); 
  }
  return shift;
}

function vkbeautify(){
  this.step = '    '; // 4 spaces
  this.shift = createShiftArr(this.step);
};

vkbeautify.prototype.xml = function(text,step) {

  var ar = text.replace(/>\s{0,}</g,"><")
         .replace(/</g,"~::~<")
         .replace(/\s*xmlns\:/g,"~::~xmlns:")
         .replace(/\s*xmlns\=/g,"~::~xmlns=")
         .split('~::~'),
    len = ar.length,
    inComment = false,
    deep = 0,
    str = '',
    ix = 0,
    shift = step ? createShiftArr(step) : this.shift;

    for(ix=0;ix<len;ix++) {
      // start comment or <![CDATA[...]]> or <!DOCTYPE //
      if(ar[ix].search(/<!/) > -1) { 
        str += shift[deep]+ar[ix];
        inComment = true; 
        // end comment  or <![CDATA[...]]> //
        if(ar[ix].search(/-->/) > -1 || ar[ix].search(/\]>/) > -1 || ar[ix].search(/!DOCTYPE/) > -1 ) { 
          inComment = false; 
        }
      } else 
      // end comment  or <![CDATA[...]]> //
      if(ar[ix].search(/-->/) > -1 || ar[ix].search(/\]>/) > -1) { 
        str += ar[ix];
        inComment = false; 
      } else 
      // <elm></elm> //
      if( /^<\w/.exec(ar[ix-1]) && /^<\/\w/.exec(ar[ix]) &&
        /^<[\w:\-\.\,]+/.exec(ar[ix-1]) == /^<\/[\w:\-\.\,]+/.exec(ar[ix])[0].replace('/','')) { 
        str += ar[ix];
        if(!inComment) deep--;
      } else
       // <elm> //
      if(ar[ix].search(/<\w/) > -1 && ar[ix].search(/<\//) == -1 && ar[ix].search(/\/>/) == -1 ) {
        str = !inComment ? str += shift[deep++]+ar[ix] : str += ar[ix];
      } else 
       // <elm>...</elm> //
      if(ar[ix].search(/<\w/) > -1 && ar[ix].search(/<\//) > -1) {
        str = !inComment ? str += shift[deep]+ar[ix] : str += ar[ix];
      } else 
      // </elm> //
      if(ar[ix].search(/<\//) > -1) { 
        str = !inComment ? str += shift[--deep]+ar[ix] : str += ar[ix];
      } else 
      // <elm/> //
      if(ar[ix].search(/\/>/) > -1 ) { 
        str = !inComment ? str += shift[deep]+ar[ix] : str += ar[ix];
      } else 
      // <? xml ... ?> //
      if(ar[ix].search(/<\?/) > -1) { 
        str += shift[deep]+ar[ix];
      } else 
      // xmlns //
      if( ar[ix].search(/xmlns\:/) > -1  || ar[ix].search(/xmlns\=/) > -1) { 
        str += shift[deep]+ar[ix];
      } 
      
      else {
        str += ar[ix];
      }
    }
    
  return  (str[0] == '\n') ? str.slice(1) : str;
}

vkbeautify.prototype.xmlmin = function(text, preserveComments) {
  var str = preserveComments ? text
    : text.replace(/\<![ \r\n\t]*(--([^\-]|[\r\n]|-[^\-])*--[ \r\n\t]*)\>/g,"")
     .replace(/[ \r\n\t]{1,}xmlns/g, ' xmlns');
  return  str.replace(/>\s{0,}</g,"><"); 
}

$wnd.vkbeautify = new vkbeautify();

})();
  }-*/;

  public int getIndentAmount() {
		return indentAmount;
	}

	public void setIndentAmount(int indentAmount) {
		this.indentAmount = indentAmount;
	}

	public String getOmitXmlDeclaration() {
		return omitXmlDeclaration;
	}

	public void setOmitXmlDeclaration(String omitXmlDeclaration) {
		this.omitXmlDeclaration = omitXmlDeclaration;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
