package org.wsdl.tools.wsdlauditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.wsdl.tools.wsdlauditor.plugin.preferences.PreferenceConstants;
import org.wsdl.tools.wsdlauditor.ruledefn.utils.Util;

public class IResourceFileUtil {
	
	private static boolean isValidExtension(File file){
		boolean returnVal=false;
		String filenameExtension=file.getName();
	    int dotPos = filenameExtension.lastIndexOf(".");
	    String extension = filenameExtension.substring(dotPos+1);
	   if(extension==null || (extension==null || "".equals(extension.trim())) || !extension.equalsIgnoreCase("wsdl")){
		   returnVal=false;
	   }else{
		   
		   returnVal=true;
	   }
	   return returnVal;
	}
	public static List<String> getFiles(IResource resource){
		String folder=resource.getLocation().toPortableString();
		File file=new File(folder);
		return getAllFiles(file,true);
	}
	private static List<String> getAllFiles(File folder, boolean validate){
		List<String> files=new ArrayList<String>();
		if(folder.isDirectory()){
		File[] children=folder.listFiles();
		if(children!=null){
			for(File child:children){
				if(child.isDirectory()){
					files.addAll(getAllFiles(child,validate));
				}else if(child.isFile()){
					boolean valid=true;
					if(validate){
						valid=isValidExtension(child);
					}
					if(valid){
						files.add(child.getAbsolutePath());
					}
				}
			}
		}
		}else{
			boolean valid=true;
			if(validate){
				valid=isValidExtension(folder);
			}
			if(valid){
				files.add(folder.getAbsolutePath());
			}
		}
		
		return files;
	}
	
	
	public static void completeProcess(String outputDir,Shell shell){
		Boolean isZip=PlatformUI.getPreferenceStore().getBoolean(PreferenceConstants.P_ZIP);
		String actualOpDir=outputDir;
		if(isZip){
			String zipFile=outputDir+File.separator+PreferenceConstants.ZIP_NAME;
			
			boolean success=writeZip(actualOpDir,zipFile);
			if(!success){
				MessageDialog.openError(shell, "Zip Error", "Unable to Zip the files, the Audit Reports exists at : " + actualOpDir);
			}else{
				MessageDialog.openInformation(shell, "Success", "Audit Reports Zip available at :" + zipFile);
				
			}
		}else{
			MessageDialog.openInformation(shell, "Success", "Audit Reports generated at :" + actualOpDir);
		}
		
	}
	public static boolean writeZip(String outputDir,String zipFileName){
		File folder=new File(outputDir);
		
		List<String> sources=getAllFiles(folder,false);
		// Create a buffer for reading the files
	    byte[] buf = new byte[1024];
	    
	    try {
	        // Create the ZIP file
	        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
	    
	        // Compress the files
	        for (String source:sources) {
	            FileInputStream in = new FileInputStream(source);
	            
	            int index=outputDir.indexOf(folder.getName());
	            String zipSource=source.substring(index+folder.getName().length()+1);
	            // Add ZIP entry to output stream.
	            out.putNextEntry(new ZipEntry(zipSource));
	    
	            // Transfer bytes from the file to the ZIP file
	            int len;
	            while ((len = in.read(buf)) > 0) {
	                out.write(buf, 0, len);
	            }
	    
	            // Complete the entry
	            out.closeEntry();
	            in.close();
	        }
	        out.finish();
	        // Complete the ZIP file
	        out.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	return false;
	    }
	    return true;
	}
	
	
	public static URL convertToURL(String path) {
		if(path==null || path.isEmpty()){
			return null;
		}
		URL url = null;
		try {
			url = new URL(path);
		} catch (MalformedURLException e) {
			url = Thread.currentThread().getContextClassLoader().getResource(
					path);
			if (url == null) {
				File file = new File(path);
				if (file.exists()) {
					try {
						url = file.toURI().toURL();
					} catch (MalformedURLException e1) {
						Logger.getLogger(Util.class.getName())
								.log(Level.SEVERE, path + " is Not valid", e);
					}
				}
			}
		}
		return url;
	}

}
