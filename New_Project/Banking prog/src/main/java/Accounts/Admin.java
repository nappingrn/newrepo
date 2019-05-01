package Accounts;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Admin extends employee{

	// this time baby ill be bulllllleeeeettttttppppprrrrrroooooofffffff
	public boolean AdminToken = true;
	
	public Admin()
	{
		this.Approval = "Admin";
	}
	
	
	public void withdrawAdmin(Statement state)
	{
		try {
		Scanner s1 = new Scanner(System.in);
		System.out.println("\n__________________________[Table of Users]_________________________________________");
		ShowAll(state);
		System.out.println("|____________________________________________________________________________________");

		System.out.print("| Which account number do you want to withdraw from : ");
		int account = s1.nextInt();
		System.out.print("| How much would you like to from account " + account +" : ");
		double amount = s1.nextDouble();
		double checkAmount = 0;
		
		ResultSet checkAcc = state.executeQuery("select amount where accountnumber = " + account);
		
		if(checkAcc.next())
		{
			checkAmount = checkAcc.getDouble("amount");
		}
		
		
		if(amount >=0 && amount <= checkAmount)
		{
			state.execute("update accounts set amount = amount - " + amount+ " where accountnumber = " + account);
			System.out.println("| Withdrew " + amount + " from account--->" + account);
			System.out.println("|_________________________________________________________________________________________\n");
		}
		else 
		{
			System.out.println("| you cannot withdraw negative values or values greater than the one in the account");
			System.out.println("|__________________________________________________________________________________________\n");
		}
		
		} catch (SQLException e) {e.printStackTrace();}
		catch(InputMismatchException i)
		{
			System.out.println("| Please follow the directions and enter in valid doubles\n"
					+ "| and valid account numbers. returning to options menu");
			System.out.println("|__________________________________________________________________________\n");
		}
		
	}
	
	
	public void depositAdmin(Statement state)
	{
		try {
			
		Scanner s1 = new Scanner(System.in);
		
		System.out.println("\n__________________________[Table of Users]_________________________________________");
		ShowAll(state);
		System.out.println("|____________________________________________________________________________________");
		
		System.out.print("| which account number do you want to deposit into? : ");
		int account = s1.nextInt();
		System.out.print("| how much would you like to deposit? : ");
		double amount = s1.nextDouble();

		if(amount>=0)
		{
			state.execute("update accounts set amount = amount + " + amount+ " where accountnumber = " + account);
		}
		else
		{
			System.out.println("| Please dont enter negative numbers.");
			return;
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(InputMismatchException i)
		{
			System.out.println("| next time please enter a valid account number, returning to select screen");
			System.out.println("|____________________________________________________________________________________");
		}
	
	}
	
	
	public void transferAdmin(Statement state)
	{
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		ArrayList<Double> amounts = new ArrayList<Double>();
		
		System.out.println("\n__________________________[Table of Users]______________________________");
		ShowAll(state);
		System.out.println("|__________________________________________________________________________\n");
		
		System.out.println("which account number would you like to transfer funds out of? : ");
		
		try {
			ResultSet AccNumbers = state.executeQuery("select accountnumber,amount "+ "from users,accounts,usersaccount "
			+ "where users.uid = usersaccount.uid "+ "and accounts.aid = usersaccount.aid ");
			
			while(AccNumbers.next() == true)
			{
				numbers.add(AccNumbers.getInt("accountnumber"));
				amounts.add(AccNumbers.getDouble("amount"));
			}
			
		Scanner S1 = new Scanner(System.in);
		int accountToTransfer = S1.nextInt();
		
		System.out.println("| how much would you like to transfer out?: ");
		int amountToTransfer = S1.nextInt();
		
		if(amountToTransfer <0 || amounts.get(numbers.indexOf(accountToTransfer)) < amountToTransfer )
		{
			System.out.println("| you must enter a positive sum which must not be greater than your accout total.");
			return;
		}
		
		
		System.out.println("| to what account do you want to transfer these funds:  ");
		
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
			System.out.println("| Incorrect target account selected, please try again.");
		}
		
		} catch (SQLException e) {e.printStackTrace();}
		catch(InputMismatchException i) {System.out.println("| Please use valid credentials next time, quitting.");}
	}
	
	
	public void deleteUser(Statement state)
	{
		ShowAll(state);
		Scanner DeletionCheck = new Scanner(System.in);
		
		System.out.print("| Which user is to be deleted out of this list : ");
		try {
		String choice = DeletionCheck.next();
		
		
			ResultSet data = state.executeQuery("Select * from "
					+ "users, accounts, usersaccount where "
					+ "users.uid = usersaccount.uid and accounts.aid = usersaccount.aid "
					+ "and users.name = " + "'" + choice + "'");
			
			ArrayList<Integer>numbers = new ArrayList<Integer>();
			ArrayList<Integer>aid = new ArrayList<Integer>();
			int uid;
			
			if(data.next()) // if there is a first entry
			{
				aid.add(data.getInt("aid")); // we can load in the first account ID
				uid = data.getInt("uid"); // the user id will never change since its just 1 user
				numbers.add(data.getInt("accountnumber")); // load the first accountnumber as well
			}
			
			
			while(data.next()) //load the rest of the account ids and the account numbers. (>>load the phasers)
			{
				aid.add(data.getInt("aid")); 
				numbers.add(data.getInt("accountnumber"));
			}
			
			System.out.print("| Phasers locked and loaded, are you sure you want to do this?(y/n):");
			String destroy = DeletionCheck.next();
			
			if(destroy.equalsIgnoreCase("y"))
			{
				for(int i = 0; i < aid.size();i++)
				{
					state.execute("delete from accounts where aid = " + aid.get(i) + ";"
							+" delete from accounts where aid = " + aid.get(i));
					
				}
				state.execute("delete from users where user = " +"'" + choice + "'");
				System.out.println("| tango down");
			}
			else
			{
				System.out.println("| deletion cancelled");
			}
			
			System.out.println("|________________________________________________________________");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(InputMismatchException i)
		{
			System.out.println("|please enter valid choices, returning to option select.");
			System.out.println("|________________________________________________________________|");
		}

	}
	
	
	public void MakeEmployee(Statement state)
	{
		System.out.print("| who would you like to make an employee: ");
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
				System.out.println("| Name: " + allusers.getString("name") + " \t\t|status:" 
			+ allusers.getString("status") + "\t\t|amount in account: " + allusers.getDouble("amount")
			+ "\t\t|Accountnumber: " + allusers.getInt("accountnumber"));

			}
		} catch (SQLException e) {e.printStackTrace();}
	}
	
}
