package com.yc.servlet.core;

import com.yc.tomcat.core.TomcatConstants;

public class Cookie {
	private String name;
	private String value;
	private long maxAge;
	
	public Cookie(String name,String value) {
		this.maxAge = TomcatConstants.SESSION_TIMEOUT;
		this.value = value;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(long maxAge) {
		this.maxAge = maxAge;
	}
	
	
}
