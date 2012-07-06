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

import org.wsdl.tools.wsdlauditor.interfaces.OutputUser;


/**
 * The Class OutputUserConfig.
 */
public class OutputUserConfig {
	
	/** The name. */
	private String name;
	
	/** The disabled. */
	private boolean disabled;
	
	
	/** The output user. */
	private OutputUser outputUser;
	
	/** The output directory. */
	private String outputDirectory;
	
	/** The params. */
	private Map<String,String> params=new HashMap<String, String>();

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Checks if is disabled.
	 * 
	 * @return true, if is disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * Sets the disabled.
	 * 
	 * @param disabled
	 *            the new disabled
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * Gets the output user.
	 * 
	 * @return the output user
	 */
	public OutputUser getOutputUser() {
		return outputUser;
	}

	/**
	 * Sets the output user.
	 * 
	 * @param outputUser
	 *            the new output user
	 */
	public void setOutputUser(OutputUser outputUser) {
		this.outputUser = outputUser;
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
	 * Gets the params.
	 * 
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * Sets the params.
	 * 
	 * @param params
	 *            the params
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	/**
	 * Put param.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void putParam(String name, String value) {
		this.params.put(name, value);
	}
}
