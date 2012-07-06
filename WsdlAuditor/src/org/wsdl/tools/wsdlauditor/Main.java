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
package org.wsdl.tools.wsdlauditor;

import java.util.Map;

import org.wsdl.tools.wsdlauditor.ruledefn.utils.Util;

/**
 * The Class Main.
 */
public class Main {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		Map<String,String> argMap=CommandLineParser.createArgMap(args);
		
		if(argMap.containsKey("help")){
			System.out.println("Usage:\n" +
					"java -jar WsdlAuditor.jar document=<WSDL DocumentPath> outputdir=<output directory> ruledef=<ruledef file path>\n" +
					"for running compare rules use following command\n" +
					"java -jar WsdlAuditor.jar document=<WSDL DocumentPath> comparedoc=<old wsdl path> outputdir=<output directory> ruledef=<ruledef file path>\n" +
					"for supplying tool configuration add following argument config=<configuration file path>\n" +
					"if help is used as argument furthur processing is ignored and only help text is displayed\n" +
					"document,ruledef are the mandatory arguments, while outputdir is mandatory but it can be supplied through config also" +
					"for detailed information visit http://wsdlauditor.sourceforge.net/");
			return;
		}
		RuleEngineDriver.checkSchema(Util.convertToURL(argMap.get("document")), Util.convertToURL(argMap.get("comparedoc")), Util.convertToURL(argMap.get("ruledef")), argMap.get("outputdir"), Util.convertToURL(argMap.get("config")));
		System.out.println("Audit Report generated sucessfully and is saved.");
	}
}
