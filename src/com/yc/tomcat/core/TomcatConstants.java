package com.yc.tomcat.core;

public class TomcatConstants {
	public static final String REQUEST_METHOD_GET ="GET";
	
	public static final String REQUEST_METHOD_POST ="POST";
	
	public static final long SESSION_TIMEOUT = 60 * 1000;
	
	public static final int CLEAR_SESSION_CYCLE = 120 * 1000;
	
	public static final String REQUEST_JSESSION = "JSESSIONID"
			;
	public static final String BASE_PATH = ReadConfig.getInstance().getProperty("path");
	
	public static final String DEFAULT_RESOURCE = ReadConfig.getInstance().getProperty("default");
}
