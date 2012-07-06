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
package org.wsdl.tools.wsdlauditor.ruledefn.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.wsdl.tools.wsdlauditor.WsdlAuditorException;
import org.wsdl.tools.wsdlauditor.ruledefn.data.Rule;
import org.wsdl.tools.wsdlauditor.ruledefn.data.RuleExecutorParam;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.Change;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.OnTypes;

/**
 * The Class Util.
 */
public class Util {
	
	/**
	 * Checks if is null or empty.
	 * 
	 * @param str
	 *            the str
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(String str){
		
		return str==null || "".equals(str.trim());
	}
	
	/**
	 * Gets the element based on change.
	 * 
	 * @param param
	 *            the param
	 * @return the element based on change
	 */
	public static Element getElementBasedOnChange(RuleExecutorParam param){
		Element element=null;
		if(param.getChange()==null || param.getChange()==Change.Added){
			element=param.getRuleElement().getElement().getElement();
		}else if(param.getChange()==Change.Removed){
			element=param.getRuleElement().getRefElement().getElement();
		}
		return element;
	}
	
	/**
	 * Gets the value.
	 * 
	 * @param param
	 *            the param
	 * @param rule
	 *            the rule
	 * @return the value
	 */
	public static List<String> getValue(RuleExecutorParam param, Rule rule) {
		Element element=getElementBasedOnChange(param);
		 List<String> value=new ArrayList<String>();
		if(element!=null){
			if(OnTypes.Attribute.equals(rule.getOn())){
				if("*".equals(rule.getName())){
					NamedNodeMap nodes=element.getAttributes();
					if(nodes!=null){
						for(int i=0;i<nodes.getLength();i++){
							value.add(nodes.item(i).getNodeValue());
						}
					}
				}else{
					value.add(element.getAttribute(rule.getName()));
				}
			}else if(OnTypes.Value.equals(rule.getOn())){
				value.add(element.getTextContent());
			}else if(OnTypes.Comment.equals(rule.getOn())){
				if(rule.getName()==null || "previous".equals(rule.getName())){
					value.add(XmlUtil.getPreviousComment(element));
				}else if("next".equals(rule.getName())){
					value.add(XmlUtil.getNextComment(element));
				}
			}
		}
		return value;
	}
	
	/**
	 * The path supplied is first tried as a URL external form string, if it fails it is tried as a Class path resource,
	 * Then it is tried as a local file path.
	 * 
	 * @param path
	 *            the path 
	 * @return the uRL
	 */
	public static URL convertToURL(String path) {
		URL url = null;
		try {
			url = new URL(path);
		} catch (MalformedURLException e) {
			url = Thread.currentThread().getContextClassLoader().getResource(
					path);
			if (url == null) {
				File file = new File(path);
				if (file.exists()) {
					try {
						url = file.toURI().toURL();
					} catch (MalformedURLException e1) {
						Logger.getLogger(Util.class.getName())
								.log(Level.SEVERE, path + " is Not valid", e1);
						throw new WsdlAuditorException(path + " is Not valid",e1);
					}
				}
			}
		}
		return url;
	}

}
