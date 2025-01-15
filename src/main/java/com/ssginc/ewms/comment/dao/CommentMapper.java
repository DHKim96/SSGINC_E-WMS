package com.ssginc.ewms.comment.dao;

// CommentMapper 인터페이스에서 사용하는 클래스 및 어노테이션을 import
import com.ssginc.ewms.comment.vo.CommentVO; // CommentVO 클래스 가져오기
import org.apache.ibatis.annotations.Mapper; // MyBatis에서 매퍼 인터페이스로 사용하기 위한 어노테이션

import java.util.List; // List 컬렉션 클래스

/**
 * CommentMapper는 MyBatis를 사용하여 데이터베이스와 상호작용하는 인터페이스입니다.
 * 댓글(Comment)과 관련된 CRUD 작업을 정의합니다.
 */
@Mapper
public interface CommentMapper {

    /**
     * 댓글을 데이터베이스에 삽입합니다.
     * @param commentVO 삽입할 댓글의 데이터가 포함된 객체
     * @return 삽입된 행(row)의 수
     */
    int insertComment(CommentVO commentVO);

    /**
     * 데이터베이스에 저장된 댓글을 수정합니다.
     * @param commentVO 수정할 댓글의 데이터가 포함된 객체
     * @return 수정된 행(row)의 수
     */
    int updateComment(CommentVO commentVO);

    /**
     * 특정 댓글을 데이터베이스에서 삭제합니다.
     * @param commentId 삭제할 댓글의 고유 ID
     * @return 삭제된 행(row)의 수
     */
    int deleteComment(int commentId);

    /**
     * 특정 게시글에 대한 댓글 목록을 조회합니다.
     * @param boardId 조회할 댓글이 속한 게시글의 ID
     * @return 해당 게시글의 댓글 목록
     */
    List<CommentVO> getCommentByBbsId(int boardId);
}
