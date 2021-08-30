/**CS102 Data Structures w/Professor Bari	
 * HW 1: Course Registration System
 * @author Thomas Huang*/

//creation of an interface with functions of students
public interface StudentInterface {
	String username();
	String password();
	String firstName();
	String lastName();
	
	void viewCourses();
	void viewOpenCourses();
	boolean register();
	boolean withdraw();
	void viewRegisteredCourses();
	/*exit function will be implemented in the main method rather than in the Student class, so that we still serialize the data,
	which is done at the end of the main function, before ending the program*/
	
	//student functions taken from the hw instructions for reference
	/*Course Management
	1.View all courses
	2.View all coursesthat are not full
	3.Register  ina  course(in  this  case the  student mustenter the  course  name,  section,  and student full name, 
		the name will be added to the appropriate course) 
	4.Withdraw from a course(in thiscasethe student will be asked to enter her/hisname and the coursename, 
		then the name of  the student will be taken offfrom the given course’slist)
	5.View all courses that the current student is registeredin
	6.Exit*/
}
