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
 * This class creates the new business and accepts both GET/ POST requests.
 * @author Mayank
 *
 */
public class CreateBusiness extends YapServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger logger=Logger.getLogger(Home.class.getName());
		PrintWriter out=response.getWriter();
		if(request.getParameterMap().size() > 0)
		{
			errorMessage(response, out, logger);
			return;
		}
		String header=getHeader("Yap - Welcomes You") + "<script type=\"text/javascript\">function validateForm(){var conf=confirm(\"Press OK for submitting your data or Click Cancel if you want to change some parameters for registering new business\");if(conf==true){return true;}else{return false;}}</script>" + getEndHeader();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		if(UserInfo.getCurrentUser(request)==null)
		{
			String res="<div class=\"alert alert-danger container\"><strong><center>Sorry, You have to login first in Yap to create a business! Please provide your valid credentials in <a href=\"/signin\">Login</a>" + " or Click " + "<a href=\"/signup\">SignUp</a>" + " to Register in Yap.</strong></center></div>";
			String body=getBodyNavigationBar("Yap!", "Home") + res;
			out.println(footer(body));
			return;
		}
		String bustypeoptions="<option value=\"Others\">Others</option><option value=\"Bar & Restaurant\">Bar & Restaurant</option><option value=\"Bookstore\">Bookstore</option><option value=\"Clinic\">Clinic</option><option value=\"Coffee House\">Coffee House</option><option value=\"Courier\">Courier</option><option value=\"Departmental Store\">Departmental Store</option><option value=\"Music Station\">Music Station</option><option value=\"Ornament\">Ornament</option><option value=\"Restaurant\">Restaurant</option><option value=\"Realty Traders\">Realty Traders</option><option value=\"Rental Apartments\">Rental Apartments</option><option value=\"Salon\">Salon</option><option value=\"Store\">Store</option>";
		String stateoptions="<option value=\"AL\">Alabama</option><option value=\"AK\">Alaska</option><option value=\"AZ\">Arizona</option><option value=\"AR\">Arkansas</option><option value=\"CA\">California</option><option value=\"CO\">Colorado</option><option value=\"CT\">Connecticut</option><option value=\"DE\">Delaware</option><option value=\"DC\">District Of Columbia</option><option value=\"FL\">Florida</option><option value=\"GA\">Georgia</option><option value=\"HI\">Hawaii</option><option value=\"ID\">Idaho</option><option value=\"IL\">Illinois</option><option value=\"IN\">Indiana</option><option value=\"IA\">Iowa</option><option value=\"KS\">Kansas</option><option value=\"KY\">Kentucky</option><option value=\"LA\">Louisiana</option><option value=\"ME\">Maine</option><option value=\"MD\">Maryland</option><option value=\"MA\">Massachusetts</option><option value=\"MI\">Michigan</option><option value=\"MN\">Minnesota</option><option value=\"MS\">Mississippi</option><option value=\"MO\">Missouri</option><option value=\"MT\">Montana</option><option value=\"NE\">Nebraska</option><option value=\"NV\">Nevada</option><option value=\"NH\">New Hampshire</option><option value=\"NJ\">New Jersey</option><option value=\"NM\">New Mexico</option><option value=\"NY\">New York</option><option value=\"NC\">North Carolina</option><option value=\"ND\">North Dakota</option><option value=\"OH\">Ohio</option><option value=\"OK\">Oklahoma</option><option value=\"OR\">Oregon</option><option value=\"PA\">Pennsylvania</option><option value=\"RI\">Rhode Island</option><option value=\"SC\">South Carolina</option><option value=\"SD\">South Dakota</option><option value=\"TN\">Tennessee</option><option value=\"TX\">Texas</option><option value=\"UT\">Utah</option><option value=\"VT\">Vermont</option><option value=\"VA\">Virginia</option><option value=\"WA\">Washington</option><option value=\"WV\">West Virginia</option><option value=\"WI\">Wisconsin</option><option value=\"WY\">Wyoming</option>";
		String defaultRes="<div class=\"alert alert-info container\"><strong><center>Enter the below required information to register a new business and Yap will store your business information!</center></strong></div>"
							+ "<div class=\"container\"><div class=\"well\">"
							+ "<form class=\"form-horizontal\" name=\"createbus\" action=\"/createBusiness\" role=\"form\" method=\"POST\" onsubmit=\"return validateForm()\">"
					    		+ "<div class=\"form-group\">"
									+ "<label class=\"control-label col-sm-2\" for=\"busname\">Business Name:</label>"
									+ "<div class=\"col-sm-6\">"
										+ "<input type=\"text\" class=\"form-control\" name=\"busname\" placeholder=\"Enter the business name (should be within 50 characters)\" maxlength=\"50\" required>"
									+ "</div>"
								+ "</div>"
								+ "<div class=\"form-group\">"
									+ "<label class=\"control-label col-sm-2\" for=\"city\">City:</label>"
									+ "<div class=\"col-sm-6\">"
										+ "<input type=\"text\" class=\"form-control\" name=\"city\" placeholder=\"Enter the city where your business is situated (should be within 100 characters)\" maxlength=\"100\" required>"
									+ "</div>"
								+ "</div>"
								+ "<div class=\"form-group\">"
									+ "<label class=\"control-label col-sm-2\" for=\"state\">State:</label>"
									+ "<div class=\"col-sm-6\">"
										+ "<select class=\"form-control\" name=\"state\" maxlength=\"10\" required>"
										+ stateoptions
										+ "</select>"
									+ "</div>"
								+ "</div>"
								+ "<div class=\"form-group\">"
									+ "<label class=\"control-label col-sm-2\" for=\"neighbours\">Neighbours:</label>"
									+ "<div class=\"col-sm-6\">"
										+ "<input type=\"text\" class=\"form-control\" name=\"neighbours\" placeholder=\"Enter the neighbours seperated by commas (should be within 200 characters)\" maxlength=\"200\">"
									+ "</div>"
								+ "</div>"
								+ "<div class=\"form-group\">"
									+ "<label class=\"control-label col-sm-2\" for=\"lon\">Longitude:</label>"
									+ "<div class=\"col-sm-6\">"
										+ "<input type=\"number\" class=\"form-control\" name=\"lon\" placeholder=\"Enter the Longitude of your business location\" step=\"any\" max=\"180\" min=\"-180\" required>"
									+ "</div>"
								+ "</div>"
								+ "<div class=\"form-group\">"
									+ "<label class=\"control-label col-sm-2\" for=\"lat\">Latitude:</label>"
									+ "<div class=\"col-sm-6\">"
										+ "<input type=\"number\" class=\"form-control\" name=\"lat\" placeholder=\"Enter the Latitude of your business location\" step=\"any\" max=\"90\" min=\"-90\" required>"
									+ "</div>"
								+ "</div>"
								+ "<div class=\"form-group\">"
									+ "<label class=\"control-label col-sm-2\" for=\"addr\">Address:</label>"
									+ "<div class=\"col-sm-6\">"
										+ "<input type=\"text\" class=\"form-control\" name=\"addr\" placeholder=\"Enter the Full Address (should be within 500 characters)\" maxlength=\"500\" required>"
									+ "</div>"
								+ "</div>"
								+ "<div class=\"form-group\">"
									+ "<label class=\"control-label col-sm-2\" for=\"bustype\">Business Type:</label>"
									+ "<div class=\"col-sm-6\">"
										+ "<select class=\"form-control\" name=\"bustype\" maxlength=\"100\" required>"
										+ bustypeoptions
										+ "</select>"
									+ "</div>"
								+ "</div>"
								+ "<div class=\"form-group\">"
									+ "<div class=\"col-sm-offset-2 col-sm-10\">"
										+ "<button type=\"submit\" class=\"btn btn-primary btn-md\">Register New Business</button>"
									+ "</div>"
								+ "</div>"
							+ "</form>"
							+ "</div></div>";
		
		String body=getBodyWelcomeNavigationBar("Register New Business!", "Home", UserInfo.getCurrentUser(request).getUser()) + defaultRes;
		out.println(footer(body));
	}

	protected void errorMessage(HttpServletResponse response, PrintWriter out, Logger logger) throws IOException
	{
		String header=getHeader("Page Not Found") + getEndHeader();
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		response.setContentType("text/html");
		out.println(header);
		String body="<html><body><div class=\"alert alert-danger container\"><strong><center>You are requesting an invalid page</center></strong></div></body></html>";
		out.println(body);
		logger.log(Level.WARNING, "Register New Business Page cannot be found due to passing of invalid parameters in URL");
		
		return;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Logger logger=Logger.getLogger(Home.class.getName());
		PrintWriter out=response.getWriter();
		String header=getHeader("Yap - Welcomes You") + getEndHeader();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		out.println(header);
		String body="";
		if(UserInfo.getCurrentUser(request)==null)
		{
			String res="<div class=\"alert alert-danger container\"><strong><center>Sorry, You have to login first in Yap to register a new business! Please provide your valid credentials in <a href=\"/signin\">Login</a>" + " or Click " + "<a href=\"/signup\">SignUp</a>" + " to Register in Yap.</strong></center></div>";
			body=getBodyNavigationBar("Yap!", "Home") + res;
			out.println(footer(body));
			return;
		}
		if(request.getParameter("busname").length() > 50 || request.getParameter("city").length() > 100 || request.getParameter("state").length() > 10 || request.getParameter("neighbours").length() > 200 || request.getParameter("addr").length() > 500 || request.getParameter("bustype").length() > 100)
		{
			body=getBodyWelcomeNavigationBar("Register New Business!", "Home", UserInfo.getCurrentUser(request).getUser()) + "<div class=\"alert alert-danger container\"><strong><center>Sorry, You must your check your form inputs to register the new business.</strong></center></div>";
			out.println(footer(body));
			return;
		}
		Double lon=Double.parseDouble(request.getParameter("lon"));
		Double lat=Double.parseDouble(request.getParameter("lat"));
		String busId=request.getParameter("busname") + request.getParameter("lon") + request.getParameter("lat");
		String sqlQuery="SELECT businessId FROM businessdataset WHERE businessId=\"" + busId + "\";";
		try 
		{
			if(new ExecuteSQLQueries().getQuery(sqlQuery))
			{
				String sqlQueryBus="INSERT INTO businessdataset (businessId,city,busName,neighbours,longitude,state,latitude,address,busType) VALUES(\"" + busId + "\",\"" + request.getParameter("city") + "\",\"" + request.getParameter("busname") + "\",\"" + request.getParameter("neighbours") + "\"," + lon + ",\"" + request.getParameter("state") + "\"," + lat + ",\"" + request.getParameter("addr") + "\",\"" + request.getParameter("bustype") + "\");";
				new ExecuteSQLQueries().updateQuery(sqlQueryBus);
				body=getBodyWelcomeNavigationBar("Register New Business!", "Home", UserInfo.getCurrentUser(request).getUser()) + "<div class=\"alert alert-success container\"><strong><center>Successfully Registered the new business in Yap! We hope your listed business get growing.</strong></center></div>";
				out.println(footer(body));
			}
			else
			{
				body=getBodyWelcomeNavigationBar("Register New Business!", "Home", UserInfo.getCurrentUser(request).getUser()) + "<div class=\"alert alert-danger container\"><strong><center>Sorry, we already have this business in our store. You must choose different busniness name or locations</strong></center></div>";
				out.println(footer(body));
			}
		}catch (SQLException e) 
		{
			StringWriter sw=new StringWriter();
			new Throwable().printStackTrace(new PrintWriter(sw));
			logger.log(Level.WARNING, "CreateBusiness Page generated SQL Exception Error: " + sw.toString());
			String header1=getHeader("Internal Server Error") + getEndHeader();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/html");
			out.println(header1);
			String body1=getBodyWelcomeNavigationBar("Register New Business!", "Home", UserInfo.getCurrentUser(request).getUser()) + "<div class=\"alert alert-danger container\"><strong><center>Exception generated in the server. Sorry for the inconvenience. Please try after some time.</strong></center></div>";
			out.println(footer(body1));
			
			return;
		}
	}
}
