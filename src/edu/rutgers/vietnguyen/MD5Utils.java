package edu.rutgers.vietnguyen;

import java.io.*;
import java.security.*;

public class MD5Utils 
{
	public static String getMD5(File file) 
	{
		if(!file.exists())
			return "";
		try
		{
			MessageDigest  md =  MessageDigest.getInstance("MD5");

			//read the file into a byte array
			byte[] input = new byte[(int) file.length()];
			InputStream in = new FileInputStream(file);
			in.read(input);
			in.close();
			
			//update the MessageDigest and process
			md.update(input);
			byte[] fileDigest = md.digest();
			
			return ByteArrayToString(fileDigest);
		}
		catch(IOException e )
		{
			e.printStackTrace();
			return "";
		}
		catch(NoSuchAlgorithmException e)
		{
			return "";
		}
	}
	
	private static String ByteArrayToString(byte[] ba)
	{
	   StringBuilder sb = new StringBuilder();
	   for(byte b: ba)
	      sb.append(String.format("%02x", b&0xff));
	   return sb.toString();
	}
}
