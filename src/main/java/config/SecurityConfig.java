package config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

		//using database auth
		@Autowired
 		private DataSource dataSource;
	
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.jdbcAuthentication()
	            .dataSource(dataSource)
	            .passwordEncoder(passwordEncoder())
	            .usersByUsernameQuery("SELECT email, password, enabled FROM users WHERE email = ?")
	            .authoritiesByUsernameQuery("SELECT email, authority FROM authorities WHERE email = ?");
	    }
	    
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests()
	            	.antMatchers("/css/**", "/js/**").permitAll() // Allow static resources
	                .antMatchers("/register/**").permitAll() // Allow public access to the register controller
	                //.antMatchers("/admin/**").hasRole("ADMIN") // Restrict access to admin routes to ADMIN role
	                .anyRequest().authenticated() // Require authentication for all other routes
	            .and()
	            	.formLogin()
	                //.loginPage("/login") // Custom login page
	            	//.usernameParameter("email")
	                .defaultSuccessUrl("/dashboard", true) // Redirect to home after successful login
	                .permitAll() // Allow everyone to access the login page
	            .and()
	            	.logout()
	                .permitAll(); // Allow everyone to access logout functionality
	    }

//previous implementation, using inMemoryAuth
//	@Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//            .withUser("admin")
//                .password(passwordEncoder().encode("admin123"))
//                .roles("ADMIN")
//             .and()
//             .withUser("salesdept")
//                .password(passwordEncoder().encode("salesdept123"))
//                .roles("SALES")
//            .and()
//            .withUser("customer")
//                .password(passwordEncoder().encode("customer123"))
//                .roles("CUSTOMER");
//    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}//end class
