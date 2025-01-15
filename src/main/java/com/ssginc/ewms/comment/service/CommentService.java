package com.ssginc.ewms.comment.service;

import com.ssginc.ewms.comment.dao.CommentMapper;
import com.ssginc.ewms.comment.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    // 댓글 추가
    public int insertComment(CommentVO commentVO) {
        commentVO.setCommentDate(LocalDateTime.now());
        return commentMapper.insertComment(commentVO);
    }

    // 댓글 수정
    public int updateComment(CommentVO commentVO) {
        return commentMapper.updateComment(commentVO);
    }

    // 댓글 삭제
    public int deleteComment(int commentId) {
        return commentMapper.deleteComment(commentId);
    }

    //특정 게시글의 댓글 가져오기
    public List<CommentVO> getCommentByBbsId(int boardId) {
        return commentMapper.getCommentByBbsId(boardId);
    }
}