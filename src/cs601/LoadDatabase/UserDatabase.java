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
	private final static String jdbcURL="jdbc:mysql://localhost:3306/";
	private final static String user="mthirani";
	private final static String pass="#Rpgsmn421#";
	private final static String database="yap";
	
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
		String jdbcString="jdbc:mysql://localhost:3306/" + database;
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
		String jdbcString="jdbc:mysql://localhost:3306/" + database;
		Connection con=DriverManager.getConnection(jdbcString, user, pass);
		Statement stmt=con.createStatement();
		String []usern={"Sami R."};
		String []address={"1234 Geary Street, CA 94121"};
		String []password={"Password123"};
		String []userid={"srollins@gmail.com"};
		String []name={"Sami Rollins"};
		for(int i=0; i<usern.length; i++)
		{
			String sqlQuery="INSERT INTO userdataset (funnyVotes,usefulVotes,coolVotes,userId,userName,password,address,name) VALUES(0,0,0,\"" + userid[i] + "\",\"" + usern[i] + "\",\"" + password[i] + "\",\"" + address[i] + "\",\"" + name[i] + "\");";
			stmt.executeUpdate(sqlQuery);
		}
		con.close();
	}
}