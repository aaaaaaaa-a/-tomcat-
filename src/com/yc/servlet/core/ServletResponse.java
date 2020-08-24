package com.yc.servlet.core;

import java.io.IOException;
import java.io.PrintWriter;

public interface ServletResponse {

	public PrintWriter getWrite() throws IOException;
	
	public void sendredirect(String url);
}
