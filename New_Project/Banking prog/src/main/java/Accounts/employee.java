package Accounts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

// this shit is heat, no real way to super bust it up since i only exact match this jank

public class employee extends Account { // btw admin should extend off this
	
	public employee()
	{
		this.Approval = "employee";
	}
	
	public void ApproveAccount(Statement state)
	{
		
		Scanner s1 = new Scanner(System.in);	
		System.out.println("would you like to approve(a) or deny (d) an account: ");
		
		String approval = s1.next();
		
		if(approval.equalsIgnoreCase("a"))
		{
		ListUsers(state);
		System.out.print("what is the username that you would like to approve for use?: ");
		String choice = s1.next().trim();
		
		
			try {
				state.execute("UPDATE users SET status = 'user' WHERE name = " 
			+ "'"+ choice + "'"+  " and status = 'pending'"); // change the selected user's status into approved
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (approval.equalsIgnoreCase("d"))
		{
			ListUsers(state);
			System.out.print("what is the username that you would like to deny: ");
			String choice = s1.next().trim();
			
			
				try {
					state.execute("UPDATE users SET status = 'denied' WHERE name = " 
				+ "'"+ choice + "'"+  " and status = 'pending'"); // change the selected user's status into approved
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		else
		{
			System.out.println("improper choice entered");
		}
		
	}
	
	public void ListUsers(Statement state)
	{
		try 
		{
			ResultSet notApproved = state.executeQuery("select name,status,accountnumber,amount from users, accounts, usersaccount"
					+ " where  users.uid = usersaccount.uid and accounts.aid = usersaccount.aid and"
					+ "(status = 'pending' or status = 'user')");
			int i = 1;
			while(notApproved.next() == true)
			{
				String user = notApproved.getString("name");
				String status = notApproved.getString("status");
				int accountnumber = notApproved.getInt("Accountnumber");
				double useramount = notApproved.getDouble("amount");
				
				System.out.println("Row " + i + " | username: " + user + " |  status: " + status
						+" |  accountnumber: " + accountnumber + " | Amount in account " + useramount );
				i++;
				
			}
		}
		catch(Exception e) {e.printStackTrace();}
		
	}

}

