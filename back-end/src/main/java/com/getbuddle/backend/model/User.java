package com.getbuddle.backend.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder 
@Entity // User 클래스가 MySQL에 테이블이 자동생성된다.
@DynamicInsert // Insert할때 null 인 필드는 제외하고 Insert 한다. ==> 다만 남용하면 좋지않음. 어노테이션을 쓸게 너무 많아진다.
public class User {

	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id;

	@Column(nullable = false, length = 100, unique = true)
	private String username; // 아이디

	@Column(nullable = false, length = 100) // 비밀번호를 암호화(해쉬) 할 것이므로 length를 길게 잡음
	private String password;

	@Column(nullable = false, length = 50)
	private String nickname;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	private int imageId;
	
	@Lob
	private String comment;
	
	@CreationTimestamp
	private Timestamp createDate;
}
