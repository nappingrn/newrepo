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
					
					System.out.println("Welcome Returning user!");
					System.out.print("enter username: ");
					Username = userInput.readLine();
					System.out.print("enter Password: ");
					Password = userInput.readLine();
					
					
					if(Username.matches("[a-zA-Z0-9]+") && Password.matches("[a-zA-Z0-9]+") &&
					   Password.length() <= 32 && Username.length() <= 32 )
							{
					Account User = new Account(Username, Password, state);
							}
					else
					{
						System.out.println("invalid Credentials");
					}
				}
				else if(in.equalsIgnoreCase("n"))
				{
					
					System.out.println("would you like to make a single(S) or joint account?(J)");
					choice = userInput.readLine();
					MakeAccount(choice, state);
				}
				else
				{
					System.out.println("you have made an incorrect choice.");
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
				System.out.println("The Rules for creation are as follows:\n - only alphanumeric characters(a-z)"
				+ " or (0-9) \n - no more than 32 char user names \n same length restriction goes for passwords");
				
				System.out.println("so, what would you like your account name to be?");
				String TestUser = getDetails.nextLine();
				System.out.println("enter password");
				String TestPass = getDetails.nextLine();
				
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
					System.out.println("the credntials that you have typed do not follow the rules that"
							+"/n i have just stated. try again kiddo.");
					return;
				}
				
			}
			else if(Choice.equalsIgnoreCase("j"))
			{
				System.out.println("The Rules for creation are as follows:"
						+"\n - only alphanumeric characters(a-z) or (0-9)"
						+ "\n - no more than 32 char user names"
						+ "\n same length and char restrictions are used for passwords");
				
				System.out.println("what would you like your first account name to be? : ");
				String TestUser = getDetails.nextLine();
				System.out.println("enter first password : ");
				String TestPass = getDetails.nextLine();

				System.out.println("what would you like your second account name to be? : ");
				String TestUser2 = getDetails.nextLine();
				System.out.println("enter second password : ");
				String TestPass2 = getDetails.nextLine();
				

				if( TestUser.matches("[a-zA-Z0-9]+") && TestPass.matches("[a-zA-Z0-9]+") && // check 4 char seq
					TestUser2.matches("[a-zA-Z0-9]+") && TestPass2.matches("[a-zA-Z0-9]+") &&
					TestUser.length() <= 32 && TestPass.length() <= 32 && // check 4 length
					TestUser2.length() <= 32 && TestPass2.length() <= 32 )
				{
				
				
					JointAccount joint = new JointAccount();
					joint.CreateJointAccount(TestUser,TestPass,TestUser2,TestPass2, state);
				
				getDetails.close();
				return;
				}
				else
				{
					System.out.println("the credntials that you have typed do not follow the rules that"
							+"/n i have just stated. try again kiddo.");
					return;
				}


				
			}
			else if(Choice.equalsIgnoreCase("quit"))
			{
				getDetails.close();
				System.out.println("qutting application.");
				System.exit(0);
			}
			else
			{
				getDetails.close();
				System.out.println("incorrect choice");
				return;
			}
			getDetails.close();
		}
}

