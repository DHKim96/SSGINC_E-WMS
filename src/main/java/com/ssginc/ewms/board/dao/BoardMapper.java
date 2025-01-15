package com.ssginc.ewms.board.dao;

import com.ssginc.ewms.board.vo.BoardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BoardMapper는 MyBatis를 사용하여 데이터베이스와 상호작용하기 위한 DAO(Data Access Object) 인터페이스입니다.
 * - MyBatis가 이 인터페이스를 기반으로 구현체를 자동으로 생성합니다.
 * - @Mapper 애노테이션을 사용하여 MyBatis에서 해당 인터페이스를 매퍼로 인식하게 합니다.
 *
 * 주요 역할:
 * - 게시판 관련 CRUD(Create, Read, Update, Delete) 및 특정 데이터 검색 기능을 정의합니다.
 */
@Mapper
public interface BoardMapper {

    /**
     * 새 게시글을 데이터베이스에 삽입합니다.
     *
     * @param boardVO 삽입할 게시글의 정보가 담긴 VO 객체
     * @return 삽입된 행(row)의 수를 반환
     */
    int insertBoard(BoardVO boardVO);

    /**
     * 게시글 번호를 기반으로 특정 게시글을 조회합니다.
     *
     * @param boardId 조회할 게시글의 고유 번호
     * @return 조회된 게시글의 정보가 담긴 VO 객체
     */
    BoardVO selectBoardByNo(int boardId);

    /**
     * 모든 게시글을 조회합니다.
     *
     * @return 모든 게시글 정보를 담은 VO 객체의 리스트
     */
    List<BoardVO> selectBoardAll();

    /**
     * 게시글 정보를 업데이트합니다.
     *
     * @param boardVO 업데이트할 게시글의 새로운 정보가 담긴 VO 객체
     * @return 업데이트된 행(row)의 수를 반환
     */
    int updateBoard(BoardVO boardVO);

    /**
     * 특정 게시글을 삭제합니다.
     *
     * @param boardId 삭제할 게시글의 고유 번호
     * @return 삭제된 행(row)의 수를 반환
     */
    int deleteBoard(int boardId);

    /**
     * 특정 키워드가 포함된 게시글을 검색합니다.
     *
     * @return 검색된 게시글 정보가 담긴 VO 객체의 리스트
     */
    List<BoardVO> getBoardByContent(
            @Param("find") String find,
            @Param("boardType") String boardType);
}

