package edu.rutgers.vietnguyen;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestDuppFind {
	private static final String dir0 = "/Users/vietnguyen/testDuppFind";
	private static final String dir1 = "/Users/vietnguyen/Ebook/EE & CS books";
	private static final String dir2 = "/Users/vietnguyen/Pictures";
	private static final String dir3 = "/Users/vietnguyen/Music";
	private static final String dir4 = "/Users/vietnguyen";
	
	public static void test0()
	{
		test(dir0);
	}
	
	public static void test1()
	{
		test(dir1);
	}
	
	public static void test2()
	{
		test(dir2);
	}
	
	public static void test3()
	{
		test(dir3);
	}
	
	public static void test4()
	{
		test(dir4);
	}
	
	public static void test(String dirName)
	{
		ExecutorService pool = Executors.newFixedThreadPool(10);
		
		Map<String, List<String>> md5FileMap = new ConcurrentHashMap<String, List<String>>();
		
		DuppFind df1 = new DuppFind(new File(dirName), md5FileMap, pool);
		
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
		//while(!result.isDone());	//wait
		
		System.out.println("------Result ------");
		for(Map.Entry<String, List<String>> entry : md5FileMap.entrySet())
		{
			if(entry.getValue().size() != 1)
			{
				System.out.println("MD5: " + entry.getKey());
				for(String s : entry.getValue())
					System.out.println("		File: " + s);
			}
		}
		System.out.println("Done");
		pool.shutdown();
	}
}
