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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.wsdl.tools.wsdlauditor.interfaces.RuleDefinitionInterface;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Change;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.SchemaTypes;

/**
 * The Class RuleDefinition.
 */
public class RuleDefinition implements RuleDefinitionInterface{
	
	/** The rules. */
	private Map<String, Rules> rules;
	
	/** The rule sets. */
	private Map<String, RuleSet> ruleSets;
	
	/**
	 * Gets the rules.
	 * 
	 * @return the rules
	 */
	public Map<String, Rules> getRules() {
		return rules;
	}
	
	/* (non-Javadoc)
	 * @see org.wsdl.tools.wsdlauditor.interfaces.RuleDefinitionInterface#getRulesOnName(java.lang.String, org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Change)
	 */
	public Set<Rules>  getRulesOnName(String name,Change change){
		Set<Rules> retRules=new HashSet<Rules>();
		Rules retRule=rules.get(name);
		if(retRule!=null){
			if((change==null && retRule.getChange()==null) || (change!=null && change.equals(retRule.getChange()))){
				retRules.add(retRule);
				if(retRule.getIncludes()!=null){
					for(String include:retRule.getIncludes()){
						retRules.addAll(getRulesOnName(include,null));
					}
				}
			}
		}
		return retRules;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.wsdl.tools.wsdlauditor.interfaces.RuleDefinitionInterface#addRules(org.wsdl.tools.wsdlauditor.ruledefn.data.Rules)
	 */
	public void addRules(Rules rules) {
		if(this.rules==null){
			this.rules=new HashMap<String, Rules>();
		}
		this.rules.put(rules.getName(), rules);
		
	}
	
	/* (non-Javadoc)
	 * @see org.wsdl.tools.wsdlauditor.interfaces.RuleDefinitionInterface#getRulesOn(org.wsdl.tools.wsdlauditor.ruledefn.data.enums.SchemaTypes, org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Change)
	 */
	public Set<Rules> getRulesOn(SchemaTypes schemaType,Change change){
		Set<Rules> retRules=new HashSet<Rules>();
		if(this.rules!=null){
			for(Map.Entry<String, Rules> entry:rules.entrySet()){
				if(schemaType.equals(entry.getValue().getOnType())){
					retRules.addAll(getRulesOnName(entry.getKey(),change));
				}
				
			}
		}
		return retRules;
	}
	
	
	/* (non-Javadoc)
	 * @see org.wsdl.tools.wsdlauditor.interfaces.RuleDefinitionInterface#getRuleSets()
	 */
	public Map<String, RuleSet> getRuleSets() {
		return ruleSets;
	}
	
	/* (non-Javadoc)
	 * @see org.wsdl.tools.wsdlauditor.interfaces.RuleDefinitionInterface#addRuleSet(org.wsdl.tools.wsdlauditor.ruledefn.data.RuleSet)
	 */
	public void addRuleSet(RuleSet ruleSet) {
		if(ruleSets==null){
			ruleSets=new HashMap<String, RuleSet>();
		}
		ruleSets.put(ruleSet.getName(), ruleSet);
	}
	
}
