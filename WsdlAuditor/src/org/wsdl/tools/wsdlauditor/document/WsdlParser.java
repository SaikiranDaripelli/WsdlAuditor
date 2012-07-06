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
package org.wsdl.tools.wsdlauditor.document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.wsdl.tools.wsdlauditor.ParserExeption;
import org.wsdl.tools.wsdlauditor.interfaces.DocumentParser;
import org.wsdl.tools.wsdlauditor.ruledefn.Factory;
import org.wsdl.tools.wsdlauditor.ruledefn.data.ElementInfo;
import org.wsdl.tools.wsdlauditor.ruledefn.data.SchemaElement;
import org.wsdl.tools.wsdlauditor.ruledefn.data.WsdlSchema;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Change;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.SchemaTypes;
import org.wsdl.tools.wsdlauditor.ruledefn.utils.XmlUtil;
import org.xml.sax.SAXException;

/**
 * The Class WsdlParser.
 */
public class WsdlParser implements DocumentParser{

	/* (non-Javadoc)
	 * @see org.wsdl.tools.wsdlauditor.interfaces.DocumentParser#parseWsdl(java.net.URL, java.net.URL)
	 */
	public WsdlSchema parseWsdl(URL documentUrl, URL oldDocumentUrl) {
		WsdlSchema retSchema = (WsdlSchema)Factory.getFactory().getDataObject(Factory.ObjectTypes.WsdlSchema);
		try {
			Document doc = getDocument(documentUrl.openStream());
			retSchema.setDocument(doc);
			Element docElem = doc.getDocumentElement();
			ElementInfo oldDocElem = null;
			if (oldDocumentUrl != null) {
				Document oldDoc = getDocument(oldDocumentUrl.openStream());
				retSchema.setRefDocument(oldDoc);
				oldDocElem = getElementInfo(oldDoc.getDocumentElement());
			}
			List<SchemaElement> complexTypes = getElements(docElem,
					"complexType", true, oldDocElem, retSchema);
			
			retSchema.setService(getElements(docElem, "wsdl:service", false,
					oldDocElem, retSchema));
			
			
			List<SchemaElement> portTypes = getElements(docElem,
					"wsdl:portType", false, oldDocElem, retSchema);

			if (portTypes != null) {
				Map<String, List<String>> messPartMap = getMessageToPartMap(
						docElem, retSchema);
				List<SchemaElement> operations = new ArrayList<SchemaElement>();
				for (SchemaElement portType : portTypes) {
					operations.addAll(getElements(portType, "operation", true,
							oldDocElem, retSchema));
				}
				retSchema.setOperation(operations);
				if (operations != null) {
					List<String> requestTypes = new ArrayList<String>();
					List<String> responseTypes = new ArrayList<String>();
					for (SchemaElement operation : operations) {
						List<SchemaElement> inputs = getElements(operation,
								"input", true, oldDocElem, retSchema);
						if (inputs != null) {
							for (SchemaElement input : inputs) {
								String message = input.getElement()
										.getElement().getAttribute("message");
								if (message != null) {
									message = removeNS(message);
									List<String> reqTypes = messPartMap
											.get(message);
									if (reqTypes != null) {
										requestTypes.addAll(reqTypes);
									}
								}
							}
						}
						List<SchemaElement> outputs = getElements(operation,
								"output", true, oldDocElem, retSchema);

						if (outputs != null) {
							for (SchemaElement input : outputs) {
								String message = input.getElement()
										.getElement().getAttribute("message");
								if (message != null) {
									message = removeNS(message);
									List<String> reqTypes = messPartMap
											.get(message);
									if (reqTypes != null) {
										responseTypes.addAll(reqTypes);
									}
								}
							}
						}
					}
					List<SchemaElement> reqTypes = filterReqRespTypesOnName(
							complexTypes, requestTypes);
					retSchema.setRequestType(reqTypes);

					List<SchemaElement> respTypes = filterReqRespTypesOnName(
							complexTypes, responseTypes);
					retSchema.setResponseType(respTypes);
					complexTypes.removeAll(reqTypes);
					complexTypes.removeAll(respTypes);
				}
			}
			retSchema.setComplexType(complexTypes);
			retSchema.setSimpleType(getElements(docElem, "simpleType", true,
					oldDocElem, retSchema));
			retSchema.setElements(getElements(docElem, "element", true,
					oldDocElem, retSchema));
			retSchema.setAttribute(getElements(docElem, "attribute", true,
					oldDocElem, retSchema));
			retSchema.setEnumeration(getElements(docElem, "enumeration", true,
					oldDocElem, retSchema));
			for(SchemaElement element:retSchema.getAllElements()){
				XmlUtil.completeElementInfo(element.getElement(), retSchema);
				XmlUtil.completeElementInfo(element.getRefElement(), retSchema);
			}
		} catch (SAXException e) {
			throw new ParserExeption("Error Parsing documents", e);
		} catch (IOException e) {
			throw new ParserExeption("Error Parsing documents", e);
		} catch (ParserConfigurationException e) {
			throw new ParserExeption("Error Parsing documents", e);
		}
		return retSchema;

	}
	
	
	/**
	 * Gets the path of element.
	 * 
	 * @param element
	 *            the element
	 * @return the path of element
	 */
	protected List<ElementInfo> getPathOfElement(ElementInfo element) {
		List<ElementInfo> parentPath = new ArrayList<ElementInfo>();
		if (element != null && element.getElement()!=null) {
			Element parentElement = XmlUtil.getParentElement(element.getElement());
			if (parentElement != null) {
				parentPath.addAll(getElementInfo(parentElement).getCompletePath());
			}
		}
		parentPath.add(element);
		return parentPath;

	}
	
	/**
	 * Gets the element from path.
	 * 
	 * @param oldDocElement
	 *            the old doc element
	 * @param paths
	 *            the paths
	 * @return the element from path
	 */
	protected ElementInfo getElementFromPath(ElementInfo oldDocElement,
			List<ElementInfo> paths) {
		if (paths == null || paths.isEmpty()) {
			return null;
		}
		if(paths.size()==1){
			return oldDocElement;
		}
		ElementInfo parent = oldDocElement;
		for (int j = 1; j < paths.size(); j++) {
			ElementInfo path = paths.get(j);
			List<Element> children=XmlUtil.getAllChilds(parent.getElement(), null);
			boolean found = false;
			for (Element node:children) {
					if (XmlUtil.areElementsEqual(node, path.getElement())) {
						found = true;
						parent = getElementInfo(node);
						break;
					}
			}
			if (!found && j<paths.size()-1) {
				return null;
			}
		}

		return parent;
	}

	/**
	 * Filter req resp types on name.
	 * 
	 * @param complexTypes
	 *            the complex types
	 * @param types
	 *            the types
	 * @return the list
	 */
	private List<SchemaElement> filterReqRespTypesOnName(
			List<SchemaElement> complexTypes, List<String> types) {
		List<SchemaElement> reqTypes = new ArrayList<SchemaElement>();
		for (SchemaElement complexType : complexTypes) {
			if (complexType.getElement() != null) {
				String name = complexType.getElement().getElement()
						.getAttribute("name");
				for (String type : types) {
					if (name.equalsIgnoreCase(type)) {
						reqTypes.addAll(getExtendingTypes(complexType,
								complexTypes));
						reqTypes.add(complexType);
					}
				}
			}
		}
		return reqTypes;
	}

	/**
	 * Gets the extending types.
	 * 
	 * @param element
	 *            the element
	 * @param complexTypes
	 *            the complex types
	 * @return the extending types
	 */
	private List<SchemaElement> getExtendingTypes(SchemaElement element,
			List<SchemaElement> complexTypes) {
		List<SchemaElement> reqTypes = new ArrayList<SchemaElement>();
		NodeList nodes = element.getElement().getElement()
				.getElementsByTagNameNS("*", "extension");
		if (nodes != null) {
			List<String> bases = new ArrayList<String>();
			for (int i = 0; i < nodes.getLength(); i++) {
				Element extn = (Element) nodes.item(i);
				String base = extn.getAttribute("base");
				base = removeNS(base);
				bases.add(base);
			}
			reqTypes.addAll(filterReqRespTypesOnName(complexTypes, bases));
		}
		return reqTypes;
	}

	/**
	 * Gets the message to part map.
	 * 
	 * @param documentElement
	 *            the document element
	 * @param retSchema
	 *            the ret schema
	 * @return the message to part map
	 */
	private Map<String, List<String>> getMessageToPartMap(
			Element documentElement, WsdlSchema retSchema) {
		Map<String, List<String>> retMap = new HashMap<String, List<String>>();
		List<SchemaElement> messages = getElements(documentElement,
				"wsdl:message", false, null, retSchema);
		if (messages != null) {
			for (SchemaElement message : messages) {
				String messName = message.getElement().getElement()
						.getAttribute("name");
				List<SchemaElement> parts = getElements(message, "part", true,
						null, retSchema);
				if (parts != null) {
					List<String> partNames = new ArrayList<String>();
					for (SchemaElement part : parts) {
						String element = part.getElement().getElement()
								.getAttribute("element");
						if (element != null) {
							element = removeNS(element);
							partNames.add(element);
						}

					}
					retMap.put(messName, partNames);
				}

			}
		}
		return retMap;
	}

	/**
	 * Removes the ns.
	 * 
	 * @param element
	 *            the element
	 * @return the string
	 */
	private String removeNS(String element) {
		String reElement = element;
		String[] elemParts = reElement.split(":");
		if (elemParts.length == 2) {
			reElement = elemParts[1];
		}
		return reElement;
	}

	/**
	 * Gets the elements.
	 * 
	 * @param parent
	 *            the parent
	 * @param tagName
	 *            the tag name
	 * @param ignoreNS
	 *            the ignore ns
	 * @param oldDocElement
	 *            the old doc element
	 * @param retSchema
	 *            the ret schema
	 * @return the elements
	 */
	private List<SchemaElement> getElements(SchemaElement parent,
			String tagName, boolean ignoreNS, ElementInfo oldDocElement,
			WsdlSchema retSchema) {
		return getElements(parent.getElement().getElement(), tagName, ignoreNS,
				oldDocElement, retSchema);
	}

	/**
	 * Gets the elements.
	 * 
	 * @param parent
	 *            the parent
	 * @param tagName
	 *            the tag name
	 * @param ignoreNS
	 *            the ignore ns
	 * @param oldDocElement
	 *            the old doc element
	 * @param retSchema
	 *            the ret schema
	 * @return the elements
	 */
	protected List<SchemaElement> getElements(Element parent,
			String tagName, boolean ignoreNS, ElementInfo oldDocElement,
			WsdlSchema retSchema) {

		List<SchemaElement> elements = new ArrayList<SchemaElement>();
		if (parent == null) {
			return elements;
		}
		NodeList nodes = null;
		if (ignoreNS) {
			nodes = parent.getElementsByTagNameNS("*", tagName);
		} else {
			nodes = parent.getElementsByTagName(tagName);
		}
		if (nodes != null) {
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				SchemaElement schemaElement = getSchemaInfo(
						oldDocElement, retSchema, element, Change.Added);
				elements.add(schemaElement);
			}
		}
		if (oldDocElement != null && oldDocElement.getElement() != null) {
			if (ignoreNS) {
				nodes = oldDocElement.getElement().getElementsByTagNameNS("*",
						tagName);
			} else {
				nodes = oldDocElement.getElement()
						.getElementsByTagName(tagName);
			}
			if (nodes != null) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Element element = (Element) nodes.item(i);
					SchemaElement schemaElement = getSchemaInfo(getElementInfo(parent.getOwnerDocument()
									.getDocumentElement()), retSchema, element,
							Change.Removed);
					if (schemaElement.getChange()!=null) {
						ElementInfo refElem=schemaElement.getRefElement();
						schemaElement.setRefElement(schemaElement.getElement());
						schemaElement.setElement(refElem);
						elements.add(schemaElement);
					}
				}
			}
		}
		return elements;
	}
	
	/* (non-Javadoc)
	 * @see org.wsdl.tools.wsdlauditor.interfaces.DocumentParser#getSchemaInfo(org.wsdl.tools.wsdlauditor.ruledefn.data.ElementInfo, org.wsdl.tools.wsdlauditor.ruledefn.data.WsdlSchema, org.w3c.dom.Element, org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Change)
	 */
	public  SchemaElement getSchemaInfo(ElementInfo oldDocElement,
			WsdlSchema retSchema, Element element,Change change) {
		SchemaElement schemaElement = (SchemaElement)Factory.getFactory().getDataObject(Factory.ObjectTypes.SchemaElement);
		ElementInfo elementInfo=getElementInfo(element);
		schemaElement.setElement(elementInfo);
		schemaElement.setType(elementInfo.getType());
		schemaElement.setElementName(element.getAttribute("name"));
		schemaElement.setSchema(retSchema);
		if(oldDocElement!=null){
			ElementInfo elementFromPath=getElementFromPath(
					oldDocElement, elementInfo.getCompletePath());
			if(elementFromPath!=null){
				if(!elementFromPath.equals(schemaElement.getElement())){
					schemaElement.setChange(change);							
				}
				schemaElement.setRefElement(elementFromPath);
			}
			
		}
		return schemaElement;
	}
	
	/* (non-Javadoc)
	 * @see org.wsdl.tools.wsdlauditor.interfaces.DocumentParser#getElementInfo(org.w3c.dom.Element)
	 */
	public  ElementInfo  getElementInfo(Element element){
    	ElementInfo elementInfo=(ElementInfo)Factory.getFactory().getDataObject(Factory.ObjectTypes.ElementInfo);
    	
    	elementInfo.setElement(element);
    	String type=XmlUtil.stripNameSpaceIf(element.getTagName());
    	SchemaTypes types=null;
    	if("element".equalsIgnoreCase(type)){
    		types=SchemaTypes.Element;
    	}else if("complextype".equalsIgnoreCase(type)){
    		types=SchemaTypes.ComplexType;
    	}else if("simpletype".equalsIgnoreCase(type)){
    		types=SchemaTypes.SimpleType;
    	}else if("enumeration".equalsIgnoreCase(type)){
    		types=SchemaTypes.Enumeration;
    	}else if("attribute".equalsIgnoreCase(type)){
    		types=SchemaTypes.Attribute;
    	}else if("service".equalsIgnoreCase(type)){
    		types=SchemaTypes.Service;
    	}else if("operation".equalsIgnoreCase(type)){
    		types=SchemaTypes.Operation;
    	}
    	elementInfo.setType(types);
    	elementInfo.setCompletePath(getPathOfElement(elementInfo));

    	return elementInfo;
    }
	
	/**
	 * Gets the document.
	 * 
	 * @param config
	 *            the config
	 * @return the document
	 * @throws SAXException
	 *             the sAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 */
	private static Document getDocument(InputStream config)
			throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.parse(config);
		return doc;

	}

	
}
