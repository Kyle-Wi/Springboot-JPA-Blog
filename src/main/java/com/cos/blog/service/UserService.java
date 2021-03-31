package com.cos.blog.service;



import javax.transaction.TransactionScoped;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// 스프링이 컴포넌트 스캔을 통해서 bean에 등록을 해줌, ioc를 해준다.
// 서비스 분리
@Service 
public class UserService {
    

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void 회원가입(User user){
    
        String rawPassword = user.getPassword();
        String encodedPassword = encoder.encode(rawPassword); //해슁된 비밀번호
        user.setPassword(encodedPassword);
        user.setRole(RoleType.USER);
        
        userRepository.save(user); 

    }

    @Transactional(readOnly = true)
    public User 회원찾기(String username){
        User user = userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });
        return user;
    }

    @Transactional
    public void 회원수정(User user){
        
        // 수정 시 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
        // select를 해서 User오브젝트를 db로 부터 가져오는 이유는 영속화를 하기 위해서!
        // 영속화된 오브젝트를 변경하면 자동으로 db에 update문을 날려준다.
        User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패");
        });

        
        // validation kakao면 아무것도 변경 못하게 
        if(persistance.getOath() == null || persistance.getOath().equals("")){
            String rawPassword = user.getPassword();
            String encodedPassword = encoder.encode(rawPassword);
            persistance.setPassword(encodedPassword);
            persistance.setEmail(user.getEmail());

        }

        // 회원수정 함수 종료 시 = 서비스 종료 = 트랜잭션 종료 = 커밋이 자동으로 작동
        // 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날림
    }



    // 전통적인 로그인 방식
    // @Transactional(readOnly = true) // select 할 때 트랜잭션 시작, 서비스 종료 시 트랜잭션 종료 ( 정합성 보장 )
    // public User 로그인(User user){

    //     return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        
    // }
}