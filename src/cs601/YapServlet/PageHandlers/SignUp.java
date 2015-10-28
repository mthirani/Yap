package cs601.YapServlet.PageHandlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.Yap.QueryEngine.ExecuteSQLQueries;

/**
 * This class serves the purpose of SignUp page in Yap and accepts both GET/ POST requests.
 * @author Mayank
 *
 */
public class SignUp extends YapServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger logger=Logger.getLogger(SignUp.class.getName());
		PrintWriter out=response.getWriter();
		if(request.getParameterMap().size()!=0)
		{
			String header=getHeader("Page Not Found") + getEndHeader();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("text/html");
			out.println(header);
			String body="<html><body><div class=\"alert alert-danger container\"><strong><center>You are requesting an invalid page.</strong></center></div></body></html>";
			out.println(body);
			logger.log(Level.WARNING, "SignUp Page cannot be found due to passing of invalid parameters in URL");
			
			return;
		}
		String javaScriptFormValidation="<script>function validateForm() {var g = document.forms[\"signUp\"][\"yourname\"].value;if (g.length > 200) {alert(\"Your first and last name must be within 200 characters. Please check and try again.\");return false;}var x = document.forms[\"signUp\"][\"username\"].value;if (x.length > 200) {alert(\"Your username must be within 200 characters. Please check and try again.\");return false;} var y = document.forms[\"signUp\"][\"email\"].value;if (y.length > 200) {alert(\"Your e-mail must be within 200 characters. Please check and try again.\");return false;}var z = document.forms[\"signUp\"][\"addr\"].value;if (z.length > 500) {alert(\"Your address must be within 500 characters. Please check and try again.\");return false;}var a = document.forms[\"signUp\"][\"pwd1\"].value;if (a.length > 200 || a.length < 8) {alert(\"Your password must be within the range of 8-200 characters. Please check and try again.\");return false;}var b = document.forms[\"signUp\"][\"pwd2\"].value;if (a!=b) {alert(\"Your password does not matches. Please check and try again.\");return false;}}</script>";
		String header=getHeader("Sign Up in Yap") + javaScriptFormValidation + getEndHeader();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		String body=getBodyNavigationBar("Sign Up in Yap is a great idea!", "SignUp") + "<div class=\"alert alert-info container\"><strong><center>Enter the below required information to register in Yap. Thats it to start growing with Yap!</center></strong></div>"
			    	+ "<div class=\"container\"><div class=\"well\">"
			    		+ "<form class=\"form-horizontal\" name=\"signUp\" role=\"form\" method=\"POST\" onsubmit=\"return validateForm()\">"
				    		+ "<div class=\"form-group\">"
		    					+ "<label class=\"control-label col-sm-2\" for=\"yourname\">Your Name:</label>"
		    					+ "<div class=\"col-sm-6\">"
		    						+ "<input type=\"text\" class=\"form-control\" name=\"yourname\" placeholder=\"Enter your First name and Last name (should be within 200 characters)\" required>"
		    					+ "</div>"
		    				+ "</div>"
		    				+ "<div class=\"form-group\">"
		    					+ "<label class=\"control-label col-sm-2\" for=\"username\">User Name:</label>"
		    					+ "<div class=\"col-sm-6\">"
		    						+ "<input type=\"text\" class=\"form-control\" name=\"username\" placeholder=\"Enter your desired choice for UserName (should be within 200 characters)\" required>"
		    					+ "</div>"
	    					+ "</div>"
			    			+ "<div class=\"form-group\">"
			    				+ "<label class=\"control-label col-sm-2\" for=\"email\">Email:</label>"
			    				+ "<div class=\"col-sm-6\">"
			    					+ "<input type=\"email\" class=\"form-control\" name=\"email\" placeholder=\"Enter email-Id (used as your login credentials)\" required>"
			    				+ "</div>"
			    			+ "</div>"
			    			+ "<div class=\"form-group\">"
		    					+ "<label class=\"control-label col-sm-2\" for=\"pwd\">Password:</label>"
		    					+ "<div class=\"col-sm-6\">"
		    						+ "<input type=\"password\" class=\"form-control\" name=\"pwd1\" placeholder=\"Enter Password (should be within 8-200 characters)\" required>"
		    					+ "</div>"
		    				+ "</div>"
		    				+ "<div class=\"form-group\">"
		    					+ "<label class=\"control-label col-sm-2\" for=\"pwd\">Confirm Password:</label>"
		    					+ "<div class=\"col-sm-6\">"
		    						+ "<input type=\"password\" class=\"form-control\" name=\"pwd2\" placeholder=\"Re-enter Password\" required>"
		    					+ "</div>"
		    				+ "</div>"
		    				+ "<div class=\"form-group\">"
		    					+ "<label class=\"control-label col-sm-2\" for=\"pwd\">Address:</label>"
		    					+ "<div class=\"col-sm-6\">"
		    						+ "<input type=\"text\" class=\"form-control\" name=\"addr\" placeholder=\"Enter your Address (should be within 500 characters)\" required>"
		    					+ "</div>"
	    					+ "</div>"
		    				+ "<div class=\"form-group\">"
		    					+ "<div class=\"col-sm-offset-2 col-sm-10\">"
		    						+ "<button type=\"submit\" class=\"btn btn-primary btn-md\">Register</button>"
		    					+ "</div>"
		    				+ "</div>"
		    			+ "</form>"
		    		+ "</div></div>";
		
		out.println(footer(body));			    			
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Logger logger=Logger.getLogger(SignUp.class.getName());
		PrintWriter out=response.getWriter();
		String header=getHeader("Sign Up in Yap") + getEndHeader();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		String body="";
		String sqlQuery="SELECT userId FROM userdataset WHERE userId=\"" + request.getParameter("email") + "\";";
		String sqlQueryUser="SELECT userName FROM userdataset WHERE userName=\"" + request.getParameter("username") + "\";";
		try 
		{
			if(new ExecuteSQLQueries().getQuery(sqlQuery) && new ExecuteSQLQueries().getQuery(sqlQueryUser))
			{
				sqlQuery="INSERT INTO userdataset (funnyVotes,usefulVotes,coolVotes,userId,userName,password,address,name) VALUES(0,0,0,\"" + request.getParameter("email") + "\",\"" + request.getParameter("username") + "\",\"" + request.getParameter("pwd1") + "\",\"" + request.getParameter("addr") + "\",\"" + request.getParameter("yourname") + "\");";
				new ExecuteSQLQueries().updateQuery(sqlQuery);
				body=getBodyNavigationBar("Yap!", "Successfull Signed Up") + "<div class=\"alert alert-success container\"><strong><center>Successfully Signed Up! We have stored your information successfully. You can log in to Yap now.</strong></center></div>";
				out.println(footer(body));
			}
			else
			{
				if(new ExecuteSQLQueries().getQuery(sqlQueryUser))
				{
					body=getBodyNavigationBar("Sign Up in Yap is a great idea!", "SignUp") + "<div class=\"alert alert-danger container\"><strong><center>Sorry, Email-Id already exists. Please choose a different valid email id and try again.</strong></center></div>";
					out.println(footer(body));
				}
				else
				{
					body=getBodyNavigationBar("Sign Up in Yap is a great idea!", "SignUp") + "<div class=\"alert alert-danger container\"><strong><center>Sorry, your choice of Username already exists. Please choose a different username and try again.</strong></center></div>";
					out.println(footer(body));
				}
			}
		}catch (SQLException e) 
		{
			StringWriter sw=new StringWriter();
			new Throwable().printStackTrace(new PrintWriter(sw));
			logger.log(Level.WARNING, "SignUp Page generated SQL Exception Error: " + sw.toString());
			String header1=getHeader("Internal Server Error") + getEndHeader();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/html");
			out.println(header1);
			String body1=getBodyNavigationBar("Yap!", "Home") + "<div class=\"alert alert-danger container\"><strong><center>Exception generated in the server. Sorry for the inconvenience. Please try after some time.</strong></center></div>";
			out.println(footer(body1));
			
			return;
		}
	}
}
