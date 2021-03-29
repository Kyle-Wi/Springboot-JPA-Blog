package com.cos.blog.handler;



import com.cos.blog.dto.ResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 모든 exception이 발생하면 이쪽으로 보내준다. 즉 모든 페이지에서 illegalArgumentException 에러가 나오면 밑에 있는 ExceptionHandler로 보낸다.
@RestController
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value=Exception.class)
    public ResponseDto<String> handleArgumentException(Exception e){
        
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
} 
