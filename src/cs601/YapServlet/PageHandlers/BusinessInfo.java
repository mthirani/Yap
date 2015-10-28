package cs601.YapServlet.PageHandlers;

import java.util.ArrayList;

/**
 * This class stores the business information along with reviews/ username mapped to businessId.
 * @author Mayank
 *
 */
public class BusinessInfo
{
	private String busName;
	private String busType;
	private String city;
	private String state;
	private ArrayList<String> userName;
	private Double avgRating;
	private ArrayList<String> review;
	private String businessId;
	private String address;
	
	public BusinessInfo(String busName, String busType, String businessId, String city, String state, String address)
	{
		this.busName=busName;
		this.busType=busType;
		this.businessId=businessId;
		this.userName=new ArrayList<String>();
		this.review=new ArrayList<String>();
		this.avgRating=0.0;
		this.city=city;
		this.state=state;
		this.address=address;
	}
	
	/**
	 * Add the average rating
	 * @param Double
	 */
	public synchronized void addAvgRating(Double avgRating)
	{
		this.avgRating=avgRating;
	}
	
	/**
	 * Add all the reviews of each business
	 * @param ArrayList
	 */
	public synchronized void addReviews(ArrayList<String> review)
	{
		this.review.addAll(review);
	}
	
	/**
	 * Add all the user names of each business
	 * @param ArrayList
	 */
	public synchronized void addUsers(ArrayList<String> userName)
	{
		this.userName.addAll(userName);
	}
	
	/**
	 * Returns the address of business
	 * @return String
	 */
	public synchronized String getAddress()
	{
		return address;
	}
	
	/**
	 * Returns the city of business
	 * @return String
	 */
	public synchronized String getCity()
	{
		return city;
	}
	
	/**
	 * Returns the state of business
	 * @return String
	 */
	public synchronized String getState()
	{
		return state;
	}
	
	/**
	 * Returns the ArrayList of Reviews stored in each business
	 * @return ArrayList
	 */
	public synchronized ArrayList<String> getReviews()
	{
		return review;
	}
	
	/**
	 * Returns the ArrayList of Users stored in each business
	 * @return ArrayList
	 */
	public synchronized ArrayList<String> getUsers()
	{
		return userName;
	}
	
	/**
	 * Returns the average rating of business
	 * @return Double
	 */
	public synchronized Double getAvgRating()
	{
		return avgRating;
	}
	
	/**
	 * Returns the businessId
	 * @return String
	 */
	public synchronized String getBusinessId()
	{
		return businessId;
	}
	
	/**
	 * Returns the business name
	 * @return String
	 */
	public synchronized String getBusName()
	{
		return busName;
	}
	
	/**
	 * Returns the business type
	 * @return String
	 */
	public synchronized String getBusType()
	{
		return busType;
	}
	
	/**
	 * Returns the string represenation of a html body for each business
	 * @param Integer
	 * @return String
	 */
	public synchronized String listToHtml(int counter) 
	{	
		StringBuilder builder = new StringBuilder(500); 
		builder.append("<dt><h5>").append(counter).append(". <a href=\"\" title=\"").append(getBusName()).append(" (").append(getUsers().size()).append(" review)").append("\" data-toggle=\"popover\" data-trigger=\"hover\" data-content=\"").append(getAddress()).append("\" target=\"_parent\">").append(getBusName()).append(" (").append(getCity()).append(", ").append(getState()).append(") ").append(" - ").append(getBusType()).append("</a></h5></dt>");
		builder.append("<mark><span style=\"color:darkred\">Average Rating: ").append(getAvgRating()).append(" (out of 5)</span></mark></br>");
		if(getUsers().size() > 0)
		{
			builder.append("</p>");
			builder.append("<dd>").append(getUsers().get(0)).append(" - ").append(getReviews().get(0)).append("</dd>");
		}
		builder.append("</br>");
		
		return builder.toString();
	}
}
