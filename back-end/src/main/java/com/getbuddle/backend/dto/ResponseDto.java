package com.getbuddle.backend.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseDto {
    private int status;
    private String message;
    private Object data;

    public ResponseDto() {
        this.status = HttpStatus.BAD_REQUEST.value();
        this.data = null;
        this.message = null;
    }
    
//    public enum StatusEnum {
//
//        OK(200, "200"),
//        BAD_REQUEST(400, "400"),
//        NOT_FOUND(404, "404"),
//        INTERNAL_SERER_ERROR(500, "500");
//
//        int statusCode;
//        String code;
//
//        StatusEnum(int statusCode, String code) {
//            this.statusCode = statusCode;
//            this.code = code;
//        }
//    }
    
}
