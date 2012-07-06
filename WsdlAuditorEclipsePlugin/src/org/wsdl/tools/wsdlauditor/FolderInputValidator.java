package org.wsdl.tools.wsdlauditor;

import java.io.File;

import org.eclipse.jface.dialogs.IInputValidator;

public class FolderInputValidator implements IInputValidator {

	public String isValid(String newText) {
		try {
			new File(newText);
		} catch (Exception e) {
			return "Please select an Valid Directory";
		}		
		return null;
	}

}
