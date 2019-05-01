package Accounts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class yep{

	public static void main(String args[])
	{
		String[] firedup = {
		"create table if not exists users(UID serial not null, name text, pass text, status text)",
		"create table if not exists accounts(AID serial not null, amount float(2), Accountnumber serial not null)",
		"create table if not exists usersaccount(UID int, AID int)"};
		
		try (BufferedReader s1 = new BufferedReader( new InputStreamReader(System.in)))
		{
			Connection con  = Connect.getRemoteConnection();
		
			for(int i = 0; i < firedup.length ;i++) // if the tables dont exist, create them.
			{
				Statement writing = con.createStatement();
				writing.execute(firedup[i]);
			}
			
		System.out.println("________________________________________________________________________");
		System.out.println("|       ______  ______  ____                 _      __     ___          |");
		System.out.println("|      |  ____||___  / |  _ \\               | |    /_ |   / _ \\         |");
		System.out.println("|      | |__      / /  | |_) |  __ _  _ __  | | __  | |  | | | |        |");
		System.out.println("|      |  __|    / /   |  _ <  / _` || '_ \\ | |/ /  | |  | | | |        |");
		System.out.println("|      | |____  / /__  | |_) || (_| || | | ||   <   | | _| |_| |        |");
		System.out.println("|      |______|/_____| |____/  \\__,_||_| |_||_|\\_\\  |_|(_)\\___/         |");
		System.out.println("|_______________________________________________________________________|");
		
		String UserChoice =  "";
		Statement state;
		System.out.print("| Access Account(Y)| OR |Make New Account(N)| : ");

			state = con.createStatement();
		
			UserChoice = s1.readLine();
		System.out.println("|__________________|____|___________________|___________________________");
		logon_creds.choice(UserChoice,state);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
			
	}
		
	}
	
