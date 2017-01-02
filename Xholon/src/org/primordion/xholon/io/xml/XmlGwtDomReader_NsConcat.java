package org.primordion.xholon.io.xml;

import com.google.gwt.xml.client.Attr;

/**
 * XML GWT DOM Reader.
 * Handle namespace prefixes by concatenating the prefix and local part of node and attribute names.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on January 1, 2017)
 */
@SuppressWarnings("serial")
public class XmlGwtDomReader_NsConcat extends XmlGwtDomReader {
	
	/**
	 * default constructor
	 */
	public XmlGwtDomReader_NsConcat() {}

	@Override
	public String getName() {
		if (currentNode == null) {return null;}
		String fullName = currentNode.getNodeName();
    if (fullName != null && fullName.indexOf(":") != -1) {
      String[] arr = fullName.split(":", 2);
      if (isRequiredPrefix(arr[0])) {
        // return only the local part; the prefix will be returned separately by a call to getPrefix()
        fullName = arr[1];
      }
      else {
    		// concatenate the prefix and the localPart, without the separating ":"
        fullName = arr[0] + arr[1];
      }
    }
    return fullName;
	}

	@Override
	public String getAttributeName(int index) {
		if (currentNodeAttributes == null) {
			return null; // or "" ?
		}
		Attr attr = (Attr)currentNodeAttributes.item(index);
		if (attr == null) {return null;}
		String attrName = attr.getNodeName(); // or .getName() ?
    if (attrName != null && attrName.indexOf(":") != -1) {
      String[] arr = attrName.split(":", 2);
      attrName = arr[0] + arr[1];
    }
		return attrName;
	}

}
