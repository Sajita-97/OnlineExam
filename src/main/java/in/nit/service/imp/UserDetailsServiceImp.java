package in.nit.service.imp;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;
import in.nit.model.Student;
import in.nit.repo.StudentRepository;
@Service
public class UserDetailsServiceImp implements UserDetailsService {
	private Logger log = (Logger) LoggerFactory.getLogger(getClass());
	@Autowired
	private StudentRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Student student = this.repo.findByUsername(username);
		if (student == null) {
			log.info("user not found");
			throw new UsernameNotFoundException("No User Found");
		}
		return student;
	}

}
