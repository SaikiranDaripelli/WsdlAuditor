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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;
import org.wsdl.tools.wsdlauditor.interfaces.DocumentParser;
import org.wsdl.tools.wsdlauditor.interfaces.RuleDefinitionInterface;
import org.wsdl.tools.wsdlauditor.interfaces.RuleExecutor;
import org.wsdl.tools.wsdlauditor.ruledefn.data.Rule;
import org.wsdl.tools.wsdlauditor.ruledefn.data.RuleExecutorParam;
import org.wsdl.tools.wsdlauditor.ruledefn.data.RuleGroup;
import org.wsdl.tools.wsdlauditor.ruledefn.data.RuleSet;
import org.wsdl.tools.wsdlauditor.ruledefn.data.Rules;
import org.wsdl.tools.wsdlauditor.ruledefn.data.SchemaElement;
import org.wsdl.tools.wsdlauditor.ruledefn.data.SchemaError;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Change;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Conjunction;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.SchemaTypes;
import org.wsdl.tools.wsdlauditor.ruledefn.utils.XmlUtil;

/**
 * The Class RuleEngine.
 */
public class RuleEngine {
	
	/** The rule defn. */
	private RuleDefinitionInterface ruleDefn;

	/**
	 * Instantiates a new rule engine.
	 */
	public RuleEngine() {
	}
    
    /**
	 * Sets the rule defn.
	 * 
	 * @param ruleDefn
	 *            the new rule defn
	 */
    public void setRuleDefn(RuleDefinitionInterface ruleDefn){
    	this.ruleDefn = ruleDefn;
    }
	
	/**
	 * Execute.
	 * 
	 * @param schemaType
	 *            the schema type
	 * @param nodes
	 *            the nodes
	 * @param change
	 *            the change
	 * @return the list
	 */
	public List<SchemaError> execute(SchemaTypes schemaType,
			List<SchemaElement> nodes, Change change) {

		Set<Rules> ruless = ruleDefn.getRulesOn(schemaType, change);
		List<SchemaError> errors = new ArrayList<SchemaError>();
		if (nodes != null) {
			for (Rules rules : ruless) {
				for (SchemaElement item : nodes) {

					Set<RuleSet> ruleSets = rules.getRulesSets();
					if (ruleSets != null) {
						for (RuleSet ruleSet : ruleSets) {
							boolean ruleVal = evaluateRuleSet(ruleSet, item,
									change);
							if (ruleVal) {
								SchemaError shemaError = (SchemaError)Factory.getFactory().getDataObject(Factory.ObjectTypes.SchemaError);
								shemaError.setError(ruleSet.getError());
								if (item.getElement() != null) {
									shemaError.setEntityName(item
											.getElementName());
								} else if (item.getRefElement() != null) {
									shemaError.setEntityName(item
											.getElementName());
								}
								errors.add(shemaError);
							}
						}
					}
				}

			}
		}
		return errors;

	}

	/**
	 * Process conjunction.
	 * 
	 * @param values
	 *            the values
	 * @param conjuntion
	 *            the conjuntion
	 * @return true, if successful
	 */
	private boolean processConjunction(List<Boolean> values,
			Conjunction conjuntion) {

		boolean retVal = true;

		if (conjuntion.equals(Conjunction.Or)) {
			retVal = false;
		}
		for (Boolean bool : values) {
			if (conjuntion.equals(Conjunction.And)) {
				retVal = retVal && bool;
			}
			if (conjuntion.equals(Conjunction.Or)) {
				retVal = retVal || bool;
			}
		}

		return retVal;
	}

	/**
	 * Evaluate rule set.
	 * 
	 * @param ruleSet
	 *            the rule set
	 * @param element
	 *            the element
	 * @param change
	 *            the change
	 * @return true, if successful
	 */
	private boolean evaluateRuleSet(RuleSet ruleSet, SchemaElement element,
			Change change) {

		List<RuleGroup> groups = ruleSet.getGroups();
		List<Boolean> retVals = new ArrayList<Boolean>();
		if (groups != null) {
			for (RuleGroup group : groups) {
				retVals.add(evaluateGroup(group, element, change));
			}
		}
		if (retVals.isEmpty()) {
			return true;
		}

		Conjunction conj = ruleSet.getConjunction();
		if (conj == null) {
			conj = Conjunction.And;
		}
		return processConjunction(retVals, conj);
	}

	/**
	 * Evaluate group.
	 * 
	 * @param group
	 *            the group
	 * @param element
	 *            the element
	 * @param change
	 *            the change
	 * @return true, if successful
	 */
	private boolean evaluateGroup(RuleGroup group, SchemaElement element,
			Change change) {
		List<Boolean> retVals = new ArrayList<Boolean>();
		if (group.getGroups() != null) {
			for (RuleGroup temp : group.getGroups()) {
				retVals.add(evaluateGroup(temp, element, change));
			}
		}

		if (group.getRules() != null) {
			for (Rule rule : group.getRules()) {
				if (rule.getChildRuleSet() != null) {
					RuleSet childRuleSet = ruleDefn.getRuleSets().get(
							rule.getChildRuleSet().trim());
					if (childRuleSet != null) {
						List<Element> children = XmlUtil.getChilds(element
								.getElement().getElement(), rule.getName(),
								element.getSchema());
						List<Boolean> childRet = new ArrayList<Boolean>();
						for (Element child : children) {
							DocumentParser parser=(DocumentParser)Factory.getFactory().getDataObject(Factory.ObjectTypes.DocumentParser);
							SchemaElement schemaElem = parser.getSchemaInfo(
									null, element.getSchema(), child, null);
							childRet.add(evaluateRuleSet(childRuleSet,
									schemaElem, change));
						}
						retVals.add(processConjunction(childRet,
								Conjunction.And));
					}
				} else {
					RuleExecutor executor = Factory.getFactory()
							.getRuleExecutor(rule.getOper());
					if (executor != null) {
						RuleExecutorParam ruleParam = (RuleExecutorParam)Factory.getFactory().getDataObject(Factory.ObjectTypes.RuleParam);
						ruleParam.setChange(change);
						ruleParam.setRuleElement(element);
						ruleParam.setRule(rule);
						retVals.add(executor.execute(ruleParam));
					}
				}
			}
		}
		if (retVals.isEmpty()) {
			return true;
		}
		Conjunction conj = group.getConjunction();
		if (conj == null) {
			conj = Conjunction.And;
		}
		boolean retVal = processConjunction(retVals, conj);
		if (group.isNegate()) {
			retVal = !retVal;
		}
		return retVal;
	}

}
