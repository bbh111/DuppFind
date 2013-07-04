package edu.rutgers.vietnguyen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MD5DuppFind implements Callable<List<List<String>>> {
	private List<String> filesToCheck;
	private List<List<String>> result; 	//collection of duplicate file groups
	
	public MD5DuppFind(List<String> filesToCheck)
	{
		this.filesToCheck = filesToCheck;
		this.result = new ArrayList<List<String>>();
	}
	
	public List<List<String>> call()
	{
		outputDuplicateFile(filesToCheck);
		return result;
	}
	
	/**
	 * From a list of files, calculate md5 and then store files with the same md5 as a group to global "result"
	 * @param in list of files
	 */
	private void outputDuplicateFile(List<String> in)
	{
		System.out.println("Process list... Number of files: " + in.size());
		while(in.size()!=0)
		{
			String fileName = in.get(0);
			File file = new File(fileName);
			String ext = FileUtils.getExtension(fileName).toLowerCase();
			String md5 = MD5Utils.getMD5(file);
			System.out.println("key md5: " + md5);
			List<String> lstDuppFiles = new ArrayList<String>();
			lstDuppFiles.add(in.get(0));
			in.remove(0);
			
			for(String f: in)
			{
				String _ext = FileUtils.getExtension(f).toLowerCase();
				if(_ext.compareTo(ext) !=0)
					continue;
				File _file = new File(f);
				String _md5 = MD5Utils.getMD5(_file);
				System.out.println("new md5: " + _md5);
				if(_md5.compareTo(md5) == 0)
				{
					lstDuppFiles.add(f);
					in.remove(f);
				}
			}
			
			//only add the list with more than 1 elements to the result
			if(lstDuppFiles.size() > 1)
			{
				System.out.println("New duplicate group! ");
				result.add(lstDuppFiles);
			}
		}
	}
	

	
}
