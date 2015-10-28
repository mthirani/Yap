package cs601.YapServlet.PageHandlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class creates the Contact page for Yap and accepts both GET/ POST requests.
 * @author Mayank
 *
 */
public class Contact extends YapServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger logger=Logger.getLogger(Contact.class.getName());
		PrintWriter out=response.getWriter();
		if(request.getParameterMap().size()!=0)
		{
			String header=getHeader("Page Not Found") + getEndHeader();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("text/html");
			out.println(header);
			String body="<html><body><div class=\"alert alert-danger container\"><strong><center>You are requesting an invalid page</strong></center></div></body></html>";
			out.println(body);
			logger.log(Level.WARNING, "Contact Page cannot be found due to passing of invalid parameters in URL");
			
			return;
		}
		String header=getHeader("Contact Us") + getEndHeader();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		String defaultRes="<div class=\"alert alert-success container\"><strong><center>We will be pleased to hear from you. Please let us know your feedbacks/ questions.</strong></center></div><div class=\"container\">"
			    		+ "<form class=\"form-horizontal\" role=\"form\" method=\"POST\">"
				    		+ "<div class=\"form-group\">"
		    					+ "<label class=\"control-label col-sm-2\" for=\"username\">Your Name:</label>"
		    					+ "<div class=\"col-sm-6\">"
		    						+ "<input type=\"text\" class=\"form-control\" name=\"username\" placeholder=\"Enter your name\" required>"
		    					+ "</div>"
		    				+ "</div>"
			    			+ "<div class=\"form-group\">"
			    				+ "<label class=\"control-label col-sm-2\" for=\"email\">Email:</label>"
			    				+ "<div class=\"col-sm-6\">"
			    					+ "<input type=\"email\" class=\"form-control\" name=\"email\" placeholder=\"Enter your email\" required>"
			    				+ "</div>"
			    			+ "</div>"
		    				+ "<div class=\"form-group\">"
		    					+ "<label class=\"control-label col-sm-2\" for=\"comment\">Comments:</label>"
		    					+ "<div class=\"col-sm-6\">"
		    						+ "<textarea class=\"form-control\" rows=\"5\" id=\"comment\" name=\"comment\" placeholder=\"Enter your comments\" required></textarea>"
		    					+ "</div>"
		    				+ "</div>"
		    				+ "<div class=\"form-group\">"
		    					+ "<div class=\"col-sm-offset-2 col-sm-10\">"
		    						+ "<button type=\"submit\" class=\"btn btn-primary btn-md\">Submit</button>"
		    					+ "</div>"
		    				+ "</div>"
		    			+ "</form>"
		    		+ "</div>"
		    		+ "</br></br></br>"
		    		+ "<div \"class=\"container\">"
		    		+ "<center><p><u>Our Location</u>: 2130 Fulton Street, Room# LS307, San Francisco, CA 94117</p>"
		    		+ "<p><u>Our Phone</u>: (415)424-1555</p></center>"
		    		+ "</div>";
		
		if(UserInfo.getCurrentUser(request)!=null)
		{
			String body=getBodyWelcomeNavigationBar("Yap!", "Contact", UserInfo.getCurrentUser(request).getUser()) + defaultRes;
			out.println(footer(body));
		}
		else
		{
			String body=getBodyNavigationBar("Yap!", "Contact") + defaultRes;
			out.println(footer(body));
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out=response.getWriter();
		String header=getHeader("Contact Us") + getEndHeader();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		String successMessage="<div class=\"alert alert-success container\"><strong><center>Thanks for writing to us. We will contact you shortly.</strong></center></div>";
		if(UserInfo.getCurrentUser(request)!=null)
		{
			String body=getBodyWelcomeNavigationBar("Yap!", "Contact", UserInfo.getCurrentUser(request).getUser()) + successMessage;
			out.println(footer(body));
		}
		else
		{
			String body=getBodyNavigationBar("Yap!", "Contact") + successMessage;
			out.println(footer(body));
		}
	}
}
