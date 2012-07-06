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
package org.wsdl.tools.wsdlauditor.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.wsdl.tools.wsdlauditor.ParserExeption;
import org.wsdl.tools.wsdlauditor.interfaces.OutputUser;
import org.wsdl.tools.wsdlauditor.interfaces.RuleExecutor;
import org.wsdl.tools.wsdlauditor.ruledefn.Factory;
import org.xml.sax.SAXException;

/**
 * The Class ConfigurationReader.
 */
public class ConfigurationReader {
	
	/**
	 * Load configuration.
	 * 
	 * @param configurationUrl
	 *            the configuration url
	 * @param configuration
	 *            the configuration
	 * @return the configuration
	 */
	public static Configuration loadConfiguration(URL configurationUrl,Configuration configuration){
		if(configuration==null){
			configuration=new Configuration();
		}
		try {
			Document document=getDocument(configurationUrl.openStream());
			configuration.setOutputDirectory(getFirstChildContent(document.getDocumentElement(),"defaultoutputdir"));
			populateOutputUsers(configuration, document);
			populateExecutors(configuration, document);
			populateDataObjects(configuration, document);
		} catch (SAXException e) {
			throw new ParserExeption("Error Parsing COnfiguration", e);
		} catch (IOException e) {
			throw new ParserExeption("Error Parsing COnfiguration", e);
		} catch (ParserConfigurationException e) {
			throw new ParserExeption("Error Parsing COnfiguration", e);
		}
		
		return configuration;
	}
	
	/**
	 * Populate output users.
	 * 
	 * @param configuration
	 *            the configuration
	 * @param document
	 *            the document
	 */
	private static void populateOutputUsers(Configuration configuration,
			Document document) {
		NodeList nodeList=document.getElementsByTagName("outputusers");
		if(nodeList!=null && nodeList.getLength()==1){
			Element element=(Element)nodeList.item(0);
			NodeList outputUsers=element.getElementsByTagName("outputuser");
			if(outputUsers!=null){
				for(int i=0;i<outputUsers.getLength();i++){
					Element outputUser=(Element)outputUsers.item(i);
					OutputUserConfig output=new OutputUserConfig();
					output.setName(outputUser.getAttribute("name").trim());
					if(outputUser.hasAttribute("disabled")){
						output.setDisabled(Boolean.parseBoolean(outputUser.getAttribute("disabled")));
					}
					output.setOutputDirectory(getFirstChildContent(outputUser,"outputdir"));
					NodeList params=outputUser.getElementsByTagName("param");
					if(params!=null){
						for(int j=0;j<params.getLength();j++){
							Element param=(Element)params.item(j);
							output.putParam(getFirstChildContent(param,"name"), getFirstChildContent(param,"value"));
						}
					}
					String className=getFirstChildContent(outputUser,"class");
					try {
						Class<? extends OutputUser> outputUserClass=Thread.currentThread().getContextClassLoader().loadClass(className).asSubclass(OutputUser.class);
						output.setOutputUser(outputUserClass.newInstance());
					} catch (ClassNotFoundException e) {
						throw new ParserExeption("OutputUser Class "+className+" Notfound", e);
					} catch (InstantiationException e) {
						throw new ParserExeption("OutputUser Class could not be instantiated, "+className+" , Include default no-arg onstructor.", e);
					} catch (IllegalAccessException e) {
						throw new ParserExeption("OutputUser Class "+className+" Not Accessible", e);
					}
					configuration.addOutputUsers(output.getName(), output);
				}
			}
		}
	}
	
	
	/**
	 * Populate data objects.
	 * 
	 * @param configuration
	 *            the configuration
	 * @param factory
	 *            the factory
	 */
	private static void populateDataObjects(Configuration configuration,
			Document factory) {
		NodeList nodeList=factory.getElementsByTagName("dataobjects");
		if(nodeList!=null && nodeList.getLength()==1){
			Element element=(Element)nodeList.item(0);
			NodeList executors=element.getElementsByTagName("dataobject");
			if(executors!=null){
				for(int i=0;i<executors.getLength();i++){
					Element executor=(Element)executors.item(i);
					String operator=getFirstChildContent(executor,"type");
					String className=getFirstChildContent(executor,"class");
					try {
						Class<? extends Object> reClass=Thread.currentThread().getContextClassLoader().loadClass(className);
						configuration.putDataObject(Factory.ObjectTypes.getInstance(operator), reClass);
					} catch (ClassNotFoundException e) {
						throw new ParserExeption("Dataobject Class "+className+" Notfound", e);
					} 
				}
			}
		}
	}
	
	/**
	 * Populate executors.
	 * 
	 * @param configuration
	 *            the configuration
	 * @param factory
	 *            the factory
	 */
	private static void populateExecutors(Configuration configuration,
			Document factory) {
		NodeList nodeList=factory.getElementsByTagName("executors");
		if(nodeList!=null && nodeList.getLength()==1){
			Element element=(Element)nodeList.item(0);
			NodeList executors=element.getElementsByTagName("executor");
			if(executors!=null){
				for(int i=0;i<executors.getLength();i++){
					Element executor=(Element)executors.item(i);
					String operator=getFirstChildContent(executor,"operator");
					
					String className=getFirstChildContent(executor,"class");
					try {
						Class<? extends RuleExecutor> reClass=Thread.currentThread().getContextClassLoader().loadClass(className).asSubclass(RuleExecutor.class);
						configuration.putRuleExecutor(operator, reClass.newInstance());
					} catch (ClassNotFoundException e) {
						throw new ParserExeption("Rule Executor Class "+className+" Notfound", e);
					} catch (InstantiationException e) {
						throw new ParserExeption("Rule Executor Class could not be instantiated, "+className+" , Include default no-arg onstructor.", e);
					} catch (IllegalAccessException e) {
						throw new ParserExeption("Rule Executor Class "+className+" Not Accessible", e);
					}
				}
			}
		}
	}
	
	/**
	 * Gets the first child content.
	 * 
	 * @param parent
	 *            the parent
	 * @param tagName
	 *            the tag name
	 * @return the first child content
	 */
	private static String getFirstChildContent(Element parent,String tagName){
		NodeList nodeList=parent.getElementsByTagName(tagName);
		if(nodeList!=null && nodeList.getLength()>0){
			Element element=(Element)nodeList.item(0);
			return element.getTextContent().trim();
		}
		return null;
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
	private static Document getDocument(InputStream config) throws SAXException, IOException, ParserConfigurationException {		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.parse(config);
		return doc;
		
	}
}
