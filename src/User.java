/**CS102 Data Structures w/Professor Bari	
 * HW 1: Course Registration System
 * @author Thomas Huang*/

//creation of a class with functions common to all users
public abstract class User implements java.io.Serializable{
	static final long serialVersionUID = 1;
	private String first, last, username, password;
	
	//constructor
	protected User(String f, String l, String u, String p) {first = f; last = l; username = u; password = p;}
	
	public String username() {
		return username;
	}
	public String password() {
		return password;
	}
	public String firstName() {
		return first;
	}
	public String lastName() {
		return last;
	}
	//to be implemented in subclasses Admin and Student
	public abstract void viewRegisteredCourses();
	//default method for viewing all courses; inherited by Student and overridden by Admin
	protected void viewCourses() {
		System.out.println("LIST OF ALL COURSES:");
		for(Course c : CRS.courses) {
			System.out.println(c.cName() + " ~ " + c.id());
		}
	}
}
