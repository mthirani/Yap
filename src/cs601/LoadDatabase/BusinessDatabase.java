package cs601.LoadDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cs601.Yap.BaseServer.JDBCConnection;

public class BusinessDatabase extends JDBCConnection
{	
	private final static String jdbcURL="jdbc:mysql://sql.cs.usfca.edu:3306/";
	private final static String user="user52";
	private final static String pass="user52";
	private final static String database="user52";
	
	public BusinessDatabase()
	{
		super(user, pass, database);
	}
	
	public static void main(String []args) throws SQLException 
	{
		//updateQuery();
		//insertQuery();
		insertMoreBusinesses();
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
		String busType[]={"Restaurant", "Restaurant", "Restaurant", "Ornament", "Bar & Restaurant", "Restaurant", "Clinic", "Courier", "Salon", "Salon", "Music Station", "Coffee House", "Salon", "Salon", "Store", "Restaurant"};
		String jdbcString=jdbcURL + database;
		Connection con=DriverManager.getConnection(jdbcString, user, pass);
		Statement stmt=con.createStatement();
		ResultSet result=stmt.executeQuery("select * from businessdataset");
		ArrayList<String> al=new ArrayList<String>();
		ArrayList<String> al1=new ArrayList<String>();
		ArrayList<Double> al2=new ArrayList<Double>();
		ArrayList<Double> al3=new ArrayList<Double>();
		int count=0;
		while(result.next())
		{
			al.add(result.getString("businessId"));
			al1.add(result.getString("busName"));
			al2.add(result.getDouble("longitude"));
			al3.add(result.getDouble("latitude"));
		}
		for(String a: al)
		{
			String sqlQuery="UPDATE businessdataset SET busType=\"" + busType[count] + "\" WHERE businessId=\"" + a + "\";";
			count++;
			stmt.executeUpdate(sqlQuery);
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
		String []city={"New York",
				"Blacksburg",
				"Pasadena",
				"Cambridge",
				"Austin",
				"Philadelphia",
				"Cambridge",
				"Palo Alto",
				"Pasadena",
				"West Lafayette",
				"Ithaca",
				"Houston",
				"Claremont",
				"Houston",
				"Cambridge",
				"San Francisco"};
		String []neighbours={"Manhattan Valley","",
							"Pasadena",
							"Central Square",
							"University of Texas",
							"University City",
							"Central Square",
							"",
							"Pasadena",
							"",
							"",
							"West University",
							"",
							"West University",
							"Central Square",
							"University Of SanFrancisco"};
		double []longitude={-73.968078,-80.4144,-118.133108,-71.1032591,-97.7358416,-75.185108,-71.104717,-122.176099,-118.131722,-86.8998518,-76.4950008,-95.40229659,-117.7208557,-95.414987,-71.103429,-122.418835};
		String []state={"NY","VA","CA","MA","TX","PA","MA","CA","CA","IN","NY","TX","CA","TX","MA","CA"};
		double []latitude={40.8027013,37.2294,34.139926,42.3647559,30.2924131,39.954622,42.364601,37.436861,34.1397033,40.4220221,42.4406106,29.71795382,34.1071402,29.7166717,42.3655077,37.782732};
		String []address={"2787 Broadway Manhattan Valley New York, NY 10025",
				"Squires Student Center Virginia Tech Campus Blacks",
				"345 S Lake Ave Ste 101 Pasadena Pasadena, CA 91101",
				"P.O Box 390152 Central Square Cambridge, MA 02139",
				"2911 San Jacinto Blvd University of Texas Austin",
				"2955 Market St University City Philadelphia, PA 19234",
				"10 Magazine St Central Square Cambridge, MA 02139",
				"777 Welch Rd Ste H Palo Alto, CA 94304",
				"350 S Lake Ave Ste 104 Pasadena Pasadena, CA 91101",
				"360 Brown St West Lafayette, IN 47906",
				"312 E Seneca St Ithaca, NY 14853",
				"6100 Main St West University Houston, TX 77005",
				"435 W Foothill Blvd Claremont, CA 91711",
				"2428 Times Blvd West University Houston, TX 77005",
				"625 Massachusetts Ave Central Square Cambridge, MA",
				"640 Polk Street, CA 94121"};
		String busType[]={"Restaurant","Restaurant","Restaurant","Ornament","Bar & Restaurant",
				"Restaurant","Clinic","Courier","Salon","Salon","Music Station",
				"Coffee House","Salon","Salon","Store","Restaurant"};
		String busName[]={"107 West Restaurant",
				"Au Bon Pain",
				"Boiling Pot",
				"Carolina Recycled Glass Jewelry",
				"Crown and Anchor Pub",
				"Cucina Italiana",
				"Dana Dental Assoc Dent",
				"James A. Cox, DDS",
				"Little Kut",
				"Neon Cactus",
				"No Radio Records",
				"Rice Coffeehouse",
				"The Spot",
				"Uncle Funky's Daughter",
				"Walgreens",
				"White Chappell"};
		for(int i=0; i<busName.length; i++)
		{
			String businessId=busName[i]+longitude[i]+latitude[i];
			String sqlQuery="INSERT INTO businessdataset (businessId,city,busName,neighbours,longitude,state,latitude,address,busType) VALUES(\"" + businessId + "\",\"" + city[i] + "\",\"" + busName[i] + "\",\"" + neighbours[i] + "\"," + longitude[i] + ",\"" + state[i] + "\"," + latitude[i] + ",\"" + address[i] + "\",\"" + busType[i] + "\");";
			stmt.executeUpdate(sqlQuery);
		}
		con.close();
	}
	
	public static void insertMoreBusinesses() throws SQLException
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
		String []city={"San Diego",
				"New York",
				"La Jolla",
				"Chapel Hill",
				"Palo Alto",
				"New York"};
		String []neighbours={"","Harlem",
							"",
							"",
							"",
							"Morningside Heights"};
		double []longitude={-117.2146218,-73.9569,-117.236132,-79.0219388,-122.1610427,-73.96433258};
		String []state={"CA","NY","CA","NC","CA","NY"};
		double []latitude={32.8796717,40.802885,32.870735,35.9068246,37.4402229,40.8070335};
		String []address={"9555 Genesee Avenue San Diego, CA 92121",
				"2101 Frederick Douglass Blvd Harlem New York, NY",
				"8950 Villa La Jolla Dr Ste B214 La Jolla, CA 92037",
				"1114 Environ Way Chapel Hill, NC 27517",
				"795 El Camino Real Palo Alto, CA 94301",
				"2922 Broadway Morningside Heights New York,NY"};
		String busType[]={"Rental Apartments","Realty Traders","Clinic","Departmental Store","Clinic","Bookstore"};
		String busName[]={"Canyon Park Apartments",
				"Bohemia Realty Group",
				"Practical Recovery",
				"fab'rik",
				"McCaffrey Michael R MD",
				"Barnes and Noble - Columbia University Bookstore"};
		for(int i=0; i<busName.length; i++)
		{
			String businessId=busName[i]+longitude[i]+latitude[i];
			String sqlQuery="INSERT INTO businessdataset (businessId,city,busName,neighbours,longitude,state,latitude,address,busType) VALUES(\"" + businessId + "\",\"" + city[i] + "\",\"" + busName[i] + "\",\"" + neighbours[i] + "\"," + longitude[i] + ",\"" + state[i] + "\"," + latitude[i] + ",\"" + address[i] + "\",\"" + busType[i] + "\");";
			stmt.executeUpdate(sqlQuery);
		}
		con.close();
	}
}