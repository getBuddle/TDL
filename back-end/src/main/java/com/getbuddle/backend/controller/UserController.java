package com.getbuddle.backend.controller;

import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.getbuddle.backend.dto.ResponseDto;
import com.getbuddle.backend.model.User;
import com.getbuddle.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {
		
	@Autowired
	private UserService userService;
	
	// 회원가입
	@PostMapping("join")
	public String join(@RequestBody User user) {
		userService.save(user);
		
		return "SUCCESS";
	}
	
	// 회원 찾기
	@GetMapping("/api/accounts/{accountId}")
	public ResponseEntity<ResponseDto> findById(@PathVariable int accountId) {
		
		User userProfile = userService.findById(accountId);
		
		ResponseDto responseDto = new ResponseDto();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setMessage("회원정보 조회 성공");
        responseDto.setData(userProfile);
		
		return new ResponseEntity<ResponseDto>(responseDto, headers, HttpStatus.OK);
	}
	
	// 회원수정
	// 프로필 이미지 업로드는 미구현
	@PatchMapping("/api/accounts/{accountId}")
	public String update(@PathVariable int accountId, @RequestBody  Map<String, String> form
//			,
//			@RequestParam("file") MultipartFile file
			) {

		userService.patch(accountId, form);
		
		return "SUCCESS";
	}
	
	// 회원삭제
	@DeleteMapping("/api/accounts/{accountId}")
	public String deleteById(@PathVariable int accountId){
		
		userService.deleteById(accountId);
		
		return "SUCCESS";
	}

}
