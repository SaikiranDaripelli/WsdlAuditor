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
package org.wsdl.tools.wsdlauditor.ruledefn.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;

/**
 * The Class WsdlSchema.
 */
public class WsdlSchema {

	/** The attribute. */
	private List<SchemaElement> attribute;
	
	/** The complex type. */
	private List<SchemaElement> complexType;
	
	/** The elements. */
	private List<SchemaElement> elements;
	
	/** The enumeration. */
	private List<SchemaElement> enumeration;
	
	/** The operation. */
	private List<SchemaElement> operation;
	
	/** The request type. */
	private List<SchemaElement> requestType;
	
	/** The response type. */
	private List<SchemaElement> responseType;
	
	/** The service. */
	private List<SchemaElement> service;
	
	/** The simple type. */
	private List<SchemaElement> simpleType;

	/** The all elements. */
	private Set<SchemaElement> allElements = new HashSet<SchemaElement>();

	/** The document. */
	private Document document;
	
	/** The ref document. */
	private Document refDocument;

	/**
	 * Gets the all elements.
	 * 
	 * @return the all elements
	 */
	public Set<SchemaElement> getAllElements() {
		return allElements;
	}

	/**
	 * Gets the document.
	 * 
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * Sets the document.
	 * 
	 * @param document
	 *            the new document
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * Gets the ref document.
	 * 
	 * @return the ref document
	 */
	public Document getRefDocument() {
		return refDocument;
	}

	/**
	 * Sets the ref document.
	 * 
	 * @param refDocument
	 *            the new ref document
	 */
	public void setRefDocument(Document refDocument) {
		this.refDocument = refDocument;
	}

	/**
	 * Gets the attribute.
	 * 
	 * @return the attribute
	 */
	public List<SchemaElement> getAttribute() {
		return attribute;
	}

	/**
	 * Sets the attribute.
	 * 
	 * @param attribute
	 *            the new attribute
	 */
	public void setAttribute(List<SchemaElement> attribute) {
		if(attribute!=null){
			allElements.addAll(attribute);
		}
		this.attribute = attribute;
	}

	/**
	 * Gets the complex type.
	 * 
	 * @return the complex type
	 */
	public List<SchemaElement> getComplexType() {
		return complexType;
	}

	/**
	 * Sets the complex type.
	 * 
	 * @param complexType
	 *            the new complex type
	 */
	public void setComplexType(List<SchemaElement> complexType) {
		if(complexType!=null){
			allElements.addAll(complexType);
		}
		this.complexType = complexType;

	}

	/**
	 * Gets the elements.
	 * 
	 * @return the elements
	 */
	public List<SchemaElement> getElements() {
		return elements;
	}

	/**
	 * Sets the elements.
	 * 
	 * @param elements
	 *            the new elements
	 */
	public void setElements(List<SchemaElement> elements) {
		if(elements!=null){
			allElements.addAll(elements);
		}
		this.elements = elements;
	}

	/**
	 * Gets the enumeration.
	 * 
	 * @return the enumeration
	 */
	public List<SchemaElement> getEnumeration() {
		return enumeration;
	}

	/**
	 * Sets the enumeration.
	 * 
	 * @param enumeration
	 *            the new enumeration
	 */
	public void setEnumeration(List<SchemaElement> enumeration) {
		if(enumeration!=null){
			allElements.addAll(enumeration);
		}
		this.enumeration = enumeration;
	}

	/**
	 * Gets the operation.
	 * 
	 * @return the operation
	 */
	public List<SchemaElement> getOperation() {
		return operation;
	}

	/**
	 * Sets the operation.
	 * 
	 * @param operation
	 *            the new operation
	 */
	public void setOperation(List<SchemaElement> operation) {
		if(operation!=null){
			allElements.addAll(operation);
		}
		this.operation = operation;
	}

	/**
	 * Gets the request type.
	 * 
	 * @return the request type
	 */
	public List<SchemaElement> getRequestType() {
		return requestType;
	}

	/**
	 * Sets the request type.
	 * 
	 * @param requestType
	 *            the new request type
	 */
	public void setRequestType(List<SchemaElement> requestType) {
		if(requestType!=null){
			allElements.addAll(requestType);
		}
		this.requestType = requestType;

	}

	/**
	 * Gets the response type.
	 * 
	 * @return the response type
	 */
	public List<SchemaElement> getResponseType() {
		return responseType;
	}

	/**
	 * Sets the response type.
	 * 
	 * @param responseType
	 *            the new response type
	 */
	public void setResponseType(List<SchemaElement> responseType) {
		if(responseType!=null){
			allElements.addAll(responseType);
		}
		this.responseType = responseType;

	}

	/**
	 * Gets the service.
	 * 
	 * @return the service
	 */
	public List<SchemaElement> getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * 
	 * @param service
	 *            the new service
	 */
	public void setService(List<SchemaElement> service) {
		if(service!=null){
			allElements.addAll(service);
		}
		this.service = service;
	}

	/**
	 * Gets the simple type.
	 * 
	 * @return the simple type
	 */
	public List<SchemaElement> getSimpleType() {
		return simpleType;
	}

	/**
	 * Sets the simple type.
	 * 
	 * @param simpleType
	 *            the new simple type
	 */
	public void setSimpleType(List<SchemaElement> simpleType) {
		if(simpleType!=null){
			allElements.addAll(simpleType);
		}
		this.simpleType = simpleType;

	}

}
