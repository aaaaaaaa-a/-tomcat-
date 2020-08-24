package com.yc.servlet.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.yc.tomcat.core.ParseXML;
import com.yc.tomcat.core.ReadConfig;
import com.yc.tomcat.core.TomcatConstants;



public class HttpServletResponse implements ServletResponse{
	private OutputStream os = null;
	private String basePath = TomcatConstants.BASE_PATH;
	private String projectName;
	public HttpServletResponse(String projectName , OutputStream os) {
		this.projectName = projectName;
		this.os = os;
	}

	@Override
	public PrintWriter getWrite() throws IOException {
		String msg = "HTTP/1.1 200 OK\r\nContent-Type:text/html;charset=utf-8\r\n\r\n";
		os.write(msg.getBytes());
		os.flush();
		return new PrintWriter(os);
	}

	@Override
	public void sendredirect(String url) {
		if(url == null || "".equals(url)) {
			error404(url);
			return;
		}
		
		if(!url.startsWith(projectName)) {//没有加项目
			url = projectName + "/" + url;	
			System.out.println(url);
		}
		
		
		if( url.indexOf("/") ==  url.lastIndexOf("/") && url.indexOf("/") + 1 < url.length()) { // /day
			send302(url);
		}else {
			if(url.endsWith("/")) {// /day/    没有考虑/day/goods
				String default1 = ReadConfig.getInstance().getProperty("default");
				File fl = new File(basePath,url.substring(1).replace("/", "\\") + default1);
				if(!fl.exists()) {
					error404(url);
					return;
				}
				send200(readFile(fl), default1.substring(default1.lastIndexOf(".") + 1).toLowerCase());
			}else {// 完整的项目路径
				File fl = new File(basePath,url.substring(1).replace("/", "\\"));
				if(!fl.exists()) {
					error404(url);
					return;
				}
			
				//					资源的扩展名
				send200(readFile(fl), url.substring(url.lastIndexOf(".") + 1).toLowerCase());
			}
		}
	}
	
	private void send302(String url) {
		try {
			String msg = "HTTP/1.1 302 Moved Temporarily\r\nContent-Type:text/html;charset=utf-8\r\nLocation:"+url+"/\r\n\r\n";
			os.write(msg.getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public byte[] readFile(File fl) {
		try(FileInputStream fis = new FileInputStream(fl)){
			byte[] bt = new byte[fis.available()];
			fis.read(bt);
			return bt;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private void send200(byte[] bt, String type) {
		try {
			//通过xml解析由文件扩展名得到Content-Type
			//System.out.println("2222222");
			String contentType = ParseXML.getCotentType(type);
			//System.out.println("contentType:"+contentType);
			//System.out.println("content: " + contentType);
			String msg = "HTTP/1.1 200 OK\r\nContent-Type:" + contentType +"\r\nContent-Length:"+bt.length+"\r\n\r\n";
			os.write(msg.getBytes());
			os.write(bt);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void error404(String url) {
		try {
			String errorInfo = "<h1>HTTP Status 404 -"+ url +"<h1/>";
			String msg = "HTTP/1.1 404 File Not Found\r\nContent-Type:text/html;charset=utf-8\r\nContent-Length:"+errorInfo.length()+"\r\n\r\n" + errorInfo;
			os.write(msg.getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
