package in.nit.service.imp;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nit.helper.UserFoundException;
import in.nit.model.Student;
import in.nit.model.StudentRole;
import in.nit.repo.RoleRepository;
import in.nit.repo.StudentRepository;
import in.nit.service.IStudentService;

@Service
public class StudentServiceImp implements IStudentService {
	@Autowired
	private StudentRepository repo;
	@Autowired
	private RoleRepository roleRepo;

	@Override
	public Student createStudent(Student student, Set<StudentRole> studentRoles) throws Exception {
		// TODO Auto-generated method stub
		Student local = this.repo.findByUsername(student.getUsername());
		if (local != null) {
			System.out.println("user already there!!");
			throw new UserFoundException();
		} else {
			// create Student
			for (StudentRole sr : studentRoles) {
				roleRepo.save(sr.getRole());

			}

			student.getStudentRoles().addAll(studentRoles);
			// this.repo.save(student);
			local = this.repo.save(student);
		}
		return local;
	}
	

	@Override
	public List<Student> searchStudents(String query) {
		// TODO Auto-generated method stub
		List<Student> students = repo.searchStudents(query);
		return students;
	}

// gettting student name bu username
	@Override
	public Student getStudent(String username) {
		return 	this.repo.findByUsername(username);
	
	}
	@Override
	public void deleteStudent(Integer id) {
		// if given id is present then we can perform delete operation
		repo.deleteById(id);
	}

	/*
	 * public Integer saveStudent(Student s) { // After save primary key is
	 * generated automatically in @GeneratedValue s=repo.save(s); return s.getId();
	 * } */
	
	// if student id exit then we perform update operation

	public void updateStudent(Student s) {
		// TODO Auto-generated method stub
		repo.save(s);

	}



	@Override
	public Optional<Student> getOneStudent(Integer id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public List<Student> getAllStudent() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}
 
	@Override
	public boolean isStudentExit(Integer id) {
		// if it exit i will perform Update Operation ,otherwise not perform
		return repo.existsById(id);
	}
	

}
