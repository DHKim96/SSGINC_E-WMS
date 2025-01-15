package com.ssginc.ewms.comment.vo;

// Lombok 라이브러리를 사용하여 반복적인 코드(게터, 세터, 생성자 등)를 자동으로 생성하도록 설정
import lombok.AllArgsConstructor; // 모든 필드를 초기화하는 생성자를 자동으로 생성
import lombok.Data; // 게터, 세터, toString, equals, hashCode 등을 자동으로 생성
import lombok.NoArgsConstructor; // 기본 생성자를 자동으로 생성

import java.time.LocalDateTime; // Java의 날짜 및 시간 데이터를 처리하기 위한 클래스

/**
 * CommentVO 클래스는 댓글(Comment) 데이터를 관리하기 위한 VO(Value Object) 클래스입니다.
 * VO는 주로 데이터를 담아 전달하거나 조회하는 데 사용됩니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {
    private int commentId; // 댓글의 고유 ID
    private LocalDateTime commentDate; // 댓글 작성 날짜와 시간
    private String comment; // 댓글 내용
    private int memberNo; // 댓글 작성자의 회원 번호
    private int boardId; // 댓글이 속한 게시글의 ID
}
