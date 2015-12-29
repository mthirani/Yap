package cs601.YapServlet.PageHandlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cs601.Yap.QueryEngine.ExecuteSQLQueries;

/**
 * This class posts the user comments for a specific business and handles only the POST request.
 * @author Mayank
 *
 */
public class WriteReview extends YapServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger logger=Logger.getLogger(Home.class.getName());
		PrintWriter out=response.getWriter();
		if(request.getParameterMap().size() > 2)
		{
			errorMessage(response, out, logger);
			return;
		}
		String header=getHeader("Yap - Welcomes You") + getEndHeader();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		String body="";
		if(UserInfo.getCurrentUser(request)==null)
		{
			String res="<div class=\"alert alert-danger container\"><strong><center>Sorry, You have to login first in Yap to write a review for the business! Please provide your valid credentials in <a href=\"/signin\">Login</a>" + " or Click " + "<a href=\"/signup\">SignUp</a>" + " to Register in Yap.</strong></center></div>";
			body=getBodyNavigationBar("Yap!", "Home") + res;
			out.println(footer(body));
			return;
		}
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		String revwDate=format1.format(date);
		String reviewID=StoreBusinessInfo.businessId() + UserInfo.getCurrentUser(request).getEmail() + revwDate;
		String sqlQuery="SELECT reviewId FROM reviewdataset WHERE reviewId=\"" + reviewID + "\";";
		try 
		{
			if(new ExecuteSQLQueries().getQuery(sqlQuery))
			{
				sqlQuery="INSERT INTO reviewdataset (funnyVotes,usefulVotes,coolVotes,userId,reviewId,stars,revwDate,review,businessId) VALUES(0,0,0,\"" + UserInfo.getCurrentUser(request).getEmail() + "\",\"" + reviewID + "\"," + request.getParameter("rating") + ",\"" + revwDate + "\",\"" + request.getParameter("comment") + "\",\"" + StoreBusinessInfo.businessId() + "\");";
				new ExecuteSQLQueries().updateQuery(sqlQuery);
				body=getBodyWelcomeNavigationBar("Yap!", "Home", UserInfo.getCurrentUser(request).getUser()) + "<div class=\"alert alert-success container\"><strong><center>Your comments has been successfully posted!</strong></center></div>";
				out.println(footer(body));
			}
			else
			{
				body=getBodyWelcomeNavigationBar("Yap!", "Home", UserInfo.getCurrentUser(request).getUser()) + "<div class=\"alert alert-danger container\"><strong><center>Sorry, you have already provided your review for this business today. Hope to see you tomorrow again!</strong></center></div>";
				out.println(footer(body));
			}
		}catch (SQLException e) 
		{
			StringWriter sw=new StringWriter();
			new Throwable().printStackTrace(new PrintWriter(sw));
			logger.log(Level.WARNING, "Write Review Page generated SQL Exception Error: " + sw.toString());
			String header1=getHeader("Internal Server Error") + getEndHeader();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/html");
			out.println(header1);
			String body1=getBodyNavigationBar("Yap!", "Home") + "<div class=\"alert alert-danger container\"><strong><center>Exception generated in the server. Sorry for the inconvenience. Please try after some time.</strong></center></div>";
			out.println(footer(body1));
			
			return;
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
		logger.log(Level.WARNING, "Write Review Page cannot be found due to passing of invalid parameters in URL");
		
		return;
	}
}
