package cs601.Yap.BaseServer;

import java.io.IOException;
import javax.servlet.http.HttpServlet;

/**
 * This is the base servlet class which can be extended by any another servlet class to start with html header and encapsulate body.
 * @author Mayank
 *
 */
public class BaseServlet extends HttpServlet 
{	
	/**
	 * Creates a base header tag
	 * @param String
	 * @return String
	 * @throws IOException
	 */
	public String getHeader(String title) throws IOException 
	{	
		String createHeader="<!DOCTYPE html><html lang=\"en\"><head><title>" + title + "</title>"
				+ "<meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
				+ "<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\">"
				+ "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>"
				+ "<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script>";
		
		return createHeader;
	}
	
	/**
	 * Returns the end tag for header
	 * @return String
	 */
	public String getEndHeader()
	{		
		return "</head>";
	}
	
	/**
	 * Encapsulates the body created by any another servlet and marks the end of html
	 * @param String
	 * @return String
	 */
	public String footer(String body) 
	{
		String htmlBody="<body>" + body + "</body></html>";
		return htmlBody;
	}
}
