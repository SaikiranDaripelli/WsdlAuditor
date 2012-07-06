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
import java.util.Set;

import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Change;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.SchemaTypes;

/**
 * The Class Rules.
 */
public class Rules{
	
	/** The name. */
	private String name;
	
	
	
	/** The on type. */
	private SchemaTypes onType;
	
	/** The rules sets. */
	private Set<RuleSet> rulesSets;
	
	/** The change. */
	private Change change;
	
	/** The includes. */
	private Set<String> includes;

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
	 * Gets the on type.
	 * 
	 * @return the on type
	 */
	public SchemaTypes getOnType() {
		return onType;
	}

	/**
	 * Sets the on type.
	 * 
	 * @param onType
	 *            the new on type
	 */
	public void setOnType(SchemaTypes onType) {
		this.onType = onType;
	}

	/**
	 * Gets the rules sets.
	 * 
	 * @return the rules sets
	 */
	public Set<RuleSet> getRulesSets() {
		if(rulesSets==null){
			rulesSets=new HashSet<RuleSet>();
		}
		return rulesSets;
	}

	/**
	 * Sets the rules sets.
	 * 
	 * @param rulesSets
	 *            the new rules sets
	 */
	public void setRulesSets(Set<RuleSet> rulesSets) {
		this.rulesSets = rulesSets;
	}
	
	/**
	 * Adds the rules sets.
	 * 
	 * @param rulesSet
	 *            the rules set
	 */
	public void addRulesSets(RuleSet rulesSet) {
		if(rulesSets==null){
			rulesSets=new HashSet<RuleSet>();
		}
		rulesSets.add(rulesSet);
	}

	

	

	/**
	 * Gets the includes.
	 * 
	 * @return the includes
	 */
	public Set<String> getIncludes() {
		return includes;
	}

	/**
	 * Sets the includes.
	 * 
	 * @param includes
	 *            the new includes
	 */
	public void setIncludes(Set<String> includes) {
		this.includes = includes;
	}
	
	/**
	 * Adds the includes.
	 * 
	 * @param include
	 *            the include
	 */
	public void addIncludes(String include) {
		if(includes==null){
			includes=new HashSet<String>();
		}
		includes.add(include);
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((change == null) ? 0 : change.hashCode());
		
		result = prime * result
				+ ((includes == null) ? 0 : includes.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((onType == null) ? 0 : onType.hashCode());
		result = prime * result
				+ ((rulesSets == null) ? 0 : rulesSets.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rules other = (Rules) obj;
		if (change == null) {
			if (other.change != null)
				return false;
		} else if (!change.equals(other.change))
			return false;
		if (includes == null) {
			if (other.includes != null)
				return false;
		} else if (!includes.equals(other.includes))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (onType != other.onType)
			return false;
		if (rulesSets == null) {
			if (other.rulesSets != null)
				return false;
		} else if (!rulesSets.equals(other.rulesSets))
			return false;
		return true;
	}

	

}
