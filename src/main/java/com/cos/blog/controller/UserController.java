package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




// 인증이 안 된 사용자들이 출입할 수 있는 경로를 /auth/** 허용 o
// 그냥 주소가 / 이면 index.jsp 허용 o
// static이하에 있는 /js/**, /image/**, /css/** 허용


// 요청 분기
@Controller
public class UserController {
    
    @GetMapping("/auth/joinForm")
    public String joinForm(){
        //
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        //
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(){
        return "user/updateForm";
    }    
}
