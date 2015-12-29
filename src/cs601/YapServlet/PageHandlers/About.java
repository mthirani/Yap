package cs601.YapServlet.PageHandlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class creates the About page for Yap and handles only the GET request.
 * @author Mayank
 *
 */
public class About extends YapServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger logger=Logger.getLogger(About.class.getName());
		PrintWriter out=response.getWriter();
		if(request.getParameterMap().size()!=0)
		{
			String header=getHeader("Page Not Found") + getEndHeader();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("text/html");
			out.println(header);
			String body="<html><body><div class=\"alert alert-danger container\"><strong><center>You are requesting an invalid page</strong></center></div></body></html>";
			out.println(body);
			logger.log(Level.WARNING, "About Page cannot be found due to passing of invalid parameters in URL");
			
			return;
		}
		String header=getHeader("About Yap") + getEndHeader();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		String defaultRes="<div class=\"alert alert-info container\">"
				+ "<strong><center>Great to find out that you are eager to know about Yap !</center></strong></div>"
				+ "<div class=\"container\">"
				+ "<h2>About Yap:</h2>"
				+ "<h3><u>CS 601 - Fall 2015 - Yap Project</u></h3>"
				+ "<p>The goal of this project is to implement a Yelp-like review site, called Yap! Make sure you are familiar with Yelp before beginning this project.</p>"
				+ "<p>For this project, you will build your own web site from scratch. You will design and implement a two-tier web application with a Java front end and a SQL backend. You may reuse pieces of your Lab assignments for this project, but it is not required. In some cases, you may need to modify or redesign your Lab code in order to integrate it into your site. You do not need to seed your database with the Yelp data set.</p>"
				+ "<p>80% of your final project grade will be based on the functionality of the features your site provides.</p>"
				+ "<h3>Release 1 Features: </h3>"
				+ "<table class=\"table table-striped table table-bordered\"><thead>"
				+ "<tr><th align=\"center\">Points</th><th align=\"left\">Feature</th><th align=\"left\">Description</th></tr></thead>"
				+ "<tbody><tr><td align=\"center\">5</td><td align=\"left\">User registration</td><td align=\"left\">Provide a <i>Sign Up</i> button that allows a user to register to use your service. User must be able to enter appropriate account information and user data must be saved appropriately in your system.</td></tr>"
				+ "<tr><td align=\"center\">5</td><td align=\"left\">View businesses</td><td align=\"left\">Display a list of all businesses with reviews in your system. Show the average rating for each business.</td></tr>"
				+ "<tr><td align=\"center\">5</td><td align=\"left\">Relational database - Users</td><td align=\"left\">Use a relational database to store <i>user account</i> data.</td></tr>"
				+ "<tr><td align=\"center\">5</td><td align=\"left\">Relational database - Reviews</td><td align=\"left\">Use a relational database to store <i>review</i> data.</td></tr>"
				+ "<tr><td align=\"center\">5</td><td align=\"left\">Javascript</td><td align=\"left\">Use Javascript and/or a template library to generate the UI shown in the browser.</td></tr>"
				+ "</tbody></table>"
				+ "<h3>Release 2 Features: </h3>"
				+ "<table class=\"table table-striped table table-bordered\"><thead>"
				+ "<tr><th align=\"center\">Points</th><th align=\"left\">Feature</th><th align=\"left\">Description</th></tr></thead>"
				+ "<tbody><tr><td align=\"center\">5</td><td align=\"left\">Login and logout</td><td align=\"left\">Allow a user to login and logout of your site. Maintain the user session appropriately.</td></tr>"
				+ "<tr><td align=\"center\">5</td><td align=\"left\">Sort by</td><td align=\"left\">Allow a user to dynamically choose how to sort a list of displayed businesses, for example by rating or alphabetically.</td></tr>"
				+ "<tr><td align=\"center\">5</td><td align=\"left\">View reviews for a specific business</td><td align=\"left\">Allow a user to select a specific business and display reviews for that business.</td></tr>"
				+ "<tr><td align=\"center\">10</td><td align=\"left\">Maps integration</td><td align=\"left\">Integrate a maps service, e.g., embed google maps on your site.</td></tr>"
				+ "</tbody></table>"
				+ "<h3>Release 3 Features: </h3>"
				+ "<table class=\"table table-striped table table-bordered\"><thead>"
				+ "<tr><th align=\"center\">Points</th><th align=\"left\">Feature</th><th align=\"left\">Description</th></tr></thead>"
				+ "<tbody><tr><td align=\"center\">5</td><td align=\"left\">Relational database - Businesses</td><td align=\"left\">Use a relational database to store business data.</td></tr>"
				+ "<tr><td align=\"center\">5</td><td align=\"left\">Show n businesses per page</td><td align=\"left\">Provide pagination to allow a user to see some specific number of businesses per page and scroll to the next page.</td></tr>"
				+ "<tr><td align=\"center\">5</td><td align=\"left\">Edit User Profile</td><td align=\"left\">Provide access to the user to update his/ her information in database.</td></tr>"
				+ "<tr><td align=\"center\">10</td><td align=\"left\">Search</td><td align=\"left\">Allow a user to search reviews for particular phrases.</td></tr>"
				+ "<tr><td align=\"center\">10</td><td align=\"left\">Add review</td><td align=\"left\">Allow a user to add a new review for a business. Unlike Yelp, allow a user to create a business if it does not already exist.</td></tr>"
				+ "</tbody></table>"
				+ "</div>";
		
		if(UserInfo.getCurrentUser(request)!=null)
		{
			String body=getBodyWelcomeNavigationBar("Yap!", "About", UserInfo.getCurrentUser(request).getUser()) + defaultRes;
			out.println(footer(body));
		}
		else
		{
			String body=getBodyNavigationBar("Yap!", "About") + defaultRes;
			out.println(footer(body));
		}
	}
}
