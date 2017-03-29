package Model;
//myinput
public class Admin extends Person{
	
	private String position;
	private boolean access;
	private Course c1;
	
	public Admin(String position,boolean access,Course c1)
	{
		this.position=position;
		this.access=access;
		this.c1=c1;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	
	
	public boolean isAccess() {
		return access;
	}

	public void setAccess(boolean access) {
		this.access = access;
	}

	public void editAccessPeriod(boolean editAccess)
	{
		access=editAccess;
	}
	
	public void addStudent(Student s1){
		
	}
	
	public void addCourse(Course c1){
		c1=new Course();
	}

}
