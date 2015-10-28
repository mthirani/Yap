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
 * This class serves the purpose of SignIn page in Yap and accepts GET as well as POST request.
 * @author Mayank
 *
 */
public class SignIn extends YapServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger logger=Logger.getLogger(SignIn.class.getName());
		PrintWriter out=response.getWriter();
		if(request.getParameterMap().size()!=0)
		{
			String header=getHeader("Page Not Found") + getEndHeader();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType("text/html");
			out.println(header);
			String body="<html><body><div class=\"alert alert-danger container\"><strong><center>You are requesting an invalid page.</strong></center></div></body></html>";
			out.println(body);
			logger.log(Level.WARNING, "SignIn Page cannot be found due to passing of invalid parameters in URL");
			
			return;
		}
		String header=getHeader("Signing in Yap") + getEndHeader();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		String body=getBodyNavigationBar("Sign in Yap to get more benefits!", "LogIn") + "<div class=\"alert alert-info container\"><strong><center>Enter the below required credentials to login to Yap.</center></strong></div>"
		    	+ "<div class=\"container\"><div class=\"well\"><div class=\"container-fluid\">"
		    		+ "<form class=\"form-horizontal\" name=\"signIn\" role=\"form\" method=\"POST\">"
		    			+ "<div class=\"form-group\">"
		    				+ "<label class=\"control-label col-sm-2\" for=\"email\">Email:</label>"
		    				+ "<div class=\"col-sm-6\">"
		    					+ "<input type=\"email\" class=\"form-control\" name=\"email\" placeholder=\"Enter your email\" required>"
		    				+ "</div>"
		    			+ "</div>"
		    			+ "<div class=\"form-group\">"
	    					+ "<label class=\"control-label col-sm-2\" for=\"pwd\">Password:</label>"
	    					+ "<div class=\"col-sm-6\">"
	    						+ "<input type=\"password\" class=\"form-control\" name=\"pwd\" placeholder=\"Enter your passsword\" required>"
	    					+ "</div>"
	    				+ "</div>"
	    				+ "<div class=\"form-group\">"
	    					+ "<div class=\"col-sm-offset-2 col-sm-10\">"
	    						+ "<button type=\"submit\" class=\"btn btn-primary btn-md\">Log In</button>"
	    					+ "</div>"
    					+ "</div>"
	    			+ "</form>"
	    		+ "</div></div>";
		
		out.println(footer(body));
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Logger logger=Logger.getLogger(SignIn.class.getName());
		PrintWriter out=response.getWriter();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		String body="";
		String header=getHeader("Signing in Yap") + getEndHeader();
		String sqlQuery="SELECT * FROM userdataset WHERE userId=\"" + request.getParameter("email") + "\";";
		try 
		{
			UserInfo user=new ExecuteSQLQueries().getQuery(sqlQuery, request.getParameter("pwd"));
			if(user!=null)
			{
				HttpSession session=request.getSession(true);
				session.setAttribute("USER", user);
				response.sendRedirect(response.encodeRedirectURL("/yap"));
				return;
			}
			else
			{
				out.println(header);
				body=getBodyNavigationBar("Sign in Yap to get more benefits!", "LogIn") + "<div class=\"alert alert-danger container\"><strong><center>Sorry, Your username/password does not matches! Please provide your valid credentials or Click " + "<a href=\"/signup\">SignUp</a>" + " to Register in Yap.</strong></center></div>";
				out.println(footer(body));
			}
		}catch (SQLException e) 
		{
			StringWriter sw=new StringWriter();
			new Throwable().printStackTrace(new PrintWriter(sw));
			logger.log(Level.WARNING, "SignIn Page generated SQL Exception Error: " + sw.toString());
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
