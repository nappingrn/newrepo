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
			Connection con  = Connect.getRemoteConnection();;
		
			for(int i = 0; i < firedup.length ;i++) // if the tables dont exist, create them.
			{
				Statement writing = con.createStatement();
				writing.execute(firedup[i]);
			}
		
		
		String UserChoice =  "";
		Statement state;
		System.out.println("Access Account(Y)");
		System.out.print("Make New Account(N): ");
		//Connection generalConnection = Connect.getRemoteConnection();
		
		
			state = con.createStatement();
		
			UserChoice = s1.readLine();
		
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
	
