package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;



// 인증이 안 된 사용자들이 출입할 수 있는 경로를 /auth/** 허용 o
// 그냥 주소가 / 이면 index.jsp 허용 o
// static이하에 있는 /js/**, /image/**, /css/** 허용


// 요청 분기
@Controller
public class UserController {

    // 회원가입, 회원찾기에 필요함
    @Autowired
    private UserService userService;

    // 카카오 로그인할 때 필요한 정보인데 이거 노출되면 안된다.
    @Value("${cos.key}")
    private String cosKey;
    // 로그인 처리에 필요함
    @Autowired
    private AuthenticationManager authenticationManager;
    
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
    public String updateForm(@AuthenticationPrincipal PrincipalDetail principal) { // AuthenticationPrincipal이라는 객체를 들고 온다.
        return "user/updateForm";
    }    

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallBack(String code){ // 데이터를 리턴해주는 controller 함수
        
        // POST방식으로 KEY-VALUE 데이터 형식으로 요청보냄(카카오 쪽으로)
        // Restofit2
        // OkHttp
        // RestTemplate
        RestTemplate rt = new RestTemplate();

        //HttpHeader 객체 생성 
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        
        // HttpBody 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "f3594b4284dfb13a8a7ec46366b9ff0e");
        params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
        params.add("code",code);

        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // Http 요청하기 - POST 방식으로, 그리고 response를 받음
        ResponseEntity <String>response = rt.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            kakaoTokenRequest,
            String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        OAuthToken oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(),OAuthToken.class);
        } catch(JsonMappingException e){
            e.printStackTrace();
        } catch(JsonProcessingException e){
            e.printStackTrace();
        }
        
        System.out.println("getAccessToken : "+ oauthToken.getAccess_token());
        
        

        // 사용자 access token가지고 정보 요청하기
        // RestTemplate
        RestTemplate rt2 = new RestTemplate();

        //HttpHeader 객체 생성 
        org.springframework.http.HttpHeaders headers2 = new org.springframework.http.HttpHeaders();
        headers2.add("Authorization", "Bearer "+ oauthToken.getAccess_token());
        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        
        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        // Http 요청하기 - POST 방식으로, 그리고 response를 받음
        ResponseEntity<String> response2 = rt2.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            kakaoProfileRequest,
            String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();

        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch(JsonMappingException e){
            e.printStackTrace();
        } catch(JsonProcessingException e){
            e.printStackTrace();
        }


        // User object : username, password, email
        System.out.println("kakao id : " + kakaoProfile.getId());
        System.out.println("kakao email : " + kakaoProfile.getKakao_account().getEmail());

        System.out.println("카카오 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
        System.out.println("카카오 이메일 : " +  kakaoProfile.getKakao_account().getEmail());

        // UUID란 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
        // UUID tempPassword = UUID.randomUUID();
        System.out.println("블로그 서버 패스워드 : " + cosKey);


        User kakaoUser = User.builder()
                        .username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
                        .password(cosKey)
                        .oath("kakao")
                        .email(kakaoProfile.getKakao_account().getEmail()).build();


        // 가입자 혹인 비가입자 체크 확인
        User originUser = userService.회원찾기(kakaoUser.getUsername());                
        
        if(originUser.getUsername() == null){
            System.out.println("------------------기존 회원 아닙니다. 자동으로 회원가입이 진행됩니다.-------------------");
            userService.회원가입(kakaoUser);
        };
        
        // 로그인 처리
        System.out.println("자동 로그인이 진행됩니다.");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/"; // 코드 값이 리턴되는 것을 보면 인증 성공.
    }

}

