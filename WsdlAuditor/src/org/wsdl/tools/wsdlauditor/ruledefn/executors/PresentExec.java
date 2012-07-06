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
package org.wsdl.tools.wsdlauditor.ruledefn.executors;

import java.util.List;

import org.w3c.dom.Element;
import org.wsdl.tools.wsdlauditor.interfaces.RuleExecutor;
import org.wsdl.tools.wsdlauditor.ruledefn.data.Rule;
import org.wsdl.tools.wsdlauditor.ruledefn.data.RuleExecutorParam;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.OnTypes;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Operators;
import org.wsdl.tools.wsdlauditor.ruledefn.utils.Util;
import org.wsdl.tools.wsdlauditor.ruledefn.utils.XmlUtil;

/**
 * The Class PresentExec.
 */
public class PresentExec implements RuleExecutor {

	/* (non-Javadoc)
	 * @see org.wsdl.tools.wsdlauditor.interfaces.RuleExecutor#execute(org.wsdl.tools.wsdlauditor.ruledefn.data.RuleExecutorParam)
	 */
	@Override
	public boolean execute(RuleExecutorParam param) {
		Rule rule=param.getRule();
		Element element=Util.getElementBasedOnChange(param);
		boolean value=false;
		if(OnTypes.Attribute.equals(rule.getOn())){
			List<String> values=Util.getValue(param, rule);
			if(values.isEmpty()){
				value=false;
			}else{
				value=true;
			}
			
		}else if(OnTypes.Value.equals(rule.getOn())){
			if(!Util.isNullOrEmpty(element.getTextContent())){
				value=true;
			}
		}else if(OnTypes.Comment.equals(rule.getOn())){
			String str=null;
			if(rule.getName()==null || "previous".equals(rule.getName())){
				str=XmlUtil.getPreviousComment(element);
			}else if("next".equals(rule.getName())){
				str=XmlUtil.getNextComment(element);
			}
			if(str!=null){
				value=true;
			}
		}else if(OnTypes.Child.equals(rule.getOn())){
			List<Element> children=XmlUtil.getChilds(element,rule.getName(),param.getRuleElement().getSchema());
			if(children!=null && !children.isEmpty()){
				value=true;
			}
		}
		if(Operators.NotPresent.getValue().equals(rule.getOper())){
			value=!value;
		}
		return value;
	}

	
}
