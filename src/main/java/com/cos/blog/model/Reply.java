package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class Reply {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false, length=200)
    private String content;

    @ManyToOne // 여러 개의 댓글은 하나의 게시물에 들어감, 기준은 앞에 있는 것 reply = many, one = board 
    @JoinColumn(name="boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;

    
    public void update(User user, Board board, String content) {
        setUser(user);
        setBoard(board);
        setContent(content);

    }


    @Override
    public String toString() {
        return "Reply [board=" + board + ", content=" + content + ", createDate=" + createDate + ", id=" + id
                + ", user=" + user + "]";
    };

    
    

}
