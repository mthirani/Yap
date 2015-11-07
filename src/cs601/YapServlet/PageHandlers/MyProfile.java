package cs601.YapServlet.PageHandlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cs601.Yap.QueryEngine.ExecuteSQLQueries;

/**
 * This class serves the purpose of User's Profile page in Yap and accepts GET/ POST requests.
 * @author Mayank
 *
 */
public class MyProfile extends YapServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger logger=Logger.getLogger(MyProfile.class.getName());
		PrintWriter out=response.getWriter();
		if(UserInfo.getCurrentUser(request)==null)
		{
			String header=getHeader("Page Not Found") + getEndHeader();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("text/html");
			out.println(header);
			String body="<html><body><div class=\"alert alert-danger container\"><strong><center>You are requesting an invalid page.</strong></center></div></body></html>";
			out.println(body);
			logger.log(Level.WARNING, "MyProfile Page cannot be found due to passing of invalid parameters in URL");
			
			return;
		}
		String name="<h4><div class=\"row\"><div class=\"col-xs-2\"><b>Name: </b></div><div class=\"col-xs-8\">" + UserInfo.getCurrentUser(request).getName() + " <a href=\"/myProfile?name=" + UserInfo.getCurrentUser(request).getName() + "\" title=\"Change My Name\" data-toggle=\"tooltip\" data-placement=\"right\" data-trigger=\"hover\"><span class=\"glyphicon glyphicon-pencil\"></span></a></div></div></h4>";
		String user="<h4><div class=\"row\"><div class=\"col-xs-2\"><b>Username: </b></div><div class=\"col-xs-8\">" + UserInfo.getCurrentUser(request).getUser() + " <a href=\"/myProfile?username=" + UserInfo.getCurrentUser(request).getUser() + "\" title=\"Change My UserName\" data-toggle=\"tooltip\" data-placement=\"right\" data-trigger=\"hover\"><span class=\"glyphicon glyphicon-pencil\"></span></a></div></div></h4>";
		String email="<h4><div class=\"row\"><div class=\"col-xs-2\"><b>Email Id: </b></div><div class=\"col-xs-8\"><a href=\"\">" + UserInfo.getCurrentUser(request).getEmail() + " <a href=\"/myProfile?email=" + UserInfo.getCurrentUser(request).getEmail() + "\" title=\"Change My Email\" data-toggle=\"tooltip\" data-placement=\"right\" data-trigger=\"hover\"><span class=\"glyphicon glyphicon-pencil\"></span></a></div></div></h4>";
		String address="<h4><div class=\"row\"><div class=\"col-xs-2\"><b>Address: </b></div><div class=\"col-xs-8\">" + UserInfo.getCurrentUser(request).getAddress() + " <a href=\"/myProfile?address=" + UserInfo.getCurrentUser(request).getAddress() + "\" title=\"Change My Address\" data-toggle=\"tooltip\" data-placement=\"right\" data-trigger=\"hover\"><span class=\"glyphicon glyphicon-pencil\"></span></a></div></div></h4></p>";
		
		if(request.getParameterMap().size() == 1)
		{
			Enumeration en=request.getParameterNames();
			String paramName=(String) en.nextElement();
			if(paramName.equals("name"))
			{
				name="<div class=\"row\"><div class=\"col-xs-2\"><h4><b>Name: </b></h4></div>"
					 + "<form class=\"form-horizontal\" name=\"editProfile\" role=\"form\" method=\"POST\" action=\"/myProfile\">"
					 + "<div class=\"form-group\">"
					 + "<div class=\"col-xs-3\"><input type=\"text\" class=\"form-control\" name=\"yourname\" value=\"" + request.getParameter("name") + "\" maxlength=\"200\" required></div>"
					 + "<button type=\"submit\" class=\"btn btn-danger\"><span class=\"glyphicon glyphicon-floppy-save\"></span></button>"
					 + "</div>"
					 + "</form></div>";
				changeRequest(request, response, out, name, user, email, address, "Ok, you are changing your name now !", "Changing");
				
				return;
			}
			else if(paramName.equals("username"))
			{
				user="<div class=\"row\"><div class=\"col-xs-2\"><h4><b>Username: </b></h4></div>"
					 + "<form class=\"form-horizontal\" name=\"editProfile\" role=\"form\" method=\"POST\" action=\"/myProfile\">"
					 + "<div class=\"form-group\">"
					 + "<div class=\"col-xs-3\"><input type=\"text\" class=\"form-control\" name=\"username\" value=\"" + request.getParameter("username") + "\" maxlength=\"200\" required></div>"
					 + "<button type=\"submit\" class=\"btn btn-danger\"><span class=\"glyphicon glyphicon-floppy-save\"></span></button>"
					 + "</div>"
					 + "</form></div>";
				changeRequest(request, response, out, name, user, email, address, "Ok, you are changing your username now !", "Changing");
				
				return;
			}
			else if(paramName.equals("email"))
			{
				email="<div class=\"row\"><div class=\"col-xs-2\"><h4><b>Email Id: </b></h4></div>"
					 + "<form class=\"form-horizontal\" name=\"editProfile\" role=\"form\" method=\"POST\" action=\"/myProfile\">"
					 + "<div class=\"form-group\">"
					 + "<div class=\"col-xs-3\"><input type=\"email\" class=\"form-control\" name=\"email\" value=\"" + request.getParameter("email") + "\" maxlength=\"100\" required></div>"
					 + "<button type=\"submit\" class=\"btn btn-danger\"><span class=\"glyphicon glyphicon-floppy-save\"></span></button>"
					 + "</div>"
					 + "</form></div>";
				changeRequest(request, response, out, name, user, email, address, "Ok, you are changing your email now ! Remember, Your new email address will be the login credential.", "Changing");
				
				return;
			}
			else if(paramName.equals("address"))
			{
				address="<div class=\"row\"><div class=\"col-xs-2\"><h4><b>Address: </b></h4></div>"
						 + "<form class=\"form-horizontal\" name=\"editProfile\" role=\"form\" method=\"POST\" action=\"/myProfile\">"
						 + "<div class=\"form-group\">"
						 + "<div class=\"col-xs-3\"><input type=\"text\" class=\"form-control\" name=\"address\" value=\"" + request.getParameter("address") + "\" maxlength=\"500\" required></div>"
						 + "<button type=\"submit\" class=\"btn btn-danger\"><span class=\"glyphicon glyphicon-floppy-save\"></span></button>"
						 + "</div>"
						 + "</form></div>";
				changeRequest(request, response, out, name, user, email, address, "Ok, you are changing your address now !", "Changing");
				
				return;
			}
			else
			{
				errorMessage(response, out, logger);
				return;
			}
		}
		processRequest(request, response, out, name, user, email, address);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response, PrintWriter out, String name, String user, String email, String address) throws ServletException, IOException
	{
		String header=getHeader("My Profile")
				+ "<script>$(document).ready(function(){$('[data-toggle=\"tooltip\"]').tooltip();});</script>"
				+ getEndHeader();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		String defaultRes="<div class=\"alert alert-info container\">"
				+ "<h4><center><strong>Welcome to your profile status " + UserInfo.getCurrentUser(request).getUser() + " !</strong></center></h4></div>"
				+ "<div class=\"container\">"
				+ name + user + email + address
				+ "<h4><b>My Current Vote Status: </b></h4><button type=\"button\" class=\"btn btn-primary\">Funny <span class=\"badge\">" + UserInfo.getCurrentUser(request).getFunny() +"</span></button><button type=\"button\" class=\"btn btn-success\">UseFul <span class=\"badge\">" + UserInfo.getCurrentUser(request).getUseful() +"</span></button><button type=\"button\" class=\"btn btn-warning\">Cool <span class=\"badge\">" + UserInfo.getCurrentUser(request).getCool() +"</span></button>"
				+ "</div>";
		
		String body=getBodyWelcomeNavigationBar("Yap!", "MyProfile", UserInfo.getCurrentUser(request).getUser()) + defaultRes;
		out.println(footer(body));
	}
	
	protected void changeRequest(HttpServletRequest request, HttpServletResponse response, PrintWriter out, String name, String user, String email, String address, String messageField, String field) throws ServletException, IOException
	{
		String header=getHeader("My Profile")
				+ "<script>$(document).ready(function(){$('[data-toggle=\"tooltip\"]').tooltip();});</script>"
				+ getEndHeader();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		String newField;
		if(field.equals("Changing"))
		{
			newField="<div class=\"alert alert-warning container\"><h4><center><strong>" + messageField + "</strong></center></h4></div>";
		}
		else if(field.equals("Changed"))
		{
			newField="<div class=\"alert alert-success container\"><h4><center><strong>" + messageField + "</strong></center></h4></div>";
		}
		else
		{
			newField="<div class=\"alert alert-danger container\"><h4><center><strong>" + messageField + "</strong></center></h4></div>";
		}
		String defaultRes=newField
				+ "<div class=\"container\">"
				+ name + user + email + address
				+ "<h4><b>My Current Vote Status: </b></h4><button type=\"button\" class=\"btn btn-primary\">Funny <span class=\"badge\">" + UserInfo.getCurrentUser(request).getFunny() +"</span></button><button type=\"button\" class=\"btn btn-success\">UseFul <span class=\"badge\">" + UserInfo.getCurrentUser(request).getUseful() +"</span></button><button type=\"button\" class=\"btn btn-warning\">Cool <span class=\"badge\">" + UserInfo.getCurrentUser(request).getCool() +"</span></button>"
				+ "</div>";
		
		String body=getBodyWelcomeNavigationBar("Yap!", "MyProfile", UserInfo.getCurrentUser(request).getUser()) + defaultRes;
		out.println(footer(body));
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger logger=Logger.getLogger(MyProfile.class.getName());
		PrintWriter out=response.getWriter();
		if(UserInfo.getCurrentUser(request)==null)
		{
			String header=getHeader("Page Not Found") + getEndHeader();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("text/html");
			out.println(header);
			String body="<html><body><div class=\"alert alert-danger container\"><strong><center>You are requesting an invalid page.</strong></center></div></body></html>";
			out.println(body);
			logger.log(Level.WARNING, "MyProfile Page cannot be found due to passing of invalid parameters in URL");
			
			return;
		}
		UserInfo userInfo=UserInfo.getCurrentUser(request);
		String name="<h4><div class=\"row\"><div class=\"col-xs-2\"><b>Name: </b></div><div class=\"col-xs-8\">" + UserInfo.getCurrentUser(request).getName() + " <a href=\"/myProfile?name=" + UserInfo.getCurrentUser(request).getName() + "\" title=\"Change My Name\" data-toggle=\"tooltip\" data-placement=\"right\" data-trigger=\"hover\"><span class=\"glyphicon glyphicon-pencil\"></span></a></div></div></h4>";
		String user="<h4><div class=\"row\"><div class=\"col-xs-2\"><b>Username: </b></div><div class=\"col-xs-8\">" + UserInfo.getCurrentUser(request).getUser() + " <a href=\"/myProfile?username=" + UserInfo.getCurrentUser(request).getUser() + "\" title=\"Change My UserName\" data-toggle=\"tooltip\" data-placement=\"right\" data-trigger=\"hover\"><span class=\"glyphicon glyphicon-pencil\"></span></a></div></div></h4>";
		String email="<h4><div class=\"row\"><div class=\"col-xs-2\"><b>Email Id: </b></div><div class=\"col-xs-8\"><a href=\"\">" + UserInfo.getCurrentUser(request).getEmail() + " <a href=\"/myProfile?email=" + UserInfo.getCurrentUser(request).getEmail() + "\" title=\"Change My Email\" data-toggle=\"tooltip\" data-placement=\"right\" data-trigger=\"hover\"><span class=\"glyphicon glyphicon-pencil\"></span></a></div></div></h4>";
		String address="<h4><div class=\"row\"><div class=\"col-xs-2\"><b>Address: </b></div><div class=\"col-xs-8\">" + UserInfo.getCurrentUser(request).getAddress() + " <a href=\"/myProfile?address=" + UserInfo.getCurrentUser(request).getAddress() + "\" title=\"Change My Address\" data-toggle=\"tooltip\" data-placement=\"right\" data-trigger=\"hover\"><span class=\"glyphicon glyphicon-pencil\"></span></a></div></div></h4></p>";
		if(request.getParameter("yourname")!=null)
		{
			String sqlQuery="UPDATE userdataset SET name=\"" + request.getParameter("yourname") + "\" WHERE userId=\"" + userInfo.getEmail() + "\";";
			try 
			{
				new ExecuteSQLQueries().updateQuery(sqlQuery);
				userInfo.updateName(request.getParameter("yourname"));
				name="<h4><div class=\"row\"><div class=\"col-xs-2\"><b>Name: </b></div><div class=\"col-xs-8\">" + UserInfo.getCurrentUser(request).getName() + " <a href=\"/myProfile?name=" + UserInfo.getCurrentUser(request).getName() + "\" title=\"Change My Name\" data-toggle=\"tooltip\" data-placement=\"right\" data-trigger=\"hover\"><span class=\"glyphicon glyphicon-pencil\"></span></a></div></div></h4>";
				changeRequest(request, response, out, name, user, email, address, "Your name is successfully updated !", "Changed");
			}catch(SQLException e) 
			{
				sqlExceptionError(request, response, out, logger);
				return;
			}
		}
		else if(request.getParameter("address")!=null)
		{
			String sqlQuery="UPDATE userdataset SET address=\"" + request.getParameter("address") + "\" WHERE userId=\"" + userInfo.getEmail() + "\";";
			try 
			{
				new ExecuteSQLQueries().updateQuery(sqlQuery);
				userInfo.updateAddress(request.getParameter("address"));
				address="<h4><div class=\"row\"><div class=\"col-xs-2\"><b>Address: </b></div><div class=\"col-xs-8\">" + UserInfo.getCurrentUser(request).getAddress() + " <a href=\"/myProfile?address=" + UserInfo.getCurrentUser(request).getAddress() + "\" title=\"Change My Address\" data-toggle=\"tooltip\" data-placement=\"right\" data-trigger=\"hover\"><span class=\"glyphicon glyphicon-pencil\"></span></a></div></div></h4></p>";
				changeRequest(request, response, out, name, user, email, address, "Your address is successfully updated !", "Changed");
			}catch(SQLException e) 
			{
				sqlExceptionError(request, response, out, logger);
				return;
			}
		}
		else if(request.getParameter("username")!=null)
		{
			String sqlQuery="SELECT userId from userdataset WHERE username=\"" + request.getParameter("username") + "\";";
			try 
			{
				String queryUserId=new ExecuteSQLQueries().getUserId(sqlQuery);
				if(queryUserId!=null)
				{
					if(queryUserId.equals(userInfo.getEmail()))
					{
						sqlQuery="UPDATE userdataset SET username=\"" + request.getParameter("username") + "\" WHERE userId=\"" + userInfo.getEmail() + "\";";
						new ExecuteSQLQueries().updateQuery(sqlQuery);
						userInfo.updateUser(request.getParameter("username"));
						user="<h4><div class=\"row\"><div class=\"col-xs-2\"><b>Username: </b></div><div class=\"col-xs-8\">" + UserInfo.getCurrentUser(request).getUser() + " <a href=\"/myProfile?username=" + UserInfo.getCurrentUser(request).getUser() + "\" title=\"Change My UserName\" data-toggle=\"tooltip\" data-placement=\"right\" data-trigger=\"hover\"><span class=\"glyphicon glyphicon-pencil\"></span></a></div></div></h4>";
						changeRequest(request, response, out, name, user, email, address, "Your username is successfully updated !", "Changed");
					}
					else
					{
						user="<div class=\"row\"><div class=\"col-xs-2\"><h4><b>Username: </b></h4></div>"
							 + "<form class=\"form-horizontal\" name=\"editProfile\" role=\"form\" method=\"POST\" action=\"/myProfile\">"
							 + "<div class=\"form-group\">"
							 + "<div class=\"col-xs-3\"><input type=\"text\" class=\"form-control\" name=\"username\" value=\"" + request.getParameter("username") + "\" maxlength=\"200\" required></div>"
							 + "<button type=\"submit\" class=\"btn btn-danger\"><span class=\"glyphicon glyphicon-floppy-save\"></span></button>"
							 + "</div>"
							 + "</form></div>";
						changeRequest(request, response, out, name, user, email, address, "This username already exists and is taken by other user ! Please choose a different one instead.", "NotChanged");
					}
				}
				else
				{
					sqlQuery="UPDATE userdataset SET username=\"" + request.getParameter("username") + "\" WHERE userId=\"" + userInfo.getEmail() + "\";";
					new ExecuteSQLQueries().updateQuery(sqlQuery);
					userInfo.updateUser(request.getParameter("username"));
					user="<h4><div class=\"row\"><div class=\"col-xs-2\"><b>Username: </b></div><div class=\"col-xs-8\">" + UserInfo.getCurrentUser(request).getUser() + " <a href=\"/myProfile?username=" + UserInfo.getCurrentUser(request).getUser() + "\" title=\"Change My UserName\" data-toggle=\"tooltip\" data-placement=\"right\" data-trigger=\"hover\"><span class=\"glyphicon glyphicon-pencil\"></span></a></div></div></h4>";
					changeRequest(request, response, out, name, user, email, address, "Your username is successfully updated !", "Changed");
				}
			}catch(SQLException e) 
			{
				sqlExceptionError(request, response, out, logger);
				return;
			}
		}
		else
		{
			String sqlQuery="SELECT userId from userdataset WHERE userId=\"" + request.getParameter("email") + "\";";
			try 
			{
				String queryUserId=new ExecuteSQLQueries().getUserId(sqlQuery);
				if(queryUserId!=null)
				{
					if(queryUserId.equals(userInfo.getEmail()))
					{
						sqlQuery="UPDATE userdataset SET userId=\"" + request.getParameter("email") + "\" WHERE userId=\"" + userInfo.getEmail() + "\";";
						new ExecuteSQLQueries().updateQuery(sqlQuery);
						userInfo.updateEmail(request.getParameter("email"));
						email="<h4><div class=\"row\"><div class=\"col-xs-2\"><b>Email Id: </b></div><div class=\"col-xs-8\"><a href=\"\">" + UserInfo.getCurrentUser(request).getEmail() + " <a href=\"/myProfile?email=" + UserInfo.getCurrentUser(request).getEmail() + "\" title=\"Change My Email\" data-toggle=\"tooltip\" data-placement=\"right\" data-trigger=\"hover\"><span class=\"glyphicon glyphicon-pencil\"></span></a></div></div></h4>";
						changeRequest(request, response, out, name, user, email, address, "Your email is successfully updated ! Now your new email address is the login credential.", "Changed");
					}
					else
					{
						email="<div class=\"row\"><div class=\"col-xs-2\"><h4><b>Email Id: </b></h4></div>"
								 + "<form class=\"form-horizontal\" name=\"editProfile\" role=\"form\" method=\"POST\" action=\"/myProfile\">"
								 + "<div class=\"form-group\">"
								 + "<div class=\"col-xs-3\"><input type=\"email\" class=\"form-control\" name=\"email\" value=\"" + request.getParameter("email") + "\" maxlength=\"100\" required></div>"
								 + "<button type=\"submit\" class=\"btn btn-danger\"><span class=\"glyphicon glyphicon-floppy-save\"></span></button>"
								 + "</div>"
								 + "</form></div>";
						changeRequest(request, response, out, name, user, email, address, "This email address already exists and seems to be of another user ! Please choose a different one instead.", "NotChanged");
					}
				}
				else
				{
					sqlQuery="UPDATE userdataset SET userId=\"" + request.getParameter("email") + "\" WHERE userId=\"" + userInfo.getEmail() + "\";";
					new ExecuteSQLQueries().updateQuery(sqlQuery);
					userInfo.updateEmail(request.getParameter("email"));
					email="<h4><div class=\"row\"><div class=\"col-xs-2\"><b>Email Id: </b></div><div class=\"col-xs-8\"><a href=\"\">" + UserInfo.getCurrentUser(request).getEmail() + " <a href=\"/myProfile?email=" + UserInfo.getCurrentUser(request).getEmail() + "\" title=\"Change My Email\" data-toggle=\"tooltip\" data-placement=\"right\" data-trigger=\"hover\"><span class=\"glyphicon glyphicon-pencil\"></span></a></div></div></h4>";
					changeRequest(request, response, out, name, user, email, address, "Your email is successfully updated ! Now your new email address is the login credential.", "Changed");
				}
			}catch(SQLException e) 
			{
				sqlExceptionError(request, response, out, logger);
				return;
			}
		}
	}
	
	protected void errorMessage(HttpServletResponse response, PrintWriter out, Logger logger) throws IOException
	{
		String header=getHeader("Page Not Found") + getEndHeader();
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		response.setContentType("text/html");
		out.println(header);
		String body="<html><body><div class=\"alert alert-danger container\"><strong><center>You are requesting an invalid page</center></strong></div></body></html>";
		out.println(body);
		logger.log(Level.WARNING, "My Profile Page cannot be found due to passing of invalid parameters in URL");
		
		return;
	}
	
	protected void sqlExceptionError(HttpServletRequest request, HttpServletResponse response, PrintWriter out, Logger logger) throws IOException
	{
		StringWriter sw=new StringWriter();
		new Throwable().printStackTrace(new PrintWriter(sw));
		logger.log(Level.WARNING, "Profile Page generated SQL Exception Error: " + sw.toString());
		String header=getHeader("Internal Server Error") + getEndHeader();
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		response.setContentType("text/html");
		out.println(header);
		String body=getBodyWelcomeNavigationBar("Yap!", "MyProfile", UserInfo.getCurrentUser(request).getUser()) + "<div class=\"alert alert-danger container\"><strong><center>Exception generated in the server. Sorry for the inconvenience. Please try after some time.</strong></center></div>";
		out.println(footer(body));
		
		return;
	}
}
