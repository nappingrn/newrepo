package Accounts;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
public class Account {
	
	protected double Amount;
	protected int AccountNumber;
	protected String Username;
	protected String PW;
	protected String Approval;
	
	
	public Account()
	{
		
	}
	
	public Account(String User, String Password, Statement state) {

		this.Approval = "None";
		String choice = "meme";
		ResultSet getStatus;
		if (logon(User,Password,state))
		{	
			this.Username = User;
			System.out.println("logged in");
			
			try 
			{
				getStatus = state.executeQuery("select status from users where name = "+ "'"+User+"'");
				if(getStatus.next())
				{
					this.Approval = getStatus.getString("status");
					System.out.println("your account type is " + this.Approval);
				}
			} catch (SQLException e) {e.printStackTrace();}
			
			Scanner S1 = new Scanner(System.in);
				
			while(choice.equals("quit") != true)
			{
				menu.Display(this.Approval);
				choice = S1.next();
				Options(choice, state,this.Approval);
			}
			
			S1.close();
		}
	}
	
	public void CreateAccount(String User, String Pass, Statement state)
	{
		System.out.println("Starting new account creation process...");
		ResultSet look;
		try {
		System.out.println("attempting creation");
		if(validate(User,state) == true) // if there is no first entry, then the username is not in use
		{
			this.Username = User;
			this.Amount = 0;
			this.PW = Pass;
			this.Approval = "pending";
			state.execute("insert into users (UID, name, pass, status)"+ " values (DEFAULT," + "'"+User+"'" + "," 
			+ "'"+Pass +"'"+ ","+ "'"+this.Approval+"'" + ")"); // insert new user into users
			
			look = state.executeQuery("select UID from users where name = " + "'" + User +"'");
			int newUID = 0;
			
			if(look.next())
			{
				newUID = look.getInt("UID"); // get user ID that was just put into the tables
			}
			 ResultSet AID =  state.executeQuery("insert into accounts (AID, Amount, AccountNumber) values "+ "( DEFAULT ,"
			+ this.Amount + "," + "DEFAULT" + ") "+ "returning AID"); // create a new account for this new user
			
			int newAID = 0;
			if(AID.next()){newAID = AID.getInt("AID");}// get the most recent id
			
			state.execute("insert into usersaccount(uid,aid) values" + " (" + newUID + "," + newAID+")"); // use both IDs to create an entry in the join table
			
			logon(User, Pass, state);
			System.out.println("new account creation complete");
		}
		else {System.out.println("username given is already in use");}
		
		} catch (SQLException e) {e.printStackTrace();}
		
			
	}
	
	public void Options(String choice, Statement state, String status)
	{
			if(choice.toLowerCase().trim().equals("d") && status.equals("pending") == false)
			{
				if(status.equals("admin"))
				{
					Admin makeEmployee = new Admin();
					makeEmployee.depositAdmin(state);
					return;
				}
				Deposit(state);
			}
			else if(choice.toLowerCase().trim().equals("w") && status.equals("pending") == false)
			{
				if(status.equals("admin"))
				{
					Admin makeEmployee = new Admin();
					makeEmployee.withdrawAdmin(state);
					return;
				}
				Withdraw(state);
			}
			else if(choice.toLowerCase().trim().equals("t") && status.equals("pending") == false)
			{
				if(status.equals("admin"))
				{
					Admin makeEmployee = new Admin();
					makeEmployee.transferAdmin(state);
					return;
				}
				TransferFunds(state);
			}
			else if(choice.toLowerCase().trim().equals("l") && (status.equals("employee")||status.equals("admin")))
			{
				employee showThem = new employee();
				showThem.ListUsers(state);
			}
			else if(choice.toLowerCase().trim().equals("v") && (status.equals("employee")||status.equals("admin")))
			{
				employee showThem = new employee();
				showThem.ApproveAccount(state);
			}
			else if(choice.toLowerCase().trim().equals("s") && status.equals("admin"))
			{
				Admin showAll = new Admin();
				showAll.ShowAll(state);
			}
			else if(choice.toLowerCase().trim().equals("m") && status.equals("admin"))
			{
				Admin makeEmployee = new Admin();
				makeEmployee.MakeEmployee(state);
			}
			else if(choice.toLowerCase().trim().equals("r") && status.equals("admin"))
			{
				Admin makeEmployee = new Admin();
				makeEmployee.deleteUser(state);
			}
			else if (choice.equalsIgnoreCase("quit"))
			{
				System.out.println("logging out, goodbye.");
			}
			
	}
	
	public boolean logon(String Username, String Password, Statement state)
	{
		ResultSet check;
		try {
			check = state.executeQuery("select name,pass from users where name = " + "'"+
					Username + "' " + "and pass = " + "'"+ Password + "'"); // attempt to select entry from users
			
			System.out.println("\nattempted to log in");
			return check.next();
			
		} catch (SQLException e) {e.printStackTrace();} 
		
		return false;
		
		
	}
	
	
	public void Deposit(Statement state)
	{
		@SuppressWarnings("resource")
		Scanner S1 = new Scanner(System.in);
		System.out.print("how much would you like to deposit into your account? :");
		double UserAmount = S1.nextDouble();
		
		
		ArrayList<Integer> values = new ArrayList<>();
		
		
		
		try {
			ResultSet ListAccounts = state.executeQuery("Select accountnumber "
			+ "from users,accounts,usersaccount where users.UID = usersaccount.uid "
			+ "and accounts.AID = usersaccount.aid and users.name = " + "'" + this.Username + "'");
			
			System.out.println("------------------------your accounts------------------------------");
			while(ListAccounts.next() == true)
			{
				values.add(ListAccounts.getInt("Accountnumber"));
				System.out.println("account number " +ListAccounts.getInt("Accountnumber") );
			}
			System.out.println("-------------------------------------------------------------------");
			
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.print("which account would you like to deposit it in?: " );
		int choice = S1.nextInt();
		
		if(Amount>=0 && values.contains(choice)) //validate deposit amount and account number.
		{
			try {
				state.execute("UPDATE accounts SET amount = amount + " + UserAmount +" WHERE accountnumber = " + choice);
				System.out.println("nice! just deposited " + UserAmount + " into account " + choice+ "!");
				return;
			} catch (SQLException e) {e.printStackTrace();} 
		}
		else
		{
			System.out.println("must deposit an amount >=0 and choose a proper account number");
		}
	}
	
	public boolean Withdraw(Statement state) {

		ResultSet ListAccounts;
		ArrayList<Integer> values = new ArrayList<>();
		Scanner s1 = new Scanner(System.in);
		
		System.out.println("------------------Your list of accounts and their amounts--------------------");
		try {
			
			ListAccounts = state.executeQuery("Select Accountnumber,amount "
										+ "from usersaccount, users, accounts "
										+ "where users.UID = usersaccount.UID"
										+ " and accounts.AID = usersaccount.AID"
										+ " and users.name = " + "'"+ this.Username +"'");
			while(ListAccounts.next())
			{
				int accountnumber = ListAccounts.getInt("accountnumber");
				double amount = ListAccounts.getDouble("amount");
				values.add(accountnumber);
				System.out.println("account number " + accountnumber + ": has " + amount);
				
			}
			
			
		} catch (SQLException e1) {e1.printStackTrace();
		}
		System.out.println("------------------------------------------------------------------");
		
		
		System.out.print("Which one of your account numbers would you like to withdraw from?: ");
		int numberToUse = s1.nextInt();
		double amountInAccount = 0;
		try {
			
			ListAccounts = state.executeQuery("select amount from accounts where accountnumber = "+numberToUse);
			if(ListAccounts.next() == false){System.out.println("incorrect choice"); return false;}
			else {amountInAccount = ListAccounts.getDouble("amount");}
			
		} catch (SQLException e1) {e1.printStackTrace();}
		
		
		System.out.println("how much would you like to withdraw: ");
		
		// use returning at the end of queries to return the value just written
		
		double amountToTake = s1.nextDouble();
		
		if(amountToTake >=0 && values.contains(numberToUse) && amountToTake <= amountInAccount)
		{
			try {
				state.execute("UPDATE accounts SET amount =" + (amountInAccount - amountToTake) +
							"	WHERE accountnumber = " + numberToUse);
				System.out.println("nice, just withdrew: " + amountToTake + " dollars!");
			} catch (SQLException e) {e.printStackTrace();}
				return true;
		}
		else
		{
			System.out.println("improper account number or improper amount entered, returning to home screen.");
			return false;
		}
		

	}
	
	public void TransferFunds(Statement state)
	{
		System.out.println("which account would you like to transfer funds out of? : ");
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		ArrayList<Double> amounts = new ArrayList<Double>();
		
		try {
			ResultSet AccNumbers = state.executeQuery("select accountnumber,amount "
													+ "from users,accounts,usersaccount "
													+ "where users.uid = usersaccount.uid "
														+ "and accounts.aid = usersaccount.aid "
														+ "and users.name = " + "'" + this.Username+ "'");
			while(AccNumbers.next() == true)
			{
				System.out.print(AccNumbers.getInt("accountnumber"));
				System.out.println(AccNumbers.getDouble("amount"));
				numbers.add(AccNumbers.getInt("accountnumber"));
				amounts.add(AccNumbers.getDouble("amount"));
			}
			
		} catch (SQLException e) {e.printStackTrace();}
		
		Scanner S1 = new Scanner(System.in);
		int accountToTransfer = S1.nextInt();
		
		if(numbers.contains(accountToTransfer) == false)
		{
			System.out.println("you must use your own account as the source of the funds.");
			S1.close();
			return;
		}
		
		System.out.println("how much would you like to transfer out?: ");
		int amountToTransfer = S1.nextInt();
		
		if(amountToTransfer <0 || amounts.get(numbers.indexOf(accountToTransfer)) < amountToTransfer )
		{
			System.out.println("you must enter a positive sum which must not be greater than your accout total.");
			S1.close();
			return;
		}
		
		
		System.out.println("to what account do you want to transfer these funds? ");
		
		int targetAccount = S1.nextInt();
		boolean valid = false;
		
		try {
			ResultSet check = state.executeQuery("select accountnumber from account where accountnumber =" + targetAccount);
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
	
	public boolean validate(Statement state, String user)
	{	
		ResultSet check;
		
		try {
			check = state.executeQuery("select name from users where name = " + "'" + user + "'");
			
			if(check.next()) // if the username already exists in the users table, dont allow a new one
			{
				return false;
			}
			else
			{
				return true;
			}
			
		} catch (SQLException e) {e.printStackTrace();}
		return true;
	} 

	public void log(String AccountName, String Type, int AccountNumber, double Amount, Statement states)
	{
		
		try {
			states.execute("insert into logs (Account, Type, AccountNumber, Amount)," + " values (" + AccountName +","+ Type +","+ AccountNumber +","+ Amount + ")");
		} catch (SQLException e) {e.printStackTrace();}
	
	}
	
	
	public boolean validate(String user, Statement state)
	{
		try
		{
			ResultSet checkIfAllowed = state.executeQuery("select name from users where name = " + "'" + user + "'");
			if(checkIfAllowed.next()){return false;}
			else{return true;}
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return true;
	}
}

