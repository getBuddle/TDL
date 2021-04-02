package com.getbuddle.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.getbuddle.backend.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	
}
