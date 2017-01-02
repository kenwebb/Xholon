package org.primordion.xholon.io.xml;

/**
 * Test of XML GWT DOM Reader.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on August 21, 2013)
 */
public class XmlGwtDomReader_Test {
	
	/**
	 * Print a trace of all events.
	 * @throws Exception
	 */
	public void test1(String xmlString) throws Exception {
		consoleLog("\n" + xmlString);
		
		IXmlReader xmlReader = XmlReaderFactory_gwt.getXmlReader(xmlString);
		
		int event = xmlReader.getEventType();
		while (event != IXmlReader.END_DOCUMENT) {
			switch(event) {
			case IXmlReader.CDSECT: consoleLog("CDSECT"); break;
			case IXmlReader.COMMENT: consoleLog("COMMENT"); break;
			case IXmlReader.DOCDECL: consoleLog("DOCDECL"); break;
			case IXmlReader.END_DOCUMENT: consoleLog("END_DOCUMENT"); break;
			case IXmlReader.END_TAG:
				consoleLog("END_TAG " + xmlReader.getName());
				if ("#document".equals(xmlReader.getName())) {
					consoleLog("     #document");
				}
				break;
			case IXmlReader.ENTITY_REF: consoleLog("ENTITY_REF"); break;
			case IXmlReader.IGNORABLE_WHITESPACE: consoleLog("IGNORABLE_WHITESPACE"); break;
			case IXmlReader.NULL_EVENT: consoleLog("NULL_EVENT"); break;
			case IXmlReader.OTHER_TYPE: consoleLog("OTHER_TYPE"); break;
			case IXmlReader.PROCESSING_INSTRUCTION: consoleLog("PROCESSING_INSTRUCTION"); break;
			case IXmlReader.START_DOCUMENT: consoleLog("START_DOCUMENT"); break;
			case IXmlReader.START_TAG:
				String tagName = xmlReader.getName();
				String attrs = "";
				int attrCount = xmlReader.getAttributeCount();
				for (int i = 0; i < attrCount; i++) {
					attrs += " " + xmlReader.getAttributeName(i) + "=" + xmlReader.getAttributeValue(i);
				}
				consoleLog("START_TAG " + tagName + attrs);
				break;
			case IXmlReader.TEXT:
				String text = xmlReader.getText().trim();
				if (text.length() > 0) {
					consoleLog("TEXT " + text);
				}
				break;
			default: consoleLog("no such event type: " + event);break;
			}
			event = xmlReader.next();
		}
		consoleLog("END_DOCUMENT");
	}
	
	protected native void consoleLog(String str) /*-{
	  $wnd.console.log(str);
	}-*/;
	
	public static void main(String[] args) throws Exception {
		XmlGwtDomReader_Test t = new XmlGwtDomReader_Test();
		// test using XML string from http://www.gwtproject.org/doc/latest/DevGuideCodingBasicsXML.html
		String xmlString =
"<?xml version=\"1.0\" ?>"
+ "<message>"
+  "<header>"
+    "<to displayName=\"Richard\" address=\"rick@school.edu\" />"
+    "<from displayName=\"Joyce\" address=\"joyce@website.com\" />"
+    "<sent>2007-05-12T12:03:55Z</sent>"
+    "<subject>Re: Flight info</subject>"
+  "</header>"
+  "<body>I'll pick you up at the airport at 8:30.  See you then!</body>"
+ "</message>";
		t.test1(xmlString);
	}
	
}
