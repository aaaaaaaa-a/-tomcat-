package com.yc.servlet.core;

public interface Servlet {

	public void inti();
	public void service(ServletRequest request, ServletResponse response);
	
	public void doGet(ServletRequest request, ServletResponse response);

	public void doPost(ServletRequest request, ServletResponse response);

}
