package edu.rutgers.vietnguyen;

import java.io.*;
import java.util.*;

public class TestMD5Utils {
	
	public static void testGetMD5()
	{
		Scanner in = new Scanner(System.in);
		System.out.print("Enter the folder: ");
		String folderPath = in.nextLine();
		File folder = new File(folderPath);
		File[] files = folder.listFiles();
		for(File file: files )
		{
			if(file.isFile())
			{
				System.out.println("File: " + file.getName() + ", MD5: " + MD5Utils.getMD5(file));
			}
		}
	}
}
