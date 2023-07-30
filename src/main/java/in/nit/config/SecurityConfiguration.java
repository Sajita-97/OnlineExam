package in.nit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.nit.service.imp.UserDetailsServiceImp;

@Configuration
@EnableMethodSecurity

public class SecurityConfiguration  {
	@Autowired
	UserDetailsServiceImp userDetailsServiceImp;
	@Autowired
	JwtAuthenticationEntryPoint unauthorizedHandler;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	  //used in login for authentication of password
	  @Bean
	    public AuthenticationManager authenticationManager(
	                                 AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }
	
	@SuppressWarnings("deprecation")
	@Bean
    public static  BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	 @Bean
	  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.csrf(csrf -> csrf.disable())
              .cors(cors -> cors.disable())
                 .authorizeHttpRequests()
                 .requestMatchers("/generate-token", "/student/").permitAll()
                 .requestMatchers(HttpMethod.OPTIONS).permitAll()


                 .anyRequest()
                 .authenticated()
                 .and()
                 .exceptionHandling(handling -> {
					try {
						handling.authenticationEntryPoint(this.unauthorizedHandler)
						         .and()
						         .sessionManagement(management -> management
						                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
					} catch (Exception e) {
					
						e.printStackTrace();
					}
				});

               http.addFilterBefore(this.jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class );
				return http.build();
	  }
	 

	  protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		   auth.userDetailsService(this.userDetailsServiceImp).passwordEncoder(passwordEncoder());
		  
	  }

}
