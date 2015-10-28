package cs601.YapServlet.PageHandlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class serves the purpose of User's Profile page in Yap and accepts only GET requests.
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
		String header=getHeader("My Profile") + getEndHeader();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		String defaultRes="<div class=\"alert alert-info container\">"
				+ "<h4><center><strong>Welcome to your profile status " + UserInfo.getCurrentUser(request).getUser() + " !</strong></center></h4></div>"
				+ "<div class=\"container\">"
				+ "<h4><b>Name: </b>" + UserInfo.getCurrentUser(request).getName() + "</h4>"
				+ "<h4><b>Username: </b>" + UserInfo.getCurrentUser(request).getUser() + "</h4>"
				+ "<h4><b>Email Id: </b><a href=\"\">" + UserInfo.getCurrentUser(request).getEmail() + "</a></h4>"
				+ "<h4><b>Address: </b>" + UserInfo.getCurrentUser(request).getAddress() + "</h4>"
				+ "<h4><b>My Current Vote Status: </b></h4>"
				+ "<button type=\"button\" class=\"btn btn-primary\">Funny <span class=\"badge\">" + UserInfo.getCurrentUser(request).getFunny() +"</span></button>"
				+ "<button type=\"button\" class=\"btn btn-success\">UseFul <span class=\"badge\">" + UserInfo.getCurrentUser(request).getUseful() +"</span></button>"
				+ "<button type=\"button\" class=\"btn btn-warning\">Cool <span class=\"badge\">" + UserInfo.getCurrentUser(request).getCool() +"</span></button>"
				+ "</div>";
		
		String body=getBodyWelcomeNavigationBar("Yap!", "MyProfile", UserInfo.getCurrentUser(request).getUser()) + defaultRes;
		out.println(footer(body));
	}
}
