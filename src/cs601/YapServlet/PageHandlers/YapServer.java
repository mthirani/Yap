package cs601.YapServlet.PageHandlers;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * This class holds the mapping of each page to it's servlets and contains main() to start the server
 * @author Mayank
 *
 */
public class YapServer
{
	public static void main(String[] args) throws Exception 
	{
		if(args.length==1)
		{
			Server server = new Server(Integer.parseInt(args[0]));
	        ServletContextHandler servhandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
	        servhandler.addServlet(Home.class, "/yap");
	        servhandler.addServlet(SignUp.class, "/signup");
	        servhandler.addServlet(SignIn.class, "/signin");
	        servhandler.addServlet(LogOut.class, "/logout");
	        servhandler.addServlet(About.class, "/about");
	        servhandler.addServlet(Contact.class, "/contact");
	        servhandler.addServlet(MyProfile.class, "/myProfile");
	        servhandler.addServlet(Reviews.class, "/yapReviews");
	        servhandler.addServlet(CreateBusiness.class, "/createBusiness");
	        servhandler.addServlet(WriteReview.class, "/postReview");
	        server.setHandler(servhandler);
	        server.start();
	        server.join();
		}
		else
		{
			System.err.println("Please pass the server port number in the command line argument; For example: java -jar YapServer.jar 10000, where 10000 is the <Port>");
		}
	}
}
