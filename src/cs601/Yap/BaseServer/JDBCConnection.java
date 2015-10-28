package cs601.Yap.BaseServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is the base for JDBC Connection and can be extended by other servlet classes for creating a connection to any DB server.
 * @author Mayank
 *
 */
public class JDBCConnection
{
	protected String username;
	protected String password;
	protected String database;
	
	public JDBCConnection(String username, String password, String database)
	{
		this.username=username;
		this.password=password;
		this.database=database;
	}
	
	/**
	 * It returns the connection object to the database
	 * @param String
	 * @return Connection
	 * @throws SQLException
	 */
	public synchronized Connection connection(String urlString) throws SQLException
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
		String jdbcString=urlString + database;
		Connection con=DriverManager.getConnection(jdbcString, username, password);
		
		return con;
	}
}
