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

import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.OnTypes;

/**
 * The Class Rule.
 */
public class Rule{
	
	/** The on. */
	private OnTypes on;
	
	/** The oper. */
	private String oper;
	
	/** The name. */
	private String name;
	
	/** The value. */
	private String value;
	
	/** The ignore case. */
	private boolean ignoreCase;
	
	/** The child rule set. */
	private String childRuleSet;

	/**
	 * Gets the on.
	 * 
	 * @return the on
	 */
	public OnTypes getOn() {
		return on;
	}

	/**
	 * Sets the on.
	 * 
	 * @param on
	 *            the new on
	 */
	public void setOn(OnTypes on) {
		this.on = on;
	}

	/**
	 * Gets the oper.
	 * 
	 * @return the oper
	 */
	public String getOper() {
		return oper;
	}

	/**
	 * Sets the oper.
	 * 
	 * @param oper
	 *            the new oper
	 */
	public void setOper(String oper) {
		this.oper = oper;
	}

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
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Checks if is ignore case.
	 * 
	 * @return true, if is ignore case
	 */
	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	/**
	 * Sets the ignore case.
	 * 
	 * @param ignoreCase
	 *            the new ignore case
	 */
	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	/**
	 * Gets the child rule set.
	 * 
	 * @return the child rule set
	 */
	public String getChildRuleSet() {
		return childRuleSet;
	}

	/**
	 * Sets the child rule set.
	 * 
	 * @param childRuleSet
	 *            the new child rule set
	 */
	public void setChildRuleSet(String childRuleSet) {
		this.childRuleSet = childRuleSet;
	}

}
