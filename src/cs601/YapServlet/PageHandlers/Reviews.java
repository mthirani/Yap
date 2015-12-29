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
 * This class serves the Reviews section for each business Id with filtering options and accepts only GET requests.
 * @author Mayank
 *
 */
public class Reviews extends YapServlet 
{
	private String addBusinesses;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger logger=Logger.getLogger(Home.class.getName());
		PrintWriter out=response.getWriter();
		String sqlQuery="";
		if(request.getParameterMap().size() == 2)
		{
			Enumeration en=request.getParameterNames();
			String paramName=(String) en.nextElement();
			if(!(paramName.equals("search")))
			{
				errorMessage(response, out, logger);
				return;
			}
			paramName=(String) en.nextElement();
			if(!(paramName.equals("businessId")))
			{
				errorMessage(response, out, logger);
				return;
			}
			if(request.getParameter("businessId").equals(""))
			{
				errorMessage(response, out, logger);
				return;
			}
			if(!(request.getParameter("businessId").equals("")) && request.getParameter("search").equals(""))
			{
				String redirect="/yap?businessId=" + request.getParameter("businessId");
				response.sendRedirect(response.encodeRedirectURL(redirect));
				return;
			}
			sqlQuery="SELECT * FROM businessdataset WHERE businessId=\"" + request.getParameter("businessId") + "\";";
		}
		else if(request.getParameterMap().size() == 1)
		{
			Enumeration en=request.getParameterNames();
			String paramName=(String) en.nextElement();
			if(!(paramName.equals("search")))
			{
				errorMessage(response, out, logger);
				return;
			}
			if(StoreBusinessInfo.numberBusinessIds() == 0)
			{
				errorMessage(response, out, logger);
				return;
			}
			sqlQuery="SELECT * FROM businessdataset WHERE businessId=\"" + StoreBusinessInfo.businessId() + "\";";
		}
		else
		{
			errorMessage(response, out, logger);
			return;
		}
		
		try 
		{
			StoreBusinessInfo business=new ExecuteSQLQueries().getQueryBusinesses(sqlQuery, request.getParameter("search"));
			addBusinesses=business.listHtmlBusinessId(request.getParameter("search"));
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
	
	protected void errorMessage(HttpServletResponse response, PrintWriter out, Logger logger) throws IOException
	{
		String header=getHeader("Page Not Found") + getEndHeader();
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		response.setContentType("text/html");
		out.println(header);
		String body="<html><body><div class=\"alert alert-danger container\"><strong><center>You are requesting an invalid page</center></strong></div></body></html>";
		out.println(body);
		logger.log(Level.WARNING, "Reviews Page cannot be found due to passing of invalid parameters in URL");
	
		return;
	}
	
	protected String getStoredBusiness()
	{
		return addBusinesses;
	}
}
