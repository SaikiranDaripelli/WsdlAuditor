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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * The Class CommandLineParser.
 */
public class CommandLineParser {

	
/**
 * Creates a Map of the User Command Line Arguments.
 * 
 * @param args
 *            the args
 * @return the map
 */
public static Map<String,String> createArgMap(String[] args) {
		
		Logger.getLogger(CommandLineParser.class.getName()).entering("CommandLineParser", "createArgMap",args);
		Map<String,String> retMap=null;
		if(args!=null){
			retMap=new HashMap<String, String>();
			for(String arg:args){
				if("help".equalsIgnoreCase(arg)){
					retMap.put("help",null);
				}else{
				String[] values=arg.split("=");
				if(values==null || values.length!=2){
					Logger.getLogger(CommandLineParser.class.getName()).severe("Command Line Arguments should be in name=value pairs");
				}
				
				retMap.put(values[0], values[1]);
				}
			}
		}
		Logger.getLogger(CommandLineParser.class.getName()).exiting("CommandLineParser", "createArgMap",retMap);
		return retMap;
	}
}
