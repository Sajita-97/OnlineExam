package in.nit;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




//import in.nit.model.Role;
//import in.nit.model.Student;
//import in.nit.model.StudentRole;
//import in.nit.service.IStudentService;

@SpringBootApplication
public class SpringBootStudentManagementWithRestApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootStudentManagementWithRestApiApplication.class, args);
        System.out.println("Start Student Management......");
	}

}
