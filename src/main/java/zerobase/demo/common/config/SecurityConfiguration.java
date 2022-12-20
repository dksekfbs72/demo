package zerobase.demo.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import zerobase.demo.user.service.UserService;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final UserService userService;

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserAuthenticationFailureHandler getFailureHandler() {
		return new UserAuthenticationFailureHandler();
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

		http.authorizeRequests()
			.antMatchers("/menu/**")
			.hasAuthority("ROLE_OWNER")
			.antMatchers("/store/**")
			.hasAuthority("ROLE_OWNER");

		http.authorizeRequests()
			.antMatchers("/admin/**")
			.hasAuthority("ROLE_ADMIN");

		http.formLogin()
			.usernameParameter("userId")
			.passwordParameter("password")
			.failureHandler(getFailureHandler())
			.successForwardUrl("/login/success")
			.permitAll();

		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
			//.logoutSuccessUrl("/user/readMyInfo") 로그아웃 후 url 설정 가능
			.invalidateHttpSession(true);

		http.exceptionHandling()
			.accessDeniedPage("/error/denied");

		super.configure(http);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userService).passwordEncoder(getPasswordEncoder());

		super.configure(auth);
	}
}
