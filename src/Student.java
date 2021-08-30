import java.util.*;

/**CS102 Data Structures w/Professor Bari	
 * HW 1: Course Registration System
 * @author Thomas Huang*/

//creation of a class to represent a Student
public class Student extends User implements StudentInterface, java.io.Serializable, Comparable<Student> {
	static final long serialVersionUID = 1;
	private ArrayList<Course> courseList;
	
	public Student(String first, String last, String username, String password) { 
		super(first, last, username, password);
		courseList = new ArrayList<Course>();
	}
	
	//use implementation of superclass User
	public void viewCourses() {
		super.viewCourses();
	}
	
	//print list of courses who have not reached maximum capacity
	public void viewOpenCourses() {
		System.out.println("LIST OF OPEN COURSES:");
		int counter = 0;
		for(Course c : CRS.courses) {
			if(!c.isFull()) {
				System.out.println(c.cName() + " ~ " + c.id());
				counter++;
			}
		}
		if(counter==0)
			System.out.println("All courses full.");
	}
	
	@SuppressWarnings("resource")
	public boolean register() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the name or id of the course you'd like to register for: ");
		String course = sc.nextLine();
		
		//find available sections and prompt user input. if no sections exist for given course name, let user know and return false
		ArrayList<Integer> sections = sections(course);
		if(sections.size() == 0) {
			System.out.println("The specified course does not exist, or there are no available sections.");
			return false;
		}
		System.out.println("Here are the available sections for " + course + ": " + sections.toString());
		System.out.println("Please enter which section you'd like to register for: ");
		int section; //holder for user input section number
		while(true) {
			boolean valid = false;
			try { section = Integer.parseInt(sc.next()); 
				for(int s : sections) {
					if(section == s) {
						valid = true;
						break; 
					}
				}
				if(valid)
					break;
				else
					System.out.println("Please enter one of the available section numbers.");
			}
			catch(NumberFormatException nfe) { 
				System.out.println("Invalid input; must be an integer."); 
			}
		}
		//prompt student for his/her full name; if it corresponds with the currently logged in student, continue
		System.out.println("Please enter your first name: ");
		String first = sc.next();
		System.out.println("Please enter your last name: ");
		String last = sc.next();
		//if first and/or last name don't match... display error and return false
		if(!(first.equals(this.firstName()) && last.equals(this.lastName()))) {
			System.out.println("The given first and/or last name does not match your login credentials. You are logged in as "
					+ this.firstName() + " " + this.lastName() + ".");
			return false;
		}
		//now that user has inputted both course name/section and student name, enroll this student in the course
		for(Course c : CRS.courses) {
			if((course.equals(c.cName()) || course.equals(c.id())) && section == c.section()) {
				if(c.addStudent(this)) {
					//print success message and return true
					courseList.add(c);
					System.out.println("Student " + this.firstName() + " " + this.lastName() + " has been successfully enrolled in: ");
					System.out.println("\t" + c.cName() + " ~ " + c.id() + " ~ Section " + c.section());
					return true;
				}
				else
					return false;
			}
				
		}
		//if something else goes wrong, notify the user that the course was not found and return false
		System.out.println("Course with given name and section not found.");
		return false;
	}	
	//helper for register method; finds sections available for a given course name/id
	public ArrayList<Integer> sections(String course) {
		ArrayList<Integer> sections = new ArrayList<Integer>();
		for(Course c : CRS.courses) {
			if(course.equals(c.cName()) || course.equals(c.id()))
					sections.add((Integer) c.section());
		}
		return sections;
	}
	
	@SuppressWarnings("resource")
	public boolean withdraw() {
		Scanner sc = new Scanner(System.in);
		//prompt user to enter course name
		System.out.println("Please enter the name of id of the course you'd like to withdraw from:");
		String course = sc.nextLine();
		//prompt user for their name; if it doesn't match login, return false
		System.out.println("Please enter your first name: ");
		String first = sc.nextLine();
		System.out.println("Please enter your last name: ");
		String last = sc.nextLine();
		//if first and/or last name don't match... display error and return false
		if(!(first.equals(this.firstName()) && last.equals(this.lastName()))) {
			System.out.println("The given first and/or last name does not match your login credentials. You are logged in as "
					+ this.firstName() + " " + this.lastName() + ".");
			return false;
		}
		for(int i = 0; i < courseList.size(); i++) {
			if(course.equals(courseList.get(i).cName()) || course.equals(courseList.get(i).id())) {
				if(courseList.get(i).removeStudent(this)) {
					courseList.remove(i);
					return true;
				}
				else {
					System.out.println("You are not enrolled in this course.");
					return false;
				}
			}
		}
		System.out.println("Course with specified name/id not found.");
		return false;
	}
	
	public void viewRegisteredCourses() {
		System.out.println(super.firstName() + " " + super.lastName() + "'s enrolled courses:");
		if(courseList.size() == 0)
			System.out.println("You are not currently registered in any courses.");
		else {
			for(Course c : courseList) {
				System.out.println("\t" + c.cName() + " ~ " + c.id());
			}
		}
	}
	
	@Override
	public int compareTo(Student s) {
		if(this.lastName().compareTo(s.lastName()) > 0)
			return 1;
		else if(this.lastName().compareTo(s.lastName()) < 0)
			return -1;
		else
			return 0;
	}

}
