import java.util.*;
/**CS102 Data Structures w/Professor Bari	
 * HW 1: Course Registration System
 * @author Thomas Huang*/

//creation of a class to represent a course
public class Course implements Comparable<Course>, java.io.Serializable {
	static final long serialVersionUID = 1;
	/* Declaration of private instance variables: course size (# of currently enrolled students), 
	 * course capacity (maximum enrollment), course section #, course name, course id, instructor name, 
	 * course location, and a list of all the students currently enrolled.*/
	private int size, capacity, section;
	private String courseName, id, instructorName, location;
	private ArrayList<Student> studentList;
	
	/* default constructor*/
	public Course() {
		size = 0; capacity = 0; section = 0; courseName = ""; id = ""; instructorName = "";
		location = ""; studentList = new ArrayList<Student>();
	}
	
	/* Constructor creates a course based on input parameters */
	public Course(int size, int capac, int sect, String cName, String id, String iName, String location, ArrayList<Student> list) {
		this.size = size; capacity = capac; section = sect; courseName = cName; this.id = id;
		instructorName = iName; this.location = location; studentList = list;
	}
	
	/**check if course if full
	 * @return if number of enrolled students equals the course capacity
	 */
	public boolean isFull() { return size == capacity; }
	
	//getters
	public int size() { return size; }
	public int capacity() { return capacity; }
	public int section() { return section; }
	public String cName() { return courseName; }
	public String id() { return id; }
	public String iName() { return instructorName; }
	public String location() { return location; }
	public ArrayList<Student> list() { return studentList; }
	
	//setters
	public void setSize(int s) { size = s; }
	public void setCapacity(int c) { capacity = c; }
	public void setSection(int s) { section = s; }
	public void setCName(String n) { courseName = n; }
	public void setid(String id) { this.id = id; }
	public void setIName(String n) { instructorName = n; }
	public void setLocation(String l) { location = l; }
	public void setList(ArrayList<Student> l) { studentList = l; }
	//method to add a student to this course given the student as a parameter
	public boolean addStudent(Student s) {
		if(!isFull()) {
			studentList.add(s);
			size++;
			sortStudents();
			return true;
		}
		else {
			System.out.println("This course is already full, you may not register.");
			return false;
		}
	}
	//method to sort students based on their last names
	private void sortStudents() {
		Collections.sort(studentList);
	}
	//method to remove a parameter student from this course
	public boolean removeStudent(Student st) {
		for(int i = 0; i < studentList.size(); i++) {
			if(studentList.get(i).equals(st)) {
				studentList.remove(i);
				size--;
				return true;
			}
		}
		return false;
	}
	
	//list students enrolled in this course
	public String listStudents() {
		String result = "";
		if(studentList.size() == 0) {
			result = "\tNo students currently enrolled.";
		}
		else {
			for(Student s : studentList) {
				result += ("\n\t" + s.lastName() + ", " + s.firstName());
			}
		}
		return result;
	}
	//method to return an Integer arraylist of the available sections for a given course
	public ArrayList<Integer> sections(String course) {
		ArrayList<Integer> sections = new ArrayList<Integer>();
		for(Course c : CRS.courses) {
			if(course.equals(c.cName()) || course.equals(c.id()))
					sections.add((Integer) c.section());
		}
		return sections;
	}
	
	//compareTo override; compares courses by the number of students enrolled (size)
	@Override
	public int compareTo(Course s) {
		if(this.size() > s.size()) {
			return 1;
		}
		else if(this.size() < s.size()) {
			return -1;
		}
		else {
			return 0;
		}
	}
}
