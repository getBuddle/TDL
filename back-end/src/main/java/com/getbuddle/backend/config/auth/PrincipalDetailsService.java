package com.getbuddle.backend.config.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.getbuddle.backend.model.User;
import com.getbuddle.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// http://localhost:8080/login 주소로 접근될 때 동작하게 된다. (스프링 시큐리티의 로그인 기본주소)	=> 여기서 동작하지 않음.
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService의 loadUserByUsername()");
		Optional<User> userEntity = userRepository.findByUsername(username);
		System.out.println("username = "+username);
		System.out.println("userEntity = "+userEntity);
		return new PrincipalDetails(userEntity.get());
	}

}
