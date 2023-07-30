package in.nit.util;



import org.springframework.stereotype.Component;


import in.nit.model.Student;
@Component
public class StudentUtil {

	public void mapToActualObject(Student actual, Student student) {
		
		// TODO Auto-generated method stub
		// if (student.getName()!=null) we should test this condition also
		//actual.setId(student.getId());
		actual.setUsername(student.getUsername());
		actual.setPassword(student.getPassword());
		actual.setFirstName(student.getFirstName());
		actual.setLastName(student.getLastName());
	
		
		actual.setEmail(student.getEmail());
		actual.setPhone(student.getPhone());
		actual.setProfile(student.getProfile());
		actual.setAddress(student.getAddress());
		
	}

}
