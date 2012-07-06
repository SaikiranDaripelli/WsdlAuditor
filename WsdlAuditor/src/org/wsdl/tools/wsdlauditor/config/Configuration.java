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

import java.util.HashMap;
import java.util.Map;

import org.wsdl.tools.wsdlauditor.interfaces.RuleExecutor;
import org.wsdl.tools.wsdlauditor.ruledefn.Factory;

/**
 * The Class Configuration.
 */
public class Configuration {
	
	/** The output users. */
	private Map<String,OutputUserConfig> outputUsers=new HashMap<String, OutputUserConfig>();

	/** The rule executors. */
	private Map<String, RuleExecutor> ruleExecutors=new HashMap<String, RuleExecutor>();
	
	/** The output directory. */
	private String outputDirectory;

	/** The data objects. */
	private Map<Factory.ObjectTypes, Class<? extends Object>> dataObjects=new HashMap<Factory.ObjectTypes, Class<? extends Object>>();

	/**
	 * Gets the output users.
	 * 
	 * @return the output users
	 */
	public Map<String,OutputUserConfig> getOutputUsers() {
		return outputUsers;
	}

	/**
	 * Sets the output users.
	 * 
	 * @param outputUsers
	 *            the output users
	 */
	public void setOutputUsers(Map<String,OutputUserConfig> outputUsers) {
		this.outputUsers = outputUsers;
	}
	
	/**
	 * Adds the output users.
	 * 
	 * @param name
	 *            the name
	 * @param outputUsers
	 *            the output users
	 */
	public void addOutputUsers(String name,OutputUserConfig outputUsers) {
		this.outputUsers.put(name, outputUsers);
	}

	/**
	 * Gets the rule executors.
	 * 
	 * @return the rule executors
	 */
	public Map<String, RuleExecutor> getRuleExecutors() {
		return ruleExecutors;
	}

	/**
	 * Sets the rule executors.
	 * 
	 * @param ruleExecutors
	 *            the rule executors
	 */
	public void setRuleExecutors(Map<String, RuleExecutor> ruleExecutors) {
		this.ruleExecutors = ruleExecutors;
	}
	
	/**
	 * Put rule executor.
	 * 
	 * @param operator
	 *            the operator
	 * @param ruleExecutors
	 *            the rule executors
	 */
	public void putRuleExecutor(String operator, RuleExecutor ruleExecutors) {
		this.ruleExecutors.put(operator, ruleExecutors);
	}

	/**
	 * Gets the output directory.
	 * 
	 * @return the output directory
	 */
	public String getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * Sets the output directory.
	 * 
	 * @param outputDirectory
	 *            the new output directory
	 */
	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	/**
	 * Gets the data objects.
	 * 
	 * @return the data objects
	 */
	public Map<Factory.ObjectTypes, Class<? extends Object>> getDataObjects() {
		return dataObjects;
	}

	/**
	 * Put data object.
	 * 
	 * @param objectType
	 *            the object type
	 * @param dataObjects
	 *            the data objects
	 */
	public void putDataObject(Factory.ObjectTypes objectType, Class<? extends Object> dataObjects) {
		this.dataObjects.put(objectType, dataObjects);
	}
	
	/**
	 * Sets the data objects.
	 * 
	 * @param dataObjects
	 *            the data objects
	 */
	public void setDataObjects(Map<Factory.ObjectTypes, Class<? extends Object>> dataObjects) {
		this.dataObjects = dataObjects;
	}
}
