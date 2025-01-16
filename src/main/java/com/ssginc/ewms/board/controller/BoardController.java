package com.ssginc.ewms.board.controller;

import com.ssginc.ewms.board.service.BoardService;
import com.ssginc.ewms.board.vo.BoardVO;
import com.ssginc.ewms.comment.service.CommentService;
import com.ssginc.ewms.comment.vo.CommentVO;
import com.ssginc.ewms.member.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
/*
 * 이 클래스는 Spring MVC의 Controller 역할을 수행하며,
 * "/board"로 시작하는 요청을 처리합니다.
 */
@RequestMapping("board")
@RequiredArgsConstructor
/*
 * final 필드에 대해 생성자를 자동으로 만들어주는 Lombok 어노테이션입니다.
 * BoardService 의존성을 생성자 주입 방식으로 주입받습니다.
 */
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    /*
     * 게시판 전체 목록 요청 처리
     * "/board" URL로 들어오는 GET 요청을 처리합니다.
     * 게시판의 모든 게시글 데이터를 조회하여 view로 전달합니다.
     */
    @GetMapping("board")
    public String board(Model model, HttpSession session) {
        System.out.println("board 화면 호출 >>>>>>>>>>>>>");
        MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
        session.setAttribute("memberNo", loginUser.getMemberNo());
        System.out.println("===============" + loginUser);
        List<BoardVO> list = boardService.getAllBoards();
        System.out.println(list);
        model.addAttribute("list", list);
        return "board/board";
    }

    /*
     * 게시판 글쓰기 화면 요청 처리
     * "/create" URL로 들어오는 GET 요청을 처리하며,
     * 게시글 작성 화면을 반환합니다.
     */
    @GetMapping("create")
    public String create(BoardVO boardVO, Model model) {
        System.out.println("create 화면 호출 >>>>>>>>>>>>>");
        return "board/create"; // templates/board/create.html 반환
    }

    /*
     * 글쓰기 처리
     * "/create2" URL로 들어오는 POST 요청을 처리하며,
     * 작성된 게시글을 저장하고 결과 화면을 반환합니다.
     */
    @PostMapping("create2")
    public String create2(BoardVO boardVO, Model model, HttpSession session) {

        MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
        System.out.println("===============" + loginUser);
        session.setAttribute("memberNo", loginUser.getMemberNo());
        System.out.println(boardVO);
        System.out.println("create2 화면 호출 >>>>>>>>>>>");
        boardVO.setContent(boardVO.getContent().replace("\n", "<br>"));
        try {
            int result = boardService.insertBoard(boardVO);
            if (result == 1) {
                model.addAttribute("boardVO", boardVO);
                return "redirect:/board/board"; // 성공 시 결과 화면 --> 컨트롤러의 @GetMapping 호출
            } else {
                return "error/error"; // 실패 시 에러 화면
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error/error";
        }
    }

    /*
     * 게시글 수정 화면 요청 처리
     * "/update" URL로 들어오는 GET 요청을 처리하며,
     * 특정 게시글의 수정 화면을 반환합니다.
     */
    @GetMapping("update")
    public String update(int boardId, Model model) {
        System.out.println("update 화면 호출 >>>>>>>>>>>>");
        System.out.println("update id >>>>>>>" + boardId);

        BoardVO boardVO = boardService.getBoardById(boardId);
        boardVO.setContent(boardVO.getContent().replace("<br>", "\n"));

        model.addAttribute("boardVO", boardVO);
        return "board/update"; // templates/board/update.html 반환
    }

    /*
     * 게시글 수정 처리
     * "/update2" URL로 들어오는 POST 요청을 처리하며,
     * 수정된 게시글을 저장하고 결과 화면을 반환합니다.
     */
    @PostMapping("update2")
    public String update2(BoardVO boardVO, Model model) {
        System.out.println("update2 처리 호출 >>>>>>>>>>>>>>>");
        System.out.println("boardVO >>>>>>>>>>>> " + boardVO);
        int result = boardService.updateBoard(boardVO);
        if (result > 0) {
            return "redirect:/board/board"; // 성공 시 결과 화면
        } else {
            return "error/error"; // 실패 시 에러 화면
        }
    }

    /*
     * 게시글 상세 보기
     * "/read" URL로 들어오는 GET 요청을 처리하며,
     * 특정 게시글의 상세 내용을 반환합니다.
     */
    @GetMapping("read")
    public String read(int boardId, Model model) {
        System.out.println("read 화면 호출 >>>>>>>>>>>");
        BoardVO boardVO = boardService.getBoardById(boardId);
        System.out.println(boardVO);
        boardVO.setContent(boardVO.getContent().replace("<br>", "\n"));
        List<CommentVO> list = commentService.getCommentByBbsId(boardId);
        System.out.println(list);
        if (boardVO != null) {
            model.addAttribute("boardVO", boardVO);
            model.addAttribute("list", list);
        }
        return "board/read"; // templates/board/read.html 반환
    }

    /*
     * 게시글 내용 검색 처리
     * "/find" URL로 들어오는 GET 요청을 처리하며,
     * 입력된 키워드로 게시글을 검색한 결과를 반환합니다.
     */
    @GetMapping("find")
    public String find(
            @RequestParam(required = false, defaultValue = "") String boardType,
            @RequestParam(required = false, defaultValue = "") String find,
            Model model) {
        System.out.println("find 화면 호출 >>>>>>>>>>>");
        System.out.println("검색어: " + find); // 값이 제대로 출력되는지 확인
        System.out.println(boardType);

        // 검색 조건이 없을 경우 전체 게시물 출력
        if (boardType.isEmpty() && find.isEmpty()) {
            model.addAttribute("list", boardService.getAllBoards());
        } else {
            // 검색 조건에 따른 결과 출력
            System.out.println("호출되니???");
            List<BoardVO> result = boardService.getBoardByContent(find, boardType);
            System.out.println(result + "================");
            model.addAttribute("list", result);
        }
        return "board/board";
    }

    /*
     * 게시글 삭제 처리
     * "/delete" URL로 들어오는 GET 요청을 처리하며,
     * 특정 게시글을 삭제하고 게시판 목록 화면을 반환합니다.
     */
    @GetMapping("delete")
    public String delete(int boardId, Model model) {
        System.out.println("delete 처리 호출 >>>>>>>>>>>>>");
        System.out.println("board no >>>>>>>>>>" + boardId);
        int result = boardService.deleteBoard(boardId);
        System.out.println(result);
        if (result > 0) {
            List<BoardVO> list = boardService.getAllBoards();
            model.addAttribute("list", list);
            return "board/board"; // 성공 시 전체 목록 화면 반환
        } else {
            return "error/error"; // 실패 시 에러 화면
        }
    }
}