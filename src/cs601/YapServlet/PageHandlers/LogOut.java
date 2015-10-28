package cs601.YapServlet.PageHandlers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class serves the purpose of LogOut button in Yap and accepts only GET requests.
 * @author Mayank
 *
 */
public class LogOut extends YapServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out=response.getWriter();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");
		String body="";
		if(UserInfo.getCurrentUser(request)!=null)
		{
			String header=getHeader("Signed Out From Yap") + getEndHeader();
			out.println(header);
			UserInfo.doLogOut(request);
			body=getBodyNavigationBar("Yap!", "Home") + "<div class=\"alert alert-success container\"><strong><center>You have been successfully signed out.</strong></center></div>";
			out.println(footer(body));
		}
		else
		{
			String header=getHeader("Yap!") + getEndHeader();
			out.println(header);
			body=getBodyNavigationBar("Yap!", "Home") + "<div class=\"alert alert-warning container\"><strong><center>You are not logged in. First log in to Yap.</strong></center></div>";
			out.println(footer(body));
		}
	}
}
