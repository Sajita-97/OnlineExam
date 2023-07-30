package in.nit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="StudentRole")
public class StudentRole {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer studentRoleId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Student student;
	@ManyToOne
	private Role role;
	public StudentRole()
	{
		
	}
	
	 /*public StudentRole(Integer studentRoleId, Student student, Role role) {
	
		this.studentRoleId = studentRoleId;
		this.student = student;
		this.role = role;
	}*/
	public Integer getStudentRoleId() {
		return studentRoleId;
	}
	public void setStudentRoleId(Integer studentRoleId) {
		this.studentRoleId = studentRoleId;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

}
