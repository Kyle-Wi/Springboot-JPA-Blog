package com.cos.blog.controller;

import com.cos.blog.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {
    

    @Autowired
    private BoardService boardService;
    

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model){
        
        model.addAttribute("board", boardService.글상세보기(id));
        
        return "board/updateForm";

    }


    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model){
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/detail";
    }

    @GetMapping({"/",""})
    public String index(Model model, @PageableDefault(size=3, sort="id", direction=Sort.Direction.DESC) Pageable pageable){ // @AuthenticationPrincipal PrincipalDetail principal 시큐리티가 로그인을 하면 이제 이 홈으로 들어오는데 이 때 만들어진 세션을 어떻게 찾을까?
        // /WEB-INF/views/index.jsp
        
        model.addAttribute("boards", boardService.글목록(pageable)); // boards라는 객체가 보내진다. 

        return "index"; // viewResolver 작동!! index로 넘어갈 때 model의 속성 값을 가지고 감. index.jsp에서 items라는 속성으로 속성을 받을 수 있다.
    }


    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }
}
