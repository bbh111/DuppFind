package edu.rutgers.vietnguyen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DuppFind implements Runnable {
	private String dirName;
	private FileTableModel fileTableModel; 
	
	List<List<String>> fileGroups;
	
	public DuppFind(String dirName, FileTableModel fileTableModel)
	{
		this.dirName = dirName;
		this.fileGroups = new ArrayList<List<String>>();
		this.fileTableModel = fileTableModel;
	}
	
	@Override
	public void run()
	{
		//1. File size comparation
		ExecutorService pool = Executors.newCachedThreadPool(); 
		
		Map<Long, List<String>> fileMap = new ConcurrentHashMap<Long, List<String>>();
		
		FileSizeDuppFind df1 = new FileSizeDuppFind(new File(dirName), fileMap, pool);
		
		Future<?> result = pool.submit(df1);
		
		try
		{
			result.get();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		catch(ExecutionException e)
		{
			e.printStackTrace();
		}
		pool.shutdown();

		
		//2. MD5 comparation
		ArrayList<Future<List<List<String>>>> fs = new ArrayList<Future<List<List<String>>>>();
		pool = Executors.newFixedThreadPool(5); 
		for(Map.Entry<Long, List<String>> entry : fileMap.entrySet())
		{
			List<String> filesToCheck= entry.getValue();
			if(filesToCheck.size() > 1)
			{
				//do work
				MD5DuppFind md5df = new MD5DuppFind(filesToCheck);
				Future<List<List<String>>> f = pool.submit(md5df);
				fs.add(f);
			}
		}
		for(Future<List<List<String>>> f : fs)
		{
			try
			{
				List<List<String>> lstDuppFilesGrp = f.get();
				fileGroups.addAll(lstDuppFilesGrp);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			catch(ExecutionException e)
			{
				e.printStackTrace();
			}

		}
		pool.shutdown();
		
		//3. Output result
		output();
		
	}
	
	private void output()
	{
		//Delete old data
		fileTableModel.deleteAllRows();
		
		//new data
		for(List<String> lstDuppFiles : fileGroups)
		{	
			System.out.println("---");
			for(String s: lstDuppFiles)
			{
				RowData row = new RowData(false, s, FileUtils.getExtension(s));
				fileTableModel.insertRow(row);
				System.out.println("File: " + s);
			}
			fileTableModel.insertEmptyRow();
		}
	}
}
