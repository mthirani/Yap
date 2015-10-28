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
        Server server = new Server(8080);
        ServletContextHandler servhandler = new ServletContextHandler(ServletContextHandler.SESSIONS);        
        server.setHandler(servhandler);
        servhandler.addServlet(Home.class, "/yap");
        servhandler.addServlet(SignUp.class, "/signup");
        servhandler.addServlet(SignIn.class, "/signin");
        servhandler.addServlet(LogOut.class, "/logout");
        servhandler.addServlet(About.class, "/about");
        servhandler.addServlet(Contact.class, "/contact");
        servhandler.addServlet(MyProfile.class, "/myProfile");
        server.setHandler(servhandler);
        server.start();
        server.join();
	}
}
