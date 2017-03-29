package Model;
//myinput
public class Admin extends Person{
	
	private boolean accessPeriod;
	private Course c1;
	
	public Admin(String gender,String name,String nationality,String userId,String password)
	{
		super(gender,name,nationality,userId,password);
		
		
	}


	public boolean isAccessPeriod() {
		return accessPeriod;
	}

	public void setAccessPeriod(boolean accessPeriod) {
		this.accessPeriod = accessPeriod;
	}

	public Course getC1() {
		return c1;
	}

	public void setC1(Course c1) {
		this.c1 = c1;
	}



	
}
