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
package org.wsdl.tools.wsdlauditor.interfaces;

import java.net.URL;

import org.w3c.dom.Element;
import org.wsdl.tools.wsdlauditor.ruledefn.data.ElementInfo;
import org.wsdl.tools.wsdlauditor.ruledefn.data.SchemaElement;
import org.wsdl.tools.wsdlauditor.ruledefn.data.WsdlSchema;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Change;

/**
 * The Interface DocumentParser.
 */
public interface DocumentParser {
	
	/**
	 * Parses the wsdl. Creates the WsdlSchema Element considering new and old wsdl documents.
	 * 
	 * @param documentUrl
	 *            the document url
	 * @param oldDocumentUrl
	 *            the old document url
	 * @return the wsdl schema
	 */
	public WsdlSchema parseWsdl(URL documentUrl, URL oldDocumentUrl);
	
	/**
	 * returns new instance of schema info object after populating the element accordingly.
	 * 
	 * @param oldDocElement
	 *            the old doc element
	 * @param retSchema
	 *            the ret schema
	 * @param element
	 *            the element
	 * @param change
	 *            the change
	 * @return the schema info
	 */
	public  SchemaElement getSchemaInfo(ElementInfo oldDocElement,
			WsdlSchema retSchema, Element element,Change change);
	
	/**
	 * Gets the element info.
	 * 
	 * @param element
	 *            the element
	 * @return the element info
	 */
	public  ElementInfo  getElementInfo(Element element);
}
