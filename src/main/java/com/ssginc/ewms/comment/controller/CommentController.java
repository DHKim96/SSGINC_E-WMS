package com.ssginc.ewms.comment.controller;

import com.ssginc.ewms.comment.service.CommentService;
import com.ssginc.ewms.comment.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("comment")
@RequiredArgsConstructor // Lombok annotation to generate a constructor for final fields
public class CommentController {

    private final CommentService commentService;

    /*
     * 댓글 작성 요청 처리
     * "/comment/create2" URL로 들어오는 POST 요청을 처리하며,
     * 댓글 데이터를 받아서 저장합니다.
     * 요청 본문은 JSON 형식으로 전달되며, @RequestBody로 CommentVO 객체에 매핑됩니다.
     */
    @PostMapping("create2")
    @ResponseBody // Response를 JSON 형식으로 반환
    public int create2(@RequestBody CommentVO commentVO, Model model) {
        System.out.println("create2 화면 호출 >>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("commentVO >>>>>>>>>>>>>>>>> " + commentVO);

        try {
            int result = commentService.insertComment(commentVO); // 댓글 저장 로직
            if (result == 1) {
                return 1; // 성공 시 1 반환
            } else {
                return 0; // 실패 시 0 반환
            }
        } catch (Exception e) {
            e.printStackTrace(); // 에러 로그 출력
            return 0; // 에러 발생 시 0 반환
        }
    }

    /*
     * 댓글 수정 요청 처리
     * "/comment/update" URL로 들어오는 PUT 요청을 처리하며,
     * 수정된 댓글 데이터를 받아서 업데이트합니다.
     * 요청 본문은 JSON 형식으로 전달되며, @RequestBody로 CommentVO 객체에 매핑됩니다.
     */
    @PutMapping("update")
    @ResponseBody // Response를 JSON 형식으로 반환
    public boolean updateComment(@RequestBody CommentVO commentVO, Model model) {
        System.out.println("-------------" + commentVO);

        int result = commentService.updateComment(commentVO); // 댓글 수정 로직
        boolean result2 = false;
        if (result == 1) {
            result2 = true; // 수정 성공 시 true 반환
        }
        return result2; // 수정 실패 시 false 반환
    }

    /*
     * 댓글 삭제 요청 처리
     * "/comment/delete/{commentId}" URL로 들어오는 DELETE 요청을 처리하며,
     * 특정 댓글 ID에 해당하는 댓글을 삭제합니다.
     * {commentId}는 경로 변수로 전달됩니다.
     */
    @DeleteMapping("delete/{commentId}")
    @ResponseBody // Response를 JSON 형식으로 반환
    public boolean deleteComment(@PathVariable("commentId") int commentId) {
        System.out.println("@DeleteMapping(\"delete/{commentId}\")------------- " + commentId);

        int result = commentService.deleteComment(commentId); // 댓글 삭제 로직
        boolean result2 = false;
        if (result == 1) {
            result2 = true; // 삭제 성공 시 true 반환
        }
        return result2; // 삭제 실패 시 false 반환
    }
}
