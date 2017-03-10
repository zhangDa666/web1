package com.zd.test.servlet;

import javax.servlet.http.HttpServlet;

public class ServletTest extends HttpServlet{
	
	private static final long serialVersionUID = 3576001380796035146L;
	
	void tt(){
		String initParameter = this.getServletConfig().getInitParameter("");
		
	}
}
