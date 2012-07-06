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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.wsdl.tools.wsdlauditor.interfaces.RuleDefinitionInterface;
import org.wsdl.tools.wsdlauditor.interfaces.RuleDefinitionReader;
import org.wsdl.tools.wsdlauditor.ruledefn.data.Error;
import org.wsdl.tools.wsdlauditor.ruledefn.data.Rule;
import org.wsdl.tools.wsdlauditor.ruledefn.data.RuleGroup;
import org.wsdl.tools.wsdlauditor.ruledefn.data.RuleSet;
import org.wsdl.tools.wsdlauditor.ruledefn.data.Rules;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Change;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Conjunction;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.ErrorType;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.OnTypes;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.SchemaTypes;
import org.xml.sax.SAXException;

/**
 * The Class DefaultRuleDefinitionReader.
 */
public class DefaultRuleDefinitionReader implements RuleDefinitionReader {

	/* (non-Javadoc)
	 * @see org.wsdl.tools.wsdlauditor.interfaces.RuleDefinitionReader#getRuleDefinition(java.net.URL)
	 */
	public RuleDefinitionInterface getRuleDefinition(URL defnFile) throws Exception {
		RuleDefinitionInterface ruleDefi=(RuleDefinitionInterface)Factory.getFactory().getDataObject(Factory.ObjectTypes.RuleDefinition);
		Document doc=getDocument(defnFile.openStream());
		NodeList ruleSets=doc.getElementsByTagName("rulesets");
		if(ruleSets!=null && ruleSets.getLength()==1){
			Element ruleSetsElem=(Element)ruleSets.item(0);
			ruleSets=ruleSetsElem.getElementsByTagName("ruleset");
			if(ruleSets!=null){
				for(int i=0;i<ruleSets.getLength();i++){
					RuleSet rules=processRuleSet((Element)ruleSets.item(i));
					ruleDefi.addRuleSet(rules);
				}
			}

		}
		NodeList ruless=doc.getElementsByTagName("rules");
		if(ruless!=null){
			for(int i=0;i<ruless.getLength();i++){
				Rules rules=processRules((Element)ruless.item(i),ruleDefi);
				ruleDefi.addRules(rules);
			}
		}
		return ruleDefi;
	}
   
	/**
	 * Process rules.
	 * 
	 * @param element
	 *            the element
	 * @param currentRuleDefn
	 *            the current rule defn
	 * @return the rules
	 */
	protected Rules processRules(Element element,RuleDefinitionInterface currentRuleDefn){
		Rules rules=(Rules)Factory.getFactory().getDataObject(Factory.ObjectTypes.Rules);
		rules.setName(element.getAttribute("name"));
		
		rules.setChange(Change.getInstance(element.getAttribute("change")));
		rules.setOnType(SchemaTypes.getInstance(element.getAttribute("on")));
		NodeList inludes=element.getElementsByTagName("include");
		if(inludes!=null){
			for(int i=0;i<inludes.getLength();i++){
				Element include=(Element)inludes.item(i);
				if(include.hasAttribute("rules")){
					rules.addIncludes(include.getAttribute("rules"));
				}
				if(include.hasAttribute("ruleset")){
					if(currentRuleDefn.getRuleSets()!=null && currentRuleDefn.getRuleSets().containsKey(include.getAttribute("ruleset"))){
						rules.addRulesSets(currentRuleDefn.getRuleSets().get(include.getAttribute("ruleset")));
					}
				}
			}
		}
		return rules;
		
	}
	
	/**
	 * Process rule set.
	 * 
	 * @param item
	 *            the item
	 * @return the rule set
	 */
	protected RuleSet processRuleSet(Element item) {
		RuleSet ruleSet=(RuleSet)Factory.getFactory().getDataObject(Factory.ObjectTypes.RuleSet);
		ruleSet.setConjunction(Conjunction.getInstance(item.getAttribute("conjunction")));
		ruleSet.setName(item.getAttribute("name"));
		NodeList errors=item.getElementsByTagName("error");
		if(errors!=null && errors.getLength()>0){
				org.wsdl.tools.wsdlauditor.ruledefn.data.Error error=processError((Element)errors.item(0));
				ruleSet.setError(error);
		}
		
		NodeList groups=item.getElementsByTagName("group");
		if(groups!=null){
			for(int i=0;i<groups.getLength();i++){
				RuleGroup group=processGroup((Element)groups.item(i));
				ruleSet.addGroups(group);
			}
		}
		return ruleSet;
	}

	/**
	 * Process group.
	 * 
	 * @param item
	 *            the item
	 * @return the rule group
	 */
	protected RuleGroup processGroup(Element item) {
		RuleGroup group=(RuleGroup)Factory.getFactory().getDataObject(Factory.ObjectTypes.RuleGroup);
		group.setConjunction(Conjunction.getInstance(item.getAttribute("conjunction")));
		group.setNegate(Boolean.parseBoolean(item.getAttribute("negate")));
		
		NodeList groups=item.getElementsByTagName("group");
		if(groups!=null){
			for(int i=0;i<groups.getLength();i++){
				RuleGroup innerGroup=processGroup((Element)groups.item(i));
				group.addGroups(innerGroup);
			}
		}
		
		NodeList rules=item.getElementsByTagName("rule");
		if(rules!=null){
			for(int i=0;i<rules.getLength();i++){
				Rule rule=processRule((Element)rules.item(i));
				group.addRules(rule);
			}
		}
		
		return group;
	}

	/**
	 * Process rule.
	 * 
	 * @param item
	 *            the item
	 * @return the rule
	 */
	protected Rule processRule(Element item) {
		Rule rule=(Rule)Factory.getFactory().getDataObject(Factory.ObjectTypes.Rule);
		String on=item.getAttribute("on");
		rule.setOn(OnTypes.getInstance(on));
		rule.setOper(item.getAttribute("oper"));
		rule.setName(item.getAttribute("name"));
		rule.setValue(item.getAttribute("value"));
		rule.setIgnoreCase(Boolean.parseBoolean(item.getAttribute("ignorecase")));
		if(OnTypes.Child.equals(rule.getOn())){
			String ruleset=item.getAttribute("ruleset");
			if(ruleset!=null && !"".equals(ruleset.trim())){
				rule.setChildRuleSet(ruleset);
			}
		}
		return rule;
	}

	/**
	 * Process error.
	 * 
	 * @param item
	 *            the item
	 * @return the error
	 */
	protected Error processError(Element item) {
		Error error=(Error)Factory.getFactory().getDataObject(Factory.ObjectTypes.Error);
		
		error.setErrorCode(item.getAttribute("code"));
		String errTyp=item.getAttribute("type");
		error.setErrorType(ErrorType.getInstance(errTyp));
		
		NodeList message=item.getElementsByTagName("message");
		if(message!=null && message.getLength()>0){
			error.setMessage(message.item(0).getTextContent());
		}
		
		NodeList sugg=item.getElementsByTagName("suggestion");
		if(sugg!=null && sugg.getLength()>0){
			error.setSuggestion(sugg.item(0).getTextContent());
		}
		return error;
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
	private Document getDocument(InputStream config) throws SAXException, IOException, ParserConfigurationException {		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.parse(config);
		return doc;
		
	}

}
