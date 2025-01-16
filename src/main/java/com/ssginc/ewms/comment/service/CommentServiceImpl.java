package com.ssginc.ewms.comment.service;

import com.ssginc.ewms.comment.dao.CommentMapper;
import com.ssginc.ewms.comment.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CommentServiceImpl 클래스는 댓글(Comment)에 대한 비즈니스 로직을 구현한 서비스 클래스입니다.
 * 이 클래스는 Service 어노테이션을 사용하여 Spring 컨테이너에 서비스로 등록되며,
 * CommentService 인터페이스를 구현하여 관련 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor // final 필드에 대해 생성자를 자동으로 생성하는 Lombok 어노테이션
public class CommentServiceImpl implements CommentService {

    // 의존성 주입: CommentMapper 객체를 주입받아 사용
    private final CommentMapper commentMapper;

    /**
     * 댓글 추가 메서드
     * @param commentVO 추가할 댓글 정보
     * @return 삽입 결과 (1: 성공, 0: 실패)
     */
    @Override
    public int insertComment(CommentVO commentVO) {
        // 현재 시간을 댓글 작성 날짜로 설정
        commentVO.setCommentDate(LocalDateTime.now());
        // 댓글 정보를 데이터베이스에 삽입
        return commentMapper.insertComment(commentVO);
    }

    /**
     * 댓글 수정 메서드
     * @param commentVO 수정할 댓글 정보
     * @return 수정 결과 (1: 성공, 0: 실패)
     */
    @Override
    public int updateComment(CommentVO commentVO) {
        // 전달받은 댓글 정보를 기반으로 데이터베이스에 업데이트
        return commentMapper.updateComment(commentVO);
    }

    /**
     * 댓글 삭제 메서드
     * @param commentId 삭제할 댓글의 ID
     * @return 삭제 결과 (1: 성공, 0: 실패)
     */
    @Override
    public int deleteComment(int commentId) {
        // 주어진 댓글 ID를 기준으로 댓글 삭제
        return commentMapper.deleteComment(commentId);
    }

    /**
     * 특정 게시글의 댓글 가져오기
     * @param boardId 댓글을 가져올 게시글 ID
     * @return 해당 게시글의 댓글 리스트
     */
    @Override
    public List<CommentVO> getCommentByBbsId(int boardId) {
        // 게시글 ID를 기준으로 해당 게시글에 작성된 댓글 리스트 조회
        return commentMapper.getCommentByBbsId(boardId);
    }
}
