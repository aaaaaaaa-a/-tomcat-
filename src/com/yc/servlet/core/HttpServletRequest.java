package com.yc.servlet.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yc.tomcat.core.TomcatConstants;

public class HttpServletRequest implements ServletRequest{

	private String method;
	private Map<String,String> parameter = new HashMap<String, String>();
	private String url;
	private InputStream is;//请求流
	private String version;
	private volatile HttpSession session = new HttpSession();
	
	private boolean checkJSessionId = false;
	private String Jsessionid;
	private Cookie[] cookies;
	
	private BufferedReader read;
	
	public HttpServletRequest(InputStream is) {
		this.is = is;
	}

	@Override
	public void parse() {
		try {
			read = new BufferedReader(new InputStreamReader(is));
			String line = null;
			List<String> headStr = new ArrayList<String>();
			while( (line = read.readLine() ) != null && !"".equals(line)) {
				headStr.add(line);
			}
			parseFirstLine(headStr.get(0));
			parseParameter(headStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * 解析起始行
	 * @param string 起始行
	 */
	private void parseFirstLine(String headLine) {
		String[] str = headLine.split(" ");
		this.method = str[0];
		this.version = str[2];
		if(str[1].contains("?")) {//说明有参数
			this.url = str[1].substring(0,str[1].indexOf("?"));
		}else {
			this.url = str[1];
		}
	}

	/**
	 * 解析参数
	 * @param headStr
	 */
	private void parseParameter(List<String> headStr) {
		String str = headStr.get(0).split(" ")[1];//获取url
		if(str.contains("?")) {
			String paramStr = str.substring(str.indexOf("?")+1);
			String[] params = paramStr.split("&");
			String[] param = null;
			for(String s : params) {
				param = s.split("=");
				parameter.put(param[0], param[1]);
				
			}
		}
		
		
		if(method.equals(TomcatConstants.REQUEST_METHOD_POST)) {//如果是post方法
			//获取请求内容长度
			int len = 0;
			for(String s : headStr) {
				if(s.contains("Content-Length:")) {
					len = Integer.parseInt(s.substring(s.indexOf(":") + 1).trim());
					break;
				}
			}
			
			if(len <= 0 ) {//说明后面没有内容
				return;
			}
			
			
			//没写完
			try {
				char[] ch = new char[1024 * 10];
				StringBuffer sbf = new StringBuffer();
				int count = 0, total = 0;
				while( (count = read.read(ch)) > 0 ) {
					sbf.append(ch,0,count);
					total += count;
					if(total >= len ) {
						break;
					}
				}
				str = URLDecoder.decode(sbf.toString(), "utf-8");
				str = str.substring(str.indexOf("?") +1 );
				System.out.println("str:"+str);
				String[] params = str.split("&");
				String[] param = null;
				for(String s : params) {
					param = s.split("=");
					parameter.put(param[0], param[1]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				
			}
		}
		
	}
	@Override
	public String getParameter(String key) {
		return parameter.get(key);
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public HttpSession getSession() {
		return null;
	}

	@Override
	public Cookie[] getCookies() {
		return null;
	}

	@Override
	public boolean checkJSessionId() {
		
		return false;
	}

	@Override
	public String getJSessionId() {
		
		return null;
	}

	public String getVersion() {
		return version;
	}
	
	

}
