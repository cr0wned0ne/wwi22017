package com.lovecat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet{

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		System.out.println("Hallo!" + username + "password: " + password);
		
		
		if (validCredentials(username, password)) {
			resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
	}

	private boolean validCredentials(String username, String password) {
		Map<String, String> users = new HashMap<String, String>();
		users.put("admin", "admin");
		
		if (users.get(username) != null && users.get(username).equals(password)) {
			return true;
		} else {
			return false;
		}
		
	}
}
