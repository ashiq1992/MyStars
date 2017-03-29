package View;

import java.util.InputMismatchException;
import java.util.Scanner;

import Control.AdminController;

public class StarsPlannerApp {
	
	public static void main(String args[])
	
	{
		int input=-1; //Takes in user input;
		int valid=1;
		boolean result;
		String password,userId;
		do {
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Type of user: ");
		System.out.println("1) Student ");
		System.out.println("2) Admin ");
		
		
		try
		{
			input=sc.nextInt();
		}catch(InputMismatchException ex)
		{
			System.out.println("Input choice is not an integer");
		}
		if(input==1)
		{
			
		}
		
		else if (input==2)
		{
			System.out.println("Enter userId:");
			userId=sc.next();
			System.out.println("Enter Password:");
			password=sc.next();
			AdminController ad=new AdminController();
			result=ad.checkAccount(userId, password);
			if(result==true){
				System.out.println("Sucess");
			}
			else{
				System.out.println("Failed");
			}
		}
		
		else 
		{
			System.out.println("Invalid Choice/Input! Re-enter");
			
		}
		}
		
		while(valid !=0);
			
		
	}
	

}
