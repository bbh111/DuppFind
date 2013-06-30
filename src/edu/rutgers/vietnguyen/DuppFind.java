package edu.rutgers.vietnguyen;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

public class DuppFind implements Runnable{
	private File directory;
	private Map<String, List<String>> md5FileMap;
	private ExecutorService pool;
	
	public DuppFind(File directory, Map<String, List<String>> md5FileMap, ExecutorService pool)
	{
		this.directory = directory;
		this.md5FileMap = md5FileMap;
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
					//System.out.println("Dir: " + file.getAbsolutePath());
					DuppFind df = new DuppFind(file, md5FileMap, pool);
					Future<?> result = pool.submit(df);
					results.add(result);
				}
				else if(file.isFile())
				{
					//calculate md5
					String md5 = MD5Utils.getMD5(file);
					List<String> listFile = new CopyOnWriteArrayList<String>();
					if(md5FileMap.get(md5) != null)
					{
						System.out.println("Duplicate md5: " + md5 + ", size now: " + md5FileMap.get(md5).size());
						listFile.addAll(md5FileMap.get(md5));
					}
					listFile.add(file.getAbsolutePath());
					md5FileMap.remove(md5);
					md5FileMap.put(md5, listFile);
					System.out.println("Add: File" + file.getAbsolutePath() + ", MD5: " + md5);
				}
			}
			
			//wait until all sub-tasks complete
			for(Future<?> result: results)
			{
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
			}
			
		}
		
		catch(Exception e)
		{
			
		}
	}
}
