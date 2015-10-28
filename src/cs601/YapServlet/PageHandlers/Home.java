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
import javax.servlet.http.HttpSession;

import cs601.Yap.QueryEngine.ExecuteSQLQueries;

/**
 * This class serves the Home page for Yap and accepts only GET requests.
 * @author Mayank
 *
 */
public class Home extends YapServlet
{	
	private String addBusinesses;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger logger=Logger.getLogger(Home.class.getName());
		PrintWriter out=response.getWriter();
		if(request.getParameterMap().size()!=0)
		{
			String header=getHeader("Page Not Found") + getEndHeader();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("text/html");
			out.println(header);
			String body="<html><body><div class=\"alert alert-danger container\"><strong><center>You are requesting an invalid page</center></strong></div></body></html>";
			out.println(body);
			logger.log(Level.WARNING, "Home Page cannot be found due to passing of invalid parameters in URL");
			
			return;
		}
		if(StoreBusinessInfo.getBusinessInfo(request)==null)
		{
			HttpSession sessionHome=request.getSession(true);
			String sqlQuery="SELECT * FROM businessdataset;";
			try 
			{
				StoreBusinessInfo business=new ExecuteSQLQueries().getQueryBusinesses(sqlQuery);
				addBusinesses=business.listToHtml();
			}catch (SQLException e) 
			{
				StringWriter sw=new StringWriter();
				new Throwable().printStackTrace(new PrintWriter(sw));
				logger.log(Level.WARNING, "SignIn Page generated SQL Exception Error: " + sw.toString());
				String header=getHeader("Internal Server Error") + getEndHeader();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/html");
				out.println(header);
				String body=getBodyNavigationBar("Yap!", "Home") + "<div class=\"alert alert-danger container\"><strong><center>Exception generated in the server. Sorry for the inconvenience. Please try after some time.</strong></center></div>";
				out.println(footer(body));
				
				return;
			}
			sessionHome.setAttribute("BUSINESS", new StoreBusinessInfo());
		}
		String header=getHeader("Yap - Welcomes You") 
					  + "<style>a:link{color:blue; background-color:transparent; text-decoration:none}a:hover{color:red; background-color:transparent; text-decoration:underline}</style>" 
					  + "<script>$(document).ready(function(){$('[data-toggle=\"popover\"]').popover();});</script>"
					  + getEndHeader();
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		String defaultRes="<div style=\"background-color: rgb(0,139,139)\" class=\"container\"><div style=\"background-color: white\" class=\"well\"><div class=\"well\"><center><h4 class='text-danger'><strong>Yap is the best way to find great local businesses</strong></h4></center></div>"
				+ "<form class=\"form-horizontal\" role=\"form\" action=\"\">"
					+ "<div class=\"form-group\">"
					+ "<div class=\"col-sm-3\">"
						+ "<input class=\"form-control\" id=\"focusedInput\" type=\"text\" placeholder=\"Find By Review Phrases\" name=\"Search\" required>"
					+ "</div>"
					+ "<button type=\"submit\" class=\"btn btn-primary btn-md\"><span class=\"glyphicon glyphicon-search\"></span></button>"
					+ "</div>"
				+ "</form><br/>"
				+ getStoredBusiness()
				+ "</div></div>";
		
		if(UserInfo.getCurrentUser(request)!=null)
		{
			String body=getBodyWelcomeNavigationBar("Yap!", "Home", UserInfo.getCurrentUser(request).getUser()) + defaultRes;
			out.println(footer(body));
		}
		else
		{
			String body=getBodyNavigationBar("Yap!", "Home") + defaultRes;
			out.println(footer(body));
		}
	}
	
	protected String getStoredBusiness()
	{
		return addBusinesses;
	}
}
