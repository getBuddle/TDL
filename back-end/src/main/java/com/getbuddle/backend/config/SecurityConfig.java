package com.getbuddle.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import com.getbuddle.backend.config.jwt.JwtAuthenticationFilter;
import com.getbuddle.backend.config.jwt.JwtAuthorizationFilter;
import com.getbuddle.backend.service.UserService;

import lombok.RequiredArgsConstructor;

// 아래의 세가지 옵션은 스프링 시큐리티에 필요한 세트라고 생각하면 된다. 잘 모르겠으면 그냥 셋 다 쓰면 된다.
@Configuration // 빈 등록(IoC 관리) => 빈(Bean) 등록 : 스프링 컨테이너에서 객체를 관리할 수 있도록 하는 것.
@EnableWebSecurity // 시큐리티 필터가 등록이 된다. 관련 설정을 아래에서 한다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CorsFilter corsFilter;
	
	@Autowired
	private UserService userService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(corsFilter)
			.formLogin().disable()
			.httpBasic().disable()
			.addFilter(new JwtAuthenticationFilter(authenticationManager(), userService))
			.addFilter(new JwtAuthorizationFilter(authenticationManager()))
			.authorizeRequests()
//			.antMatchers("/api/**").authenticated()
			.anyRequest().permitAll();
	}
}
