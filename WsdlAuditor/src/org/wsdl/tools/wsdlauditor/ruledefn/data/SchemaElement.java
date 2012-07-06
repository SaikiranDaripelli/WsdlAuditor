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

import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Change;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.SchemaTypes;

/**
 * The Class SchemaElement.
 */
public class SchemaElement {
	
	/** The element. */
	private ElementInfo element;
	
	/** The ref element. */
	private ElementInfo refElement;
	
	/** The change. */
	private Change change;
	
	/** The schema. */
	private WsdlSchema schema;
	
	/** The element name. */
	private String elementName;
	
	/** The type. */
	private SchemaTypes type;
	
	
	
	/**
	 * Gets the element name.
	 * 
	 * @return the element name
	 */
	public String getElementName() {
		return elementName;
	}

	/**
	 * Sets the element name.
	 * 
	 * @param elementName
	 *            the new element name
	 */
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public SchemaTypes getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(SchemaTypes type) {
		this.type = type;
	}

	/**
	 * Gets the schema.
	 * 
	 * @return the schema
	 */
	public WsdlSchema getSchema() {
		return schema;
	}

	/**
	 * Sets the schema.
	 * 
	 * @param schema
	 *            the new schema
	 */
	public void setSchema(WsdlSchema schema) {
		this.schema = schema;
	}

	

	/**
	 * Gets the element.
	 * 
	 * @return the element
	 */
	public ElementInfo getElement() {
		return element;
	}

	/**
	 * Sets the element.
	 * 
	 * @param element
	 *            the new element
	 */
	public void setElement(ElementInfo element) {
		this.element = element;
	}

	/**
	 * Gets the ref element.
	 * 
	 * @return the ref element
	 */
	public ElementInfo getRefElement() {
		return refElement;
	}

	/**
	 * Sets the ref element.
	 * 
	 * @param refElement
	 *            the new ref element
	 */
	public void setRefElement(ElementInfo refElement) {
		this.refElement = refElement;
	}

	

	/**
	 * Gets the change.
	 * 
	 * @return the change
	 */
	public Change getChange() {
		return change;
	}

	/**
	 * Sets the change.
	 * 
	 * @param change
	 *            the new change
	 */
	public void setChange(Change change) {
		this.change = change;
	}

	

	
	

}
