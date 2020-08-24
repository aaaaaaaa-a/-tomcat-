package com.yc.servlet.core;

public interface ServletRequest {
	/***
	 * 解析请求的方法
	 */
	public void parse();
	/***
	 * 解析请求的方法
	 */
	public String getParameter(String key);
	/***
	 * 解析请求的方法
	 */
	public String getUrl();
	/***
	 * 解析请求的方法
	 */
	public String getMethod();
	
	public HttpSession getSession();
	
	public Cookie[] getCookies();
	
	public boolean checkJSessionId();
	
	public String getJSessionId();
	
}
