package in.nit.service;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import in.nit.model.Student;
import in.nit.model.StudentRole;

public interface IStudentService {
	// crud operation
	public Student createStudent(Student student ,Set<StudentRole> studentRoles
			) throws Exception;
	
	// get user by username
	public Student getStudent(String username);
	void deleteStudent(Integer id);
	//Integer saveStudent(Student s);
	void updateStudent(Student s);
	
 Optional<Student>getOneStudent(Integer id);
	List<Student>getAllStudent();

	boolean isStudentExit(Integer id);
	  List<Student> searchStudents(String query); 

}
