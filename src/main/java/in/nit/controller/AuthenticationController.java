package in.nit.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.nit.config.JwtUtil;
import in.nit.model.JwtRequest;
import in.nit.model.JwtResponse;
import in.nit.model.Student;
import in.nit.service.imp.UserDetailsServiceImp;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class AuthenticationController {

	@Autowired
	private UserDetailsServiceImp userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/generate-token")
	public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception
	{
		this.authenticate(jwtRequest.getUsername(),jwtRequest.getPassword());
		UserDetails userDetails= this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtUtil.generateToken(userDetails);
		JwtResponse response=new JwtResponse();
		response.setToken(token);
		return new ResponseEntity<JwtResponse>(response,HttpStatus.OK);
		
	}

	private void authenticate(String username, String password) throws Exception {
		// TODO Auto-generated method stub

		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		   } catch (BadCredentialsException e) {
			System.out.println("invalid details");
			throw new Exception("invalid username or password");
		}

	}
	@GetMapping("/current-user")
	public Student getCurrentUser(Principal principal) {
		
	return	((Student)this.userDetailsService.loadUserByUsername(principal.getName()));
	}

}
