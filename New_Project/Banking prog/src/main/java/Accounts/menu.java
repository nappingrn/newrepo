package Accounts;

public class menu {

		public static void Display(String S)
		{
			
			if(S.equalsIgnoreCase("user") ||S.equalsIgnoreCase("employee")|| S.equalsIgnoreCase("admin") )
			{
				System.out.println("____________________________________________________________________________________");
				System.out.println("|User Options| (W to withdraw)  |  (D to deposit)  |  (T to transfer)               |");
						
				
			}
			if(S.equalsIgnoreCase("employee")|| S.equalsIgnoreCase("admin"))
			{
				System.out.println("|____________|__________________|__________________|________________________________|");
				System.out.println("|Employee Options| (L to list all of the users)  | (V to start to verify process)   |");

				
			}
			if(S.equalsIgnoreCase("admin"))
			{ 
				System.out.println("|________________|_______________________________|__________________________________|");
				System.out.println("|Admin Options| (S to show users) | (R to remove accounts) | (M to make employees)  |");
				System.out.println("|_____________|___________________|________________________|________________________|\n");
				
			}
				
			System.out.println("[ Enter (quit) to quit the program and log out ]\n");
			System.out.println("_______________________________________________________________________________________");
			System.out.print("| What would you like to do: ");

		}
	
}
