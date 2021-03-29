package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import com.cos.blog.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;


// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면  UserDetails type의 오브젝트를 
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
// 밑의 principalDetail을 저장하는 것.
@Getter
public class PrincipalDetail implements UserDetails{
    
    private User user; //컴포지션
    
    public PrincipalDetail(User user){
        this.user = user;
    }
    
    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return user.getPassword();
    }
    
    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return user.getUsername();
    }
    

    // 계정이 만료되었는지 확인하는 메소드(true:만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    
    // 계정이 안 잠겨있는지 확인하는 메소드(true:잠기지않음)
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }
    
    // 비밀번호가 만료되지 않았는지 확인하는 메소드(true:만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    
    // 계정활성화가 되어 있는지 확인하는 메소드(true:활성화)
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }


    // 계정의 권한 리스트를 리턴해주는 메소드. 만약에 권한이 여러 개면 for문을 돌면서 리턴해야하지만 지금은 하나 밖에 없어서
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();

        // 자바는 함수를 요인으로 받을 수 없다. 객체로 받아야하는데 이를 람다로 받을 수 있음.
        // collectors.add(new GrantedAuthority(){
            
        //     @Override
        //     public String getAuthoriry(){
        //         return "ROLE_"+user.getRole(); // ROLE_USER
        //     }
        // });

        collectors.add(()->{return "ROLE_"+user.getRole();});

        return collectors;
    }   
}
