/**CS102 Data Structures w/Professor Bari	
 * HW 1: Course Registration System
 * @author Thomas Huang*/

//creation of an interface with functions of admins
public interface AdminInterface {
	String username();
	String password();
	String firstName();
	String lastName();
	
	boolean createCourse();
	boolean deleteCourse();
	boolean editCourse();
	void displayCourseInfo();
	void registerStudent(); 
	void viewCourses();
	void viewFullCourses();
	void writeFullCourses();
	void viewStudentsInCourse();
	void viewRegisteredCourses();
	void sortCoursesByEnrollment();
	/*exit function will be implemented in the main method rather than in the Admin class, so that we still serialize the data,
	which is done at the end of the main function, before ending the program*/
	
	//admin functions taken from the hw instructions for reference
	/*course management
	 * 1. Create a new course
	 * 2.Delete a course
	 * 3.Edit a course(this will allow the admin to editany information on the courseexceptforcourse ID and name)
	 * 4.Display information for a given course(by course ID)
	 * 5.Registera student(this option will allow the admin to add a student without assigning to a course check Req 11 for student’s information–Hint: You might need to have an ArrayList of  Students where you store Studentobjects)
	 * 6.Exit
	 * 
	 * Reports
	 * 1.View  all  courses(for  every  course  the  admin  should  be  able  to  see  the  listof   enrolled student’s names, enrolled student’sids, number  of   students registered,and  the  maximum number of  students allowed to be registered)
	 * 2.View all coursesthat are FULL(reached the maximum number of  students) 
	 * 3.Write to a file the list of  course that are full
	 * 4.View the names of  the studentsthat areregisteredin a specific course
	 * 5.View the list ofcoursesthat agivenstudent is registered in (given a student first name and last name the system shall display all the courses that student is registered in)
	6. Sortthe courses based onthe current number of  studentsregistered
	7. Exit */
}
