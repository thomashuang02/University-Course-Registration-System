/**CS102 Data Structures w/Professor Bari	
 * HW 1: Course Registration System
 * @author Thomas Huang*/
import java.io.*;
import java.util.*;

//creation of a class to represent an Admin
public class Admin extends User implements AdminInterface, java.io.Serializable {
	static final long serialVersionUID = 1;
	
	public Admin(String first, String last, String username, String password) { 
		super(first, last, username, password);
	}
	@SuppressWarnings("resource")
	//admin function of creating course; must enter all relevant information
	public boolean createCourse() {
		Scanner sc = new Scanner(System.in);
		Course c = new Course(); //default new course
		
		System.out.println("Enter course name: ");
		String name = sc.nextLine();
		for(Course i : CRS.courses) { //check if course name already exists; if it does, return false
			if(name.equals(i.cName())) {
				System.out.println("Course name already exists.");
				return false;
			}
		}
		c.setCName(name);
		
		System.out.println("Enter course ID: ");
		String id = sc.nextLine();
		for(Course i : CRS.courses) { //check if course id already exists; if it does, return false
			if(id.equals(i.id())) {
				System.out.println("Course ID already exists.");
				return false;
			}
		}
		c.setid(id);
		
		while(true) {
			System.out.println("Enter course enrollment capacity: ");
			try { c.setCapacity(Integer.parseInt(sc.next())); break; } 
			catch(NumberFormatException nfe) { 
				System.out.println("Invalid input; must be an integer."); 
			}
		}
		
		sc.nextLine();
		System.out.println("Enter course instructor: ");
		c.setIName(sc.nextLine());
		
		while(true) {
			System.out.println("Enter course section: ");
			try{ c.setSection(Integer.parseInt(sc.nextLine())); break; }
			catch(NumberFormatException nfe) { 
				System.out.println("Invalid input; must be an integer."); 
			}
		}
		
		System.out.println("Enter course location: ");
		c.setLocation(sc.nextLine());
		
		CRS.courses.add(c);
		
		//if the method makes it to the end, return true signifying the course has been successfully added
		return true;
	}
	@SuppressWarnings("resource")
	//admin function to delete course
	public boolean deleteCourse() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name or ID of the course you'd like to delete: ");
		String input = sc.nextLine();
		System.out.println("Enter the section number the course you'd like to edit: ");
		int section;
		while(true) {
			try { section = Integer.parseInt(sc.nextLine()); break;}
			catch(NumberFormatException nfe) { 
				System.out.println("Invalid input; must be an integer.");
			}
		}
		for(int i = 0; i < CRS.courses.size(); i++) { //go through course list and look for course with the given name/id and section
			if((input.equals(CRS.courses.get(i).cName()) || input.equals(CRS.courses.get(i).id())) && section==CRS.courses.get(i).section()) {
				CRS.courses.remove(i);
				return true; //if found, remove course and return true signifying course has been successfully deleted
			}
		}
		System.out.println("Course name/ID and section not found."); //if all courses have been checked and none match, return false;
		return false;
	}
	@SuppressWarnings("resource")
	//admin function to edit course
	public boolean editCourse() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of ID of the course you'd like to edit: ");
		String input = sc.nextLine();
		System.out.println("Enter the section number of the course you'd like to edit: ");
		int section;
		while(true) {
			try { section = Integer.parseInt(sc.nextLine()); break;}
			catch(NumberFormatException nfe) { 
				System.out.println("Invalid input; must be an integer.");
			}
		}
		for(int i = 0; i < CRS.courses.size(); i++) { //go through course list and look for course with the given name/id and section
			while((input.equals(CRS.courses.get(i).cName()) || input.equals(CRS.courses.get(i).id())) && section==CRS.courses.get(i).section()) {
				//show selected course info and prompt user to select which field to change
				System.out.println("EDITING:\n" + CRS.courses.get(i).cName() + 
						"\n\tID: " + CRS.courses.get(i).id() + "\n\tMaximum capacity: " + CRS.courses.get(i).capacity() + 
						"\n\tInstructor: " + CRS.courses.get(i).iName() + "\n\tSection: " + CRS.courses.get(i).section() + 
						"\n\tLocation: " + CRS.courses.get(i).location());
				System.out.println("To edit any of the following, enter the corresponding number: " + 
						"\n1: Maximum capacity"
						+ "\n2: Instructor"
						+ "\n3: Section"
						+ "\n4: Location");
				
				//string to hold user's input decision
				String d = sc.nextLine();
				if(d.equals("1")) {
					while(true) {
						System.out.println("Enter new maximum capacity: ");
						try { CRS.courses.get(i).setCapacity(Integer.parseInt(sc.next())); break; }
						catch(NumberFormatException nfe) { 
							System.out.println("Invalid input; must be an integer."); 
						}
					}
					return true;
				}
				else if(d.equals("2")) {
					System.out.println("Enter new instructor name: ");
					CRS.courses.get(i).setIName(sc.nextLine());
					return true;
				}	
				else if(d.equals("3")) {
					while(true) {
						System.out.println("Enter new section number: ");
						try { CRS.courses.get(i).setSection(Integer.parseInt(sc.next())); break; }
						catch(NumberFormatException nfe) { 
							System.out.println("Invalid input; must be an integer."); 
						}
					}
					return true;
				}	
				else if(d.equals("4")) {
					System.out.println("Enter new location: ");
					CRS.courses.get(i).setLocation(sc.next());
					return true;
				}	
				else
					System.out.println("Invalid input, try again.");
			}
		}
		//if course not found in for loop, let user know and return false;
		System.out.println("Course name or ID not found.");
		return false;
	}
	@SuppressWarnings("resource")
	//finds course based on user search to use as parameter for overloaded version below
	public void displayCourseInfo() { 
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter course name or ID: ");
		String input = sc.nextLine();
		System.out.println("Enter the section number the course you'd like to view: ");
		int section;
		while(true) {
			try { section = Integer.parseInt(sc.nextLine()); break;}
			catch(NumberFormatException nfe) { 
				System.out.println("Invalid input; must be an integer.");
			}
		}
		for(int i = 0; i < CRS.courses.size(); i++) { //go through course list and look for course with the given name/id
			if((input.equals(CRS.courses.get(i).cName()) || input.equals(CRS.courses.get(i).id())) && section==CRS.courses.get(i).section()) {
				displayCourseInfo(CRS.courses.get(i));
				return;
			}
		}
		System.out.println("Course name/id and section not found.");
	} 
	//display specific course
	private void displayCourseInfo(Course c) {
		System.out.println("COURSE INFO FOR: " + c.cName() + "\nID: " + c.id() + "\nEnrolled/Max: " + c.size() + "/" + c.capacity()
		+ "\nList of enrolled students:" + c.listStudents() + 
		"\nInstructor: " + c.iName() + "\nSection: " + c.section() + "\nLocation: " + c.location());
	}

	@SuppressWarnings("resource")
	//prompt user for information fields of new student, to be added to list of students in CRS, which will be serialized
	//at the end of the program
	public void registerStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter new student's first name: ");
		String first = sc.nextLine();
		System.out.println("Enter new student's last name: ");
		String last = sc.nextLine();
		System.out.println("Enter new student's username: ");
		String username = sc.nextLine();
		System.out.println("Enter new student's password: ");
		String password = sc.nextLine();
		
		CRS.students.add(new Student(first, last, username, password));
		System.out.println("Student " + first + " " + last + " added with username '" + username + "' and password '" + password + "'.");
	}
	
	@SuppressWarnings("resource")
	public void registerAdmin() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter new admin's first name: ");
		String first = sc.nextLine();
		System.out.println("Enter new admin's last name: ");
		String last = sc.nextLine();
		System.out.println("Enter new admin's username: ");
		String username = sc.nextLine();
		System.out.println("Enter new admin's password: ");
		String password = sc.nextLine();
		
		CRS.admins.add(new Admin(first, last, username, password));
		System.out.println("Admin " + first + " " + last + " added with username '" + username + "' and password '" + password + "'.");
	}
	
	/*display all courses, including course name + id
	 * number of enrolled students / maximum enrollment
	 * list of enrolled students
	 * */
	//overrides superclass User viewCourses(); this one includes a list of enrolled students for each class
	public void viewCourses() {
		System.out.println("LIST OF ALL COURSES:");
		for(Course c : CRS.courses) {
			System.out.println(c.cName() + " ~ " + c.id() 
					+ "\nSection: " + c.section()
					+ "\n\tENROLLMENT STATUS: " + c.size() + "/" + c.capacity()
					+ c.listStudents());
		}
	}
	
	/*same as viewCourses(), but only full courses are displayed
	 */
	public void viewFullCourses() {
		System.out.println("LIST OF FULL COURSES:");
		int counter = 0;
		for(Course c : CRS.courses) {
			if(c.isFull()) {
				System.out.println(c.cName() + " ~ " + c.id() 
						+ "\nSection: " + c.section()
						+ "\n\tENROLLMENT STATUS: " + c.size() + "/" + c.capacity());
				counter++;
			}
		}
		if(counter==0)
			System.out.println("No full courses.");
	}
	
	//writes to a file a list of full courses created by method listFullCourses()
	public void writeFullCourses() {
		//list of full courses
		ArrayList<Course> full = listFullCourses();
		StringBuilder sb = new StringBuilder();
		//columns
		sb.append("Course,Course_ID");
		//if there are full courses, append each course + id on a new row with comma separators
		if(full.size() != 0) {
			//label
			for(Course c : full) {
				sb.append("\n" + c.cName() + "," + c.id());
			}
		}
		else
			sb.append("\nNo full courses.");
		
		String result = sb.toString();
		
		try (PrintWriter writer = new PrintWriter(new File("fullCourses.csv"))) {
		      writer.write(result);

		      System.out.println("List of full courses written to 'fullCourses.csv.'");
		} 
		catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		}
	}
	
	//goes through list of courses and adds to a new list all the full courses; returns the new list
	private ArrayList<Course> listFullCourses() {
		ArrayList<Course> full = new ArrayList<Course>();
		for(Course c : CRS.courses) { 
			if(c.isFull())
				full.add(c);
		}
		return full;
	}
	
	//method to view a list of the students enrolled in a course
	@SuppressWarnings("resource")
	public void viewStudentsInCourse() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name or id of the course whose students you'd like to view: ");
		String input = sc.nextLine();
		System.out.println("Enter the section number the course whose students you'd like to view: ");
		int section;
		while(true) {
			try { section = Integer.parseInt(sc.nextLine()); break;}
			catch(NumberFormatException nfe) { 
				System.out.println("Invalid input; must be an integer.");
			}
		}
		for(int i = 0; i < CRS.courses.size(); i++) { //go through course list and look for course with the given name/id
			if((input.equals(CRS.courses.get(i).cName()) || input.equals(CRS.courses.get(i).id())) && section==CRS.courses.get(i).section()) {
				System.out.println("Students enrolled in " + CRS.courses.get(i).cName() + ", Section " + section + ":"
				+ CRS.courses.get(i).listStudents());
				return;
			}
		}
		System.out.println("Course name or ID not found.");
		
	}
	@SuppressWarnings("resource")
	public void viewRegisteredCourses() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the student's first name: ");
		String first = sc.next();
		System.out.println("Enter the student's last name: ");
		String last = sc.next();
		for(int i = 0; i < CRS.students.size(); i++) { //go through course list and look for course with the given name/id
			if(first.equals(CRS.students.get(i).firstName()) && last.equals(CRS.students.get(i).lastName())) {
				CRS.students.get(i).viewRegisteredCourses();
				return;
			}
		}
		System.out.println("Student '" + first + " " + last + "' was not found.");
	}
	
	//sorts courses using sort method of Collections based on compareTo method in course class
	public void sortCoursesByEnrollment() {
		Collections.sort(CRS.courses);
		Collections.reverse(CRS.courses);
		System.out.println("Courses sorted in descending order of enrollment.");
	}
}