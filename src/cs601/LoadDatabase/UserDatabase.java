package cs601.LoadDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cs601.Yap.BaseServer.JDBCConnection;

public class UserDatabase extends JDBCConnection
{	
	private final static String jdbcURL="jdbc:mysql://sql.cs.usfca.edu:3306/";
	private final static String user="user52";
	private final static String pass="user52";
	private final static String database="user52";
	
	public UserDatabase()
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
		String jdbcString=jdbcURL + database;
		Connection con=DriverManager.getConnection(jdbcString, user, pass);
		Statement stmt=con.createStatement();
		ResultSet result=stmt.executeQuery("select userId from userdataset");
		ArrayList<String> al=new ArrayList<String>();
		while(result.next())
		{
			al.add(result.getString("userId"));
		}
		for(String a: al)
		{
			String sqlQuery="UPDATE userdataset SET name=\"Sami Rollins\" WHERE userId=\"" + a + "\";";
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
		String []usern={"B Dayal",
						"Chayu",
						"Mark",
						"Monarch",
						"Maya",
						"Monster",
						"Mayank",
						"Mthirani",
						"Nishu",
						"Sami R.",
						"SunilM.",
						"VPandey"};
		String address="1234 Geary Street, CA 94121";
		String []password={"Password123","Password123","Password123","Password123","Password123","Password123","Password123","Password123","Password123",
				"Password123","Password123","Password123"};
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
				"vibyrs813@hotmail.com"};
		String []name={"Bonny Dayal","Chan Wong","Mark Henry","Monaco Taco",
				"Maya Maheswari","Monster Daemon","Mayank Rathi","Mayank Thirani",
				"Nishant Thirani","Sami Rollins","Sunil Masih","Vibhore Pandeya"};
		for(int i=0; i<usern.length; i++)
		{
			String sqlQuery="INSERT INTO userdataset (funnyVotes,usefulVotes,coolVotes,userId,userName,password,address,name) VALUES(0,0,0,\"" + userId[i] + "\",\"" + usern[i] + "\",\"" + password[i] + "\",\"" + address + "\",\"" + name[i] + "\");";
			stmt.executeUpdate(sqlQuery);
		}
		con.close();
	}
}