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
package org.wsdl.tools.wsdlauditor.ruledefn;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.wsdl.tools.wsdlauditor.WsdlAuditorException;
import org.wsdl.tools.wsdlauditor.config.Configuration;
import org.wsdl.tools.wsdlauditor.config.ConfigurationReader;
import org.wsdl.tools.wsdlauditor.config.OutputUserConfig;
import org.wsdl.tools.wsdlauditor.interfaces.RuleExecutor;

/**
 * The Class Factory.
 */
public class Factory {
	
	/** The config. */
	private Configuration config;
	/**
	 * Instantiates a new context.
	 */
	private Factory() {
		URL defaultConfiguration=Thread.currentThread().getContextClassLoader().getResource("Configuration.xml");
		config=ConfigurationReader.loadConfiguration(defaultConfiguration, config);
	}

	/** The thread loc singleton. */
	private static ThreadLocal<Factory> threadLocSingleton = new ThreadLocal<Factory>() {
		protected synchronized Factory initialValue() {
			return new Factory();
		}
	};

	/**
	 * Refresh context.
	 */
	public static void refreshFactoryToDefaults() {
		threadLocSingleton.set(new Factory());

	}

	/**
	 * Gets the context.
	 * 
	 * @return the context
	 */
	public static Factory getFactory() {
		return threadLocSingleton.get();
	}

	/**
	 * Load configuration.
	 * 
	 * @param configFileUrl
	 *            the config file url
	 */
	public void loadConfiguration(URL configFileUrl) {
		 ConfigurationReader.loadConfiguration(configFileUrl, config);
	}
	
	/**
	 * Gets the rule executor.
	 * 
	 * @param operator
	 *            the operator
	 * @return the rule executor
	 */
	public RuleExecutor getRuleExecutor(String operator) {
		return config.getRuleExecutors().get(operator);
	}
	
	/**
	 * Gets the data object.
	 * 
	 * @param type
	 *            the type
	 * @return the data object
	 */
	public Object getDataObject(Factory.ObjectTypes type) {
		try {
			return config.getDataObjects().get(type).newInstance();
		} catch (InstantiationException e) {
			throw new WsdlAuditorException("Unable to instantiate RuleExeutor : " + config.getDataObjects().get(type), e);
		} catch (IllegalAccessException e) {
			throw new WsdlAuditorException("Unable to access RuleExeutor : " + config.getDataObjects().get(type), e);
		}
	}

	/**
	 * Adds the rule executor.
	 * 
	 * @param operator
	 *            the operator
	 * @param ruleExecutor
	 *            the rule executor
	 */
	public void addRuleExecutor(String operator, RuleExecutor ruleExecutor) {
		config.putRuleExecutor(operator, ruleExecutor);
	}
	
	/**
	 * Gets the output users.
	 * 
	 * @return the output users
	 */
	public List<OutputUserConfig> getOutputUsers() {
		return new ArrayList<OutputUserConfig>(config.getOutputUsers().values());
	}
	
	/**
	 * Gets the output directory.
	 * 
	 * @return the output directory
	 */
	public String getOutputDirectory() {
		return config.getOutputDirectory();
	}
	
	/**
	 * The Enum ObjectTypes.
	 */
	public enum ObjectTypes {
		
		/** The Executor. */
		Executor("Executor"), 
 /** The Rule engine. */
 RuleEngine("RuleEngine"), 
 /** The Element info. */
 ElementInfo(
				"ElementInfo"), 
 /** The Error. */
 Error("Error"), 
 /** The Rule. */
 Rule("Rule"), 
 /** The Rule definition. */
 RuleDefinition(
				"RuleDefinition"), 
 /** The Rule param. */
 RuleParam("RuleParam"), 
 /** The Rule group. */
 RuleGroup(
				"RuleGroup"), 
 /** The Rules. */
 Rules("Rules"), 
 /** The Rule set. */
 RuleSet("RuleSet"), 
 /** The Schema element. */
 SchemaElement(
				"SchemaElement"), 
 /** The Schema error. */
 SchemaError("SchemaError"), 
 /** The Wsdl schema. */
 WsdlSchema(
				"WsdlSchema"),
/** The Rule definition reader. */
RuleDefinitionReader("RuleDefinitionReader"),
/** The Document parser. */
DocumentParser("DocumentParser"),
/** The Output user. */
OutputUser("OutputUser");
		
		/** The value. */
		private String value;

		/**
		 * Instantiates a new object types.
		 * 
		 * @param value
		 *            the value
		 */
		ObjectTypes(String value) {
			this.value = value;
		}

		/**
		 * Gets the value.
		 * 
		 * @return the value
		 */
		public String getValue() {
			return this.value;
		}
		
		/**
		 * Gets the single instance of ObjectTypes.
		 * 
		 * @param value
		 *            the value
		 * @return single instance of ObjectTypes
		 */
		public static ObjectTypes getInstance(String value){
	    	for(ObjectTypes erTyp:ObjectTypes.values()){
	    		if(erTyp.getValue().equals(value)){
	    			return erTyp;
	    		}
	    	}
	    	return null;
	    }
	}
   
}
