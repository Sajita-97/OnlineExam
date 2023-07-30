package in.nit.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import in.nit.model.Role;
import in.nit.model.Student;
import in.nit.model.StudentRole;
import in.nit.service.IStudentService;
import in.nit.util.StudentUtil;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins="http://localhost:4200")
public class StudentController {
	private Logger log=(Logger) LoggerFactory.getLogger( StudentController.class);
	
   @Autowired
   private IStudentService  service;
   @Autowired
	private StudentUtil util;
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;
   
   @PostMapping("/")
   public Student createStudent(@RequestBody Student student) throws Exception {
	  student.setProfile("default.png");
	   student.setPassword(this.passwordEncoder.encode(student.getPassword()));
	   Set<StudentRole>roles=new HashSet<>();
	   Role role=new Role();
	   role.setRoleId(3);
	   role.setRoleName("NORMAL");
	   
	   
	   StudentRole studentRoles=new StudentRole();
	   studentRoles.setRole(role);
	   studentRoles.setStudent(student);
	   roles.add(studentRoles);
	   
	   return this.service.createStudent(student, roles);
   }
   @GetMapping("/{username}")
   public Student getStudent(@PathVariable String username) {
	  return  this.service.getStudent(username);
   }
 //  delete one record based on id  if id exit then delete  else return message
	
	
	 
	@DeleteMapping("/{id}")
	public ResponseEntity<String>deleteStudent(@PathVariable Integer id){
		log.info("Enter into delete method");
		ResponseEntity<String>resp=null;
		
		try {
			log.info("Make service call for data check");
		    boolean exist = service.isStudentExit(id);	
		      if(exist) {
		    	  service.deleteStudent(id);
		    	  log.info("Student exist with given id and deleted=>"+id);
		    	  resp=new ResponseEntity<String>("Student '"+id+"' deleted",
		    			  HttpStatus.OK);
		      }
		      else {
		    	  log.warn("Student id not exist=>"+id);
		    	  resp=new ResponseEntity<String>("Student '"+id+"' not Exist",HttpStatus.BAD_REQUEST);  
		    	 
		      }
			
		} catch (Exception e) {
			log.error("Unable to perform delete operation"+e.getMessage());
			resp=new ResponseEntity<String>("Unable to delete",HttpStatus.INTERNAL_SERVER_ERROR);  
			e.printStackTrace();
		
		}

    return resp;
	 }
	


	
	/* Read JSON (student) and convert into object format 
	 * store in database and return a message.*/


	@GetMapping("/all")
	public ResponseEntity<?>getAllStudent(){
		log.info("Enter into  student fetch operation");
		ResponseEntity<?> resp=null;
		try {
			log.info("About to call student fetch service operation");
			List<Student> list=service .getAllStudent();
			if(list!=null && !list.isEmpty()) {
				log.info("Data is not Empty =>"+list.size());
				list.sort((s1,s2)->s1.getFirstName().compareTo(s2.getFirstName()));
				resp=new ResponseEntity <List<Student>>(list,
						HttpStatus.OK);
			}
			else {
				log.info("No student exit:size"+list.size());
				//resp=new ResponseEntity<String>(HttpStatus.NO_CONTENT);
				resp=new ResponseEntity <String>("Student not Found",
						HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("Unable to fect student data Problem is:"+e.getMessage());
			resp=new ResponseEntity <String>("Unable to fetch data",
					HttpStatus.INTERNAL_SERVER_ERROR);
			
           e.printStackTrace();

		}
		log.info("About to exit fetch all Response ");
		
		return resp;
	}
	
	
	/* get one student based on ID
	 * if exist then return student object
	 * else return message
	 */
	@GetMapping("/one/{id}")
	public ResponseEntity<?>getOne(@PathVariable Integer id)
	{
		log.info("Enter into get One student Data");
		
		ResponseEntity<?> resp=null;
		
		try {
			log.info("About to make service call  to fetch one record");
			
			Optional<Student>opt = service.getOneStudent(id);
			if(opt.isPresent()) {
				log.info(" Student Exit=>"+id );
				resp=new ResponseEntity<Student>(opt.get(),
						HttpStatus.OK);
			}
			else {
				log.warn(" Given student id not exit=>"+id);
				resp=new ResponseEntity<String>("Student ' "+id+" ' not Exit",HttpStatus.BAD_REQUEST);
			
			}
			
		} catch (Exception e)
		   {
			log.error("Unable to fetch student id"+e.getMessage());
			resp=new ResponseEntity<String>("Unable to fetch  id",
					HttpStatus.INTERNAL_SERVER_ERROR);
			
			e.printStackTrace();
		  }
		
		log.info("Enter into  student fetch operation");
		return resp;
	}
	
	
	
	// update studentdata
	@PutMapping("/modify/{id}")
	public ResponseEntity<String>updateStudent(
			@PathVariable Integer id,
			@RequestBody Student student){
		log.info("Enter into student update method");
		ResponseEntity<String>resp=null;
		
		try {
			log.info("About to check student id exist or not in db");
		
		Optional<Student>opt=service.getOneStudent(id);
		if(opt.isPresent()) {
			log.info("student present in datbase");
			Student actual=opt.get();
			/*actual.setName(student.getName());
			actual.setFee(student.getFee());
			actual.setCourse(student.getCourse());
			actual.setEmail(student.getEmail());
			actual.setAddr(student.getAddr());
			*/
			 util.mapToActualObject(actual,student);
			service.updateStudent(actual);
			resp=new ResponseEntity<String>("Student '"+id+"' updated",HttpStatus.RESET_CONTENT);
			log.info("Student  update done sucessfully");
		
	 }
		else {
			log.info("Student id not exit=>"+id);
			resp=new ResponseEntity<String>("Student ' "+id+" ' not exist",HttpStatus.BAD_REQUEST);
		}
	
		} catch (Exception e) {
				
			log.error("Unable to Update student data"+e.getMessage());
			 resp=new ResponseEntity<String>("Unable to process update",
					 HttpStatus.INTERNAL_SERVER_ERROR);
			 e.printStackTrace();
			}
		return resp;
 }
	@GetMapping("/search")
	public ResponseEntity<List<Student>> searchStudents(@RequestParam("query") String query){
		 return ResponseEntity.ok(service.searchStudents(query));
	} 
}
/*
@PostMapping("/save")
public ResponseEntity<String> saveStudent(@RequestBody Student student) {
	log.info("Enter into  student save operation");
	log.info("Enter into  student Name: " + student.getFirstName());
	ResponseEntity<String> resp=null;
	try {
		//stored in database
		log.info("About to call save operation");
		Integer id =	service.saveStudent(student);
		// if successfully save  i want to return some message
		log.debug("Student '"+id+"' created");
		String body="Student '"+id+"' Created";
		resp = new ResponseEntity<String>(body, HttpStatus.CREATED); //status 201
		log.info("Student response constructed");
		
	 } catch (Exception e) {
		 log.error("Unable to save student :Problem is :"+e.getMessage());
			resp = new ResponseEntity<String>("Unable to Create Student", HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();	
	 }
	
	log.info("exit the response");
	return resp;
	} 
 fetch data from database using service and return as JSON
  else No Data Found message
 */
	

	