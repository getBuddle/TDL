package com.getbuddle.backend.config.jwt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

// 시큐리티가 filter를 가지고 있는데, 그 필터중 BasicAuthenticationFilter라는 것이 있음.
// 권한이나 인증이 필요한 특정 주소를 요청했을 때, 위의 필터를 무조건 타게 되어있음.
// 만약 권한이나 인증이 필요한 주소가 아니라면 위의 필터를 타지 않음.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	// 인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 타게 된다.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("인증이나 권한이 필요한 주소가 요청됨");
			
//		System.out.println("getRequestURL : "+request.getRequestURL().toString());
		
		if (request.getRequestURL().toString().equals("http://localhost:5000/join")) {
			System.out.println("회원가입은 JWT 인증이 필요하지 않음");
			chain.doFilter(request, response);		// 다시 필터를 타도록 함
			return;
		}
		
		String jwtHeader = request.getHeader("Authorization");
//		System.out.println("jwtHeader : "+jwtHeader);
		
		// header가 있는지, 정상적인지 확인
		if (jwtHeader == null) {
//		if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
//			chain.doFilter(request, response);		// 다시 필터를 타도록 함
			
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			
			PrintWriter out = response.getWriter();

			out.println("{\r\n" + 
					"    \"status\": 401,\r\n" + 
					"    \"message\": \"JWT Token does not exist\"\r\n" + 
					"}");
			
			return;
		}
		
		// JWT 토큰을 검증해서 정상적인 사용자인지 확인
		String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");	// Bearer(+공백)을 공백으로 치환함
		
		String username =
				JWT.require(Algorithm.HMAC512("ddevrang")).build().verify(jwtToken).getClaim("username").asString();
		
		// 서명이 정상적으로 된 경우
		if (username != null) {
			chain.doFilter(request, response);		// 다시 필터를 타도록 함
		}else {
			
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			
			PrintWriter out = response.getWriter();

			out.println("{\r\n" + 
					"    \"status\": 401,\r\n" + 
					"    \"message\": \"JWT Token is not valid\"\r\n" + 
					"}");
			
			return;
		}
	}
}
