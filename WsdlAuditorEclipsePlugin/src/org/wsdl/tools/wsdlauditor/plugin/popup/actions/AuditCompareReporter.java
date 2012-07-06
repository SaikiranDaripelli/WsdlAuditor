package org.wsdl.tools.wsdlauditor.plugin.popup.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.wsdl.tools.wsdlauditor.DriverHandler;
import org.wsdl.tools.wsdlauditor.FolderInputValidator;
import org.wsdl.tools.wsdlauditor.IResourceFileUtil;
import org.wsdl.tools.wsdlauditor.plugin.preferences.PreferenceConstants;

public class AuditCompareReporter implements IObjectActionDelegate {

	private Shell shell;
	
	private ISelection selection;
	
	/**
	 * Constructor for Action1.
	 */
	public AuditCompareReporter() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		
		List<String> files=new ArrayList<String>();
		if(selection instanceof IStructuredSelection){
			IStructuredSelection str=(IStructuredSelection)selection;
			Iterator iter=str.iterator();
			while(iter.hasNext()){
				Object selected=iter.next();
				if(selected instanceof IResource){
					IResource resource=(IResource)selected;
					files.addAll(IResourceFileUtil.getFiles(resource));
				}
			}
		}
		if(files!=null && !files.isEmpty()){
			if(files.size()!=1){
				MessageDialog.openError(shell, "One Files", "Select Only one file for compare.");
			}
			FileDialog fileDialog=new FileDialog(shell);
			fileDialog.setFilterExtensions(new String[]{"wsdl"});
			fileDialog.setText("Select Old File to Compare");
			fileDialog.open();
			String compareFile=fileDialog.getFileName();
			if("".equals(compareFile.trim())){
				MessageDialog.openError(shell, "Old Files", "Select Old  file for compare.");
				return;
			}
			String opDir=PlatformUI.getPreferenceStore().getString(PreferenceConstants.P_OUTPUT_DIRECTORY);
			if(opDir==null || "".equals(opDir.trim())){
				InputDialog inputDialog=new InputDialog(shell, "Reports Directory", "Output directory is not set in preferences, please select. ", opDir, new FolderInputValidator());
				inputDialog.setBlockOnOpen(true);
				inputDialog.open();
				int returnCode=inputDialog.getReturnCode();
				if(returnCode==InputDialog.OK){
					opDir=inputDialog.getValue();
				}else{
					return;
				}
			}
			DriverHandler.callDriver(opDir,shell,files);
			
		}else{
			MessageDialog.openError(shell, "No Files", "No WSDL Files Exist in the selection.");
		}
		
	}
	

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection=selection;
	}

}
