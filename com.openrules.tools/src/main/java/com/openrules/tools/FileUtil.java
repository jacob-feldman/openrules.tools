package com.openrules.tools;

/*
 * Copyright (C) 2006-2013 OpenRules, Inc. 
 */

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;

public class FileUtil {

	static public String fileLastModified(String filename) {
		File f = new File(filename);
		if (f.exists()) {
			Date d = (new Date(f.lastModified()));
			return DateFormat.getInstance().format(d);
		}
		return "";
	}
	
	static public boolean deleteFile(String filename) {
		File f = new File(filename);
		boolean deleted = f.delete();
		if (!deleted)
			System.out.println("Failed to delete file " + filename);
		return deleted;
	} 
	
	static public boolean deleteFiles(String dirName, String filePrefix) {
		File dir = new File(dirName);
		String[] children = dir.list();
		if (children != null && children.length > 0) {
	        for (int i=0; i<children.length; i++) {
	        	if (children[i].startsWith(filePrefix)) {
	        		deleteFile(dirName + children[i]);
	        	}
	        }
		}
		return true;
	}
	
	static public String[] filesInDir(String dirName, String filePrefix, String fileSuffix) {
		File dir = new File(dirName);
		String[] children = dir.list();
		if (children == null || children.length == 0 )
			return null;
		Vector<String> names = new Vector<String>();
        for (int i=0; i<children.length; i++) {
        	if (children[i].startsWith(filePrefix) && children[i].endsWith(fileSuffix)) {
            	names.add(children[i]);
        	}
        }
		String[] files = new String[names.size()];
		for (int i = 0; i < files.length; i++) {
			files[i] = names.get(i);
		}
		return files;
	}
	
	static public void makeDir(String dirPath) {
		boolean result = new File(dirPath).mkdir();
//		if (!result)
//			System.out.println("Failed to create directory " + dirPath);
	}
	



	public static void main(String[] args) {
		// Create an instance of file object.
		File file = new File("FileLastModificationDate.java");
		// Get the last modification information.
		Long lastModified = 0L;
		if (file.exists())
		  lastModified = file.lastModified();
		// Create a new date object and pass last modified information
		// to the date object.
		Date date = new Date(lastModified);
		// We know when the last time the file was modified.
		System.out.println(date);
		
		String name = "run.html";
		System.err.println(name + " last modified: "+FileUtil.fileLastModified(name));
		
		//create file object
	    File file1 = new File("ExistsDemo.txt");
	 
	    /*
	     * To determine whether specified file or directory exists, use
	     * boolean exists() method of Java File class.
	     *
	     * This method returns true if a particular file or directory exists
	     * at specified path in the file system, false otherwise.
	     */
	 
	     boolean blnExists = file1.exists();
	 
	     System.out.println("Does file " + file1.getPath() + " exist ?: " + blnExists);
	     
	     FileUtil.makeDir("c:/temp/X/");

	}

}
