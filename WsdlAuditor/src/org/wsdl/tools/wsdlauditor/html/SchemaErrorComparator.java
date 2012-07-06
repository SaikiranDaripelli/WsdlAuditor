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

import java.util.Comparator;

import org.wsdl.tools.wsdlauditor.ruledefn.data.SchemaError;
import org.wsdl.tools.wsdlauditor.ruledefn.data.enums.ErrorType;

/**
 * The Class SchemaErrorComparator.
 */
public class SchemaErrorComparator implements Comparator<SchemaError> {
	
	/**
	 * Gets the rank.
	 * 
	 * @param errorType
	 *            the error type
	 * @return the rank
	 */
	private int getRank(ErrorType errorType){
		if(errorType==null){
			return 0;
		}
		if(errorType==ErrorType.ERROR){
			return 3;
		}
		if(errorType==ErrorType.WARNING){
			return 2;
		}
		if(errorType==ErrorType.INFO){
			return 1;
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(SchemaError arg0, SchemaError arg1) {
		if(arg0==null && arg1==null){
			return 0;
		}
		if(arg0==null){
			return 1;
		}
		if(arg1==null){
			return -1;
		}
		if(arg0.getError().getErrorType()==arg1.getError().getErrorType()){
			return 0;
		}
		return getRank(arg1.getError().getErrorType())-getRank(arg0.getError().getErrorType());
	}

}
