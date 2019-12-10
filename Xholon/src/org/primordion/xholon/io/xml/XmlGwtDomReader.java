package org.primordion.xholon.io.xml;

import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Attr;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.Element;
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
	
	/** A GWT Document Object Model (DOM) document. */
	private Document document = null;
	
	/** A native JavaScript Document Object Model (DOM) document. */
	private Object docNative = null;
	
	/** The current node in the DOM. The node currently pointed to by the DOM cursor. */
	protected Node currentNode = null;
	
	/** The attributes of the currentNode. */
	protected NamedNodeMap currentNodeAttributes = null;
	
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
	 * Any PROCESSING_INSTRUCTION with a target equal to this value,
	 * should be processed within this XmlReader and not passed on to a Xholon Xml2Xholon instance.
	 */
	private static final String PI_XML_READER = "xmlreader";
	private static final String PI_XML_READER_PATCH = "xmlreader-patch";
	private static final String PI_XHMATH = "xhmath";
	
	private boolean debug = false;
	
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
		if (this.debug) {
			logDocument(document);
		}
		currentNode = document;
		
		//this.logDocFirstElementChildNode(document); //currentNode);
		// document.createTreeWalker() // maybe use this JavaScript method to walk the XML tree
		
		//docNative = this.unwrapDoc(document); // only do this when and if docNative is required (when processing a PI)
		
		currentNodeAttributes = currentNode.getAttributes();
		eventType = START_DOCUMENT;
	}
	
	/**
	 * unwrapDoc
	 * @param doc a GWT Document
	 * @return a JavaScript Document
	 */
	protected native Object unwrapDoc(Document doc) /*-{
		$wnd.console.log(doc);
		var obj = null;
		for (pname in doc) {
			var pval = doc[pname];
			if ((typeof pval == "object") && pval.nodeName && (pval.nodeName == "#document")) {
				obj = pval;
				break;
			}
		}
		return obj;
	}-*/;
	
	/**
	 * unwrapNode
	 * @param node a GWT Node
	 * @return a JavaScript Node
	 */
	protected native Object unwrapNode(Node node, String nodeName) /*-{
		$wnd.console.log(node);
		var obj = null;
		for (pname in node) {
			var pval = node[pname];
			if ((typeof pval == "object") && pval.nodeName && (pval.nodeName == nodeName)) {
				obj = pval;
				break;
			}
		}
		return obj;
	}-*/;	
	
	/**
	 * unwrapAndTestDoc
	 * 
	 * CSS query
temp1.querySelector("stage > Attribute_String");
	 * 
	 * XPath query
var xpathExpr = "/PhysicalSystem/stage/Attribute_String/text()";
var xpathExpr = "/PhysicalSystem/stage/Attribute_String";
var contextNode = temp1;
var xPathResult = temp1.evaluate(xpathExpr, contextNode, null, XPathResult.ANY_TYPE, null);
if (xPathResult.resultType == XPathResult.UNORDERED_NODE_ITERATOR_TYPE) {
  var thisNode = xPathResult.iterateNext();
  while (thisNode) {
    console.log(thisNode);
    thisNode = xPathResult.iterateNext();
  }
}

var xpathExpr = "stage/Attribute_String";
var contextNode = temp1.firstElementChild;
var xPathResult = temp1.evaluate(xpathExpr, contextNode, null, XPathResult.ANY_TYPE, null);
if (xPathResult.resultType == XPathResult.UNORDERED_NODE_ITERATOR_TYPE) {
  var thisNode = xPathResult.iterateNext();
  while (thisNode) {
    console.log(thisNode);
    thisNode = xPathResult.iterateNext();
  }
}
	 */
	protected native Object unwrapAndTestDoc(Document doc) /*-{
		$wnd.console.log(doc);
		var obj = null;
		for (pname in doc) {
			var pval = doc[pname];
			if ((typeof pval == "object") && pval.nodeName && (pval.nodeName == "#document")) {
				// pval is the native XML Document
				$wnd.console.log(pname);
				$wnd.console.log(pval.nodeName);
				//$wnd.console.log(pval.docType); // undefined
				$wnd.console.log(pval.documentURI);
				$wnd.console.log(pval.childElementCount);
				$wnd.console.log(pval.children);
				$wnd.console.log(pval.firstElementChild);
				$wnd.console.log(pval.firstElementChild.nodeName);
				$wnd.console.log(pval.createTreeWalker); // a function
				$wnd.console.log(pval.toString());
				$wnd.console.log(pval);
				obj = pval;
				if (pval.firstElementChild.nodeName.endsWith("System")) {
					// this is the start of the workbook CSH (ex: "PhysicalSystem")
					// TESTING
					var treeWalker = pval.createTreeWalker(
						pval.firstElementChild,
						NodeFilter.SHOW_ELEMENT,
						{
							acceptNode: function(node) {
								if (node.nodeName == "characters") {
									return NodeFilter.FILTER_ACCEPT;
								}
								else {
									return NodeFilter.FILTER_REJECT;
								}
							}
						},
						false
					);
					var nodeList = [];
					while(treeWalker.nextNode()) {
						nodeList.push(treeWalker.currentNode)
					}
					$wnd.console.log("TREEWALKER NODELIST");
					$wnd.console.log(nodeList);
					for (var i = 0; i < nodeList.length; i++) {
						var node = nodeList[i];
						if (node.parentNode) {
							//node.parentNode.removeChild(node); // this works
						}
					}
				}
			}
		}
		return obj;
	}-*/;
	
	protected void logDocument(Document doc) {
		Element child = doc.getDocumentElement();
		consoleLog(child.getTagName());
	}
	
	protected void logDocFirstElementChildNode(Document doc) {
		/*Node child = node.getFirstChild();
		while (child != null) {
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				consoleLog("XmlGwtDomReader currentNode = document; " + child.getNodeName());
				consoleLog(child);
				return;
			}
			child = child.getNextSibling();
		}*/
		consoleLog(doc.getClass().getName());
		Element child = doc.getDocumentElement();
		consoleLog("XmlGwtDomReader.logDocFirstElementChildNode() " + child.getTagName());
		consoleLog(child);
		this.logDocFirstElementChildNodeNative(child);
		//consoleLog(com.google.gwt.dom.client.Element.as(child)); // NO
	}
	
	protected native void logDocFirstElementChildNodeNative(Element ele) /*-{
		$wnd.console.log(ele);
	}-*/;
	
	/**
	 * Do xhmath process.
	 * @see lib/xhmath/modules/parser.js
	 */
	protected native void doXhmath(String command, String mathStr, Object nodeNative) /*-{
		var commandArr = command.split("."); // ex: xhmath.pSet01
		var method = commandArr[1];
		if ($wnd.xh.xhmath && method && $wnd.xh.xhmath[method]) {
			var xmlStr = $wnd.xh.xhmath[method](mathStr);
			var dp = new DOMParser();
			var doc = dp.parseFromString(xmlStr, "application/xml");
			if (doc) {
				var fragment = doc.documentElement;
				if (fragment) {
					nodeNative.appendChild(fragment);
				}
			}
		}
	}-*/;
	
	/**
	 * Do XML Patch.
	 * I use concepts from ietf rfc5261 "An Extensible Markup Language (XML) Patch Operations Framework Utilizing XML Path Language (XPath) Selectors"
	 * see: https://tools.ietf.org/html/rfc5261
	 * see also MeteorPlatformService.java
	 */
	protected native void doXmlPatch(String jsonStr, Object docNative, Object nodeNative) /*-{
		$wnd.console.log(jsonStr);
		$wnd.console.log(docNative);
		$wnd.console.log(nodeNative);
		var jsObj = $wnd.JSON.parse(jsonStr);
		$wnd.console.log(jsObj);
		var params = $wnd.location.search;
		var ver = new URLSearchParams(params).get("ver");
		if (ver) {
			$wnd.console.log(ver);
			var patchArr = jsObj[ver];
			if (patchArr) {
				$wnd.console.log(patchArr);
				for (var i = 0; i < patchArr.length; i++) {
					var patchObj = patchArr[i];
					$wnd.console.log(patchObj);
					var xpathExpr = patchObj.sel;
					// use XPathResult.FIRST_ORDERED_NODE_TYPE instead of ANY_TYPE and UNORDERED_NODE_ITERATOR_TYPE
					var xPathResult = docNative.evaluate(xpathExpr, nodeNative, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null); //XPathResult.ANY_TYPE
					if (xPathResult.resultType == XPathResult.FIRST_ORDERED_NODE_TYPE) { //XPathResult.UNORDERED_NODE_ITERATOR_TYPE
						var thisNode = xPathResult.singleNodeValue; //xPathResult.iterateNext();
						if (thisNode) {
							$wnd.console.log(thisNode);
							switch (patchObj["op"]) {
							case "add":
								if (patchObj["type"]) {
									var attrName = patchObj["type"];
									if (attrName && attrName.startsWith("@")) {
										attrName = attrName.substring(1);
										thisNode[attrName] = patchObj["content"];
									}
								}
								else {
									var dp = new DOMParser();
									var doc = dp.parseFromString(patchObj["content"], "application/xml");
									if (doc) {
										var fragment = doc.documentElement;
										if (fragment) {
											thisNode.appendChild(fragment);
										}
									}
								}
								break;
							case "replace":
								switch (thisNode.nodeType) {
								case Node.ELEMENT_NODE: // 1
									var dp = new DOMParser();
									var doc = dp.parseFromString(patchObj["content"], "application/xml");
									if (doc) {
										var fragment = doc.documentElement;
										if (fragment) {
											thisNode.parentNode.replaceChild(fragment, thisNode);
										}
									}
									break;
								case Node.ATTRIBUTE_NODE:	// 2
									thisNode.nodeValue = patchObj["content"];
									break;
								case Node.TEXT_NODE: // 3
									// TODO
									break;
								default: break;
								}
								break;
							case "remove":
								switch (thisNode.nodeType) {
								case Node.ELEMENT_NODE: // 1
									thisNode.parentNode.removeChild(thisNode);
									break;
								case Node.ATTRIBUTE_NODE:	// 2
									delete thisNode;
									break;
								case Node.TEXT_NODE: // 3
									// TODO
									break;
								default: break;
								}
								break;
							default: break;
							}
							//thisNode = xPathResult.iterateNext(); // error because the XML has been mutated
							//break;
						}
					}
				}
			}
		}
	}-*/;

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
			eventType = OTHER_TYPE;
			break;
		case Node.TEXT_NODE:
			eventType = TEXT;
			break;
		case Node.CDATA_SECTION_NODE:	
			eventType = TEXT; // CDATASection is a subclass of Text so this should work
			break;
		case Node.ENTITY_REFERENCE_NODE:
			eventType = ENTITY_REF;
			break;
		case Node.ENTITY_NODE:
			eventType = OTHER_TYPE;
			break;
		case Node.PROCESSING_INSTRUCTION_NODE:
			if (currentNode.getNodeName().startsWith(PI_XML_READER)) {
				// process the PI right here
				String nval = currentNode.getNodeValue(); // nval should be a JSON string
				if (PI_XML_READER_PATCH.equals(currentNode.getNodeName())) {
					Object nodeNative = this.unwrapNode(currentNode.getParentNode(), currentNode.getParentNode().getNodeName());
					if (nodeNative != null) {
						if (docNative == null) {
							docNative = this.unwrapDoc(document);
						}
						this.doXmlPatch(nval, docNative, nodeNative);
					}
				}
				eventType = NULL_EVENT;
			}
			else if (currentNode.getNodeName().startsWith(PI_XHMATH)) {
				// process the PI right here
				String nval = currentNode.getNodeValue(); // nval should be a JSON string
				Object nodeNative = this.unwrapNode(currentNode.getParentNode(), currentNode.getParentNode().getNodeName());
				if (nodeNative != null) {
					this.doXhmath(currentNode.getNodeName(), nval, nodeNative);
				}
				eventType = NULL_EVENT;
			}
			else {
				eventType = PROCESSING_INSTRUCTION;
			}
			break;
		case Node.COMMENT_NODE:
			eventType = COMMENT;
			break;
		case Node.DOCUMENT_NODE:
			eventType = OTHER_TYPE;
			break;
		case Node.DOCUMENT_TYPE_NODE:
			eventType = OTHER_TYPE;
			break;
		case Node.DOCUMENT_FRAGMENT_NODE:
			eventType = OTHER_TYPE;
			break;
		case Node.NOTATION_NODE:
			eventType = OTHER_TYPE;
			break;
		default:
			eventType = OTHER_TYPE;
			break;
		}
		return eventType;
	}

	@Override
	public String getName() {
		if (currentNode == null) {return null;}
		// get just the local part of the name, excluding the prefix
		String fullName = currentNode.getNodeName();
		if (fullName != null && fullName.indexOf(":") != -1) {
			return fullName.split(":", 2)[1];
		}
		return fullName;
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
		// browsers report: "'Attr.nodeValue' is deprecated. Please use 'value' instead."
		return attr.getValue(); //getNodeValue();
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
		String prefix = currentNode.getPrefix();
		return isRequiredPrefix(prefix) ? prefix : null;
	}

	@Override
	public String getNamespaceURI() {
		return currentNode.getNamespaceURI();
	}

	@Override
	public Object getUnderlyingReader() {
		return in;
	}

	/**
	 * Is this a required prefix?
	 * @param prefix
	 * @return true or false
	 */
	protected boolean isRequiredPrefix(String prefix) {
		switch (prefix) {
		case "attr":
		case "xi":
			return true;
		default: return false;
		}
	}
	
	@Override
	public String toString() {
		if (currentNode == null) {return null;}
		return this.toStringNative(currentNode);
	}
	
	protected native String toStringNative(Node currentNode) /*-{
		return currentNode.toString();
	}-*/;

}
