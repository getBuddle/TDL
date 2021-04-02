package com.getbuddle.backend.service;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.getbuddle.backend.model.User;
import com.getbuddle.backend.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. (IoC를 해준다 => 메모리에 띄워준다.)
@Service
public class UserService {

	@Autowired // DI
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
		
	@Transactional(readOnly = true)
	public User findByUsername(String username) { 
		User user = userRepository.findByUsername(username).orElseGet(()->{

			return new User();
		});
		
		return user;
	}
		
	@Transactional
	public void save(User user) {
		String rawPassword = user.getPassword();	// 1234 원문
		String encPassword = encoder.encode(rawPassword);		// 해쉬(단방향 암호화)
		user.setPassword(encPassword);
		
		userRepository.save(user);
	}
	
	@Transactional(readOnly = true)
	public User findById(int accountId) { 
		User user = userRepository.findById(accountId).orElseGet(()->{

			return new User();
		});

		return user;
	}
	
	@Transactional
	public void patch(int accountId, Map<String, String> data) {
		User user = userRepository.findById(accountId) // 영속화시킴
				.orElseThrow(() -> {
					return new IllegalArgumentException("회원찾기 실패 : 아이디를 찾을 수 없습니다.");
				});
		
		Set<String> keySet = data.keySet();
		
		if (keySet.contains("nickname")) {
			String nicknameString = data.get("nickname");
			user.setNickname(nicknameString);
		}
		
		if (keySet.contains("email")) {
			String emailString = data.get("email");
			user.setEmail(emailString);
		}
		
		if (keySet.contains("imageId")) {
			String imageIdString = data.get("imageId");
			user.setImageId(Integer.parseInt(imageIdString));
		}
		
		if (keySet.contains("comment")) {
			String commentString = data.get("comment");
			user.setComment(commentString);
		}

		// 해당 함수 종료 시(Service가 종료될 때) 트랜잭션도 종료되는데, 이 때 더티체킹을 하면서 자동업데이트가 된다.(DB로 flush : 커밋이 된다.)
	}
		
	@Transactional
	public void deleteById(int accountId) {
		userRepository.deleteById(accountId);
	}

}
