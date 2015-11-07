package cs601.YapServlet.PageHandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This class stores the user's related information.
 * @author Mayank
 *
 */
public class UserInfo 
{
	private String username;
	private String email;
	private Integer funny;
	private Integer useful;
	private Integer cool;
	private String address;
	private String name;
	
	public UserInfo(String user, String email, Integer funny, Integer useful, Integer cool, String address, String name)
	{
		this.username=user;
		this.email=email;
		this.funny=funny;
		this.useful=useful;
		this.cool=cool;
		this.address=address;
		this.name=name;
	}
	
	/**
	 * Updates the full name of the user
	 */
	public synchronized void updateName(String fullname)
	{
		name=fullname;
	}
	
	/**
	 * Returns the name of the user
	 * @return String
	 */
	public synchronized String getName()
	{
		return name;
	}
	
	/**
	 * Updates the address of the user
	 */
	public synchronized void updateAddress(String adrs)
	{
		address=adrs;
	}
	
	/**
	 * Returns the address of the user
	 * @return String
	 */
	public synchronized String getAddress()
	{
		return address;
	}
	
	/**
	 * Updates the user name of the user
	 */
	public synchronized void updateUser(String user)
	{
		username=user;
	}
	
	/**
	 * Returns the user name of the user
	 * @return String
	 */
	public synchronized String getUser()
	{
		return username;
	}
	
	/**
	 * Updates the email of the user
	 */
	public synchronized void updateEmail(String emailId)
	{
		email=emailId;
	}
	
	/**
	 * Returns the e-mail of the user
	 * @return String
	 */
	public synchronized String getEmail()
	{
		return email;
	}
	
	/**
	 * Returns the number of funny votes stored
	 * @return Integer
	 */
	public synchronized Integer getFunny()
	{
		return funny;
	}
	
	/**
	 * Returns the number of useful votes stored
	 * @return Integer
	 */
	public synchronized Integer getUseful()
	{
		return useful;
	}
	
	/**
	 * Returns the number of cool votes stored
	 * @return Integer
	 */
	public synchronized Integer getCool()
	{
		return cool;
	}
	
	/**
	 * Returns the UserInfo object if HttpSession stores in it
	 * The below session validation code in this function is copyright from "http://www.javamex.com/tutorials/servlets/session_api.shtml"
	 * @param HttpServletRequest
	 * @return UserInfo
	 */
	public static synchronized UserInfo getCurrentUser(HttpServletRequest req)
	{
		HttpSession sess=req.getSession(false);
		if (sess==null)
		{
			return null;
		}
		return ((UserInfo)sess.getAttribute("USER"));
	}
	
	/**
	 * Make the session terminate for the appropriate user
	 * @param HttpServletRequest
	 */
	public static synchronized void doLogOut(HttpServletRequest req)
	{
		HttpSession sess=req.getSession(false);
		if (sess!=null)
		{
			sess.invalidate();
		}
	}
}
