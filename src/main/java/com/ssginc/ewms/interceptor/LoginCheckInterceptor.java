package com.ssginc.ewms.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;

/**
 * 비인가 사용자의 접근을 제한하기 위한 인터셉터 구현체
 */
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        log.info("[LOGIN CHECK INTERCEPTOR] 요청 URI: {}", requestURI);

        HttpSession session = request.getSession(false); // getSession 은 기본적으로 세션이 없으면 새로 생성
        // => false 를 통해 새 새션 생성을 방지

        if (session == null || session.getAttribute("loginUser") == null) {
            log.info("미인증 사용자 요청 - IP: {}, User-Agent: {}",
                    request.getRemoteAddr(), request.getHeader("User-Agent"));
            response.sendRedirect("/login?redirectURL=" + URLEncoder.encode(requestURI, "UTF-8") + "&msg=true");
            // false 반환 시 다음 인터셉터가 호출되지 않음
            return false;
        }

        return true; // 인증된 사용자
    }
}
