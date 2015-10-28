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
	private final static String jdbcURL="jdbc:mysql://localhost:3306/";
	private final static String user="mthirani";
	private final static String pass="#Rpgsmn421#";
	private final static String database="yap";
	
	public BusinessDatabase()
	{
		super(user, pass, database);
	}
	
	public static void main(String []args) throws SQLException 
	{
		updateQuery();
		//insertQuery();
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
		String address[]={"350 S Lake Ave Ste 104 Pasadena Pasadena, CA 91101",
				"435 W Foothill Blvd Claremont, CA 91711",
				"10 Magazine St Central Square Cambridge, MA 02139",
				"2787 Broadway Manhattan Valley New York, NY 10025",
				"777 Welch Rd Ste H Palo Alto, CA 94304",
				"6100 Main St West University Houston, TX 77005",
				"625 Massachusetts Ave Central Square Cambridge, MA",
				"312 E Seneca St Ithaca, NY 14853",
				"2911 San Jacinto Blvd University of Texas Austin",
				"345 S Lake Ave Ste 101 Pasadena Pasadena, CA 91101",
				"360 Brown St West Lafayette, IN 47906",
				"Squires Student Center Virginia Tech Campus Blacks",
				"2428 Times Blvd West University Houston, TX 77005",
				"P.O Box 390152 Central Square Cambridge, MA 02139",
				"2955 Market St University City Philadelphia, PA 19234"};
		String busType[]={"Restaurant", "Restaurant", "Restaurant", "Ornament", "Bar & Restaurant", "Restaurant", "Clinic", "Courier", "Salon", "Salon", "Music Station", "Coffee House", "Salon", "Salon", "Store", "Restaurant"};
		String jdbcString="jdbc:mysql://localhost:3306/" + database;
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
			//String busId=al1.get(count)+al2.get(count)+al3.get(count);
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
		String jdbcString="jdbc:mysql://localhost:3306/" + database;
		Connection con=DriverManager.getConnection(jdbcString, user, pass);
		Statement stmt=con.createStatement();
		String []city={"San Francisco"};
		String []busName={"White Chappell"};
		String []neighbours={"University Of SanFrancisco"};
		double []longitude={-21.56};
		String []state={"CA"};
		double []latitude={-1.56};
		String []address={"640 Polk Street, CA 94121"};
		String busType[]={"Restaurant"};
		for(int i=0; i<busName.length; i++)
		{
			String businessId=busName[i]+longitude[i]+latitude[i];
			String sqlQuery="INSERT INTO BusinessDataSet (businessId,city,busName,neighbours,longitude,state,latitude,address,busType) VALUES(\"" + businessId + "\",\"" + city[i] + "\",\"" + busName[i] + "\",\"" + neighbours[i] + "\"," + longitude[i] + ",\"" + state[i] + "\"," + latitude[i] + ",\"" + address[i] + "\",\"" + busType[i] + "\");";
			stmt.executeUpdate(sqlQuery);
		}
		con.close();
	}
}