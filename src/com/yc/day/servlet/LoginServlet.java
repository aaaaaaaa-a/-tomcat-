package com.yc.day.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.yc.servlet.core.HttpServlet;
import com.yc.servlet.core.ServletRequest;
import com.yc.servlet.core.ServletResponse;

public class LoginServlet extends HttpServlet{

	@Override
	public void doGet(ServletRequest request, ServletResponse response) {
		doPost(request, response);
	}

	@Override
	public void doPost(ServletRequest request, ServletResponse response) {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		PrintWriter out = null;
		try {
			 out = response.getWrite();
			 out.println("id"+id+"name"+name);
			 response.sendredirect("index.html");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(out != null) {
				out.close();
			}
		}
	}

}
