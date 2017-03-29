package Control;

import java.util.ArrayList;

import Model.Admin;

public class AdminController {
	
	//1) StarsPlannerApp>>AdminController>>FileController>>Mode
	
	
	public ArrayList<Admin> retriveAllAdmins()
	{
		ArrayList<Admin> a2 = new ArrayList<Admin>();
		
		return null;
	}
	
	
	
	public boolean checkAccount(String userId,String password)
	{
		ArrayList<Admin> a1 = retriveAllAdmins();
		
		for(int i=0;i<a1.size();i++)
			
		{
			if(a1.get(i).getUserId()==userId && a1.get(i).getPassword()==password)
			{
				return true;
			}
			
			else 
			{
				return false;
			}
		}
		return false;
		
	}
	
	
	

}
