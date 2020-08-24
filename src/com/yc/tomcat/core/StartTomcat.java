package com.yc.tomcat.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.SocketSecurityException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartTomcat {
	public static void main(String[] args) {
		try {
			new StartTomcat().start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() throws IOException {
		int port = Integer.parseInt(ReadConfig.getInstance().getProperty("port"));
		ServerSocket ssk = new ServerSocket(port);
		System.out.println("服务器启动成功");
		
		new ParseUrlPattern();
		
		new ParseXML();//获取扩展名类型
		ExecutorService theadPool = Executors.newFixedThreadPool(10);
		
		Socket sk = null;
		
		while(true) {
			sk = ssk.accept();
			
			//交给线程池处理
			theadPool.submit(new ServeService(sk));
		}
	}
}
