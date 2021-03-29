package com.cos.blog.repository;

import java.util.Optional;

import com.cos.blog.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

//DAO
//자동으로 bean으로 등록된다. 
//@Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer>{

    
    // SELECT * FROM user where username = ?1
    Optional<User> findByUsername(String username);


    //JPA Naming 쿼리 전략
    // SELECT * FROM user WHERE username = ? AND password = ? 이런 쿼리가 자동으로 ..
    // 대문자 조심
    // User findByUsernameAndPassword(String username, String password);

//    @Query(value="SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//    User login(String username, String password);
}



