package com.getbuddle.backend.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder 
@Entity // 클래스가 MySQL에 테이블이 자동생성된다.
public class Image {

	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id;

	@Column(nullable = false)
	private String imageName;
	
	@Column(nullable = false)
	private String imageOriName;
	
	@Column(nullable = false)
	private String imagePath;

	@CreationTimestamp
	private Timestamp createDate;
}
