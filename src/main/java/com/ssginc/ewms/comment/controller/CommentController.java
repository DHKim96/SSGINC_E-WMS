package com.ssginc.ewms.comment.controller;

import com.ssginc.ewms.comment.service.CommentService;
import com.ssginc.ewms.comment.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("create2")
    @ResponseBody
    public int create2(@RequestBody CommentVO commentVO, Model model) {
        System.out.println("create2 화면 호출 >>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("commentVO >>>>>>>>>>>>>>>>> " + commentVO);
        try {
            int result = commentService.insertComment(commentVO);
            if (result == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @PutMapping("update")
    @ResponseBody
    public boolean updateComment(@RequestBody CommentVO commentVO, Model model) {
        //수정 로직 구현
        System.out.println("-------------" + commentVO);
        int result = commentService.updateComment(commentVO);
        boolean result2 = false;
        if (result == 1) {
            result2 = true;
        }
        return result2;
    }

    @DeleteMapping("delete/{commentId}")
    @ResponseBody
    public boolean deleteComment(@PathVariable("id") int commentId) {
        //삭제 로직 구현
        System.out.println("@DeleteMapping(\"delete/{id}\")------------- " + commentId);
        int result = commentService.deleteComment(commentId);
        boolean result2 = false;
        if (result == 1) {
            result2 = true;
        }
        return result2;
    }
}
