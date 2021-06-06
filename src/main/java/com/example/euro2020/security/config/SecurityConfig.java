package com.example.euro2020.security.config;

import com.example.euro2020.security.JwtConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtConfigurer jwtConfigurer;

	public SecurityConfig (JwtConfigurer jwtConfigurer) {
		this.jwtConfigurer = jwtConfigurer;
	}

	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
//			.antMatchers("/api/authorization").permitAll()
			.antMatchers(
				"/styles/**", "/js/**", "/images/**", "/bootstrap/**", "/fonts/**",
				"/", "/groups", "/registration**", "/authorization**", "/templates", "/api**"
			).permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/authorization").permitAll()
			.usernameParameter("login")
			.failureUrl("/authorization?error=true")
			.defaultSuccessUrl("/")
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST", true))
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.deleteCookies("JSESSIONID")
			.logoutSuccessUrl("/")
			.and()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.apply(jwtConfigurer);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean () throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	protected PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder(12);
	}
}
