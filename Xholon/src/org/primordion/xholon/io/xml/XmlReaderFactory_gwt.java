package org.primordion.xholon.io.xml;

import org.primordion.xholon.base.Xholon;

/**
 * XML Reader Factory, for GWT.
 * 
 * To change the XmlReader implementation from within a XholonWorkbook:
<params>
  <param name="XmlReaderImpl" value="XmlGwtDomReader_NsConcat"/>
</params>
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on August 21, 2013)
 */
@SuppressWarnings("serial")
public class XmlReaderFactory_gwt extends Xholon {
	
	/** The singleton instance of this class. */
	private static XmlReaderFactory_gwt xmlReaderFactory = null;
	
	public static IXmlReader getXmlReader(Object in) {
		IXmlReader xmlReader = null;
		if (xmlReaderFactory == null) {
			xmlReaderFactory = new XmlReaderFactory_gwt();
		}
		// try to make use of a XML GWT "DOM" parser
		if (xmlReader == null) {
			xmlReader = xmlReaderFactory.newGwtDomInstance();
			if (xmlReader != null) {
				xmlReader.createNew(in);
			}
		}
		return xmlReader;
	}
	
	protected IXmlReader newGwtDomInstance() {
	  String xmlReaderImpl = getXmlReaderImpl();
	  if ("XmlGwtDomReader_NsConcat".equals(xmlReaderImpl)) {
	    return new XmlGwtDomReader_NsConcat();
	  }
		return new XmlGwtDomReader();
	}
	
	protected native String getXmlReaderImpl() /*-{
	  return $wnd.xh.param("XmlReaderImpl");
	}-*/;
	
}
