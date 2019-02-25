package com.lovecat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet{

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		
		System.out.println("Hallo!" + username + "password: " + password);
		
		
		if (registerCredentials(username, password, email)) {
			resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
	}

	private boolean registerCredentials(String username, String password, String email) {
		Connection con; 
		Statement statement;
		
		try {
			// Step 1: create a connection:
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lovecatdb?useSSL=false", "root", "start123");
			
			// Step 2: create a Statement:
			statement = con.createStatement();
			
			//Step 3: execute a SQL Statement:
			String sql = "insert into users values ('" + username + "', '" + password + "', '" + email+ "');";
			int result =  statement.executeUpdate(sql);
			
			// Step 4: process the result:
			if(result > 0) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
		
	}
}
