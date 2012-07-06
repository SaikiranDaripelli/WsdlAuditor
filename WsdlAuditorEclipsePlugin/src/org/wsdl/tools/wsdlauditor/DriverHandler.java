package org.wsdl.tools.wsdlauditor;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.wsdl.tools.wsdlauditor.plugin.preferences.PreferenceConstants;


public class DriverHandler {

	public static void callDriver(String outputDir, Shell shell,
			List<String> actFiles) {
		Boolean isZip = PlatformUI.getPreferenceStore().getBoolean(
				PreferenceConstants.P_ZIP);
		boolean returned = true;
		if (isZip) {
			File file = new File(outputDir + File.separator
					+ PreferenceConstants.ZIP_NAME);
			if (file.exists()) {
				returned = MessageDialog.openConfirm(shell, "File Exists",
						"Do you want to override the existing file?");
				if(returned){
					file.delete();
				}
			}
		}
		File file = new File(outputDir);
		if (file.exists()) {
			if (file.isDirectory()) {
				String[] files = file.list();					
				if (files != null && files.length > 0) {
					returned = MessageDialog.openConfirm(shell,
							"Folder Exists",
							"Output folder not empty\nDo you want to continue?");
				}
			}
		}
		if (returned) {
			String config = PlatformUI.getPreferenceStore().getString(
					PreferenceConstants.P_CONFIG_FILE_PATH);
			String ruleDef = PlatformUI.getPreferenceStore().getString(
					PreferenceConstants.RULE_DEF_PATH);
			if("".equals(ruleDef)){
				MessageDialog.openError(shell, "Error", "Select Rule Definition file from Preferences, it is mandatory");
				return;
			}
			

			String jars = PlatformUI.getPreferenceStore().getString(
					PreferenceConstants.P_JAR_FILE_PATH);
			List<URL> urls=new ArrayList<URL>(); 
			if (jars != null && !"".equals(jars.trim())) {
				urls.addAll(getJarUrls(jars, shell));
				
			}
			
			
			if (!urls.isEmpty()) {
				PluginClassLoader cl = new PluginClassLoader(urls.toArray(new URL[]{}));
				Thread.currentThread().setContextClassLoader(cl);
			}
			Map<String,String> errorFiles=new HashMap<String, String>(); 
				for(String docFile:actFiles){
					try {
					RuleEngineDriver.checkSchema(
							IResourceFileUtil.convertToURL(docFile),
							null, 
							IResourceFileUtil.convertToURL(ruleDef),
							outputDir,
							IResourceFileUtil.convertToURL(config));
					} catch (Throwable t) {
						errorFiles.put(docFile, t.getMessage());
						
					}
				}
				IResourceFileUtil.completeProcess(outputDir, shell);
			if(!errorFiles.isEmpty()){
				StringBuffer message=new StringBuffer("Though Audit Report was Sucessfull, Followinf files Errored out\n");
				for(Map.Entry<String, String> error:errorFiles.entrySet()){
					message.append(error.getKey() +" : " +error.getValue()+"\n");
				}
				MessageDialog.openError(shell, "Error", message.toString());
			}
		}
	}
	
	
	public static void callCompare(String outputDir, Shell shell,
			String actFiles,String cmpFile) {
		Boolean isZip = PlatformUI.getPreferenceStore().getBoolean(
				PreferenceConstants.P_ZIP);
		boolean returned = true;
		if (isZip) {
			File file = new File(outputDir + File.separator
					+ PreferenceConstants.ZIP_NAME);
			if (file.exists()) {
				returned = MessageDialog.openConfirm(shell, "File Exists",
						"Do you want to override the existing file?");
				if(returned){
					file.delete();
				}
			}
		}
		File file = new File(outputDir);
		if (file.exists()) {
			if (file.isDirectory()) {
				String[] files = file.list();					
				if (files != null && files.length > 0) {
					returned = MessageDialog.openConfirm(shell,
							"Folder Exists",
							"Output folder not empty\nDo you want to continue?");
				}
			}
		}
		if (returned) {
			String config = PlatformUI.getPreferenceStore().getString(
					PreferenceConstants.P_CONFIG_FILE_PATH);
			String ruleDef = PlatformUI.getPreferenceStore().getString(
					PreferenceConstants.RULE_DEF_PATH);
			if("".equals(ruleDef)){
				MessageDialog.openError(shell, "Error", "Select Rule Definition file from Preferences, it is mandatory");
				return;
			}
			

			String jars = PlatformUI.getPreferenceStore().getString(
					PreferenceConstants.P_JAR_FILE_PATH);
			List<URL> urls=new ArrayList<URL>(); 
			if (jars != null && !"".equals(jars.trim())) {
				urls.addAll(getJarUrls(jars, shell));
				
			}
			
			
			if (!urls.isEmpty()) {
				PluginClassLoader cl = new PluginClassLoader(urls.toArray(new URL[]{}));
				Thread.currentThread().setContextClassLoader(cl);
			}
			try {
				URL cmpFileUrl=IResourceFileUtil.convertToURL(cmpFile);
				if(cmpFileUrl==null){
					MessageDialog.openError(shell, "Error", "Old WSDL file needs to be selected.");
				}
					RuleEngineDriver.checkSchema(
							IResourceFileUtil.convertToURL(actFiles),
							cmpFileUrl, 
							IResourceFileUtil.convertToURL(ruleDef),
							outputDir,
							IResourceFileUtil.convertToURL(config));
				IResourceFileUtil.completeProcess(outputDir, shell);
			} catch (Throwable t) {
				MessageDialog.openError(shell, "Error", t.getMessage());
			}
		}
	}

	private static List<URL> getJarUrls(String jarString, Shell shell) {
		String[] jars = jarString.split(";");
		List<URL> urls = new ArrayList<URL>();
		if (jars != null) {
			for (int i = 0; i < jars.length; i++) {
				String jar = jars[i];
				if (jar != null && !"".equals(jar.trim())) {
					try {
						URL url = new URL("file:" + jar);
						urls.add(url);
					} catch (MalformedURLException e) {
						MessageDialog.openError(shell, "Jar File Error",
								"Error in jar file path specified in preferences. file: "
										+ jar);
					}
				}
			}
		}
		return urls;
	}
}
