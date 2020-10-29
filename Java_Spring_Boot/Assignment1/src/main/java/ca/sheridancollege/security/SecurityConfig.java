package ca.sheridancollege.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginAccessDeniedHandler denied;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); //Remove before deployment
		http.headers().frameOptions().disable(); //Remove before deployment
		http.authorizeRequests()
			//Restricting HTML pages is wrong, you have to restrict URLs
			.antMatchers("/user/**").hasAnyRole("STUDENT","PROFESSOR")
			.antMatchers("/professor/**").hasRole("PROFESSOR")
			.antMatchers("/","/css/**", "/images/**", "/js/**", "/**").permitAll()//Login not required
			.antMatchers("/h2-console/**").permitAll() //Remove before deployment
			.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/login").permitAll()
		.and()
			.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login?logout")
			.permitAll()
		.and()
			.exceptionHandling()
			.accessDeniedHandler(denied);
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}