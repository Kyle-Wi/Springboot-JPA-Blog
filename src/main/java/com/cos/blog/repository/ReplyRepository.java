package com.cos.blog.repository;

import com.cos.blog.model.Reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository <Reply,Integer> {


    // interface안에서는 public안써도 된다.
    @Modifying
    @Query(value="INSERT INTO reply (userId, boardId, content, createDate) VALUES(?1,?2,?3,now())", nativeQuery=true)
    int mSave(int userId, int boardId, String content);
    
    // @Modifying
    // @Query(value="DELETE FROM reply WHERE nameId = ?1 AND boardId = ?2 And id = ?3", nativeQuery=true)
    // int mDelete(int userId, int boardId, int replyId);
}
