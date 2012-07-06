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


import org.wsdl.tools.wsdlauditor.interfaces.RuleExecutor;
import org.wsdl.tools.wsdlauditor.ruledefn.data.Rule;
import org.wsdl.tools.wsdlauditor.ruledefn.data.RuleExecutorParam;
import org.wsdl.tools.wsdlauditor.ruledefn.executors.exception.ExecutionException;
import org.wsdl.tools.wsdlauditor.ruledefn.utils.Util;

/**
 * The Class MethodExec.
 */
public class MethodExec implements RuleExecutor {

	/* (non-Javadoc)
	 * @see org.wsdl.tools.wsdlauditor.interfaces.RuleExecutor#execute(org.wsdl.tools.wsdlauditor.ruledefn.data.RuleExecutorParam)
	 */
	@Override
	public boolean execute(RuleExecutorParam param) {
		Rule rule=param.getRule();
		
		String compareValue=rule.getValue();
		try {
			if(!Util.isNullOrEmpty(compareValue)){
				Class<? extends RuleExecutor> methExe= Thread.currentThread().getContextClassLoader().loadClass(compareValue.trim()).asSubclass(RuleExecutor.class);
				RuleExecutor ruleExe= methExe.newInstance();
				return ruleExe.execute(param);
			}
		} catch (ClassNotFoundException e) {
			throw new ExecutionException(e);
		} catch (InstantiationException e) {
			throw new ExecutionException(e);
		} catch (IllegalAccessException e) {
			throw new ExecutionException(e);
		}
		
		return false;
	}

	
}
