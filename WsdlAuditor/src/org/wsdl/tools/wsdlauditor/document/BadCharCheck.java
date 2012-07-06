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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.wsdl.tools.wsdlauditor.ParserExeption;
import org.wsdl.tools.wsdlauditor.ruledefn.Factory;
import org.wsdl.tools.wsdlauditor.ruledefn.data.Error;
import org.wsdl.tools.wsdlauditor.ruledefn.data.SchemaError;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.ErrorType;

/**
 * The Class BadCharCheck.
 */
public class BadCharCheck {
	
	/**
	 * Check bad chars.
	 * 
	 * @param file
	 *            the file
	 * @return the list
	 */
	public static List<SchemaError> checkBadChars(URL file){
		List<SchemaError> errors=new ArrayList<SchemaError>();
		if(file!=null){
			
			try {
				BufferedReader br=new BufferedReader(new InputStreamReader(file.openStream()));
				String line=br.readLine();
				int j=0;
				while(line!=null){
					j++;
					for(int i=0;i<line.length();i++){
						if(line.codePointAt(i)>127){
							SchemaError shemaError = (SchemaError)Factory.getFactory().getDataObject(Factory.ObjectTypes.SchemaError);
							shemaError.setEntityName(file.getFile());
							Error error=(Error)Factory.getFactory().getDataObject(Factory.ObjectTypes.Error);
							error.setErrorType(ErrorType.ERROR);
							error.setErrorCode("-9999");
							error.setMessage("Bad Unicode Character encounted at line number: <br/>"+ j + " Charcter : "+ line.charAt(i));
							error.setSuggestion("Remove the bad character as only UTF-8 characters can be parsed, check with XmlSpy.<br/> Line with Error: "+ line);
							shemaError.setError(error);
							errors.add(shemaError);
						}
					}
					line=br.readLine();
					
				}
			} catch (IOException e) {
				throw new ParserExeption("Error Parsing documents", e);
			}
		}
		return errors;
	}

}
