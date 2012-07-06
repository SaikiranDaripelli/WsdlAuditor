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
package org.wsdl.tools.wsdlauditor.ruledefn.data.enums;

/**
 * The Enum ErrorType.
 */
public enum ErrorType {
	
	/** The ERROR. */
	ERROR("error"),/** The WARNING. */
WARNING("warning"),/** The INFO. */
INFO("info");
    
    /** The value. */
    private String value;
    
    /**
	 * Instantiates a new error type.
	 * 
	 * @param value
	 *            the value
	 */
    ErrorType(String value){
    	this.value=value;
    }
    
    /**
	 * Gets the value.
	 * 
	 * @return the value
	 */
    public String getValue(){
    	return value;
    }
    
    /**
	 * Gets the single instance of ErrorType.
	 * 
	 * @param value
	 *            the value
	 * @return single instance of ErrorType
	 */
    public static ErrorType getInstance(String value){
    	for(ErrorType erTyp:ErrorType.values()){
    		if(erTyp.getValue().equals(value)){
    			return erTyp;
    		}
    	}
    	return null;
    }
}
