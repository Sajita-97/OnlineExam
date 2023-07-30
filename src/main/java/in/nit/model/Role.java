package in.nit.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="roles")
public class Role {
	@Id
	//@GeneratedValue
	private Integer roleId;
	private String roleName;
	// here we use lazy bcz we when we fetch role we didn't want instance user 
	//when we call the we found user
	@ OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY,mappedBy="role")
	private Set<StudentRole> studentRoles=new HashSet<>();
	
public Role() {
		
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
	public Set<StudentRole> getStudentRoles() {
		return studentRoles;
	}
	public void setStudentRoles(Set<StudentRole> studentRoles) {
		this.studentRoles = studentRoles;
	}
	public Role(Integer roleId, String roleName) {
	
		this.roleId = roleId;
		this.roleName = roleName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	

}
