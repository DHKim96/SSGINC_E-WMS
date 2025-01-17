package com.ssginc.ewms.comment.service;

import com.ssginc.ewms.comment.vo.CommentVO;

import java.util.List;

public interface CommentService {
    // 댓글 추가
    int insertComment(CommentVO commentVO);

    // 댓글 수정
    int updateComment(CommentVO commentVO);

    // 댓글 삭제
    int deleteComment(int commentId);

    //특정 게시글의 댓글 가져오기
    List<CommentVO> getCommentByBbsId(int boardId);
}
