package Accounts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect 
{
	/*yoinked from https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/java-rds.html#java-rds-javase*/
	public static Connection getRemoteConnection() {

	      try {
	      Class.forName("org.postgresql.Driver");
	      String dbName = "postgres";
	      String userName = "postgres"; // fill in later
	      String password = "about to slap you with the quickness."; // save in text file
	      String hostname = "bankingapp.c3povt7bydjz.us-east-2.rds.amazonaws.com";
	      String port = "5432";
	      String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
	      
	      Connection con = DriverManager.getConnection(jdbcUrl);
	      return con;
	    }
	    catch (SQLException e) { e.printStackTrace();} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return null;
	  
	}
}
    
