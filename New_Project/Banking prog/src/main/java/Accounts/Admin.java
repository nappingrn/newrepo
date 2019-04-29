package Accounts;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends employee{

	public boolean AdminToken = true;
	
	public Admin()
	{
		this.Approval = "Admin";
	}
	
	
	public void Log(String log)
	{
		try(FileWriter f = new FileWriter("src/logs/accounts.txt/", true); 
				BufferedWriter b = new BufferedWriter(f); 
				PrintWriter p = new PrintWriter(b);)
		{
			p.println(log);
		} 
		catch (IOException e) {e.printStackTrace();}
		
		/*
		 * Insert into Logs values (?) (Type, User, Amount, Time);
		 * 
		 * */
		
	}
	
	
	public void withdrawAdmin(Statement state)
	{
		Scanner s1 = new Scanner(System.in);
		ShowAll(state);
		System.out.println("which account number do you want to withdraw from? : ");
		int account = s1.nextInt();
		System.out.println("how much would you like to withdraw? : ");
		double amount = s1.nextDouble();
		
		try {
			state.execute("update accounts set amount = amount - " + amount+ " where accountnumber = " + account);
		} catch (SQLException e) {e.printStackTrace();}
		
	}
	
	
	public void depositAdmin(Statement state)
	{
		Scanner s1 = new Scanner(System.in);
		ShowAll(state);
		System.out.println("which account do you want to deposit into? : ");
		int account = s1.nextInt();
		System.out.println("how much would you like to deposit? : ");
		double amount = s1.nextDouble();

		try {
			
		if(amount>=0)
		{
			state.execute("update accounts set amount = amount + " + amount+ " where accountnumber = " + account);
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void transferAdmin(Statement state)
	{
		
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		ArrayList<Double> amounts = new ArrayList<Double>();
		
		ShowAll(state);
		
		System.out.println("which account number would you like to transfer funds out of? : ");
		
		try {
			ResultSet AccNumbers = state.executeQuery("select accountnumber,amount "
													+ "from users,accounts,usersaccount "
													+ "where users.uid = usersaccount.uid "
														+ "and accounts.aid = usersaccount.aid "
														);
			while(AccNumbers.next() == true)
			{
				numbers.add(AccNumbers.getInt("accountnumber"));
				amounts.add(AccNumbers.getDouble("amount"));
			}
			
		} catch (SQLException e) {e.printStackTrace();}
		
		Scanner S1 = new Scanner(System.in);
		int accountToTransfer = S1.nextInt();
		
		
		System.out.println("how much would you like to transfer out?: ");
		int amountToTransfer = S1.nextInt();
		
		if(amountToTransfer <0 || amounts.get(numbers.indexOf(accountToTransfer)) < amountToTransfer )
		{
			System.out.println("you must enter a positive sum which must not be greater than your accout total.");
			return;
		}
		
		
		System.out.println("to what account do you want to transfer these funds? ");
		
		int targetAccount = S1.nextInt();
		boolean valid = false;
		
		try {
			ResultSet check = state.executeQuery("select accountnumber from accounts where accountnumber =" + targetAccount);
			if(check.next())
			{
				valid = true;
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		
		if (valid == true)
		{
			try {
				state.execute("UPDATE accounts SET amount = amount -"+ amountToTransfer +
						"	WHERE accountnumber = " + accountToTransfer);
				state.execute("UPDATE accounts SET amount = amount +"+ amountToTransfer +
						"	WHERE accountnumber = " + targetAccount);
			} catch (SQLException e) {e.printStackTrace();}
		}
		else
		{
			System.out.println("incorrect target account selected, please try again.");
		}
		
		
	}
	
	
	public void deleteUser(Statement state)
	{
		ShowAll(state);
		System.out.println("Which user is to be deleted?: ");
		
		Scanner delete = new Scanner(System.in);
		
		String toBeDestroyed = delete.next();
		
		System.out.print("Are you sure you want to delete " + toBeDestroyed + " and all of their accounts? (y/n) :");
		
		String choice = delete.next();
		
		if(choice.equalsIgnoreCase("y"))
		{
			
			try {
				ResultSet numbers = state.executeQuery("Select Accountnumber, uid, aid "
						+ "from usersaccount, users, accounts "
						+ "where users.uid = usersaccount.uid "
						+ "and accounts.aid = usersaccount.aid "
						+ " and users.name = " + "'"+ toBeDestroyed +"'");

				while(numbers.next())
				{
					state.execute("delete from usersaccount where uid = "+ numbers.getInt("uid")  + " and aid = " + numbers.getInt("aid"));
					state.execute("delete from accounts where accountnumber = " + numbers.getInt("accountnumber"));
				}
				
				state.execute("delete from users where name = " +"'"+toBeDestroyed+"'");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		delete.close();
	}
	
	public void MakeEmployee(Statement state)
	{
		System.out.print("who would you like to make an employee?");
		Scanner S1 = new Scanner(System.in);
		String newEmployee = S1.next();
		
		try {
			state.execute("UPDATE users SET status = 'employee' where name = " + "'"+ newEmployee + "'"+  " and status = 'user'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public void ShowAll(Statement state)
	{	
		try {
			ResultSet allusers = state.executeQuery("select name, status, amount, accountnumber from usersaccount, users, accounts "
					+ "where users.UID = usersaccount.UID  and accounts.AID = usersaccount.AID");
			while(allusers.next())
			{
				System.out.println(" Name: " + allusers.getString("name") + " |  status: " 
			+ allusers.getString("status") + " | amount in account: " + allusers.getDouble("amount")
			+ " | Accountnumber: " + allusers.getInt("accountnumber"));

			}
		} catch (SQLException e) {e.printStackTrace();}
	}
	
}
