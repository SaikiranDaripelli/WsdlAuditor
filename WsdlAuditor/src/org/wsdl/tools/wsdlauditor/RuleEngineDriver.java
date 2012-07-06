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
package org.wsdl.tools.wsdlauditor;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.wsdl.tools.wsdlauditor.config.OutputUserConfig;
import org.wsdl.tools.wsdlauditor.document.BadCharCheck;
import org.wsdl.tools.wsdlauditor.interfaces.DocumentParser;
import org.wsdl.tools.wsdlauditor.interfaces.OutputUser;
import org.wsdl.tools.wsdlauditor.interfaces.RuleDefinitionInterface;
import org.wsdl.tools.wsdlauditor.interfaces.RuleDefinitionReader;
import org.wsdl.tools.wsdlauditor.ruledefn.Factory;
import org.wsdl.tools.wsdlauditor.ruledefn.RuleEngine;
import org.wsdl.tools.wsdlauditor.ruledefn.data.SchemaError;
import org.wsdl.tools.wsdlauditor.ruledefn.data.WsdlSchema;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.SchemaTypes;
import org.wsdl.tools.wsdlauditor.ruledefn.utils.Util;

/**
 * The Class RuleEngineDriver.
 */
public class RuleEngineDriver {

	

	/**
	 * Check schema.
	 * 
	 * @param ruleDefn
	 *            the rule defn
	 * @param doc
	 *            the doc
	 * @return the list
	 */
	private static List<SchemaError> checkSchema(RuleDefinitionInterface ruleDefn,
			WsdlSchema doc) {
		List<SchemaError> schemaErrors = new ArrayList<SchemaError>();
		RuleEngine ruleEngine =(RuleEngine)Factory.getFactory().getDataObject(Factory.ObjectTypes.RuleEngine);
		ruleEngine.setRuleDefn(ruleDefn);
		schemaErrors.addAll(ruleEngine.execute(SchemaTypes.Service,
				doc.getService(), null));
		schemaErrors.addAll(ruleEngine.execute(SchemaTypes.Operation,
				doc.getOperation(), null));

		schemaErrors.addAll(ruleEngine.execute(SchemaTypes.RequestType,
				doc.getRequestType(), null));

		schemaErrors.addAll(ruleEngine.execute(SchemaTypes.ResponseType,
				doc.getResponseType(), null));

		schemaErrors.addAll(ruleEngine.execute(SchemaTypes.ComplexType,
				doc.getComplexType(), null));
		schemaErrors.addAll(ruleEngine.execute(SchemaTypes.SimpleType,
				doc.getSimpleType(), null));
		schemaErrors.addAll(ruleEngine.execute(SchemaTypes.Element,
				doc.getElements(), null));
		schemaErrors.addAll(ruleEngine.execute(SchemaTypes.Attribute,
				doc.getAttribute(), null));
		schemaErrors.addAll(ruleEngine.execute(SchemaTypes.Enumeration,
				doc.getEnumeration(), null));
		return schemaErrors;
	}
    
    /**
	 * Validate inputs.
	 * 
	 * @param newDocumentUrl
	 *            the new document url
	 * @param ruleDefnUrl
	 *            the rule defn url
	 */
    private static void validateInputs(URL newDocumentUrl, URL ruleDefnUrl){
    	if(newDocumentUrl==null || ruleDefnUrl==null){
    		throw new WsdlAuditorException("Input not valid, Wsdl Document and RuleDefinition are mandatory");
    	}
    }
	
	/**
	 * Check schema, this is the main method whcich is responsible for the lifecycle of WsdlAuditor,
	 * This method validates the input, calls the WsdlParser, RuleEngine and Output Users.
	 * 
	 * @param newDocumentUrl
	 *            the new document url
	 * @param oldDocumentUrl
	 *            the old document url
	 * @param ruleDefnUrl
	 *            the rule defn url
	 * @param outputFolder
	 *            the output folder
	 * @param configFileUrl
	 *            the config file url
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public static List<SchemaError> checkSchema(
			URL newDocumentUrl,URL oldDocumentUrl, URL ruleDefnUrl,String outputFolder,URL configFileUrl)
			throws Exception {
		validateInputs(newDocumentUrl,ruleDefnUrl);
		Factory factory=Factory.getFactory();
		if(configFileUrl!=null){
			factory.loadConfiguration(configFileUrl);
		}
		List<SchemaError> errors=BadCharCheck.checkBadChars(newDocumentUrl);
		if(errors.isEmpty()){
			RuleDefinitionInterface ruleDefn = ((RuleDefinitionReader)factory.getDataObject(Factory.ObjectTypes.RuleDefinitionReader))
					.getRuleDefinition(ruleDefnUrl);
			DocumentParser parser=(DocumentParser)factory.getDataObject(Factory.ObjectTypes.DocumentParser);
			WsdlSchema doc = parser.parseWsdl(newDocumentUrl,oldDocumentUrl);
				errors=checkSchema(ruleDefn,doc);
		}
		for(OutputUserConfig outputUserConfig:factory.getOutputUsers()){
			if(!outputUserConfig.isDisabled()){
				OutputUser op=outputUserConfig.getOutputUser();
				if(Util.isNullOrEmpty(outputFolder)){
					outputFolder=outputUserConfig.getOutputDirectory();
					if(Util.isNullOrEmpty(outputFolder)){
						outputFolder=factory.getOutputDirectory();
					}
				}
				if(outputFolder==null || outputFolder.isEmpty()){
					throw new WsdlAuditorException("Input not valid, outputdir is mandatory");
				}
				op.generate(errors, outputFolder, newDocumentUrl, outputUserConfig.getParams());
			}
			
		}
		return errors;
	}
    

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		String fileName="C:/Saikiran/Work/Workspace/DocTool/src/test/resources/sampleWsdl/CatalogService(1.0.0).wsdl";
		File file=new File(fileName);
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		System.out.println(checkSchema(file.toURI().toURL(),null,
				loader.getResource("SampleRuleDefinition.xml"),"c:/wsdlAuditor/",null));
		
	}

}
