package Accounts;

public class menu {

		public static void Display(String S)
		{
			
			if(S.equalsIgnoreCase("user") ||S.equalsIgnoreCase("employee")|| S.equalsIgnoreCase("admin") )
			{
				System.out.println("\nUser operations : (W to withdraw) :: (D to deposit) :: (T to transfer)");
						
				
			}
			if(S.equalsIgnoreCase("employee")|| S.equalsIgnoreCase("admin"))
			{
				System.out.println("Employee options"
						+ ": (L to list all of the users) :: "
						+ "(V to start to verify process) ");
				
			}
			if(S.equalsIgnoreCase("admin"))
			{
				System.out.println("Admin options: (S to show all users) :: (R to remove accounts)"
						+ ":: (M to make employees) ");
				
			}
			
			System.out.print("(quit) to quit the program and log out: ");

		}
	
}
