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
 * This class serves the Home page and reviews page (for each business Id) for Yap and accepts only GET requests.
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
		String sorting="";
		int page=1;
		if(request.getParameterMap().size() == 1)
		{
			Enumeration en=request.getParameterNames();
			String paramName=(String) en.nextElement();
			if(!(paramName.equals("businessId")) && !(paramName.equals("sortby")) && !(paramName.equals("page")))
			{
				errorMessage(response, out, logger);
				return;
			}
			if(paramName.equals("businessId"))
			{
				if(request.getParameter("businessId").equals(""))
				{
					errorMessage(response, out, logger);
					return;
				}
				doGetBusinessReviewHandling(request, response, out, logger, request.getParameter("businessId"));
				return;
			}
			if(paramName.equals("page"))
			{
				if(request.getParameter("page").equals(""))
				{
					errorMessage(response, out, logger);
					return;
				}
				page=Integer.parseInt(request.getParameter("page"));
				sorting=StoreBusinessInfo.getSorting();
			}
			if(paramName.equals("sortby"))
			{
				if(request.getParameter("sortby").equals("") && !(request.getParameter("sortby").equals("alphaAscending")) && !(request.getParameter("sortby").equals("ratingAscending")) && !(request.getParameter("sortby").equals("alphaDescending")) && !(request.getParameter("sortby").equals("ratingDescending")))
				{
					errorMessage(response, out, logger);
					return;
				}
				sorting=request.getParameter("sortby");
				page=StoreBusinessInfo.getPage();
			}
		}
		else
		{
			if(request.getParameterMap().size() > 1)
			{
				errorMessage(response, out, logger);
				return;
			}
			else
			{
				sorting="default";
				page=1;
			}
		}
		String sqlQuery="SELECT * FROM businessdataset;";
		try 
		{
			StoreBusinessInfo business=new ExecuteSQLQueries().getQueryBusinesses(sqlQuery, "");
			addBusinesses=business.listToHtml(sorting, page);
		}catch (SQLException e) 
		{
			StringWriter sw=new StringWriter();
			new Throwable().printStackTrace(new PrintWriter(sw));
			logger.log(Level.WARNING, "Home Page generated SQL Exception Error: " + sw.toString());
			String header=getHeader("Internal Server Error") + getEndHeader();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/html");
			out.println(header);
			String body=getBodyNavigationBar("Yap!", "Home") + "<div class=\"alert alert-danger container\"><strong><center>Exception generated in the server. Sorry for the inconvenience. Please try after some time.</strong></center></div>";
			out.println(footer(body));
			
			return;
		}
		String header=getHeader("Yap - Welcomes You") 
					  + "<style>a:link{color:blue; background-color:transparent; text-decoration:none}a:hover{color:red; background-color:transparent; text-decoration:underline}.wrapper {text-align: center;}</style>" 
					  + "<script>$(document).ready(function(){$('[data-toggle=\"popover\"]').popover();});</script>"
					  + getEndHeader();
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		String defaultRes="<div style=\"background-color: rgb(0,139,139)\" class=\"container\"><div style=\"background-color: white\" class=\"well\"><div class=\"well\"><center><h4 class='text-danger'><strong>Yap is the best way to find great local businesses</strong></h4></center><br/><div class=\"wrapper\"><form role=\"form\" action=\"createBusiness\" method=\"GET\"><button type=\"submit\" class=\"btn btn-danger\">Create Business</button></form></div></div>"
				+ "<div class=\"btn-group\"><button type=\"button\" class=\"btn btn-primary\">Sort By </button><button class=\"btn btn-primary dropdown-toggle\" type=\"button\" data-toggle=\"dropdown\"><span class=\"caret\"></span></button><ul class=\"dropdown-menu\"><li><a href=\"/yap?sortby=alphaAscending\"><span class=\"glyphicon glyphicon-sort-by-alphabet\"></span> BusinessName</a></li><li><a href=\"/yap?sortby=alphaDescending\"><span class=\"glyphicon glyphicon-sort-by-alphabet-alt\"></span> BusinessName</a></li><li><a href=\"/yap?sortby=ratingAscending\"><span class=\"glyphicon glyphicon-sort-by-attributes\"></span> Rating</a></li><li><a href=\"/yap?sortby=ratingDescending\"><span class=\"glyphicon glyphicon-sort-by-attributes-alt\"></span> Rating</a></li></ul></div></p>"
				+ "<br/>"
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
	
	protected void errorMessage(HttpServletResponse response, PrintWriter out, Logger logger) throws IOException
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
	
	protected void doGetBusinessReviewHandling(HttpServletRequest request, HttpServletResponse response, PrintWriter out, Logger logger, String busId) throws IOException
	{
		String sqlQuery="SELECT * FROM businessdataset WHERE businessId=\"" + busId + "\";";
		try 
		{
			StoreBusinessInfo business=new ExecuteSQLQueries().getQueryBusinesses(sqlQuery, "");
			if(business==null)
			{
				errorMessage(response, out, logger);
				return;
			}
			addBusinesses=business.listHtmlBusinessId("");
		}catch (SQLException e) 
		{
			StringWriter sw=new StringWriter();
			new Throwable().printStackTrace(new PrintWriter(sw));
			logger.log(Level.WARNING, "Review Page generated SQL Exception Error: " + sw.toString());
			String header=getHeader("Internal Server Error") + getEndHeader();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/html");
			out.println(header);
			String body=getBodyNavigationBar("Yap!", "Home") + "<div class=\"alert alert-danger container\"><strong><center>Exception generated in the server. Sorry for the inconvenience. Please try after some time.</strong></center></div>";
			out.println(footer(body));
			
			return;
		}
		String header=getHeader("Yap - Welcomes You") 
					+ "<style>a:link{color:blue; background-color:transparent; text-decoration:none}a:hover{color:blue; background-color:transparent; text-decoration:underline}#map{width: 300px;height: 200px;}.wrapper{text-align: center;}</style>"
					+ "<script src=\"https://maps.googleapis.com/maps/api/js\"></script>"
					+ getEndHeader();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		if(UserInfo.getCurrentUser(request)!=null)
		{
			String body=getBodyWelcomeNavigationBar("Yap!", "Home", UserInfo.getCurrentUser(request).getUser()) + getStoredBusiness();
			out.println(footer(body));
		}
		else
		{
			String body=getBodyNavigationBar("Yap!", "Home") + getStoredBusiness();
			out.println(footer(body));
		}
	}
	
	protected String getStoredBusiness()
	{
		return addBusinesses;
	}
}
