package com.ssginc.ewms.board.service;

import com.ssginc.ewms.board.vo.BoardVO;

import java.util.List;

public interface BoardService {
    /*
     * 게시글 생성 메서드
     * BoardMapper의 `insertBoard` 메서드를 호출하여 새로운 게시글을 DB에 저장합니다.
     * @param boardVO 게시글 정보가 담긴 객체
     * @return 성공적으로 저장된 경우 1을 반환
     */
    int insertBoard(BoardVO boardVO);

    /*
     * 모든 게시글 조회 메서드
     * BoardMapper의 `selectBoardAll` 메서드를 호출하여 모든 게시글 정보를 반환합니다.
     * @return 게시글 목록(List<BoardVO>)
     */
    List<BoardVO> getAllBoards();

    /*
     * 게시글 번호로 특정 게시글 조회 메서드
     * BoardMapper의 `selectBoardByNo` 메서드를 호출하여 해당 게시글 정보를 반환합니다.
     * @param boardNo 게시글 번호
     * @return 해당 게시글 정보(BoardVO)
     */
    BoardVO getBoardById(int boardId);

    /*
     * 게시글 내용으로 검색하는 메서드
     * BoardMapper의 `getBoardByContent` 메서드를 호출하여
     * 입력된 키워드와 일치하는 게시글 목록을 반환합니다.
     * @param find 검색 키워드
     * @return 검색된 게시글 목록(List<BoardVO>)
     */
    //
    List<BoardVO> getBoardByContent(String find,
                                    String boardType);

    /*
     * 게시글 수정 메서드
     * BoardMapper의 `updateBoard` 메서드를 호출하여
     * 전달된 게시글 정보를 기반으로 게시글을 수정합니다.
     * @param boardVO 수정할 게시글 정보가 담긴 객체
     * @return 수정된 행의 수 (성공 시 1 이상 반환)
     */
    int updateBoard(BoardVO boardVO);

    /*
     * 게시글 삭제 메서드
     * BoardMapper의 `deleteBoard` 메서드를 호출하여
     * 특정 게시글을 삭제합니다.
     * @param boardNo 삭제할 게시글 번호
     * @return 삭제된 행의 수 (성공 시 1 이상 반환)
     */
    int deleteBoard(int boardId);
}
