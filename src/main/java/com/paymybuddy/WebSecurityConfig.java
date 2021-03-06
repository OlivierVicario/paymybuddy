package com.paymybuddy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.paymybuddy.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Database users
		auth.authenticationProvider(authenticationProvider());

		// InMemory users
				String encodedPassword = passwordEncoder().encode("password");
				auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
					.withUser("olivier").password(encodedPassword).roles("USER"); 
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/private/**").authenticated().anyRequest().permitAll()
				.and().formLogin().usernameParameter("email").defaultSuccessUrl("/private/dashboard").permitAll()
				.and().rememberMe().tokenValiditySeconds(7 * 24 * 60 * 60) 
				.key("AbcdefghiJklmNoPqRstUvXyz") 
				.and().logout().logoutSuccessUrl("/").permitAll();
	}
}
