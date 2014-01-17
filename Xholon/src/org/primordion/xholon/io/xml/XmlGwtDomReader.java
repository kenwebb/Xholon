package org.primordion.xholon.io.xml;

import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Attr;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;

/**
 * XML GWT DOM Reader.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on August 21, 2013)
 */
@SuppressWarnings("serial")
public class XmlGwtDomReader extends XmlReader {

	/** The XML string. */
	private Object in = null;
	
	/** A Document Object Model (DOM) document. */
	private Document document = null;
	
	/** The current node in the DOM. The node currently pointed to by the DOM cursor. */
	private Node currentNode = null;
	
	/** The attributes of the currentNode. */
	private NamedNodeMap currentNodeAttributes = null;
	
	/** The IXholonReader (Stax) event currently associated with the currentNode. */
	private int eventType = IXmlReader.START_DOCUMENT;
	
	// state of the currentNode
	/** The currentNode is ready to return its "normal" event. */
	private static final int STATE_READY = 1;
	/** The currentNode (an Element) has just returned its END_TAG event. */
	private static final int STATE_END_TAG_RETURNED = 2;
	
	/**
	 * Whether to return START_TAG or END_TAG .
	 */
	private int state = STATE_READY;
	
	/**
	 * default constructor
	 */
	public XmlGwtDomReader() {}
	
	@Override
	public void createNew(Object in) {
		this.in = in;
		if (in instanceof String) {
			try {
				// parse the XML document into a DOM
				document = XMLParser.parse((String)in);
			} catch (DOMException e) {
				Window.alert("Could not parse XML document: " + ((String)in).substring(0, 100));
			}
		}
		else {
			Window.alert("XML input to createNew() must be a String.");
		}
		currentNode = document;
		currentNodeAttributes = currentNode.getAttributes();
		eventType = START_DOCUMENT;
	}

	@Override
	public int getEventType() {
		return eventType;
	}

	@Override
	public int next() {
		switch (state) {
		case STATE_READY: {
			Node newNode = null;
			switch (currentNode.getNodeType()) {
			case Node.ELEMENT_NODE:
				newNode = currentNode.getFirstChild();
				if (newNode == null) {
					// this is a leaf Element, that needs to return an END_TAG
					// DO NOT change the value of currentNode
					eventType = END_TAG;
					currentNodeAttributes = null;
					state = STATE_END_TAG_RETURNED;
					return eventType;
				}
				break;
			default:
				newNode = currentNode.getFirstChild();
				if (newNode == null) {
					newNode = currentNode.getNextSibling();
				}
				if (newNode == null) {
					newNode = currentNode.getParentNode();
					if (newNode != null) {
						// this is an Element, that needs to return an END_TAG
						currentNode = newNode;
						eventType = END_TAG;
						currentNodeAttributes = null;
						state = STATE_END_TAG_RETURNED;
						return eventType;
					}
				}
				break;
			}

			currentNode = newNode;
			if (currentNode == null) {
				eventType = END_DOCUMENT;
				currentNodeAttributes = null;
				return eventType;
			} else {
				currentNodeAttributes = currentNode.getAttributes();
			}
			break;
		}

		case STATE_END_TAG_RETURNED: {
			// currentNode is a leaf Element,
			// that returned an END_TAG on the previous call to next()
			state = STATE_READY;
			Node newNode = currentNode.getNextSibling();
			if (newNode == null) {
				newNode = currentNode.getParentNode();
				if (newNode != null) {
					currentNode = newNode;
					if (currentNode.getNodeType() == Node.DOCUMENT_NODE) {
						eventType = END_DOCUMENT;
					}
					else {
						eventType = END_TAG;
					}
					currentNodeAttributes = null;
					state = STATE_END_TAG_RETURNED;
					return eventType;
				}
			}
			currentNode = newNode;
			if (currentNode == null) {
				eventType = END_DOCUMENT;
				currentNodeAttributes = null;
				return eventType;
			} else {
				currentNodeAttributes = currentNode.getAttributes();
			}
			break;
		}
		default:
			break;
		}

		switch (currentNode.getNodeType()) {
		case Node.ELEMENT_NODE:
			eventType = START_TAG;
			break;
		case Node.ATTRIBUTE_NODE:
			System.out.println("==> ATTRIBUTE_NODE");
			eventType = OTHER_TYPE;
			break;
		case Node.TEXT_NODE:
			eventType = TEXT;
			break;
		case Node.CDATA_SECTION_NODE:	
			System.out.println("==> CDATA_SECTION_NODE");
		  //CDATASection cdata = (CDATASection)currentNode;
	    //System.out.println("The CDATA has text: [" + cdata.getData() + "]");
			//eventType = CDSECT;
			eventType = TEXT; // CDATASection is a subclass of Text so this should work
			break;
		case Node.ENTITY_REFERENCE_NODE:
			System.out.println("==> ENTITY_REFERENCE_NODE");
			eventType = ENTITY_REF;
			break;
		case Node.ENTITY_NODE:
			System.out.println("==> ENTITY_NODE");
			eventType = OTHER_TYPE;
			break;
		case Node.PROCESSING_INSTRUCTION_NODE:
			System.out.println("==> PROCESSING_INSTRUCTION_NODE");
			eventType = PROCESSING_INSTRUCTION;
			break;
		case Node.COMMENT_NODE:
			eventType = COMMENT;
			break;
		case Node.DOCUMENT_NODE:
			System.out.println("==> DOCUMENT_NODE");
			eventType = OTHER_TYPE;
			break;
		case Node.DOCUMENT_TYPE_NODE:
			System.out.println("==> DOCUMENT_TYPE_NODE");
			eventType = OTHER_TYPE;
			break;
		case Node.DOCUMENT_FRAGMENT_NODE:
			System.out.println("==> DOCUMENT_FRAGMENT_NODE");
			eventType = OTHER_TYPE;
			break;
		case Node.NOTATION_NODE:
			System.out.println("==> NOTATION_NODE");
			eventType = OTHER_TYPE;
			break;
		default:
			System.out.println("==> unknown node");
			eventType = OTHER_TYPE;
			break;
		}
		return eventType;
	}

	@Override
	public String getName() {
		if (currentNode == null) {return null;}
		// get just the local part of the name, excluding the prefix
		return currentNode.getNodeName();
	}

	@Override
	public String getText() {
		String text = currentNode.getNodeValue();
		return text;
	}

	@Override
	public String getAttributeName(int index) {
		if (currentNodeAttributes == null) {
			return null; // or "" ?
		}
		Attr attr = (Attr)currentNodeAttributes.item(index);
		if (attr == null) {return null;}
		return attr.getNodeName(); // or .getName() ?
	}

	@Override
	public String getAttributeValue(int index) {
		if (currentNodeAttributes == null) {
			return null; // or "" ?
		}
		Attr attr = (Attr)currentNodeAttributes.item(index);
		if (attr == null) {return null;}
		return attr.getNodeValue(); // or .getValue() ?
	}

	@Override
	public int getAttributeCount() {
		if (currentNodeAttributes == null) {
		  return 0;
		}
		int count = currentNodeAttributes.getLength();
		return count;
	}

	@Override
	public String getPrefix() {
		return currentNode.getPrefix();
	}

	@Override
	public String getNamespaceURI() {
		return currentNode.getNamespaceURI();
	}

	@Override
	public Object getUnderlyingReader() {
		return in;
	}

}
