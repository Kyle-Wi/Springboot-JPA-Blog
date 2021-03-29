package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




// ORM -> JAVA(모든 언어포함) Object를 테이블로 매핑해주는 기술, 즉 나는 object만 만들면 된다.
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity // User 클래스가 프로젝트가 시작될 때 변수를 읽어서 자동으로 mysql에 테이블이 생성된다.
public class User {
    
    @Id //primary keys
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. 만약에 ORACLE을 연결하면 시퀀스, MYSQL이면 AUTO_INCREMENT를 사용한다는 것
    // application.yml에 use-new-id-generator-mappings가 true면 jpa가 기본적으로 사용하는 넘버링 전략을 따라간다는 것이고 false면 안한다는 것 
    private int id; // 시퀀스(oracle), auto_increment(mysql) , 만약 여기에 아무값도 안 넣어도 자동으로 전략을 따라감


    @Column(nullable = false, length = 30, unique = true) //null값을 거부하고. length를 30자로 한정, unique 즉 중복 가입 안돼 @
    private String username; // 아이디

    @Column(nullable = false, length = 100) //null값을 거부하고. length를 100자로 한정 123456 => 해슁 (비밀번호 암호화할거라서 100자로 넉넉하게)
    private String password;

    @Column(nullable = false, length = 50) //null값을 거부하고. length를 50자로 한정
    private String email;

    // @ColumnDefault("'user'") 
    // DB에는 ROLETYPE이 없다. 
    @Enumerated(EnumType.STRING)
    private RoleType role; // Enum을 쓰는게 좋다. 타입이 강제되어서 실수할 가능성을 줄인다.// ADMIN, USER, MANAGER 등등 enum을 통해 도메인을 정할 수 있다. 예컨대 저 3개중에 하나만 쓸 수 있음

    @CreationTimestamp // 시간이 자동으로 입력이 된다.
    private Timestamp createDate; //java.sql , 만약에 여기에 아무것도 안 넣어도 현재시간이 자동으로 입력 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    

}

// application.yml에 ddl-auto가 create가 되어 있는지 봐야함. 근데 프로젝트가 실행될 때마다 다시 만들어짐.... 조심해야함 한번만 create, 나중엔 update