package cs601.LoadDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cs601.Yap.BaseServer.JDBCConnection;

public class ReviewDatabase extends JDBCConnection
{	
	private final static String jdbcURL="jdbc:mysql://sql.cs.usfca.edu:3306/";
	private final static String user="user52";
	private final static String pass="user52";
	private final static String database="user52";
	
	public ReviewDatabase()
	{
		super(user, pass, database);
	}
	
	public static void main(String []args) throws SQLException 
	{
		//updateQuery();
		insertQuery();
	}

	public static void updateQuery() throws SQLException
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (Exception e) 
		{
			System.err.println("Can't find driver");
			System.exit(1);
		}
		String businessId[]={"107 West Restaurant-73.96807840.8027013",
				"Au Bon Pain-80.414437.2294",
				"Boiling Pot-118.13310834.139926",
				"Carolina Recycled Glass Jewelry-71.103259142.3647559",
				"Crown and Anchor Pub-97.735841630.2924131",
				"Cucina Italiana-75.18510839.954622",
				"Dana Dental Assoc Dent-71.10471742.364601",
				"James A. Cox, DDS-122.17609937.436861",
				"Little Kuts-118.13172234.1397033",
				"Neon Cactus-86.899851840.4220221",
				"No Radio Records-76.495000842.4406106",
				"Rice Coffeehouse-95.4022965929.71795382",
				"The Spot-117.720855734.1071402",
				"Uncle Funky's Daughter-95.41498729.7166717",
				"Walgreens-71.10342942.3655077",
				"White Chappell-21.56-1.56"};
		String userId[]={"benny813@outlook.com",
				"chan813@outlook.com",
				"mark130812@outlook.com",
				"mark130813@outlook.com",
				"mark1308@outlook.com",
				"mark137813@outlook.com",
				"mayank1312@gmail.com",
				"mthirani@dons.usfca.edu",
				"nis1213@gmail.com",
				"srollins@gmail.com",
				"sunil121991@gmail.com",
				"vibyrs813@hotmail.com","vibyrs813@hotmail.com","srollins@gmail.com"};
		String date[]={"2006-12-19",
				"2011-10-17",
				"2012-09-19",
				"2011-03-27",
				"2010-04-01",
				"2010-02-23",
				"2011-08-06",
				"2010-08-26",
				"2008-11-30",
				"2011-09-25",
				"2012-05-07",
				"2011-10-18",
				"2010-04-10",
				"2007-05-07"};
		String jdbcString=jdbcURL + database;
		Connection con=DriverManager.getConnection(jdbcString, user, pass);
		System.out.println("jdbcstring: " + jdbcString);
		System.out.println("jdbcstring: " + con);
		Statement stmt=con.createStatement();
		ResultSet result=stmt.executeQuery("select reviewId from reviewdataset;");
		ArrayList<String> al=new ArrayList<String>();
		int count=0;
		while(result.next())
		{
			al.add(result.getString("reviewId"));
		}
		for(String a: al)
		{
			String newString=businessId[count] + userId[count] + date[count];
			String sqlQuery="UPDATE reviewdataset SET reviewId=\"" + newString + "\" WHERE reviewId='" + a + "';";
			stmt.executeUpdate(sqlQuery);
			count++;
		}
		con.close();
	}
	
	public static void insertQuery() throws SQLException
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (Exception e) 
		{
			System.err.println("Can't find driver");
			System.exit(1);
		}
		String jdbcString=jdbcURL + database;
		Connection con=DriverManager.getConnection(jdbcString, user, pass);
		Statement stmt=con.createStatement();
		String businessId[]={"107 West Restaurant-73.96807840.8027013",
				"Au Bon Pain-80.414437.2294",
				"Boiling Pot-118.13310834.139926",
				"Carolina Recycled Glass Jewelry-71.103259142.3647559",
				"Crown and Anchor Pub-97.735841630.2924131",
				"Cucina Italiana-75.18510839.954622",
				"Dana Dental Assoc Dent-71.10471742.364601",
				"James A. Cox, DDS-122.17609937.436861",
				"Little Kuts-118.13172234.1397033",
				"Neon Cactus-86.899851840.4220221",
				"No Radio Records-76.495000842.4406106",
				"Rice Coffeehouse-95.4022965929.71795382",
				"The Spot-117.720855734.1071402",
				"Uncle Funky's Daughter-95.41498729.7166717",
				"Walgreens-71.10342942.3655077",
				"White Chappell-122.41883537.782732","Dana Dental Assoc Dent-71.10471742.364601","No Radio Records-76.495000842.4406106","Boiling Pot-118.13310834.139926"};
		String userId[]={"benny813@outlook.com",
				"chan813@outlook.com",
				"mark130812@outlook.com",
				"mark130813@outlook.com",
				"mark1308@outlook.com",
				"mark137813@outlook.com",
				"mayank1312@gmail.com",
				"mthirani@dons.usfca.edu",
				"nis1213@gmail.com",
				"srollins@gmail.com",
				"sunil121991@gmail.com","benny813@outlook.com","chan813@outlook.com",
				"vibyrs813@hotmail.com","vibyrs813@hotmail.com","srollins@gmail.com","sunil121991@gmail.com","mayank1312@gmail.com","nis1213@gmail.com"};
		String revwDate[]={"2006-12-19",
				"2011-10-17",
				"2012-09-19",
				"2011-03-27",
				"2010-04-01",
				"2010-02-23",
				"2011-08-06",
				"2010-08-26",
				"2009-11-30",
				"2011-09-25",
				"2012-05-07",
				"2011-10-18",
				"2010-04-10",
				"2009-08-09",
				"2014-11-11",
				"2015-05-07","2014-01-07","2013-02-09","2013-12-12"};
		int []stars={3,4,1,3,5,4,4,1,3,4,3,4,5,3,3,4,5,4,2};
		String review[]={"one waitress hooks it up by not asking for your ID",
				"It was so warm on Saturday day, my friend and I had a wonderful lunch",
				"If I could give this place NO Stars - I'd give it.",
				"The jewellery seems to be well recycled but the sharpness and brightness of the ornaments deceased.",
				"Wow, what a great ambience inside and outside as well :)",
				"Thanks to my girl, Mari J, for introducing me to this place",
				"The clinic has maintained it's dignity by having up-to-date equipments and is too clean.",
				"The courier service is too poor and even does not provide good response.",
				"I love the decor, nothing memorable. There's a bed and done!",
				"The highly trained professionals and cutting edge equipments made it fast for me.",
				"WOW this place is good. I went here a few months back also.",
				"I went to have breakfast with my family and we liked it.",
				"DAMN I really want to like this place more",
				"Beauty's is a bit anomalous in a world dominated by this place",
				"The men's selection could be a LITTLE better.",
				"I got a great restaurant cum bar near my house now and I really liked the environment and servicing.",
				"The medicines are at reasonable price and clinic also maintained highly qualified doctors",
				"The collections are not up to the pace with the market though I liked the interiors and environment :)",
				"I had a bad experince with this place."};
		for(int i=0; i<userId.length; i++)
		{
			String reviewId=businessId[i] + userId[i] + revwDate[i];
			String sqlQuery="INSERT INTO reviewdataset (funnyVotes,usefulVotes,coolVotes,userId,reviewId,stars,revwDate,review,businessId) VALUES(0,0,0,\"" + userId[i] + "\",\"" + reviewId + "\"," + stars[i] + ",\"" + revwDate[i] + "\",\"" + review[i] + "\",\"" + businessId[i] + "\");";
			stmt.executeUpdate(sqlQuery);
		}
		con.close();
	}
}