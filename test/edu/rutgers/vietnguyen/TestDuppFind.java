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
//		DuppFind df = new DuppFind(dirName);
//		df.run();
	}
}
