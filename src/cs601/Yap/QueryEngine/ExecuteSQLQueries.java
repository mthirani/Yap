package cs601.Yap.QueryEngine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import cs601.Yap.BaseServer.JDBCConnection;
import cs601.YapServlet.PageHandlers.BusinessInfo;
import cs601.YapServlet.PageHandlers.StoreBusinessInfo;
import cs601.YapServlet.PageHandlers.UserInfo;

/**
 * This class extends the base JDBCConnection class for executing different SQL queries.
 * @author Mayank
 *
 */
public class ExecuteSQLQueries extends JDBCConnection
{	
	private final String jdbcURL="jdbc:mysql://sql.cs.usfca.edu:3306/";	//localhost
	private final static String user="user52";							//mthirani
	private final static String pass="user52";							//#Rpgsmn421#
	private final static String database="user52";						//yap
	
	public ExecuteSQLQueries()
	{
		super(user, pass, database);
	}
	
	/**
	 * Updates the information in database
	 * @param String
	 * @throws SQLException
	 */
	public synchronized void updateQuery(String sqlQuery) throws SQLException 
	{
		Connection con=connection(jdbcURL);
		Statement stmt=con.createStatement();
		stmt.executeUpdate(sqlQuery);
		con.close();
	}
	
	/**
	 * Returns the user Id for the user information stored in database
	 * @param String
	 * @return String
	 * @throws SQLException
	 */
	public synchronized String getUserId(String sqlQuery) throws SQLException
	{
		Connection con=connection(jdbcURL);
		Statement stmt=con.createStatement();
		ResultSet result=stmt.executeQuery(sqlQuery);
		try
		{
			if(result.next())
			{
				return result.getString("userId");
			}
			else
			{
				return null;
			}
		}finally
		{
			con.close();
		}	
	}
	
	/**
	 * Returns true or false depending on the information stored in database or not
	 * @param String
	 * @return boolean
	 * @throws SQLException
	 */
	public synchronized boolean getQuery(String sqlQuery) throws SQLException
	{
		Connection con=connection(jdbcURL);
		Statement stmt=con.createStatement();
		ResultSet result=stmt.executeQuery(sqlQuery);
		try
		{
			if(!result.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}finally
		{
			con.close();
		}		
	}
	
	/**
	 * Extracts all the required users information
	 * @param String
	 * @param String
	 * @return UserInfo
	 * @throws SQLException
	 */
	public synchronized UserInfo getQuery(String sqlQuery, String str1) throws SQLException
	{
		Connection con=connection(jdbcURL);
		Statement stmt=con.createStatement();
		ResultSet result=stmt.executeQuery(sqlQuery);
		try
		{
			if(!result.next())
			{
				return null;
			}
			else
			{
				if(result.getString("password").equals(str1))
				{
					String user=result.getString("userName");
					String userId=result.getString("userId");
					Integer funnyVotes=result.getInt("funnyVotes");
					Integer usefulVotes=result.getInt("usefulVotes");
					Integer coolVotes=result.getInt("coolVotes");
					String address=result.getString("address");
					String name=result.getString("name");
					return new UserInfo(user, userId, funnyVotes, usefulVotes, coolVotes, address, name);
				}
				return null;
			}
		}finally
		{
			con.close();
		}	
	}
	
	/**
	 * Extracts all the required business informations and maps them to user names appropriately
	 * @param String
	 * @return StoreBusinessInfo
	 * @throws SQLException
	 */
	public synchronized StoreBusinessInfo getQueryBusinesses(String sqlQuery, String review) throws SQLException
	{
		StoreBusinessInfo busInfo=new StoreBusinessInfo();
		ArrayList<BusinessInfo> businesses=new ArrayList<BusinessInfo>();
		Connection con=connection(jdbcURL);
		Statement stmt=con.createStatement();
		ResultSet result=stmt.executeQuery(sqlQuery);
		if(result.next())
		{
			result.previous();
		}
		else
		{
			return null;
		}
		try
		{			
			while(result.next())
			{
				businesses.add(new BusinessInfo(result.getString("busName"), result.getString("busType"), result.getString("businessId"), result.getString("city"), result.getString("state"), result.getString("address"), result.getDouble("latitude"), result.getDouble("longitude")));
			}
			ArrayList<String> users=new ArrayList<String>();
			ArrayList<String> reviews=new ArrayList<String>();
			ArrayList<String> revwDates=new ArrayList<String>();
			ArrayList<Integer> stars=new ArrayList<Integer>();
			for(BusinessInfo busStore: businesses)
			{
				String queryBusId="SELECT AVG(stars) FROM reviewdataset WHERE businessId=\"" + busStore.getBusinessId() + "\";";
				Statement stmt1=con.createStatement();
				ResultSet result1=stmt1.executeQuery(queryBusId);
				result1.next();
				double avgRating=result1.getDouble(1);
				if(review.equals(""))
				{
					queryBusId="SELECT review,userName,stars,revwDate FROM reviewdataset,userdataset WHERE businessId=\"" + busStore.getBusinessId() + "\" AND userdataset.userId=reviewdataset.userId;";
				}
				else
				{
					queryBusId="SELECT review,userName,stars,revwDate FROM reviewdataset,userdataset WHERE businessId=\"" + busStore.getBusinessId() + "\" AND review like \"%" + review + "%\" AND userdataset.userId=reviewdataset.userId;";
				}
				Statement stmt2=con.createStatement();
				ResultSet result2=stmt2.executeQuery(queryBusId);
				if(result2.next())
				{
					users.add(result2.getString("userName"));
					reviews.add(result2.getString("review"));
					revwDates.add(result2.getString("revwDate"));
					stars.add(result2.getInt("stars"));
					while(result2.next())
					{
						users.add(result2.getString("userName"));
						reviews.add(result2.getString("review"));
						revwDates.add(result2.getString("revwDate"));
						stars.add(result2.getInt("stars"));
					}
				}
				
				busStore.addAvgRating(avgRating);
				busStore.addReviews(reviews);
				busStore.addUsers(users);
				busStore.addRating(stars);
				busStore.addReviewDate(revwDates);
				busInfo.addBusiness(busStore.getBusinessId(), busStore);
				users.clear();
				reviews.clear();
				stars.clear();
				revwDates.clear();
			}
			
			return busInfo;
			
		}finally
		{
			con.close();
		}	
	}
}