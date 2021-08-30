/**CS102 Data Structures w/Professor Bari	
 * HW 1: Course Registration System
 * @author Thomas Huang*/

//main program of CRS

import java.io.*;  
import java.util.*;
@SuppressWarnings("resource") 
public class CRS {
	/**main method
	 * @param arguments
	 * @throws Exception
	 */
	protected static ArrayList<Course> courses;
	protected static ArrayList<Admin> admins;
	protected static ArrayList<Student> students;
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) throws Exception {
		//create an ArrayList of courses that will hold the courses and info specified in the csv
		//if there is an existing serialized file of the course list, retrieve it and update courses;
		//if not, read in information from csv.
		if(deserializeCourses("course_list.ser") != null) {
			courses = deserializeCourses("course_list.ser");
		}
		else
			courses = csvToArrayList("MyUniversityCourses.csv");
		
		//same as above, but for admin list. If no admin file, add default admin with u: Admin and p: Admin001
		//if(new File("admin_list.ser").isFile())
		if(deserializeAdmins("admin_list.ser") != null) {
			admins = deserializeAdmins("admin_list.ser");
		}
		else {
			admins = new ArrayList<Admin>();
			admins.add(new Admin("Default","Admin","Admin","Admin001"));
		}
		
		//same as above, but for students instead
		if(deserializeStudents("student_list.ser") != null) {
			students = deserializeStudents("student_list.ser");
		}
		else
			students = new ArrayList<Student>();
		
		//declare a user whose type will be specified by login
		User user = null;
		
		System.out.println("Welcome to the Course Registration System!");
		System.out.println("Press ENTER to continue.");
		sc.nextLine();
		
		boolean exit = false; //exit boolean; if user opts to exit during login, program skips straight to serialization
		//LOGIN LOOP
		while(true) {
			//prompt user to login
			System.out.println("Please enter '1' for Admin login or '2' for Student login."
					+ "\nTo exit the system, please enter '3'.");
			String l = sc.next();
			if(l.equals("3")) {
				exit = true;
				break;
			}
			User login = login(l); //try logging in
			if(login != null) { //if login does not fail...
				//login method will return object of appropriate type based on login
				user = login;	
				promptEnter();
				break;
			}
			else { //case of invalid login
				System.out.println("Invalid login; please try again.");
				promptEnter();
			}
		}
		
		boolean logout = false;
		//unless user has opted to exit during login, continue with operations
		while(!exit) {
			//at beginning of loop; if user opted to logout at some point, loop will restart and logout statement will execute
			if(logout) {
				while(true) {
					//prompt user to login
					System.out.println("Please enter '1' for Admin login or '2' for Student login."
							+ "\nTo exit the system, please enter '3'.");
					String l = sc.next();
					if(l.equals("3")) {
						exit = true;
						break;
					}
					User login = login(l); //try logging in
					if(login != null) { //if login does not fail...
						//login method will return object of appropriate type based on login
						user = login;
						logout = false;
						promptEnter();
						break;
					}
					else { //case of invalid login
						System.out.println("Invalid login; please try again.");
						promptEnter();
					}
				}
			}
			//admin functions
			if(user instanceof Admin) {
				System.out.println("ADMIN MENU: "
						+ "\n1: Manage courses"
						+ "\n2: View reports"
						+ "\n3: Logout"
						+ "\n4: Exit program");
				String action = sc.next();
				while(action.equals("1")) { //course management option
					System.out.println("COURSE MANAGEMENT: enter any of the following numbers."
							+ "\n1: Create a new course"
							+ "\n2: Delete a course"
							+ "\n3: Edit a course"//allow the admin to edit any info EXCEPT course ID and name
							+ "\n4: Display information for a course" //by course ID
							+ "\n5: Register a new student" //add to arraylist of students
							+ "\n6: Register a new admin"
							+ "\n7: Return to main admin menu"
							+ "\n8: Exit program");
					String cmF = sc.next(); //user decision
					if(cmF.equals("1")) {
						if(((Admin) user).createCourse())
							System.out.println("Course successfully created.");
						else
							System.out.println("Action unsuccessful.");
						
					}
					else if(cmF.equals("2")) {
						if(((Admin) user).deleteCourse())
							System.out.println("Course successfully deleted.");
						else
							System.out.println("Action unsuccessful.");

					}
					else if(cmF.equals("3")) {
						if(((Admin) user).editCourse())
							System.out.println("Course successfully edited.");
						else
							System.out.println("Action unsuccessful.");
					}
					else if(cmF.equals("4")) {
						((Admin) user).displayCourseInfo();
					}
					else if(cmF.equals("5")) {
						((Admin) user).registerStudent();
					}
					else if(cmF.equals("6")) {
						((Admin) user).registerAdmin();
					}
					else if(cmF.equals("7")) {
						action = "back"; //breaks this while loop and evades error message, going back course management menu
					}
					else if(cmF.equals("8"))
					{
						exit = true;
						break;
					}
					else {
						System.out.println("Invalid input. Try again.");
					}
					
					//in any case other than going back, put this here so there is a buffer between the current action and the next
					if(!cmF.equals("7"))
						promptEnter();
				}
				while(action.equals("2")) { //view reports option
					System.out.println("VIEWING REPORTS: enter any of the following numbers."
							+ "\n1: View ALL courses"
							+ "\n2: View FULL courses"
							+ "\n3: Write the list of full courses to 'fullCourses.csv'"
							+ "\n4: View a courses's enrolled students"
							+ "\n5: View a student's enrolled courses"
							+ "\n6: Sort courses by number of enrolled students"
							+ "\n7: Return to main admin menu"
							+ "\n8: Exit program");
					String vrF = sc.next(); //user decision
					if(vrF.equals("1")) {
						((Admin) user).viewCourses();
					}
					else if(vrF.equals("2")) {
						((Admin) user).viewFullCourses();
					}
					else if(vrF.equals("3")) {
						((Admin) user).writeFullCourses();
					}
					else if(vrF.equals("4")) {
						((Admin) user).viewStudentsInCourse();
					}
					else if(vrF.equals("5")) {
						((Admin) user).viewRegisteredCourses();
					}
					else if(vrF.equals("6")) {
						((Admin) user).sortCoursesByEnrollment();
					}
					else if(vrF.equals("7")) {
						action = "back"; //breaks this while loop and evades error message, going back course management menu
					}
					else if(vrF.equals("8"))
					{
						exit = true;
						break;
					}
					else {
						System.out.println("Invalid input. Try again.");
					}
					if(!vrF.equals("7"))
						promptEnter();
				}
				if(action.equals("3")) {
					System.out.println("Logging out from admin " + user.firstName() + " " + user.lastName() + "...");
					logout = true;
				}
				else if(action.equals("4") || exit==true) { //exit; breaks loop and proceeds to serialization of data and termination of program
					break;
				}
				//if during either course management or report viewing, user decides to go back, this allows the loop to restart
				//without "invalid input" message
				else if(action.equals("back")) { 
				}
				else {
					System.out.println("Invalid input. Try again.");
				}
			}
			
			
			//student functions
			else if(user instanceof Student) {
				System.out.println("STUDENT MENU: "
						+ "\n1: Manage courses"
						+ "\n2: Logout"
						+ "\n3: Exit program");
				String action = sc.next();
				while(action.equals("1")) { //course management option
					System.out.println("COURSE MANAGEMENT: enter any of the following numbers."
							+ "\n1: View all courses"
							+ "\n2: View all courses that are not full"
							+ "\n3: Register for a course"
							+ "\n4: Withdraw from a course" 
							+ "\n5: View your registered courses" //courses for this student
							+ "\n6: Return to main student menu"
							+ "\n7: Exit program");
					String cmF = sc.next(); //user decision
					if(cmF.equals("1")) {
						((Student) user).viewCourses();
						
					}
					else if(cmF.equals("2")) {
						((Student) user).viewOpenCourses();

					}
					else if(cmF.equals("3")) {
						if(((Student) user).register())
							System.out.println("Successfully registered for this course.");
						else
							System.out.println("Registration unsuccessful.");
					}
					else if(cmF.equals("4")) {
						if(((Student) user).withdraw())
							System.out.println("Successfully withdrawn from this course.");
						else
							System.out.println("Withdrawal unsuccessful.");
					}
					else if(cmF.equals("5")) {
						((Student) user).viewRegisteredCourses();
					}
					else if(cmF.equals("6")) {
						action = "back"; //breaks this while loop and evades error message, going back course management menu
					}
					else if(cmF.equals("7"))
					{
						exit = true;
						break;
					}
					else {
						System.out.println("Invalid input. Try again.");
					}
					
					//in any case other than going back, put this here so there is a buffer between the current action and the next
					if(!cmF.equals("6"))
						promptEnter();
				}
				if(action.equals("2")) {
					System.out.println("Logging out from student " + user.firstName() + " " + user.lastName() + "...");
					logout = true;
				}
				else if(action.equals("3") || exit==true) { //exit; breaks loop and proceeds to serialization of data and termination of program
					break;
				}
				//if during either course management or report viewing, user decides to go back, this allows the loop to restart
				//without "invalid input" message
				else if(action.equals("back")) { 
				}
				else {
					System.out.println("Invalid input. Try again.");
				}
			}
		}
		
		//at the end of the program before exit, serialize and store permanently the current course and student data
		serialize(courses, "course_list.ser");
		serialize(admins, "admin_list.ser");
		serialize(students, "student_list.ser");
		System.out.println("Course, admin, and student data have been saved to respective SER files. Ending program.");
	}
	
	
	
	//login method
	public static User login(String user) {
		if(user.equals("1")) {
			System.out.println("[Logging in as Admin]\nUsername:");
			String username = sc.next();
			System.out.println("Password:");
			String password = sc.next();
			for(Admin a : admins) {
				if(username.equals(a.username()) && password.equals(a.password())) {
					System.out.println("Successfully logged in as Admin: " + a.firstName() + " " + a.lastName() + ".");
					return a;
				}
			}
		}
		if(user.equals("2")) {
			System.out.println("[Logging in as Student]\nUsername:");
			String username = sc.next();
			System.out.println("Password:");
			String password = sc.next();
			for(Student s : students) {
				if(username.equals(s.username()) && password.equals(s.password())) {
					System.out.println("Successfully logged in as Student: " + s.firstName() + " " + s.lastName() + ".");
					return s;
				}
			}
		}
		return null;
	}
	
	/**reads courses from csv file into a list
	 * @param name of csv file
	 * @return arraylist of courses
	 */
	public static ArrayList<Course> csvToArrayList(String csvName) throws Exception {
		ArrayList<Course> courses = new ArrayList<Course>();
		try {  
			String line = "";  //used to determine if there is another line in the csv
			String tokenSplit = ",";  //use comma as token delimiter
			
			//parse csv into a buffered reader
			BufferedReader br = new BufferedReader(new FileReader(csvName));  
			br.readLine(); //skip first line, as it is just labels for the columns
			
			while ((line = br.readLine()) != null) { //loops until the end of the file (no more lines)
				String[] course = line.split(tokenSplit);    //make a string array of this line's course info  
				Course c = new Course(); //make a new course
				//set course info as taken from the string array
				c.setCName(course[0]);
				c.setid(course[1]);
				c.setCapacity(Integer.parseInt(course[2]));
				c.setSize(Integer.parseInt(course[3]));
				/*(since none of the classes start with enrolled students, skip setting the student list; the
				default constructor for Course instantiates an empty ArrayList anyway)*/
				c.setIName(course[5]);
				c.setSection(Integer.parseInt(course[6]));
				c.setLocation(course[7]);
				
				//add course to list of courses
				courses.add(c);
			}  
		}   
		catch(IOException e) { e.printStackTrace(); }  //catch any input/output exceptions
		return courses;
	}
	
	//taken from HW 1 sample code; modified to be a method that serializes a given parameter to a given file name
	public static void serialize(ArrayList<?> input, String serName) {
		try {
			//file designated for writing data
			FileOutputStream fos = new FileOutputStream(serName);
			
			//writing object to a stream of data
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			//write input object to oos
			oos.writeObject(input);
			
			//close both streams
			oos.close();
			fos.close();
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//taken from HW 1 sample code; modified to be a method that deserializes a given file and returns the object
	@SuppressWarnings("unchecked")
	public static ArrayList<Course> deserializeCourses(String serName) {
		//try deserialization
		try {
			//FileInputSystem receives bytes from a file
			FileInputStream fis = new FileInputStream(serName);
		      
		    //ObjectInputStream does the deserialization-- it reconstructs the data into an object
		    ObjectInputStream ois = new ObjectInputStream(fis);
		      
		    //Cast as arraylist of courses. readObject will take the object from ObjectInputStream
		    ArrayList<Course> de = (ArrayList<Course>) ois.readObject();
	    	ois.close();
		    fis.close();
		    return de;
		}
		catch(IOException ioe) {
		    return null;
		}
		catch(ClassNotFoundException cnfe) {
		    cnfe.printStackTrace();
		    return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Student> deserializeStudents(String serName) {
		//try deserialization
		try {
			//FileInputSystem receives bytes from a file
			FileInputStream fis = new FileInputStream(serName);
		      
		    //ObjectInputStream does the deserialization-- it reconstructs the data into an object
		    ObjectInputStream ois = new ObjectInputStream(fis);
		      
		    //Cast as arraylist of courses. readObject will take the object from ObjectInputStream
		    ArrayList<Student> de = (ArrayList<Student>) ois.readObject();
	    	ois.close();
		    fis.close();
		    return de;
		}
		catch(IOException ioe) {
		    return null;
		}
		catch(ClassNotFoundException cnfe) {
		    cnfe.printStackTrace();
		    return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Admin> deserializeAdmins(String serName) {
		//try deserialization
		try {
			//FileInputSystem receives bytes from a file
			FileInputStream fis = new FileInputStream(serName);
		      
		    //ObjectInputStream does the deserialization-- it reconstructs the data into an object
		    ObjectInputStream ois = new ObjectInputStream(fis);
		      
		    //Cast as arraylist of courses. readObject will take the object from ObjectInputStream
		    ArrayList<Admin> de = (ArrayList<Admin>) ois.readObject();
	    	ois.close();
		    fis.close();
		    return de;
		}
		catch(IOException ioe) {
		    return null;
		}
		catch(ClassNotFoundException cnfe) {
		    cnfe.printStackTrace();
		    return null;
		}
	}
	
	//method that pauses program and prompts user for ENTER before continuing
	public static void promptEnter() {
		System.out.println("Press ENTER to continue.");
		sc.nextLine();//in case a previous line of code creates a new line
		sc.nextLine();
	}
	
}
