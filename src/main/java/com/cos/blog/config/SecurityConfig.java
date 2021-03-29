package com.cos.blog.config;

import com.cos.blog.config.auth.PrincipalDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration // 빈 등록 : (ioc 관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private PrincipalDetailService PrincipalDetailService;

    @Bean // Ioc가 된다. 즉 여기서 리턴된 값을 스프링이 관리
    public BCryptPasswordEncoder encodePWD(){

        return new BCryptPasswordEncoder();
    }


    // 시큐리티가 대신 로그인하는데 password를 가로채기 하는데 
    // 해당 password가 뭘로 해슁이 되어 회원가입이 되었는지 알아야 
    // 같은 해쉬로 암호화해서 db에 있는 해쉬와 비교가능. 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(PrincipalDetailService).passwordEncoder(encodePWD()); // 정리하자면 principalDetailService에 있는 정보를 가지고 encoded 된 방식대로 확인한다.
    }

    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
            .csrf().disable() // csrf 토큰 비활성화(테스트 시 걸어두는 게 좋다)
            .authorizeRequests()
                .antMatchers("/","/auth/**", "/js/**","/css/**","/image/**","/dummy/**") // auth로 들어오는 것은 누구나 들어올 수 있다.
                .permitAll()
                .anyRequest() //나머지 요청은 권한이 필요하다
                .authenticated()
            .and()
                .formLogin() // home으로 가면 로그인을 해야한다. 인증이 되어 있지 않다면 form login을 할건데 
                .loginPage("/auth/loginForm") //그 페이지가 이 페이지다. 
                .loginProcessingUrl("/auth/loginProc")// 스프링 시큐리티가 해당 요청오는 url을 intercept해서 대신 해준다.
                .defaultSuccessUrl("/"); // 정상적으로 로그인 성공 시 해당 주소로 이동
                
    
    }           
}
