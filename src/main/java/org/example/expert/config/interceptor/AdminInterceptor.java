package org.example.expert.config.interceptor;

import jakarta.servlet.http.HttpServletRequest; // HTTP 요청 정보를 가져오기 위한 라이브러리
import jakarta.servlet.http.HttpServletResponse; // HTTP 응답 정보를 가져오기 위한 라이브러리
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component; // Spring이 자동으로 관리하도록 하는 기능
import org.springframework.web.servlet.HandlerInterceptor; // Interceptor의 기본 인터페이스

import java.time.LocalDateTime; // 현재 날짜와 시간을 가져오는 기능

@Slf4j // 로깅(Logger) 기능을 자동으로 활성화
@Component
public class AdminInterceptor implements HandlerInterceptor {

    // Interceptor가 실행될 때 자동으로 실행되는 함수
    @Override

    // 요청이 들어오면 먼저 실행되는 메서드 (API 실행 전에 동작)
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 현재 사용자의 권한을 가져오기 (ADMIN인지 확인)
        String userRole = (String) request.getSession().getAttribute("userRole");

        // 사용자가 요청한 API 주소 가져오기
        String requestUrl = request.getRequestURI();

        // 현재 시간 가져오기 (요청한 시간 기록)
        LocalDateTime requestTime = LocalDateTime.now();

        // ADMIN 권한이 없으면 API 실행 차단
        if (userRole == null || !userRole.equals("ADMIN")) {

            // 로그에 '접근 차단' 기록
            log.warn("관리자 권한이 없습니다. URL : {}", requestUrl);

            // 응답을 403(Forbidden)으로 설정 -> API 실행 차단
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자 권한이 없습니다.");

            // API 실행 차단
            return false;
        }

        // ADMIN 권한이 있으면 API 실행 허용
        log.info("관리자 권한으로 API를 요청합니다. URL: {}, Time: {}", requestUrl, requestTime);

        // API 실행
        return true;
    }
}

















