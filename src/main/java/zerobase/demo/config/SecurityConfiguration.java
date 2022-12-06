package zerobase.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import zerobase.demo.service.UserService;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final UserService userService;

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();

		http.authorizeRequests()
			.antMatchers(
				"/**"
			)
			.permitAll();

//		http.authorizeRequests()
//			.antMatchers("/admin/**")
//			.hasAuthority("ROLE_ADMIN");

		http.formLogin()
			.loginProcessingUrl("/user/login")
			.defaultSuccessUrl("/user/loginSuccess")
			.failureUrl("/user/loginFail")
			.permitAll();

		super.configure(http);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(getPasswordEncoder());

		super.configure(auth);
	}
}
