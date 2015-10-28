package cs601.YapServlet.PageHandlers;

import cs601.Yap.BaseServer.BaseServlet;

/**
 * This class extends the BaseServlet class to create the header tag appropriately for Yap.
 * @author Mayank
 *
 */
public class YapServlet extends BaseServlet  
{	
	/**
	 * It returns the String representation of html body for navigation header (if no user is logged in)
	 * @param String
	 * @param String
	 * @return String
	 */
	public String getBodyNavigationBar(String navBrand, String active)
	{
		String homeActive="";
		String aboutActive="";
		String contactActive="";
		String signUpActive="";
		String loginActive="";
		
		if(active.equals("Home"))
		{
			homeActive="active";
		}
		else if(active.equals("About"))
		{
			aboutActive="active";
		}
		else if(active.equals("Contact"))
		{
			contactActive="active";
		}
		else if(active.equals("SignUp"))
		{
			signUpActive="active";
		}
		else
		{
			loginActive="active";
		}
		String bodyNavBar="<nav class=\"navbar navbar-inverse\">"
				+ "<div class=\"container-fluid\">"
	    		+ "<div class=\"navbar-header\">"
	    			+ "<a class=\"navbar-brand\">" + navBrand + "</a>"
	    		+ "</div>"
	    		+ "<div>"
	    			+ "<ul class=\"nav navbar-nav\">"
	    				+ "<li class=" + homeActive + "><a href=\"/yap\">Home</a></li>"
	    				+ "<li class=" + aboutActive + "><a href=\"/about\">About Yap</a></li>"
	    				+ "<li class=" + contactActive + "><a href=\"/contact\">Contact Us</a></li>"
	    			+ "</ul>"
	    			+ "<ul class=\"nav navbar-nav navbar-right\">"
	    				+ "<li class=" + signUpActive + "><a href=\"/signup\"><span class=\"glyphicon glyphicon-user\"></span> Sign Up</a></li>"
	    				+ "<li class=" + loginActive + "><a href=\"/signin\"><span class=\"glyphicon glyphicon-log-in\"></span> Login</a></li>"
	    			+ "</ul>"
	    		+ "</div>"
	    	+ "</div>"	
	    + "</nav>";
		
		return bodyNavBar;
	}
	
	/**
	 * It returns the String representation of html body for navigation header (if user is logged in)
	 * @param String
	 * @param String
	 * @return String
	 */
	public String getBodyWelcomeNavigationBar(String navBrand, String active, String name)
	{
		String homeActive="";
		String aboutActive="";
		String contactActive="";
		String logoutActive="";
		String userActive="";
		
		if(active.equals("Home"))
		{
			homeActive="active";
		}
		else if(active.equals("About"))
		{
			aboutActive="active";
		}
		else if(active.equals("Contact"))
		{
			contactActive="active";
		}
		else if(active.equals("LogOut"))
		{
			logoutActive="active";
		}
		else
		{
			userActive="active";
		}
		String bodyNavBar="<nav class=\"navbar navbar-inverse\">"
				+ "<div class=\"container-fluid\">"
	    		+ "<div class=\"navbar-header\">"
	    			+ "<a class=\"navbar-brand\">" + navBrand + "</a>"
	    		+ "</div>"
	    		+ "<div>"
	    			+ "<ul class=\"nav navbar-nav\">"
	    				+ "<li class=" + homeActive + "><a href=\"/yap\">Home</a></li>"
	    				+ "<li class=" + aboutActive + "><a href=\"/about\">About Yap</a></li>"
	    				+ "<li class=" + contactActive + "><a href=\"/contact\">Contact Us</a></li>"
	    			+ "</ul>"
	    			+ "<ul class=\"nav navbar-nav navbar-right\">"
	    				+ "<li class=" + userActive + "><a class=\"dropdown-toggle\" data-toggle=\"dropdown\"><span class=\"glyphicon glyphicon-user\"></span>Welcome " + name +" <span class=\"caret\"></span></a>"
	    				+ "<ul class=\"dropdown-menu\"><li><a href=\"/myProfile\">My Profile</a></li></ul></li>"
	    				+ "<li class=" + logoutActive + "><a href=\"/logout\"><span class=\"glyphicon glyphicon-log-out\"></span> LogOut</a></li>"
	    			+ "</ul>"
	    		+ "</div>"
	    	+ "</div>"	
	    + "</nav>";
		
		return bodyNavBar;
	}
}
