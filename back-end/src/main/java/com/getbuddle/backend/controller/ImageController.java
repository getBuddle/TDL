package com.getbuddle.backend.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.getbuddle.backend.dto.ResponseDto;
import com.getbuddle.backend.model.Image;
import com.getbuddle.backend.service.ImageService;
import com.getbuddle.backend.util.MediaUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageController {
		
	@Autowired
	private ImageService imageService;
	
	// 이미지 조회
	@GetMapping("/api/images/{imageId}")
	public ResponseEntity<byte[]> findById(@PathVariable int imageId) throws IOException {
		
		Image image = imageService.findById(imageId);
		
		// 이미지 파일 확장자 불러오기
		String formatName = image.getImageOriName().substring(image.getImageOriName().lastIndexOf(".")+1);
		
		// 확장자에 맞는 MediaType을 헤더에 세팅
		HttpHeaders headers = new HttpHeaders();
		MediaType mType = MediaUtils.getMediaType(formatName);
		headers.setContentType(mType);
		
		InputStream in = new FileInputStream(image.getImagePath()+image.getImageName());

        return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);

	}
		
	// 이미지 등록
	@PostMapping("/api/images")
	public ResponseEntity<ResponseDto> join(@RequestParam("image") MultipartFile requestImage) {
		
		Image saveImage = imageService.save(requestImage);
		
		ResponseDto responseDto = new ResponseDto();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setMessage("이미지 등록 성공");
        responseDto.setData(saveImage);
		
		return new ResponseEntity<ResponseDto>(responseDto, headers, HttpStatus.OK);
		
	}
	
	// 이미지 수정
	@PutMapping("/api/images/{imageId}")
	public String update(@PathVariable int imageId, @RequestParam("image") MultipartFile requestImage) {
		
		// DB 정보 업데이트
		imageService.put(imageId, requestImage);
		
		return "SUCCESS";
	}
	
	// 이미지 삭제
	@DeleteMapping("/api/images/{imageId}")
	public String deleteById(@PathVariable int imageId){
		
		// 삭제할 이미지의 정보를 미리 불러오기
		Image deleteImage = imageService.findById(imageId);
		
		// DB에서 이미지 삭제
		imageService.deleteById(imageId);
		
		// 서버에 저장된 이미지 삭제
		File deleteFile = new File(deleteImage.getImagePath()+deleteImage.getImageName());
		if (deleteFile.exists()) {
			deleteFile.delete();
		}
		
		return "SUCCESS";
	}

}
