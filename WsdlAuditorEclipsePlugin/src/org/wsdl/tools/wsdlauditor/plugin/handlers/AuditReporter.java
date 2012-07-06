package org.wsdl.tools.wsdlauditor.plugin.handlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.wsdl.tools.wsdlauditor.DriverHandler;
import org.wsdl.tools.wsdlauditor.FolderInputValidator;
import org.wsdl.tools.wsdlauditor.IResourceFileUtil;
import org.wsdl.tools.wsdlauditor.plugin.preferences.PreferenceConstants;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AuditReporter extends AbstractHandler {

	/**
	 * The constructor.
	 */
	public AuditReporter() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<String> files=new ArrayList<String>();
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		Shell shell=window.getShell();
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			for (Iterator<Object> iterator = strucSelection.iterator(); iterator
					.hasNext();) {
				IResource resource=null;
				Object element = iterator.next();
				if(element instanceof IProject){
					resource=(IProject)element;
					
				}
				Class clazz=element.getClass();
				Method m;
				try {
					m = clazz.getMethod("getResource", null);
					 resource=(IResource)m.invoke(element, null);
					
				} catch (SecurityException e) {
					//Ignored Intentionally
				} catch (NoSuchMethodException e) {
					//Ignored Intentionally
				} catch (IllegalArgumentException e) {
					//Ignored Intentionally
				} catch (IllegalAccessException e) {
					//Ignored Intentionally
				} catch (InvocationTargetException e) {
					//Ignored Intentionally
				}
				if(resource!=null){
					files.addAll(IResourceFileUtil.getFiles(resource));
				}
				
			}
		}
		
		if(!files.isEmpty()){
			String opDir=PlatformUI.getPreferenceStore().getString(PreferenceConstants.P_OUTPUT_DIRECTORY);
			if(opDir==null || "".equals(opDir.trim())){
				InputDialog inputDialog=new InputDialog(shell, "Documents Directory", "Output directory is not set in preferences, please select. ", opDir, new FolderInputValidator());
				inputDialog.setBlockOnOpen(true);
				inputDialog.open();
				int returnCode=inputDialog.getReturnCode();
				if(returnCode==InputDialog.OK){
					opDir=inputDialog.getValue();
				}else{
					return null;
					
				}
			}
			DriverHandler.callDriver(opDir,shell,files);
		}else{
			MessageDialog.openError(shell, "No Files", "No WSDL Files Exist in the selection.");
		}
		return null;
	}
	
	

}
