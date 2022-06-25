package com.recipes.jwt.config;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.recipes.jwt.controller.JwtController;
import com.recipes.jwt.filter.JwtFilter;
import com.recipes.jwt.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity //allows Spring to find and automatically apply the class to the global Web Security.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	Logger logger = LoggerFactory.getLogger(SecurityConfig.class); 

	@Autowired
	private JwtFilter jwtFilter;
	@Autowired
	private CustomUserDetailService userDetailsService;
	
	
	/*The implementation of UserDetailsService will be used for configuring DaoAuthenticationProvider by 
	 * AuthenticationManagerBuilder.userDetailsService() method.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	private static final String[] PUBLIC_URLS= {
			"/authenticate",
			"/signup",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
	
	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	/*  We override the configure(HttpSecurity http) method from WebSecurityConfigurerAdapter interface.
	 *  It tells Spring Security how we configure CORS and CSRF, when we want to require all users to be authenticated 
	 *  or not, which filter (jwtFilter) and when we want it to work (filter before UsernamePasswordAuthenticationFilter) */
	
  @Override
protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable().cors().and()
	.authorizeRequests().antMatchers(PUBLIC_URLS).permitAll().anyRequest().authenticated().and()
	.exceptionHandling().and()
	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	
	logger.info("jwt Filter was registered");





}
	
	
	
}
