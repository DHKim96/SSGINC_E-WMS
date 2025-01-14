package com.ssginc.ewms.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("error")
public class CustomErrorController implements ErrorController {

    @GetMapping("")
    public String errorPage(@RequestParam(value = "message", required = false, defaultValue = "서버와의 통신 중 에러가 발생했습니다.")  String message, HttpServletRequest request, Model model) {

        // Referer 헤더에서 이전 페이지 URL 가져오기
        String referer = request.getHeader("Referer");

        // Referer가 있으면 리다이렉트 URL 설정
        if (referer != null && !referer.isEmpty()) {
            model.addAttribute("redirectURL", referer);
        } else {
            // Referer가 없으면 메인 페이지로 이동
            model.addAttribute("redirectURL", "/");
        }

        model.addAttribute("message", message);
        return "error/error";
    }
}
