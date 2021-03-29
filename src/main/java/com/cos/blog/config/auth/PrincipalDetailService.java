package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service //bean등록
public class PrincipalDetailService implements UserDetailsService{
    

    @Autowired
    private UserRepository userRepository;


    // 스프링이 로그인 요청을 가로챌 때 username, password를 가져오는데 
    // password 부분 처리는 자동으로 되는데 
    // usernamedl DB에 있는지 확인만 해주면 된다. loadUserByUsername라는 얘가 확인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User principal = userRepository.findByUsername(username)
            .orElseThrow(()->{
                return new  UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username);
            });
        return new PrincipalDetail(principal); // 시큐리티 유저 정보에 principal이 저장됨. 타입이 userDetails 타임
    }


}
