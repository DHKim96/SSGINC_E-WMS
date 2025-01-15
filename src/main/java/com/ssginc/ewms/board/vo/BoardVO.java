package com.ssginc.ewms.board.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * BoardVO 클래스는 게시판의 데이터를 저장하는 VO(Value Object)입니다.
 * Lombok 라이브러리를 사용하여 코드를 간결하게 유지합니다.
 */

@Data //Getter/Setter 메서드, toString() 자동 생성
@NoArgsConstructor //기본 생성자 자동 생성
@AllArgsConstructor //모든 멤버변수 생성자 자동 생성
@Builder //Builder 패턴을 사용하여 객체를 생성할 수 있도록 지원
public class BoardVO {
    private int boardId; // 게시글 번호(Primary Key)
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private int memberNo; // 작성자(회원)의 고유 번호
    private int viewCount; // 조회수
    private String boardType; // 게시판 유형(ex. 감사/공지, 문제해결)
    private LocalDateTime boardDate; // 게시글 작성 또는 수정 시간 (TIMESTAMP 형식)
}
