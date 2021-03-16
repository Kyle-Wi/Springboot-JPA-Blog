package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


// 사용자가 요청 -> 응답(HTML 파일)
// @Controller


// 사용자가 요청 -> 응답(Data) @RestController
@RestController
public class HttpControllerTest {

    // http://localhost:8080/http/get (select)
    // 인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다.
    // @RequestParam get 요청을 했을 때 데이터를 인자로 받을 수 있다.


    @GetMapping("/http/get")
    public String getTest(Member m){ //http://localhost:8080/http/get?id=1&username=홍길&password=1234&state=배고프다아의 값을 getTest에 넣는다.

        return "get 요청:" + m.getId()+ ", username:" + m.getUsername() +", password:"+ m.getPassword() + ", state:" + m.getState()+", email:" + m.getEmail();
    }

    // http://localhost:8080/http/post (insert)
    @PostMapping("/http/post") // text/plain(평문), application/json
    public String postTest(@RequestBody Member m){  // MessageConverter이라는 스프링부트성능이 자동으로 일을 해준다... 정말 좋다.
        return "post요청" + m.getId()+ ", username:" + m.getUsername() +", password:"+ m.getPassword() + ", state:" + m.getState()+", email:" + m.getEmail();
    }

    // http://localhost:8080/http/put (update)
    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m){
        return "put 요청" + m.getId()+ ", username:" + m.getUsername() +", password:"+ m.getPassword() + ", state:" + m.getState()+", email:" + m.getEmail();
    }

    // http://localhost:8080/http/delete (delete)
    @DeleteMapping("/http/delete")
    public String deleteTest(){
        return "delete 요청";
    }
}
