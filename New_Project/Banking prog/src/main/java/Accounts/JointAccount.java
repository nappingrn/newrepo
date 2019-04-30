package Accounts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JointAccount extends Account {

	
	
	public JointAccount()
	{
		
	}
	
	
	public void CreateJointAccount(String User1, String Pass1, String User2, String Pass2,Statement state)
	{
		
		if(validate(User1,state) && validate(User2,state))
		{
			int UID1 = 0;
			int UID2 = 0;
			int AID = 0;
			
			
			try {
				ResultSet ID = state.executeQuery("insert into users(uid, name,pass,status) "
				+ "values (DEFAULT,"+ "'" + User1 + "'" + "," + "'" +Pass1 + "'"+ "," + "'pending'" + ")"+ "returning uid");
				if (ID.next())
				{
					UID1 = ID.getInt("UID");
				}
				
				
				ID = state.executeQuery("insert into users(uid, name,pass,status) "
				+ "values (DEFAULT ,"+ "'" +User2 + "'" + "," + "'" +Pass2 +"'"+ "," + "'pending'" + ") returning uid");
				
				if(ID.next())
				{
					UID2 = ID.getInt("UID");
				}

				
				ID = state.executeQuery("insert into Accounts (aid,amount,accountnumber)"
								+ " values (DEFAULT, 0.0, DEFAULT) returning aid");
				if(ID.next())
				{
					AID = ID.getInt("AID");
				}
			
				
				state.execute("insert into usersaccount(uid, aid) values (" + UID1 + "," + AID + ")");
				state.execute("insert into usersaccount(uid, aid) values (" + UID2 + "," + AID + ")");
				
				System.out.println("accounts created with the quickness.");
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else
		{
			System.out.println("usernames are not unique, please try again");
		}
		
	}
	
}
