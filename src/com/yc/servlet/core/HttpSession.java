package com.yc.servlet.core;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
	public Map<String, Object> session = new HashMap<String,Object>();
	private String jsessionid = null;
	
	public void setAttirbute(String key, Object value) {
		
	}
	
	public Object getAttribute(String key) {
		return session.get(key);
	}
	
	public void serHSessionId(String jsession) {
		this.jsessionid = jsession;
	}
}
