package edu.rutgers.vietnguyen;

import java.io.File;

public class FileUtils {
	public 	static String getExtension(String fileName)
	{
		String extension = "";
		int i = fileName.lastIndexOf('.');
		if(i>0)
		{
			extension = fileName.substring(i+1);
		}
		return extension;
	}
	
	public static boolean deleteFile(String fullPath)
	{
		File file = new File(fullPath);
		return file.delete();
	}
}
