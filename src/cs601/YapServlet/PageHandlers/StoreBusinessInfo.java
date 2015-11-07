package cs601.YapServlet.PageHandlers;

import java.util.ArrayList;
import java.util.Collections;
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
	private static HashMap<String, BusinessInfo> busInfo;
	private final int totalDisplayBusiness=10;
	private static int page=1;
	private static String sorting="default";
	
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
	 * Returns the String representation of html body of the business Id
	 * @return String
	 */
	public synchronized String listHtmlBusinessId(String reviews)
	{
		StringBuilder htmlReturn=new StringBuilder();
		BusinessInfo obj=null;
		for(BusinessInfo bus:busInfo.values())
		{
			obj=(BusinessInfo)bus;
		}
		if(reviews.equals(""))
		{	
			htmlReturn.append(obj.listHtmlBusinessId());
		}
		else
		{
			htmlReturn.append(obj.listHtmlBusinessId(reviews));
		}
			
		return htmlReturn.toString();
	}
	
	/**
	 * Returns the String representation of html body of all business informations
	 * @return String
	 */
	public synchronized String listToHtml(String sorting, int page)
	{
		StringBuilder htmlReturn=new StringBuilder();
		ArrayList<BusinessInfo> businessInfo=new ArrayList<BusinessInfo>(busInfo.values());
		if(sorting.equals("alphaAscending"))
		{
			Collections.sort(businessInfo, BusinessInfo.ascBusinessName);
		}
		else
		{
			if(sorting.equals("ratingAscending"))
			{
				Collections.sort(businessInfo, BusinessInfo.ascAverageRating);
			}
			else if(sorting.equals("alphaDescending"))
			{
				Collections.sort(businessInfo, BusinessInfo.descBusinessName);
			}
			else if(sorting.equals("ratingDescending"))
			{
				Collections.sort(businessInfo, BusinessInfo.descAverageRating);
			}
		}
		StoreBusinessInfo.page=page;
		StoreBusinessInfo.sorting=sorting;
		int total=businessInfo.size();
		int maxCounter;
		int startIndex;
		double totalPages=(double) numberBusinessIds()/totalDisplayBusiness;
		if(page == Math.ceil(totalPages))
		{
			maxCounter=total-((page-1)*totalDisplayBusiness);
		}
		else
		{
			maxCounter=totalDisplayBusiness;
		}
		startIndex=((page-1)*totalDisplayBusiness);
		htmlReturn.append("<h4 style=\"color: darkred\"><strong>Best of Yap: ").append(total).append(" businesses found</strong></h4>");
		htmlReturn.append("<dl>");
		for(int i=0; i<maxCounter;i++)
		{
			htmlReturn.append(businessInfo.get(startIndex+i).listToHtml(startIndex+i+1));
		}
		htmlReturn.append("</dl>");	
		htmlReturn.append(pagination(page));
		
		return htmlReturn.toString();
	}
	
	/**
	 * Get the BusinessId stored for each business
	 * @return
	 */
	public synchronized String pagination(int pageActive)
	{
		StringBuilder paging=new StringBuilder(200);
		double totalPages=(double) numberBusinessIds()/totalDisplayBusiness;
		paging.append("<div style=\"overflow: auto\"><div class=\"text-center\"><ul class=\"pagination\">");
		for(int i=1;i<=Math.ceil(totalPages);i++)
		{
			if(i==pageActive)
			{
				paging.append("<li class=\"active\"><a href=\"/yap?page=").append(i).append("\">").append(i).append("</a></li>");
			}
			else
			{
				paging.append("<li><a href=\"/yap?page=").append(i).append("\">").append(i).append("</a></li>");	
			}
		}
		paging.append("</ul></div></div>");
		
		return paging.toString();
	}
	
	/**
	 * Get the number of BusinessInfo objects stored
	 * @return
	 */
	public synchronized static int numberBusinessIds()
	{
		return busInfo.size();
	}
	
	/**
	 * Get the active page stored
	 * @return int
	 */
	public synchronized static int getPage()
	{
		return page;
	}
	
	/**
	 * Get the active sorting stored
	 * @return int
	 */
	public synchronized static String getSorting()
	{
		return sorting;
	}
	
	/**
	 * Get the BusinessId stored for each business
	 * @return
	 */
	public synchronized static String businessId()
	{
		BusinessInfo obj=null;
		for(BusinessInfo bus:busInfo.values())
		{
			obj=(BusinessInfo)bus;
		}
		
		return obj.getBusinessId();
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
