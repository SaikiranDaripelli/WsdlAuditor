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

import java.util.ArrayList;
import java.util.List;

import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Conjunction;

/**
 * The Class RuleSet.
 */
public class RuleSet{
	
	/** The groups. */
	private List<RuleGroup> groups;
	
	/** The conjunction. */
	private Conjunction conjunction;

	/** The error. */
	private Error error;
	
	/** The name. */
	private String name;

	/**
	 * Gets the groups.
	 * 
	 * @return the groups
	 */
	public List<RuleGroup> getGroups() {
		return groups;
	}

	/**
	 * Sets the groups.
	 * 
	 * @param groups
	 *            the new groups
	 */
	public void setGroups(List<RuleGroup> groups) {
		this.groups = groups;
	}
	
	/**
	 * Adds the groups.
	 * 
	 * @param group
	 *            the group
	 */
	public void addGroups(RuleGroup group) {
		if(groups==null){
			groups=new ArrayList<RuleGroup>();
		}
		groups.add(group);
	}

	/**
	 * Gets the error.
	 * 
	 * @return the error
	 */
	public Error getError() {
		return error;
	}

	/**
	 * Sets the error.
	 * 
	 * @param error
	 *            the new error
	 */
	public void setError(Error error) {
		this.error = error;
	}

	/**
	 * Gets the conjunction.
	 * 
	 * @return the conjunction
	 */
	public Conjunction getConjunction() {
		return conjunction;
	}

	/**
	 * Sets the conjunction.
	 * 
	 * @param conjunction
	 *            the new conjunction
	 */
	public void setConjunction(Conjunction conjunction) {
		this.conjunction = conjunction;
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
}
