package Control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import Model.Admin;

public class AdminController {
	public static final String SEPARATOR = "|";
	
	public static ArrayList readAllAdmins(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList alr = new ArrayList() ;// to store Admins data

        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				String  gender = star.nextToken().trim();
				String  nationality = star.nextToken().trim();
				String  name = star.nextToken().trim();
				String  userId = star.nextToken().trim();
				String  password = star.nextToken().trim();	// second token
				//int  contact = Integer.parseInt(star.nextToken().trim()); // third token
				// create Admin object from file data
				Admin admin = new Admin(gender,nationality,name,userId,password);
				
				// add to Professors list
				alr.add(admin) ;
			}
			return alr ;
	}
	
	/** Read the contents of the given file. */
	  public static List read(String fileName) throws IOException {
		List data = new ArrayList() ;
	    Scanner scanner = new Scanner(new FileInputStream(fileName));
	    try {
	      while (scanner.hasNextLine()){
	        data.add(scanner.nextLine());
	      }
	    }
	    finally{
	      scanner.close();
	    }
	    return data;
	  }
	
	public ArrayList<Admin> retriveAllAdmins()
	{
		ArrayList<Admin> a2 = new ArrayList<Admin>();
		
		AdminController txtDB = new AdminController();
    	String filename = "src/admin.txt" ;
		try {
			// read file containing Professor records.
			ArrayList al = AdminController.readAllAdmins(filename) ;
			for (int i = 0 ; i < al.size() ; i++) {
					Admin admin = (Admin)al.get(i);
					a2.add(admin);
					//System.out.println("Gender: " + admin.getGender());
					//System.out.println("Name: " + admin.getName() );
			}
			//Professo p1 = new Professor("Joseph","jos@ntu.edu.sg",67909999);
			// al is an array list containing Professor objs
			//al.add(p1);
			// write Professor record/s to file.
			///TextDB.saveProfessors(filename, al);
		}catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
  
		
		return a2;
	}
	
	
	
	public boolean checkAccount(String userId,String password)
	{
		int valid=0;
		ArrayList<Admin> a1 = retriveAllAdmins();
		
		for(int i=0;i<a1.size();i++)	
		{
			int userVal=a1.get(i).getUserId().compareTo(userId);
			int passVal=a1.get(i).getPassword().compareTo(password);
			//System.out.println(userVal);
			//System.out.println(passVal);
			
			if((userVal == passVal))
			{
			
				valid=1;
				System.out.println("Account accessed");
				System.out.println("Gender: " + a1.get(i).getGender());
				System.out.println("Name: " + a1.get(i).getName() );
				break;
				
			}
			
			else 
			{
				valid=0;
				
			}
		}
		
		if(valid==1)
		{
			return true;
		}
		
		else
		{
			return false;
		}
		
	}
	
	
	

}
