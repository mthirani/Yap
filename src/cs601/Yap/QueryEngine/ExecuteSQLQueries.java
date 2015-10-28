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
	private final String jdbcURL="jdbc:mysql://localhost:3306/";
	private final static String user="mthirani";
	private final static String pass="#Rpgsmn421#";
	private final static String database="yap";
	
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
	 * Returns true or false depending on the user information stored in database or not
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
	public synchronized StoreBusinessInfo getQueryBusinesses(String sqlQuery) throws SQLException
	{
		StoreBusinessInfo busInfo=new StoreBusinessInfo();
		ArrayList<BusinessInfo> businesses=new ArrayList<BusinessInfo>();
		Connection con=connection(jdbcURL);
		Statement stmt=con.createStatement();
		ResultSet result=stmt.executeQuery(sqlQuery);
		try
		{			
			while(result.next())
			{
				businesses.add(new BusinessInfo(result.getString("busName"), result.getString("busType"), result.getString("businessId"), result.getString("city"), result.getString("state"), result.getString("address")));
			}
			ArrayList<String> users=new ArrayList<String>();
			ArrayList<String> reviews=new ArrayList<String>();
			for(BusinessInfo busStore: businesses)
			{
				String queryBusId="SELECT AVG(stars) FROM reviewdataset WHERE businessId=\"" + busStore.getBusinessId() + "\";";
				Statement stmt1=con.createStatement();
				ResultSet result1=stmt1.executeQuery(queryBusId);
				result1.next();
				double avgRating=result1.getDouble(1);
				queryBusId="SELECT review,userName,stars FROM reviewdataset,userdataset WHERE businessId=\"" + busStore.getBusinessId() + "\" AND userdataset.userId=reviewdataset.userId;";
				Statement stmt2=con.createStatement();
				ResultSet result2=stmt2.executeQuery(queryBusId);
				if(result2.next())
				{
					users.add(result2.getString("userName"));
					reviews.add(result2.getString("review"));
					while(result2.next())
					{
						users.add(result2.getString("userName"));
						reviews.add(result2.getString("review"));
					}
				}
				busStore.addAvgRating(avgRating);
				busStore.addReviews(reviews);
				busStore.addUsers(users);
				busInfo.addBusiness(busStore.getBusinessId(), busStore);
				users.clear();
				reviews.clear();
			}
			
			return busInfo;
			
		}finally
		{
			con.close();
		}	
	}
}