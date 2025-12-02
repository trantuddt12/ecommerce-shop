package com.ttl.core.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConcurrentLoginTester {
	
	private static final int THREAD_COUNT = 2000;
	
	public static void main(String[] args) throws Exception {
		ExecutorService lvExecutor = Executors.newFixedThreadPool(200);
		CountDownLatch lvLatchCount = new CountDownLatch(THREAD_COUNT);
		long lvStartTime = System.currentTimeMillis();
		int lvTotalFail = 0;
		List<Future<Integer>> lvFuture = new ArrayList<Future<Integer>>();
		for (int i = 0; i < THREAD_COUNT; i++) {
			Callable<Integer> lvTask = new Callable<Integer>() {
				int mvCountFail = 0;
				@Override
				public Integer call() throws Exception {
					try {
						sendLoginRequest();
					}catch (Exception e) {
						System.out.println("Request fail : " + e.getMessage());
						mvCountFail ++;
					}finally {
						lvLatchCount.countDown();
					}
					return mvCountFail;
				}
			};
			lvFuture.add(lvExecutor.submit(lvTask));
		}
		
		
		for (int i = 0; i < lvFuture.size(); i++) {
			lvTotalFail += lvFuture.get(i).get();
		}
		lvLatchCount.await();
		lvExecutor.shutdown();
		long lvDuration = System.currentTimeMillis() - lvStartTime;
		System.out.println("All requests done in :" + lvDuration);
		System.out.println("Total request fail :" + lvTotalFail);
	}

	private static void sendLoginRequest() throws MalformedURLException, IOException {
		HttpURLConnection lvConnection = (HttpURLConnection) new URL("http://localhost:8080/api/auth/login").openConnection();
		lvConnection.setRequestMethod("POST");
		lvConnection.setDoOutput(true);
		lvConnection.setRequestProperty("Content-Type", "application/json");
		
		String lvBody = "{\"username\" :\"an\", \"password\" : \"123456\"}";
		OutputStream os = lvConnection.getOutputStream();
		os.write(lvBody.getBytes());
		os.flush();
		os.close();
		
		int code = lvConnection.getResponseCode();
		System.out.println("Response code : " + code);
	}

}
