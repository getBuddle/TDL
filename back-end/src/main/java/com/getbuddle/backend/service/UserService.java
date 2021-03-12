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
//			System.out.println("UserService : findByUsername() 실패");
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
//			System.out.println("UserService : findById() 실패");
			return new User();
		});

		return user;
	}
	
	@Transactional
	public void patch(int id, Map<String, String> form) {
		User user = userRepository.findById(id) // 영속화시킴
				.orElseThrow(() -> {
					return new IllegalArgumentException("회원찾기 실패 : 아이디를 찾을 수 없습니다.");
				});
		
		Set<String> keySet = form.keySet();
		
		if (keySet.contains("nickname")) {
			String nicknameString = form.get("nickname");
			user.setNickname(nicknameString);
		}
		
		if (keySet.contains("email")) {
			String emailString = form.get("email");
			user.setEmail(emailString);
		}
		
//		if (keySet.contains("profileImage")) {
//			String profiileImageString = form.get("profileImage");
//			user.setProfileImage(profiileImageString);
//		}
		
		if (keySet.contains("profileComment")) {
			String profileCommentString = form.get("profileComment");
			user.setProfileComment(profileCommentString);
		}


		// 해당 함수 종료 시(Service가 종료될 때) 트랜잭션도 종료되는데, 이 때 더티체킹을 하면서 자동업데이트가 된다.(DB로 flush :
		// 커밋이 된다.)
		// 따라서 영속화된 board에 수정하고자하는 값만 set해주면 되고, 별도의 다른 명령은 필요없다.
	}
		
	@Transactional
	public void deleteById(int accountId) {
		userRepository.deleteById(accountId);
	}

}
