package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller // 해당 경로 밑에 있는 파일을 리턴한다. 즉 이것의 기본 경로인 src/main/resources/static의 하위에 있는 파일을 리턴한다.
public class TempControllerTest {
    
    // http://localhost:8000/blog/temp/home
    @GetMapping("/temp/home")
    public String tempHome(){
        System.out.println("tempHome()");

        // 파일 리턴 기본경로 : src/main/resources/static
        // 리턴명 : /home.html
        // 풀경로 : src/main/resources/static/home.html
        return "/home.html";
    }
    @GetMapping("/temp/jsp")
    public String tempJsp(){
        // prefix : /WEB-INF/views
        // surfix : .jsp
        // 풀네임 : /WEB-INF/views/test.jsp
        return "test";
    }

}
