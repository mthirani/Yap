package cs601.YapServlet.PageHandlers;

import java.util.ArrayList;
import java.util.Comparator;

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
	private ArrayList<Integer> stars;
	private ArrayList<String> reviewDate;
	private Double avgRating;
	private Double lat;
	private Double lon;
	private ArrayList<String> review;
	private String businessId;
	private String address;
	
	public BusinessInfo(String busName, String busType, String businessId, String city, String state, String address, Double lat, Double lon)
	{
		this.busName=busName;
		this.busType=busType;
		this.businessId=businessId;
		this.userName=new ArrayList<String>();
		this.review=new ArrayList<String>();
		this.reviewDate=new ArrayList<String>();
		this.stars=new ArrayList<Integer>();
		this.avgRating=0.0;
		this.city=city;
		this.state=state;
		this.address=address;
		this.lat=lat;
		this.lon=lon;
	}
	
	/**
	 * This sorts the business info objects by business name in ascending order
	 */
	public static Comparator<BusinessInfo> ascBusinessName=new Comparator<BusinessInfo>()
	{
		public	int compare(BusinessInfo obj1, BusinessInfo obj2)
		{
			return obj1.getBusName().compareToIgnoreCase(obj2.getBusName());
		}
	};
	
	/**
	 * This sorts the business info objects by business name in descending order
	 */
	public static Comparator<BusinessInfo> descBusinessName=new Comparator<BusinessInfo>()
	{
		public	int compare(BusinessInfo obj1, BusinessInfo obj2)
		{
			return obj2.getBusName().compareToIgnoreCase(obj1.getBusName());
		}
	};
	
	/**
	 * This sorts the business info objects by average rating in ascending order
	 */
	public static Comparator<BusinessInfo> ascAverageRating=new Comparator<BusinessInfo>()
	{
		public	int compare(BusinessInfo obj1, BusinessInfo obj2)
		{
			return Double.compare(obj1.avgRating, obj2.avgRating);
		}
	};
	
	/**
	 * This sorts the business info objects by average rating in descending order
	 */
	public static Comparator<BusinessInfo> descAverageRating=new Comparator<BusinessInfo>()
	{
		public	int compare(BusinessInfo obj1, BusinessInfo obj2)
		{
			return Double.compare(obj2.avgRating, obj1.avgRating);
		}
	};
	
	/**
	 * Add the reviewDate for each user
	 * @param ArrayList
	 */
	public synchronized void addReviewDate(ArrayList<String> revwDate)
	{
		this.reviewDate.addAll(revwDate);
	}
	
	/**
	 * Add the ratings for each user
	 * @param ArrayList
	 */
	public synchronized void addRating(ArrayList<Integer> ratings)
	{
		this.stars.addAll(ratings);
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
	 * Returns the review dates
	 * @return ArrayList
	 */
	public synchronized ArrayList<String> getReviewDates()
	{
		return reviewDate;
	}
	
	/**
	 * Returns the ratings of each user
	 * @return ArrayList
	 */
	public synchronized ArrayList<Integer> getRatings()
	{
		return stars;
	}
	
	/**
	 * Returns the latitude of business
	 * @return Double
	 */
	public synchronized Double getLat()
	{
		return lat;
	}
	
	/**
	 * Returns the longitude of business
	 * @return Double
	 */
	public synchronized Double getLon()
	{
		return lon;
	}
	
	/**
	 * Returns the string represenation of a html body (only the header of the business) for a specific businessId
	 * Google Map Script in this fucntion is copyright from "https://developers.google.com/maps/documentation/javascript/examples/event-simple"
	 * @return String
	 */
	public synchronized String getHtmlBusinessId()
	{
		StringBuilder builder = new StringBuilder(500);
		builder.append("<div class=\"well\"><div class=\"container\"><h4><strong>").append(getBusName()).append(" (").append(getCity()).append(", ").append(getState()).append(") ").append("</strong></h4>");
		builder.append("</p>");
		builder.append("<mark><span style=\"color:darkred\">Average Rating: ").append(getAvgRating()).append(" (out of 5) </span></mark>");
		builder.append(getUsers().size()).append(" Reviews");
		builder.append("</p>");
		builder.append("$ . ").append("<a href=\"\" data-trigger=\"focus\">").append(getBusType()).append("</a>");
		builder.append("</p>");
		builder.append("<div id=\"map\"></div><script>function initMap(){var myLatlng = {lat: ").append(getLat()).append(", lng: ").append(getLon()).append("};var title = \"").append(getAddress()).append("\";var mapCanvas = document.getElementById(\"map\");var mapOptions = {center: myLatlng,zoom: 12,mapTypeId: google.maps.MapTypeId.ROADMAP};var map = new google.maps.Map(mapCanvas, mapOptions);var marker = new google.maps.Marker({position: myLatlng,map: map,title: title});marker.addListener('click', function(){map.setZoom(15);map.setCenter(marker.getPosition());});}google.maps.event.addDomListener(window, 'load', initMap);</script>");
		builder.append("<strong>").append(getAddress()).append("</strong>");
		builder.append("</p>");
		builder.append("<a href=\"https://maps.google.com\" data-trigger=\"focus\">").append("Get Directions").append("</a>");
		builder.append("</p>");
		builder.append("</div></div>");
		builder.append("<div class=\"container\">");
		builder.append("<button type=\"button\" class=\"btn btn-danger btn-sg\" data-toggle=\"modal\" data-target=\"#myModal\"><span class=\"glyphicon glyphicon-star\"></span> Write A Review</button>");
		builder.append("</p>");
		builder.append("<div class=\"modal fade\" id=\"myModal\" role=\"dialog\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button type\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button><h4 class=\"modal-title\"><div style=\"color:darkred\"><strong>Post your review</strong></div></h4></div><form class=\"form-horizontal\" name=\"reviewsection\" role=\"form\" action=\"/postReview\" method=\"POST\"><div class=\"modal-body\"><strong>Review: </strong><textarea class=\"form-control\" rows=\"4\" id=\"comment\" name=\"comment\" placeholder=\"Enter your comments (should be within 200 characters)\" maxlength=\"200\" required></textarea><br/><strong>Rating: </strong><select class=\"form-control\" name=\"rating\" id=\"rating\" required><option value=\"1\">1 (I Hate it)</option><option value=\"2\">2 (Not upto the mark)</option><option value=\"3\">3 (Ahh Ok)</option><option value=\"4\">4 (Nice to have this place!)</option><option value=\"5\">5 (Great! Will visit soon)</option></select></div><div class=\"modal-footer\"><div class=\"col-sm-1\"><button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button></div><div class=\"col-sm-11\"><button type=\"submit\" class=\"btn btn-danger\">Post</button></div></div></form></div></div></div>");
		
		return builder.toString();
	}
	
	/**
	 * Returns the string represenation of a html body (only the reviews section) for a specific businessId with no filtering results
	 * @return String
	 */
	public synchronized String listHtmlBusinessId()
	{
		StringBuilder builder = new StringBuilder(500);
		builder.append(getHtmlBusinessId());
		if(getUsers().size() > 0)
		{
			int counter=0;
			builder.append("<div class=\"row\"><div class=\"col-sm-4\"><h4 class='text-danger'><strong>Recommended Reviews</strong></h4></div><form class=\"form-horizontal\" role=\"form\" action=\"/yapReviews\" method=\"GET\"><div class=\"form-group\"><div class=\"col-sm-3\"><input class=\"form-control\" id=\"focusedInput\" type=\"text\" placeholder=\"Find By Review Phrases\" name=\"search\" required></div><button type=\"submit\" class=\"btn btn-danger\"><span class=\"glyphicon glyphicon-search\"></span></button></div></form></div>");
			builder.append("<table class=\"table table-striped\"><tbody>");
			for(String users: getUsers())
			{
				builder.append("<tr>");
				builder.append("<td style=\"color: darkblue\"><strong>").append(users).append("</strong></td>");
				builder.append("<td>Rating: ");
				for(int i=1;i<=getRatings().get(counter);i++)
				{
					builder.append("<span class=\"glyphicon glyphicon-star\"></span>");
				}
				builder.append(" on <i><strong>").append(getReviewDates().get(counter)).append("</strong></i></p>").append(getReviews().get(counter)).append("</td>");
				builder.append("</tr>");
				counter++;
			}
		}
		else
		{
			builder.append("<div class=\"alert alert-warning container\"><strong><center>There are no reviews yet for this business. Be the first one to review!</center></strong></div>");
		}
		builder.append("</div>");
		
		return builder.toString();
	}
	
	/**
	 * Returns the string represenation of a html body (only the reviews section) for a specific businessId with filtering results
	 * @return String
	 */
	public synchronized String listHtmlBusinessId(String phrase)
	{
		StringBuilder builder = new StringBuilder(500);
		builder.append(getHtmlBusinessId());
		int counter=0;
		builder.append("<div class=\"row\"><div class=\"col-sm-4\"><h4 class='text-danger'><strong>Recommended Reviews</strong></h4></div><form class=\"form-horizontal\" role=\"form\" action=\"/yapReviews\" method=\"GET\"><div class=\"form-group\"><div class=\"col-sm-3\"><input class=\"form-control\" id=\"focusedInput\" type=\"text\" value=\"").append(phrase).append("\" name=\"search\" required></div><button type=\"submit\" class=\"btn btn-danger\"><span class=\"glyphicon glyphicon-search\"></span></button></div></form></div>");
		builder.append("<div class=\"row\"><div class=\"col-sm-4\"><h5 class='text-danger'><strong>").append(getUsers().size()).append(" reviews mentioning \"").append(phrase).append("\"</strong></h5></div><div class=\"col-sm-3\"><h5><a href=\"/yap?businessId=").append(getBusinessId()).append("\">Clear Results <span class=\"glyphicon glyphicon-search\"></span></a></h5></div></div>");
		builder.append("<table class=\"table table-striped\"><tbody>");
		for(String users: getUsers())
		{
			builder.append("<tr>");
			builder.append("<td style=\"color: darkblue\"><strong>").append(users).append("</strong></td>");
			builder.append("<td>Rating: ");
			for(int i=1;i<=getRatings().get(counter);i++)
			{
				builder.append("<span class=\"glyphicon glyphicon-star\"></span>");
			}
			builder.append(" on <i><strong>").append(getReviewDates().get(counter)).append("</strong></i></p>").append(getReviews().get(counter)).append("</td>");
			builder.append("</tr>");
			counter++;
		}
		builder.append("</div>");
		
		return builder.toString();
	}
	
	/**
	 * Returns the string represenation of a html body for each business for the Home page
	 * @param Integer
	 * @return String
	 */
	public synchronized String listToHtml(int counter) 
	{	
		StringBuilder builder = new StringBuilder(500); 
		builder.append("<dt><h5>").append(counter).append(". <a href=\"").append("/yap?businessId=").append(getBusinessId()).append("\" title=\"").append(getBusName()).append(" (").append(getUsers().size()).append(" review)").append("\" data-toggle=\"popover\" data-trigger=\"hover\" data-content=\"").append(getAddress()).append("\" target=\"_parent\">").append(getBusName()).append(" (").append(getCity()).append(", ").append(getState()).append(") ").append(" - ").append(getBusType()).append("</a></h5></dt>");
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
