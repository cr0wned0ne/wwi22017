package com.lovecat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet{
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		
		Map<String, String> users = new HashMap<String, String>();
		users.put("admin", "admin");
		users.put("test", "test");
		users.put("start", "start");
		
		writer.append("<table>");
		for (Entry<String, String> user : users.entrySet())  {
			writer.append("<tr>");
			writer.append("<td>");
			writer.append(user.getKey());
			writer.append("</td>");
			writer.append("<td>");
			writer.append(user.getValue());
			writer.append("</td>");
			writer.append("</tr>");
		}
		
		writer.append("</table>");
		writer.close();
	}

}
