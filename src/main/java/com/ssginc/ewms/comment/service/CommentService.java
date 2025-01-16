package com.ssginc.ewms.comment.service;

import com.ssginc.ewms.comment.vo.CommentVO;
import java.util.List;

public interface CommentService {

    /*
     * 댓글 추가
     * @param commentVO 댓글 정보를 담고 있는 VO 객체
     * @return 성공 시 1, 실패 시 0 반환
     */
    int insertComment(CommentVO commentVO);

    /*
     * 댓글 수정
     * @param commentVO 수정된 댓글 정보를 담고 있는 VO 객체
     * @return 성공 시 1, 실패 시 0 반환
     */
    int updateComment(CommentVO commentVO);

    /*
     * 댓글 삭제
     * @param commentId 삭제할 댓글의 고유 ID
     * @return 성공 시 1, 실패 시 0 반환
     */
    int deleteComment(int commentId);

    /*
     * 특정 게시글의 댓글 목록 가져오기
     * @param boardId 게시글의 고유 ID
     * @return 해당 게시글에 달린 댓글 목록(List<CommentVO>)
     */
    List<CommentVO> getCommentByBbsId(int boardId);
}

