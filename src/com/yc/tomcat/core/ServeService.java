package com.yc.tomcat.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

import com.yc.servlet.core.HttpServletRequest;
import com.yc.servlet.core.HttpServletResponse;
import com.yc.servlet.core.Servlet;
import com.yc.servlet.core.ServletRequest;
import com.yc.servlet.core.ServletResponse;

public class ServeService implements Runnable{
	private Socket sk = null;
	private InputStream is = null;
	private OutputStream os = null;
	
	public ServeService(Socket sk) {
		this.sk = sk;
		
	}

	@Override
	public void run() {
		
		try {
			this.os = sk.getOutputStream();
			this.is = sk.getInputStream();
			ServletRequest request = new HttpServletRequest(is);
			
			request.parse();
			
			
			
			String url = request.getUrl();
			
			
			
			String urlStr = url.substring(1);//  day/index.html
			String projectName = urlStr.substring(0, urlStr.indexOf("/")); // day
			System.out.println("projectName" + projectName);
			ServletResponse response = new HttpServletResponse("/"+projectName,os);
			
			String clazz = ParseUrlPattern.getClass(url);
			//
			
			if(null == clazz || "".equals(clazz)) {
				response.sendredirect(url);
				return;
			}
			System.out.println("clazz:" + clazz);
			
			URLClassLoader loader = null;
			URL classPath = null;
			try {
				classPath = new URL("file",null,TomcatConstants.BASE_PATH + "\\" + projectName + "\\bin");
				
				loader = new URLClassLoader(new URL[] {classPath});
				
				Class<?> cla = loader.loadClass(clazz);
				
				Servlet servlet = (Servlet) cla.newInstance();
				
				servlet.service(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	
	
}
