/*
* Copyright 2010 Saikiran Daripelli(saikirandaripelli@gmail.com). All rights reserved.
*
*Redistribution and use in source and binary forms, with or without modification, are
*permitted provided that the following conditions are met:
*
*  1. Redistributions of source code must retain the above copyright notice, this list of
*     conditions and the following disclaimer.
*
*  2. Redistributions in binary form must reproduce the above copyright notice, this list
*     of conditions and the following disclaimer in the documentation and/or other materials
*     provided with the distribution.
*
*THIS SOFTWARE IS PROVIDED BY Saikiran Daripelli(saikirandaripelli@gmail.com) ``AS IS'' 
*AND ANY EXPRESS OR IMPLIED
*WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
*FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Saikiran Daripelli
*(saikirandaripelli@gmail.com) OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
* SPECIAL, EXEMPLARY, OR
*CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
*SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
*ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
*NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
*ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*The views and conclusions contained in the software and documentation are those of the
*authors and should not be interpreted as representing official policies, either expressed
*or implied, of Saikiran Daripelli(saikirandaripelli@gmail.com).
*/
package org.wsdl.tools.wsdlauditor.ruledefn.utils;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wsdl.tools.wsdlauditor.ruledefn.data.ElementInfo;
import org.wsdl.tools.wsdlauditor.ruledefn.data.SchemaElement;
import org.wsdl.tools.wsdlauditor.ruledefn.data.WsdlSchema;

/**
 * The Class XmlUtil.
 */
public class XmlUtil {

	/** The primitive types. */
	private static List<String> primitiveTypes;
	static {
		primitiveTypes = new ArrayList<String>();
		primitiveTypes.add("string");
		primitiveTypes.add("boolean");
		primitiveTypes.add("decimal");
		primitiveTypes.add("float");
		primitiveTypes.add("double");
		primitiveTypes.add("duration");
		primitiveTypes.add("dateTime");
		primitiveTypes.add("time");
		primitiveTypes.add("date");
		primitiveTypes.add("gYearMonth");
		primitiveTypes.add("gYear");
		primitiveTypes.add("gMonthDay");
		primitiveTypes.add("gDay");
		primitiveTypes.add("gMonth");
		primitiveTypes.add("hexBinary");
		primitiveTypes.add("base64Binary");
		primitiveTypes.add("anyURI");
		primitiveTypes.add("QName");
		primitiveTypes.add("NOTATION");
		primitiveTypes.add("normalizedString");
		primitiveTypes.add("token");
		primitiveTypes.add("language");
		primitiveTypes.add("NMTOKEN");
		primitiveTypes.add("NMTOKENS");
		primitiveTypes.add("Name");
		primitiveTypes.add("NCName");
		primitiveTypes.add("ID");
		primitiveTypes.add("IDREF");
		primitiveTypes.add("IDREFS");
		primitiveTypes.add("ENTITY");
		primitiveTypes.add("ENTITIES");
		primitiveTypes.add("integer");
		primitiveTypes.add("nonPositiveInteger");
		primitiveTypes.add("negativeInteger");
		primitiveTypes.add("long");
		primitiveTypes.add("int");
		primitiveTypes.add("short");
		primitiveTypes.add("byte");
		primitiveTypes.add("nonNegativeInteger");
		primitiveTypes.add("unsignedLong");
		primitiveTypes.add("unsignedInt");
		primitiveTypes.add("unsignedShort");
		primitiveTypes.add("unsignedByte");
		primitiveTypes.add("positiveInteger");
	}

	/**
	 * Checks if is primitive.
	 * 
	 * @param type
	 *            the type
	 * @return true, if is primitive
	 */
	public static boolean isPrimitive(String type) {
		if (type != null) {
			type = stripNameSpaceIf(type);
			return primitiveTypes.contains(type);
		}
		return false;
	}

	/**
	 * Strip name space if.
	 * 
	 * @param name
	 *            the name
	 * @return the string
	 */
	public static String stripNameSpaceIf(String name) {
		if (name != null) {
			String[] names = name.split(":");
			if (names.length > 1) {
				name = names[names.length - 1].trim();
			}
		}
		return name.trim();
	}

	/**
	 * Gets the previous comment.
	 * 
	 * @param element
	 *            the element
	 * @return the previous comment
	 */
	public static String getPreviousComment(Node element) {
		while (element.getPreviousSibling() != null) {
			Node prev = element.getPreviousSibling();
			if (prev.getNodeType() == Node.COMMENT_NODE) {
				return prev.getTextContent();
			} else if (prev.getNodeType() == Node.TEXT_NODE) {
				return getPreviousComment(prev);
			} else if (prev.getNodeType() == Node.ELEMENT_NODE) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Gets the next comment.
	 * 
	 * @param element
	 *            the element
	 * @return the next comment
	 */
	public static String getNextComment(Node element) {
		while (element.getNextSibling() != null) {
			Node prev = element.getNextSibling();
			if (prev.getNodeType() == Node.COMMENT_NODE) {
				return prev.getTextContent();
			} else if (prev.getNodeType() == Node.TEXT_NODE) {
				return getNextComment(prev);
			} else if (prev.getNodeType() == Node.ELEMENT_NODE) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Gets the childs.
	 * 
	 * @param parent
	 *            the parent
	 * @param childPath
	 *            the child path
	 * @param schema
	 *            the schema
	 * @return the childs
	 */
	public static List<Element> getChilds(Element parent, String childPath,
			WsdlSchema schema) {
		List<Element> children = new ArrayList<Element>();
		if (childPath != null) {
			children.add(parent);
			String[] splitChilds = childPath.split("\\.");
			for (int i = 0; i < splitChilds.length; i++) {
				String childName = splitChilds[i];
				children = getChilds(children, childName, schema);
			}

		}
		return children;

	}

	/**
	 * Complete element info.
	 * 
	 * @param element
	 *            the element
	 * @param schema
	 *            the schema
	 */
	public static void completeElementInfo(ElementInfo element,
			WsdlSchema schema) {
		if (element != null) {
			Element parent = XmlUtil.getParentElement(element.getElement());
			List<Element> childElements = XmlUtil.getAllChilds(parent, schema);
			int size = childElements == null ? 0 : childElements.size();
			element.setNumberOfPeers(size);
			element.setPosition(-1);
			if (childElements != null) {
				for (int i = 0; i < childElements.size(); i++) {
					Element chiElement = childElements.get(i);
					if (areElementsEqual(chiElement, element.getElement())) {
						element.setPosition(i);
						break;
					}
				}
			}
		}
	}

	/**
	 * Gets the childs.
	 * 
	 * @param parents
	 *            the parents
	 * @param child
	 *            the child
	 * @param schema
	 *            the schema
	 * @return the childs
	 */
	private static List<Element> getChilds(List<Element> parents, String child,
			WsdlSchema schema) {
		List<Element> children = new ArrayList<Element>();
		for (Element parent : parents) {

			children.addAll(getTagChilds(parent, child, schema));

		}

		return children;

	}

	/**
	 * Are elements equal.
	 * 
	 * @param element1
	 *            the element1
	 * @param element2
	 *            the element2
	 * @return true, if successful
	 */
	public static boolean areElementsEqual(Element element1, Element element2) {
		if (!element1.getTagName().equals(element2.getTagName())) {
			return false;
		}
		NamedNodeMap nodeAttrMap = element1.getAttributes();
		NamedNodeMap pathAttrMap = element2.getAttributes();
		if ((nodeAttrMap == null && pathAttrMap == null)
				|| (pathAttrMap.getLength() == 0 && nodeAttrMap.getLength() == 0)) {
			return true;
		} else {
			if (element1.hasAttribute("name") && element2.hasAttribute("name")) {
				if (element1.getAttribute("name").equals(
						element2.getAttribute("name"))) {
					return true;
				}
			} else if (nodeAttrMap != null && pathAttrMap != null
					&& (nodeAttrMap.getLength() == pathAttrMap.getLength())) {
				for (int k = 0; k < nodeAttrMap.getLength(); k++) {
					Node nodeAttr = nodeAttrMap.item(k);
					String nodeAttrName = nodeAttr.getNodeName();
					String nodeAttrValue = nodeAttr.getNodeValue();
					if (element2.hasAttribute(nodeAttrName)
							&& nodeAttrValue.equals(element2
									.getAttribute(nodeAttrName))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Gets the all childs.
	 * 
	 * @param parent
	 *            the parent
	 * @param schema
	 *            the schema
	 * @return the all childs
	 */
	public static List<Element> getAllChilds(Element parent, WsdlSchema schema) {
		List<Element> children = new ArrayList<Element>();
		if (parent != null) {
			NodeList nodes = parent.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					if (schema != null
							&& element.getTagName().equals("extension")) {
						String extn = element.getAttribute("base");
						extn = XmlUtil.stripNameSpaceIf(extn);
						List<SchemaElement> extnElems = schema.getComplexType();
						for (SchemaElement extnElem : extnElems) {
							if (extnElem != null
									&& extn.equals(extnElem.getElementName())) {
								children.addAll(getAllChilds(extnElem
										.getElement().getElement(), schema));
							}
						}

					}
					children.add((Element) node);
				}
			}
		}
		return children;

	}

	/**
	 * Gets the tag childs.
	 * 
	 * @param parent
	 *            the parent
	 * @param child
	 *            the child
	 * @param schema
	 *            the schema
	 * @return the tag childs
	 */
	private static List<Element> getTagChilds(Element parent, String child,
			WsdlSchema schema) {
		List<Element> children = new ArrayList<Element>();
		if (child != null) {
			NodeList nodes = null;
			if (child.contains(":")) {
				nodes = parent.getElementsByTagName(child.trim());
			} else {
				nodes = parent.getElementsByTagNameNS("*", child.trim());
			}
			if (nodes != null) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node childNode = nodes.item(i);
					Element childElem = (Element) childNode;
					children.add(childElem);
				}
			}
			if (!child.equals("extension")) {
				List<Element> directChilds = getTagChilds(parent, "extension",
						schema);
				if (directChilds != null && !directChilds.isEmpty()) {
					for (Element element : directChilds) {
						String extn = element.getAttribute("base");
						extn = XmlUtil.stripNameSpaceIf(extn);
						List<SchemaElement> extnElems = schema.getComplexType();
						for (SchemaElement extnElem : extnElems) {
							if (extnElem != null
									&& extn.equals(extnElem.getElementName())) {
								children.addAll(getTagChilds(extnElem
										.getElement().getElement(), child,
										schema));
							}
						}
					}
				}
			}
		}

		return children;
	}

	/**
	 * Gets the parent element.
	 * 
	 * @param element
	 *            the element
	 * @return the parent element
	 */
	public static Element getParentElement(Element element) {
		if (element == null) {
			return null;
		}
		Element parentElement = null;
		Node parentNode = element.getParentNode();
		while (parentNode != null && parentElement == null) {
			if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
				parentElement = (Element) parentNode;
			}
			if (parentNode.getNodeType() == Node.DOCUMENT_NODE) {
				parentElement = ((Document) parentNode).getDocumentElement();
				if (element.isSameNode(parentElement)) {
					parentElement = null;
				}
			}
			parentNode = parentNode.getParentNode();
		}

		return parentElement;
	}
}
