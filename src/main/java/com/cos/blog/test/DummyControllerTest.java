package com.cos.blog.test;


import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



// html파일이 아니라 데이터를 리턴해주는 controller = RestController
@RestController
// @DynamicInsert insert 시에 null인 필드를 제외시켜준다.
public class DummyControllerTest {
    
    @Autowired // 의존성 주입 DI함수
    private UserRepository userRepository;

    @DeleteMapping("dummy/user/delete/{id}")
    public String delete(@PathVariable int id){
        try{
        userRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            return "삭제 실패하였습니다. 해당 id는 존재하지 않습니다.";
        }
        return "삭제되었습니다. id : " + id;

    }

    // email이랑 password를 바꾼다.
    @Transactional // save를 하지 않아도 update가 되는데 더티체킹이란 것과 관련있음
    @PutMapping("dummy/user/{id}") // 이건 put이고 밑의 get도 같은 주소인데 구분을 해준다.
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){ //@RequestBody는 json파일을 받을 수 있게 한다. Json 데이터를 요청 -> java object(MessageConverter의 Jackson라이브러리가 변환해서 받아줌)
        System.out.println("id:" + id);
        System.out.println("password:" + requestUser.getPassword());
        System.out.println("Email:" + requestUser.getEmail());
        System.out.println("Email:" + requestUser.getUsername());

        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정이 실패하였습니다.");
        });

        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
        user.setUsername(requestUser.getUsername());


        
        // save함수는 id를 전달하지 않으면 insert를 해주고
        // save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
        // save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해준다.
        // userRepository.save(user); //insert할 때 사용하는 것이 save()인데 없는 데이터인 즉 createDate같은 값은 null로 바뀐다. 그래서 save로 update를 하려면 모든 데이터를 알거나, 예외처리를 해야한다.

        // 더티 체킹
        // save를 하지 않아도 update가 되는데 왜일까?        
        return user;
    }


    // 모든 데이터를 select하는 방법
    @GetMapping("dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }


    // 한 페이지당 2건의 데이터를 리턴받아 볼 예정
    @GetMapping("dummy/user")
    public List<User> pageList(@PageableDefault(size=2,sort="id",direction=Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable); // Page 타입을 리턴한다

        List<User> users = pagingUser.getContent(); // page타입인데 이를 리스트로도 getContent()를 통해 받을 수 있다.
        return users;
    }


    //{id}파라미터를 전달 받을 수 있다.
    // http://localhost:8000/blog/dummy/user/{id}
    @GetMapping("dummy/user/{id}")
    public User detail(@PathVariable int id) {
        
        // user/4을 찾는데 만약 못찾으면 user가 null이 될 것 아닌가?
        // 그럼 return null 이 되는데 이럼 문제가 되지 않는가?
        // Optional로 너의 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해.
        // User user = userRepository.findById(id).get(); // get()을 바로하면 확인하는 것이 아니라 바로 가져오는 것 위험함.
        
        // findbyid로 아이디를 찾으면 거기서 끝이지만, 만약 못찾으면 orelseget이라는 함수를 타고 빈 객체를 return한다
        // 즉 null을 return하지 않고 user() 빈 객체를 returngksek.
        // User user = userRepository.findById(id).orElseGet(new Supplier<User>(){
        //     @Override
        //     public User get(){
        //         return new User();
        //     }

        // }); 
        

        // new Supplier<IllegalArgumentException> 이렇게 쓰면 너무 타입을 찾거나 쓰는데 노력과 시간이 많이 든다
        // 이때 사용하는게 람다식 
        // orElseThrow(new Supplier<IllegalArgumentException>(()->{
        //     return new IllegalArgumentException("해당 사용자는 없습니다.") 
        // }) 로 처리할 수 있다.
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>(){
            @Override
            public IllegalArgumentException get(){
                return new IllegalArgumentException("해당 유저가 없습니다. id : " + id);  // 나중에 이 부분에 error 페이지나 이벤트화면을 보여주면 된다.
            }
        });

        // 요청을 웹브라우저로 했음 , reponse는 스프링 서버가 함. 
        // user 객체는 자바 오브젝트 웹브라우저가 이해할 수 없다. 즉 user 객체를 변환해야한다. 그 데이터형식이 JSON이다.(Gson 라이브러리)
        // 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
        // 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서 user 오브젝트를 json으로 변환하여 리턴해준다.
        return user;
    }
    //http://localhost:8000/blog/dummy/join (요청)
    // http의 body에 username, password, email 데이터를 가지고 (요청)
    // @PostMapping("/dummy/join")
    // public String join(String username, String password, String email){ // key=value(약속된 규칙)
        

    //     System.out.println("username:" + username );
    //     System.out.println("password:" + password );
    //     System.out.println("email:" + email );
    //     return "회원가입이 완료되었습니다.";
    // }

    @PostMapping("/dummy/join")
    public String join(User user){ // key=value(약속된 규칙)
        


        System.out.println("username:" + user.getUsername() );
        System.out.println("password:" + user.getPassword() );
        System.out.println("email:" + user.getEmail() );
        System.out.println("roll" + user.getRole() );
        System.out.println("createDate:" + user.getCreateDate() );
        
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
