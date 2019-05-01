package Accounts;

import java.io.BufferedReader;
import java.util.regex.*;
import java.io.InputStreamReader;
import java.sql.Statement;
import java.util.Scanner;


// this time baby, i'll be BUUUULLLLLLLEEEEETTTTPPPPRRRROOOOOOOFFFFFFFF
public class logon_creds {


		public static void choice(String in, Statement state)
		{
			String choice, Username, Password = "";
			
			try(BufferedReader userInput = new BufferedReader( new InputStreamReader(System.in)))
			{
				if (in.equalsIgnoreCase("y"))
				{
					
					System.out.println("| Welcome Returning User!");
					System.out.print("| Enter Username: ");
					Username = userInput.readLine();
					System.out.print("| Enter Password: ");
					Password = userInput.readLine();
					
					
					if(Username.matches("[a-zA-Z0-9]+") && Password.matches("[a-zA-Z0-9]+") &&
					   Password.length() <= 32 && Username.length() <= 32 )
							{
					Account User = new Account(Username, Password, state);
							}
					else
					{
						System.out.println("| Invalid Credentials");
					}
				}
				else if(in.equalsIgnoreCase("n"))
				{
					
					System.out.print("| Would you like to make a |Single (S)| or |Joint(J)| account: ");
					choice = userInput.readLine();
					System.out.println("\n|-------------------[Starting Account Creation Process]-----------------|");
					MakeAccount(choice, state);
				}
				else
				{
					System.out.println("You have made an incorrect choice for account type.");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}	
		}
		
		
		public static void MakeAccount(String Choice, Statement state)
		{
			Scanner getDetails = new Scanner(System.in);
			if(Choice.equalsIgnoreCase("s"))
			{
				System.out.println("\n|____________________[Rules for Account Creation]_______________________|"
				+ "\n| 1) Only alphanumeric characters [(a-z)"
				+ " or (0-9)] 			|\n| 2) No more than 32 character are allowed in your Username		| \n"
				+ "| 3) The same length restriction applies for Password.			|");
				System.out.println("|_______________________________________________________________________|");
				
				System.out.print("| What would you like your account name to be : ");
				String TestUser = getDetails.nextLine();
				System.out.print("| What would you like your account password to be : ");
				String TestPass = getDetails.nextLine();
		
				System.out.println("|_______________________________________________________________________|");
				
				if(TestUser.matches("[a-zA-Z0-9]+") && TestPass.matches("[a-zA-Z0-9]+") && //check for char seq
				   TestUser.length() <= 32 && TestPass.length() <= 32 ) // check for length
				{
				
				
				Account NewUser = new Account();
				NewUser.CreateAccount(TestUser, TestPass, state);
				
				getDetails.close();
				return;
				}
				else
				{
					System.out.println("|___________________________________________________________________");
					System.out.println("|The credentials entered do not follow the aforementioned rules");
					return;
				}
				
			}
			else if(Choice.equalsIgnoreCase("j"))
			{
				System.out.println("\n|____________________[Rules for Account Creation]_______________________|"
						+ "\n| 1) Only alphanumeric characters [(a-z)"
						+ " or (0-9)] 			|\n| 2) No more than 32 character are allowed in your Username		| \n"
						+ "| 3) The same length restriction applies for Password.			|"
						+ "\n| 4) usernames can NOT be the same.                                     |");
						System.out.println("|_______________________________________________________________________|");
				
				System.out.println("| What would you like your first account name to be? : ");
				String TestUser = getDetails.nextLine();
				System.out.println("| Enter first password : ");
				String TestPass = getDetails.nextLine();

				System.out.println("| What would you like your second account name to be? : ");
				String TestUser2 = getDetails.nextLine();
				System.out.println("| Enter second password : ");
				String TestPass2 = getDetails.nextLine();
				

				if( TestUser.matches("[a-zA-Z0-9]+") && TestPass.matches("[a-zA-Z0-9]+") && // check 4 char seq
					TestUser2.matches("[a-zA-Z0-9]+") && TestPass2.matches("[a-zA-Z0-9]+") &&
					TestUser.length() <= 32 && TestPass.length() <= 32 && // check 4 length
					TestUser2.length() <= 32 && TestPass2.length() <= 32 && (TestUser.equals(TestUser2) != true) )
				{
				
				
					JointAccount joint = new JointAccount();
					joint.CreateJointAccount(TestUser,TestPass,TestUser2,TestPass2, state);
				
				getDetails.close();
				return;
				}
				else
				{
					System.out.println("| The credentials entered do not follow the aforementioned rules.");
					return;
				}

			}
			else if(Choice.equalsIgnoreCase("quit"))
			{
				getDetails.close();
				System.out.println("| Logging off, Goodbye!");
				System.out.println("________________________________________________________________");
				System.exit(0);
			}
			else
			{
				getDetails.close();
				System.out.println("| An incorrect choice has been entered.");
				return;
			}
			getDetails.close();
		}
}

