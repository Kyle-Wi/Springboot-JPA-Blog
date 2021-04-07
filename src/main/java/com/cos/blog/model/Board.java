package com.cos.blog.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Board {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private int id;

    @Column(nullable=false, length=100)
    private String title;


    @Lob //대용량 데이터
    private String content; // 섬머노트 라이브러리 , 일반 노트가 디자인 되어서 들어간다.<html>태그가 섞여서 디자인 된다.


    private int count; // 조회수

    @ManyToOne(fetch=FetchType.EAGER) //fetch:user 정보 필요한게 한개라서 바로 가져온다.// many = board, one = User , 즉 한 명의 유저가 여러 개의 BOARD를 쓸 수 있다. // 연관관계를 나타냄
    @JoinColumn(name="userId") // userId라는 필드값으로 만들어진다.
    private User user; // DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다. //User 객체를 참조하기 때문에 자동으로 FK 참조가 되는 것




    @JsonIgnoreProperties("{board}")
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // 글을 삭제하면 댓글도 삭제되야하는데 그때 CASCADE에 REMOVE를 설정하면 된다. 이를 따로 공부하자
    @javax.persistence.OrderBy("id desc") //fetch: 필요할 때 가져올 때는 lazy // mappedby가 있으면 연관관계의 주인이 아니다(난 fk가 아니다) db에 칼럼을 만들지 마세요.
    // joincolumn은 필요가 없다. 왜냐하면 한 board에 여러 댓글이 달릴 수 있는데 이때 원자성을 잃어버리기 때문에
    private List<Reply> replys; // join문만을 통해 정보를 얻기 위해 필요한 것이다. 여러 댓글이 달릴 수 있으니 LIST로 가져온다.

    @CreationTimestamp
    private java.sql.Timestamp createDate;


}
