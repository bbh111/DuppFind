package edu.rutgers.vietnguyen;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

public class FileSizeDuppFind implements Runnable{
	private File directory;
	private Map<Long, List<String>> sizeFileMap;
	private ExecutorService pool;
	
	public FileSizeDuppFind(File directory, Map<Long, List<String>> sizeFileMap, ExecutorService pool)
	{
		this.directory = directory;
		this.sizeFileMap = sizeFileMap;
		this.pool = pool;
	}	

	@Override
	public void run () {
		try
		{
			File[] files = directory.listFiles();
			ArrayList<Future<?>> results = new ArrayList<Future<?>>();
			for(File file : files)
			{
				if(file.isDirectory())
				{
					FileSizeDuppFind df = new FileSizeDuppFind(file, sizeFileMap, pool);
					Future<?> result = pool.submit(df);
					results.add(result);
				}
				else if(file.isFile())
				{
					//calculate file size
					long filesize = file.length();
					List<String> listFile = sizeFileMap.get(filesize);
					if(listFile != null)
					{
						listFile.add(file.getAbsolutePath());
					}
					else
					{
						listFile = new CopyOnWriteArrayList<String>();
						listFile.add(file.getAbsolutePath());
						sizeFileMap.put(filesize, listFile);
					}
					System.out.println("Add: File" + file.getAbsolutePath() + ", FileSize: " + filesize);
				}
			}
			
			//wait until all sub-tasks complete
			for(Future<?> result: results)
			{
				result.get();
			}
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
}
