package com.getbuddle.backend.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.getbuddle.backend.model.Image;
import com.getbuddle.backend.repository.ImageRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. (IoC를 해준다 => 메모리에 띄워준다.)
@Service
public class ImageService {

	@Autowired // DI
	private ImageRepository imageRepository;
	
	@Value("${file.path}")
	private String filePath;
		
	@Transactional(readOnly = true)
	public Image findById(int imageId) { 
		Image image = imageRepository.findById(imageId).orElseGet(()->{
			return new Image();
		});

		return image;
	}
		
	@Transactional
	public Image save(MultipartFile requestImage) {
		
		Image image = new Image();
		UUID uuid = UUID.randomUUID();

		// 파일명 중복을 예방하기위해 uuid를 결합
		String saveImageName = uuid + "_" + requestImage.getOriginalFilename();

		try {
				requestImage.transferTo(new File(filePath + saveImageName));  // 경로에 업로드
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		image.setImageName(saveImageName);
		image.setImageOriName(requestImage.getOriginalFilename());
		image.setImagePath(filePath);
		
		Image saveImage = imageRepository.save(image);
		
		return saveImage;
	}
	
	@Transactional
	public void put(int imageId, MultipartFile requestImage) {
		Image image = imageRepository.findById(imageId) // 영속화시킴
				.orElseThrow(() -> {
					return new IllegalArgumentException("이미지 조회 실패 : 이미지를 찾을 수 없습니다.");
				});
				
		UUID uuid = UUID.randomUUID();

		// 파일명 중복을 예방하기위해 uuid를 결합
		String saveImageName = uuid + "_" + requestImage.getOriginalFilename();

		try {
				// 서버에 새로운 이미지 저장
				requestImage.transferTo(new File(filePath + saveImageName));
				
				// 서버에 저장된 이전 이미지 삭제
				File deleteFile = new File(image.getImagePath()+image.getImageName());
				if (deleteFile.exists()) {
					deleteFile.delete();
				}
				
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		image.setImageName(saveImageName);
		image.setImageOriName(requestImage.getOriginalFilename());
		image.setImagePath(filePath);
				
		// 해당 함수 종료 시(Service가 종료될 때) 트랜잭션도 종료되는데, 이 때 더티체킹을 하면서 자동업데이트가 된다.(DB로 flush : 커밋이 된다.)
	}
		
	@Transactional
	public void deleteById(int accountId) {
		imageRepository.deleteById(accountId);
	}

}
