package com.getbuddle.backend.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
	private int id;

	private String username;

	private String nickname;
	
	private String email;
	
	private int imageId;
	
	private String comment;
	
	private Timestamp createDate;
    
}
