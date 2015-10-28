package cs601.YapServlet.PageHandlers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This class provides the mapping of each businessID to BusinessInfo object.
 * @author Mayank
 *
 */
public class StoreBusinessInfo 
{
	private HashMap<String, BusinessInfo> busInfo;
	
	public StoreBusinessInfo()
	{
		busInfo=new HashMap<String, BusinessInfo>();
	}
	
	/**
	 * It puts each Business to the HashMap
	 * @param String
	 * @param BusinessInfo
	 */
	public synchronized void addBusiness(String businessId, BusinessInfo businessData) 
	{
		busInfo.put(businessId, businessData);
	}
	
	/**
	 * Returns the String representation of html body of all business informations
	 * @return String
	 */
	public synchronized String listToHtml()
	{
		StringBuilder htmlReturn=new StringBuilder();
		int counter=0;
		int total=busInfo.size();
		htmlReturn.append("<h4 style=\"color: darkred\"><strong>Best of Yap: ").append(total).append(" businesses found</strong></h4>");
		htmlReturn.append("<dl>");
		counter=0;
		for (BusinessInfo obj : busInfo.values()) 
		{
			htmlReturn.append(obj.listToHtml(counter+1));
		    counter++;
		}
		htmlReturn.append("</dl>");
		
		return htmlReturn.toString();
	}
	
	/**
	 * Returns the StoreBusinessInfo object if HttpSession stores in it
	 * @param HttpServletRequest
	 * @return StoreBusinessInfo
	 */
	public static synchronized StoreBusinessInfo getBusinessInfo(HttpServletRequest req)
	{
		HttpSession sess=req.getSession(false);
		if(sess==null)
		{
			return null;
		}
		
		return ((StoreBusinessInfo)sess.getAttribute("BUSINESS"));
	}
}
