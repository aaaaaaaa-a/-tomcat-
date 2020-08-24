package com.yc.servlet.core;

import com.yc.tomcat.core.TomcatConstants;

public class HttpServlet implements Servlet{

	@Override
	public void inti() {
		
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) {
		switch(request.getMethod()) {
		case TomcatConstants.REQUEST_METHOD_GET : doGet(request, response);
		case TomcatConstants.REQUEST_METHOD_POST : doPost(request, response);
		}
	}

	@Override
	public void doGet(ServletRequest request, ServletResponse response) {
		
	}

	@Override
	public void doPost(ServletRequest request, ServletResponse response) {
		
	}

}
