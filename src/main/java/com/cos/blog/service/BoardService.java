package com.cos.blog.service;



import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// 스프링이 컴포넌트 스캔을 통해서 bean에 등록을 해줌, ioc를 해준다.
// 서비스 분리
@Service 
public class BoardService {
    

    @Autowired
    private BoardRepository boardRepository;



    @Transactional
    public void 글쓰기(Board board, User user){
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board); 

    }

    @Transactional(readOnly = true)
    public Board 글상세보기(int id){
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
                });
    }

    @Transactional
    public void 글삭제하기(int id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정하기(int id, Board requestBoard){
        Board board = boardRepository.findById(id)
                        .orElseThrow(()->{
                            return new IllegalArgumentException("글 수정이 실패하였습니다 : 아이디를 찾을 수 없습니다.");
                        }); // 영속화
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해당함수가 종료 시(서비스단에서 서비스 종료될 때) 트랜잭션이 종료되는데 이 때 더티체킹이 발생함. 자동 업데이트 db flush
    }

    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable){

        return boardRepository.findAll(pageable); // 페이징을 하면 리턴 타입이 Page로 바뀐다.
    }


    // 전통적인 로그인 방식
    // @Transactional(readOnly = true) // select 할 때 트랜잭션 시작, 서비스 종료 시 트랜잭션 종료 ( 정합성 보장 )
    // public User 로그인(User user){

    //     return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        
    // }
}