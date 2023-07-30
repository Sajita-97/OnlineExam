package in.nit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import in.nit.model.Student;

@EnableJpaRepositories
public interface StudentRepository extends JpaRepository<Student, Integer> {
	@Query("SELECT p FROM Student p WHERE " + "p.firstName LIKE CONCAT('%',:query, '%')"
			+ "Or p.lastName LIKE CONCAT('%', :query, '%')")
	// @Query(value="select * from Student where firstName like 'query%' or
	// 'lastName like 'query%",nativeQuery=true)
	List<Student> searchStudents(String query);

	public Student findByUsername(String username);

}
