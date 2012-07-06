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
package org.wsdl.tools.wsdlauditor.html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.wsdl.tools.wsdlauditor.OutputUserException;
import org.wsdl.tools.wsdlauditor.interfaces.OutputUser;
import org.wsdl.tools.wsdlauditor.ruledefn.data.SchemaError;

/**
 * The Class DefaultOutputUser.
 */
public class DefaultOutputUser implements OutputUser {

	
	
	/* (non-Javadoc)
	 * @see org.wsdl.tools.wsdlauditor.interfaces.OutputUser#generate(java.util.List, java.lang.String, java.net.URL, java.util.Map)
	 */
	@Override
	public void generate(List<SchemaError> errors,String outputDir,URL documentUrl,Map<String,String> params) {
		Collections.sort(errors, new SchemaErrorComparator());
    	StringBuffer html=new StringBuffer();
    	html.append("<html><head><title> Schema Errors </title>");
		
    	html.append("</head><body>");
    	html.append("<h1><center>Schema Errors</center></h1>");
    	html.append("<br/>");
    	html.append("<table width='100%' BORDER CELLSPACING=2>");
    	
    	html.append("<tr style='background-color:#CCCCFF'><th>Entity</th><th>Severity</th><th>Error Code</th><th>Error Description</th><th>Suggested Fix</th></tr>");
    	for(SchemaError error:errors){
    		org.wsdl.tools.wsdlauditor.ruledefn.data.Error coreError=error.getError();
    		String entityName=error.getEntityName();
    		if(entityName==null || entityName.isEmpty()){
    			entityName="NA";
    		}
    		String errorType="NA";
    		if(coreError.getErrorType()!=null){
    			errorType=coreError.getErrorType().getValue();
    		}
    		String errorCode=coreError.getErrorCode();
    		if(errorCode==null || errorCode.isEmpty()){
    			errorCode="NA";
    		}
    		String message=coreError.getMessage();
    		if(message==null || message.isEmpty()){
    			message="NA";
    		}
    		String suggestion=coreError.getSuggestion();
    		if(suggestion==null || suggestion.isEmpty()){
    			suggestion="NA";
    		}
    		html.append("<tr><td>"+entityName+"</td><td>"+errorType+"</td><td>"+errorCode+"</td><td>"+message+"</td><td>"+suggestion+"</td></tr>");
    	}
    	
    	html.append("</table>");
    	html.append("</body></html>");
    	
    	String[] paths=documentUrl.getPath().split("/");
    	String path=paths[paths.length-1];
    	paths=path.split("\\\\");
    	path=paths[paths.length-1];
    	int index=path.lastIndexOf(".");
    	if(index!=-1){
    		path=path.substring(0, index);
    	}else{
    		path="Audit Report";
    	}
    	writeFile(html,outputDir,path+".html");
    }
    
    /**
	 * Write file.
	 * 
	 * @param html
	 *            the html
	 * @param dir
	 *            the dir
	 * @param fileName
	 *            the file name
	 */
    private void writeFile(StringBuffer html, String dir, String fileName) {
		try {
			File file = new File(dir);
			file.mkdirs();
			FileWriter fw = new FileWriter(dir + File.separator + fileName);
			fw.write(html.toString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			throw new OutputUserException("Error writing report ", e);
		}
	}

}
